/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import java.io.Serializable;
import java.lang.invoke.MethodHandleStatics;
import java.lang.invoke.MethodType;
import sun.invoke.util.Wrapper;

final class MethodTypeForm {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int ERASE = 1;
    public static final int INTS = 4;
    public static final int LONGS = 5;
    public static final int NO_CHANGE = 0;
    public static final int RAW_RETURN = 6;
    public static final int UNWRAP = 3;
    public static final int WRAP = 2;
    final long argCounts;
    final int[] argToSlotTable;
    final MethodType basicType;
    final MethodType erasedType;
    final long primCounts;
    final int[] slotToArgTable;

    protected MethodTypeForm(MethodType object) {
        int n;
        Object object2;
        Object[] arrobject;
        int n2;
        int n3;
        this.erasedType = object;
        Class<?>[] arrclass = object.ptypes();
        int n4 = arrclass.length;
        int n5 = 1;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        Object object3 = arrclass;
        int n11 = 0;
        Object object4 = arrclass;
        do {
            arrobject = object4;
            if (n11 >= arrclass.length) break;
            object4 = arrclass[n11];
            n3 = n6++;
            n2 = n7;
            object2 = object3;
            if (object4 != Object.class) {
                object2 = Wrapper.forPrimitiveType(object4);
                n = n7;
                if (((Wrapper)((Object)object2)).isDoubleWord()) {
                    n = n7 + 1;
                }
                if (((Wrapper)((Object)object2)).isSubwordOrInt()) {
                    n3 = n6;
                    n2 = n;
                    object2 = object3;
                    if (object4 != Integer.TYPE) {
                        object4 = object3;
                        if (object3 == arrclass) {
                            object4 = (Class[])object3.clone();
                        }
                        object4[n11] = Integer.TYPE;
                        n3 = n6;
                        n2 = n;
                        object2 = object4;
                    }
                } else {
                    object2 = object3;
                    n2 = n;
                    n3 = n6;
                }
            }
            ++n11;
            object4 = arrobject;
            n6 = n3;
            n7 = n2;
            object3 = object2;
        } while (true);
        int n12 = n4 + n7;
        arrobject = object4 = object.returnType();
        object2 = arrobject;
        if (arrobject != Object.class) {
            n2 = 0 + 1;
            object2 = Wrapper.forPrimitiveType(arrobject);
            n = n10;
            if (((Wrapper)((Object)object2)).isDoubleWord()) {
                n = 0 + 1;
            }
            if (((Wrapper)((Object)object2)).isSubwordOrInt() && arrobject != Integer.TYPE) {
                object4 = Integer.TYPE;
            }
            if (arrobject == Void.TYPE) {
                n11 = 0;
                n5 = 0;
                n3 = n;
                n = n11;
            } else {
                n11 = 1 + n;
                n3 = n;
                n = n11;
            }
        } else {
            n = 1;
            n3 = n9;
            n2 = n8;
            object4 = object2;
        }
        if (arrclass == object3 && object4 == arrobject) {
            this.basicType = object;
            if (n7 != 0) {
                n11 = n4 + n7;
                object = new int[n11 + 1];
                object4 = new int[n4 + 1];
                object4[0] = (Class<?>)n11;
                for (n8 = 0; n8 < arrclass.length; ++n8) {
                    n9 = n11;
                    if (Wrapper.forBasicType(arrclass[n8]).isDoubleWord()) {
                        n9 = n11 - 1;
                    }
                    n11 = n9 - 1;
                    object[n11] = n8 + 1;
                    object4[n8 + 1] = (Class<?>)n11;
                }
            } else if (n6 != 0) {
                object4 = MethodType.genericMethodType(n4).form();
                object = object4.slotToArgTable;
                object4 = object4.argToSlotTable;
            } else {
                n8 = n4;
                object3 = new int[n8 + 1];
                arrobject = new int[n4 + 1];
                arrobject[0] = (Class<?>)n8;
                n11 = 0;
                do {
                    object = object3;
                    object4 = arrobject;
                    if (n11 >= n4) break;
                    object3[--n8] = n11 + 1;
                    arrobject[n11 + 1] = (Class<?>)n8;
                    ++n11;
                } while (true);
            }
            this.primCounts = MethodTypeForm.pack(n3, n2, n7, n6);
            this.argCounts = MethodTypeForm.pack(n, n5, n12, n4);
            this.argToSlotTable = object4;
            this.slotToArgTable = object;
            if (n12 < 256) {
                return;
            }
            throw MethodHandleStatics.newIllegalArgumentException("too many arguments");
        }
        this.basicType = MethodType.makeImpl(object4, object3, true);
        object = this.basicType.form();
        this.primCounts = object.primCounts;
        this.argCounts = object.argCounts;
        this.argToSlotTable = object.argToSlotTable;
        this.slotToArgTable = object.slotToArgTable;
    }

    private boolean assertIsBasicType() {
        return true;
    }

