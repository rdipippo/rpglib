package org.rpglib.persistence;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.deadsimple.mundungus.EmbeddedMongo;
import org.deadsimple.mundungus.EntityManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rpglib.controller.RPGLib;

public class BaseTest {
    private EntityManager em = new EntityManager();

    private GameState gs = new GameState();

    private ObjectId itemTemplateId = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        EmbeddedMongo.start();
        RPGLib.init();
    }

    @AfterClass
    public static void afterClass() {
        EmbeddedMongo.stop();
    }

    @Before
    public void baseBefore() {
        em().getCollection(Item.class).remove(new BasicDBObject());
        em().getCollection(GameState.class).remove(new BasicDBObject());

        Attack attack = new Attack(1, 6);

        gs = new GameState();
        gs.setName("Gildor");
        gs.setBaseAttack(50);
        gs.setAttack(50);
        gs.setHealth(10);
        gs.setCurrentAttack(attack);
        gs.setLevel(1);

        final ObjectId playerId = em().persist(gs);
        gs.setId(playerId);

        Item itemTemplate = new Item();
        itemTemplate.setName("Widget");
        itemTemplateId = em().persist(itemTemplate);

        em().persist(gs);
    }

    public EntityManager em() {
        return em;
    }

    public GameState getGameState() {
        return gs;
    }

    public ObjectId getItemTemplate() { return itemTemplateId; }

    protected AdventureArea getAdventureArea(boolean includeOpponent) {
        AdventureArea area = new AdventureArea();

        if (includeOpponent) {
            Opponent opponent = new Opponent();
            opponent.setAttack(25);
            opponent.setDefense(25);
            opponent.setHealth(2);
            opponent.setName("Orc");

            Attack attack = new Attack(1, 6);
            opponent.setCurrentAttack(attack);

            em().persist(opponent);

            Encounter encounter = new Encounter();
            encounter.setPercentChance(100);
            encounter.setOpponentTemplate(opponent.getId());

            StatReward attackReward = new StatReward(1, 6, "attack");
            ItemReward widgetReward = new ItemReward(1, 1, getItemTemplate());
            StatReward xpReward = new StatReward(101, 101, "experiencePoints");
            encounter.setRewards(Lists.newArrayList((Reward) attackReward, (Reward) widgetReward, (Reward) xpReward));

            em().persist(encounter);

            area.setEncounters(Lists.newArrayList(encounter));
        }

        em().persist(area);

        AdventureArea areaParam = new AdventureArea();
        areaParam.setId(area.getId());
        return areaParam;
    }

}
