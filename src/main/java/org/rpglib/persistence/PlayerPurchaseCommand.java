package org.rpglib.persistence;

import org.bson.types.ObjectId;

public class PlayerPurchaseCommand extends PlayerCommand {
    int moneyCost;

    ObjectId itemTemplateId;

    public int getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(int moneyCost) {
        this.moneyCost = moneyCost;
    }

    public ObjectId getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(ObjectId itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }
}
