/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.service.carrier.CarrierIdentifier
 *  android.service.euicc.EuiccProfileInfo
 *  android.service.euicc.EuiccProfileInfo$Builder
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.UiccAccessRule
 *  android.telephony.euicc.EuiccNotification
 *  android.telephony.euicc.EuiccRulesAuthTable
 *  android.telephony.euicc.EuiccRulesAuthTable$Builder
 *  android.text.TextUtils
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.telephony.uicc.asn1.Asn1Decoder
 *  com.android.internal.telephony.uicc.asn1.Asn1Node
 *  com.android.internal.telephony.uicc.asn1.Asn1Node$Builder
 *  com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException
 *  com.android.internal.telephony.uicc.asn1.TagNotFoundException
 *  com.android.internal.telephony.uicc.euicc.-$
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$0NUjmK32-r6146hGb0RCJUAfiOg
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ-7-Uy6ZOQ
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ-YvOM
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$8wofF-Li1V6a8rJQc-M2IGeJ26E
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$ADB4BKXCYw8oHd-aqHgRFEm7vGg
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$AWltG4uFbHn2Xq7ZPpU3U1qOqVM
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$B99bQ-FkeD9OwB8_qTcKScitlrM
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$HBn5KBGylwjLqIEm3rBhXnUU_8U
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$HgCDP54gCppk81aqhuCG0YGJWEc
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$MRlmz2j6osUyi5hGvD3j9D4Tsrg
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$Qej04bOzl5rj_T7NIjvbnJX7b2s
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$UxQlywWQ3cqQ7G7vS2KuMEwtYro
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$WE7TDTe507w4dBh1UvCgBgp3xVk
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$WoM2ziweCgrYxAgllxpAtHF-3Es
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$X8OWFy8Bi7TMh117x6vCBqzSqVY
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP-URUuDP17U
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$g0LHcTcRLtF0WE8Tyv2BvipGgrM
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$gM-702GsygHKxB8F-_MrSarNKjg
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$hCCBghNOkOgvjeYe8LWQml6I9Ow
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$tPSWjOKtm9yQg21kHmLX49PPf_4
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$u2-6zCuoZP9CLxIS2g4BREHHECI
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk
 *  com.android.internal.telephony.uicc.euicc.-$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A
 */
