/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.WindowInsets;
import android.view.WindowInsetsAnimationControlListener;

public interface WindowInsetsController {
    default public void controlInputMethodAnimation(WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        this.controlWindowInsetsAnimation(WindowInsets.Type.ime(), windowInsetsAnimationControlListener);
    }

    public void controlWindowInsetsAnimation(int var1, WindowInsetsAnimationControlListener var2);

    public void hide(int var1);

    default public void hideInputMethod() {
        this.hide(WindowInsets.Type.ime());
    }

    public void show(int var1);

    default public void showInputMethod() {
        this.show(WindowInsets.Type.ime());
    }
}

