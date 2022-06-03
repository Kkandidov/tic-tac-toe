package org.astashonok.battleservice.services.impl;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.entities.Move;
import org.astashonok.battleservice.exceptions.BattleException;
import org.astashonok.battleservice.exceptions.MoveException;
import org.astashonok.battleservice.factories.BattleFactory;
import org.astashonok.battleservice.models.BattleInfo;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.repositories.MoveRepository;
import org.astashonok.battleservice.services.AuthUserService;
import org.astashonok.battleservice.services.BattleService;
import org.astashonok.battleservice.validators.BattleValidator;
import org.astashonok.battleservice.validators.BattleCreationFormValidator;
import org.astashonok.battleservice.validators.MoveFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.astashonok.battleservice.constants.ErrorMessage.BATTLE_NOT_EXISTS;

@Service
@Transactional
public class BattleServiceImpl implements BattleService {

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private BattleFactory battleFactory;

    @Autowired
    private MoveFormValidator moveFormValidator;

    @Autowired
    private BattleValidator battleValidator;

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private BattleCreationFormValidator battleCreationFormValidator;

    @Override
    public void makeMove(MoveForm moveForm) {
        asserts(() -> moveFormValidator.validate(moveForm), MoveException::new);

        Optional.of(moveForm.getBattleId()).flatMap(battleId -> battleRepository.findById(battleId)).map(battle -> {
            asserts(() -> battleValidator.validate(battle), BattleException::new);
            return battle;
        }).ifPresent(battle -> makeMove(battle, moveForm));
    }

    @Override
    public List<Battle> getOpenedBattles() {
        return battleRepository.getAllByStatus(BattleStatus.OPENED);
    }

    @Override
    public void join(Long battleId) {
        Optional.ofNullable(battleId).flatMap(battleRepository::findById).map(battle -> {
            battle.setOParticipantId(authUserService.getCurrentUserId());
            battle.setStatus(BattleStatus.IN_PROCESS);
            return battle;
        }).map(battleRepository::save).orElseThrow(() -> new BattleException(String.format(BATTLE_NOT_EXISTS, battleId)));
    }

    @Override
    public Battle openBattle(BattleCreationForm form) {
        return battleRepository.save(battleFactory.create(form));
    }

    @Override
    public BattleInfo getBattleInfo(Long battleId) {
        return Optional.ofNullable(battleId)
                .flatMap(battleRepository::findById)
                .map(battle -> new BattleInfo(battle.getStatus(), battle.getWinnerId()))
                .orElseThrow(() -> new BattleException(String.format(BATTLE_NOT_EXISTS, battleId)));
    }

    private void asserts(Supplier<List<String>> validate, Function<String, RuntimeException> exceptionCreator) {
        List<String> messages = validate.get();
        if (!CollectionUtils.isEmpty(messages)) {
            throw exceptionCreator.apply(messages.toString());
        }
    }

    private void makeMove(Battle battle, MoveForm moveForm) {
        battle.setFieldNumberValue(authUserService.getCurrentUserId(), moveForm.getXCoordinate(), moveForm.getYCoordinate());
        battle.decrementRemainingFreeMoveCount();
        battle.swapNextMoveParticipantId();
        battle.setWinnerId(getWinnerId(battle));
        if (battle.getWinnerId() != null || battle.getRemainingFreeMoveCount() == 0) {
            battle.setStatus(BattleStatus.FINISHED);
        }
        battleRepository.save(battle);
        saveUserMove(moveForm);
    }

    private void saveUserMove(MoveForm moveForm) {
        Move move = new Move();
        move.setBattleId(moveForm.getBattleId());
        move.setUserId(authUserService.getCurrentUserId());
        move.setXCoordinate(moveForm.getXCoordinate());
        move.setYCoordinate(moveForm.getYCoordinate());
        move.setTime(new Date());

        moveRepository.save(move);
    }

    private Long getWinnerId(Battle battle) {
        int winningNumberInRow = battle.getWinningNumberInRow();

        for (int rowNumber = 0; rowNumber < battle.getBoardHeight() - winningNumberInRow + 1; rowNumber++) {
            for (int columnNumber = 0; columnNumber < battle.getBoardWidth() - winningNumberInRow + 1; columnNumber++) {
                Long winnerId = getWinnerId(battle, rowNumber, columnNumber);
                if (winnerId != null) {
                    return winnerId;
                }
            }
        }

        return null;
    }

