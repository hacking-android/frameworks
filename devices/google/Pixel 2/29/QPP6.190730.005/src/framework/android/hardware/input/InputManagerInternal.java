/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.input;

import android.hardware.display.DisplayViewport;
import android.view.InputEvent;
import java.util.List;

public abstract class InputManagerInternal {
    public abstract boolean injectInputEvent(InputEvent var1, int var2);

    public abstract void setDisplayViewports(List<DisplayViewport> var1);

    public abstract void setInteractive(boolean var1);

    public abstract void setPulseGestureEnabled(boolean var1);

    public abstract void toggleCapsLock(int var1);
}

