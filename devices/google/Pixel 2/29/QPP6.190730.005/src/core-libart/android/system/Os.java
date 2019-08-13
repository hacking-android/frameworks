/*
 * Decompiled with CFR 0.145.
 */
package android.system;

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

public final class Os {
    private Os() {
    }

    public static FileDescriptor accept(FileDescriptor fileDescriptor, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        return Os.accept(fileDescriptor, (SocketAddress)inetSocketAddress);
    }

    public static FileDescriptor accept(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        return Libcore.os.accept(fileDescriptor, socketAddress);
    }

    public static boolean access(String string, int n) throws ErrnoException {
        return Libcore.os.access(string, n);
    }

    public static InetAddress[] android_getaddrinfo(String string, StructAddrinfo structAddrinfo, int n) throws GaiException {
        return Libcore.os.android_getaddrinfo(string, structAddrinfo, n);
    }

    public static void bind(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws ErrnoException, SocketException {
        Libcore.os.bind(fileDescriptor, inetAddress, n);
    }

    public static void bind(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        Libcore.os.bind(fileDescriptor, socketAddress);
    }

    public static StructCapUserData[] capget(StructCapUserHeader structCapUserHeader) throws ErrnoException {
        return Libcore.os.capget(structCapUserHeader);
    }

    public static void capset(StructCapUserHeader structCapUserHeader, StructCapUserData[] arrstructCapUserData) throws ErrnoException {
        Libcore.os.capset(structCapUserHeader, arrstructCapUserData);
    }

    public static void chmod(String string, int n) throws ErrnoException {
        Libcore.os.chmod(string, n);
    }

    public static void chown(String string, int n, int n2) throws ErrnoException {
        Libcore.os.chown(string, n, n2);
    }

    public static void close(FileDescriptor fileDescriptor) throws ErrnoException {
        Libcore.os.close(fileDescriptor);
    }

    public static void connect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws ErrnoException, SocketException {
        Libcore.os.connect(fileDescriptor, inetAddress, n);
    }

    public static void connect(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        Libcore.os.connect(fileDescriptor, socketAddress);
    }

    public static FileDescriptor dup(FileDescriptor fileDescriptor) throws ErrnoException {
        return Libcore.os.dup(fileDescriptor);
    }

    public static FileDescriptor dup2(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        return Libcore.os.dup2(fileDescriptor, n);
    }

    public static String[] environ() {
        return Libcore.os.environ();
    }

    public static void execv(String string, String[] arrstring) throws ErrnoException {
        Libcore.os.execv(string, arrstring);
    }

    public static void execve(String string, String[] arrstring, String[] arrstring2) throws ErrnoException {
        Libcore.os.execve(string, arrstring, arrstring2);
    }

    public static void fchmod(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        Libcore.os.fchmod(fileDescriptor, n);
    }

    public static void fchown(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        Libcore.os.fchown(fileDescriptor, n, n2);
    }

    public static int fcntlFlock(FileDescriptor fileDescriptor, int n, StructFlock structFlock) throws ErrnoException, InterruptedIOException {
        return Libcore.os.fcntlFlock(fileDescriptor, n, structFlock);
    }

    public static int fcntlInt(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.fcntlInt(fileDescriptor, n, n2);
    }

    public static int fcntlVoid(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        return Libcore.os.fcntlVoid(fileDescriptor, n);
    }

    public static void fdatasync(FileDescriptor fileDescriptor) throws ErrnoException {
        Libcore.os.fdatasync(fileDescriptor);
    }

    public static StructStat fstat(FileDescriptor fileDescriptor) throws ErrnoException {
        return Libcore.os.fstat(fileDescriptor);
    }

    public static StructStatVfs fstatvfs(FileDescriptor fileDescriptor) throws ErrnoException {
        return Libcore.os.fstatvfs(fileDescriptor);
    }

    public static void fsync(FileDescriptor fileDescriptor) throws ErrnoException {
        Libcore.os.fsync(fileDescriptor);
    }

    public static void ftruncate(FileDescriptor fileDescriptor, long l) throws ErrnoException {
        Libcore.os.ftruncate(fileDescriptor, l);
    }

    public static String gai_strerror(int n) {
        return Libcore.os.gai_strerror(n);
    }

    public static int getegid() {
        return Libcore.os.getegid();
    }

    public static String getenv(String string) {
        return Libcore.os.getenv(string);
    }

    public static int geteuid() {
        return Libcore.os.geteuid();
    }

    public static int getgid() {
        return Libcore.os.getgid();
    }

    public static StructIfaddrs[] getifaddrs() throws ErrnoException {
        return Libcore.os.getifaddrs();
    }

    public static String getnameinfo(InetAddress inetAddress, int n) throws GaiException {
        return Libcore.os.getnameinfo(inetAddress, n);
    }

    public static SocketAddress getpeername(FileDescriptor fileDescriptor) throws ErrnoException {
        return Libcore.os.getpeername(fileDescriptor);
    }

    public static int getpgid(int n) throws ErrnoException {
        return Libcore.os.getpgid(n);
    }

    public static int getpid() {
        return Libcore.os.getpid();
    }

    public static int getppid() {
        return Libcore.os.getppid();
    }

    public static StructPasswd getpwnam(String string) throws ErrnoException {
        return Libcore.os.getpwnam(string);
    }

    public static StructPasswd getpwuid(int n) throws ErrnoException {
        return Libcore.os.getpwuid(n);
    }

    public static StructRlimit getrlimit(int n) throws ErrnoException {
        return Libcore.os.getrlimit(n);
    }

    public static SocketAddress getsockname(FileDescriptor fileDescriptor) throws ErrnoException {
        return Libcore.os.getsockname(fileDescriptor);
    }

    public static int getsockoptByte(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.getsockoptByte(fileDescriptor, n, n2);
    }

    public static InetAddress getsockoptInAddr(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.getsockoptInAddr(fileDescriptor, n, n2);
    }

    public static int getsockoptInt(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.getsockoptInt(fileDescriptor, n, n2);
    }

    public static StructLinger getsockoptLinger(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.getsockoptLinger(fileDescriptor, n, n2);
    }

    public static StructTimeval getsockoptTimeval(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.getsockoptTimeval(fileDescriptor, n, n2);
    }

    public static StructUcred getsockoptUcred(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return Libcore.os.getsockoptUcred(fileDescriptor, n, n2);
    }

    public static int gettid() {
        return Libcore.os.gettid();
    }

    public static int getuid() {
        return Libcore.os.getuid();
    }

    public static byte[] getxattr(String string, String string2) throws ErrnoException {
        return Libcore.os.getxattr(string, string2);
    }

    public static String if_indextoname(int n) {
        return Libcore.os.if_indextoname(n);
    }

    public static int if_nametoindex(String string) {
        return Libcore.os.if_nametoindex(string);
    }

    public static InetAddress inet_pton(int n, String string) {
        return Libcore.os.inet_pton(n, string);
    }

    public static InetAddress ioctlInetAddress(FileDescriptor fileDescriptor, int n, String string) throws ErrnoException {
        return Libcore.os.ioctlInetAddress(fileDescriptor, n, string);
    }

    public static int ioctlInt(FileDescriptor fileDescriptor, int n, Int32Ref int32Ref) throws ErrnoException {
        return Libcore.os.ioctlInt(fileDescriptor, n, int32Ref);
    }

    public static boolean isatty(FileDescriptor fileDescriptor) {
        return Libcore.os.isatty(fileDescriptor);
    }

    public static void kill(int n, int n2) throws ErrnoException {
        Libcore.os.kill(n, n2);
    }

    public static void lchown(String string, int n, int n2) throws ErrnoException {
        Libcore.os.lchown(string, n, n2);
    }

    public static void link(String string, String string2) throws ErrnoException {
        Libcore.os.link(string, string2);
    }

    public static void listen(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        Libcore.os.listen(fileDescriptor, n);
    }

    public static String[] listxattr(String string) throws ErrnoException {
        return Libcore.os.listxattr(string);
    }

    public static long lseek(FileDescriptor fileDescriptor, long l, int n) throws ErrnoException {
        return Libcore.os.lseek(fileDescriptor, l, n);
    }

    public static StructStat lstat(String string) throws ErrnoException {
        return Libcore.os.lstat(string);
    }

    public static void mincore(long l, long l2, byte[] arrby) throws ErrnoException {
        Libcore.os.mincore(l, l2, arrby);
    }

    public static void mkdir(String string, int n) throws ErrnoException {
        Libcore.os.mkdir(string, n);
    }

    public static void mkfifo(String string, int n) throws ErrnoException {
        Libcore.os.mkfifo(string, n);
    }

    public static void mlock(long l, long l2) throws ErrnoException {
        Libcore.os.mlock(l, l2);
    }

    public static long mmap(long l, long l2, int n, int n2, FileDescriptor fileDescriptor, long l3) throws ErrnoException {
        return Libcore.os.mmap(l, l2, n, n2, fileDescriptor, l3);
    }

    public static void msync(long l, long l2, int n) throws ErrnoException {
        Libcore.os.msync(l, l2, n);
    }

    public static void munlock(long l, long l2) throws ErrnoException {
        Libcore.os.munlock(l, l2);
    }

    public static void munmap(long l, long l2) throws ErrnoException {
        Libcore.os.munmap(l, l2);
    }

    public static FileDescriptor open(String string, int n, int n2) throws ErrnoException {
        return Libcore.os.open(string, n, n2);
    }

    public static FileDescriptor[] pipe() throws ErrnoException {
        return Libcore.os.pipe2(0);
    }

    public static FileDescriptor[] pipe2(int n) throws ErrnoException {
        return Libcore.os.pipe2(n);
    }

    public static int poll(StructPollfd[] arrstructPollfd, int n) throws ErrnoException {
        return Libcore.os.poll(arrstructPollfd, n);
    }

    public static void posix_fallocate(FileDescriptor fileDescriptor, long l, long l2) throws ErrnoException {
        Libcore.os.posix_fallocate(fileDescriptor, l, l2);
    }

    public static int prctl(int n, long l, long l2, long l3, long l4) throws ErrnoException {
        return Libcore.os.prctl(n, l, l2, l3, l4);
    }

    public static int pread(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        return Libcore.os.pread(fileDescriptor, byteBuffer, l);
    }

    public static int pread(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        return Libcore.os.pread(fileDescriptor, arrby, n, n2, l);
    }

    public static int pwrite(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        return Libcore.os.pwrite(fileDescriptor, byteBuffer, l);
    }

    public static int pwrite(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        return Libcore.os.pwrite(fileDescriptor, arrby, n, n2, l);
    }

    public static int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        return Libcore.os.read(fileDescriptor, byteBuffer);
    }

