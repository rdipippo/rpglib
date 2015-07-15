package org.rpglib.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import org.deadsimple.mundungus.EntityManager;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.rpglib.intelligence.annotations.CombatListener;
import org.rpglib.intelligence.annotations.ItemListener;
import org.rpglib.intelligence.annotations.PlayerAttributeField;
import org.rpglib.persistence.GameState;
import org.rpglib.persistence.PlayerAttribute;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

public class RPGLibReflections extends Reflections {
    public RPGLibReflections(final String string, final MethodAnnotationsScanner methodAnnotationsScanner, final TypeAnnotationsScanner typeAnnotationsScanner) {
        super(string, methodAnnotationsScanner, typeAnnotationsScanner);
    }

    /**
     * Return all methods annotated with [annotation] on classes annotated with an ItemListener
     * for the given item name.
     *
     * @param annotation
     * @param methodAnnotationParam
     * @return
     */
    public Set<Method> getAnnotatedMethodsOnAnnotatedClass(final Class<? extends Annotation> classAnnotation, final Class<? extends Annotation> methodAnnotation, final String methodAnnotationParam) {
        Annotation il = null;

        if (classAnnotation.getName().equals(ItemListener.class.getName())) {
            il = new ItemListener() {

                public Class<? extends Annotation> annotationType() {
                    return ItemListener.class;
                }

                public String itemName() {
                    return methodAnnotationParam;
                }

            };
        } else if (classAnnotation.getName().equals(CombatListener.class.getName())) {
            il = new CombatListener() {

                public Class<? extends Annotation> annotationType() {
                    return CombatListener.class;
                }

                public String opponentName() {
                    return methodAnnotationParam;
                }

            };
        }

        final Set<Method> methodsAnnotatedWith = this.getMethodsAnnotatedWith(methodAnnotation);
        final Set<Class<?>> typesAnnotatedWith = super.getTypesAnnotatedWith(il);
        
        if (typesAnnotatedWith.size() > 0) {
            final Class<?> clazz = typesAnnotatedWith.iterator().next();
            
            final Set<Method> filter = Sets.filter(methodsAnnotatedWith, new Predicate<Method>() {
                
                public boolean apply(final Method input) {
                    if (input.getDeclaringClass().getName().equals(clazz.getName())) {
                        return true;
                    } else {
                        return false;
                    }
                }
                
            });
            
            return filter;
        }
        
        return null;
    }
}
