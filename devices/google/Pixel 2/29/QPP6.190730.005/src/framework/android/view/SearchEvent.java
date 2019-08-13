/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.InputDevice;

public class SearchEvent {
    private InputDevice mInputDevice;

    public SearchEvent(InputDevice inputDevice) {
        this.mInputDevice = inputDevice;
    }

    public InputDevice getInputDevice() {
        return this.mInputDevice;
    }
}

