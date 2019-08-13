/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Insets;

public interface WindowInsetsAnimationController {
    public void changeInsets(Insets var1);

    public void finish(int var1);

    public Insets getCurrentInsets();

    public Insets getHiddenStateInsets();

    public Insets getShownStateInsets();

    public int getTypes();
}

