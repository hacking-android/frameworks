/*
 * Decompiled with CFR 0.145.
 */
package android.net.nsd;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class NsdServiceInfo
implements Parcelable {
    public static final Parcelable.Creator<NsdServiceInfo> CREATOR = new Parcelable.Creator<NsdServiceInfo>(){

        @Override
        public NsdServiceInfo createFromParcel(Parcel parcel) {
            NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
            nsdServiceInfo.mServiceName = parcel.readString();
            nsdServiceInfo.mServiceType = parcel.readString();
            if (parcel.readInt() == 1) {
                try {
                    nsdServiceInfo.mHost = InetAddress.getByAddress(parcel.createByteArray());
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            nsdServiceInfo.mPort = parcel.readInt();
            int n = parcel.readInt();
            for (int i = 0; i < n; ++i) {
                byte[] arrby = null;
                if (parcel.readInt() == 1) {
                    arrby = new byte[parcel.readInt()];
                    parcel.readByteArray(arrby);
                }
                nsdServiceInfo.mTxtRecord.put(parcel.readString(), arrby);
            }
            return nsdServiceInfo;
        }

        public NsdServiceInfo[] newArray(int n) {
            return new NsdServiceInfo[n];
        }
    };
    private static final String TAG = "NsdServiceInfo";
    private InetAddress mHost;
    private int mPort;
    private String mServiceName;
    private String mServiceType;
    private final ArrayMap<String, byte[]> mTxtRecord = new ArrayMap();

    public NsdServiceInfo() {
    }

    public NsdServiceInfo(String string2, String string3) {
        this.mServiceName = string2;
        this.mServiceType = string3;
    }

    private int getTxtRecordSize() {
        int n = 0;
        for (Map.Entry<String, byte[]> entry : this.mTxtRecord.entrySet()) {
            int n2 = entry.getKey().length();
            byte[] arrby = entry.getValue();
            int n3 = arrby == null ? 0 : arrby.length;
            n = n + 2 + n2 + n3;
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Map<String, byte[]> getAttributes() {
        return Collections.unmodifiableMap(this.mTxtRecord);
    }

    public InetAddress getHost() {
        return this.mHost;
    }

    public int getPort() {
        return this.mPort;
    }

    public String getServiceName() {
        return this.mServiceName;
    }

    public String getServiceType() {
        return this.mServiceType;
    }

    public byte[] getTxtRecord() {
        int n = this.getTxtRecordSize();
        if (n == 0) {
            return new byte[0];
        }
        byte[] arrby = new byte[n];
        n = 0;
        for (Map.Entry<String, byte[]> entry : this.mTxtRecord.entrySet()) {
            String string2 = entry.getKey();
            byte[] arrby2 = entry.getValue();
            int n2 = n + 1;
            int n3 = string2.length();
            int n4 = arrby2 == null ? 0 : arrby2.length;
            arrby[n] = (byte)(n3 + n4 + 1);
            System.arraycopy(string2.getBytes(StandardCharsets.US_ASCII), 0, arrby, n2, string2.length());
            n = n2 + string2.length();
            n4 = n + 1;
            arrby[n] = (byte)61;
            n = n4;
            if (arrby2 == null) continue;
            System.arraycopy(arrby2, 0, arrby, n4, arrby2.length);
            n = n4 + arrby2.length;
        }
        return arrby;
    }

    public void removeAttribute(String string2) {
        this.mTxtRecord.remove(string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setAttribute(String var1_1, String var2_3) {
        if (var2_3 != null) ** GOTO lbl5
        try {
            block2 : {
                var2_3 = null;
                break block2;
lbl5: // 1 sources:
                var2_3 = var2_3.getBytes("UTF-8");
            }
            this.setAttribute(var1_1, var2_3);
            return;
        }
        catch (UnsupportedEncodingException var1_2) {
            throw new IllegalArgumentException("Value must be UTF-8");
        }
    }

    @UnsupportedAppUsage
    public void setAttribute(String string2, byte[] arrby) {
        if (!TextUtils.isEmpty(string2)) {
            char c;
            int n;
            for (n = 0; n < string2.length(); ++n) {
                c = string2.charAt(n);
                if (c >= ' ' && c <= '~') {
                    if (c != '=') {
                        continue;
                    }
                    throw new IllegalArgumentException("Key strings must not include '='");
                }
                throw new IllegalArgumentException("Key strings must be printable US-ASCII");
            }
            int n2 = string2.length();
            c = '\u0000';
            n = arrby == null ? 0 : arrby.length;
            if (n2 + n < 255) {
                if (string2.length() > 9) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Key lengths > 9 are discouraged: ");
                    stringBuilder.append(string2);
                    Log.w(TAG, stringBuilder.toString());
                }
                n2 = this.getTxtRecordSize();
                int n3 = string2.length();
                n = arrby == null ? (int)c : arrby.length;
                if ((n = n3 + n2 + n + 2) <= 1300) {
                    if (n > 400) {
                        Log.w(TAG, "Total length of all attributes exceeds 400 bytes; truncation may occur");
                    }
                    this.mTxtRecord.put(string2, arrby);
                    return;
                }
                throw new IllegalArgumentException("Total length of attributes must be < 1300 bytes");
            }
            throw new IllegalArgumentException("Key length + value length must be < 255 bytes");
        }
        throw new IllegalArgumentException("Key cannot be empty");
    }

    public void setHost(InetAddress inetAddress) {
        this.mHost = inetAddress;
    }

    public void setPort(int n) {
        this.mPort = n;
    }

    public void setServiceName(String string2) {
        this.mServiceName = string2;
    }

    public void setServiceType(String string2) {
        this.mServiceType = string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setTxtRecords(String var1_1) {
        var2_3 = Base64.decode((String)var1_1, 0);
        var3_4 = 0;
        while (var3_4 < var2_3.length) {
            block12 : {
                var4_5 = var2_3[var3_4] & 255;
                var5_6 = var3_4 + 1;
                if (var4_5 == 0) ** GOTO lbl35
                var3_4 = var4_5;
                var6_7 = var4_5;
                try {
                    block13 : {
                        if (var5_6 + var4_5 > var2_3.length) {
                            var6_7 = var4_5;
                            var6_7 = var4_5;
                            var1_1 = new StringBuilder();
                            var6_7 = var4_5;
                            var1_1.append("Corrupt record length (pos = ");
                            var6_7 = var4_5;
                            var1_1.append(var5_6);
                            var6_7 = var4_5;
                            var1_1.append("): ");
                            var6_7 = var4_5;
                            var1_1.append(var4_5);
                            var6_7 = var4_5;
                            Log.w("NsdServiceInfo", var1_1.toString());
                            var6_7 = var4_5;
                            var3_4 = var2_3.length - var5_6;
                        }
                        var7_8 = null;
                        var1_1 = null;
                        var8_9 = 0;
                        break block13;
lbl35: // 1 sources:
                        var6_7 = var4_5;
                        var6_7 = var4_5;
                        var1_1 = new IllegalArgumentException("Zero sized txt record");
                        var6_7 = var4_5;
                        throw var1_1;
                    }
                    for (var4_5 = var5_6; var4_5 < var5_6 + var3_4; ++var4_5) {
                        block10 : {
                            block11 : {
                                if (var7_8 != null) ** GOTO lbl52
                                var9_10 /* !! */  = var1_1;
                                var6_7 = var8_9;
                                if (var2_3[var4_5] != 61) break block10;
                                var6_7 = var3_4;
                                var7_8 = new String(var2_3, var5_6, var4_5 - var5_6, StandardCharsets.US_ASCII);
                                var9_10 /* !! */  = var1_1;
                                var6_7 = var8_9;
                                break block10;
lbl52: // 1 sources:
                                var9_10 /* !! */  = var1_1;
                                if (var1_1 != null) break block11;
                                var6_7 = var3_4;
                                var9_10 /* !! */  = new byte[var3_4 - var7_8.length() - 1];
                            }
                            var9_10 /* !! */ [var8_9] = var2_3[var4_5];
                            var6_7 = var8_9 + 1;
                        }
                        var1_1 = var9_10 /* !! */ ;
                        var8_9 = var6_7;
                    }
                    var9_10 /* !! */  = var7_8;
                    if (var7_8 == null) {
                        var6_7 = var3_4;
                        var6_7 = var3_4;
                        var9_10 /* !! */  = new String(var2_3, var5_6, var3_4, StandardCharsets.US_ASCII);
                    }
                    var6_7 = var3_4;
                    if (TextUtils.isEmpty((CharSequence)var9_10 /* !! */ )) {
                        var6_7 = var3_4;
                        var6_7 = var3_4;
                        var1_1 = new IllegalArgumentException("Invalid txt record (key is empty)");
                        var6_7 = var3_4;
                        throw var1_1;
                    }
                    var6_7 = var3_4;
                    if (this.getAttributes().containsKey(var9_10 /* !! */ )) {
                        var6_7 = var3_4;
                        var6_7 = var3_4;
                        var6_7 = var3_4;
                        var7_8 = new StringBuilder();
                        var6_7 = var3_4;
                        var7_8.append("Invalid txt record (duplicate key \"");
                        var6_7 = var3_4;
                        var7_8.append((String)var9_10 /* !! */ );
                        var6_7 = var3_4;
                        var7_8.append("\")");
                        var6_7 = var3_4;
                        var1_1 = new IllegalArgumentException(var7_8.toString());
                        var6_7 = var3_4;
                        throw var1_1;
                    }
                    var6_7 = var3_4;
                    this.setAttribute((String)var9_10 /* !! */ , (byte[])var1_1);
                    break block12;
                }
                catch (IllegalArgumentException var1_2) {}
                var7_8 = new StringBuilder();
                var7_8.append("While parsing txt records (pos = ");
                var7_8.append(var5_6);
                var7_8.append("): ");
                var7_8.append(var1_2.getMessage());
                Log.e("NsdServiceInfo", var7_8.toString());
                var3_4 = var6_7;
            }
            var3_4 = var5_6 + var3_4;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("name: ");
        stringBuffer.append(this.mServiceName);
        stringBuffer.append(", type: ");
        stringBuffer.append(this.mServiceType);
        stringBuffer.append(", host: ");
        stringBuffer.append(this.mHost);
        stringBuffer.append(", port: ");
        stringBuffer.append(this.mPort);
        byte[] arrby = this.getTxtRecord();
        if (arrby != null) {
            stringBuffer.append(", txtRecord: ");
            stringBuffer.append(new String(arrby, StandardCharsets.UTF_8));
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mServiceName);
        parcel.writeString(this.mServiceType);
        if (this.mHost != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mHost.getAddress());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mPort);
        parcel.writeInt(this.mTxtRecord.size());
        for (String string2 : this.mTxtRecord.keySet()) {
            byte[] arrby = this.mTxtRecord.get(string2);
            if (arrby != null) {
                parcel.writeInt(1);
                parcel.writeInt(arrby.length);
                parcel.writeByteArray(arrby);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeString(string2);
        }
    }

}

