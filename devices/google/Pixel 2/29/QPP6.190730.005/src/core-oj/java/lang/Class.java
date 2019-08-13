/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  dalvik.system.ClassExt
 *  libcore.reflect.GenericSignatureParser
 *  libcore.reflect.ListOfTypes
 *  libcore.reflect.Types
 *  libcore.util.BasicLruCache
 *  libcore.util.CollectionUtils
 *  libcore.util.EmptyArray
 */
package java.lang;

import dalvik.annotation.optimization.FastNative;
import dalvik.system.ClassExt;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import libcore.reflect.GenericSignatureParser;
import libcore.reflect.ListOfTypes;
import libcore.reflect.Types;
import libcore.util.BasicLruCache;
import libcore.util.CollectionUtils;
import libcore.util.EmptyArray;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public final class Class<T>
implements Serializable,
GenericDeclaration,
Type,
AnnotatedElement {
    private static final int ANNOTATION = 8192;
    private static final int ENUM = 16384;
    private static final int FINALIZABLE = Integer.MIN_VALUE;
    private static final int SYNTHETIC = 4096;
    private static final long serialVersionUID = 3206093459760846163L;
    private transient int accessFlags;
    private transient int classFlags;
    private transient ClassLoader classLoader;
    private transient int classSize;
    private transient int clinitThreadId;
    private transient Class<?> componentType;
    private transient short copiedMethodsOffset;
    private transient Object dexCache;
    private transient int dexClassDefIndex;
    private volatile transient int dexTypeIndex;
    private transient ClassExt extData;
    private transient long iFields;
    private transient Object[] ifTable;
    private transient long methods;
    private transient String name;
    private transient int numReferenceInstanceFields;
    private transient int numReferenceStaticFields;
    private transient int objectSize;
    private transient int objectSizeAllocFastPath;
    private transient int primitiveType;
    private transient int referenceInstanceOffsets;
    private transient long sFields;
    private transient int status;
    private transient Class<? super T> superClass;
    private transient short virtualMethodsOffset;
    private transient Object vtable;

    private Class() {
    }

    private String cannotCastMsg(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot cast ");
        stringBuilder.append(object.getClass().getName());
        stringBuilder.append(" to ");
        stringBuilder.append(this.getName());
        return stringBuilder.toString();
    }

    @FastNative
    static native Class<?> classForName(String var0, boolean var1, ClassLoader var2) throws ClassNotFoundException;

    private boolean classNameImpliesTopLevel() {
        return this.getName().contains("$") ^ true;
    }

    private Method findInterfaceMethod(String string, Class<?>[] arrclass) {
        Object[] arrobject = this.ifTable;
        if (arrobject != null) {
            for (int i = arrobject.length - 2; i >= 0; i -= 2) {
                Method method = ((Class)arrobject[i]).getPublicMethodRecursive(string, arrclass);
                if (method == null || !Modifier.isPublic(method.getAccessFlags())) continue;
                return method;
            }
        }
        return null;
    }

    @CallerSensitive
    public static Class<?> forName(String string) throws ClassNotFoundException {
        return Class.forName(string, true, ClassLoader.getClassLoader(Reflection.getCallerClass()));
    }

    @CallerSensitive
    public static Class<?> forName(String object, boolean bl, ClassLoader object2) throws ClassNotFoundException {
        ClassLoader classLoader = object2;
        if (object2 == null) {
            classLoader = BootClassLoader.getInstance();
        }
        try {
            object = Class.classForName((String)object, bl, classLoader);
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            object2 = classNotFoundException.getCause();
            if (object2 instanceof LinkageError) {
                throw (LinkageError)object2;
            }
            throw classNotFoundException;
        }
    }

    private Constructor<T> getConstructor0(Class<?>[] object, int n) throws NoSuchMethodException {
        Object[] arrobject = object;
        if (object == null) {
            arrobject = EmptyArray.CLASS;
        }
        int n2 = arrobject.length;
        for (int i = 0; i < n2; ++i) {
            if (arrobject[i] != null) {
                continue;
            }
            throw new NoSuchMethodException("parameter type is null");
        }
        object = this.getDeclaredConstructorInternal((Class<?>[])arrobject);
        if (object != null && (n != 0 || Modifier.isPublic(((Executable)object).getAccessFlags()))) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(".<init> ");
        ((StringBuilder)object).append(Arrays.toString(arrobject));
        throw new NoSuchMethodException(((StringBuilder)object).toString());
    }

    @FastNative
    private native Constructor<T> getDeclaredConstructorInternal(Class<?>[] var1);

    @FastNative
    private native Constructor<?>[] getDeclaredConstructorsInternal(boolean var1);

    @FastNative
    private native Method getDeclaredMethodInternal(String var1, Class<?>[] var2);

    @FastNative
    private native Constructor<?> getEnclosingConstructorNative();

    @FastNative
    private native Method getEnclosingMethodNative();

    @FastNative
    private native int getInnerClassFlags(int var1);

    @FastNative
    private native String getInnerClassName();

    @FastNative
    private native Class<?>[] getInterfacesInternal();

    private Method getMethod(String string, Class<?>[] object, boolean bl) throws NoSuchMethodException {
        if (string != null) {
            Object[] arrobject = object;
            if (object == null) {
                arrobject = EmptyArray.CLASS;
            }
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                if (arrobject[i] != null) {
                    continue;
                }
                throw new NoSuchMethodException("parameter type is null");
            }
            object = bl ? this.getPublicMethodRecursive(string, (Class<?>[])arrobject) : this.getDeclaredMethodInternal(string, (Class<?>[])arrobject);
            if (object != null && (!bl || Modifier.isPublic(((Executable)object).getAccessFlags()))) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(this.getName());
            ((StringBuilder)object).append(".");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(Arrays.toString(arrobject));
            throw new NoSuchMethodException(((StringBuilder)object).toString());
        }
        throw new NullPointerException("name == null");
    }

    @FastNative
    private native String getNameNative();

    @FastNative
    static native Class<?> getPrimitiveClass(String var0);

    @FastNative
    private native Field[] getPublicDeclaredFields();

    @FastNative
    private native Field getPublicFieldRecursive(String var1);

    private void getPublicFieldsRecursive(List<Field> list) {
        Object object = this;
        while (object != null) {
            Collections.addAll(list, object.getPublicDeclaredFields());
            object = object.superClass;
        }
        object = this.ifTable;
        if (object != null) {
            for (int i = 0; i < ((Object[])object).length; i += 2) {
                Collections.addAll(list, ((Class)object[i]).getPublicDeclaredFields());
            }
        }
    }

    private Method getPublicMethodRecursive(String string, Class<?>[] arrclass) {
        for (Class<T> class_ = this; class_ != null; class_ = class_.getSuperclass()) {
            Method method = class_.getDeclaredMethodInternal(string, arrclass);
            if (method == null || !Modifier.isPublic(method.getAccessFlags())) continue;
            return method;
        }
        return this.findInterfaceMethod(string, arrclass);
    }

    private void getPublicMethodsInternal(List<Method> list) {
        Object object;
        Collections.addAll(list, this.getDeclaredMethodsUnchecked(true));
        if (!this.isInterface()) {
            object = this.superClass;
            while (object != null) {
                Collections.addAll(list, object.getDeclaredMethodsUnchecked(true));
                object = object.superClass;
            }
        }
        if ((object = this.ifTable) != null) {
            for (int i = 0; i < ((Object[])object).length; i += 2) {
                Collections.addAll(list, ((Class)object[i]).getDeclaredMethodsUnchecked(true));
            }
        }
    }

    @FastNative
    private native String[] getSignatureAnnotation();

    private String getSignatureAttribute() {
        String[] arrstring = this.getSignatureAnnotation();
        if (arrstring == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrstring[i]);
        }
        return stringBuilder.toString();
    }

    @FastNative
    private native boolean isDeclaredAnnotationPresent(Class<? extends Annotation> var1);

    private boolean isLocalOrAnonymousClass() {
        boolean bl = this.isLocalClass() || this.isAnonymousClass();
        return bl;
    }

    private String resolveName(String string) {
        Object object;
        if (string == null) {
            return string;
        }
        if (!string.startsWith("/")) {
            object = this;
            while (((Class)object).isArray()) {
                object = ((Class)object).getComponentType();
            }
            String string2 = ((Class)object).getName();
            int n = string2.lastIndexOf(46);
            object = string;
            if (n != -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2.substring(0, n).replace('.', '/'));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(string);
                object = ((StringBuilder)object).toString();
            }
        } else {
            object = string.substring(1);
        }
        return object;
    }

    public <U> Class<? extends U> asSubclass(Class<U> class_) {
        if (class_.isAssignableFrom(this)) {
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toString());
        stringBuilder.append(" cannot be cast to ");
        stringBuilder.append(class_.getName());
        throw new ClassCastException(stringBuilder.toString());
    }

    public T cast(Object object) {
        if (object != null && !this.isInstance(object)) {
            throw new ClassCastException(this.cannotCastMsg(object));
        }
        return (T)object;
    }

    public boolean desiredAssertionStatus() {
        return false;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        Objects.requireNonNull(class_);
        Object object = this.getDeclaredAnnotation(class_);
        if (object != null) {
            return (A)object;
        }
        if (Class.super.isDeclaredAnnotationPresent(Inherited.class)) {
            for (object = this.getSuperclass(); object != null; object = object.getSuperclass()) {
                A a = ((Class)object).getDeclaredAnnotation(class_);
                if (a == null) continue;
                return a;
            }
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Annotation[] getAnnotations() {
        void var5_7;
        HashMap<Class<? extends Annotation>, Annotation> hashMap = new HashMap<Class<? extends Annotation>, Annotation>();
        for (Annotation object2 : this.getDeclaredAnnotations()) {
            hashMap.put(object2.annotationType(), object2);
        }
        Class<T> class_ = this.getSuperclass();
        while (var5_7 != null) {
            for (Annotation annotation : var5_7.getDeclaredAnnotations()) {
                Class<? extends Annotation> class_2 = annotation.annotationType();
                if (hashMap.containsKey(class_2) || !Class.super.isDeclaredAnnotationPresent(Inherited.class)) continue;
                hashMap.put(class_2, annotation);
            }
            Class<T> class_3 = var5_7.getSuperclass();
        }
        Collection<V> collection = hashMap.values();
        return collection.toArray(new Annotation[collection.size()]);
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> class_) {
        Object object = GenericDeclaration.super.getAnnotationsByType(class_);
        if (((Annotation[])object).length != 0) {
            return object;
        }
        if (Class.super.isDeclaredAnnotationPresent(Inherited.class) && (object = this.getSuperclass()) != null) {
            return object.getAnnotationsByType(class_);
        }
        return (Annotation[])Array.newInstance(class_, 0);
    }

    public String getCanonicalName() {
        if (this.isArray()) {
            String string = this.getComponentType().getCanonicalName();
            if (string != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("[]");
                return stringBuilder.toString();
            }
            return null;
        }
        if (this.isLocalOrAnonymousClass()) {
            return null;
        }
        Serializable serializable = this.getEnclosingClass();
        if (serializable == null) {
            return this.getName();
        }
        String string = ((Class)serializable).getCanonicalName();
        if (string == null) {
            return null;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(".");
        ((StringBuilder)serializable).append(this.getSimpleName());
        return ((StringBuilder)serializable).toString();
    }

    public ClassLoader getClassLoader() {
        ClassLoader classLoader;
        if (this.isPrimitive()) {
            return null;
        }
        ClassLoader classLoader2 = classLoader = this.classLoader;
        if (classLoader == null) {
            classLoader2 = BootClassLoader.getInstance();
        }
        return classLoader2;
    }

    @CallerSensitive
    public Class<?>[] getClasses() {
        ArrayList<Class<?>> arrayList = new ArrayList<Class<?>>();
        Class<T> class_ = this;
        while (class_ != null) {
            for (Class<?> class_2 : class_.getDeclaredClasses()) {
                if (!Modifier.isPublic(class_2.getModifiers())) continue;
                arrayList.add(class_2);
            }
            class_ = class_.superClass;
        }
        return arrayList.toArray(new Class[arrayList.size()]);
    }

    public Class<?> getComponentType() {
        return this.componentType;
    }

    public Constructor<T> getConstructor(Class<?> ... arrclass) throws NoSuchMethodException, SecurityException {
        return this.getConstructor0(arrclass, 0);
    }

    @CallerSensitive
    public Constructor<?>[] getConstructors() throws SecurityException {
        return this.getDeclaredConstructorsInternal(true);
    }

    @FastNative
    public native <A extends Annotation> A getDeclaredAnnotation(Class<A> var1);

    @FastNative
    @Override
    public native Annotation[] getDeclaredAnnotations();

    @FastNative
    public native Class<?>[] getDeclaredClasses();

    @CallerSensitive
    public Constructor<T> getDeclaredConstructor(Class<?> ... arrclass) throws NoSuchMethodException, SecurityException {
        return this.getConstructor0(arrclass, 1);
    }

    public Constructor<?>[] getDeclaredConstructors() throws SecurityException {
        return this.getDeclaredConstructorsInternal(false);
    }

    @FastNative
    public native Field getDeclaredField(String var1) throws NoSuchFieldException;

    @FastNative
    public native Field[] getDeclaredFields();

    @FastNative
    public native Field[] getDeclaredFieldsUnchecked(boolean var1);

    @CallerSensitive
    public Method getDeclaredMethod(String string, Class<?> ... arrclass) throws NoSuchMethodException, SecurityException {
        return this.getMethod(string, arrclass, false);
    }

    public Method[] getDeclaredMethods() throws SecurityException {
        Method[] arrmethod = this.getDeclaredMethodsUnchecked(false);
        int n = arrmethod.length;
        for (int i = 0; i < n; ++i) {
            Method method = arrmethod[i];
            method.getReturnType();
            method.getParameterTypes();
        }
        return arrmethod;
    }

    @FastNative
    public native Method[] getDeclaredMethodsUnchecked(boolean var1);

    @FastNative
    public native Class<?> getDeclaringClass();

    @FastNative
    public native Class<?> getEnclosingClass();

    public Constructor<?> getEnclosingConstructor() {
        if (this.classNameImpliesTopLevel()) {
            return null;
        }
        return this.getEnclosingConstructorNative();
    }

    public Method getEnclosingMethod() {
        if (this.classNameImpliesTopLevel()) {
            return null;
        }
        return this.getEnclosingMethodNative();
    }

    public T[] getEnumConstants() {
        Object object = this.getEnumConstantsShared();
        object = object != null ? (Object[])object.clone() : null;
        return object;
    }

    public T[] getEnumConstantsShared() {
        if (!this.isEnum()) {
            return null;
        }
        return Enum.getSharedConstants((Class)this);
    }

    public Field getField(String string) throws NoSuchFieldException {
        if (string != null) {
            Field field = this.getPublicFieldRecursive(string);
            if (field != null) {
                return field;
            }
            throw new NoSuchFieldException(string);
        }
        throw new NullPointerException("name == null");
    }

    @CallerSensitive
    public Field[] getFields() throws SecurityException {
        ArrayList<Field> arrayList = new ArrayList<Field>();
        this.getPublicFieldsRecursive(arrayList);
        return arrayList.toArray(new Field[arrayList.size()]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Type[] getGenericInterfaces() {
        Object object;
        BasicLruCache basicLruCache = Caches.genericInterfaces;
        synchronized (basicLruCache) {
            GenericSignatureParser genericSignatureParser = (GenericSignatureParser)Caches.genericInterfaces.get((Object)this);
            object = genericSignatureParser;
            if (genericSignatureParser == null) {
                object = this.getSignatureAttribute();
                if (object == null) {
                    object = this.getInterfaces();
                } else {
                    genericSignatureParser = new GenericSignatureParser(this.getClassLoader());
                    genericSignatureParser.parseForClass((GenericDeclaration)this, (String)object);
                    object = Types.getTypeArray((ListOfTypes)genericSignatureParser.interfaceTypes, (boolean)false);
                }
                Caches.genericInterfaces.put((Object)this, object);
            }
        }
        if (((Type[])object).length != 0) return (Type[])object.clone();
        return object;
    }

    public Type getGenericSuperclass() {
        Object object = this.getSuperclass();
        if (object == null) {
            return null;
        }
        String string = this.getSignatureAttribute();
        if (string != null) {
            object = new GenericSignatureParser(this.getClassLoader());
            object.parseForClass((GenericDeclaration)this, string);
            object = object.superclassType;
        }
        return Types.getType(object);
    }

    public Method getInstanceMethod(String string, Class<?>[] arrclass) throws NoSuchMethodException, IllegalAccessException {
        for (Class<T> class_ = this; class_ != null; class_ = class_.getSuperclass()) {
            Method method = class_.getDeclaredMethodInternal(string, arrclass);
            if (method == null || Modifier.isStatic(method.getModifiers())) continue;
            return method;
        }
        return this.findInterfaceMethod(string, arrclass);
    }

    public Class<?>[] getInterfaces() {
        if (this.isArray()) {
            return new Class[]{Cloneable.class, Serializable.class};
        }
        Class<?>[] arrclass = this.getInterfacesInternal();
        if (arrclass == null) {
            return EmptyArray.CLASS;
        }
        return arrclass;
    }

    @CallerSensitive
    public Method getMethod(String string, Class<?> ... arrclass) throws NoSuchMethodException, SecurityException {
        return this.getMethod(string, arrclass, true);
    }

    @CallerSensitive
    public Method[] getMethods() throws SecurityException {
        ArrayList<Method> arrayList = new ArrayList<Method>();
        this.getPublicMethodsInternal(arrayList);
        CollectionUtils.removeDuplicates(arrayList, Method.ORDER_BY_SIGNATURE);
        return arrayList.toArray(new Method[arrayList.size()]);
    }

    public int getModifiers() {
        if (this.isArray()) {
            int n;
            int n2 = n = this.getComponentType().getModifiers();
            if ((n & 512) != 0) {
                n2 = n & -521;
            }
            return n2 | 1040;
        }
        return this.getInnerClassFlags(this.accessFlags & 65535) & 65535;
    }

    public String getName() {
        String string;
        String string2 = string = this.name;
        if (string == null) {
            string2 = string = this.getNameNative();
            this.name = string;
        }
        return string2;
    }

    public Package getPackage() {
        ClassLoader classLoader = this.getClassLoader();
        Package package_ = null;
        if (classLoader != null) {
            String string = this.getPackageName$();
            if (string != null) {
                package_ = classLoader.getPackage(string);
            }
            return package_;
        }
        return null;
    }

    public String getPackageName$() {
        String string = this.getName();
        int n = string.lastIndexOf(46);
        string = n == -1 ? null : string.substring(0, n);
        return string;
    }

    public ProtectionDomain getProtectionDomain() {
        return null;
    }

    public URL getResource(String object) {
        String string = this.resolveName((String)object);
        object = this.getClassLoader();
        if (object == null) {
            return ClassLoader.getSystemResource(string);
        }
        return ((ClassLoader)object).getResource(string);
    }

    public InputStream getResourceAsStream(String string) {
        string = this.resolveName(string);
        ClassLoader classLoader = this.getClassLoader();
        if (classLoader == null) {
            return ClassLoader.getSystemResourceAsStream(string);
        }
        return classLoader.getResourceAsStream(string);
    }

    public Object[] getSigners() {
        return null;
    }

    public String getSimpleName() {
        if (this.isArray()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getComponentType().getSimpleName());
            stringBuilder.append("[]");
            return stringBuilder.toString();
        }
        if (this.isAnonymousClass()) {
            return "";
        }
        if (!this.isMemberClass() && !this.isLocalClass()) {
            String string = this.getName();
            if (string.lastIndexOf(".") > 0) {
                return string.substring(string.lastIndexOf(".") + 1);
            }
            return string;
        }
        return this.getInnerClassName();
    }

    public Class<? super T> getSuperclass() {
        if (this.isInterface()) {
            return null;
        }
        return this.superClass;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String getTypeName() {
        if (!this.isArray()) return this.getName();
        Object object = this;
        int n = 0;
        while (((Class)object).isArray()) {
            ++n;
            object = ((Class)object).getComponentType();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((Class)object).getName());
        for (int i = 0; i < n; ++i) {
            stringBuilder.append("[]");
        }
        try {
            return stringBuilder.toString();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return this.getName();
    }

    public TypeVariable<Class<T>>[] getTypeParameters() {
        synchronized (this) {
            TypeVariable[] arrtypeVariable;
            block5 : {
                arrtypeVariable = this.getSignatureAttribute();
                if (arrtypeVariable != null) break block5;
                arrtypeVariable = EmptyArray.TYPE_VARIABLE;
                return arrtypeVariable;
            }
            GenericSignatureParser genericSignatureParser = new GenericSignatureParser(this.getClassLoader());
            genericSignatureParser.parseForClass((GenericDeclaration)this, (String)arrtypeVariable);
            arrtypeVariable = genericSignatureParser.formalTypeParameters;
            return arrtypeVariable;
        }
    }

    public boolean isAnnotation() {
        boolean bl = (this.getModifiers() & 8192) != 0;
        return bl;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> class_) {
        if (class_ != null) {
            if (this.isDeclaredAnnotationPresent(class_)) {
                return true;
            }
            if (Class.super.isDeclaredAnnotationPresent(Inherited.class)) {
                for (Class<T> class_2 = this.getSuperclass(); class_2 != null; class_2 = class_2.getSuperclass()) {
                    if (!Class.super.isDeclaredAnnotationPresent(class_)) continue;
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("annotationClass == null");
    }

    @FastNative
    public native boolean isAnonymousClass();

    public boolean isArray() {
        boolean bl = this.getComponentType() != null;
        return bl;
    }

    public boolean isAssignableFrom(Class<?> object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (this == Object.class) {
            return true ^ object.isPrimitive();
        }
        if (this.isArray()) {
            if (!object.isArray() || !this.componentType.isAssignableFrom(object.componentType)) {
                bl = false;
            }
            return bl;
        }
        if (this.isInterface()) {
            object = object.ifTable;
            if (object != null) {
                for (int i = 0; i < ((Object[])object).length; i += 2) {
                    if (object[i] != this) continue;
                    return true;
                }
            }
            return false;
        }
        if (!object.isInterface()) {
            Class<? super T> class_;
            while ((class_ = object.superClass) != null) {
                object = class_;
                if (class_ != this) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isEnum() {
        boolean bl = (this.getModifiers() & 16384) != 0 && this.getSuperclass() == Enum.class;
        return bl;
    }

    public boolean isFinalizable() {
        boolean bl = (this.getModifiers() & Integer.MIN_VALUE) != 0;
        return bl;
    }

    public boolean isInstance(Object object) {
        if (object == null) {
            return false;
        }
        return this.isAssignableFrom(object.getClass());
    }

    public boolean isInterface() {
        boolean bl = (this.accessFlags & 512) != 0;
        return bl;
    }

    public boolean isLocalClass() {
        boolean bl = (this.getEnclosingMethod() != null || this.getEnclosingConstructor() != null) && !this.isAnonymousClass();
        return bl;
    }

    public boolean isMemberClass() {
        boolean bl = this.getDeclaringClass() != null;
        return bl;
    }

    public boolean isPrimitive() {
        boolean bl = (this.primitiveType & 65535) != 0;
        return bl;
    }

    public boolean isProxy() {
        boolean bl = (this.accessFlags & 262144) != 0;
        return bl;
    }

    public boolean isSynthetic() {
        boolean bl = (this.getModifiers() & 4096) != 0;
        return bl;
    }

    @FastNative
    public native T newInstance() throws InstantiationException, IllegalAccessException;

    public String toGenericString() {
        if (this.isPrimitive()) {
            return this.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.getModifiers() & Modifier.classModifiers();
        if (n != 0) {
            stringBuilder.append(Modifier.toString(n));
            stringBuilder.append(' ');
        }
        if (this.isAnnotation()) {
            stringBuilder.append('@');
        }
        if (this.isInterface()) {
            stringBuilder.append("interface");
        } else if (this.isEnum()) {
            stringBuilder.append("enum");
        } else {
            stringBuilder.append("class");
        }
        stringBuilder.append(' ');
        stringBuilder.append(this.getName());
        TypeVariable<Class<T>>[] arrtypeVariable = this.getTypeParameters();
        if (arrtypeVariable.length > 0) {
            boolean bl = true;
            stringBuilder.append('<');
            for (TypeVariable<Class<T>> typeVariable : arrtypeVariable) {
                if (!bl) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(typeVariable.getTypeName());
                bl = false;
            }
            stringBuilder.append('>');
        }
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.isInterface() ? "interface " : (this.isPrimitive() ? "" : "class ");
        stringBuilder.append(string);
        stringBuilder.append(this.getName());
        return stringBuilder.toString();
    }

    private static class Caches {
        private static final BasicLruCache<Class, Type[]> genericInterfaces = new BasicLruCache(8);

        private Caches() {
        }
    }

}

