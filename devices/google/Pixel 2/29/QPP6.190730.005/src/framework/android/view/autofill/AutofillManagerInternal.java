/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.content.AutofillOptions;

public abstract class AutofillManagerInternal {
    public abstract AutofillOptions getAutofillOptions(String var1, long var2, int var4);

    public abstract boolean isAugmentedAutofillServiceForUser(int var1, int var2);

    public abstract void onBackKeyPressed();
}

