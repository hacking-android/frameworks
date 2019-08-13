/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Build;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;

public final class InputEventConsistencyVerifier {
    private static final String EVENT_TYPE_GENERIC_MOTION = "GenericMotionEvent";
    private static final String EVENT_TYPE_KEY = "KeyEvent";
    private static final String EVENT_TYPE_TOUCH = "TouchEvent";
    private static final String EVENT_TYPE_TRACKBALL = "TrackballEvent";
    public static final int FLAG_RAW_DEVICE_INPUT = 1;
    private static final boolean IS_ENG_BUILD = Build.IS_ENG;
    private static final int RECENT_EVENTS_TO_LOG = 5;
    private int mButtonsPressed;
    private final Object mCaller;
    private InputEvent mCurrentEvent;
    private String mCurrentEventType;
    private final int mFlags;
    private boolean mHoverEntered;
    private KeyState mKeyStateList;
    private int mLastEventSeq;
    private String mLastEventType;
    private int mLastNestingLevel;
    private final String mLogTag;
    private int mMostRecentEventIndex;
    private InputEvent[] mRecentEvents;
    private boolean[] mRecentEventsUnhandled;
    private int mTouchEventStreamDeviceId = -1;
    private boolean mTouchEventStreamIsTainted;
    private int mTouchEventStreamPointers;
    private int mTouchEventStreamSource;
    private boolean mTouchEventStreamUnhandled;
    private boolean mTrackballDown;
    private boolean mTrackballUnhandled;
    private StringBuilder mViolationMessage;

    @UnsupportedAppUsage
    public InputEventConsistencyVerifier(Object object, int n) {
        this(object, n, null);
    }

    public InputEventConsistencyVerifier(Object object, int n, String string2) {
        this.mCaller = object;
        this.mFlags = n;
        object = string2 != null ? string2 : "InputEventConsistencyVerifier";
        this.mLogTag = object;
    }

    private void addKeyState(int n, int n2, int n3) {
        KeyState keyState = KeyState.obtain(n, n2, n3);
        keyState.next = this.mKeyStateList;
        this.mKeyStateList = keyState;
    }

    private static void appendEvent(StringBuilder stringBuilder, int n, InputEvent inputEvent, boolean bl) {
        stringBuilder.append(n);
        stringBuilder.append(": sent at ");
        stringBuilder.append(inputEvent.getEventTimeNano());
        stringBuilder.append(", ");
        if (bl) {
            stringBuilder.append("(unhandled) ");
        }
        stringBuilder.append(inputEvent);
    }

    private void ensureActionButtonIsNonZeroForThisAction(MotionEvent motionEvent) {
        if (motionEvent.getActionButton() == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No action button set. Action button should always be non-zero for ");
            stringBuilder.append(MotionEvent.actionToString(motionEvent.getAction()));
            this.problem(stringBuilder.toString());
        }
    }

    private void ensureHistorySizeIsZeroForThisAction(MotionEvent motionEvent) {
        int n = motionEvent.getHistorySize();
        if (n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("History size is ");
            stringBuilder.append(n);
            stringBuilder.append(" but it should always be 0 for ");
            stringBuilder.append(MotionEvent.actionToString(motionEvent.getAction()));
            this.problem(stringBuilder.toString());
        }
    }

    private void ensureMetaStateIsNormalized(int n) {
        int n2 = KeyEvent.normalizeMetaState(n);
        if (n2 != n) {
            this.problem(String.format("Metastate not normalized.  Was 0x%08x but expected 0x%08x.", n, n2));
        }
    }

    private void ensurePointerCountIsOneForThisAction(MotionEvent motionEvent) {
        int n = motionEvent.getPointerCount();
        if (n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Pointer count is ");
            stringBuilder.append(n);
            stringBuilder.append(" but it should always be 1 for ");
            stringBuilder.append(MotionEvent.actionToString(motionEvent.getAction()));
            this.problem(stringBuilder.toString());
        }
    }

