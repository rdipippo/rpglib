package org.rpglib.controller;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.EntityCursor;
import org.rpglib.persistence.*;

import java.util.Iterator;


public class TurnController {
   public GameState takeTurn(GameState gs, AdventureArea area) throws NoSuchFieldException {
       EntityCursor<AdventureArea> cursor = RPGLib.entityManager().get(area);
       AdventureArea storedArea = cursor.nextEntity();
       GameState newGameState = gs;

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

       if (storedArea.getEncounters() != null && storedArea.getEncounters().size() > 0) {
           Encounter encounter = storedArea.chooseEncounter(gs);
           ObjectId opponentTemplate = encounter.getOpponentTemplate();

           if (opponentTemplate != null) {
               Opponent opponent = new Opponent(opponentTemplate);
               RPGLib.entityManager().persist(opponent);

               Combat combat = new Combat(gs, opponent);
               encounter.setCombat(combat);
               gs.setEncounter(encounter);

               CombatController combatController = new CombatController();
               newGameState = combatController.combatRound(gs);
           }

           if (newGameState.getEncounter().getCombat().isPlayerWon()) {
               encounter.collectRewards(newGameState);
           }
       }

       RPGLib.entityManager().persist(gs);

       return newGameState;
   }

    public GameState continueTurn(GameState gs) {
        GameState newGameState = null;

        if (! gs.getEncounter().getCombat().isComplete()) {
            Combat combat = gs.getEncounter().getCombat();

            CombatController combatController = new CombatController();
            newGameState = combatController.combatRound(gs);

            if (newGameState.getEncounter().getCombat().isPlayerWon()) {
                newGameState.getEncounter().collectRewards(newGameState);
            }
        }

        return newGameState;
    }
}
