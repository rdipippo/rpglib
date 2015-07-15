package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.combat.Combatant;

@Collection
public class Combat {

    ObjectId opponentId;

    int combatRound;

    boolean complete = false;

    boolean playerHasInitiative;

    boolean playerWon = false;

    public Combat() {

    }

    public Combat(GameState gs, Combatant opponent, boolean playerWonInitiative) {
        this.opponentId = opponent.getId();
        this.playerHasInitiative = playerWonInitiative;
        this.combatRound = 0;
    }

    public Combat(GameState gs, Opponent opponent) {
        this.opponentId = opponent.getId();
        this.combatRound = 0;
    }

    public ObjectId getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(ObjectId opponentId) {
        this.opponentId = opponentId;
    }

    public int getCombatRound() {
        return combatRound;
    }

    public boolean getPlayerHasInitiative() {
        return playerHasInitiative;
    }

    public void setPlayerHasInitiative(boolean playerHasInitiative) {
        this.playerHasInitiative = playerHasInitiative;
    }

    public void incrementCombatRound() {
        combatRound++;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }

}
