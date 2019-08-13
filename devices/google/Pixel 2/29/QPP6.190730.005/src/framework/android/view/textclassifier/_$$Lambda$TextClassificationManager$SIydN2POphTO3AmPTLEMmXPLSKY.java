/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassificationSessionFactory;
import android.view.textclassifier.TextClassifier;

public final class _$$Lambda$TextClassificationManager$SIydN2POphTO3AmPTLEMmXPLSKY
implements TextClassificationSessionFactory {
    private final /* synthetic */ TextClassificationManager f$0;

    public /* synthetic */ _$$Lambda$TextClassificationManager$SIydN2POphTO3AmPTLEMmXPLSKY(TextClassificationManager textClassificationManager) {
        this.f$0 = textClassificationManager;
    }

    @Override
    public final TextClassifier createTextClassificationSession(TextClassificationContext textClassificationContext) {
        return this.f$0.lambda$new$1$TextClassificationManager(textClassificationContext);
    }
}

