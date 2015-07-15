package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.SubCollection;
import org.deadsimple.mundungus.annotations.Transient;
import org.rpglib.calc.Outcome;
import org.rpglib.persistence.GameState;

import java.util.List;

@Collection
public class Encounter implements Outcome {
    ObjectId opponentTemplate;

    int percentChance;

    ObjectId id;

    Combat combat;

    @SubCollection(Reward.class)
    List<Reward> rewards;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getOpponentTemplate() {
        return opponentTemplate;
    }

    public void setOpponentTemplate(ObjectId opponentTemplate) {
        this.opponentTemplate = opponentTemplate;
    }

    @Override
    public int getPercentChance() {
        return percentChance;
    }

    public void setPercentChance(int percentChance) {
        this.percentChance = percentChance;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    @Transient
    public boolean isComplete(GameState newGameState) {
        if (newGameState.getEncounter() == null) {
            return true;
        }

        return false;
    }

    public void collectRewards(GameState newGameState) {
        for (Reward reward : rewards) {
            reward.collectReward(newGameState);
        }

        if (newGameState.checkForLevelUp()) {
            newGameState.levelUp();
        }
    }
}
