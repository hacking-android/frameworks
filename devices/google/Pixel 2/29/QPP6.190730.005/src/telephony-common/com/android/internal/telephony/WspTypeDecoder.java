/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import java.io.Serializable;
import java.util.HashMap;

public class WspTypeDecoder {
    public static final String CONTENT_TYPE_B_MMS = "application/vnd.wap.mms-message";
    public static final String CONTENT_TYPE_B_PUSH_CO = "application/vnd.wap.coc";
    public static final String CONTENT_TYPE_B_PUSH_SYNCML_NOTI = "application/vnd.syncml.notification";
    public static final int PARAMETER_ID_X_WAP_APPLICATION_ID = 47;
    public static final int PDU_TYPE_CONFIRMED_PUSH = 7;
    public static final int PDU_TYPE_PUSH = 6;
    private static final int Q_VALUE = 0;
    private static final int WAP_PDU_LENGTH_QUOTE = 31;
    private static final int WAP_PDU_SHORT_LENGTH_MAX = 30;
    private static final HashMap<Integer, String> WELL_KNOWN_MIME_TYPES = new HashMap();
    private static final HashMap<Integer, String> WELL_KNOWN_PARAMETERS = new HashMap();
    HashMap<String, String> mContentParameters;
    int mDataLength;
    String mStringValue;
    long mUnsigned32bit;
    @UnsupportedAppUsage
    byte[] mWspData;

