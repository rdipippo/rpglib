package org.rpglib.item.intelligence;

import org.rpglib.intelligence.annotations.OnEquip;
import org.rpglib.persistence.Item;
import org.rpglib.persistence.GameState;

public class IntelligentSword {
    @OnEquip(itemName="Intelligent Sword")
    public void equip(final GameState player, final Item item) {
        System.out.println(player.getName());
        System.out.println(item.getName());
    }
}
