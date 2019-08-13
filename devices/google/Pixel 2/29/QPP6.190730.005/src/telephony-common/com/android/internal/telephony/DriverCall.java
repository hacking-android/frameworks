/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import com.android.internal.telephony.ATParseEx;
import com.android.internal.telephony.ATResponseParser;
import com.android.internal.telephony.UUSInfo;

public class DriverCall
implements Comparable<DriverCall> {
    public static final int AUDIO_QUALITY_AMR = 1;
    public static final int AUDIO_QUALITY_AMR_WB = 2;
    public static final int AUDIO_QUALITY_EVRC = 6;
    public static final int AUDIO_QUALITY_EVRC_B = 7;
    public static final int AUDIO_QUALITY_EVRC_NW = 9;
    public static final int AUDIO_QUALITY_EVRC_WB = 8;
    public static final int AUDIO_QUALITY_GSM_EFR = 3;
    public static final int AUDIO_QUALITY_GSM_FR = 4;
    public static final int AUDIO_QUALITY_GSM_HR = 5;
    public static final int AUDIO_QUALITY_UNSPECIFIED = 0;
    static final String LOG_TAG = "DriverCall";
    public int TOA;
    public int als;
    public int audioQuality = 0;
    @UnsupportedAppUsage
    public int index;
    @UnsupportedAppUsage
    public boolean isMT;
    public boolean isMpty;
    @UnsupportedAppUsage
    public boolean isVoice;
    public boolean isVoicePrivacy;
    @UnsupportedAppUsage
    public String name;
    public int namePresentation;
    @UnsupportedAppUsage
    public String number;
    @UnsupportedAppUsage
    public int numberPresentation;
    @UnsupportedAppUsage
    public State state;
    public UUSInfo uusInfo;

    static DriverCall fromCLCCLine(String string) {
        boolean bl;
        DriverCall driverCall = new DriverCall();
        ATResponseParser aTResponseParser = new ATResponseParser(string);
        try {
            driverCall.index = aTResponseParser.nextInt();
            driverCall.isMT = aTResponseParser.nextBoolean();
            driverCall.state = DriverCall.stateFromCLCC(aTResponseParser.nextInt());
            bl = aTResponseParser.nextInt() == 0;
        }
        catch (ATParseEx aTParseEx) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid CLCC line: '");
            stringBuilder.append(string);
            stringBuilder.append("'");
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
            return null;
        }
        driverCall.isVoice = bl;
        driverCall.isMpty = aTResponseParser.nextBoolean();
        driverCall.numberPresentation = 1;
        if (aTResponseParser.hasMore()) {
            driverCall.number = PhoneNumberUtils.extractNetworkPortionAlt((String)aTResponseParser.nextString());
            if (driverCall.number.length() == 0) {
                driverCall.number = null;
            }
            driverCall.TOA = aTResponseParser.nextInt();
            driverCall.number = PhoneNumberUtils.stringFromStringAndTOA((String)driverCall.number, (int)driverCall.TOA);
        }
        return driverCall;
    }

    public static int presentationFromCLIP(int n) throws ATParseEx {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        return 4;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("illegal presentation ");
                    stringBuilder.append(n);
                    throw new ATParseEx(stringBuilder.toString());
                }
                return 3;
            }
            return 2;
        }
        return 1;
    }

    public static State stateFromCLCC(int n) throws ATParseEx {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                return State.WAITING;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("illegal call state ");
                            stringBuilder.append(n);
                            throw new ATParseEx(stringBuilder.toString());
                        }
                        return State.INCOMING;
                    }
                    return State.ALERTING;
                }
                return State.DIALING;
            }
            return State.HOLDING;
        }
        return State.ACTIVE;
    }

    @Override
    public int compareTo(DriverCall driverCall) {
        int n = this.index;
        int n2 = driverCall.index;
        if (n < n2) {
            return -1;
        }
        return n != n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=");
        stringBuilder.append(this.index);
        stringBuilder.append(",");
        stringBuilder.append((Object)this.state);
        stringBuilder.append(",toa=");
        stringBuilder.append(this.TOA);
        stringBuilder.append(",");
        String string = this.isMpty ? "conf" : "norm";
        stringBuilder.append(string);
        stringBuilder.append(",");
        string = this.isMT ? "mt" : "mo";
        stringBuilder.append(string);
        stringBuilder.append(",");
        stringBuilder.append(this.als);
        stringBuilder.append(",");
        string = this.isVoice ? "voc" : "nonvoc";
        stringBuilder.append(string);
        stringBuilder.append(",");
        string = this.isVoicePrivacy ? "evp" : "noevp";
        stringBuilder.append(string);
        stringBuilder.append(",,cli=");
        stringBuilder.append(this.numberPresentation);
        stringBuilder.append(",,");
        stringBuilder.append(this.namePresentation);
        stringBuilder.append(",audioQuality=");
        stringBuilder.append(this.audioQuality);
        return stringBuilder.toString();
    }

    public static enum State {
        ACTIVE,
        HOLDING,
        DIALING,
        ALERTING,
        INCOMING,
        WAITING;
        
    }

}

