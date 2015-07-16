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
           ObjectId opponentTemplate = encounter.getOpponentTemplate();

           if (opponentTemplate != null) {
               Opponent opponent = new Opponent(opponentTemplate);
               RPGLib.entityManager().persist(opponent);

               Combat combat = new Combat(gs, opponent);
               encounter.setCombat(combat);
               gs.setEncounter(encounter);

               RPGLib.entityManager().persist(gs);

               CombatController combatController = new CombatController();
               gs = combatController.combatRound(gs.getId());
           }

           if (gs.getEncounter().getCombat().isPlayerWon()) {
               encounter.collectRewards(gs);
           }
       }

       RPGLib.entityManager().persist(gs);

       return gs;
   }

    public GameState continueTurn(GameState gs) {
        GameState newGameState = null;

        if (! gs.getEncounter().getCombat().isComplete()) {
            Combat combat = gs.getEncounter().getCombat();

            CombatController combatController = new CombatController();
            newGameState = combatController.combatRound(gs.getId());

            if (newGameState.getEncounter().getCombat().isPlayerWon()) {
                newGameState.getEncounter().collectRewards(newGameState);
            }
        }

        return newGameState;
    }
}
