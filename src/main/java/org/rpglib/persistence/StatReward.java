package org.rpglib.persistence;

import org.deadsimple.mundungus.annotations.Transient;
import org.rpglib.calc.Random;
import org.rpglib.controller.RPGLib;

import org.rpglib.domain.GameMessage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class StatReward implements Reward {
    int minValue;

    int maxValue;

    String fieldName;

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public StatReward(int min, int max, String fieldName) {
        this.maxValue = max;
        this.minValue = min;
        this.fieldName = fieldName;
    }

    public StatReward() {

    }

    public void collectReward(GameState gs) {

        int rewardValue = Random.nextInt(minValue, maxValue);

        // TODO need better error handling!
        try {
            int currentValue = RPGLib.getNumericGameStateField(gs, fieldName);
            RPGLib.setNumericGameStateField(gs, fieldName, currentValue + rewardValue);
            RPGLib.entityManager().persist(gs);
            gs.addMessage(GameMessage.PLAYER_GOT_STAT_REWARD.format(rewardValue, fieldName));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Transient
    public int getPercentChance() {
        return 100;
    }
}
