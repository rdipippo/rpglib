package org.rpglib.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.deadsimple.mundungus.EntityManager;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.rpglib.intelligence.annotations.OnEquip;
import org.rpglib.intelligence.annotations.OnUnEquip;
import org.rpglib.persistence.Item;
import org.rpglib.persistence.GameState;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class RPGLib {
    static Reflections reflections = null;

    static EntityManager em;

    public static void init() {
        reflections = new Reflections("org.rpglib", new MethodAnnotationsScanner(), new TypeAnnotationsScanner());
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
        final Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(new OnEquip() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return OnEquip.class;
            }

            @Override
            public String itemName() {
                return itemName;
            }
        });

        return (methodsAnnotatedWith != null && methodsAnnotatedWith.size() > 0);
    }

    static void equip(final GameState gameState, final Item item) {
        final Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(new OnEquip() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return OnEquip.class;
            }

            @Override
            public String itemName() {
                return item.getName();
            }
        });

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
        final Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(new OnUnEquip() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return OnUnEquip.class;
            }

            @Override
            public String itemName() {
                return item.getName();
            }
        });

        if (methodsAnnotatedWith != null && methodsAnnotatedWith.size() > 0) {
            final Method unequipMethod = methodsAnnotatedWith.iterator().next();
            if (unequipMethod != null) {
                try {
                    final Object newInstance = unequipMethod.getDeclaringClass().newInstance();
                    unequipMethod.invoke(newInstance, gs, item);
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