    public static int read(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        return Libcore.os.read(fileDescriptor, arrby, n, n2);
    }

    public static String readlink(String string) throws ErrnoException {
        return Libcore.os.readlink(string);
    }

    public static int readv(FileDescriptor fileDescriptor, Object[] arrobject, int[] arrn, int[] arrn2) throws ErrnoException, InterruptedIOException {
        return Libcore.os.readv(fileDescriptor, arrobject, arrn, arrn2);
    }

    public static String realpath(String string) throws ErrnoException {
        return Libcore.os.realpath(string);
    }

    public static int recvfrom(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        return Libcore.os.recvfrom(fileDescriptor, byteBuffer, n, inetSocketAddress);
    }

    public static int recvfrom(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        return Libcore.os.recvfrom(fileDescriptor, arrby, n, n2, n3, inetSocketAddress);
    }

    public static void remove(String string) throws ErrnoException {
        Libcore.os.remove(string);
    }

    public static void removexattr(String string, String string2) throws ErrnoException {
        Libcore.os.removexattr(string, string2);
    }

    public static void rename(String string, String string2) throws ErrnoException {
        Libcore.os.rename(string, string2);
    }

    public static long sendfile(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, Int64Ref int64Ref, long l) throws ErrnoException {
        return Libcore.os.sendfile(fileDescriptor, fileDescriptor2, int64Ref, l);
    }

