/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf.nano;

import java.util.HashMap;
import java.util.Map;

public final class MapFactories {
    private static volatile MapFactory mapFactory = new DefaultMapFactory();

    private MapFactories() {
    }

    public static MapFactory getMapFactory() {
        return mapFactory;
    }

    static void setMapFactory(MapFactory mapFactory) {
        MapFactories.mapFactory = mapFactory;
    }

    private static class DefaultMapFactory
    implements MapFactory {
        private DefaultMapFactory() {
        }

        @Override
        public <K, V> Map<K, V> forMap(Map<K, V> map) {
            if (map == null) {
                return new HashMap();
            }
            return map;
        }
    }

    public static interface MapFactory {
        public <K, V> Map<K, V> forMap(Map<K, V> var1);
    }

}

