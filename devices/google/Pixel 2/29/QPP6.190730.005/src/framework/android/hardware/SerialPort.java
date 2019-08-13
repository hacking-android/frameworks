/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.os.ParcelFileDescriptor;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SerialPort {
    private static final String TAG = "SerialPort";
    private ParcelFileDescriptor mFileDescriptor;
    private final String mName;
    @UnsupportedAppUsage
    private int mNativeContext;

    public SerialPort(String string2) {
        this.mName = string2;
    }

    private native void native_close();

    private native void native_open(FileDescriptor var1, int var2) throws IOException;

    private native int native_read_array(byte[] var1, int var2) throws IOException;

    private native int native_read_direct(ByteBuffer var1, int var2) throws IOException;

    private native void native_send_break();

    private native void native_write_array(byte[] var1, int var2) throws IOException;

    private native void native_write_direct(ByteBuffer var1, int var2) throws IOException;

    @UnsupportedAppUsage
    public void close() throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = this.mFileDescriptor;
        if (parcelFileDescriptor != null) {
            parcelFileDescriptor.close();
            this.mFileDescriptor = null;
        }
        this.native_close();
    }

    public String getName() {
        return this.mName;
    }

    public void open(ParcelFileDescriptor parcelFileDescriptor, int n) throws IOException {
        this.native_open(parcelFileDescriptor.getFileDescriptor(), n);
        this.mFileDescriptor = parcelFileDescriptor;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.isDirect()) {
            return this.native_read_direct(byteBuffer, byteBuffer.remaining());
        }
        if (byteBuffer.hasArray()) {
            return this.native_read_array(byteBuffer.array(), byteBuffer.remaining());
        }
        throw new IllegalArgumentException("buffer is not direct and has no array");
    }

    public void sendBreak() {
        this.native_send_break();
    }

    @UnsupportedAppUsage
    public void write(ByteBuffer byteBuffer, int n) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!byteBuffer.isDirect()) break block2;
                    this.native_write_direct(byteBuffer, n);
                    break block3;
                }
                if (!byteBuffer.hasArray()) break block4;
                this.native_write_array(byteBuffer.array(), n);
            }
            return;
        }
        throw new IllegalArgumentException("buffer is not direct and has no array");
    }
}

