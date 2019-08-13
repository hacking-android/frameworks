/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import sun.nio.fs.AbstractUserDefinedFileAttributeView;
import sun.nio.fs.LinuxNativeDispatcher;
import sun.nio.fs.NativeBuffer;
import sun.nio.fs.NativeBuffers;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixPath;
import sun.nio.fs.Util;

class LinuxUserDefinedFileAttributeView
extends AbstractUserDefinedFileAttributeView {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String USER_NAMESPACE = "user.";
    private static final int XATTR_NAME_MAX = 255;
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private final UnixPath file;
    private final boolean followLinks;

    LinuxUserDefinedFileAttributeView(UnixPath unixPath, boolean bl) {
        this.file = unixPath;
        this.followLinks = bl;
    }

    private List<String> asList(long l, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = n2;
            if (unsafe.getByte((long)i + l) == 0) {
                int n4 = i - n2;
                Object object = new byte[n4];
                for (n3 = 0; n3 < n4; ++n3) {
                    object[n3] = unsafe.getByte((long)n2 + l + (long)n3);
                }
                if (((String)(object = Util.toString((byte[])object))).startsWith(USER_NAMESPACE)) {
                    arrayList.add(((String)object).substring(USER_NAMESPACE.length()));
                }
                n3 = i + 1;
            }
            n2 = n3;
        }
        return arrayList;
    }

    private static void copyExtendedAttribute(int n, byte[] arrby, int n2) throws UnixException {
        int n3 = LinuxNativeDispatcher.fgetxattr(n, arrby, 0L, 0);
        NativeBuffer nativeBuffer = NativeBuffers.getNativeBuffer(n3);
        try {
            long l = nativeBuffer.address();
            LinuxNativeDispatcher.fsetxattr(n2, arrby, l, LinuxNativeDispatcher.fgetxattr(n, arrby, l, n3));
            return;
        }
        finally {
            nativeBuffer.release();
        }
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static void copyExtendedAttributes(int n, int n2) {
        NativeBuffer nativeBuffer = null;
        int n3 = 1024;
        NativeBuffer nativeBuffer2 = NativeBuffers.getNativeBuffer(1024);
        do {
            nativeBuffer = nativeBuffer2;
            int n4 = LinuxNativeDispatcher.flistxattr(n, nativeBuffer2.address(), n3);
            nativeBuffer = nativeBuffer2;
            long l = nativeBuffer2.address();
            int n5 = 0;
            for (n3 = 0; n3 < n4; ++n3) {
                int n6;
                block16 : {
                    n6 = n5;
                    nativeBuffer = nativeBuffer2;
                    if (unsafe.getByte((long)n3 + l) != 0) break block16;
                    int n7 = n3 - n5;
                    nativeBuffer = nativeBuffer2;
                    byte[] arrby = new byte[n7];
                    for (n6 = 0; n6 < n7; ++n6) {
                        nativeBuffer = nativeBuffer2;
                        arrby[n6] = unsafe.getByte((long)n5 + l + (long)n6);
                    }
                    nativeBuffer = nativeBuffer2;
                    try {
                        LinuxUserDefinedFileAttributeView.copyExtendedAttribute(n, arrby, n2);
                    }
                    catch (UnixException unixException) {
                        // empty catch block
                    }
                    n6 = n3 + 1;
                }
                n5 = n6;
            }
            nativeBuffer2.release();
            return;
            catch (UnixException unixException) {
                block17 : {
                    nativeBuffer = nativeBuffer2;
                    try {
                        if (unixException.errno() != UnixConstants.ERANGE || n3 >= 32768) break block17;
                        nativeBuffer = nativeBuffer2;
                    }
                    catch (Throwable throwable) {
                        if (nativeBuffer == null) throw throwable;
                        nativeBuffer.release();
                        throw throwable;
                    }
                    nativeBuffer2.release();
                    n3 *= 2;
                    nativeBuffer = null;
                    nativeBuffer2 = NativeBuffers.getNativeBuffer(n3);
                    continue;
                }
                if (nativeBuffer2 == null) return;
                nativeBuffer2.release();
                return;
            }
            break;
        } while (true);
    }

    private byte[] nameAsBytes(UnixPath object, String string) throws IOException {
        if (string != null) {
            Object object2 = new StringBuilder();
            object2.append(USER_NAMESPACE);
            object2.append(string);
            string = object2.toString();
            object2 = Util.toBytes(string);
            if (((byte[])object2).length <= 255) {
                return object2;
            }
            object2 = ((UnixPath)object).getPathForExceptionMessage();
            object = new StringBuilder();
            ((StringBuilder)object).append("'");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("' is too big");
            throw new FileSystemException((String)object2, null, ((StringBuilder)object).toString());
        }
        throw new NullPointerException("'name' is null");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void delete(String string) throws IOException {
        Throwable throwable2222;
        if (System.getSecurityManager() != null) {
            this.checkAccess(this.file.getPathForPermissionCheck(), false, true);
        }
        int n = this.file.openForAttributeAccess(this.followLinks);
        LinuxNativeDispatcher.fremovexattr(n, this.nameAsBytes(this.file, string));
        LinuxNativeDispatcher.close(n);
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (UnixException unixException) {}
            {
                String string2 = this.file.getPathForExceptionMessage();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to delete extended attribute '");
                stringBuilder.append(string);
                stringBuilder.append("': ");
                stringBuilder.append(unixException.getMessage());
                FileSystemException fileSystemException = new FileSystemException(string2, null, stringBuilder.toString());
                throw fileSystemException;
            }
        }
        LinuxNativeDispatcher.close(n);
        throw throwable2222;
    }

    @Override
    public List<String> list() throws IOException {
        if (System.getSecurityManager() != null) {
            this.checkAccess(this.file.getPathForPermissionCheck(), true, false);
        }
        int n = this.file.openForAttributeAccess(this.followLinks);
        NativeBuffer nativeBuffer = null;
        int n2 = 1024;
        NativeBuffer nativeBuffer2 = NativeBuffers.getNativeBuffer(1024);
        do {
            List<String> list;
            nativeBuffer = nativeBuffer2;
            int n3 = LinuxNativeDispatcher.flistxattr(n, nativeBuffer2.address(), n2);
            nativeBuffer = nativeBuffer2;
            try {
                list = Collections.unmodifiableList(this.asList(nativeBuffer2.address(), n3));
                nativeBuffer2.release();
            }
            catch (UnixException unixException) {
                block19 : {
                    nativeBuffer = nativeBuffer2;
                    try {
                        if (unixException.errno() != UnixConstants.ERANGE || n2 >= 32768) break block19;
                        nativeBuffer = nativeBuffer2;
                    }
                    catch (Throwable throwable) {
                        if (nativeBuffer != null) {
                            nativeBuffer.release();
                        }
                        LinuxNativeDispatcher.close(n);
                        throw throwable;
                    }
                    nativeBuffer2.release();
                    n2 *= 2;
                    nativeBuffer = null;
                    nativeBuffer2 = NativeBuffers.getNativeBuffer(n2);
                    continue;
                }
                nativeBuffer = nativeBuffer2;
                nativeBuffer = nativeBuffer2;
                String string = this.file.getPathForExceptionMessage();
                nativeBuffer = nativeBuffer2;
                nativeBuffer = nativeBuffer2;
                StringBuilder stringBuilder = new StringBuilder();
                nativeBuffer = nativeBuffer2;
                stringBuilder.append("Unable to get list of extended attributes: ");
                nativeBuffer = nativeBuffer2;
                stringBuilder.append(unixException.getMessage());
                nativeBuffer = nativeBuffer2;
                FileSystemException fileSystemException = new FileSystemException(string, null, stringBuilder.toString());
                nativeBuffer = nativeBuffer2;
                throw fileSystemException;
            }
            LinuxNativeDispatcher.close(n);
            return list;
            break;
        } while (true);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int read(String var1_1, ByteBuffer var2_4) throws IOException {
        block18 : {
            block17 : {
                block19 : {
                    block16 : {
                        if (System.getSecurityManager() != null) {
                            this.checkAccess(this.file.getPathForPermissionCheck(), true, false);
                        }
                        if (var2_4.isReadOnly() != false) throw new IllegalArgumentException("Read-only buffer");
                        var3_7 = var2_4.position();
                        var4_8 = var3_7 <= (var4_8 = var2_4.limit()) ? (var4_8 -= var3_7) : 0;
                        if (var2_4 instanceof DirectBuffer) {
                            var5_9 = ((DirectBuffer)var2_4).address() + (long)var3_7;
                            var7_10 = null;
                        } else {
                            var7_10 = NativeBuffers.getNativeBuffer(var4_8);
                            var5_9 = var7_10.address();
                        }
                        var8_11 = this.file.openForAttributeAccess(this.followLinks);
                        var9_12 = LinuxNativeDispatcher.fgetxattr(var8_11, this.nameAsBytes(this.file, var1_1), var5_9, var4_8);
                        if (var4_8 != 0) ** GOTO lbl24
                        if (var9_12 > 0) break block16;
                        LinuxNativeDispatcher.close(var8_11);
                        return 0;
                    }
                    var2_4 = new UnixException(UnixConstants.ERANGE);
                    throw var2_4;
lbl24: // 1 sources:
                    if (var7_10 == null) break block17;
                    break block19;
                    finally {
                        if (var7_10 != null) {
                            var7_10.release();
                        }
                    }
                }
                for (var4_8 = 0; var4_8 < var9_12; ++var4_8) {
                    var2_4.put(LinuxUserDefinedFileAttributeView.unsafe.getByte((long)var4_8 + var5_9));
                }
            }
            var2_4.position(var3_7 + var9_12);
            LinuxNativeDispatcher.close(var8_11);
            if (var7_10 == null) return var9_12;
            var7_10.release();
            return var9_12;
            {
                catch (Throwable var1_2) {
                    break block18;
                }
                catch (UnixException var2_5) {}
                {
                    var2_6 = var2_5.errno() == UnixConstants.ERANGE ? "Insufficient space in buffer" : var2_5.getMessage();
                    var11_14 = this.file.getPathForExceptionMessage();
                    var12_15 = new StringBuilder();
                    var12_15.append("Error reading extended attribute '");
                    var12_15.append(var1_1);
                    var12_15.append("': ");
                    var12_15.append(var2_6);
                    var10_13 = new FileSystemException(var11_14, null, var12_15.toString());
                    throw var10_13;
                }
            }
        }
        LinuxNativeDispatcher.close(var8_11);
        throw var1_2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int size(String string) throws IOException {
        Throwable throwable2222;
        if (System.getSecurityManager() != null) {
            this.checkAccess(this.file.getPathForPermissionCheck(), true, false);
        }
        int n = this.file.openForAttributeAccess(this.followLinks);
        int n2 = LinuxNativeDispatcher.fgetxattr(n, this.nameAsBytes(this.file, string), 0L, 0);
        LinuxNativeDispatcher.close(n);
        return n2;
        {
            catch (Throwable throwable2222) {
            }
            catch (UnixException unixException) {}
            {
                String string2 = this.file.getPathForExceptionMessage();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to get size of extended attribute '");
                stringBuilder.append(string);
                stringBuilder.append("': ");
                stringBuilder.append(unixException.getMessage());
                FileSystemException fileSystemException = new FileSystemException(string2, null, stringBuilder.toString());
                throw fileSystemException;
            }
        }
        LinuxNativeDispatcher.close(n);
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public int write(String string, ByteBuffer object) throws IOException {
        Throwable throwable2222;
        int n2;
        int n;
        Object object2;
        long l;
        Object object3 = System.getSecurityManager();
        int n3 = 0;
        if (object3 != null) {
            this.checkAccess(this.file.getPathForPermissionCheck(), false, true);
        }
        if ((n = ((Buffer)object).position()) <= (n2 = ((Buffer)object).limit())) {
            n3 = n2 - n;
        }
        if (object instanceof DirectBuffer) {
            object3 = null;
            l = ((DirectBuffer)object).address() + (long)n;
        } else {
            object2 = NativeBuffers.getNativeBuffer(n3);
            long l2 = ((NativeBuffer)object2).address();
            if (((ByteBuffer)object).hasArray()) {
                for (n2 = 0; n2 < n3; ++n2) {
                    unsafe.putByte((long)n2 + l2, ((ByteBuffer)object).get());
                }
                object3 = object2;
                l = l2;
            } else {
                byte[] arrby = new byte[n3];
                ((ByteBuffer)object).get(arrby);
                ((ByteBuffer)object).position(n);
                n2 = 0;
                do {
                    object3 = object2;
                    l = l2;
                    if (n2 >= n3) break;
                    unsafe.putByte((long)n2 + l2, arrby[n2]);
                    ++n2;
                } while (true);
            }
        }
        n2 = this.file.openForAttributeAccess(this.followLinks);
        LinuxNativeDispatcher.fsetxattr(n2, this.nameAsBytes(this.file, string), l, n3);
        ((ByteBuffer)object).position(n + n3);
        try {
            LinuxNativeDispatcher.close(n2);
            return n3;
        }
        finally {
            if (object3 != null) {
                ((NativeBuffer)object3).release();
            }
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (UnixException unixException) {}
            {
                object = this.file.getPathForExceptionMessage();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Error writing extended attribute '");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append("': ");
                ((StringBuilder)object2).append(unixException.getMessage());
                FileSystemException fileSystemException = new FileSystemException((String)object, null, ((StringBuilder)object2).toString());
                throw fileSystemException;
            }
        }
        LinuxNativeDispatcher.close(n2);
        throw throwable2222;
    }
}

