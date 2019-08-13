/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.telephony.ims.ImsConferenceState
 *  android.util.Log
 *  android.util.Xml
 *  com.android.internal.util.XmlUtils
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.android.internal.telephony.test;

import android.os.Bundle;
import android.telephony.ims.ImsConferenceState;
import android.util.Log;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TestConferenceEventPackageParser {
    private static final String LOG_TAG = "TestConferenceEventPackageParser";
    private static final String PARTICIPANT_TAG = "participant";
    private InputStream mInputStream;

    public TestConferenceEventPackageParser(InputStream inputStream) {
        this.mInputStream = inputStream;
    }

    private Bundle parseParticipant(XmlPullParser object) throws IOException, XmlPullParserException {
        Bundle bundle = new Bundle();
        String string = "";
        String string2 = "";
        String string3 = "";
        String string4 = "";
        int n = object.getDepth();
        while (XmlUtils.nextElementWithin((XmlPullParser)object, (int)n)) {
            if (object.getName().equals("user")) {
                object.next();
                string = object.getText();
                continue;
            }
            if (object.getName().equals("display-text")) {
                object.next();
                string2 = object.getText();
                continue;
            }
            if (object.getName().equals("endpoint")) {
                object.next();
                string3 = object.getText();
                continue;
            }
            if (!object.getName().equals("status")) continue;
            object.next();
            string4 = object.getText();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("User: ");
        ((StringBuilder)object).append(string);
        Log.v((String)LOG_TAG, (String)((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("DisplayText: ");
        ((StringBuilder)object).append(string2);
        Log.v((String)LOG_TAG, (String)((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("Endpoint: ");
        ((StringBuilder)object).append(string3);
        Log.v((String)LOG_TAG, (String)((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("Status: ");
        ((StringBuilder)object).append(string4);
        Log.v((String)LOG_TAG, (String)((StringBuilder)object).toString());
        bundle.putString("user", string);
        bundle.putString("display-text", string2);
        bundle.putString("endpoint", string3);
        bundle.putString("status", string4);
        return bundle;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ImsConferenceState parse() {
        Throwable throwable2222;
        ImsConferenceState imsConferenceState = new ImsConferenceState();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(this.mInputStream, null);
        xmlPullParser.nextTag();
        int n = xmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin((XmlPullParser)xmlPullParser, (int)n)) {
            if (!xmlPullParser.getName().equals(PARTICIPANT_TAG)) continue;
            Log.v((String)LOG_TAG, (String)"Found participant.");
            Bundle bundle = this.parseParticipant(xmlPullParser);
            imsConferenceState.mParticipants.put(bundle.getString("endpoint"), bundle);
        }
        try {
            this.mInputStream.close();
            return imsConferenceState;
        }
        catch (IOException iOException) {
            Log.e((String)LOG_TAG, (String)"Failed to close test conference event package InputStream", (Throwable)iOException);
            return null;
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException | XmlPullParserException throwable3) {}
            {
                Log.e((String)LOG_TAG, (String)"Failed to read test conference event package from XML file", (Throwable)throwable3);
            }
            try {
                this.mInputStream.close();
                return null;
            }
            catch (IOException iOException) {
                Log.e((String)LOG_TAG, (String)"Failed to close test conference event package InputStream", (Throwable)iOException);
                return null;
            }
        }
        try {
            this.mInputStream.close();
        }
        catch (IOException iOException) {
            Log.e((String)LOG_TAG, (String)"Failed to close test conference event package InputStream", (Throwable)iOException);
            return null;
        }
        throw throwable2222;
    }
}

