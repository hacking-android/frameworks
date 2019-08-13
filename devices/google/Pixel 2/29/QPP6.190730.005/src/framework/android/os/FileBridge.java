/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  libcore.io.IoBridge
 *  libcore.io.IoUtils
 *  libcore.io.Memory
 *  libcore.io.Streams
 *  libcore.util.ArrayUtils
 */
package android.os;

import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import libcore.io.IoBridge;
import libcore.io.IoUtils;
import libcore.io.Memory;
import libcore.io.Streams;
import libcore.util.ArrayUtils;

@Deprecated
public class FileBridge
extends Thread {
    private static final int CMD_CLOSE = 3;
    private static final int CMD_FSYNC = 2;
    private static final int CMD_WRITE = 1;
    private static final int MSG_LENGTH = 8;
    private static final String TAG = "FileBridge";
    private final FileDescriptor mClient = new FileDescriptor();
    private volatile boolean mClosed;
    private final FileDescriptor mServer = new FileDescriptor();
    private FileDescriptor mTarget;

    public FileBridge() {
        try {
            Os.socketpair((int)OsConstants.AF_UNIX, (int)OsConstants.SOCK_STREAM, (int)0, (FileDescriptor)this.mServer, (FileDescriptor)this.mClient);
            return;
        }
        catch (ErrnoException errnoException) {
            throw new RuntimeException("Failed to create bridge");
        }
    }

    public void forceClose() {
        IoUtils.closeQuietly((FileDescriptor)this.mTarget);
        IoUtils.closeQuietly((FileDescriptor)this.mServer);
        IoUtils.closeQuietly((FileDescriptor)this.mClient);
        this.mClosed = true;
    }

    public FileDescriptor getClientSocket() {
        return this.mClient;
    }

    public boolean isClosed() {
        return this.mClosed;
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
    public void run() {
        Throwable throwable2222;
        block11 : {
            Object object = new byte[8192];
            while (IoBridge.read((FileDescriptor)this.mServer, (byte[])object, (int)0, (int)8) == 8) {
                int n;
                int n2 = Memory.peekInt((byte[])object, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN);
                if (n2 == 1) {
                } else {
                    if (n2 == 2) {
                        Os.fsync((FileDescriptor)this.mTarget);
                        IoBridge.write((FileDescriptor)this.mServer, (byte[])object, (int)0, (int)8);
                        continue;
                    }
                    if (n2 != 3) continue;
                    Os.fsync((FileDescriptor)this.mTarget);
                    Os.close((FileDescriptor)this.mTarget);
                    this.mClosed = true;
                    IoBridge.write((FileDescriptor)this.mServer, (byte[])object, (int)0, (int)8);
                    break;
                }
                for (n2 = Memory.peekInt((byte[])object, (int)4, (ByteOrder)ByteOrder.BIG_ENDIAN); n2 > 0; n2 -= n) {
                    n = IoBridge.read((FileDescriptor)this.mServer, (byte[])object, (int)0, (int)Math.min(((byte[])object).length, n2));
                    if (n != -1) {
                        IoBridge.write((FileDescriptor)this.mTarget, (byte[])object, (int)0, (int)n);
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected EOF; still expected ");
                    stringBuilder.append(n2);
                    stringBuilder.append(" bytes");
                    object = new IOException(stringBuilder.toString());
                    throw object;
                }
            }
            {
                catch (Throwable throwable2222) {
                    break block11;
                }
                catch (ErrnoException | IOException throwable3) {}
                {
                    Log.wtf(TAG, "Failed during bridge", throwable3);
                }
            }
            this.forceClose();
            return;
        }
        this.forceClose();
        throw throwable2222;
    }

    public void setTargetFile(FileDescriptor fileDescriptor) {
        this.mTarget = fileDescriptor;
    }

    public static class FileBridgeOutputStream
    extends OutputStream {
        private final FileDescriptor mClient;
        private final ParcelFileDescriptor mClientPfd;
        private final byte[] mTemp = new byte[8];

        public FileBridgeOutputStream(ParcelFileDescriptor parcelFileDescriptor) {
            this.mClientPfd = parcelFileDescriptor;
            this.mClient = parcelFileDescriptor.getFileDescriptor();
        }

        public FileBridgeOutputStream(FileDescriptor fileDescriptor) {
            this.mClientPfd = null;
            this.mClient = fileDescriptor;
        }

        private void writeCommandAndBlock(int n, String string2) throws IOException {
            Memory.pokeInt((byte[])this.mTemp, (int)0, (int)n, (ByteOrder)ByteOrder.BIG_ENDIAN);
            IoBridge.write((FileDescriptor)this.mClient, (byte[])this.mTemp, (int)0, (int)8);
            if (IoBridge.read((FileDescriptor)this.mClient, (byte[])this.mTemp, (int)0, (int)8) == 8 && Memory.peekInt((byte[])this.mTemp, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN) == n) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to execute ");
            stringBuilder.append(string2);
            stringBuilder.append(" across bridge");
            throw new IOException(stringBuilder.toString());
        }

        @Override
        public void close() throws IOException {
            try {
                this.writeCommandAndBlock(3, "close()");
                return;
            }
            finally {
                IoBridge.closeAndSignalBlockedThreads((FileDescriptor)this.mClient);
                IoUtils.closeQuietly((AutoCloseable)this.mClientPfd);
            }
        }

        public void fsync() throws IOException {
            this.writeCommandAndBlock(2, "fsync()");
        }

        @Override
        public void write(int n) throws IOException {
            Streams.writeSingleByte((OutputStream)this, (int)n);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            ArrayUtils.throwsIfOutOfBounds((int)arrby.length, (int)n, (int)n2);
            Memory.pokeInt((byte[])this.mTemp, (int)0, (int)1, (ByteOrder)ByteOrder.BIG_ENDIAN);
            Memory.pokeInt((byte[])this.mTemp, (int)4, (int)n2, (ByteOrder)ByteOrder.BIG_ENDIAN);
            IoBridge.write((FileDescriptor)this.mClient, (byte[])this.mTemp, (int)0, (int)8);
            IoBridge.write((FileDescriptor)this.mClient, (byte[])arrby, (int)n, (int)n2);
        }
    }

}

