/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.i18n.phonenumbers.AsYouTypeFormatter
 *  com.android.i18n.phonenumbers.PhoneNumberUtil
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import com.android.i18n.phonenumbers.AsYouTypeFormatter;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import java.util.Locale;

public class PhoneNumberFormattingTextWatcher
implements TextWatcher {
    @UnsupportedAppUsage
    private AsYouTypeFormatter mFormatter;
    private boolean mSelfChange = false;
    private boolean mStopFormatting;

    public PhoneNumberFormattingTextWatcher() {
        this(Locale.getDefault().getCountry());
    }

    public PhoneNumberFormattingTextWatcher(String string2) {
        if (string2 != null) {
            this.mFormatter = PhoneNumberUtil.getInstance().getAsYouTypeFormatter(string2);
            return;
        }
        throw new IllegalArgumentException();
    }

    private String getFormattedNumber(char c, boolean bl) {
        String string2 = bl ? this.mFormatter.inputDigitAndRememberPosition(c) : this.mFormatter.inputDigit(c);
        return string2;
    }

    private boolean hasSeparator(CharSequence charSequence, int n, int n2) {
        for (int i = n; i < n + n2; ++i) {
            if (PhoneNumberUtils.isNonSeparator(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    private String reformat(CharSequence charSequence, int n) {
        String string2 = null;
        this.mFormatter.clear();
        char c = '\u0000';
        boolean bl = false;
        int n2 = charSequence.length();
        char c2 = c;
        for (int i = 0; i < n2; ++i) {
            char c3 = charSequence.charAt(i);
            String string3 = string2;
            c = c2;
            boolean bl2 = bl;
            if (PhoneNumberUtils.isNonSeparator(c3)) {
                bl2 = bl;
                if (c2 != '\u0000') {
                    string2 = this.getFormattedNumber(c2, bl);
                    bl2 = false;
                }
                c = c3;
                string3 = string2;
            }
            bl = bl2;
            if (i == n - 1) {
                bl = true;
            }
            string2 = string3;
            c2 = c;
        }
        if (c2 != '\u0000') {
            string2 = this.getFormattedNumber(c2, bl);
        }
        return string2;
    }

    private void stopFormatting() {
        this.mStopFormatting = true;
        this.mFormatter.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void afterTextChanged(Editable editable) {
        synchronized (this) {
            boolean bl = this.mStopFormatting;
            boolean bl2 = true;
            if (bl) {
                if (editable.length() == 0) {
                    bl2 = false;
                }
                this.mStopFormatting = bl2;
                return;
            }
            bl2 = this.mSelfChange;
            if (bl2) {
                return;
            }
            String string2 = this.reformat(editable, Selection.getSelectionEnd(editable));
            if (string2 != null) {
                int n = this.mFormatter.getRememberedPosition();
                this.mSelfChange = true;
                editable.replace(0, editable.length(), string2, 0, string2.length());
                if (string2.equals(editable.toString())) {
                    Selection.setSelection(editable, n);
                }
                this.mSelfChange = false;
            }
            PhoneNumberUtils.ttsSpanAsPhoneNumber(editable, 0, editable.length());
            return;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        if (!this.mSelfChange && !this.mStopFormatting) {
            if (n2 > 0 && this.hasSeparator(charSequence, n, n2)) {
                this.stopFormatting();
            }
            return;
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        if (!this.mSelfChange && !this.mStopFormatting) {
            if (n3 > 0 && this.hasSeparator(charSequence, n, n3)) {
                this.stopFormatting();
            }
            return;
        }
    }
}

