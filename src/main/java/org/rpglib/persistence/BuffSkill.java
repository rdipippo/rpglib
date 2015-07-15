package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.controller.RPGLib;

import java.util.HashMap;

@Collection(name="Skill")
public class BuffSkill extends Skill {
    ObjectId id;

    Effect effect;

    @Override
    public GameState useSkill(ObjectId gameStateId, HashMap<String, Object> parameters) throws NoSuchFieldException{
        GameState gs = RPGLib.entityManager().find(GameState.class, gameStateId);
        gs.addEffect(effect);
        RPGLib.entityManager().persist(gs);
        return gs;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
