/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill.augmented;

import android.annotation.SystemApi;
import android.graphics.Rect;
import android.service.autofill.augmented.AugmentedAutofillService;
import java.io.PrintWriter;

@SystemApi
public abstract class PresentationParams {
    PresentationParams() {
    }

    abstract void dump(String var1, PrintWriter var2);

    public Area getSuggestionArea() {
        return null;
    }

    @SystemApi
    public static abstract class Area {
        private final Rect mBounds;
        public final AugmentedAutofillService.AutofillProxy proxy;

        private Area(AugmentedAutofillService.AutofillProxy autofillProxy, Rect rect) {
            this.proxy = autofillProxy;
            this.mBounds = rect;
        }

        public Rect getBounds() {
            return this.mBounds;
        }

        public String toString() {
            return this.mBounds.toString();
        }
    }

    public static final class SystemPopupPresentationParams
    extends PresentationParams {
        private final Area mSuggestionArea;

        public SystemPopupPresentationParams(AugmentedAutofillService.AutofillProxy autofillProxy, Rect rect) {
            this.mSuggestionArea = new Area(autofillProxy, rect){};
        }

        @Override
        void dump(String string2, PrintWriter printWriter) {
            printWriter.print(string2);
            printWriter.print("area: ");
            printWriter.println(this.mSuggestionArea);
        }

        @Override
        public Area getSuggestionArea() {
            return this.mSuggestionArea;
        }

    }

}

