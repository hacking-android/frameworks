/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.ArrayElementVarHandle
 *  java.lang.invoke.ByteArrayViewVarHandle
 *  java.lang.invoke.ByteBufferViewVarHandle
 *  java.lang.invoke.FieldVarHandle
 */
package java.lang.invoke;

import java.io.Serializable;
import java.lang.invoke.ArrayElementVarHandle;
import java.lang.invoke.ByteArrayViewVarHandle;
import java.lang.invoke.ByteBufferViewVarHandle;
import java.lang.invoke.FieldVarHandle;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleImpl;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandleStatics;
import java.lang.invoke.MethodType;
import java.lang.invoke.Transformers;
import java.lang.invoke.VarHandle;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import sun.invoke.util.VerifyAccess;
import sun.invoke.util.Wrapper;
import sun.reflect.Reflection;

public class MethodHandles {
    private MethodHandles() {
    }

    public static byte arrayElementGetter(byte[] arrby, int n) {
        return arrby[n];
    }

    public static char arrayElementGetter(char[] arrc, int n) {
        return arrc[n];
    }

    public static double arrayElementGetter(double[] arrd, int n) {
        return arrd[n];
    }

    public static float arrayElementGetter(float[] arrf, int n) {
        return arrf[n];
    }

    public static int arrayElementGetter(int[] arrn, int n) {
        return arrn[n];
    }

    public static long arrayElementGetter(long[] arrl, int n) {
        return arrl[n];
    }

