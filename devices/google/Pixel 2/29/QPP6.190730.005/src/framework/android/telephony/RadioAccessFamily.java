/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.RILConstants;

public class RadioAccessFamily
implements Parcelable {
    private static final int CDMA = 72;
    public static final Parcelable.Creator<RadioAccessFamily> CREATOR = new Parcelable.Creator<RadioAccessFamily>(){

        @Override
        public RadioAccessFamily createFromParcel(Parcel parcel) {
            return new RadioAccessFamily(parcel.readInt(), parcel.readInt());
        }

        public RadioAccessFamily[] newArray(int n) {
            return new RadioAccessFamily[n];
        }
    };
    private static final int EVDO = 10288;
    private static final int GSM = 32771;
    private static final int HS = 17280;
    private static final int LTE = 266240;
    private static final int NR = 524288;
    public static final int RAF_1xRTT = 64;
    public static final int RAF_EDGE = 2;
    public static final int RAF_EHRPD = 8192;
    public static final int RAF_EVDO_0 = 16;
    public static final int RAF_EVDO_A = 32;
    public static final int RAF_EVDO_B = 2048;
    public static final int RAF_GPRS = 1;
    public static final int RAF_GSM = 32768;
    public static final int RAF_HSDPA = 128;
    public static final int RAF_HSPA = 512;
    public static final int RAF_HSPAP = 16384;
    public static final int RAF_HSUPA = 256;
    public static final int RAF_IS95A = 8;
    public static final int RAF_IS95B = 8;
    public static final int RAF_LTE = 4096;
    public static final int RAF_LTE_CA = 262144;
    public static final int RAF_NR = 524288;
    public static final int RAF_TD_SCDMA = 65536;
    public static final int RAF_UMTS = 4;
    public static final int RAF_UNKNOWN = 0;
    private static final int WCDMA = 17284;
    private int mPhoneId;
    private int mRadioAccessFamily;

    @UnsupportedAppUsage
    public RadioAccessFamily(int n, int n2) {
        this.mPhoneId = n;
        this.mRadioAccessFamily = n2;
    }

    private static int getAdjustedRaf(int n) {
        block5 : {
            if ((n & 32771) > 0) {
                n = 32771 | n;
            }
            if ((n & 17284) > 0) {
                n |= 17284;
            }
            if ((n & 72) > 0) {
                n |= 72;
            }
            if ((n & 10288) > 0) {
                n |= 10288;
            }
            if ((n & 266240) > 0) {
                n = 266240 | n;
            }
            if ((n & 524288) <= 0) break block5;
            n = 524288 | n;
        }
        return n;
    }

    public static int getHighestRafCapability(int n) {
        if ((266240 & n) > 0) {
            return 3;
        }
        if ((n & 17284 | 27568) > 0) {
            return 2;
        }
        return (32771 | n & 72) > 0;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static int getNetworkTypeFromRaf(int n) {
        switch (RadioAccessFamily.getAdjustedRaf(n)) {
            default: {
                return RILConstants.PREFERRED_NETWORK_MODE;
            }
            case 916479: {
                return 33;
            }
            case 906119: {
                return 32;
            }
            case 888835: {
                return 30;
            }
            case 873348: {
                return 31;
            }
            case 856064: {
                return 29;
            }
            case 850943: {
                return 27;
            }
            case 840583: {
                return 26;
            }
            case 807812: {
                return 28;
            }
            case 800888: {
                return 25;
            }
            case 790528: {
                return 24;
            }
            case 524288: {
                return 23;
            }
            case 392191: {
                return 22;
            }
            case 381831: {
                return 20;
            }
            case 364547: {
                return 17;
            }
            case 349060: {
                return 19;
            }
            case 331776: {
                return 15;
            }
            case 326655: {
                return 10;
            }
            case 316295: {
                return 9;
            }
            case 283524: {
                return 12;
            }
            case 276600: {
                return 8;
            }
            case 266240: {
                return 11;
            }
            case 125951: {
                return 21;
            }
            case 115591: {
                return 18;
            }
            case 98307: {
                return 16;
            }
            case 82820: {
                return 14;
            }
            case 65536: {
                return 13;
            }
            case 60415: {
                return 7;
            }
            case 50055: {
                return 0;
            }
            case 32771: {
                return 1;
            }
            case 17284: {
                return 2;
            }
            case 10360: {
                return 4;
            }
            case 10288: {
                return 6;
            }
            case 72: 
        }
        return 5;
    }

    @UnsupportedAppUsage
    public static int getRafFromNetworkType(int n) {
        switch (n) {
            default: {
                return 0;
            }
            case 33: {
                return 916479;
            }
            case 32: {
                return 906119;
            }
            case 31: {
                return 873348;
            }
            case 30: {
                return 888835;
            }
            case 29: {
                return 856064;
            }
            case 28: {
                return 807812;
            }
            case 27: {
                return 850943;
            }
            case 26: {
                return 840583;
            }
            case 25: {
                return 800888;
            }
            case 24: {
                return 790528;
            }
            case 23: {
                return 524288;
            }
            case 22: {
                return 392191;
            }
            case 21: {
                return 125951;
            }
            case 20: {
                return 381831;
            }
            case 19: {
                return 349060;
            }
            case 18: {
                return 115591;
            }
            case 17: {
                return 364547;
            }
            case 16: {
                return 98307;
            }
            case 15: {
                return 331776;
            }
            case 14: {
                return 82820;
            }
            case 13: {
                return 65536;
            }
            case 12: {
                return 283524;
            }
            case 11: {
                return 266240;
            }
            case 10: {
                return 326655;
            }
            case 9: {
                return 316295;
            }
            case 8: {
                return 276600;
            }
            case 7: {
                return 60415;
            }
            case 6: {
                return 10288;
            }
            case 5: {
                return 72;
            }
            case 4: {
                return 10360;
            }
            case 3: {
                return 50055;
            }
            case 2: {
                return 17284;
            }
            case 1: {
                return 32771;
            }
            case 0: 
        }
        return 50055;
    }

    public static int rafTypeFromString(String arrstring) {
        arrstring = arrstring.toUpperCase().split("\\|");
        int n = 0;
        int n2 = arrstring.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = RadioAccessFamily.singleRafTypeFromString(arrstring[i].trim());
            if (n3 == 0) {
                return n3;
            }
            n |= n3;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int singleRafTypeFromString(String var0) {
        block50 : {
            switch (var0.hashCode()) {
                case 2056938943: {
                    if (!var0.equals("EVDO_B")) break;
                    var1_1 = 11;
                    break block50;
                }
                case 2056938942: {
                    if (!var0.equals("EVDO_A")) break;
                    var1_1 = 7;
                    break block50;
                }
                case 2056938925: {
                    if (!var0.equals("EVDO_0")) break;
                    var1_1 = 6;
                    break block50;
                }
                case 82410124: {
                    if (!var0.equals("WCDMA")) break;
                    var1_1 = 20;
                    break block50;
                }
                case 69946172: {
                    if (!var0.equals("IS95B")) break;
                    var1_1 = 4;
                    break block50;
                }
                case 69946171: {
                    if (!var0.equals("IS95A")) break;
                    var1_1 = 3;
                    break block50;
                }
                case 69050395: {
                    if (!var0.equals("HSUPA")) break;
                    var1_1 = 9;
                    break block50;
                }
                case 69045140: {
                    if (!var0.equals("HSPAP")) break;
                    var1_1 = 14;
                    break block50;
                }
                case 69034058: {
                    if (!var0.equals("HSDPA")) break;
                    var1_1 = 8;
                    break block50;
                }
                case 65949251: {
                    if (!var0.equals("EHRPD")) break;
                    var1_1 = 12;
                    break block50;
                }
                case 47955627: {
                    if (!var0.equals("1XRTT")) break;
                    var1_1 = 5;
                    break block50;
                }
                case 2608919: {
                    if (!var0.equals("UMTS")) break;
                    var1_1 = 2;
                    break block50;
                }
                case 2227260: {
                    if (!var0.equals("HSPA")) break;
                    var1_1 = 10;
                    break block50;
                }
                case 2194666: {
                    if (!var0.equals("GPRS")) break;
                    var1_1 = 0;
                    break block50;
                }
                case 2140412: {
                    if (!var0.equals("EVDO")) break;
                    var1_1 = 19;
                    break block50;
                }
                case 2123197: {
                    if (!var0.equals("EDGE")) break;
                    var1_1 = 1;
                    break block50;
                }
                case 2063797: {
                    if (!var0.equals("CDMA")) break;
                    var1_1 = 18;
                    break block50;
                }
                case 75709: {
                    if (!var0.equals("LTE")) break;
                    var1_1 = 13;
                    break block50;
                }
                case 70881: {
                    if (!var0.equals("GSM")) break;
                    var1_1 = 15;
                    break block50;
                }
                case 2500: {
                    if (!var0.equals("NR")) break;
                    var1_1 = 22;
                    break block50;
                }
                case 2315: {
                    if (!var0.equals("HS")) break;
                    var1_1 = 17;
                    break block50;
                }
                case -908593671: {
                    if (!var0.equals("TD_SCDMA")) break;
                    var1_1 = 16;
                    break block50;
                }
                case -2039427040: {
                    if (!var0.equals("LTE_CA")) break;
                    var1_1 = 21;
                    break block50;
                }
            }
            ** break;
lbl95: // 1 sources:
            var1_1 = -1;
        }
        switch (var1_1) {
            default: {
                return 0;
            }
            case 22: {
                return 524288;
            }
            case 21: {
                return 262144;
            }
            case 20: {
                return 17284;
            }
            case 19: {
                return 10288;
            }
            case 18: {
                return 72;
            }
            case 17: {
                return 17280;
            }
            case 16: {
                return 65536;
            }
            case 15: {
                return 32768;
            }
            case 14: {
                return 16384;
            }
            case 13: {
                return 4096;
            }
            case 12: {
                return 8192;
            }
            case 11: {
                return 2048;
            }
            case 10: {
                return 512;
            }
            case 9: {
                return 256;
            }
            case 8: {
                return 128;
            }
            case 7: {
                return 32;
            }
            case 6: {
                return 16;
            }
            case 5: {
                return 64;
            }
            case 4: {
                return 8;
            }
            case 3: {
                return 8;
            }
            case 2: {
                return 4;
            }
            case 1: {
                return 2;
            }
            case 0: 
        }
        return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public int getPhoneId() {
        return this.mPhoneId;
    }

    @UnsupportedAppUsage
    public int getRadioAccessFamily() {
        return this.mRadioAccessFamily;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ mPhoneId = ");
        stringBuilder.append(this.mPhoneId);
        stringBuilder.append(", mRadioAccessFamily = ");
        stringBuilder.append(this.mRadioAccessFamily);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mPhoneId);
        parcel.writeInt(this.mRadioAccessFamily);
    }

}

