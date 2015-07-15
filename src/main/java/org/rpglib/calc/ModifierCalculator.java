package org.rpglib.calc;

import org.rpglib.controller.RPGLib;
import org.rpglib.persistence.GameState;

import java.lang.reflect.InvocationTargetException;

public class ModifierCalculator {
    int baseValue = 0;

    long totalModifier = 0;

    long totalPercentBonus = 0;

    boolean isPenalty;

    GameState obj;

    String fieldName;

    public ModifierCalculator(final GameState obj, String fieldName, final String modifier, final boolean isPenalty) {
        this.obj = obj;
        this.fieldName = fieldName;
        this.isPenalty = isPenalty;

        // get the base value for the fieldname being modified.
        // getter for the base value starts with 'getBase' not 'get'.
        final StringBuilder sb = new StringBuilder("base" + fieldName);
        sb.setCharAt(4, Character.toUpperCase(sb.charAt(4)));

        try {
            this.baseValue = RPGLib.getNumericGameStateField(obj, sb.toString());
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (final SecurityException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (modifier.endsWith("%")) {
            final Integer percentModifier = Integer.valueOf(modifier.substring(0, modifier.length() - 1));
            this.totalPercentBonus = percentModifier;
        } else {
            final Integer intModifier = Integer.valueOf(modifier);
            this.totalModifier = intModifier;
        }
    }

    public void apply() {
        int calculatedValue;
        try {
            calculatedValue = RPGLib.getNumericGameStateField(obj, fieldName);

            if (this.isPenalty) {
                this.totalModifier *= -1;
            }
            calculatedValue += this.totalModifier;

            if (this.totalPercentBonus > 0) {
               /*
                  If we are calculating a penalty (usually because we're unequipping an item),
                  use the ceiling function. Otherwise (when equipping then unequipping an item with 10% bonus):
                  25 + floor(25 * 0.1) = 25 + 2 = 27
                  27 + floor(-(25 * 0.1)) = 27 + -3 = 24
                  floor goes the opposite direction with negative numbers.
                */
                if (this.isPenalty) {
                    this.totalPercentBonus *= -1;
                    calculatedValue += Math.ceil((this.totalPercentBonus / 100D) * this.baseValue);
                } else {
                    calculatedValue += Math.floor((this.totalPercentBonus / 100D) * this.baseValue);
                }
            }

            RPGLib.setNumericGameStateField(this.obj, fieldName, calculatedValue);
        } catch (final IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
