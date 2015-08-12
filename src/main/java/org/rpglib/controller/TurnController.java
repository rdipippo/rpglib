package org.rpglib.controller;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.EntityCursor;
import org.rpglib.persistence.*;

import java.util.Iterator;


public class TurnController {
   public GameState takeTurn(ObjectId gsId, ObjectId areaId) throws NoSuchFieldException {
       AdventureArea area = RPGLib.entityManager().find(AdventureArea.class, areaId);
       GameState gs = RPGLib.entityManager().find(GameState.class, gsId);

       if (gs.getRemainingTurns() - area.getTurnCount() <= 0) {
           // TODO set error message.
       }

       gs.setRemainingTurns(gs.getRemainingTurns() - area.getTurnCount());

       // check for effects that have expired.
       if (gs.getEffects() != null && gs.getEffects().size() > 0) {
           Iterator<Effect> effectIter = gs.getEffects().iterator();
           while(effectIter.hasNext()) {
               Effect effect = effectIter.next();
               effect.decrementRoundCount();
               if (effect.getNumRounds() == 0) {
                   effect.cancel(gs);
                   effectIter.remove();
               }
           }
       }

       if (area.getEncounters() != null && area.getEncounters().size() > 0) {
           Encounter encounter = area.chooseEncounter(gs);
           gs.setEncounter(encounter);

           encounter.runEncounter(gs);
       }

       RPGLib.entityManager().persist(gs);

       return gs;
   }

    public GameState continueTurn(GameState gs) {
        if (! gs.getEncounter().isComplete(gs)) {
            gs = gs.getEncounter().continueEncounter(gs);
        } else {
            gs.setEncounter(null);
        }

        return gs;
    }
}
