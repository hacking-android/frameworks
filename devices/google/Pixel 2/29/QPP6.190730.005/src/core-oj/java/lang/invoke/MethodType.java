/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.invoke.MethodHandleStatics;
import java.lang.invoke.MethodTypeForm;
import java.lang.invoke.Stable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.invoke.util.BytecodeDescriptor;
import sun.invoke.util.Wrapper;
import sun.misc.Unsafe;

public final class MethodType
implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int MAX_JVM_ARITY = 255;
    static final int MAX_MH_ARITY = 254;
    static final int MAX_MH_INVOKER_ARITY = 253;
    static final Class<?>[] NO_PTYPES;
    static final ConcurrentWeakInternSet<MethodType> internTable;
    private static final MethodType[] objectOnlyTypes;
    private static final long ptypesOffset;
    private static final long rtypeOffset;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long serialVersionUID = 292L;
    @Stable
    private MethodTypeForm form;
    @Stable
    private String methodDescriptor;
    private final Class<?>[] ptypes;
    private final Class<?> rtype;
    @Stable
    private MethodType wrapAlt;

    static {
        internTable = new ConcurrentWeakInternSet();
        NO_PTYPES = new Class[0];
        objectOnlyTypes = new MethodType[20];
        serialPersistentFields = new ObjectStreamField[0];
        try {
            rtypeOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("rtype"));
            ptypesOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("ptypes"));
            return;
        }
        catch (Exception exception) {
            throw new Error(exception);
        }
    }

    private MethodType() {
        this.rtype = null;
        this.ptypes = null;
    }

    private MethodType(Class<?> arrclass, Class<?>[] arrclass2, boolean bl) {
        MethodType.checkRtype(arrclass);
        MethodType.checkPtypes(arrclass2);
        this.rtype = arrclass;
        arrclass = bl ? arrclass2 : Arrays.copyOf(arrclass2, arrclass2.length);
        this.ptypes = arrclass;
    }

    private MethodType(Class<?>[] arrclass, Class<?> class_) {
        this.rtype = class_;
        this.ptypes = arrclass;
    }

    private void MethodType_init(Class<?> class_, Class<?>[] arrclass) {
        MethodType.checkRtype(class_);
        MethodType.checkPtypes(arrclass);
        MethodHandleStatics.UNSAFE.putObject(this, rtypeOffset, class_);
        MethodHandleStatics.UNSAFE.putObject(this, ptypesOffset, arrclass);
    }

    static boolean canConvert(Class<?> object, Class<?> object2) {
        if (object != object2 && object != Object.class && object2 != Object.class) {
            if (((Class)object).isPrimitive()) {
                if (object == Void.TYPE) {
                    return true;
                }
                object = Wrapper.forPrimitiveType(object);
                if (((Class)object2).isPrimitive()) {
                    return Wrapper.forPrimitiveType(object2).isConvertibleFrom((Wrapper)((Object)object));
                }
                return ((Class)object2).isAssignableFrom(((Wrapper)((Object)object)).wrapperType());
            }
            if (((Class)object2).isPrimitive()) {
                if (object2 == Void.TYPE) {
                    return true;
                }
                if (((Class)object).isAssignableFrom(((Wrapper)((Object)(object2 = Wrapper.forPrimitiveType(object2)))).wrapperType())) {
                    return true;
                }
                return Wrapper.isWrapperType(object) && ((Wrapper)((Object)object2)).isConvertibleFrom(Wrapper.forWrapperType(object));
            }
            return true;
        }
        return true;
    }

    private boolean canConvertParameters(Class<?>[] arrclass, Class<?>[] arrclass2) {
        for (int i = 0; i < arrclass.length; ++i) {
            if (MethodType.canConvert(arrclass[i], arrclass2[i])) continue;
            return false;
        }
        return true;
    }

    private static void checkPtype(Class<?> class_) {
        Objects.requireNonNull(class_);
        if (class_ != Void.TYPE) {
            return;
        }
        throw MethodHandleStatics.newIllegalArgumentException("parameter type cannot be void");
    }

    private static int checkPtypes(Class<?>[] arrclass) {
        int n = 0;
        int n2 = arrclass.length;
        for (int i = 0; i < n2; ++i) {
            int n3;
            block4 : {
                block3 : {
                    Class<?> class_ = arrclass[i];
                    MethodType.checkPtype(class_);
                    if (class_ == Double.TYPE) break block3;
                    n3 = n;
                    if (class_ != Long.TYPE) break block4;
                }
                n3 = n + 1;
            }
            n = n3;
        }
        MethodType.checkSlotCount(arrclass.length + n);
        return n;
    }

    private static void checkRtype(Class<?> class_) {
        Objects.requireNonNull(class_);
    }

    static void checkSlotCount(int n) {
        if ((n & 255) == n) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("bad parameter count ");
        stringBuilder.append(n);
        throw MethodHandleStatics.newIllegalArgumentException(stringBuilder.toString());
    }

    private boolean equals(MethodType methodType) {
        boolean bl = this.rtype == methodType.rtype && Arrays.equals(this.ptypes, methodType.ptypes);
        return bl;
    }

    private static boolean explicitCastEquivalentToAsType(Class<?> class_, Class<?> class_2) {
        boolean bl = true;
        if (class_ != class_2 && class_2 != Object.class && class_2 != Void.TYPE) {
            if (class_.isPrimitive() && class_ != Void.TYPE) {
                return MethodType.canConvert(class_, class_2);
            }
            if (class_2.isPrimitive()) {
                return false;
            }
            boolean bl2 = bl;
            if (class_2.isInterface()) {
                bl2 = class_2.isAssignableFrom(class_) ? bl : false;
            }
            return bl2;
        }
        return true;
    }

    public static MethodType fromMethodDescriptorString(String object, ClassLoader object2) throws IllegalArgumentException, TypeNotPresentException {
        if (((String)object).startsWith("(") && ((String)object).indexOf(41) >= 0 && ((String)object).indexOf(46) < 0) {
            object = BytecodeDescriptor.parseMethod((String)object, (ClassLoader)object2);
            object2 = (Class)object.remove(object.size() - 1);
            MethodType.checkSlotCount(object.size());
            return MethodType.makeImpl(object2, MethodType.listToArray(object), true);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("not a method descriptor: ");
        ((StringBuilder)object2).append((String)object);
        throw MethodHandleStatics.newIllegalArgumentException(((StringBuilder)object2).toString());
    }

    public static MethodType genericMethodType(int n) {
        return MethodType.genericMethodType(n, false);
    }

    public static MethodType genericMethodType(int n, boolean bl) {
        MethodType.checkSlotCount(n);
        int n2 = n * 2 + bl;
        Object[] arrobject = objectOnlyTypes;
        if (n2 < arrobject.length && (arrobject = arrobject[n2]) != null) {
            return arrobject;
        }
        arrobject = new Class[n + bl];
        Arrays.fill(arrobject, Object.class);
        if (bl) {
            arrobject[n] = Object[].class;
        }
        MethodType methodType = MethodType.makeImpl(Object.class, arrobject, true);
        arrobject = objectOnlyTypes;
        if (n2 < arrobject.length) {
            arrobject[n2] = methodType;
        }
        return methodType;
    }

    private static Class<?>[] listToArray(List<Class<?>> list) {
        MethodType.checkSlotCount(list.size());
        return list.toArray(NO_PTYPES);
    }

    static MethodType makeImpl(Class<?> serializable, Class<?>[] arrclass, boolean bl) {
        Class<?>[] arrclass2 = internTable.get(new MethodType(arrclass, (Class<?>)serializable));
        if (arrclass2 != null) {
            return arrclass2;
        }
        arrclass2 = arrclass;
        if (arrclass.length == 0) {
            arrclass2 = NO_PTYPES;
            bl = true;
        }
        serializable = new MethodType((Class<?>)serializable, arrclass2, bl);
        ((MethodType)serializable).form = MethodTypeForm.findForm(serializable);
        return (MethodType)((Object)internTable.add((MethodType)serializable));
    }

    public static MethodType methodType(Class<?> class_) {
        return MethodType.makeImpl(class_, NO_PTYPES, true);
    }

    public static MethodType methodType(Class<?> class_, Class<?> class_2) {
        return MethodType.makeImpl(class_, new Class[]{class_2}, true);
    }

    public static MethodType methodType(Class<?> class_, Class<?> class_2, Class<?> ... arrclass) {
        Class[] arrclass2 = new Class[arrclass.length + 1];
        arrclass2[0] = class_2;
        System.arraycopy(arrclass, 0, arrclass2, 1, arrclass.length);
        return MethodType.makeImpl(class_, arrclass2, true);
    }

    public static MethodType methodType(Class<?> class_, MethodType methodType) {
        return MethodType.makeImpl(class_, methodType.ptypes, true);
    }

    public static MethodType methodType(Class<?> class_, List<Class<?>> list) {
        return MethodType.makeImpl(class_, MethodType.listToArray(list), false);
    }

    public static MethodType methodType(Class<?> class_, Class<?>[] arrclass) {
        return MethodType.makeImpl(class_, arrclass, false);
    }

    private static IndexOutOfBoundsException newIndexOutOfBoundsException(Object object) {
        Object object2 = object;
        if (object instanceof Integer) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("bad index: ");
            ((StringBuilder)object2).append(object);
            object2 = ((StringBuilder)object2).toString();
        }
        return new IndexOutOfBoundsException(object2.toString());
    }

    private void readObject(ObjectInputStream arrclass) throws IOException, ClassNotFoundException {
        arrclass.defaultReadObject();
        Class class_ = (Class)arrclass.readObject();
        arrclass = (Class[])arrclass.readObject();
        MethodType.checkRtype(class_);
        MethodType.checkPtypes(arrclass);
        this.MethodType_init(class_, (Class[])arrclass.clone());
    }

    private Object readResolve() {
        return MethodType.methodType(this.rtype, this.ptypes);
    }

    static String toFieldDescriptorString(Class<?> class_) {
        return BytecodeDescriptor.unparse(class_);
    }

    private static MethodType unwrapWithNoPrims(MethodType methodType) {
        MethodType methodType2;
        MethodType methodType3 = methodType2 = methodType.wrapAlt;
        if (methodType2 == null) {
            methodType3 = methodType2 = MethodTypeForm.canonicalize(methodType, 3, 3);
            if (methodType2 == null) {
                methodType3 = methodType;
            }
            methodType.wrapAlt = methodType3;
        }
        return methodType3;
    }

    private static MethodType wrapWithPrims(MethodType methodType) {
        MethodType methodType2;
        MethodType methodType3 = methodType2 = methodType.wrapAlt;
        if (methodType2 == null) {
            methodType.wrapAlt = methodType3 = MethodTypeForm.canonicalize(methodType, 2, 2);
        }
        return methodType3;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.returnType());
        objectOutputStream.writeObject(this.parameterArray());
    }

    public MethodType appendParameterTypes(List<Class<?>> list) {
        return this.insertParameterTypes(this.parameterCount(), list);
    }

    public MethodType appendParameterTypes(Class<?> ... arrclass) {
        return this.insertParameterTypes(this.parameterCount(), arrclass);
    }

    MethodType asCollectorType(Class<?> serializable, int n) {
        if (serializable == Object[].class) {
            MethodType methodType = MethodType.genericMethodType(n);
            Class<?> class_ = this.rtype;
            serializable = methodType;
            if (class_ != Object.class) {
                serializable = methodType.changeReturnType(class_);
            }
        } else {
            serializable = ((Class)serializable).getComponentType();
            serializable = MethodType.methodType(this.rtype, Collections.nCopies(n, serializable));
        }
        if (this.ptypes.length == 1) {
            return serializable;
        }
        return ((MethodType)serializable).insertParameterTypes(0, this.parameterList().subList(0, this.ptypes.length - 1));
    }

    MethodType asSpreaderType(Class<?> serializable, int n) {
        Object[] arrobject;
        int n2 = this.ptypes.length - n;
        if (n == 0) {
            return this;
        }
        if (serializable == Object[].class) {
            if (this.isGeneric()) {
                return this;
            }
            if (n2 == 0) {
                MethodType methodType = MethodType.genericMethodType(n);
                Class<?> class_ = this.rtype;
                serializable = methodType;
                if (class_ != Object.class) {
                    serializable = methodType.changeReturnType(class_);
                }
                return serializable;
            }
        }
        serializable = serializable.getComponentType();
        for (n = n2; n < (arrobject = this.ptypes).length; ++n) {
            if (arrobject[n] == serializable) continue;
            arrobject = (Class[])arrobject.clone();
            Arrays.fill(arrobject, n, this.ptypes.length, serializable);
            return MethodType.methodType(this.rtype, arrobject);
        }
        return this;
    }

    public MethodType changeParameterType(int n, Class<?> class_) {
        if (this.parameterType(n) == class_) {
            return this;
        }
        MethodType.checkPtype(class_);
        Class[] arrclass = (Class[])this.ptypes.clone();
        arrclass[n] = class_;
        return MethodType.makeImpl(this.rtype, arrclass, true);
    }

    public MethodType changeReturnType(Class<?> class_) {
        if (this.returnType() == class_) {
            return this;
        }
        return MethodType.makeImpl(class_, this.ptypes, true);
    }

    public MethodType dropParameterTypes(int n, int n2) {
        Object object = this.ptypes;
        int n3 = ((Class<?>[])object).length;
        if (n >= 0 && n <= n2 && n2 <= n3) {
            if (n == n2) {
                return this;
            }
            if (n == 0) {
                object = n2 == n3 ? NO_PTYPES : (Class[])Arrays.copyOfRange(object, n2, n3);
            } else if (n2 == n3) {
                object = (Class[])Arrays.copyOfRange(object, 0, n);
            } else {
                object = (Class[])Arrays.copyOfRange(object, 0, n + (n3 -= n2));
                System.arraycopy(this.ptypes, n2, object, n, n3);
            }
            return MethodType.makeImpl(this.rtype, object, true);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("start=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" end=");
        ((StringBuilder)object).append(n2);
        throw MethodType.newIndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public boolean equals(Object object) {
        boolean bl = this == object || object instanceof MethodType && this.equals((MethodType)object);
        return bl;
    }

    public MethodType erase() {
        return this.form.erasedType();
    }

    boolean explicitCastEquivalentToAsType(MethodType arrclass) {
        if (this == arrclass) {
            return true;
        }
        if (!MethodType.explicitCastEquivalentToAsType(this.rtype, arrclass.rtype)) {
            return false;
        }
        Class<?>[] arrclass2 = this.ptypes;
        arrclass = arrclass.ptypes;
        if (arrclass2 == arrclass) {
            return true;
        }
        for (int i = 0; i < arrclass2.length; ++i) {
            if (MethodType.explicitCastEquivalentToAsType(arrclass[i], arrclass2[i])) continue;
            return false;
        }
        return true;
    }

    MethodTypeForm form() {
        return this.form;
    }

    public MethodType generic() {
        return MethodType.genericMethodType(this.parameterCount());
    }

    public boolean hasPrimitives() {
        return this.form.hasPrimitives();
    }

    public boolean hasWrappers() {
        boolean bl = this.unwrap() != this;
        return bl;
    }

    public int hashCode() {
        int n = this.rtype.hashCode() + 31;
        Class<?>[] arrclass = this.ptypes;
        int n2 = arrclass.length;
        for (int i = 0; i < n2; ++i) {
            n = n * 31 + arrclass[i].hashCode();
        }
        return n;
    }

    public MethodType insertParameterTypes(int n, List<Class<?>> list) {
        return this.insertParameterTypes(n, MethodType.listToArray(list));
    }

    public MethodType insertParameterTypes(int n, Class<?> ... arrclass) {
        int n2 = this.ptypes.length;
        if (n >= 0 && n <= n2) {
            int n3 = MethodType.checkPtypes(arrclass);
            MethodType.checkSlotCount(this.parameterSlotCount() + arrclass.length + n3);
            n3 = arrclass.length;
            if (n3 == 0) {
                return this;
            }
            Class<?>[] arrclass2 = Arrays.copyOfRange(this.ptypes, 0, n2 + n3);
            System.arraycopy(arrclass2, n, arrclass2, n + n3, n2 - n);
            System.arraycopy(arrclass, 0, arrclass2, n, n3);
            return MethodType.makeImpl(this.rtype, arrclass2, true);
        }
        throw MethodType.newIndexOutOfBoundsException(n);
    }

    boolean isConvertibleTo(MethodType methodType) {
        MethodTypeForm methodTypeForm;
        MethodTypeForm methodTypeForm2 = this.form();
        if (methodTypeForm2 == (methodTypeForm = methodType.form())) {
            return true;
        }
        if (!MethodType.canConvert(this.returnType(), methodType.returnType())) {
            return false;
        }
        Class<?>[] arrclass = methodType.ptypes;
        Class<?>[] arrclass2 = this.ptypes;
        if (arrclass == arrclass2) {
            return true;
        }
        int n = arrclass.length;
        if (n != arrclass2.length) {
            return false;
        }
        if (n <= 1) {
            return n != 1 || MethodType.canConvert(arrclass[0], arrclass2[0]);
        }
        if (methodTypeForm2.primitiveParameterCount() == 0 && methodTypeForm2.erasedType == this || methodTypeForm.primitiveParameterCount() == 0 && methodTypeForm.erasedType == methodType) {
            return true;
        }
        return this.canConvertParameters(arrclass, arrclass2);
    }

    boolean isGeneric() {
        boolean bl = this == this.erase() && !this.hasPrimitives();
        return bl;
    }

    Class<?> lastParameterType() {
        Object object = this.ptypes;
        int n = ((Class<?>[])object).length;
        object = n == 0 ? Void.TYPE : object[n - 1];
        return object;
    }

    Class<?> leadingReferenceParameter() {
        Object object = this.ptypes;
        if (((Class<?>[])object).length != 0 && !((Class)(object = object[0])).isPrimitive()) {
            return object;
        }
        throw MethodHandleStatics.newIllegalArgumentException("no leading reference parameter");
    }

    public Class<?>[] parameterArray() {
        return (Class[])this.ptypes.clone();
    }

    public int parameterCount() {
        return this.ptypes.length;
    }

    public List<Class<?>> parameterList() {
        return Collections.unmodifiableList(Arrays.asList((Class[])this.ptypes.clone()));
    }

    int parameterSlotCount() {
        return this.form.parameterSlotCount();
    }

    public Class<?> parameterType(int n) {
        return this.ptypes[n];
    }

    public Class<?>[] ptypes() {
        return this.ptypes;
    }

    MethodType replaceParameterTypes(int n, int n2, Class<?> ... object) {
        if (n == n2) {
            return this.insertParameterTypes(n, (Class<?>)object);
        }
        int n3 = this.ptypes.length;
        if (n >= 0 && n <= n2 && n2 <= n3) {
            if (((Object)object).length == 0) {
                return this.dropParameterTypes(n, n2);
            }
            return this.dropParameterTypes(n, n2).insertParameterTypes(n, (Class<?>)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("start=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" end=");
        ((StringBuilder)object).append(n2);
        throw MethodType.newIndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public Class<?> returnType() {
        return this.rtype;
    }

    public Class<?> rtype() {
        return this.rtype;
    }

    public String toMethodDescriptorString() {
        String string;
        String string2 = string = this.methodDescriptor;
        if (string == null) {
            this.methodDescriptor = string2 = BytecodeDescriptor.unparse(this);
        }
        return string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < this.ptypes.length; ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(this.ptypes[i].getSimpleName());
        }
        stringBuilder.append(")");
        stringBuilder.append(this.rtype.getSimpleName());
        return stringBuilder.toString();
    }

    public MethodType unwrap() {
        MethodType methodType = !this.hasPrimitives() ? this : MethodType.wrapWithPrims(this);
        return MethodType.unwrapWithNoPrims(methodType);
    }

    public MethodType wrap() {
        MethodType methodType = this.hasPrimitives() ? MethodType.wrapWithPrims(this) : this;
        return methodType;
    }

    private static class ConcurrentWeakInternSet<T> {
        private final ConcurrentMap<WeakEntry<T>, WeakEntry<T>> map = new ConcurrentHashMap<WeakEntry<T>, WeakEntry<T>>();
        private final ReferenceQueue<T> stale = new ReferenceQueue();

        private void expungeStaleElements() {
            Reference<T> reference;
            while ((reference = this.stale.poll()) != null) {
                this.map.remove(reference);
            }
        }

        public T add(T t) {
            if (t != null) {
                WeakEntry<T> weakEntry;
                WeakEntry<T> weakEntry2 = new WeakEntry<T>(t, this.stale);
                do {
                    this.expungeStaleElements();
                    weakEntry = this.map.putIfAbsent(weakEntry2, weakEntry2);
                } while ((weakEntry = weakEntry == null ? t : weakEntry.get()) == null);
                return (T)weakEntry;
            }
            throw new NullPointerException();
        }

        public T get(T object) {
            if (object != null) {
                this.expungeStaleElements();
                object = (WeakEntry)this.map.get(new WeakEntry<T>(object));
                if (object != null && (object = ((Reference)object).get()) != null) {
                    return object;
                }
                return null;
            }
            throw new NullPointerException();
        }

        private static class WeakEntry<T>
        extends WeakReference<T> {
            public final int hashcode;

            public WeakEntry(T t) {
                super(t);
                this.hashcode = t.hashCode();
            }

            public WeakEntry(T t, ReferenceQueue<T> referenceQueue) {
                super(t, referenceQueue);
                this.hashcode = t.hashCode();
            }

            public boolean equals(Object object) {
                boolean bl = object instanceof WeakEntry;
                boolean bl2 = false;
                if (bl) {
                    Object t = ((WeakEntry)object).get();
                    Object t2 = this.get();
                    if (t != null && t2 != null) {
                        bl2 = t2.equals(t);
                    } else if (this == object) {
                        bl2 = true;
                    }
                    return bl2;
                }
                return false;
            }

            public int hashCode() {
                return this.hashcode;
            }
        }

    }

}

