package org.rpglib.controller;

import org.deadsimple.mundungus.EntityManager;
import org.rpglib.calc.Random;
import org.rpglib.combat.Combatant;
import org.rpglib.domain.GameMessage;
import org.rpglib.persistence.GameState;
import org.rpglib.persistence.Combat;
import org.rpglib.persistence.Opponent;


public class CombatController {
    public GameState combatRound(final GameState gs) {
        EntityManager em = RPGLib.entityManager();

        boolean playerWonInitiative = false;
        Combatant combatantWithInitiative = null, combatantWithoutInitiative = null;
        Combat combatInProgress = gs.getEncounter().getCombat();

        gs.setMessages(null);

        if (gs.getEncounter().getCombat().getCombatRound() == 0) {
            playerWonInitiative = determineInitiative();
            gs.getEncounter().getCombat().setPlayerHasInitiative(playerWonInitiative);
        } else {
            playerWonInitiative = combatInProgress.getPlayerHasInitiative();
        }

        combatInProgress.incrementCombatRound();

        Opponent opponent = RPGLib.entityManager().find(Opponent.class, combatInProgress.getOpponentId());

        if (playerWonInitiative) {
            combatantWithInitiative = gs;
            combatantWithoutInitiative = opponent;
        } else {
            combatantWithInitiative = opponent;
            combatantWithoutInitiative = gs;
        }

        boolean combatComplete = attack(gs, combatantWithInitiative, combatantWithoutInitiative);

        if (! combatComplete) {
            combatComplete = attack(gs, combatantWithoutInitiative, combatantWithInitiative);
        }

        if (combatComplete) {
            combatInProgress.setComplete(true);

            if (gs.getHealth() <= 0) {
                gs.addMessage(GameMessage.PLAYER_LOST_COMBAT.format());
                gs.getEncounter().getCombat().setPlayerWon(false);
            } else {
                gs.addMessage(GameMessage.PLAYER_WON_COMBAT.format());
                gs.getEncounter().getCombat().setPlayerWon(true);
            }
        }

        em.persist(gs);
        em.persist(opponent);

        return gs;
    }

    protected boolean attack(GameState gs, Combatant combatant1, Combatant combatant2) {
        int attackPercent = combatant1.getAttack();
        int defensePercent = combatant2.getDefense();
        String combatResultMessage = null;
        int hitPercent = attackPercent - defensePercent;
        boolean combatComplete = false;

        if (hitPercent <= 0) {
           combatResultMessage = GameMessage.MISSED.format(combatant1.getName());
           gs.addMessage(combatResultMessage);
        } else {
            Random rand = new Random();
            int hit = rand.nextInt(1, 100);

            if (hit <= hitPercent) {
                int minDamage = combatant1.getCurrentAttack().getMinDamage();
                int maxDamage = combatant1.getCurrentAttack().getMaxDamage();

                int damage = rand.nextInt(minDamage, maxDamage);
                combatant2.takeDamage(damage);

                combatResultMessage = GameMessage.HIT.format(combatant1.getName(), damage);
                gs.addMessage(combatResultMessage);
            } else {
                combatResultMessage = GameMessage.MISSED.format(combatant1.getName());
                gs.addMessage(combatResultMessage);
            }
        }

        if (combatant1.getHealth() <= 0 || combatant2.getHealth() <=0) {
            combatComplete = true;
        }

        return combatComplete;
    }

    protected boolean determineInitiative() {
        Random rand = new Random();
        return rand.nextBoolean();
    }
}
