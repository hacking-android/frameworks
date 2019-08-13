/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$dUWXWbBHcaaVBn031EDBP91NR7k
 */
package android.app;

import android.app.-$;
import android.app.Activity;
import android.app._$$Lambda$dUWXWbBHcaaVBn031EDBP91NR7k;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.IVoiceInteractorCallback;
import com.android.internal.app.IVoiceInteractorRequest;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public final class VoiceInteractor {
    static final boolean DEBUG = false;
    public static final String KEY_CANCELLATION_SIGNAL = "key_cancellation_signal";
    public static final String KEY_KILL_SIGNAL = "key_kill_signal";
    static final int MSG_ABORT_VOICE_RESULT = 4;
    static final int MSG_CANCEL_RESULT = 6;
    static final int MSG_COMMAND_RESULT = 5;
    static final int MSG_COMPLETE_VOICE_RESULT = 3;
    static final int MSG_CONFIRMATION_RESULT = 1;
    static final int MSG_PICK_OPTION_RESULT = 2;
    static final Request[] NO_REQUESTS = new Request[0];
    static final String TAG = "VoiceInteractor";
    final ArrayMap<IBinder, Request> mActiveRequests = new ArrayMap();
    Activity mActivity;
    final IVoiceInteractorCallback.Stub mCallback = new IVoiceInteractorCallback.Stub(){

        @Override
        public void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) {
            VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageOO(4, iVoiceInteractorRequest, bundle));
        }

        @Override
        public void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) {
            VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageOO(6, iVoiceInteractorRequest, null));
        }

        @Override
        public void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, Bundle bundle) {
            HandlerCaller handlerCaller = VoiceInteractor.this.mHandlerCaller;
            HandlerCaller handlerCaller2 = VoiceInteractor.this.mHandlerCaller;
            handlerCaller.sendMessage(handlerCaller2.obtainMessageIOO(5, (int)bl, iVoiceInteractorRequest, bundle));
        }

        @Override
        public void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) {
            VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageOO(3, iVoiceInteractorRequest, bundle));
        }

        @Override
        public void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, Bundle bundle) {
            HandlerCaller handlerCaller = VoiceInteractor.this.mHandlerCaller;
            HandlerCaller handlerCaller2 = VoiceInteractor.this.mHandlerCaller;
            handlerCaller.sendMessage(handlerCaller2.obtainMessageIOO(1, (int)bl, iVoiceInteractorRequest, bundle));
        }

        @Override
        public void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl, PickOptionRequest.Option[] arroption, Bundle bundle) {
            HandlerCaller handlerCaller = VoiceInteractor.this.mHandlerCaller;
            HandlerCaller handlerCaller2 = VoiceInteractor.this.mHandlerCaller;
            handlerCaller.sendMessage(handlerCaller2.obtainMessageIOOO(2, (int)bl, iVoiceInteractorRequest, arroption, bundle));
        }

        @Override
        public void destroy() {
            VoiceInteractor.this.mHandlerCaller.getHandler().sendMessage(PooledLambda.obtainMessage(_$$Lambda$dUWXWbBHcaaVBn031EDBP91NR7k.INSTANCE, VoiceInteractor.this));
        }
    };
    Context mContext;
    final HandlerCaller mHandlerCaller;
    final HandlerCaller.Callback mHandlerCallerCallback = new HandlerCaller.Callback(){

        @Override
        public void executeMessage(Message object) {
            SomeArgs someArgs = (SomeArgs)((Message)object).obj;
            int n = ((Message)object).what;
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            switch (n) {
                default: {
                    break;
                }
                case 6: {
                    object = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest)someArgs.arg1, true);
                    if (object == null) break;
                    ((Request)object).onCancel();
                    ((Request)object).clear();
                    break;
                }
                case 5: {
                    bl = ((Message)object).arg1 != 0;
                    Request request = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest)someArgs.arg1, bl);
                    if (request == null) break;
                    CommandRequest commandRequest = (CommandRequest)request;
                    if (((Message)object).arg1 != 0) {
                        bl3 = true;
                    }
                    commandRequest.onCommandResult(bl3, (Bundle)someArgs.arg2);
                    if (!bl) break;
                    request.clear();
                    break;
                }
                case 4: {
                    object = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest)someArgs.arg1, true);
                    if (object == null) break;
                    ((AbortVoiceRequest)object).onAbortResult((Bundle)someArgs.arg2);
                    ((Request)object).clear();
                    break;
                }
                case 3: {
                    object = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest)someArgs.arg1, true);
                    if (object == null) break;
                    ((CompleteVoiceRequest)object).onCompleteResult((Bundle)someArgs.arg2);
                    ((Request)object).clear();
                    break;
                }
                case 2: {
                    if (((Message)object).arg1 != 0) {
                        bl = true;
                    }
                    if ((object = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest)someArgs.arg1, bl)) == null) break;
                    ((PickOptionRequest)object).onPickOptionResult(bl, (PickOptionRequest.Option[])someArgs.arg2, (Bundle)someArgs.arg3);
                    if (!bl) break;
                    ((Request)object).clear();
                    break;
                }
                case 1: {
                    Request request = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest)someArgs.arg1, true);
                    if (request == null) break;
                    ConfirmationRequest confirmationRequest = (ConfirmationRequest)request;
                    bl = bl2;
                    if (((Message)object).arg1 != 0) {
                        bl = true;
                    }
                    confirmationRequest.onConfirmationResult(bl, (Bundle)someArgs.arg2);
                    request.clear();
                }
            }
        }
    };
    IVoiceInteractor mInteractor;
    final ArrayMap<Runnable, Executor> mOnDestroyCallbacks = new ArrayMap();
    boolean mRetaining;

    VoiceInteractor(IVoiceInteractor iInterface, Context object, Activity activity, Looper looper) {
        this.mInteractor = iInterface;
        this.mContext = object;
        this.mActivity = activity;
        this.mHandlerCaller = new HandlerCaller((Context)object, looper, this.mHandlerCallerCallback, true);
        try {
            object = this.mInteractor;
            super(this);
            object.setKillCallback((ICancellationSignal)iInterface);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private ArrayList<Request> makeRequestList() {
        int n = this.mActiveRequests.size();
        if (n < 1) {
            return null;
        }
        ArrayList<Request> arrayList = new ArrayList<Request>(n);
        for (int i = 0; i < n; ++i) {
            arrayList.add(this.mActiveRequests.valueAt(i));
        }
        return arrayList;
    }

    void attachActivity(Activity activity) {
        this.mRetaining = false;
        if (this.mActivity == activity) {
            return;
        }
        this.mContext = activity;
        this.mActivity = activity;
        ArrayList<Request> arrayList = this.makeRequestList();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); ++i) {
                Request request = arrayList.get(i);
                request.mContext = activity;
                request.mActivity = activity;
                request.onAttached(activity);
            }
        }
    }

    void destroy() {
        Object object;
        int n;
        for (n = this.mActiveRequests.size() - 1; n >= 0; --n) {
            object = this.mActiveRequests.valueAt(n);
            this.mActiveRequests.removeAt(n);
            ((Request)object).cancel();
        }
        for (n = this.mOnDestroyCallbacks.size() - 1; n >= 0; --n) {
            object = this.mOnDestroyCallbacks.keyAt(n);
            this.mOnDestroyCallbacks.valueAt(n).execute((Runnable)object);
            this.mOnDestroyCallbacks.removeAt(n);
        }
        this.mInteractor = null;
        object = this.mActivity;
        if (object != null) {
            ((Activity)object).setVoiceInteractor(null);
        }
    }

    void detachActivity() {
        int n;
        ArrayList<Request> arrayList = this.makeRequestList();
        if (arrayList != null) {
            for (n = 0; n < arrayList.size(); ++n) {
                Request request = arrayList.get(n);
                request.onDetached();
                request.mActivity = null;
                request.mContext = null;
            }
        }
        if (!this.mRetaining) {
            arrayList = this.makeRequestList();
            if (arrayList != null) {
                for (n = 0; n < arrayList.size(); ++n) {
                    arrayList.get(n).cancel();
                }
            }
            this.mActiveRequests.clear();
        }
        this.mContext = null;
        this.mActivity = null;
    }

    void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(string2);
        charSequence.append("    ");
        charSequence = charSequence.toString();
        if (this.mActiveRequests.size() > 0) {
            printWriter.print(string2);
            printWriter.println("Active voice requests:");
            for (int i = 0; i < this.mActiveRequests.size(); ++i) {
                Request request = this.mActiveRequests.valueAt(i);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(request);
                request.dump((String)charSequence, fileDescriptor, printWriter, arrstring);
            }
        }
        printWriter.print(string2);
        printWriter.println("VoiceInteractor misc state:");
        printWriter.print(string2);
        printWriter.print("  mInteractor=");
        printWriter.println(this.mInteractor.asBinder());
        printWriter.print(string2);
        printWriter.print("  mActivity=");
        printWriter.println(this.mActivity);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Request getActiveRequest(String string2) {
        if (this.isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return null;
        }
        ArrayMap<IBinder, Request> arrayMap = this.mActiveRequests;
        synchronized (arrayMap) {
            Request request;
            int n = this.mActiveRequests.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return null;
                }
                request = this.mActiveRequests.valueAt(n2);
                if (string2 == request.getName() || string2 != null && string2.equals(request.getName())) break;
                ++n2;
            } while (true);
            return request;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Request[] getActiveRequests() {
        if (this.isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return null;
        }
        ArrayMap<IBinder, Request> arrayMap = this.mActiveRequests;
        synchronized (arrayMap) {
            int n = this.mActiveRequests.size();
            if (n <= 0) {
                return NO_REQUESTS;
            }
            Request[] arrrequest = new Request[n];
            int n2 = 0;
            while (n2 < n) {
                arrrequest[n2] = this.mActiveRequests.valueAt(n2);
                ++n2;
            }
            return arrrequest;
        }
    }

    public boolean isDestroyed() {
        boolean bl = this.mInteractor == null;
        return bl;
    }

    public void notifyDirectActionsChanged() {
        if (this.isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return;
        }
        try {
            this.mInteractor.notifyDirectActionsChanged(this.mActivity.getTaskId(), this.mActivity.getAssistToken());
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Voice interactor has died", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Request pullRequest(IVoiceInteractorRequest iVoiceInteractorRequest, boolean bl) {
        ArrayMap<IBinder, Request> arrayMap = this.mActiveRequests;
        synchronized (arrayMap) {
            Request request = this.mActiveRequests.get(iVoiceInteractorRequest.asBinder());
            if (request != null && bl) {
                this.mActiveRequests.remove(iVoiceInteractorRequest.asBinder());
            }
            return request;
        }
    }

    public boolean registerOnDestroyedCallback(Executor executor, Runnable runnable) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(runnable);
        if (this.isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return false;
        }
        this.mOnDestroyCallbacks.put(runnable, executor);
        return true;
    }

    void retainInstance() {
        this.mRetaining = true;
    }

    public boolean submitRequest(Request request) {
        return this.submitRequest(request, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean submitRequest(Request var1_1, String var2_3) {
        if (this.isDestroyed()) {
            Log.w("VoiceInteractor", "Cannot interact with a destroyed voice interactor");
            return false;
        }
        try {
            if (var1_1.mRequestInterface != null) ** GOTO lbl-1000
            var1_1.mRequestInterface = var3_4 = var1_1.submit(this.mInteractor, this.mContext.getOpPackageName(), this.mCallback);
            var1_1.mContext = this.mContext;
            var1_1.mActivity = this.mActivity;
            var1_1.mName = var2_3;
            var2_3 = this.mActiveRequests;
            // MONITORENTER : var2_3
            this.mActiveRequests.put(var3_4.asBinder(), var1_1);
        }
        catch (RemoteException var1_2) {
            Log.w("VoiceInteractor", "Remove voice interactor service died", var1_2);
            return false;
        }
        // MONITOREXIT : var2_3
        return true;
lbl-1000: // 1 sources:
        {
            var2_3 = new ArrayMap<IBinder, Request>();
            var2_3.append("Given ");
            var2_3.append((Object)var1_1);
            var2_3.append(" is already active");
            var3_5 = new IllegalStateException(var2_3.toString());
            throw var3_5;
        }
    }

    public boolean[] supportsCommands(String[] arrobject) {
        if (this.isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return new boolean[arrobject.length];
        }
        try {
            arrobject = this.mInteractor.supportsCommands(this.mContext.getOpPackageName(), (String[])arrobject);
            return arrobject;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("Voice interactor has died", remoteException);
        }
    }

    public boolean unregisterOnDestroyedCallback(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        boolean bl = this.isDestroyed();
        boolean bl2 = false;
        if (bl) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return false;
        }
        if (this.mOnDestroyCallbacks.remove(runnable) != null) {
            bl2 = true;
        }
        return bl2;
    }

    public static class AbortVoiceRequest
    extends Request {
        final Bundle mExtras;
        final Prompt mPrompt;

        public AbortVoiceRequest(Prompt prompt, Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public AbortVoiceRequest(CharSequence object, Bundle bundle) {
            object = object != null ? new Prompt((CharSequence)object) : null;
            this.mPrompt = object;
            this.mExtras = bundle;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(string2);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override
        String getRequestTypeName() {
            return "AbortVoice";
        }

        public void onAbortResult(Bundle bundle) {
        }

        @Override
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String string2, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startAbortVoice(string2, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class CommandRequest
    extends Request {
        final Bundle mArgs;
        final String mCommand;

        public CommandRequest(String string2, Bundle bundle) {
            this.mCommand = string2;
            this.mArgs = bundle;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mCommand=");
            printWriter.println(this.mCommand);
            if (this.mArgs != null) {
                printWriter.print(string2);
                printWriter.print("mArgs=");
                printWriter.println(this.mArgs);
            }
        }

        @Override
        String getRequestTypeName() {
            return "Command";
        }

        public void onCommandResult(boolean bl, Bundle bundle) {
        }

        @Override
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String string2, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startCommand(string2, iVoiceInteractorCallback, this.mCommand, this.mArgs);
        }
    }

    public static class CompleteVoiceRequest
    extends Request {
        final Bundle mExtras;
        final Prompt mPrompt;

        public CompleteVoiceRequest(Prompt prompt, Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public CompleteVoiceRequest(CharSequence object, Bundle bundle) {
            object = object != null ? new Prompt((CharSequence)object) : null;
            this.mPrompt = object;
            this.mExtras = bundle;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(string2);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override
        String getRequestTypeName() {
            return "CompleteVoice";
        }

        public void onCompleteResult(Bundle bundle) {
        }

        @Override
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String string2, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startCompleteVoice(string2, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class ConfirmationRequest
    extends Request {
        final Bundle mExtras;
        final Prompt mPrompt;

        public ConfirmationRequest(Prompt prompt, Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public ConfirmationRequest(CharSequence object, Bundle bundle) {
            object = object != null ? new Prompt((CharSequence)object) : null;
            this.mPrompt = object;
            this.mExtras = bundle;
        }

        @Override
        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, fileDescriptor, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(string2);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override
        String getRequestTypeName() {
            return "Confirmation";
        }

        public void onConfirmationResult(boolean bl, Bundle bundle) {
        }

        @Override
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String string2, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startConfirmation(string2, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    private static final class KillCallback
    extends ICancellationSignal.Stub {
        private final WeakReference<VoiceInteractor> mInteractor;

        KillCallback(VoiceInteractor voiceInteractor) {
            this.mInteractor = new WeakReference<VoiceInteractor>(voiceInteractor);
        }

        @Override
        public void cancel() {
            VoiceInteractor voiceInteractor = (VoiceInteractor)this.mInteractor.get();
            if (voiceInteractor != null) {
                voiceInteractor.mHandlerCaller.getHandler().sendMessage(PooledLambda.obtainMessage(_$$Lambda$dUWXWbBHcaaVBn031EDBP91NR7k.INSTANCE, voiceInteractor));
            }
        }
    }

    public static class PickOptionRequest
    extends Request {
        final Bundle mExtras;
        final Option[] mOptions;
        final Prompt mPrompt;

        public PickOptionRequest(Prompt prompt, Option[] arroption, Bundle bundle) {
            this.mPrompt = prompt;
            this.mOptions = arroption;
            this.mExtras = bundle;
        }

        public PickOptionRequest(CharSequence object, Option[] arroption, Bundle bundle) {
            object = object != null ? new Prompt((CharSequence)object) : null;
            this.mPrompt = object;
            this.mOptions = arroption;
            this.mExtras = bundle;
        }

        @Override
        void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
            super.dump(string2, (FileDescriptor)object, printWriter, arrstring);
            printWriter.print(string2);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mOptions != null) {
                printWriter.print(string2);
                printWriter.println("Options:");
                for (int i = 0; i < ((Option[])(object = this.mOptions)).length; ++i) {
                    object = object[i];
                    printWriter.print(string2);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.println(":");
                    printWriter.print(string2);
                    printWriter.print("    mLabel=");
                    printWriter.println(object.mLabel);
                    printWriter.print(string2);
                    printWriter.print("    mIndex=");
                    printWriter.println(object.mIndex);
                    if (object.mSynonyms != null && object.mSynonyms.size() > 0) {
                        printWriter.print(string2);
                        printWriter.println("    Synonyms:");
                        for (int j = 0; j < object.mSynonyms.size(); ++j) {
                            printWriter.print(string2);
                            printWriter.print("      #");
                            printWriter.print(j);
                            printWriter.print(": ");
                            printWriter.println(object.mSynonyms.get(j));
                        }
                    }
                    if (object.mExtras == null) continue;
                    printWriter.print(string2);
                    printWriter.print("    mExtras=");
                    printWriter.println(object.mExtras);
                }
            }
            if (this.mExtras != null) {
                printWriter.print(string2);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override
        String getRequestTypeName() {
            return "PickOption";
        }

        public void onPickOptionResult(boolean bl, Option[] arroption, Bundle bundle) {
        }

        @Override
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String string2, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startPickOption(string2, iVoiceInteractorCallback, this.mPrompt, this.mOptions, this.mExtras);
        }

        public static final class Option
        implements Parcelable {
            public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>(){

                @Override
                public Option createFromParcel(Parcel parcel) {
                    return new Option(parcel);
                }

                public Option[] newArray(int n) {
                    return new Option[n];
                }
            };
            Bundle mExtras;
            final int mIndex;
            final CharSequence mLabel;
            ArrayList<CharSequence> mSynonyms;

            Option(Parcel parcel) {
                this.mLabel = parcel.readCharSequence();
                this.mIndex = parcel.readInt();
                this.mSynonyms = parcel.readCharSequenceList();
                this.mExtras = parcel.readBundle();
            }

            public Option(CharSequence charSequence) {
                this.mLabel = charSequence;
                this.mIndex = -1;
            }

            public Option(CharSequence charSequence, int n) {
                this.mLabel = charSequence;
                this.mIndex = n;
            }

            public Option addSynonym(CharSequence charSequence) {
                if (this.mSynonyms == null) {
                    this.mSynonyms = new ArrayList();
                }
                this.mSynonyms.add(charSequence);
                return this;
            }

            public int countSynonyms() {
                ArrayList<CharSequence> arrayList = this.mSynonyms;
                int n = arrayList != null ? arrayList.size() : 0;
                return n;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public int getIndex() {
                return this.mIndex;
            }

            public CharSequence getLabel() {
                return this.mLabel;
            }

            public CharSequence getSynonymAt(int n) {
                ArrayList<CharSequence> arrayList = this.mSynonyms;
                arrayList = arrayList != null ? arrayList.get(n) : null;
                return arrayList;
            }

            public void setExtras(Bundle bundle) {
                this.mExtras = bundle;
            }

            @Override
            public void writeToParcel(Parcel parcel, int n) {
                parcel.writeCharSequence(this.mLabel);
                parcel.writeInt(this.mIndex);
                parcel.writeCharSequenceList(this.mSynonyms);
                parcel.writeBundle(this.mExtras);
            }

        }

    }

    public static class Prompt
    implements Parcelable {
        public static final Parcelable.Creator<Prompt> CREATOR = new Parcelable.Creator<Prompt>(){

            @Override
            public Prompt createFromParcel(Parcel parcel) {
                return new Prompt(parcel);
            }

            public Prompt[] newArray(int n) {
                return new Prompt[n];
            }
        };
        private final CharSequence mVisualPrompt;
        private final CharSequence[] mVoicePrompts;

        Prompt(Parcel parcel) {
            this.mVoicePrompts = parcel.readCharSequenceArray();
            this.mVisualPrompt = parcel.readCharSequence();
        }

        public Prompt(CharSequence charSequence) {
            this.mVoicePrompts = new CharSequence[]{charSequence};
            this.mVisualPrompt = charSequence;
        }

        public Prompt(CharSequence[] arrcharSequence, CharSequence charSequence) {
            if (arrcharSequence != null) {
                if (arrcharSequence.length != 0) {
                    if (charSequence != null) {
                        this.mVoicePrompts = arrcharSequence;
                        this.mVisualPrompt = charSequence;
                        return;
                    }
                    throw new NullPointerException("visualPrompt must not be null");
                }
                throw new IllegalArgumentException("voicePrompts must not be empty");
            }
            throw new NullPointerException("voicePrompts must not be null");
        }

        public int countVoicePrompts() {
            return this.mVoicePrompts.length;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public CharSequence getVisualPrompt() {
            return this.mVisualPrompt;
        }

        public CharSequence getVoicePromptAt(int n) {
            return this.mVoicePrompts[n];
        }

        public String toString() {
            CharSequence[] arrcharSequence;
            StringBuilder stringBuilder = new StringBuilder(128);
            DebugUtils.buildShortClassTag(this, stringBuilder);
            CharSequence charSequence = this.mVisualPrompt;
            if (charSequence != null && (arrcharSequence = this.mVoicePrompts) != null && arrcharSequence.length == 1 && charSequence.equals(arrcharSequence[0])) {
                stringBuilder.append(" ");
                stringBuilder.append(this.mVisualPrompt);
            } else {
                if (this.mVisualPrompt != null) {
                    stringBuilder.append(" visual=");
                    stringBuilder.append(this.mVisualPrompt);
                }
                if (this.mVoicePrompts != null) {
                    stringBuilder.append(", voice=");
                    for (int i = 0; i < this.mVoicePrompts.length; ++i) {
                        if (i > 0) {
                            stringBuilder.append(" | ");
                        }
                        stringBuilder.append(this.mVoicePrompts[i]);
                    }
                }
            }
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeCharSequenceArray(this.mVoicePrompts);
            parcel.writeCharSequence(this.mVisualPrompt);
        }

    }

    public static abstract class Request {
        Activity mActivity;
        Context mContext;
        String mName;
        IVoiceInteractorRequest mRequestInterface;

        Request() {
        }

        public void cancel() {
            Object object = this.mRequestInterface;
            if (object != null) {
                try {
                    object.cancel();
                }
                catch (RemoteException remoteException) {
                    Log.w(VoiceInteractor.TAG, "Voice interactor has died", remoteException);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Request ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" is no longer active");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        void clear() {
            this.mRequestInterface = null;
            this.mContext = null;
            this.mActivity = null;
            this.mName = null;
        }

        void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            printWriter.print(string2);
            printWriter.print("mRequestInterface=");
            printWriter.println(this.mRequestInterface.asBinder());
            printWriter.print(string2);
            printWriter.print("mActivity=");
            printWriter.println(this.mActivity);
            printWriter.print(string2);
            printWriter.print("mName=");
            printWriter.println(this.mName);
        }

        public Activity getActivity() {
            return this.mActivity;
        }

        public Context getContext() {
            return this.mContext;
        }

        public String getName() {
            return this.mName;
        }

        String getRequestTypeName() {
            return "Request";
        }

        public void onAttached(Activity activity) {
        }

        public void onCancel() {
        }

        public void onDetached() {
        }

        abstract IVoiceInteractorRequest submit(IVoiceInteractor var1, String var2, IVoiceInteractorCallback var3) throws RemoteException;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            DebugUtils.buildShortClassTag(this, stringBuilder);
            stringBuilder.append(" ");
            stringBuilder.append(this.getRequestTypeName());
            stringBuilder.append(" name=");
            stringBuilder.append(this.mName);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

}

