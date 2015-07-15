package org.rpglib.combat;

import org.bson.types.ObjectId;
import org.rpglib.persistence.Attack;

public interface Combatant {
    public int getAttack();

    public void setAttack(final int attack);

    public int getDefense();

    public void setDefense(final int defense);

    public int getHealth();

    public void setHealth(final int health);

    ObjectId getId();

    String getName();

    void takeDamage(int damage);

    Attack getCurrentAttack();

    void setCurrentAttack(Attack attack);
}
