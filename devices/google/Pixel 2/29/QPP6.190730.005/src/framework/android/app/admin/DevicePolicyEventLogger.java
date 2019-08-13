/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.content.ComponentName;
import android.stats.devicepolicy.nano.StringList;
import android.util.StatsLog;
import com.android.framework.protobuf.nano.MessageNano;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

public class DevicePolicyEventLogger {
    private String mAdminPackageName;
    private boolean mBooleanValue;
    private final int mEventId;
    private int mIntValue;
    private String[] mStringArrayValue;
    private long mTimePeriodMs;

    private DevicePolicyEventLogger(int n) {
        this.mEventId = n;
    }

    public static DevicePolicyEventLogger createEvent(int n) {
        return new DevicePolicyEventLogger(n);
    }

    private static byte[] stringArrayValueToBytes(String[] arrstring) {
        if (arrstring == null) {
            return null;
        }
        StringList stringList = new StringList();
        stringList.stringValue = arrstring;
        return MessageNano.toByteArray(stringList);
    }

    public String getAdminPackageName() {
        return this.mAdminPackageName;
    }

    public boolean getBoolean() {
        return this.mBooleanValue;
    }

    public int getEventId() {
        return this.mEventId;
    }

    public int getInt() {
        return this.mIntValue;
    }

    public String[] getStringArray() {
        String[] arrstring = this.mStringArrayValue;
        if (arrstring == null) {
            return null;
        }
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    public long getTimePeriod() {
        return this.mTimePeriodMs;
    }

    public DevicePolicyEventLogger setAdmin(ComponentName object) {
        object = object != null ? ((ComponentName)object).getPackageName() : null;
        this.mAdminPackageName = object;
        return this;
    }

    public DevicePolicyEventLogger setAdmin(String string2) {
        this.mAdminPackageName = string2;
        return this;
    }

    public DevicePolicyEventLogger setBoolean(boolean bl) {
        this.mBooleanValue = bl;
        return this;
    }

    public DevicePolicyEventLogger setInt(int n) {
        this.mIntValue = n;
        return this;
    }

    public DevicePolicyEventLogger setStrings(String string2, String string3, String[] arrstring) {
        Preconditions.checkNotNull(arrstring, "values parameter cannot be null");
        String[] arrstring2 = this.mStringArrayValue = new String[arrstring.length + 2];
        arrstring2[0] = string2;
        arrstring2[1] = string3;
        System.arraycopy(arrstring, 0, arrstring2, 2, arrstring.length);
        return this;
    }

    public DevicePolicyEventLogger setStrings(String string2, String[] arrstring) {
        Preconditions.checkNotNull(arrstring, "values parameter cannot be null");
        String[] arrstring2 = this.mStringArrayValue = new String[arrstring.length + 1];
        arrstring2[0] = string2;
        System.arraycopy(arrstring, 0, arrstring2, 1, arrstring.length);
        return this;
    }

    public DevicePolicyEventLogger setStrings(String ... arrstring) {
        this.mStringArrayValue = arrstring;
        return this;
    }

    public DevicePolicyEventLogger setTimePeriod(long l) {
        this.mTimePeriodMs = l;
        return this;
    }

    public void write() {
        byte[] arrby = DevicePolicyEventLogger.stringArrayValueToBytes(this.mStringArrayValue);
        StatsLog.write(103, this.mEventId, this.mAdminPackageName, this.mIntValue, this.mBooleanValue, this.mTimePeriodMs, arrby);
    }
}

