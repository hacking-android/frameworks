/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.Preconditions;
import java.util.List;

public abstract class ViewStructure {
    public abstract int addChildCount(int var1);

    public abstract void asyncCommit();

    public abstract ViewStructure asyncNewChild(int var1);

    public abstract AutofillId getAutofillId();

    public abstract int getChildCount();

    public abstract Bundle getExtras();

    public abstract CharSequence getHint();

    public abstract Rect getTempRect();

    public abstract CharSequence getText();

    public abstract int getTextSelectionEnd();

    public abstract int getTextSelectionStart();

    public abstract boolean hasExtras();

    public abstract ViewStructure newChild(int var1);

    public abstract HtmlInfo.Builder newHtmlInfoBuilder(String var1);

    public abstract void setAccessibilityFocused(boolean var1);

    public abstract void setActivated(boolean var1);

    public abstract void setAlpha(float var1);

    public abstract void setAssistBlocked(boolean var1);

    public abstract void setAutofillHints(String[] var1);

    public abstract void setAutofillId(AutofillId var1);

    public abstract void setAutofillId(AutofillId var1, int var2);

    public abstract void setAutofillOptions(CharSequence[] var1);

    public abstract void setAutofillType(int var1);

    public abstract void setAutofillValue(AutofillValue var1);

    public abstract void setCheckable(boolean var1);

    public abstract void setChecked(boolean var1);

    public abstract void setChildCount(int var1);

    public abstract void setClassName(String var1);

    public abstract void setClickable(boolean var1);

    public abstract void setContentDescription(CharSequence var1);

    public abstract void setContextClickable(boolean var1);

    public abstract void setDataIsSensitive(boolean var1);

    public abstract void setDimens(int var1, int var2, int var3, int var4, int var5, int var6);

    public abstract void setElevation(float var1);

    public abstract void setEnabled(boolean var1);

    public abstract void setFocusable(boolean var1);

    public abstract void setFocused(boolean var1);

    public abstract void setHint(CharSequence var1);

    public abstract void setHtmlInfo(HtmlInfo var1);

    public abstract void setId(int var1, String var2, String var3, String var4);

    public void setImportantForAutofill(int n) {
    }

    public abstract void setInputType(int var1);

    public abstract void setLocaleList(LocaleList var1);

    public abstract void setLongClickable(boolean var1);

    public void setMaxTextEms(int n) {
    }

    public void setMaxTextLength(int n) {
    }

    public void setMinTextEms(int n) {
    }

    public abstract void setOpaque(boolean var1);

    public abstract void setSelected(boolean var1);

    public abstract void setText(CharSequence var1);

    public abstract void setText(CharSequence var1, int var2, int var3);

    public void setTextIdEntry(String string2) {
        Preconditions.checkNotNull(string2);
    }

    public abstract void setTextLines(int[] var1, int[] var2);

    public abstract void setTextStyle(float var1, int var2, int var3, int var4);

    public abstract void setTransformation(Matrix var1);

    public abstract void setVisibility(int var1);

    public abstract void setWebDomain(String var1);

    public static abstract class HtmlInfo {
        public abstract List<Pair<String, String>> getAttributes();

        public abstract String getTag();

        public static abstract class Builder {
            public abstract Builder addAttribute(String var1, String var2);

            public abstract HtmlInfo build();
        }

    }

}

