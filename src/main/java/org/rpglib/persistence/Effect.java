package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.calc.ModifierCalculator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Collection
public class Effect {
    ObjectId id;

    String name;

    String description;

    int numRounds;

    String fieldName;

    String modifier;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public GameState apply(GameState gs) throws NoSuchFieldException {
        new ModifierCalculator(gs, fieldName, modifier, false).apply();

        List<Effect> effects = gs.getEffects();
        if (effects == null) {
            effects = new ArrayList<Effect>();
            effects.add(this);
        }

        return gs;
    }

    public GameState cancel(GameState gs) throws NoSuchFieldException {
        new ModifierCalculator(gs, fieldName, modifier, true).apply();
        return gs;
    }

    public void decrementRoundCount() {
        numRounds--;
    }
}
