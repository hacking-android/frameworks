/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.mtp;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.mtp.MtpDeviceInfo;
import android.mtp.MtpEvent;
import android.mtp.MtpObjectInfo;
import android.mtp.MtpStorageInfo;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.UserManager;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.IOException;

public final class MtpDevice {
    private static final String TAG = "MtpDevice";
    @GuardedBy(value={"mLock"})
    private CloseGuard mCloseGuard = CloseGuard.get();
    @GuardedBy(value={"mLock"})
    private UsbDeviceConnection mConnection;
    private final UsbDevice mDevice;
    private final Object mLock = new Object();
    private long mNativeContext;

    static {
        System.loadLibrary("media_jni");
    }

    public MtpDevice(UsbDevice usbDevice) {
        Preconditions.checkNotNull(usbDevice);
        this.mDevice = usbDevice;
    }

    private native void native_close();

    private native boolean native_delete_object(int var1);

    private native void native_discard_event_request(int var1);

    private native MtpDeviceInfo native_get_device_info();

    private native byte[] native_get_object(int var1, long var2);

    private native int[] native_get_object_handles(int var1, int var2, int var3);

    private native MtpObjectInfo native_get_object_info(int var1);

    private native long native_get_object_size_long(int var1, int var2) throws IOException;

    private native int native_get_parent(int var1);

    private native long native_get_partial_object(int var1, long var2, long var4, byte[] var6) throws IOException;

    private native int native_get_partial_object_64(int var1, long var2, long var4, byte[] var6) throws IOException;

    private native int native_get_storage_id(int var1);

    private native int[] native_get_storage_ids();

    private native MtpStorageInfo native_get_storage_info(int var1);

    private native byte[] native_get_thumbnail(int var1);

    private native boolean native_import_file(int var1, int var2);

    private native boolean native_import_file(int var1, String var2);

    private native boolean native_open(String var1, int var2);

    private native MtpEvent native_reap_event_request(int var1) throws IOException;

    private native boolean native_send_object(int var1, long var2, int var4);

    private native MtpObjectInfo native_send_object_info(MtpObjectInfo var1);

    private native int native_submit_event_request() throws IOException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mConnection != null) {
                this.mCloseGuard.close();
                this.native_close();
                this.mConnection.close();
                this.mConnection = null;
            }
            return;
        }
    }

    public boolean deleteObject(int n) {
        return this.native_delete_object(n);
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

    public int getDeviceId() {
        return this.mDevice.getDeviceId();
    }

    public MtpDeviceInfo getDeviceInfo() {
        return this.native_get_device_info();
    }

    public String getDeviceName() {
        return this.mDevice.getDeviceName();
    }

    public byte[] getObject(int n, int n2) {
        Preconditions.checkArgumentNonnegative(n2, "objectSize should not be negative");
        return this.native_get_object(n, n2);
    }

    public int[] getObjectHandles(int n, int n2, int n3) {
        return this.native_get_object_handles(n, n2, n3);
    }

    public MtpObjectInfo getObjectInfo(int n) {
        return this.native_get_object_info(n);
    }

    public long getObjectSizeLong(int n, int n2) throws IOException {
        return this.native_get_object_size_long(n, n2);
    }

    public long getParent(int n) {
        return this.native_get_parent(n);
    }

    public long getPartialObject(int n, long l, long l2, byte[] arrby) throws IOException {
        return this.native_get_partial_object(n, l, l2, arrby);
    }

    public long getPartialObject64(int n, long l, long l2, byte[] arrby) throws IOException {
        return this.native_get_partial_object_64(n, l, l2, arrby);
    }

    public long getStorageId(int n) {
        return this.native_get_storage_id(n);
    }

    public int[] getStorageIds() {
        return this.native_get_storage_ids();
    }

    public MtpStorageInfo getStorageInfo(int n) {
        return this.native_get_storage_info(n);
    }

    public byte[] getThumbnail(int n) {
        return this.native_get_thumbnail(n);
    }

    public boolean importFile(int n, ParcelFileDescriptor parcelFileDescriptor) {
        return this.native_import_file(n, parcelFileDescriptor.getFd());
    }

    public boolean importFile(int n, String string2) {
        return this.native_import_file(n, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean open(UsbDeviceConnection usbDeviceConnection) {
        boolean bl = false;
        Context context = usbDeviceConnection.getContext();
        Object object = this.mLock;
        synchronized (object) {
            Throwable throwable2;
            block7 : {
                boolean bl2;
                block6 : {
                    bl2 = bl;
                    if (context != null) {
                        bl2 = bl;
                        try {
                            if (((UserManager)context.getSystemService("user")).hasUserRestriction("no_usb_file_transfer")) break block6;
                            bl2 = this.native_open(this.mDevice.getDeviceName(), usbDeviceConnection.getFileDescriptor());
                        }
                        catch (Throwable throwable2) {
                            break block7;
                        }
                    }
                }
                if (!bl2) {
                    usbDeviceConnection.close();
                } else {
                    this.mConnection = usbDeviceConnection;
                    this.mCloseGuard.open("close");
                }
                return bl2;
            }
            throw throwable2;
        }
    }

    public MtpEvent readEvent(CancellationSignal cancellationSignal) throws IOException {
        final int n = this.native_submit_event_request();
        boolean bl = n >= 0;
        Preconditions.checkState(bl, "Other thread is reading an event.");
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

                @Override
                public void onCancel() {
                    MtpDevice.this.native_discard_event_request(n);
                }
            });
        }
        try {
            MtpEvent mtpEvent = this.native_reap_event_request(n);
            return mtpEvent;
        }
        finally {
            if (cancellationSignal != null) {
                cancellationSignal.setOnCancelListener(null);
            }
        }
    }

    public boolean sendObject(int n, long l, ParcelFileDescriptor parcelFileDescriptor) {
        return this.native_send_object(n, l, parcelFileDescriptor.getFd());
    }

    public MtpObjectInfo sendObjectInfo(MtpObjectInfo mtpObjectInfo) {
        return this.native_send_object_info(mtpObjectInfo);
    }

    public String toString() {
        return this.mDevice.getDeviceName();
    }

}

