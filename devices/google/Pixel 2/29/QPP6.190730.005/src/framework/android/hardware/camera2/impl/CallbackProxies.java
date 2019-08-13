/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.impl;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$9H0ZdANdMrdpoq2bfIL2l3DVsKk;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$ISQyEhLUI1khcOCin3OIsRyTUoU;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$gvbTsp9UPpKJAbdycdci_ZW5BeI;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$hoQOYc189Bss2NBtrutabMRw4VU;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$soW0qC12Osypoky6AfL3P2_TeDw;
import android.hardware.camera2.impl._$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw;
import android.os.Binder;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;

public class CallbackProxies {
    private CallbackProxies() {
        throw new AssertionError();
    }

    public static class SessionStateCallbackProxy
    extends CameraCaptureSession.StateCallback {
        private final CameraCaptureSession.StateCallback mCallback;
        private final Executor mExecutor;

        public SessionStateCallbackProxy(Executor executor, CameraCaptureSession.StateCallback stateCallback) {
            this.mExecutor = Preconditions.checkNotNull(executor, "executor must not be null");
            this.mCallback = Preconditions.checkNotNull(stateCallback, "callback must not be null");
        }

        public /* synthetic */ void lambda$onActive$3$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onActive(cameraCaptureSession);
        }

        public /* synthetic */ void lambda$onCaptureQueueEmpty$4$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onCaptureQueueEmpty(cameraCaptureSession);
        }

        public /* synthetic */ void lambda$onClosed$5$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onClosed(cameraCaptureSession);
        }

        public /* synthetic */ void lambda$onConfigureFailed$1$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onConfigureFailed(cameraCaptureSession);
        }

        public /* synthetic */ void lambda$onConfigured$0$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onConfigured(cameraCaptureSession);
        }

        public /* synthetic */ void lambda$onReady$2$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession) {
            this.mCallback.onReady(cameraCaptureSession);
        }

        public /* synthetic */ void lambda$onSurfacePrepared$6$CallbackProxies$SessionStateCallbackProxy(CameraCaptureSession cameraCaptureSession, Surface surface) {
            this.mCallback.onSurfacePrepared(cameraCaptureSession, surface);
        }

        @Override
        public void onActive(CameraCaptureSession cameraCaptureSession) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$ISQyEhLUI1khcOCin3OIsRyTUoU _$$Lambda$CallbackProxies$SessionStateCallbackProxy$ISQyEhLUI1khcOCin3OIsRyTUoU = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$ISQyEhLUI1khcOCin3OIsRyTUoU(this, cameraCaptureSession);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$ISQyEhLUI1khcOCin3OIsRyTUoU);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onCaptureQueueEmpty(CameraCaptureSession cameraCaptureSession) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$hoQOYc189Bss2NBtrutabMRw4VU _$$Lambda$CallbackProxies$SessionStateCallbackProxy$hoQOYc189Bss2NBtrutabMRw4VU = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$hoQOYc189Bss2NBtrutabMRw4VU(this, cameraCaptureSession);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$hoQOYc189Bss2NBtrutabMRw4VU);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onClosed(CameraCaptureSession cameraCaptureSession) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$9H0ZdANdMrdpoq2bfIL2l3DVsKk _$$Lambda$CallbackProxies$SessionStateCallbackProxy$9H0ZdANdMrdpoq2bfIL2l3DVsKk = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$9H0ZdANdMrdpoq2bfIL2l3DVsKk(this, cameraCaptureSession);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$9H0ZdANdMrdpoq2bfIL2l3DVsKk);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$gvbTsp9UPpKJAbdycdci_ZW5BeI _$$Lambda$CallbackProxies$SessionStateCallbackProxy$gvbTsp9UPpKJAbdycdci_ZW5BeI = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$gvbTsp9UPpKJAbdycdci_ZW5BeI(this, cameraCaptureSession);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$gvbTsp9UPpKJAbdycdci_ZW5BeI);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$soW0qC12Osypoky6AfL3P2_TeDw _$$Lambda$CallbackProxies$SessionStateCallbackProxy$soW0qC12Osypoky6AfL3P2_TeDw = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$soW0qC12Osypoky6AfL3P2_TeDw(this, cameraCaptureSession);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$soW0qC12Osypoky6AfL3P2_TeDw);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onReady(CameraCaptureSession cameraCaptureSession) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90 _$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90 = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90(this, cameraCaptureSession);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$Hoz_iT1tD_pl7sCGu4flyo_xB90);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void onSurfacePrepared(CameraCaptureSession cameraCaptureSession, Surface surface) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                _$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw _$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw = new _$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw(this, cameraCaptureSession, surface);
                executor.execute(_$$Lambda$CallbackProxies$SessionStateCallbackProxy$tuajQwbKz3BV5CZZJdjl97HF6Tw);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }
    }

}

