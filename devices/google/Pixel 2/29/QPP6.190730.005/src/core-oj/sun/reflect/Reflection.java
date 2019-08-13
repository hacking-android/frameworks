/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMStack
 */
package sun.reflect;

import dalvik.system.VMStack;
import java.lang.reflect.Modifier;

public class Reflection {
    public static void ensureMemberAccess(Class<?> class_, Class<?> class_2, Object object, int n) throws IllegalAccessException {
        if (class_ != null && class_2 != null) {
            if (Reflection.verifyMemberAccess(class_, class_2, object, n)) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Class ");
            ((StringBuilder)object).append(class_.getName());
            ((StringBuilder)object).append(" can not access a member of class ");
            ((StringBuilder)object).append(class_2.getName());
            ((StringBuilder)object).append(" with modifiers \"");
            ((StringBuilder)object).append(Modifier.toString(n));
            ((StringBuilder)object).append("\"");
            throw new IllegalAccessException(((StringBuilder)object).toString());
        }
        throw new InternalError();
    }

    public static Class<?> getCallerClass() {
        return VMStack.getStackClass2();
    }

    private static boolean isSameClassPackage(Class<?> class_, Class<?> class_2) {
        return Reflection.isSameClassPackage(class_.getClassLoader(), class_.getName(), class_2.getClassLoader(), class_2.getName());
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isSameClassPackage(ClassLoader object, String string, ClassLoader classLoader, String string2) {
        boolean bl = false;
        if (object != classLoader) {
            return false;
        }
        int n = string.lastIndexOf(46);
        int n2 = string2.lastIndexOf(46);
        if (n != -1 && n2 != -1) {
            int n3;
            int n4 = 0;
            int n5 = 0;
            if (string.charAt(0) == '[') {
                do {
                    n4 = n3 = n4 + 1;
                } while (string.charAt(n3) == '[');
                if (string.charAt(n3) != 'L') {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Illegal class name ");
                    ((StringBuilder)object).append(string);
                    throw new InternalError(((StringBuilder)object).toString());
                }
            } else {
                n3 = 0;
            }
            if (string2.charAt(0) == '[') {
                n4 = n5;
                do {
                    n4 = n5 = n4 + 1;
                } while (string2.charAt(n5) == '[');
                if (string2.charAt(n5) != 'L') {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Illegal class name ");
                    ((StringBuilder)object).append(string2);
                    throw new InternalError(((StringBuilder)object).toString());
                }
                n4 = n5;
            } else {
                n4 = 0;
            }
            n5 = n - n3;
            if (n5 == n2 - n4) return string.regionMatches(false, n3, string2, n4, n5);
            return false;
        }
        if (n != n2) return bl;
        return true;
    }

    static boolean isSubclassOf(Class<?> class_, Class<?> class_2) {
        while (class_ != null) {
            if (class_ == class_2) {
                return true;
            }
            class_ = class_.getSuperclass();
        }
        return false;
    }

    public static boolean verifyMemberAccess(Class<?> class_, Class<?> class_2, Object class_3, int n) {
        boolean bl;
        boolean bl2;
        boolean bl3 = false;
        boolean bl4 = false;
        if (class_ == class_2) {
            return true;
        }
        if (!Modifier.isPublic(class_2.getAccessFlags())) {
            bl = Reflection.isSameClassPackage(class_, class_2);
            bl3 = true;
            bl4 = bl;
            if (!bl) {
                return false;
            }
        }
        if (Modifier.isPublic(n)) {
            return true;
        }
        boolean bl5 = bl2 = false;
        if (Modifier.isProtected(n)) {
            bl5 = bl2;
            if (Reflection.isSubclassOf(class_, class_2)) {
                bl5 = true;
            }
        }
        boolean bl6 = bl3;
        bl = bl4;
        boolean bl7 = bl5;
        if (!bl5) {
            bl6 = bl3;
            bl = bl4;
            bl7 = bl5;
            if (!Modifier.isPrivate(n)) {
                bl2 = bl3;
                if (!bl3) {
                    bl4 = Reflection.isSameClassPackage(class_, class_2);
                    bl2 = true;
                }
                bl6 = bl2;
                bl = bl4;
                bl7 = bl5;
                if (bl4) {
                    bl7 = true;
                    bl = bl4;
                    bl6 = bl2;
                }
            }
        }
        if (!bl7) {
            return false;
        }
        if (Modifier.isProtected(n) && (class_3 = class_3 == null ? class_2 : class_3.getClass()) != class_) {
            if (!bl6) {
                bl = Reflection.isSameClassPackage(class_, class_2);
            }
            if (!bl && !Reflection.isSubclassOf(class_3, class_)) {
                return false;
            }
        }
        return true;
    }
}

