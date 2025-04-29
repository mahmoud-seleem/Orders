package com.example.orders.shared.utils;

import org.springframework.stereotype.Component;
import java.lang.reflect.Field;

@Component
public class Validation {



    public void isNotNull(Object value, String name) {
        if (value == null) {
            throw new CustomValidationException("field can't be null !!", name, null);
        }
    }

    public void ensureFieldIsNull(Class clazz, Object object, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(clazz, name);
        field.setAccessible(true);
        if (field.get(object) != null) {
            field.set(object, null);
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Search in parent class
            }
        }
        return null; // Field not found
    }

}
