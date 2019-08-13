/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.system.CloseGuard
 *  dalvik.system.VMRuntime
 *  libcore.io.IoUtils
 *  libcore.io.Memory
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.MemoryFile;
import android.os.MessageQueue;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Log;
import dalvik.system.CloseGuard;
import dalvik.system.VMRuntime;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UncheckedIOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.ByteOrder;
import libcore.io.IoUtils;
import libcore.io.Memory;

public class ParcelFileDescriptor
implements Parcelable,
Closeable {
    public static final Parcelable.Creator<ParcelFileDescriptor> CREATOR = new Parcelable.Creator<ParcelFileDescriptor>(){

        @Override
        public ParcelFileDescriptor createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            FileDescriptor fileDescriptor = parcel.readRawFileDescriptor();
            FileDescriptor fileDescriptor2 = null;
            if (n != 0) {
                fileDescriptor2 = parcel.readRawFileDescriptor();
            }
            return new ParcelFileDescriptor(fileDescriptor, fileDescriptor2);
        }

        public ParcelFileDescriptor[] newArray(int n) {
            return new ParcelFileDescriptor[n];
        }
    };
    private static final int MAX_STATUS = 1024;
    public static final int MODE_APPEND = 33554432;
    public static final int MODE_CREATE = 134217728;
    public static final int MODE_READ_ONLY = 268435456;
    public static final int MODE_READ_WRITE = 805306368;
    public static final int MODE_TRUNCATE = 67108864;
    @Deprecated
    public static final int MODE_WORLD_READABLE = 1;
    @Deprecated
    public static final int MODE_WORLD_WRITEABLE = 2;
    public static final int MODE_WRITE_ONLY = 536870912;
    private static final String TAG = "ParcelFileDescriptor";
    private volatile boolean mClosed;
    private FileDescriptor mCommFd;
    private final FileDescriptor mFd;
    private final CloseGuard mGuard = CloseGuard.get();
    private Status mStatus;
    private byte[] mStatusBuf;
    private final ParcelFileDescriptor mWrapped;

    public ParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        this.mWrapped = parcelFileDescriptor;
        this.mFd = null;
        this.mCommFd = null;
        this.mClosed = true;
    }

    @UnsupportedAppUsage
    public ParcelFileDescriptor(FileDescriptor fileDescriptor) {
        this(fileDescriptor, null);
    }

    public ParcelFileDescriptor(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2) {
        if (fileDescriptor != null) {
            this.mWrapped = null;
            this.mFd = fileDescriptor;
            IoUtils.setFdOwner((FileDescriptor)this.mFd, (Object)this);
            fileDescriptor = this.mCommFd = fileDescriptor2;
            if (fileDescriptor != null) {
                IoUtils.setFdOwner((FileDescriptor)fileDescriptor, (Object)this);
            }
            this.mGuard.open("close");
            return;
        }
        throw new NullPointerException("FileDescriptor must not be null");
    }

    public static ParcelFileDescriptor adoptFd(int n) {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(n);
        return new ParcelFileDescriptor(fileDescriptor);
    }

    private void closeWithStatus(int n, String string2) {
        if (this.mClosed) {
            return;
        }
        this.mClosed = true;
        CloseGuard closeGuard = this.mGuard;
        if (closeGuard != null) {
            closeGuard.close();
        }
        this.writeCommStatusAndClose(n, string2);
        IoUtils.closeQuietly((FileDescriptor)this.mFd);
        this.releaseResources();
    }

    private static FileDescriptor[] createCommSocketPair() throws IOException {
        FileDescriptor fileDescriptor;
        FileDescriptor fileDescriptor2;
        try {
            fileDescriptor = new FileDescriptor();
            fileDescriptor2 = new FileDescriptor();
            Os.socketpair((int)OsConstants.AF_UNIX, (int)(OsConstants.SOCK_SEQPACKET | ParcelFileDescriptor.ifAtLeastQ(OsConstants.SOCK_CLOEXEC)), (int)0, (FileDescriptor)fileDescriptor, (FileDescriptor)fileDescriptor2);
            IoUtils.setBlocking((FileDescriptor)fileDescriptor, (boolean)false);
            IoUtils.setBlocking((FileDescriptor)fileDescriptor2, (boolean)false);
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
        return new FileDescriptor[]{fileDescriptor, fileDescriptor2};
    }

    public static ParcelFileDescriptor[] createPipe() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;
        Object object;
        try {
            object = Os.pipe2((int)ParcelFileDescriptor.ifAtLeastQ(OsConstants.O_CLOEXEC));
            parcelFileDescriptor = new ParcelFileDescriptor(object[0]);
            object = new ParcelFileDescriptor(object[1]);
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
        return new ParcelFileDescriptor[]{parcelFileDescriptor, object};
    }

    public static ParcelFileDescriptor[] createReliablePipe() throws IOException {
        Object object;
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            FileDescriptor[] arrfileDescriptor = ParcelFileDescriptor.createCommSocketPair();
            object = Os.pipe2((int)ParcelFileDescriptor.ifAtLeastQ(OsConstants.O_CLOEXEC));
            parcelFileDescriptor = new ParcelFileDescriptor(object[0], arrfileDescriptor[0]);
            object = new ParcelFileDescriptor(object[1], arrfileDescriptor[1]);
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
        return new ParcelFileDescriptor[]{parcelFileDescriptor, object};
    }

    public static ParcelFileDescriptor[] createReliableSocketPair() throws IOException {
        return ParcelFileDescriptor.createReliableSocketPair(OsConstants.SOCK_STREAM);
    }

    public static ParcelFileDescriptor[] createReliableSocketPair(int n) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;
        Object object;
        try {
            FileDescriptor[] arrfileDescriptor = ParcelFileDescriptor.createCommSocketPair();
            FileDescriptor fileDescriptor = new FileDescriptor();
            object = new FileDescriptor();
            Os.socketpair((int)OsConstants.AF_UNIX, (int)(ParcelFileDescriptor.ifAtLeastQ(OsConstants.SOCK_CLOEXEC) | n), (int)0, (FileDescriptor)fileDescriptor, (FileDescriptor)object);
            parcelFileDescriptor = new ParcelFileDescriptor(fileDescriptor, arrfileDescriptor[0]);
            object = new ParcelFileDescriptor((FileDescriptor)object, arrfileDescriptor[1]);
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
        return new ParcelFileDescriptor[]{parcelFileDescriptor, object};
    }

    public static ParcelFileDescriptor[] createSocketPair() throws IOException {
        return ParcelFileDescriptor.createSocketPair(OsConstants.SOCK_STREAM);
    }

    public static ParcelFileDescriptor[] createSocketPair(int n) throws IOException {
        Object object;
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            object = new FileDescriptor();
            FileDescriptor fileDescriptor = new FileDescriptor();
            Os.socketpair((int)OsConstants.AF_UNIX, (int)(ParcelFileDescriptor.ifAtLeastQ(OsConstants.SOCK_CLOEXEC) | n), (int)0, (FileDescriptor)object, (FileDescriptor)fileDescriptor);
            parcelFileDescriptor = new ParcelFileDescriptor((FileDescriptor)object);
            object = new ParcelFileDescriptor(fileDescriptor);
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
        return new ParcelFileDescriptor[]{parcelFileDescriptor, object};
    }

    public static ParcelFileDescriptor dup(FileDescriptor object) throws IOException {
        try {
            FileDescriptor fileDescriptor = new FileDescriptor();
            int n = ParcelFileDescriptor.isAtLeastQ() ? OsConstants.F_DUPFD_CLOEXEC : OsConstants.F_DUPFD;
            fileDescriptor.setInt$(Os.fcntlInt((FileDescriptor)object, (int)n, (int)0));
            object = new ParcelFileDescriptor(fileDescriptor);
            return object;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static ParcelFileDescriptor fromData(byte[] object, String object2) throws IOException {
        Object var2_2 = null;
        if (object == null) {
            return null;
        }
        object2 = new MemoryFile((String)object2, ((byte[])object).length);
        if (((byte[])object).length > 0) {
            ((MemoryFile)object2).writeBytes((byte[])object, 0, 0, ((byte[])object).length);
        }
        ((MemoryFile)object2).deactivate();
        object2 = ((MemoryFile)object2).getFileDescriptor();
        object = var2_2;
        if (object2 != null) {
            object = ParcelFileDescriptor.dup((FileDescriptor)object2);
        }
        return object;
    }

    public static ParcelFileDescriptor fromDatagramSocket(DatagramSocket object) {
        if ((object = object.getFileDescriptor$()) != null) {
            try {
                object = ParcelFileDescriptor.dup((FileDescriptor)object);
            }
            catch (IOException iOException) {
                throw new UncheckedIOException(iOException);
            }
        } else {
            object = null;
        }
        return object;
    }

    public static ParcelFileDescriptor fromFd(int n) throws IOException {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(n);
        try {
            Object object = new FileDescriptor();
            n = ParcelFileDescriptor.isAtLeastQ() ? OsConstants.F_DUPFD_CLOEXEC : OsConstants.F_DUPFD;
            object.setInt$(Os.fcntlInt((FileDescriptor)fileDescriptor, (int)n, (int)0));
            object = new ParcelFileDescriptor((FileDescriptor)object);
            return object;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    public static ParcelFileDescriptor fromFd(FileDescriptor object, Handler object2, final OnCloseListener onCloseListener) throws IOException {
        if (object2 != null) {
            if (onCloseListener != null) {
                FileDescriptor[] arrfileDescriptor = ParcelFileDescriptor.createCommSocketPair();
                object = new ParcelFileDescriptor((FileDescriptor)object, arrfileDescriptor[0]);
                object2 = ((Handler)object2).getLooper().getQueue();
                ((MessageQueue)object2).addOnFileDescriptorEventListener(arrfileDescriptor[1], 1, new MessageQueue.OnFileDescriptorEventListener(){

                    @Override
                    public int onFileDescriptorEvents(FileDescriptor fileDescriptor, int n) {
                        Status status = null;
                        if ((n & 1) != 0) {
                            status = ParcelFileDescriptor.readCommStatus(fileDescriptor, new byte[1024]);
                        } else if ((n & 4) != 0) {
                            status = new Status(-2);
                        }
                        if (status != null) {
                            MessageQueue.this.removeOnFileDescriptorEventListener(fileDescriptor);
                            IoUtils.closeQuietly((FileDescriptor)fileDescriptor);
                            onCloseListener.onClose(status.asIOException());
                            return 0;
                        }
                        return 1;
                    }
                });
                return object;
            }
            throw new IllegalArgumentException("Listener must not be null");
        }
        throw new IllegalArgumentException("Handler must not be null");
    }

    public static ParcelFileDescriptor fromPfd(ParcelFileDescriptor parcelFileDescriptor, Handler handler, OnCloseListener onCloseListener) throws IOException {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.setInt$(parcelFileDescriptor.detachFd());
        return ParcelFileDescriptor.fromFd(fileDescriptor, handler, onCloseListener);
    }

    public static ParcelFileDescriptor fromSocket(Socket object) {
        if ((object = object.getFileDescriptor$()) != null) {
            try {
                object = ParcelFileDescriptor.dup((FileDescriptor)object);
            }
            catch (IOException iOException) {
                throw new UncheckedIOException(iOException);
            }
        } else {
            object = null;
        }
        return object;
    }

    public static File getFile(FileDescriptor object) throws IOException {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/proc/self/fd/");
            stringBuilder.append(object.getInt$());
            object = Os.readlink((String)stringBuilder.toString());
            if (OsConstants.S_ISREG((int)Os.stat((String)object).st_mode)) {
                return new File((String)object);
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Not a regular file: ");
            stringBuilder.append((String)object);
            IOException iOException = new IOException(stringBuilder.toString());
            throw iOException;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    private byte[] getOrCreateStatusBuffer() {
        if (this.mStatusBuf == null) {
            this.mStatusBuf = new byte[1024];
        }
        return this.mStatusBuf;
    }

    private static int ifAtLeastQ(int n) {
        if (!ParcelFileDescriptor.isAtLeastQ()) {
            n = 0;
        }
        return n;
    }

    private static boolean isAtLeastQ() {
        boolean bl = VMRuntime.getRuntime().getTargetSdkVersion() >= 29;
        return bl;
    }

    public static ParcelFileDescriptor open(File object, int n) throws FileNotFoundException {
        if ((object = ParcelFileDescriptor.openInternal((File)object, n)) == null) {
            return null;
        }
        return new ParcelFileDescriptor((FileDescriptor)object);
    }

    public static ParcelFileDescriptor open(File object, int n, Handler handler, OnCloseListener onCloseListener) throws IOException {
        if (handler != null) {
            if (onCloseListener != null) {
                if ((object = ParcelFileDescriptor.openInternal((File)object, n)) == null) {
                    return null;
                }
                return ParcelFileDescriptor.fromFd((FileDescriptor)object, handler, onCloseListener);
            }
            throw new IllegalArgumentException("Listener must not be null");
        }
        throw new IllegalArgumentException("Handler must not be null");
    }

    private static FileDescriptor openInternal(File object, int n) throws FileNotFoundException {
        int n2;
        int n3 = FileUtils.translateModePfdToPosix(n);
        int n4 = ParcelFileDescriptor.ifAtLeastQ(OsConstants.O_CLOEXEC);
        int n5 = n2 = OsConstants.S_IRWXU | OsConstants.S_IRWXG;
        if ((n & 1) != 0) {
            n5 = n2 | OsConstants.S_IROTH;
        }
        n2 = n5;
        if ((n & 2) != 0) {
            n2 = n5 | OsConstants.S_IWOTH;
        }
        object = ((File)object).getPath();
        try {
            object = Os.open((String)object, (int)(n3 | n4), (int)n2);
            return object;
        }
        catch (ErrnoException errnoException) {
            throw new FileNotFoundException(errnoException.getMessage());
        }
    }

    public static int parseMode(String string2) {
        return FileUtils.translateModePosixToPfd(FileUtils.translateModeStringToPosix(string2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Status readCommStatus(FileDescriptor object, byte[] arrby) {
        try {
            int n = Os.read((FileDescriptor)object, (byte[])arrby, (int)0, (int)arrby.length);
            if (n == 0) {
                return new Status(-2);
            }
            int n2 = Memory.peekInt((byte[])arrby, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN);
            if (n2 != 1) return new Status(n2);
            object = new String(arrby, 4, n - 4);
            return new Status(n2, (String)object);
        }
        catch (InterruptedIOException interruptedIOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to read status; assuming dead: ");
            ((StringBuilder)object).append(interruptedIOException);
            Log.d(TAG, ((StringBuilder)object).toString());
            return new Status(-2);
        }
        catch (ErrnoException errnoException) {
            if (errnoException.errno == OsConstants.EAGAIN) {
                return null;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to read status; assuming dead: ");
            ((StringBuilder)object).append((Object)errnoException);
            Log.d(TAG, ((StringBuilder)object).toString());
            return new Status(-2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeCommStatusAndClose(int n, String charSequence) {
        Object object;
        block11 : {
            if (this.mCommFd == null) {
                if (charSequence == null) return;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to inform peer: ");
                stringBuilder.append((String)charSequence);
                Log.w(TAG, stringBuilder.toString());
                return;
            }
            if (n == 2) {
                Log.w(TAG, "Peer expected signal when closed; unable to deliver after detach");
            }
            if (n == -1) {
                IoUtils.closeQuietly((FileDescriptor)this.mCommFd);
                this.mCommFd = null;
                return;
            }
            this.mStatus = ParcelFileDescriptor.readCommStatus(this.mCommFd, this.getOrCreateStatusBuffer());
            object = this.mStatus;
            if (object == null) break block11;
            IoUtils.closeQuietly((FileDescriptor)this.mCommFd);
            this.mCommFd = null;
            return;
        }
        try {
            try {
                int n2;
                object = this.getOrCreateStatusBuffer();
                Memory.pokeInt((byte[])object, (int)0, (int)n, (ByteOrder)ByteOrder.BIG_ENDIAN);
                n = n2 = 0 + 4;
                if (charSequence != null) {
                    charSequence = ((String)charSequence).getBytes();
                    n = Math.min(((CharSequence)charSequence).length, ((Object)object).length - n2);
                    System.arraycopy(charSequence, 0, object, n2, n);
                    n = n2 + n;
                }
                Os.write((FileDescriptor)this.mCommFd, (byte[])object, (int)0, (int)n);
                return;
            }
            catch (InterruptedIOException interruptedIOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to report status: ");
                ((StringBuilder)object).append(interruptedIOException);
                Log.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            catch (ErrnoException errnoException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Failed to report status: ");
                ((StringBuilder)charSequence).append((Object)errnoException);
                Log.w(TAG, ((StringBuilder)charSequence).toString());
                return;
            }
        }
        finally {
            IoUtils.closeQuietly((FileDescriptor)this.mCommFd);
            this.mCommFd = null;
        }
    }

    public boolean canDetectErrors() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.canDetectErrors();
        }
        boolean bl = this.mCommFd != null;
        return bl;
    }

    public void checkError() throws IOException {
        Object object = this.mWrapped;
        if (object != null) {
            ((ParcelFileDescriptor)object).checkError();
            return;
        }
        if (this.mStatus == null) {
            object = this.mCommFd;
            if (object == null) {
                Log.w(TAG, "Peer didn't provide a comm channel; unable to check for errors");
                return;
            }
            this.mStatus = ParcelFileDescriptor.readCommStatus((FileDescriptor)object, this.getOrCreateStatusBuffer());
        }
        if ((object = this.mStatus) != null && ((Status)object).status != 0) {
            throw this.mStatus.asIOException();
        }
    }

    @Override
    public void close() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            }
            finally {
                this.releaseResources();
            }
        } else {
            this.closeWithStatus(0, null);
        }
    }

    public void closeWithError(String string2) throws IOException {
        block7 : {
            ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.closeWithError(string2);
                }
                finally {
                    this.releaseResources();
                }
            }
            if (string2 == null) break block7;
            this.closeWithStatus(1, string2);
            return;
        }
        throw new IllegalArgumentException("Message must not be null");
    }

    @Override
    public int describeContents() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.describeContents();
        }
        return 1;
    }

    public int detachFd() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.detachFd();
        }
        if (!this.mClosed) {
            int n = IoUtils.acquireRawFd((FileDescriptor)this.mFd);
            this.writeCommStatusAndClose(2, null);
            this.mClosed = true;
            this.mGuard.close();
            this.releaseResources();
            return n;
        }
        throw new IllegalStateException("Already closed");
    }

    public ParcelFileDescriptor dup() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.dup();
        }
        return ParcelFileDescriptor.dup(this.getFileDescriptor());
    }

    protected void finalize() throws Throwable {
        CloseGuard closeGuard;
        if (this.mWrapped != null) {
            this.releaseResources();
        }
        if ((closeGuard = this.mGuard) != null) {
            closeGuard.warnIfOpen();
        }
        try {
            if (!this.mClosed) {
                this.closeWithStatus(3, null);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getFd() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.getFd();
        }
        if (!this.mClosed) {
            return this.mFd.getInt$();
        }
        throw new IllegalStateException("Already closed");
    }

    public FileDescriptor getFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.getFileDescriptor();
        }
        return this.mFd;
    }

    public long getStatSize() {
        ParcelFileDescriptor parcelFileDescriptor;
        block4 : {
            parcelFileDescriptor = this.mWrapped;
            if (parcelFileDescriptor != null) {
                return parcelFileDescriptor.getStatSize();
            }
            try {
                parcelFileDescriptor = Os.fstat((FileDescriptor)this.mFd);
                if (OsConstants.S_ISREG((int)((StructStat)parcelFileDescriptor).st_mode) || OsConstants.S_ISLNK((int)((StructStat)parcelFileDescriptor).st_mode)) break block4;
                return -1L;
            }
            catch (ErrnoException errnoException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("fstat() failed: ");
                stringBuilder.append((Object)errnoException);
                Log.w(TAG, stringBuilder.toString());
                return -1L;
            }
        }
        long l = ((StructStat)parcelFileDescriptor).st_size;
        return l;
    }

    public void releaseResources() {
    }

    @UnsupportedAppUsage
    public long seekTo(long l) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.seekTo(l);
        }
        try {
            l = Os.lseek((FileDescriptor)this.mFd, (long)l, (int)OsConstants.SEEK_SET);
            return l;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    public String toString() {
        Object object = this.mWrapped;
        if (object != null) {
            return ((ParcelFileDescriptor)object).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("{ParcelFileDescriptor: ");
        ((StringBuilder)object).append(this.mFd);
        ((StringBuilder)object).append("}");
        return ((StringBuilder)object).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        ParcelFileDescriptor parcelFileDescriptor = this.mWrapped;
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.writeToParcel(parcel, n);
            }
            finally {
                this.releaseResources();
            }
        } else {
            if (this.mCommFd != null) {
                parcel.writeInt(1);
                parcel.writeFileDescriptor(this.mFd);
                parcel.writeFileDescriptor(this.mCommFd);
            } else {
                parcel.writeInt(0);
                parcel.writeFileDescriptor(this.mFd);
            }
            if ((n & 1) != 0 && !this.mClosed) {
                this.closeWithStatus(-1, null);
            }
        }
    }

    public static class AutoCloseInputStream
    extends FileInputStream {
        private final ParcelFileDescriptor mPfd;

        public AutoCloseInputStream(ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mPfd = parcelFileDescriptor;
        }

        @Override
        public void close() throws IOException {
            try {
                super.close();
                return;
            }
            finally {
                this.mPfd.close();
            }
        }

        @Override
        public int read() throws IOException {
            int n = super.read();
            if (n == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return n;
        }

        @Override
        public int read(byte[] arrby) throws IOException {
            int n = super.read(arrby);
            if (n == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if ((n = super.read(arrby, n, n2)) == -1 && this.mPfd.canDetectErrors()) {
                this.mPfd.checkError();
            }
            return n;
        }
    }

    public static class AutoCloseOutputStream
    extends FileOutputStream {
        private final ParcelFileDescriptor mPfd;

        public AutoCloseOutputStream(ParcelFileDescriptor parcelFileDescriptor) {
            super(parcelFileDescriptor.getFileDescriptor());
            this.mPfd = parcelFileDescriptor;
        }

        @Override
        public void close() throws IOException {
            try {
                super.close();
                return;
            }
            finally {
                this.mPfd.close();
            }
        }
    }

    public static class FileDescriptorDetachedException
    extends IOException {
        private static final long serialVersionUID = 955542466045L;

        public FileDescriptorDetachedException() {
            super("Remote side is detached");
        }
    }

    public static interface OnCloseListener {
        public void onClose(IOException var1);
    }

    private static class Status {
        public static final int DEAD = -2;
        public static final int DETACHED = 2;
        public static final int ERROR = 1;
        public static final int LEAKED = 3;
        public static final int OK = 0;
        public static final int SILENCE = -1;
        public final String msg;
        public final int status;

        public Status(int n) {
            this(n, null);
        }

        public Status(int n, String string2) {
            this.status = n;
            this.msg = string2;
        }

        public IOException asIOException() {
            int n = this.status;
            if (n != -2) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            if (n != 3) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Unknown status: ");
                                stringBuilder.append(this.status);
                                return new IOException(stringBuilder.toString());
                            }
                            return new IOException("Remote side was leaked");
                        }
                        return new FileDescriptorDetachedException();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Remote error: ");
                    stringBuilder.append(this.msg);
                    return new IOException(stringBuilder.toString());
                }
                return null;
            }
            return new IOException("Remote side is dead");
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(this.status);
            stringBuilder.append(": ");
            stringBuilder.append(this.msg);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

