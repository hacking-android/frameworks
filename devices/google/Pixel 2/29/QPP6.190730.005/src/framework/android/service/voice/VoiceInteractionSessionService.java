/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice.IVoiceInteractionSessionService;
import android.service.voice.VoiceInteractionSession;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class VoiceInteractionSessionService
extends Service {
    static final int MSG_NEW_SESSION = 1;
    HandlerCaller mHandlerCaller;
    final HandlerCaller.Callback mHandlerCallerCallback = new HandlerCaller.Callback(){

        @Override
        public void executeMessage(Message message) {
            SomeArgs someArgs = (SomeArgs)message.obj;
            if (message.what == 1) {
                VoiceInteractionSessionService.this.doNewSession((IBinder)someArgs.arg1, (Bundle)someArgs.arg2, someArgs.argi1);
            }
        }
    };
    IVoiceInteractionSessionService mInterface = new IVoiceInteractionSessionService.Stub(){

        @Override
        public void newSession(IBinder iBinder, Bundle bundle, int n) {
            VoiceInteractionSessionService.this.mHandlerCaller.sendMessage(VoiceInteractionSessionService.this.mHandlerCaller.obtainMessageIOO(1, n, iBinder, bundle));
        }
    };
    VoiceInteractionSession mSession;
    IVoiceInteractionManagerService mSystemService;

    void doNewSession(IBinder iBinder, Bundle bundle, int n) {
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.doDestroy();
            this.mSession = null;
        }
        this.mSession = this.onNewSession(bundle);
        try {
            this.mSystemService.deliverNewSession(iBinder, this.mSession.mSession, this.mSession.mInteractor);
            this.mSession.doCreate(this.mSystemService, iBinder);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        if (this.mSession == null) {
            printWriter.println("(no active session)");
        } else {
            printWriter.println("VoiceInteractionSession:");
            this.mSession.dump("  ", fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mInterface.asBinder();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.onConfigurationChanged(configuration);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mSystemService = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService("voiceinteraction"));
        this.mHandlerCaller = new HandlerCaller(this, Looper.myLooper(), this.mHandlerCallerCallback, true);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.onLowMemory();
        }
    }

    public abstract VoiceInteractionSession onNewSession(Bundle var1);

    @Override
    public void onTrimMemory(int n) {
        super.onTrimMemory(n);
        VoiceInteractionSession voiceInteractionSession = this.mSession;
        if (voiceInteractionSession != null) {
            voiceInteractionSession.onTrimMemory(n);
        }
    }

}

