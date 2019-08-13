/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$ActionsSuggestionsHelper
 *  android.view.textclassifier.-$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I
 */
package android.view.textclassifier;

import android.content.pm.ResolveInfo;
import android.view.textclassifier.-$;
import android.view.textclassifier.ActionsSuggestionsHelper;
import android.view.textclassifier.intent.LabeledIntent;

public final class _$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I
implements LabeledIntent.TitleChooser {
    public static final /* synthetic */ -$.Lambda.ActionsSuggestionsHelper.sY0w9od2zcl4YFel0lG4VB3vf7I INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I();
    }

    private /* synthetic */ _$$Lambda$ActionsSuggestionsHelper$sY0w9od2zcl4YFel0lG4VB3vf7I() {
    }

    @Override
    public final CharSequence chooseTitle(LabeledIntent labeledIntent, ResolveInfo resolveInfo) {
        return ActionsSuggestionsHelper.lambda$createTitleChooser$1(labeledIntent, resolveInfo);
    }
}

