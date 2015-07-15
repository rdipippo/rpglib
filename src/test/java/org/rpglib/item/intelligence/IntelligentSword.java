package org.rpglib.item.intelligence;

import org.rpglib.intelligence.annotations.ItemListener;
import org.rpglib.intelligence.annotations.OnEquip;
import org.rpglib.persistence.Item;
import org.rpglib.persistence.GameState;

@ItemListener(itemName="Intelligent Sword")
public class IntelligentSword {
    @OnEquip
    public void equip(final GameState player, final Item item) {
        System.out.println(player.getName());
        System.out.println(item.getName());
    }
}
