package org.rpglib.persistence;

import com.google.common.collect.Lists;
import org.deadsimple.mundungus.EntityCursor;
import org.junit.Assert;
import org.junit.Test;
import org.rpglib.controller.RPGLib;
import org.rpglib.domain.GameMessage;

public class StoreTest extends BaseTest {
    @Test
    public void testPurchase() {
        StoreEncounter se = new StoreEncounter();

        PlayerPurchaseCommand pcc = new PlayerPurchaseCommand();
        pcc.setDisplayText("Long sword");
        pcc.setMoneyCost(100);
        pcc.setItemTemplateId(getItemTemplate());

        se.setPlayerCommands(Lists.newArrayList((PlayerCommand) pcc));

        GameState gs = getGameState();
        gs.setCommand(pcc);

        gs = se.continueEncounter(gs);

        Assert.assertEquals(0, gs.getMoney());
        Assert.assertNull(gs.getMessages());

        Item search = new Item();
        search.setTemplateId(getItemTemplate());
        final EntityCursor<Item> itemEntityCursor = RPGLib.entityManager().get(search);
        Item newItem = itemEntityCursor.nextEntity();

        Assert.assertEquals("Widget", newItem.getName());
        Assert.assertEquals(gs.getId(), newItem.getPlayer().getId());
    }

    @Test
    public void testPurchaseNotEnoughMoolah() {
        StoreEncounter se = new StoreEncounter();

        PlayerPurchaseCommand pcc = new PlayerPurchaseCommand();
        pcc.setDisplayText("Long sword");
        pcc.setMoneyCost(200);
        pcc.setItemTemplateId(getItemTemplate());

        se.setPlayerCommands(Lists.newArrayList((PlayerCommand) pcc));

        GameState gs = getGameState();
        gs.setCommand(pcc);

        gs = se.continueEncounter(gs);

        Assert.assertEquals(100, gs.getMoney());
        Assert.assertNotNull(gs.getMessages());

        final String message = gs.getMessages().iterator().next();
        Assert.assertEquals(GameMessage.NOT_ENOUGH_MONEY.format(200, 100), message);
    }
}
