/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.cs;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class ThreadLocalCoders {
    private static final int CACHE_SIZE = 3;
    private static Cache decoderCache = new Cache(3){
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override
        Object create(Object object) {
            if (object instanceof String) {
                return Charset.forName((String)object).newDecoder();
            }
            if (object instanceof Charset) {
                return ((Charset)object).newDecoder();
            }
            return null;
        }

        @Override
        boolean hasName(Object object, Object object2) {
            if (object2 instanceof String) {
                return ((CharsetDecoder)object).charset().name().equals(object2);
            }
            if (object2 instanceof Charset) {
                return ((CharsetDecoder)object).charset().equals(object2);
            }
            return false;
        }
    };
    private static Cache encoderCache = new Cache(3){
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override
        Object create(Object object) {
            if (object instanceof String) {
                return Charset.forName((String)object).newEncoder();
            }
            if (object instanceof Charset) {
                return ((Charset)object).newEncoder();
            }
            return null;
        }

        @Override
        boolean hasName(Object object, Object object2) {
            if (object2 instanceof String) {
                return ((CharsetEncoder)object).charset().name().equals(object2);
            }
            if (object2 instanceof Charset) {
                return ((CharsetEncoder)object).charset().equals(object2);
            }
            return false;
        }
    };

    public static CharsetDecoder decoderFor(Object object) {
        object = (CharsetDecoder)decoderCache.forName(object);
        ((CharsetDecoder)object).reset();
        return object;
    }

    public static CharsetEncoder encoderFor(Object object) {
        object = (CharsetEncoder)encoderCache.forName(object);
        ((CharsetEncoder)object).reset();
        return object;
    }

    private static abstract class Cache {
        private ThreadLocal<Object[]> cache = new ThreadLocal();
        private final int size;

        Cache(int n) {
            this.size = n;
        }

        private void moveToFront(Object[] arrobject, int n) {
            Object object = arrobject[n];
            while (n > 0) {
                arrobject[n] = arrobject[n - 1];
                --n;
            }
            arrobject[0] = object;
        }

        abstract Object create(Object var1);

        Object forName(Object object) {
            Object object2;
            Object[] arrobject = this.cache.get();
            if (arrobject == null) {
                object2 = new Object[this.size];
                this.cache.set((Object[])object2);
            } else {
                int n = 0;
                do {
                    object2 = arrobject;
                    if (n >= arrobject.length) break;
                    object2 = arrobject[n];
                    if (object2 != null && this.hasName(object2, object)) {
                        if (n > 0) {
                            this.moveToFront(arrobject, n);
                        }
                        return object2;
                    }
                    ++n;
                } while (true);
            }
            object2[((Object[])object2).length - 1] = object = this.create(object);
            this.moveToFront((Object[])object2, ((Object[])object2).length - 1);
            return object;
        }

        abstract boolean hasName(Object var1, Object var2);
    }

}

