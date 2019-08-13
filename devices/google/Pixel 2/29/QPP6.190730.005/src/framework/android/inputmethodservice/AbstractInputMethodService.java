/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.IInputMethodWrapper;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class AbstractInputMethodService
extends Service
implements KeyEvent.Callback {
    final KeyEvent.DispatcherState mDispatcherState = new KeyEvent.DispatcherState();
    private InputMethod mInputMethod;

    @Override
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    public void exposeContent(InputContentInfo inputContentInfo, InputConnection inputConnection) {
    }

    public KeyEvent.DispatcherState getKeyDispatcherState() {
        return this.mDispatcherState;
    }

    public void notifyUserActionIfNecessary() {
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (this.mInputMethod == null) {
            this.mInputMethod = this.onCreateInputMethodInterface();
        }
        return new IInputMethodWrapper(this, this.mInputMethod);
    }

    public abstract AbstractInputMethodImpl onCreateInputMethodInterface();

    public abstract AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface();

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public abstract class AbstractInputMethodImpl
    implements InputMethod {
        @Override
        public void createSession(InputMethod.SessionCallback sessionCallback) {
            sessionCallback.sessionCreated(AbstractInputMethodService.this.onCreateInputMethodSessionInterface());
        }

        @Override
        public void revokeSession(InputMethodSession inputMethodSession) {
            ((AbstractInputMethodSessionImpl)inputMethodSession).revokeSelf();
        }

        @Override
        public void setSessionEnabled(InputMethodSession inputMethodSession, boolean bl) {
            ((AbstractInputMethodSessionImpl)inputMethodSession).setEnabled(bl);
        }
    }

    public abstract class AbstractInputMethodSessionImpl
    implements InputMethodSession {
        boolean mEnabled = true;
        boolean mRevoked;

        @Override
        public void dispatchGenericMotionEvent(int n, MotionEvent motionEvent, InputMethodSession.EventCallback eventCallback) {
            boolean bl = AbstractInputMethodService.this.onGenericMotionEvent(motionEvent);
            if (eventCallback != null) {
                eventCallback.finishedEvent(n, bl);
            }
        }

        @Override
        public void dispatchKeyEvent(int n, KeyEvent keyEvent, InputMethodSession.EventCallback eventCallback) {
            AbstractInputMethodService abstractInputMethodService = AbstractInputMethodService.this;
            boolean bl = keyEvent.dispatch(abstractInputMethodService, abstractInputMethodService.mDispatcherState, this);
            if (eventCallback != null) {
                eventCallback.finishedEvent(n, bl);
            }
        }

        @Override
        public void dispatchTrackballEvent(int n, MotionEvent motionEvent, InputMethodSession.EventCallback eventCallback) {
            boolean bl = AbstractInputMethodService.this.onTrackballEvent(motionEvent);
            if (eventCallback != null) {
                eventCallback.finishedEvent(n, bl);
            }
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        public boolean isRevoked() {
            return this.mRevoked;
        }

        public void revokeSelf() {
            this.mRevoked = true;
            this.mEnabled = false;
        }

        public void setEnabled(boolean bl) {
            if (!this.mRevoked) {
                this.mEnabled = bl;
            }
        }
    }

}