    static {
        Serializable serializable = WELL_KNOWN_MIME_TYPES;
        Integer n = 0;
        serializable.put(n, "*/*");
        Serializable serializable2 = WELL_KNOWN_MIME_TYPES;
        serializable = 1;
        serializable2.put((Integer)serializable, "text/*");
        Serializable serializable3 = WELL_KNOWN_MIME_TYPES;
        serializable2 = 2;
        serializable3.put((Integer)serializable2, "text/html");
        Serializable serializable4 = WELL_KNOWN_MIME_TYPES;
        serializable3 = 3;
        serializable4.put((Integer)serializable3, "text/plain");
        WELL_KNOWN_MIME_TYPES.put(4, "text/x-hdml");
        WELL_KNOWN_MIME_TYPES.put(5, "text/x-ttml");
        WELL_KNOWN_MIME_TYPES.put(6, "text/x-vCalendar");
        Serializable serializable5 = WELL_KNOWN_MIME_TYPES;
        serializable4 = 7;
        serializable5.put((Integer)serializable4, "text/x-vCard");
        Serializable serializable6 = WELL_KNOWN_MIME_TYPES;
        serializable5 = 8;
        serializable6.put((Integer)serializable5, "text/vnd.wap.wml");
        HashMap<Integer, String> hashMap = WELL_KNOWN_MIME_TYPES;
        serializable6 = 9;
        hashMap.put((Integer)serializable6, "text/vnd.wap.wmlscript");
        WELL_KNOWN_MIME_TYPES.put(10, "text/vnd.wap.wta-event");
        WELL_KNOWN_MIME_TYPES.put(11, "multipart/*");
        WELL_KNOWN_MIME_TYPES.put(12, "multipart/mixed");
        WELL_KNOWN_MIME_TYPES.put(13, "multipart/form-data");
        WELL_KNOWN_MIME_TYPES.put(14, "multipart/byterantes");
        WELL_KNOWN_MIME_TYPES.put(15, "multipart/alternative");
        WELL_KNOWN_MIME_TYPES.put(16, "application/*");
        WELL_KNOWN_MIME_TYPES.put(17, "application/java-vm");
        WELL_KNOWN_MIME_TYPES.put(18, "application/x-www-form-urlencoded");
        WELL_KNOWN_MIME_TYPES.put(19, "application/x-hdmlc");
        WELL_KNOWN_MIME_TYPES.put(20, "application/vnd.wap.wmlc");
        WELL_KNOWN_MIME_TYPES.put(21, "application/vnd.wap.wmlscriptc");
        WELL_KNOWN_MIME_TYPES.put(22, "application/vnd.wap.wta-eventc");
        WELL_KNOWN_MIME_TYPES.put(23, "application/vnd.wap.uaprof");
        WELL_KNOWN_MIME_TYPES.put(24, "application/vnd.wap.wtls-ca-certificate");
        WELL_KNOWN_MIME_TYPES.put(25, "application/vnd.wap.wtls-user-certificate");
        WELL_KNOWN_MIME_TYPES.put(26, "application/x-x509-ca-cert");
        WELL_KNOWN_MIME_TYPES.put(27, "application/x-x509-user-cert");
        WELL_KNOWN_MIME_TYPES.put(28, "image/*");
        WELL_KNOWN_MIME_TYPES.put(29, "image/gif");
        WELL_KNOWN_MIME_TYPES.put(30, "image/jpeg");
        WELL_KNOWN_MIME_TYPES.put(31, "image/tiff");
        WELL_KNOWN_MIME_TYPES.put(32, "image/png");
        WELL_KNOWN_MIME_TYPES.put(33, "image/vnd.wap.wbmp");
        WELL_KNOWN_MIME_TYPES.put(34, "application/vnd.wap.multipart.*");
        WELL_KNOWN_MIME_TYPES.put(35, "application/vnd.wap.multipart.mixed");
        WELL_KNOWN_MIME_TYPES.put(36, "application/vnd.wap.multipart.form-data");
        WELL_KNOWN_MIME_TYPES.put(37, "application/vnd.wap.multipart.byteranges");
        WELL_KNOWN_MIME_TYPES.put(38, "application/vnd.wap.multipart.alternative");
        WELL_KNOWN_MIME_TYPES.put(39, "application/xml");
        WELL_KNOWN_MIME_TYPES.put(40, "text/xml");
        WELL_KNOWN_MIME_TYPES.put(41, "application/vnd.wap.wbxml");
        WELL_KNOWN_MIME_TYPES.put(42, "application/x-x968-cross-cert");
        WELL_KNOWN_MIME_TYPES.put(43, "application/x-x968-ca-cert");
        WELL_KNOWN_MIME_TYPES.put(44, "application/x-x968-user-cert");
        WELL_KNOWN_MIME_TYPES.put(45, "text/vnd.wap.si");
        WELL_KNOWN_MIME_TYPES.put(46, "application/vnd.wap.sic");
        WELL_KNOWN_MIME_TYPES.put(47, "text/vnd.wap.sl");
        WELL_KNOWN_MIME_TYPES.put(48, "application/vnd.wap.slc");
        WELL_KNOWN_MIME_TYPES.put(49, "text/vnd.wap.co");
        WELL_KNOWN_MIME_TYPES.put(50, CONTENT_TYPE_B_PUSH_CO);
        WELL_KNOWN_MIME_TYPES.put(51, "application/vnd.wap.multipart.related");
        WELL_KNOWN_MIME_TYPES.put(52, "application/vnd.wap.sia");
        WELL_KNOWN_MIME_TYPES.put(53, "text/vnd.wap.connectivity-xml");
        WELL_KNOWN_MIME_TYPES.put(54, "application/vnd.wap.connectivity-wbxml");
        WELL_KNOWN_MIME_TYPES.put(55, "application/pkcs7-mime");
        WELL_KNOWN_MIME_TYPES.put(56, "application/vnd.wap.hashed-certificate");
        WELL_KNOWN_MIME_TYPES.put(57, "application/vnd.wap.signed-certificate");
        WELL_KNOWN_MIME_TYPES.put(58, "application/vnd.wap.cert-response");
        WELL_KNOWN_MIME_TYPES.put(59, "application/xhtml+xml");
        WELL_KNOWN_MIME_TYPES.put(60, "application/wml+xml");
        WELL_KNOWN_MIME_TYPES.put(61, "text/css");
        WELL_KNOWN_MIME_TYPES.put(62, CONTENT_TYPE_B_MMS);
        WELL_KNOWN_MIME_TYPES.put(63, "application/vnd.wap.rollover-certificate");
        WELL_KNOWN_MIME_TYPES.put(64, "application/vnd.wap.locc+wbxml");
        WELL_KNOWN_MIME_TYPES.put(65, "application/vnd.wap.loc+xml");
        WELL_KNOWN_MIME_TYPES.put(66, "application/vnd.syncml.dm+wbxml");
        WELL_KNOWN_MIME_TYPES.put(67, "application/vnd.syncml.dm+xml");
        WELL_KNOWN_MIME_TYPES.put(68, CONTENT_TYPE_B_PUSH_SYNCML_NOTI);
        WELL_KNOWN_MIME_TYPES.put(69, "application/vnd.wap.xhtml+xml");
        WELL_KNOWN_MIME_TYPES.put(70, "application/vnd.wv.csp.cir");
        WELL_KNOWN_MIME_TYPES.put(71, "application/vnd.oma.dd+xml");
        WELL_KNOWN_MIME_TYPES.put(72, "application/vnd.oma.drm.message");
        WELL_KNOWN_MIME_TYPES.put(73, "application/vnd.oma.drm.content");
        WELL_KNOWN_MIME_TYPES.put(74, "application/vnd.oma.drm.rights+xml");
        WELL_KNOWN_MIME_TYPES.put(75, "application/vnd.oma.drm.rights+wbxml");
        WELL_KNOWN_MIME_TYPES.put(76, "application/vnd.wv.csp+xml");
        WELL_KNOWN_MIME_TYPES.put(77, "application/vnd.wv.csp+wbxml");
        WELL_KNOWN_MIME_TYPES.put(78, "application/vnd.syncml.ds.notification");
        WELL_KNOWN_MIME_TYPES.put(79, "audio/*");
        WELL_KNOWN_MIME_TYPES.put(80, "video/*");
        WELL_KNOWN_MIME_TYPES.put(81, "application/vnd.oma.dd2+xml");
        WELL_KNOWN_MIME_TYPES.put(82, "application/mikey");
        WELL_KNOWN_MIME_TYPES.put(83, "application/vnd.oma.dcd");
        WELL_KNOWN_MIME_TYPES.put(84, "application/vnd.oma.dcdc");
        WELL_KNOWN_MIME_TYPES.put(513, "application/vnd.uplanet.cacheop-wbxml");
        WELL_KNOWN_MIME_TYPES.put(514, "application/vnd.uplanet.signal");
        WELL_KNOWN_MIME_TYPES.put(515, "application/vnd.uplanet.alert-wbxml");
        WELL_KNOWN_MIME_TYPES.put(516, "application/vnd.uplanet.list-wbxml");
        WELL_KNOWN_MIME_TYPES.put(517, "application/vnd.uplanet.listcmd-wbxml");
        WELL_KNOWN_MIME_TYPES.put(518, "application/vnd.uplanet.channel-wbxml");
        WELL_KNOWN_MIME_TYPES.put(519, "application/vnd.uplanet.provisioning-status-uri");
        WELL_KNOWN_MIME_TYPES.put(520, "x-wap.multipart/vnd.uplanet.header-set");
        WELL_KNOWN_MIME_TYPES.put(521, "application/vnd.uplanet.bearer-choice-wbxml");
        WELL_KNOWN_MIME_TYPES.put(522, "application/vnd.phonecom.mmc-wbxml");
        WELL_KNOWN_MIME_TYPES.put(523, "application/vnd.nokia.syncset+wbxml");
        WELL_KNOWN_MIME_TYPES.put(524, "image/x-up-wpng");
        WELL_KNOWN_MIME_TYPES.put(768, "application/iota.mmc-wbxml");
        WELL_KNOWN_MIME_TYPES.put(769, "application/iota.mmc-xml");
        WELL_KNOWN_MIME_TYPES.put(770, "application/vnd.syncml+xml");
        WELL_KNOWN_MIME_TYPES.put(771, "application/vnd.syncml+wbxml");
        WELL_KNOWN_MIME_TYPES.put(772, "text/vnd.wap.emn+xml");
        WELL_KNOWN_MIME_TYPES.put(773, "text/calendar");
        WELL_KNOWN_MIME_TYPES.put(774, "application/vnd.omads-email+xml");
        WELL_KNOWN_MIME_TYPES.put(775, "application/vnd.omads-file+xml");
        WELL_KNOWN_MIME_TYPES.put(776, "application/vnd.omads-folder+xml");
        WELL_KNOWN_MIME_TYPES.put(777, "text/directory;profile=vCard");
        WELL_KNOWN_MIME_TYPES.put(778, "application/vnd.wap.emn+wbxml");
        WELL_KNOWN_MIME_TYPES.put(779, "application/vnd.nokia.ipdc-purchase-response");
        WELL_KNOWN_MIME_TYPES.put(780, "application/vnd.motorola.screen3+xml");
        WELL_KNOWN_MIME_TYPES.put(781, "application/vnd.motorola.screen3+gzip");
        WELL_KNOWN_MIME_TYPES.put(782, "application/vnd.cmcc.setting+wbxml");
        WELL_KNOWN_MIME_TYPES.put(783, "application/vnd.cmcc.bombing+wbxml");
        WELL_KNOWN_MIME_TYPES.put(784, "application/vnd.docomo.pf");
        WELL_KNOWN_MIME_TYPES.put(785, "application/vnd.docomo.ub");
        WELL_KNOWN_MIME_TYPES.put(786, "application/vnd.omaloc-supl-init");
        WELL_KNOWN_MIME_TYPES.put(787, "application/vnd.oma.group-usage-list+xml");
        WELL_KNOWN_MIME_TYPES.put(788, "application/oma-directory+xml");
        WELL_KNOWN_MIME_TYPES.put(789, "application/vnd.docomo.pf2");
        WELL_KNOWN_MIME_TYPES.put(790, "application/vnd.oma.drm.roap-trigger+wbxml");
        WELL_KNOWN_MIME_TYPES.put(791, "application/vnd.sbm.mid2");
        WELL_KNOWN_MIME_TYPES.put(792, "application/vnd.wmf.bootstrap");
        WELL_KNOWN_MIME_TYPES.put(793, "application/vnc.cmcc.dcd+xml");
        WELL_KNOWN_MIME_TYPES.put(794, "application/vnd.sbm.cid");
        WELL_KNOWN_MIME_TYPES.put(795, "application/vnd.oma.bcast.provisioningtrigger");
        WELL_KNOWN_PARAMETERS.put(n, "Q");
        WELL_KNOWN_PARAMETERS.put((Integer)serializable, "Charset");
        WELL_KNOWN_PARAMETERS.put((Integer)serializable2, "Level");
        WELL_KNOWN_PARAMETERS.put((Integer)serializable3, "Type");
        WELL_KNOWN_PARAMETERS.put((Integer)serializable4, "Differences");
        WELL_KNOWN_PARAMETERS.put((Integer)serializable5, "Padding");
        WELL_KNOWN_PARAMETERS.put((Integer)serializable6, "Type");
        WELL_KNOWN_PARAMETERS.put(14, "Max-Age");
        WELL_KNOWN_PARAMETERS.put(16, "Secure");
        WELL_KNOWN_PARAMETERS.put(17, "SEC");
        WELL_KNOWN_PARAMETERS.put(18, "MAC");
        WELL_KNOWN_PARAMETERS.put(19, "Creation-date");
        WELL_KNOWN_PARAMETERS.put(20, "Modification-date");
        WELL_KNOWN_PARAMETERS.put(21, "Read-date");
        WELL_KNOWN_PARAMETERS.put(22, "Size");
        WELL_KNOWN_PARAMETERS.put(23, "Name");
        WELL_KNOWN_PARAMETERS.put(24, "Filename");
        WELL_KNOWN_PARAMETERS.put(25, "Start");
        WELL_KNOWN_PARAMETERS.put(26, "Start-info");
        WELL_KNOWN_PARAMETERS.put(27, "Comment");
        WELL_KNOWN_PARAMETERS.put(28, "Domain");
        WELL_KNOWN_PARAMETERS.put(29, "Path");
    }