    private KeyState findKeyState(int n, int n2, int n3, boolean bl) {
        KeyState keyState = null;
        KeyState keyState2 = this.mKeyStateList;
        while (keyState2 != null) {
            if (keyState2.deviceId == n && keyState2.source == n2 && keyState2.keyCode == n3) {
                if (bl) {
                    if (keyState != null) {
                        keyState.next = keyState2.next;
                    } else {
                        this.mKeyStateList = keyState2.next;
                    }
                    keyState2.next = null;
                }
                return keyState2;
            }
            keyState = keyState2;
            keyState2 = keyState2.next;
        }
        return null;
    }

    private void finishEvent() {
        int n;
        Object object = this.mViolationMessage;
        if (object != null && object.length() != 0) {
            if (!this.mCurrentEvent.isTainted()) {
                object = this.mViolationMessage;
                object.append("\n  in ");
                object.append(this.mCaller);
                this.mViolationMessage.append("\n  ");
                InputEventConsistencyVerifier.appendEvent(this.mViolationMessage, 0, this.mCurrentEvent, false);
                if (this.mRecentEvents != null) {
                    int n2;
                    this.mViolationMessage.append("\n  -- recent events --");
                    for (n = 0; n < 5 && (object = this.mRecentEvents[n2 = (this.mMostRecentEventIndex + 5 - n) % 5]) != null; ++n) {
                        this.mViolationMessage.append("\n  ");
                        InputEventConsistencyVerifier.appendEvent(this.mViolationMessage, n + 1, (InputEvent)object, this.mRecentEventsUnhandled[n2]);
                    }
                }
                Log.d(this.mLogTag, this.mViolationMessage.toString());
                this.mCurrentEvent.setTainted(true);
            }
            this.mViolationMessage.setLength(0);
        }
        if (this.mRecentEvents == null) {
            this.mRecentEvents = new InputEvent[5];
            this.mRecentEventsUnhandled = new boolean[5];
        }
        this.mMostRecentEventIndex = n = (this.mMostRecentEventIndex + 1) % 5;
        object = this.mRecentEvents;
        if (object[n] != null) {
            object[n].recycle();
        }
        this.mRecentEvents[n] = this.mCurrentEvent.copy();
        this.mRecentEventsUnhandled[n] = false;
        this.mCurrentEvent = null;
        this.mCurrentEventType = null;
    }

    @UnsupportedAppUsage
    public static boolean isInstrumentationEnabled() {
        return IS_ENG_BUILD;
    }

    private void problem(String string2) {
        if (this.mViolationMessage == null) {
            this.mViolationMessage = new StringBuilder();
        }
        if (this.mViolationMessage.length() == 0) {
            StringBuilder stringBuilder = this.mViolationMessage;
            stringBuilder.append(this.mCurrentEventType);
            stringBuilder.append(": ");
        } else {
            this.mViolationMessage.append("\n  ");
        }
        this.mViolationMessage.append(string2);
    }

    private boolean startEvent(InputEvent inputEvent, int n, String string2) {
        int n2 = inputEvent.getSequenceNumber();
        if (n2 == this.mLastEventSeq && n < this.mLastNestingLevel && string2 == this.mLastEventType) {
            return false;
        }
        if (n > 0) {
            this.mLastEventSeq = n2;
            this.mLastEventType = string2;
            this.mLastNestingLevel = n;
        } else {
            this.mLastEventSeq = -1;
            this.mLastEventType = null;
            this.mLastNestingLevel = 0;
        }
        this.mCurrentEvent = inputEvent;
        this.mCurrentEventType = string2;
        return true;
    }

