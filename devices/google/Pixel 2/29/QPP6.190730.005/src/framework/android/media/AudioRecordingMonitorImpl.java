/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.media.AudioRecordingMonitor;
import android.media.AudioRecordingMonitorClient;
import android.media.IAudioService;
import android.media.IRecordingConfigDispatcher;
import android.media._$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr__fiLO_SMYka7I;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class AudioRecordingMonitorImpl
implements AudioRecordingMonitor {
    private static final int MSG_RECORDING_CONFIG_CHANGE = 1;
    private static final String TAG = "android.media.AudioRecordingMonitor";
    private static IAudioService sService;
    private final AudioRecordingMonitorClient mClient;
    @GuardedBy(value={"mRecordCallbackLock"})
    private LinkedList<AudioRecordingCallbackInfo> mRecordCallbackList = new LinkedList();
    private final Object mRecordCallbackLock = new Object();
    @GuardedBy(value={"mRecordCallbackLock"})
    private final IRecordingConfigDispatcher mRecordingCallback = new IRecordingConfigDispatcher.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> object) {
            Parcelable parcelable = AudioRecordingMonitorImpl.this.getMyConfig((List<AudioRecordingConfiguration>)object);
            if (parcelable == null) return;
            object = AudioRecordingMonitorImpl.this.mRecordCallbackLock;
            synchronized (object) {
                if (AudioRecordingMonitorImpl.this.mRecordingCallbackHandler == null) return;
                parcelable = AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.obtainMessage(1, parcelable);
                AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.sendMessage((Message)parcelable);
                return;
            }
        }
    };
    @GuardedBy(value={"mRecordCallbackLock"})
    private volatile Handler mRecordingCallbackHandler;
    @GuardedBy(value={"mRecordCallbackLock"})
    private HandlerThread mRecordingCallbackHandlerThread;

    AudioRecordingMonitorImpl(AudioRecordingMonitorClient audioRecordingMonitorClient) {
        this.mClient = audioRecordingMonitorClient;
    }

    @GuardedBy(value={"mRecordCallbackLock"})
    private void beginRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread == null) {
            this.mRecordingCallbackHandlerThread = new HandlerThread("android.media.AudioRecordingMonitor.RecordingCallback");
            this.mRecordingCallbackHandlerThread.start();
            Object object = this.mRecordingCallbackHandlerThread.getLooper();
            if (object != null) {
                this.mRecordingCallbackHandler = new Handler((Looper)object){

                    static /* synthetic */ void lambda$handleMessage$0(AudioRecordingCallbackInfo audioRecordingCallbackInfo, ArrayList arrayList) {
                        audioRecordingCallbackInfo.mCb.onRecordingConfigChanged(arrayList);
                    }

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void handleMessage(Message object) {
                        Object object2;
                        if (((Message)object).what != 1) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown event ");
                            stringBuilder.append(((Message)object).what);
                            Log.e(AudioRecordingMonitorImpl.TAG, stringBuilder.toString());
                            return;
                        }
                        if (((Message)object).obj == null) {
                            return;
                        }
                        ArrayList<AudioRecordingConfiguration> arrayList = new ArrayList<AudioRecordingConfiguration>();
                        arrayList.add((AudioRecordingConfiguration)((Message)object).obj);
                        object = AudioRecordingMonitorImpl.this.mRecordCallbackLock;
                        synchronized (object) {
                            if (AudioRecordingMonitorImpl.this.mRecordCallbackList.size() == 0) {
                                return;
                            }
                            object2 = new LinkedList(AudioRecordingMonitorImpl.this.mRecordCallbackList);
                        }
                        long l = Binder.clearCallingIdentity();
                        try {
                            Iterator iterator = ((AbstractSequentialList)object2).iterator();
                            while (iterator.hasNext()) {
                                AudioRecordingCallbackInfo audioRecordingCallbackInfo = (AudioRecordingCallbackInfo)iterator.next();
                                object2 = audioRecordingCallbackInfo.mExecutor;
                                object = new _$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr__fiLO_SMYka7I(audioRecordingCallbackInfo, arrayList);
                                object2.execute((Runnable)object);
                            }
                            return;
                        }
                        finally {
                            Binder.restoreCallingIdentity(l);
                        }
                    }
                };
                object = AudioRecordingMonitorImpl.getService();
                try {
                    object.registerRecordingCallback(this.mRecordingCallback);
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
        }
    }

    @GuardedBy(value={"mRecordCallbackLock"})
    private void endRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread != null) {
            IAudioService iAudioService = AudioRecordingMonitorImpl.getService();
            try {
                iAudioService.unregisterRecordingCallback(this.mRecordingCallback);
                this.mRecordingCallbackHandlerThread.quit();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            this.mRecordingCallbackHandlerThread = null;
        }
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        return sService;
    }

    @Override
    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        Object object = AudioRecordingMonitorImpl.getService();
        try {
            object = this.getMyConfig(object.getActiveRecordingConfigurations());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    AudioRecordingConfiguration getMyConfig(List<AudioRecordingConfiguration> object) {
        int n = this.mClient.getPortId();
        object = object.iterator();
        while (object.hasNext()) {
            AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration)object.next();
            if (audioRecordingConfiguration.getClientPortId() != n) continue;
            return audioRecordingConfiguration;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerAudioRecordingCallback(Executor object, AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback");
        }
        if (object == null) {
            throw new IllegalArgumentException("Illegal null Executor");
        }
        Object object2 = this.mRecordCallbackLock;
        synchronized (object2) {
            Object object3 = this.mRecordCallbackList.iterator();
            do {
                if (object3.hasNext()) continue;
                this.beginRecordingCallbackHandling();
                object3 = this.mRecordCallbackList;
                AudioRecordingCallbackInfo audioRecordingCallbackInfo = new AudioRecordingCallbackInfo((Executor)object, audioRecordingCallback);
                ((LinkedList)object3).add(audioRecordingCallbackInfo);
                return;
            } while (((AudioRecordingCallbackInfo)object3.next()).mCb != audioRecordingCallback);
            object = new IllegalArgumentException("AudioRecordingCallback already registered");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback object) {
        if (object == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        Object object2 = this.mRecordCallbackLock;
        synchronized (object2) {
            AudioRecordingCallbackInfo audioRecordingCallbackInfo;
            Iterator iterator = this.mRecordCallbackList.iterator();
            do {
                if (!iterator.hasNext()) {
                    object = new IllegalArgumentException("AudioRecordingCallback was not registered");
                    throw object;
                }
                audioRecordingCallbackInfo = (AudioRecordingCallbackInfo)iterator.next();
            } while (audioRecordingCallbackInfo.mCb != object);
            this.mRecordCallbackList.remove(audioRecordingCallbackInfo);
            if (this.mRecordCallbackList.size() == 0) {
                this.endRecordingCallbackHandling();
            }
            return;
        }
    }

    private static class AudioRecordingCallbackInfo {
        final AudioManager.AudioRecordingCallback mCb;
        final Executor mExecutor;

        AudioRecordingCallbackInfo(Executor executor, AudioManager.AudioRecordingCallback audioRecordingCallback) {
            this.mExecutor = executor;
            this.mCb = audioRecordingCallback;
        }
    }

}

