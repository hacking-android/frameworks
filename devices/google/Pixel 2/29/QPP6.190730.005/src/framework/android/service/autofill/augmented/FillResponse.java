/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.service.autofill.augmented.FillWindow;

@SystemApi
public final class FillResponse {
    private final FillWindow mFillWindow;

    private FillResponse(Builder builder) {
        this.mFillWindow = builder.mFillWindow;
    }

    FillWindow getFillWindow() {
        return this.mFillWindow;
    }

    @SystemApi
    public static final class Builder {
        private FillWindow mFillWindow;

        public FillResponse build() {
            return new FillResponse(this);
        }

        public Builder setFillWindow(FillWindow fillWindow) {
            this.mFillWindow = fillWindow;
            return this;
        }
    }

}

