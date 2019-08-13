/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

class MethodHandleNatives {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    MethodHandleNatives() {
    }

    static boolean refKindIsField(byte by) {
        boolean bl = by <= 4;
        return bl;
    }

    static boolean refKindIsValid(int n) {
        boolean bl = n > 0 && n < 10;
        return bl;
    }

    static String refKindName(byte by) {
        switch (by) {
            default: {
                return "REF_???";
            }
            case 9: {
                return "invokeInterface";
            }
            case 8: {
                return "newInvokeSpecial";
            }
            case 7: {
                return "invokeSpecial";
            }
            case 6: {
                return "invokeStatic";
            }
            case 5: {
                return "invokeVirtual";
            }
            case 4: {
                return "putStatic";
            }
            case 3: {
                return "putField";
            }
            case 2: {
                return "getStatic";
            }
            case 1: 
        }
        return "getField";
    }

    static class Constants {
        static final byte REF_LIMIT = 10;
        static final byte REF_NONE = 0;
        static final byte REF_getField = 1;
        static final byte REF_getStatic = 2;
        static final byte REF_invokeInterface = 9;
        static final byte REF_invokeSpecial = 7;
        static final byte REF_invokeStatic = 6;
        static final byte REF_invokeVirtual = 5;
        static final byte REF_newInvokeSpecial = 8;
        static final byte REF_putField = 3;
        static final byte REF_putStatic = 4;

        Constants() {
        }
    }

}

