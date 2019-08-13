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
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import libcore.io.Libcore;

public interface Os {
    public static boolean compareAndSetDefault(Os os, Os os2) {
        return Libcore.compareAndSetOs(os, os2);
    }

    public static Os getDefault() {
        return Libcore.getOs();
    }

    public FileDescriptor accept(FileDescriptor var1, SocketAddress var2) throws ErrnoException, SocketException;

    public boolean access(String var1, int var2) throws ErrnoException;

    public void android_fdsan_exchange_owner_tag(FileDescriptor var1, long var2, long var4);

    public long android_fdsan_get_owner_tag(FileDescriptor var1);

    public String android_fdsan_get_tag_type(long var1);

    public long android_fdsan_get_tag_value(long var1);

    public InetAddress[] android_getaddrinfo(String var1, StructAddrinfo var2, int var3) throws GaiException;

    public void bind(FileDescriptor var1, InetAddress var2, int var3) throws ErrnoException, SocketException;

    public void bind(FileDescriptor var1, SocketAddress var2) throws ErrnoException, SocketException;

    public StructCapUserData[] capget(StructCapUserHeader var1) throws ErrnoException;

    public void capset(StructCapUserHeader var1, StructCapUserData[] var2) throws ErrnoException;

    @UnsupportedAppUsage
    public void chmod(String var1, int var2) throws ErrnoException;

    public void chown(String var1, int var2, int var3) throws ErrnoException;

    @UnsupportedAppUsage
    public void close(FileDescriptor var1) throws ErrnoException;

    @UnsupportedAppUsage
    public void connect(FileDescriptor var1, InetAddress var2, int var3) throws ErrnoException, SocketException;

    public void connect(FileDescriptor var1, SocketAddress var2) throws ErrnoException, SocketException;

    public FileDescriptor dup(FileDescriptor var1) throws ErrnoException;

    public FileDescriptor dup2(FileDescriptor var1, int var2) throws ErrnoException;

    public String[] environ();

    public void execv(String var1, String[] var2) throws ErrnoException;

    public void execve(String var1, String[] var2, String[] var3) throws ErrnoException;

    public void fchmod(FileDescriptor var1, int var2) throws ErrnoException;

