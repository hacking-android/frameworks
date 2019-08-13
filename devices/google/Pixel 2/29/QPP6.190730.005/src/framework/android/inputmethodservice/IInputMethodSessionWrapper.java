/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputMethodSession;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import com.android.internal.view.IInputMethodSession;

class IInputMethodSessionWrapper
extends IInputMethodSession.Stub
implements HandlerCaller.Callback {
    private static final int DO_APP_PRIVATE_COMMAND = 100;
    private static final int DO_DISPLAY_COMPLETIONS = 65;
    private static final int DO_FINISH_SESSION = 110;
    private static final int DO_NOTIFY_IME_HIDDEN = 120;
    private static final int DO_TOGGLE_SOFT_INPUT = 105;
    private static final int DO_UPDATE_CURSOR = 95;
    private static final int DO_UPDATE_CURSOR_ANCHOR_INFO = 99;
    private static final int DO_UPDATE_EXTRACTED_TEXT = 67;
    private static final int DO_UPDATE_SELECTION = 90;
    private static final int DO_VIEW_CLICKED = 115;
    private static final String TAG = "InputMethodWrapper";
    @UnsupportedAppUsage
    HandlerCaller mCaller;
    InputChannel mChannel;
    InputMethodSession mInputMethodSession;
    ImeInputEventReceiver mReceiver;

    public IInputMethodSessionWrapper(Context context, InputMethodSession inputMethodSession, InputChannel inputChannel) {
        this.mCaller = new HandlerCaller(context, null, this, true);
        this.mInputMethodSession = inputMethodSession;
        this.mChannel = inputChannel;
        if (inputChannel != null) {
            this.mReceiver = new ImeInputEventReceiver(inputChannel, context.getMainLooper());
        }
    }

    private void doFinishSession() {
        this.mInputMethodSession = null;
        Object object = this.mReceiver;
        if (object != null) {
            ((InputEventReceiver)object).dispose();
            this.mReceiver = null;
        }
        if ((object = this.mChannel) != null) {
            ((InputChannel)object).dispose();
            this.mChannel = null;
        }
    }

    @Override
    public void appPrivateCommand(String string2, Bundle bundle) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(100, string2, bundle));
    }

    @Override
    public void displayCompletions(CompletionInfo[] arrcompletionInfo) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(65, arrcompletionInfo));
    }

    @Override
    public void executeMessage(Message object) {
        if (this.mInputMethodSession == null) {
            int n = ((Message)object).what;
            if (n == 90 || n == 100) {
                ((SomeArgs)((Message)object).obj).recycle();
            }
            return;
        }
        int n = ((Message)object).what;
        if (n != 65) {
            if (n != 67) {
                if (n != 90) {
                    if (n != 95) {
                        if (n != 105) {
                            if (n != 110) {
                                if (n != 115) {
                                    if (n != 120) {
                                        if (n != 99) {
                                            if (n != 100) {
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("Unhandled message code: ");
                                                stringBuilder.append(((Message)object).what);
                                                Log.w(TAG, stringBuilder.toString());
                                                return;
                                            }
                                            object = (SomeArgs)((Message)object).obj;
                                            this.mInputMethodSession.appPrivateCommand((String)((SomeArgs)object).arg1, (Bundle)((SomeArgs)object).arg2);
                                            ((SomeArgs)object).recycle();
                                            return;
                                        }
                                        this.mInputMethodSession.updateCursorAnchorInfo((CursorAnchorInfo)((Message)object).obj);
                                        return;
                                    }
                                    this.mInputMethodSession.notifyImeHidden();
                                    return;
                                }
                                InputMethodSession inputMethodSession = this.mInputMethodSession;
                                n = ((Message)object).arg1;
                                boolean bl = true;
                                if (n != 1) {
                                    bl = false;
                                }
                                inputMethodSession.viewClicked(bl);
                                return;
                            }
                            this.doFinishSession();
                            return;
                        }
                        this.mInputMethodSession.toggleSoftInput(((Message)object).arg1, ((Message)object).arg2);
                        return;
                    }
                    this.mInputMethodSession.updateCursor((Rect)((Message)object).obj);
                    return;
                }
                object = (SomeArgs)((Message)object).obj;
                this.mInputMethodSession.updateSelection(((SomeArgs)object).argi1, ((SomeArgs)object).argi2, ((SomeArgs)object).argi3, ((SomeArgs)object).argi4, ((SomeArgs)object).argi5, ((SomeArgs)object).argi6);
                ((SomeArgs)object).recycle();
                return;
            }
            this.mInputMethodSession.updateExtractedText(((Message)object).arg1, (ExtractedText)((Message)object).obj);
            return;
        }
        this.mInputMethodSession.displayCompletions((CompletionInfo[])((Message)object).obj);
    }

    @Override
    public void finishSession() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(110));
    }

    public InputMethodSession getInternalInputMethodSession() {
        return this.mInputMethodSession;
    }

    @Override
    public void notifyImeHidden() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(120));
    }

    @Override
    public void toggleSoftInput(int n, int n2) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageII(105, n, n2));
    }

    @Override
    public void updateCursor(Rect rect) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(95, rect));
    }

    @Override
    public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(99, cursorAnchorInfo));
    }

    @Override
    public void updateExtractedText(int n, ExtractedText extractedText) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIO(67, n, extractedText));
    }

    @Override
    public void updateSelection(int n, int n2, int n3, int n4, int n5, int n6) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIIIII(90, n, n2, n3, n4, n5, n6));
    }

    @Override
    public void viewClicked(boolean bl) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(115, (int)bl));
    }

    private final class ImeInputEventReceiver
    extends InputEventReceiver
    implements InputMethodSession.EventCallback {
        private final SparseArray<InputEvent> mPendingEvents;

        public ImeInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
            this.mPendingEvents = new SparseArray();
        }

        @Override
        public void finishedEvent(int n, boolean bl) {
            if ((n = this.mPendingEvents.indexOfKey(n)) >= 0) {
                InputEvent inputEvent = this.mPendingEvents.valueAt(n);
                this.mPendingEvents.removeAt(n);
                this.finishInputEvent(inputEvent, bl);
            }
        }

        @Override
        public void onInputEvent(InputEvent inputEvent) {
            if (IInputMethodSessionWrapper.this.mInputMethodSession == null) {
                this.finishInputEvent(inputEvent, false);
                return;
            }
            int n = inputEvent.getSequenceNumber();
            this.mPendingEvents.put(n, inputEvent);
            if (inputEvent instanceof KeyEvent) {
                inputEvent = (KeyEvent)inputEvent;
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchKeyEvent(n, (KeyEvent)inputEvent, this);
            } else if ((inputEvent = (MotionEvent)inputEvent).isFromSource(4)) {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchTrackballEvent(n, (MotionEvent)inputEvent, this);
            } else {
                IInputMethodSessionWrapper.this.mInputMethodSession.dispatchGenericMotionEvent(n, (MotionEvent)inputEvent, this);
            }
        }
    }

}

