package org.rpglib.persistence;

import org.junit.Before;
import org.junit.Test;
import org.rpglib.controller.CombatController;

public class CombatControllerTest extends BaseTest {
    Opponent opponent;

    @Before
    public void before() {
        Attack attack = new Attack(1, 6);

        opponent = new Opponent();
        opponent.setAttack(25);
        opponent.setDefense(25);
        opponent.setHealth(10);
        opponent.setName("Orc");

        opponent.setCurrentAttack(attack);

        em().persist(opponent);

        Combat combat = new Combat(getGameState(), opponent);
        CombatEncounter encounter = new CombatEncounter();
        encounter.setPercentChance(100);
        encounter.setCombat(combat);
        getGameState().setEncounter(encounter);

        em().persist(getGameState());
    }

    @Test
    public void testCombat() {
        CombatController controller = new CombatController();
        GameState gs = null;

        do {
            gs = controller.combatRound(getGameState().getId());

            for (String msg : gs.getMessages()) {
                System.out.println(msg);
            }

            opponent = em().find(Opponent.class, opponent.getId());
            System.out.println("Player: " + gs.getHealth() + " remaining health.");
            System.out.println("Opponent: " + opponent.getHealth() + " remaining health.");
        } while(gs.getEncounter().isComplete(gs) == false);
    }
}
