package org.rpglib.controller;

import com.google.common.base.Predicates;
import org.apache.commons.beanutils.BeanUtils;
import org.deadsimple.mundungus.EntityManager;
import org.reflections.ReflectionUtils;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.rpglib.intelligence.annotations.ItemListener;
import org.rpglib.intelligence.annotations.OnEquip;
import org.rpglib.intelligence.annotations.OnUnEquip;
import org.rpglib.persistence.Item;
import org.rpglib.persistence.GameState;
import org.rpglib.persistence.PlayerAttribute;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class RPGLib {
    static RPGLibReflections reflections = null;

    static EntityManager em;

    public static void init() {
        reflections = new RPGLibReflections("org.rpglib", new MethodAnnotationsScanner(), new TypeAnnotationsScanner());
        em = new EntityManager();
    }

    public static Integer getNumericGameStateField(GameState gs, String fieldName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return Integer.valueOf(BeanUtils.getProperty(gs, fieldName));
    }

    public static void setNumericGameStateField(GameState gs, String fieldName, Integer value) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.setProperty(gs, fieldName, value);
    }

    public static EntityManager entityManager() {
        return em;
    }

    static boolean itemHasEquipIntelligence(final String itemName) {
        final Set<Method> itemListenerMethodsAnnotatedWith = reflections.getAnnotatedMethodsOnAnnotatedClass(ItemListener.class, OnEquip.class, itemName);
        return (itemListenerMethodsAnnotatedWith != null && itemListenerMethodsAnnotatedWith.size() > 0);
    }

    static void equip(final GameState gameState, final Item item) {
        final Set<Method> methodsAnnotatedWith = reflections.getAnnotatedMethodsOnAnnotatedClass(ItemListener.class, OnEquip.class, item.getName());

        if (methodsAnnotatedWith != null && methodsAnnotatedWith.size() > 0) {
            final Method equipMethod = methodsAnnotatedWith.iterator().next();
            if (equipMethod != null) {
                try {
                    final Object newInstance = equipMethod.getDeclaringClass().newInstance();
                    equipMethod.invoke(newInstance, gameState, item);
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (final InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (final InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void unequip(final GameState gs, final Item item) {
        final Set<Method> methodsAnnotatedWith = reflections.getAnnotatedMethodsOnAnnotatedClass(ItemListener.class, OnUnEquip.class, item.getName());

        if (methodsAnnotatedWith != null && methodsAnnotatedWith.size() > 0) {
            final Method equipMethod = methodsAnnotatedWith.iterator().next();
            if (equipMethod != null) {
                try {
                    final Object newInstance = equipMethod.getDeclaringClass().newInstance();
                    equipMethod.invoke(newInstance, gs, item);
                } catch (final IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (final InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (final InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
