/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.util;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import com.google.android.util.AbstractMessageParser;
import com.google.android.util.SmileyResources;
import java.util.ArrayList;

public class SmileyParser
extends AbstractMessageParser {
    private SmileyResources mRes;

    public SmileyParser(String string2, SmileyResources smileyResources) {
        super(string2, true, false, false, false, false, false);
        this.mRes = smileyResources;
    }

    @Override
    protected AbstractMessageParser.Resources getResources() {
        return this.mRes;
    }

    public CharSequence getSpannableString(Context context) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.getPartCount() == 0) {
            return "";
        }
        ArrayList<AbstractMessageParser.Token> arrayList = this.getPart(0).getTokens();
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            int n2;
            AbstractMessageParser.Token token = arrayList.get(i);
            int n3 = spannableStringBuilder.length();
            spannableStringBuilder.append(token.getRawText());
            if (token.getType() != AbstractMessageParser.Token.Type.SMILEY || (n2 = this.mRes.getSmileyRes(token.getRawText())) == -1) continue;
            spannableStringBuilder.setSpan(new ImageSpan(context, n2), n3, spannableStringBuilder.length(), 33);
        }
        return spannableStringBuilder;
    }
}

