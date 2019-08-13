/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

import android.content.Context;
import android.content.SharedPreferences;
import android.mtp.MtpDatabase;
import android.mtp.MtpStorage;
import android.util.ByteStringUtils;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.util.Random;

public class MtpServer
implements Runnable {
    private static final int sID_LEN_BYTES = 16;
    private static final int sID_LEN_STR = 32;
    private final Context mContext;
    private final MtpDatabase mDatabase;
    private long mNativeContext;
    private final Runnable mOnTerminate;

    static {
        System.loadLibrary("media_jni");
    }

    public MtpServer(MtpDatabase mtpDatabase, FileDescriptor fileDescriptor, boolean bl, Runnable object, String string2, String string3, String string4) {
        this.mDatabase = Preconditions.checkNotNull(mtpDatabase);
        this.mOnTerminate = Preconditions.checkNotNull(object);
        this.mContext = this.mDatabase.getContext();
        object = null;
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("mtp-cfg", 0);
        if (sharedPreferences.contains("mtp-id")) {
            String string5 = sharedPreferences.getString("mtp-id", null);
            if (string5.length() != 32) {
                object = null;
            } else {
                int n = 0;
                do {
                    object = string5;
                    if (n >= string5.length()) break;
                    if (Character.digit(string5.charAt(n), 16) == -1) {
                        object = null;
                        break;
                    }
                    ++n;
                } while (true);
            }
        }
        if (object == null) {
            object = this.getRandId();
            sharedPreferences.edit().putString("mtp-id", (String)object).apply();
        }
        this.native_setup(mtpDatabase, fileDescriptor, bl, string2, string3, string4, (String)object);
        mtpDatabase.setServer(this);
    }

    public static void configure(boolean bl) {
        MtpServer.native_configure(bl);
    }

    private String getRandId() {
        Random random = new Random();
        byte[] arrby = new byte[16];
        random.nextBytes(arrby);
        return ByteStringUtils.toHexString(arrby);
    }

    private final native void native_add_storage(MtpStorage var1);

    private final native void native_cleanup();

    public static final native void native_configure(boolean var0);

    private final native void native_remove_storage(int var1);

    private final native void native_run();

    private final native void native_send_device_property_changed(int var1);

    private final native void native_send_object_added(int var1);

    private final native void native_send_object_info_changed(int var1);

    private final native void native_send_object_removed(int var1);

    private final native void native_setup(MtpDatabase var1, FileDescriptor var2, boolean var3, String var4, String var5, String var6, String var7);

    public void addStorage(MtpStorage mtpStorage) {
        this.native_add_storage(mtpStorage);
    }

    public void removeStorage(MtpStorage mtpStorage) {
        this.native_remove_storage(mtpStorage.getStorageId());
    }

    @Override
    public void run() {
        this.native_run();
        this.native_cleanup();
        this.mDatabase.close();
        this.mOnTerminate.run();
    }

    public void sendDevicePropertyChanged(int n) {
        this.native_send_device_property_changed(n);
    }

    public void sendObjectAdded(int n) {
        this.native_send_object_added(n);
    }

    public void sendObjectInfoChanged(int n) {
        this.native_send_object_info_changed(n);
    }

    public void sendObjectRemoved(int n) {
        this.native_send_object_removed(n);
    }

    public void start() {
        new Thread((Runnable)this, "MtpServer").start();
    }
}