    @UnsupportedAppUsage
    public WspTypeDecoder(byte[] arrby) {
        this.mWspData = arrby;
    }

    private boolean decodeNoValue(int n) {
        if (this.mWspData[n] == 0) {
            this.mDataLength = 1;
            return true;
        }
        return false;
    }

    private void expandWellKnownMimeType() {
        if (this.mStringValue == null) {
            int n = (int)this.mUnsigned32bit;
            this.mStringValue = WELL_KNOWN_MIME_TYPES.get(n);
        } else {
            this.mUnsigned32bit = -1L;
        }
    }

    private boolean readContentParameters(int n, int n2, int n3) {
        block11 : {
            block14 : {
                int n4;
                CharSequence charSequence;
                String string;
                block13 : {
                    block12 : {
                        if (n2 <= 0) break block11;
                        n4 = this.mWspData[n];
                        if ((n4 & 128) != 0 || n4 <= 31) break block12;
                        this.decodeTokenText(n);
                        string = this.mStringValue;
                        n4 = 0 + this.mDataLength;
                        break block13;
                    }
                    if (!this.decodeIntegerValue(n)) break block14;
                    n4 = 0 + this.mDataLength;
                    int n5 = (int)this.mUnsigned32bit;
                    string = WELL_KNOWN_PARAMETERS.get(n5);
                    charSequence = string;
                    if (string == null) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("unassigned/0x");
                        ((StringBuilder)charSequence).append(Long.toHexString(n5));
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    if (n5 == 0) {
                        if (this.decodeUintvarInteger(n + n4)) {
                            long l = this.mUnsigned32bit;
                            this.mContentParameters.put((String)charSequence, String.valueOf(l));
                            return this.readContentParameters(n + (n4 += this.mDataLength), n2 - n4, n3 + n4);
                        }
                        return false;
                    }
                    string = charSequence;
                }
                if (this.decodeNoValue(n + n4)) {
                    n4 += this.mDataLength;
                    charSequence = null;
                } else if (this.decodeIntegerValue(n + n4)) {
                    n4 += this.mDataLength;
                    charSequence = String.valueOf((int)this.mUnsigned32bit);
                } else {
                    this.decodeTokenText(n + n4);
                    n4 += this.mDataLength;
                    charSequence = this.mStringValue;
                    if (((String)charSequence).startsWith("\"")) {
                        charSequence = ((String)charSequence).substring(1);
                    }
                }
                this.mContentParameters.put(string, (String)charSequence);
                return this.readContentParameters(n + n4, n2 - n4, n3 + n4);
            }
            return false;
        }
        this.mDataLength = n3;
        return true;
    }

