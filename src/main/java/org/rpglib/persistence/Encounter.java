package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.deadsimple.mundungus.annotations.SubCollection;
import org.deadsimple.mundungus.annotations.Transient;
import org.rpglib.calc.Outcome;
import org.rpglib.persistence.GameState;

import java.util.List;

@Collection
public abstract class Encounter implements Outcome {

    int percentChance;

    ObjectId id;

    String introductionText;

    @SubCollection(Reward.class)
    List<Reward> rewards;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getIntroductionText() {
        return introductionText;
    }

    public void setIntroductionText(String introText) {
        this.introductionText = introText;
    }

    @Transient
    public abstract boolean isComplete(GameState newGameState);

    public void collectRewards(GameState newGameState) {
        for (Reward reward : rewards) {
            reward.collectReward(newGameState);
        }

        if (newGameState.checkForLevelUp()) {
            newGameState.levelUp();
        }
    }

    public abstract GameState runEncounter(GameState gs);

    public abstract GameState continueEncounter(GameState gs);

    @Transient
    public abstract List<PlayerCommand> getCommands();
}
