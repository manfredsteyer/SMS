package com.example.ticket.utils;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;

public class JpaHelper {
    public static <T> Set<T> unwrap(Set<T> set) {
        if (!Hibernate.isInitialized(set)) {
            return new HashSet<T>();
        }
        return set;
    }

    public static <T> T unwrap(T object) {
        if (!Hibernate.isInitialized(object)) {
            return null;
        }
        return object;
    }
}
