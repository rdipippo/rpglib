package org.rpglib.persistence;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.annotations.Collection;
import org.rpglib.combat.Combatant;
import org.rpglib.controller.RPGLib;

@Collection
public class Opponent implements Combatant {
    int attack;
    int defense;
    int health;
    ObjectId id;
    String name;
    Attack currentAttack;
    ObjectId templateId;

    public Opponent() {

    }

    public Opponent(ObjectId opponentTemplate) {
        Opponent opponent = RPGLib.entityManager().find(Opponent.class, opponentTemplate);
        this.setAttack(opponent.getAttack());
        this.setCurrentAttack(opponent.getCurrentAttack());
        this.setDefense(opponent.getDefense());
        this.setName(opponent.getName());
        this.setHealth(opponent.getHealth());
        this.setTemplateId(opponentTemplate);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public Attack getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(Attack currentAttack) {
        this.currentAttack = currentAttack;
    }


    public ObjectId getTemplateId() {
        return templateId;
    }

    public void setTemplateId(ObjectId templateId) {
        this.templateId = templateId;
    }
}
