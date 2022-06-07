package org.astashonok.battleservice.components.calculators;

import org.astashonok.battleservice.entities.Battle;

import java.util.UUID;

public interface WinnerCalculator {

    UUID getWinnerId(Battle battle);
}
