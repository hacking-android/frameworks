/*
 * Decompiled with CFR 0.145.
 */
package sun.invoke.util;

import java.io.Serializable;
import java.lang.reflect.Modifier;

public class VerifyAccess {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean ALLOW_NESTMATE_ACCESS = false;
    private static final int ALL_ACCESS_MODES = 7;
    private static final int PACKAGE_ALLOWED = 8;
    private static final int PACKAGE_ONLY = 0;
    private static final int PROTECTED_OR_PACKAGE_ALLOWED = 12;

    private VerifyAccess() {
    }

    private static Class<?> getOutermostEnclosingClass(Class<?> class_) {
        Class<?> class_2 = class_;
        do {
            Class<?> class_3 = class_.getEnclosingClass();
            class_ = class_3;
            if (class_3 == null) break;
            class_2 = class_;
        } while (true);
        return class_2;
    }

    public static boolean isClassAccessible(Class<?> class_, Class<?> class_2, int n) {
        if (n == 0) {
            return false;
        }
        if (Modifier.isPublic(class_.getModifiers())) {
            return true;
        }
        return (n & 8) != 0 && VerifyAccess.isSamePackage(class_2, class_);
    }

    public static boolean isMemberAccessible(Class<?> serializable, Class<?> class_, int n, Class<?> class_2, int n2) {
        boolean bl;
        block10 : {
            bl = false;
            if (n2 == 0) {
                return false;
            }
            if (!VerifyAccess.isClassAccessible(serializable, class_2, n2)) {
                return false;
            }
            if (class_ == class_2 && (n2 & 2) != 0) {
                return true;
            }
            int n3 = n & 7;
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 == 4) {
                            if ((n2 & 12) != 0 && VerifyAccess.isSamePackage(class_, class_2)) {
                                return true;
                            }
                            if ((n2 & 4) == 0) {
                                return false;
                            }
                            if ((n & 8) != 0 && !VerifyAccess.isRelatedClass(serializable, class_2)) {
                                return false;
                            }
                            return (n2 & 4) != 0 && VerifyAccess.isSubClass(class_2, class_);
                        }
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("bad modifiers: ");
                        ((StringBuilder)serializable).append(Modifier.toString(n));
                        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                    }
                    return false;
                }
                return true;
            }
            if ((n2 & 8) == 0 || !VerifyAccess.isSamePackage(class_, class_2)) break block10;
            bl = true;
        }
        return bl;
    }

    static boolean isRelatedClass(Class<?> class_, Class<?> class_2) {
        boolean bl = class_ == class_2 || VerifyAccess.isSubClass(class_, class_2) || VerifyAccess.isSubClass(class_2, class_);
        return bl;
    }

    public static boolean isSamePackage(Class<?> object, Class<?> object2) {
        if (!((Class)object).isArray() && !((Class)object2).isArray()) {
            if (object == object2) {
                return true;
            }
            if (((Class)object).getClassLoader() != ((Class)object2).getClassLoader()) {
                return false;
            }
            object = ((Class)object).getName();
            object2 = ((Class)object2).getName();
            int n = ((String)object).lastIndexOf(46);
            if (n != ((String)object2).lastIndexOf(46)) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (((String)object).charAt(i) == ((String)object2).charAt(i)) continue;
                return false;
            }
            return true;
        }
        throw new IllegalArgumentException();
    }

    public static boolean isSamePackageMember(Class<?> class_, Class<?> class_2) {
        if (class_ == class_2) {
            return true;
        }
        if (!VerifyAccess.isSamePackage(class_, class_2)) {
            return false;
        }
        return VerifyAccess.getOutermostEnclosingClass(class_) == VerifyAccess.getOutermostEnclosingClass(class_2);
    }

    static boolean isSubClass(Class<?> class_, Class<?> class_2) {
        boolean bl = class_2.isAssignableFrom(class_) && !class_.isInterface();
        return bl;
    }
}

