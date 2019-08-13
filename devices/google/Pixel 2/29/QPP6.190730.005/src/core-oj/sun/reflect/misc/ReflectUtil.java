/*
 * Decompiled with CFR 0.145.
 */
package sun.reflect.misc;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import sun.reflect.Reflection;

public final class ReflectUtil {
    private ReflectUtil() {
    }

    public static void checkPackageAccess(Class<?> class_) {
        ReflectUtil.checkPackageAccess(class_.getName());
        if (ReflectUtil.isNonPublicProxyClass(class_)) {
            ReflectUtil.checkProxyPackageAccess(class_);
        }
    }

    public static void checkPackageAccess(String string) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            String string2;
            int n;
            string = string2 = string.replace('/', '.');
            if (string2.startsWith("[")) {
                n = string2.lastIndexOf(91) + 2;
                string = string2;
                if (n > 1) {
                    string = string2;
                    if (n < string2.length()) {
                        string = string2.substring(n);
                    }
                }
            }
            if ((n = string.lastIndexOf(46)) != -1) {
                securityManager.checkPackageAccess(string.substring(0, n));
            }
        }
    }

    public static void checkProxyPackageAccess(Class<?> arrclass) {
        if (System.getSecurityManager() != null && Proxy.isProxyClass(arrclass)) {
            arrclass = arrclass.getInterfaces();
            int n = arrclass.length;
            for (int i = 0; i < n; ++i) {
                ReflectUtil.checkPackageAccess(arrclass[i]);
            }
        }
    }

    public static void checkProxyPackageAccess(ClassLoader classLoader, Class<?> ... arrclass) {
        if (System.getSecurityManager() != null) {
            for (Class<?> class_ : arrclass) {
                if (!ReflectUtil.needsPackageAccessCheck(classLoader, class_.getClassLoader())) continue;
                ReflectUtil.checkPackageAccess(class_);
            }
        }
    }

    public static void ensureMemberAccess(Class<?> class_, Class<?> class_2, Object object, int n) throws IllegalAccessException {
        if (object == null && Modifier.isProtected(n)) {
            n = n & -5 | 1;
            Reflection.ensureMemberAccess(class_, class_2, object, n);
            try {
                Reflection.ensureMemberAccess(class_, class_2, object, n & -2);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                if (ReflectUtil.isSubclassOf(class_, class_2)) {
                    return;
                }
                throw illegalAccessException;
            }
        }
        Reflection.ensureMemberAccess(class_, class_2, object, n);
    }

    public static Class<?> forName(String string) throws ClassNotFoundException {
        ReflectUtil.checkPackageAccess(string);
        return Class.forName(string);
    }

    private static boolean isAncestor(ClassLoader classLoader, ClassLoader classLoader2) {
        do {
            if (classLoader != (classLoader2 = classLoader2.getParent())) continue;
            return true;
        } while (classLoader2 != null);
        return false;
    }

    public static boolean isNonPublicProxyClass(Class<?> class_) {
        String string = class_.getName();
        int n = string.lastIndexOf(46);
        boolean bl = false;
        string = n != -1 ? string.substring(0, n) : "";
        boolean bl2 = bl;
        if (Proxy.isProxyClass(class_)) {
            bl2 = bl;
            if (!string.isEmpty()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static boolean isPackageAccessible(Class<?> class_) {
        try {
            ReflectUtil.checkPackageAccess(class_);
            return true;
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

    private static boolean isSubclassOf(Class<?> class_, Class<?> class_2) {
        while (class_ != null) {
            if (class_ == class_2) {
                return true;
            }
            class_ = class_.getSuperclass();
        }
        return false;
    }

    public static boolean needsPackageAccessCheck(ClassLoader classLoader, ClassLoader classLoader2) {
        if (classLoader != null && classLoader != classLoader2) {
            if (classLoader2 == null) {
                return true;
            }
            return true ^ ReflectUtil.isAncestor(classLoader, classLoader2);
        }
        return false;
    }

    public static Object newInstance(Class<?> class_) throws InstantiationException, IllegalAccessException {
        ReflectUtil.checkPackageAccess(class_);
        return class_.newInstance();
    }
}

