package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.calc.Random;
import org.rpglib.controller.RPGLib;

import java.util.HashMap;

@Collection(name="Skill")
public class CombatSkill extends Skill {
    int minDamage;

    int maxDamage;

    @Override
    public GameState useSkill(ObjectId gameStateId, HashMap<String, Object> parameters) throws NoSuchFieldException {
        GameState gs = RPGLib.entityManager().find(GameState.class, gameStateId);

        Random rand = new Random();
        int damage = rand.nextInt(getMinDamage(), getMaxDamage());

        ObjectId opponentId = gs.getEncounter().getCombat().getOpponentId();
        Opponent opponent = RPGLib.entityManager().find(Opponent.class, opponentId);
        opponent.takeDamage(damage);

        RPGLib.entityManager().persist(opponent);
        RPGLib.entityManager().persist(gs);

        return gs;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }
}
