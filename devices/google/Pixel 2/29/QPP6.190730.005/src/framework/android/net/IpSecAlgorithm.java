/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.HexDump;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class IpSecAlgorithm
implements Parcelable {
    public static final String AUTH_CRYPT_AES_GCM = "rfc4106(gcm(aes))";
    public static final String AUTH_HMAC_MD5 = "hmac(md5)";
    public static final String AUTH_HMAC_SHA1 = "hmac(sha1)";
    public static final String AUTH_HMAC_SHA256 = "hmac(sha256)";
    public static final String AUTH_HMAC_SHA384 = "hmac(sha384)";
    public static final String AUTH_HMAC_SHA512 = "hmac(sha512)";
    public static final Parcelable.Creator<IpSecAlgorithm> CREATOR = new Parcelable.Creator<IpSecAlgorithm>(){

        @Override
        public IpSecAlgorithm createFromParcel(Parcel parcel) {
            return new IpSecAlgorithm(parcel.readString(), parcel.createByteArray(), parcel.readInt());
        }

        public IpSecAlgorithm[] newArray(int n) {
            return new IpSecAlgorithm[n];
        }
    };
    public static final String CRYPT_AES_CBC = "cbc(aes)";
    public static final String CRYPT_NULL = "ecb(cipher_null)";
    private static final String TAG = "IpSecAlgorithm";
    private final byte[] mKey;
    private final String mName;
    private final int mTruncLenBits;

    public IpSecAlgorithm(String string2, byte[] arrby) {
        this(string2, arrby, 0);
    }

    public IpSecAlgorithm(String string2, byte[] arrby, int n) {
        this.mName = string2;
        this.mKey = (byte[])arrby.clone();
        this.mTruncLenBits = n;
        IpSecAlgorithm.checkValidOrThrow(this.mName, this.mKey.length * 8, this.mTruncLenBits);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void checkValidOrThrow(String var0, int var1_1, int var2_2) {
        block24 : {
            var3_3 = 1;
            var4_4 = var0.hashCode();
            var5_5 = 1;
            var6_6 = 1;
            var7_7 = 1;
            var8_8 = 1;
            var9_9 = 1;
            var10_10 = 1;
            var11_11 = 1;
            switch (var4_4) {
                case 2065384259: {
                    if (!var0.equals("hmac(sha1)")) break;
                    var4_4 = 2;
                    break block24;
                }
                case 759177996: {
                    if (!var0.equals("hmac(md5)")) break;
                    var4_4 = 1;
                    break block24;
                }
                case 559510590: {
                    if (!var0.equals("hmac(sha512)")) break;
                    var4_4 = 5;
                    break block24;
                }
                case 559457797: {
                    if (!var0.equals("hmac(sha384)")) break;
                    var4_4 = 4;
                    break block24;
                }
                case 559425185: {
                    if (!var0.equals("hmac(sha256)")) break;
                    var4_4 = 3;
                    break block24;
                }
                case 394796030: {
                    if (!var0.equals("cbc(aes)")) break;
                    var4_4 = 0;
                    break block24;
                }
                case -1137603038: {
                    if (!var0.equals("rfc4106(gcm(aes))")) break;
                    var4_4 = 6;
                    break block24;
                }
            }
            ** break;
lbl40: // 1 sources:
            var4_4 = -1;
        }
        switch (var4_4) {
            default: {
                var12_12 = new StringBuilder();
                var12_12.append("Couldn't find an algorithm: ");
                var12_12.append((String)var0);
                throw new IllegalArgumentException(var12_12.toString());
            }
            case 6: {
                var4_4 = var1_1 != 160 && var1_1 != 224 && var1_1 != 288 ? 0 : 1;
                var3_3 = var4_4;
                var4_4 = var11_11;
                if (var2_2 != 64) {
                    var4_4 = var11_11;
                    if (var2_2 != 96) {
                        var4_4 = var2_2 == 128 ? var11_11 : 0;
                    }
                }
                var11_11 = var4_4;
                var4_4 = var3_3;
                var3_3 = var11_11;
                ** break;
            }
            case 5: {
                var4_4 = var1_1 == 512 ? 1 : 0;
                var3_3 = var4_4;
                var4_4 = var2_2 >= 256 && var2_2 <= 512 ? var5_5 : 0;
                var11_11 = var4_4;
                var4_4 = var3_3;
                var3_3 = var11_11;
                ** break;
            }
            case 4: {
                var4_4 = var1_1 == 384 ? 1 : 0;
                var3_3 = var4_4;
                var4_4 = var2_2 >= 192 && var2_2 <= 384 ? var6_6 : 0;
                var11_11 = var4_4;
                var4_4 = var3_3;
                var3_3 = var11_11;
                ** break;
            }
            case 3: {
                var4_4 = var1_1 == 256 ? 1 : 0;
                var3_3 = var4_4;
                var4_4 = var2_2 >= 96 && var2_2 <= 256 ? var7_7 : 0;
                var11_11 = var4_4;
                var4_4 = var3_3;
                var3_3 = var11_11;
                ** break;
            }
            case 2: {
                var4_4 = var1_1 == 160 ? 1 : 0;
                var3_3 = var4_4;
                var4_4 = var2_2 >= 96 && var2_2 <= 160 ? var8_8 : 0;
                var11_11 = var4_4;
                var4_4 = var3_3;
                var3_3 = var11_11;
                ** break;
            }
            case 1: {
                var4_4 = var1_1 == 128 ? 1 : 0;
                var3_3 = var4_4;
                var4_4 = var2_2 >= 96 && var2_2 <= 128 ? var9_9 : 0;
                var11_11 = var4_4;
                var4_4 = var3_3;
                var3_3 = var11_11;
                ** break;
            }
            case 0: 
        }
        var4_4 = var10_10;
        if (var1_1 != 128) {
            var4_4 = var10_10;
            if (var1_1 != 192) {
                var4_4 = var1_1 == 256 ? var10_10 : 0;
            }
        }
lbl108: // 11 sources:
        if (var4_4 == 0) {
            var0 = new StringBuilder();
            var0.append("Invalid key material keyLength: ");
            var0.append(var1_1);
            throw new IllegalArgumentException(var0.toString());
        }
        if (var3_3 != 0) {
            return;
        }
        var0 = new StringBuilder();
        var0.append("Invalid truncation keyLength: ");
        var0.append(var2_2);
        throw new IllegalArgumentException(var0.toString());
    }

    @VisibleForTesting
    public static boolean equals(IpSecAlgorithm ipSecAlgorithm, IpSecAlgorithm ipSecAlgorithm2) {
        boolean bl = true;
        boolean bl2 = true;
        if (ipSecAlgorithm != null && ipSecAlgorithm2 != null) {
            if (!ipSecAlgorithm.mName.equals(ipSecAlgorithm2.mName) || !Arrays.equals(ipSecAlgorithm.mKey, ipSecAlgorithm2.mKey) || ipSecAlgorithm.mTruncLenBits != ipSecAlgorithm2.mTruncLenBits) {
                bl2 = false;
            }
            return bl2;
        }
        bl2 = ipSecAlgorithm == ipSecAlgorithm2 ? bl : false;
        return bl2;
    }

    private static boolean isUnsafeBuild() {
        boolean bl = Build.IS_DEBUGGABLE && Build.IS_ENG;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getKey() {
        return (byte[])this.mKey.clone();
    }

    public String getName() {
        return this.mName;
    }

    public int getTruncationLengthBits() {
        return this.mTruncLenBits;
    }

    public boolean isAead() {
        return this.getName().equals(AUTH_CRYPT_AES_GCM);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isAuthentication() {
        String string2 = this.getName();
        switch (string2.hashCode()) {
            default: {
                return false;
            }
            case 2065384259: {
                if (!string2.equals(AUTH_HMAC_SHA1)) return false;
                return true;
            }
            case 759177996: {
                if (!string2.equals(AUTH_HMAC_MD5)) return false;
                return true;
            }
            case 559510590: {
                if (!string2.equals(AUTH_HMAC_SHA512)) return false;
                return true;
            }
            case 559457797: {
                if (!string2.equals(AUTH_HMAC_SHA384)) return false;
                return true;
            }
            case 559425185: {
                if (!string2.equals(AUTH_HMAC_SHA256)) return false;
                return true;
            }
        }
    }

    public boolean isEncryption() {
        return this.getName().equals(CRYPT_AES_CBC);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mName=");
        stringBuilder.append(this.mName);
        stringBuilder.append(", mKey=");
        String string2 = IpSecAlgorithm.isUnsafeBuild() ? HexDump.toHexString(this.mKey) : "<hidden>";
        stringBuilder.append(string2);
        stringBuilder.append(", mTruncLenBits=");
        stringBuilder.append(this.mTruncLenBits);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mName);
        parcel.writeByteArray(this.mKey);
        parcel.writeInt(this.mTruncLenBits);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AlgorithmName {
    }

}

