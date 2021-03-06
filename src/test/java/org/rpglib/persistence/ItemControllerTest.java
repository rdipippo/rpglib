package org.rpglib.persistence;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.rpglib.MyGameState;
import org.rpglib.controller.ItemController;

public class ItemControllerTest extends BaseTest {
    
    ItemController controller = new ItemController();
    
    static Item sword = null, girdle = null, intelligentSword = null;

    static ObjectId intelligentSwordId = null;

    @Before
    public void before() {

        sword = new Item();
        sword.setName("Sword of Strength");

        final Attribute attr = new Attribute("attack", "10%");

        sword.setEquipAttributes(Lists.newArrayList(attr));

        sword.setPlayer(getGameState());

        ObjectId swordId = em().persist(sword);
        sword.setId(swordId);

        girdle = new Item();
        girdle.setName("Girdle of fire giant strength");

        final Attribute attr2 = new Attribute("attack", "22");

        girdle.setEquipAttributes(Lists.newArrayList(attr2));

        girdle.setPlayer(getGameState());

        ObjectId girdleId = em().persist(girdle);
        girdle.setId(girdleId);

        intelligentSword = new Item();
        intelligentSword.setName("Intelligent Sword");
        intelligentSword.setPlayer(getGameState());

        Attack attack = new Attack(6, 12);
        intelligentSword.setAttack(attack);

        intelligentSwordId = em().persist(intelligentSword);
        intelligentSword.setId(intelligentSwordId);
    }

    @Test
    public void testEquipUneqip() throws Exception {
        GameState gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(girdle.getId(), sword.getId()), false);

        girdle = em().find(Item.class, girdle.getId());
        sword = em().find(Item.class, sword.getId());

        Assert.assertTrue(girdle.isEquipped());
        Assert.assertTrue(sword.isEquipped());

        gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(sword.getId(), girdle.getId()), true);

        girdle = em().find(Item.class, girdle.getId());
        sword = em().find(Item.class, sword.getId());

        Assert.assertFalse(girdle.isEquipped());
        Assert.assertFalse(sword.isEquipped());
    }

    @Test
    public void testEquipItemStrengthBonus() throws Exception {
        GameState gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(girdle.getId()), false);
        Assert.assertEquals(72, gs.getAttack());

        gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(girdle.getId()), true);
        Assert.assertEquals(50, gs.getAttack());
    }

    @Test
    public void testEquipItemStrengthPercentBonus() throws Exception {
        GameState gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(sword.getId()), false);
        Assert.assertEquals(55, gs.getAttack());

        gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(sword.getId()), true);
        Assert.assertEquals(50, gs.getAttack());
    }

    @Test
    public void testEquipItemStrengthPercentAndIntegerBonusDifferentItems() throws Exception {
        GameState gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(girdle.getId(), sword.getId()), false);
        Assert.assertEquals(77, gs.getAttack());

        gs = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(sword.getId(), girdle.getId()), true);
        Assert.assertEquals(50, gs.getAttack());
    }
    
    @Test
    public void testOnEquipHandler() throws Exception {
        this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(intelligentSword.getId()), false);
    }

    @Test
    public void testAttack() throws Exception {
        GameState gameState = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(intelligentSword.getId()), false);
        Assert.assertEquals(12, gameState.getCurrentAttack().getMaxDamage());
        Assert.assertEquals(6, gameState.getCurrentAttack().getMinDamage());

        gameState = this.controller.equipOrUnequipItems(getGameState().getId(), Lists.newArrayList(intelligentSword.getId()), true);
        Assert.assertEquals(6, gameState.getCurrentAttack().getMaxDamage());
        Assert.assertEquals(1, gameState.getCurrentAttack().getMinDamage());
    }

    @Test
    public void testCustomGameStateField() throws Exception {
        final MyGameState gameState = new MyGameState();
        em().persist(gameState);
        this.controller.equipOrUnequipItems(gameState.getId(), Lists.newArrayList(intelligentSword.getId()), true);
    }
}