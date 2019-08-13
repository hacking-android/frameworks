/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import android.system.ErrnoException;
import android.system.GaiException;
import android.system.Int32Ref;
import android.system.Int64Ref;
import android.system.StructAddrinfo;
import android.system.StructCapUserData;
import android.system.StructCapUserHeader;
import android.system.StructFlock;
import android.system.StructGroupReq;
import android.system.StructIfaddrs;
import android.system.StructLinger;
import android.system.StructPasswd;
import android.system.StructPollfd;
import android.system.StructRlimit;
import android.system.StructStat;
import android.system.StructStatVfs;
import android.system.StructTimeval;
import android.system.StructUcred;
import android.system.StructUtsname;
import java.io.FileDescriptor;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.NioUtils;
import libcore.io.Os;

public final class Linux
implements Os {
    Linux() {
    }

    private static void maybeUpdateBufferPosition(ByteBuffer byteBuffer, int n, int n2) {
        if (n2 > 0) {
            byteBuffer.position(n2 + n);
        }
    }

    private native int preadBytes(FileDescriptor var1, Object var2, int var3, int var4, long var5) throws ErrnoException, InterruptedIOException;

    private native int pwriteBytes(FileDescriptor var1, Object var2, int var3, int var4, long var5) throws ErrnoException, InterruptedIOException;

    private native int readBytes(FileDescriptor var1, Object var2, int var3, int var4) throws ErrnoException, InterruptedIOException;

    private native int recvfromBytes(FileDescriptor var1, Object var2, int var3, int var4, int var5, InetSocketAddress var6) throws ErrnoException, SocketException;

    private native int sendtoBytes(FileDescriptor var1, Object var2, int var3, int var4, int var5, InetAddress var6, int var7) throws ErrnoException, SocketException;

    private native int sendtoBytes(FileDescriptor var1, Object var2, int var3, int var4, int var5, SocketAddress var6) throws ErrnoException, SocketException;

    private native int umaskImpl(int var1);

    private native int writeBytes(FileDescriptor var1, Object var2, int var3, int var4) throws ErrnoException, InterruptedIOException;

    @Override
    public native FileDescriptor accept(FileDescriptor var1, SocketAddress var2) throws ErrnoException, SocketException;

    @Override
    public native boolean access(String var1, int var2) throws ErrnoException;

    @Override
    public native void android_fdsan_exchange_owner_tag(FileDescriptor var1, long var2, long var4);

    @Override
    public native long android_fdsan_get_owner_tag(FileDescriptor var1);

    @Override
    public native String android_fdsan_get_tag_type(long var1);

    @Override
    public native long android_fdsan_get_tag_value(long var1);

    @Override
    public native InetAddress[] android_getaddrinfo(String var1, StructAddrinfo var2, int var3) throws GaiException;

    @Override
    public native void bind(FileDescriptor var1, InetAddress var2, int var3) throws ErrnoException, SocketException;

    @Override
    public native void bind(FileDescriptor var1, SocketAddress var2) throws ErrnoException, SocketException;

    @Override
    public native StructCapUserData[] capget(StructCapUserHeader var1) throws ErrnoException;

    @Override
    public native void capset(StructCapUserHeader var1, StructCapUserData[] var2) throws ErrnoException;

    @Override
    public native void chmod(String var1, int var2) throws ErrnoException;

    @Override
    public native void chown(String var1, int var2, int var3) throws ErrnoException;

    @Override
    public native void close(FileDescriptor var1) throws ErrnoException;

    @Override
    public native void connect(FileDescriptor var1, InetAddress var2, int var3) throws ErrnoException, SocketException;

    @Override
    public native void connect(FileDescriptor var1, SocketAddress var2) throws ErrnoException, SocketException;

    @Override
    public native FileDescriptor dup(FileDescriptor var1) throws ErrnoException;

    @Override
    public native FileDescriptor dup2(FileDescriptor var1, int var2) throws ErrnoException;

    @Override
    public native String[] environ();

    @Override
    public native void execv(String var1, String[] var2) throws ErrnoException;

    @Override
    public native void execve(String var1, String[] var2, String[] var3) throws ErrnoException;

    @Override
    public native void fchmod(FileDescriptor var1, int var2) throws ErrnoException;

    @Override
    public native void fchown(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native int fcntlFlock(FileDescriptor var1, int var2, StructFlock var3) throws ErrnoException, InterruptedIOException;

    @Override
    public native int fcntlInt(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native int fcntlVoid(FileDescriptor var1, int var2) throws ErrnoException;

    @Override
    public native void fdatasync(FileDescriptor var1) throws ErrnoException;

    @Override
    public native StructStat fstat(FileDescriptor var1) throws ErrnoException;

    @Override
    public native StructStatVfs fstatvfs(FileDescriptor var1) throws ErrnoException;

    @Override
    public native void fsync(FileDescriptor var1) throws ErrnoException;

    @Override
    public native void ftruncate(FileDescriptor var1, long var2) throws ErrnoException;

    @Override
    public native String gai_strerror(int var1);

    @Override
    public native int getegid();

    @Override
    public native String getenv(String var1);

    @Override
    public native int geteuid();

    @Override
    public native int getgid();

    @Override
    public native StructIfaddrs[] getifaddrs() throws ErrnoException;

    @Override
    public native String getnameinfo(InetAddress var1, int var2) throws GaiException;

    @Override
    public native SocketAddress getpeername(FileDescriptor var1) throws ErrnoException;

    @Override
    public native int getpgid(int var1);

    @Override
    public native int getpid();

    @Override
    public native int getppid();

    @Override
    public native StructPasswd getpwnam(String var1) throws ErrnoException;

    @Override
    public native StructPasswd getpwuid(int var1) throws ErrnoException;

    @Override
    public native StructRlimit getrlimit(int var1) throws ErrnoException;

    @Override
    public native SocketAddress getsockname(FileDescriptor var1) throws ErrnoException;

    @Override
    public native int getsockoptByte(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native InetAddress getsockoptInAddr(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native int getsockoptInt(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native StructLinger getsockoptLinger(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native StructTimeval getsockoptTimeval(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native StructUcred getsockoptUcred(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    @Override
    public native int gettid();

    @Override
    public native int getuid();

    @Override
    public native byte[] getxattr(String var1, String var2) throws ErrnoException;

    @Override
    public native String if_indextoname(int var1);

    @Override
    public native int if_nametoindex(String var1);

    @Override
    public native InetAddress inet_pton(int var1, String var2);

    @Override
    public native int ioctlFlags(FileDescriptor var1, String var2) throws ErrnoException;

    @Override
    public native InetAddress ioctlInetAddress(FileDescriptor var1, int var2, String var3) throws ErrnoException;

    @Override
    public native int ioctlInt(FileDescriptor var1, int var2, Int32Ref var3) throws ErrnoException;

    @Override
    public native int ioctlMTU(FileDescriptor var1, String var2) throws ErrnoException;

    @Override
    public native boolean isatty(FileDescriptor var1);

    @Override
    public native void kill(int var1, int var2) throws ErrnoException;

    @Override
    public native void lchown(String var1, int var2, int var3) throws ErrnoException;

    @Override
    public native void link(String var1, String var2) throws ErrnoException;

    @Override
    public native void listen(FileDescriptor var1, int var2) throws ErrnoException;

    @Override
    public native String[] listxattr(String var1) throws ErrnoException;

    @Override
    public native long lseek(FileDescriptor var1, long var2, int var4) throws ErrnoException;

    @Override
    public native StructStat lstat(String var1) throws ErrnoException;

    @Override
    public native void mincore(long var1, long var3, byte[] var5) throws ErrnoException;

    @Override
    public native void mkdir(String var1, int var2) throws ErrnoException;

    @Override
    public native void mkfifo(String var1, int var2) throws ErrnoException;

    @Override
    public native void mlock(long var1, long var3) throws ErrnoException;

    @Override
    public native long mmap(long var1, long var3, int var5, int var6, FileDescriptor var7, long var8) throws ErrnoException;

    @Override
    public native void msync(long var1, long var3, int var5) throws ErrnoException;

    @Override
    public native void munlock(long var1, long var3) throws ErrnoException;

    @Override
    public native void munmap(long var1, long var3) throws ErrnoException;

    @Override
    public native FileDescriptor open(String var1, int var2, int var3) throws ErrnoException;

    @Override
    public native FileDescriptor[] pipe2(int var1) throws ErrnoException;

    @Override
    public native int poll(StructPollfd[] var1, int var2) throws ErrnoException;

    @Override
    public native void posix_fallocate(FileDescriptor var1, long var2, long var4) throws ErrnoException;

    @Override
    public native int prctl(int var1, long var2, long var4, long var6, long var8) throws ErrnoException;

    @Override
    public int pread(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        int n = byteBuffer.position();
        int n2 = byteBuffer.isDirect() ? this.preadBytes(fileDescriptor, byteBuffer, n, byteBuffer.remaining(), l) : this.preadBytes(fileDescriptor, NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + n, byteBuffer.remaining(), l);
        Linux.maybeUpdateBufferPosition(byteBuffer, n, n2);
        return n2;
    }

    @Override
    public int pread(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        return this.preadBytes(fileDescriptor, arrby, n, n2, l);
    }

    @Override
    public int pwrite(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        int n = byteBuffer.position();
        int n2 = byteBuffer.isDirect() ? this.pwriteBytes(fileDescriptor, byteBuffer, n, byteBuffer.remaining(), l) : this.pwriteBytes(fileDescriptor, NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + n, byteBuffer.remaining(), l);
        Linux.maybeUpdateBufferPosition(byteBuffer, n, n2);
        return n2;
    }

    @Override
    public int pwrite(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        return this.pwriteBytes(fileDescriptor, arrby, n, n2, l);
    }

    @Override
    public int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        int n = byteBuffer.position();
        int n2 = byteBuffer.isDirect() ? this.readBytes(fileDescriptor, byteBuffer, n, byteBuffer.remaining()) : this.readBytes(fileDescriptor, NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + n, byteBuffer.remaining());
        Linux.maybeUpdateBufferPosition(byteBuffer, n, n2);
        return n2;
    }

    @Override
    public int read(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        return this.readBytes(fileDescriptor, arrby, n, n2);
    }

    @Override
    public native String readlink(String var1) throws ErrnoException;

    @Override
    public native int readv(FileDescriptor var1, Object[] var2, int[] var3, int[] var4) throws ErrnoException, InterruptedIOException;

    @Override
    public native String realpath(String var1) throws ErrnoException;

    @Override
    public int recvfrom(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        int n2 = byteBuffer.position();
        n = byteBuffer.isDirect() ? this.recvfromBytes(fileDescriptor, byteBuffer, n2, byteBuffer.remaining(), n, inetSocketAddress) : this.recvfromBytes(fileDescriptor, NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + n2, byteBuffer.remaining(), n, inetSocketAddress);
        Linux.maybeUpdateBufferPosition(byteBuffer, n2, n);
        return n;
    }

    @Override
    public int recvfrom(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        return this.recvfromBytes(fileDescriptor, arrby, n, n2, n3, inetSocketAddress);
    }

    @Override
    public native void remove(String var1) throws ErrnoException;

    @Override
    public native void removexattr(String var1, String var2) throws ErrnoException;

    @Override
    public native void rename(String var1, String var2) throws ErrnoException;

    @Override
    public native long sendfile(FileDescriptor var1, FileDescriptor var2, Int64Ref var3, long var4) throws ErrnoException;

    @Override
    public int sendto(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetAddress inetAddress, int n2) throws ErrnoException, SocketException {
        int n3 = byteBuffer.position();
        n = byteBuffer.isDirect() ? this.sendtoBytes(fileDescriptor, byteBuffer, n3, byteBuffer.remaining(), n, inetAddress, n2) : this.sendtoBytes(fileDescriptor, NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + n3, byteBuffer.remaining(), n, inetAddress, n2);
        Linux.maybeUpdateBufferPosition(byteBuffer, n3, n);
        return n;
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetAddress inetAddress, int n4) throws ErrnoException, SocketException {
        return this.sendtoBytes(fileDescriptor, arrby, n, n2, n3, inetAddress, n4);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, SocketAddress socketAddress) throws ErrnoException, SocketException {
        return this.sendtoBytes(fileDescriptor, arrby, n, n2, n3, socketAddress);
    }

    @Override
    public native void setegid(int var1) throws ErrnoException;

    @Override
    public native void setenv(String var1, String var2, boolean var3) throws ErrnoException;

    @Override
    public native void seteuid(int var1) throws ErrnoException;

    @Override
    public native void setgid(int var1) throws ErrnoException;

    @Override
    public native void setpgid(int var1, int var2) throws ErrnoException;

    @Override
    public native void setregid(int var1, int var2) throws ErrnoException;

    @Override
    public native void setreuid(int var1, int var2) throws ErrnoException;

    @Override
    public native int setsid() throws ErrnoException;

    @Override
    public native void setsockoptByte(FileDescriptor var1, int var2, int var3, int var4) throws ErrnoException;

    @Override
    public native void setsockoptGroupReq(FileDescriptor var1, int var2, int var3, StructGroupReq var4) throws ErrnoException;

    @Override
    public native void setsockoptIfreq(FileDescriptor var1, int var2, int var3, String var4) throws ErrnoException;

    @Override
    public native void setsockoptInt(FileDescriptor var1, int var2, int var3, int var4) throws ErrnoException;

    @Override
    public native void setsockoptIpMreqn(FileDescriptor var1, int var2, int var3, int var4) throws ErrnoException;

    @Override
    public native void setsockoptLinger(FileDescriptor var1, int var2, int var3, StructLinger var4) throws ErrnoException;

    @Override
    public native void setsockoptTimeval(FileDescriptor var1, int var2, int var3, StructTimeval var4) throws ErrnoException;

    @Override
    public native void setuid(int var1) throws ErrnoException;

    @Override
    public native void setxattr(String var1, String var2, byte[] var3, int var4) throws ErrnoException;

    @Override
    public native void shutdown(FileDescriptor var1, int var2) throws ErrnoException;

    @Override
    public native FileDescriptor socket(int var1, int var2, int var3) throws ErrnoException;

    @Override
    public native void socketpair(int var1, int var2, int var3, FileDescriptor var4, FileDescriptor var5) throws ErrnoException;

    @Override
    public native long splice(FileDescriptor var1, Int64Ref var2, FileDescriptor var3, Int64Ref var4, long var5, int var7) throws ErrnoException;

    @Override
    public native StructStat stat(String var1) throws ErrnoException;

    @Override
    public native StructStatVfs statvfs(String var1) throws ErrnoException;

    @Override
    public native String strerror(int var1);

    @Override
    public native String strsignal(int var1);

    @Override
    public native void symlink(String var1, String var2) throws ErrnoException;

    @Override
    public native long sysconf(int var1);

    @Override
    public native void tcdrain(FileDescriptor var1) throws ErrnoException;

    @Override
    public native void tcsendbreak(FileDescriptor var1, int var2) throws ErrnoException;

    @Override
    public int umask(int n) {
        if ((n & 511) == n) {
            return this.umaskImpl(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid umask: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public native StructUtsname uname();

    @Override
    public native void unlink(String var1) throws ErrnoException;

    @Override
    public native void unsetenv(String var1) throws ErrnoException;

    @Override
    public native int waitpid(int var1, Int32Ref var2, int var3) throws ErrnoException;

    @Override
    public int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        int n = byteBuffer.position();
        int n2 = byteBuffer.isDirect() ? this.writeBytes(fileDescriptor, byteBuffer, n, byteBuffer.remaining()) : this.writeBytes(fileDescriptor, NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + n, byteBuffer.remaining());
        Linux.maybeUpdateBufferPosition(byteBuffer, n, n2);
        return n2;
    }

    @Override
    public int write(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        return this.writeBytes(fileDescriptor, arrby, n, n2);
    }

    @Override
    public native int writev(FileDescriptor var1, Object[] var2, int[] var3, int[] var4) throws ErrnoException, InterruptedIOException;
}