    /*
     * Exception decompiling
     */
    public void onGenericMotionEvent(MotionEvent var1_1, int var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void onInputEvent(InputEvent inputEvent, int n) {
        if (inputEvent instanceof KeyEvent) {
            this.onKeyEvent((KeyEvent)inputEvent, n);
        } else if (((MotionEvent)(inputEvent = (MotionEvent)inputEvent)).isTouchEvent()) {
            this.onTouchEvent((MotionEvent)inputEvent, n);
        } else if ((((MotionEvent)inputEvent).getSource() & 4) != 0) {
            this.onTrackballEvent((MotionEvent)inputEvent, n);
        } else {
            this.onGenericMotionEvent((MotionEvent)inputEvent, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onKeyEvent(KeyEvent object, int n) {
        if (!this.startEvent((InputEvent)object, n, EVENT_TYPE_KEY)) {
            return;
        }
        try {
            this.ensureMetaStateIsNormalized(((KeyEvent)object).getMetaState());
            int n2 = ((KeyEvent)object).getAction();
            int n3 = ((KeyEvent)object).getDeviceId();
            n = ((KeyEvent)object).getSource();
            int n4 = ((KeyEvent)object).getKeyCode();
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) return;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid action ");
                    ((StringBuilder)object).append(KeyEvent.actionToString(n2));
                    ((StringBuilder)object).append(" for key event.");
                    this.problem(((StringBuilder)object).toString());
                    return;
                }
                object = this.findKeyState(n3, n, n4, true);
                if (object == null) {
                    this.problem("ACTION_UP but key was not down.");
                    return;
                }
                ((KeyState)object).recycle();
                return;
            }
            KeyState keyState = this.findKeyState(n3, n, n4, false);
            if (keyState != null) {
                if (keyState.unhandled) {
                    keyState.unhandled = false;
                    return;
                }
                if ((1 & this.mFlags) != 0) return;
                if (((KeyEvent)object).getRepeatCount() != 0) return;
                this.problem("ACTION_DOWN but key is already down and this event is not a key repeat.");
                return;
            }
            this.addKeyState(n3, n, n4);
            return;
        }
        finally {
            this.finishEvent();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void onTouchEvent(MotionEvent object, int n) {
        if (!this.startEvent((InputEvent)object, n, EVENT_TYPE_TOUCH)) {
            return;
        }
        int n2 = ((MotionEvent)object).getAction();
        n = n2 != 0 && n2 != 3 && n2 != 4 ? 0 : 1;
        if (n != 0 && (this.mTouchEventStreamIsTainted || this.mTouchEventStreamUnhandled)) {
            this.mTouchEventStreamIsTainted = false;
            this.mTouchEventStreamUnhandled = false;
            this.mTouchEventStreamPointers = 0;
        }
        if (this.mTouchEventStreamIsTainted) {
            ((MotionEvent)object).setTainted(true);
        }
        try {
            StringBuilder stringBuilder;
            this.ensureMetaStateIsNormalized(((MotionEvent)object).getMetaState());
            int n3 = ((MotionEvent)object).getDeviceId();
            int n4 = ((MotionEvent)object).getSource();
            if (n == 0 && this.mTouchEventStreamDeviceId != -1 && (this.mTouchEventStreamDeviceId != n3 || this.mTouchEventStreamSource != n4)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Touch event stream contains events from multiple sources: previous device id ");
                stringBuilder.append(this.mTouchEventStreamDeviceId);
                stringBuilder.append(", previous source ");
                stringBuilder.append(Integer.toHexString(this.mTouchEventStreamSource));
                stringBuilder.append(", new device id ");
                stringBuilder.append(n3);
                stringBuilder.append(", new source ");
                stringBuilder.append(Integer.toHexString(n4));
                this.problem(stringBuilder.toString());
            }
            this.mTouchEventStreamDeviceId = n3;
            this.mTouchEventStreamSource = n4;
            n = ((MotionEvent)object).getPointerCount();
            if ((n4 & 2) != 0) {
                if (n2 != 0) {
                    if (n2 == 1) {
                        this.ensureHistorySizeIsZeroForThisAction((MotionEvent)object);
                        this.ensurePointerCountIsOneForThisAction((MotionEvent)object);
                        this.mTouchEventStreamPointers = 0;
                        this.mTouchEventStreamIsTainted = false;
                        return;
                    }
                    if (n2 == 2) {
                        n2 = Integer.bitCount(this.mTouchEventStreamPointers);
                        if (n == n2) return;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("ACTION_MOVE contained ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" pointers but there are currently ");
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append(" pointers down.");
                        this.problem(((StringBuilder)object).toString());
                        this.mTouchEventStreamIsTainted = true;
                        return;
                    }
                    if (n2 == 3) {
                        this.mTouchEventStreamPointers = 0;
                        this.mTouchEventStreamIsTainted = false;
                        return;
                    }
                    if (n2 != 4) {
                        n3 = ((MotionEvent)object).getActionMasked();
                        n4 = ((MotionEvent)object).getActionIndex();
                        if (n3 == 5) {
                            if (this.mTouchEventStreamPointers == 0) {
                                this.problem("ACTION_POINTER_DOWN but no other pointers were down.");
                                this.mTouchEventStreamIsTainted = true;
                            }
                            if (n4 >= 0 && n4 < n) {
                                n2 = ((MotionEvent)object).getPointerId(n4);
                                n = 1 << n2;
                                if ((this.mTouchEventStreamPointers & n) != 0) {
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("ACTION_POINTER_DOWN specified pointer id ");
                                    stringBuilder.append(n2);
                                    stringBuilder.append(" which is already down.");
                                    this.problem(stringBuilder.toString());
                                    this.mTouchEventStreamIsTainted = true;
                                } else {
                                    this.mTouchEventStreamPointers |= n;
                                }
                            } else {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("ACTION_POINTER_DOWN index is ");
                                stringBuilder.append(n4);
                                stringBuilder.append(" but the pointer count is ");
                                stringBuilder.append(n);
                                stringBuilder.append(".");
                                this.problem(stringBuilder.toString());
                                this.mTouchEventStreamIsTainted = true;
                            }
                            this.ensureHistorySizeIsZeroForThisAction((MotionEvent)object);
                            return;
                        }
                        if (n3 != 6) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Invalid action ");
                            ((StringBuilder)object).append(MotionEvent.actionToString(n2));
                            ((StringBuilder)object).append(" for touch event.");
                            this.problem(((StringBuilder)object).toString());
                            return;
                        }
                        if (n4 >= 0 && n4 < n) {
                            n2 = ((MotionEvent)object).getPointerId(n4);
                            n = 1 << n2;
                            if ((this.mTouchEventStreamPointers & n) == 0) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("ACTION_POINTER_UP specified pointer id ");
                                stringBuilder.append(n2);
                                stringBuilder.append(" which is not currently down.");
                                this.problem(stringBuilder.toString());
                                this.mTouchEventStreamIsTainted = true;
                            } else {
                                this.mTouchEventStreamPointers &= n;
                            }
                        } else {
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("ACTION_POINTER_UP index is ");
                            stringBuilder.append(n4);
                            stringBuilder.append(" but the pointer count is ");
                            stringBuilder.append(n);
                            stringBuilder.append(".");
                            this.problem(stringBuilder.toString());
                            this.mTouchEventStreamIsTainted = true;
                        }
                        this.ensureHistorySizeIsZeroForThisAction((MotionEvent)object);
                        return;
                    }
                    if (this.mTouchEventStreamPointers != 0) {
                        this.problem("ACTION_OUTSIDE but pointers are still down.");
                    }
                    this.ensureHistorySizeIsZeroForThisAction((MotionEvent)object);
                    this.ensurePointerCountIsOneForThisAction((MotionEvent)object);
                    this.mTouchEventStreamIsTainted = false;
                    return;
                }
                if (this.mTouchEventStreamPointers != 0) {
                    this.problem("ACTION_DOWN but pointers are already down.  Probably missing ACTION_UP from previous gesture.");
                }
                this.ensureHistorySizeIsZeroForThisAction((MotionEvent)object);
                this.ensurePointerCountIsOneForThisAction((MotionEvent)object);
                this.mTouchEventStreamPointers = 1 << ((MotionEvent)object).getPointerId(0);
                return;
            }
            this.problem("Source was not SOURCE_CLASS_POINTER.");
            return;
        }
        finally {
            this.finishEvent();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onTrackballEvent(MotionEvent motionEvent, int n) {
        if (!this.startEvent(motionEvent, n, EVENT_TYPE_TRACKBALL)) {
            return;
        }
        try {
            this.ensureMetaStateIsNormalized(motionEvent.getMetaState());
            n = motionEvent.getAction();
            if ((motionEvent.getSource() & 4) != 0) {
                if (n != 0) {
                    if (n != 1) {
                        if (n != 2) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid action ");
                            stringBuilder.append(MotionEvent.actionToString(n));
                            stringBuilder.append(" for trackball event.");
                            this.problem(stringBuilder.toString());
                        } else {
                            this.ensurePointerCountIsOneForThisAction(motionEvent);
                        }
                    } else {
                        if (!this.mTrackballDown) {
                            this.problem("ACTION_UP but trackball is not down.");
                        } else {
                            this.mTrackballDown = false;
                            this.mTrackballUnhandled = false;
                        }
                        this.ensureHistorySizeIsZeroForThisAction(motionEvent);
                        this.ensurePointerCountIsOneForThisAction(motionEvent);
                    }
                } else {
                    if (this.mTrackballDown && !this.mTrackballUnhandled) {
                        this.problem("ACTION_DOWN but trackball is already down.");
                    } else {
                        this.mTrackballDown = true;
                        this.mTrackballUnhandled = false;
                    }
                    this.ensureHistorySizeIsZeroForThisAction(motionEvent);
                    this.ensurePointerCountIsOneForThisAction(motionEvent);
                }
                if (this.mTrackballDown && motionEvent.getPressure() <= 0.0f) {
                    this.problem("Trackball is down but pressure is not greater than 0.");
                    return;
                }
                if (this.mTrackballDown) return;
                if (motionEvent.getPressure() == 0.0f) return;
                this.problem("Trackball is up but pressure is not equal to 0.");
                return;
            }
            this.problem("Source was not SOURCE_CLASS_TRACKBALL.");
            return;
        }
        finally {
            this.finishEvent();
        }
    }

    @UnsupportedAppUsage
    public void onUnhandledEvent(InputEvent object, int n) {
        if (n != this.mLastNestingLevel) {
            return;
        }
        boolean[] arrbl = this.mRecentEventsUnhandled;
        if (arrbl != null) {
            arrbl[this.mMostRecentEventIndex] = true;
        }
        if (object instanceof KeyEvent) {
            object = (KeyEvent)object;
            if ((object = this.findKeyState(((KeyEvent)object).getDeviceId(), ((KeyEvent)object).getSource(), ((KeyEvent)object).getKeyCode(), false)) != null) {
                ((KeyState)object).unhandled = true;
            }
        } else if (((MotionEvent)(object = (MotionEvent)object)).isTouchEvent()) {
            this.mTouchEventStreamUnhandled = true;
        } else if ((((MotionEvent)object).getSource() & 4) != 0 && this.mTrackballDown) {
            this.mTrackballUnhandled = true;
        }
    }

    public void reset() {
        this.mLastEventSeq = -1;
        this.mLastNestingLevel = 0;
        this.mTrackballDown = false;
        this.mTrackballUnhandled = false;
        this.mTouchEventStreamPointers = 0;
        this.mTouchEventStreamIsTainted = false;
        this.mTouchEventStreamUnhandled = false;
        this.mHoverEntered = false;
        this.mButtonsPressed = 0;
        while (this.mKeyStateList != null) {
            KeyState keyState = this.mKeyStateList;
            this.mKeyStateList = keyState.next;
            keyState.recycle();
        }
    }

    private static final class KeyState {
        private static KeyState mRecycledList;
        private static Object mRecycledListLock;
        public int deviceId;
        public int keyCode;
        public KeyState next;
        public int source;
        public boolean unhandled;

        static {
            mRecycledListLock = new Object();
        }

        private KeyState() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static KeyState obtain(int n, int n2, int n3) {
            KeyState keyState;
            Object object = mRecycledListLock;
            synchronized (object) {
                keyState = mRecycledList;
                if (keyState != null) {
                    mRecycledList = keyState.next;
                } else {
                    keyState = new KeyState();
                }
            }
            keyState.deviceId = n;
            keyState.source = n2;
            keyState.keyCode = n3;
            keyState.unhandled = false;
            return keyState;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void recycle() {
            Object object = mRecycledListLock;
            synchronized (object) {
                mRecycledList = this.next = mRecycledList;
                return;
            }
        }
    }

}