    public static MethodHandle arrayElementGetter(Class<?> object) throws IllegalArgumentException {
        MethodHandles.checkClassIsArray(object);
        Class<?> class_ = ((Class)object).getComponentType();
        if (class_.isPrimitive()) {
            try {
                object = Lookup.PUBLIC_LOOKUP.findStatic(MethodHandles.class, "arrayElementGetter", MethodType.methodType(class_, object, new Class[]{Integer.TYPE}));
                return object;
            }
            catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
                throw new AssertionError(reflectiveOperationException);
            }
        }
        return new Transformers.ReferenceArrayElementGetter((Class<?>)object);
    }

    public static short arrayElementGetter(short[] arrs, int n) {
        return arrs[n];
    }

    public static boolean arrayElementGetter(boolean[] arrbl, int n) {
        return arrbl[n];
    }

    public static MethodHandle arrayElementSetter(Class<?> object) throws IllegalArgumentException {
        MethodHandles.checkClassIsArray(object);
        Class<?> class_ = ((Class)object).getComponentType();
        if (class_.isPrimitive()) {
            try {
                object = Lookup.PUBLIC_LOOKUP.findStatic(MethodHandles.class, "arrayElementSetter", MethodType.methodType(Void.TYPE, object, new Class[]{Integer.TYPE, class_}));
                return object;
            }
            catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
                throw new AssertionError(reflectiveOperationException);
            }
        }
        return new Transformers.ReferenceArrayElementSetter((Class<?>)object);
    }

    public static void arrayElementSetter(byte[] arrby, int n, byte by) {
        arrby[n] = by;
    }

    public static void arrayElementSetter(char[] arrc, int n, char c) {
        arrc[n] = c;
    }

    public static void arrayElementSetter(double[] arrd, int n, double d) {
        arrd[n] = d;
    }

    public static void arrayElementSetter(float[] arrf, int n, float f) {
        arrf[n] = f;
    }

    public static void arrayElementSetter(int[] arrn, int n, int n2) {
        arrn[n] = n2;
    }

    public static void arrayElementSetter(long[] arrl, int n, long l) {
        arrl[n] = l;
    }

    public static void arrayElementSetter(short[] arrs, int n, short s) {
        arrs[n] = s;
    }

    public static void arrayElementSetter(boolean[] arrbl, int n, boolean bl) {
        arrbl[n] = bl;
    }

    public static VarHandle arrayElementVarHandle(Class<?> class_) throws IllegalArgumentException {
        MethodHandles.checkClassIsArray(class_);
        return ArrayElementVarHandle.create(class_);
    }

    public static VarHandle byteArrayViewVarHandle(Class<?> class_, ByteOrder byteOrder) throws IllegalArgumentException {
        MethodHandles.checkClassIsArray(class_);
        MethodHandles.checkTypeIsViewable(class_.getComponentType());
        return ByteArrayViewVarHandle.create(class_, (ByteOrder)byteOrder);
    }

    public static VarHandle byteBufferViewVarHandle(Class<?> class_, ByteOrder byteOrder) throws IllegalArgumentException {
        MethodHandles.checkClassIsArray(class_);
        MethodHandles.checkTypeIsViewable(class_.getComponentType());
        return ByteBufferViewVarHandle.create(class_, (ByteOrder)byteOrder);
    }

    public static MethodHandle catchException(MethodHandle object, Class<? extends Throwable> class_, MethodHandle methodHandle) {
        MethodType methodType = ((MethodHandle)object).type();
        MethodType methodType2 = methodHandle.type();
        if (methodType2.parameterCount() >= 1 && methodType2.parameterType(0).isAssignableFrom(class_)) {
            if (methodType2.returnType() == methodType.returnType()) {
                int n;
                List<Class<?>> list = methodType.parameterList();
                List<Class<?>> list2 = methodType2.parameterList();
                if (!(list.equals(list2 = list2.subList(1, list2.size())) || (n = list2.size()) < list.size() && list.subList(0, n).equals(list2))) {
                    throw MethodHandles.misMatchedTypes("target and handler types", methodType, methodType2);
                }
                return new Transformers.CatchException((MethodHandle)object, methodHandle, class_);
            }
            throw MethodHandles.misMatchedTypes("target and handler return types", methodType, methodType2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("handler does not accept exception type ");
        ((StringBuilder)object).append(class_);
        throw MethodHandleStatics.newIllegalArgumentException(((StringBuilder)object).toString());
    }

    private static void checkClassIsArray(Class<?> class_) {
        if (class_.isArray()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not an array type: ");
        stringBuilder.append(class_);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static void checkTypeIsViewable(Class<?> class_) {
        if (class_ != Short.TYPE && class_ != Character.TYPE && class_ != Integer.TYPE && class_ != Long.TYPE && class_ != Float.TYPE && class_ != Double.TYPE) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Component type not supported: ");
            stringBuilder.append(class_);
            throw new UnsupportedOperationException(stringBuilder.toString());
        }
    }

    public static MethodHandle collectArguments(MethodHandle methodHandle, int n, MethodHandle methodHandle2) {
        return new Transformers.CollectArguments(methodHandle, methodHandle2, n, MethodHandles.collectArgumentsChecks(methodHandle, n, methodHandle2));
    }

    private static MethodType collectArgumentsChecks(MethodHandle object, int n, MethodHandle object2) throws RuntimeException {
        object = ((MethodHandle)object).type();
        object2 = ((MethodHandle)object2).type();
        Class<?> class_ = ((MethodType)object2).returnType();
        List<Class<?>> list = ((MethodType)object2).parameterList();
        if (class_ == Void.TYPE) {
            return ((MethodType)object).insertParameterTypes(n, list);
        }
        if (class_ == ((MethodType)object).parameterType(n)) {
            return ((MethodType)object).dropParameterTypes(n, n + 1).insertParameterTypes(n, list);
        }
        throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", object, object2);
    }

    public static MethodHandle constant(Class<?> class_, Object object) {
        Object object2 = object;
        if (class_.isPrimitive()) {
            if (class_ != Void.TYPE) {
                object2 = Wrapper.forPrimitiveType(class_).convert(object, class_);
            } else {
                throw MethodHandleStatics.newIllegalArgumentException("void type");
            }
        }
        return new Transformers.Constant(class_, object2);
    }

    private static List<Class<?>> copyTypes(List<Class<?>> arrobject) {
        arrobject = arrobject.toArray();
        return Arrays.asList((Class[])Arrays.copyOf(arrobject, arrobject.length, Class[].class));
    }

    private static int dropArgumentChecks(MethodType methodType, int n, List<Class<?>> list) {
        int n2 = list.size();
        MethodType.checkSlotCount(n2);
        int n3 = methodType.parameterCount();
        if (n >= 0 && n <= n3) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("no argument type to remove");
        stringBuilder.append(Arrays.asList(methodType, n, list, n3 + n2, n3));
        throw MethodHandleStatics.newIllegalArgumentException(stringBuilder.toString());
    }

    public static MethodHandle dropArguments(MethodHandle methodHandle, int n, List<Class<?>> list) {
        list = MethodHandles.copyTypes(list);
        MethodType methodType = methodHandle.type();
        int n2 = MethodHandles.dropArgumentChecks(methodType, n, list);
        methodType = methodType.insertParameterTypes(n, list);
        if (n2 == 0) {
            return methodHandle;
        }
        return new Transformers.DropArguments(methodType, methodHandle, n, list.size());
    }

    public static MethodHandle dropArguments(MethodHandle methodHandle, int n, Class<?> ... arrclass) {
        return MethodHandles.dropArguments(methodHandle, n, Arrays.asList(arrclass));
    }

    public static MethodHandle exactInvoker(MethodType methodType) {
        return new Transformers.Invoker(methodType, true);
    }

    public static MethodHandle explicitCastArguments(MethodHandle methodHandle, MethodType methodType) {
        MethodHandles.explicitCastArgumentsChecks(methodHandle, methodType);
        MethodType methodType2 = methodHandle.type();
        if (methodType2 == methodType) {
            return methodHandle;
        }
        if (methodType2.explicitCastEquivalentToAsType(methodType)) {
            return methodHandle.asFixedArity().asType(methodType);
        }
        return new Transformers.ExplicitCastArguments(methodHandle, methodType);
    }

    private static void explicitCastArgumentsChecks(MethodHandle methodHandle, MethodType methodType) {
        if (methodHandle.type().parameterCount() == methodType.parameterCount()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cannot explicitly cast ");
        stringBuilder.append(methodHandle);
        stringBuilder.append(" to ");
        stringBuilder.append(methodType);
        throw new WrongMethodTypeException(stringBuilder.toString());
    }

    private static void filterArgumentChecks(MethodHandle object, int n, MethodHandle object2) throws RuntimeException {
        object = ((MethodHandle)object).type();
        if (((MethodType)(object2 = ((MethodHandle)object2).type())).parameterCount() == 1 && ((MethodType)object2).returnType() == ((MethodType)object).parameterType(n)) {
            return;
        }
        throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", object, object2);
    }

    public static MethodHandle filterArguments(MethodHandle methodHandle, int n, MethodHandle ... arrmethodHandle) {
        MethodHandles.filterArgumentsCheckArity(methodHandle, n, arrmethodHandle);
        for (int i = 0; i < arrmethodHandle.length; ++i) {
            MethodHandles.filterArgumentChecks(methodHandle, i + n, arrmethodHandle[i]);
        }
        return new Transformers.FilterArguments(methodHandle, n, arrmethodHandle);
    }

    private static void filterArgumentsCheckArity(MethodHandle methodHandle, int n, MethodHandle[] arrmethodHandle) {
        int n2 = methodHandle.type().parameterCount();
        if (arrmethodHandle.length + n <= n2) {
            return;
        }
        throw MethodHandleStatics.newIllegalArgumentException("too many filters");
    }

    public static MethodHandle filterReturnValue(MethodHandle methodHandle, MethodHandle methodHandle2) {
        MethodHandles.filterReturnValueChecks(methodHandle.type(), methodHandle2.type());
        return new Transformers.FilterReturnValue(methodHandle, methodHandle2);
    }

    private static void filterReturnValueChecks(MethodType methodType, MethodType methodType2) throws RuntimeException {
        Class<?> class_ = methodType.returnType();
        int n = methodType2.parameterCount();
        if (n == 0 ? class_ == Void.TYPE : class_ == methodType2.parameterType(0) && n == 1) {
            return;
        }
        throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", methodType, methodType2);
    }

    private static Class<?> foldArgumentChecks(int n, MethodType methodType, MethodType methodType2) {
        int n2 = methodType2.parameterCount();
        Class<?> class_ = methodType2.returnType();
        Class<Void> class_2 = Void.TYPE;
        int n3 = 1;
        int n4 = class_ == class_2 ? 0 : 1;
        int n5 = n + n4;
        if (methodType.parameterCount() < n5 + n2) {
            n3 = 0;
        }
        n = n3;
        if (n3 != 0) {
            n = n3;
            if (!methodType2.parameterList().equals(methodType.parameterList().subList(n5, n5 + n2))) {
                n = 0;
            }
        }
        n3 = n;
        if (n != 0) {
            n3 = n;
            if (n4 != 0) {
                n3 = n;
                if (methodType2.returnType() != methodType.parameterType(0)) {
                    n3 = 0;
                }
            }
        }
        if (n3 != 0) {
            return class_;
        }
        throw MethodHandles.misMatchedTypes("target and combiner types", methodType, methodType2);
    }

    public static MethodHandle foldArguments(MethodHandle methodHandle, MethodHandle methodHandle2) {
        MethodHandles.foldArgumentChecks(0, methodHandle.type(), methodHandle2.type());
        return new Transformers.FoldArguments(methodHandle, methodHandle2);
    }

    private static MethodHandleImpl getMethodHandleImpl(MethodHandle methodHandle) {
        Object object = methodHandle;
        if (methodHandle instanceof Transformers.Construct) {
            object = ((Transformers.Construct)methodHandle).getConstructorHandle();
        }
        methodHandle = object;
        if (object instanceof Transformers.VarargsCollector) {
            methodHandle = ((MethodHandle)object).asFixedArity();
        }
        if (methodHandle instanceof MethodHandleImpl) {
            return (MethodHandleImpl)methodHandle;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(methodHandle);
        ((StringBuilder)object).append(" is not a direct handle");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static MethodHandle guardWithTest(MethodHandle object, MethodHandle methodHandle, MethodHandle methodHandle2) {
        Object object2;
        MethodType methodType = ((MethodHandle)object).type();
        MethodType methodType2 = methodHandle.type();
        if (methodType2.equals(object2 = methodHandle2.type())) {
            if (methodType.returnType() == Boolean.TYPE) {
                List<Class<?>> list = methodType2.parameterList();
                List<Class<?>> list2 = methodType.parameterList();
                object2 = object;
                if (!list.equals(list2)) {
                    int n;
                    int n2 = list2.size();
                    if (n2 < (n = list.size()) && list.subList(0, n2).equals(list2)) {
                        object2 = MethodHandles.dropArguments((MethodHandle)object, n2, list.subList(n2, n));
                        ((MethodHandle)object2).type();
                    } else {
                        throw MethodHandles.misMatchedTypes("target and test types", methodType2, methodType);
                    }
                }
                return new Transformers.GuardWithTest((MethodHandle)object2, methodHandle, methodHandle2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("guard type is not a predicate ");
            ((StringBuilder)object).append(methodType);
            throw MethodHandleStatics.newIllegalArgumentException(((StringBuilder)object).toString());
        }
        throw MethodHandles.misMatchedTypes("target and fallback types", methodType2, (MethodType)object2);
    }

    public static byte identity(byte by) {
        return by;
    }

    public static char identity(char c) {
        return c;
    }

    public static double identity(double d) {
        return d;
    }

    public static float identity(float f) {
        return f;
    }

    public static int identity(int n) {
        return n;
    }

    public static long identity(long l) {
        return l;
    }

    public static MethodHandle identity(Class<?> object) {
        if (object != null) {
            if (((Class)object).isPrimitive()) {
                try {
                    object = Lookup.PUBLIC_LOOKUP.findStatic(MethodHandles.class, "identity", MethodType.methodType(object, object));
                    return object;
                }
                catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
                    throw new AssertionError(reflectiveOperationException);
                }
            }
            return new Transformers.ReferenceIdentity((Class<?>)object);
        }
        throw new NullPointerException("type == null");
    }

    public static short identity(short s) {
        return s;
    }

    public static boolean identity(boolean bl) {
        return bl;
    }

    public static MethodHandle insertArguments(MethodHandle methodHandle, int n, Object ... arrobject) {
        int n2 = arrobject.length;
        Class<?>[] arrclass = MethodHandles.insertArgumentsChecks(methodHandle, n2, n);
        if (n2 == 0) {
            return methodHandle;
        }
        for (int i = 0; i < n2; ++i) {
            Class<?> class_ = arrclass[n + i];
            if (!class_.isPrimitive()) {
                arrclass[n + i].cast(arrobject[i]);
                continue;
            }
            arrobject[i] = Wrapper.forPrimitiveType(class_).convert(arrobject[i], class_);
        }
        return new Transformers.InsertArguments(methodHandle, n, arrobject);
    }

    private static Class<?>[] insertArgumentsChecks(MethodHandle object, int n, int n2) throws RuntimeException {
        n = ((MethodType)(object = ((MethodHandle)object).type())).parameterCount() - n;
        if (n >= 0) {
            if (n2 >= 0 && n2 <= n) {
                return ((MethodType)object).ptypes();
            }
            throw MethodHandleStatics.newIllegalArgumentException("no argument type to append");
        }
        throw MethodHandleStatics.newIllegalArgumentException("too many values to insert");
    }

    public static MethodHandle invoker(MethodType methodType) {
        return new Transformers.Invoker(methodType, false);
    }

    public static Lookup lookup() {
        return new Lookup(Reflection.getCallerClass());
    }

    private static MethodHandle methodHandleForVarHandleAccessor(VarHandle.AccessMode object, MethodType serializable, boolean bl) {
        Method method;
        try {
            method = VarHandle.class.getDeclaredMethod(object.methodName(), Object[].class);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("No method for AccessMode ");
            ((StringBuilder)serializable).append(object);
            throw new InternalError(((StringBuilder)serializable).toString(), noSuchMethodException);
        }
        object = ((MethodType)serializable).insertParameterTypes(0, VarHandle.class);
        int n = bl ? 8 : 7;
        return new MethodHandleImpl(method.getArtMethod(), n, (MethodType)object);
    }

    static RuntimeException misMatchedTypes(String string, MethodType methodType, MethodType methodType2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" must match: ");
        stringBuilder.append(methodType);
        stringBuilder.append(" != ");
        stringBuilder.append(methodType2);
        return MethodHandleStatics.newIllegalArgumentException(stringBuilder.toString());
    }

    private static boolean permuteArgumentChecks(int[] arrn, MethodType serializable, MethodType methodType) {
        if (((MethodType)serializable).returnType() == methodType.returnType()) {
            if (arrn.length == methodType.parameterCount()) {
                int n;
                block4 : {
                    block5 : {
                        int n2 = ((MethodType)serializable).parameterCount();
                        int n3 = 0;
                        int n4 = 0;
                        do {
                            n = n3;
                            if (n4 >= arrn.length) break block4;
                            n = arrn[n4];
                            if (n < 0 || n >= n2) break block5;
                            if (((MethodType)serializable).parameterType(n) != methodType.parameterType(n4)) break;
                            ++n4;
                        } while (true);
                        throw MethodHandleStatics.newIllegalArgumentException("parameter types do not match after reorder", methodType, serializable);
                    }
                    n = 1;
                }
                if (n == 0) {
                    return true;
                }
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("bad reorder array: ");
            ((StringBuilder)serializable).append(Arrays.toString(arrn));
            throw MethodHandleStatics.newIllegalArgumentException(((StringBuilder)serializable).toString());
        }
        throw MethodHandleStatics.newIllegalArgumentException("return types do not match", methodType, serializable);
    }

    public static MethodHandle permuteArguments(MethodHandle methodHandle, MethodType methodType, int ... arrn) {
        arrn = (int[])arrn.clone();
        MethodHandles.permuteArgumentChecks(arrn, methodType, methodHandle.type());
        return new Transformers.PermuteArguments(methodType, methodHandle, arrn);
    }

    public static Lookup publicLookup() {
        return Lookup.PUBLIC_LOOKUP;
    }

    public static <T extends Member> T reflectAs(Class<T> class_, MethodHandle methodHandle) {
        return (T)((Member)class_.cast(MethodHandles.getMethodHandleImpl(methodHandle).getMemberInternal()));
    }

    public static MethodHandle spreadInvoker(MethodType methodType, int n) {
        if (n >= 0 && n <= methodType.parameterCount()) {
            return MethodHandles.invoker(methodType).asSpreader(Object[].class, methodType.parameterCount() - n);
        }
        throw MethodHandleStatics.newIllegalArgumentException("bad argument count", n);
    }

    public static MethodHandle throwException(Class<?> class_, Class<? extends Throwable> class_2) {
        if (Throwable.class.isAssignableFrom(class_2)) {
            return new Transformers.AlwaysThrow(class_, class_2);
        }
        throw new ClassCastException(class_2.getName());
    }

    public static MethodHandle varHandleExactInvoker(VarHandle.AccessMode accessMode, MethodType methodType) {
        return MethodHandles.methodHandleForVarHandleAccessor(accessMode, methodType, true);
    }

    public static MethodHandle varHandleInvoker(VarHandle.AccessMode accessMode, MethodType methodType) {
        return MethodHandles.methodHandleForVarHandleAccessor(accessMode, methodType, false);
    }

    public static final class Lookup {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int ALL_MODES = 15;
        static final Lookup IMPL_LOOKUP;
        public static final int PACKAGE = 8;
        public static final int PRIVATE = 2;
        public static final int PROTECTED = 4;
        public static final int PUBLIC = 1;
        static final Lookup PUBLIC_LOOKUP;
        private final int allowedModes;
        private final Class<?> lookupClass;

        static {
            PUBLIC_LOOKUP = new Lookup(Object.class, 1);
            IMPL_LOOKUP = new Lookup(Object.class, 15);
        }

        Lookup(Class<?> class_) {
            this(class_, 15);
            Lookup.checkUnprivilegedlookupClass(class_, 15);
        }

        private Lookup(Class<?> class_, int n) {
            this.lookupClass = class_;
            this.allowedModes = n;
        }

        private void checkReturnType(Method method, MethodType methodType) throws NoSuchMethodException {
            if (method.getReturnType() == methodType.rtype()) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(method.getName());
            stringBuilder.append(methodType);
            throw new NoSuchMethodException(stringBuilder.toString());
        }

        private void checkSpecialCaller(Class<?> class_) throws IllegalAccessException {
            if (this.hasPrivateAccess() && class_ == this.lookupClass()) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("no private access for invokespecial : ");
            stringBuilder.append(class_);
            stringBuilder.append(", from");
            stringBuilder.append(this);
            throw new IllegalAccessException(stringBuilder.toString());
        }

        private static void checkUnprivilegedlookupClass(Class<?> class_, int n) {
            CharSequence charSequence = class_.getName();
            if (!((String)charSequence).startsWith("java.lang.invoke.")) {
                if (n == 15 && class_.getClassLoader() == Object.class.getClassLoader() && (((String)charSequence).startsWith("java.") || ((String)charSequence).startsWith("sun.") && !((String)charSequence).startsWith("sun.invoke.") && !((String)charSequence).equals("sun.reflect.ReflectionFactory"))) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("illegal lookupClass: ");
                    ((StringBuilder)charSequence).append(class_);
                    throw MethodHandleStatics.newIllegalArgumentException(((StringBuilder)charSequence).toString());
                }
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("illegal lookupClass: ");
            ((StringBuilder)charSequence).append(class_);
            throw MethodHandleStatics.newIllegalArgumentException(((StringBuilder)charSequence).toString());
        }

        private void commonFieldChecks(Field object, Class<?> serializable, Class<?> class_, boolean bl, boolean bl2) throws IllegalAccessException {
            int n = ((Field)object).getModifiers();
            if (bl2) {
                this.checkAccess((Class<?>)serializable, ((Field)object).getDeclaringClass(), n, ((Field)object).getName());
            }
            if (Modifier.isStatic(n) != bl) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Field ");
                ((StringBuilder)serializable).append(object);
                ((StringBuilder)serializable).append(" is ");
                object = bl ? "not " : "";
                ((StringBuilder)serializable).append((String)object);
                ((StringBuilder)serializable).append("static");
                throw new IllegalAccessException(((StringBuilder)serializable).toString());
            }
        }

        private static MethodHandle createMethodHandle(Method method, int n, MethodType object) {
            object = new MethodHandleImpl(method.getArtMethod(), n, (MethodType)object);
            if (method.isVarArgs()) {
                return new Transformers.VarargsCollector((MethodHandle)object);
            }
            return object;
        }

        private MethodHandle createMethodHandleForConstructor(Constructor constructor) {
            Object object = constructor.getDeclaringClass();
            Object object2 = MethodType.methodType(object, constructor.getParameterTypes());
            if (object == String.class) {
                object2 = new MethodHandleImpl(constructor.getArtMethod(), 2, (MethodType)object2);
            } else {
                object = Lookup.initMethodType((MethodType)object2);
                object2 = new Transformers.Construct(new MethodHandleImpl(constructor.getArtMethod(), 2, (MethodType)object), (MethodType)object2);
            }
            object = object2;
            if (constructor.isVarArgs()) {
                object = new Transformers.VarargsCollector((MethodHandle)object2);
            }
            return object;
        }

        private MethodHandle findAccessor(Class<?> class_, String string, Class<?> class_2, int n) throws NoSuchFieldException, IllegalAccessException {
            return this.findAccessor(this.findFieldOfType(class_, string, class_2), class_, class_2, n, true);
        }

        private MethodHandle findAccessor(Field object, Class<?> serializable, Class<?> class_, int n, boolean bl) throws IllegalAccessException {
            boolean bl2 = n == 10 || n == 12;
            boolean bl3 = n == 11 || n == 12;
            this.commonFieldChecks((Field)object, (Class<?>)serializable, class_, bl3, bl);
            if (bl) {
                int n2 = ((Field)object).getModifiers();
                if (bl2 && Modifier.isFinal(n2)) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Field ");
                    ((StringBuilder)serializable).append(object);
                    ((StringBuilder)serializable).append(" is final");
                    throw new IllegalAccessException(((StringBuilder)serializable).toString());
                }
            }
            switch (n) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid kind ");
                    ((StringBuilder)object).append(n);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                case 12: {
                    serializable = MethodType.methodType(Void.TYPE, class_);
                    break;
                }
                case 11: {
                    serializable = MethodType.methodType(class_);
                    break;
                }
                case 10: {
                    serializable = MethodType.methodType(Void.TYPE, serializable, new Class[]{class_});
                    break;
                }
                case 9: {
                    serializable = MethodType.methodType(class_, serializable);
                }
            }
            return new MethodHandleImpl(((Field)object).getArtField(), n, (MethodType)serializable);
        }

        private Field findFieldOfType(Class<?> class_, String string, Class<?> class_2) throws NoSuchFieldException {
            Field field;
            Field field2 = null;
            AnnotatedElement annotatedElement = class_;
            do {
                field = field2;
                if (annotatedElement == null) break;
                try {
                    field = annotatedElement.getDeclaredField(string);
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    annotatedElement = annotatedElement.getSuperclass();
                    continue;
                }
                break;
            } while (true);
            annotatedElement = field;
            if (field == null) {
                annotatedElement = class_.getDeclaredField(string);
            }
            if (((Field)annotatedElement).getType() == class_2) {
                return annotatedElement;
            }
            throw new NoSuchFieldException(string);
        }

        private MethodHandle findSpecial(Method object, MethodType serializable, Class<?> class_, Class<?> class_2) throws IllegalAccessException {
            if (!Modifier.isStatic(((Method)object).getModifiers())) {
                if (Modifier.isPrivate(((Method)object).getModifiers())) {
                    if (class_ == this.lookupClass()) {
                        return Lookup.createMethodHandle((Method)object, 2, ((MethodType)serializable).insertParameterTypes(0, class_));
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("no private access for invokespecial : ");
                    ((StringBuilder)object).append(class_);
                    ((StringBuilder)object).append(", from");
                    ((StringBuilder)object).append(this);
                    throw new IllegalAccessException(((StringBuilder)object).toString());
                }
                if (((Method)object).getDeclaringClass().isAssignableFrom(class_2)) {
                    return Lookup.createMethodHandle((Method)object, 1, ((MethodType)serializable).insertParameterTypes(0, class_2));
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(class_);
                ((StringBuilder)object).append("is not assignable from ");
                ((StringBuilder)object).append(class_2);
                throw new IllegalAccessException(((StringBuilder)object).toString());
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("expected a non-static method:");
            ((StringBuilder)serializable).append(object);
            throw new IllegalAccessException(((StringBuilder)serializable).toString());
        }

        private MethodHandle findVirtualForMH(String string, MethodType methodType) {
            if ("invoke".equals(string)) {
                return MethodHandles.invoker(methodType);
            }
            if ("invokeExact".equals(string)) {
                return MethodHandles.exactInvoker(methodType);
            }
            return null;
        }

        private MethodHandle findVirtualForVH(String object, MethodType methodType) {
            try {
                object = VarHandle.AccessMode.valueFromMethodName(object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
            return MethodHandles.varHandleInvoker((VarHandle.AccessMode)((Object)object), methodType);
        }

        private static int fixmods(int n) {
            if ((n &= 7) == 0) {
                n = 8;
            }
            return n;
        }

        private boolean hasPrivateAccess() {
            boolean bl = (this.allowedModes & 2) != 0;
            return bl;
        }

        private static MethodType initMethodType(MethodType methodType) {
            Class[] arrclass = new Class[methodType.ptypes().length + 1];
            arrclass[0] = methodType.rtype();
            System.arraycopy(methodType.ptypes(), 0, arrclass, 1, methodType.ptypes().length);
            return MethodType.methodType(Void.TYPE, arrclass);
        }

        private void throwMakeAccessException(String charSequence, Object object) throws IllegalAccessException {
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append(": ");
            charSequence2.append(this.toString());
            charSequence2 = charSequence2.toString();
            charSequence = charSequence2;
            if (object != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append(", from ");
                ((StringBuilder)charSequence).append(object);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            throw new IllegalAccessException((String)charSequence);
        }

        String accessFailedMessage(Class<?> class_, Class<?> class_2, int n) {
            boolean bl = Modifier.isPublic(class_2.getModifiers());
            boolean bl2 = true;
            boolean bl3 = bl && (class_2 == class_ || Modifier.isPublic(class_.getModifiers()));
            boolean bl4 = bl3;
            if (!bl3) {
                bl4 = bl3;
                if ((this.allowedModes & 8) != 0) {
                    bl3 = VerifyAccess.isClassAccessible(class_2, this.lookupClass(), 15) && (class_2 == class_ || VerifyAccess.isClassAccessible(class_, this.lookupClass(), 15)) ? bl2 : false;
                    bl4 = bl3;
                }
            }
            if (!bl4) {
                return "class is not public";
            }
            if (Modifier.isPublic(n)) {
                return "access to public member failed";
            }
            if (Modifier.isPrivate(n)) {
                return "member is private";
            }
            if (Modifier.isProtected(n)) {
                return "member is protected";
            }
            return "member is private to package";
        }

        public MethodHandle bind(Object object, String object2, MethodType object3) throws NoSuchMethodException, IllegalAccessException {
            object3 = this.findVirtual(object.getClass(), (String)object2, (MethodType)object3);
            object2 = ((MethodHandle)object3).bindTo(object);
            MethodType methodType = ((MethodHandle)object2).type();
            object = object2;
            if (((MethodHandle)object3).isVarargsCollector()) {
                object = ((MethodHandle)object2).asVarargsCollector(methodType.parameterType(methodType.parameterCount() - 1));
            }
            return object;
        }

        void checkAccess(Class<?> class_, Class<?> class_2, int n, String string) throws IllegalAccessException {
            int n2 = this.allowedModes;
            int n3 = n;
            if (Modifier.isProtected(n)) {
                n3 = n;
                if (class_2 == Object.class) {
                    n3 = n;
                    if ("clone".equals(string)) {
                        n3 = n;
                        if (class_.isArray()) {
                            n3 = n ^ 5;
                        }
                    }
                }
            }
            n = n3;
            if (Modifier.isProtected(n3)) {
                n = n3;
                if (Modifier.isConstructor(n3)) {
                    n = n3 ^ 4;
                }
            }
            if (Modifier.isPublic(n) && Modifier.isPublic(class_.getModifiers()) && n2 != 0) {
                return;
            }
            n3 = Lookup.fixmods(n);
            if ((n3 & n2) != 0 ? VerifyAccess.isMemberAccessible(class_, class_2, n, this.lookupClass(), n2) : (n3 & 4) != 0 && (n2 & 8) != 0 && VerifyAccess.isSamePackage(class_2, this.lookupClass())) {
                return;
            }
            this.throwMakeAccessException(this.accessFailedMessage(class_, class_2, n), this);
        }

        public MethodHandle findConstructor(Class<?> serializable, MethodType serializable2) throws NoSuchMethodException, IllegalAccessException {
            if (!((Class)serializable).isArray()) {
                Constructor<?> constructor = ((Class)serializable).getDeclaredConstructor(((MethodType)serializable2).ptypes());
                if (constructor != null) {
                    this.checkAccess((Class<?>)serializable, constructor.getDeclaringClass(), constructor.getModifiers(), constructor.getName());
                    return this.createMethodHandleForConstructor(constructor);
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("No constructor for ");
                ((StringBuilder)serializable).append(constructor.getDeclaringClass());
                ((StringBuilder)serializable).append(" matching ");
                ((StringBuilder)serializable).append(serializable2);
                throw new NoSuchMethodException(((StringBuilder)serializable).toString());
            }
            serializable2 = new StringBuilder();
            ((StringBuilder)serializable2).append("no constructor for array class: ");
            ((StringBuilder)serializable2).append(((Class)serializable).getName());
            throw new NoSuchMethodException(((StringBuilder)serializable2).toString());
        }

        public MethodHandle findGetter(Class<?> class_, String string, Class<?> class_2) throws NoSuchFieldException, IllegalAccessException {
            return this.findAccessor(class_, string, class_2, 9);
        }

        public MethodHandle findSetter(Class<?> class_, String string, Class<?> class_2) throws NoSuchFieldException, IllegalAccessException {
            return this.findAccessor(class_, string, class_2, 10);
        }

        public MethodHandle findSpecial(Class<?> serializable, String object, MethodType methodType, Class<?> class_) throws NoSuchMethodException, IllegalAccessException {
            if (class_ != null) {
                if (methodType != null) {
                    if (object != null) {
                        if (serializable != null) {
                            this.checkSpecialCaller(class_);
                            if (!((String)object).startsWith("<")) {
                                object = ((Class)serializable).getDeclaredMethod((String)object, methodType.ptypes());
                                this.checkReturnType((Method)object, methodType);
                                return this.findSpecial((Method)object, methodType, (Class<?>)serializable, class_);
                            }
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append((String)object);
                            ((StringBuilder)serializable).append(" is not a valid method name.");
                            throw new NoSuchMethodException(((StringBuilder)serializable).toString());
                        }
                        throw new NullPointerException("ref == null");
                    }
                    throw new NullPointerException("name == null");
                }
                throw new NullPointerException("type == null");
            }
            throw new NullPointerException("specialCaller == null");
        }

        public MethodHandle findStatic(Class<?> serializable, String object, MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            int n = ((Method)(object = ((Class)serializable).getDeclaredMethod((String)object, methodType.ptypes()))).getModifiers();
            if (Modifier.isStatic(n)) {
                this.checkReturnType((Method)object, methodType);
                this.checkAccess((Class<?>)serializable, ((Method)object).getDeclaringClass(), n, ((Method)object).getName());
                return Lookup.createMethodHandle((Method)object, 3, methodType);
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Method");
            ((StringBuilder)serializable).append(object);
            ((StringBuilder)serializable).append(" is not static");
            throw new IllegalAccessException(((StringBuilder)serializable).toString());
        }

        public MethodHandle findStaticGetter(Class<?> class_, String string, Class<?> class_2) throws NoSuchFieldException, IllegalAccessException {
            return this.findAccessor(class_, string, class_2, 11);
        }

        public MethodHandle findStaticSetter(Class<?> class_, String string, Class<?> class_2) throws NoSuchFieldException, IllegalAccessException {
            return this.findAccessor(class_, string, class_2, 12);
        }

        public VarHandle findStaticVarHandle(Class<?> class_, String object, Class<?> class_2) throws NoSuchFieldException, IllegalAccessException {
            object = this.findFieldOfType(class_, (String)object, class_2);
            this.commonFieldChecks((Field)object, class_, class_2, true, true);
            return FieldVarHandle.create((Field)object);
        }

        public VarHandle findVarHandle(Class<?> class_, String object, Class<?> class_2) throws NoSuchFieldException, IllegalAccessException {
            object = this.findFieldOfType(class_, (String)object, class_2);
            this.commonFieldChecks((Field)object, class_, class_2, false, true);
            return FieldVarHandle.create((Field)object);
        }

        public MethodHandle findVirtual(Class<?> serializable, String string, MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            Object object;
            if (serializable == MethodHandle.class ? (object = this.findVirtualForMH(string, methodType)) != null : serializable == VarHandle.class && (object = this.findVirtualForVH(string, methodType)) != null) {
                return object;
            }
            object = ((Class)serializable).getInstanceMethod(string, methodType.ptypes());
            if (object == null) {
                try {
                    object = ((Class)serializable).getDeclaredMethod(string, methodType.ptypes());
                    if (Modifier.isStatic(((Method)object).getModifiers())) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Method");
                        ((StringBuilder)serializable).append(object);
                        ((StringBuilder)serializable).append(" is static");
                        IllegalAccessException illegalAccessException = new IllegalAccessException(((StringBuilder)serializable).toString());
                        throw illegalAccessException;
                    }
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append(string);
                ((StringBuilder)serializable).append(" ");
                ((StringBuilder)serializable).append(Arrays.toString(methodType.ptypes()));
                throw new NoSuchMethodException(((StringBuilder)serializable).toString());
            }
            this.checkReturnType((Method)object, methodType);
            this.checkAccess((Class<?>)serializable, ((Method)object).getDeclaringClass(), ((Method)object).getModifiers(), ((Method)object).getName());
            return Lookup.createMethodHandle((Method)object, 0, methodType.insertParameterTypes(0, new Class[]{serializable}));
        }

        public Lookup in(Class<?> class_) {
            int n;
            class_.getClass();
            Class<?> class_2 = this.lookupClass;
            if (class_ == class_2) {
                return this;
            }
            int n2 = n = this.allowedModes & 11;
            if ((n & 8) != 0) {
                n2 = n;
                if (!VerifyAccess.isSamePackage(class_2, class_)) {
                    n2 = n & -11;
                }
            }
            n = n2;
            if ((n2 & 2) != 0) {
                n = n2;
                if (!VerifyAccess.isSamePackageMember(this.lookupClass, class_)) {
                    n = n2 & -3;
                }
            }
            n2 = n;
            if ((n & 1) != 0) {
                n2 = n;
                if (!VerifyAccess.isClassAccessible(class_, this.lookupClass, this.allowedModes)) {
                    n2 = 0;
                }
            }
            Lookup.checkUnprivilegedlookupClass(class_, n2);
            return new Lookup(class_, n2);
        }

        public Class<?> lookupClass() {
            return this.lookupClass;
        }

        public int lookupModes() {
            return this.allowedModes & 15;
        }

        public MethodHandleInfo revealDirect(MethodHandle object) {
            object = MethodHandles.getMethodHandleImpl((MethodHandle)object).reveal();
            try {
                this.checkAccess(this.lookupClass(), object.getDeclaringClass(), object.getModifiers(), object.getName());
                return object;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalArgumentException("Unable to access memeber.", illegalAccessException);
            }
        }

        public String toString() {
            String string = this.lookupClass.getName();
            int n = this.allowedModes;
            if (n != 0) {
                if (n != 1) {
                    if (n != 9) {
                        if (n != 11) {
                            if (n != 15) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(string);
                                stringBuilder.append("/");
                                stringBuilder.append(Integer.toHexString(this.allowedModes));
                                string = stringBuilder.toString();
                                return string;
                            }
                            return string;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(string);
                        stringBuilder.append("/private");
                        return stringBuilder.toString();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string);
                    stringBuilder.append("/package");
                    return stringBuilder.toString();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("/public");
                return stringBuilder.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("/noaccess");
            return stringBuilder.toString();
        }

        public MethodHandle unreflect(Method method) throws IllegalAccessException {
            if (method != null) {
                MethodType methodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
                if (!method.isAccessible()) {
                    this.checkAccess(method.getDeclaringClass(), method.getDeclaringClass(), method.getModifiers(), method.getName());
                }
                if (Modifier.isStatic(method.getModifiers())) {
                    return Lookup.createMethodHandle(method, 3, methodType);
                }
                return Lookup.createMethodHandle(method, 0, methodType.insertParameterTypes(0, method.getDeclaringClass()));
            }
            throw new NullPointerException("m == null");
        }

        public MethodHandle unreflectConstructor(Constructor<?> constructor) throws IllegalAccessException {
            if (constructor != null) {
                if (!constructor.isAccessible()) {
                    this.checkAccess(constructor.getDeclaringClass(), constructor.getDeclaringClass(), constructor.getModifiers(), constructor.getName());
                }
                return this.createMethodHandleForConstructor(constructor);
            }
            throw new NullPointerException("c == null");
        }

        public MethodHandle unreflectGetter(Field field) throws IllegalAccessException {
            Class<?> class_ = field.getDeclaringClass();
            Class<?> class_2 = field.getType();
            int n = Modifier.isStatic(field.getModifiers()) ? 11 : 9;
            return this.findAccessor(field, class_, class_2, n, field.isAccessible() ^ true);
        }

        public MethodHandle unreflectSetter(Field field) throws IllegalAccessException {
            Class<?> class_ = field.getDeclaringClass();
            Class<?> class_2 = field.getType();
            int n = Modifier.isStatic(field.getModifiers()) ? 12 : 10;
            return this.findAccessor(field, class_, class_2, n, field.isAccessible() ^ true);
        }

        public MethodHandle unreflectSpecial(Method method, Class<?> class_) throws IllegalAccessException {
            if (method != null) {
                if (class_ != null) {
                    if (!method.isAccessible()) {
                        this.checkSpecialCaller(class_);
                    }
                    return this.findSpecial(method, MethodType.methodType(method.getReturnType(), method.getParameterTypes()), method.getDeclaringClass(), class_);
                }
                throw new NullPointerException("specialCaller == null");
            }
            throw new NullPointerException("m == null");
        }

        public VarHandle unreflectVarHandle(Field field) throws IllegalAccessException {
            boolean bl = Modifier.isStatic(field.getModifiers());
            this.commonFieldChecks(field, field.getDeclaringClass(), field.getType(), bl, true);
            return FieldVarHandle.create((Field)field);
        }
    }

}

