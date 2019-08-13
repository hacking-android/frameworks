/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ServiceState;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ApnSetting
implements Parcelable {
    private static final Map<Integer, String> APN_TYPE_INT_MAP;
    private static final Map<String, Integer> APN_TYPE_STRING_MAP;
    public static final int AUTH_TYPE_CHAP = 2;
    public static final int AUTH_TYPE_NONE = 0;
    public static final int AUTH_TYPE_PAP = 1;
    public static final int AUTH_TYPE_PAP_OR_CHAP = 3;
    public static final Parcelable.Creator<ApnSetting> CREATOR;
    private static final String LOG_TAG = "ApnSetting";
    public static final int MVNO_TYPE_GID = 2;
    public static final int MVNO_TYPE_ICCID = 3;
    public static final int MVNO_TYPE_IMSI = 1;
    private static final Map<Integer, String> MVNO_TYPE_INT_MAP;
    public static final int MVNO_TYPE_SPN = 0;
    private static final Map<String, Integer> MVNO_TYPE_STRING_MAP;
    private static final Map<Integer, String> PROTOCOL_INT_MAP;
    public static final int PROTOCOL_IP = 0;
    public static final int PROTOCOL_IPV4V6 = 2;
    public static final int PROTOCOL_IPV6 = 1;
    public static final int PROTOCOL_NON_IP = 4;
    public static final int PROTOCOL_PPP = 3;
    private static final Map<String, Integer> PROTOCOL_STRING_MAP;
    public static final int PROTOCOL_UNSTRUCTURED = 5;
    public static final int TYPE_ALL = 255;
    public static final int TYPE_CBS = 128;
    public static final int TYPE_DEFAULT = 17;
    public static final int TYPE_DUN = 8;
    public static final int TYPE_EMERGENCY = 512;
    public static final int TYPE_FOTA = 32;
    public static final int TYPE_HIPRI = 16;
    public static final int TYPE_IA = 256;
    public static final int TYPE_IMS = 64;
    public static final int TYPE_MCX = 1024;
    public static final int TYPE_MMS = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SUPL = 4;
    public static final int UNSET_MTU = 0;
    private static final int UNSPECIFIED_INT = -1;
    private static final String UNSPECIFIED_STRING = "";
    private static final String V2_FORMAT_REGEX = "^\\[ApnSettingV2\\]\\s*";
    private static final String V3_FORMAT_REGEX = "^\\[ApnSettingV3\\]\\s*";
    private static final String V4_FORMAT_REGEX = "^\\[ApnSettingV4\\]\\s*";
    private static final String V5_FORMAT_REGEX = "^\\[ApnSettingV5\\]\\s*";
    private static final String V6_FORMAT_REGEX = "^\\[ApnSettingV6\\]\\s*";
    private static final String V7_FORMAT_REGEX = "^\\[ApnSettingV7\\]\\s*";
    private static final boolean VDBG = false;
    private final String mApnName;
    private final int mApnSetId;
    private final int mApnTypeBitmask;
    private final int mAuthType;
    private final boolean mCarrierEnabled;
    private final int mCarrierId;
    private final String mEntryName;
    private final int mId;
    private final int mMaxConns;
    private final int mMaxConnsTime;
    private final String mMmsProxyAddress;
    private final int mMmsProxyPort;
    private final Uri mMmsc;
    private final int mMtu;
    private final String mMvnoMatchData;
    private final int mMvnoType;
    private final int mNetworkTypeBitmask;
    private final String mOperatorNumeric;
    private final String mPassword;
    private boolean mPermanentFailed = false;
    private final boolean mPersistent;
    private final int mProfileId;
    private final int mProtocol;
    private final String mProxyAddress;
    private final int mProxyPort;
    private final int mRoamingProtocol;
    private final int mSkip464Xlat;
    private final String mUser;
    private final int mWaitTime;

    static {
        APN_TYPE_STRING_MAP = new ArrayMap<String, Integer>();
        APN_TYPE_STRING_MAP.put("*", 255);
        Object object = APN_TYPE_STRING_MAP;
        Integer n = 17;
        object.put((String)"default", (Integer)n);
        Object object2 = APN_TYPE_STRING_MAP;
        object = 2;
        object2.put((String)"mms", (Integer)object);
        Object object3 = APN_TYPE_STRING_MAP;
        object2 = 4;
        object3.put((String)"supl", (Integer)object2);
        Map<String, Integer> map = APN_TYPE_STRING_MAP;
        object3 = 8;
        map.put("dun", (Integer)object3);
        APN_TYPE_STRING_MAP.put("hipri", 16);
        APN_TYPE_STRING_MAP.put("fota", 32);
        APN_TYPE_STRING_MAP.put("ims", 64);
        APN_TYPE_STRING_MAP.put("cbs", 128);
        APN_TYPE_STRING_MAP.put("ia", 256);
        APN_TYPE_STRING_MAP.put("emergency", 512);
        APN_TYPE_STRING_MAP.put("mcx", 1024);
        APN_TYPE_INT_MAP = new ArrayMap<Integer, String>();
        APN_TYPE_INT_MAP.put(n, "default");
        APN_TYPE_INT_MAP.put((Integer)object, "mms");
        APN_TYPE_INT_MAP.put((Integer)object2, "supl");
        APN_TYPE_INT_MAP.put((Integer)object3, "dun");
        APN_TYPE_INT_MAP.put(16, "hipri");
        APN_TYPE_INT_MAP.put(32, "fota");
        APN_TYPE_INT_MAP.put(64, "ims");
        APN_TYPE_INT_MAP.put(128, "cbs");
        APN_TYPE_INT_MAP.put(256, "ia");
        APN_TYPE_INT_MAP.put(512, "emergency");
        APN_TYPE_INT_MAP.put(1024, "mcx");
        PROTOCOL_STRING_MAP = new ArrayMap<String, Integer>();
        object3 = PROTOCOL_STRING_MAP;
        n = 0;
        object3.put((String)"IP", (Integer)n);
        map = PROTOCOL_STRING_MAP;
        object3 = 1;
        map.put("IPV6", (Integer)object3);
        PROTOCOL_STRING_MAP.put("IPV4V6", (Integer)object);
        map = PROTOCOL_STRING_MAP;
        Integer n2 = 3;
        map.put("PPP", n2);
        PROTOCOL_STRING_MAP.put("NON-IP", (Integer)object2);
        PROTOCOL_STRING_MAP.put("UNSTRUCTURED", 5);
        PROTOCOL_INT_MAP = new ArrayMap<Integer, String>();
        PROTOCOL_INT_MAP.put(n, "IP");
        PROTOCOL_INT_MAP.put((Integer)object3, "IPV6");
        PROTOCOL_INT_MAP.put((Integer)object, "IPV4V6");
        PROTOCOL_INT_MAP.put(n2, "PPP");
        PROTOCOL_INT_MAP.put((Integer)object2, "NON-IP");
        PROTOCOL_INT_MAP.put(5, "UNSTRUCTURED");
        MVNO_TYPE_STRING_MAP = new ArrayMap<String, Integer>();
        MVNO_TYPE_STRING_MAP.put("spn", n);
        MVNO_TYPE_STRING_MAP.put("imsi", (Integer)object3);
        MVNO_TYPE_STRING_MAP.put("gid", (Integer)object);
        MVNO_TYPE_STRING_MAP.put("iccid", n2);
        MVNO_TYPE_INT_MAP = new ArrayMap<Integer, String>();
        MVNO_TYPE_INT_MAP.put(n, "spn");
        MVNO_TYPE_INT_MAP.put((Integer)object3, "imsi");
        MVNO_TYPE_INT_MAP.put((Integer)object, "gid");
        MVNO_TYPE_INT_MAP.put(n2, "iccid");
        CREATOR = new Parcelable.Creator<ApnSetting>(){

            @Override
            public ApnSetting createFromParcel(Parcel parcel) {
                return ApnSetting.readFromParcel(parcel);
            }

            public ApnSetting[] newArray(int n) {
                return new ApnSetting[n];
            }
        };
    }

    private ApnSetting(Builder builder) {
        this.mEntryName = builder.mEntryName;
        this.mApnName = builder.mApnName;
        this.mProxyAddress = builder.mProxyAddress;
        this.mProxyPort = builder.mProxyPort;
        this.mMmsc = builder.mMmsc;
        this.mMmsProxyAddress = builder.mMmsProxyAddress;
        this.mMmsProxyPort = builder.mMmsProxyPort;
        this.mUser = builder.mUser;
        this.mPassword = builder.mPassword;
        this.mAuthType = builder.mAuthType;
        this.mApnTypeBitmask = builder.mApnTypeBitmask;
        this.mId = builder.mId;
        this.mOperatorNumeric = builder.mOperatorNumeric;
        this.mProtocol = builder.mProtocol;
        this.mRoamingProtocol = builder.mRoamingProtocol;
        this.mMtu = builder.mMtu;
        this.mCarrierEnabled = builder.mCarrierEnabled;
        this.mNetworkTypeBitmask = builder.mNetworkTypeBitmask;
        this.mProfileId = builder.mProfileId;
        this.mPersistent = builder.mModemCognitive;
        this.mMaxConns = builder.mMaxConns;
        this.mWaitTime = builder.mWaitTime;
        this.mMaxConnsTime = builder.mMaxConnsTime;
        this.mMvnoType = builder.mMvnoType;
        this.mMvnoMatchData = builder.mMvnoMatchData;
        this.mApnSetId = builder.mApnSetId;
        this.mCarrierId = builder.mCarrierId;
        this.mSkip464Xlat = builder.mSkip464Xlat;
    }

    private static Uri UriFromString(String object) {
        object = TextUtils.isEmpty((CharSequence)object) ? null : Uri.parse((String)object);
        return object;
    }

    private static String UriToString(Uri object) {
        object = object == null ? null : ((Uri)object).toString();
        return object;
    }

    public static List<ApnSetting> arrayFromString(String object) {
        ArrayList<ApnSetting> arrayList = new ArrayList<ApnSetting>();
        if (TextUtils.isEmpty((CharSequence)object)) {
            return arrayList;
        }
        String[] arrstring = ((String)object).split("\\s*;\\s*");
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            object = ApnSetting.fromString(arrstring[i]);
            if (object == null) continue;
            arrayList.add((ApnSetting)object);
        }
        return arrayList;
    }

    public static ApnSetting fromString(String arrobject) {
        Object object;
        int n;
        int n2;
        String string2;
        int n3;
        boolean bl;
        int n4;
        int n5;
        int n6;
        int n7;
        Object object2;
        int n8;
        int n9;
        boolean bl2;
        int n10;
        Object object3;
        String string3;
        String[] arrstring;
        int n11;
        block34 : {
            if (arrobject == null) {
                return null;
            }
            if (arrobject.matches("^\\[ApnSettingV7\\]\\s*.*")) {
                arrobject = arrobject.replaceFirst(V7_FORMAT_REGEX, UNSPECIFIED_STRING);
                n6 = 7;
            } else if (arrobject.matches("^\\[ApnSettingV6\\]\\s*.*")) {
                arrobject = arrobject.replaceFirst(V6_FORMAT_REGEX, UNSPECIFIED_STRING);
                n6 = 6;
            } else if (arrobject.matches("^\\[ApnSettingV5\\]\\s*.*")) {
                arrobject = arrobject.replaceFirst(V5_FORMAT_REGEX, UNSPECIFIED_STRING);
                n6 = 5;
            } else if (arrobject.matches("^\\[ApnSettingV4\\]\\s*.*")) {
                arrobject = arrobject.replaceFirst(V4_FORMAT_REGEX, UNSPECIFIED_STRING);
                n6 = 4;
            } else if (arrobject.matches("^\\[ApnSettingV3\\]\\s*.*")) {
                arrobject = arrobject.replaceFirst(V3_FORMAT_REGEX, UNSPECIFIED_STRING);
                n6 = 3;
            } else if (arrobject.matches("^\\[ApnSettingV2\\]\\s*.*")) {
                arrobject = arrobject.replaceFirst(V2_FORMAT_REGEX, UNSPECIFIED_STRING);
                n6 = 2;
            } else {
                n6 = 1;
            }
            arrstring = arrobject.split("\\s*,\\s*", -1);
            if (arrstring.length < 14) {
                return null;
            }
            try {
                n3 = Integer.parseInt(arrstring[12]);
            }
            catch (NumberFormatException numberFormatException) {
                n3 = 0;
            }
            n4 = 0;
            int n12 = 0;
            bl2 = false;
            n = 0;
            n11 = 0;
            n9 = 0;
            n5 = 0;
            n10 = 0;
            n2 = 0;
            string3 = UNSPECIFIED_STRING;
            arrobject = UNSPECIFIED_STRING;
            int n13 = 0;
            n7 = -1;
            if (n6 == 1) {
                arrobject = new String[arrstring.length - 13];
                System.arraycopy(arrstring, 13, arrobject, 0, arrstring.length - 13);
                object = PROTOCOL_INT_MAP.get(0);
                string2 = PROTOCOL_INT_MAP.get(0);
                bl2 = true;
                n8 = 0;
                bl = false;
                n7 = 0;
                n6 = 0;
                n = 0;
                n9 = 0;
                string3 = UNSPECIFIED_STRING;
                object2 = UNSPECIFIED_STRING;
                n11 = 0;
                n5 = -1;
                n10 = -1;
                n4 = 0;
                n2 = 0;
            } else {
                if (arrstring.length < 18) {
                    return null;
                }
                object2 = arrstring[13].split("\\s*\\|\\s*");
                string2 = arrstring[14];
                object = arrstring[15];
                bl = Boolean.parseBoolean(arrstring[16]);
                n8 = ServiceState.getBitmaskFromString(arrstring[17]);
                n6 = n9;
                n9 = n10;
                if (arrstring.length > 22) {
                    bl2 = Boolean.parseBoolean(arrstring[19]);
                    n4 = n12;
                    n = n11;
                    n6 = n5;
                    n4 = n9 = Integer.parseInt(arrstring[18]);
                    n = n11;
                    n6 = n5;
                    n11 = Integer.parseInt(arrstring[20]);
                    n4 = n9;
                    n = n11;
                    n6 = n5;
                    n5 = Integer.parseInt(arrstring[21]);
                    n4 = n9;
                    n = n11;
                    n6 = n5;
                    try {
                        n10 = n12 = Integer.parseInt(arrstring[22]);
                        n4 = n9;
                        n = n11;
                        n6 = n5;
                        n9 = n10;
                    }
                    catch (NumberFormatException numberFormatException) {
                        n9 = n10;
                    }
                }
                n11 = n2;
                if (arrstring.length > 23) {
                    try {
                        n11 = Integer.parseInt(arrstring[23]);
                    }
                    catch (NumberFormatException numberFormatException) {
                        n11 = n2;
                    }
                }
                if (arrstring.length > 25) {
                    string3 = arrstring[24];
                    arrobject = arrstring[25];
                }
                n2 = arrstring.length > 26 ? ServiceState.getBitmaskFromString(arrstring[26]) : 0;
                n5 = n13;
                if (arrstring.length > 27) {
                    n5 = Integer.parseInt(arrstring[27]);
                }
                n10 = n7;
                if (arrstring.length > 28) {
                    n10 = Integer.parseInt(arrstring[28]);
                }
                if (arrstring.length > 29) {
                    try {
                        n13 = Integer.parseInt(arrstring[29]);
                        n7 = n4;
                        boolean bl3 = bl2;
                        n12 = n;
                        n = n9;
                        n9 = n11;
                        Object[] arrobject2 = arrobject;
                        n11 = n5;
                        n5 = n10;
                        object3 = string2;
                        string2 = object;
                        n4 = n8;
                        bl2 = bl;
                        arrobject = object2;
                        n10 = n13;
                        object2 = arrobject2;
                        object = object3;
                        n8 = n7;
                        bl = bl3;
                        n7 = n12;
                        break block34;
                    }
                    catch (NumberFormatException numberFormatException) {
                        // empty catch block
                    }
                }
                n13 = n4;
                n7 = n;
                n = n9;
                n9 = n11;
                n11 = n5;
                n5 = n10;
                n10 = -1;
                String string4 = string2;
                n4 = n8;
                boolean bl4 = bl;
                object3 = object2;
                bl = bl2;
                n8 = n13;
                string2 = object;
                object = string4;
                object2 = arrobject;
                bl2 = bl4;
                arrobject = object3;
            }
        }
        n4 = n2 == 0 ? ServiceState.convertBearerBitmaskToNetworkTypeBitmask(n4) : n2;
        object3 = new StringBuilder();
        object3.append(arrstring[10]);
        object3.append(arrstring[11]);
        return ApnSetting.makeApnSetting(-1, object3.toString(), arrstring[0], arrstring[1], arrstring[2], ApnSetting.portFromString(arrstring[3]), ApnSetting.UriFromString(arrstring[7]), arrstring[8], ApnSetting.portFromString(arrstring[9]), arrstring[4], arrstring[5], n3, ApnSetting.getApnTypesBitmaskFromString(TextUtils.join((CharSequence)",", arrobject)), ApnSetting.getProtocolIntFromString((String)object), ApnSetting.getProtocolIntFromString(string2), bl2, n4, n8, bl, n7, n6, n, n9, ApnSetting.getMvnoTypeIntFromString(string3), (String)object2, n11, n5, n10);
    }

    public static String getApnTypeString(int n) {
        String string2;
        block1 : {
            if (n == 255) {
                return "*";
            }
            string2 = APN_TYPE_INT_MAP.get(n);
            if (string2 != null) break block1;
            string2 = "Unknown";
        }
        return string2;
    }

    public static int getApnTypesBitmaskFromString(String arrstring) {
        if (TextUtils.isEmpty((CharSequence)arrstring)) {
            return 255;
        }
        int n = 0;
        for (String string2 : arrstring.split(",")) {
            Integer object = APN_TYPE_STRING_MAP.get(string2.toLowerCase());
            int n2 = n;
            if (object != null) {
                n2 = n | object;
            }
            n = n2;
        }
        return n;
    }

    public static String getApnTypesStringFromBitmask(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Integer n2 : APN_TYPE_INT_MAP.keySet()) {
            if ((n2 & n) != n2) continue;
            arrayList.add(APN_TYPE_INT_MAP.get(n2));
        }
        return TextUtils.join((CharSequence)",", arrayList);
    }

    public static int getMvnoTypeIntFromString(String object) {
        if (!TextUtils.isEmpty((CharSequence)object)) {
            object = ((String)object).toLowerCase();
        }
        object = MVNO_TYPE_STRING_MAP.get(object);
        int n = object == null ? -1 : (Integer)object;
        return n;
    }

    public static String getMvnoTypeStringFromInt(int n) {
        String string2;
        block0 : {
            string2 = MVNO_TYPE_INT_MAP.get(n);
            if (string2 != null) break block0;
            string2 = UNSPECIFIED_STRING;
        }
        return string2;
    }

    public static int getProtocolIntFromString(String object) {
        int n = (object = PROTOCOL_STRING_MAP.get(object)) == null ? -1 : (Integer)object;
        return n;
    }

    public static String getProtocolStringFromInt(int n) {
        String string2;
        block0 : {
            string2 = PROTOCOL_INT_MAP.get(n);
            if (string2 != null) break block0;
            string2 = UNSPECIFIED_STRING;
        }
        return string2;
    }

    private boolean hasApnType(int n) {
        boolean bl = (this.mApnTypeBitmask & n) == n;
        return bl;
    }

    public static InetAddress inetAddressFromString(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        try {
            object = InetAddress.getByName((String)object);
            return object;
        }
        catch (UnknownHostException unknownHostException) {
            Log.e(LOG_TAG, "Can't parse InetAddress from string: unknown host.");
            return null;
        }
    }

    public static String inetAddressToString(InetAddress object) {
        block3 : {
            if (object == null) {
                return null;
            }
            String string2 = ((InetAddress)object).toString();
            if (TextUtils.isEmpty(string2)) {
                return null;
            }
            object = string2.substring(0, string2.indexOf("/"));
            string2 = string2.substring(string2.indexOf("/") + 1);
            if (TextUtils.isEmpty((CharSequence)object) && TextUtils.isEmpty(string2)) {
                return null;
            }
            if (!TextUtils.isEmpty((CharSequence)object)) break block3;
            object = string2;
        }
        return object;
    }

    public static ApnSetting makeApnSetting(int n, String string2, String string3, String string4, String string5, int n2, Uri uri, String string6, int n3, String string7, String string8, int n4, int n5, int n6, int n7, boolean bl, int n8, int n9, boolean bl2, int n10, int n11, int n12, int n13, int n14, String string9) {
        return ApnSetting.makeApnSetting(n, string2, string3, string4, string5, n2, uri, string6, n3, string7, string8, n4, n5, n6, n7, bl, n8, n9, bl2, n10, n11, n12, n13, n14, string9, 0, -1, -1);
    }

    public static ApnSetting makeApnSetting(int n, String string2, String string3, String string4, String string5, int n2, Uri uri, String string6, int n3, String string7, String string8, int n4, int n5, int n6, int n7, boolean bl, int n8, int n9, boolean bl2, int n10, int n11, int n12, int n13, int n14, String string9, int n15, int n16, int n17) {
        return new Builder().setId(n).setOperatorNumeric(string2).setEntryName(string3).setApnName(string4).setProxyAddress(string5).setProxyPort(n2).setMmsc(uri).setMmsProxyAddress(string6).setMmsProxyPort(n3).setUser(string7).setPassword(string8).setAuthType(n4).setApnTypeBitmask(n5).setProtocol(n6).setRoamingProtocol(n7).setCarrierEnabled(bl).setNetworkTypeBitmask(n8).setProfileId(n9).setModemCognitive(bl2).setMaxConns(n10).setWaitTime(n11).setMaxConnsTime(n12).setMtu(n13).setMvnoType(n14).setMvnoMatchData(string9).setApnSetId(n15).setCarrierId(n16).setSkip464Xlat(n17).buildWithoutCheck();
    }

    public static ApnSetting makeApnSetting(Cursor cursor) {
        int n = ApnSetting.getApnTypesBitmaskFromString(cursor.getString(cursor.getColumnIndexOrThrow("type")));
        int n2 = cursor.getInt(cursor.getColumnIndexOrThrow("network_type_bitmask"));
        if (n2 == 0) {
            n2 = cursor.getInt(cursor.getColumnIndexOrThrow("bearer_bitmask"));
            n2 = ServiceState.convertBearerBitmaskToNetworkTypeBitmask(n2);
        }
        int n3 = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String string2 = cursor.getString(cursor.getColumnIndexOrThrow("numeric"));
        String string3 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String string4 = cursor.getString(cursor.getColumnIndexOrThrow("apn"));
        String string5 = cursor.getString(cursor.getColumnIndexOrThrow("proxy"));
        int n4 = ApnSetting.portFromString(cursor.getString(cursor.getColumnIndexOrThrow("port")));
        Uri uri = ApnSetting.UriFromString(cursor.getString(cursor.getColumnIndexOrThrow("mmsc")));
        String string6 = cursor.getString(cursor.getColumnIndexOrThrow("mmsproxy"));
        int n5 = ApnSetting.portFromString(cursor.getString(cursor.getColumnIndexOrThrow("mmsport")));
        String string7 = cursor.getString(cursor.getColumnIndexOrThrow("user"));
        String string8 = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        int n6 = cursor.getInt(cursor.getColumnIndexOrThrow("authtype"));
        int n7 = ApnSetting.getProtocolIntFromString(cursor.getString(cursor.getColumnIndexOrThrow("protocol")));
        int n8 = ApnSetting.getProtocolIntFromString(cursor.getString(cursor.getColumnIndexOrThrow("roaming_protocol")));
        boolean bl = cursor.getInt(cursor.getColumnIndexOrThrow("carrier_enabled")) == 1;
        int n9 = cursor.getInt(cursor.getColumnIndexOrThrow("profile_id"));
        boolean bl2 = cursor.getInt(cursor.getColumnIndexOrThrow("modem_cognitive")) == 1;
        return ApnSetting.makeApnSetting(n3, string2, string3, string4, string5, n4, uri, string6, n5, string7, string8, n6, n, n7, n8, bl, n2, n9, bl2, cursor.getInt(cursor.getColumnIndexOrThrow("max_conns")), cursor.getInt(cursor.getColumnIndexOrThrow("wait_time")), cursor.getInt(cursor.getColumnIndexOrThrow("max_conns_time")), cursor.getInt(cursor.getColumnIndexOrThrow("mtu")), ApnSetting.getMvnoTypeIntFromString(cursor.getString(cursor.getColumnIndexOrThrow("mvno_type"))), cursor.getString(cursor.getColumnIndexOrThrow("mvno_match_data")), cursor.getInt(cursor.getColumnIndexOrThrow("apn_set_id")), cursor.getInt(cursor.getColumnIndexOrThrow("carrier_id")), cursor.getInt(cursor.getColumnIndexOrThrow("skip_464xlat")));
    }

    public static ApnSetting makeApnSetting(ApnSetting apnSetting) {
        return ApnSetting.makeApnSetting(apnSetting.mId, apnSetting.mOperatorNumeric, apnSetting.mEntryName, apnSetting.mApnName, apnSetting.mProxyAddress, apnSetting.mProxyPort, apnSetting.mMmsc, apnSetting.mMmsProxyAddress, apnSetting.mMmsProxyPort, apnSetting.mUser, apnSetting.mPassword, apnSetting.mAuthType, apnSetting.mApnTypeBitmask, apnSetting.mProtocol, apnSetting.mRoamingProtocol, apnSetting.mCarrierEnabled, apnSetting.mNetworkTypeBitmask, apnSetting.mProfileId, apnSetting.mPersistent, apnSetting.mMaxConns, apnSetting.mWaitTime, apnSetting.mMaxConnsTime, apnSetting.mMtu, apnSetting.mMvnoType, apnSetting.mMvnoMatchData, apnSetting.mApnSetId, apnSetting.mCarrierId, apnSetting.mSkip464Xlat);
    }

    private String nullToEmpty(String string2) {
        block0 : {
            if (string2 != null) break block0;
            string2 = UNSPECIFIED_STRING;
        }
        return string2;
    }

    private static int portFromString(String string2) {
        int n;
        int n2 = n = -1;
        if (!TextUtils.isEmpty(string2)) {
            try {
                n2 = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                Log.e(LOG_TAG, "Can't parse port from String");
                n2 = n;
            }
        }
        return n2;
    }

    private static String portToString(int n) {
        String string2 = n == -1 ? null : Integer.toString(n);
        return string2;
    }

    private static ApnSetting readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        String string2 = parcel.readString();
        String string3 = parcel.readString();
        String string4 = parcel.readString();
        String string5 = parcel.readString();
        int n2 = parcel.readInt();
        Uri uri = (Uri)parcel.readValue(Uri.class.getClassLoader());
        String string6 = parcel.readString();
        int n3 = parcel.readInt();
        String string7 = parcel.readString();
        String string8 = parcel.readString();
        int n4 = parcel.readInt();
        int n5 = parcel.readInt();
        int n6 = parcel.readInt();
        int n7 = parcel.readInt();
        boolean bl = parcel.readBoolean();
        int n8 = parcel.readInt();
        return ApnSetting.makeApnSetting(n, string2, string3, string4, string5, n2, uri, string6, n3, string7, string8, n4, n5, n6, n7, bl, parcel.readInt(), 0, false, 0, 0, 0, 0, n8, null, parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    private boolean typeSameAny(ApnSetting apnSetting, ApnSetting apnSetting2) {
        return (apnSetting.mApnTypeBitmask & apnSetting2.mApnTypeBitmask) != 0;
    }

    private boolean xorEquals(Object object, Object object2) {
        boolean bl = object == null || object2 == null || object.equals(object2);
        return bl;
    }

    private boolean xorEqualsInt(int n, int n2) {
        boolean bl = n == -1 || n2 == -1 || Objects.equals(n, n2);
        return bl;
    }

    public boolean canHandleType(int n) {
        if (!this.mCarrierEnabled) {
            return false;
        }
        return this.hasApnType(n);
    }

    public boolean canSupportNetworkType(int n) {
        if (n == 16 && ((long)this.mNetworkTypeBitmask & 3L) != 0L) {
            return true;
        }
        return ServiceState.bitmaskHasTech(this.mNetworkTypeBitmask, n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof ApnSetting;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (ApnSetting)object;
            if (!this.mEntryName.equals(((ApnSetting)object).mEntryName) || !Objects.equals(this.mId, ((ApnSetting)object).mId) || !Objects.equals(this.mOperatorNumeric, ((ApnSetting)object).mOperatorNumeric) || !Objects.equals(this.mApnName, ((ApnSetting)object).mApnName) || !Objects.equals(this.mProxyAddress, ((ApnSetting)object).mProxyAddress) || !Objects.equals(this.mMmsc, ((ApnSetting)object).mMmsc) || !Objects.equals(this.mMmsProxyAddress, ((ApnSetting)object).mMmsProxyAddress) || !Objects.equals(this.mMmsProxyPort, ((ApnSetting)object).mMmsProxyPort) || !Objects.equals(this.mProxyPort, ((ApnSetting)object).mProxyPort) || !Objects.equals(this.mUser, ((ApnSetting)object).mUser) || !Objects.equals(this.mPassword, ((ApnSetting)object).mPassword) || !Objects.equals(this.mAuthType, ((ApnSetting)object).mAuthType) || !Objects.equals(this.mApnTypeBitmask, ((ApnSetting)object).mApnTypeBitmask) || !Objects.equals(this.mProtocol, ((ApnSetting)object).mProtocol) || !Objects.equals(this.mRoamingProtocol, ((ApnSetting)object).mRoamingProtocol) || !Objects.equals(this.mCarrierEnabled, ((ApnSetting)object).mCarrierEnabled) || !Objects.equals(this.mProfileId, ((ApnSetting)object).mProfileId) || !Objects.equals(this.mPersistent, ((ApnSetting)object).mPersistent) || !Objects.equals(this.mMaxConns, ((ApnSetting)object).mMaxConns) || !Objects.equals(this.mWaitTime, ((ApnSetting)object).mWaitTime) || !Objects.equals(this.mMaxConnsTime, ((ApnSetting)object).mMaxConnsTime) || !Objects.equals(this.mMtu, ((ApnSetting)object).mMtu) || !Objects.equals(this.mMvnoType, ((ApnSetting)object).mMvnoType) || !Objects.equals(this.mMvnoMatchData, ((ApnSetting)object).mMvnoMatchData) || !Objects.equals(this.mNetworkTypeBitmask, ((ApnSetting)object).mNetworkTypeBitmask) || !Objects.equals(this.mApnSetId, ((ApnSetting)object).mApnSetId) || !Objects.equals(this.mCarrierId, ((ApnSetting)object).mCarrierId) || !Objects.equals(this.mSkip464Xlat, ((ApnSetting)object).mSkip464Xlat)) break block1;
            bl = true;
        }
        return bl;
    }

    public boolean equals(Object object, boolean bl) {
        boolean bl2 = object instanceof ApnSetting;
        boolean bl3 = false;
        if (!bl2) {
            return false;
        }
        object = (ApnSetting)object;
        bl = !(!this.mEntryName.equals(((ApnSetting)object).mEntryName) || !Objects.equals(this.mOperatorNumeric, ((ApnSetting)object).mOperatorNumeric) || !Objects.equals(this.mApnName, ((ApnSetting)object).mApnName) || !Objects.equals(this.mProxyAddress, ((ApnSetting)object).mProxyAddress) || !Objects.equals(this.mMmsc, ((ApnSetting)object).mMmsc) || !Objects.equals(this.mMmsProxyAddress, ((ApnSetting)object).mMmsProxyAddress) || !Objects.equals(this.mMmsProxyPort, ((ApnSetting)object).mMmsProxyPort) || !Objects.equals(this.mProxyPort, ((ApnSetting)object).mProxyPort) || !Objects.equals(this.mUser, ((ApnSetting)object).mUser) || !Objects.equals(this.mPassword, ((ApnSetting)object).mPassword) || !Objects.equals(this.mAuthType, ((ApnSetting)object).mAuthType) || !Objects.equals(this.mApnTypeBitmask, ((ApnSetting)object).mApnTypeBitmask) || !bl && !Objects.equals(this.mProtocol, ((ApnSetting)object).mProtocol) || bl && !Objects.equals(this.mRoamingProtocol, ((ApnSetting)object).mRoamingProtocol) || !Objects.equals(this.mCarrierEnabled, ((ApnSetting)object).mCarrierEnabled) || !Objects.equals(this.mProfileId, ((ApnSetting)object).mProfileId) || !Objects.equals(this.mPersistent, ((ApnSetting)object).mPersistent) || !Objects.equals(this.mMaxConns, ((ApnSetting)object).mMaxConns) || !Objects.equals(this.mWaitTime, ((ApnSetting)object).mWaitTime) || !Objects.equals(this.mMaxConnsTime, ((ApnSetting)object).mMaxConnsTime) || !Objects.equals(this.mMtu, ((ApnSetting)object).mMtu) || !Objects.equals(this.mMvnoType, ((ApnSetting)object).mMvnoType) || !Objects.equals(this.mMvnoMatchData, ((ApnSetting)object).mMvnoMatchData) || !Objects.equals(this.mApnSetId, ((ApnSetting)object).mApnSetId) || !Objects.equals(this.mCarrierId, ((ApnSetting)object).mCarrierId) || !Objects.equals(this.mSkip464Xlat, ((ApnSetting)object).mSkip464Xlat)) ? true : bl3;
        return bl;
    }

    public String getApnName() {
        return this.mApnName;
    }

    public int getApnSetId() {
        return this.mApnSetId;
    }

    public int getApnTypeBitmask() {
        return this.mApnTypeBitmask;
    }

    public List<Integer> getApnTypes() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Integer n : APN_TYPE_INT_MAP.keySet()) {
            if ((this.mApnTypeBitmask & n) != n) continue;
            arrayList.add(n);
        }
        return arrayList;
    }

    public int getAuthType() {
        return this.mAuthType;
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public String getEntryName() {
        return this.mEntryName;
    }

    public int getId() {
        return this.mId;
    }

    public int getMaxConns() {
        return this.mMaxConns;
    }

    public int getMaxConnsTime() {
        return this.mMaxConnsTime;
    }

    @Deprecated
    public InetAddress getMmsProxyAddress() {
        return ApnSetting.inetAddressFromString(this.mMmsProxyAddress);
    }

    public String getMmsProxyAddressAsString() {
        return this.mMmsProxyAddress;
    }

    public int getMmsProxyPort() {
        return this.mMmsProxyPort;
    }

    public Uri getMmsc() {
        return this.mMmsc;
    }

    public int getMtu() {
        return this.mMtu;
    }

    public String getMvnoMatchData() {
        return this.mMvnoMatchData;
    }

    public int getMvnoType() {
        return this.mMvnoType;
    }

    public int getNetworkTypeBitmask() {
        return this.mNetworkTypeBitmask;
    }

    public String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public boolean getPermanentFailed() {
        return this.mPermanentFailed;
    }

    public int getProfileId() {
        return this.mProfileId;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    @Deprecated
    public InetAddress getProxyAddress() {
        return ApnSetting.inetAddressFromString(this.mProxyAddress);
    }

    public String getProxyAddressAsString() {
        return this.mProxyAddress;
    }

    public int getProxyPort() {
        return this.mProxyPort;
    }

    public int getRoamingProtocol() {
        return this.mRoamingProtocol;
    }

    public int getSkip464Xlat() {
        return this.mSkip464Xlat;
    }

    public String getUser() {
        return this.mUser;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public boolean hasMvnoParams() {
        boolean bl = !TextUtils.isEmpty(ApnSetting.getMvnoTypeStringFromInt(this.mMvnoType)) && !TextUtils.isEmpty(this.mMvnoMatchData);
        return bl;
    }

    public boolean isEnabled() {
        return this.mCarrierEnabled;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public void setPermanentFailed(boolean bl) {
        this.mPermanentFailed = bl;
    }

    public boolean similar(ApnSetting apnSetting) {
        boolean bl = !this.canHandleType(8) && !apnSetting.canHandleType(8) && Objects.equals(this.mApnName, apnSetting.mApnName) && !this.typeSameAny(this, apnSetting) && this.xorEquals(this.mProxyAddress, apnSetting.mProxyAddress) && this.xorEqualsInt(this.mProxyPort, apnSetting.mProxyPort) && this.xorEquals(this.mProtocol, apnSetting.mProtocol) && this.xorEquals(this.mRoamingProtocol, apnSetting.mRoamingProtocol) && Objects.equals(this.mCarrierEnabled, apnSetting.mCarrierEnabled) && Objects.equals(this.mProfileId, apnSetting.mProfileId) && Objects.equals(this.mMvnoType, apnSetting.mMvnoType) && Objects.equals(this.mMvnoMatchData, apnSetting.mMvnoMatchData) && this.xorEquals(this.mMmsc, apnSetting.mMmsc) && this.xorEquals(this.mMmsProxyAddress, apnSetting.mMmsProxyAddress) && this.xorEqualsInt(this.mMmsProxyPort, apnSetting.mMmsProxyPort) && Objects.equals(this.mNetworkTypeBitmask, apnSetting.mNetworkTypeBitmask) && Objects.equals(this.mApnSetId, apnSetting.mApnSetId) && Objects.equals(this.mCarrierId, apnSetting.mCarrierId) && Objects.equals(this.mSkip464Xlat, apnSetting.mSkip464Xlat);
        return bl;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("numeric", this.nullToEmpty(this.mOperatorNumeric));
        contentValues.put("name", this.nullToEmpty(this.mEntryName));
        contentValues.put("apn", this.nullToEmpty(this.mApnName));
        contentValues.put("proxy", this.nullToEmpty(this.mProxyAddress));
        contentValues.put("port", this.nullToEmpty(ApnSetting.portToString(this.mProxyPort)));
        contentValues.put("mmsc", this.nullToEmpty(ApnSetting.UriToString(this.mMmsc)));
        contentValues.put("mmsport", this.nullToEmpty(ApnSetting.portToString(this.mMmsProxyPort)));
        contentValues.put("mmsproxy", this.nullToEmpty(this.mMmsProxyAddress));
        contentValues.put("user", this.nullToEmpty(this.mUser));
        contentValues.put("password", this.nullToEmpty(this.mPassword));
        contentValues.put("authtype", this.mAuthType);
        contentValues.put("type", this.nullToEmpty(ApnSetting.getApnTypesStringFromBitmask(this.mApnTypeBitmask)));
        contentValues.put("protocol", ApnSetting.getProtocolStringFromInt(this.mProtocol));
        contentValues.put("roaming_protocol", ApnSetting.getProtocolStringFromInt(this.mRoamingProtocol));
        contentValues.put("carrier_enabled", this.mCarrierEnabled);
        contentValues.put("mvno_type", ApnSetting.getMvnoTypeStringFromInt(this.mMvnoType));
        contentValues.put("network_type_bitmask", this.mNetworkTypeBitmask);
        contentValues.put("carrier_id", this.mCarrierId);
        contentValues.put("skip_464xlat", this.mSkip464Xlat);
        return contentValues;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ApnSettingV7] ");
        stringBuilder.append(this.mEntryName);
        stringBuilder.append(", ");
        stringBuilder.append(this.mId);
        stringBuilder.append(", ");
        stringBuilder.append(this.mOperatorNumeric);
        stringBuilder.append(", ");
        stringBuilder.append(this.mApnName);
        stringBuilder.append(", ");
        stringBuilder.append(this.mProxyAddress);
        stringBuilder.append(", ");
        stringBuilder.append(ApnSetting.UriToString(this.mMmsc));
        stringBuilder.append(", ");
        stringBuilder.append(this.mMmsProxyAddress);
        stringBuilder.append(", ");
        stringBuilder.append(ApnSetting.portToString(this.mMmsProxyPort));
        stringBuilder.append(", ");
        stringBuilder.append(ApnSetting.portToString(this.mProxyPort));
        stringBuilder.append(", ");
        stringBuilder.append(this.mAuthType);
        stringBuilder.append(", ");
        stringBuilder.append(TextUtils.join((CharSequence)" | ", ApnSetting.getApnTypesStringFromBitmask(this.mApnTypeBitmask).split(",")));
        stringBuilder.append(", ");
        stringBuilder.append(PROTOCOL_INT_MAP.get(this.mProtocol));
        stringBuilder.append(", ");
        stringBuilder.append(PROTOCOL_INT_MAP.get(this.mRoamingProtocol));
        stringBuilder.append(", ");
        stringBuilder.append(this.mCarrierEnabled);
        stringBuilder.append(", ");
        stringBuilder.append(this.mProfileId);
        stringBuilder.append(", ");
        stringBuilder.append(this.mPersistent);
        stringBuilder.append(", ");
        stringBuilder.append(this.mMaxConns);
        stringBuilder.append(", ");
        stringBuilder.append(this.mWaitTime);
        stringBuilder.append(", ");
        stringBuilder.append(this.mMaxConnsTime);
        stringBuilder.append(", ");
        stringBuilder.append(this.mMtu);
        stringBuilder.append(", ");
        stringBuilder.append(MVNO_TYPE_INT_MAP.get(this.mMvnoType));
        stringBuilder.append(", ");
        stringBuilder.append(this.mMvnoMatchData);
        stringBuilder.append(", ");
        stringBuilder.append(this.mPermanentFailed);
        stringBuilder.append(", ");
        stringBuilder.append(this.mNetworkTypeBitmask);
        stringBuilder.append(", ");
        stringBuilder.append(this.mApnSetId);
        stringBuilder.append(", ");
        stringBuilder.append(this.mCarrierId);
        stringBuilder.append(", ");
        stringBuilder.append(this.mSkip464Xlat);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mOperatorNumeric);
        parcel.writeString(this.mEntryName);
        parcel.writeString(this.mApnName);
        parcel.writeString(this.mProxyAddress);
        parcel.writeInt(this.mProxyPort);
        parcel.writeValue(this.mMmsc);
        parcel.writeString(this.mMmsProxyAddress);
        parcel.writeInt(this.mMmsProxyPort);
        parcel.writeString(this.mUser);
        parcel.writeString(this.mPassword);
        parcel.writeInt(this.mAuthType);
        parcel.writeInt(this.mApnTypeBitmask);
        parcel.writeInt(this.mProtocol);
        parcel.writeInt(this.mRoamingProtocol);
        parcel.writeBoolean(this.mCarrierEnabled);
        parcel.writeInt(this.mMvnoType);
        parcel.writeInt(this.mNetworkTypeBitmask);
        parcel.writeInt(this.mApnSetId);
        parcel.writeInt(this.mCarrierId);
        parcel.writeInt(this.mSkip464Xlat);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ApnType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AuthType {
    }

    public static class Builder {
        private String mApnName;
        private int mApnSetId;
        private int mApnTypeBitmask;
        private int mAuthType;
        private boolean mCarrierEnabled;
        private int mCarrierId = -1;
        private String mEntryName;
        private int mId;
        private int mMaxConns;
        private int mMaxConnsTime;
        private String mMmsProxyAddress;
        private int mMmsProxyPort = -1;
        private Uri mMmsc;
        private boolean mModemCognitive;
        private int mMtu;
        private String mMvnoMatchData;
        private int mMvnoType = -1;
        private int mNetworkTypeBitmask;
        private String mOperatorNumeric;
        private String mPassword;
        private int mProfileId;
        private int mProtocol = -1;
        private String mProxyAddress;
        private int mProxyPort = -1;
        private int mRoamingProtocol = -1;
        private int mSkip464Xlat = -1;
        private String mUser;
        private int mWaitTime;

        private Builder setId(int n) {
            this.mId = n;
            return this;
        }

        public ApnSetting build() {
            if ((this.mApnTypeBitmask & 255) != 0 && !TextUtils.isEmpty(this.mApnName) && !TextUtils.isEmpty(this.mEntryName)) {
                return new ApnSetting(this);
            }
            return null;
        }

        public ApnSetting buildWithoutCheck() {
            return new ApnSetting(this);
        }

        public Builder setApnName(String string2) {
            this.mApnName = string2;
            return this;
        }

        public Builder setApnSetId(int n) {
            this.mApnSetId = n;
            return this;
        }

        public Builder setApnTypeBitmask(int n) {
            this.mApnTypeBitmask = n;
            return this;
        }

        public Builder setAuthType(int n) {
            this.mAuthType = n;
            return this;
        }

        public Builder setCarrierEnabled(boolean bl) {
            this.mCarrierEnabled = bl;
            return this;
        }

        public Builder setCarrierId(int n) {
            this.mCarrierId = n;
            return this;
        }

        public Builder setEntryName(String string2) {
            this.mEntryName = string2;
            return this;
        }

        public Builder setMaxConns(int n) {
            this.mMaxConns = n;
            return this;
        }

        public Builder setMaxConnsTime(int n) {
            this.mMaxConnsTime = n;
            return this;
        }

        public Builder setMmsProxyAddress(String string2) {
            this.mMmsProxyAddress = string2;
            return this;
        }

        @Deprecated
        public Builder setMmsProxyAddress(InetAddress inetAddress) {
            this.mMmsProxyAddress = ApnSetting.inetAddressToString(inetAddress);
            return this;
        }

        public Builder setMmsProxyPort(int n) {
            this.mMmsProxyPort = n;
            return this;
        }

        public Builder setMmsc(Uri uri) {
            this.mMmsc = uri;
            return this;
        }

        public Builder setModemCognitive(boolean bl) {
            this.mModemCognitive = bl;
            return this;
        }

        public Builder setMtu(int n) {
            this.mMtu = n;
            return this;
        }

        public Builder setMvnoMatchData(String string2) {
            this.mMvnoMatchData = string2;
            return this;
        }

        public Builder setMvnoType(int n) {
            this.mMvnoType = n;
            return this;
        }

        public Builder setNetworkTypeBitmask(int n) {
            this.mNetworkTypeBitmask = n;
            return this;
        }

        public Builder setOperatorNumeric(String string2) {
            this.mOperatorNumeric = string2;
            return this;
        }

        public Builder setPassword(String string2) {
            this.mPassword = string2;
            return this;
        }

        public Builder setProfileId(int n) {
            this.mProfileId = n;
            return this;
        }

        public Builder setProtocol(int n) {
            this.mProtocol = n;
            return this;
        }

        public Builder setProxyAddress(String string2) {
            this.mProxyAddress = string2;
            return this;
        }

        @Deprecated
        public Builder setProxyAddress(InetAddress inetAddress) {
            this.mProxyAddress = ApnSetting.inetAddressToString(inetAddress);
            return this;
        }

        public Builder setProxyPort(int n) {
            this.mProxyPort = n;
            return this;
        }

        public Builder setRoamingProtocol(int n) {
            this.mRoamingProtocol = n;
            return this;
        }

        public Builder setSkip464Xlat(int n) {
            this.mSkip464Xlat = n;
            return this;
        }

        public Builder setUser(String string2) {
            this.mUser = string2;
            return this;
        }

        public Builder setWaitTime(int n) {
            this.mWaitTime = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MvnoType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProtocolType {
    }

}