package com.android.internal.telephony.uicc.euicc;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Registrant;
import android.os.RegistrantList;
import android.service.carrier.CarrierIdentifier;
import android.service.euicc.EuiccProfileInfo;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.UiccAccessRule;
import android.telephony.euicc.EuiccNotification;
import android.telephony.euicc.EuiccRulesAuthTable;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.asn1.Asn1Decoder;
import com.android.internal.telephony.uicc.asn1.Asn1Node;
import com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException;
import com.android.internal.telephony.uicc.asn1.TagNotFoundException;
import com.android.internal.telephony.uicc.euicc.-$;
import com.android.internal.telephony.uicc.euicc.EuiccCardErrorException;
import com.android.internal.telephony.uicc.euicc.EuiccCardException;
import com.android.internal.telephony.uicc.euicc.EuiccSpecVersion;
import com.android.internal.telephony.uicc.euicc.Tags;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$00j_sPLzMkCJBnrpRWJA8rfmUIY;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$0N6_V0pqmnTfKxVMU5IUj_svXDA;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$0NUjmK32_r6146hGb0RCJUAfiOg;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ_7_Uy6ZOQ;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$519fif8g_uQDyFgB0QDuhelpNTM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$5wK_r0z9fLtA1ZRVlbk3WfOYXJI;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ_YvOM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$8wofF_Li1V6a8rJQc_M2IGeJ26E;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$ADB4BKXCYw8oHd_aqHgRFEm7vGg;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$AWltG4uFbHn2Xq7ZPpU3U1qOqVM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$AYHfF2w_VlO00s9p_djcPJl_1no;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$B99bQ_FkeD9OwB8_qTcKScitlrM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$DsQXeVrINumCqiGAAeDJPNFEix0;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$EcGEDb4lqNEz5YyXQfIgXTUpv_c;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$FbRMt6fKnYLkYt6oi5qhs1ZyEvc;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$HBn5KBGylwjLqIEm3rBhXnUU_8U;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$HgCDP54gCppk81aqhuCG0YGJWEc;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$MRlmz2j6osUyi5hGvD3j9D4Tsrg;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$MoRNAw8O6kYG_c2AJkozlJwO2NM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$QGtQZCF6KEnI_x59_tp1eo8mWew;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$Qej04bOzl5rj_T7NIjvbnJX7b2s;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$Rc41c7zRLip3RrHuKqZ_Sv7h8wI;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$SiGT87lDw1xXD_7PyidTGv5wxfQ;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$U1ORE3W_o_HdXWc6N59UnRQmLQI;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$UxQlywWQ3cqQ7G7vS2KuMEwtYro;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$WE7TDTe507w4dBh1UvCgBgp3xVk;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$WoM2ziweCgrYxAgllxpAtHF_3Es;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$Wx9UmYdMwRy23Rf6Vd7b2aSx6S8;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$X8OWFy8Bi7TMh117x6vCBqzSqVY;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$XDNTzAU_9I92HztVAJQr4NXR3DU;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$Y4to2oZTgOnUA9QDesgeA5MRLr4;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$_VOB5FQfE7RUMgpmr8bK_j3CsUA;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$dXiSnJocvC7r6HwRUJlZI7Qnleo;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP_URUuDP17U;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$ep5FQKIEACJvfaaqyTp6OGIepAc;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$fcz5l0a6JlSxs8MXCst7wXG4bUc;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$g0LHcTcRLtF0WE8Tyv2BvipGgrM;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$gM_702GsygHKxB8F__MrSarNKjg;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$hCCBghNOkOgvjeYe8LWQml6I9Ow;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$iHmYnivZKaYKk9UB26Y_pNgqjVU;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$krunAJLFPj0Co1L7ROlSfW13UNg;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$okradEAowCk8rNBK1OaJIA6l6eA;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$tPSWjOKtm9yQg21kHmLX49PPf_4;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$u2_6zCuoZP9CLxIS2g4BREHHECI;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk;
import com.android.internal.telephony.uicc.euicc._$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A;
import com.android.internal.telephony.uicc.euicc.apdu.ApduException;
import com.android.internal.telephony.uicc.euicc.apdu.ApduSender;
import com.android.internal.telephony.uicc.euicc.apdu.ApduSenderResultCallback;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;
import com.android.internal.telephony.uicc.euicc.apdu.RequestProvider;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultCallback;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultHelper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class EuiccCard
extends UiccCard {
    private static final int APDU_ERROR_SIM_REFRESH = 28416;
    private static final int CODE_NOTHING_TO_DELETE = 1;
    private static final int CODE_NO_RESULT_AVAILABLE = 1;
    private static final int CODE_OK = 0;
    private static final int CODE_PROFILE_NOT_IN_EXPECTED_STATE = 2;
    private static final boolean DBG = true;
    private static final String DEV_CAP_CDMA_1X = "cdma1x";
    private static final String DEV_CAP_CRL = "crl";
    private static final String DEV_CAP_EHRPD = "ehrpd";
    private static final String DEV_CAP_EUTRAN = "eutran";
    private static final String DEV_CAP_GSM = "gsm";
    private static final String DEV_CAP_HRPD = "hrpd";
    private static final String DEV_CAP_NFC = "nfc";
    private static final String DEV_CAP_UTRAN = "utran";
    private static final int ICCID_LENGTH = 20;
    private static final String ISD_R_AID = "A0000005591010FFFFFFFF8900000100";
    private static final String LOG_TAG = "EuiccCard";
    private static final EuiccSpecVersion SGP22_V_2_0 = new EuiccSpecVersion(2, 0, 0);
    private static final EuiccSpecVersion SGP22_V_2_1 = new EuiccSpecVersion(2, 1, 0);
    private final ApduSender mApduSender;
    private volatile String mEid;
    private RegistrantList mEidReadyRegistrants;
    private EuiccSpecVersion mSpecVersion;

    public EuiccCard(Context object, CommandsInterface commandsInterface, IccCardStatus iccCardStatus, int n, Object object2) {
        super((Context)object, commandsInterface, iccCardStatus, n, object2);
        this.mApduSender = new ApduSender(commandsInterface, ISD_R_AID, false);
        if (TextUtils.isEmpty((CharSequence)iccCardStatus.eid)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("no eid given in constructor for phone ");
            ((StringBuilder)object).append(n);
            EuiccCard.loge(((StringBuilder)object).toString());
            this.loadEidAndNotifyRegistrants();
        } else {
            this.mEid = iccCardStatus.eid;
            this.mCardId = iccCardStatus.eid;
        }
    }

    private static CarrierIdentifier buildCarrierIdentifier(Asn1Node asn1Node) throws InvalidAsn1DataException, TagNotFoundException {
        String string = null;
        if (asn1Node.hasChild(129, new int[0])) {
            string = IccUtils.bytesToHexString((byte[])asn1Node.getChild(129, new int[0]).asBytes());
        }
        String string2 = null;
        if (asn1Node.hasChild(130, new int[0])) {
            string2 = IccUtils.bytesToHexString((byte[])asn1Node.getChild(130, new int[0]).asBytes());
        }
        return new CarrierIdentifier(asn1Node.getChild(128, new int[0]).asBytes(), string, string2);
    }

    private static void buildProfile(Asn1Node object, EuiccProfileInfo.Builder builder) throws TagNotFoundException, InvalidAsn1DataException {
        if (object.hasChild(144, new int[0])) {
            builder.setNickname(object.getChild(144, new int[0]).asString());
        }
        if (object.hasChild(145, new int[0])) {
            builder.setServiceProviderName(object.getChild(145, new int[0]).asString());
        }
        if (object.hasChild(146, new int[0])) {
            builder.setProfileName(object.getChild(146, new int[0]).asString());
        }
        if (object.hasChild(183, new int[0])) {
            builder.setCarrierIdentifier(EuiccCard.buildCarrierIdentifier(object.getChild(183, new int[0])));
        }
        if (object.hasChild(40816, new int[0])) {
            builder.setState(object.getChild(40816, new int[0]).asInteger());
        } else {
            builder.setState(0);
        }
        if (object.hasChild(149, new int[0])) {
            builder.setProfileClass(object.getChild(149, new int[0]).asInteger());
        } else {
            builder.setProfileClass(2);
        }
        if (object.hasChild(153, new int[0])) {
            builder.setPolicyRules(object.getChild(153, new int[0]).asBits());
        }
        if (object.hasChild(49014, new int[0])) {
            UiccAccessRule[] arruiccAccessRule = EuiccCard.buildUiccAccessRule(object.getChild(49014, new int[0]).getChildren(226));
            object = null;
            if (arruiccAccessRule != null) {
                object = Arrays.asList(arruiccAccessRule);
            }
            builder.setUiccAccessRule((List)object);
        }
    }

    private static UiccAccessRule[] buildUiccAccessRule(List<Asn1Node> list) throws InvalidAsn1DataException, TagNotFoundException {
        if (list.isEmpty()) {
            return null;
        }
        int n = list.size();
        UiccAccessRule[] arruiccAccessRule = new UiccAccessRule[n];
        for (int i = 0; i < n; ++i) {
            Asn1Node asn1Node = list.get(i);
            Asn1Node asn1Node2 = asn1Node.getChild(225, new int[0]);
            byte[] arrby = asn1Node2.getChild(193, new int[0]).asBytes();
            String string = null;
            if (asn1Node2.hasChild(202, new int[0])) {
                string = asn1Node2.getChild(202, new int[0]).asString();
            }
            long l = 0L;
            if (asn1Node.hasChild(227, new int[]{219})) {
                l = asn1Node.getChild(227, new int[]{219}).asRawLong();
            }
            arruiccAccessRule[i] = new UiccAccessRule(arrby, string, l);
        }
        return arruiccAccessRule;
    }

    private static EuiccNotification createNotification(Asn1Node object) throws TagNotFoundException, InvalidAsn1DataException {
        Object object2 = object.getTag() == 48943 ? object : (object.getTag() == 48951 ? object.getChild(48935, new int[]{48943}) : object.getChild(48943, new int[0]));
        int n = object2.getChild(128, new int[0]).asInteger();
        String string = object2.getChild(12, new int[0]).asString();
        int n2 = object2.getChild(129, new int[0]).asBits();
        object = object.getTag() == 48943 ? null : object.toBytes();
        return new EuiccNotification(n, string, n2, (byte[])object);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public static byte[] getDeviceId(String string, EuiccSpecVersion object) {
        byte[] arrby = new byte[8];
        if (((EuiccSpecVersion)object).compareTo(SGP22_V_2_1) >= 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append('F');
            IccUtils.bcdToBytes((String)((StringBuilder)object).toString(), (byte[])arrby);
            byte by = arrby[7];
            arrby[7] = (byte)((by & 255) << 4 | (by & 255) >>> 4);
        } else {
            IccUtils.bcdToBytes((String)string, (byte[])arrby);
        }
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private EuiccSpecVersion getOrExtractSpecVersion(byte[] object) {
        EuiccSpecVersion euiccSpecVersion = this.mSpecVersion;
        if (euiccSpecVersion != null) {
            return euiccSpecVersion;
        }
        euiccSpecVersion = EuiccSpecVersion.fromOpenChannelResponse(object);
        if (euiccSpecVersion == null) return euiccSpecVersion;
        object = this.mLock;
        synchronized (object) {
            if (this.mSpecVersion != null) return euiccSpecVersion;
            this.mSpecVersion = euiccSpecVersion;
            return euiccSpecVersion;
        }
    }

    static /* synthetic */ byte[] lambda$authenticateServer$33(byte[] asn1Node) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        asn1Node = EuiccCard.parseResponse((byte[])asn1Node);
        if (!asn1Node.hasChild(161, new int[]{2})) {
            return asn1Node.toBytes();
        }
        throw new EuiccCardErrorException(3, asn1Node.getChild(161, new int[]{2}).asInteger());
    }

    static /* synthetic */ void lambda$cancelSession$39(byte[] arrby, int n, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48961).addChildAsBytes(128, arrby).addChildAsInteger(129, n).build().toHex());
    }

    static /* synthetic */ byte[] lambda$cancelSession$40(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return EuiccCard.parseResponseAndCheckSimpleError(arrby, 4).toBytes();
    }

    static /* synthetic */ void lambda$deleteProfile$14(String arrby, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        arrby = IccUtils.bcdToBytes((String)EuiccCard.padTrailingFs((String)arrby));
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48947).addChildAsBytes(90, arrby).build().toHex());
    }

    static /* synthetic */ Void lambda$deleteProfile$15(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult(arrby);
        if (n == 0) {
            return null;
        }
        throw new EuiccCardErrorException(12, n);
    }

    static /* synthetic */ void lambda$disableProfile$6(String arrby, boolean bl, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        arrby = IccUtils.bcdToBytes((String)EuiccCard.padTrailingFs((String)arrby));
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48946).addChild(Asn1Node.newBuilder((int)160).addChildAsBytes(90, arrby)).addChildAsBoolean(129, bl).build().toHex());
    }

    static /* synthetic */ Void lambda$disableProfile$7(String string, byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult((byte[])object);
        if (n != 0) {
            if (n == 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Profile is already disabled, iccid: ");
                ((StringBuilder)object).append(SubscriptionInfo.givePrintableIccid((String)string));
                EuiccCard.logd(((StringBuilder)object).toString());
                return null;
            }
            throw new EuiccCardErrorException(11, n);
        }
        return null;
    }

    static /* synthetic */ void lambda$getAllProfiles$2(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48941).addChildAsBytes(92, Tags.EUICC_PROFILE_TAGS).build().toHex());
    }

    static /* synthetic */ EuiccProfileInfo[] lambda$getAllProfiles$3(byte[] builder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        List list = new Asn1Decoder((byte[])builder).nextNode().getChild(160, new int[0]).getChildren(227);
        int n = list.size();
        EuiccProfileInfo[] arreuiccProfileInfo = new EuiccProfileInfo[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Asn1Node asn1Node = (Asn1Node)list.get(i);
            if (!asn1Node.hasChild(90, new int[0])) {
                EuiccCard.loge("Profile must have an ICCID.");
                continue;
            }
            builder = new EuiccProfileInfo.Builder(EuiccCard.stripTrailingFs(asn1Node.getChild(90, new int[0]).asBytes()));
            EuiccCard.buildProfile(asn1Node, builder);
            arreuiccProfileInfo[n2] = builder.build();
            ++n2;
        }
        return arreuiccProfileInfo;
    }

    static /* synthetic */ void lambda$getDefaultSmdpAddress$18(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48956).build().toHex());
    }

    static /* synthetic */ String lambda$getDefaultSmdpAddress$19(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return EuiccCard.parseResponse(arrby).getChild(128, new int[0]).asString();
    }

    static /* synthetic */ void lambda$getEid$10(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48958).addChildAsBytes(92, new byte[]{90}).build().toHex());
    }

    static /* synthetic */ void lambda$getEuiccChallenge$26(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48942).build().toHex());
    }

    static /* synthetic */ byte[] lambda$getEuiccChallenge$27(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return EuiccCard.parseResponse(arrby).getChild(128, new int[0]).asBytes();
    }

    static /* synthetic */ void lambda$getEuiccInfo1$28(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48928).build().toHex());
    }

    static /* synthetic */ byte[] lambda$getEuiccInfo1$29(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return arrby;
    }

    static /* synthetic */ void lambda$getEuiccInfo2$30(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48930).build().toHex());
    }

    static /* synthetic */ byte[] lambda$getEuiccInfo2$31(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return arrby;
    }

    static /* synthetic */ void lambda$getProfile$4(String string, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48941).addChild(Asn1Node.newBuilder((int)160).addChildAsBytes(90, IccUtils.bcdToBytes((String)EuiccCard.padTrailingFs(string))).build()).addChildAsBytes(92, Tags.EUICC_PROFILE_TAGS).build().toHex());
    }

    static /* synthetic */ EuiccProfileInfo lambda$getProfile$5(byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        if ((object = new Asn1Decoder((byte[])object).nextNode().getChild(160, new int[0]).getChildren(227)).isEmpty()) {
            return null;
        }
        object = (Asn1Node)object.get(0);
        EuiccProfileInfo.Builder builder = new EuiccProfileInfo.Builder(EuiccCard.stripTrailingFs(object.getChild(90, new int[0]).asBytes()));
        EuiccCard.buildProfile((Asn1Node)object, builder);
        return builder.build();
    }

    static /* synthetic */ void lambda$getRulesAuthTable$24(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48963).build().toHex());
    }

    static /* synthetic */ EuiccRulesAuthTable lambda$getRulesAuthTable$25(byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        object = EuiccCard.parseResponse((byte[])object).getChildren(160);
        EuiccRulesAuthTable.Builder builder = new EuiccRulesAuthTable.Builder(object.size());
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            Asn1Node asn1Node = (Asn1Node)object.get(i);
            List list = asn1Node.getChild(48, new int[]{161}).getChildren();
            int n2 = list.size();
            CarrierIdentifier[] arrcarrierIdentifier = new CarrierIdentifier[n2];
            for (int j = 0; j < n2; ++j) {
                arrcarrierIdentifier[j] = EuiccCard.buildCarrierIdentifier((Asn1Node)list.get(j));
            }
            builder.add(asn1Node.getChild(48, new int[]{128}).asBits(), Arrays.asList(arrcarrierIdentifier), asn1Node.getChild(48, new int[]{130}).asBits());
        }
        return builder.build();
    }

    static /* synthetic */ void lambda$getSmdsAddress$20(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48956).build().toHex());
    }

    static /* synthetic */ String lambda$getSmdsAddress$21(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return EuiccCard.parseResponse(arrby).getChild(129, new int[0]).asString();
    }

    static /* synthetic */ void lambda$getSpecVersion$0(RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
    }

    static /* synthetic */ void lambda$listNotifications$41(int n, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48936).addChildAsBits(129, n).build().toHex());
    }

    static /* synthetic */ EuiccNotification[] lambda$listNotifications$42(byte[] arrobject) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        List list = EuiccCard.parseResponseAndCheckSimpleError(arrobject, 6).getChild(160, new int[0]).getChildren();
        arrobject = new EuiccNotification[list.size()];
        for (int i = 0; i < arrobject.length; ++i) {
            arrobject[i] = (byte)EuiccCard.createNotification((Asn1Node)list.get(i));
        }
        return arrobject;
    }

    static /* synthetic */ void lambda$loadBoundProfilePackage$36(byte[] asn1Node, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n;
        Asn1Node asn1Node2 = new Asn1Decoder((byte[])asn1Node).nextNode();
        Asn1Node asn1Node3 = asn1Node2.getChild(48931, new int[0]);
        Asn1Node asn1Node4 = asn1Node2.getChild(160, new int[0]);
        asn1Node = asn1Node2.getChild(161, new int[0]);
        List list = asn1Node.getChildren(136);
        Asn1Node asn1Node5 = asn1Node2.getChild(163, new int[0]);
        List list2 = asn1Node5.getChildren(134);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(asn1Node2.getHeadAsHex());
        stringBuilder.append(asn1Node3.toHex());
        requestBuilder.addStoreData(stringBuilder.toString());
        requestBuilder.addStoreData(asn1Node4.toHex());
        requestBuilder.addStoreData(asn1Node.getHeadAsHex());
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            requestBuilder.addStoreData(((Asn1Node)list.get(n)).toHex());
        }
        if (asn1Node2.hasChild(162, new int[0])) {
            requestBuilder.addStoreData(asn1Node2.getChild(162, new int[0]).toHex());
        }
        requestBuilder.addStoreData(asn1Node5.getHeadAsHex());
        n2 = list2.size();
        for (n = 0; n < n2; ++n) {
            requestBuilder.addStoreData(((Asn1Node)list2.get(n)).toHex());
        }
    }

    static /* synthetic */ byte[] lambda$loadBoundProfilePackage$37(byte[] asn1Node) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        asn1Node = EuiccCard.parseResponse((byte[])asn1Node);
        if (!asn1Node.hasChild(48935, new int[]{162, 161, 129})) {
            return asn1Node.toBytes();
        }
        asn1Node = asn1Node.getChild(48935, new int[]{162, 161, 129});
        throw new EuiccCardErrorException(5, asn1Node.asInteger(), asn1Node);
    }

    static /* synthetic */ boolean lambda$loadBoundProfilePackage$38(IccIoResult arrby) {
        arrby = arrby.payload;
        if (arrby != null && arrby.length > 2 && ((arrby[0] & 255) << 8 | arrby[1] & 255) == 48951) {
            EuiccCard.logd("loadBoundProfilePackage failed due to an early error.");
            return false;
        }
        return true;
    }

    static /* synthetic */ void lambda$prepareDownload$34(byte[] builder, byte[] arrby, byte[] arrby2, byte[] arrby3, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        builder = Asn1Node.newBuilder((int)48929).addChild(new Asn1Decoder((byte[])builder).nextNode()).addChild(new Asn1Decoder(arrby).nextNode());
        if (arrby2 != null) {
            builder.addChildAsBytes(4, arrby2);
        }
        requestBuilder.addStoreData(builder.addChild(new Asn1Decoder(arrby3).nextNode()).build().toHex());
    }

    static /* synthetic */ byte[] lambda$prepareDownload$35(byte[] asn1Node) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        asn1Node = EuiccCard.parseResponse((byte[])asn1Node);
        if (!asn1Node.hasChild(161, new int[]{2})) {
            return asn1Node.toBytes();
        }
        throw new EuiccCardErrorException(2, asn1Node.getChild(161, new int[]{2}).asInteger());
    }

    static /* synthetic */ void lambda$removeNotificationFromList$47(int n, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48944).addChildAsInteger(128, n).build().toHex());
    }

    static /* synthetic */ Void lambda$removeNotificationFromList$48(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult(arrby);
        if (n != 0 && n != 1) {
            throw new EuiccCardErrorException(9, n);
        }
        return null;
    }

    static /* synthetic */ void lambda$resetMemory$16(int n, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48948).addChildAsBits(130, n).build().toHex());
    }

    static /* synthetic */ Void lambda$resetMemory$17(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult(arrby);
        if (n != 0 && n != 1) {
            throw new EuiccCardErrorException(13, n);
        }
        return null;
    }

    static /* synthetic */ void lambda$retrieveNotification$45(int n, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48939).addChild(Asn1Node.newBuilder((int)160).addChildAsInteger(128, n)).build().toHex());
    }

    static /* synthetic */ EuiccNotification lambda$retrieveNotification$46(byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        if ((object = EuiccCard.parseResponseAndCheckSimpleError((byte[])object, 8).getChild(160, new int[0]).getChildren()).size() > 0) {
            return EuiccCard.createNotification((Asn1Node)object.get(0));
        }
        return null;
    }

    static /* synthetic */ void lambda$retrieveNotificationList$43(int n, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48939).addChild(Asn1Node.newBuilder((int)160).addChildAsBits(129, n)).build().toHex());
    }

    static /* synthetic */ EuiccNotification[] lambda$retrieveNotificationList$44(byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        if ((object = EuiccCard.parseResponse((byte[])object)).hasChild(129, new int[0])) {
            int n = object.getChild(129, new int[0]).asInteger();
            if (n == 1) {
                return new EuiccNotification[0];
            }
            throw new EuiccCardErrorException(8, n);
        }
        object = object.getChild(160, new int[0]).getChildren();
        EuiccNotification[] arreuiccNotification = new EuiccNotification[object.size()];
        for (int i = 0; i < arreuiccNotification.length; ++i) {
            arreuiccNotification[i] = EuiccCard.createNotification((Asn1Node)object.get(i));
        }
        return arreuiccNotification;
    }

    static /* synthetic */ void lambda$sendApdu$50(AsyncResultCallback asyncResultCallback, Throwable throwable) {
        asyncResultCallback.onException(new EuiccCardException("Cannot send APDU.", throwable));
    }

    static /* synthetic */ void lambda$sendApdu$51(AsyncResultCallback asyncResultCallback, Throwable throwable) {
        asyncResultCallback.onException(new EuiccCardException("Cannot send APDU.", throwable));
    }

    static /* synthetic */ void lambda$sendApduWithSimResetErrorWorkaround$52(AsyncResultCallback asyncResultCallback, Throwable throwable) {
        if (throwable instanceof ApduException && ((ApduException)throwable).getApduStatus() == 28416) {
            EuiccCard.logi("Sim is refreshed after disabling profile, no response got.");
            asyncResultCallback.onResult(null);
        } else {
            asyncResultCallback.onException(new EuiccCardException("Cannot send APDU.", throwable));
        }
    }

    static /* synthetic */ void lambda$setDefaultSmdpAddress$22(String string, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48959).addChildAsString(128, string).build().toHex());
    }

    static /* synthetic */ Void lambda$setDefaultSmdpAddress$23(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult(arrby);
        if (n == 0) {
            return null;
        }
        throw new EuiccCardErrorException(14, n);
    }

    static /* synthetic */ void lambda$setNickname$12(String string, String string2, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48937).addChildAsBytes(90, IccUtils.bcdToBytes((String)EuiccCard.padTrailingFs(string))).addChildAsString(144, string2).build().toHex());
    }

    static /* synthetic */ Void lambda$setNickname$13(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult(arrby);
        if (n == 0) {
            return null;
        }
        throw new EuiccCardErrorException(7, n);
    }

    static /* synthetic */ void lambda$switchToProfile$8(String arrby, boolean bl, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        arrby = IccUtils.bcdToBytes((String)EuiccCard.padTrailingFs((String)arrby));
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48945).addChild(Asn1Node.newBuilder((int)160).addChildAsBytes(90, arrby)).addChildAsBoolean(129, bl).build().toHex());
    }

    static /* synthetic */ Void lambda$switchToProfile$9(String string, byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        int n = EuiccCard.parseSimpleResult((byte[])object);
        if (n != 0) {
            if (n == 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Profile is already enabled, iccid: ");
                ((StringBuilder)object).append(SubscriptionInfo.givePrintableIccid((String)string));
                EuiccCard.logd(((StringBuilder)object).toString());
                return null;
            }
            throw new EuiccCardErrorException(10, n);
        }
        return null;
    }

    private static void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private static void loge(String string, Throwable throwable) {
        Rlog.e((String)LOG_TAG, (String)string, (Throwable)throwable);
    }

    private static void logi(String string) {
        Rlog.i((String)LOG_TAG, (String)string);
    }

    private RequestProvider newRequestProvider(ApduRequestBuilder apduRequestBuilder) {
        return new _$$Lambda$EuiccCard$SiGT87lDw1xXD_7PyidTGv5wxfQ(this, apduRequestBuilder);
    }

    private static String padTrailingFs(String string) {
        CharSequence charSequence = string;
        if (!TextUtils.isEmpty((CharSequence)string)) {
            charSequence = string;
            if (string.length() < 20) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(new String(new char[20 - string.length()]).replace('\u0000', 'F'));
                charSequence = ((StringBuilder)charSequence).toString();
            }
        }
        return charSequence;
    }

    private static Asn1Node parseResponse(byte[] asn1Decoder) throws EuiccCardException, InvalidAsn1DataException {
        if ((asn1Decoder = new Asn1Decoder((byte[])asn1Decoder)).hasNextNode()) {
            return asn1Decoder.nextNode();
        }
        throw new EuiccCardException("Empty response", null);
    }

    private static Asn1Node parseResponseAndCheckSimpleError(byte[] asn1Node, int n) throws EuiccCardException, InvalidAsn1DataException, TagNotFoundException {
        if (!(asn1Node = EuiccCard.parseResponse((byte[])asn1Node)).hasChild(129, new int[0])) {
            return asn1Node;
        }
        throw new EuiccCardErrorException(n, asn1Node.getChild(129, new int[0]).asInteger());
    }

    private static int parseSimpleResult(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return EuiccCard.parseResponse(arrby).getChild(128, new int[0]).asInteger();
    }

    private <T> void sendApdu(RequestProvider requestProvider, final ApduResponseHandler<T> apduResponseHandler, final ApduExceptionHandler apduExceptionHandler, final ApduIntermediateResultHandler apduIntermediateResultHandler, final AsyncResultCallback<T> asyncResultCallback, Handler handler) {
        this.mApduSender.send(requestProvider, new ApduSenderResultCallback(){

            @Override
            public void onException(Throwable throwable) {
                apduExceptionHandler.handleException(throwable);
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    asyncResultCallback.onResult(apduResponseHandler.handleResult(arrby));
                }
                catch (InvalidAsn1DataException | TagNotFoundException throwable) {
                    AsyncResultCallback asyncResultCallback2 = asyncResultCallback;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot parse response: ");
                    stringBuilder.append(IccUtils.bytesToHexString((byte[])arrby));
                    asyncResultCallback2.onException(new EuiccCardException(stringBuilder.toString(), throwable));
                }
                catch (EuiccCardException euiccCardException) {
                    asyncResultCallback.onException(euiccCardException);
                }
            }

            @Override
            public boolean shouldContinueOnIntermediateResult(IccIoResult iccIoResult) {
                ApduIntermediateResultHandler apduIntermediateResultHandler2 = apduIntermediateResultHandler;
                if (apduIntermediateResultHandler2 == null) {
                    return true;
                }
                return apduIntermediateResultHandler2.shouldContinue(iccIoResult);
            }
        }, handler);
    }

    private <T> void sendApdu(RequestProvider requestProvider, ApduResponseHandler<T> apduResponseHandler, ApduIntermediateResultHandler apduIntermediateResultHandler, AsyncResultCallback<T> asyncResultCallback, Handler handler) {
        this.sendApdu(requestProvider, apduResponseHandler, new _$$Lambda$EuiccCard$519fif8g_uQDyFgB0QDuhelpNTM(asyncResultCallback), apduIntermediateResultHandler, asyncResultCallback, handler);
    }

    private <T> void sendApdu(RequestProvider requestProvider, ApduResponseHandler<T> apduResponseHandler, AsyncResultCallback<T> asyncResultCallback, Handler handler) {
        this.sendApdu(requestProvider, apduResponseHandler, new _$$Lambda$EuiccCard$Y4to2oZTgOnUA9QDesgeA5MRLr4(asyncResultCallback), null, asyncResultCallback, handler);
    }

    private void sendApduWithSimResetErrorWorkaround(RequestProvider requestProvider, ApduResponseHandler<Void> apduResponseHandler, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApdu(requestProvider, apduResponseHandler, new _$$Lambda$EuiccCard$krunAJLFPj0Co1L7ROlSfW13UNg(asyncResultCallback), null, asyncResultCallback, handler);
    }

    private static String stripTrailingFs(byte[] arrby) {
        return IccUtils.stripTrailingFs((String)IccUtils.bchToString((byte[])arrby, (int)0, (int)arrby.length));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public void addDeviceCapability(Asn1Node.Builder var1_1, String var2_3) {
        block23 : {
            var3_4 = var2_3.split(",");
            var4_5 = var3_4.length;
            var5_6 = 2;
            if (var4_5 != 2) {
                var1_1 = new StringBuilder();
                var1_1.append("Invalid device capability item: ");
                var1_1.append(Arrays.toString(var3_4));
                EuiccCard.loge(var1_1.toString());
                return;
            }
            var2_3 = var3_4[0].trim();
            try {
                var4_5 = Integer.parseInt(var3_4[1].trim());
            }
            catch (NumberFormatException var1_2) {
                EuiccCard.loge("Invalid device capability version number.", var1_2);
                return;
            }
            var3_4 = new byte[]{Integer.valueOf(var4_5).byteValue(), (byte)(false ? 1 : 0), (byte)(false ? 1 : 0)};
            switch (var2_3.hashCode()) {
                case 111620384: {
                    if (!var2_3.equals("utran")) break;
                    var5_6 = 1;
                    break block23;
                }
                case 96487011: {
                    if (!var2_3.equals("ehrpd")) break;
                    var5_6 = 4;
                    break block23;
                }
                case 3211390: {
                    if (!var2_3.equals("hrpd")) break;
                    var5_6 = 3;
                    break block23;
                }
                case 108971: {
                    if (!var2_3.equals("nfc")) break;
                    var5_6 = 6;
                    break block23;
                }
                case 102657: {
                    if (!var2_3.equals("gsm")) break;
                    var5_6 = 0;
                    break block23;
                }
                case 98781: {
                    if (!var2_3.equals("crl")) break;
                    var5_6 = 7;
                    break block23;
                }
                case -1291802661: {
                    if (!var2_3.equals("eutran")) break;
                    var5_6 = 5;
                    break block23;
                }
                case -1364987172: {
                    if (var2_3.equals("cdma1x")) break block23;
                }
            }
            ** break;
lbl51: // 1 sources:
            var5_6 = -1;
        }
        switch (var5_6) {
            default: {
                var1_1 = new StringBuilder();
                var1_1.append("Invalid device capability name: ");
                var1_1.append(var2_3);
                EuiccCard.loge(var1_1.toString());
                return;
            }
            case 7: {
                var1_1.addChildAsBytes(135, (byte[])var3_4);
                return;
            }
            case 6: {
                var1_1.addChildAsBytes(134, (byte[])var3_4);
                return;
            }
            case 5: {
                var1_1.addChildAsBytes(133, (byte[])var3_4);
                return;
            }
            case 4: {
                var1_1.addChildAsBytes(132, (byte[])var3_4);
                return;
            }
            case 3: {
                var1_1.addChildAsBytes(131, (byte[])var3_4);
                return;
            }
            case 2: {
                var1_1.addChildAsBytes(130, (byte[])var3_4);
                return;
            }
            case 1: {
                var1_1.addChildAsBytes(129, (byte[])var3_4);
                return;
            }
            case 0: 
        }
        var1_1.addChildAsBytes(128, (byte[])var3_4);
    }

    public void authenticateServer(String string, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$dXiSnJocvC7r6HwRUJlZI7Qnleo(this, string, arrby, arrby2, arrby3, arrby4)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$MRlmz2j6osUyi5hGvD3j9D4Tsrg.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void cancelSession(byte[] arrby, int n, AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$ep5FQKIEACJvfaaqyTp6OGIepAc(arrby, n)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$oIgPJRYTuRtjfuUxIzR_B282KsA.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void deleteProfile(String string, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$MoRNAw8O6kYG_c2AJkozlJwO2NM(string)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$6M0Cvkh43ith8i9YF2YZNZ_YvOM.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void disableProfile(String string, boolean bl, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApduWithSimResetErrorWorkaround(this.newRequestProvider(new _$$Lambda$EuiccCard$0N6_V0pqmnTfKxVMU5IUj_svXDA(string, bl)), new _$$Lambda$EuiccCard$Rc41c7zRLip3RrHuKqZ_Sv7h8wI(string), asyncResultCallback, handler);
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        super.dump((FileDescriptor)object, printWriter, arrstring);
        printWriter.println("EuiccCard:");
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEid=");
        ((StringBuilder)object).append(this.mEid);
        printWriter.println(((StringBuilder)object).toString());
    }

    public void getAllProfiles(AsyncResultCallback<EuiccProfileInfo[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$toN63DWLt72dzp0WCl28UOMSmzE.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$B99bQ_FkeD9OwB8_qTcKScitlrM.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void getDefaultSmdpAddress(AsyncResultCallback<String> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$3LRPBN7jGieBA4qKqsiYoON1xT0.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$Qej04bOzl5rj_T7NIjvbnJX7b2s.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    protected byte[] getDeviceId() {
        Phone phone = PhoneFactory.getPhone(this.getPhoneId());
        if (phone == null) {
            return new byte[8];
        }
        return EuiccCard.getDeviceId(phone.getDeviceId(), this.mSpecVersion);
    }

    public String getEid() {
        return this.mEid;
    }

    public void getEid(AsyncResultCallback<String> asyncResultCallback, Handler handler) {
        if (this.mEid != null) {
            AsyncResultHelper.returnResult(this.mEid, asyncResultCallback, handler);
            return;
        }
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$HBn5KBGylwjLqIEm3rBhXnUU_8U.INSTANCE), new _$$Lambda$EuiccCard$okradEAowCk8rNBK1OaJIA6l6eA(this), asyncResultCallback, handler);
    }

    public void getEuiccChallenge(AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$8wofF_Li1V6a8rJQc_M2IGeJ26E.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$AGpR_ArLREPF7xVOCf0sgHwbDtA.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void getEuiccInfo1(AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$WE7TDTe507w4dBh1UvCgBgp3xVk.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$hCCBghNOkOgvjeYe8LWQml6I9Ow.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void getEuiccInfo2(AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$UxQlywWQ3cqQ7G7vS2KuMEwtYro.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$X8OWFy8Bi7TMh117x6vCBqzSqVY.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public final void getProfile(String string, AsyncResultCallback<EuiccProfileInfo> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$QGtQZCF6KEnI_x59_tp1eo8mWew(string)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$TTvsStUIyUFrPpvGTlsjBCy3NyM.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    protected Resources getResources() {
        return Resources.getSystem();
    }

    public void getRulesAuthTable(AsyncResultCallback<EuiccRulesAuthTable> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$AWltG4uFbHn2Xq7ZPpU3U1qOqVM.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$IMmMA3gSh1g8aaHsYtCih61EKmo.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void getSmdsAddress(AsyncResultCallback<String> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$tPSWjOKtm9yQg21kHmLX49PPf_4.INSTANCE), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$u2_6zCuoZP9CLxIS2g4BREHHECI.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void getSpecVersion(AsyncResultCallback<EuiccSpecVersion> asyncResultCallback, Handler handler) {
        EuiccSpecVersion euiccSpecVersion = this.mSpecVersion;
        if (euiccSpecVersion != null) {
            AsyncResultHelper.returnResult(euiccSpecVersion, asyncResultCallback, handler);
            return;
        }
        this.sendApdu(this.newRequestProvider((ApduRequestBuilder)_$$Lambda$EuiccCard$HgCDP54gCppk81aqhuCG0YGJWEc.INSTANCE), new _$$Lambda$EuiccCard$U1ORE3W_o_HdXWc6N59UnRQmLQI(this), asyncResultCallback, handler);
    }

    public /* synthetic */ void lambda$authenticateServer$32$EuiccCard(String string, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, RequestBuilder requestBuilder) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        byte[] arrby5 = this.getDeviceId();
        byte[] arrby6 = new byte[4];
        System.arraycopy(arrby5, 0, arrby6, 0, 4);
        Asn1Node.Builder builder = Asn1Node.newBuilder((int)161);
        String[] arrstring = this.getResources().getStringArray(17236064);
        if (arrstring != null) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                this.addDeviceCapability(builder, arrstring[i]);
            }
        } else {
            EuiccCard.logd("No device capabilities set.");
        }
        string = Asn1Node.newBuilder((int)160).addChildAsString(128, string).addChild(Asn1Node.newBuilder((int)161).addChildAsBytes(128, arrby6).addChild(builder).addChildAsBytes(130, arrby5));
        requestBuilder.addStoreData(Asn1Node.newBuilder((int)48952).addChild(new Asn1Decoder(arrby).nextNode()).addChild(new Asn1Decoder(arrby2).nextNode()).addChild(new Asn1Decoder(arrby3).nextNode()).addChild(new Asn1Decoder(arrby4).nextNode()).addChild((Asn1Node.Builder)string).build().toHex());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ String lambda$getEid$11$EuiccCard(byte[] object) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        String string = IccUtils.bytesToHexString((byte[])EuiccCard.parseResponse(object).getChild(90, new int[0]).asBytes());
        object = this.mLock;
        synchronized (object) {
            this.mEid = string;
            this.mCardId = string;
            return string;
        }
    }

    public /* synthetic */ EuiccSpecVersion lambda$getSpecVersion$1$EuiccCard(byte[] arrby) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException {
        return this.mSpecVersion;
    }

    public /* synthetic */ void lambda$newRequestProvider$49$EuiccCard(ApduRequestBuilder object, byte[] object2, RequestBuilder object3) throws Throwable {
        if ((object2 = this.getOrExtractSpecVersion((byte[])object2)) != null) {
            try {
                if (((EuiccSpecVersion)object2).compareTo(SGP22_V_2_0) >= 0) {
                    object.build((RequestBuilder)object3);
                    return;
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("eUICC spec version is unsupported: ");
                ((StringBuilder)object3).append(object2);
                object = new EuiccCardException(((StringBuilder)object3).toString());
                throw object;
            }
            catch (InvalidAsn1DataException | TagNotFoundException throwable) {
                throw new EuiccCardException("Cannot parse ASN1 to build request.", throwable);
            }
        }
        throw new EuiccCardException("Cannot get eUICC spec version.");
    }

    public void listNotifications(int n, AsyncResultCallback<EuiccNotification[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$00j_sPLzMkCJBnrpRWJA8rfmUIY(n)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$gM_702GsygHKxB8F__MrSarNKjg.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void loadBoundProfilePackage(byte[] arrby, AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$XDNTzAU_9I92HztVAJQr4NXR3DU(arrby)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$g0LHcTcRLtF0WE8Tyv2BvipGgrM.INSTANCE, (ApduIntermediateResultHandler)_$$Lambda$EuiccCard$dwMNgp0nb8jQ75klP_URUuDP17U.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    protected void loadEidAndNotifyRegistrants() {
        Handler handler = new Handler();
        this.getEid(new AsyncResultCallback<String>(){

            @Override
            public void onException(Throwable throwable) {
                if (EuiccCard.this.mEidReadyRegistrants != null) {
                    EuiccCard.this.mEidReadyRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
                }
                EuiccCard.this.mEid = "";
                EuiccCard.this.mCardId = "";
                Rlog.e((String)EuiccCard.LOG_TAG, (String)"Failed loading eid", (Throwable)throwable);
            }

            @Override
            public void onResult(String string) {
                if (EuiccCard.this.mEidReadyRegistrants != null) {
                    EuiccCard.this.mEidReadyRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
                }
            }
        }, handler);
    }

    public void prepareDownload(byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, AsyncResultCallback<byte[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$5wK_r0z9fLtA1ZRVlbk3WfOYXJI(arrby2, arrby3, arrby, arrby4)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$v0S5B6MBAksDVSST9c1nk2Movvk.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void registerForEidReady(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        if (this.mEid != null) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        } else {
            if (this.mEidReadyRegistrants == null) {
                this.mEidReadyRegistrants = new RegistrantList();
            }
            this.mEidReadyRegistrants.add((Registrant)handler);
        }
    }

    public void removeNotificationFromList(int n, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$iHmYnivZKaYKk9UB26Y_pNgqjVU(n)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$WoM2ziweCgrYxAgllxpAtHF_3Es.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void resetMemory(int n, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApduWithSimResetErrorWorkaround(this.newRequestProvider(new _$$Lambda$EuiccCard$Wx9UmYdMwRy23Rf6Vd7b2aSx6S8(n)), (ApduResponseHandler<Void>)_$$Lambda$EuiccCard$0NUjmK32_r6146hGb0RCJUAfiOg.INSTANCE, asyncResultCallback, handler);
    }

    public void retrieveNotification(int n, AsyncResultCallback<EuiccNotification> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$EcGEDb4lqNEz5YyXQfIgXTUpv_c(n)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$ADB4BKXCYw8oHd_aqHgRFEm7vGg.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void retrieveNotificationList(int n, AsyncResultCallback<EuiccNotification[]> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$DsQXeVrINumCqiGAAeDJPNFEix0(n)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$NcqG_zW56i_tsv86TpwlBqIvg4U.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void setDefaultSmdpAddress(String string, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$FbRMt6fKnYLkYt6oi5qhs1ZyEvc(string)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$wgj93ukgzqjttFzrDLqGFk_Sd5A.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void setNickname(String string, String string2, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApdu(this.newRequestProvider(new _$$Lambda$EuiccCard$_VOB5FQfE7RUMgpmr8bK_j3CsUA(string, string2)), (ApduResponseHandler<T>)_$$Lambda$EuiccCard$4gL9ssytVrnit44qHJ_7_Uy6ZOQ.INSTANCE, (AsyncResultCallback<T>)asyncResultCallback, handler);
    }

    public void switchToProfile(String string, boolean bl, AsyncResultCallback<Void> asyncResultCallback, Handler handler) {
        this.sendApduWithSimResetErrorWorkaround(this.newRequestProvider(new _$$Lambda$EuiccCard$AYHfF2w_VlO00s9p_djcPJl_1no(string, bl)), new _$$Lambda$EuiccCard$fcz5l0a6JlSxs8MXCst7wXG4bUc(string), asyncResultCallback, handler);
    }

    public void unregisterForEidReady(Handler handler) {
        RegistrantList registrantList = this.mEidReadyRegistrants;
        if (registrantList != null) {
            registrantList.remove(handler);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void update(Context context, CommandsInterface commandsInterface, IccCardStatus iccCardStatus) {
        Object object = this.mLock;
        synchronized (object) {
            if (!TextUtils.isEmpty((CharSequence)iccCardStatus.eid)) {
                this.mEid = iccCardStatus.eid;
            }
            super.update(context, commandsInterface, iccCardStatus);
            return;
        }
    }

    @Override
    protected void updateCardId() {
        if (TextUtils.isEmpty((CharSequence)this.mEid)) {
            super.updateCardId();
        } else {
            this.mCardId = this.mEid;
        }
    }

    private static interface ApduExceptionHandler {
        public void handleException(Throwable var1);
    }

    private static interface ApduIntermediateResultHandler {
        public boolean shouldContinue(IccIoResult var1);
    }

    private static interface ApduRequestBuilder {
        public void build(RequestBuilder var1) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException;
    }

    private static interface ApduResponseHandler<T> {
        public T handleResult(byte[] var1) throws EuiccCardException, TagNotFoundException, InvalidAsn1DataException;
    }

}

