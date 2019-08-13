/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.omadm;

import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.omadm.XMLNode;
import android.net.wifi.hotspot2.omadm.XMLParser;
import android.net.wifi.hotspot2.pps.Credential;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.net.wifi.hotspot2.pps.Policy;
import android.net.wifi.hotspot2.pps.UpdateParameter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xml.sax.SAXException;

public final class PpsMoParser {
    private static final String NODE_AAA_SERVER_TRUST_ROOT = "AAAServerTrustRoot";
    private static final String NODE_ABLE_TO_SHARE = "AbleToShare";
    private static final String NODE_CERTIFICATE_TYPE = "CertificateType";
    private static final String NODE_CERT_SHA256_FINGERPRINT = "CertSHA256Fingerprint";
    private static final String NODE_CERT_URL = "CertURL";
    private static final String NODE_CHECK_AAA_SERVER_CERT_STATUS = "CheckAAAServerCertStatus";
    private static final String NODE_COUNTRY = "Country";
    private static final String NODE_CREATION_DATE = "CreationDate";
    private static final String NODE_CREDENTIAL = "Credential";
    private static final String NODE_CREDENTIAL_PRIORITY = "CredentialPriority";
    private static final String NODE_DATA_LIMIT = "DataLimit";
    private static final String NODE_DIGITAL_CERTIFICATE = "DigitalCertificate";
    private static final String NODE_DOWNLINK_BANDWIDTH = "DLBandwidth";
    private static final String NODE_EAP_METHOD = "EAPMethod";
    private static final String NODE_EAP_TYPE = "EAPType";
    private static final String NODE_EXPIRATION_DATE = "ExpirationDate";
    private static final String NODE_EXTENSION = "Extension";
    private static final String NODE_FQDN = "FQDN";
    private static final String NODE_FQDN_MATCH = "FQDN_Match";
    private static final String NODE_FRIENDLY_NAME = "FriendlyName";
    private static final String NODE_HESSID = "HESSID";
    private static final String NODE_HOMESP = "HomeSP";
    private static final String NODE_HOME_OI = "HomeOI";
    private static final String NODE_HOME_OI_LIST = "HomeOIList";
    private static final String NODE_HOME_OI_REQUIRED = "HomeOIRequired";
    private static final String NODE_ICON_URL = "IconURL";
    private static final String NODE_INNER_EAP_TYPE = "InnerEAPType";
    private static final String NODE_INNER_METHOD = "InnerMethod";
    private static final String NODE_INNER_VENDOR_ID = "InnerVendorID";
    private static final String NODE_INNER_VENDOR_TYPE = "InnerVendorType";
    private static final String NODE_IP_PROTOCOL = "IPProtocol";
    private static final String NODE_MACHINE_MANAGED = "MachineManaged";
    private static final String NODE_MAXIMUM_BSS_LOAD_VALUE = "MaximumBSSLoadValue";
    private static final String NODE_MIN_BACKHAUL_THRESHOLD = "MinBackhaulThreshold";
    private static final String NODE_NETWORK_ID = "NetworkID";
    private static final String NODE_NETWORK_TYPE = "NetworkType";
    private static final String NODE_OTHER = "Other";
    private static final String NODE_OTHER_HOME_PARTNERS = "OtherHomePartners";
    private static final String NODE_PASSWORD = "Password";
    private static final String NODE_PER_PROVIDER_SUBSCRIPTION = "PerProviderSubscription";
    private static final String NODE_POLICY = "Policy";
    private static final String NODE_POLICY_UPDATE = "PolicyUpdate";
    private static final String NODE_PORT_NUMBER = "PortNumber";
    private static final String NODE_PREFERRED_ROAMING_PARTNER_LIST = "PreferredRoamingPartnerList";
    private static final String NODE_PRIORITY = "Priority";
    private static final String NODE_REALM = "Realm";
    private static final String NODE_REQUIRED_PROTO_PORT_TUPLE = "RequiredProtoPortTuple";
    private static final String NODE_RESTRICTION = "Restriction";
    private static final String NODE_ROAMING_CONSORTIUM_OI = "RoamingConsortiumOI";
    private static final String NODE_SIM = "SIM";
    private static final String NODE_SIM_IMSI = "IMSI";
    private static final String NODE_SOFT_TOKEN_APP = "SoftTokenApp";
    private static final String NODE_SP_EXCLUSION_LIST = "SPExclusionList";
    private static final String NODE_SSID = "SSID";
    private static final String NODE_START_DATE = "StartDate";
    private static final String NODE_SUBSCRIPTION_PARAMETER = "SubscriptionParameters";
    private static final String NODE_SUBSCRIPTION_UPDATE = "SubscriptionUpdate";
    private static final String NODE_TIME_LIMIT = "TimeLimit";
    private static final String NODE_TRUST_ROOT = "TrustRoot";
    private static final String NODE_TYPE_OF_SUBSCRIPTION = "TypeOfSubscription";
    private static final String NODE_UPDATE_IDENTIFIER = "UpdateIdentifier";
    private static final String NODE_UPDATE_INTERVAL = "UpdateInterval";
    private static final String NODE_UPDATE_METHOD = "UpdateMethod";
    private static final String NODE_UPLINK_BANDWIDTH = "ULBandwidth";
    private static final String NODE_URI = "URI";
    private static final String NODE_USAGE_LIMITS = "UsageLimits";
    private static final String NODE_USAGE_TIME_PERIOD = "UsageTimePeriod";
    private static final String NODE_USERNAME = "Username";
    private static final String NODE_USERNAME_PASSWORD = "UsernamePassword";
    private static final String NODE_VENDOR_ID = "VendorId";
    private static final String NODE_VENDOR_TYPE = "VendorType";
    private static final String PPS_MO_URN = "urn:wfa:mo:hotspot2dot0-perprovidersubscription:1.0";
    private static final String TAG = "PpsMoParser";
    private static final String TAG_DDF_NAME = "DDFName";
    private static final String TAG_MANAGEMENT_TREE = "MgmtTree";
    private static final String TAG_NODE = "Node";
    private static final String TAG_NODE_NAME = "NodeName";
    private static final String TAG_RT_PROPERTIES = "RTProperties";
    private static final String TAG_TYPE = "Type";
    private static final String TAG_VALUE = "Value";
    private static final String TAG_VER_DTD = "VerDTD";

