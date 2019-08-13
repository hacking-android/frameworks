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
import java.util.Objects;
import libcore.io.Os;

public class ForwardingOs
implements Os {
    @UnsupportedAppUsage
    private final Os os;

    @UnsupportedAppUsage
    protected ForwardingOs(Os os) {
        this.os = Objects.requireNonNull(os);
    }

    @Override
    public FileDescriptor accept(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        return this.os.accept(fileDescriptor, socketAddress);
    }

    @UnsupportedAppUsage
    @Override
    public boolean access(String string, int n) throws ErrnoException {
        return this.os.access(string, n);
    }

    @Override
    public void android_fdsan_exchange_owner_tag(FileDescriptor fileDescriptor, long l, long l2) {
        this.os.android_fdsan_exchange_owner_tag(fileDescriptor, l, l2);
    }

    @Override
    public long android_fdsan_get_owner_tag(FileDescriptor fileDescriptor) {
        return this.os.android_fdsan_get_owner_tag(fileDescriptor);
    }

    @Override
    public String android_fdsan_get_tag_type(long l) {
        return this.os.android_fdsan_get_tag_type(l);
    }

    @Override
    public long android_fdsan_get_tag_value(long l) {
        return this.os.android_fdsan_get_tag_value(l);
    }

    @Override
    public InetAddress[] android_getaddrinfo(String string, StructAddrinfo structAddrinfo, int n) throws GaiException {
        return this.os.android_getaddrinfo(string, structAddrinfo, n);
    }

    @Override
    public void bind(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws ErrnoException, SocketException {
        this.os.bind(fileDescriptor, inetAddress, n);
    }

    @Override
    public void bind(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        this.os.bind(fileDescriptor, socketAddress);
    }

    @Override
    public StructCapUserData[] capget(StructCapUserHeader structCapUserHeader) throws ErrnoException {
        return this.os.capget(structCapUserHeader);
    }

    @Override
    public void capset(StructCapUserHeader structCapUserHeader, StructCapUserData[] arrstructCapUserData) throws ErrnoException {
        this.os.capset(structCapUserHeader, arrstructCapUserData);
    }

    @UnsupportedAppUsage
    @Override
    public void chmod(String string, int n) throws ErrnoException {
        this.os.chmod(string, n);
    }

    @UnsupportedAppUsage
    @Override
    public void chown(String string, int n, int n2) throws ErrnoException {
        this.os.chown(string, n, n2);
    }

    @Override
    public void close(FileDescriptor fileDescriptor) throws ErrnoException {
        this.os.close(fileDescriptor);
    }

    @Override
    public void connect(FileDescriptor fileDescriptor, InetAddress inetAddress, int n) throws ErrnoException, SocketException {
        this.os.connect(fileDescriptor, inetAddress, n);
    }

    @Override
    public void connect(FileDescriptor fileDescriptor, SocketAddress socketAddress) throws ErrnoException, SocketException {
        this.os.connect(fileDescriptor, socketAddress);
    }

    protected final Os delegate() {
        return this.os;
    }

    @Override
    public FileDescriptor dup(FileDescriptor fileDescriptor) throws ErrnoException {
        return this.os.dup(fileDescriptor);
    }

    @Override
    public FileDescriptor dup2(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        return this.os.dup2(fileDescriptor, n);
    }

    @Override
    public String[] environ() {
        return this.os.environ();
    }

    @Override
    public void execv(String string, String[] arrstring) throws ErrnoException {
        this.os.execv(string, arrstring);
    }

    @Override
    public void execve(String string, String[] arrstring, String[] arrstring2) throws ErrnoException {
        this.os.execve(string, arrstring, arrstring2);
    }

    @Override
    public void fchmod(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        this.os.fchmod(fileDescriptor, n);
    }

    @Override
    public void fchown(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        this.os.fchown(fileDescriptor, n, n2);
    }

    @Override
    public int fcntlFlock(FileDescriptor fileDescriptor, int n, StructFlock structFlock) throws ErrnoException, InterruptedIOException {
        return this.os.fcntlFlock(fileDescriptor, n, structFlock);
    }

    @Override
    public int fcntlInt(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.fcntlInt(fileDescriptor, n, n2);
    }

    @Override
    public int fcntlVoid(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        return this.os.fcntlVoid(fileDescriptor, n);
    }

    @Override
    public void fdatasync(FileDescriptor fileDescriptor) throws ErrnoException {
        this.os.fdatasync(fileDescriptor);
    }

    @Override
    public StructStat fstat(FileDescriptor fileDescriptor) throws ErrnoException {
        return this.os.fstat(fileDescriptor);
    }

    @Override
    public StructStatVfs fstatvfs(FileDescriptor fileDescriptor) throws ErrnoException {
        return this.os.fstatvfs(fileDescriptor);
    }

    @Override
    public void fsync(FileDescriptor fileDescriptor) throws ErrnoException {
        this.os.fsync(fileDescriptor);
    }

    @Override
    public void ftruncate(FileDescriptor fileDescriptor, long l) throws ErrnoException {
        this.os.ftruncate(fileDescriptor, l);
    }

    @Override
    public String gai_strerror(int n) {
        return this.os.gai_strerror(n);
    }

    @Override
    public int getegid() {
        return this.os.getegid();
    }

    @UnsupportedAppUsage
    @Override
    public String getenv(String string) {
        return this.os.getenv(string);
    }

    @Override
    public int geteuid() {
        return this.os.geteuid();
    }

    @Override
    public int getgid() {
        return this.os.getgid();
    }

    @Override
    public StructIfaddrs[] getifaddrs() throws ErrnoException {
        return this.os.getifaddrs();
    }

    @Override
    public String getnameinfo(InetAddress inetAddress, int n) throws GaiException {
        return this.os.getnameinfo(inetAddress, n);
    }

    @Override
    public SocketAddress getpeername(FileDescriptor fileDescriptor) throws ErrnoException {
        return this.os.getpeername(fileDescriptor);
    }

    @Override
    public int getpgid(int n) throws ErrnoException {
        return this.os.getpgid(n);
    }

    @Override
    public int getpid() {
        return this.os.getpid();
    }

    @Override
    public int getppid() {
        return this.os.getppid();
    }

    @Override
    public StructPasswd getpwnam(String string) throws ErrnoException {
        return this.os.getpwnam(string);
    }

    @Override
    public StructPasswd getpwuid(int n) throws ErrnoException {
        return this.os.getpwuid(n);
    }

    @Override
    public StructRlimit getrlimit(int n) throws ErrnoException {
        return this.os.getrlimit(n);
    }

    @Override
    public SocketAddress getsockname(FileDescriptor fileDescriptor) throws ErrnoException {
        return this.os.getsockname(fileDescriptor);
    }

    @Override
    public int getsockoptByte(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.getsockoptByte(fileDescriptor, n, n2);
    }

    @Override
    public InetAddress getsockoptInAddr(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.getsockoptInAddr(fileDescriptor, n, n2);
    }

    @Override
    public int getsockoptInt(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.getsockoptInt(fileDescriptor, n, n2);
    }

    @Override
    public StructLinger getsockoptLinger(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.getsockoptLinger(fileDescriptor, n, n2);
    }

    @Override
    public StructTimeval getsockoptTimeval(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.getsockoptTimeval(fileDescriptor, n, n2);
    }

    @Override
    public StructUcred getsockoptUcred(FileDescriptor fileDescriptor, int n, int n2) throws ErrnoException {
        return this.os.getsockoptUcred(fileDescriptor, n, n2);
    }

    @Override
    public int gettid() {
        return this.os.gettid();
    }

    @Override
    public int getuid() {
        return this.os.getuid();
    }

    @Override
    public byte[] getxattr(String string, String string2) throws ErrnoException {
        return this.os.getxattr(string, string2);
    }

    @Override
    public String if_indextoname(int n) {
        return this.os.if_indextoname(n);
    }

    @Override
    public int if_nametoindex(String string) {
        return this.os.if_nametoindex(string);
    }

    @Override
    public InetAddress inet_pton(int n, String string) {
        return this.os.inet_pton(n, string);
    }

    @Override
    public int ioctlFlags(FileDescriptor fileDescriptor, String string) throws ErrnoException {
        return this.os.ioctlFlags(fileDescriptor, string);
    }

    @Override
    public InetAddress ioctlInetAddress(FileDescriptor fileDescriptor, int n, String string) throws ErrnoException {
        return this.os.ioctlInetAddress(fileDescriptor, n, string);
    }

    @Override
    public int ioctlInt(FileDescriptor fileDescriptor, int n, Int32Ref int32Ref) throws ErrnoException {
        return this.os.ioctlInt(fileDescriptor, n, int32Ref);
    }

    @Override
    public int ioctlMTU(FileDescriptor fileDescriptor, String string) throws ErrnoException {
        return this.os.ioctlMTU(fileDescriptor, string);
    }

    @Override
    public boolean isatty(FileDescriptor fileDescriptor) {
        return this.os.isatty(fileDescriptor);
    }

    @Override
    public void kill(int n, int n2) throws ErrnoException {
        this.os.kill(n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public void lchown(String string, int n, int n2) throws ErrnoException {
        this.os.lchown(string, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public void link(String string, String string2) throws ErrnoException {
        this.os.link(string, string2);
    }

    @Override
    public void listen(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        this.os.listen(fileDescriptor, n);
    }

    @Override
    public String[] listxattr(String string) throws ErrnoException {
        return this.os.listxattr(string);
    }

    @Override
    public long lseek(FileDescriptor fileDescriptor, long l, int n) throws ErrnoException {
        return this.os.lseek(fileDescriptor, l, n);
    }

    @UnsupportedAppUsage
    @Override
    public StructStat lstat(String string) throws ErrnoException {
        return this.os.lstat(string);
    }

    @Override
    public void mincore(long l, long l2, byte[] arrby) throws ErrnoException {
        this.os.mincore(l, l2, arrby);
    }

    @UnsupportedAppUsage
    @Override
    public void mkdir(String string, int n) throws ErrnoException {
        this.os.mkdir(string, n);
    }

    @UnsupportedAppUsage
    @Override
    public void mkfifo(String string, int n) throws ErrnoException {
        this.os.mkfifo(string, n);
    }

    @Override
    public void mlock(long l, long l2) throws ErrnoException {
        this.os.mlock(l, l2);
    }

    @Override
    public long mmap(long l, long l2, int n, int n2, FileDescriptor fileDescriptor, long l3) throws ErrnoException {
        return this.os.mmap(l, l2, n, n2, fileDescriptor, l3);
    }

    @Override
    public void msync(long l, long l2, int n) throws ErrnoException {
        this.os.msync(l, l2, n);
    }

    @Override
    public void munlock(long l, long l2) throws ErrnoException {
        this.os.munlock(l, l2);
    }

    @Override
    public void munmap(long l, long l2) throws ErrnoException {
        this.os.munmap(l, l2);
    }

    @UnsupportedAppUsage
    @Override
    public FileDescriptor open(String string, int n, int n2) throws ErrnoException {
        return this.os.open(string, n, n2);
    }

    @Override
    public FileDescriptor[] pipe2(int n) throws ErrnoException {
        return this.os.pipe2(n);
    }

    @Override
    public int poll(StructPollfd[] arrstructPollfd, int n) throws ErrnoException {
        return this.os.poll(arrstructPollfd, n);
    }

    @Override
    public void posix_fallocate(FileDescriptor fileDescriptor, long l, long l2) throws ErrnoException {
        this.os.posix_fallocate(fileDescriptor, l, l2);
    }

    @Override
    public int prctl(int n, long l, long l2, long l3, long l4) throws ErrnoException {
        return this.os.prctl(n, l, l2, l3, l4);
    }

    @Override
    public int pread(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        return this.os.pread(fileDescriptor, byteBuffer, l);
    }

    @Override
    public int pread(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        return this.os.pread(fileDescriptor, arrby, n, n2, l);
    }

    @Override
    public int pwrite(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, long l) throws ErrnoException, InterruptedIOException {
        return this.os.pwrite(fileDescriptor, byteBuffer, l);
    }

    @Override
    public int pwrite(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, long l) throws ErrnoException, InterruptedIOException {
        return this.os.pwrite(fileDescriptor, arrby, n, n2, l);
    }

    @Override
    public int read(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        return this.os.read(fileDescriptor, byteBuffer);
    }

    @Override
    public int read(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        return this.os.read(fileDescriptor, arrby, n, n2);
    }

    @UnsupportedAppUsage
    @Override
    public String readlink(String string) throws ErrnoException {
        return this.os.readlink(string);
    }

    @Override
    public int readv(FileDescriptor fileDescriptor, Object[] arrobject, int[] arrn, int[] arrn2) throws ErrnoException, InterruptedIOException {
        return this.os.readv(fileDescriptor, arrobject, arrn, arrn2);
    }

    @Override
    public String realpath(String string) throws ErrnoException {
        return this.os.realpath(string);
    }

    @Override
    public int recvfrom(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        return this.os.recvfrom(fileDescriptor, byteBuffer, n, inetSocketAddress);
    }

    @Override
    public int recvfrom(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetSocketAddress inetSocketAddress) throws ErrnoException, SocketException {
        return this.os.recvfrom(fileDescriptor, arrby, n, n2, n3, inetSocketAddress);
    }

    @UnsupportedAppUsage
    @Override
    public void remove(String string) throws ErrnoException {
        this.os.remove(string);
    }

    @UnsupportedAppUsage
    @Override
    public void removexattr(String string, String string2) throws ErrnoException {
        this.os.removexattr(string, string2);
    }

    @UnsupportedAppUsage
    @Override
    public void rename(String string, String string2) throws ErrnoException {
        this.os.rename(string, string2);
    }

    @Override
    public long sendfile(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, Int64Ref int64Ref, long l) throws ErrnoException {
        return this.os.sendfile(fileDescriptor, fileDescriptor2, int64Ref, l);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, ByteBuffer byteBuffer, int n, InetAddress inetAddress, int n2) throws ErrnoException, SocketException {
        return this.os.sendto(fileDescriptor, byteBuffer, n, inetAddress, n2);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, InetAddress inetAddress, int n4) throws ErrnoException, SocketException {
        return this.os.sendto(fileDescriptor, arrby, n, n2, n3, inetAddress, n4);
    }

    @Override
    public int sendto(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2, int n3, SocketAddress socketAddress) throws ErrnoException, SocketException {
        return this.os.sendto(fileDescriptor, arrby, n, n2, n3, socketAddress);
    }

    @Override
    public void setegid(int n) throws ErrnoException {
        this.os.setegid(n);
    }

    @UnsupportedAppUsage
    @Override
    public void setenv(String string, String string2, boolean bl) throws ErrnoException {
        this.os.setenv(string, string2, bl);
    }

    @Override
    public void seteuid(int n) throws ErrnoException {
        this.os.seteuid(n);
    }

    @Override
    public void setgid(int n) throws ErrnoException {
        this.os.setgid(n);
    }

    @Override
    public void setpgid(int n, int n2) throws ErrnoException {
        this.os.setpgid(n, n2);
    }

    @Override
    public void setregid(int n, int n2) throws ErrnoException {
        this.os.setregid(n, n2);
    }

    @Override
    public void setreuid(int n, int n2) throws ErrnoException {
        this.os.setreuid(n, n2);
    }

    @Override
    public int setsid() throws ErrnoException {
        return this.os.setsid();
    }

    @Override
    public void setsockoptByte(FileDescriptor fileDescriptor, int n, int n2, int n3) throws ErrnoException {
        this.os.setsockoptByte(fileDescriptor, n, n2, n3);
    }

    @Override
    public void setsockoptGroupReq(FileDescriptor fileDescriptor, int n, int n2, StructGroupReq structGroupReq) throws ErrnoException {
        this.os.setsockoptGroupReq(fileDescriptor, n, n2, structGroupReq);
    }

    @Override
    public void setsockoptIfreq(FileDescriptor fileDescriptor, int n, int n2, String string) throws ErrnoException {
        this.os.setsockoptIfreq(fileDescriptor, n, n2, string);
    }

    @Override
    public void setsockoptInt(FileDescriptor fileDescriptor, int n, int n2, int n3) throws ErrnoException {
        this.os.setsockoptInt(fileDescriptor, n, n2, n3);
    }

    @Override
    public void setsockoptIpMreqn(FileDescriptor fileDescriptor, int n, int n2, int n3) throws ErrnoException {
        this.os.setsockoptIpMreqn(fileDescriptor, n, n2, n3);
    }

    @Override
    public void setsockoptLinger(FileDescriptor fileDescriptor, int n, int n2, StructLinger structLinger) throws ErrnoException {
        this.os.setsockoptLinger(fileDescriptor, n, n2, structLinger);
    }

    @UnsupportedAppUsage
    @Override
    public void setsockoptTimeval(FileDescriptor fileDescriptor, int n, int n2, StructTimeval structTimeval) throws ErrnoException {
        this.os.setsockoptTimeval(fileDescriptor, n, n2, structTimeval);
    }

    @Override
    public void setuid(int n) throws ErrnoException {
        this.os.setuid(n);
    }

    @UnsupportedAppUsage
    @Override
    public void setxattr(String string, String string2, byte[] arrby, int n) throws ErrnoException {
        this.os.setxattr(string, string2, arrby, n);
    }

    @Override
    public void shutdown(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        this.os.shutdown(fileDescriptor, n);
    }

    @Override
    public FileDescriptor socket(int n, int n2, int n3) throws ErrnoException {
        return this.os.socket(n, n2, n3);
    }

    @Override
    public void socketpair(int n, int n2, int n3, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) throws ErrnoException {
        this.os.socketpair(n, n2, n3, fileDescriptor, fileDescriptor2);
    }

    @Override
    public long splice(FileDescriptor fileDescriptor, Int64Ref int64Ref, FileDescriptor fileDescriptor2, Int64Ref int64Ref2, long l, int n) throws ErrnoException {
        return this.os.splice(fileDescriptor, int64Ref, fileDescriptor2, int64Ref2, l, n);
    }

    @UnsupportedAppUsage
    @Override
    public StructStat stat(String string) throws ErrnoException {
        return this.os.stat(string);
    }

    @UnsupportedAppUsage
    @Override
    public StructStatVfs statvfs(String string) throws ErrnoException {
        return this.os.statvfs(string);
    }

    @Override
    public String strerror(int n) {
        return this.os.strerror(n);
    }

    @Override
    public String strsignal(int n) {
        return this.os.strsignal(n);
    }

    @UnsupportedAppUsage
    @Override
    public void symlink(String string, String string2) throws ErrnoException {
        this.os.symlink(string, string2);
    }

    @UnsupportedAppUsage
    @Override
    public long sysconf(int n) {
        return this.os.sysconf(n);
    }

    @Override
    public void tcdrain(FileDescriptor fileDescriptor) throws ErrnoException {
        this.os.tcdrain(fileDescriptor);
    }

    @Override
    public void tcsendbreak(FileDescriptor fileDescriptor, int n) throws ErrnoException {
        this.os.tcsendbreak(fileDescriptor, n);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ForwardingOs{os=");
        stringBuilder.append(this.os);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public int umask(int n) {
        return this.os.umask(n);
    }

    @Override
    public StructUtsname uname() {
        return this.os.uname();
    }

    @UnsupportedAppUsage
    @Override
    public void unlink(String string) throws ErrnoException {
        this.os.unlink(string);
    }

    @Override
    public void unsetenv(String string) throws ErrnoException {
        this.os.unsetenv(string);
    }

    @Override
    public int waitpid(int n, Int32Ref int32Ref, int n2) throws ErrnoException {
        return this.os.waitpid(n, int32Ref, n2);
    }

    @Override
    public int write(FileDescriptor fileDescriptor, ByteBuffer byteBuffer) throws ErrnoException, InterruptedIOException {
        return this.os.write(fileDescriptor, byteBuffer);
    }

    @Override
    public int write(FileDescriptor fileDescriptor, byte[] arrby, int n, int n2) throws ErrnoException, InterruptedIOException {
        return this.os.write(fileDescriptor, arrby, n, n2);
    }

    @Override
    public int writev(FileDescriptor fileDescriptor, Object[] arrobject, int[] arrn, int[] arrn2) throws ErrnoException, InterruptedIOException {
        return this.os.writev(fileDescriptor, arrobject, arrn, arrn2);
    }
}

