package org.rpglib.persistence;

import org.rpglib.controller.RPGLib;
import org.rpglib.domain.GameMessage;

import java.util.List;

public class StoreEncounter extends Encounter {
    private List<PlayerCommand> playerCommands;

    @Override
    public boolean isComplete(GameState newGameState) {
        return false;
    }

    @Override
    public GameState runEncounter(GameState gs) {
        return gs;
    }

    @Override
    public GameState continueEncounter(GameState gs) {
        PlayerPurchaseCommand attemptedCommand = (PlayerPurchaseCommand)gs.getCommand();

        for (PlayerCommand c : getCommands()) {
            PlayerPurchaseCommand ppc = (PlayerPurchaseCommand)c;
            if (ppc.getDisplayText().equals(attemptedCommand.getDisplayText())) {
                final int moneyCost = ppc.getMoneyCost();

                if (moneyCost > gs.getMoney()) {
                    gs.addMessage(GameMessage.NOT_ENOUGH_MONEY.format(moneyCost, gs.getMoney()));
                } else {
                    gs.spendMoney(moneyCost);
                    Item template = RPGLib.entityManager().find(Item.class, ppc.getItemTemplateId());
                    Item newItem = new Item(template, gs);
                    RPGLib.entityManager().persist(newItem);
                }
            }
        }



        return gs;
    }

    @Override
    public List<PlayerCommand> getCommands() {
        return playerCommands;
    }

    public void setPlayerCommands(List<PlayerCommand> playerCommands) {
        this.playerCommands = playerCommands;
    }
}
