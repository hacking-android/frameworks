/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 *  java.io.-$
 *  java.io.-$$Lambda
 *  java.io.-$$Lambda$ObjectStreamClass
 *  java.io.-$$Lambda$ObjectStreamClass$GVMp_c-BEBrBo_ZKh_HiLSO-fGo
 */
package java.io;

import dalvik.system.VMRuntime;
import java.io.-$;
import java.io.Bits;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.OutputStream;
import java.io.Serializable;
import java.io._$$Lambda$ObjectStreamClass$GVMp_c_BEBrBo_ZKh_HiLSO_fGo;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;

public class ObjectStreamClass
implements Serializable {
    static final int MAX_SDK_TARGET_FOR_CLINIT_UIDGEN_WORKAROUND = 23;
    public static final ObjectStreamField[] NO_FIELDS = new ObjectStreamField[0];
    private static final ObjectStreamField[] serialPersistentFields = NO_FIELDS;
    private static final long serialVersionUID = -6120832682080437368L;
    public static DefaultSUIDCompatibilityListener suidCompatibilityListener = _$$Lambda$ObjectStreamClass$GVMp_c_BEBrBo_ZKh_HiLSO_fGo.INSTANCE;
    private Class<?> cl;
    private Constructor<?> cons;
    private volatile ClassDataSlot[] dataLayout;
    private ExceptionInfo defaultSerializeEx;
    private ExceptionInfo deserializeEx;
    private boolean externalizable;
    private FieldReflector fieldRefl;
    private ObjectStreamField[] fields;
    private boolean hasBlockExternalData;
    private boolean hasWriteObjectData;
    private boolean initialized;
    private boolean isEnum;
    private boolean isProxy;
    private ObjectStreamClass localDesc;
    private String name;
    private int numObjFields;
    private int primDataSize;
    private Method readObjectMethod;
    private Method readObjectNoDataMethod;
    private Method readResolveMethod;
    private ClassNotFoundException resolveEx;
    private boolean serializable;
    private ExceptionInfo serializeEx;
    private volatile Long suid;
    private ObjectStreamClass superDesc;
    private Method writeObjectMethod;
    private Method writeReplaceMethod;

    ObjectStreamClass() {
        this.hasBlockExternalData = true;
    }

    private ObjectStreamClass(final Class<?> arrobjectStreamField) {
        block6 : {
            this.hasBlockExternalData = true;
            this.cl = arrobjectStreamField;
            this.name = arrobjectStreamField.getName();
            this.isProxy = Proxy.isProxyClass(arrobjectStreamField);
            this.isEnum = Enum.class.isAssignableFrom((Class<?>)arrobjectStreamField);
            this.serializable = Serializable.class.isAssignableFrom((Class<?>)arrobjectStreamField);
            this.externalizable = Externalizable.class.isAssignableFrom((Class<?>)arrobjectStreamField);
            Serializable serializable = arrobjectStreamField.getSuperclass();
            serializable = serializable != null ? ObjectStreamClass.lookup(serializable, false) : null;
            this.superDesc = serializable;
            this.localDesc = this;
            if (this.serializable) {
                AccessController.doPrivileged(new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        Serializable serializable;
                        if (ObjectStreamClass.this.isEnum) {
                            ObjectStreamClass.this.suid = 0L;
                            ObjectStreamClass.this.fields = NO_FIELDS;
                            return null;
                        }
                        if (arrobjectStreamField.isArray()) {
                            ObjectStreamClass.this.fields = NO_FIELDS;
                            return null;
                        }
                        ObjectStreamClass.this.suid = ObjectStreamClass.getDeclaredSUID(arrobjectStreamField);
                        try {
                            ObjectStreamClass.this.fields = ObjectStreamClass.getSerialFields(arrobjectStreamField);
                            ObjectStreamClass.this.computeFieldOffsets();
                        }
                        catch (InvalidClassException invalidClassException) {
                            serializable = ObjectStreamClass.this;
                            serializable.serializeEx = (serializable.deserializeEx = new ExceptionInfo(invalidClassException.classname, invalidClassException.getMessage()));
                            ObjectStreamClass.this.fields = NO_FIELDS;
                        }
                        if (ObjectStreamClass.this.externalizable) {
                            ObjectStreamClass.this.cons = ObjectStreamClass.getExternalizableConstructor(arrobjectStreamField);
                        } else {
                            ObjectStreamClass.this.cons = ObjectStreamClass.getSerializableConstructor(arrobjectStreamField);
                            serializable = ObjectStreamClass.this;
                            Serializable serializable2 = arrobjectStreamField;
                            boolean bl = true;
                            Class<Void> class_ = Void.TYPE;
                            serializable.writeObjectMethod = ObjectStreamClass.getPrivateMethod(serializable2, "writeObject", new Class[]{ObjectOutputStream.class}, class_);
                            serializable2 = ObjectStreamClass.this;
                            serializable = arrobjectStreamField;
                            class_ = Void.TYPE;
                            ((ObjectStreamClass)serializable2).readObjectMethod = ObjectStreamClass.getPrivateMethod((Class)serializable, "readObject", new Class[]{ObjectInputStream.class}, class_);
                            ObjectStreamClass.this.readObjectNoDataMethod = ObjectStreamClass.getPrivateMethod(arrobjectStreamField, "readObjectNoData", null, Void.TYPE);
                            serializable = ObjectStreamClass.this;
                            if (serializable.writeObjectMethod == null) {
                                bl = false;
                            }
                            serializable.hasWriteObjectData = bl;
                        }
                        ObjectStreamClass.this.writeReplaceMethod = ObjectStreamClass.getInheritableMethod(arrobjectStreamField, "writeReplace", null, Object.class);
                        ObjectStreamClass.this.readResolveMethod = ObjectStreamClass.getInheritableMethod(arrobjectStreamField, "readResolve", null, Object.class);
                        return null;
                    }
                });
            } else {
                this.suid = 0L;
                this.fields = NO_FIELDS;
            }
            try {
                this.fieldRefl = ObjectStreamClass.getReflector(this.fields, this);
                if (this.deserializeEx != null) break block6;
                if (this.isEnum) {
                    this.deserializeEx = new ExceptionInfo(this.name, "enum type");
                    break block6;
                }
                if (this.cons != null) break block6;
                this.deserializeEx = new ExceptionInfo(this.name, "no valid constructor");
            }
            catch (InvalidClassException invalidClassException) {
                throw new InternalError(invalidClassException);
            }
        }
        for (int i = 0; i < (arrobjectStreamField = this.fields).length; ++i) {
            if (arrobjectStreamField[i].getField() != null) continue;
            this.defaultSerializeEx = new ExceptionInfo(this.name, "unmatched serializable field(s) declared");
        }
        this.initialized = true;
        return;
    }

    private static boolean classNamesEqual(String string, String string2) {
        return string.substring(string.lastIndexOf(46) + 1).equals(string2.substring(string2.lastIndexOf(46) + 1));
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static long computeDefaultSUID(Class<?> class_) {
        int n;
        Object object;
        if (!Serializable.class.isAssignableFrom(class_)) return 0L;
        if (Proxy.isProxyClass(class_)) {
            return 0L;
        }
        byte[] arrby = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)arrby);
        dataOutputStream.writeUTF(class_.getName());
        int n2 = class_.getModifiers() & 1553;
        Method[] arrmethod = class_.getDeclaredMethods();
        int n3 = n2;
        if ((n2 & 512) != 0) {
            n3 = arrmethod.length > 0 ? n2 | 1024 : n2 & -1025;
        }
        dataOutputStream.writeInt(n3);
        if (!class_.isArray()) {
            object = class_.getInterfaces();
            Object[] arrobject = new String[((Class<?>[])object).length];
            for (n2 = 0; n2 < ((Object[])object).length; ++n2) {
                arrobject[n2] = ((Class)object[n2]).getName();
            }
            Arrays.sort(arrobject);
            for (n2 = 0; n2 < arrobject.length; ++n2) {
                dataOutputStream.writeUTF((String)arrobject[n2]);
            }
        }
        Field[] arrfield = class_.getDeclaredFields();
        object = new MemberSignature[arrfield.length];
        for (n2 = 0; n2 < arrfield.length; ++n2) {
            object[n2] = new MemberSignature(arrfield[n2]);
        }
        Comparator<MemberSignature> comparator = new Comparator<MemberSignature>(){

            @Override
            public int compare(MemberSignature memberSignature, MemberSignature memberSignature2) {
                return memberSignature.name.compareTo(memberSignature2.name);
            }
        };
        Arrays.sort(object, comparator);
        for (n2 = 0; n2 < ((Object[])object).length; ++n2) {
            Object object2 = object[n2];
            n = ((MemberSignature)object2).member.getModifiers() & 223;
            if ((n & 2) != 0 && (n & 136) != 0) continue;
            dataOutputStream.writeUTF(((MemberSignature)object2).name);
            dataOutputStream.writeInt(n);
            dataOutputStream.writeUTF(((MemberSignature)object2).signature);
        }
        boolean bl = VMRuntime.getRuntime().getTargetSdkVersion() <= 23;
        n2 = 0;
        n = 0;
        if (ObjectStreamClass.hasStaticInitializer(class_, bl)) {
            n2 = n;
            if (bl) {
                n2 = n;
                if (!ObjectStreamClass.hasStaticInitializer(class_, false)) {
                    n2 = 1;
                }
            }
            dataOutputStream.writeUTF("<clinit>");
            dataOutputStream.writeInt(8);
            dataOutputStream.writeUTF("()V");
        }
        Constructor<?>[] arrconstructor = class_.getDeclaredConstructors();
        object = new MemberSignature[arrconstructor.length];
        for (n = 0; n < arrconstructor.length; ++n) {
            object[n] = new MemberSignature(arrconstructor[n]);
        }
        Comparator<MemberSignature> comparator2 = new Comparator<MemberSignature>(){

            @Override
            public int compare(MemberSignature memberSignature, MemberSignature memberSignature2) {
                return memberSignature.signature.compareTo(memberSignature2.signature);
            }
        };
        Arrays.sort(object, comparator2);
        for (n = 0; n < ((Object[])object).length; ++n) {
            Object object3 = object[n];
            int n4 = ((MemberSignature)object3).member.getModifiers() & 3391;
            if ((n4 & 2) != 0) continue;
            dataOutputStream.writeUTF("<init>");
            dataOutputStream.writeInt(n4);
            dataOutputStream.writeUTF(((MemberSignature)object3).signature.replace('/', '.'));
        }
        object = new MemberSignature[arrmethod.length];
        for (n3 = 0; n3 < arrmethod.length; ++n3) {
            object[n3] = new MemberSignature(arrmethod[n3]);
        }
        Comparator<MemberSignature> comparator3 = new Comparator<MemberSignature>(){

            @Override
            public int compare(MemberSignature memberSignature, MemberSignature memberSignature2) {
                int n;
                int n2 = n = memberSignature.name.compareTo(memberSignature2.name);
                if (n == 0) {
                    n2 = memberSignature.signature.compareTo(memberSignature2.signature);
                }
                return n2;
            }
        };
        Arrays.sort(object, comparator3);
        for (n3 = 0; n3 < ((Object[])object).length; ++n3) {
            Object object4 = object[n3];
            n = ((MemberSignature)object4).member.getModifiers() & 3391;
            if ((n & 2) != 0) continue;
            dataOutputStream.writeUTF(((MemberSignature)object4).name);
            dataOutputStream.writeInt(n);
            dataOutputStream.writeUTF(((MemberSignature)object4).signature.replace('/', '.'));
        }
        dataOutputStream.flush();
        object = MessageDigest.getInstance("SHA");
        arrby = ((MessageDigest)object).digest(arrby.toByteArray());
        long l = 0L;
        for (n3 = java.lang.Math.min((int)arrby.length, (int)8) - 1; n3 >= 0; --n3) {
            l = l << 8 | (long)(arrby[n3] & 255);
        }
        if (n2 == 0) return l;
        try {
            suidCompatibilityListener.warnDefaultSUIDTargetVersionDependent(class_, l);
            return l;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new SecurityException(noSuchAlgorithmException.getMessage());
        }
        catch (IOException iOException) {
            throw new InternalError(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void computeFieldOffsets() throws InvalidClassException {
        this.primDataSize = 0;
        this.numObjFields = 0;
        var1_1 = -1;
        var2_2 = 0;
        do {
            block11 : {
                block10 : {
                    if (var2_2 >= ((ObjectStreamField[])(var3_3 = this.fields)).length) {
                        if (var1_1 == -1) return;
                        if (this.numObjFields + var1_1 != ((Object)var3_3).length) throw new InvalidClassException(this.name, "illegal field order");
                        return;
                    }
                    var4_4 = (var3_3 = var3_3[var2_2]).getTypeCode();
                    if (var4_4 == 70) break block10;
                    if (var4_4 == 76) ** GOTO lbl-1000
                    if (var4_4 == 83) ** GOTO lbl33
                    if (var4_4 == 73) break block10;
                    if (var4_4 == 74) ** GOTO lbl-1000
                    if (var4_4 == 90) ** GOTO lbl-1000
                    if (var4_4 != 91) {
                        switch (var4_4) {
                            default: {
                                throw new InternalError();
                            }
                            case 66: lbl-1000: // 2 sources:
                            {
                                var4_4 = this.primDataSize;
                                this.primDataSize = var4_4 + 1;
                                var3_3.setOffset(var4_4);
                                var4_4 = var1_1;
                                ** break;
                            }
                            case 68: lbl-1000: // 2 sources:
                            {
                                var3_3.setOffset(this.primDataSize);
                                this.primDataSize += 8;
                                var4_4 = var1_1;
                                ** break;
                            }
                            case 67: 
                        }
lbl33: // 2 sources:
                        var3_3.setOffset(this.primDataSize);
                        this.primDataSize += 2;
                        var4_4 = var1_1;
                        ** break;
lbl37: // 3 sources:
                    } else lbl-1000: // 2 sources:
                    {
                        var4_4 = this.numObjFields;
                        this.numObjFields = var4_4 + 1;
                        var3_3.setOffset(var4_4);
                        var4_4 = var1_1;
                        if (var1_1 == -1) {
                            var4_4 = var2_2;
                        }
                    }
                    break block11;
                }
                var3_3.setOffset(this.primDataSize);
                this.primDataSize += 4;
                var4_4 = var1_1;
            }
            ++var2_2;
            var1_1 = var4_4;
        } while (true);
    }

    private ClassDataSlot[] getClassDataLayout0() throws InvalidClassException {
        Class<?> class_;
        ArrayList<ClassDataSlot> arrayList = new ArrayList<ClassDataSlot>();
        Class<?> class_2 = this.cl;
        for (class_ = this.cl; class_ != null && Serializable.class.isAssignableFrom(class_); class_ = class_.getSuperclass()) {
        }
        HashSet<String> hashSet = new HashSet<String>(3);
        ObjectStreamClass objectStreamClass = this;
        while (objectStreamClass != null) {
            if (!hashSet.contains(objectStreamClass.name)) {
                Class<?> class_3;
                hashSet.add(objectStreamClass.name);
                Class<?> class_4 = objectStreamClass.cl;
                String string = class_4 != null ? class_4.getName() : objectStreamClass.name;
                Class<?> class_5 = null;
                class_4 = class_2;
                do {
                    class_3 = class_5;
                    if (class_4 == class_) break;
                    if (string.equals(class_4.getName())) {
                        class_3 = class_4;
                        break;
                    }
                    class_4 = class_4.getSuperclass();
                } while (true);
                class_4 = class_2;
                if (class_3 != null) {
                    while (class_2 != class_3) {
                        arrayList.add(new ClassDataSlot(ObjectStreamClass.lookup(class_2, true), false));
                        class_2 = class_2.getSuperclass();
                    }
                    class_4 = class_3.getSuperclass();
                }
                arrayList.add(new ClassDataSlot(objectStreamClass.getVariantFor(class_3), true));
                objectStreamClass = objectStreamClass.superDesc;
                class_2 = class_4;
                continue;
            }
            throw new InvalidClassException("Circular reference.");
        }
        while (class_2 != class_) {
            arrayList.add(new ClassDataSlot(ObjectStreamClass.lookup(class_2, true), false));
            class_2 = class_2.getSuperclass();
        }
        Collections.reverse(arrayList);
        return arrayList.toArray(new ClassDataSlot[arrayList.size()]);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String getClassSignature(Class<?> class_) {
        StringBuilder stringBuilder = new StringBuilder();
        while (class_.isArray()) {
            stringBuilder.append('[');
            class_ = class_.getComponentType();
        }
        if (class_.isPrimitive()) {
            if (class_ == Integer.TYPE) {
                stringBuilder.append('I');
                return stringBuilder.toString();
            } else if (class_ == Byte.TYPE) {
                stringBuilder.append('B');
                return stringBuilder.toString();
            } else if (class_ == Long.TYPE) {
                stringBuilder.append('J');
                return stringBuilder.toString();
            } else if (class_ == Float.TYPE) {
                stringBuilder.append('F');
                return stringBuilder.toString();
            } else if (class_ == Double.TYPE) {
                stringBuilder.append('D');
                return stringBuilder.toString();
            } else if (class_ == Short.TYPE) {
                stringBuilder.append('S');
                return stringBuilder.toString();
            } else if (class_ == Character.TYPE) {
                stringBuilder.append('C');
                return stringBuilder.toString();
            } else if (class_ == Boolean.TYPE) {
                stringBuilder.append('Z');
                return stringBuilder.toString();
            } else {
                if (class_ != Void.TYPE) throw new InternalError();
                stringBuilder.append('V');
            }
            return stringBuilder.toString();
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append('L');
            stringBuilder2.append(class_.getName().replace('.', '/'));
            stringBuilder2.append(';');
            stringBuilder.append(stringBuilder2.toString());
        }
        return stringBuilder.toString();
    }

    private static long getConstructorId(Class<?> serializable) {
        int n = VMRuntime.getRuntime().getTargetSdkVersion();
        if (n > 0 && n <= 24) {
            System.logE("WARNING: ObjectStreamClass.getConstructorId(Class<?>) is private API andwill be removed in a future Android release.");
            return 1189998819991197253L;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("ObjectStreamClass.getConstructorId(Class<?>) is not supported on SDK ");
        ((StringBuilder)serializable).append(n);
        throw new UnsupportedOperationException(((StringBuilder)serializable).toString());
    }

    private static Long getDeclaredSUID(Class<?> annotatedElement) {
        block2 : {
            long l;
            try {
                annotatedElement = ((Class)annotatedElement).getDeclaredField("serialVersionUID");
                if ((((Field)annotatedElement).getModifiers() & 24) != 24) break block2;
                ((AccessibleObject)annotatedElement).setAccessible(true);
                l = ((Field)annotatedElement).getLong(null);
            }
            catch (Exception exception) {
                // empty catch block
            }
            return l;
        }
        return null;
    }

    private static ObjectStreamField[] getDeclaredSerialFields(Class<?> serializable) throws InvalidClassException {
        Object object;
        Object object2 = null;
        ObjectStreamField[] arrobjectStreamField = null;
        try {
            object = ((Class)serializable).getDeclaredField("serialPersistentFields");
            if ((((Field)object).getModifiers() & 26) == 26) {
                ((AccessibleObject)object).setAccessible(true);
                arrobjectStreamField = (ObjectStreamField[])((Field)object).get(null);
            }
        }
        catch (Exception exception) {
            arrobjectStreamField = object2;
        }
        if (arrobjectStreamField == null) {
            return null;
        }
        if (arrobjectStreamField.length == 0) {
            return NO_FIELDS;
        }
        ObjectStreamField[] arrobjectStreamField2 = new ObjectStreamField[arrobjectStreamField.length];
        object = new HashSet(arrobjectStreamField.length);
        for (int i = 0; i < arrobjectStreamField.length; ++i) {
            ObjectStreamField objectStreamField = arrobjectStreamField[i];
            object2 = objectStreamField.getName();
            if (!object.contains(object2)) {
                object.add(object2);
                try {
                    Field field = ((Class)serializable).getDeclaredField((String)object2);
                    if (field.getType() == objectStreamField.getType() && (field.getModifiers() & 8) == 0) {
                        ObjectStreamField objectStreamField2;
                        arrobjectStreamField2[i] = objectStreamField2 = new ObjectStreamField(field, objectStreamField.isUnshared(), true);
                    }
                }
                catch (NoSuchFieldException noSuchFieldException) {
                    // empty catch block
                }
                if (arrobjectStreamField2[i] != null) continue;
                arrobjectStreamField2[i] = new ObjectStreamField((String)object2, objectStreamField.getType(), objectStreamField.isUnshared());
                continue;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("multiple serializable fields named ");
            ((StringBuilder)serializable).append((String)object2);
            throw new InvalidClassException(((StringBuilder)serializable).toString());
        }
        return arrobjectStreamField2;
    }

    private static ObjectStreamField[] getDefaultSerialFields(Class<?> serializable) {
        int n;
        Field[] arrfield = ((Class)serializable).getDeclaredFields();
        serializable = new ArrayList();
        for (n = 0; n < arrfield.length; ++n) {
            if ((arrfield[n].getModifiers() & 136) != 0) continue;
            ((ArrayList)serializable).add(new ObjectStreamField(arrfield[n], false, true));
        }
        n = ((ArrayList)serializable).size();
        serializable = n == 0 ? NO_FIELDS : ((ArrayList)serializable).toArray(new ObjectStreamField[n]);
        return serializable;
    }

    private static Constructor<?> getExternalizableConstructor(Class<?> genericDeclaration) {
        Object var1_2 = null;
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredConstructor(null);
            ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            int n = ((Constructor)genericDeclaration).getModifiers();
            if ((1 & n) == 0) {
                genericDeclaration = var1_2;
            }
            return genericDeclaration;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    private static Method getInheritableMethod(Class<?> class_, String object, Class<?>[] arrclass, Class<?> class_2) {
        Method method;
        Method method2 = null;
        Class<?> class_3 = class_;
        do {
            method = method2;
            if (class_3 == null) break;
            try {
                method = class_3.getDeclaredMethod((String)object, arrclass);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                class_3 = class_3.getSuperclass();
                continue;
            }
            break;
        } while (true);
        arrclass = null;
        object = null;
        if (method != null && method.getReturnType() == class_2) {
            method.setAccessible(true);
            int n = method.getModifiers();
            if ((n & 1032) != 0) {
                return null;
            }
            if ((n & 5) != 0) {
                return method;
            }
            if ((n & 2) != 0) {
                if (class_ == class_3) {
                    object = method;
                }
                return object;
            }
            object = arrclass;
            if (ObjectStreamClass.packageEquals(class_, class_3)) {
                object = method;
            }
            return object;
        }
        return null;
    }

    private static String getMethodSignature(Class<?>[] arrclass, Class<?> class_) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        for (int i = 0; i < arrclass.length; ++i) {
            stringBuilder.append(ObjectStreamClass.getClassSignature(arrclass[i]));
        }
        stringBuilder.append(')');
        stringBuilder.append(ObjectStreamClass.getClassSignature(class_));
        return stringBuilder.toString();
    }

    private static String getPackageName(Class<?> object) {
        String string = ((Class)object).getName();
        int n = string.lastIndexOf(91);
        object = string;
        if (n >= 0) {
            object = string.substring(n + 2);
        }
        object = (n = ((String)object).lastIndexOf(46)) >= 0 ? ((String)object).substring(0, n) : "";
        return object;
    }

    private static Method getPrivateMethod(Class<?> genericDeclaration, String object, Class<?>[] arrclass, Class<?> class_) {
        Object var4_5 = null;
        try {
            genericDeclaration = ((Class)genericDeclaration).getDeclaredMethod((String)object, arrclass);
            ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            int n = ((Method)genericDeclaration).getModifiers();
            object = ((Method)genericDeclaration).getReturnType();
            if (object != class_ || (n & 8) != 0 || (n & 2) == 0) {
                genericDeclaration = var4_5;
            }
            return genericDeclaration;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    private static FieldReflector getReflector(ObjectStreamField[] object, ObjectStreamClass objectStreamClass) throws InvalidClassException {
        Class class_ = objectStreamClass != null && ((ObjectStreamField[])object).length > 0 ? objectStreamClass.cl : null;
        ObjectStreamClass.processQueue(Caches.reflectorsQueue, Caches.reflectors);
        FieldReflectorKey fieldReflectorKey = new FieldReflectorKey(class_, (ObjectStreamField[])object, Caches.reflectorsQueue);
        Reference reference = (Reference)Caches.reflectors.get(fieldReflectorKey);
        class_ = null;
        if (reference != null) {
            class_ = reference.get();
        }
        Reference reference2 = null;
        Object object2 = class_;
        Object object3 = reference2;
        if (class_ == null) {
            EntryFuture entryFuture = new EntryFuture();
            SoftReference<EntryFuture> softReference = new SoftReference<EntryFuture>(entryFuture);
            object2 = class_;
            object3 = reference;
            do {
                if (object3 != null) {
                    Caches.reflectors.remove(fieldReflectorKey, object3);
                }
                object3 = Caches.reflectors.putIfAbsent(fieldReflectorKey, softReference);
                class_ = object2;
                if (object3 != null) {
                    class_ = ((Reference)object3).get();
                }
                if (object3 == null) break;
                object2 = class_;
            } while (class_ == null);
            object2 = class_;
            object3 = reference2;
            if (class_ == null) {
                object3 = entryFuture;
                object2 = class_;
            }
        }
        if (object2 instanceof FieldReflector) {
            return (FieldReflector)object2;
        }
        if (object2 instanceof EntryFuture) {
            class_ = ((EntryFuture)object2).get();
        } else {
            class_ = object2;
            if (object2 == null) {
                try {
                    class_ = new FieldReflector(ObjectStreamClass.matchFields((ObjectStreamField[])object, objectStreamClass));
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                ((EntryFuture)object3).set(class_);
                Caches.reflectors.put(fieldReflectorKey, new SoftReference<Object>(class_));
            }
        }
        if (class_ instanceof FieldReflector) {
            return (FieldReflector)((Object)class_);
        }
        if (!(class_ instanceof InvalidClassException)) {
            if (!(class_ instanceof RuntimeException)) {
                if (class_ instanceof Error) {
                    throw (Error)((Object)class_);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("unexpected entry: ");
                ((StringBuilder)object).append(class_);
                throw new InternalError(((StringBuilder)object).toString());
            }
            throw (RuntimeException)((Object)class_);
        }
        throw (InvalidClassException)((Object)class_);
    }

    private static ObjectStreamField[] getSerialFields(Class<?> class_) throws InvalidClassException {
        Object[] arrobject;
        if (Serializable.class.isAssignableFrom(class_) && !Externalizable.class.isAssignableFrom(class_) && !Proxy.isProxyClass(class_) && !class_.isInterface()) {
            ObjectStreamField[] arrobjectStreamField = ObjectStreamClass.getDeclaredSerialFields(class_);
            arrobject = arrobjectStreamField;
            if (arrobjectStreamField == null) {
                arrobject = ObjectStreamClass.getDefaultSerialFields(class_);
            }
            Arrays.sort(arrobject);
        } else {
            arrobject = NO_FIELDS;
        }
        return arrobject;
    }

    private static Constructor<?> getSerializableConstructor(Class<?> class_) {
        block6 : {
            GenericDeclaration genericDeclaration;
            GenericDeclaration genericDeclaration2;
            block7 : {
                genericDeclaration2 = class_;
                while (Serializable.class.isAssignableFrom((Class<?>)genericDeclaration2)) {
                    genericDeclaration2 = genericDeclaration = ((Class)genericDeclaration2).getSuperclass();
                    if (genericDeclaration != null) continue;
                    return null;
                }
                try {
                    genericDeclaration = ((Class)genericDeclaration2).getDeclaredConstructor(null);
                    int n = ((Constructor)genericDeclaration).getModifiers();
                    if ((n & 2) != 0) break block6;
                    if ((n & 5) != 0) break block7;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    return null;
                }
                if (!ObjectStreamClass.packageEquals(class_, genericDeclaration2)) break block6;
            }
            genericDeclaration2 = genericDeclaration;
            if (((Constructor)genericDeclaration).getDeclaringClass() != class_) {
                genericDeclaration2 = ((Constructor)genericDeclaration).serializationCopy(((Constructor)genericDeclaration).getDeclaringClass(), class_);
            }
            ((AccessibleObject)((Object)genericDeclaration2)).setAccessible(true);
            return genericDeclaration2;
        }
        return null;
    }

    private ObjectStreamClass getVariantFor(Class<?> class_) throws InvalidClassException {
        if (this.cl == class_) {
            return this;
        }
        ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        if (this.isProxy) {
            objectStreamClass.initProxy(class_, null, this.superDesc);
        } else {
            objectStreamClass.initNonProxy(this, class_, null, this.superDesc);
        }
        return objectStreamClass;
    }

    private static native boolean hasStaticInitializer(Class<?> var0, boolean var1);

    static /* synthetic */ void lambda$static$0(Class class_, long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Class ");
        stringBuilder.append(class_.getCanonicalName());
        stringBuilder.append(" relies on its default SUID which is dependent on the app's targetSdkVersion. To avoid problems during upgrade add the following to class ");
        stringBuilder.append(class_.getCanonicalName());
        stringBuilder.append("\n    private static final long serialVersionUID = ");
        stringBuilder.append(l);
        stringBuilder.append("L;");
        System.logW(stringBuilder.toString());
    }

    public static ObjectStreamClass lookup(Class<?> class_) {
        return ObjectStreamClass.lookup(class_, false);
    }

    static ObjectStreamClass lookup(Class<?> serializable, boolean bl) {
        if (!bl && !Serializable.class.isAssignableFrom((Class<?>)serializable)) {
            return null;
        }
        ObjectStreamClass.processQueue(Caches.localDescsQueue, Caches.localDescs);
        WeakClassKey weakClassKey = new WeakClassKey((Class<?>)serializable, Caches.localDescsQueue);
        Reference reference = (Reference)Caches.localDescs.get(weakClassKey);
        Object object = null;
        if (reference != null) {
            object = reference.get();
        }
        EntryFuture entryFuture = null;
        Object object2 = object;
        Object object3 = entryFuture;
        if (object == null) {
            EntryFuture entryFuture2 = new EntryFuture();
            SoftReference<EntryFuture> softReference = new SoftReference<EntryFuture>(entryFuture2);
            object2 = object;
            object3 = reference;
            do {
                if (object3 != null) {
                    Caches.localDescs.remove(weakClassKey, object3);
                }
                object3 = Caches.localDescs.putIfAbsent(weakClassKey, softReference);
                object = object2;
                if (object3 != null) {
                    object = ((Reference)object3).get();
                }
                if (object3 == null) break;
                object2 = object;
            } while (object == null);
            object2 = object;
            object3 = entryFuture;
            if (object == null) {
                object3 = entryFuture2;
                object2 = object;
            }
        }
        if (object2 instanceof ObjectStreamClass) {
            return (ObjectStreamClass)object2;
        }
        object = object2;
        if (object2 instanceof EntryFuture) {
            object3 = (EntryFuture)object2;
            object = ((EntryFuture)object3).getOwner() == Thread.currentThread() ? null : ((EntryFuture)object3).get();
        }
        object2 = object;
        if (object == null) {
            try {
                object2 = new ObjectStreamClass((Class<?>)serializable);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (((EntryFuture)object3).set(object2)) {
                Caches.localDescs.put(weakClassKey, new SoftReference<Object>(object2));
            } else {
                object2 = ((EntryFuture)object3).get();
            }
        }
        if (object2 instanceof ObjectStreamClass) {
            return object2;
        }
        if (!(object2 instanceof RuntimeException)) {
            if (object2 instanceof Error) {
                throw (Error)object2;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("unexpected entry: ");
            ((StringBuilder)serializable).append(object2);
            throw new InternalError(((StringBuilder)serializable).toString());
        }
        throw (RuntimeException)object2;
    }

    public static ObjectStreamClass lookupAny(Class<?> class_) {
        return ObjectStreamClass.lookup(class_, true);
    }

    private static ObjectStreamField[] matchFields(ObjectStreamField[] arrobjectStreamField, ObjectStreamClass object) throws InvalidClassException {
        ObjectStreamField[] arrobjectStreamField2 = object != null ? ((ObjectStreamClass)object).fields : NO_FIELDS;
        ObjectStreamField[] arrobjectStreamField3 = new ObjectStreamField[arrobjectStreamField.length];
        for (int i = 0; i < arrobjectStreamField.length; ++i) {
            Object object2;
            ObjectStreamField objectStreamField = arrobjectStreamField[i];
            object = null;
            for (int j = 0; j < arrobjectStreamField2.length; ++j) {
                ObjectStreamField objectStreamField2 = arrobjectStreamField2[j];
                object2 = object;
                if (objectStreamField.getName().equals(objectStreamField2.getName())) {
                    object2 = object;
                    if (objectStreamField.getSignature().equals(objectStreamField2.getSignature())) {
                        object2 = objectStreamField2.getField() != null ? new ObjectStreamField(objectStreamField2.getField(), objectStreamField2.isUnshared(), false) : new ObjectStreamField(objectStreamField2.getName(), objectStreamField2.getSignature(), objectStreamField2.isUnshared());
                    }
                }
                object = object2;
            }
            object2 = object;
            if (object == null) {
                object2 = new ObjectStreamField(objectStreamField.getName(), objectStreamField.getSignature(), false);
            }
            ((ObjectStreamField)object2).setOffset(objectStreamField.getOffset());
            arrobjectStreamField3[i] = object2;
        }
        return arrobjectStreamField3;
    }

    private static Object newInstance(Class<?> serializable, long l) {
        int n = VMRuntime.getRuntime().getTargetSdkVersion();
        if (n > 0 && n <= 24) {
            System.logE("WARNING: ObjectStreamClass.newInstance(Class<?>, long) is private API andwill be removed in a future Android release.");
            return Unsafe.getUnsafe().allocateInstance((Class<?>)serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("ObjectStreamClass.newInstance(Class<?>, long) is not supported on SDK ");
        ((StringBuilder)serializable).append(n);
        throw new UnsupportedOperationException(((StringBuilder)serializable).toString());
    }

    private static boolean packageEquals(Class<?> class_, Class<?> class_2) {
        boolean bl = class_.getClassLoader() == class_2.getClassLoader() && ObjectStreamClass.getPackageName(class_).equals(ObjectStreamClass.getPackageName(class_2));
        return bl;
    }

    static void processQueue(ReferenceQueue<Class<?>> referenceQueue, ConcurrentMap<? extends WeakReference<Class<?>>, ?> concurrentMap) {
        Reference<Class<?>> reference;
        while ((reference = referenceQueue.poll()) != null) {
            concurrentMap.remove(reference);
        }
    }

    private final void requireInitialized() {
        if (this.initialized) {
            return;
        }
        throw new InternalError("Unexpected call when not initialized");
    }

    private static void throwMiscException(Throwable throwable) throws IOException {
        if (!(throwable instanceof RuntimeException)) {
            if (throwable instanceof Error) {
                throw (Error)throwable;
            }
            IOException iOException = new IOException("unexpected exception type");
            iOException.initCause(throwable);
            throw iOException;
        }
        throw (RuntimeException)throwable;
    }

    void checkDefaultSerialize() throws InvalidClassException {
        this.requireInitialized();
        ExceptionInfo exceptionInfo = this.defaultSerializeEx;
        if (exceptionInfo == null) {
            return;
        }
        throw exceptionInfo.newInvalidClassException();
    }

    void checkDeserialize() throws InvalidClassException {
        this.requireInitialized();
        ExceptionInfo exceptionInfo = this.deserializeEx;
        if (exceptionInfo == null) {
            return;
        }
        throw exceptionInfo.newInvalidClassException();
    }

    void checkSerialize() throws InvalidClassException {
        this.requireInitialized();
        ExceptionInfo exceptionInfo = this.serializeEx;
        if (exceptionInfo == null) {
            return;
        }
        throw exceptionInfo.newInvalidClassException();
    }

    @CallerSensitive
    public Class<?> forClass() {
        if (this.cl == null) {
            return null;
        }
        this.requireInitialized();
        if (System.getSecurityManager() != null && ReflectUtil.needsPackageAccessCheck(Reflection.getCallerClass().getClassLoader(), this.cl.getClassLoader())) {
            ReflectUtil.checkPackageAccess(this.cl);
        }
        return this.cl;
    }

    ClassDataSlot[] getClassDataLayout() throws InvalidClassException {
        if (this.dataLayout == null) {
            this.dataLayout = this.getClassDataLayout0();
        }
        return this.dataLayout;
    }

    public ObjectStreamField getField(String string) {
        return this.getField(string, null);
    }

    ObjectStreamField getField(String string, Class<?> class_) {
        Object object;
        for (int i = 0; i < ((ObjectStreamField[])(object = this.fields)).length; ++i) {
            ObjectStreamField objectStreamField = object[i];
            if (!objectStreamField.getName().equals(string)) continue;
            if (class_ != null && (class_ != Object.class || objectStreamField.isPrimitive())) {
                object = objectStreamField.getType();
                if (object == null || !class_.isAssignableFrom((Class<?>)object)) continue;
                return objectStreamField;
            }
            return objectStreamField;
        }
        return null;
    }

    public ObjectStreamField[] getFields() {
        return this.getFields(true);
    }

    ObjectStreamField[] getFields(boolean bl) {
        ObjectStreamField[] arrobjectStreamField;
        ObjectStreamField[] arrobjectStreamField2 = arrobjectStreamField = this.fields;
        if (bl) {
            arrobjectStreamField2 = (ObjectStreamField[])arrobjectStreamField.clone();
        }
        return arrobjectStreamField2;
    }

    ObjectStreamClass getLocalDesc() {
        this.requireInitialized();
        return this.localDesc;
    }

    public String getName() {
        return this.name;
    }

    int getNumObjFields() {
        return this.numObjFields;
    }

    void getObjFieldValues(Object object, Object[] arrobject) {
        this.fieldRefl.getObjFieldValues(object, arrobject);
    }

    int getPrimDataSize() {
        return this.primDataSize;
    }

    void getPrimFieldValues(Object object, byte[] arrby) {
        this.fieldRefl.getPrimFieldValues(object, arrby);
    }

    ClassNotFoundException getResolveException() {
        return this.resolveEx;
    }

    public long getSerialVersionUID() {
        if (this.suid == null) {
            this.suid = AccessController.doPrivileged(new PrivilegedAction<Long>(){

                @Override
                public Long run() {
                    return ObjectStreamClass.computeDefaultSUID(ObjectStreamClass.this.cl);
                }
            });
        }
        return this.suid;
    }

    ObjectStreamClass getSuperDesc() {
        this.requireInitialized();
        return this.superDesc;
    }

    boolean hasBlockExternalData() {
        this.requireInitialized();
        return this.hasBlockExternalData;
    }

    boolean hasReadObjectMethod() {
        this.requireInitialized();
        boolean bl = this.readObjectMethod != null;
        return bl;
    }

    boolean hasReadObjectNoDataMethod() {
        this.requireInitialized();
        boolean bl = this.readObjectNoDataMethod != null;
        return bl;
    }

    boolean hasReadResolveMethod() {
        this.requireInitialized();
        boolean bl = this.readResolveMethod != null;
        return bl;
    }

    boolean hasWriteObjectData() {
        this.requireInitialized();
        return this.hasWriteObjectData;
    }

    boolean hasWriteObjectMethod() {
        this.requireInitialized();
        boolean bl = this.writeObjectMethod != null;
        return bl;
    }

    boolean hasWriteReplaceMethod() {
        this.requireInitialized();
        boolean bl = this.writeReplaceMethod != null;
        return bl;
    }

    void initNonProxy(ObjectStreamClass object, Class<?> serializable, ClassNotFoundException object2, ObjectStreamClass objectStreamClass) throws InvalidClassException {
        long l;
        ObjectStreamClass objectStreamClass2;
        block7 : {
            block8 : {
                ObjectStreamClass objectStreamClass3;
                block9 : {
                    block10 : {
                        l = ((ObjectStreamClass)object).getSerialVersionUID();
                        objectStreamClass2 = null;
                        if (serializable == null) break block7;
                        objectStreamClass3 = ObjectStreamClass.lookup(serializable, true);
                        if (objectStreamClass3.isProxy) break block8;
                        boolean bl = ((ObjectStreamClass)object).isEnum;
                        if (bl != objectStreamClass3.isEnum) {
                            object = bl ? "cannot bind enum descriptor to a non-enum class" : "cannot bind non-enum descriptor to an enum class";
                            throw new InvalidClassException((String)object);
                        }
                        if (((ObjectStreamClass)object).serializable == objectStreamClass3.serializable && !((Class)serializable).isArray() && l != objectStreamClass3.getSerialVersionUID()) {
                            object = objectStreamClass3.name;
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("local class incompatible: stream classdesc serialVersionUID = ");
                            ((StringBuilder)serializable).append(l);
                            ((StringBuilder)serializable).append(", local class serialVersionUID = ");
                            ((StringBuilder)serializable).append(objectStreamClass3.getSerialVersionUID());
                            throw new InvalidClassException((String)object, ((StringBuilder)serializable).toString());
                        }
                        if (!ObjectStreamClass.classNamesEqual(((ObjectStreamClass)object).name, objectStreamClass3.name)) break block9;
                        objectStreamClass2 = objectStreamClass3;
                        if (((ObjectStreamClass)object).isEnum) break block7;
                        if (((ObjectStreamClass)object).serializable == objectStreamClass3.serializable && ((ObjectStreamClass)object).externalizable != objectStreamClass3.externalizable) {
                            throw new InvalidClassException(objectStreamClass3.name, "Serializable incompatible with Externalizable");
                        }
                        boolean bl2 = ((ObjectStreamClass)object).serializable;
                        if (bl2 != objectStreamClass3.serializable || (bl = ((ObjectStreamClass)object).externalizable) != objectStreamClass3.externalizable) break block10;
                        objectStreamClass2 = objectStreamClass3;
                        if (bl2) break block7;
                        objectStreamClass2 = objectStreamClass3;
                        if (bl) break block7;
                    }
                    this.deserializeEx = new ExceptionInfo(objectStreamClass3.name, "class invalid for deserialization");
                    objectStreamClass2 = objectStreamClass3;
                    break block7;
                }
                object2 = objectStreamClass3.name;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("local class name incompatible with stream class name \"");
                ((StringBuilder)serializable).append(((ObjectStreamClass)object).name);
                ((StringBuilder)serializable).append("\"");
                throw new InvalidClassException((String)object2, ((StringBuilder)serializable).toString());
            }
            throw new InvalidClassException("cannot bind non-proxy descriptor to a proxy class");
        }
        this.cl = serializable;
        this.resolveEx = object2;
        this.superDesc = objectStreamClass;
        this.name = ((ObjectStreamClass)object).name;
        this.suid = l;
        this.isProxy = false;
        this.isEnum = ((ObjectStreamClass)object).isEnum;
        this.serializable = ((ObjectStreamClass)object).serializable;
        this.externalizable = ((ObjectStreamClass)object).externalizable;
        this.hasBlockExternalData = ((ObjectStreamClass)object).hasBlockExternalData;
        this.hasWriteObjectData = ((ObjectStreamClass)object).hasWriteObjectData;
        this.fields = ((ObjectStreamClass)object).fields;
        this.primDataSize = ((ObjectStreamClass)object).primDataSize;
        this.numObjFields = ((ObjectStreamClass)object).numObjFields;
        if (objectStreamClass2 != null) {
            this.localDesc = objectStreamClass2;
            object = this.localDesc;
            this.writeObjectMethod = ((ObjectStreamClass)object).writeObjectMethod;
            this.readObjectMethod = ((ObjectStreamClass)object).readObjectMethod;
            this.readObjectNoDataMethod = ((ObjectStreamClass)object).readObjectNoDataMethod;
            this.writeReplaceMethod = ((ObjectStreamClass)object).writeReplaceMethod;
            this.readResolveMethod = ((ObjectStreamClass)object).readResolveMethod;
            if (this.deserializeEx == null) {
                this.deserializeEx = ((ObjectStreamClass)object).deserializeEx;
            }
            this.cons = this.localDesc.cons;
        }
        this.fieldRefl = ObjectStreamClass.getReflector(this.fields, this.localDesc);
        this.fields = this.fieldRefl.getFields();
        this.initialized = true;
    }

    void initProxy(Class<?> serializable, ClassNotFoundException classNotFoundException, ObjectStreamClass objectStreamClass) throws InvalidClassException {
        ObjectStreamClass objectStreamClass2 = null;
        if (serializable != null) {
            objectStreamClass2 = ObjectStreamClass.lookup(serializable, true);
            if (!objectStreamClass2.isProxy) {
                throw new InvalidClassException("cannot bind proxy descriptor to a non-proxy class");
            }
        }
        this.cl = serializable;
        this.resolveEx = classNotFoundException;
        this.superDesc = objectStreamClass;
        this.isProxy = true;
        this.serializable = true;
        this.suid = 0L;
        this.fields = NO_FIELDS;
        if (objectStreamClass2 != null) {
            this.localDesc = objectStreamClass2;
            serializable = this.localDesc;
            this.name = ((ObjectStreamClass)serializable).name;
            this.externalizable = ((ObjectStreamClass)serializable).externalizable;
            this.writeReplaceMethod = ((ObjectStreamClass)serializable).writeReplaceMethod;
            this.readResolveMethod = ((ObjectStreamClass)serializable).readResolveMethod;
            this.deserializeEx = ((ObjectStreamClass)serializable).deserializeEx;
            this.cons = ((ObjectStreamClass)serializable).cons;
        }
        this.fieldRefl = ObjectStreamClass.getReflector(this.fields, this.localDesc);
        this.initialized = true;
    }

    void invokeReadObject(Object object, ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException, UnsupportedOperationException {
        this.requireInitialized();
        Method method = this.readObjectMethod;
        if (method != null) {
            Throwable throwable;
            block4 : {
                block5 : {
                    try {
                        method.invoke(object, objectInputStream);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        throw new InternalError(illegalAccessException);
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        throwable = invocationTargetException.getTargetException();
                        if (throwable instanceof ClassNotFoundException) break block4;
                        if (throwable instanceof IOException) break block5;
                        ObjectStreamClass.throwMiscException(throwable);
                    }
                    return;
                }
                throw (IOException)throwable;
            }
            throw (ClassNotFoundException)throwable;
        }
        throw new UnsupportedOperationException();
    }

    void invokeReadObjectNoData(Object object) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        Method method = this.readObjectNoDataMethod;
        if (method != null) {
            Throwable throwable;
            block4 : {
                try {
                    method.invoke(object, null);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new InternalError(illegalAccessException);
                }
                catch (InvocationTargetException invocationTargetException) {
                    throwable = invocationTargetException.getTargetException();
                    if (throwable instanceof ObjectStreamException) break block4;
                    ObjectStreamClass.throwMiscException(throwable);
                }
                return;
            }
            throw (ObjectStreamException)throwable;
        }
        throw new UnsupportedOperationException();
    }

    Object invokeReadResolve(Object object) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        Method method = this.readResolveMethod;
        if (method != null) {
            try {
                object = method.invoke(object, null);
                return object;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new InternalError(illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                Throwable throwable = invocationTargetException.getTargetException();
                if (throwable instanceof ObjectStreamException) {
                    throw (ObjectStreamException)throwable;
                }
                ObjectStreamClass.throwMiscException(throwable);
                throw new InternalError(throwable);
            }
        }
        throw new UnsupportedOperationException();
    }

    void invokeWriteObject(Object object, ObjectOutputStream objectOutputStream) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        Method method = this.writeObjectMethod;
        if (method != null) {
            Throwable throwable;
            block4 : {
                try {
                    method.invoke(object, objectOutputStream);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new InternalError(illegalAccessException);
                }
                catch (InvocationTargetException invocationTargetException) {
                    throwable = invocationTargetException.getTargetException();
                    if (throwable instanceof IOException) break block4;
                    ObjectStreamClass.throwMiscException(throwable);
                }
                return;
            }
            throw (IOException)throwable;
        }
        throw new UnsupportedOperationException();
    }

    Object invokeWriteReplace(Object object) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        Method method = this.writeReplaceMethod;
        if (method != null) {
            try {
                object = method.invoke(object, null);
                return object;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new InternalError(illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                Throwable throwable = invocationTargetException.getTargetException();
                if (throwable instanceof ObjectStreamException) {
                    throw (ObjectStreamException)throwable;
                }
                ObjectStreamClass.throwMiscException(throwable);
                throw new InternalError(throwable);
            }
        }
        throw new UnsupportedOperationException();
    }

    boolean isEnum() {
        this.requireInitialized();
        return this.isEnum;
    }

    boolean isExternalizable() {
        this.requireInitialized();
        return this.externalizable;
    }

    boolean isInstantiable() {
        this.requireInitialized();
        boolean bl = this.cons != null;
        return bl;
    }

    boolean isProxy() {
        this.requireInitialized();
        return this.isProxy;
    }

    boolean isSerializable() {
        this.requireInitialized();
        return this.serializable;
    }

    Object newInstance() throws InstantiationException, InvocationTargetException, UnsupportedOperationException {
        this.requireInitialized();
        Constructor<?> constructor = this.cons;
        if (constructor != null) {
            try {
                constructor = constructor.newInstance(new Object[0]);
                return constructor;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new InternalError(illegalAccessException);
            }
        }
        throw new UnsupportedOperationException();
    }

    void readNonProxy(ObjectInputStream object) throws IOException, ClassNotFoundException {
        this.name = ((ObjectInputStream)object).readUTF();
        this.suid = ((ObjectInputStream)object).readLong();
        this.isProxy = false;
        int n = ((ObjectInputStream)object).readByte();
        boolean bl = (n & 1) != 0;
        this.hasWriteObjectData = bl;
        bl = (n & 8) != 0;
        this.hasBlockExternalData = bl;
        bl = (n & 4) != 0;
        this.externalizable = bl;
        int n2 = (n & 2) != 0 ? 1 : 0;
        if (this.externalizable && n2 != 0) {
            throw new InvalidClassException(this.name, "serializable and externalizable flags conflict");
        }
        bl = this.externalizable || n2 != 0;
        this.serializable = bl;
        bl = (n & 16) != 0;
        this.isEnum = bl;
        if (this.isEnum && this.suid != 0L) {
            String string = this.name;
            object = new StringBuilder();
            ((StringBuilder)object).append("enum descriptor has non-zero serialVersionUID: ");
            ((StringBuilder)object).append(this.suid);
            throw new InvalidClassException(string, ((StringBuilder)object).toString());
        }
        n = ((ObjectInputStream)object).readShort();
        if (this.isEnum && n != 0) {
            object = this.name;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("enum descriptor has non-zero field count: ");
            stringBuilder.append(n);
            throw new InvalidClassException((String)object, stringBuilder.toString());
        }
        Object object2 = n > 0 ? new ObjectStreamField[n] : NO_FIELDS;
        this.fields = object2;
        for (n2 = 0; n2 < n; ++n2) {
            char c = (char)((ObjectInputStream)object).readByte();
            String string = ((ObjectInputStream)object).readUTF();
            object2 = c != 'L' && c != '[' ? new String(new char[]{c}) : ((ObjectInputStream)object).readTypeString();
            try {
                this.fields[n2] = new ObjectStreamField(string, (String)object2, false);
                continue;
            }
            catch (RuntimeException runtimeException) {
                object = this.name;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid descriptor for field ");
                stringBuilder.append(string);
                throw (IOException)new InvalidClassException((String)object, stringBuilder.toString()).initCause(runtimeException);
            }
        }
        this.computeFieldOffsets();
    }

    void setObjFieldValues(Object object, Object[] arrobject) {
        this.fieldRefl.setObjFieldValues(object, arrobject);
    }

    void setPrimFieldValues(Object object, byte[] arrby) {
        this.fieldRefl.setPrimFieldValues(object, arrby);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append(": static final long serialVersionUID = ");
        stringBuilder.append(this.getSerialVersionUID());
        stringBuilder.append("L;");
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void writeNonProxy(ObjectOutputStream var1_1) throws IOException {
        block7 : {
            var1_1.writeUTF(this.name);
            var1_1.writeLong(this.getSerialVersionUID());
            var2_2 = 0;
            if (!this.externalizable) break block7;
            var2_2 = var3_3 = (int)(0 | 4);
            if (var1_1.getProtocolVersion() != 1) {
                var2_2 = (byte)(var3_3 | 8);
            }
            ** GOTO lbl-1000
        }
        if (this.serializable) {
            var3_3 = (byte)(0 | 2);
        } else lbl-1000: // 2 sources:
        {
            var3_3 = var2_2;
        }
        var2_2 = var3_3;
        if (this.hasWriteObjectData) {
            var2_2 = (byte)(var3_3 | 1);
        }
        var3_3 = var2_2;
        if (this.isEnum) {
            var3_3 = (byte)(var2_2 | 16);
        }
        var1_1.writeByte(var3_3);
        var1_1.writeShort(this.fields.length);
        var2_2 = 0;
        while (var2_2 < ((ObjectStreamField[])(var4_4 = this.fields)).length) {
            var4_4 = var4_4[var2_2];
            var1_1.writeByte(var4_4.getTypeCode());
            var1_1.writeUTF(var4_4.getName());
            if (!var4_4.isPrimitive()) {
                var1_1.writeTypeString(var4_4.getTypeString());
            }
            ++var2_2;
        }
    }

    private static class Caches {
        static final ConcurrentMap<WeakClassKey, Reference<?>> localDescs = new ConcurrentHashMap();
        private static final ReferenceQueue<Class<?>> localDescsQueue;
        static final ConcurrentMap<FieldReflectorKey, Reference<?>> reflectors;
        private static final ReferenceQueue<Class<?>> reflectorsQueue;

        static {
            reflectors = new ConcurrentHashMap();
            localDescsQueue = new ReferenceQueue();
            reflectorsQueue = new ReferenceQueue();
        }

        private Caches() {
        }
    }

    static class ClassDataSlot {
        final ObjectStreamClass desc;
        final boolean hasData;

        ClassDataSlot(ObjectStreamClass objectStreamClass, boolean bl) {
            this.desc = objectStreamClass;
            this.hasData = bl;
        }
    }

    public static interface DefaultSUIDCompatibilityListener {
        public void warnDefaultSUIDTargetVersionDependent(Class<?> var1, long var2);
    }

    private static class EntryFuture {
        private static final Object unset = new Object();
        private Object entry = unset;
        private final Thread owner = Thread.currentThread();

        private EntryFuture() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        Object get() {
            synchronized (this) {
                PrivilegedAction<Void> privilegedAction;
                boolean bl = false;
                do {
                    privilegedAction = this.entry;
                    Object object = unset;
                    if (privilegedAction != object) break;
                    try {
                        this.wait();
                        continue;
                    }
                    catch (InterruptedException interruptedException) {
                        bl = true;
                        continue;
                    }
                    break;
                } while (true);
                if (!bl) return this.entry;
                privilegedAction = new PrivilegedAction<Void>(){

                    @Override
                    public Void run() {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                };
                AccessController.doPrivileged(privilegedAction);
                return this.entry;
            }
        }

        Thread getOwner() {
            return this.owner;
        }

        boolean set(Object object) {
            synchronized (this) {
                block4 : {
                    Object object2 = this.entry;
                    Object object3 = unset;
                    if (object2 == object3) break block4;
                    return false;
                }
                this.entry = object;
                this.notifyAll();
                return true;
            }
        }

    }

    private static class ExceptionInfo {
        private final String className;
        private final String message;

        ExceptionInfo(String string, String string2) {
            this.className = string;
            this.message = string2;
        }

        InvalidClassException newInvalidClassException() {
            return new InvalidClassException(this.className, this.message);
        }
    }

    private static class FieldReflector {
        private static final Unsafe unsafe = Unsafe.getUnsafe();
        private final ObjectStreamField[] fields;
        private final int numPrimFields;
        private final int[] offsets;
        private final long[] readKeys;
        private final char[] typeCodes;
        private final Class<?>[] types;
        private final long[] writeKeys;

        FieldReflector(ObjectStreamField[] arrobjectStreamField) {
            this.fields = arrobjectStreamField;
            int n = arrobjectStreamField.length;
            this.readKeys = new long[n];
            this.writeKeys = new long[n];
            this.offsets = new int[n];
            this.typeCodes = new char[n];
            ArrayList<Object> arrayList = new ArrayList<Object>();
            HashSet<Long> hashSet = new HashSet<Long>();
            for (int i = 0; i < n; ++i) {
                Class<Object> class_ = arrobjectStreamField[i];
                Field field = ((ObjectStreamField)((Object)class_)).getField();
                long l = -1L;
                long l2 = field != null ? unsafe.objectFieldOffset(field) : -1L;
                this.readKeys[i] = l2;
                long[] arrl = this.writeKeys;
                if (!hashSet.add(l2)) {
                    l2 = l;
                }
                arrl[i] = l2;
                this.offsets[i] = ((ObjectStreamField)((Object)class_)).getOffset();
                this.typeCodes[i] = ((ObjectStreamField)((Object)class_)).getTypeCode();
                if (((ObjectStreamField)((Object)class_)).isPrimitive()) continue;
                class_ = field != null ? field.getType() : null;
                arrayList.add(class_);
            }
            this.types = arrayList.toArray(new Class[arrayList.size()]);
            this.numPrimFields = n - this.types.length;
        }

        ObjectStreamField[] getFields() {
            return this.fields;
        }

        void getObjFieldValues(Object object, Object[] arrobject) {
            if (object != null) {
                for (int i = this.numPrimFields; i < this.fields.length; ++i) {
                    char c = this.typeCodes[i];
                    if (c != 'L' && c != '[') {
                        throw new InternalError();
                    }
                    arrobject[this.offsets[i]] = unsafe.getObject(object, this.readKeys[i]);
                }
                return;
            }
            throw new NullPointerException();
        }

        void getPrimFieldValues(Object object, byte[] arrby) {
            if (object != null) {
                for (int i = 0; i < this.numPrimFields; ++i) {
                    long l = this.readKeys[i];
                    int n = this.offsets[i];
                    char c = this.typeCodes[i];
                    if (c != 'F') {
                        if (c != 'S') {
                            if (c != 'Z') {
                                if (c != 'I') {
                                    if (c != 'J') {
                                        switch (c) {
                                            default: {
                                                throw new InternalError();
                                            }
                                            case 'D': {
                                                Bits.putDouble(arrby, n, unsafe.getDouble(object, l));
                                                break;
                                            }
                                            case 'C': {
                                                Bits.putChar(arrby, n, unsafe.getChar(object, l));
                                                break;
                                            }
                                            case 'B': {
                                                arrby[n] = unsafe.getByte(object, l);
                                                break;
                                            }
                                        }
                                        continue;
                                    }
                                    Bits.putLong(arrby, n, unsafe.getLong(object, l));
                                    continue;
                                }
                                Bits.putInt(arrby, n, unsafe.getInt(object, l));
                                continue;
                            }
                            Bits.putBoolean(arrby, n, unsafe.getBoolean(object, l));
                            continue;
                        }
                        Bits.putShort(arrby, n, unsafe.getShort(object, l));
                        continue;
                    }
                    Bits.putFloat(arrby, n, unsafe.getFloat(object, l));
                }
                return;
            }
            throw new NullPointerException();
        }

        void setObjFieldValues(Object object, Object[] object2) {
            if (object != null) {
                for (int i = this.numPrimFields; i < this.fields.length; ++i) {
                    long l = this.writeKeys[i];
                    if (l == -1L) continue;
                    char c = this.typeCodes[i];
                    if (c != 'L' && c != '[') {
                        throw new InternalError();
                    }
                    Object object3 = object2[this.offsets[i]];
                    if (object3 != null && !this.types[i - this.numPrimFields].isInstance(object3)) {
                        object2 = this.fields[i].getField();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("cannot assign instance of ");
                        stringBuilder.append(object3.getClass().getName());
                        stringBuilder.append(" to field ");
                        stringBuilder.append(((Field)object2).getDeclaringClass().getName());
                        stringBuilder.append(".");
                        stringBuilder.append(((Field)object2).getName());
                        stringBuilder.append(" of type ");
                        stringBuilder.append(((Field)object2).getType().getName());
                        stringBuilder.append(" in instance of ");
                        stringBuilder.append(object.getClass().getName());
                        throw new ClassCastException(stringBuilder.toString());
                    }
                    unsafe.putObject(object, l, object3);
                }
                return;
            }
            throw new NullPointerException();
        }

        void setPrimFieldValues(Object object, byte[] arrby) {
            if (object != null) {
                for (int i = 0; i < this.numPrimFields; ++i) {
                    long l = this.writeKeys[i];
                    if (l == -1L) continue;
                    int n = this.offsets[i];
                    char c = this.typeCodes[i];
                    if (c != 'F') {
                        if (c != 'S') {
                            if (c != 'Z') {
                                if (c != 'I') {
                                    if (c != 'J') {
                                        switch (c) {
                                            default: {
                                                throw new InternalError();
                                            }
                                            case 'D': {
                                                unsafe.putDouble(object, l, Bits.getDouble(arrby, n));
                                                break;
                                            }
                                            case 'C': {
                                                unsafe.putChar(object, l, Bits.getChar(arrby, n));
                                                break;
                                            }
                                            case 'B': {
                                                unsafe.putByte(object, l, arrby[n]);
                                                break;
                                            }
                                        }
                                        continue;
                                    }
                                    unsafe.putLong(object, l, Bits.getLong(arrby, n));
                                    continue;
                                }
                                unsafe.putInt(object, l, Bits.getInt(arrby, n));
                                continue;
                            }
                            unsafe.putBoolean(object, l, Bits.getBoolean(arrby, n));
                            continue;
                        }
                        unsafe.putShort(object, l, Bits.getShort(arrby, n));
                        continue;
                    }
                    unsafe.putFloat(object, l, Bits.getFloat(arrby, n));
                }
                return;
            }
            throw new NullPointerException();
        }
    }

    private static class FieldReflectorKey
    extends WeakReference<Class<?>> {
        private final int hash;
        private final boolean nullClass;
        private final String sigs;

        FieldReflectorKey(Class<?> class_, ObjectStreamField[] arrobjectStreamField, ReferenceQueue<Class<?>> object) {
            super(class_, object);
            boolean bl = class_ == null;
            this.nullClass = bl;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arrobjectStreamField.length; ++i) {
                object = arrobjectStreamField[i];
                stringBuilder.append(((ObjectStreamField)object).getName());
                stringBuilder.append(((ObjectStreamField)object).getSignature());
            }
            this.sigs = stringBuilder.toString();
            this.hash = System.identityHashCode(class_) + this.sigs.hashCode();
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (object instanceof FieldReflectorKey) {
                Class class_;
                object = (FieldReflectorKey)object;
                if (!(this.nullClass ? ((FieldReflectorKey)object).nullClass : (class_ = (Class)this.get()) != null && class_ == ((Reference)object).get()) || !this.sigs.equals(((FieldReflectorKey)object).sigs)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static class MemberSignature {
        public final Member member;
        public final String name;
        public final String signature;

        public MemberSignature(Constructor<?> constructor) {
            this.member = constructor;
            this.name = constructor.getName();
            this.signature = ObjectStreamClass.getMethodSignature(constructor.getParameterTypes(), Void.TYPE);
        }

        public MemberSignature(Field field) {
            this.member = field;
            this.name = field.getName();
            this.signature = ObjectStreamClass.getClassSignature(field.getType());
        }

        public MemberSignature(Method method) {
            this.member = method;
            this.name = method.getName();
            this.signature = ObjectStreamClass.getMethodSignature(method.getParameterTypes(), method.getReturnType());
        }
    }

    static class WeakClassKey
    extends WeakReference<Class<?>> {
        private final int hash;

        WeakClassKey(Class<?> class_, ReferenceQueue<Class<?>> referenceQueue) {
            super(class_, referenceQueue);
            this.hash = System.identityHashCode(class_);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (object instanceof WeakClassKey) {
                Object t = this.get();
                if (t == null || t != ((WeakClassKey)object).get()) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

}

