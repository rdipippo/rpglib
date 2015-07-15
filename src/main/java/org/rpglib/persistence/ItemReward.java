package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.rpglib.calc.Random;
import org.rpglib.controller.RPGLib;
import org.rpglib.domain.GameMessage;

public class ItemReward implements Reward {
    int minValue;

    int maxValue;

    ObjectId itemTemplateId;

    public ItemReward(int min, int max, ObjectId itemTemplateId) {
        this.maxValue = max;
        this.minValue = min;
        this.itemTemplateId = itemTemplateId;
    }

    public ItemReward() {

    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public ObjectId getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(ObjectId itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void collectReward(GameState gs) {
        int rewardValue = Random.nextInt(minValue, maxValue);

        Item templateItem = RPGLib.entityManager().find(Item.class, itemTemplateId);
        Item droppedItem = new Item(templateItem, gs);
        RPGLib.entityManager().persist(droppedItem);
        gs.addMessage(GameMessage.PLAYER_GOT_ITEM.format(droppedItem.getName()));
    }

    public int getPercentChance() {
        return 100;
    }
}
