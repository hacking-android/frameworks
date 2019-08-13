/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$TextLinksParams
 *  android.view.textclassifier.-$$Lambda$TextLinksParams$km8pN8nazHT6NQiHykIrRALWbkE
 */
package android.view.textclassifier;

import android.os.Parcelable;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.EventLog;
import android.view.textclassifier.-$;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier._$$Lambda$TextLinksParams$km8pN8nazHT6NQiHykIrRALWbkE;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public final class TextLinksParams {
    private static final Function<TextLinks.TextLink, TextLinks.TextLinkSpan> DEFAULT_SPAN_FACTORY = _$$Lambda$TextLinksParams$km8pN8nazHT6NQiHykIrRALWbkE.INSTANCE;
    private final int mApplyStrategy;
    private final TextClassifier.EntityConfig mEntityConfig;
    private final Function<TextLinks.TextLink, TextLinks.TextLinkSpan> mSpanFactory;

    private TextLinksParams(int n, Function<TextLinks.TextLink, TextLinks.TextLinkSpan> function) {
        this.mApplyStrategy = n;
        this.mSpanFactory = function;
        this.mEntityConfig = TextClassifier.EntityConfig.createWithHints(null);
    }

    private static int checkApplyStrategy(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("Invalid apply strategy. See TextLinksParams.ApplyStrategy for options.");
        }
        return n;
    }

    public static TextLinksParams fromLinkMask(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if ((n & 1) != 0) {
            arrayList.add("url");
        }
        if ((n & 2) != 0) {
            arrayList.add("email");
        }
        if ((n & 4) != 0) {
            arrayList.add("phone");
        }
        if ((n & 8) != 0) {
            arrayList.add("address");
        }
        return new Builder().setEntityConfig(TextClassifier.EntityConfig.createWithExplicitEntityList(arrayList)).build();
    }

    static /* synthetic */ TextLinks.TextLinkSpan lambda$static$0(TextLinks.TextLink textLink) {
        return new TextLinks.TextLinkSpan(textLink);
    }

    public int apply(Spannable spannable, TextLinks parcelable2) {
        Preconditions.checkNotNull(spannable);
        Preconditions.checkNotNull(parcelable2);
        Object object = spannable.toString();
        if (Linkify.containsUnsupportedCharacters((String)object)) {
            EventLog.writeEvent(1397638484, "116321860", -1, "");
            return 4;
        }
        if (!((String)object).startsWith(((TextLinks)parcelable2).getText())) {
            return 3;
        }
        if (((TextLinks)parcelable2).getLinks().isEmpty()) {
            return 1;
        }
        int n = 0;
        for (TextLinks.TextLink textLink : ((TextLinks)parcelable2).getLinks()) {
            object = this.mSpanFactory.apply(textLink);
            int n2 = n;
            if (object != null) {
                ClickableSpan[] arrclickableSpan = spannable.getSpans(textLink.getStart(), textLink.getEnd(), ClickableSpan.class);
                if (arrclickableSpan.length > 0) {
                    n2 = n;
                    if (this.mApplyStrategy == 1) {
                        int n3 = arrclickableSpan.length;
                        for (n2 = 0; n2 < n3; ++n2) {
                            spannable.removeSpan(arrclickableSpan[n2]);
                        }
                        spannable.setSpan(object, textLink.getStart(), textLink.getEnd(), 33);
                        n2 = n + 1;
                    }
                } else {
                    spannable.setSpan(object, textLink.getStart(), textLink.getEnd(), 33);
                    n2 = n + 1;
                }
            }
            n = n2;
        }
        if (n == 0) {
            return 2;
        }
        return 0;
    }

    public TextClassifier.EntityConfig getEntityConfig() {
        return this.mEntityConfig;
    }

    public static final class Builder {
        private int mApplyStrategy = 0;
        private Function<TextLinks.TextLink, TextLinks.TextLinkSpan> mSpanFactory = TextLinksParams.access$000();

        public TextLinksParams build() {
            return new TextLinksParams(this.mApplyStrategy, this.mSpanFactory);
        }

        public Builder setApplyStrategy(int n) {
            this.mApplyStrategy = TextLinksParams.checkApplyStrategy(n);
            return this;
        }

        public Builder setEntityConfig(TextClassifier.EntityConfig entityConfig) {
            return this;
        }

        public Builder setSpanFactory(Function<TextLinks.TextLink, TextLinks.TextLinkSpan> function) {
            if (function == null) {
                function = DEFAULT_SPAN_FACTORY;
            }
            this.mSpanFactory = function;
            return this;
        }
    }

}

