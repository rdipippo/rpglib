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
        Encounter encounter = new Encounter();
        encounter.setCombat(combat);
        getGameState().setEncounter(encounter);

        em().persist(getGameState());
    }

    @Test
    public void testCombat() {
        CombatController controller = new CombatController();

        do {
            GameState gs = controller.combatRound(getGameState());

            for (String msg : gs.getMessages()) {
                System.out.println(msg);
            }

            System.out.println("Player: " + getGameState().getHealth() + " remaining health.");
            System.out.println("Opponent: " + opponent.getHealth() + " remaining health.");
        } while(getGameState().getEncounter().getCombat().isComplete() == false);
    }
}