    private Long getWinnerId(Battle battle, int rowNumber, int columnNumber) {
        Long winnerId = getWinnerIdByRows(battle, columnNumber, rowNumber);

        return winnerId == null
                ? (winnerId = getWinnerIdByColumns(battle, columnNumber, rowNumber)) == null
                ? getWinnerIdByDiagonals(battle, columnNumber, rowNumber)
                : winnerId
                : winnerId;
    }

    private Long getWinnerIdByDiagonals(Battle battle, int xCoordinate, int yCoordinate) {
        int[][] board = battle.getBoard();
        int winningNumberInRow = battle.getWinningNumberInRow();
        Long winnerId = getWinnerId(battle, getFirstDiagonalSum(xCoordinate, yCoordinate, board, winningNumberInRow));

        return winnerId == null
                ? getWinnerId(battle, getSecondDiagonalSum(xCoordinate, yCoordinate, board, winningNumberInRow))
                : winnerId;
    }

    private int getSecondDiagonalSum(int xCoordinate, int yCoordinate, int[][] board, int winningNumberInRow) {
        return getDiagonalSum(board, new AtomicInteger(yCoordinate),
                new AtomicInteger(xCoordinate + winningNumberInRow - 1),
                (row, column) -> row.get() < yCoordinate + winningNumberInRow && column.get() >= xCoordinate,
                (row, column) -> {
                    row.incrementAndGet();
                    column.decrementAndGet();
                });
    }

    private int getFirstDiagonalSum(int xCoordinate, int yCoordinate, int[][] board, int winningNumberInRow) {
        return getDiagonalSum(board, new AtomicInteger(yCoordinate), new AtomicInteger(xCoordinate),
                (row, column) -> column.get() < xCoordinate + winningNumberInRow
                        && row.get() < yCoordinate + winningNumberInRow,
                (row, column) -> {
                    row.incrementAndGet();
                    column.incrementAndGet();
                });
    }

    private int getDiagonalSum(int[][] board, AtomicInteger rowNumber, AtomicInteger columnNumber,
                               BiPredicate<AtomicInteger, AtomicInteger> iterationPredicate,
                               BiConsumer<AtomicInteger, AtomicInteger> iterationAction) {

        int diagonalSum = 0;
        for (; iterationPredicate.test(rowNumber, columnNumber); iterationAction.accept(rowNumber, columnNumber)) {
            diagonalSum += board[columnNumber.get()][rowNumber.get()];
        }
        return diagonalSum;
    }

    private Long getWinnerIdByRows(Battle battle, int xCoordinate, int yCoordinate) {
        int[][] board = battle.getBoard();
        for (int rowNumber = yCoordinate; rowNumber < yCoordinate + battle.getWinningNumberInRow(); rowNumber++) {
            int rowSum = getRowSum(board[rowNumber], xCoordinate, battle.getWinningNumberInRow());
            Long winnerId = getWinnerId(battle, rowSum);
            if (winnerId != null) {
                return winnerId;
            }
        }

        return null;
    }

    private int getRowSum(int[] row, int xCoordinate, int winningNumberInRow) {
        int rowSum = 0;
        for (int columnNumber = xCoordinate; columnNumber < xCoordinate + winningNumberInRow; columnNumber++) {
            rowSum += row[columnNumber];
        }
        return rowSum;
    }

    private Long getWinnerIdByColumns(Battle battle, int xCoordinate, int yCoordinate) {
        int[][] board = battle.getBoard();
        for (int columnNumber = xCoordinate; columnNumber < xCoordinate + battle.getWinningNumberInRow(); columnNumber++) {
            int columnSum = getColumnSum(board, yCoordinate, battle.getWinningNumberInRow(), columnNumber);
            Long winnerId = getWinnerId(battle, columnSum);
            if (winnerId != null) {
                return winnerId;
            }
        }

        return null;
    }

    private int getColumnSum(int[][] board, int yCoordinate, int winningNumberInRow, int columnNumber) {
        int columnSum = 0;
        for (int rowNumber = yCoordinate; rowNumber < yCoordinate + winningNumberInRow; rowNumber++) {
            columnSum += board[rowNumber][columnNumber];
        }
        return columnSum;
    }

    private Long getWinnerId(Battle battle, int columnSum) {
        int xNumberValueSum = battle.getXNumberValue() * battle.getWinningNumberInRow();
        int oNumberValueSum = battle.getONumberValue() * battle.getWinningNumberInRow();

        return columnSum == xNumberValueSum
                ? battle.getXParticipantId()
                : columnSum == oNumberValueSum
                ? battle.getOParticipantId()
                : null;
    }
}