    static Class<?> canonicalize(Class<?> class_, int n) {
        block17 : {
            block18 : {
                block19 : {
                    block20 : {
                        block11 : {
                            block15 : {
                                block16 : {
                                    block12 : {
                                        block13 : {
                                            block14 : {
                                                if (class_ == Object.class) break block11;
                                                if (class_.isPrimitive()) break block12;
                                                if (n == 1) break block13;
                                                if (n == 3) break block14;
                                                if (n == 6) break block13;
                                                break block11;
                                            }
                                            Class<?> class_2 = Wrapper.asPrimitiveType(class_);
                                            if (class_2 != class_) {
                                                return class_2;
                                            }
                                            break block11;
                                        }
                                        return Object.class;
                                    }
                                    if (class_ != Void.TYPE) break block15;
                                    if (n == 2) break block16;
                                    if (n == 6) {
                                        return Integer.TYPE;
                                    }
                                    break block11;
                                }
                                return Void.class;
                            }
                            if (n == 2) break block17;
                            if (n == 4) break block18;
                            if (n == 5) break block19;
                            if (n == 6) break block20;
                        }
                        return null;
                    }
                    if (class_ != Integer.TYPE && class_ != Long.TYPE && class_ != Float.TYPE && class_ != Double.TYPE) {
                        return Integer.TYPE;
                    }
                    return null;
                }
                if (class_ == Long.TYPE) {
                    return null;
                }
                return Long.TYPE;
            }
            if (class_ != Integer.TYPE && class_ != Long.TYPE) {
                if (class_ == Double.TYPE) {
                    return Long.TYPE;
                }
                return Integer.TYPE;
            }
            return null;
        }
        return Wrapper.asWrapperType(class_);
    }

    public static MethodType canonicalize(MethodType serializable, int n, int n2) {
        Class<?>[] arrclass = serializable.ptypes();
        Class<?>[] arrclass2 = MethodTypeForm.canonicalizeAll(arrclass, n2);
        Class<?> class_ = serializable.returnType();
        Class<?>[] arrclass3 = MethodTypeForm.canonicalize(class_, n);
        if (arrclass2 == null && arrclass3 == null) {
            return null;
        }
        serializable = arrclass3;
        if (arrclass3 == null) {
            serializable = class_;
        }
        arrclass3 = arrclass2;
        if (arrclass2 == null) {
            arrclass3 = arrclass;
        }
        return MethodType.makeImpl(serializable, arrclass3, true);
    }

    static Class<?>[] canonicalizeAll(Class<?>[] arrclass, int n) {
        Class[] arrclass2 = null;
        int n2 = arrclass.length;
        for (int i = 0; i < n2; ++i) {
            Class[] arrclass3;
            Class[] arrclass4 = arrclass3 = MethodTypeForm.canonicalize(arrclass[i], n);
            if (arrclass3 == Void.TYPE) {
                arrclass4 = null;
            }
            arrclass3 = arrclass2;
            if (arrclass4 != null) {
                arrclass3 = arrclass2;
                if (arrclass2 == null) {
                    arrclass3 = (Class[])arrclass.clone();
                }
                arrclass3[i] = arrclass4;
            }
            arrclass2 = arrclass3;
        }
        return arrclass2;
    }

    static MethodTypeForm findForm(MethodType methodType) {
        MethodType methodType2 = MethodTypeForm.canonicalize(methodType, 1, 1);
        if (methodType2 == null) {
            return new MethodTypeForm(methodType);
        }
        return methodType2.form();
    }

    private static long pack(int n, int n2, int n3, int n4) {
        return (long)(n << 16 | n2) << 32 | (long)(n3 << 16 | n4);
    }

    private static char unpack(long l, int n) {
        return (char)(l >> (3 - n) * 16);
    }

    public int argSlotToParameter(int n) {
        return this.slotToArgTable[n] - 1;
    }

    public MethodType basicType() {
        return this.basicType;
    }

    public MethodType erasedType() {
        return this.erasedType;
    }

    public boolean hasLongPrimitives() {
        boolean bl = (this.longPrimitiveParameterCount() | this.longPrimitiveReturnCount()) != 0;
        return bl;
    }

    public boolean hasNonVoidPrimitives() {
        long l = this.primCounts;
        boolean bl = false;
        if (l == 0L) {
            return false;
        }
        if (this.primitiveParameterCount() != 0) {
            return true;
        }
        boolean bl2 = bl;
        if (this.primitiveReturnCount() != 0) {
            bl2 = bl;
            if (this.returnCount() != 0) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public boolean hasPrimitives() {
        boolean bl = this.primCounts != 0L;
        return bl;
    }

    public int longPrimitiveParameterCount() {
        return MethodTypeForm.unpack(this.primCounts, 2);
    }

    public int longPrimitiveReturnCount() {
        return MethodTypeForm.unpack(this.primCounts, 0);
    }

    public int parameterCount() {
        return MethodTypeForm.unpack(this.argCounts, 3);
    }

    public int parameterSlotCount() {
        return MethodTypeForm.unpack(this.argCounts, 2);
    }

    public int parameterToArgSlot(int n) {
        return this.argToSlotTable[n + 1];
    }

    public int primitiveParameterCount() {
        return MethodTypeForm.unpack(this.primCounts, 3);
    }

    public int primitiveReturnCount() {
        return MethodTypeForm.unpack(this.primCounts, 1);
    }

    public int returnCount() {
        return MethodTypeForm.unpack(this.argCounts, 1);
    }

    public int returnSlotCount() {
        return MethodTypeForm.unpack(this.argCounts, 0);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Form");
        stringBuilder.append(this.erasedType);
        return stringBuilder.toString();
    }
}

