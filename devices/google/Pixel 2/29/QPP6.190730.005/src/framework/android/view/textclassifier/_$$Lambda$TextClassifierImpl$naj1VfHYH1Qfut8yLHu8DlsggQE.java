/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$TextClassifierImpl
 *  android.view.textclassifier.-$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE
 */
package android.view.textclassifier;

import android.content.pm.ResolveInfo;
import android.view.textclassifier.-$;
import android.view.textclassifier.TextClassifierImpl;
import android.view.textclassifier.intent.LabeledIntent;

public final class _$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE
implements LabeledIntent.TitleChooser {
    public static final /* synthetic */ -$.Lambda.TextClassifierImpl.naj1VfHYH1Qfut8yLHu8DlsggQE INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE();
    }

    private /* synthetic */ _$$Lambda$TextClassifierImpl$naj1VfHYH1Qfut8yLHu8DlsggQE() {
    }

    @Override
    public final CharSequence chooseTitle(LabeledIntent labeledIntent, ResolveInfo resolveInfo) {
        return TextClassifierImpl.lambda$createClassificationResult$2(labeledIntent, resolveInfo);
    }
}