    public boolean decodeConstrainedEncoding(int n) {
        if (this.decodeShortInteger(n)) {
            this.mStringValue = null;
            return true;
        }
        return this.decodeExtensionMedia(n);
    }

    public boolean decodeContentLength(int n) {
        return this.decodeIntegerValue(n);
    }

    public boolean decodeContentLocation(int n) {
        return this.decodeTextString(n);
    }

    @UnsupportedAppUsage
    public boolean decodeContentType(int n) {
        int n2;
        int n3;
        block10 : {
            block8 : {
                boolean bl;
                block9 : {
                    this.mContentParameters = new HashMap();
                    try {
                        if (this.decodeValueLength(n)) break block8;
                        bl = this.decodeConstrainedEncoding(n);
                        if (!bl) break block9;
                    }
                    catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        return false;
                    }
                    this.expandWellKnownMimeType();
                }
                return bl;
            }
            n3 = (int)this.mUnsigned32bit;
            n2 = this.getDecodedDataLength();
            if (!this.decodeIntegerValue(n + n2)) break block10;
            this.mDataLength += n2;
            int n4 = this.mDataLength;
            this.mStringValue = null;
            this.expandWellKnownMimeType();
            long l = this.mUnsigned32bit;
            String string = this.mStringValue;
            if (this.readContentParameters(this.mDataLength + n, n3 - (this.mDataLength - n2), 0)) {
                this.mDataLength += n4;
                this.mUnsigned32bit = l;
                this.mStringValue = string;
                return true;
            }
            return false;
        }
        if (this.decodeExtensionMedia(n + n2)) {
            this.mDataLength += n2;
            int n5 = this.mDataLength;
            this.expandWellKnownMimeType();
            long l = this.mUnsigned32bit;
            String string = this.mStringValue;
            if (this.readContentParameters(this.mDataLength + n, n3 - (this.mDataLength - n2), 0)) {
                this.mDataLength += n5;
                this.mUnsigned32bit = l;
                this.mStringValue = string;
                return true;
            }
        }
        return false;
    }

    public boolean decodeExtensionMedia(int n) {
        int n2 = n;
        boolean bl = false;
        this.mDataLength = 0;
        this.mStringValue = null;
        int n3 = this.mWspData.length;
        int n4 = n2;
        if (n2 < n3) {
            bl = true;
            n4 = n2;
        }
        while (n4 < n3 && this.mWspData[n4] != 0) {
            ++n4;
        }
        this.mDataLength = n4 - n + 1;
        this.mStringValue = new String(this.mWspData, n, this.mDataLength - 1);
        return bl;
    }

    @UnsupportedAppUsage
    public boolean decodeIntegerValue(int n) {
        if (this.decodeShortInteger(n)) {
            return true;
        }
        return this.decodeLongInteger(n);
    }

    public boolean decodeLongInteger(int n) {
        int n2 = this.mWspData[n] & 255;
        if (n2 > 30) {
            return false;
        }
        this.mUnsigned32bit = 0L;
        for (int i = 1; i <= n2; ++i) {
            this.mUnsigned32bit = this.mUnsigned32bit << 8 | (long)(this.mWspData[n + i] & 255);
        }
        this.mDataLength = n2 + 1;
        return true;
    }

    @UnsupportedAppUsage
    public boolean decodeShortInteger(int n) {
        byte[] arrby = this.mWspData;
        if ((arrby[n] & 128) == 0) {
            return false;
        }
        this.mUnsigned32bit = arrby[n] & 127;
        this.mDataLength = 1;
        return true;
    }

    @UnsupportedAppUsage
    public boolean decodeTextString(int n) {
        byte[] arrby;
        int n2 = n;
        while ((arrby = this.mWspData)[n2] != 0) {
            ++n2;
        }
        this.mDataLength = n2 - n + 1;
        this.mStringValue = arrby[n] == 127 ? new String(arrby, n + 1, this.mDataLength - 2) : new String(arrby, n, this.mDataLength - 1);
        return true;
    }

    public boolean decodeTokenText(int n) {
        byte[] arrby;
        int n2 = n;
        while ((arrby = this.mWspData)[n2] != 0) {
            ++n2;
        }
        this.mDataLength = n2 - n + 1;
        this.mStringValue = new String(arrby, n, this.mDataLength - 1);
        return true;
    }

    @UnsupportedAppUsage
    public boolean decodeUintvarInteger(int n) {
        byte[] arrby;
        int n2 = n;
        this.mUnsigned32bit = 0L;
        while (((arrby = this.mWspData)[n2] & 128) != 0) {
            if (n2 - n >= 4) {
                return false;
            }
            this.mUnsigned32bit = this.mUnsigned32bit << 7 | (long)(arrby[n2] & 127);
            ++n2;
        }
        this.mUnsigned32bit = this.mUnsigned32bit << 7 | (long)(arrby[n2] & 127);
        this.mDataLength = n2 - n + 1;
        return true;
    }

    @UnsupportedAppUsage
    public boolean decodeValueLength(int n) {
        byte[] arrby = this.mWspData;
        if ((arrby[n] & 255) > 31) {
            return false;
        }
        if (arrby[n] < 31) {
            this.mUnsigned32bit = arrby[n];
            this.mDataLength = 1;
        } else {
            this.decodeUintvarInteger(n + 1);
            ++this.mDataLength;
        }
        return true;
    }

    @UnsupportedAppUsage
    public boolean decodeXWapApplicationId(int n) {
        if (this.decodeIntegerValue(n)) {
            this.mStringValue = null;
            return true;
        }
        return this.decodeTextString(n);
    }

    public boolean decodeXWapContentURI(int n) {
        return this.decodeTextString(n);
    }

    public boolean decodeXWapInitiatorURI(int n) {
        return this.decodeTextString(n);
    }

    @UnsupportedAppUsage
    public HashMap<String, String> getContentParameters() {
        return this.mContentParameters;
    }

    @UnsupportedAppUsage
    public int getDecodedDataLength() {
        return this.mDataLength;
    }

    @UnsupportedAppUsage
    public long getValue32() {
        return this.mUnsigned32bit;
    }

    @UnsupportedAppUsage
    public String getValueString() {
        return this.mStringValue;
    }

    @UnsupportedAppUsage
    public boolean seekXWapApplicationId(int n, int n2) {
        while (n <= n2) {
            int n3;
            block18 : {
                block17 : {
                    block16 : {
                        block15 : {
                            try {
                                if (!this.decodeIntegerValue(n)) break block15;
                                if ((int)this.getValue32() == 47) {
                                    this.mUnsigned32bit = n + 1;
                                    return true;
                                }
                                break block16;
                            }
                            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                                return false;
                            }
                        }
                        if (this.decodeTextString(n)) break block16;
                        return false;
                    }
                    if ((n += this.getDecodedDataLength()) <= n2) break block17;
                    return false;
                }
                n3 = this.mWspData[n];
                if (n3 < 0 || n3 > 30) break block18;
                n += this.mWspData[n] + 1;
                continue;
            }
            if (n3 == 31) {
                block19 : {
                    if (n + 1 >= n2) {
                        return false;
                    }
                    ++n;
                    if (this.decodeUintvarInteger(n)) break block19;
                    return false;
                }
                n += this.getDecodedDataLength();
                continue;
            }
            if (31 < n3 && n3 <= 127) {
                block20 : {
                    if (this.decodeTextString(n)) break block20;
                    return false;
                }
                n3 = this.getDecodedDataLength();
                n += n3;
                continue;
            }
            ++n;
        }
        return false;
    }
}

