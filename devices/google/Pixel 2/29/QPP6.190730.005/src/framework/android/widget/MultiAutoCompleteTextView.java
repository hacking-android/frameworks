/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.QwertyKeyListener;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;

public class MultiAutoCompleteTextView
extends AutoCompleteTextView {
    private Tokenizer mTokenizer;

    public MultiAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public MultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842859);
    }

    public MultiAutoCompleteTextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MultiAutoCompleteTextView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public boolean enoughToFilter() {
        Tokenizer tokenizer;
        Editable editable = this.getText();
        int n = this.getSelectionEnd();
        if (n >= 0 && (tokenizer = this.mTokenizer) != null) {
            return n - tokenizer.findTokenStart(editable, n) >= this.getThreshold();
        }
        return false;
    }

    void finishInit() {
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return MultiAutoCompleteTextView.class.getName();
    }

    @Override
    protected void performFiltering(CharSequence object, int n) {
        if (this.enoughToFilter()) {
            int n2 = this.getSelectionEnd();
            this.performFiltering((CharSequence)object, this.mTokenizer.findTokenStart((CharSequence)object, n2), n2, n);
        } else {
            this.dismissDropDown();
            object = this.getFilter();
            if (object != null) {
                ((Filter)object).filter(null);
            }
        }
    }

    protected void performFiltering(CharSequence charSequence, int n, int n2, int n3) {
        this.getFilter().filter(charSequence.subSequence(n, n2), this);
    }

    @Override
    public void performValidation() {
        AutoCompleteTextView.Validator validator = this.getValidator();
        if (validator != null && this.mTokenizer != null) {
            Editable editable = this.getText();
            int n = this.getText().length();
            while (n > 0) {
                int n2 = this.mTokenizer.findTokenStart(editable, n);
                CharSequence charSequence = editable.subSequence(n2, this.mTokenizer.findTokenEnd(editable, n2));
                if (TextUtils.isEmpty(charSequence)) {
                    editable.replace(n2, n, "");
                } else if (!validator.isValid(charSequence)) {
                    editable.replace(n2, n, this.mTokenizer.terminateToken(validator.fixText(charSequence)));
                }
                n = n2;
            }
            return;
        }
    }

    @Override
    protected void replaceText(CharSequence charSequence) {
        this.clearComposingText();
        int n = this.getSelectionEnd();
        int n2 = this.mTokenizer.findTokenStart(this.getText(), n);
        Editable editable = this.getText();
        QwertyKeyListener.markAsReplaced(editable, n2, n, TextUtils.substring(editable, n2, n));
        editable.replace(n2, n, this.mTokenizer.terminateToken(charSequence));
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.mTokenizer = tokenizer;
    }

    public static class CommaTokenizer
    implements Tokenizer {
        @Override
        public int findTokenEnd(CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            while (n < n2) {
                if (charSequence.charAt(n) == ',') {
                    return n;
                }
                ++n;
            }
            return n2;
        }

        @Override
        public int findTokenStart(CharSequence charSequence, int n) {
            int n2;
            int n3 = n;
            do {
                n2 = n3;
                if (n3 <= 0) break;
                n2 = n3;
                if (charSequence.charAt(n3 - 1) == ',') break;
                --n3;
            } while (true);
            while (n2 < n && charSequence.charAt(n2) == ' ') {
                ++n2;
            }
            return n2;
        }

        @Override
        public CharSequence terminateToken(CharSequence charSequence) {
            int n;
            for (n = charSequence.length(); n > 0 && charSequence.charAt(n - 1) == ' '; --n) {
            }
            if (n > 0 && charSequence.charAt(n - 1) == ',') {
                return charSequence;
            }
            if (charSequence instanceof Spanned) {
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append((Object)charSequence);
                charSequence2.append(", ");
                charSequence2 = new SpannableString(charSequence2.toString());
                TextUtils.copySpansFrom((Spanned)charSequence, 0, charSequence.length(), Object.class, (Spannable)charSequence2, 0);
                return charSequence2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)charSequence);
            stringBuilder.append(", ");
            return stringBuilder.toString();
        }
    }

    public static interface Tokenizer {
        public int findTokenEnd(CharSequence var1, int var2);

        public int findTokenStart(CharSequence var1, int var2);

        public CharSequence terminateToken(CharSequence var1);
    }

}

