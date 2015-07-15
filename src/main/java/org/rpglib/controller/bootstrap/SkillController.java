package org.rpglib.controller.bootstrap;

import org.bson.types.ObjectId;
import org.rpglib.controller.RPGLib;
import org.rpglib.persistence.GameState;
import org.rpglib.persistence.Skill;

import java.util.HashMap;

public class SkillController {
    public GameState useSkill(ObjectId skillId, ObjectId gameStateId, HashMap<String, Object> parameters) {
        GameState gs = RPGLib.entityManager().find(GameState.class, gameStateId);
        Skill skill  = RPGLib.entityManager().find(Skill.class, skillId);

        try {
            gs = skill.useSkill(gameStateId, parameters);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            gs.addMessage("Error: no such skill.");
        }

        return gs;
    }
}
