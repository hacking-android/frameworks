/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.hardware.usb;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbRequest;
import android.os.ParcelFileDescriptor;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.FileDescriptor;
import java.util.concurrent.TimeoutException;

public class UsbDeviceConnection {
    private static final String TAG = "UsbDeviceConnection";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private Context mContext;
    private final UsbDevice mDevice;
    @UnsupportedAppUsage
    private long mNativeContext;

    public UsbDeviceConnection(UsbDevice usbDevice) {
        this.mDevice = usbDevice;
    }

    private static void checkBounds(byte[] arrby, int n, int n2) {
        int n3 = arrby != null ? arrby.length : 0;
        if (n2 >= 0 && n >= 0 && n + n2 <= n3) {
            return;
        }
        throw new IllegalArgumentException("Buffer start or length out of bounds.");
    }

    private native int native_bulk_request(int var1, byte[] var2, int var3, int var4, int var5);

    private native boolean native_claim_interface(int var1, boolean var2);

    private native void native_close();

    private native int native_control_request(int var1, int var2, int var3, int var4, byte[] var5, int var6, int var7, int var8);

    private native byte[] native_get_desc();

    private native int native_get_fd();

    private native String native_get_serial();

    private native boolean native_open(String var1, FileDescriptor var2);

    private native boolean native_release_interface(int var1);

    private native UsbRequest native_request_wait(long var1) throws TimeoutException;

    private native boolean native_reset_device();

    private native boolean native_set_configuration(int var1);

    private native boolean native_set_interface(int var1, int var2);

    public int bulkTransfer(UsbEndpoint usbEndpoint, byte[] arrby, int n, int n2) {
        return this.bulkTransfer(usbEndpoint, arrby, 0, n, n2);
    }

    public int bulkTransfer(UsbEndpoint usbEndpoint, byte[] arrby, int n, int n2, int n3) {
        UsbDeviceConnection.checkBounds(arrby, n, n2);
        int n4 = n2;
        if (this.mContext.getApplicationInfo().targetSdkVersion < 28) {
            n4 = n2;
            if (n2 > 16384) {
                n4 = 16384;
            }
        }
        return this.native_bulk_request(usbEndpoint.getAddress(), arrby, n, n4, n3);
    }

    public boolean claimInterface(UsbInterface usbInterface, boolean bl) {
        return this.native_claim_interface(usbInterface.getId(), bl);
    }

    public void close() {
        if (this.mNativeContext != 0L) {
            this.native_close();
            this.mCloseGuard.close();
        }
    }

    public int controlTransfer(int n, int n2, int n3, int n4, byte[] arrby, int n5, int n6) {
        return this.controlTransfer(n, n2, n3, n4, arrby, 0, n5, n6);
    }

    public int controlTransfer(int n, int n2, int n3, int n4, byte[] arrby, int n5, int n6, int n7) {
        UsbDeviceConnection.checkBounds(arrby, n5, n6);
        return this.native_control_request(n, n2, n3, n4, arrby, n5, n6, n7);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getFileDescriptor() {
        return this.native_get_fd();
    }

    public byte[] getRawDescriptors() {
        return this.native_get_desc();
    }

    public String getSerial() {
        return this.native_get_serial();
    }

    boolean open(String string2, ParcelFileDescriptor parcelFileDescriptor, Context context) {
        this.mContext = context.getApplicationContext();
        boolean bl = this.native_open(string2, parcelFileDescriptor.getFileDescriptor());
        if (bl) {
            this.mCloseGuard.open("close");
        }
        return bl;
    }

    public boolean releaseInterface(UsbInterface usbInterface) {
        return this.native_release_interface(usbInterface.getId());
    }

    public UsbRequest requestWait() {
        UsbRequest usbRequest = null;
        try {
            UsbRequest usbRequest2;
            usbRequest = usbRequest2 = this.native_request_wait(-1L);
        }
        catch (TimeoutException timeoutException) {
            // empty catch block
        }
        if (usbRequest != null) {
            boolean bl = this.mContext.getApplicationInfo().targetSdkVersion >= 26;
            usbRequest.dequeue(bl);
        }
        return usbRequest;
    }

    public UsbRequest requestWait(long l) throws TimeoutException {
        UsbRequest usbRequest = this.native_request_wait(Preconditions.checkArgumentNonnegative(l, "timeout"));
        if (usbRequest != null) {
            usbRequest.dequeue(true);
        }
        return usbRequest;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public boolean resetDevice() {
        return this.native_reset_device();
    }

    public boolean setConfiguration(UsbConfiguration usbConfiguration) {
        return this.native_set_configuration(usbConfiguration.getId());
    }

    public boolean setInterface(UsbInterface usbInterface) {
        return this.native_set_interface(usbInterface.getId(), usbInterface.getAlternateSetting());
    }
}

