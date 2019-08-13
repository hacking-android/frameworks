/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.telecom.PhoneAccountHandle
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.SmsMessage
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.telephony.VisualVoicemailSms
 *  android.telephony.VisualVoicemailSms$Builder
 *  android.telephony.VisualVoicemailSmsFilterSettings
 *  android.util.ArrayMap
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.telecom.PhoneAccountHandle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.VisualVoicemailSms;
import android.telephony.VisualVoicemailSmsFilterSettings;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.VisualVoicemailSmsParser;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisualVoicemailSmsFilter {
    private static final PhoneAccountHandleConverter DEFAULT_PHONE_ACCOUNT_HANDLE_CONVERTER;
    private static final ComponentName PSTN_CONNECTION_SERVICE_COMPONENT;
    private static final String TAG = "VvmSmsFilter";
    private static final String TELEPHONY_SERVICE_PACKAGE = "com.android.phone";
    private static Map<String, List<Pattern>> sPatterns;
    private static PhoneAccountHandleConverter sPhoneAccountHandleConverter;

    static {
        PSTN_CONNECTION_SERVICE_COMPONENT = new ComponentName(TELEPHONY_SERVICE_PACKAGE, "com.android.services.telephony.TelephonyConnectionService");
        sPhoneAccountHandleConverter = DEFAULT_PHONE_ACCOUNT_HANDLE_CONVERTER = new PhoneAccountHandleConverter(){

            @Override
            public PhoneAccountHandle fromSubId(int n) {
                if (!SubscriptionManager.isValidSubscriptionId((int)n)) {
                    return null;
                }
                if ((n = SubscriptionManager.getPhoneId((int)n)) == -1) {
                    return null;
                }
                return new PhoneAccountHandle(PSTN_CONNECTION_SERVICE_COMPONENT, PhoneFactory.getPhone(n).getFullIccSerialNumber());
            }
        };
    }

    private static void buildPatternsMap(Context arrstring) {
        if (sPatterns != null) {
            return;
        }
        sPatterns = new ArrayMap();
        for (String string : arrstring.getResources().getStringArray(17236076)) {
            arrstring = string.split(";")[0].split(",");
            Pattern object = Pattern.compile(string.split(";")[1]);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                String string2 = arrstring[i];
                if (!sPatterns.containsKey(string2)) {
                    sPatterns.put(string2, new ArrayList());
                }
                sPatterns.get(string2).add(object);
            }
        }
    }

    public static boolean filter(Context object, byte[][] object2, String object3, int n, int n2) {
        VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings = ((TelephonyManager)object.getSystemService("phone")).getActiveVisualVoicemailSmsFilterSettings(n2);
        if (visualVoicemailSmsFilterSettings == null) {
            if ((object2 = VisualVoicemailSmsFilter.getFullMessage(object2, (String)object3)) != null && VisualVoicemailSmsFilter.messageBodyMatchesVvmPattern((Context)object, n2, object2.fullMessageBody)) {
                Log.e((String)TAG, (String)"SMS matching VVM format received but the filter not been set yet");
                return true;
            }
            return false;
        }
        PhoneAccountHandle phoneAccountHandle = sPhoneAccountHandleConverter.fromSubId(n2);
        if (phoneAccountHandle == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to convert subId ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" to PhoneAccountHandle");
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            return false;
        }
        if ((object3 = VisualVoicemailSmsFilter.getFullMessage(object2, (String)object3)) == null) {
            Log.i((String)TAG, (String)"Unparsable SMS received");
            object2 = VisualVoicemailSmsFilter.parseAsciiPduMessage(object2);
            object2 = VisualVoicemailSmsParser.parseAlternativeFormat((String)object2);
            if (object2 != null) {
                VisualVoicemailSmsFilter.sendVvmSmsBroadcast((Context)object, visualVoicemailSmsFilterSettings, phoneAccountHandle, (VisualVoicemailSmsParser.WrappedMessageData)object2, null);
            }
            return false;
        }
        object2 = ((FullMessage)object3).fullMessageBody;
        Object object4 = visualVoicemailSmsFilterSettings.clientPrefix;
        if ((object4 = VisualVoicemailSmsParser.parse((String)object4, (String)object2)) != null) {
            if (visualVoicemailSmsFilterSettings.destinationPort == -2) {
                if (n == -1) {
                    Log.i((String)TAG, (String)"SMS matching VVM format received but is not a DATA SMS");
                    return false;
                }
            } else if (visualVoicemailSmsFilterSettings.destinationPort != -1 && visualVoicemailSmsFilterSettings.destinationPort != n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("SMS matching VVM format received but is not directed to port ");
                ((StringBuilder)object).append(visualVoicemailSmsFilterSettings.destinationPort);
                Log.i((String)TAG, (String)((StringBuilder)object).toString());
                return false;
            }
            if (!visualVoicemailSmsFilterSettings.originatingNumbers.isEmpty() && !VisualVoicemailSmsFilter.isSmsFromNumbers(((FullMessage)object3).firstMessage, visualVoicemailSmsFilterSettings.originatingNumbers)) {
                Log.i((String)TAG, (String)"SMS matching VVM format received but is not from originating numbers");
                return false;
            }
            VisualVoicemailSmsFilter.sendVvmSmsBroadcast((Context)object, visualVoicemailSmsFilterSettings, phoneAccountHandle, (VisualVoicemailSmsParser.WrappedMessageData)object4, null);
            return true;
        }
        if (VisualVoicemailSmsFilter.messageBodyMatchesVvmPattern((Context)object, n2, (String)object2)) {
            Log.w((String)TAG, (String)"SMS matches pattern but has illegal format, still dropping as VVM SMS");
            VisualVoicemailSmsFilter.sendVvmSmsBroadcast((Context)object, visualVoicemailSmsFilterSettings, phoneAccountHandle, null, (String)object2);
            return true;
        }
        return false;
    }

    private static FullMessage getFullMessage(byte[][] arrby, String string) {
        FullMessage fullMessage = new FullMessage();
        StringBuilder stringBuilder = new StringBuilder();
        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])arrby[i], (String)string);
            if (smsMessage == null) {
                return null;
            }
            if (fullMessage.firstMessage == null) {
                fullMessage.firstMessage = smsMessage;
            }
            String string2 = smsMessage.getMessageBody();
            Object object = string2;
            if (string2 == null) {
                object = string2;
                if (smsMessage.getUserData() != null) {
                    object = ByteBuffer.wrap(smsMessage.getUserData());
                    try {
                        object = charsetDecoder.decode((ByteBuffer)object).toString();
                    }
                    catch (CharacterCodingException characterCodingException) {
                        return null;
                    }
                }
            }
            if (object == null) continue;
            stringBuilder.append((String)object);
        }
        fullMessage.fullMessageBody = stringBuilder.toString();
        return fullMessage;
    }

    private static boolean isSmsFromNumbers(SmsMessage smsMessage, List<String> object) {
        if (smsMessage == null) {
            Log.e((String)TAG, (String)"Unable to create SmsMessage from PDU, cannot determine originating number");
            return false;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (!PhoneNumberUtils.compare((String)((String)object.next()), (String)smsMessage.getOriginatingAddress())) continue;
            return true;
        }
        return false;
    }

    private static boolean messageBodyMatchesVvmPattern(Context object, int n, String charSequence) {
        VisualVoicemailSmsFilter.buildPatternsMap((Context)object);
        object = ((TelephonyManager)object.getSystemService(TelephonyManager.class)).getSimOperator(n);
        object = sPatterns.get(object);
        if (object != null && !object.isEmpty()) {
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                object = (Pattern)iterator.next();
                if (!((Pattern)object).matcher(charSequence).matches()) continue;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Incoming SMS matches pattern ");
                ((StringBuilder)charSequence).append(object);
                Log.w((String)TAG, (String)((StringBuilder)charSequence).toString());
                return true;
            }
            return false;
        }
        return false;
    }

    private static String parseAsciiPduMessage(byte[][] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(new String(arrby[i], StandardCharsets.US_ASCII));
        }
        return stringBuilder.toString();
    }

    private static void sendVvmSmsBroadcast(Context context, VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings, PhoneAccountHandle phoneAccountHandle, VisualVoicemailSmsParser.WrappedMessageData wrappedMessageData, String string) {
        Log.i((String)TAG, (String)"VVM SMS received");
        Intent intent = new Intent("com.android.internal.provider.action.VOICEMAIL_SMS_RECEIVED");
        VisualVoicemailSms.Builder builder = new VisualVoicemailSms.Builder();
        if (wrappedMessageData != null) {
            builder.setPrefix(wrappedMessageData.prefix);
            builder.setFields(wrappedMessageData.fields);
        }
        if (string != null) {
            builder.setMessageBody(string);
        }
        builder.setPhoneAccountHandle(phoneAccountHandle);
        intent.putExtra("android.provider.extra.VOICEMAIL_SMS", (Parcelable)builder.build());
        intent.putExtra("android.provider.extra.TARGET_PACAKGE", visualVoicemailSmsFilterSettings.packageName);
        intent.setPackage(TELEPHONY_SERVICE_PACKAGE);
        context.sendBroadcast(intent);
    }

    @VisibleForTesting
    public static void setPhoneAccountHandleConverterForTest(PhoneAccountHandleConverter phoneAccountHandleConverter) {
        sPhoneAccountHandleConverter = phoneAccountHandleConverter == null ? DEFAULT_PHONE_ACCOUNT_HANDLE_CONVERTER : phoneAccountHandleConverter;
    }

    private static class FullMessage {
        public SmsMessage firstMessage;
        public String fullMessageBody;

        private FullMessage() {
        }
    }

    @VisibleForTesting
    public static interface PhoneAccountHandleConverter {
        public PhoneAccountHandle fromSubId(int var1);
    }

}