    public static int sendto(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetAddress inetAddress, int n2) throws ErrnoException, SocketException {
        return Libcore.os.sendto(fileDescriptor, byteBuffer, n, inetAddress, n2);
    }

    public static int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetAddress inetAddress, int n4) throws ErrnoException, SocketException {
        return Libcore.os.sendto(fileDescriptor, arrby, n, n2, n3, inetAddress, n4);
    }

    public static int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, SocketAddress socketAddress) throws ErrnoException, SocketException {
        return Libcore.os.sendto(fileDescriptor, arrby, n, n2, n3, socketAddress);
    }

    @Deprecated
    public static void setegid(int n) throws ErrnoException {
        Libcore.os.setegid(n);
    }

    public static void setenv(String string, String string2, boolean bl) throws ErrnoException {
        Libcore.os.setenv(string, string2, bl);
    }

    @Deprecated
    public static void seteuid(int n) throws ErrnoException {
        Libcore.os.seteuid(n);
    }

    @Deprecated
    public static void setgid(int n) throws ErrnoException {
        Libcore.os.setgid(n);
    }

    public static void setpgid(int n, int n2) throws ErrnoException {
        Libcore.os.setpgid(n, n2);
    }

    public static void setregid(int n, int n2) throws ErrnoException {
        Libcore.os.setregid(n, n2);
    }

    public static void setreuid(int n, int n2) throws ErrnoException {
        Libcore.os.setreuid(n, n2);
    }

    public static int setsid() throws ErrnoException {
        return Libcore.os.setsid();
    }

    public static void setsockoptByte(FileDescriptor fileDescriptor, int n, int n2, int n3) throws ErrnoException {
        Libcore.os.setsockoptByte(fileDescriptor, n, n2, n3);
    }

    public static void setsockoptGroupReq(FileDescriptor fileDescriptor, int n, int n2, StructGroupReq structGroupReq) throws ErrnoException {
        Libcore.os.setsockoptGroupReq(fileDescriptor, n, n2, structGroupReq);
    }

    @UnsupportedAppUsage
    public static void setsockoptIfreq(FileDescriptor fileDescriptor, int n, int n2, String string) throws ErrnoException {
        Libcore.os.setsockoptIfreq(fileDescriptor, n, n2, string);
    }

    public static void setsockoptInt(FileDescriptor fileDescriptor, int n, int n2, int n3) throws ErrnoException {
        Libcore.os.setsockoptInt(fileDescriptor, n, n2, n3);
    }

    public static void setsockoptIpMreqn(FileDescriptor fileDescriptor, int n, int n2, int n3) throws ErrnoException {
        Libcore.os.setsockoptIpMreqn(fileDescriptor, n, n2, n3);
    }

    public static void setsockoptLinger(FileDescriptor fileDescriptor, int n, int n2, StructLinger structLinger) throws ErrnoException {
        Libcore.os.setsockoptLinger(fileDescriptor, n, n2, structLinger);
    }

    public static void setsockoptTimeval(FileDescriptor fileDescriptor, int n, int n2, StructTimeval structTimeval) throws ErrnoException {
        Libcore.os.setsockoptTimeval(fileDescriptor, n, n2, structTimeval);
    }

    @Deprecated
    public static void setuid(int n) throws ErrnoException {
        Libcore.os.setuid(n);
    }

    public static void setxattr(String string, String string2, byte[] arrby, int n) throws ErrnoException {
        Libcore.os.setxattr(string, string2, arrby, n);
    }

    public static void shutdown(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        Libcore.os.shutdown(fileDescriptor, n);
    }

    public static FileDescriptor socket(int n, int n2, int n3) throws ErrnoException {
        return Libcore.os.socket(n, n2, n3);
    }

    public static void socketpair(int n, int n2, int n3, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) throws ErrnoException {
        Libcore.os.socketpair(n, n2, n3, fileDescriptor, fileDescriptor2);
    }

    public static long splice(FileDescriptor fileDescriptor, Int64Ref int64Ref, FileDescriptor fileDescriptor2, Int64Ref int64Ref2, long l, int n) throws ErrnoException {
        return Libcore.os.splice(fileDescriptor, int64Ref, fileDescriptor2, int64Ref2, l, n);
    }

    public static StructStat stat(String string) throws ErrnoException {
        return Libcore.os.stat(string);
    }

    public static StructStatVfs statvfs(String string) throws ErrnoException {
        return Libcore.os.statvfs(string);
    }

    public static String strerror(int n) {
        return Libcore.os.strerror(n);
    }

    public static String strsignal(int n) {
        return Libcore.os.strsignal(n);
    }

    public static void symlink(String string, String string2) throws ErrnoException {
        Libcore.os.symlink(string, string2);
    }

    public static long sysconf(int n) {
        return Libcore.os.sysconf(n);
    }

    public static void tcdrain(FileDescriptor fileDescriptor) throws ErrnoException {
        Libcore.os.tcdrain(fileDescriptor);
    }

    public static void tcsendbreak(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        Libcore.os.tcsendbreak(fileDescriptor, n);
    }

    public static int umask(int n) {
        return Libcore.os.umask(n);
    }

    public static StructUtsname uname() {
        return Libcore.os.uname();
    }

    public static void unlink(String string) throws ErrnoException {
        Libcore.os.unlink(string);
    }

    public static void unsetenv(String string) throws ErrnoException {
        Libcore.os.unsetenv(string);
    }

    public static int waitpid(int n, Int32Ref int32Ref, int n2) throws ErrnoException {
        return Libcore.os.waitpid(n, int32Ref, n2);
    }

    public static int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        return Libcore.os.write(fileDescriptor, byteBuffer);
    }

    public static int write(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        return Libcore.os.write(fileDescriptor, arrby, n, n2);
    }

    public static int writev(FileDescriptor fileDescriptor, Object[] arrobject, int[] arrn, int[] arrn2) throws ErrnoException, InterruptedIOException {
        return Libcore.os.writev(fileDescriptor, arrobject, arrn, arrn2);
    }
}

