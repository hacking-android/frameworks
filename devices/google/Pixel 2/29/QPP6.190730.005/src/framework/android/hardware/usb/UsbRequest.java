/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.hardware.usb;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.util.Log;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

public class UsbRequest {
    static final int MAX_USBFS_BUFFER_SIZE = 16384;
    private static final String TAG = "UsbRequest";
    @UnsupportedAppUsage
    private ByteBuffer mBuffer;
    private Object mClientData;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private UsbDeviceConnection mConnection;
    private UsbEndpoint mEndpoint;
    private boolean mIsUsingNewQueue;
    @UnsupportedAppUsage
    private int mLength;
    private final Object mLock = new Object();
    @UnsupportedAppUsage
    private long mNativeContext;
    private ByteBuffer mTempBuffer;

    private native boolean native_cancel();

    private native void native_close();

    private native int native_dequeue_array(byte[] var1, int var2, boolean var3);

    private native int native_dequeue_direct();

    private native boolean native_init(UsbDeviceConnection var1, int var2, int var3, int var4, int var5);

    private native boolean native_queue(ByteBuffer var1, int var2, int var3);

    private native boolean native_queue_array(byte[] var1, int var2, boolean var3);

    private native boolean native_queue_direct(ByteBuffer var1, int var2, boolean var3);

    public boolean cancel() {
        return this.native_cancel();
    }

    public void close() {
        if (this.mNativeContext != 0L) {
            this.mEndpoint = null;
            this.mConnection = null;
            this.native_close();
            this.mCloseGuard.close();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void dequeue(boolean var1_1) {
        block10 : {
            block9 : {
                block11 : {
                    var2_2 = this.mEndpoint.getDirection() == 0;
                    var3_3 = this.mLock;
                    // MONITORENTER : var3_3
                    if (!this.mIsUsingNewQueue) break block9;
                    var4_4 = this.native_dequeue_direct();
                    this.mIsUsingNewQueue = false;
                    if (this.mBuffer == null) break block10;
                    if (this.mTempBuffer != null) break block11;
                    this.mBuffer.position(this.mBuffer.position() + var4_4);
                    break block10;
                }
                this.mTempBuffer.limit(var4_4);
                if (!var2_2) ** GOTO lbl20
                try {
                    this.mBuffer.position(this.mBuffer.position() + var4_4);
lbl20: // 1 sources:
                    this.mBuffer.put(this.mTempBuffer);
                }
                finally {
                    this.mTempBuffer = null;
                }
            }
            var4_5 = this.mBuffer.isDirect() != false ? this.native_dequeue_direct() : this.native_dequeue_array(this.mBuffer.array(), this.mLength, var2_2);
            if (var4_5 >= 0) {
                var4_5 = Math.min(var4_5, this.mLength);
                try {
                    this.mBuffer.position(var4_5);
                }
                catch (IllegalArgumentException var6_8) {
                    if (var1_1 == false) throw var6_8;
                    var5_7 /* !! */  = new StringBuilder();
                    var5_7 /* !! */ .append("Buffer ");
                    var5_7 /* !! */ .append(this.mBuffer);
                    var5_7 /* !! */ .append(" does not have enough space to read ");
                    var5_7 /* !! */ .append(var4_5);
                    var5_7 /* !! */ .append(" bytes");
                    Log.e("UsbRequest", var5_7 /* !! */ .toString(), var6_8);
                    var5_7 /* !! */  = new BufferOverflowException();
                    throw var5_7 /* !! */ ;
                }
            }
        }
        this.mBuffer = null;
        this.mLength = 0;
        // MONITOREXIT : var3_3
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public Object getClientData() {
        return this.mClientData;
    }

    public UsbEndpoint getEndpoint() {
        return this.mEndpoint;
    }

    public boolean initialize(UsbDeviceConnection usbDeviceConnection, UsbEndpoint usbEndpoint) {
        this.mEndpoint = usbEndpoint;
        this.mConnection = Preconditions.checkNotNull(usbDeviceConnection, "connection");
        boolean bl = this.native_init(usbDeviceConnection, usbEndpoint.getAddress(), usbEndpoint.getAttributes(), usbEndpoint.getMaxPacketSize(), usbEndpoint.getInterval());
        if (bl) {
            this.mCloseGuard.open("close");
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean queue(ByteBuffer byteBuffer) {
        boolean bl = this.mNativeContext != 0L;
        Preconditions.checkState(bl, "request is not initialized");
        Preconditions.checkState(this.mIsUsingNewQueue ^ true, "this request is currently queued");
        boolean bl2 = this.mEndpoint.getDirection() == 0;
        Object object = this.mLock;
        // MONITORENTER : object
        this.mBuffer = byteBuffer;
        if (byteBuffer == null) {
            this.mIsUsingNewQueue = true;
            bl = this.native_queue(null, 0, 0);
        } else {
            if (this.mConnection.getContext().getApplicationInfo().targetSdkVersion < 28) {
                Preconditions.checkArgumentInRange(byteBuffer.remaining(), 0, 16384, "number of remaining bytes");
            }
            bl = !byteBuffer.isReadOnly() || bl2;
            Preconditions.checkArgument(bl, "buffer can not be read-only when receiving data");
            ByteBuffer byteBuffer2 = byteBuffer;
            if (!byteBuffer.isDirect()) {
                this.mTempBuffer = ByteBuffer.allocateDirect(this.mBuffer.remaining());
                if (bl2) {
                    this.mBuffer.mark();
                    this.mTempBuffer.put(this.mBuffer);
                    this.mTempBuffer.flip();
                    this.mBuffer.reset();
                }
                byteBuffer2 = this.mTempBuffer;
            }
            this.mIsUsingNewQueue = true;
            bl = this.native_queue(byteBuffer2, byteBuffer2.position(), byteBuffer2.remaining());
        }
        // MONITOREXIT : object
        if (bl) return bl;
        this.mIsUsingNewQueue = false;
        this.mTempBuffer = null;
        this.mBuffer = null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean queue(ByteBuffer object, int n) {
        boolean bl = this.mEndpoint.getDirection() == 0;
        int n2 = n;
        if (this.mConnection.getContext().getApplicationInfo().targetSdkVersion < 28) {
            n2 = n;
            if (n > 16384) {
                n2 = 16384;
            }
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            this.mBuffer = object;
            this.mLength = n2;
            if (((ByteBuffer)object).isDirect()) {
                bl = this.native_queue_direct((ByteBuffer)object, n2, bl);
            } else {
                if (!((ByteBuffer)object).hasArray()) {
                    object = new IllegalArgumentException("buffer is not direct and has no array");
                    throw object;
                }
                bl = this.native_queue_array(((ByteBuffer)object).array(), n2, bl);
            }
            if (!bl) {
                this.mBuffer = null;
                this.mLength = 0;
            }
            return bl;
        }
    }

    public void setClientData(Object object) {
        this.mClientData = object;
    }
}

