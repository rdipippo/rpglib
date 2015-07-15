package org.rpglib.persistence;

import org.rpglib.calc.Outcome;

public interface Reward extends Outcome {
    public void collectReward(GameState gs);
}
