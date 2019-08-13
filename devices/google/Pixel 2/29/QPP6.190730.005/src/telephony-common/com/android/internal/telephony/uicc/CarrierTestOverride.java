/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Environment
 *  android.telephony.Rlog
 *  android.util.Xml
 *  com.android.internal.util.XmlUtils
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.android.internal.telephony.uicc;

import android.os.Environment;
import android.telephony.Rlog;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class CarrierTestOverride {
    static final String CARRIER_TEST_XML_HEADER = "carrierTestOverrides";
    static final String CARRIER_TEST_XML_ITEM_KEY = "key";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_GID1 = "gid1";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_GID2 = "gid2";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_ICCID = "iccid";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_IMSI = "imsi";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_ISINTESTMODE = "isInTestMode";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_MCCMNC = "mccmnc";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_PNN = "pnn";
    static final String CARRIER_TEST_XML_ITEM_KEY_STRING_SPN = "spn";
    static final String CARRIER_TEST_XML_ITEM_VALUE = "value";
    static final String CARRIER_TEST_XML_SUBHEADER = "carrierTestOverride";
    static final String DATA_CARRIER_TEST_OVERRIDE_PATH = "/user_de/0/com.android.phone/files/carrier_test_conf.xml";
    static final String LOG_TAG = "CarrierTestOverride";
    private HashMap<String, String> mCarrierTestParamMap = new HashMap();

    CarrierTestOverride() {
        this.loadCarrierTestOverrides();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadCarrierTestOverrides() {
        Object object = new File(Environment.getDataDirectory(), DATA_CARRIER_TEST_OVERRIDE_PATH);
        Object object2 = new FileReader((File)object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CarrierTestConfig file Modified Timestamp: ");
        stringBuilder.append(((File)object).lastModified());
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput((Reader)object2);
            XmlUtils.beginDocument((XmlPullParser)xmlPullParser, (String)CARRIER_TEST_XML_HEADER);
            do {
                XmlUtils.nextElement((XmlPullParser)xmlPullParser);
                if (!CARRIER_TEST_XML_SUBHEADER.equals(xmlPullParser.getName())) {
                    ((InputStreamReader)object2).close();
                    return;
                }
                object = xmlPullParser.getAttributeValue(null, CARRIER_TEST_XML_ITEM_KEY);
                String string = xmlPullParser.getAttributeValue(null, CARRIER_TEST_XML_ITEM_VALUE);
                stringBuilder = new StringBuilder();
                stringBuilder.append("extracting key-values from CarrierTestConfig file: ");
                stringBuilder.append((String)object);
                stringBuilder.append("|");
                stringBuilder.append(string);
                Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
                this.mCarrierTestParamMap.put((String)object, string);
            } while (true);
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Exception in carrier_test_conf parser ");
            ((StringBuilder)object2).append(iOException);
            Rlog.w((String)LOG_TAG, (String)((StringBuilder)object2).toString());
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Exception in carrier_test_conf parser ");
            ((StringBuilder)object).append((Object)xmlPullParserException);
            Rlog.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
        return;
        catch (FileNotFoundException fileNotFoundException) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Can not open ");
            stringBuilder2.append(((File)object).getAbsolutePath());
            Rlog.w((String)LOG_TAG, (String)stringBuilder2.toString());
            return;
        }
    }

    String getFakeGid1() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_GID1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading gid1 from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No gid1 in CarrierTestConfig file ");
            return null;
        }
    }

    String getFakeGid2() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_GID2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading gid2 from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No gid2 in CarrierTestConfig file ");
            return null;
        }
    }

    String getFakeIMSI() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_IMSI);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading imsi from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No imsi in CarrierTestConfig file ");
            return null;
        }
    }

    String getFakeIccid() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_ICCID);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading iccid from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No iccid in CarrierTestConfig file ");
            return null;
        }
    }

    String getFakeMccMnc() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_MCCMNC);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading mccmnc from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No mccmnc in CarrierTestConfig file ");
            return null;
        }
    }

    String getFakePnnHomeName() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_PNN);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading pnn from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No pnn in CarrierTestConfig file ");
            return null;
        }
    }

    String getFakeSpn() {
        try {
            String string = this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_SPN);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reading spn from CarrierTestConfig file: ");
            stringBuilder.append(string);
            Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
            return string;
        }
        catch (NullPointerException nullPointerException) {
            Rlog.w((String)LOG_TAG, (String)"No spn in CarrierTestConfig file ");
            return null;
        }
    }

    boolean isInTestMode() {
        boolean bl = this.mCarrierTestParamMap.containsKey(CARRIER_TEST_XML_ITEM_KEY_STRING_ISINTESTMODE) && this.mCarrierTestParamMap.get(CARRIER_TEST_XML_ITEM_KEY_STRING_ISINTESTMODE).equals("true");
        return bl;
    }

    void override(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_ISINTESTMODE, "true");
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_MCCMNC, string);
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_IMSI, string2);
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_ICCID, string3);
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_GID1, string4);
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_GID2, string5);
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_PNN, string6);
        this.mCarrierTestParamMap.put(CARRIER_TEST_XML_ITEM_KEY_STRING_SPN, string7);
    }
}

