/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.InputEvent;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;

public class InputEventCompatProcessor {
    protected Context mContext;
    private List<InputEvent> mProcessedEvents;
    protected int mTargetSdkVersion;

    public InputEventCompatProcessor(Context context) {
        this.mContext = context;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.mProcessedEvents = new ArrayList<InputEvent>();
    }

    public InputEvent processInputEventBeforeFinish(InputEvent inputEvent) {
        return inputEvent;
    }

    public List<InputEvent> processInputEventForCompatibility(InputEvent inputEvent) {
        if (this.mTargetSdkVersion < 23 && inputEvent instanceof MotionEvent) {
            this.mProcessedEvents.clear();
            inputEvent = (MotionEvent)inputEvent;
            int n = ((MotionEvent)inputEvent).getButtonState();
            int n2 = (n & 96) >> 4;
            if (n2 != 0) {
                ((MotionEvent)inputEvent).setButtonState(n | n2);
            }
            this.mProcessedEvents.add(inputEvent);
            return this.mProcessedEvents;
        }
        return null;
    }
}