    private static PPSNode buildPpsNode(XMLNode object) throws ParsingException {
        Object object2 = null;
        CharSequence charSequence = null;
        ArrayList<PPSNode> arrayList = new ArrayList<PPSNode>();
        HashSet<String> hashSet = new HashSet<String>();
        Iterator<XMLNode> iterator = ((XMLNode)object).getChildren().iterator();
        object = object2;
        while (iterator.hasNext()) {
            XMLNode xMLNode = iterator.next();
            object2 = xMLNode.getTag();
            if (TextUtils.equals((CharSequence)object2, TAG_NODE_NAME)) {
                if (object == null) {
                    object = xMLNode.getText();
                    continue;
                }
                throw new ParsingException("Duplicate NodeName node");
            }
            if (TextUtils.equals((CharSequence)object2, TAG_NODE)) {
                object2 = PpsMoParser.buildPpsNode(xMLNode);
                if (!hashSet.contains(((PPSNode)object2).getName())) {
                    hashSet.add(((PPSNode)object2).getName());
                    arrayList.add((PPSNode)object2);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Duplicate node: ");
                ((StringBuilder)object).append(((PPSNode)object2).getName());
                throw new ParsingException(((StringBuilder)object).toString());
            }
            if (TextUtils.equals((CharSequence)object2, TAG_VALUE)) {
                if (charSequence == null) {
                    charSequence = xMLNode.getText();
                    continue;
                }
                throw new ParsingException("Duplicate Value node");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown tag: ");
            ((StringBuilder)object).append((String)object2);
            throw new ParsingException(((StringBuilder)object).toString());
        }
        if (object != null) {
            if (charSequence == null && arrayList.size() == 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid node: ");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" missing both value and children");
                throw new ParsingException(((StringBuilder)charSequence).toString());
            }
            if (charSequence != null && arrayList.size() > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid node: ");
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" contained both value and children");
                throw new ParsingException(((StringBuilder)charSequence).toString());
            }
            if (charSequence != null) {
                return new LeafNode((String)object, (String)charSequence);
            }
            return new InternalNode((String)object, arrayList);
        }
        throw new ParsingException("Invalid node: missing NodeName");
    }

    private static long[] convertFromLongList(List<Long> arrl) {
        Long[] arrlong = arrl.toArray(new Long[arrl.size()]);
        arrl = new long[arrlong.length];
        for (int i = 0; i < arrlong.length; ++i) {
            arrl[i] = arrlong[i];
        }
        return arrl;
    }

    private static String getPpsNodeValue(PPSNode pPSNode) throws ParsingException {
        if (pPSNode.isLeaf()) {
            return pPSNode.getValue();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot get value from a non-leaf node: ");
        stringBuilder.append(pPSNode.getName());
        throw new ParsingException(stringBuilder.toString());
    }

    private static Map<String, byte[]> parseAAAServerTrustRootList(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            HashMap<String, byte[]> hashMap = new HashMap<String, byte[]>();
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            while (iterator.hasNext()) {
                object = PpsMoParser.parseTrustRoot(iterator.next());
                hashMap.put((String)((Pair)object).first, (byte[])((Pair)object).second);
            }
            return hashMap;
        }
        throw new ParsingException("Leaf node not expected for AAAServerTrustRoot");
    }

    private static Credential.CertificateCredential parseCertificateCredential(PPSNode pPSNode2) throws ParsingException {
        if (!pPSNode2.isLeaf()) {
            Object object = new Credential.CertificateCredential();
            for (PPSNode pPSNode2 : pPSNode2.getChildren()) {
                String string2 = pPSNode2.getName();
                int n = -1;
                int n2 = string2.hashCode();
                if (n2 != -1914611375) {
                    if (n2 == -285451687 && string2.equals(NODE_CERT_SHA256_FINGERPRINT)) {
                        n = 1;
                    }
                } else if (string2.equals(NODE_CERTIFICATE_TYPE)) {
                    n = 0;
                }
                if (n != 0) {
                    if (n == 1) {
                        ((Credential.CertificateCredential)object).setCertSha256Fingerprint(PpsMoParser.parseHexString(PpsMoParser.getPpsNodeValue(pPSNode2)));
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under DigitalCertificate: ");
                    ((StringBuilder)object).append(pPSNode2.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                ((Credential.CertificateCredential)object).setCertType(PpsMoParser.getPpsNodeValue(pPSNode2));
            }
            return object;
        }
        throw new ParsingException("Leaf node not expected for DigitalCertificate");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Credential parseCredential(PPSNode var0) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for HomeSP");
        var1_1 = new Credential();
        var2_2 = var0.getChildren().iterator();
        block18 : while (var2_2.hasNext() != false) {
            block19 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 1749851981: {
                        if (!var3_3.equals("CreationDate")) break;
                        var4_4 = 0;
                        ** break;
                    }
                    case 646045490: {
                        if (!var3_3.equals("CheckAAAServerCertStatus")) break;
                        var4_4 = 5;
                        ** break;
                    }
                    case 494843313: {
                        if (!var3_3.equals("UsernamePassword")) break;
                        var4_4 = 2;
                        ** break;
                    }
                    case 78834287: {
                        if (!var3_3.equals("Realm")) break;
                        var4_4 = 4;
                        ** break;
                    }
                    case 82103: {
                        if (!var3_3.equals("SIM")) break;
                        var4_4 = 6;
                        ** break;
                    }
                    case -1208321921: {
                        if (!var3_3.equals("DigitalCertificate")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case -1670804707: {
                        if (!var3_3.equals("ExpirationDate")) break;
                        var4_4 = 1;
                        break block19;
                    }
                }
                ** break;
            }
            switch (var4_4) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Unknown node under Credential: ");
                    var1_1.append(var0.getName());
                    throw new ParsingException(var1_1.toString());
                }
                case 6: {
                    var1_1.setSimCredential(PpsMoParser.parseSimCredential(var0));
                    continue block18;
                }
                case 5: {
                    var1_1.setCheckAaaServerCertStatus(Boolean.parseBoolean(PpsMoParser.getPpsNodeValue(var0)));
                    continue block18;
                }
                case 4: {
                    var1_1.setRealm(PpsMoParser.getPpsNodeValue(var0));
                    continue block18;
                }
                case 3: {
                    var1_1.setCertCredential(PpsMoParser.parseCertificateCredential(var0));
                    continue block18;
                }
                case 2: {
                    var1_1.setUserCredential(PpsMoParser.parseUserCredential(var0));
                    continue block18;
                }
                case 1: {
                    var1_1.setExpirationTimeInMillis(PpsMoParser.parseDate(PpsMoParser.getPpsNodeValue(var0)));
                    continue block18;
                }
                case 0: 
            }
            var1_1.setCreationTimeInMillis(PpsMoParser.parseDate(PpsMoParser.getPpsNodeValue(var0)));
        }
        return var1_1;
    }

    private static long parseDate(String string2) throws ParsingException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            long l = simpleDateFormat.parse(string2).getTime();
            return l;
        }
        catch (ParseException parseException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Badly formatted time: ");
            stringBuilder.append(string2);
            throw new ParsingException(stringBuilder.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void parseEAPMethod(PPSNode var0, Credential.UserCredential var1_1) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for EAPMethod");
        var2_2 = var0.getChildren().iterator();
        block14 : while (var2_2.hasNext() != false) {
            block15 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 961456313: {
                        if (!var3_3.equals("InnerVendorID")) break;
                        var4_4 = 5;
                        ** break;
                    }
                    case 901061303: {
                        if (!var3_3.equals("InnerMethod")) break;
                        var4_4 = 1;
                        ** break;
                    }
                    case 541930360: {
                        if (!var3_3.equals("InnerVendorType")) break;
                        var4_4 = 6;
                        ** break;
                    }
                    case -1249356658: {
                        if (!var3_3.equals("EAPType")) break;
                        var4_4 = 0;
                        ** break;
                    }
                    case -1607163710: {
                        if (!var3_3.equals("VendorType")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case -1706447464: {
                        if (!var3_3.equals("InnerEAPType")) break;
                        var4_4 = 4;
                        ** break;
                    }
                    case -2048597853: {
                        if (!var3_3.equals("VendorId")) break;
                        var4_4 = 2;
                        break block15;
                    }
                }
                ** break;
            }
            switch (var4_4) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Unknown node under EAPMethod: ");
                    var1_1.append(var0.getName());
                    throw new ParsingException(var1_1.toString());
                }
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: {
                    var3_3 = new StringBuilder();
                    var3_3.append("Ignore unsupported EAP method parameter: ");
                    var3_3.append(var0.getName());
                    Log.d("PpsMoParser", var3_3.toString());
                    continue block14;
                }
                case 1: {
                    var1_1.setNonEapInnerMethod(PpsMoParser.getPpsNodeValue(var0));
                    continue block14;
                }
                case 0: 
            }
            var1_1.setEapType(PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue(var0)));
        }
    }

    private static byte[] parseHexString(String string2) throws ParsingException {
        if ((string2.length() & 1) != 1) {
            byte[] arrby = new byte[string2.length() / 2];
            for (int i = 0; i < arrby.length; ++i) {
                int n = i * 2;
                try {
                    arrby[i] = (byte)Integer.parseInt(string2.substring(n, n + 2), 16);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid hex string: ");
                    stringBuilder.append(string2);
                    throw new ParsingException(stringBuilder.toString());
                }
            }
            return arrby;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Odd length hex string: ");
        stringBuilder.append(string2.length());
        throw new ParsingException(stringBuilder.toString());
    }

    private static Pair<Long, Boolean> parseHomeOIInstance(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            Long l = null;
            PPSNode pPSNode = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = pPSNode;
            while (iterator.hasNext()) {
                pPSNode = iterator.next();
                String string2 = pPSNode.getName();
                int n = -1;
                int n2 = string2.hashCode();
                if (n2 != -2127810791) {
                    if (n2 == -1935174184 && string2.equals(NODE_HOME_OI_REQUIRED)) {
                        n = 1;
                    }
                } else if (string2.equals(NODE_HOME_OI)) {
                    n = 0;
                }
                if (n != 0) {
                    if (n == 1) {
                        object = Boolean.valueOf(PpsMoParser.getPpsNodeValue(pPSNode));
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under NetworkID instance: ");
                    ((StringBuilder)object).append(pPSNode.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                try {
                    l = Long.valueOf(PpsMoParser.getPpsNodeValue(pPSNode), 16);
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid HomeOI: ");
                    stringBuilder.append(PpsMoParser.getPpsNodeValue(pPSNode));
                    throw new ParsingException(stringBuilder.toString());
                }
            }
            if (l != null) {
                if (object != null) {
                    return new Pair<Object, PPSNode>(l, (PPSNode)object);
                }
                throw new ParsingException("HomeOI instance missing required field");
            }
            throw new ParsingException("HomeOI instance missing OI field");
        }
        throw new ParsingException("Leaf node not expected for HomeOI instance");
    }

    private static Pair<List<Long>, List<Long>> parseHomeOIList(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            ArrayList<Long> arrayList = new ArrayList<Long>();
            ArrayList<Long> arrayList2 = new ArrayList<Long>();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                Pair<Long, Boolean> pair = PpsMoParser.parseHomeOIInstance((PPSNode)object.next());
                if (((Boolean)pair.second).booleanValue()) {
                    arrayList.add((Long)pair.first);
                    continue;
                }
                arrayList2.add((Long)pair.first);
            }
            return new Pair<List<Long>, List<Long>>(arrayList, arrayList2);
        }
        throw new ParsingException("Leaf node not expected for HomeOIList");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static HomeSp parseHomeSP(PPSNode var0) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for HomeSP");
        var1_5 = new HomeSp();
        var2_6 = var0.getChildren().iterator();
        block18 : while (var2_6.hasNext() != false) {
            block19 : {
                var0_2 = var2_6.next();
                var3_7 = var0_2.getName();
                var4_8 = -1;
                switch (var3_7.hashCode()) {
                    case 1956561338: {
                        if (!var3_7.equals("OtherHomePartners")) break;
                        var4_8 = 6;
                        ** break;
                    }
                    case 626253302: {
                        if (!var3_7.equals("FriendlyName")) break;
                        var4_8 = 1;
                        ** break;
                    }
                    case 542998228: {
                        if (!var3_7.equals("RoamingConsortiumOI")) break;
                        var4_8 = 2;
                        ** break;
                    }
                    case 2165397: {
                        if (!var3_7.equals("FQDN")) break;
                        var4_8 = 0;
                        ** break;
                    }
                    case -228216919: {
                        if (!var3_7.equals("NetworkID")) break;
                        var4_8 = 4;
                        ** break;
                    }
                    case -991549930: {
                        if (!var3_7.equals("IconURL")) break;
                        var4_8 = 3;
                        ** break;
                    }
                    case -1560207529: {
                        if (!var3_7.equals("HomeOIList")) break;
                        var4_8 = 5;
                        break block19;
                    }
                }
                ** break;
            }
            switch (var4_8) {
                default: {
                    var1_5 = new StringBuilder();
                    var1_5.append("Unknown node under HomeSP: ");
                    var1_5.append(var0_2.getName());
                    throw new ParsingException(var1_5.toString());
                }
                case 6: {
                    var1_5.setOtherHomePartners(PpsMoParser.parseOtherHomePartners(var0_2));
                    continue block18;
                }
                case 5: {
                    var0_3 = PpsMoParser.parseHomeOIList(var0_2);
                    var1_5.setMatchAllOis(PpsMoParser.convertFromLongList((List)var0_3.first));
                    var1_5.setMatchAnyOis(PpsMoParser.convertFromLongList((List)var0_3.second));
                    continue block18;
                }
                case 4: {
                    var1_5.setHomeNetworkIds(PpsMoParser.parseNetworkIds(var0_2));
                    continue block18;
                }
                case 3: {
                    var1_5.setIconUrl(PpsMoParser.getPpsNodeValue(var0_2));
                    continue block18;
                }
                case 2: {
                    var1_5.setRoamingConsortiumOis(PpsMoParser.parseRoamingConsortiumOI(PpsMoParser.getPpsNodeValue(var0_2)));
                    continue block18;
                }
                case 1: {
                    var1_5.setFriendlyName(PpsMoParser.getPpsNodeValue(var0_2));
                    continue block18;
                }
                case 0: 
            }
            var1_5.setFqdn(PpsMoParser.getPpsNodeValue(var0_2));
        }
        return var1_5;
    }

    private static int parseInteger(String string2) throws ParsingException {
        try {
            int n = Integer.parseInt(string2);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid integer value: ");
            stringBuilder.append(string2);
            throw new ParsingException(stringBuilder.toString());
        }
    }

    private static long parseLong(String string2, int n) throws ParsingException {
        try {
            long l = Long.parseLong(string2, n);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid long integer value: ");
            stringBuilder.append(string2);
            throw new ParsingException(stringBuilder.toString());
        }
    }

    private static void parseMinBackhaulThreshold(PPSNode object, Policy policy) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                PpsMoParser.parseMinBackhaulThresholdInstance((PPSNode)object.next(), policy);
            }
            return;
        }
        throw new ParsingException("Leaf node not expected for MinBackhaulThreshold");
    }

    private static void parseMinBackhaulThresholdInstance(PPSNode object, Policy object2) throws ParsingException {
        block15 : {
            block16 : {
                block19 : {
                    block18 : {
                        long l;
                        long l2;
                        block17 : {
                            if (((PPSNode)object).isLeaf()) break block15;
                            PPSNode pPSNode = null;
                            l = Long.MIN_VALUE;
                            l2 = Long.MIN_VALUE;
                            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
                            object = pPSNode;
                            while (iterator.hasNext()) {
                                pPSNode = iterator.next();
                                String string2 = pPSNode.getName();
                                int n = -1;
                                int n2 = string2.hashCode();
                                if (n2 != -272744856) {
                                    if (n2 != -133967910) {
                                        if (n2 == 349434121 && string2.equals(NODE_DOWNLINK_BANDWIDTH)) {
                                            n = 1;
                                        }
                                    } else if (string2.equals(NODE_UPLINK_BANDWIDTH)) {
                                        n = 2;
                                    }
                                } else if (string2.equals(NODE_NETWORK_TYPE)) {
                                    n = 0;
                                }
                                if (n != 0) {
                                    if (n != 1) {
                                        if (n == 2) {
                                            l2 = PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue(pPSNode), 10);
                                            continue;
                                        }
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Unknown node under MinBackhaulThreshold instance ");
                                        ((StringBuilder)object).append(pPSNode.getName());
                                        throw new ParsingException(((StringBuilder)object).toString());
                                    }
                                    l = PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue(pPSNode), 10);
                                    continue;
                                }
                                object = PpsMoParser.getPpsNodeValue(pPSNode);
                            }
                            if (object == null) break block16;
                            if (!TextUtils.equals((CharSequence)object, "home")) break block17;
                            ((Policy)object2).setMinHomeDownlinkBandwidth(l);
                            ((Policy)object2).setMinHomeUplinkBandwidth(l2);
                            break block18;
                        }
                        if (!TextUtils.equals((CharSequence)object, "roaming")) break block19;
                        ((Policy)object2).setMinRoamingDownlinkBandwidth(l);
                        ((Policy)object2).setMinRoamingUplinkBandwidth(l2);
                    }
                    return;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Invalid network type: ");
                ((StringBuilder)object2).append((String)object);
                throw new ParsingException(((StringBuilder)object2).toString());
            }
            throw new ParsingException("Missing NetworkType field");
        }
        throw new ParsingException("Leaf node not expected for MinBackhaulThreshold instance");
    }

    public static PasspointConfiguration parseMoText(String charSequence) {
        Object object;
        XMLNode xMLNode;
        block14 : {
            object = new XMLParser();
            try {
                xMLNode = ((XMLParser)object).parse((String)charSequence);
                if (xMLNode == null) {
                    return null;
                }
                if (xMLNode.getTag() == TAG_MANAGEMENT_TREE) break block14;
            }
            catch (IOException | SAXException exception) {
                return null;
            }
            Log.e(TAG, "Root is not a MgmtTree");
            return null;
        }
        charSequence = null;
        object = null;
        for (XMLNode xMLNode2 : xMLNode.getChildren()) {
            String string2 = xMLNode2.getTag();
            int n = -1;
            int n2 = string2.hashCode();
            if (n2 != -1736120495) {
                if (n2 == 2433570 && string2.equals(TAG_NODE)) {
                    n = 1;
                }
            } else if (string2.equals(TAG_VER_DTD)) {
                n = 0;
            }
            if (n != 0) {
                if (n != 1) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Unknown node: ");
                    ((StringBuilder)charSequence).append(xMLNode2.getTag());
                    Log.e(TAG, ((StringBuilder)charSequence).toString());
                    return null;
                }
                if (object != null) {
                    Log.e(TAG, "Unexpected multiple Node element under MgmtTree");
                    return null;
                }
                try {
                    object = PpsMoParser.parsePpsNode(xMLNode2);
                    continue;
                }
                catch (ParsingException parsingException) {
                    Log.e(TAG, parsingException.getMessage());
                    return null;
                }
            }
            if (charSequence != null) {
                Log.e(TAG, "Duplicate VerDTD element");
                return null;
            }
            charSequence = xMLNode2.getText();
        }
        return object;
    }

    private static Pair<String, Long> parseNetworkIdInstance(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            PPSNode pPSNode = null;
            Long l = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = pPSNode;
            while (iterator.hasNext()) {
                pPSNode = iterator.next();
                String string2 = pPSNode.getName();
                int n = -1;
                int n2 = string2.hashCode();
                if (n2 != 2554747) {
                    if (n2 == 2127576568 && string2.equals(NODE_HESSID)) {
                        n = 1;
                    }
                } else if (string2.equals(NODE_SSID)) {
                    n = 0;
                }
                if (n != 0) {
                    if (n == 1) {
                        l = PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue(pPSNode), 16);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under NetworkID instance: ");
                    ((StringBuilder)object).append(pPSNode.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                object = PpsMoParser.getPpsNodeValue(pPSNode);
            }
            if (object != null) {
                return new Pair<PPSNode, Object>((PPSNode)object, l);
            }
            throw new ParsingException("NetworkID instance missing SSID");
        }
        throw new ParsingException("Leaf node not expected for NetworkID instance");
    }

    private static Map<String, Long> parseNetworkIds(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            HashMap<String, Long> hashMap = new HashMap<String, Long>();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                Pair<String, Long> pair = PpsMoParser.parseNetworkIdInstance((PPSNode)object.next());
                hashMap.put((String)pair.first, (Long)pair.second);
            }
            return hashMap;
        }
        throw new ParsingException("Leaf node not expected for NetworkID");
    }

    private static String parseOtherHomePartnerInstance(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            CharSequence charSequence = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = charSequence;
            while (iterator.hasNext()) {
                object = iterator.next();
                charSequence = ((PPSNode)object).getName();
                int n = -1;
                if (((String)charSequence).hashCode() == 2165397 && ((String)charSequence).equals(NODE_FQDN)) {
                    n = 0;
                }
                if (n == 0) {
                    object = PpsMoParser.getPpsNodeValue((PPSNode)object);
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unknown node under OtherHomePartner instance: ");
                ((StringBuilder)charSequence).append(((PPSNode)object).getName());
                throw new ParsingException(((StringBuilder)charSequence).toString());
            }
            if (object != null) {
                return object;
            }
            throw new ParsingException("OtherHomePartner instance missing FQDN field");
        }
        throw new ParsingException("Leaf node not expected for OtherHomePartner instance");
    }

    private static String[] parseOtherHomePartners(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                arrayList.add(PpsMoParser.parseOtherHomePartnerInstance((PPSNode)object.next()));
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }
        throw new ParsingException("Leaf node not expected for OtherHomePartners");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Policy parsePolicy(PPSNode var0) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for Policy");
        var1_1 = new Policy();
        var2_2 = var0.getChildren().iterator();
        while (var2_2.hasNext() != false) {
            block15 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 1337803246: {
                        if (!var3_3.equals("PreferredRoamingPartnerList")) break;
                        var4_4 = 0;
                        ** break;
                    }
                    case 783647838: {
                        if (!var3_3.equals("RequiredProtoPortTuple")) break;
                        var4_4 = 4;
                        ** break;
                    }
                    case 586018863: {
                        if (!var3_3.equals("SPExclusionList")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case -166875607: {
                        if (!var3_3.equals("MaximumBSSLoadValue")) break;
                        var4_4 = 5;
                        ** break;
                    }
                    case -281271454: {
                        if (!var3_3.equals("MinBackhaulThreshold")) break;
                        var4_4 = 1;
                        ** break;
                    }
                    case -1710886725: {
                        if (!var3_3.equals("PolicyUpdate")) break;
                        var4_4 = 2;
                        break block15;
                    }
                }
                ** break;
            }
            if (var4_4 != 0) {
                if (var4_4 != 1) {
                    if (var4_4 != 2) {
                        if (var4_4 != 3) {
                            if (var4_4 != 4) {
                                if (var4_4 != 5) {
                                    var1_1 = new StringBuilder();
                                    var1_1.append("Unknown node under Policy: ");
                                    var1_1.append(var0.getName());
                                    throw new ParsingException(var1_1.toString());
                                }
                                var1_1.setMaximumBssLoadValue(PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue(var0)));
                                continue;
                            }
                            var1_1.setRequiredProtoPortMap(PpsMoParser.parseRequiredProtoPortTuple(var0));
                            continue;
                        }
                        var1_1.setExcludedSsidList(PpsMoParser.parseSpExclusionList(var0));
                        continue;
                    }
                    var1_1.setPolicyUpdate(PpsMoParser.parseUpdateParameter(var0));
                    continue;
                }
                PpsMoParser.parseMinBackhaulThreshold(var0, (Policy)var1_1);
                continue;
            }
            var1_1.setPreferredRoamingPartnerList(PpsMoParser.parsePreferredRoamingPartnerList(var0));
        }
        return var1_1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static PasspointConfiguration parsePpsInstance(PPSNode var0) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for PPS instance");
        var1_1 = new PasspointConfiguration();
        var2_2 = var0.getChildren().iterator();
        block20 : while (var2_2.hasNext() != false) {
            block21 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 2017737531: {
                        if (!var3_3.equals("CredentialPriority")) break;
                        var4_4 = 6;
                        ** break;
                    }
                    case 1391410207: {
                        if (!var3_3.equals("Extension")) break;
                        var4_4 = 7;
                        ** break;
                    }
                    case 1310049399: {
                        if (!var3_3.equals("Credential")) break;
                        var4_4 = 1;
                        ** break;
                    }
                    case 1112908551: {
                        if (!var3_3.equals("SubscriptionParameters")) break;
                        var4_4 = 5;
                        ** break;
                    }
                    case 314411254: {
                        if (!var3_3.equals("AAAServerTrustRoot")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case 162345062: {
                        if (!var3_3.equals("SubscriptionUpdate")) break;
                        var4_4 = 4;
                        ** break;
                    }
                    case -1898802862: {
                        if (!var3_3.equals("Policy")) break;
                        var4_4 = 2;
                        ** break;
                    }
                    case -2127810660: {
                        if (!var3_3.equals("HomeSP")) break;
                        var4_4 = 0;
                        break block21;
                    }
                }
                ** break;
            }
            switch (var4_4) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Unknown node: ");
                    var1_1.append(var0.getName());
                    throw new ParsingException(var1_1.toString());
                }
                case 7: {
                    Log.d("PpsMoParser", "Ignore Extension node for vendor specific information");
                    continue block20;
                }
                case 6: {
                    var1_1.setCredentialPriority(PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue(var0)));
                    continue block20;
                }
                case 5: {
                    PpsMoParser.parseSubscriptionParameter(var0, (PasspointConfiguration)var1_1);
                    continue block20;
                }
                case 4: {
                    var1_1.setSubscriptionUpdate(PpsMoParser.parseUpdateParameter(var0));
                    continue block20;
                }
                case 3: {
                    var1_1.setTrustRootCertList(PpsMoParser.parseAAAServerTrustRootList(var0));
                    continue block20;
                }
                case 2: {
                    var1_1.setPolicy(PpsMoParser.parsePolicy(var0));
                    continue block20;
                }
                case 1: {
                    var1_1.setCredential(PpsMoParser.parseCredential(var0));
                    continue block20;
                }
                case 0: 
            }
            var1_1.setHomeSp(PpsMoParser.parseHomeSP(var0));
        }
        return var1_1;
    }

    private static PasspointConfiguration parsePpsNode(XMLNode object) throws ParsingException {
        Object object2 = null;
        Object object3 = null;
        int n = Integer.MIN_VALUE;
        Iterator<XMLNode> iterator = ((XMLNode)object).getChildren().iterator();
        object = object3;
        while (iterator.hasNext()) {
            object3 = iterator.next();
            String string2 = ((XMLNode)object3).getTag();
            int n2 = -1;
            int n3 = string2.hashCode();
            if (n3 != -1852765931) {
                if (n3 != 2433570) {
                    if (n3 == 1187524557 && string2.equals(TAG_NODE_NAME)) {
                        n2 = 0;
                    }
                } else if (string2.equals(TAG_NODE)) {
                    n2 = 1;
                }
            } else if (string2.equals(TAG_RT_PROPERTIES)) {
                n2 = 2;
            }
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) {
                        if (TextUtils.equals((CharSequence)(object3 = PpsMoParser.parseUrn((XMLNode)object3)), PPS_MO_URN)) continue;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknown URN: ");
                        ((StringBuilder)object).append((String)object3);
                        throw new ParsingException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown tag under PPS node: ");
                    ((StringBuilder)object).append(((XMLNode)object3).getTag());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                if (TextUtils.equals(((PPSNode)(object3 = PpsMoParser.buildPpsNode((XMLNode)object3))).getName(), NODE_UPDATE_IDENTIFIER)) {
                    if (n == Integer.MIN_VALUE) {
                        n = PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue((PPSNode)object3));
                        continue;
                    }
                    throw new ParsingException("Multiple node for UpdateIdentifier");
                }
                if (object2 == null) {
                    object2 = PpsMoParser.parsePpsInstance((PPSNode)object3);
                    continue;
                }
                throw new ParsingException("Multiple PPS instance");
            }
            if (object == null) {
                object = ((XMLNode)object3).getText();
                if (TextUtils.equals((CharSequence)object, NODE_PER_PROVIDER_SUBSCRIPTION)) continue;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unexpected NodeName: ");
                ((StringBuilder)object2).append((String)object);
                throw new ParsingException(((StringBuilder)object2).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Duplicate NodeName: ");
            ((StringBuilder)object).append(((XMLNode)object3).getText());
            throw new ParsingException(((StringBuilder)object).toString());
        }
        if (object2 != null && n != Integer.MIN_VALUE) {
            ((PasspointConfiguration)object2).setUpdateIdentifier(n);
        }
        return object2;
    }

    private static Policy.RoamingPartner parsePreferredRoamingPartner(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            Policy.RoamingPartner roamingPartner = new Policy.RoamingPartner();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                Object object2 = (PPSNode)object.next();
                String[] arrstring = ((PPSNode)object2).getName();
                int n = -1;
                int n2 = arrstring.hashCode();
                if (n2 != -1672482954) {
                    if (n2 != -1100816956) {
                        if (n2 == 305746811 && arrstring.equals(NODE_FQDN_MATCH)) {
                            n = 0;
                        }
                    } else if (arrstring.equals(NODE_PRIORITY)) {
                        n = 1;
                    }
                } else if (arrstring.equals(NODE_COUNTRY)) {
                    n = 2;
                }
                if (n != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            roamingPartner.setCountries(PpsMoParser.getPpsNodeValue((PPSNode)object2));
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknown node under PreferredRoamingPartnerList instance ");
                        ((StringBuilder)object).append(((PPSNode)object2).getName());
                        throw new ParsingException(((StringBuilder)object).toString());
                    }
                    roamingPartner.setPriority(PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue((PPSNode)object2)));
                    continue;
                }
                arrstring = ((String)(object2 = PpsMoParser.getPpsNodeValue((PPSNode)object2))).split(",");
                if (arrstring.length == 2) {
                    roamingPartner.setFqdn(arrstring[0]);
                    if (TextUtils.equals(arrstring[1], "exactMatch")) {
                        roamingPartner.setFqdnExactMatch(true);
                        continue;
                    }
                    if (TextUtils.equals(arrstring[1], "includeSubdomains")) {
                        roamingPartner.setFqdnExactMatch(false);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid FQDN_Match: ");
                    ((StringBuilder)object).append((String)object2);
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid FQDN_Match: ");
                ((StringBuilder)object).append((String)object2);
                throw new ParsingException(((StringBuilder)object).toString());
            }
            return roamingPartner;
        }
        throw new ParsingException("Leaf node not expected for PreferredRoamingPartner instance");
    }

    private static List<Policy.RoamingPartner> parsePreferredRoamingPartnerList(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            ArrayList<Policy.RoamingPartner> arrayList = new ArrayList<Policy.RoamingPartner>();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                arrayList.add(PpsMoParser.parsePreferredRoamingPartner((PPSNode)object.next()));
            }
            return arrayList;
        }
        throw new ParsingException("Leaf node not expected for PreferredRoamingPartnerList");
    }

    private static Pair<Integer, String> parseProtoPortTuple(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            int n = Integer.MIN_VALUE;
            PPSNode pPSNode = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = pPSNode;
            while (iterator.hasNext()) {
                pPSNode = iterator.next();
                String string2 = pPSNode.getName();
                int n2 = -1;
                int n3 = string2.hashCode();
                if (n3 != -952572705) {
                    if (n3 == 1727403850 && string2.equals(NODE_PORT_NUMBER)) {
                        n2 = 1;
                    }
                } else if (string2.equals(NODE_IP_PROTOCOL)) {
                    n2 = 0;
                }
                if (n2 != 0) {
                    if (n2 == 1) {
                        object = PpsMoParser.getPpsNodeValue(pPSNode);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under RequiredProtoPortTuple instance");
                    ((StringBuilder)object).append(pPSNode.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                n = PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue(pPSNode));
            }
            if (n != Integer.MIN_VALUE) {
                if (object != null) {
                    return Pair.create(n, object);
                }
                throw new ParsingException("Missing PortNumber field");
            }
            throw new ParsingException("Missing IPProtocol field");
        }
        throw new ParsingException("Leaf node not expected for RequiredProtoPortTuple instance");
    }

    private static Map<Integer, String> parseRequiredProtoPortTuple(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                Pair<Integer, String> pair = PpsMoParser.parseProtoPortTuple((PPSNode)object.next());
                hashMap.put((Integer)pair.first, (String)pair.second);
            }
            return hashMap;
        }
        throw new ParsingException("Leaf node not expected for RequiredProtoPortTuple");
    }

    private static long[] parseRoamingConsortiumOI(String arrstring) throws ParsingException {
        arrstring = arrstring.split(",");
        long[] arrl = new long[arrstring.length];
        for (int i = 0; i < arrstring.length; ++i) {
            arrl[i] = PpsMoParser.parseLong(arrstring[i], 16);
        }
        return arrl;
    }

    private static Credential.SimCredential parseSimCredential(PPSNode pPSNode2) throws ParsingException {
        if (!pPSNode2.isLeaf()) {
            Object object = new Credential.SimCredential();
            for (PPSNode pPSNode2 : pPSNode2.getChildren()) {
                String string2 = pPSNode2.getName();
                int n = -1;
                int n2 = string2.hashCode();
                if (n2 != -1249356658) {
                    if (n2 == 2251386 && string2.equals(NODE_SIM_IMSI)) {
                        n = 0;
                    }
                } else if (string2.equals(NODE_EAP_TYPE)) {
                    n = 1;
                }
                if (n != 0) {
                    if (n == 1) {
                        ((Credential.SimCredential)object).setEapType(PpsMoParser.parseInteger(PpsMoParser.getPpsNodeValue(pPSNode2)));
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under SIM: ");
                    ((StringBuilder)object).append(pPSNode2.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                ((Credential.SimCredential)object).setImsi(PpsMoParser.getPpsNodeValue(pPSNode2));
            }
            return object;
        }
        throw new ParsingException("Leaf node not expected for SIM");
    }

    private static String parseSpExclusionInstance(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            PPSNode pPSNode = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = pPSNode;
            while (iterator.hasNext()) {
                pPSNode = iterator.next();
                object = pPSNode.getName();
                int n = -1;
                if (((String)object).hashCode() == 2554747 && ((String)object).equals(NODE_SSID)) {
                    n = 0;
                }
                if (n == 0) {
                    object = PpsMoParser.getPpsNodeValue(pPSNode);
                    continue;
                }
                throw new ParsingException("Unknown node under SPExclusion instance");
            }
            return object;
        }
        throw new ParsingException("Leaf node not expected for SPExclusion instance");
    }

    private static String[] parseSpExclusionList(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            ArrayList<String> arrayList = new ArrayList<String>();
            object = ((PPSNode)object).getChildren().iterator();
            while (object.hasNext()) {
                arrayList.add(PpsMoParser.parseSpExclusionInstance((PPSNode)object.next()));
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }
        throw new ParsingException("Leaf node not expected for SPExclusionList");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void parseSubscriptionParameter(PPSNode var0, PasspointConfiguration var1_1) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for SubscriptionParameter");
        var2_2 = var0.getChildren().iterator();
        while (var2_2.hasNext() != false) {
            block11 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 1749851981: {
                        if (!var3_3.equals("CreationDate")) break;
                        var4_4 = 0;
                        ** break;
                    }
                    case -1655596402: {
                        if (!var3_3.equals("TypeOfSubscription")) break;
                        var4_4 = 2;
                        ** break;
                    }
                    case -1670804707: {
                        if (!var3_3.equals("ExpirationDate")) break;
                        var4_4 = 1;
                        ** break;
                    }
                    case -1930116871: {
                        if (!var3_3.equals("UsageLimits")) break;
                        var4_4 = 3;
                        break block11;
                    }
                }
                ** break;
            }
            if (var4_4 != 0) {
                if (var4_4 != 1) {
                    if (var4_4 != 2) {
                        if (var4_4 != 3) {
                            var1_1 = new StringBuilder();
                            var1_1.append("Unknown node under SubscriptionParameter");
                            var1_1.append(var0.getName());
                            throw new ParsingException(var1_1.toString());
                        }
                        PpsMoParser.parseUsageLimits(var0, (PasspointConfiguration)var1_1);
                        continue;
                    }
                    var1_1.setSubscriptionType(PpsMoParser.getPpsNodeValue(var0));
                    continue;
                }
                var1_1.setSubscriptionExpirationTimeInMillis(PpsMoParser.parseDate(PpsMoParser.getPpsNodeValue(var0)));
                continue;
            }
            var1_1.setSubscriptionCreationTimeInMillis(PpsMoParser.parseDate(PpsMoParser.getPpsNodeValue(var0)));
        }
    }

    private static Pair<String, byte[]> parseTrustRoot(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            PPSNode pPSNode = null;
            byte[] arrby = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = pPSNode;
            while (iterator.hasNext()) {
                pPSNode = iterator.next();
                String string2 = pPSNode.getName();
                int n = -1;
                int n2 = string2.hashCode();
                if (n2 != -1961397109) {
                    if (n2 == -285451687 && string2.equals(NODE_CERT_SHA256_FINGERPRINT)) {
                        n = 1;
                    }
                } else if (string2.equals(NODE_CERT_URL)) {
                    n = 0;
                }
                if (n != 0) {
                    if (n == 1) {
                        arrby = PpsMoParser.parseHexString(PpsMoParser.getPpsNodeValue(pPSNode));
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under TrustRoot: ");
                    ((StringBuilder)object).append(pPSNode.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                object = PpsMoParser.getPpsNodeValue(pPSNode);
            }
            return Pair.create(object, arrby);
        }
        throw new ParsingException("Leaf node not expected for TrustRoot");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static UpdateParameter parseUpdateParameter(PPSNode var0) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for Update Parameters");
        var1_1 = new UpdateParameter();
        var0 = var0.getChildren().iterator();
        block18 : while (var0.hasNext() != false) {
            block19 : {
                var2_2 = var0.next();
                var3_3 = var2_2.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 494843313: {
                        if (!var3_3.equals("UsernamePassword")) break;
                        var4_4 = 4;
                        ** break;
                    }
                    case 438596814: {
                        if (!var3_3.equals("UpdateInterval")) break;
                        var4_4 = 0;
                        ** break;
                    }
                    case 106806188: {
                        if (!var3_3.equals("Restriction")) break;
                        var4_4 = 2;
                        ** break;
                    }
                    case 76517104: {
                        if (!var3_3.equals("Other")) break;
                        var4_4 = 6;
                        ** break;
                    }
                    case 84300: {
                        if (!var3_3.equals("URI")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case -524654790: {
                        if (!var3_3.equals("TrustRoot")) break;
                        var4_4 = 5;
                        ** break;
                    }
                    case -961491158: {
                        if (!var3_3.equals("UpdateMethod")) break;
                        var4_4 = 1;
                        break block19;
                    }
                }
                ** break;
            }
            switch (var4_4) {
                default: {
                    var0 = new StringBuilder();
                    var0.append("Unknown node under Update Parameters: ");
                    var0.append(var2_2.getName());
                    throw new ParsingException(var0.toString());
                }
                case 6: {
                    var3_3 = new StringBuilder();
                    var3_3.append("Ignore unsupported paramter: ");
                    var3_3.append(var2_2.getName());
                    Log.d("PpsMoParser", var3_3.toString());
                    continue block18;
                }
                case 5: {
                    var2_2 = PpsMoParser.parseTrustRoot((PPSNode)var2_2);
                    var1_1.setTrustRootCertUrl((String)var2_2.first);
                    var1_1.setTrustRootCertSha256Fingerprint((byte[])var2_2.second);
                    continue block18;
                }
                case 4: {
                    var2_2 = PpsMoParser.parseUpdateUserCredential((PPSNode)var2_2);
                    var1_1.setUsername((String)var2_2.first);
                    var1_1.setBase64EncodedPassword((String)var2_2.second);
                    continue block18;
                }
                case 3: {
                    var1_1.setServerUri(PpsMoParser.getPpsNodeValue((PPSNode)var2_2));
                    continue block18;
                }
                case 2: {
                    var1_1.setRestriction(PpsMoParser.getPpsNodeValue((PPSNode)var2_2));
                    continue block18;
                }
                case 1: {
                    var1_1.setUpdateMethod(PpsMoParser.getPpsNodeValue((PPSNode)var2_2));
                    continue block18;
                }
                case 0: 
            }
            var1_1.setUpdateIntervalInMinutes(PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue((PPSNode)var2_2), 10));
        }
        return var1_1;
    }

    private static Pair<String, String> parseUpdateUserCredential(PPSNode object) throws ParsingException {
        if (!((PPSNode)object).isLeaf()) {
            PPSNode pPSNode = null;
            String string2 = null;
            Iterator<PPSNode> iterator = ((PPSNode)object).getChildren().iterator();
            object = pPSNode;
            while (iterator.hasNext()) {
                pPSNode = iterator.next();
                String string3 = pPSNode.getName();
                int n = -1;
                int n2 = string3.hashCode();
                if (n2 != -201069322) {
                    if (n2 == 1281629883 && string3.equals(NODE_PASSWORD)) {
                        n = 1;
                    }
                } else if (string3.equals(NODE_USERNAME)) {
                    n = 0;
                }
                if (n != 0) {
                    if (n == 1) {
                        string2 = PpsMoParser.getPpsNodeValue(pPSNode);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown node under UsernamePassword: ");
                    ((StringBuilder)object).append(pPSNode.getName());
                    throw new ParsingException(((StringBuilder)object).toString());
                }
                object = PpsMoParser.getPpsNodeValue(pPSNode);
            }
            return Pair.create(object, string2);
        }
        throw new ParsingException("Leaf node not expected for UsernamePassword");
    }

    private static String parseUrn(XMLNode object) throws ParsingException {
        if (((XMLNode)object).getChildren().size() == 1) {
            Object object2 = ((XMLNode)object).getChildren().get(0);
            if (((XMLNode)object2).getChildren().size() == 1) {
                if (TextUtils.equals(((XMLNode)object2).getTag(), TAG_TYPE)) {
                    object = ((XMLNode)object2).getChildren().get(0);
                    if (((XMLNode)object).getChildren().isEmpty()) {
                        if (TextUtils.equals(((XMLNode)object).getTag(), TAG_DDF_NAME)) {
                            return ((XMLNode)object).getText();
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unexpected tag for DDFName: ");
                        ((StringBuilder)object2).append(((XMLNode)object).getTag());
                        throw new ParsingException(((StringBuilder)object2).toString());
                    }
                    throw new ParsingException("Expect DDFName node to have no child");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected tag for Type: ");
                ((StringBuilder)object).append(((XMLNode)object2).getTag());
                throw new ParsingException(((StringBuilder)object).toString());
            }
            throw new ParsingException("Expect Type node to only have one child");
        }
        throw new ParsingException("Expect RTPProperties node to only have one child");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void parseUsageLimits(PPSNode var0, PasspointConfiguration var1_1) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for UsageLimits");
        var2_2 = var0.getChildren().iterator();
        while (var2_2.hasNext() != false) {
            block11 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 2022760654: {
                        if (!var3_3.equals("TimeLimit")) break;
                        var4_4 = 2;
                        ** break;
                    }
                    case 1622722065: {
                        if (!var3_3.equals("DataLimit")) break;
                        var4_4 = 0;
                        ** break;
                    }
                    case 587064143: {
                        if (!var3_3.equals("UsageTimePeriod")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case -125810928: {
                        if (!var3_3.equals("StartDate")) break;
                        var4_4 = 1;
                        break block11;
                    }
                }
                ** break;
            }
            if (var4_4 != 0) {
                if (var4_4 != 1) {
                    if (var4_4 != 2) {
                        if (var4_4 != 3) {
                            var1_1 = new StringBuilder();
                            var1_1.append("Unknown node under UsageLimits");
                            var1_1.append(var0.getName());
                            throw new ParsingException(var1_1.toString());
                        }
                        var1_1.setUsageLimitUsageTimePeriodInMinutes(PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue(var0), 10));
                        continue;
                    }
                    var1_1.setUsageLimitTimeLimitInMinutes(PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue(var0), 10));
                    continue;
                }
                var1_1.setUsageLimitStartTimeInMillis(PpsMoParser.parseDate(PpsMoParser.getPpsNodeValue(var0)));
                continue;
            }
            var1_1.setUsageLimitDataLimit(PpsMoParser.parseLong(PpsMoParser.getPpsNodeValue(var0), 10));
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Credential.UserCredential parseUserCredential(PPSNode var0) throws ParsingException {
        if (var0.isLeaf() != false) throw new ParsingException("Leaf node not expected for UsernamePassword");
        var1_1 = new Credential.UserCredential();
        var2_2 = var0.getChildren().iterator();
        while (var2_2.hasNext() != false) {
            block15 : {
                var0 = var2_2.next();
                var3_3 = var0.getName();
                var4_4 = -1;
                switch (var3_3.hashCode()) {
                    case 1740345653: {
                        if (!var3_3.equals("EAPMethod")) break;
                        var4_4 = 5;
                        ** break;
                    }
                    case 1410776018: {
                        if (!var3_3.equals("SoftTokenApp")) break;
                        var4_4 = 3;
                        ** break;
                    }
                    case 1281629883: {
                        if (!var3_3.equals("Password")) break;
                        var4_4 = 1;
                        ** break;
                    }
                    case 1045832056: {
                        if (!var3_3.equals("MachineManaged")) break;
                        var4_4 = 2;
                        ** break;
                    }
                    case -123996342: {
                        if (!var3_3.equals("AbleToShare")) break;
                        var4_4 = 4;
                        ** break;
                    }
                    case -201069322: {
                        if (!var3_3.equals("Username")) break;
                        var4_4 = 0;
                        break block15;
                    }
                }
                ** break;
            }
            if (var4_4 != 0) {
                if (var4_4 != 1) {
                    if (var4_4 != 2) {
                        if (var4_4 != 3) {
                            if (var4_4 != 4) {
                                if (var4_4 != 5) {
                                    var1_1 = new StringBuilder();
                                    var1_1.append("Unknown node under UsernamPassword: ");
                                    var1_1.append(var0.getName());
                                    throw new ParsingException(var1_1.toString());
                                }
                                PpsMoParser.parseEAPMethod(var0, (Credential.UserCredential)var1_1);
                                continue;
                            }
                            var1_1.setAbleToShare(Boolean.parseBoolean(PpsMoParser.getPpsNodeValue(var0)));
                            continue;
                        }
                        var1_1.setSoftTokenApp(PpsMoParser.getPpsNodeValue(var0));
                        continue;
                    }
                    var1_1.setMachineManaged(Boolean.parseBoolean(PpsMoParser.getPpsNodeValue(var0)));
                    continue;
                }
                var1_1.setPassword(PpsMoParser.getPpsNodeValue(var0));
                continue;
            }
            var1_1.setUsername(PpsMoParser.getPpsNodeValue(var0));
        }
        return var1_1;
    }

    private static class InternalNode
    extends PPSNode {
        private final List<PPSNode> mChildren;

        public InternalNode(String string2, List<PPSNode> list) {
            super(string2);
            this.mChildren = list;
        }

        @Override
        public List<PPSNode> getChildren() {
            return this.mChildren;
        }

        @Override
        public String getValue() {
            return null;
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
    }

    private static class LeafNode
    extends PPSNode {
        private final String mValue;

        public LeafNode(String string2, String string3) {
            super(string2);
            this.mValue = string3;
        }

        @Override
        public List<PPSNode> getChildren() {
            return null;
        }

        @Override
        public String getValue() {
            return this.mValue;
        }

        @Override
        public boolean isLeaf() {
            return true;
        }
    }

    private static abstract class PPSNode {
        private final String mName;

        public PPSNode(String string2) {
            this.mName = string2;
        }

        public abstract List<PPSNode> getChildren();

        public String getName() {
            return this.mName;
        }

        public abstract String getValue();

        public abstract boolean isLeaf();
    }

    private static class ParsingException
    extends Exception {
        public ParsingException(String string2) {
            super(string2);
        }
    }

}

