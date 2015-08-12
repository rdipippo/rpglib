package org.rpglib.persistence;

import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.Transient;

import java.util.List;

@Collection(name="Encounter")
public class StatGainEncounter extends Encounter {
    @Override
    @Transient
    public boolean isComplete(GameState newGameState) {
        return true;
    }

    @Override
    public GameState runEncounter(GameState gs) {
        collectRewards(gs);
        return gs;
    }

    @Override
    public GameState continueEncounter(GameState gs) {
        return null; // this should never be called.
    }

    @Override
    public List<PlayerCommand> getCommands() {
        return null; // this should never be called.
    }
}