    public void fchown(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public int fcntlFlock(FileDescriptor var1, int var2, StructFlock var3) throws ErrnoException, InterruptedIOException;

    public int fcntlInt(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public int fcntlVoid(FileDescriptor var1, int var2) throws ErrnoException;

    public void fdatasync(FileDescriptor var1) throws ErrnoException;

    public StructStat fstat(FileDescriptor var1) throws ErrnoException;

    public StructStatVfs fstatvfs(FileDescriptor var1) throws ErrnoException;

    public void fsync(FileDescriptor var1) throws ErrnoException;

    public void ftruncate(FileDescriptor var1, long var2) throws ErrnoException;

    @UnsupportedAppUsage
    public String gai_strerror(int var1);

    public int getegid();

    public String getenv(String var1);

    public int geteuid();

    public int getgid();

    public StructIfaddrs[] getifaddrs() throws ErrnoException;

    public String getnameinfo(InetAddress var1, int var2) throws GaiException;

    public SocketAddress getpeername(FileDescriptor var1) throws ErrnoException;

    public int getpgid(int var1) throws ErrnoException;

    public int getpid();

    public int getppid();

    public StructPasswd getpwnam(String var1) throws ErrnoException;

    public StructPasswd getpwuid(int var1) throws ErrnoException;

    public StructRlimit getrlimit(int var1) throws ErrnoException;

    public SocketAddress getsockname(FileDescriptor var1) throws ErrnoException;

    public int getsockoptByte(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public InetAddress getsockoptInAddr(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public int getsockoptInt(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public StructLinger getsockoptLinger(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public StructTimeval getsockoptTimeval(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public StructUcred getsockoptUcred(FileDescriptor var1, int var2, int var3) throws ErrnoException;

    public int gettid();

    public int getuid();

    public byte[] getxattr(String var1, String var2) throws ErrnoException;

    public String if_indextoname(int var1);

    public int if_nametoindex(String var1);

    public InetAddress inet_pton(int var1, String var2);

    public int ioctlFlags(FileDescriptor var1, String var2) throws ErrnoException;

    public InetAddress ioctlInetAddress(FileDescriptor var1, int var2, String var3) throws ErrnoException;

    public int ioctlInt(FileDescriptor var1, int var2, Int32Ref var3) throws ErrnoException;

    public int ioctlMTU(FileDescriptor var1, String var2) throws ErrnoException;

    public boolean isatty(FileDescriptor var1);

    public void kill(int var1, int var2) throws ErrnoException;

    public void lchown(String var1, int var2, int var3) throws ErrnoException;

    public void link(String var1, String var2) throws ErrnoException;

    public void listen(FileDescriptor var1, int var2) throws ErrnoException;

    public String[] listxattr(String var1) throws ErrnoException;

    public long lseek(FileDescriptor var1, long var2, int var4) throws ErrnoException;

    public StructStat lstat(String var1) throws ErrnoException;

    public void mincore(long var1, long var3, byte[] var5) throws ErrnoException;

    public void mkdir(String var1, int var2) throws ErrnoException;

    public void mkfifo(String var1, int var2) throws ErrnoException;

    public void mlock(long var1, long var3) throws ErrnoException;

    @UnsupportedAppUsage
    public long mmap(long var1, long var3, int var5, int var6, FileDescriptor var7, long var8) throws ErrnoException;

    public void msync(long var1, long var3, int var5) throws ErrnoException;

    public void munlock(long var1, long var3) throws ErrnoException;

    @UnsupportedAppUsage
    public void munmap(long var1, long var3) throws ErrnoException;

    @UnsupportedAppUsage
    public FileDescriptor open(String var1, int var2, int var3) throws ErrnoException;

    public FileDescriptor[] pipe2(int var1) throws ErrnoException;

    public int poll(StructPollfd[] var1, int var2) throws ErrnoException;

    public void posix_fallocate(FileDescriptor var1, long var2, long var4) throws ErrnoException;

    public int prctl(int var1, long var2, long var4, long var6, long var8) throws ErrnoException;

    public int pread(FileDescriptor var1, ByteBuffer var2, long var3) throws ErrnoException, InterruptedIOException;

    public int pread(FileDescriptor var1, byte[] var2, int var3, int var4, long var5) throws ErrnoException, InterruptedIOException;

    public int pwrite(FileDescriptor var1, ByteBuffer var2, long var3) throws ErrnoException, InterruptedIOException;

    public int pwrite(FileDescriptor var1, byte[] var2, int var3, int var4, long var5) throws ErrnoException, InterruptedIOException;

    public int read(FileDescriptor var1, ByteBuffer var2) throws ErrnoException, InterruptedIOException;

    @UnsupportedAppUsage
    public int read(FileDescriptor var1, byte[] var2, int var3, int var4) throws ErrnoException, InterruptedIOException;

    public String readlink(String var1) throws ErrnoException;

    public int readv(FileDescriptor var1, Object[] var2, int[] var3, int[] var4) throws ErrnoException, InterruptedIOException;

    public String realpath(String var1) throws ErrnoException;

    public int recvfrom(FileDescriptor var1, ByteBuffer var2, int var3, InetSocketAddress var4) throws ErrnoException, SocketException;

    public int recvfrom(FileDescriptor var1, byte[] var2, int var3, int var4, int var5, InetSocketAddress var6) throws ErrnoException, SocketException;

    @UnsupportedAppUsage
    public void remove(String var1) throws ErrnoException;

    public void removexattr(String var1, String var2) throws ErrnoException;

    public void rename(String var1, String var2) throws ErrnoException;

    public long sendfile(FileDescriptor var1, FileDescriptor var2, Int64Ref var3, long var4) throws ErrnoException;

    public int sendto(FileDescriptor var1, ByteBuffer var2, int var3, InetAddress var4, int var5) throws ErrnoException, SocketException;

    public int sendto(FileDescriptor var1, byte[] var2, int var3, int var4, int var5, InetAddress var6, int var7) throws ErrnoException, SocketException;

    public int sendto(FileDescriptor var1, byte[] var2, int var3, int var4, int var5, SocketAddress var6) throws ErrnoException, SocketException;

    public void setegid(int var1) throws ErrnoException;

    @UnsupportedAppUsage
    public void setenv(String var1, String var2, boolean var3) throws ErrnoException;

    public void seteuid(int var1) throws ErrnoException;

    public void setgid(int var1) throws ErrnoException;

    public void setpgid(int var1, int var2) throws ErrnoException;

    public void setregid(int var1, int var2) throws ErrnoException;

    public void setreuid(int var1, int var2) throws ErrnoException;

    public int setsid() throws ErrnoException;

    public void setsockoptByte(FileDescriptor var1, int var2, int var3, int var4) throws ErrnoException;

    public void setsockoptGroupReq(FileDescriptor var1, int var2, int var3, StructGroupReq var4) throws ErrnoException;

    public void setsockoptIfreq(FileDescriptor var1, int var2, int var3, String var4) throws ErrnoException;

    public void setsockoptInt(FileDescriptor var1, int var2, int var3, int var4) throws ErrnoException;

    public void setsockoptIpMreqn(FileDescriptor var1, int var2, int var3, int var4) throws ErrnoException;

    public void setsockoptLinger(FileDescriptor var1, int var2, int var3, StructLinger var4) throws ErrnoException;

    @UnsupportedAppUsage
    public void setsockoptTimeval(FileDescriptor var1, int var2, int var3, StructTimeval var4) throws ErrnoException;

    public void setuid(int var1) throws ErrnoException;

    public void setxattr(String var1, String var2, byte[] var3, int var4) throws ErrnoException;

    public void shutdown(FileDescriptor var1, int var2) throws ErrnoException;

    public FileDescriptor socket(int var1, int var2, int var3) throws ErrnoException;

    public void socketpair(int var1, int var2, int var3, FileDescriptor var4, FileDescriptor var5) throws ErrnoException;

    public long splice(FileDescriptor var1, Int64Ref var2, FileDescriptor var3, Int64Ref var4, long var5, int var7) throws ErrnoException;

    @UnsupportedAppUsage
    public StructStat stat(String var1) throws ErrnoException;

    public StructStatVfs statvfs(String var1) throws ErrnoException;

    @UnsupportedAppUsage
    public String strerror(int var1);

    public String strsignal(int var1);

    public void symlink(String var1, String var2) throws ErrnoException;

    @UnsupportedAppUsage
    public long sysconf(int var1);

    public void tcdrain(FileDescriptor var1) throws ErrnoException;

    public void tcsendbreak(FileDescriptor var1, int var2) throws ErrnoException;

    public int umask(int var1);

    public StructUtsname uname();

    public void unlink(String var1) throws ErrnoException;

    public void unsetenv(String var1) throws ErrnoException;

    public int waitpid(int var1, Int32Ref var2, int var3) throws ErrnoException;

    public int write(FileDescriptor var1, ByteBuffer var2) throws ErrnoException, InterruptedIOException;

    public int write(FileDescriptor var1, byte[] var2, int var3, int var4) throws ErrnoException, InterruptedIOException;

    public int writev(FileDescriptor var1, Object[] var2, int[] var3, int[] var4) throws ErrnoException, InterruptedIOException;
}

