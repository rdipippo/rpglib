package org.rpglib.controller;

import org.bson.types.ObjectId;
import org.deadsimple.mundungus.EntityManager;
import org.rpglib.domain.GameMessage;
import org.rpglib.persistence.Attack;
import org.rpglib.persistence.Attribute;
import org.rpglib.persistence.GameState;
import org.rpglib.persistence.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemController {
   private static Attack defaultAttack = new Attack(1, 6);

   public GameState equipOrUnequipItems(final ObjectId gsId, final List<ObjectId> itemIds, final boolean unequip) throws Exception {
	   final EntityManager em = RPGLib.entityManager();
	   GameState gs = em.find(GameState.class, gsId);

	   final List<String> errors = new ArrayList<String>();
	   
	   for (ObjectId itemId : itemIds) {
		   Item item = em.find(Item.class, itemId);

		   if (unequip && ! item.isEquipped()) {
		       errors.add(GameMessage.ITEM_NOT_EQUIPPED.format(item.getName()));
		   } else if (! unequip && item.isEquipped()) {
		       errors.add(GameMessage.ITEM_ALREADY_EQUIPPED.format(item.getName()));
		   } else if (! item.getPlayer().getId().equals(gs.getId())) {
		       errors.add(GameMessage.ITEM_IS_NOT_YOURS.format(item.getName()));
		   } else if (item.getEquipAttributes() == null && ! RPGLib.itemHasEquipIntelligence(item.getName())) {
		       errors.add(GameMessage.ITEM_NOT_EQUIPPABLE.format(item.getName()));
		   }

		   final List<Attribute> equipAttributes = item.getEquipAttributes();

		   if (equipAttributes != null) {
		       for (final Attribute attr: equipAttributes) {
				   gs.applyModifier(attr, unequip);
		       }
		   }

		   final Attack attack = item.getAttack();
		   if (attack != null) {
			   if (unequip) {
				   gs.setCurrentAttack(defaultAttack);
			   } else {
				   gs.setCurrentAttack(attack);
			   }

			   RPGLib.entityManager().persist(gs);
		   }

		   if (unequip) {
			   item.setEquipped(false);
			   RPGLib.unequip(gs, item);
		   } else {
			   item.setEquipped(true);
			   RPGLib.equip(gs, item);
		   }

		   RPGLib.entityManager().persist(item);
	   }

	   em.persist(gs);
	   
	   return gs;
   }
}