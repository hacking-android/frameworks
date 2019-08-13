/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.-$
 *  android.os.-$$Lambda
 *  android.os.-$$Lambda$HidlSupport
 *  android.os.-$$Lambda$HidlSupport$CwwfmHPEvZaybUxpLzKdwrpQRfA
 *  android.os.-$$Lambda$HidlSupport$GHxmwrIWiKN83tl6aMQt_nV5hiw
 */
package android.os;

import android.annotation.SystemApi;
import android.os.-$;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os._$$Lambda$HidlSupport$4ktYtLCfMafhYI23iSXUQOH_hxo;
import android.os._$$Lambda$HidlSupport$CwwfmHPEvZaybUxpLzKdwrpQRfA;
import android.os._$$Lambda$HidlSupport$GHxmwrIWiKN83tl6aMQt_nV5hiw;
import android.os._$$Lambda$HidlSupport$oV2DlGQSAfcavBj7TK20nYhwS0U;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SystemApi
public class HidlSupport {
    @SystemApi
    public static boolean deepEquals(Object arrobject, Object arrobject2) {
        boolean bl = true;
        if (arrobject == arrobject2) {
            return true;
        }
        if (arrobject != null && arrobject2 != null) {
            Class<?> class_;
            Class<?> class_2 = arrobject.getClass();
            if (class_2 != (class_ = arrobject2.getClass())) {
                return false;
            }
            if (class_2.isArray()) {
                if ((class_2 = class_2.getComponentType()) != class_.getComponentType()) {
                    return false;
                }
                if (class_2.isPrimitive()) {
                    return Objects.deepEquals(arrobject, arrobject2);
                }
                if ((arrobject = (Object[])arrobject).length != (arrobject2 = (Object[])arrobject2).length || !IntStream.range(0, arrobject.length).allMatch(new _$$Lambda$HidlSupport$4ktYtLCfMafhYI23iSXUQOH_hxo(arrobject, arrobject2))) {
                    bl = false;
                }
                return bl;
            }
            if (arrobject instanceof List) {
                arrobject = (List)arrobject;
                arrobject2 = (List)arrobject2;
                if (arrobject.size() != arrobject2.size()) {
                    return false;
                }
                arrobject = arrobject.iterator();
                return arrobject2.stream().allMatch(new _$$Lambda$HidlSupport$oV2DlGQSAfcavBj7TK20nYhwS0U((Iterator)arrobject));
            }
            HidlSupport.throwErrorIfUnsupportedType(arrobject);
            return arrobject.equals(arrobject2);
        }
        return false;
    }

    @SystemApi
    public static int deepHashCode(Object object) {
        if (object == null) {
            return 0;
        }
        Class<?> class_ = object.getClass();
        if (class_.isArray()) {
            if (class_.getComponentType().isPrimitive()) {
                return HidlSupport.primitiveArrayHashCode(object);
            }
            return Arrays.hashCode(Arrays.stream((Object[])object).mapToInt((ToIntFunction<Object>)_$$Lambda$HidlSupport$GHxmwrIWiKN83tl6aMQt_nV5hiw.INSTANCE).toArray());
        }
        if (object instanceof List) {
            return Arrays.hashCode(((List)object).stream().mapToInt(_$$Lambda$HidlSupport$CwwfmHPEvZaybUxpLzKdwrpQRfA.INSTANCE).toArray());
        }
        HidlSupport.throwErrorIfUnsupportedType(object);
        return object.hashCode();
    }

    @SystemApi
    public static native int getPidIfSharable();

    @SystemApi
    public static boolean interfacesEqual(IHwInterface iHwInterface, Object object) {
        if (iHwInterface == object) {
            return true;
        }
        if (iHwInterface != null && object != null) {
            if (!(object instanceof IHwInterface)) {
                return false;
            }
            return Objects.equals(iHwInterface.asBinder(), ((IHwInterface)object).asBinder());
        }
        return false;
    }

    static /* synthetic */ boolean lambda$deepEquals$0(Object[] arrobject, Object[] arrobject2, int n) {
        return HidlSupport.deepEquals(arrobject[n], arrobject2[n]);
    }

    static /* synthetic */ boolean lambda$deepEquals$1(Iterator iterator, Object object) {
        return HidlSupport.deepEquals(iterator.next(), object);
    }

    static /* synthetic */ int lambda$deepHashCode$2(Object object) {
        return HidlSupport.deepHashCode(object);
    }

    static /* synthetic */ int lambda$deepHashCode$3(Object object) {
        return HidlSupport.deepHashCode(object);
    }

    private static int primitiveArrayHashCode(Object object) {
        Class<?> class_ = object.getClass().getComponentType();
        if (class_ == Boolean.TYPE) {
            return Arrays.hashCode((boolean[])object);
        }
        if (class_ == Byte.TYPE) {
            return Arrays.hashCode((byte[])object);
        }
        if (class_ == Character.TYPE) {
            return Arrays.hashCode((char[])object);
        }
        if (class_ == Double.TYPE) {
            return Arrays.hashCode((double[])object);
        }
        if (class_ == Float.TYPE) {
            return Arrays.hashCode((float[])object);
        }
        if (class_ == Integer.TYPE) {
            return Arrays.hashCode((int[])object);
        }
        if (class_ == Long.TYPE) {
            return Arrays.hashCode((long[])object);
        }
        if (class_ == Short.TYPE) {
            return Arrays.hashCode((short[])object);
        }
        throw new UnsupportedOperationException();
    }

    private static void throwErrorIfUnsupportedType(Object object) {
        if (object instanceof Collection && !(object instanceof List)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot check equality on collections other than lists: ");
            stringBuilder.append(object.getClass().getName());
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
        if (!(object instanceof Map)) {
            return;
        }
        throw new UnsupportedOperationException("Cannot check equality on maps");
    }

    public static final class Mutable<E> {
        public E value;

        public Mutable() {
            this.value = null;
        }

        public Mutable(E e) {
            this.value = e;
        }
    }

}

