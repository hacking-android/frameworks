/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.FileDescriptor;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Set;
import sun.misc.JavaIOFileDescriptorAccess;
import sun.misc.SharedSecrets;
import sun.nio.ch.FileChannelImpl;
import sun.nio.ch.SimpleAsynchronousFileChannelImpl;
import sun.nio.ch.ThreadPool;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;

class UnixChannelFactory {
    private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();

    protected UnixChannelFactory() {
    }

    static AsynchronousFileChannel newAsynchronousFileChannel(UnixPath unixPath, Set<? extends OpenOption> object, int n, ThreadPool threadPool) throws UnixException {
        object = Flags.toFlags(object);
        if (!((Flags)object).read && !((Flags)object).write) {
            ((Flags)object).read = true;
        }
        if (!((Flags)object).append) {
            return SimpleAsynchronousFileChannelImpl.open(UnixChannelFactory.open(-1, unixPath, null, (Flags)object, n), ((Flags)object).read, ((Flags)object).write, threadPool);
        }
        throw new UnsupportedOperationException("APPEND not allowed");
    }

    static FileChannel newFileChannel(int n, String string, boolean bl, boolean bl2) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fdAccess.set(fileDescriptor, n);
        return FileChannelImpl.open(fileDescriptor, string, bl, bl2, null);
    }

    static FileChannel newFileChannel(int n, UnixPath unixPath, String string, Set<? extends OpenOption> object, int n2) throws UnixException {
        object = Flags.toFlags(object);
        if (!((Flags)object).read && !((Flags)object).write) {
            if (((Flags)object).append) {
                ((Flags)object).write = true;
            } else {
                ((Flags)object).read = true;
            }
        }
        if (((Flags)object).read && ((Flags)object).append) {
            throw new IllegalArgumentException("READ + APPEND not allowed");
        }
        if (((Flags)object).append && ((Flags)object).truncateExisting) {
            throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
        }
        return FileChannelImpl.open(UnixChannelFactory.open(n, unixPath, string, (Flags)object, n2), unixPath.toString(), ((Flags)object).read, ((Flags)object).write, ((Flags)object).append, null);
    }

    static FileChannel newFileChannel(UnixPath unixPath, Set<? extends OpenOption> set, int n) throws UnixException {
        return UnixChannelFactory.newFileChannel(-1, unixPath, null, set, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected static FileDescriptor open(int var0, UnixPath var1_1, String var2_4, Flags var3_5, int var4_6) throws UnixException {
        block24 : {
            block27 : {
                block25 : {
                    block26 : {
                        var5_7 = var3_5.read != false && var3_5.write != false ? UnixConstants.O_RDWR : (var3_5.write != false ? UnixConstants.O_WRONLY : UnixConstants.O_RDONLY);
                        var6_8 = var5_7;
                        if (var3_5.write) {
                            var6_8 = var5_7;
                            if (var3_5.truncateExisting) {
                                var6_8 = var5_7 | UnixConstants.O_TRUNC;
                            }
                            var5_7 = var6_8;
                            if (var3_5.append) {
                                var5_7 = var6_8 | UnixConstants.O_APPEND;
                            }
                            if (var3_5.createNew) {
                                var7_9 = var1_1.asByteArray();
                                if (var7_9[var7_9.length - 1] == 46) {
                                    if (var7_9.length == 1) throw new UnixException(UnixConstants.EEXIST);
                                    if (var7_9[var7_9.length - 2] == 47) throw new UnixException(UnixConstants.EEXIST);
                                }
                                var6_8 = var5_7 | (UnixConstants.O_CREAT | UnixConstants.O_EXCL);
                            } else {
                                var6_8 = var5_7;
                                if (var3_5.create) {
                                    var6_8 = var5_7 | UnixConstants.O_CREAT;
                                }
                            }
                        }
                        var8_16 = true;
                        var5_7 = var6_8;
                        var9_17 = var8_16;
                        if (var3_5.createNew) break block25;
                        if (var3_5.noFollowLinks) break block26;
                        var5_7 = var6_8;
                        var9_17 = var8_16;
                        if (!var3_5.deleteOnClose) break block25;
                    }
                    if (var3_5.deleteOnClose && UnixConstants.O_NOFOLLOW == 0) {
                        try {
                            if (UnixFileAttributes.get((UnixPath)var1_1, false).isSymbolicLink()) {
                                var7_10 = new UnixException("DELETE_ON_CLOSE specified and file is a symbolic link");
                                throw var7_10;
                            }
                        }
                        catch (UnixException var7_11) {
                            if (var3_5.create == false) throw var7_11;
                            if (var7_11.errno() != UnixConstants.ENOENT) throw var7_11;
                        }
                    }
                    var9_17 = false;
                    var5_7 = var6_8 | UnixConstants.O_NOFOLLOW;
                }
                var6_8 = var5_7;
                if (var3_5.dsync) {
                    var6_8 = var5_7 | UnixConstants.O_DSYNC;
                }
                var5_7 = var6_8;
                if (var3_5.sync) {
                    var5_7 = var6_8 | UnixConstants.O_SYNC;
                }
                if ((var10_18 = System.getSecurityManager()) != null) {
                    var7_13 = var2_4;
                    if (var2_4 == null) {
                        var7_14 = var1_1.getPathForPermissionCheck();
                    }
                    if (var3_5.read) {
                        var10_18.checkRead((String)var7_15);
                    }
                    if (var3_5.write) {
                        var10_18.checkWrite((String)var7_15);
                    }
                    if (var3_5.deleteOnClose) {
                        var10_18.checkDelete((String)var7_15);
                    }
                }
                if (var0 < 0) ** GOTO lbl61
                var4_6 = UnixNativeDispatcher.openat(var0, var1_1.asByteArray(), var5_7, var4_6);
                break block27;
lbl61: // 1 sources:
                var4_6 = UnixNativeDispatcher.open((UnixPath)var1_1, var5_7, var4_6);
            }
            if (!var3_5.deleteOnClose) break block24;
            if (var0 < 0) ** GOTO lbl68
            try {
                UnixNativeDispatcher.unlinkat(var0, var1_1.asByteArray(), 0);
                break block24;
lbl68: // 1 sources:
                UnixNativeDispatcher.unlink((UnixPath)var1_1);
            }
            catch (UnixException var1_2) {
                // empty catch block
            }
        }
        var1_1 = new FileDescriptor();
        UnixChannelFactory.fdAccess.set((FileDescriptor)var1_1, var4_6);
        return var1_1;
        catch (UnixException var1_3) {
            if (var3_5.createNew && var1_3.errno() == UnixConstants.EISDIR) {
                var1_3.setError(UnixConstants.EEXIST);
            }
            var2_4 = var1_3;
            if (var9_17 != false) throw var2_4;
            var2_4 = var1_3;
            if (var1_3.errno() != UnixConstants.ELOOP) throw var2_4;
            var2_4 = new StringBuilder();
            var2_4.append(var1_3.getMessage());
            var2_4.append(" (NOFOLLOW_LINKS specified)");
            var2_4 = new UnixException(var2_4.toString());
            throw var2_4;
        }
    }

    protected static class Flags {
        boolean append;
        boolean create;
        boolean createNew;
        boolean deleteOnClose;
        boolean dsync;
        boolean noFollowLinks;
        boolean read;
        boolean sync;
        boolean truncateExisting;
        boolean write;

        protected Flags() {
        }

        static Flags toFlags(Set<? extends OpenOption> object) {
            Object object2 = new Flags();
            Iterator<? extends OpenOption> iterator = object.iterator();
            block12 : while (iterator.hasNext()) {
                object = iterator.next();
                if (object instanceof StandardOpenOption) {
                    switch ((StandardOpenOption)object) {
                        default: {
                            throw new UnsupportedOperationException();
                        }
                        case DSYNC: {
                            ((Flags)object2).dsync = true;
                            continue block12;
                        }
                        case SYNC: {
                            ((Flags)object2).sync = true;
                            continue block12;
                        }
                        case SPARSE: {
                            continue block12;
                        }
                        case DELETE_ON_CLOSE: {
                            ((Flags)object2).deleteOnClose = true;
                            continue block12;
                        }
                        case CREATE_NEW: {
                            ((Flags)object2).createNew = true;
                            continue block12;
                        }
                        case CREATE: {
                            ((Flags)object2).create = true;
                            continue block12;
                        }
                        case TRUNCATE_EXISTING: {
                            ((Flags)object2).truncateExisting = true;
                            continue block12;
                        }
                        case APPEND: {
                            ((Flags)object2).append = true;
                            continue block12;
                        }
                        case WRITE: {
                            ((Flags)object2).write = true;
                            continue block12;
                        }
                        case READ: 
                    }
                    ((Flags)object2).read = true;
                    continue;
                }
                if (object == LinkOption.NOFOLLOW_LINKS && UnixConstants.O_NOFOLLOW != 0) {
                    ((Flags)object2).noFollowLinks = true;
                    continue;
                }
                if (object == null) {
                    throw new NullPointerException();
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" not supported");
                throw new UnsupportedOperationException(((StringBuilder)object2).toString());
            }
            return object2;
        }
    }

}

