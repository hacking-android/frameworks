/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.ParcelUtil;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WifiEnterpriseConfig
implements Parcelable {
    public static final String ALTSUBJECT_MATCH_KEY = "altsubject_match";
    public static final String ANON_IDENTITY_KEY = "anonymous_identity";
    public static final String CA_CERT_ALIAS_DELIMITER = " ";
    public static final String CA_CERT_KEY = "ca_cert";
    public static final String CA_CERT_PREFIX = "keystore://CACERT_";
    public static final String CA_PATH_KEY = "ca_path";
    public static final String CLIENT_CERT_KEY = "client_cert";
    public static final String CLIENT_CERT_PREFIX = "keystore://USRCERT_";
    public static final Parcelable.Creator<WifiEnterpriseConfig> CREATOR;
    public static final String DOM_SUFFIX_MATCH_KEY = "domain_suffix_match";
    public static final String EAP_KEY = "eap";
    public static final String EMPTY_VALUE = "NULL";
    public static final String ENGINE_DISABLE = "0";
    public static final String ENGINE_ENABLE = "1";
    public static final String ENGINE_ID_KEY = "engine_id";
    public static final String ENGINE_ID_KEYSTORE = "keystore";
    public static final String ENGINE_KEY = "engine";
    public static final String IDENTITY_KEY = "identity";
    public static final String KEYSTORES_URI = "keystores://";
    public static final String KEYSTORE_URI = "keystore://";
    public static final String OPP_KEY_CACHING = "proactive_key_caching";
    public static final String PASSWORD_KEY = "password";
    public static final String PHASE2_KEY = "phase2";
    public static final String PLMN_KEY = "plmn";
    public static final String PRIVATE_KEY_ID_KEY = "key_id";
    public static final String REALM_KEY = "realm";
    public static final String SUBJECT_MATCH_KEY = "subject_match";
    private static final String[] SUPPLICANT_CONFIG_KEYS;
    private static final String TAG = "WifiEnterpriseConfig";
    private static final List<String> UNQUOTED_KEYS;
    private X509Certificate[] mCaCerts;
    private X509Certificate[] mClientCertificateChain;
    private PrivateKey mClientPrivateKey;
    private int mEapMethod = -1;
    @UnsupportedAppUsage
    private HashMap<String, String> mFields = new HashMap();
    private boolean mIsAppInstalledCaCert = false;
    private boolean mIsAppInstalledDeviceKeyAndCert = false;
    private int mPhase2Method = 0;

    static {
        SUPPLICANT_CONFIG_KEYS = new String[]{IDENTITY_KEY, ANON_IDENTITY_KEY, PASSWORD_KEY, CLIENT_CERT_KEY, CA_CERT_KEY, SUBJECT_MATCH_KEY, ENGINE_KEY, ENGINE_ID_KEY, PRIVATE_KEY_ID_KEY, ALTSUBJECT_MATCH_KEY, DOM_SUFFIX_MATCH_KEY, CA_PATH_KEY};
        UNQUOTED_KEYS = Arrays.asList(ENGINE_KEY, OPP_KEY_CACHING);
        CREATOR = new Parcelable.Creator<WifiEnterpriseConfig>(){

            @Override
            public WifiEnterpriseConfig createFromParcel(Parcel parcel) {
                WifiEnterpriseConfig wifiEnterpriseConfig = new WifiEnterpriseConfig();
                int n = parcel.readInt();
                for (int i = 0; i < n; ++i) {
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    wifiEnterpriseConfig.mFields.put(string2, string3);
                }
                wifiEnterpriseConfig.mEapMethod = parcel.readInt();
                wifiEnterpriseConfig.mPhase2Method = parcel.readInt();
                wifiEnterpriseConfig.mCaCerts = ParcelUtil.readCertificates(parcel);
                wifiEnterpriseConfig.mClientPrivateKey = ParcelUtil.readPrivateKey(parcel);
                wifiEnterpriseConfig.mClientCertificateChain = ParcelUtil.readCertificates(parcel);
                wifiEnterpriseConfig.mIsAppInstalledDeviceKeyAndCert = parcel.readBoolean();
                wifiEnterpriseConfig.mIsAppInstalledCaCert = parcel.readBoolean();
                return wifiEnterpriseConfig;
            }

            public WifiEnterpriseConfig[] newArray(int n) {
                return new WifiEnterpriseConfig[n];
            }
        };
    }

    public WifiEnterpriseConfig() {
    }

    public WifiEnterpriseConfig(WifiEnterpriseConfig wifiEnterpriseConfig) {
        this.copyFrom(wifiEnterpriseConfig, false, "");
    }

    private String convertToQuotedString(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        return stringBuilder.toString();
    }

    private void copyFrom(WifiEnterpriseConfig wifiEnterpriseConfig, boolean bl, String arrx509Certificate) {
        for (String string2 : wifiEnterpriseConfig.mFields.keySet()) {
            if (bl && string2.equals(PASSWORD_KEY) && TextUtils.equals(wifiEnterpriseConfig.mFields.get(string2), (CharSequence)arrx509Certificate)) continue;
            this.mFields.put(string2, wifiEnterpriseConfig.mFields.get(string2));
        }
        arrx509Certificate = wifiEnterpriseConfig.mCaCerts;
        this.mCaCerts = arrx509Certificate != null ? Arrays.copyOf(arrx509Certificate, arrx509Certificate.length) : null;
        this.mClientPrivateKey = wifiEnterpriseConfig.mClientPrivateKey;
        arrx509Certificate = wifiEnterpriseConfig.mClientCertificateChain;
        this.mClientCertificateChain = arrx509Certificate != null ? Arrays.copyOf(arrx509Certificate, arrx509Certificate.length) : null;
        this.mEapMethod = wifiEnterpriseConfig.mEapMethod;
        this.mPhase2Method = wifiEnterpriseConfig.mPhase2Method;
        this.mIsAppInstalledDeviceKeyAndCert = wifiEnterpriseConfig.mIsAppInstalledDeviceKeyAndCert;
        this.mIsAppInstalledCaCert = wifiEnterpriseConfig.mIsAppInstalledCaCert;
    }

    public static String decodeCaCertificateAlias(String string2) {
        Object object = new byte[string2.length() >> 1];
        int n = 0;
        int n2 = 0;
        while (n < string2.length()) {
            object[n2] = (byte)Integer.parseInt(string2.substring(n, n + 2), 16);
            n += 2;
            ++n2;
        }
        try {
            object = new String((byte[])object, StandardCharsets.UTF_8);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            return string2;
        }
    }

    public static String encodeCaCertificateAlias(String charSequence) {
        byte[] arrby = ((String)charSequence).getBytes(StandardCharsets.UTF_8);
        charSequence = new StringBuilder(arrby.length * 2);
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            ((StringBuilder)charSequence).append(String.format("%02x", arrby[i] & 255));
        }
        return ((StringBuilder)charSequence).toString();
    }

    private String getFieldValue(String string2, String string3) {
        if (!TextUtils.isEmpty(string2 = this.mFields.get(string2)) && !EMPTY_VALUE.equals(string2)) {
            if ((string2 = this.removeDoubleQuotes(string2)).startsWith(string3)) {
                return string2.substring(string3.length());
            }
            return string2;
        }
        return "";
    }

    private int getStringIndex(String[] arrstring, String string2, int n) {
        if (TextUtils.isEmpty(string2)) {
            return n;
        }
        for (int i = 0; i < arrstring.length; ++i) {
            if (!string2.equals(arrstring[i])) continue;
            return i;
        }
        return n;
    }

    private boolean isEapMethodValid() {
        int n = this.mEapMethod;
        if (n == -1) {
            Log.e(TAG, "WiFi enterprise configuration is invalid as it supplies no EAP method.");
            return false;
        }
        if (n >= 0 && n < Eap.strings.length) {
            n = this.mPhase2Method;
            if (n >= 0 && n < Phase2.strings.length) {
                return true;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("mPhase2Method is invald for WiFi enterprise configuration: ");
            stringBuilder.append(this.mPhase2Method);
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mEapMethod is invald for WiFi enterprise configuration: ");
        stringBuilder.append(this.mEapMethod);
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    private String removeDoubleQuotes(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return "";
        }
        int n = string2.length();
        if (n > 1 && string2.charAt(0) == '\"' && string2.charAt(n - 1) == '\"') {
            return string2.substring(1, n - 1);
        }
        return string2;
    }

    private void setFieldValue(String string2, String string3, String string4) {
        if (TextUtils.isEmpty(string3)) {
            this.mFields.put(string2, EMPTY_VALUE);
        } else {
            if (!UNQUOTED_KEYS.contains(string2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string4);
                stringBuilder.append(string3);
                string3 = this.convertToQuotedString(stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string4);
                stringBuilder.append(string3);
                string3 = stringBuilder.toString();
            }
            this.mFields.put(string2, string3);
        }
    }

    public void copyFromExternal(WifiEnterpriseConfig wifiEnterpriseConfig, String string2) {
        this.copyFrom(wifiEnterpriseConfig, true, this.convertToQuotedString(string2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAltSubjectMatch() {
        return this.getFieldValue(ALTSUBJECT_MATCH_KEY);
    }

    public String getAnonymousIdentity() {
        return this.getFieldValue(ANON_IDENTITY_KEY);
    }

    public X509Certificate getCaCertificate() {
        X509Certificate[] arrx509Certificate = this.mCaCerts;
        if (arrx509Certificate != null && arrx509Certificate.length > 0) {
            return arrx509Certificate[0];
        }
        return null;
    }

    @UnsupportedAppUsage
    public String getCaCertificateAlias() {
        return this.getFieldValue(CA_CERT_KEY, CA_CERT_PREFIX);
    }

    public String[] getCaCertificateAliases() {
        String string2 = this.getFieldValue(CA_CERT_KEY);
        if (string2.startsWith(CA_CERT_PREFIX)) {
            return new String[]{this.getFieldValue(CA_CERT_KEY, CA_CERT_PREFIX)};
        }
        boolean bl = string2.startsWith(KEYSTORES_URI);
        String[] arrstring = null;
        String[] arrstring2 = null;
        if (bl) {
            arrstring = TextUtils.split(string2.substring(KEYSTORES_URI.length()), CA_CERT_ALIAS_DELIMITER);
            for (int i = 0; i < arrstring.length; ++i) {
                arrstring[i] = WifiEnterpriseConfig.decodeCaCertificateAlias(arrstring[i]);
                if (!arrstring[i].startsWith("CACERT_")) continue;
                arrstring[i] = arrstring[i].substring("CACERT_".length());
            }
            if (arrstring.length != 0) {
                arrstring2 = arrstring;
            }
            return arrstring2;
        }
        arrstring2 = TextUtils.isEmpty(string2) ? arrstring : new String[]{string2};
        return arrstring2;
    }

    public X509Certificate[] getCaCertificates() {
        X509Certificate[] arrx509Certificate = this.mCaCerts;
        if (arrx509Certificate != null && arrx509Certificate.length > 0) {
            return arrx509Certificate;
        }
        return null;
    }

    public String getCaPath() {
        return this.getFieldValue(CA_PATH_KEY);
    }

    public X509Certificate getClientCertificate() {
        X509Certificate[] arrx509Certificate = this.mClientCertificateChain;
        if (arrx509Certificate != null && arrx509Certificate.length > 0) {
            return arrx509Certificate[0];
        }
        return null;
    }

    @UnsupportedAppUsage
    public String getClientCertificateAlias() {
        return this.getFieldValue(CLIENT_CERT_KEY, CLIENT_CERT_PREFIX);
    }

    public X509Certificate[] getClientCertificateChain() {
        X509Certificate[] arrx509Certificate = this.mClientCertificateChain;
        if (arrx509Certificate != null && arrx509Certificate.length > 0) {
            return arrx509Certificate;
        }
        return null;
    }

    public PrivateKey getClientPrivateKey() {
        return this.mClientPrivateKey;
    }

    public String getDomainSuffixMatch() {
        return this.getFieldValue(DOM_SUFFIX_MATCH_KEY);
    }

    public int getEapMethod() {
        return this.mEapMethod;
    }

    public String getFieldValue(String string2) {
        return this.getFieldValue(string2, "");
    }

    public String getIdentity() {
        return this.getFieldValue(IDENTITY_KEY);
    }

    public String getKeyId(WifiEnterpriseConfig object) {
        int n = this.mEapMethod;
        String string2 = EMPTY_VALUE;
        if (n == -1) {
            if (object != null) {
                string2 = ((WifiEnterpriseConfig)object).getKeyId(null);
            }
            return string2;
        }
        if (!this.isEapMethodValid()) {
            return EMPTY_VALUE;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(Eap.strings[this.mEapMethod]);
        ((StringBuilder)object).append("_");
        ((StringBuilder)object).append(Phase2.strings[this.mPhase2Method]);
        return ((StringBuilder)object).toString();
    }

    public String getPassword() {
        return this.getFieldValue(PASSWORD_KEY);
    }

    public int getPhase2Method() {
        return this.mPhase2Method;
    }

    public String getPlmn() {
        return this.getFieldValue(PLMN_KEY);
    }

    public String getRealm() {
        return this.getFieldValue(REALM_KEY);
    }

    public String getSubjectMatch() {
        return this.getFieldValue(SUBJECT_MATCH_KEY);
    }

    public boolean isAppInstalledCaCert() {
        return this.mIsAppInstalledCaCert;
    }

    public boolean isAppInstalledDeviceKeyAndCert() {
        return this.mIsAppInstalledDeviceKeyAndCert;
    }

    public void loadFromSupplicant(SupplicantLoader object) {
        for (String string2 : SUPPLICANT_CONFIG_KEYS) {
            String string3 = object.loadValue(string2);
            if (string3 == null) {
                this.mFields.put(string2, EMPTY_VALUE);
                continue;
            }
            this.mFields.put(string2, string3);
        }
        Object object2 = object.loadValue(EAP_KEY);
        this.mEapMethod = this.getStringIndex(Eap.strings, (String)object2, -1);
        object2 = this.removeDoubleQuotes(object.loadValue(PHASE2_KEY));
        if (((String)object2).startsWith("auth=")) {
            object = ((String)object2).substring("auth=".length());
        } else {
            object = object2;
            if (((String)object2).startsWith("autheap=")) {
                object = ((String)object2).substring("autheap=".length());
            }
        }
        this.mPhase2Method = this.getStringIndex(Phase2.strings, (String)object, 0);
    }

    public void resetCaCertificate() {
        this.mCaCerts = null;
    }

    public void resetClientKeyEntry() {
        this.mClientPrivateKey = null;
        this.mClientCertificateChain = null;
    }

    public boolean saveToSupplicant(SupplicantSaver supplicantSaver) {
        int n;
        boolean bl = this.isEapMethodValid();
        int n2 = 0;
        if (!bl) {
            return false;
        }
        int n3 = this.mEapMethod;
        n3 = n3 != 4 && n3 != 5 && n3 != 6 ? 0 : 1;
        for (String charSequence : this.mFields.keySet()) {
            if (n3 != 0 && ANON_IDENTITY_KEY.equals(charSequence) || supplicantSaver.saveValue(charSequence, this.mFields.get(charSequence))) continue;
            return false;
        }
        if (!supplicantSaver.saveValue(EAP_KEY, Eap.strings[this.mEapMethod])) {
            return false;
        }
        int n4 = this.mEapMethod;
        if (n4 != 1 && (n = this.mPhase2Method) != 0) {
            n3 = n2;
            if (n4 == 2) {
                n3 = n2;
                if (n == 4) {
                    n3 = 1;
                }
            }
            String string2 = n3 != 0 ? "autheap=" : "auth=";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(Phase2.strings[this.mPhase2Method]);
            return supplicantSaver.saveValue(PHASE2_KEY, this.convertToQuotedString(stringBuilder.toString()));
        }
        if (this.mPhase2Method == 0) {
            return supplicantSaver.saveValue(PHASE2_KEY, null);
        }
        Log.e(TAG, "WiFi enterprise configuration is invalid as it supplies a phase 2 method but the phase1 method does not support it.");
        return false;
    }

    public void setAltSubjectMatch(String string2) {
        this.setFieldValue(ALTSUBJECT_MATCH_KEY, string2);
    }

    public void setAnonymousIdentity(String string2) {
        this.setFieldValue(ANON_IDENTITY_KEY, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCaCertificate(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            this.mCaCerts = null;
            return;
        }
        if (x509Certificate.getBasicConstraints() >= 0) {
            this.mIsAppInstalledCaCert = true;
            this.mCaCerts = new X509Certificate[]{x509Certificate};
            return;
        }
        this.mCaCerts = null;
        throw new IllegalArgumentException("Not a CA certificate");
    }

    @UnsupportedAppUsage
    public void setCaCertificateAlias(String string2) {
        this.setFieldValue(CA_CERT_KEY, string2, CA_CERT_PREFIX);
    }

    public void setCaCertificateAliases(String[] arrstring) {
        if (arrstring == null) {
            this.setFieldValue(CA_CERT_KEY, null, CA_CERT_PREFIX);
        } else if (arrstring.length == 1) {
            this.setCaCertificateAlias(arrstring[0]);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arrstring.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(CA_CERT_ALIAS_DELIMITER);
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("CACERT_");
                stringBuilder2.append(arrstring[i]);
                stringBuilder.append(WifiEnterpriseConfig.encodeCaCertificateAlias(stringBuilder2.toString()));
            }
            this.setFieldValue(CA_CERT_KEY, stringBuilder.toString(), KEYSTORES_URI);
        }
    }

    public void setCaCertificates(X509Certificate[] arrx509Certificate) {
        if (arrx509Certificate != null) {
            X509Certificate[] arrx509Certificate2 = new X509Certificate[arrx509Certificate.length];
            for (int i = 0; i < arrx509Certificate.length; ++i) {
                if (arrx509Certificate[i].getBasicConstraints() >= 0) {
                    arrx509Certificate2[i] = arrx509Certificate[i];
                    continue;
                }
                this.mCaCerts = null;
                throw new IllegalArgumentException("Not a CA certificate");
            }
            this.mCaCerts = arrx509Certificate2;
            this.mIsAppInstalledCaCert = true;
        } else {
            this.mCaCerts = null;
        }
    }

    public void setCaPath(String string2) {
        this.setFieldValue(CA_PATH_KEY, string2);
    }

    @UnsupportedAppUsage
    public void setClientCertificateAlias(String string2) {
        this.setFieldValue(CLIENT_CERT_KEY, string2, CLIENT_CERT_PREFIX);
        this.setFieldValue(PRIVATE_KEY_ID_KEY, string2, "USRPKEY_");
        if (TextUtils.isEmpty(string2)) {
            this.setFieldValue(ENGINE_KEY, ENGINE_DISABLE);
            this.setFieldValue(ENGINE_ID_KEY, "");
        } else {
            this.setFieldValue(ENGINE_KEY, ENGINE_ENABLE);
            this.setFieldValue(ENGINE_ID_KEY, ENGINE_ID_KEYSTORE);
        }
    }

    public void setClientKeyEntry(PrivateKey privateKey, X509Certificate x509Certificate) {
        X509Certificate[] arrx509Certificate = null;
        if (x509Certificate != null) {
            arrx509Certificate = new X509Certificate[]{x509Certificate};
        }
        this.setClientKeyEntryWithCertificateChain(privateKey, arrx509Certificate);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setClientKeyEntryWithCertificateChain(PrivateKey privateKey, X509Certificate[] arrx509Certificate) {
        X509Certificate[] arrx509Certificate2;
        X509Certificate[] arrx509Certificate3 = arrx509Certificate2 = null;
        if (arrx509Certificate != null) {
            arrx509Certificate3 = arrx509Certificate2;
            if (arrx509Certificate.length > 0) {
                if (arrx509Certificate[0].getBasicConstraints() != -1) throw new IllegalArgumentException("First certificate in the chain must be a client end certificate");
                for (int i = 1; i < arrx509Certificate.length; ++i) {
                    if (arrx509Certificate[i].getBasicConstraints() == -1) throw new IllegalArgumentException("All certificates following the first must be CA certificates");
                }
                arrx509Certificate3 = Arrays.copyOf(arrx509Certificate, arrx509Certificate.length);
                if (privateKey == null) throw new IllegalArgumentException("Client cert without a private key");
                if (privateKey.getEncoded() == null) {
                    throw new IllegalArgumentException("Private key cannot be encoded");
                }
            }
        }
        this.mClientPrivateKey = privateKey;
        this.mClientCertificateChain = arrx509Certificate3;
        this.mIsAppInstalledDeviceKeyAndCert = true;
    }

    public void setDomainSuffixMatch(String string2) {
        this.setFieldValue(DOM_SUFFIX_MATCH_KEY, string2);
    }

    public void setEapMethod(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Unknown EAP method");
            }
            case 1: 
            case 7: {
                this.setPhase2Method(0);
            }
            case 0: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        this.mEapMethod = n;
        this.setFieldValue(OPP_KEY_CACHING, ENGINE_ENABLE);
    }

    public void setFieldValue(String string2, String string3) {
        this.setFieldValue(string2, string3, "");
    }

    public void setIdentity(String string2) {
        this.setFieldValue(IDENTITY_KEY, string2, "");
    }

    public void setPassword(String string2) {
        this.setFieldValue(PASSWORD_KEY, string2);
    }

    public void setPhase2Method(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Unknown Phase 2 method");
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
        }
        this.mPhase2Method = n;
    }

    public void setPlmn(String string2) {
        this.setFieldValue(PLMN_KEY, string2);
    }

    public void setRealm(String string2) {
        this.setFieldValue(REALM_KEY, string2);
    }

    public void setSubjectMatch(String string2) {
        this.setFieldValue(SUBJECT_MATCH_KEY, string2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String string2 : this.mFields.keySet()) {
            String string3 = PASSWORD_KEY.equals(string2) ? "<removed>" : this.mFields.get(string2);
            stringBuffer.append(string2);
            stringBuffer.append(CA_CERT_ALIAS_DELIMITER);
            stringBuffer.append(string3);
            stringBuffer.append("\n");
        }
        int n = this.mEapMethod;
        if (n >= 0 && n < Eap.strings.length) {
            stringBuffer.append("eap_method: ");
            stringBuffer.append(Eap.strings[this.mEapMethod]);
            stringBuffer.append("\n");
        }
        if ((n = this.mPhase2Method) > 0 && n < Phase2.strings.length) {
            stringBuffer.append("phase2_method: ");
            stringBuffer.append(Phase2.strings[this.mPhase2Method]);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFields.size());
        for (Map.Entry<String, String> entry : this.mFields.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
        parcel.writeInt(this.mEapMethod);
        parcel.writeInt(this.mPhase2Method);
        ParcelUtil.writeCertificates(parcel, this.mCaCerts);
        ParcelUtil.writePrivateKey(parcel, this.mClientPrivateKey);
        ParcelUtil.writeCertificates(parcel, this.mClientCertificateChain);
        parcel.writeBoolean(this.mIsAppInstalledDeviceKeyAndCert);
        parcel.writeBoolean(this.mIsAppInstalledCaCert);
    }

    public static final class Eap {
        public static final int AKA = 5;
        public static final int AKA_PRIME = 6;
        public static final int NONE = -1;
        public static final int PEAP = 0;
        public static final int PWD = 3;
        public static final int SIM = 4;
        public static final int TLS = 1;
        public static final int TTLS = 2;
        public static final int UNAUTH_TLS = 7;
        public static final String[] strings = new String[]{"PEAP", "TLS", "TTLS", "PWD", "SIM", "AKA", "AKA'", "WFA-UNAUTH-TLS"};

        private Eap() {
        }
    }

    public static final class Phase2 {
        public static final int AKA = 6;
        public static final int AKA_PRIME = 7;
        private static final String AUTHEAP_PREFIX = "autheap=";
        private static final String AUTH_PREFIX = "auth=";
        public static final int GTC = 4;
        public static final int MSCHAP = 2;
        public static final int MSCHAPV2 = 3;
        public static final int NONE = 0;
        public static final int PAP = 1;
        public static final int SIM = 5;
        public static final String[] strings = new String[]{"NULL", "PAP", "MSCHAP", "MSCHAPV2", "GTC", "SIM", "AKA", "AKA'"};

        private Phase2() {
        }
    }

    public static interface SupplicantLoader {
        public String loadValue(String var1);
    }

    public static interface SupplicantSaver {
        public boolean saveValue(String var1, String var2);
    }

}

