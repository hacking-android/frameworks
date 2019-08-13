/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMStack
 */
package java.io;

import dalvik.system.VMStack;
import java.io.Bits;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.ObjectInput;
import java.io.ObjectInputValidation;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.ObjectStreamField;
import java.io.SerialCallbackContext;
import java.io.Serializable;
import java.io.SerializablePermission;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.reflect.misc.ReflectUtil;

public class ObjectInputStream
extends InputStream
implements ObjectInput,
ObjectStreamConstants {
    private static final int NULL_HANDLE = -1;
    private static final HashMap<String, Class<?>> primClasses;
    private static final Object unsharedMarker;
    private final BlockDataInputStream bin;
    private boolean closed;
    private SerialCallbackContext curContext;
    private boolean defaultDataEnd = false;
    private int depth;
    private final boolean enableOverride;
    private boolean enableResolve;
    private final HandleTable handles;
    private int passHandle = -1;
    private byte[] primVals;
    private final ValidationList vlist;

    static {
        unsharedMarker = new Object();
        primClasses = new HashMap(8, 1.0f);
        primClasses.put("boolean", Boolean.TYPE);
        primClasses.put("byte", Byte.TYPE);
        primClasses.put("char", Character.TYPE);
        primClasses.put("short", Short.TYPE);
        primClasses.put("int", Integer.TYPE);
        primClasses.put("long", Long.TYPE);
        primClasses.put("float", Float.TYPE);
        primClasses.put("double", Double.TYPE);
        primClasses.put("void", Void.TYPE);
    }

    protected ObjectInputStream() throws IOException, SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
        }
        this.bin = null;
        this.handles = null;
        this.vlist = null;
        this.enableOverride = true;
    }

    public ObjectInputStream(InputStream inputStream) throws IOException {
        this.verifySubclass();
        this.bin = new BlockDataInputStream(inputStream);
        this.handles = new HandleTable(10);
        this.vlist = new ValidationList();
        this.enableOverride = false;
        this.readStreamHeader();
        this.bin.setBlockDataMode(true);
    }

    static /* synthetic */ boolean access$500(ObjectInputStream objectInputStream) {
        return objectInputStream.defaultDataEnd;
    }

    static /* synthetic */ void access$600(ObjectInputStream objectInputStream) throws StreamCorruptedException {
        objectInputStream.handleReset();
    }

    private static boolean auditSubclass(final Class<?> class_) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                for (Class class_2 = class_; class_2 != ObjectInputStream.class; class_2 = class_2.getSuperclass()) {
                    try {
                        class_2.getDeclaredMethod("readUnshared", null);
                        Boolean bl = Boolean.FALSE;
                        return bl;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        try {
                            class_2.getDeclaredMethod("readFields", null);
                            Boolean bl = Boolean.FALSE;
                            return bl;
                        }
                        catch (NoSuchMethodException noSuchMethodException2) {
                            continue;
                        }
                    }
                }
                return Boolean.TRUE;
            }
        });
    }

    private static native void bytesToDoubles(byte[] var0, int var1, double[] var2, int var3, int var4);

    private static native void bytesToFloats(byte[] var0, int var1, float[] var2, int var3, int var4);

    private Object checkResolve(Object object) throws IOException {
        if (this.enableResolve && this.handles.lookupException(this.passHandle) == null) {
            Object object2 = this.resolveObject(object);
            if (object2 != object) {
                this.handles.setObject(this.passHandle, object2);
            }
            return object2;
        }
        return object;
    }

    private void clear() {
        this.handles.clear();
        this.vlist.clear();
    }

    private static Object cloneArray(Object object) {
        if (object instanceof Object[]) {
            return ((Object[])object).clone();
        }
        if (object instanceof boolean[]) {
            return ((boolean[])object).clone();
        }
        if (object instanceof byte[]) {
            return ((byte[])object).clone();
        }
        if (object instanceof char[]) {
            return ((char[])object).clone();
        }
        if (object instanceof double[]) {
            return ((double[])object).clone();
        }
        if (object instanceof float[]) {
            return ((float[])object).clone();
        }
        if (object instanceof int[]) {
            return ((int[])object).clone();
        }
        if (object instanceof long[]) {
            return ((long[])object).clone();
        }
        if (object instanceof short[]) {
            return ((short[])object).clone();
        }
        throw new AssertionError();
    }

    private void defaultReadFields(Object object, ObjectStreamClass objectStreamClass) throws IOException {
        Object[] arrobject = objectStreamClass.forClass();
        if (arrobject != null && object != null && !arrobject.isInstance(object)) {
            throw new ClassCastException();
        }
        int n = objectStreamClass.getPrimDataSize();
        arrobject = this.primVals;
        if (arrobject == null || arrobject.length < n) {
            this.primVals = new byte[n];
        }
        this.bin.readFully(this.primVals, 0, n, false);
        if (object != null) {
            objectStreamClass.setPrimFieldValues(object, this.primVals);
        }
        int n2 = this.passHandle;
        ObjectStreamField[] arrobjectStreamField = objectStreamClass.getFields(false);
        arrobject = new Object[objectStreamClass.getNumObjFields()];
        int n3 = arrobjectStreamField.length;
        int n4 = arrobject.length;
        for (n = 0; n < arrobject.length; ++n) {
            ObjectStreamField objectStreamField = arrobjectStreamField[n3 - n4 + n];
            arrobject[n] = (byte)this.readObject0(objectStreamField.isUnshared());
            if (objectStreamField.getField() == null) continue;
            this.handles.markDependency(n2, this.passHandle);
        }
        if (object != null) {
            objectStreamClass.setObjFieldValues(object, arrobject);
        }
        this.passHandle = n2;
    }

    private void handleReset() throws StreamCorruptedException {
        if (this.depth <= 0) {
            this.clear();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unexpected reset; recursion depth: ");
        stringBuilder.append(this.depth);
        throw new StreamCorruptedException(stringBuilder.toString());
    }

    private boolean isCustomSubclass() {
        boolean bl = this.getClass().getClassLoader() != ObjectInputStream.class.getClassLoader();
        return bl;
    }

    private static ClassLoader latestUserDefinedLoader() {
        return VMStack.getClosestUserClassLoader();
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object readArray(boolean bl) throws IOException {
        void var6_9;
        if (this.bin.readByte() != 117) throw new InternalError();
        ObjectStreamClass objectStreamClass = this.readClassDesc(false);
        int n = this.bin.readInt();
        Object object = null;
        Object[] arrobject = null;
        Class<?> class_ = objectStreamClass.forClass();
        if (class_ != null) {
            arrobject = class_.getComponentType();
            object = Array.newInstance(arrobject, n);
        }
        HandleTable handleTable = this.handles;
        if (bl) {
            Object object2 = unsharedMarker;
        } else {
            Object object3 = object;
        }
        int n2 = handleTable.assign(var6_9);
        ClassNotFoundException classNotFoundException = objectStreamClass.getResolveException();
        if (classNotFoundException != null) {
            this.handles.markException(n2, classNotFoundException);
        }
        if (arrobject == null) {
            for (int i = 0; i < n; ++i) {
                this.readObject0(false);
            }
        } else if (arrobject.isPrimitive()) {
            if (arrobject == Integer.TYPE) {
                this.bin.readInts((int[])object, 0, n);
            } else if (arrobject == Byte.TYPE) {
                this.bin.readFully((byte[])object, 0, n, true);
            } else if (arrobject == Long.TYPE) {
                this.bin.readLongs((long[])object, 0, n);
            } else if (arrobject == Float.TYPE) {
                this.bin.readFloats((float[])object, 0, n);
            } else if (arrobject == Double.TYPE) {
                this.bin.readDoubles((double[])object, 0, n);
            } else if (arrobject == Short.TYPE) {
                this.bin.readShorts((short[])object, 0, n);
            } else if (arrobject == Character.TYPE) {
                this.bin.readChars((char[])object, 0, n);
            } else {
                if (arrobject != Boolean.TYPE) throw new InternalError();
                this.bin.readBooleans((boolean[])object, 0, n);
            }
        } else {
            arrobject = (Object[])object;
            for (int i = 0; i < n; ++i) {
                arrobject[i] = this.readObject0(false);
                this.handles.markDependency(n2, this.passHandle);
            }
        }
        this.handles.finish(n2);
        this.passHandle = n2;
        return object;
    }

    private Class<?> readClass(boolean bl) throws IOException {
        if (this.bin.readByte() == 118) {
            ObjectStreamClass objectStreamClass = this.readClassDesc(false);
            Class<?> class_ = objectStreamClass.forClass();
            HandleTable handleTable = this.handles;
            Serializable serializable = bl ? unsharedMarker : class_;
            this.passHandle = handleTable.assign(serializable);
            serializable = objectStreamClass.getResolveException();
            if (serializable != null) {
                this.handles.markException(this.passHandle, (ClassNotFoundException)serializable);
            }
            this.handles.finish(this.passHandle);
            return class_;
        }
        throw new InternalError();
    }

    private ObjectStreamClass readClassDesc(boolean bl) throws IOException {
        ObjectStreamClass objectStreamClass;
        byte by = this.bin.peekByte();
        if (by != 125) {
            switch (by) {
                default: {
                    throw new StreamCorruptedException(String.format("invalid type code: %02X", by));
                }
                case 114: {
                    objectStreamClass = this.readNonProxyDesc(bl);
                    break;
                }
                case 113: {
                    objectStreamClass = (ObjectStreamClass)this.readHandle(bl);
                    break;
                }
                case 112: {
                    objectStreamClass = (ObjectStreamClass)this.readNull();
                    break;
                }
            }
        } else {
            objectStreamClass = this.readProxyDesc(bl);
        }
        return objectStreamClass;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Enum<?> readEnum(boolean var1_1) throws IOException {
        if (this.bin.readByte() != 126) throw new InternalError();
        var2_2 = this.readClassDesc(false);
        if (!var2_2.isEnum()) {
            var4_6 = new StringBuilder();
            var4_6.append("non-enum class: ");
            var4_6.append(var2_2);
            throw new InvalidClassException(var4_6.toString());
        }
        var3_3 = this.handles;
        var4_4 = var1_1 != false ? ObjectInputStream.unsharedMarker : null;
        var5_7 = var3_3.assign(var4_4);
        var4_4 = var2_2.getResolveException();
        if (var4_4 != null) {
            this.handles.markException(var5_7, (ClassNotFoundException)var4_4);
        }
        var3_3 = this.readString(false);
        var4_4 = null;
        var6_8 = var2_2.forClass();
        if (var6_8 != null) {
            try {
                var2_2 = Enum.valueOf(var6_8, (String)var3_3);
                var4_4 = var2_2;
                ** if (var1_1) goto lbl-1000
            }
            catch (IllegalArgumentException var4_5) {
                var2_2 = new StringBuilder();
                var2_2.append("enum constant ");
                var2_2.append((String)var3_3);
                var2_2.append(" does not exist in ");
                var2_2.append(var6_8);
                throw (IOException)new InvalidObjectException(var2_2.toString()).initCause(var4_5);
            }
lbl-1000: // 1 sources:
            {
                this.handles.setObject(var5_7, var2_2);
                var4_4 = var2_2;
            }
lbl-1000: // 2 sources:
            {
            }
        }
        this.handles.finish(var5_7);
        this.passHandle = var5_7;
        return var4_4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readExternalData(Externalizable externalizable, ObjectStreamClass objectStreamClass) throws IOException {
        SerialCallbackContext serialCallbackContext = this.curContext;
        if (serialCallbackContext != null) {
            serialCallbackContext.check();
        }
        this.curContext = null;
        try {
            boolean bl = objectStreamClass.hasBlockExternalData();
            if (bl) {
                this.bin.setBlockDataMode(true);
            }
            if (externalizable != null) {
                try {
                    externalizable.readExternal(this);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    this.handles.markException(this.passHandle, classNotFoundException);
                }
            }
            if (!bl) return;
            this.skipCustomData();
            return;
        }
        finally {
            if (serialCallbackContext != null) {
                serialCallbackContext.check();
            }
            this.curContext = serialCallbackContext;
        }
    }

    private IOException readFatalException() throws IOException {
        if (this.bin.readByte() == 123) {
            this.clear();
            IOException iOException = (IOException)this.readObject0(false);
            this.clear();
            return iOException;
        }
        throw new InternalError();
    }

    private Object readHandle(boolean bl) throws IOException {
        if (this.bin.readByte() == 113) {
            this.passHandle = this.bin.readInt() - 8257536;
            int n = this.passHandle;
            if (n >= 0 && n < this.handles.size()) {
                if (!bl) {
                    Object object = this.handles.lookupObject(this.passHandle);
                    if (object != unsharedMarker) {
                        return object;
                    }
                    throw new InvalidObjectException("cannot read back reference to unshared object");
                }
                throw new InvalidObjectException("cannot read back reference as unshared");
            }
            throw new StreamCorruptedException(String.format("invalid handle value: %08X", this.passHandle + 8257536));
        }
        throw new InternalError();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private ObjectStreamClass readNonProxyDesc(boolean bl) throws IOException {
        Object object2;
        ObjectStreamClass objectStreamClass2;
        if (this.bin.readByte() != 114) throw new InternalError();
        ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        Object object = this.handles;
        Object object3 = bl ? unsharedMarker : objectStreamClass;
        int n = ((HandleTable)object).assign(object3);
        this.passHandle = -1;
        try {
            objectStreamClass2 = this.readClassDescriptor();
            object3 = null;
            object2 = null;
            this.bin.setBlockDataMode(true);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw (IOException)new InvalidClassException("failed to read class descriptor").initCause(classNotFoundException);
        }
        bl = this.isCustomSubclass();
        Class<?> class_ = this.resolveClass(objectStreamClass2);
        object = class_;
        if (class_ == null) {
            object3 = object;
            object3 = object;
            object3 = object2 = new ClassNotFoundException("null class");
        } else {
            object3 = object2;
            if (bl) {
                object3 = object;
                ReflectUtil.checkPackageAccess(object);
                object3 = object2;
            }
        }
        object2 = object;
        object = object3;
        catch (ClassNotFoundException classNotFoundException) {
            object2 = object3;
        }
        this.skipCustomData();
        objectStreamClass.initNonProxy(objectStreamClass2, (Class<?>)object2, (ClassNotFoundException)object, this.readClassDesc(false));
        this.handles.finish(n);
        this.passHandle = n;
        return objectStreamClass;
    }

    private Object readNull() throws IOException {
        if (this.bin.readByte() == 112) {
            this.passHandle = -1;
            return null;
        }
        throw new InternalError();
    }

    /*
     * Exception decompiling
     */
    private Object readObject0(boolean var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [10[CASE]], but top level block is 0[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private Object readOrdinaryObject(boolean bl) throws IOException {
        block10 : {
            block11 : {
                ObjectStreamClass objectStreamClass;
                Object object;
                Object object2;
                Object object3;
                block12 : {
                    block9 : {
                        if (this.bin.readByte() != 115) break block10;
                        objectStreamClass = this.readClassDesc(false);
                        objectStreamClass.checkDeserialize();
                        object2 = objectStreamClass.forClass();
                        if (object2 == String.class || object2 == Class.class || object2 == ObjectStreamClass.class) break block11;
                        try {
                            object2 = objectStreamClass.isInstantiable() ? objectStreamClass.newInstance() : null;
                            object = this.handles;
                            object3 = bl ? unsharedMarker : object2;
                            this.passHandle = ((HandleTable)object).assign(object3);
                            object3 = objectStreamClass.getResolveException();
                            if (object3 != null) {
                                this.handles.markException(this.passHandle, (ClassNotFoundException)object3);
                            }
                            if (!objectStreamClass.isExternalizable()) break block9;
                        }
                        catch (Exception exception) {
                            throw (IOException)new InvalidClassException(objectStreamClass.forClass().getName(), "unable to create instance").initCause(exception);
                        }
                        this.readExternalData((Externalizable)object2, objectStreamClass);
                        break block12;
                    }
                    this.readSerialData(object2, objectStreamClass);
                }
                this.handles.finish(this.passHandle);
                object = object2;
                if (object2 != null) {
                    object = object2;
                    if (this.handles.lookupException(this.passHandle) == null) {
                        object = object2;
                        if (objectStreamClass.hasReadResolveMethod()) {
                            object3 = object = objectStreamClass.invokeReadResolve(object2);
                            if (bl) {
                                object3 = object;
                                if (object.getClass().isArray()) {
                                    object3 = ObjectInputStream.cloneArray(object);
                                }
                            }
                            object = object2;
                            if (object3 != object2) {
                                object2 = this.handles;
                                int n = this.passHandle;
                                object = object3;
                                ((HandleTable)object2).setObject(n, object3);
                            }
                        }
                    }
                }
                return object;
            }
            throw new InvalidClassException("invalid class descriptor");
        }
        throw new InternalError();
    }

    private ObjectStreamClass readProxyDesc(boolean bl) throws IOException {
        block13 : {
            Object object;
            int n;
            ObjectStreamClass objectStreamClass;
            Object object2;
            block14 : {
                Object object3;
                block12 : {
                    block11 : {
                        block10 : {
                            if (this.bin.readByte() != 125) break block13;
                            objectStreamClass = new ObjectStreamClass();
                            object2 = this.handles;
                            object3 = bl ? unsharedMarker : objectStreamClass;
                            n = ((HandleTable)object2).assign(object3);
                            this.passHandle = -1;
                            int n2 = this.bin.readInt();
                            object2 = new String[n2];
                            for (int i = 0; i < n2; ++i) {
                                object2[i] = this.bin.readUTF();
                            }
                            object3 = null;
                            object = null;
                            this.bin.setBlockDataMode(true);
                            Class<?> class_ = this.resolveProxyClass((String[])object2);
                            object2 = class_;
                            if (class_ != null) break block10;
                            object3 = object2;
                            object3 = object2;
                            object3 = object = new ClassNotFoundException("null class");
                            break block11;
                        }
                        object3 = object2;
                        if (!Proxy.isProxyClass(object2)) break block12;
                        object3 = object2;
                        ReflectUtil.checkProxyPackageAccess(this.getClass().getClassLoader(), ((Class)object2).getInterfaces());
                        object3 = object;
                    }
                    object = object2;
                    object2 = object3;
                    break block14;
                }
                object3 = object2;
                object3 = object2;
                object = new InvalidClassException("Not a proxy");
                object3 = object2;
                try {
                    throw object;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    object = object3;
                }
            }
            this.skipCustomData();
            objectStreamClass.initProxy((Class<?>)object, (ClassNotFoundException)object2, this.readClassDesc(false));
            this.handles.finish(n);
            this.passHandle = n;
            return objectStreamClass;
        }
        throw new InternalError();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void readSerialData(Object object, ObjectStreamClass object2) throws IOException {
        ObjectStreamClass.ClassDataSlot[] arrclassDataSlot = ((ObjectStreamClass)object2).getClassDataLayout();
        int n = 0;
        while (n < arrclassDataSlot.length) {
            block17 : {
                ObjectStreamClass objectStreamClass;
                block16 : {
                    Throwable throwable2222;
                    block14 : {
                        block13 : {
                            block15 : {
                                objectStreamClass = arrclassDataSlot[n].desc;
                                if (!arrclassDataSlot[n].hasData) break block15;
                                if (object != null && this.handles.lookupException(this.passHandle) == null) {
                                    if (objectStreamClass.hasReadObjectMethod()) {
                                        SerialCallbackContext serialCallbackContext;
                                        object2 = this.curContext;
                                        if (object2 != null) {
                                            ((SerialCallbackContext)object2).check();
                                        }
                                        this.curContext = serialCallbackContext = new SerialCallbackContext(object, objectStreamClass);
                                        this.bin.setBlockDataMode(true);
                                        objectStreamClass.invokeReadObject(object, this);
                                        this.curContext.setUsed();
                                        if (object2 == null) break block13;
                                    }
                                    this.defaultReadFields(object, objectStreamClass);
                                } else {
                                    this.defaultReadFields(null, objectStreamClass);
                                }
                                break block16;
                            }
                            if (object != null && objectStreamClass.hasReadObjectNoDataMethod() && this.handles.lookupException(this.passHandle) == null) {
                                objectStreamClass.invokeReadObjectNoData(object);
                            }
                            break block17;
                            {
                                catch (Throwable throwable2222) {
                                    break block14;
                                }
                                catch (ClassNotFoundException classNotFoundException) {}
                                {
                                    this.handles.markException(this.passHandle, classNotFoundException);
                                    this.curContext.setUsed();
                                    if (object2 == null) break block13;
                                }
                            }
                            ((SerialCallbackContext)object2).check();
                        }
                        this.curContext = object2;
                        this.defaultDataEnd = false;
                        break block16;
                    }
                    this.curContext.setUsed();
                    if (object2 != null) {
                        ((SerialCallbackContext)object2).check();
                    }
                    this.curContext = object2;
                    throw throwable2222;
                }
                if (objectStreamClass.hasWriteObjectData()) {
                    this.skipCustomData();
                } else {
                    this.bin.setBlockDataMode(false);
                }
            }
            ++n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String readString(boolean bl) throws IOException {
        String string;
        byte by = this.bin.readByte();
        if (by != 116) {
            if (by != 124) throw new StreamCorruptedException(String.format("invalid type code: %02X", by));
            string = this.bin.readLongUTF();
        } else {
            string = this.bin.readUTF();
        }
        HandleTable handleTable = this.handles;
        Object object = bl ? unsharedMarker : string;
        this.passHandle = handleTable.assign(object);
        this.handles.finish(this.passHandle);
        return string;
    }

    private void skipCustomData() throws IOException {
        int n = this.passHandle;
        do {
            byte by;
            if (this.bin.getBlockDataMode()) {
                this.bin.skipBlockData();
                this.bin.setBlockDataMode(false);
            }
            if ((by = this.bin.peekByte()) != 119) {
                if (by != 120) {
                    if (by != 122) {
                        this.readObject0(false);
                        continue;
                    }
                } else {
                    this.bin.readByte();
                    this.passHandle = n;
                    return;
                }
            }
            this.bin.setBlockDataMode(true);
        } while (true);
    }

    private void verifySubclass() {
        Boolean bl;
        Class<?> class_ = this.getClass();
        if (class_ == ObjectInputStream.class) {
            return;
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return;
        }
        ObjectStreamClass.processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        ObjectStreamClass.WeakClassKey weakClassKey = new ObjectStreamClass.WeakClassKey(class_, Caches.subclassAuditsQueue);
        Boolean bl2 = bl = (Boolean)Caches.subclassAudits.get(weakClassKey);
        if (bl == null) {
            bl2 = ObjectInputStream.auditSubclass(class_);
            Caches.subclassAudits.putIfAbsent(weakClassKey, bl2);
        }
        if (bl2.booleanValue()) {
            return;
        }
        securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
    }

    @Override
    public int available() throws IOException {
        return this.bin.available();
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        if (this.depth == 0) {
            this.clear();
        }
        this.bin.close();
    }

    public void defaultReadObject() throws IOException, ClassNotFoundException {
        Object object = this.curContext;
        if (object != null) {
            Object object2 = ((SerialCallbackContext)object).getObj();
            object = ((SerialCallbackContext)object).getDesc();
            this.bin.setBlockDataMode(false);
            this.defaultReadFields(object2, (ObjectStreamClass)object);
            this.bin.setBlockDataMode(true);
            if (!((ObjectStreamClass)object).hasWriteObjectData()) {
                this.defaultDataEnd = true;
            }
            if ((object2 = this.handles.lookupException(this.passHandle)) == null) {
                return;
            }
            throw object2;
        }
        throw new NotActiveException("not in call to readObject");
    }

    protected boolean enableResolveObject(boolean bl) throws SecurityException {
        SecurityManager securityManager;
        if (bl == this.enableResolve) {
            return bl;
        }
        if (bl && (securityManager = System.getSecurityManager()) != null) {
            securityManager.checkPermission(SUBSTITUTION_PERMISSION);
        }
        this.enableResolve = bl;
        return this.enableResolve ^ true;
    }

    @Override
    public int read() throws IOException {
        return this.bin.read();
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            int n3 = n + n2;
            if (n >= 0 && n2 >= 0 && n3 <= arrby.length && n3 >= 0) {
                return this.bin.read(arrby, n, n2, false);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }

    @Override
    public boolean readBoolean() throws IOException {
        return this.bin.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return this.bin.readByte();
    }

    @Override
    public char readChar() throws IOException {
        return this.bin.readChar();
    }

    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        objectStreamClass.readNonProxy(this);
        return objectStreamClass;
    }

    @Override
    public double readDouble() throws IOException {
        return this.bin.readDouble();
    }

    public GetField readFields() throws IOException, ClassNotFoundException {
        Object object = this.curContext;
        if (object != null) {
            ((SerialCallbackContext)object).getObj();
            ObjectStreamClass objectStreamClass = ((SerialCallbackContext)object).getDesc();
            this.bin.setBlockDataMode(false);
            object = new GetFieldImpl(objectStreamClass);
            ((GetFieldImpl)object).readFields();
            this.bin.setBlockDataMode(true);
            if (!objectStreamClass.hasWriteObjectData()) {
                this.defaultDataEnd = true;
            }
            return object;
        }
        throw new NotActiveException("not in call to readObject");
    }

    @Override
    public float readFloat() throws IOException {
        return this.bin.readFloat();
    }

    @Override
    public void readFully(byte[] arrby) throws IOException {
        this.bin.readFully(arrby, 0, arrby.length, false);
    }

    @Override
    public void readFully(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n + n2;
        if (n >= 0 && n2 >= 0 && n3 <= arrby.length && n3 >= 0) {
            this.bin.readFully(arrby, n, n2, false);
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readInt() throws IOException {
        return this.bin.readInt();
    }

    @Deprecated
    @Override
    public String readLine() throws IOException {
        return this.bin.readLine();
    }

    @Override
    public long readLong() throws IOException {
        return this.bin.readLong();
    }

    @Override
    public final Object readObject() throws IOException, ClassNotFoundException {
        if (this.enableOverride) {
            return this.readObjectOverride();
        }
        int n = this.passHandle;
        try {
            Object object = this.readObject0(false);
            this.handles.markDependency(n, this.passHandle);
            ClassNotFoundException classNotFoundException = this.handles.lookupException(this.passHandle);
            if (classNotFoundException == null) {
                if (this.depth == 0) {
                    this.vlist.doCallbacks();
                }
                return object;
            }
            throw classNotFoundException;
        }
        finally {
            this.passHandle = n;
            if (this.closed && this.depth == 0) {
                this.clear();
            }
        }
    }

    protected Object readObjectOverride() throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public short readShort() throws IOException {
        return this.bin.readShort();
    }

    protected void readStreamHeader() throws IOException, StreamCorruptedException {
        short s = this.bin.readShort();
        short s2 = this.bin.readShort();
        if (s == -21267 && s2 == 5) {
            return;
        }
        throw new StreamCorruptedException(String.format("invalid stream header: %04X%04X", s, s2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String readTypeString() throws IOException {
        int n = this.passHandle;
        try {
            byte by = this.bin.peekByte();
            if (by != 112) {
                if (by == 113) {
                    String string = (String)this.readHandle(false);
                    return string;
                }
                if (by != 116 && by != 124) {
                    StreamCorruptedException streamCorruptedException = new StreamCorruptedException(String.format("invalid type code: %02X", by));
                    throw streamCorruptedException;
                }
                String string = this.readString(false);
                return string;
            }
            String string = (String)this.readNull();
            return string;
        }
        finally {
            this.passHandle = n;
        }
    }

    @Override
    public String readUTF() throws IOException {
        return this.bin.readUTF();
    }

    public Object readUnshared() throws IOException, ClassNotFoundException {
        int n = this.passHandle;
        try {
            Object object = this.readObject0(true);
            this.handles.markDependency(n, this.passHandle);
            ClassNotFoundException classNotFoundException = this.handles.lookupException(this.passHandle);
            if (classNotFoundException == null) {
                if (this.depth == 0) {
                    this.vlist.doCallbacks();
                }
                return object;
            }
            throw classNotFoundException;
        }
        finally {
            this.passHandle = n;
            if (this.closed && this.depth == 0) {
                this.clear();
            }
        }
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return this.bin.readUnsignedByte();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return this.bin.readUnsignedShort();
    }

    public void registerValidation(ObjectInputValidation objectInputValidation, int n) throws NotActiveException, InvalidObjectException {
        if (this.depth != 0) {
            this.vlist.register(objectInputValidation, n);
            return;
        }
        throw new NotActiveException("stream inactive");
    }

    protected Class<?> resolveClass(ObjectStreamClass class_) throws IOException, ClassNotFoundException {
        class_ = ((ObjectStreamClass)((Object)class_)).getName();
        try {
            Class<?> class_2 = Class.forName((String)((Object)class_), false, ObjectInputStream.latestUserDefinedLoader());
            return class_2;
        }
        catch (ClassNotFoundException classNotFoundException) {
            class_ = primClasses.get(class_);
            if (class_ != null) {
                return class_;
            }
            throw classNotFoundException;
        }
    }

    protected Object resolveObject(Object object) throws IOException {
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected Class<?> resolveProxyClass(String[] object) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = ObjectInputStream.latestUserDefinedLoader();
        ClassLoader classLoader2 = null;
        boolean bl = false;
        Class[] arrclass = new Class[((String[])object).length];
        for (int i = 0; i < ((String[])object).length; ++i) {
            Class<?> class_ = Class.forName(object[i], false, classLoader);
            ClassLoader classLoader3 = classLoader2;
            boolean bl2 = bl;
            if ((class_.getModifiers() & 1) == 0) {
                if (bl) {
                    if (classLoader2 != class_.getClassLoader()) throw new IllegalAccessError("conflicting non-public interface class loaders");
                    classLoader3 = classLoader2;
                    bl2 = bl;
                } else {
                    classLoader3 = class_.getClassLoader();
                    bl2 = true;
                }
            }
            arrclass[i] = class_;
            classLoader2 = classLoader3;
            bl = bl2;
        }
        if (!bl) {
            classLoader2 = classLoader;
        }
        try {
            return Proxy.getProxyClass(classLoader2, arrclass);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new ClassNotFoundException(null, illegalArgumentException);
        }
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return this.bin.skipBytes(n);
    }

    private class BlockDataInputStream
    extends InputStream
    implements DataInput {
        private static final int CHAR_BUF_SIZE = 256;
        private static final int HEADER_BLOCKED = -2;
        private static final int MAX_BLOCK_SIZE = 1024;
        private static final int MAX_HEADER_SIZE = 5;
        private boolean blkmode = false;
        private final byte[] buf = new byte[1024];
        private final char[] cbuf = new char[256];
        private final DataInputStream din;
        private int end = -1;
        private final byte[] hbuf = new byte[5];
        private final PeekInputStream in;
        private int pos = 0;
        private int unread = 0;

        BlockDataInputStream(InputStream inputStream) {
            this.in = new PeekInputStream(inputStream);
            this.din = new DataInputStream(this);
        }

        /*
         * Unable to fully structure code
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private int readBlockHeader(boolean var1_1) throws IOException {
            if (ObjectInputStream.access$500(ObjectInputStream.this)) {
                return -1;
            }
            do {
                block15 : {
                    block14 : {
                        if (var1_1) {
                            var2_2 = Integer.MAX_VALUE;
                        } else {
                            var2_2 = this.in.available();
                        }
                        if (var2_2 == 0) {
                            return -2;
                        }
                        var3_3 = this.in.peek();
                        if (var3_3 == 119) break;
                        if (var3_3 == 121) ** GOTO lbl37
                        if (var3_3 == 122) break block14;
                        if (var3_3 < 0 || var3_3 >= 112 && var3_3 <= 126) return -1;
                        var4_4 = new StreamCorruptedException(String.format("invalid type code: %02X", new Object[]{var3_3}));
                        throw var4_4;
                    }
                    if (var2_2 < 5) {
                        return -2;
                    }
                    this.in.readFully(this.hbuf, 0, 5);
                    var2_2 = Bits.getInt(this.hbuf, 1);
                    if (var2_2 < 0) break block15;
                    return var2_2;
                }
                var5_7 = new StringBuilder();
                var5_7.append("illegal block data header length: ");
                var5_7.append(var2_2);
                var4_5 = new StreamCorruptedException(var5_7.toString());
                throw var4_5;
lbl37: // 1 sources:
                this.in.read();
                ObjectInputStream.access$600(ObjectInputStream.this);
                continue;
                break;
            } while (true);
            if (var2_2 < 2) {
                return -2;
            }
            try {
                this.in.readFully(this.hbuf, 0, 2);
                var2_2 = this.hbuf[1];
                return var2_2 & 255;
            }
            catch (EOFException var4_6) {
                throw new StreamCorruptedException("unexpected EOF while reading block data header");
            }
        }

        private String readUTFBody(long l) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            long l2 = l;
            if (!this.blkmode) {
                this.pos = 0;
                this.end = 0;
                l2 = l;
            }
            while (l2 > 0L) {
                int n = this.end;
                int n2 = this.pos;
                if ((n -= n2) < 3 && (long)n != l2) {
                    if (this.blkmode) {
                        l = l2 - (long)this.readUTFChar(stringBuilder, l2);
                    } else {
                        if (n > 0) {
                            byte[] arrby = this.buf;
                            System.arraycopy(arrby, n2, arrby, 0, n);
                        }
                        this.pos = 0;
                        this.end = (int)Math.min(1024L, l2);
                        this.in.readFully(this.buf, n, this.end - n);
                        l = l2;
                    }
                } else {
                    l = l2 - this.readUTFSpan(stringBuilder, l2);
                }
                l2 = l;
            }
            return stringBuilder.toString();
        }

        private int readUTFChar(StringBuilder stringBuilder, long l) throws IOException {
            int n = this.readByte() & 255;
            switch (n >> 4) {
                default: {
                    throw new UTFDataFormatException();
                }
                case 14: {
                    if (l < 3L) {
                        if (l == 2L) {
                            this.readByte();
                        }
                        throw new UTFDataFormatException();
                    }
                    byte by = this.readByte();
                    byte by2 = this.readByte();
                    if ((by & 192) == 128 && (by2 & 192) == 128) {
                        stringBuilder.append((char)((n & 15) << 12 | (by & 63) << 6 | (by2 & 63) << 0));
                        return 3;
                    }
                    throw new UTFDataFormatException();
                }
                case 12: 
                case 13: {
                    if (l >= 2L) {
                        byte by = this.readByte();
                        if ((by & 192) == 128) {
                            stringBuilder.append((char)((n & 31) << 6 | (by & 63) << 0));
                            return 2;
                        }
                        throw new UTFDataFormatException();
                    }
                    throw new UTFDataFormatException();
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
            }
            stringBuilder.append((char)n);
            return 1;
        }

        /*
         * Exception decompiling
         */
        private long readUTFSpan(StringBuilder var1_1, long var2_3) throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CASE]], but top level block is 1[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void refill() throws IOException {
            try {
                int n;
                int n2;
                do {
                    this.pos = 0;
                    if (this.unread > 0) {
                        n = this.in.read(this.buf, 0, Math.min(this.unread, 1024));
                        if (n < 0) {
                            StreamCorruptedException streamCorruptedException = new StreamCorruptedException("unexpected EOF in middle of data block");
                            throw streamCorruptedException;
                        }
                        this.end = n;
                        this.unread -= n;
                        continue;
                    }
                    n = this.readBlockHeader(true);
                    if (n >= 0) {
                        this.end = 0;
                        this.unread = n;
                        continue;
                    }
                    this.end = -1;
                    this.unread = 0;
                } while ((n = this.pos) == (n2 = this.end));
                return;
            }
            catch (IOException iOException) {
                this.pos = 0;
                this.end = -1;
                this.unread = 0;
                throw iOException;
            }
        }

        @Override
        public int available() throws IOException {
            if (this.blkmode) {
                int n = this.pos;
                int n2 = this.end;
                int n3 = 0;
                if (n == n2 && this.unread == 0) {
                    while ((n2 = this.readBlockHeader(false)) == 0) {
                    }
                    if (n2 != -2) {
                        if (n2 != -1) {
                            this.pos = 0;
                            this.end = 0;
                            this.unread = n2;
                        } else {
                            this.pos = 0;
                            this.end = -1;
                        }
                    }
                }
                n2 = this.unread > 0 ? Math.min(this.in.available(), this.unread) : 0;
                n = this.end;
                if (n >= 0) {
                    n3 = n - this.pos + n2;
                }
                return n3;
            }
            return this.in.available();
        }

        @Override
        public void close() throws IOException {
            if (this.blkmode) {
                this.pos = 0;
                this.end = -1;
                this.unread = 0;
            }
            this.in.close();
        }

        int currentBlockRemaining() {
            if (this.blkmode) {
                int n = this.end;
                n = n >= 0 ? n - this.pos + this.unread : 0;
                return n;
            }
            throw new IllegalStateException();
        }

        boolean getBlockDataMode() {
            return this.blkmode;
        }

        long getBytesRead() {
            return this.in.getBytesRead();
        }

        int peek() throws IOException {
            if (this.blkmode) {
                if (this.pos == this.end) {
                    this.refill();
                }
                int n = this.end >= 0 ? this.buf[this.pos] & 255 : -1;
                return n;
            }
            return this.in.peek();
        }

        byte peekByte() throws IOException {
            int n = this.peek();
            if (n >= 0) {
                return (byte)n;
            }
            throw new EOFException();
        }

        @Override
        public int read() throws IOException {
            if (this.blkmode) {
                int n;
                if (this.pos == this.end) {
                    this.refill();
                }
                if (this.end >= 0) {
                    byte[] arrby = this.buf;
                    n = this.pos;
                    this.pos = n + 1;
                    n = arrby[n] & 255;
                } else {
                    n = -1;
                }
                return n;
            }
            return this.in.read();
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            return this.read(arrby, n, n2, false);
        }

        int read(byte[] arrby, int n, int n2, boolean bl) throws IOException {
            if (n2 == 0) {
                return 0;
            }
            if (this.blkmode) {
                int n3;
                if (this.pos == this.end) {
                    this.refill();
                }
                if ((n3 = this.end) < 0) {
                    return -1;
                }
                n2 = Math.min(n2, n3 - this.pos);
                System.arraycopy(this.buf, this.pos, arrby, n, n2);
                this.pos += n2;
                return n2;
            }
            if (bl) {
                if ((n2 = this.in.read(this.buf, 0, Math.min(n2, 1024))) > 0) {
                    System.arraycopy(this.buf, 0, arrby, n, n2);
                }
                return n2;
            }
            return this.in.read(arrby, n, n2);
        }

        @Override
        public boolean readBoolean() throws IOException {
            int n = this.read();
            if (n >= 0) {
                boolean bl = n != 0;
                return bl;
            }
            throw new EOFException();
        }

        void readBooleans(boolean[] arrbl, int n, int n2) throws IOException {
            int n3 = n + n2;
            block0 : while (n < n3) {
                int n4;
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 1024);
                    this.in.readFully(this.buf, 0, n2);
                    n2 = n + n2;
                    this.pos = 0;
                    n4 = n;
                } else {
                    n4 = this.end;
                    n2 = this.pos;
                    if (n4 - n2 < 1) {
                        arrbl[n] = this.din.readBoolean();
                        ++n;
                        continue;
                    }
                    n2 = Math.min(n3, n4 + n - n2);
                    n4 = n;
                }
                do {
                    n = n4;
                    if (n4 >= n2) continue block0;
                    byte[] arrby = this.buf;
                    n = this.pos;
                    this.pos = n + 1;
                    arrbl[n4] = Bits.getBoolean(arrby, n);
                    ++n4;
                } while (true);
            }
        }

        @Override
        public byte readByte() throws IOException {
            int n = this.read();
            if (n >= 0) {
                return (byte)n;
            }
            throw new EOFException();
        }

        @Override
        public char readChar() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 2);
            } else if (this.end - this.pos < 2) {
                return this.din.readChar();
            }
            char c = Bits.getChar(this.buf, this.pos);
            this.pos += 2;
            return c;
        }

        void readChars(char[] arrc, int n, int n2) throws IOException {
            int n3 = n + n2;
            block0 : while (n < n3) {
                int n4;
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 512);
                    this.in.readFully(this.buf, 0, n2 << 1);
                    n2 = n + n2;
                    this.pos = 0;
                    n4 = n;
                } else {
                    n4 = this.end;
                    n2 = this.pos;
                    if (n4 - n2 < 2) {
                        arrc[n] = this.din.readChar();
                        ++n;
                        continue;
                    }
                    n2 = Math.min(n3, (n4 - n2 >> 1) + n);
                    n4 = n;
                }
                do {
                    n = ++n4;
                    if (n4 >= n2) continue block0;
                    arrc[n4] = Bits.getChar(this.buf, this.pos);
                    this.pos += 2;
                } while (true);
            }
        }

        @Override
        public double readDouble() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 8);
            } else if (this.end - this.pos < 8) {
                return this.din.readDouble();
            }
            double d = Bits.getDouble(this.buf, this.pos);
            this.pos += 8;
            return d;
        }

        void readDoubles(double[] arrd, int n, int n2) throws IOException {
            int n3 = n + n2;
            while (n < n3) {
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 128);
                    this.in.readFully(this.buf, 0, n2 << 3);
                    this.pos = 0;
                } else {
                    n2 = this.end;
                    int n4 = this.pos;
                    if (n2 - n4 < 8) {
                        arrd[n] = this.din.readDouble();
                        ++n;
                        continue;
                    }
                    n2 = Math.min(n3 - n, n2 - n4 >> 3);
                }
                ObjectInputStream.bytesToDoubles(this.buf, this.pos, arrd, n, n2);
                n += n2;
                this.pos += n2 << 3;
            }
        }

        @Override
        public float readFloat() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 4);
            } else if (this.end - this.pos < 4) {
                return this.din.readFloat();
            }
            float f = Bits.getFloat(this.buf, this.pos);
            this.pos += 4;
            return f;
        }

        void readFloats(float[] arrf, int n, int n2) throws IOException {
            int n3 = n + n2;
            while (n < n3) {
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 256);
                    this.in.readFully(this.buf, 0, n2 << 2);
                    this.pos = 0;
                } else {
                    int n4 = this.end;
                    n2 = this.pos;
                    if (n4 - n2 < 4) {
                        arrf[n] = this.din.readFloat();
                        ++n;
                        continue;
                    }
                    n2 = Math.min(n3 - n, n4 - n2 >> 2);
                }
                ObjectInputStream.bytesToFloats(this.buf, this.pos, arrf, n, n2);
                n += n2;
                this.pos += n2 << 2;
            }
        }

        @Override
        public void readFully(byte[] arrby) throws IOException {
            this.readFully(arrby, 0, arrby.length, false);
        }

        @Override
        public void readFully(byte[] arrby, int n, int n2) throws IOException {
            this.readFully(arrby, n, n2, false);
        }

        public void readFully(byte[] arrby, int n, int n2, boolean bl) throws IOException {
            while (n2 > 0) {
                int n3 = this.read(arrby, n, n2, bl);
                if (n3 >= 0) {
                    n += n3;
                    n2 -= n3;
                    continue;
                }
                throw new EOFException();
            }
        }

        @Override
        public int readInt() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 4);
            } else if (this.end - this.pos < 4) {
                return this.din.readInt();
            }
            int n = Bits.getInt(this.buf, this.pos);
            this.pos += 4;
            return n;
        }

        void readInts(int[] arrn, int n, int n2) throws IOException {
            int n3 = n + n2;
            block0 : while (n < n3) {
                int n4;
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 256);
                    this.in.readFully(this.buf, 0, n2 << 2);
                    n4 = n + n2;
                    this.pos = 0;
                    n2 = n;
                } else {
                    n2 = this.end;
                    n4 = this.pos;
                    if (n2 - n4 < 4) {
                        arrn[n] = this.din.readInt();
                        ++n;
                        continue;
                    }
                    n4 = Math.min(n3, (n2 - n4 >> 2) + n);
                    n2 = n;
                }
                do {
                    n = ++n2;
                    if (n2 >= n4) continue block0;
                    arrn[n2] = Bits.getInt(this.buf, this.pos);
                    this.pos += 4;
                } while (true);
            }
        }

        @Override
        public String readLine() throws IOException {
            return this.din.readLine();
        }

        @Override
        public long readLong() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 8);
            } else if (this.end - this.pos < 8) {
                return this.din.readLong();
            }
            long l = Bits.getLong(this.buf, this.pos);
            this.pos += 8;
            return l;
        }

        String readLongUTF() throws IOException {
            return this.readUTFBody(this.readLong());
        }

        void readLongs(long[] arrl, int n, int n2) throws IOException {
            int n3 = n + n2;
            block0 : while (n < n3) {
                int n4;
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 128);
                    this.in.readFully(this.buf, 0, n2 << 3);
                    n2 = n + n2;
                    this.pos = 0;
                    n4 = n;
                } else {
                    n2 = this.end;
                    n4 = this.pos;
                    if (n2 - n4 < 8) {
                        arrl[n] = this.din.readLong();
                        ++n;
                        continue;
                    }
                    n2 = Math.min(n3, (n2 - n4 >> 3) + n);
                    n4 = n;
                }
                do {
                    n = ++n4;
                    if (n4 >= n2) continue block0;
                    arrl[n4] = Bits.getLong(this.buf, this.pos);
                    this.pos += 8;
                } while (true);
            }
        }

        @Override
        public short readShort() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 2);
            } else if (this.end - this.pos < 2) {
                return this.din.readShort();
            }
            short s = Bits.getShort(this.buf, this.pos);
            this.pos += 2;
            return s;
        }

        void readShorts(short[] arrs, int n, int n2) throws IOException {
            int n3 = n + n2;
            block0 : while (n < n3) {
                int n4;
                if (!this.blkmode) {
                    n2 = Math.min(n3 - n, 512);
                    this.in.readFully(this.buf, 0, n2 << 1);
                    n4 = n + n2;
                    this.pos = 0;
                    n2 = n;
                } else {
                    n4 = this.end;
                    n2 = this.pos;
                    if (n4 - n2 < 2) {
                        arrs[n] = this.din.readShort();
                        ++n;
                        continue;
                    }
                    n4 = Math.min(n3, (n4 - n2 >> 1) + n);
                    n2 = n;
                }
                do {
                    n = ++n2;
                    if (n2 >= n4) continue block0;
                    arrs[n2] = Bits.getShort(this.buf, this.pos);
                    this.pos += 2;
                } while (true);
            }
        }

        @Override
        public String readUTF() throws IOException {
            return this.readUTFBody(this.readUnsignedShort());
        }

        @Override
        public int readUnsignedByte() throws IOException {
            int n = this.read();
            if (n >= 0) {
                return n;
            }
            throw new EOFException();
        }

        @Override
        public int readUnsignedShort() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 2);
            } else if (this.end - this.pos < 2) {
                return this.din.readUnsignedShort();
            }
            short s = Bits.getShort(this.buf, this.pos);
            this.pos += 2;
            return s & 65535;
        }

        boolean setBlockDataMode(boolean bl) throws IOException {
            block6 : {
                block5 : {
                    block4 : {
                        boolean bl2 = this.blkmode;
                        if (bl2 == bl) {
                            return bl2;
                        }
                        if (!bl) break block4;
                        this.pos = 0;
                        this.end = 0;
                        this.unread = 0;
                        break block5;
                    }
                    if (this.pos < this.end) break block6;
                }
                this.blkmode = bl;
                return this.blkmode ^ true;
            }
            throw new IllegalStateException("unread block data");
        }

        @Override
        public long skip(long l) throws IOException {
            long l2 = l;
            while (l2 > 0L) {
                int n;
                if (this.blkmode) {
                    if (this.pos == this.end) {
                        this.refill();
                    }
                    if ((n = this.end) < 0) break;
                    n = (int)Math.min(l2, (long)(n - this.pos));
                    l2 -= (long)n;
                    this.pos += n;
                    continue;
                }
                n = (int)Math.min(l2, 1024L);
                if ((n = this.in.read(this.buf, 0, n)) < 0) break;
                l2 -= (long)n;
            }
            return l - l2;
        }

        void skipBlockData() throws IOException {
            if (this.blkmode) {
                while (this.end >= 0) {
                    this.refill();
                }
                return;
            }
            throw new IllegalStateException("not in block data mode");
        }

        @Override
        public int skipBytes(int n) throws IOException {
            return this.din.skipBytes(n);
        }
    }

    private static class Caches {
        static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap<ObjectStreamClass.WeakClassKey, Boolean>();
        static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue();

        private Caches() {
        }
    }

    public static abstract class GetField {
        public abstract boolean defaulted(String var1) throws IOException;

        public abstract byte get(String var1, byte var2) throws IOException;

        public abstract char get(String var1, char var2) throws IOException;

        public abstract double get(String var1, double var2) throws IOException;

        public abstract float get(String var1, float var2) throws IOException;

        public abstract int get(String var1, int var2) throws IOException;

        public abstract long get(String var1, long var2) throws IOException;

        public abstract Object get(String var1, Object var2) throws IOException;

        public abstract short get(String var1, short var2) throws IOException;

        public abstract boolean get(String var1, boolean var2) throws IOException;

        public abstract ObjectStreamClass getObjectStreamClass();
    }

    private class GetFieldImpl
    extends GetField {
        private final ObjectStreamClass desc;
        private final int[] objHandles;
        private final Object[] objVals;
        private final byte[] primVals;

        GetFieldImpl(ObjectStreamClass objectStreamClass) {
            this.desc = objectStreamClass;
            this.primVals = new byte[objectStreamClass.getPrimDataSize()];
            this.objVals = new Object[objectStreamClass.getNumObjFields()];
            this.objHandles = new int[this.objVals.length];
        }

        private int getFieldOffset(String string, Class<?> class_) {
            Object object = this.desc.getField(string, class_);
            if (object != null) {
                return ((ObjectStreamField)object).getOffset();
            }
            if (this.desc.getLocalDesc().getField(string, class_) != null) {
                return -1;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("no such field ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" with type ");
            ((StringBuilder)object).append(class_);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public boolean defaulted(String string) throws IOException {
            boolean bl = this.getFieldOffset(string, null) < 0;
            return bl;
        }

        @Override
        public byte get(String string, byte by) throws IOException {
            int n = this.getFieldOffset(string, Byte.TYPE);
            byte by2 = n >= 0 ? (by = this.primVals[n]) : by;
            return by2;
        }

        @Override
        public char get(String string, char c) throws IOException {
            int n = this.getFieldOffset(string, Character.TYPE);
            char c2 = n >= 0 ? (c = Bits.getChar(this.primVals, n)) : c;
            return c2;
        }

        @Override
        public double get(String string, double d) throws IOException {
            block0 : {
                int n = this.getFieldOffset(string, Double.TYPE);
                if (n < 0) break block0;
                d = Bits.getDouble(this.primVals, n);
            }
            return d;
        }

        @Override
        public float get(String string, float f) throws IOException {
            block0 : {
                int n = this.getFieldOffset(string, Float.TYPE);
                if (n < 0) break block0;
                f = Bits.getFloat(this.primVals, n);
            }
            return f;
        }

        @Override
        public int get(String string, int n) throws IOException {
            block0 : {
                int n2 = this.getFieldOffset(string, Integer.TYPE);
                if (n2 < 0) break block0;
                n = Bits.getInt(this.primVals, n2);
            }
            return n;
        }

        @Override
        public long get(String string, long l) throws IOException {
            block0 : {
                int n = this.getFieldOffset(string, Long.TYPE);
                if (n < 0) break block0;
                l = Bits.getLong(this.primVals, n);
            }
            return l;
        }

        @Override
        public Object get(String object, Object object2) throws IOException {
            int n = this.getFieldOffset((String)object, Object.class);
            if (n >= 0) {
                int n2 = this.objHandles[n];
                ObjectInputStream.this.handles.markDependency(ObjectInputStream.this.passHandle, n2);
                object = ObjectInputStream.this.handles.lookupException(n2) == null ? this.objVals[n] : null;
                return object;
            }
            return object2;
        }

        @Override
        public short get(String string, short s) throws IOException {
            int n = this.getFieldOffset(string, Short.TYPE);
            short s2 = n >= 0 ? (s = Bits.getShort(this.primVals, n)) : s;
            return s2;
        }

        @Override
        public boolean get(String string, boolean bl) throws IOException {
            block0 : {
                int n = this.getFieldOffset(string, Boolean.TYPE);
                if (n < 0) break block0;
                bl = Bits.getBoolean(this.primVals, n);
            }
            return bl;
        }

        @Override
        public ObjectStreamClass getObjectStreamClass() {
            return this.desc;
        }

        void readFields() throws IOException {
            ObjectStreamField[] arrobjectStreamField = ObjectInputStream.this.bin;
            Object[] arrobject = this.primVals;
            arrobjectStreamField.readFully((byte[])arrobject, 0, arrobject.length, false);
            int n = ObjectInputStream.this.passHandle;
            arrobjectStreamField = this.desc.getFields(false);
            int n2 = arrobjectStreamField.length;
            int n3 = this.objVals.length;
            for (int i = 0; i < (arrobject = this.objVals).length; ++i) {
                arrobject[i] = (byte)ObjectInputStream.this.readObject0(arrobjectStreamField[n2 - n3 + i].isUnshared());
                this.objHandles[i] = ObjectInputStream.this.passHandle;
            }
            ObjectInputStream.this.passHandle = n;
        }
    }

    private static class HandleTable {
        private static final byte STATUS_EXCEPTION = 3;
        private static final byte STATUS_OK = 1;
        private static final byte STATUS_UNKNOWN = 2;
        HandleList[] deps;
        Object[] entries;
        int lowDep = -1;
        int size = 0;
        byte[] status;

        HandleTable(int n) {
            this.status = new byte[n];
            this.entries = new Object[n];
            this.deps = new HandleList[n];
        }

        private void grow() {
            int n = (this.entries.length << 1) + 1;
            byte[] arrby = new byte[n];
            Object[] arrobject = new Object[n];
            HandleList[] arrhandleList = new HandleList[n];
            System.arraycopy(this.status, 0, arrby, 0, this.size);
            System.arraycopy(this.entries, 0, arrobject, 0, this.size);
            System.arraycopy(this.deps, 0, arrhandleList, 0, this.size);
            this.status = arrby;
            this.entries = arrobject;
            this.deps = arrhandleList;
        }

        int assign(Object object) {
            if (this.size >= this.entries.length) {
                this.grow();
            }
            byte[] arrby = this.status;
            int n = this.size;
            arrby[n] = (byte)2;
            this.entries[n] = object;
            this.size = n + 1;
            return n;
        }

        void clear() {
            Arrays.fill(this.status, 0, this.size, (byte)0);
            Arrays.fill(this.entries, 0, this.size, null);
            Arrays.fill(this.deps, 0, this.size, null);
            this.lowDep = -1;
            this.size = 0;
        }

        void finish(int n) {
            block10 : {
                int n2;
                block9 : {
                    block8 : {
                        n2 = this.lowDep;
                        if (n2 >= 0) break block8;
                        n2 = n + 1;
                        break block9;
                    }
                    if (n2 < n) break block10;
                    n2 = this.size;
                    this.lowDep = -1;
                }
                while (n < n2) {
                    byte[] arrby = this.status;
                    byte by = arrby[n];
                    if (by != 1) {
                        if (by != 2) {
                            if (by != 3) {
                                throw new InternalError();
                            }
                        } else {
                            arrby[n] = (byte)(true ? 1 : 0);
                            this.deps[n] = null;
                        }
                    }
                    ++n;
                }
                return;
            }
        }

        ClassNotFoundException lookupException(int n) {
            ClassNotFoundException classNotFoundException = n != -1 && this.status[n] == 3 ? (ClassNotFoundException)this.entries[n] : null;
            return classNotFoundException;
        }

        Object lookupObject(int n) {
            Object object = n != -1 && this.status[n] != 3 ? this.entries[n] : null;
            return object;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void markDependency(int n, int n2) {
            if (n == -1 || n2 == -1) return;
            byte[] arrby = this.status;
            byte by = arrby[n];
            if (by != 2) {
                if (by == 3) return;
                throw new InternalError();
            }
            by = arrby[n2];
            if (by == 1) return;
            if (by != 2) {
                if (by != 3) throw new InternalError();
                this.markException(n, (ClassNotFoundException)this.entries[n2]);
                return;
            } else {
                HandleList[] arrhandleList = this.deps;
                if (arrhandleList[n2] == null) {
                    arrhandleList[n2] = new HandleList();
                }
                this.deps[n2].add(n);
                n = this.lowDep;
                if (n >= 0 && n <= n2) return;
                this.lowDep = n2;
            }
        }

        void markException(int n, ClassNotFoundException classNotFoundException) {
            Object object = this.status;
            int n2 = object[n];
            if (n2 != 2) {
                if (n2 != 3) {
                    throw new InternalError();
                }
            } else {
                object[n] = (byte)3;
                this.entries[n] = classNotFoundException;
                object = this.deps[n];
                if (object != null) {
                    int n3 = ((HandleList)object).size();
                    for (n2 = 0; n2 < n3; ++n2) {
                        this.markException(((HandleList)object).get(n2), classNotFoundException);
                    }
                    this.deps[n] = null;
                }
            }
        }

        void setObject(int n, Object object) {
            byte by = this.status[n];
            if (by != 1 && by != 2) {
                if (by != 3) {
                    throw new InternalError();
                }
            } else {
                this.entries[n] = object;
            }
        }

        int size() {
            return this.size;
        }

        private static class HandleList {
            private int[] list = new int[4];
            private int size = 0;

            public void add(int n) {
                int[] arrn;
                int n2 = this.size;
                int[] arrn2 = this.list;
                if (n2 >= arrn2.length) {
                    arrn = new int[arrn2.length << 1];
                    System.arraycopy((Object)arrn2, 0, (Object)arrn, 0, arrn2.length);
                    this.list = arrn;
                }
                arrn = this.list;
                n2 = this.size;
                this.size = n2 + 1;
                arrn[n2] = n;
            }

            public int get(int n) {
                if (n < this.size) {
                    return this.list[n];
                }
                throw new ArrayIndexOutOfBoundsException();
            }

            public int size() {
                return this.size;
            }
        }

    }

    private static class PeekInputStream
    extends InputStream {
        private final InputStream in;
        private int peekb = -1;
        private long totalBytesRead = 0L;

        PeekInputStream(InputStream inputStream) {
            this.in = inputStream;
        }

        @Override
        public int available() throws IOException {
            int n = this.in.available();
            int n2 = this.peekb >= 0 ? 1 : 0;
            return n + n2;
        }

        @Override
        public void close() throws IOException {
            this.in.close();
        }

        public long getBytesRead() {
            return this.totalBytesRead;
        }

        int peek() throws IOException {
            int n = this.peekb;
            if (n >= 0) {
                return n;
            }
            this.peekb = this.in.read();
            long l = this.totalBytesRead;
            long l2 = this.peekb >= 0 ? 1L : 0L;
            this.totalBytesRead = l + l2;
            return this.peekb;
        }

        @Override
        public int read() throws IOException {
            if (this.peekb >= 0) {
                int n = this.peekb;
                this.peekb = -1;
                return n;
            }
            int n = this.in.read();
            long l = this.totalBytesRead;
            long l2 = n >= 0 ? 1L : 0L;
            this.totalBytesRead = l + l2;
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if (n2 == 0) {
                return 0;
            }
            int n3 = this.peekb;
            long l = 0L;
            if (n3 < 0) {
                n = this.in.read(arrby, n, n2);
                long l2 = this.totalBytesRead;
                if (n >= 0) {
                    l = n;
                }
                this.totalBytesRead = l2 + l;
                return n;
            }
            arrby[n] = (byte)n3;
            this.peekb = -1;
            n = this.in.read(arrby, n + 1, n2 - 1);
            long l3 = this.totalBytesRead;
            if (n >= 0) {
                l = n;
            }
            this.totalBytesRead = l3 + l;
            n = n >= 0 ? ++n : 1;
            return n;
        }

        void readFully(byte[] arrby, int n, int n2) throws IOException {
            int n3;
            for (int i = 0; i < n2; i += n3) {
                n3 = this.read(arrby, n + i, n2 - i);
                if (n3 >= 0) {
                    continue;
                }
                throw new EOFException();
            }
        }

        @Override
        public long skip(long l) throws IOException {
            if (l <= 0L) {
                return 0L;
            }
            int n = 0;
            long l2 = l;
            if (this.peekb >= 0) {
                this.peekb = -1;
                n = 0 + 1;
                l2 = l - 1L;
            }
            l = (long)n + this.in.skip(l2);
            this.totalBytesRead += l;
            return l;
        }
    }

    private static class ValidationList {
        private Callback list;

        ValidationList() {
        }

        static /* synthetic */ Callback access$400(ValidationList validationList) {
            return validationList.list;
        }

        public void clear() {
            this.list = null;
        }

        void doCallbacks() throws InvalidObjectException {
            try {
                while (this.list != null) {
                    PrivilegedExceptionAction<Void> privilegedExceptionAction = new PrivilegedExceptionAction<Void>(){

                        @Override
                        public Void run() throws InvalidObjectException {
                            ValidationList.access$400((ValidationList)this).obj.validateObject();
                            return null;
                        }
                    };
                    AccessController.doPrivileged(privilegedExceptionAction, this.list.acc);
                    this.list = this.list.next;
                }
                return;
            }
            catch (PrivilegedActionException privilegedActionException) {
                this.list = null;
                throw (InvalidObjectException)privilegedActionException.getException();
            }
        }

        void register(ObjectInputValidation objectInputValidation, int n) throws InvalidObjectException {
            if (objectInputValidation != null) {
                Callback callback = null;
                Callback callback2 = this.list;
                while (callback2 != null && n < callback2.priority) {
                    callback = callback2;
                    callback2 = callback2.next;
                }
                AccessControlContext accessControlContext = AccessController.getContext();
                if (callback != null) {
                    callback.next = new Callback(objectInputValidation, n, callback2, accessControlContext);
                } else {
                    this.list = new Callback(objectInputValidation, n, this.list, accessControlContext);
                }
                return;
            }
            throw new InvalidObjectException("null callback");
        }

        private static class Callback {
            final AccessControlContext acc;
            Callback next;
            final ObjectInputValidation obj;
            final int priority;

            Callback(ObjectInputValidation objectInputValidation, int n, Callback callback, AccessControlContext accessControlContext) {
                this.obj = objectInputValidation;
                this.priority = n;
                this.next = callback;
                this.acc = accessControlContext;
            }
        }

    }

}

