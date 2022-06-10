package org.astashonok.battleservice.components.calculators.impl;

import lombok.NonNull;
import org.astashonok.battleservice.components.calculators.BattleStateCalculator;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleState;
import org.astashonok.battleservice.models.BattleStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

@Component
public class BattleStateCalculatorImpl implements BattleStateCalculator {

    @Override
    public BattleState calculateAndGet(@NonNull Battle battle) {
        BattleState battleState = new BattleState();

        UUID winnerId = null;
        int winningNumberInRow = battle.getWinningNumberInRow();

        for (int rowNumber = 0; rowNumber < battle.getBoardHeight() - winningNumberInRow + 1; rowNumber++) {
            for (int columnNumber = 0; columnNumber < battle.getBoardWidth() - winningNumberInRow + 1; columnNumber++) {
                winnerId = getWinnerId(battle, rowNumber, columnNumber);
                if (winnerId != null) {
                    battleState.setWinnerId(winnerId);
                }
            }
        }

        if (winnerId != null || battle.getRemainingFreeMoveCount() == 0) {
            battle.setStatus(BattleStatus.FINISHED);
        }

        return battleState;
    }


    private UUID getWinnerId(Battle battle, int rowNumber, int columnNumber) {
        UUID winnerId = getWinnerIdByRows(battle, columnNumber, rowNumber);

        return winnerId == null
                ? (winnerId = getWinnerIdByColumns(battle, columnNumber, rowNumber)) == null
                ? getWinnerIdByDiagonals(battle, columnNumber, rowNumber)
                : winnerId
                : winnerId;
    }

    private UUID getWinnerIdByDiagonals(Battle battle, int xCoordinate, int yCoordinate) {
        int[][] board = battle.getBoard();
        int winningNumberInRow = battle.getWinningNumberInRow();
        UUID winnerId = getWinnerId(battle, getFirstDiagonalSum(xCoordinate, yCoordinate, board, winningNumberInRow));

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

    private UUID getWinnerIdByRows(Battle battle, int xCoordinate, int yCoordinate) {
        int[][] board = battle.getBoard();
        for (int rowNumber = yCoordinate; rowNumber < yCoordinate + battle.getWinningNumberInRow(); rowNumber++) {
            int rowSum = getRowSum(board[rowNumber], xCoordinate, battle.getWinningNumberInRow());
            UUID winnerId = getWinnerId(battle, rowSum);
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

    private UUID getWinnerIdByColumns(Battle battle, int xCoordinate, int yCoordinate) {
        int[][] board = battle.getBoard();
        for (int columnNumber = xCoordinate; columnNumber < xCoordinate + battle.getWinningNumberInRow(); columnNumber++) {
            int columnSum = getColumnSum(board, yCoordinate, battle.getWinningNumberInRow(), columnNumber);
            UUID winnerId = getWinnerId(battle, columnSum);
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

    private UUID getWinnerId(Battle battle, int columnSum) {
        int xNumberValueSum = battle.getXNumberValue() * battle.getWinningNumberInRow();
        int oNumberValueSum = battle.getONumberValue() * battle.getWinningNumberInRow();

        return columnSum == xNumberValueSum
                ? battle.getXParticipantId()
                : columnSum == oNumberValueSum
                ? battle.getOParticipantId()
                : null;
    }
}
