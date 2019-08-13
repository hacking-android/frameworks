/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.EmptyArray
 */
package java.lang.reflect;

import dalvik.annotation.optimization.FastNative;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.WeakCache;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import libcore.util.EmptyArray;
import sun.reflect.CallerSensitive;

public class Proxy
implements Serializable {
    private static final Comparator<Method> ORDER_BY_SIGNATURE_AND_SUBTYPE;
    private static final Class<?>[] constructorParams;
    private static final Object key0;
    private static final WeakCache<ClassLoader, Class<?>[], Class<?>> proxyClassCache;
    private static final long serialVersionUID = -2222568056686623797L;
    protected InvocationHandler h;

    static {
        constructorParams = new Class[]{InvocationHandler.class};
        proxyClassCache = new WeakCache(new KeyFactory(), new ProxyClassFactory());
        key0 = new Object();
        ORDER_BY_SIGNATURE_AND_SUBTYPE = new Comparator<Method>(){

            @Override
            public int compare(Method genericDeclaration, Method genericDeclaration2) {
                int n = Method.ORDER_BY_SIGNATURE.compare((Method)genericDeclaration, (Method)genericDeclaration2);
                if (n != 0) {
                    return n;
                }
                if ((genericDeclaration = ((Method)genericDeclaration).getDeclaringClass()) == (genericDeclaration2 = ((Method)genericDeclaration2).getDeclaringClass())) {
                    return 0;
                }
                if (((Class)genericDeclaration).isAssignableFrom((Class<?>)genericDeclaration2)) {
                    return 1;
                }
                if (((Class)genericDeclaration2).isAssignableFrom((Class<?>)genericDeclaration)) {
                    return -1;
                }
                return 0;
            }
        };
    }

    private Proxy() {
    }

    protected Proxy(InvocationHandler invocationHandler) {
        Objects.requireNonNull(invocationHandler);
        this.h = invocationHandler;
    }

    private static List<Class<?>[]> deduplicateAndGetExceptions(List<Method> list) {
        ArrayList<Class<?>[]> arrayList = new ArrayList<Class<?>[]>(list.size());
        int n = 0;
        while (n < list.size()) {
            Method method = list.get(n);
            Class<?>[] arrclass = method.getExceptionTypes();
            if (n > 0 && Method.ORDER_BY_SIGNATURE.compare(method, list.get(n - 1)) == 0) {
                arrayList.set(n - 1, Proxy.intersectExceptions((Class[])arrayList.get(n - 1), arrclass));
                list.remove(n);
                continue;
            }
            arrayList.add(arrclass);
            ++n;
        }
        return arrayList;
    }

    @FastNative
    private static native Class<?> generateProxy(String var0, Class<?>[] var1, ClassLoader var2, Method[] var3, Class<?>[][] var4);

    @CallerSensitive
    public static InvocationHandler getInvocationHandler(Object object) throws IllegalArgumentException {
        if (Proxy.isProxyClass(object.getClass())) {
            return ((Proxy)object).h;
        }
        throw new IllegalArgumentException("not a proxy instance");
    }

    private static List<Method> getMethods(Class<?>[] arrclass) {
        ArrayList<Method> arrayList = new ArrayList<Method>();
        try {
            arrayList.add(Object.class.getMethod("equals", Object.class));
            arrayList.add(Object.class.getMethod("hashCode", EmptyArray.CLASS));
            arrayList.add(Object.class.getMethod("toString", EmptyArray.CLASS));
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new AssertionError();
        }
        Proxy.getMethodsRecursive(arrclass, arrayList);
        return arrayList;
    }

    private static void getMethodsRecursive(Class<?>[] arrclass, List<Method> list) {
        for (Class<?> class_ : arrclass) {
            Proxy.getMethodsRecursive(class_.getInterfaces(), list);
            Collections.addAll(list, class_.getDeclaredMethods());
        }
    }

    @CallerSensitive
    public static Class<?> getProxyClass(ClassLoader classLoader, Class<?> ... arrclass) throws IllegalArgumentException {
        return Proxy.getProxyClass0(classLoader, arrclass);
    }

    private static Class<?> getProxyClass0(ClassLoader classLoader, Class<?> ... arrclass) {
        if (arrclass.length <= 65535) {
            return proxyClassCache.get(classLoader, arrclass);
        }
        throw new IllegalArgumentException("interface limit exceeded");
    }

    private static Class<?>[] intersectExceptions(Class<?>[] arrclass, Class<?>[] arrclass2) {
        if (arrclass.length != 0 && arrclass2.length != 0) {
            if (Arrays.equals(arrclass, arrclass2)) {
                return arrclass;
            }
            HashSet<Class<?>> hashSet = new HashSet<Class<?>>();
            for (Class<?> class_ : arrclass) {
                for (Class<?> class_2 : arrclass2) {
                    if (class_.isAssignableFrom(class_2)) {
                        hashSet.add(class_2);
                        continue;
                    }
                    if (!class_2.isAssignableFrom(class_)) continue;
                    hashSet.add(class_);
                }
            }
            return hashSet.toArray(new Class[hashSet.size()]);
        }
        return EmptyArray.CLASS;
    }

    private static Object invoke(Proxy proxy, Method method, Object[] arrobject) throws Throwable {
        return proxy.h.invoke(proxy, method, arrobject);
    }

    public static boolean isProxyClass(Class<?> class_) {
        boolean bl = Proxy.class.isAssignableFrom(class_) && proxyClassCache.containsValue(class_);
        return bl;
    }

    @CallerSensitive
    public static Object newProxyInstance(ClassLoader object, Class<?>[] object2, InvocationHandler invocationHandler) throws IllegalArgumentException {
        Objects.requireNonNull(invocationHandler);
        object = Proxy.getProxyClass0((ClassLoader)object, (Class[])object2.clone());
        try {
            object2 = ((Class)object).getConstructor(constructorParams);
            if (!Modifier.isPublic(((Class)object).getModifiers())) {
                ((AccessibleObject)object2).setAccessible(true);
            }
            object = ((Constructor)object2).newInstance(invocationHandler);
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new InternalError(noSuchMethodException.toString(), noSuchMethodException);
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            throw new InternalError(throwable.toString(), throwable);
        }
        catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            throw new InternalError(reflectiveOperationException.toString(), reflectiveOperationException);
        }
    }

    private static void validateReturnTypes(List<Method> object) {
        Method method = null;
        Object object2 = object.iterator();
        object = method;
        while (object2.hasNext()) {
            method = object2.next();
            if (object != null && ((Method)object).equalNameAndParameters(method)) {
                Class<?> class_ = method.getReturnType();
                Class<?> class_2 = ((Method)object).getReturnType();
                if (class_.isInterface() && class_2.isInterface()) continue;
                if (class_2.isAssignableFrom(class_)) {
                    object = method;
                    continue;
                }
                if (class_.isAssignableFrom(class_2)) continue;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("proxied interface methods have incompatible return types:\n  ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append("\n  ");
                ((StringBuilder)object2).append(method);
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            object = method;
        }
    }

    private static final class Key1
    extends WeakReference<Class<?>> {
        private final int hash;

        Key1(Class<?> class_) {
            super(class_);
            this.hash = class_.hashCode();
        }

        public boolean equals(Object object) {
            Class class_;
            boolean bl = this == object || object != null && object.getClass() == Key1.class && (class_ = (Class)this.get()) != null && class_ == ((Key1)object).get();
            return bl;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static final class Key2
    extends WeakReference<Class<?>> {
        private final int hash;
        private final WeakReference<Class<?>> ref2;

        Key2(Class<?> class_, Class<?> class_2) {
            super(class_);
            this.hash = class_.hashCode() * 31 + class_2.hashCode();
            this.ref2 = new WeakReference(class_2);
        }

        public boolean equals(Object object) {
            Class class_;
            boolean bl = this == object || object != null && object.getClass() == Key2.class && (class_ = (Class)this.get()) != null && class_ == ((Key2)object).get() && (class_ = (Class)this.ref2.get()) != null && class_ == ((Key2)object).ref2.get();
            return bl;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static final class KeyFactory
    implements BiFunction<ClassLoader, Class<?>[], Object> {
        private KeyFactory() {
        }

        @Override
        public Object apply(ClassLoader classLoader, Class<?>[] arrclass) {
            int n = arrclass.length;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        return new KeyX(arrclass);
                    }
                    return new Key2(arrclass[0], arrclass[1]);
                }
                return new Key1(arrclass[0]);
            }
            return key0;
        }
    }

    private static final class KeyX {
        private final int hash;
        private final WeakReference<Class<?>>[] refs;

        KeyX(Class<?>[] arrclass) {
            this.hash = Arrays.hashCode(arrclass);
            this.refs = new WeakReference[arrclass.length];
            for (int i = 0; i < arrclass.length; ++i) {
                this.refs[i] = new WeakReference(arrclass[i]);
            }
        }

        private static boolean equals(WeakReference<Class<?>>[] arrweakReference, WeakReference<Class<?>>[] arrweakReference2) {
            if (arrweakReference.length != arrweakReference2.length) {
                return false;
            }
            for (int i = 0; i < arrweakReference.length; ++i) {
                Class class_ = (Class)arrweakReference[i].get();
                if (class_ != null && class_ == arrweakReference2[i].get()) {
                    continue;
                }
                return false;
            }
            return true;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object.getClass() == KeyX.class && KeyX.equals(this.refs, ((KeyX)object).refs);
            return bl;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static final class ProxyClassFactory
    implements BiFunction<ClassLoader, Class<?>[], Class<?>> {
        private static final AtomicLong nextUniqueNumber = new AtomicLong();
        private static final String proxyClassNamePrefix = "$Proxy";

        private ProxyClassFactory() {
        }

        @Override
        public Class<?> apply(ClassLoader object, Class<?>[] arrclass) {
            Object object2;
            Method[] arrmethod;
            Serializable serializable = new IdentityHashMap(arrclass.length);
            for (Class<?> arrclass22 : arrclass) {
                arrmethod = null;
                try {
                    arrmethod = object2 = Class.forName(arrclass22.getName(), false, (ClassLoader)object);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
                if (arrmethod == arrclass22) {
                    if (arrmethod.isInterface()) {
                        if (serializable.put(arrmethod, Boolean.TRUE) == null) {
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("repeated interface: ");
                        ((StringBuilder)object).append(arrmethod.getName());
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(arrmethod.getName());
                    ((StringBuilder)object).append(" is not an interface");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(arrclass22);
                ((StringBuilder)object).append(" is not visible from class loader");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            int n = arrclass.length;
            arrmethod = null;
            for (int i = 0; i < n; ++i) {
                Class<?> class_ = arrclass[i];
                object2 = arrmethod;
                if (!Modifier.isPublic(class_.getModifiers())) {
                    object2 = class_.getName();
                    int n2 = object2.lastIndexOf(46);
                    object2 = n2 == -1 ? "" : object2.substring(0, n2 + 1);
                    if (arrmethod != null) {
                        if (object2.equals(arrmethod)) {
                            object2 = arrmethod;
                        } else {
                            throw new IllegalArgumentException("non-public interfaces from different packages");
                        }
                    }
                }
                arrmethod = object2;
            }
            object2 = arrmethod;
            if (arrmethod == null) {
                object2 = "";
            }
            arrmethod = Proxy.getMethods(arrclass);
            Collections.sort(arrmethod, ORDER_BY_SIGNATURE_AND_SUBTYPE);
            Proxy.validateReturnTypes((List)arrmethod);
            List list = Proxy.deduplicateAndGetExceptions((List)arrmethod);
            arrmethod = arrmethod.toArray(new Method[arrmethod.size()]);
            Class[][] arrclass2 = (Class[][])list.toArray((T[])new Class[list.size()][]);
            long l = nextUniqueNumber.getAndIncrement();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append((String)object2);
            ((StringBuilder)serializable).append(proxyClassNamePrefix);
            ((StringBuilder)serializable).append(l);
            return Proxy.generateProxy(((StringBuilder)serializable).toString(), arrclass, (ClassLoader)object, arrmethod, arrclass2);
        }
    }

}

