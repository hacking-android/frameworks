/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

public interface WatchEvent<T> {
    public T context();

    public int count();

    public Kind<T> kind();

    public static interface Kind<T> {
        public String name();

        public Class<T> type();
    }

    public static interface Modifier {
        public String name();
    }

}

