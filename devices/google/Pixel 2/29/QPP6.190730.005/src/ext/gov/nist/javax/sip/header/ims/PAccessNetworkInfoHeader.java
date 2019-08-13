/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface PAccessNetworkInfoHeader
extends Parameters,
Header {
    public static final String ADSL = "ADSL";
    public static final String ADSL2 = "ADSL2";
    public static final String ADSL2p = "ADSL2+";
    public static final String GGGPP2_1X = "3GPP2-1X";
    public static final String GGGPP2_1XHRPD = "3GPP2-1XHRPD";
    public static final String GGGPP_CDMA2000 = "3GPP-CDMA2000";
    public static final String GGGPP_GERAN = "3GPP-GERAN";
    public static final String GGGPP_UTRAN_FDD = "3GPP-UTRAN-FDD";
    public static final String GGGPP_UTRAN_TDD = "3GPP-UTRAN-TDD";
    public static final String GSHDSL = "G.SHDSL";
    public static final String HDSL = "HDSL";
    public static final String HDSL2 = "HDSL2";
    public static final String IDSL = "IDSL";
    public static final String IEEE_802_11 = "IEEE-802.11";
    public static final String IEEE_802_11A = "IEEE-802.11a";
    public static final String IEEE_802_11B = "IEEE-802.11b";
    public static final String IEEE_802_11G = "IEEE-802.11g";
    public static final String NAME = "P-Access-Network-Info";
    public static final String RADSL = "RADSL";
    public static final String SDSL = "SDSL";
    public static final String VDSL = "VDSL";

    public String getAccessType();

    public String getCGI3GPP();

    public String getCI3GPP2();

    public String getDSLLocation();

    public Object getExtensionAccessInfo();

    public String getUtranCellID3GPP();

    public void setAccessType(String var1) throws ParseException;

    public void setCGI3GPP(String var1) throws ParseException;

    public void setCI3GPP2(String var1) throws ParseException;

    public void setDSLLocation(String var1) throws ParseException;

    public void setExtensionAccessInfo(Object var1) throws ParseException;

    public void setUtranCellID3GPP(String var1) throws ParseException;
}

