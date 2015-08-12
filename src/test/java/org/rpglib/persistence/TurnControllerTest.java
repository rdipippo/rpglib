package org.rpglib.persistence;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.rpglib.controller.TurnController;
import org.rpglib.persistence.GameState;

public class TurnControllerTest extends BaseTest {
    @Test
    public void testStatGainEncounter() throws Exception {
        AdventureArea areaParam = getAdventureAreaWithStatGainEncounter();

        TurnController controller = new TurnController();

        GameState newGameState = controller.takeTurn(getGameState().getId(), areaParam.getId());
        newGameState = controller.continueTurn(newGameState);

        if (newGameState.getMessages() != null) {
            for (String msg : newGameState.getMessages()) {
                System.out.println(msg);
            }
        }
    }

    @Test
    public void testFleeCombat() throws Exception {
        AdventureArea areaParam = getAdventureArea(true);

        TurnController controller = new TurnController();
        GameState currentGameState = em().find(GameState.class, getGameState().getId());
        currentGameState.setCommand(new PlayerCommand("Flee"));
        em().persist(currentGameState);

        GameState newGameState = controller.takeTurn(getGameState().getId(), areaParam.getId());
        newGameState = controller.continueTurn(newGameState);

        if (newGameState.getMessages() != null) {
            for (String msg : newGameState.getMessages()) {
                System.out.println(msg);
            }
        }
    }

    @Test
    public void takeTurn() throws Exception {
        AdventureArea areaParam = getAdventureArea(true);

        TurnController controller = new TurnController();
        GameState currentGameState = em().find(GameState.class, getGameState().getId());
        currentGameState.setCommand(new PlayerCommand("Continue Attack"));
        em().persist(currentGameState);

        GameState newGameState = controller.takeTurn(getGameState().getId(), areaParam.getId());

        if (newGameState.getMessages() != null) {
            for (String msg : newGameState.getMessages()) {
                System.out.println(msg);
            }
        }

        while(newGameState != null && newGameState.getEncounter() != null) {
            newGameState = controller.continueTurn(newGameState);

            if (newGameState != null && newGameState.getMessages() != null) {
                for (String msg : newGameState.getMessages()) {
                    System.out.println(msg);
                }
            }
        }
    }
}
