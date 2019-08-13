/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import android.system.ErrnoException;
import android.system.GaiException;
import android.system.Int64Ref;
import android.system.OsConstants;
import android.system.StructAddrinfo;
import android.system.StructLinger;
import android.system.StructPollfd;
import android.system.StructStat;
import android.system.StructStatVfs;
import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.system.BlockGuard;
import dalvik.system.SocketTagger;
import java.io.FileDescriptor;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import libcore.io.ForwardingOs;
import libcore.io.Libcore;
import libcore.io.Os;

public class BlockGuardOs
extends ForwardingOs {
    @UnsupportedAppUsage
    public BlockGuardOs(Os os) {
        super(os);
    }

    private static boolean isInetDomain(int n) {
        boolean bl = n == OsConstants.AF_INET || n == OsConstants.AF_INET6;
        return bl;
    }

    private static boolean isInetSocket(FileDescriptor fileDescriptor) throws ErrnoException {
        return BlockGuardOs.isInetDomain(Libcore.os.getsockoptInt(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_DOMAIN));
    }

    private static boolean isLingerSocket(FileDescriptor object) throws ErrnoException {
        boolean bl = ((StructLinger)(object = Libcore.os.getsockoptLinger((FileDescriptor)object, OsConstants.SOL_SOCKET, OsConstants.SO_LINGER))).isOn() && ((StructLinger)object).l_linger > 0;
        return bl;
    }

    private static boolean isUdpSocket(FileDescriptor fileDescriptor) throws ErrnoException {
        boolean bl = Libcore.os.getsockoptInt(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_PROTOCOL) == OsConstants.IPPROTO_UDP;
        return bl;
    }

    private FileDescriptor tagSocket(FileDescriptor fileDescriptor) throws ErrnoException {
        try {
            SocketTagger.get().tag(fileDescriptor);
            return fileDescriptor;
        }
        catch (SocketException socketException) {
            throw new ErrnoException("socket", OsConstants.EINVAL, socketException);
        }
    }

    @Override
    public FileDescriptor accept(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        BlockGuard.getThreadPolicy().onNetwork();
        fileDescriptor = super.accept(fileDescriptor, socketAddress);
        if (BlockGuardOs.isInetSocket(fileDescriptor)) {
            this.tagSocket(fileDescriptor);
        }
        return fileDescriptor;
    }

    @Override
    public boolean access(String string, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.access(string, n);
    }

    @Override
    public InetAddress[] android_getaddrinfo(String string, StructAddrinfo structAddrinfo, int n) throws GaiException {
        boolean bl = (structAddrinfo.ai_flags & OsConstants.AI_NUMERICHOST) != 0;
        if (!bl) {
            BlockGuard.getThreadPolicy().onNetwork();
        }
        return super.android_getaddrinfo(string, structAddrinfo, n);
    }

    @UnsupportedAppUsage
    @Override
    public void chmod(String string, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.chmod(string, n);
    }

    @UnsupportedAppUsage
    @Override
    public void chown(String string, int n, int n2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.chown(string, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public void close(FileDescriptor fileDescriptor) throws ErrnoException {
        try {
            if (fileDescriptor.isSocket$() && BlockGuardOs.isLingerSocket(fileDescriptor)) {
                BlockGuard.getThreadPolicy().onNetwork();
            }
        }
        catch (ErrnoException errnoException) {
            // empty catch block
        }
        super.close(fileDescriptor);
    }

    @Override
    public void connect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws ErrnoException, SocketException {
        boolean bl;
        boolean bl2 = false;
        try {
            bl = BlockGuardOs.isUdpSocket(fileDescriptor);
        }
        catch (ErrnoException errnoException) {
            bl = bl2;
        }
        if (!bl) {
            BlockGuard.getThreadPolicy().onNetwork();
        }
        super.connect(fileDescriptor, inetAddress, n);
    }

    @Override
    public void connect(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        boolean bl;
        boolean bl2 = false;
        try {
            bl = BlockGuardOs.isUdpSocket(fileDescriptor);
        }
        catch (ErrnoException errnoException) {
            bl = bl2;
        }
        if (!bl) {
            BlockGuard.getThreadPolicy().onNetwork();
        }
        super.connect(fileDescriptor, socketAddress);
    }

    @Override
    public void execv(String string, String[] arrstring) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.execv(string, arrstring);
    }

    @Override
    public void execve(String string, String[] arrstring, String[] arrstring2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.execve(string, arrstring, arrstring2);
    }

    @UnsupportedAppUsage
    @Override
    public void fchmod(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        super.fchmod(fileDescriptor, n);
    }

    @UnsupportedAppUsage
    @Override
    public void fchown(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        super.fchown(fileDescriptor, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public void fdatasync(FileDescriptor fileDescriptor) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        super.fdatasync(fileDescriptor);
    }

    @UnsupportedAppUsage
    @Override
    public StructStat fstat(FileDescriptor fileDescriptor) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.fstat(fileDescriptor);
    }

    @UnsupportedAppUsage
    @Override
    public StructStatVfs fstatvfs(FileDescriptor fileDescriptor) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.fstatvfs(fileDescriptor);
    }

    @Override
    public void fsync(FileDescriptor fileDescriptor) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        super.fsync(fileDescriptor);
    }

    @Override
    public void ftruncate(FileDescriptor fileDescriptor, long l) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        super.ftruncate(fileDescriptor, l);
    }

    @Override
    public byte[] getxattr(String string, String string2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.getxattr(string, string2);
    }

    @UnsupportedAppUsage
    @Override
    public void lchown(String string, int n, int n2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.lchown(string, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public void link(String string, String string2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        BlockGuard.getVmPolicy().onPathAccess(string2);
        super.link(string, string2);
    }

    @UnsupportedAppUsage
    @Override
    public long lseek(FileDescriptor fileDescriptor, long l, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.lseek(fileDescriptor, l, n);
    }

    @UnsupportedAppUsage
    @Override
    public StructStat lstat(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.lstat(string);
    }

    @UnsupportedAppUsage
    @Override
    public void mkdir(String string, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.mkdir(string, n);
    }

    @UnsupportedAppUsage
    @Override
    public void mkfifo(String string, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.mkfifo(string, n);
    }

    @Override
    public void msync(long l, long l2, int n) throws ErrnoException {
        if ((OsConstants.MS_SYNC & n) != 0) {
            BlockGuard.getThreadPolicy().onWriteToDisk();
        }
        super.msync(l, l2, n);
    }

    @UnsupportedAppUsage
    @Override
    public FileDescriptor open(String string, int n, int n2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        if ((OsConstants.O_ACCMODE & n) != OsConstants.O_RDONLY) {
            BlockGuard.getThreadPolicy().onWriteToDisk();
        }
        return super.open(string, n, n2);
    }

    @Override
    public int poll(StructPollfd[] arrstructPollfd, int n) throws ErrnoException {
        if (n != 0) {
            BlockGuard.getThreadPolicy().onNetwork();
        }
        return super.poll(arrstructPollfd, n);
    }

    @UnsupportedAppUsage
    @Override
    public void posix_fallocate(FileDescriptor fileDescriptor, long l, long l2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        super.posix_fallocate(fileDescriptor, l, l2);
    }

    @UnsupportedAppUsage
    @Override
    public int pread(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.pread(fileDescriptor, byteBuffer, l);
    }

    @UnsupportedAppUsage
    @Override
    public int pread(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.pread(fileDescriptor, arrby, n, n2, l);
    }

    @UnsupportedAppUsage
    @Override
    public int pwrite(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return super.pwrite(fileDescriptor, byteBuffer, l);
    }

    @UnsupportedAppUsage
    @Override
    public int pwrite(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return super.pwrite(fileDescriptor, arrby, n, n2, l);
    }

    @UnsupportedAppUsage
    @Override
    public int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.read(fileDescriptor, byteBuffer);
    }

    @UnsupportedAppUsage
    @Override
    public int read(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.read(fileDescriptor, arrby, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public String readlink(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.readlink(string);
    }

    @UnsupportedAppUsage
    @Override
    public int readv(FileDescriptor fileDescriptor, Object[] arrobject, int[] arrn, int[] arrn2) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.readv(fileDescriptor, arrobject, arrn, arrn2);
    }

    @UnsupportedAppUsage
    @Override
    public String realpath(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.realpath(string);
    }

    @Override
    public int recvfrom(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        BlockGuard.getThreadPolicy().onNetwork();
        return super.recvfrom(fileDescriptor, byteBuffer, n, inetSocketAddress);
    }

    @Override
    public int recvfrom(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        BlockGuard.getThreadPolicy().onNetwork();
        return super.recvfrom(fileDescriptor, arrby, n, n2, n3, inetSocketAddress);
    }

    @UnsupportedAppUsage
    @Override
    public void remove(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.remove(string);
    }

    @Override
    public void removexattr(String string, String string2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.removexattr(string, string2);
    }

    @UnsupportedAppUsage
    @Override
    public void rename(String string, String string2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        BlockGuard.getVmPolicy().onPathAccess(string2);
        super.rename(string, string2);
    }

    @Override
    public long sendfile(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, Int64Ref int64Ref, long l) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return super.sendfile(fileDescriptor, fileDescriptor2, int64Ref, l);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetAddress inetAddress, int n2) throws ErrnoException, SocketException {
        BlockGuard.getThreadPolicy().onNetwork();
        return super.sendto(fileDescriptor, byteBuffer, n, inetAddress, n2);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetAddress inetAddress, int n4) throws ErrnoException, SocketException {
        if (inetAddress != null) {
            BlockGuard.getThreadPolicy().onNetwork();
        }
        return super.sendto(fileDescriptor, arrby, n, n2, n3, inetAddress, n4);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, SocketAddress socketAddress) throws ErrnoException, SocketException {
        BlockGuard.getThreadPolicy().onNetwork();
        return super.sendto(fileDescriptor, arrby, n, n2, n3, socketAddress);
    }

    @Override
    public void setxattr(String string, String string2, byte[] arrby, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.setxattr(string, string2, arrby, n);
    }

    @Override
    public FileDescriptor socket(int n, int n2, int n3) throws ErrnoException {
        FileDescriptor fileDescriptor = super.socket(n, n2, n3);
        if (BlockGuardOs.isInetDomain(n)) {
            this.tagSocket(fileDescriptor);
        }
        return fileDescriptor;
    }

    @Override
    public void socketpair(int n, int n2, int n3, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) throws ErrnoException {
        super.socketpair(n, n2, n3, fileDescriptor, fileDescriptor2);
        if (BlockGuardOs.isInetDomain(n)) {
            this.tagSocket(fileDescriptor);
            this.tagSocket(fileDescriptor2);
        }
    }

    @Override
    public long splice(FileDescriptor fileDescriptor, Int64Ref int64Ref, FileDescriptor fileDescriptor2, Int64Ref int64Ref2, long l, int n) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return super.splice(fileDescriptor, int64Ref, fileDescriptor2, int64Ref2, l, n);
    }

    @UnsupportedAppUsage
    @Override
    public StructStat stat(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.stat(string);
    }

    @UnsupportedAppUsage
    @Override
    public StructStatVfs statvfs(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        return super.statvfs(string);
    }

    @UnsupportedAppUsage
    @Override
    public void symlink(String string, String string2) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        BlockGuard.getVmPolicy().onPathAccess(string2);
        super.symlink(string, string2);
    }

    @Override
    public void unlink(String string) throws ErrnoException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        BlockGuard.getVmPolicy().onPathAccess(string);
        super.unlink(string);
    }

    @UnsupportedAppUsage
    @Override
    public int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return super.write(fileDescriptor, byteBuffer);
    }

    @UnsupportedAppUsage
    @Override
    public int write(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return super.write(fileDescriptor, arrby, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public int writev(FileDescriptor fileDescriptor, Object[] arrobject, int[] arrn, int[] arrn2) throws ErrnoException, InterruptedIOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return super.writev(fileDescriptor, arrobject, arrn, arrn2);
    }
}

