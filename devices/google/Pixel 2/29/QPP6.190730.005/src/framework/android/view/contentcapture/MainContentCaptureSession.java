/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.LocalLog;
import android.util.Log;
import android.util.TimeUtils;
import android.view.autofill.AutofillId;
import android.view.contentcapture.ChildContentCaptureSession;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureEvent;
import android.view.contentcapture.ContentCaptureHelper;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.contentcapture.IContentCaptureDirectManager;
import android.view.contentcapture.IContentCaptureManager;
import android.view.contentcapture.ViewNode;
import android.view.contentcapture._$$Lambda$MainContentCaptureSession$1$JPRO_nNGZpgXrKr4QC_iQiTbQx0;
import android.view.contentcapture._$$Lambda$MainContentCaptureSession$1$Xhq3WJibbalS1G_W3PRC2m7muhM;
import android.view.contentcapture._$$Lambda$MainContentCaptureSession$49zT7C2BXrEdkyggyGk1Qs4d46k;
import android.view.contentcapture._$$Lambda$MainContentCaptureSession$HTmdDf687TPcaTnLyPp3wo0gI60;
import android.view.contentcapture._$$Lambda$MainContentCaptureSession$UWslDbWedtPhv49PtRsvG4TlYWw;
import com.android.internal.os.IResultReceiver;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MainContentCaptureSession
extends ContentCaptureSession {
    public static final String EXTRA_BINDER = "binder";
    public static final String EXTRA_ENABLED_STATE = "enabled";
    private static final boolean FORCE_FLUSH = true;
    private static final int MSG_FLUSH = 1;
    private static final String TAG = MainContentCaptureSession.class.getSimpleName();
    private IBinder mApplicationToken;
    private ComponentName mComponentName;
    private final Context mContext;
    private IContentCaptureDirectManager mDirectServiceInterface;
    private IBinder.DeathRecipient mDirectServiceVulture;
    private final AtomicBoolean mDisabled = new AtomicBoolean(false);
    private ArrayList<ContentCaptureEvent> mEvents;
    private final LocalLog mFlushHistory;
    private final Handler mHandler;
    private final ContentCaptureManager mManager;
    private long mNextFlush;
    private boolean mNextFlushForTextChanged = false;
    private final IResultReceiver.Stub mSessionStateReceiver;
    private int mState = 0;
    private final IContentCaptureManager mSystemServerInterface;

    protected MainContentCaptureSession(Context object, ContentCaptureManager contentCaptureManager, Handler handler, IContentCaptureManager iContentCaptureManager) {
        this.mContext = object;
        this.mManager = contentCaptureManager;
        this.mHandler = handler;
        this.mSystemServerInterface = iContentCaptureManager;
        int n = this.mManager.mOptions.logHistorySize;
        object = n > 0 ? new LocalLog(n) : null;
        this.mFlushHistory = object;
        this.mSessionStateReceiver = new IResultReceiver.Stub(){

            public /* synthetic */ void lambda$send$0$MainContentCaptureSession$1() {
                MainContentCaptureSession.this.resetSession(260);
            }

            public /* synthetic */ void lambda$send$1$MainContentCaptureSession$1(int n, IBinder iBinder) {
                MainContentCaptureSession.this.onSessionStarted(n, iBinder);
            }

            @Override
            public void send(int n, Bundle object) {
                if (object != null) {
                    if (((BaseBundle)object).getBoolean(MainContentCaptureSession.EXTRA_ENABLED_STATE)) {
                        boolean bl = n == 2;
                        MainContentCaptureSession.this.mDisabled.set(bl);
                        return;
                    }
                    if ((object = ((Bundle)object).getBinder(MainContentCaptureSession.EXTRA_BINDER)) == null) {
                        Log.wtf(TAG, "No binder extra result");
                        MainContentCaptureSession.this.mHandler.post(new _$$Lambda$MainContentCaptureSession$1$JPRO_nNGZpgXrKr4QC_iQiTbQx0(this));
                        return;
                    }
                } else {
                    object = null;
                }
                MainContentCaptureSession.this.mHandler.post(new _$$Lambda$MainContentCaptureSession$1$Xhq3WJibbalS1G_W3PRC2m7muhM(this, n, (IBinder)object));
            }
        };
    }

    private ParceledListSlice<ContentCaptureEvent> clearEvents() {
        List<ContentCaptureEvent> list = this.mEvents;
        if (list == null) {
            list = Collections.emptyList();
        }
        this.mEvents = null;
        return new ParceledListSlice<ContentCaptureEvent>(list);
    }

    private void destroySession() {
        Serializable serializable;
        String string2;
        if (ContentCaptureHelper.sDebug) {
            string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Destroying session (ctx=");
            stringBuilder.append(this.mContext);
            stringBuilder.append(", id=");
            stringBuilder.append(this.mId);
            stringBuilder.append(") with ");
            serializable = this.mEvents;
            int n = serializable == null ? 0 : ((ArrayList)serializable).size();
            stringBuilder.append(n);
            stringBuilder.append(" event(s) for ");
            stringBuilder.append(this.getDebugState());
            Log.d(string2, stringBuilder.toString());
        }
        try {
            this.mSystemServerInterface.finishSession(this.mId);
        }
        catch (RemoteException remoteException) {
            string2 = TAG;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Error destroying system-service session ");
            ((StringBuilder)serializable).append(this.mId);
            ((StringBuilder)serializable).append(" for ");
            ((StringBuilder)serializable).append(this.getDebugState());
            ((StringBuilder)serializable).append(": ");
            ((StringBuilder)serializable).append(remoteException);
            Log.e(string2, ((StringBuilder)serializable).toString());
        }
    }

    private void flushIfNeeded(int n) {
        ArrayList<ContentCaptureEvent> arrayList = this.mEvents;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.flush(n);
            return;
        }
        if (ContentCaptureHelper.sVerbose) {
            Log.v(TAG, "Nothing to flush");
        }
    }

    private String getActivityName() {
        CharSequence charSequence;
        if (this.mComponentName == null) {
            charSequence = new StringBuilder();
            charSequence.append("pkg:");
            charSequence.append(this.mContext.getPackageName());
            charSequence = charSequence.toString();
        } else {
            charSequence = new StringBuilder();
            charSequence.append("act:");
            charSequence.append(this.mComponentName.flattenToShortString());
            charSequence = charSequence.toString();
        }
        return charSequence;
    }

    private String getDebugState() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getActivityName());
        stringBuilder.append(" [state=");
        stringBuilder.append(MainContentCaptureSession.getStateAsString(this.mState));
        stringBuilder.append(", disabled=");
        stringBuilder.append(this.mDisabled.get());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private String getDebugState(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getDebugState());
        stringBuilder.append(", reason=");
        stringBuilder.append(MainContentCaptureSession.getFlushReasonAsString(n));
        return stringBuilder.toString();
    }

    private boolean hasStarted() {
        boolean bl = this.mState != 0;
        return bl;
    }

    private void onSessionStarted(int n, IBinder object) {
        StringBuilder stringBuilder;
        String string2;
        int n2 = 0;
        if (object != null) {
            this.mDirectServiceInterface = IContentCaptureDirectManager.Stub.asInterface((IBinder)object);
            this.mDirectServiceVulture = new _$$Lambda$MainContentCaptureSession$UWslDbWedtPhv49PtRsvG4TlYWw(this);
            try {
                object.linkToDeath(this.mDirectServiceVulture, 0);
            }
            catch (RemoteException remoteException) {
                string2 = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to link to death on ");
                stringBuilder.append(object);
                stringBuilder.append(": ");
                stringBuilder.append(remoteException);
                Log.w(string2, stringBuilder.toString());
            }
        }
        if ((n & 4) != 0) {
            this.resetSession(n);
        } else {
            this.mState = n;
            this.mDisabled.set(false);
        }
        if (ContentCaptureHelper.sVerbose) {
            string2 = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("handleSessionStarted() result: id=");
            stringBuilder.append(this.mId);
            stringBuilder.append(" resultCode=");
            stringBuilder.append(n);
            stringBuilder.append(", state=");
            stringBuilder.append(MainContentCaptureSession.getStateAsString(this.mState));
            stringBuilder.append(", disabled=");
            stringBuilder.append(this.mDisabled.get());
            stringBuilder.append(", binder=");
            stringBuilder.append(object);
            stringBuilder.append(", events=");
            object = this.mEvents;
            n = object == null ? n2 : ((ArrayList)object).size();
            stringBuilder.append(n);
            Log.v(string2, stringBuilder.toString());
        }
    }

    private void resetSession(int n) {
        Object object;
        if (ContentCaptureHelper.sVerbose) {
            String string2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("handleResetSession(");
            ((StringBuilder)object).append(this.getActivityName());
            ((StringBuilder)object).append("): from ");
            ((StringBuilder)object).append(MainContentCaptureSession.getStateAsString(this.mState));
            ((StringBuilder)object).append(" to ");
            ((StringBuilder)object).append(MainContentCaptureSession.getStateAsString(n));
            Log.v(string2, ((StringBuilder)object).toString());
        }
        this.mState = n;
        object = this.mDisabled;
        boolean bl = (n & 4) != 0;
        ((AtomicBoolean)object).set(bl);
        this.mApplicationToken = null;
        this.mComponentName = null;
        this.mEvents = null;
        object = this.mDirectServiceInterface;
        if (object != null) {
            object.asBinder().unlinkToDeath(this.mDirectServiceVulture, 0);
        }
        this.mDirectServiceInterface = null;
        this.mHandler.removeMessages(1);
    }

    private void scheduleFlush(int n, boolean bl) {
        Object object;
        CharSequence charSequence;
        block11 : {
            int n2;
            block10 : {
                block9 : {
                    if (ContentCaptureHelper.sVerbose) {
                        charSequence = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("handleScheduleFlush(");
                        ((StringBuilder)object).append(this.getDebugState(n));
                        ((StringBuilder)object).append(", checkExisting=");
                        ((StringBuilder)object).append(bl);
                        Log.v((String)charSequence, ((StringBuilder)object).toString());
                    }
                    if (!this.hasStarted()) {
                        if (ContentCaptureHelper.sVerbose) {
                            Log.v(TAG, "handleScheduleFlush(): session not started yet");
                        }
                        return;
                    }
                    if (this.mDisabled.get()) {
                        charSequence = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("handleScheduleFlush(");
                        stringBuilder.append(this.getDebugState(n));
                        stringBuilder.append("): should not be called when disabled. events=");
                        object = this.mEvents;
                        object = object == null ? null : Integer.valueOf(((ArrayList)object).size());
                        stringBuilder.append(object);
                        Log.e((String)charSequence, stringBuilder.toString());
                        return;
                    }
                    if (bl && this.mHandler.hasMessages(1)) {
                        this.mHandler.removeMessages(1);
                    }
                    if (n != 5) break block9;
                    n2 = this.mManager.mOptions.idleFlushingFrequencyMs;
                    break block10;
                }
                if (n != 6) break block11;
                n2 = this.mManager.mOptions.textChangeFlushingFrequencyMs;
            }
            this.mNextFlush = System.currentTimeMillis() + (long)n2;
            if (ContentCaptureHelper.sVerbose) {
                charSequence = TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("handleScheduleFlush(): scheduled to flush in ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append("ms: ");
                ((StringBuilder)object).append(TimeUtils.logTimeOfDay(this.mNextFlush));
                Log.v((String)charSequence, ((StringBuilder)object).toString());
            }
            this.mHandler.postDelayed((Runnable)new _$$Lambda$MainContentCaptureSession$49zT7C2BXrEdkyggyGk1Qs4d46k(this, n), 1, (long)n2);
            return;
        }
        object = TAG;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("handleScheduleFlush(");
        ((StringBuilder)charSequence).append(this.getDebugState(n));
        ((StringBuilder)charSequence).append("): not called with a timeout reason.");
        Log.e((String)object, ((StringBuilder)charSequence).toString());
    }

    private void sendEvent(ContentCaptureEvent contentCaptureEvent) {
        this.sendEvent(contentCaptureEvent, false);
    }

    private void sendEvent(ContentCaptureEvent object, boolean bl) {
        CharSequence charSequence;
        Object object2;
        int n;
        Object object3;
        int n2 = ((ContentCaptureEvent)object).getType();
        if (ContentCaptureHelper.sVerbose) {
            charSequence = TAG;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("handleSendEvent(");
            ((StringBuilder)object3).append(this.getDebugState());
            ((StringBuilder)object3).append("): ");
            ((StringBuilder)object3).append(object);
            Log.v((String)charSequence, ((StringBuilder)object3).toString());
        }
        if (!this.hasStarted() && n2 != -1 && n2 != 6) {
            object3 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("handleSendEvent(");
            ((StringBuilder)object).append(this.getDebugState());
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(ContentCaptureEvent.getTypeAsString(n2));
            ((StringBuilder)object).append("): dropping because session not started yet");
            Log.v(object3, ((StringBuilder)object).toString());
            return;
        }
        if (this.mDisabled.get()) {
            if (ContentCaptureHelper.sVerbose) {
                Log.v(TAG, "handleSendEvent(): ignoring when disabled");
            }
            return;
        }
        int n3 = this.mManager.mOptions.maxBufferSize;
        if (this.mEvents == null) {
            if (ContentCaptureHelper.sVerbose) {
                charSequence = TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("handleSendEvent(): creating buffer for ");
                ((StringBuilder)object3).append(n3);
                ((StringBuilder)object3).append(" events");
                Log.v((String)charSequence, ((StringBuilder)object3).toString());
            }
            this.mEvents = new ArrayList(n3);
        }
        int n4 = n = 1;
        if (!this.mEvents.isEmpty()) {
            n4 = n;
            if (n2 == 3) {
                object3 = this.mEvents;
                object3 = (ContentCaptureEvent)((ArrayList)object3).get(((ArrayList)object3).size() - 1);
                n4 = n;
                if (((ContentCaptureEvent)object3).getType() == 3) {
                    n4 = n;
                    if (((ContentCaptureEvent)object3).getId().equals(((ContentCaptureEvent)object).getId())) {
                        if (ContentCaptureHelper.sVerbose) {
                            charSequence = TAG;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Buffering VIEW_TEXT_CHANGED event, updated text=");
                            ((StringBuilder)object2).append(ContentCaptureHelper.getSanitizedString(((ContentCaptureEvent)object).getText()));
                            Log.v((String)charSequence, ((StringBuilder)object2).toString());
                        }
                        ((ContentCaptureEvent)object3).mergeEvent((ContentCaptureEvent)object);
                        n4 = 0;
                    }
                }
            }
        }
        n = n4;
        if (!this.mEvents.isEmpty()) {
            n = n4;
            if (n2 == 2) {
                object3 = this.mEvents;
                object2 = ((ArrayList)object3).get(((ArrayList)object3).size() - 1);
                n = n4;
                if (((ContentCaptureEvent)object2).getType() == 2) {
                    n = n4;
                    if (((ContentCaptureEvent)object).getSessionId() == ((ContentCaptureEvent)object2).getSessionId()) {
                        if (ContentCaptureHelper.sVerbose) {
                            object3 = TAG;
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Buffering TYPE_VIEW_DISAPPEARED events for session ");
                            ((StringBuilder)charSequence).append(((ContentCaptureEvent)object2).getSessionId());
                            Log.v((String)object3, ((StringBuilder)charSequence).toString());
                        }
                        ((ContentCaptureEvent)object2).mergeEvent((ContentCaptureEvent)object);
                        n = 0;
                    }
                }
            }
        }
        if (n != 0) {
            this.mEvents.add((ContentCaptureEvent)object);
        }
        if ((n4 = (n = this.mEvents.size()) < n3 ? 1 : 0) != 0 && !bl) {
            if (n2 == 3) {
                this.mNextFlushForTextChanged = true;
                n4 = 6;
            } else {
                if (this.mNextFlushForTextChanged) {
                    if (ContentCaptureHelper.sVerbose) {
                        Log.i(TAG, "Not scheduling flush because next flush is for text changed");
                    }
                    return;
                }
                n4 = 5;
            }
            this.scheduleFlush(n4, true);
            return;
        }
        if (this.mState != 2 && n >= n3) {
            if (ContentCaptureHelper.sDebug) {
                object = TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Closing session for ");
                ((StringBuilder)object3).append(this.getDebugState());
                ((StringBuilder)object3).append(" after ");
                ((StringBuilder)object3).append(n);
                ((StringBuilder)object3).append(" delayed events");
                Log.d((String)object, ((StringBuilder)object3).toString());
            }
            this.resetSession(132);
            return;
        }
        n4 = n2 != -2 ? (n2 != -1 ? 1 : 3) : 4;
        this.flush(n4);
    }

    @Override
    void dump(String string2, PrintWriter printWriter) {
        Object object;
        super.dump(string2, printWriter);
        printWriter.print(string2);
        printWriter.print("mContext: ");
        printWriter.println(this.mContext);
        printWriter.print(string2);
        printWriter.print("user: ");
        printWriter.println(this.mContext.getUserId());
        if (this.mDirectServiceInterface != null) {
            printWriter.print(string2);
            printWriter.print("mDirectServiceInterface: ");
            printWriter.println(this.mDirectServiceInterface);
        }
        printWriter.print(string2);
        printWriter.print("mDisabled: ");
        printWriter.println(this.mDisabled.get());
        printWriter.print(string2);
        printWriter.print("isEnabled(): ");
        printWriter.println(this.isContentCaptureEnabled());
        printWriter.print(string2);
        printWriter.print("state: ");
        printWriter.println(MainContentCaptureSession.getStateAsString(this.mState));
        if (this.mApplicationToken != null) {
            printWriter.print(string2);
            printWriter.print("app token: ");
            printWriter.println(this.mApplicationToken);
        }
        if (this.mComponentName != null) {
            printWriter.print(string2);
            printWriter.print("component name: ");
            printWriter.println(this.mComponentName.flattenToShortString());
        }
        if ((object = this.mEvents) != null && !((ArrayList)object).isEmpty()) {
            int n = this.mEvents.size();
            printWriter.print(string2);
            printWriter.print("buffered events: ");
            printWriter.print(n);
            printWriter.print('/');
            printWriter.println(this.mManager.mOptions.maxBufferSize);
            if (ContentCaptureHelper.sVerbose && n > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("  ");
                object = ((StringBuilder)object).toString();
                for (int i = 0; i < n; ++i) {
                    ContentCaptureEvent contentCaptureEvent = this.mEvents.get(i);
                    printWriter.print((String)object);
                    printWriter.print(i);
                    printWriter.print(": ");
                    contentCaptureEvent.dump(printWriter);
                    printWriter.println();
                }
            }
            printWriter.print(string2);
            printWriter.print("mNextFlushForTextChanged: ");
            printWriter.println(this.mNextFlushForTextChanged);
            printWriter.print(string2);
            printWriter.print("flush frequency: ");
            if (this.mNextFlushForTextChanged) {
                printWriter.println(this.mManager.mOptions.textChangeFlushingFrequencyMs);
            } else {
                printWriter.println(this.mManager.mOptions.idleFlushingFrequencyMs);
            }
            printWriter.print(string2);
            printWriter.print("next flush: ");
            TimeUtils.formatDuration(this.mNextFlush - System.currentTimeMillis(), printWriter);
            printWriter.print(" (");
            printWriter.print(TimeUtils.logTimeOfDay(this.mNextFlush));
            printWriter.println(")");
        }
        if (this.mFlushHistory != null) {
            printWriter.print(string2);
            printWriter.println("flush history:");
            this.mFlushHistory.reverseDump(null, printWriter, null);
            printWriter.println();
        } else {
            printWriter.print(string2);
            printWriter.println("not logging flush history");
        }
        super.dump(string2, printWriter);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void flush(int n) {
        String string2;
        StringBuilder stringBuilder;
        if (this.mEvents == null) {
            return;
        }
        if (this.mDisabled.get()) {
            String string3 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("handleForceFlush(");
            stringBuilder2.append(this.getDebugState(n));
            stringBuilder2.append("): should not be when disabled");
            Log.e(string3, stringBuilder2.toString());
            return;
        }
        if (this.mDirectServiceInterface == null) {
            if (ContentCaptureHelper.sVerbose) {
                String string4 = TAG;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("handleForceFlush(");
                stringBuilder3.append(this.getDebugState(n));
                stringBuilder3.append("): hold your horses, client not ready: ");
                stringBuilder3.append(this.mEvents);
                Log.v(string4, stringBuilder3.toString());
            }
            if (this.mHandler.hasMessages(1)) return;
            this.scheduleFlush(n, false);
            return;
        }
        int n2 = this.mEvents.size();
        Object object = MainContentCaptureSession.getFlushReasonAsString(n);
        if (ContentCaptureHelper.sDebug) {
            string2 = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Flushing ");
            stringBuilder.append(n2);
            stringBuilder.append(" event(s) for ");
            stringBuilder.append(this.getDebugState(n));
            Log.d(string2, stringBuilder.toString());
        }
        if (this.mFlushHistory != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("r=");
            stringBuilder.append((String)object);
            stringBuilder.append(" s=");
            stringBuilder.append(n2);
            stringBuilder.append(" m=");
            stringBuilder.append(this.mManager.mOptions.maxBufferSize);
            stringBuilder.append(" i=");
            stringBuilder.append(this.mManager.mOptions.idleFlushingFrequencyMs);
            object = stringBuilder.toString();
            this.mFlushHistory.log((String)object);
        }
        try {
            this.mHandler.removeMessages(1);
            if (n == 6) {
                this.mNextFlushForTextChanged = false;
            }
            object = this.clearEvents();
            this.mDirectServiceInterface.sendEvents((ParceledListSlice)object, n, this.mManager.mOptions);
            return;
        }
        catch (RemoteException remoteException) {
            string2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("Error sending ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append(this.getDebugState());
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(remoteException);
            Log.w(string2, ((StringBuilder)object).toString());
        }
    }

    @Override
    MainContentCaptureSession getMainCaptureSession() {
        return this;
    }

    @Override
    void internalNotifyViewAppeared(ViewNode.ViewStructureImpl viewStructureImpl) {
        this.notifyViewAppeared(this.mId, viewStructureImpl);
    }

    @Override
    void internalNotifyViewDisappeared(AutofillId autofillId) {
        this.notifyViewDisappeared(this.mId, autofillId);
    }

    @Override
    void internalNotifyViewTextChanged(AutofillId autofillId, CharSequence charSequence) {
        this.notifyViewTextChanged(this.mId, autofillId, charSequence);
    }

    @Override
    public void internalNotifyViewTreeEvent(boolean bl) {
        this.notifyViewTreeEvent(this.mId, bl);
    }

    @Override
    boolean isContentCaptureEnabled() {
        boolean bl = super.isContentCaptureEnabled() && this.mManager.isContentCaptureEnabled();
        return bl;
    }

    boolean isDisabled() {
        return this.mDisabled.get();
    }

    public /* synthetic */ void lambda$onDestroy$0$MainContentCaptureSession() {
        this.destroySession();
    }

    public /* synthetic */ void lambda$onSessionStarted$1$MainContentCaptureSession() {
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Keeping session ");
        stringBuilder.append(this.mId);
        stringBuilder.append(" when service died");
        Log.w(string2, stringBuilder.toString());
        this.mState = 1024;
        this.mDisabled.set(true);
    }

    public /* synthetic */ void lambda$scheduleFlush$2$MainContentCaptureSession(int n) {
        this.flushIfNeeded(n);
    }

    @Override
    ContentCaptureSession newChild(ContentCaptureContext contentCaptureContext) {
        ChildContentCaptureSession childContentCaptureSession = new ChildContentCaptureSession(this, contentCaptureContext);
        this.notifyChildSessionStarted(this.mId, childContentCaptureSession.mId, contentCaptureContext);
        return childContentCaptureSession;
    }

    void notifyChildSessionFinished(int n, int n2) {
        this.sendEvent(new ContentCaptureEvent(n2, -2).setParentSessionId(n), true);
    }

    void notifyChildSessionStarted(int n, int n2, ContentCaptureContext contentCaptureContext) {
        this.sendEvent(new ContentCaptureEvent(n2, -1).setParentSessionId(n).setClientContext(contentCaptureContext), true);
    }

    void notifyContextUpdated(int n, ContentCaptureContext contentCaptureContext) {
        this.sendEvent(new ContentCaptureEvent(n, 6).setClientContext(contentCaptureContext));
    }

    public void notifySessionLifecycle(boolean bl) {
        int n = bl ? 7 : 8;
        this.sendEvent(new ContentCaptureEvent(this.mId, n), true);
    }

    void notifyViewAppeared(int n, ViewNode.ViewStructureImpl viewStructureImpl) {
        this.sendEvent(new ContentCaptureEvent(n, 1).setViewNode(viewStructureImpl.mNode));
    }

    public void notifyViewDisappeared(int n, AutofillId autofillId) {
        this.sendEvent(new ContentCaptureEvent(n, 2).setAutofillId(autofillId));
    }

    void notifyViewTextChanged(int n, AutofillId autofillId, CharSequence charSequence) {
        this.sendEvent(new ContentCaptureEvent(n, 3).setAutofillId(autofillId).setText(charSequence));
    }

    public void notifyViewTreeEvent(int n, boolean bl) {
        int n2 = bl ? 4 : 5;
        this.sendEvent(new ContentCaptureEvent(n, n2), true);
    }

    @Override
    void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.post(new _$$Lambda$MainContentCaptureSession$HTmdDf687TPcaTnLyPp3wo0gI60(this));
    }

    boolean setDisabled(boolean bl) {
        return this.mDisabled.compareAndSet(bl ^ true, bl);
    }

    void start(IBinder object, ComponentName componentName, int n) {
        CharSequence charSequence;
        CharSequence charSequence2;
        if (!this.isContentCaptureEnabled()) {
            return;
        }
        if (ContentCaptureHelper.sVerbose) {
            charSequence2 = TAG;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("start(): token=");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(", comp=");
            ((StringBuilder)charSequence).append(ComponentName.flattenToShortString(componentName));
            Log.v((String)charSequence2, ((StringBuilder)charSequence).toString());
        }
        if (this.hasStarted()) {
            if (ContentCaptureHelper.sDebug) {
                charSequence = TAG;
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("ignoring handleStartSession(");
                ((StringBuilder)charSequence2).append(object);
                ((StringBuilder)charSequence2).append("/");
                ((StringBuilder)charSequence2).append(ComponentName.flattenToShortString(componentName));
                ((StringBuilder)charSequence2).append(" while on state ");
                ((StringBuilder)charSequence2).append(MainContentCaptureSession.getStateAsString(this.mState));
                Log.d((String)charSequence, ((StringBuilder)charSequence2).toString());
            }
            return;
        }
        this.mState = 1;
        this.mApplicationToken = object;
        this.mComponentName = componentName;
        if (ContentCaptureHelper.sVerbose) {
            charSequence = TAG;
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("handleStartSession(): token=");
            ((StringBuilder)charSequence2).append(object);
            ((StringBuilder)charSequence2).append(", act=");
            ((StringBuilder)charSequence2).append(this.getDebugState());
            ((StringBuilder)charSequence2).append(", id=");
            ((StringBuilder)charSequence2).append(this.mId);
            Log.v((String)charSequence, ((StringBuilder)charSequence2).toString());
        }
        try {
            this.mSystemServerInterface.startSession(this.mApplicationToken, componentName, this.mId, n, this.mSessionStateReceiver);
        }
        catch (RemoteException remoteException) {
            charSequence2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("Error starting session for ");
            ((StringBuilder)object).append(componentName.flattenToShortString());
            ((StringBuilder)object).append(": ");
            ((StringBuilder)object).append(remoteException);
            Log.w((String)charSequence2, ((StringBuilder)object).toString());
        }
    }

    @Override
    public void updateContentCaptureContext(ContentCaptureContext contentCaptureContext) {
        this.notifyContextUpdated(this.mId, contentCaptureContext);
    }

}

