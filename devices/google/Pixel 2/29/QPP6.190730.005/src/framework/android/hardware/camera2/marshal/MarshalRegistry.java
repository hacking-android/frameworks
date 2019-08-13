/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.marshal;

import android.hardware.camera2.marshal.MarshalHelpers;
import android.hardware.camera2.marshal.MarshalQueryable;
import android.hardware.camera2.marshal.Marshaler;
import android.hardware.camera2.utils.TypeReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MarshalRegistry {
    private static final Object sMarshalLock = new Object();
    private static final HashMap<MarshalToken<?>, Marshaler<?>> sMarshalerMap;
    private static final List<MarshalQueryable<?>> sRegisteredMarshalQueryables;

    static {
        sRegisteredMarshalQueryables = new ArrayList();
        sMarshalerMap = new HashMap();
    }

    private MarshalRegistry() {
        throw new AssertionError();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> Marshaler<T> getMarshaler(TypeReference<T> object, int n) {
        Object object2 = sMarshalLock;
        synchronized (object2) {
            MarshalToken<T> marshalToken = new MarshalToken<T>((TypeReference<T>)object, n);
            Object object3 = sMarshalerMap.get(marshalToken);
            Marshaler marshaler = object3;
            if (object3 == null) {
                block6 : {
                    if (sRegisteredMarshalQueryables.size() == 0) {
                        object = new AssertionError((Object)"No available query marshalers registered");
                        throw object;
                    }
                    Iterator<MarshalQueryable<?>> iterator = sRegisteredMarshalQueryables.iterator();
                    do {
                        marshaler = object3;
                        if (!iterator.hasNext()) break block6;
                    } while (!(marshaler = iterator.next()).isTypeMappingSupported(object, n));
                    marshaler = marshaler.createMarshaler(object, n);
                }
                if (marshaler == null) {
                    marshaler = new Marshaler();
                    ((StringBuilder)((Object)marshaler)).append("Could not find marshaler that matches the requested combination of type reference ");
                    ((StringBuilder)((Object)marshaler)).append(object);
                    ((StringBuilder)((Object)marshaler)).append(" and native type ");
                    ((StringBuilder)((Object)marshaler)).append(MarshalHelpers.toStringNativeType(n));
                    object3 = new UnsupportedOperationException(((StringBuilder)((Object)marshaler)).toString());
                    throw object3;
                }
                sMarshalerMap.put(marshalToken, marshaler);
            }
            return marshaler;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> void registerMarshalQueryable(MarshalQueryable<T> marshalQueryable) {
        Object object = sMarshalLock;
        synchronized (object) {
            sRegisteredMarshalQueryables.add(marshalQueryable);
            return;
        }
    }

    private static class MarshalToken<T> {
        private final int hash;
        final int nativeType;
        final TypeReference<T> typeReference;

        public MarshalToken(TypeReference<T> typeReference, int n) {
            this.typeReference = typeReference;
            this.nativeType = n;
            this.hash = typeReference.hashCode() ^ n;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof MarshalToken;
            boolean bl2 = false;
            if (bl) {
                object = (MarshalToken)object;
                bl = bl2;
                if (this.typeReference.equals(((MarshalToken)object).typeReference)) {
                    bl = bl2;
                    if (this.nativeType == ((MarshalToken)object).nativeType) {
                        bl = true;
                    }
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

}

