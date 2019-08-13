/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.text.Emoji;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import java.text.BreakIterator;
import java.util.Arrays;

public abstract class SmsMessageBase {
    protected String mEmailBody;
    protected String mEmailFrom;
    protected int mIndexOnIcc = -1;
    protected boolean mIsEmail;
    @UnsupportedAppUsage
    protected boolean mIsMwi;
    @UnsupportedAppUsage
    protected String mMessageBody;
    @UnsupportedAppUsage
    public int mMessageRef;
    @UnsupportedAppUsage
    protected boolean mMwiDontStore;
    @UnsupportedAppUsage
    protected boolean mMwiSense;
    @UnsupportedAppUsage
    protected SmsAddress mOriginatingAddress;
    @UnsupportedAppUsage
    protected byte[] mPdu;
    protected String mPseudoSubject;
    protected SmsAddress mRecipientAddress;
    @UnsupportedAppUsage
    protected String mScAddress;
    protected long mScTimeMillis;
    protected int mStatusOnIcc = -1;
    protected byte[] mUserData;
    @UnsupportedAppUsage
    protected SmsHeader mUserDataHeader;

    public static GsmAlphabet.TextEncodingDetails calcUnicodeEncodingDetails(CharSequence charSequence) {
        GsmAlphabet.TextEncodingDetails textEncodingDetails = new GsmAlphabet.TextEncodingDetails();
        int n = charSequence.length() * 2;
        textEncodingDetails.codeUnitSize = 3;
        textEncodingDetails.codeUnitCount = charSequence.length();
        if (n > 140) {
            int n2;
            int n3 = n2 = 134;
            if (!SmsMessage.hasEmsSupport()) {
                n3 = n2;
                if (n <= (134 - 2) * 9) {
                    n3 = 134 - 2;
                }
            }
            n = 0;
            n2 = 0;
            while (n < charSequence.length()) {
                int n4 = SmsMessageBase.findNextUnicodePosition(n, n3, charSequence);
                if (n4 == charSequence.length()) {
                    textEncodingDetails.codeUnitsRemaining = n3 / 2 + n - charSequence.length();
                }
                n = n4;
                ++n2;
            }
            textEncodingDetails.msgCount = n2;
        } else {
            textEncodingDetails.msgCount = 1;
            textEncodingDetails.codeUnitsRemaining = (140 - n) / 2;
        }
        return textEncodingDetails;
    }

    public static int findNextUnicodePosition(int n, int n2, CharSequence charSequence) {
        int n3;
        n2 = n3 = Math.min(n2 / 2 + n, charSequence.length());
        if (n3 < charSequence.length()) {
            BreakIterator breakIterator = BreakIterator.getCharacterInstance();
            breakIterator.setText(charSequence.toString());
            n2 = n3;
            if (!breakIterator.isBoundary(n3)) {
                n2 = breakIterator.preceding(n3);
                while (n2 + 4 <= n3 && Emoji.isRegionalIndicatorSymbol(Character.codePointAt(charSequence, n2)) && Emoji.isRegionalIndicatorSymbol(Character.codePointAt(charSequence, n2 + 2))) {
                    n2 += 4;
                }
                if (n2 <= n) {
                    n2 = n3;
                    if (Character.isHighSurrogate(charSequence.charAt(n3 - 1))) {
                        n2 = n3 - 1;
                    }
                }
            }
        }
        return n2;
    }

    protected void extractEmailAddressFromMessageBody() {
        String[] arrstring = this.mMessageBody.split("( /)|( )", 2);
        if (arrstring.length < 2) {
            return;
        }
        this.mEmailFrom = arrstring[0];
        this.mEmailBody = arrstring[1];
        this.mIsEmail = Telephony.Mms.isEmailAddress(this.mEmailFrom);
    }

    @UnsupportedAppUsage
    public String getDisplayMessageBody() {
        if (this.mIsEmail) {
            return this.mEmailBody;
        }
        return this.getMessageBody();
    }

    @UnsupportedAppUsage
    public String getDisplayOriginatingAddress() {
        if (this.mIsEmail) {
            return this.mEmailFrom;
        }
        return this.getOriginatingAddress();
    }

    public String getEmailBody() {
        return this.mEmailBody;
    }

    public String getEmailFrom() {
        return this.mEmailFrom;
    }

    public int getIndexOnIcc() {
        return this.mIndexOnIcc;
    }

    @UnsupportedAppUsage
    public String getMessageBody() {
        return this.mMessageBody;
    }

    public abstract SmsConstants.MessageClass getMessageClass();

    @UnsupportedAppUsage
    public String getOriginatingAddress() {
        SmsAddress smsAddress = this.mOriginatingAddress;
        if (smsAddress == null) {
            return null;
        }
        return smsAddress.getAddressString();
    }

    public byte[] getPdu() {
        return this.mPdu;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public abstract int getProtocolIdentifier();

    @UnsupportedAppUsage
    public String getPseudoSubject() {
        String string2;
        String string3 = string2 = this.mPseudoSubject;
        if (string2 == null) {
            string3 = "";
        }
        return string3;
    }

    public String getRecipientAddress() {
        SmsAddress smsAddress = this.mRecipientAddress;
        if (smsAddress == null) {
            return null;
        }
        return smsAddress.getAddressString();
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public String getServiceCenterAddress() {
        return this.mScAddress;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public abstract int getStatus();

    public int getStatusOnIcc() {
        return this.mStatusOnIcc;
    }

    @UnsupportedAppUsage
    public long getTimestampMillis() {
        return this.mScTimeMillis;
    }

    @UnsupportedAppUsage
    public byte[] getUserData() {
        return this.mUserData;
    }

    @UnsupportedAppUsage
    public SmsHeader getUserDataHeader() {
        return this.mUserDataHeader;
    }

    public abstract boolean isCphsMwiMessage();

    public boolean isEmail() {
        return this.mIsEmail;
    }

    public abstract boolean isMWIClearMessage();

    public abstract boolean isMWISetMessage();

    public abstract boolean isMwiDontStore();

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public abstract boolean isReplace();

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public abstract boolean isReplyPathPresent();

    @UnsupportedAppUsage
    public abstract boolean isStatusReportMessage();

    protected void parseMessageBody() {
        SmsAddress smsAddress = this.mOriginatingAddress;
        if (smsAddress != null && smsAddress.couldBeEmailGateway()) {
            this.extractEmailAddressFromMessageBody();
        }
    }

    public static abstract class SubmitPduBase {
        @UnsupportedAppUsage
        public byte[] encodedMessage;
        @UnsupportedAppUsage
        public byte[] encodedScAddress;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SubmitPdu: encodedScAddress = ");
            stringBuilder.append(Arrays.toString(this.encodedScAddress));
            stringBuilder.append(", encodedMessage = ");
            stringBuilder.append(Arrays.toString(this.encodedMessage));
            return stringBuilder.toString();
        }
    }

}

