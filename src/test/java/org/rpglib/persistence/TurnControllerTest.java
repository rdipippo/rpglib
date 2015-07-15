package org.rpglib.persistence;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.rpglib.controller.TurnController;
import org.rpglib.persistence.GameState;

public class TurnControllerTest extends BaseTest {
    @Test
    public void takeTurn() throws Exception {
        AdventureArea areaParam = getAdventureArea(true);

        TurnController controller = new TurnController();
        GameState newGameState = controller.takeTurn(getGameState(), areaParam);

        for (String msg : newGameState.getMessages()) {
            System.out.println(msg);
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
