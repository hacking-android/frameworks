/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.IInputFilter;
import android.view.IInputFilterHost;
import android.view.InputEvent;
import android.view.InputEventConsistencyVerifier;

public abstract class InputFilter
extends IInputFilter.Stub {
    private static final int MSG_INPUT_EVENT = 3;
    private static final int MSG_INSTALL = 1;
    private static final int MSG_UNINSTALL = 2;
    private final H mH;
    private IInputFilterHost mHost;
    private final InputEventConsistencyVerifier mInboundInputEventConsistencyVerifier;
    private final InputEventConsistencyVerifier mOutboundInputEventConsistencyVerifier;

    @UnsupportedAppUsage
    public InputFilter(Looper looper) {
        boolean bl = InputEventConsistencyVerifier.isInstrumentationEnabled();
        Object var3_3 = null;
        InputEventConsistencyVerifier inputEventConsistencyVerifier = bl ? new InputEventConsistencyVerifier(this, 1, "InputFilter#InboundInputEventConsistencyVerifier") : null;
        this.mInboundInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        inputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 1, "InputFilter#OutboundInputEventConsistencyVerifier") : var3_3;
        this.mOutboundInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mH = new H(looper);
    }

    @Override
    public final void filterInputEvent(InputEvent inputEvent, int n) {
        this.mH.obtainMessage(3, n, 0, inputEvent).sendToTarget();
    }

    @Override
    public final void install(IInputFilterHost iInputFilterHost) {
        this.mH.obtainMessage(1, iInputFilterHost).sendToTarget();
    }

    @UnsupportedAppUsage
    public void onInputEvent(InputEvent inputEvent, int n) {
        this.sendInputEvent(inputEvent, n);
    }

    public void onInstalled() {
    }

    public void onUninstalled() {
    }

    public void sendInputEvent(InputEvent inputEvent, int n) {
        if (inputEvent != null) {
            if (this.mHost != null) {
                InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mOutboundInputEventConsistencyVerifier;
                if (inputEventConsistencyVerifier != null) {
                    inputEventConsistencyVerifier.onInputEvent(inputEvent, 0);
                }
                try {
                    this.mHost.sendInputEvent(inputEvent, n);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                return;
            }
            throw new IllegalStateException("Cannot send input event because the input filter is not installed.");
        }
        throw new IllegalArgumentException("event must not be null");
    }

    @Override
    public final void uninstall() {
        this.mH.obtainMessage(2).sendToTarget();
    }

    private final class H
    extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        InputEvent inputEvent = (InputEvent)message.obj;
                        try {
                            if (InputFilter.this.mInboundInputEventConsistencyVerifier != null) {
                                InputFilter.this.mInboundInputEventConsistencyVerifier.onInputEvent(inputEvent, 0);
                            }
                            InputFilter.this.onInputEvent(inputEvent, message.arg1);
                        }
                        finally {
                            inputEvent.recycle();
                        }
                    }
                } else {
                    try {
                        InputFilter.this.onUninstalled();
                    }
                    finally {
                        InputFilter.this.mHost = null;
                    }
                }
            } else {
                InputFilter.this.mHost = (IInputFilterHost)message.obj;
                if (InputFilter.this.mInboundInputEventConsistencyVerifier != null) {
                    InputFilter.this.mInboundInputEventConsistencyVerifier.reset();
                }
                if (InputFilter.this.mOutboundInputEventConsistencyVerifier != null) {
                    InputFilter.this.mOutboundInputEventConsistencyVerifier.reset();
                }
                InputFilter.this.onInstalled();
            }
        }
    }

}

