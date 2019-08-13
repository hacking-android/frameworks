/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.Bits;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.ObjectOutput;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.ObjectStreamField;
import java.io.OutputStream;
import java.io.SerialCallbackContext;
import java.io.Serializable;
import java.io.SerializablePermission;
import java.io.UTFDataFormatException;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.reflect.misc.ReflectUtil;

public class ObjectOutputStream
extends OutputStream
implements ObjectOutput,
ObjectStreamConstants {
    private static final boolean extendedDebugInfo = false;
    private final BlockDataOutputStream bout;
    private SerialCallbackContext curContext;
    private PutFieldImpl curPut;
    private final DebugTraceInfoStack debugInfoStack;
    private int depth;
    private final boolean enableOverride;
    private boolean enableReplace;
    private final HandleTable handles;
    private byte[] primVals;
    private int protocol = 2;
    private final ReplaceTable subs;

    protected ObjectOutputStream() throws IOException, SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
        }
        this.bout = null;
        this.handles = null;
        this.subs = null;
        this.enableOverride = true;
        this.debugInfoStack = null;
    }

    public ObjectOutputStream(OutputStream outputStream) throws IOException {
        this.verifySubclass();
        this.bout = new BlockDataOutputStream(outputStream);
        this.handles = new HandleTable(10, 3.0f);
        this.subs = new ReplaceTable(10, 3.0f);
        this.enableOverride = false;
        this.writeStreamHeader();
        this.bout.setBlockDataMode(true);
        this.debugInfoStack = null;
    }

    private static boolean auditSubclass(final Class<?> class_) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                for (Class class_2 = class_; class_2 != ObjectOutputStream.class; class_2 = class_2.getSuperclass()) {
                    try {
                        class_2.getDeclaredMethod("writeUnshared", Object.class);
                        Boolean bl = Boolean.FALSE;
                        return bl;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        try {
                            class_2.getDeclaredMethod("putFields", null);
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

    private void clear() {
        this.subs.clear();
        this.handles.clear();
    }

    private void defaultWriteFields(Object object, ObjectStreamClass objectStreamClass) throws IOException {
        Object[] arrobject = objectStreamClass.forClass();
        if (arrobject != null && object != null && !arrobject.isInstance(object)) {
            throw new ClassCastException();
        }
        objectStreamClass.checkDefaultSerialize();
        int n = objectStreamClass.getPrimDataSize();
        arrobject = this.primVals;
        if (arrobject == null || arrobject.length < n) {
            this.primVals = new byte[n];
        }
        objectStreamClass.getPrimFieldValues(object, this.primVals);
        this.bout.write(this.primVals, 0, n, false);
        ObjectStreamField[] arrobjectStreamField = objectStreamClass.getFields(false);
        arrobject = new Object[objectStreamClass.getNumObjFields()];
        int n2 = arrobjectStreamField.length;
        int n3 = arrobject.length;
        objectStreamClass.getObjFieldValues(object, arrobject);
        for (n = 0; n < arrobject.length; ++n) {
            this.writeObject0(arrobject[n], arrobjectStreamField[n2 - n3 + n].isUnshared());
        }
    }

    private static native void doublesToBytes(double[] var0, int var1, byte[] var2, int var3, int var4);

    private static native void floatsToBytes(float[] var0, int var1, byte[] var2, int var3, int var4);

    private boolean isCustomSubclass() {
        boolean bl = this.getClass().getClassLoader() != ObjectOutputStream.class.getClassLoader();
        return bl;
    }

    private void verifySubclass() {
        Boolean bl;
        Class<?> class_ = this.getClass();
        if (class_ == ObjectOutputStream.class) {
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
            bl2 = ObjectOutputStream.auditSubclass(class_);
            Caches.subclassAudits.putIfAbsent(weakClassKey, bl2);
        }
        if (bl2.booleanValue()) {
            return;
        }
        securityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void writeArray(Object arrobject, ObjectStreamClass serializable, boolean bl) throws IOException {
        this.bout.writeByte(117);
        this.writeClassDesc((ObjectStreamClass)serializable, false);
        HandleTable handleTable = this.handles;
        Object[] arrobject2 = bl ? null : arrobject;
        handleTable.assign(arrobject2);
        serializable = ((ObjectStreamClass)serializable).forClass().getComponentType();
        if (((Class)serializable).isPrimitive()) {
            if (serializable == Integer.TYPE) {
                arrobject = (int[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeInts((int[])arrobject, 0, arrobject.length);
                return;
            } else if (serializable == Byte.TYPE) {
                arrobject = (byte[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.write((byte[])arrobject, 0, arrobject.length, true);
                return;
            } else if (serializable == Long.TYPE) {
                arrobject = (long[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeLongs((long[])arrobject, 0, arrobject.length);
                return;
            } else if (serializable == Float.TYPE) {
                arrobject = (float[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeFloats((float[])arrobject, 0, arrobject.length);
                return;
            } else if (serializable == Double.TYPE) {
                arrobject = (double[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeDoubles((double[])arrobject, 0, arrobject.length);
                return;
            } else if (serializable == Short.TYPE) {
                arrobject = (short[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeShorts((short[])arrobject, 0, arrobject.length);
                return;
            } else if (serializable == Character.TYPE) {
                arrobject = (char[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeChars((char[])arrobject, 0, arrobject.length);
                return;
            } else {
                if (serializable != Boolean.TYPE) throw new InternalError();
                arrobject = (boolean[])arrobject;
                this.bout.writeInt(arrobject.length);
                this.bout.writeBooleans((boolean[])arrobject, 0, arrobject.length);
            }
            return;
        } else {
            int n = arrobject.length;
            this.bout.writeInt(n);
            for (int i = 0; i < n; ++i) {
                this.writeObject0(arrobject[i], false);
            }
        }
    }

    private void writeClass(Class<?> class_, boolean bl) throws IOException {
        this.bout.writeByte(118);
        this.writeClassDesc(ObjectStreamClass.lookup(class_, true), false);
        HandleTable handleTable = this.handles;
        if (bl) {
            class_ = null;
        }
        handleTable.assign(class_);
    }

    private void writeClassDesc(ObjectStreamClass objectStreamClass, boolean bl) throws IOException {
        int n;
        if (objectStreamClass == null) {
            this.writeNull();
        } else if (!bl && (n = this.handles.lookup(objectStreamClass)) != -1) {
            this.writeHandle(n);
        } else if (objectStreamClass.isProxy()) {
            this.writeProxyDesc(objectStreamClass, bl);
        } else {
            this.writeNonProxyDesc(objectStreamClass, bl);
        }
    }

    private void writeEnum(Enum<?> enum_, ObjectStreamClass object, boolean bl) throws IOException {
        this.bout.writeByte(126);
        Object object2 = ((ObjectStreamClass)object).getSuperDesc();
        if (((ObjectStreamClass)object2).forClass() != Enum.class) {
            object = object2;
        }
        this.writeClassDesc((ObjectStreamClass)object, false);
        object2 = this.handles;
        object = bl ? null : enum_;
        ((HandleTable)object2).assign(object);
        this.writeString(enum_.name(), false);
    }

    private void writeExternalData(Externalizable externalizable) throws IOException {
        PutFieldImpl putFieldImpl = this.curPut;
        this.curPut = null;
        SerialCallbackContext serialCallbackContext = this.curContext;
        try {
            this.curContext = null;
            if (this.protocol == 1) {
                externalizable.writeExternal(this);
            } else {
                this.bout.setBlockDataMode(true);
                externalizable.writeExternal(this);
                this.bout.setBlockDataMode(false);
                this.bout.writeByte(120);
            }
            this.curPut = putFieldImpl;
            return;
        }
        finally {
            this.curContext = serialCallbackContext;
        }
    }

    private void writeFatalException(IOException iOException) throws IOException {
        this.clear();
        boolean bl = this.bout.setBlockDataMode(false);
        try {
            this.bout.writeByte(123);
            this.writeObject0(iOException, false);
            this.clear();
            return;
        }
        finally {
            this.bout.setBlockDataMode(bl);
        }
    }

    private void writeHandle(int n) throws IOException {
        this.bout.writeByte(113);
        this.bout.writeInt(8257536 + n);
    }

    private void writeNonProxyDesc(ObjectStreamClass objectStreamClass, boolean bl) throws IOException {
        this.bout.writeByte(114);
        HandleTable handleTable = this.handles;
        Serializable serializable = bl ? null : objectStreamClass;
        handleTable.assign(serializable);
        if (this.protocol == 1) {
            objectStreamClass.writeNonProxy(this);
        } else {
            this.writeClassDescriptor(objectStreamClass);
        }
        serializable = objectStreamClass.forClass();
        this.bout.setBlockDataMode(true);
        if (serializable != null && this.isCustomSubclass()) {
            ReflectUtil.checkPackageAccess(serializable);
        }
        this.annotateClass((Class<?>)serializable);
        this.bout.setBlockDataMode(false);
        this.bout.writeByte(120);
        this.writeClassDesc(objectStreamClass.getSuperDesc(), false);
    }

    private void writeNull() throws IOException {
        this.bout.writeByte(112);
    }

    private void writeObject0(Object object, boolean bl) throws IOException {
        boolean bl2;
        Object object2;
        block33 : {
            block32 : {
                Object object3;
                Object object4;
                block31 : {
                    int n;
                    Class<?> class_;
                    block29 : {
                        Class<?> class_2;
                        Object object5;
                        block28 : {
                            block27 : {
                                block26 : {
                                    bl2 = this.bout.setBlockDataMode(false);
                                    ++this.depth;
                                    object = this.subs.lookup(object);
                                    class_ = object;
                                    if (object != null) break block26;
                                    this.writeNull();
                                    return;
                                }
                                if (!bl) {
                                    n = this.handles.lookup(class_);
                                    if (n == -1) break block27;
                                    this.writeHandle(n);
                                    --this.depth;
                                    this.bout.setBlockDataMode(bl2);
                                    return;
                                }
                            }
                            object5 = class_.getClass();
                            object3 = ObjectStreamClass.lookup(object5, true);
                            class_2 = object5;
                            object = object3;
                            object2 = class_;
                            if (!((ObjectStreamClass)object3).hasWriteReplaceMethod()) break block28;
                            Class<?> class_3 = ((ObjectStreamClass)object3).invokeWriteReplace(class_);
                            object4 = class_3;
                            class_2 = object5;
                            object = object3;
                            object2 = object4;
                            if (class_3 == null) break block28;
                            class_3 = object4.getClass();
                            class_2 = object5;
                            object = object3;
                            object2 = object4;
                            if (class_3 == object5) break block28;
                            class_2 = class_3;
                            object = ObjectStreamClass.lookup(class_2, true);
                            object2 = object4;
                        }
                        object5 = object2;
                        object2 = class_2;
                        object4 = object;
                        object3 = object5;
                        if (!this.enableReplace) break block29;
                        object3 = this.replaceObject(object5);
                        object2 = class_2;
                        object4 = object;
                        if (object3 == object5) break block29;
                        object2 = class_2;
                        object4 = object;
                        if (object3 == null) break block29;
                        object2 = object3.getClass();
                        object4 = ObjectStreamClass.lookup(object2, true);
                    }
                    if (object3 != class_) {
                        block30 : {
                            this.subs.assign(class_, object3);
                            if (object3 != null) break block30;
                            this.writeNull();
                            --this.depth;
                            this.bout.setBlockDataMode(bl2);
                            return;
                        }
                        if (!bl) {
                            n = this.handles.lookup(object3);
                            if (n == -1) break block31;
                            this.writeHandle(n);
                            --this.depth;
                            this.bout.setBlockDataMode(bl2);
                            return;
                        }
                    }
                }
                if (object3 instanceof Class) {
                    this.writeClass((Class)object3, bl);
                    break block32;
                }
                if (object3 instanceof ObjectStreamClass) {
                    this.writeClassDesc((ObjectStreamClass)object3, bl);
                    break block32;
                }
                if (object3 instanceof String) {
                    this.writeString((String)object3, bl);
                    break block32;
                }
                if (((Class)object2).isArray()) {
                    this.writeArray(object3, (ObjectStreamClass)object4, bl);
                    break block32;
                }
                if (object3 instanceof Enum) {
                    this.writeEnum((Enum)object3, (ObjectStreamClass)object4, bl);
                    break block32;
                }
                if (!(object3 instanceof Serializable)) break block33;
                this.writeOrdinaryObject(object3, (ObjectStreamClass)object4, bl);
            }
            --this.depth;
            this.bout.setBlockDataMode(bl2);
            return;
        }
        try {
            object = new NotSerializableException(((Class)object2).getName());
            throw object;
        }
        finally {
            --this.depth;
            this.bout.setBlockDataMode(bl2);
        }
    }

    private void writeOrdinaryObject(Object object, ObjectStreamClass objectStreamClass, boolean bl) throws IOException {
        objectStreamClass.checkSerialize();
        this.bout.writeByte(115);
        this.writeClassDesc(objectStreamClass, false);
        HandleTable handleTable = this.handles;
        Object object2 = bl ? null : object;
        handleTable.assign(object2);
        if (objectStreamClass.isExternalizable() && !objectStreamClass.isProxy()) {
            this.writeExternalData((Externalizable)object);
        } else {
            this.writeSerialData(object, objectStreamClass);
        }
    }

    private void writeProxyDesc(ObjectStreamClass arrclass, boolean bl) throws IOException {
        this.bout.writeByte(125);
        Object object = this.handles;
        Class<?>[] arrclass2 = bl ? null : arrclass;
        ((HandleTable)object).assign(arrclass2);
        object = arrclass.forClass();
        arrclass2 = ((Class)object).getInterfaces();
        this.bout.writeInt(arrclass2.length);
        for (int i = 0; i < arrclass2.length; ++i) {
            this.bout.writeUTF(arrclass2[i].getName());
        }
        this.bout.setBlockDataMode(true);
        if (this.isCustomSubclass()) {
            ReflectUtil.checkPackageAccess(object);
        }
        this.annotateProxyClass((Class<?>)object);
        this.bout.setBlockDataMode(false);
        this.bout.writeByte(120);
        this.writeClassDesc(arrclass.getSuperDesc(), false);
    }

    private void writeSerialData(Object object, ObjectStreamClass object2) throws IOException {
        ObjectStreamClass.ClassDataSlot[] arrclassDataSlot = ((ObjectStreamClass)object2).getClassDataLayout();
        for (int i = 0; i < arrclassDataSlot.length; ++i) {
            ObjectStreamClass objectStreamClass = arrclassDataSlot[i].desc;
            if (objectStreamClass.hasWriteObjectMethod()) {
                PutFieldImpl putFieldImpl = this.curPut;
                this.curPut = null;
                object2 = this.curContext;
                try {
                    SerialCallbackContext serialCallbackContext;
                    this.curContext = serialCallbackContext = new SerialCallbackContext(object, objectStreamClass);
                    this.bout.setBlockDataMode(true);
                    objectStreamClass.invokeWriteObject(object, this);
                    this.bout.setBlockDataMode(false);
                    this.bout.writeByte(120);
                    this.curPut = putFieldImpl;
                    continue;
                }
                finally {
                    this.curContext.setUsed();
                    this.curContext = object2;
                }
            }
            this.defaultWriteFields(object, objectStreamClass);
        }
    }

    private void writeString(String string, boolean bl) throws IOException {
        HandleTable handleTable = this.handles;
        String string2 = bl ? null : string;
        handleTable.assign(string2);
        long l = this.bout.getUTFLength(string);
        if (l <= 65535L) {
            this.bout.writeByte(116);
            this.bout.writeUTF(string, l);
        } else {
            this.bout.writeByte(124);
            this.bout.writeLongUTF(string, l);
        }
    }

    protected void annotateClass(Class<?> class_) throws IOException {
    }

    protected void annotateProxyClass(Class<?> class_) throws IOException {
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.bout.close();
    }

    public void defaultWriteObject() throws IOException {
        Object object = this.curContext;
        if (object != null) {
            Object object2 = ((SerialCallbackContext)object).getObj();
            object = ((SerialCallbackContext)object).getDesc();
            this.bout.setBlockDataMode(false);
            this.defaultWriteFields(object2, (ObjectStreamClass)object);
            this.bout.setBlockDataMode(true);
            return;
        }
        throw new NotActiveException("not in call to writeObject");
    }

    protected void drain() throws IOException {
        this.bout.drain();
    }

    protected boolean enableReplaceObject(boolean bl) throws SecurityException {
        SecurityManager securityManager;
        if (bl == this.enableReplace) {
            return bl;
        }
        if (bl && (securityManager = System.getSecurityManager()) != null) {
            securityManager.checkPermission(SUBSTITUTION_PERMISSION);
        }
        this.enableReplace = bl;
        return this.enableReplace ^ true;
    }

    @Override
    public void flush() throws IOException {
        this.bout.flush();
    }

    int getProtocolVersion() {
        return this.protocol;
    }

    public PutField putFields() throws IOException {
        if (this.curPut == null) {
            SerialCallbackContext serialCallbackContext = this.curContext;
            if (serialCallbackContext != null) {
                serialCallbackContext.getObj();
                this.curPut = new PutFieldImpl(serialCallbackContext.getDesc());
            } else {
                throw new NotActiveException("not in call to writeObject");
            }
        }
        return this.curPut;
    }

    protected Object replaceObject(Object object) throws IOException {
        return object;
    }

    public void reset() throws IOException {
        if (this.depth == 0) {
            this.bout.setBlockDataMode(false);
            this.bout.writeByte(121);
            this.clear();
            this.bout.setBlockDataMode(true);
            return;
        }
        throw new IOException("stream active");
    }

    public void useProtocolVersion(int n) throws IOException {
        if (this.handles.size() == 0) {
            if (n != 1 && n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unknown version: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.protocol = n;
            return;
        }
        throw new IllegalStateException("stream non-empty");
    }

    @Override
    public void write(int n) throws IOException {
        this.bout.write(n);
    }

    @Override
    public void write(byte[] arrby) throws IOException {
        this.bout.write(arrby, 0, arrby.length, false);
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        if (arrby != null) {
            int n3 = n + n2;
            if (n >= 0 && n2 >= 0 && n3 <= arrby.length && n3 >= 0) {
                this.bout.write(arrby, n, n2, false);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new NullPointerException();
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        this.bout.writeBoolean(bl);
    }

    @Override
    public void writeByte(int n) throws IOException {
        this.bout.writeByte(n);
    }

    @Override
    public void writeBytes(String string) throws IOException {
        this.bout.writeBytes(string);
    }

    @Override
    public void writeChar(int n) throws IOException {
        this.bout.writeChar(n);
    }

    @Override
    public void writeChars(String string) throws IOException {
        this.bout.writeChars(string);
    }

    protected void writeClassDescriptor(ObjectStreamClass objectStreamClass) throws IOException {
        objectStreamClass.writeNonProxy(this);
    }

    @Override
    public void writeDouble(double d) throws IOException {
        this.bout.writeDouble(d);
    }

    public void writeFields() throws IOException {
        if (this.curPut != null) {
            this.bout.setBlockDataMode(false);
            this.curPut.writeFields();
            this.bout.setBlockDataMode(true);
            return;
        }
        throw new NotActiveException("no current PutField object");
    }

    @Override
    public void writeFloat(float f) throws IOException {
        this.bout.writeFloat(f);
    }

    @Override
    public void writeInt(int n) throws IOException {
        this.bout.writeInt(n);
    }

    @Override
    public void writeLong(long l) throws IOException {
        this.bout.writeLong(l);
    }

    @Override
    public final void writeObject(Object object) throws IOException {
        if (this.enableOverride) {
            this.writeObjectOverride(object);
            return;
        }
        try {
            this.writeObject0(object, false);
            return;
        }
        catch (IOException iOException) {
            if (this.depth == 0) {
                try {
                    this.writeFatalException(iOException);
                }
                catch (IOException iOException2) {
                    // empty catch block
                }
            }
            throw iOException;
        }
    }

    protected void writeObjectOverride(Object object) throws IOException {
        if (this.enableOverride) {
            return;
        }
        throw new IOException();
    }

    @Override
    public void writeShort(int n) throws IOException {
        this.bout.writeShort(n);
    }

    protected void writeStreamHeader() throws IOException {
        this.bout.writeShort(-21267);
        this.bout.writeShort(5);
    }

    void writeTypeString(String string) throws IOException {
        if (string == null) {
            this.writeNull();
        } else {
            int n = this.handles.lookup(string);
            if (n != -1) {
                this.writeHandle(n);
            } else {
                this.writeString(string, false);
            }
        }
    }

    @Override
    public void writeUTF(String string) throws IOException {
        this.bout.writeUTF(string);
    }

    public void writeUnshared(Object object) throws IOException {
        try {
            this.writeObject0(object, true);
            return;
        }
        catch (IOException iOException) {
            if (this.depth == 0) {
                this.writeFatalException(iOException);
            }
            throw iOException;
        }
    }

    private static class BlockDataOutputStream
    extends OutputStream
    implements DataOutput {
        private static final int CHAR_BUF_SIZE = 256;
        private static final int MAX_BLOCK_SIZE = 1024;
        private static final int MAX_HEADER_SIZE = 5;
        private boolean blkmode = false;
        private final byte[] buf = new byte[1024];
        private final char[] cbuf = new char[256];
        private final DataOutputStream dout;
        private final byte[] hbuf = new byte[5];
        private final OutputStream out;
        private int pos = 0;
        private boolean warnOnceWhenWriting;

        BlockDataOutputStream(OutputStream outputStream) {
            this.out = outputStream;
            this.dout = new DataOutputStream(this);
        }

        private void warnIfClosed() {
            if (this.warnOnceWhenWriting) {
                System.logW("The app is relying on undefined behavior. Attempting to write to a closed ObjectOutputStream could produce corrupt output in a future release of Android.", new IOException("Stream Closed"));
                this.warnOnceWhenWriting = false;
            }
        }

        private void writeBlockHeader(int n) throws IOException {
            if (n <= 255) {
                byte[] arrby = this.hbuf;
                arrby[0] = (byte)119;
                arrby[1] = (byte)n;
                this.out.write(arrby, 0, 2);
            } else {
                byte[] arrby = this.hbuf;
                arrby[0] = (byte)122;
                Bits.putInt(arrby, 1, n);
                this.out.write(this.hbuf, 0, 5);
            }
            this.warnIfClosed();
        }

        private void writeUTFBody(String string) throws IOException {
            int n;
            int n2 = string.length();
            for (int i = 0; i < n2; i += n) {
                n = Math.min(n2 - i, 256);
                string.getChars(i, i + n, this.cbuf, 0);
                for (int j = 0; j < n; ++j) {
                    char c = this.cbuf[j];
                    int n3 = this.pos;
                    if (n3 <= 1021) {
                        byte[] arrby;
                        if (c <= '' && c != '\u0000') {
                            arrby = this.buf;
                            this.pos = n3 + 1;
                            arrby[n3] = (byte)c;
                            continue;
                        }
                        if (c > '\u07ff') {
                            arrby = this.buf;
                            n3 = this.pos;
                            arrby[n3 + 2] = (byte)(c >> 0 & 63 | 128);
                            arrby[n3 + 1] = (byte)(c >> 6 & 63 | 128);
                            arrby[n3 + 0] = (byte)(c >> 12 & 15 | 224);
                            this.pos = n3 + 3;
                            continue;
                        }
                        arrby = this.buf;
                        n3 = this.pos;
                        arrby[n3 + 1] = (byte)(c >> 0 & 63 | 128);
                        arrby[n3 + 0] = (byte)(c >> 6 & 31 | 192);
                        this.pos = n3 + 2;
                        continue;
                    }
                    if (c <= '' && c != '\u0000') {
                        this.write(c);
                        continue;
                    }
                    if (c > '\u07ff') {
                        this.write(c >> 12 & 15 | 224);
                        this.write(c >> 6 & 63 | 128);
                        this.write(c >> 0 & 63 | 128);
                        continue;
                    }
                    this.write(c >> 6 & 31 | 192);
                    this.write(c >> 0 & 63 | 128);
                }
            }
        }

        @Override
        public void close() throws IOException {
            this.flush();
            this.out.close();
            this.warnOnceWhenWriting = true;
        }

        void drain() throws IOException {
            int n = this.pos;
            if (n == 0) {
                return;
            }
            if (this.blkmode) {
                this.writeBlockHeader(n);
            }
            this.out.write(this.buf, 0, this.pos);
            this.pos = 0;
            this.warnIfClosed();
        }

        @Override
        public void flush() throws IOException {
            this.drain();
            this.out.flush();
        }

        boolean getBlockDataMode() {
            return this.blkmode;
        }

        long getUTFLength(String string) {
            int n;
            int n2 = string.length();
            long l = 0L;
            for (int i = 0; i < n2; i += n) {
                n = Math.min(n2 - i, 256);
                string.getChars(i, i + n, this.cbuf, 0);
                for (int j = 0; j < n; ++j) {
                    char c = this.cbuf[j];
                    if (c >= '\u0001' && c <= '') {
                        ++l;
                        continue;
                    }
                    if (c > '\u07ff') {
                        l += 3L;
                        continue;
                    }
                    l += 2L;
                }
            }
            return l;
        }

        boolean setBlockDataMode(boolean bl) throws IOException {
            boolean bl2 = this.blkmode;
            if (bl2 == bl) {
                return bl2;
            }
            this.drain();
            this.blkmode = bl;
            return this.blkmode ^ true;
        }

        @Override
        public void write(int n) throws IOException {
            if (this.pos >= 1024) {
                this.drain();
            }
            byte[] arrby = this.buf;
            int n2 = this.pos;
            this.pos = n2 + 1;
            arrby[n2] = (byte)n;
        }

        @Override
        public void write(byte[] arrby) throws IOException {
            this.write(arrby, 0, arrby.length, false);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            this.write(arrby, n, n2, false);
        }

        void write(byte[] arrby, int n, int n2, boolean bl) throws IOException {
            int n3 = n;
            int n4 = n2;
            if (!bl) {
                n3 = n;
                n4 = n2;
                if (!this.blkmode) {
                    this.drain();
                    this.out.write(arrby, n, n2);
                    this.warnIfClosed();
                    return;
                }
            }
            while (n4 > 0) {
                if (this.pos >= 1024) {
                    this.drain();
                }
                if (n4 >= 1024 && !bl && this.pos == 0) {
                    this.writeBlockHeader(1024);
                    this.out.write(arrby, n3, 1024);
                    n3 += 1024;
                    n4 -= 1024;
                    continue;
                }
                n = Math.min(n4, 1024 - this.pos);
                System.arraycopy(arrby, n3, this.buf, this.pos, n);
                this.pos += n;
                n3 += n;
                n4 -= n;
            }
            this.warnIfClosed();
        }

        @Override
        public void writeBoolean(boolean bl) throws IOException {
            if (this.pos >= 1024) {
                this.drain();
            }
            byte[] arrby = this.buf;
            int n = this.pos;
            this.pos = n + 1;
            Bits.putBoolean(arrby, n, bl);
        }

        void writeBooleans(boolean[] arrbl, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                if (this.pos >= 1024) {
                    this.drain();
                }
                int n3 = Math.min(n2, 1024 - this.pos + n);
                while (n < n3) {
                    byte[] arrby = this.buf;
                    int n4 = this.pos;
                    this.pos = n4 + 1;
                    Bits.putBoolean(arrby, n4, arrbl[n]);
                    ++n;
                }
            }
        }

        @Override
        public void writeByte(int n) throws IOException {
            if (this.pos >= 1024) {
                this.drain();
            }
            byte[] arrby = this.buf;
            int n2 = this.pos;
            this.pos = n2 + 1;
            arrby[n2] = (byte)n;
        }

        @Override
        public void writeBytes(String string) throws IOException {
            int n = string.length();
            int n2 = 0;
            int n3 = 0;
            for (int i = 0; i < n; i += n3) {
                int n4;
                int n5 = n2;
                int n6 = n3;
                if (n2 >= n3) {
                    n5 = 0;
                    n6 = Math.min(n - i, 256);
                    string.getChars(i, i + n6, this.cbuf, 0);
                }
                if (this.pos >= 1024) {
                    this.drain();
                }
                n3 = Math.min(n6 - n5, 1024 - this.pos);
                n2 = this.pos;
                while ((n4 = this.pos) < n2 + n3) {
                    byte[] arrby = this.buf;
                    this.pos = n4 + 1;
                    arrby[n4] = (byte)this.cbuf[n5];
                    ++n5;
                }
                n2 = n5;
                n3 = n6;
            }
        }

        @Override
        public void writeChar(int n) throws IOException {
            int n2 = this.pos;
            if (n2 + 2 <= 1024) {
                Bits.putChar(this.buf, n2, (char)n);
                this.pos += 2;
            } else {
                this.dout.writeChar(n);
            }
        }

        @Override
        public void writeChars(String string) throws IOException {
            int n;
            int n2 = string.length();
            for (int i = 0; i < n2; i += n) {
                n = Math.min(n2 - i, 256);
                string.getChars(i, i + n, this.cbuf, 0);
                this.writeChars(this.cbuf, 0, n);
            }
        }

        void writeChars(char[] arrc, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                int n3 = this.pos;
                if (n3 <= 1022) {
                    n3 = Math.min(n2, n + (1024 - n3 >> 1));
                    while (n < n3) {
                        Bits.putChar(this.buf, this.pos, arrc[n]);
                        this.pos += 2;
                        ++n;
                    }
                    continue;
                }
                this.dout.writeChar(arrc[n]);
                ++n;
            }
        }

        @Override
        public void writeDouble(double d) throws IOException {
            int n = this.pos;
            if (n + 8 <= 1024) {
                Bits.putDouble(this.buf, n, d);
                this.pos += 8;
            } else {
                this.dout.writeDouble(d);
            }
        }

        void writeDoubles(double[] arrd, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                int n3 = this.pos;
                if (n3 <= 1016) {
                    n3 = Math.min(n2 - n, 1024 - n3 >> 3);
                    ObjectOutputStream.doublesToBytes(arrd, n, this.buf, this.pos, n3);
                    n += n3;
                    this.pos += n3 << 3;
                    continue;
                }
                this.dout.writeDouble(arrd[n]);
                ++n;
            }
        }

        @Override
        public void writeFloat(float f) throws IOException {
            int n = this.pos;
            if (n + 4 <= 1024) {
                Bits.putFloat(this.buf, n, f);
                this.pos += 4;
            } else {
                this.dout.writeFloat(f);
            }
        }

        void writeFloats(float[] arrf, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                int n3 = this.pos;
                if (n3 <= 1020) {
                    n3 = Math.min(n2 - n, 1024 - n3 >> 2);
                    ObjectOutputStream.floatsToBytes(arrf, n, this.buf, this.pos, n3);
                    n += n3;
                    this.pos += n3 << 2;
                    continue;
                }
                this.dout.writeFloat(arrf[n]);
                ++n;
            }
        }

        @Override
        public void writeInt(int n) throws IOException {
            int n2 = this.pos;
            if (n2 + 4 <= 1024) {
                Bits.putInt(this.buf, n2, n);
                this.pos += 4;
            } else {
                this.dout.writeInt(n);
            }
        }

        void writeInts(int[] arrn, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                int n3 = this.pos;
                if (n3 <= 1020) {
                    n3 = Math.min(n2, n + (1024 - n3 >> 2));
                    while (n < n3) {
                        Bits.putInt(this.buf, this.pos, arrn[n]);
                        this.pos += 4;
                        ++n;
                    }
                    continue;
                }
                this.dout.writeInt(arrn[n]);
                ++n;
            }
        }

        @Override
        public void writeLong(long l) throws IOException {
            int n = this.pos;
            if (n + 8 <= 1024) {
                Bits.putLong(this.buf, n, l);
                this.pos += 8;
            } else {
                this.dout.writeLong(l);
            }
        }

        void writeLongUTF(String string) throws IOException {
            this.writeLongUTF(string, this.getUTFLength(string));
        }

        void writeLongUTF(String string, long l) throws IOException {
            this.writeLong(l);
            if (l == (long)string.length()) {
                this.writeBytes(string);
            } else {
                this.writeUTFBody(string);
            }
        }

        void writeLongs(long[] arrl, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                int n3 = this.pos;
                if (n3 <= 1016) {
                    n3 = Math.min(n2, n + (1024 - n3 >> 3));
                    while (n < n3) {
                        Bits.putLong(this.buf, this.pos, arrl[n]);
                        this.pos += 8;
                        ++n;
                    }
                    continue;
                }
                this.dout.writeLong(arrl[n]);
                ++n;
            }
        }

        @Override
        public void writeShort(int n) throws IOException {
            int n2 = this.pos;
            if (n2 + 2 <= 1024) {
                Bits.putShort(this.buf, n2, (short)n);
                this.pos += 2;
            } else {
                this.dout.writeShort(n);
            }
        }

        void writeShorts(short[] arrs, int n, int n2) throws IOException {
            n2 = n + n2;
            while (n < n2) {
                int n3 = this.pos;
                if (n3 <= 1022) {
                    n3 = Math.min(n2, n + (1024 - n3 >> 1));
                    while (n < n3) {
                        Bits.putShort(this.buf, this.pos, arrs[n]);
                        this.pos += 2;
                        ++n;
                    }
                    continue;
                }
                this.dout.writeShort(arrs[n]);
                ++n;
            }
        }

        @Override
        public void writeUTF(String string) throws IOException {
            this.writeUTF(string, this.getUTFLength(string));
        }

        void writeUTF(String string, long l) throws IOException {
            if (l <= 65535L) {
                this.writeShort((int)l);
                if (l == (long)string.length()) {
                    this.writeBytes(string);
                } else {
                    this.writeUTFBody(string);
                }
                return;
            }
            throw new UTFDataFormatException();
        }
    }

    private static class Caches {
        static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap<ObjectStreamClass.WeakClassKey, Boolean>();
        static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue();

        private Caches() {
        }
    }

    private static class DebugTraceInfoStack {
        private final List<String> stack = new ArrayList<String>();

        DebugTraceInfoStack() {
        }

        void clear() {
            this.stack.clear();
        }

        void pop() {
            List<String> list = this.stack;
            list.remove(list.size() - 1);
        }

        void push(String string) {
            List<String> list = this.stack;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\t- ");
            stringBuilder.append(string);
            list.add(stringBuilder.toString());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            if (!this.stack.isEmpty()) {
                for (int i = this.stack.size(); i > 0; --i) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(this.stack.get(i - 1));
                    String string = i != 1 ? "\n" : "";
                    stringBuilder2.append(string);
                    stringBuilder.append(stringBuilder2.toString());
                }
            }
            return stringBuilder.toString();
        }
    }

    private static class HandleTable {
        private final float loadFactor;
        private int[] next;
        private Object[] objs;
        private int size;
        private int[] spine;
        private int threshold;

        HandleTable(int n, float f) {
            this.loadFactor = f;
            this.spine = new int[n];
            this.next = new int[n];
            this.objs = new Object[n];
            this.threshold = (int)((float)n * f);
            this.clear();
        }

        private void growEntries() {
            Object[] arrobject = this.next;
            int n = (arrobject.length << 1) + 1;
            int[] arrn = new int[n];
            System.arraycopy(arrobject, 0, (Object)arrn, 0, this.size);
            this.next = arrn;
            arrobject = new Object[n];
            System.arraycopy(this.objs, 0, arrobject, 0, this.size);
            this.objs = arrobject;
        }

        private void growSpine() {
            int[] arrn = this.spine = new int[(this.spine.length << 1) + 1];
            this.threshold = (int)((float)arrn.length * this.loadFactor);
            Arrays.fill(arrn, -1);
            for (int i = 0; i < this.size; ++i) {
                this.insert(this.objs[i], i);
            }
        }

        private int hash(Object object) {
            return System.identityHashCode(object) & Integer.MAX_VALUE;
        }

        private void insert(Object object, int n) {
            int n2 = this.hash(object);
            int[] arrn = this.spine;
            this.objs[n] = object;
            this.next[n] = arrn[n2 %= arrn.length];
            arrn[n2] = n;
        }

        int assign(Object object) {
            if (this.size >= this.next.length) {
                this.growEntries();
            }
            if (this.size >= this.threshold) {
                this.growSpine();
            }
            this.insert(object, this.size);
            int n = this.size;
            this.size = n + 1;
            return n;
        }

        void clear() {
            Arrays.fill(this.spine, -1);
            Arrays.fill(this.objs, 0, this.size, null);
            this.size = 0;
        }

        int lookup(Object object) {
            if (this.size == 0) {
                return -1;
            }
            int n = this.hash(object);
            int[] arrn = this.spine;
            n = arrn[n % arrn.length];
            while (n >= 0) {
                if (this.objs[n] == object) {
                    return n;
                }
                n = this.next[n];
            }
            return -1;
        }

        int size() {
            return this.size;
        }
    }

    public static abstract class PutField {
        public abstract void put(String var1, byte var2);

        public abstract void put(String var1, char var2);

        public abstract void put(String var1, double var2);

        public abstract void put(String var1, float var2);

        public abstract void put(String var1, int var2);

        public abstract void put(String var1, long var2);

        public abstract void put(String var1, Object var2);

        public abstract void put(String var1, short var2);

        public abstract void put(String var1, boolean var2);

        @Deprecated
        public abstract void write(ObjectOutput var1) throws IOException;
    }

    private class PutFieldImpl
    extends PutField {
        private final ObjectStreamClass desc;
        private final Object[] objVals;
        private final byte[] primVals;

        PutFieldImpl(ObjectStreamClass objectStreamClass) {
            this.desc = objectStreamClass;
            this.primVals = new byte[objectStreamClass.getPrimDataSize()];
            this.objVals = new Object[objectStreamClass.getNumObjFields()];
        }

        private int getFieldOffset(String string, Class<?> class_) {
            Object object = this.desc.getField(string, class_);
            if (object != null) {
                return ((ObjectStreamField)object).getOffset();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("no such field ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" with type ");
            ((StringBuilder)object).append(class_);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        @Override
        public void put(String string, byte by) {
            this.primVals[this.getFieldOffset((String)string, Byte.TYPE)] = by;
        }

        @Override
        public void put(String string, char c) {
            Bits.putChar(this.primVals, this.getFieldOffset(string, Character.TYPE), c);
        }

        @Override
        public void put(String string, double d) {
            Bits.putDouble(this.primVals, this.getFieldOffset(string, Double.TYPE), d);
        }

        @Override
        public void put(String string, float f) {
            Bits.putFloat(this.primVals, this.getFieldOffset(string, Float.TYPE), f);
        }

        @Override
        public void put(String string, int n) {
            Bits.putInt(this.primVals, this.getFieldOffset(string, Integer.TYPE), n);
        }

        @Override
        public void put(String string, long l) {
            Bits.putLong(this.primVals, this.getFieldOffset(string, Long.TYPE), l);
        }

        @Override
        public void put(String string, Object object) {
            this.objVals[this.getFieldOffset((String)string, Object.class)] = object;
        }

        @Override
        public void put(String string, short s) {
            Bits.putShort(this.primVals, this.getFieldOffset(string, Short.TYPE), s);
        }

        @Override
        public void put(String string, boolean bl) {
            Bits.putBoolean(this.primVals, this.getFieldOffset(string, Boolean.TYPE), bl);
        }

        @Override
        public void write(ObjectOutput objectOutput) throws IOException {
            if (ObjectOutputStream.this == objectOutput) {
                Object[] arrobject = this.primVals;
                objectOutput.write((byte[])arrobject, 0, arrobject.length);
                arrobject = this.desc.getFields(false);
                int n = arrobject.length;
                int n2 = this.objVals.length;
                for (int i = 0; i < this.objVals.length; ++i) {
                    if (!arrobject[n - n2 + i].isUnshared()) {
                        objectOutput.writeObject(this.objVals[i]);
                        continue;
                    }
                    throw new IOException("cannot write unshared object");
                }
                return;
            }
            throw new IllegalArgumentException("wrong stream");
        }

        void writeFields() throws IOException {
            ObjectStreamField[] arrobjectStreamField = ObjectOutputStream.this.bout;
            Object[] arrobject = this.primVals;
            arrobjectStreamField.write((byte[])arrobject, 0, arrobject.length, false);
            arrobjectStreamField = this.desc.getFields(false);
            int n = arrobjectStreamField.length;
            int n2 = this.objVals.length;
            for (int i = 0; i < (arrobject = this.objVals).length; ++i) {
                ObjectOutputStream.this.writeObject0(arrobject[i], arrobjectStreamField[n - n2 + i].isUnshared());
            }
        }
    }

    private static class ReplaceTable {
        private final HandleTable htab;
        private Object[] reps;

        ReplaceTable(int n, float f) {
            this.htab = new HandleTable(n, f);
            this.reps = new Object[n];
        }

        private void grow() {
            Object[] arrobject = this.reps;
            Object[] arrobject2 = new Object[(arrobject.length << 1) + 1];
            System.arraycopy(arrobject, 0, arrobject2, 0, arrobject.length);
            this.reps = arrobject2;
        }

        void assign(Object arrobject, Object object) {
            int n = this.htab.assign(arrobject);
            while (n >= (arrobject = this.reps).length) {
                this.grow();
            }
            arrobject[n] = object;
        }

        void clear() {
            Arrays.fill(this.reps, 0, this.htab.size(), null);
            this.htab.clear();
        }

        Object lookup(Object object) {
            block0 : {
                int n = this.htab.lookup(object);
                if (n < 0) break block0;
                object = this.reps[n];
            }
            return object;
        }

        int size() {
            return this.htab.size();
        }
    }

}

