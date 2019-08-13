/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IState;
import com.android.internal.util.State;
import com.android.internal.util._$$Lambda$StateMachine$SmHandler$KkPO7NIVuI9r_FPEGrY6ux6a5Ks;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StateMachine {
    public static final boolean HANDLED = true;
    public static final boolean NOT_HANDLED = false;
    private static final int SM_INIT_CMD = -2;
    private static final int SM_QUIT_CMD = -1;
    private String mName;
    private SmHandler mSmHandler;
    private HandlerThread mSmThread;

    @UnsupportedAppUsage
    protected StateMachine(String string2) {
        this.mSmThread = new HandlerThread(string2);
        this.mSmThread.start();
        this.initStateMachine(string2, this.mSmThread.getLooper());
    }

    @UnsupportedAppUsage
    protected StateMachine(String string2, Handler handler) {
        this.initStateMachine(string2, handler.getLooper());
    }

    @UnsupportedAppUsage
    protected StateMachine(String string2, Looper looper) {
        this.initStateMachine(string2, looper);
    }

    private void initStateMachine(String string2, Looper looper) {
        this.mName = string2;
        this.mSmHandler = new SmHandler(looper, this);
    }

    public void addLogRec(String string2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.mLogRecords.add(this, smHandler.getCurrentMessage(), string2, smHandler.getCurrentState(), SmHandler.access$2100((SmHandler)smHandler)[SmHandler.access$2200((SmHandler)smHandler)].state, smHandler.mDestState);
    }

    @UnsupportedAppUsage
    public final void addState(State state) {
        this.mSmHandler.addState(state, null);
    }

    public final void addState(State state, State state2) {
        this.mSmHandler.addState(state, state2);
    }

    public final Collection<LogRec> copyLogRecs() {
        Vector<LogRec> vector = new Vector<LogRec>();
        Object object = this.mSmHandler;
        if (object != null) {
            object = ((SmHandler)object).mLogRecords.mLogRecVector.iterator();
            while (object.hasNext()) {
                vector.add((LogRec)object.next());
            }
        }
        return vector;
    }

    public final void deferMessage(Message message) {
        this.mSmHandler.deferMessage(message);
    }

    @UnsupportedAppUsage
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getName());
        ((StringBuilder)object).append(":");
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" total records=");
        ((StringBuilder)object).append(this.getLogRecCount());
        printWriter.println(((StringBuilder)object).toString());
        for (int i = 0; i < this.getLogRecSize(); ++i) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" rec[");
            ((StringBuilder)object).append(i);
            ((StringBuilder)object).append("]: ");
            ((StringBuilder)object).append(this.getLogRec(i).toString());
            printWriter.println(((StringBuilder)object).toString());
            printWriter.flush();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("curState=");
        ((StringBuilder)object).append(this.getCurrentState().getName());
        printWriter.println(((StringBuilder)object).toString());
    }

    public final Message getCurrentMessage() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentMessage();
    }

    public final IState getCurrentState() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentState();
    }

    public final Handler getHandler() {
        return this.mSmHandler;
    }

    public final LogRec getLogRec(int n) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.mLogRecords.get(n);
    }

    public final int getLogRecCount() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.count();
    }

    @VisibleForTesting
    public final int getLogRecMaxSize() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.mMaxSize;
    }

    public final int getLogRecSize() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.size();
    }

    protected String getLogRecString(Message message) {
        return "";
    }

    public final String getName() {
        return this.mName;
    }

    protected String getWhatToString(int n) {
        return null;
    }

    protected void haltedProcessMessage(Message message) {
    }

    protected final boolean hasDeferredMessages(int n) {
        Object object = this.mSmHandler;
        if (object == null) {
            return false;
        }
        object = ((SmHandler)object).mDeferredMessages.iterator();
        while (object.hasNext()) {
            if (((Message)object.next()).what != n) continue;
            return true;
        }
        return false;
    }

    protected final boolean hasMessages(int n) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.hasMessages(n);
    }

    public boolean isDbg() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.isDbg();
    }

    protected final boolean isQuit(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            boolean bl = message.what == -1;
            return bl;
        }
        return smHandler.isQuit(message);
    }

    protected void log(String string2) {
        Log.d(this.mName, string2);
    }

    protected void logAndAddLogRec(String string2) {
        this.addLogRec(string2);
        this.log(string2);
    }

    protected void logd(String string2) {
        Log.d(this.mName, string2);
    }

    protected void loge(String string2) {
        Log.e(this.mName, string2);
    }

    protected void loge(String string2, Throwable throwable) {
        Log.e(this.mName, string2, throwable);
    }

    protected void logi(String string2) {
        Log.i(this.mName, string2);
    }

    protected void logv(String string2) {
        Log.v(this.mName, string2);
    }

    protected void logw(String string2) {
        Log.w(this.mName, string2);
    }

    public final Message obtainMessage() {
        return Message.obtain(this.mSmHandler);
    }

    public final Message obtainMessage(int n) {
        return Message.obtain((Handler)this.mSmHandler, n);
    }

    public final Message obtainMessage(int n, int n2) {
        return Message.obtain(this.mSmHandler, n, n2, 0);
    }

    @UnsupportedAppUsage
    public final Message obtainMessage(int n, int n2, int n3) {
        return Message.obtain(this.mSmHandler, n, n2, n3);
    }

    @UnsupportedAppUsage
    public final Message obtainMessage(int n, int n2, int n3, Object object) {
        return Message.obtain(this.mSmHandler, n, n2, n3, object);
    }

    public final Message obtainMessage(int n, Object object) {
        return Message.obtain(this.mSmHandler, n, object);
    }

    protected void onHalting() {
    }

    protected void onPostHandleMessage(Message message) {
    }

    protected void onPreHandleMessage(Message message) {
    }

    protected void onQuitting() {
    }

    public final void quit() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.quit();
    }

    public final void quitNow() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.quitNow();
    }

    protected boolean recordLogRec(Message message) {
        return true;
    }

    protected final void removeDeferredMessages(int n) {
        Object object = this.mSmHandler;
        if (object == null) {
            return;
        }
        object = ((SmHandler)object).mDeferredMessages.iterator();
        while (object.hasNext()) {
            if (((Message)object.next()).what != n) continue;
            object.remove();
        }
    }

    protected final void removeMessages(int n) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.removeMessages(n);
    }

    public final void removeState(State state) {
        this.mSmHandler.removeState(state);
    }

    @UnsupportedAppUsage
    public void sendMessage(int n) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(this.obtainMessage(n));
    }

    @UnsupportedAppUsage
    public void sendMessage(int n, int n2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(this.obtainMessage(n, n2));
    }

    public void sendMessage(int n, int n2, int n3) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(this.obtainMessage(n, n2, n3));
    }

    @UnsupportedAppUsage
    public void sendMessage(int n, int n2, int n3, Object object) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(this.obtainMessage(n, n2, n3, object));
    }

    @UnsupportedAppUsage
    public void sendMessage(int n, Object object) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(this.obtainMessage(n, object));
    }

    @UnsupportedAppUsage
    public void sendMessage(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(message);
    }

    protected final void sendMessageAtFrontOfQueue(int n) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(this.obtainMessage(n));
    }

    protected final void sendMessageAtFrontOfQueue(int n, int n2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(this.obtainMessage(n, n2));
    }

    protected final void sendMessageAtFrontOfQueue(int n, int n2, int n3) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(this.obtainMessage(n, n2, n3));
    }

    protected final void sendMessageAtFrontOfQueue(int n, int n2, int n3, Object object) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(this.obtainMessage(n, n2, n3, object));
    }

    protected final void sendMessageAtFrontOfQueue(int n, Object object) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(this.obtainMessage(n, object));
    }

    protected final void sendMessageAtFrontOfQueue(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(message);
    }

    public void sendMessageDelayed(int n, int n2, int n3, long l) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(this.obtainMessage(n, n2, n3), l);
    }

    public void sendMessageDelayed(int n, int n2, int n3, Object object, long l) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(this.obtainMessage(n, n2, n3, object), l);
    }

    public void sendMessageDelayed(int n, int n2, long l) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(this.obtainMessage(n, n2), l);
    }

    public void sendMessageDelayed(int n, long l) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(this.obtainMessage(n), l);
    }

    public void sendMessageDelayed(int n, Object object, long l) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(this.obtainMessage(n, object), l);
    }

    public void sendMessageDelayed(Message message, long l) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(message, l);
    }

    public void setDbg(boolean bl) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.setDbg(bl);
    }

    @UnsupportedAppUsage
    public final void setInitialState(State state) {
        this.mSmHandler.setInitialState(state);
    }

    public final void setLogOnlyTransitions(boolean bl) {
        this.mSmHandler.mLogRecords.setLogOnlyTransitions(bl);
    }

    public final void setLogRecSize(int n) {
        this.mSmHandler.mLogRecords.setSize(n);
    }

    @UnsupportedAppUsage
    public void start() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.completeConstruction();
    }

    public String toString() {
        CharSequence charSequence;
        String string2 = "(null)";
        CharSequence charSequence2 = "(null)";
        string2 = charSequence = this.mName.toString();
        try {
            String string3 = this.mSmHandler.getCurrentState().getName().toString();
            charSequence2 = string3;
            string2 = charSequence;
            charSequence = charSequence2;
        }
        catch (ArrayIndexOutOfBoundsException | NullPointerException runtimeException) {
            charSequence = charSequence2;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("name=");
        ((StringBuilder)charSequence2).append(string2);
        ((StringBuilder)charSequence2).append(" state=");
        ((StringBuilder)charSequence2).append((String)charSequence);
        return ((StringBuilder)charSequence2).toString();
    }

    @UnsupportedAppUsage
    public final void transitionTo(IState iState) {
        this.mSmHandler.transitionTo(iState);
    }

    public final void transitionToHaltingState() {
        SmHandler smHandler = this.mSmHandler;
        smHandler.transitionTo(smHandler.mHaltingState);
    }

    protected void unhandledMessage(Message message) {
        if (this.mSmHandler.mDbg) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" - unhandledMessage: msg.what=");
            stringBuilder.append(message.what);
            this.loge(stringBuilder.toString());
        }
    }

    public static class LogRec {
        private IState mDstState;
        private String mInfo;
        private IState mOrgState;
        private StateMachine mSm;
        private IState mState;
        private long mTime;
        private int mWhat;

        LogRec(StateMachine stateMachine, Message message, String string2, IState iState, IState iState2, IState iState3) {
            this.update(stateMachine, message, string2, iState, iState2, iState3);
        }

        public IState getDestState() {
            return this.mDstState;
        }

        public String getInfo() {
            return this.mInfo;
        }

        public IState getOriginalState() {
            return this.mOrgState;
        }

        public IState getState() {
            return this.mState;
        }

        public long getTime() {
            return this.mTime;
        }

        public long getWhat() {
            return this.mWhat;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("time=");
            Object object = Calendar.getInstance();
            ((Calendar)object).setTimeInMillis(this.mTime);
            stringBuilder.append(String.format("%tm-%td %tH:%tM:%tS.%tL", object, object, object, object, object, object));
            stringBuilder.append(" processed=");
            object = this.mState;
            String string2 = "<null>";
            object = object == null ? "<null>" : object.getName();
            stringBuilder.append((String)object);
            stringBuilder.append(" org=");
            object = this.mOrgState;
            object = object == null ? "<null>" : object.getName();
            stringBuilder.append((String)object);
            stringBuilder.append(" dest=");
            object = this.mDstState;
            object = object == null ? string2 : object.getName();
            stringBuilder.append((String)object);
            stringBuilder.append(" what=");
            object = this.mSm;
            object = object != null ? ((StateMachine)object).getWhatToString(this.mWhat) : "";
            if (TextUtils.isEmpty((CharSequence)object)) {
                stringBuilder.append(this.mWhat);
                stringBuilder.append("(0x");
                stringBuilder.append(Integer.toHexString(this.mWhat));
                stringBuilder.append(")");
            } else {
                stringBuilder.append((String)object);
            }
            if (!TextUtils.isEmpty(this.mInfo)) {
                stringBuilder.append(" ");
                stringBuilder.append(this.mInfo);
            }
            return stringBuilder.toString();
        }

        public void update(StateMachine stateMachine, Message message, String string2, IState iState, IState iState2, IState iState3) {
            this.mSm = stateMachine;
            this.mTime = System.currentTimeMillis();
            int n = message != null ? message.what : 0;
            this.mWhat = n;
            this.mInfo = string2;
            this.mState = iState;
            this.mOrgState = iState2;
            this.mDstState = iState3;
        }
    }

    private static class LogRecords {
        private static final int DEFAULT_SIZE = 20;
        private int mCount = 0;
        private boolean mLogOnlyTransitions = false;
        private Vector<LogRec> mLogRecVector = new Vector();
        private int mMaxSize = 20;
        private int mOldestIndex = 0;

        private LogRecords() {
        }

        void add(StateMachine stateMachine, Message message, String string2, IState iState, IState iState2, IState iState3) {
            synchronized (this) {
                ++this.mCount;
                if (this.mLogRecVector.size() < this.mMaxSize) {
                    Vector<LogRec> vector = this.mLogRecVector;
                    LogRec logRec = new LogRec(stateMachine, message, string2, iState, iState2, iState3);
                    vector.add(logRec);
                } else {
                    LogRec logRec = this.mLogRecVector.get(this.mOldestIndex);
                    ++this.mOldestIndex;
                    if (this.mOldestIndex >= this.mMaxSize) {
                        this.mOldestIndex = 0;
                    }
                    logRec.update(stateMachine, message, string2, iState, iState2, iState3);
                }
                return;
            }
        }

        void cleanup() {
            synchronized (this) {
                this.mLogRecVector.clear();
                return;
            }
        }

        int count() {
            synchronized (this) {
                int n = this.mCount;
                return n;
            }
        }

        LogRec get(int n) {
            synchronized (this) {
                block6 : {
                    int n2;
                    n = n2 = this.mOldestIndex + n;
                    if (n2 >= this.mMaxSize) {
                        n = n2 - this.mMaxSize;
                    }
                    if (n < (n2 = this.size())) break block6;
                    return null;
                }
                LogRec logRec = this.mLogRecVector.get(n);
                return logRec;
            }
        }

        boolean logOnlyTransitions() {
            synchronized (this) {
                boolean bl = this.mLogOnlyTransitions;
                return bl;
            }
        }

        void setLogOnlyTransitions(boolean bl) {
            synchronized (this) {
                this.mLogOnlyTransitions = bl;
                return;
            }
        }

        void setSize(int n) {
            synchronized (this) {
                this.mMaxSize = n;
                this.mOldestIndex = 0;
                this.mCount = 0;
                this.mLogRecVector.clear();
                return;
            }
        }

        int size() {
            synchronized (this) {
                int n = this.mLogRecVector.size();
                return n;
            }
        }
    }

    private static class SmHandler
    extends Handler {
        private static final Object mSmHandlerObj = new Object();
        private boolean mDbg = false;
        private ArrayList<Message> mDeferredMessages = new ArrayList();
        private State mDestState;
        private HaltingState mHaltingState = new HaltingState();
        private boolean mHasQuit = false;
        private State mInitialState;
        private boolean mIsConstructionCompleted;
        private LogRecords mLogRecords = new LogRecords();
        private Message mMsg;
        private QuittingState mQuittingState = new QuittingState();
        private StateMachine mSm;
        private HashMap<State, StateInfo> mStateInfo = new HashMap();
        private StateInfo[] mStateStack;
        private int mStateStackTopIndex = -1;
        private StateInfo[] mTempStateStack;
        private int mTempStateStackCount;
        private boolean mTransitionInProgress = false;

        private SmHandler(Looper looper, StateMachine stateMachine) {
            super(looper);
            this.mSm = stateMachine;
            this.addState(this.mHaltingState, null);
            this.addState(this.mQuittingState, null);
        }

        static /* synthetic */ StateInfo[] access$2100(SmHandler smHandler) {
            return smHandler.mStateStack;
        }

        static /* synthetic */ int access$2200(SmHandler smHandler) {
            return smHandler.mStateStackTopIndex;
        }

        private final StateInfo addState(State object, State object2) {
            Object object3;
            Object object4;
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("addStateInternal: E state=");
                ((StringBuilder)object3).append(((State)object).getName());
                ((StringBuilder)object3).append(",parent=");
                object4 = object2 == null ? "" : ((State)object2).getName();
                ((StringBuilder)object3).append((String)object4);
                stateMachine.log(((StringBuilder)object3).toString());
            }
            object4 = null;
            if (object2 != null) {
                object4 = object3 = this.mStateInfo.get(object2);
                if (object3 == null) {
                    object4 = this.addState((State)object2, null);
                }
            }
            object2 = object3 = this.mStateInfo.get(object);
            if (object3 == null) {
                object2 = new StateInfo();
                this.mStateInfo.put((State)object, (StateInfo)object2);
            }
            if (((StateInfo)object2).parentStateInfo != null && ((StateInfo)object2).parentStateInfo != object4) {
                throw new RuntimeException("state already added");
            }
            ((StateInfo)object2).state = object;
            ((StateInfo)object2).parentStateInfo = object4;
            ((StateInfo)object2).active = false;
            if (this.mDbg) {
                object = this.mSm;
                object4 = new StringBuilder();
                ((StringBuilder)object4).append("addStateInternal: X stateInfo: ");
                ((StringBuilder)object4).append(object2);
                ((StateMachine)object).log(((StringBuilder)object4).toString());
            }
            return object2;
        }

        private final void cleanupAfterQuitting() {
            if (this.mSm.mSmThread != null) {
                this.getLooper().quit();
                this.mSm.mSmThread = null;
            }
            this.mSm.mSmHandler = null;
            this.mSm = null;
            this.mMsg = null;
            this.mLogRecords.cleanup();
            this.mStateStack = null;
            this.mTempStateStack = null;
            this.mStateInfo.clear();
            this.mInitialState = null;
            this.mDestState = null;
            this.mDeferredMessages.clear();
            this.mHasQuit = true;
        }

        /*
         * WARNING - void declaration
         */
        private final void completeConstruction() {
            if (this.mDbg) {
                this.mSm.log("completeConstruction: E");
            }
            int n = 0;
            for (StateInfo stateInfo : this.mStateInfo.values()) {
                void object;
                int n2 = 0;
                while (object != null) {
                    StateInfo stateInfo2 = object.parentStateInfo;
                    ++n2;
                }
                int n3 = n;
                if (n < n2) {
                    n3 = n2;
                }
                n = n3;
            }
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("completeConstruction: maxDepth=");
                stringBuilder.append(n);
                stateMachine.log(stringBuilder.toString());
            }
            this.mStateStack = new StateInfo[n];
            this.mTempStateStack = new StateInfo[n];
            this.setupInitialStateStack();
            this.sendMessageAtFrontOfQueue(this.obtainMessage(-2, mSmHandlerObj));
            if (this.mDbg) {
                this.mSm.log("completeConstruction: X");
            }
        }

        private final void deferMessage(Message message) {
            Object object;
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                object = new StringBuilder();
                ((StringBuilder)object).append("deferMessage: msg=");
                ((StringBuilder)object).append(message.what);
                stateMachine.log(((StringBuilder)object).toString());
            }
            object = this.obtainMessage();
            ((Message)object).copyFrom(message);
            this.mDeferredMessages.add((Message)object);
        }

        private final Message getCurrentMessage() {
            return this.mMsg;
        }

        private final IState getCurrentState() {
            return this.mStateStack[this.mStateStackTopIndex].state;
        }

        private final void invokeEnterMethods(int n) {
            int n2;
            for (int i = n; i <= (n2 = this.mStateStackTopIndex); ++i) {
                if (n == n2) {
                    this.mTransitionInProgress = false;
                }
                if (this.mDbg) {
                    StateMachine stateMachine = this.mSm;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invokeEnterMethods: ");
                    stringBuilder.append(this.mStateStack[i].state.getName());
                    stateMachine.log(stringBuilder.toString());
                }
                this.mStateStack[i].state.enter();
                this.mStateStack[i].active = true;
            }
            this.mTransitionInProgress = false;
        }

        private final void invokeExitMethods(StateInfo stateInfo) {
            int n;
            Object object;
            while ((n = this.mStateStackTopIndex) >= 0 && (object = this.mStateStack)[n] != stateInfo) {
                State state = object[n].state;
                if (this.mDbg) {
                    StateMachine stateMachine = this.mSm;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("invokeExitMethods: ");
                    ((StringBuilder)object).append(state.getName());
                    stateMachine.log(((StringBuilder)object).toString());
                }
                state.exit();
                object = this.mStateStack;
                n = this.mStateStackTopIndex;
                ((StateInfo)object[n]).active = false;
                this.mStateStackTopIndex = n - 1;
            }
        }

        private final boolean isDbg() {
            return this.mDbg;
        }

        private final boolean isQuit(Message message) {
            boolean bl = message.what == -1 && message.obj == mSmHandlerObj;
            return bl;
        }

        static /* synthetic */ boolean lambda$removeState$0(StateInfo stateInfo, StateInfo stateInfo2) {
            boolean bl = stateInfo2.parentStateInfo == stateInfo;
            return bl;
        }

        private final void moveDeferredMessageAtFrontOfQueue() {
            for (int i = this.mDeferredMessages.size() - 1; i >= 0; --i) {
                Message message = this.mDeferredMessages.get(i);
                if (this.mDbg) {
                    StateMachine stateMachine = this.mSm;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("moveDeferredMessageAtFrontOfQueue; what=");
                    stringBuilder.append(message.what);
                    stateMachine.log(stringBuilder.toString());
                }
                this.sendMessageAtFrontOfQueue(message);
            }
            this.mDeferredMessages.clear();
        }

        private final int moveTempStateStackToStateStack() {
            Object object;
            Object object2;
            int n = this.mStateStackTopIndex + 1;
            int n2 = n;
            for (int i = this.mTempStateStackCount - 1; i >= 0; --i) {
                if (this.mDbg) {
                    object = this.mSm;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("moveTempStackToStateStack: i=");
                    ((StringBuilder)object2).append(i);
                    ((StringBuilder)object2).append(",j=");
                    ((StringBuilder)object2).append(n2);
                    ((StateMachine)object).log(((StringBuilder)object2).toString());
                }
                this.mStateStack[n2] = this.mTempStateStack[i];
                ++n2;
            }
            this.mStateStackTopIndex = n2 - 1;
            if (this.mDbg) {
                object2 = this.mSm;
                object = new StringBuilder();
                ((StringBuilder)object).append("moveTempStackToStateStack: X mStateStackTop=");
                ((StringBuilder)object).append(this.mStateStackTopIndex);
                ((StringBuilder)object).append(",startingIndex=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(",Top=");
                ((StringBuilder)object).append(this.mStateStack[this.mStateStackTopIndex].state.getName());
                ((StateMachine)object2).log(((StringBuilder)object).toString());
            }
            return n;
        }

        private void performTransitions(State object, Message object2) {
            Message message;
            StateMachine stateMachine;
            State state = this.mStateStack[this.mStateStackTopIndex].state;
            boolean bl = this.mSm.recordLogRec(this.mMsg) && ((Message)object2).obj != mSmHandlerObj;
            if (this.mLogRecords.logOnlyTransitions()) {
                if (this.mDestState != null) {
                    object2 = this.mLogRecords;
                    stateMachine = this.mSm;
                    message = this.mMsg;
                    ((LogRecords)object2).add(stateMachine, message, stateMachine.getLogRecString(message), (IState)object, state, this.mDestState);
                }
            } else if (bl) {
                object2 = this.mLogRecords;
                stateMachine = this.mSm;
                message = this.mMsg;
                ((LogRecords)object2).add(stateMachine, message, stateMachine.getLogRecString(message), (IState)object, state, this.mDestState);
            }
            object = object2 = this.mDestState;
            if (object2 != null) {
                object = object2;
                do {
                    if (this.mDbg) {
                        this.mSm.log("handleMessage: new destination call exit/enter");
                    }
                    object2 = this.setupTempStateStackWithStatesToEnter((State)object);
                    this.mTransitionInProgress = true;
                    this.invokeExitMethods((StateInfo)object2);
                    this.invokeEnterMethods(this.moveTempStateStackToStateStack());
                    this.moveDeferredMessageAtFrontOfQueue();
                    if (object == this.mDestState) break;
                    object = this.mDestState;
                } while (true);
                this.mDestState = null;
            }
            if (object != null) {
                if (object == this.mQuittingState) {
                    this.mSm.onQuitting();
                    this.cleanupAfterQuitting();
                } else if (object == this.mHaltingState) {
                    this.mSm.onHalting();
                }
            }
        }

        private final State processMsg(Message object) {
            Object object2;
            Object object3;
            Object object4 = this.mStateStack[this.mStateStackTopIndex];
            if (this.mDbg) {
                object3 = this.mSm;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("processMsg: ");
                ((StringBuilder)object2).append(((StateInfo)object4).state.getName());
                ((StateMachine)object3).log(((StringBuilder)object2).toString());
            }
            object3 = object4;
            if (this.isQuit((Message)object)) {
                this.transitionTo(this.mQuittingState);
            } else {
                do {
                    object4 = object3;
                    if (((StateInfo)object3).state.processMessage((Message)object)) break;
                    object4 = ((StateInfo)object3).parentStateInfo;
                    if (object4 == null) {
                        this.mSm.unhandledMessage((Message)object);
                        break;
                    }
                    object3 = object4;
                    if (!this.mDbg) continue;
                    object2 = this.mSm;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("processMsg: ");
                    ((StringBuilder)object3).append(((StateInfo)object4).state.getName());
                    ((StateMachine)object2).log(((StringBuilder)object3).toString());
                    object3 = object4;
                } while (true);
            }
            object = object4 != null ? ((StateInfo)object4).state : null;
            return object;
        }

        private final void quit() {
            if (this.mDbg) {
                this.mSm.log("quit:");
            }
            this.sendMessage(this.obtainMessage(-1, mSmHandlerObj));
        }

        private final void quitNow() {
            if (this.mDbg) {
                this.mSm.log("quitNow:");
            }
            this.sendMessageAtFrontOfQueue(this.obtainMessage(-1, mSmHandlerObj));
        }

        private void removeState(State state) {
            StateInfo stateInfo = this.mStateInfo.get(state);
            if (stateInfo != null && !stateInfo.active) {
                if (this.mStateInfo.values().stream().filter(new _$$Lambda$StateMachine$SmHandler$KkPO7NIVuI9r_FPEGrY6ux6a5Ks(stateInfo)).findAny().isPresent()) {
                    return;
                }
                this.mStateInfo.remove(state);
                return;
            }
        }

        private final void setDbg(boolean bl) {
            this.mDbg = bl;
        }

        private final void setInitialState(State state) {
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setInitialState: initialState=");
                stringBuilder.append(state.getName());
                stateMachine.log(stringBuilder.toString());
            }
            this.mInitialState = state;
        }

        private final void setupInitialStateStack() {
            Object object;
            if (this.mDbg) {
                object = this.mSm;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setupInitialStateStack: E mInitialState=");
                stringBuilder.append(this.mInitialState.getName());
                ((StateMachine)object).log(stringBuilder.toString());
            }
            object = this.mStateInfo.get(this.mInitialState);
            int n = 0;
            do {
                this.mTempStateStackCount = n;
                if (object == null) break;
                this.mTempStateStack[this.mTempStateStackCount] = object;
                object = ((StateInfo)object).parentStateInfo;
                n = this.mTempStateStackCount + 1;
            } while (true);
            this.mStateStackTopIndex = -1;
            this.moveTempStateStackToStateStack();
        }

        private final StateInfo setupTempStateStackWithStatesToEnter(State object) {
            Object object2;
            this.mTempStateStackCount = 0;
            object = this.mStateInfo.get(object);
            do {
                object2 = this.mTempStateStack;
                int n = this.mTempStateStackCount;
                this.mTempStateStackCount = n + 1;
                object2[n] = object;
                object2 = ((StateInfo)object).parentStateInfo;
                if (object2 == null) break;
                object = object2;
            } while (!object2.active);
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                object = new StringBuilder();
                ((StringBuilder)object).append("setupTempStateStackWithStatesToEnter: X mTempStateStackCount=");
                ((StringBuilder)object).append(this.mTempStateStackCount);
                ((StringBuilder)object).append(",curStateInfo: ");
                ((StringBuilder)object).append(object2);
                stateMachine.log(((StringBuilder)object).toString());
            }
            return object2;
        }

        private final void transitionTo(IState object) {
            Object object2;
            if (this.mTransitionInProgress) {
                String string2 = this.mSm.mName;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("transitionTo called while transition already in progress to ");
                ((StringBuilder)object2).append(this.mDestState);
                ((StringBuilder)object2).append(", new target state=");
                ((StringBuilder)object2).append(object);
                Log.wtf(string2, ((StringBuilder)object2).toString());
            }
            this.mDestState = (State)object;
            if (this.mDbg) {
                object2 = this.mSm;
                object = new StringBuilder();
                ((StringBuilder)object).append("transitionTo: destState=");
                ((StringBuilder)object).append(this.mDestState.getName());
                ((StateMachine)object2).log(((StringBuilder)object).toString());
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public final void handleMessage(Message var1_1) {
            if (this.mHasQuit != false) return;
            if (this.mSm != null && var1_1.what != -2 && var1_1.what != -1) {
                this.mSm.onPreHandleMessage(var1_1);
            }
            if (this.mDbg) {
                var2_2 = this.mSm;
                var3_3 = new StringBuilder();
                var3_3.append("handleMessage: E msg.what=");
                var3_3.append(var1_1.what);
                var2_2.log(var3_3.toString());
            }
            this.mMsg = var1_1;
            var3_3 = null;
            if (this.mIsConstructionCompleted || this.mMsg.what == -1) ** GOTO lbl25
            if (!this.mIsConstructionCompleted && this.mMsg.what == -2 && this.mMsg.obj == SmHandler.mSmHandlerObj) {
                this.mIsConstructionCompleted = true;
                this.invokeEnterMethods(0);
            } else {
                var3_3 = new StringBuilder();
                var3_3.append("StateMachine.handleMessage: The start method not called, received msg: ");
                var3_3.append(var1_1);
                throw new RuntimeException(var3_3.toString());
lbl25: // 1 sources:
                var3_3 = this.processMsg(var1_1);
            }
            this.performTransitions((State)var3_3, var1_1);
            if (this.mDbg && (var3_3 = this.mSm) != null) {
                var3_3.log("handleMessage: X");
            }
            if (this.mSm == null) return;
            if (var1_1.what == -2) return;
            if (var1_1.what == -1) return;
            this.mSm.onPostHandleMessage(var1_1);
        }

        private class HaltingState
        extends State {
            private HaltingState() {
            }

            @Override
            public boolean processMessage(Message message) {
                SmHandler.this.mSm.haltedProcessMessage(message);
                return true;
            }
        }

        private class QuittingState
        extends State {
            private QuittingState() {
            }

            @Override
            public boolean processMessage(Message message) {
                return false;
            }
        }

        private class StateInfo {
            boolean active;
            StateInfo parentStateInfo;
            State state;

            private StateInfo() {
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("state=");
                stringBuilder.append(this.state.getName());
                stringBuilder.append(",active=");
                stringBuilder.append(this.active);
                stringBuilder.append(",parent=");
                Object object = this.parentStateInfo;
                object = object == null ? "null" : ((StateInfo)object).state.getName();
                stringBuilder.append((String)object);
                return stringBuilder.toString();
            }
        }

    }

}

