/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.lang.ref.WeakReference;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.util.HashMap;
import java.util.Map;

public class CoderResult {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int CR_ERROR_MIN = 2;
    private static final int CR_MALFORMED = 2;
    private static final int CR_OVERFLOW = 1;
    private static final int CR_UNDERFLOW = 0;
    private static final int CR_UNMAPPABLE = 3;
    public static final CoderResult OVERFLOW;
    public static final CoderResult UNDERFLOW;
    private static Cache malformedCache;
    private static final String[] names;
    private static Cache unmappableCache;
    private final int length;
    private final int type;

    static {
        names = new String[]{"UNDERFLOW", "OVERFLOW", "MALFORMED", "UNMAPPABLE"};
        UNDERFLOW = new CoderResult(0, 0);
        OVERFLOW = new CoderResult(1, 0);
        malformedCache = new Cache(){

            @Override
            public CoderResult create(int n) {
                return new CoderResult(2, n);
            }
        };
        unmappableCache = new Cache(){

            @Override
            public CoderResult create(int n) {
                return new CoderResult(3, n);
            }
        };
    }

    private CoderResult(int n, int n2) {
        this.type = n;
        this.length = n2;
    }

    public static CoderResult malformedForLength(int n) {
        return CoderResult.malformedCache.get(n);
    }

    public static CoderResult unmappableForLength(int n) {
        return CoderResult.unmappableCache.get(n);
    }

    public boolean isError() {
        boolean bl = this.type >= 2;
        return bl;
    }

    public boolean isMalformed() {
        boolean bl = this.type == 2;
        return bl;
    }

    public boolean isOverflow() {
        int n = this.type;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isUnderflow() {
        boolean bl = this.type == 0;
        return bl;
    }

    public boolean isUnmappable() {
        boolean bl = this.type == 3;
        return bl;
    }

    public int length() {
        if (this.isError()) {
            return this.length;
        }
        throw new UnsupportedOperationException();
    }

    public void throwException() throws CharacterCodingException {
        int n = this.type;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return;
                    }
                    throw new UnmappableCharacterException(this.length);
                }
                throw new MalformedInputException(this.length);
            }
            throw new BufferOverflowException();
        }
        throw new BufferUnderflowException();
    }

    public String toString() {
        String string;
        block0 : {
            string = names[this.type];
            if (!this.isError()) break block0;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("[");
            stringBuilder.append(this.length);
            stringBuilder.append("]");
            string = stringBuilder.toString();
        }
        return string;
    }

    private static abstract class Cache {
        private Map<Integer, WeakReference<CoderResult>> cache = null;

        private Cache() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private CoderResult get(int n) {
            synchronized (this) {
                Throwable throwable2;
                if (n > 0) {
                    try {
                        WeakReference<CoderResult> weakReference;
                        Integer n2 = new Integer(n);
                        WeakReference<Object> weakReference2 = null;
                        if (this.cache == null) {
                            weakReference = new HashMap();
                            this.cache = weakReference;
                        } else {
                            weakReference = this.cache.get(n2);
                            if (weakReference != null) {
                                weakReference2 = (CoderResult)weakReference.get();
                            }
                        }
                        weakReference = weakReference2;
                        if (weakReference2 == null) {
                            weakReference = this.create(n);
                            Map<Integer, WeakReference<CoderResult>> map = this.cache;
                            weakReference2 = new WeakReference<Object>(weakReference);
                            map.put(n2, weakReference2);
                        }
                        return weakReference;
                    }
                    catch (Throwable throwable2) {}
                } else {
                    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Non-positive length");
                    throw illegalArgumentException;
                }
                throw throwable2;
            }
        }

        protected abstract CoderResult create(int var1);
    }

}

