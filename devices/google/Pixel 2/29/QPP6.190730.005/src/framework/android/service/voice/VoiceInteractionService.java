/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.voice.-$
 *  android.service.voice.-$$Lambda
 *  android.service.voice.-$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM
 *  android.service.voice.-$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY
 *  android.service.voice.-$$Lambda$VoiceInteractionService
 *  android.service.voice.-$$Lambda$VoiceInteractionService$1
 *  android.service.voice.-$$Lambda$VoiceInteractionService$1$ILMD_OnlN3EpU4AqKW9H-tgCoMg
 *  android.service.voice.-$$Lambda$VoiceInteractionService$1$WnZueQJxACwCZWfYsmNtGrcNbEc
 *  android.service.voice.-$$Lambda$VoiceInteractionService$1$gKwKkiuvnPnBCMXtKcZDpBR3098
 */
package android.service.voice;

import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.soundtrigger.KeyphraseEnrollmentInfo;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.voice.-$;
import android.service.voice.AlwaysOnHotwordDetector;
import android.service.voice.IVoiceInteractionService;
import android.service.voice._$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM;
import android.service.voice._$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY;
import android.service.voice._$$Lambda$VoiceInteractionService$1$ILMD_OnlN3EpU4AqKW9H_tgCoMg;
import android.service.voice._$$Lambda$VoiceInteractionService$1$WnZueQJxACwCZWfYsmNtGrcNbEc;
import android.service.voice._$$Lambda$VoiceInteractionService$1$gKwKkiuvnPnBCMXtKcZDpBR3098;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.IVoiceActionCheckCallback;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class VoiceInteractionService
extends Service {
    public static final String SERVICE_INTERFACE = "android.service.voice.VoiceInteractionService";
    public static final String SERVICE_META_DATA = "android.voice_interaction";
    private AlwaysOnHotwordDetector mHotwordDetector;
    IVoiceInteractionService mInterface = new IVoiceInteractionService.Stub(){

        static /* synthetic */ void lambda$getActiveServiceSupportedActions$2(Object object, List list, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            ((VoiceInteractionService)object).onHandleVoiceActionCheck(list, iVoiceActionCheckCallback);
        }

        static /* synthetic */ void lambda$shutdown$0(Object object) {
            ((VoiceInteractionService)object).onShutdownInternal();
        }

        static /* synthetic */ void lambda$soundModelsChanged$1(Object object) {
            ((VoiceInteractionService)object).onSoundModelsChangedInternal();
        }

        @Override
        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(_$$Lambda$VoiceInteractionService$1$gKwKkiuvnPnBCMXtKcZDpBR3098.INSTANCE, VoiceInteractionService.this, list, iVoiceActionCheckCallback));
        }

        @Override
        public void launchVoiceAssistFromKeyguard() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(_$$Lambda$2vcT7tC5Khx2oNbQI6Zvwrft_YM.INSTANCE, VoiceInteractionService.this));
        }

        @Override
        public void ready() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(_$$Lambda$SpnCJ0NiI1Uo14qQ5iHFyV2F2mY.INSTANCE, VoiceInteractionService.this));
        }

        @Override
        public void shutdown() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(_$$Lambda$VoiceInteractionService$1$ILMD_OnlN3EpU4AqKW9H_tgCoMg.INSTANCE, VoiceInteractionService.this));
        }

        @Override
        public void soundModelsChanged() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(_$$Lambda$VoiceInteractionService$1$WnZueQJxACwCZWfYsmNtGrcNbEc.INSTANCE, VoiceInteractionService.this));
        }
    };
    private KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    private final Object mLock = new Object();
    IVoiceInteractionManagerService mSystemService;

    public static boolean isActiveService(Context object, ComponentName componentName) {
        if ((object = Settings.Secure.getString(((Context)object).getContentResolver(), "voice_interaction_service")) != null && !((String)object).isEmpty()) {
            if ((object = ComponentName.unflattenFromString((String)object)) == null) {
                return false;
            }
            return ((ComponentName)object).equals(componentName);
        }
        return false;
    }

    private void onHandleVoiceActionCheck(List<String> arrayList, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        if (iVoiceActionCheckCallback != null) {
            try {
                Set<String> set = new Set<String>(arrayList);
                set = this.onGetSupportedVoiceActions(set);
                arrayList = new ArrayList<String>(set);
                iVoiceActionCheckCallback.onComplete(arrayList);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private void onShutdownInternal() {
        this.onShutdown();
        this.safelyShutdownHotwordDetector();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onSoundModelsChangedInternal() {
        synchronized (this) {
            if (this.mHotwordDetector != null) {
                this.mHotwordDetector.onSoundModelsChanged();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void safelyShutdownHotwordDetector() {
        block5 : {
            try {
                Object object = this.mLock;
                // MONITORENTER : object
                if (this.mHotwordDetector == null) break block5;
                this.mHotwordDetector.stopRecognition();
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.mHotwordDetector.invalidate();
            this.mHotwordDetector = null;
        }
        // MONITOREXIT : object
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(String string2, Locale locale, AlwaysOnHotwordDetector.Callback callback) {
        if (this.mSystemService != null) {
            Object object = this.mLock;
            synchronized (object) {
                AlwaysOnHotwordDetector alwaysOnHotwordDetector;
                this.safelyShutdownHotwordDetector();
                this.mHotwordDetector = alwaysOnHotwordDetector = new AlwaysOnHotwordDetector(string2, locale, callback, this.mKeyphraseEnrollmentInfo, this.mInterface, this.mSystemService);
                return this.mHotwordDetector;
            }
        }
        throw new IllegalStateException("Not available until onReady() is called");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("VOICE INTERACTION");
        object = this.mLock;
        synchronized (object) {
            printWriter.println("  AlwaysOnHotwordDetector");
            if (this.mHotwordDetector == null) {
                printWriter.println("    NULL");
            } else {
                this.mHotwordDetector.dump("    ", printWriter);
            }
            return;
        }
    }

    public int getDisabledShowContext() {
        try {
            int n = this.mSystemService.getDisabledShowContext();
            return n;
        }
        catch (RemoteException remoteException) {
            return 0;
        }
    }

    @VisibleForTesting
    protected final KeyphraseEnrollmentInfo getKeyphraseEnrollmentInfo() {
        return this.mKeyphraseEnrollmentInfo;
    }

    @UnsupportedAppUsage
    public final boolean isKeyphraseAndLocaleSupportedForHotword(String string2, Locale locale) {
        KeyphraseEnrollmentInfo keyphraseEnrollmentInfo = this.mKeyphraseEnrollmentInfo;
        boolean bl = false;
        if (keyphraseEnrollmentInfo == null) {
            return false;
        }
        if (keyphraseEnrollmentInfo.getKeyphraseMetadata(string2, locale) != null) {
            bl = true;
        }
        return bl;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        return null;
    }

    public Set<String> onGetSupportedVoiceActions(Set<String> set) {
        return Collections.emptySet();
    }

    public void onLaunchVoiceAssistFromKeyguard() {
    }

    public void onReady() {
        this.mSystemService = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService("voiceinteraction"));
        this.mKeyphraseEnrollmentInfo = new KeyphraseEnrollmentInfo(this.getPackageManager());
    }

    public void onShutdown() {
    }

    public void setDisabledShowContext(int n) {
        try {
            this.mSystemService.setDisabledShowContext(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void setUiHints(Bundle bundle) {
        if (bundle != null) {
            try {
                this.mSystemService.setUiHints(this.mInterface, bundle);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("Hints must be non-null");
    }

    public void showSession(Bundle bundle, int n) {
        IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mSystemService;
        if (iVoiceInteractionManagerService != null) {
            try {
                iVoiceInteractionManagerService.showSession(this.mInterface, bundle, n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        throw new IllegalStateException("Not available until onReady() is called");
    }

}

