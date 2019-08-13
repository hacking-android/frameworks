/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.app.ProfilerInfo;
import android.app.servertransaction.ClientTransaction;
import android.content.AutofillOptions;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IApplicationThread
extends IInterface {
    public void attachAgent(String var1) throws RemoteException;

    public void bindApplication(String var1, ApplicationInfo var2, List<ProviderInfo> var3, ComponentName var4, ProfilerInfo var5, Bundle var6, IInstrumentationWatcher var7, IUiAutomationConnection var8, int var9, boolean var10, boolean var11, boolean var12, boolean var13, Configuration var14, CompatibilityInfo var15, Map var16, Bundle var17, String var18, AutofillOptions var19, ContentCaptureOptions var20) throws RemoteException;

    public void clearDnsCache() throws RemoteException;

    public void dispatchPackageBroadcast(int var1, String[] var2) throws RemoteException;

    public void dumpActivity(ParcelFileDescriptor var1, IBinder var2, String var3, String[] var4) throws RemoteException;

    public void dumpDbInfo(ParcelFileDescriptor var1, String[] var2) throws RemoteException;

    public void dumpGfxInfo(ParcelFileDescriptor var1, String[] var2) throws RemoteException;

    public void dumpHeap(boolean var1, boolean var2, boolean var3, String var4, ParcelFileDescriptor var5, RemoteCallback var6) throws RemoteException;

    public void dumpMemInfo(ParcelFileDescriptor var1, Debug.MemoryInfo var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, String[] var8) throws RemoteException;

    public void dumpMemInfoProto(ParcelFileDescriptor var1, Debug.MemoryInfo var2, boolean var3, boolean var4, boolean var5, boolean var6, String[] var7) throws RemoteException;

    public void dumpProvider(ParcelFileDescriptor var1, IBinder var2, String[] var3) throws RemoteException;

    public void dumpService(ParcelFileDescriptor var1, IBinder var2, String[] var3) throws RemoteException;

    public void handleTrustStorageUpdate() throws RemoteException;

    public void notifyCleartextNetwork(byte[] var1) throws RemoteException;

    public void performDirectAction(IBinder var1, String var2, Bundle var3, RemoteCallback var4, RemoteCallback var5) throws RemoteException;

    public void processInBackground() throws RemoteException;

    public void profilerControl(boolean var1, ProfilerInfo var2, int var3) throws RemoteException;

    public void requestAssistContextExtras(IBinder var1, IBinder var2, int var3, int var4, int var5) throws RemoteException;

    public void requestDirectActions(IBinder var1, IVoiceInteractor var2, RemoteCallback var3, RemoteCallback var4) throws RemoteException;

    public void runIsolatedEntryPoint(String var1, String[] var2) throws RemoteException;

    public void scheduleApplicationInfoChanged(ApplicationInfo var1) throws RemoteException;

    @UnsupportedAppUsage
    public void scheduleBindService(IBinder var1, Intent var2, boolean var3, int var4) throws RemoteException;

    public void scheduleCrash(String var1) throws RemoteException;

    public void scheduleCreateBackupAgent(ApplicationInfo var1, CompatibilityInfo var2, int var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void scheduleCreateService(IBinder var1, ServiceInfo var2, CompatibilityInfo var3, int var4) throws RemoteException;

    public void scheduleDestroyBackupAgent(ApplicationInfo var1, CompatibilityInfo var2, int var3) throws RemoteException;

    public void scheduleEnterAnimationComplete(IBinder var1) throws RemoteException;

    public void scheduleExit() throws RemoteException;

    public void scheduleInstallProvider(ProviderInfo var1) throws RemoteException;

    public void scheduleLocalVoiceInteractionStarted(IBinder var1, IVoiceInteractor var2) throws RemoteException;

    public void scheduleLowMemory() throws RemoteException;

    public void scheduleOnNewActivityOptions(IBinder var1, Bundle var2) throws RemoteException;

    public void scheduleReceiver(Intent var1, ActivityInfo var2, CompatibilityInfo var3, int var4, String var5, Bundle var6, boolean var7, int var8, int var9) throws RemoteException;

    public void scheduleRegisteredReceiver(IIntentReceiver var1, Intent var2, int var3, String var4, Bundle var5, boolean var6, boolean var7, int var8, int var9) throws RemoteException;

    public void scheduleServiceArgs(IBinder var1, ParceledListSlice var2) throws RemoteException;

    public void scheduleSleeping(IBinder var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void scheduleStopService(IBinder var1) throws RemoteException;

    public void scheduleSuicide() throws RemoteException;

    public void scheduleTransaction(ClientTransaction var1) throws RemoteException;

    public void scheduleTranslucentConversionComplete(IBinder var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void scheduleTrimMemory(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void scheduleUnbindService(IBinder var1, Intent var2) throws RemoteException;

    public void setCoreSettings(Bundle var1) throws RemoteException;

    public void setNetworkBlockSeq(long var1) throws RemoteException;

    public void setProcessState(int var1) throws RemoteException;

    public void setSchedulingGroup(int var1) throws RemoteException;

    public void startBinderTracking() throws RemoteException;

    public void stopBinderTrackingAndDump(ParcelFileDescriptor var1) throws RemoteException;

    public void unstableProviderDied(IBinder var1) throws RemoteException;

    public void updateHttpProxy() throws RemoteException;

    public void updatePackageCompatibilityInfo(String var1, CompatibilityInfo var2) throws RemoteException;

    public void updateTimePrefs(int var1) throws RemoteException;

    public void updateTimeZone() throws RemoteException;

    public static class Default
    implements IApplicationThread {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void attachAgent(String string2) throws RemoteException {
        }

        @Override
        public void bindApplication(String string2, ApplicationInfo applicationInfo, List<ProviderInfo> list, ComponentName componentName, ProfilerInfo profilerInfo, Bundle bundle, IInstrumentationWatcher iInstrumentationWatcher, IUiAutomationConnection iUiAutomationConnection, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, Configuration configuration, CompatibilityInfo compatibilityInfo, Map map, Bundle bundle2, String string3, AutofillOptions autofillOptions, ContentCaptureOptions contentCaptureOptions) throws RemoteException {
        }

        @Override
        public void clearDnsCache() throws RemoteException {
        }

        @Override
        public void dispatchPackageBroadcast(int n, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String string2, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpHeap(boolean bl, boolean bl2, boolean bl3, String string2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] arrstring) throws RemoteException {
        }

        @Override
        public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] arrstring) throws RemoteException {
        }

        @Override
        public void handleTrustStorageUpdate() throws RemoteException {
        }

        @Override
        public void notifyCleartextNetwork(byte[] arrby) throws RemoteException {
        }

        @Override
        public void performDirectAction(IBinder iBinder, String string2, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override
        public void processInBackground() throws RemoteException {
        }

        @Override
        public void profilerControl(boolean bl, ProfilerInfo profilerInfo, int n) throws RemoteException {
        }

        @Override
        public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override
        public void runIsolatedEntryPoint(String string2, String[] arrstring) throws RemoteException {
        }

        @Override
        public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException {
        }

        @Override
        public void scheduleBindService(IBinder iBinder, Intent intent, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void scheduleCrash(String string2) throws RemoteException {
        }

        @Override
        public void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int n, int n2) throws RemoteException {
        }

        @Override
        public void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int n) throws RemoteException {
        }

        @Override
        public void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int n) throws RemoteException {
        }

        @Override
        public void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void scheduleExit() throws RemoteException {
        }

        @Override
        public void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException {
        }

        @Override
        public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
        }

        @Override
        public void scheduleLowMemory() throws RemoteException {
        }

        @Override
        public void scheduleOnNewActivityOptions(IBinder iBinder, Bundle bundle) throws RemoteException {
        }

        @Override
        public void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int n, String string2, Bundle bundle, boolean bl, int n2, int n3) throws RemoteException {
        }

        @Override
        public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2, int n3) throws RemoteException {
        }

        @Override
        public void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void scheduleSleeping(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void scheduleStopService(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void scheduleSuicide() throws RemoteException {
        }

        @Override
        public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
        }

        @Override
        public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean bl) throws RemoteException {
        }

        @Override
        public void scheduleTrimMemory(int n) throws RemoteException {
        }

        @Override
        public void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException {
        }

        @Override
        public void setCoreSettings(Bundle bundle) throws RemoteException {
        }

        @Override
        public void setNetworkBlockSeq(long l) throws RemoteException {
        }

        @Override
        public void setProcessState(int n) throws RemoteException {
        }

        @Override
        public void setSchedulingGroup(int n) throws RemoteException {
        }

        @Override
        public void startBinderTracking() throws RemoteException {
        }

        @Override
        public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override
        public void unstableProviderDied(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void updateHttpProxy() throws RemoteException {
        }

        @Override
        public void updatePackageCompatibilityInfo(String string2, CompatibilityInfo compatibilityInfo) throws RemoteException {
        }

        @Override
        public void updateTimePrefs(int n) throws RemoteException {
        }

        @Override
        public void updateTimeZone() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IApplicationThread {
        private static final String DESCRIPTOR = "android.app.IApplicationThread";
        static final int TRANSACTION_attachAgent = 48;
        static final int TRANSACTION_bindApplication = 4;
        static final int TRANSACTION_clearDnsCache = 26;
        static final int TRANSACTION_dispatchPackageBroadcast = 22;
        static final int TRANSACTION_dumpActivity = 25;
        static final int TRANSACTION_dumpDbInfo = 35;
        static final int TRANSACTION_dumpGfxInfo = 33;
        static final int TRANSACTION_dumpHeap = 24;
        static final int TRANSACTION_dumpMemInfo = 31;
        static final int TRANSACTION_dumpMemInfoProto = 32;
        static final int TRANSACTION_dumpProvider = 34;
        static final int TRANSACTION_dumpService = 12;
        static final int TRANSACTION_handleTrustStorageUpdate = 47;
        static final int TRANSACTION_notifyCleartextNetwork = 43;
        static final int TRANSACTION_performDirectAction = 53;
        static final int TRANSACTION_processInBackground = 9;
        static final int TRANSACTION_profilerControl = 16;
        static final int TRANSACTION_requestAssistContextExtras = 37;
        static final int TRANSACTION_requestDirectActions = 52;
        static final int TRANSACTION_runIsolatedEntryPoint = 5;
        static final int TRANSACTION_scheduleApplicationInfoChanged = 49;
        static final int TRANSACTION_scheduleBindService = 10;
        static final int TRANSACTION_scheduleCrash = 23;
        static final int TRANSACTION_scheduleCreateBackupAgent = 18;
        static final int TRANSACTION_scheduleCreateService = 2;
        static final int TRANSACTION_scheduleDestroyBackupAgent = 19;
        static final int TRANSACTION_scheduleEnterAnimationComplete = 42;
        static final int TRANSACTION_scheduleExit = 6;
        static final int TRANSACTION_scheduleInstallProvider = 40;
        static final int TRANSACTION_scheduleLocalVoiceInteractionStarted = 46;
        static final int TRANSACTION_scheduleLowMemory = 14;
        static final int TRANSACTION_scheduleOnNewActivityOptions = 20;
        static final int TRANSACTION_scheduleReceiver = 1;
        static final int TRANSACTION_scheduleRegisteredReceiver = 13;
        static final int TRANSACTION_scheduleServiceArgs = 7;
        static final int TRANSACTION_scheduleSleeping = 15;
        static final int TRANSACTION_scheduleStopService = 3;
        static final int TRANSACTION_scheduleSuicide = 21;
        static final int TRANSACTION_scheduleTransaction = 51;
        static final int TRANSACTION_scheduleTranslucentConversionComplete = 38;
        static final int TRANSACTION_scheduleTrimMemory = 30;
        static final int TRANSACTION_scheduleUnbindService = 11;
        static final int TRANSACTION_setCoreSettings = 28;
        static final int TRANSACTION_setNetworkBlockSeq = 50;
        static final int TRANSACTION_setProcessState = 39;
        static final int TRANSACTION_setSchedulingGroup = 17;
        static final int TRANSACTION_startBinderTracking = 44;
        static final int TRANSACTION_stopBinderTrackingAndDump = 45;
        static final int TRANSACTION_unstableProviderDied = 36;
        static final int TRANSACTION_updateHttpProxy = 27;
        static final int TRANSACTION_updatePackageCompatibilityInfo = 29;
        static final int TRANSACTION_updateTimePrefs = 41;
        static final int TRANSACTION_updateTimeZone = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IApplicationThread asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IApplicationThread) {
                return (IApplicationThread)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IApplicationThread getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 53: {
                    return "performDirectAction";
                }
                case 52: {
                    return "requestDirectActions";
                }
                case 51: {
                    return "scheduleTransaction";
                }
                case 50: {
                    return "setNetworkBlockSeq";
                }
                case 49: {
                    return "scheduleApplicationInfoChanged";
                }
                case 48: {
                    return "attachAgent";
                }
                case 47: {
                    return "handleTrustStorageUpdate";
                }
                case 46: {
                    return "scheduleLocalVoiceInteractionStarted";
                }
                case 45: {
                    return "stopBinderTrackingAndDump";
                }
                case 44: {
                    return "startBinderTracking";
                }
                case 43: {
                    return "notifyCleartextNetwork";
                }
                case 42: {
                    return "scheduleEnterAnimationComplete";
                }
                case 41: {
                    return "updateTimePrefs";
                }
                case 40: {
                    return "scheduleInstallProvider";
                }
                case 39: {
                    return "setProcessState";
                }
                case 38: {
                    return "scheduleTranslucentConversionComplete";
                }
                case 37: {
                    return "requestAssistContextExtras";
                }
                case 36: {
                    return "unstableProviderDied";
                }
                case 35: {
                    return "dumpDbInfo";
                }
                case 34: {
                    return "dumpProvider";
                }
                case 33: {
                    return "dumpGfxInfo";
                }
                case 32: {
                    return "dumpMemInfoProto";
                }
                case 31: {
                    return "dumpMemInfo";
                }
                case 30: {
                    return "scheduleTrimMemory";
                }
                case 29: {
                    return "updatePackageCompatibilityInfo";
                }
                case 28: {
                    return "setCoreSettings";
                }
                case 27: {
                    return "updateHttpProxy";
                }
                case 26: {
                    return "clearDnsCache";
                }
                case 25: {
                    return "dumpActivity";
                }
                case 24: {
                    return "dumpHeap";
                }
                case 23: {
                    return "scheduleCrash";
                }
                case 22: {
                    return "dispatchPackageBroadcast";
                }
                case 21: {
                    return "scheduleSuicide";
                }
                case 20: {
                    return "scheduleOnNewActivityOptions";
                }
                case 19: {
                    return "scheduleDestroyBackupAgent";
                }
                case 18: {
                    return "scheduleCreateBackupAgent";
                }
                case 17: {
                    return "setSchedulingGroup";
                }
                case 16: {
                    return "profilerControl";
                }
                case 15: {
                    return "scheduleSleeping";
                }
                case 14: {
                    return "scheduleLowMemory";
                }
                case 13: {
                    return "scheduleRegisteredReceiver";
                }
                case 12: {
                    return "dumpService";
                }
                case 11: {
                    return "scheduleUnbindService";
                }
                case 10: {
                    return "scheduleBindService";
                }
                case 9: {
                    return "processInBackground";
                }
                case 8: {
                    return "updateTimeZone";
                }
                case 7: {
                    return "scheduleServiceArgs";
                }
                case 6: {
                    return "scheduleExit";
                }
                case 5: {
                    return "runIsolatedEntryPoint";
                }
                case 4: {
                    return "bindApplication";
                }
                case 3: {
                    return "scheduleStopService";
                }
                case 2: {
                    return "scheduleCreateService";
                }
                case 1: 
            }
            return "scheduleReceiver";
        }

        public static boolean setDefaultImpl(IApplicationThread iApplicationThread) {
            if (Proxy.sDefaultImpl == null && iApplicationThread != null) {
                Proxy.sDefaultImpl = iApplicationThread;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel parcelable, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)((Object)parcelable), (Parcel)object, n2);
                    }
                    case 53: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)((Object)parcelable)).readStrongBinder();
                        String string2 = ((Parcel)((Object)parcelable)).readString();
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        RemoteCallback remoteCallback = ((Parcel)((Object)parcelable)).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.performDirectAction(iBinder, string2, (Bundle)object, remoteCallback, (RemoteCallback)parcelable);
                        return true;
                    }
                    case 52: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)((Object)parcelable)).readStrongBinder();
                        IVoiceInteractor iVoiceInteractor = IVoiceInteractor.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder());
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.requestDirectActions(iBinder, iVoiceInteractor, (RemoteCallback)object, (RemoteCallback)parcelable);
                        return true;
                    }
                    case 51: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? ClientTransaction.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleTransaction((ClientTransaction)parcelable);
                        return true;
                    }
                    case 50: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.setNetworkBlockSeq(((Parcel)((Object)parcelable)).readLong());
                        return true;
                    }
                    case 49: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleApplicationInfoChanged((ApplicationInfo)parcelable);
                        return true;
                    }
                    case 48: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.attachAgent(((Parcel)((Object)parcelable)).readString());
                        return true;
                    }
                    case 47: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.handleTrustStorageUpdate();
                        return true;
                    }
                    case 46: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleLocalVoiceInteractionStarted(((Parcel)((Object)parcelable)).readStrongBinder(), IVoiceInteractor.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder()));
                        return true;
                    }
                    case 45: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.stopBinderTrackingAndDump((ParcelFileDescriptor)parcelable);
                        return true;
                    }
                    case 44: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.startBinderTracking();
                        return true;
                    }
                    case 43: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.notifyCleartextNetwork(((Parcel)((Object)parcelable)).createByteArray());
                        return true;
                    }
                    case 42: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleEnterAnimationComplete(((Parcel)((Object)parcelable)).readStrongBinder());
                        return true;
                    }
                    case 41: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.updateTimePrefs(((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 40: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? ProviderInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleInstallProvider((ProviderInfo)parcelable);
                        return true;
                    }
                    case 39: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.setProcessState(((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 38: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readStrongBinder();
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl5 = true;
                        }
                        this.scheduleTranslucentConversionComplete((IBinder)object, bl5);
                        return true;
                    }
                    case 37: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.requestAssistContextExtras(((Parcel)((Object)parcelable)).readStrongBinder(), ((Parcel)((Object)parcelable)).readStrongBinder(), ((Parcel)((Object)parcelable)).readInt(), ((Parcel)((Object)parcelable)).readInt(), ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 36: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.unstableProviderDied(((Parcel)((Object)parcelable)).readStrongBinder());
                        return true;
                    }
                    case 35: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.dumpDbInfo((ParcelFileDescriptor)object, ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 34: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.dumpProvider((ParcelFileDescriptor)object, ((Parcel)((Object)parcelable)).readStrongBinder(), ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 33: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.dumpGfxInfo((ParcelFileDescriptor)object, ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 32: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        Debug.MemoryInfo memoryInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? Debug.MemoryInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        bl5 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl2 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl4 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        this.dumpMemInfoProto((ParcelFileDescriptor)object, memoryInfo, bl5, bl, bl2, bl4, ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 31: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        Debug.MemoryInfo memoryInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? Debug.MemoryInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        bl5 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl2 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl4 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl3 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        this.dumpMemInfo((ParcelFileDescriptor)object, memoryInfo, bl5, bl, bl2, bl4, bl3, ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 30: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleTrimMemory(((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 29: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readString();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.updatePackageCompatibilityInfo((String)object, (CompatibilityInfo)parcelable);
                        return true;
                    }
                    case 28: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.setCoreSettings((Bundle)parcelable);
                        return true;
                    }
                    case 27: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.updateHttpProxy();
                        return true;
                    }
                    case 26: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.clearDnsCache();
                        return true;
                    }
                    case 25: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.dumpActivity((ParcelFileDescriptor)object, ((Parcel)((Object)parcelable)).readStrongBinder(), ((Parcel)((Object)parcelable)).readString(), ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 24: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        bl5 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl2 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        String string3 = ((Parcel)((Object)parcelable)).readString();
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.dumpHeap(bl5, bl, bl2, string3, (ParcelFileDescriptor)object, (RemoteCallback)parcelable);
                        return true;
                    }
                    case 23: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleCrash(((Parcel)((Object)parcelable)).readString());
                        return true;
                    }
                    case 22: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.dispatchPackageBroadcast(((Parcel)((Object)parcelable)).readInt(), ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 21: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleSuicide();
                        return true;
                    }
                    case 20: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readStrongBinder();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleOnNewActivityOptions((IBinder)object, (Bundle)parcelable);
                        return true;
                    }
                    case 19: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        CompatibilityInfo compatibilityInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleDestroyBackupAgent((ApplicationInfo)object, compatibilityInfo, ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 18: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        CompatibilityInfo compatibilityInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleCreateBackupAgent((ApplicationInfo)object, compatibilityInfo, ((Parcel)((Object)parcelable)).readInt(), ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 17: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.setSchedulingGroup(((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 16: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        bl5 = bl;
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl5 = true;
                        }
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.profilerControl(bl5, (ProfilerInfo)object, ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 15: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readStrongBinder();
                        bl5 = bl2;
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl5 = true;
                        }
                        this.scheduleSleeping((IBinder)object, bl5);
                        return true;
                    }
                    case 14: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleLowMemory();
                        return true;
                    }
                    case 13: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        IIntentReceiver iIntentReceiver = IIntentReceiver.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder());
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        n = ((Parcel)((Object)parcelable)).readInt();
                        String string4 = ((Parcel)((Object)parcelable)).readString();
                        Bundle bundle = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        bl5 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl = ((Parcel)((Object)parcelable)).readInt() != 0;
                        this.scheduleRegisteredReceiver(iIntentReceiver, (Intent)object, n, string4, bundle, bl5, bl, ((Parcel)((Object)parcelable)).readInt(), ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 12: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.dumpService((ParcelFileDescriptor)object, ((Parcel)((Object)parcelable)).readStrongBinder(), ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 11: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readStrongBinder();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleUnbindService((IBinder)object, (Intent)parcelable);
                        return true;
                    }
                    case 10: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)((Object)parcelable)).readStrongBinder();
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        bl5 = bl3;
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl5 = true;
                        }
                        this.scheduleBindService(iBinder, (Intent)object, bl5, ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.processInBackground();
                        return true;
                    }
                    case 8: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.updateTimeZone();
                        return true;
                    }
                    case 7: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)((Object)parcelable)).readStrongBinder();
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleServiceArgs((IBinder)object, (ParceledListSlice)parcelable);
                        return true;
                    }
                    case 6: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleExit();
                        return true;
                    }
                    case 5: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.runIsolatedEntryPoint(((Parcel)((Object)parcelable)).readString(), ((Parcel)((Object)parcelable)).createStringArray());
                        return true;
                    }
                    case 4: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)((Object)parcelable)).readString();
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        ArrayList<ProviderInfo> arrayList = ((Parcel)((Object)parcelable)).createTypedArrayList(ProviderInfo.CREATOR);
                        ComponentName componentName = ((Parcel)((Object)parcelable)).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        ProfilerInfo profilerInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? ProfilerInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        Bundle bundle = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        IInstrumentationWatcher iInstrumentationWatcher = IInstrumentationWatcher.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder());
                        IUiAutomationConnection iUiAutomationConnection = IUiAutomationConnection.Stub.asInterface(((Parcel)((Object)parcelable)).readStrongBinder());
                        n = ((Parcel)((Object)parcelable)).readInt();
                        bl5 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl = ((Parcel)((Object)parcelable)).readInt() != 0;
                        bl2 = ((Parcel)((Object)parcelable)).readInt() != 0;
                        if (((Parcel)((Object)parcelable)).readInt() != 0) {
                            bl4 = true;
                        }
                        Configuration configuration = ((Parcel)((Object)parcelable)).readInt() != 0 ? Configuration.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        CompatibilityInfo compatibilityInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        HashMap hashMap = ((Parcel)((Object)parcelable)).readHashMap(this.getClass().getClassLoader());
                        Bundle bundle2 = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        String string6 = ((Parcel)((Object)parcelable)).readString();
                        AutofillOptions autofillOptions = ((Parcel)((Object)parcelable)).readInt() != 0 ? AutofillOptions.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        parcelable = ((Parcel)((Object)parcelable)).readInt() != 0 ? ContentCaptureOptions.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.bindApplication(string5, (ApplicationInfo)object, arrayList, componentName, profilerInfo, bundle, iInstrumentationWatcher, iUiAutomationConnection, n, bl5, bl, bl2, bl4, configuration, compatibilityInfo, hashMap, bundle2, string6, autofillOptions, (ContentCaptureOptions)parcelable);
                        return true;
                    }
                    case 3: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        this.scheduleStopService(((Parcel)((Object)parcelable)).readStrongBinder());
                        return true;
                    }
                    case 2: {
                        ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)((Object)parcelable)).readStrongBinder();
                        object = ((Parcel)((Object)parcelable)).readInt() != 0 ? ServiceInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        CompatibilityInfo compatibilityInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                        this.scheduleCreateService(iBinder, (ServiceInfo)object, compatibilityInfo, ((Parcel)((Object)parcelable)).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)((Object)parcelable)).enforceInterface(DESCRIPTOR);
                object = ((Parcel)((Object)parcelable)).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                ActivityInfo activityInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? ActivityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                CompatibilityInfo compatibilityInfo = ((Parcel)((Object)parcelable)).readInt() != 0 ? CompatibilityInfo.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                n = ((Parcel)((Object)parcelable)).readInt();
                String string7 = ((Parcel)((Object)parcelable)).readString();
                Bundle bundle = ((Parcel)((Object)parcelable)).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)((Object)parcelable)) : null;
                bl5 = ((Parcel)((Object)parcelable)).readInt() != 0;
                this.scheduleReceiver((Intent)object, activityInfo, compatibilityInfo, n, string7, bundle, bl5, ((Parcel)((Object)parcelable)).readInt(), ((Parcel)((Object)parcelable)).readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IApplicationThread {
            public static IApplicationThread sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void attachAgent(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(48, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attachAgent(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void bindApplication(String var1_1, ApplicationInfo var2_6, List<ProviderInfo> var3_7, ComponentName var4_8, ProfilerInfo var5_9, Bundle var6_10, IInstrumentationWatcher var7_11, IUiAutomationConnection var8_12, int var9_13, boolean var10_14, boolean var11_15, boolean var12_16, boolean var13_17, Configuration var14_18, CompatibilityInfo var15_19, Map var16_20, Bundle var17_21, String var18_22, AutofillOptions var19_23, ContentCaptureOptions var20_24) throws RemoteException {
                block43 : {
                    block44 : {
                        block46 : {
                            block42 : {
                                block41 : {
                                    block40 : {
                                        block45 : {
                                            block39 : {
                                                block38 : {
                                                    block37 : {
                                                        block36 : {
                                                            block35 : {
                                                                block34 : {
                                                                    block33 : {
                                                                        var21_25 = Parcel.obtain();
                                                                        var21_25.writeInterfaceToken("android.app.IApplicationThread");
                                                                        var21_25.writeString(var1_1);
                                                                        if (var2_6 == null) break block33;
                                                                        try {
                                                                            var21_25.writeInt(1);
                                                                            var2_6.writeToParcel(var21_25, 0);
                                                                            ** GOTO lbl15
                                                                        }
                                                                        catch (Throwable var1_2) {
                                                                            break block43;
                                                                        }
                                                                    }
                                                                    var21_25.writeInt(0);
lbl15: // 2 sources:
                                                                    var21_25.writeTypedList(var3_7);
                                                                    if (var4_8 == null) break block34;
                                                                    var21_25.writeInt(1);
                                                                    var4_8.writeToParcel(var21_25, 0);
                                                                    break block35;
                                                                }
                                                                var21_25.writeInt(0);
                                                            }
                                                            if (var5_9 != null) {
                                                                var21_25.writeInt(1);
                                                                var5_9.writeToParcel(var21_25, 0);
                                                            } else {
                                                                var21_25.writeInt(0);
                                                            }
                                                            if (var6_10 != null) {
                                                                var21_25.writeInt(1);
                                                                var6_10.writeToParcel(var21_25, 0);
                                                            } else {
                                                                var21_25.writeInt(0);
                                                            }
                                                            if (var7_11 != null) {
                                                                var22_26 = var7_11.asBinder();
                                                            } else {
                                                                var22_26 = null;
                                                            }
                                                            var21_25.writeStrongBinder((IBinder)var22_26);
                                                            if (var8_12 == null) break block36;
                                                            var22_26 = var8_12.asBinder();
                                                            break block37;
                                                        }
                                                        var22_26 = null;
                                                    }
                                                    var21_25.writeStrongBinder((IBinder)var22_26);
                                                    var21_25.writeInt(var9_13);
                                                    var23_27 = var10_14 != false ? 1 : 0;
                                                    var21_25.writeInt(var23_27);
                                                    var23_27 = var11_15 != false ? 1 : 0;
                                                    var21_25.writeInt(var23_27);
                                                    var23_27 = var12_16 != false ? 1 : 0;
                                                    var21_25.writeInt(var23_27);
                                                    var23_27 = var13_17 != false ? 1 : 0;
                                                    var21_25.writeInt(var23_27);
                                                    if (var14_18 == null) break block38;
                                                    var21_25.writeInt(1);
                                                    var14_18.writeToParcel(var21_25, 0);
                                                    break block39;
                                                }
                                                var21_25.writeInt(0);
                                            }
                                            if (var15_19 == null) break block45;
                                            var21_25.writeInt(1);
                                            var15_19.writeToParcel(var21_25, 0);
                                            ** GOTO lbl81
                                        }
                                        var21_25.writeInt(0);
lbl81: // 2 sources:
                                        var21_25.writeMap(var16_20);
                                        if (var17_21 == null) break block40;
                                        var21_25.writeInt(1);
                                        var17_21.writeToParcel(var21_25, 0);
                                        ** GOTO lbl90
                                    }
                                    var21_25.writeInt(0);
lbl90: // 2 sources:
                                    var21_25.writeString(var18_22);
                                    if (var19_23 == null) break block41;
                                    var21_25.writeInt(1);
                                    var19_23.writeToParcel(var21_25, 0);
                                    break block42;
                                }
                                var21_25.writeInt(0);
                            }
                            if (var20_24 == null) break block46;
                            var21_25.writeInt(1);
                            var20_24.writeToParcel(var21_25, 0);
                            ** GOTO lbl108
                        }
                        var21_25.writeInt(0);
lbl108: // 2 sources:
                        if (this.mRemote.transact(4, var21_25, null, 1) || Stub.getDefaultImpl() == null) break block44;
                        var22_26 = Stub.getDefaultImpl();
                        try {
                            var22_26.bindApplication(var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12, var9_13, var10_14, var11_15, var12_16, var13_17, var14_18, var15_19, var16_20, var17_21, var18_22, var19_23, var20_24);
                            var21_25.recycle();
                            return;
                        }
                        catch (Throwable var1_3) {}
                        break block43;
                    }
                    var21_25.recycle();
                    return;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var21_25.recycle();
                throw var1_5;
            }

            @Override
            public void clearDnsCache() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDnsCache();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchPackageBroadcast(int n, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchPackageBroadcast(n, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dumpActivity(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(25, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpActivity(parcelFileDescriptor, iBinder, string2, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dumpDbInfo(ParcelFileDescriptor parcelFileDescriptor, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(35, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpDbInfo(parcelFileDescriptor, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dumpGfxInfo(ParcelFileDescriptor parcelFileDescriptor, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(33, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpGfxInfo(parcelFileDescriptor, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void dumpHeap(boolean bl, boolean bl2, boolean bl3, String string2, ParcelFileDescriptor parcelFileDescriptor, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel;
                void var4_8;
                block11 : {
                    block10 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        int n = bl ? 1 : 0;
                        parcel.writeInt(n);
                        n = bl2 ? 1 : 0;
                        parcel.writeInt(n);
                        n = bl3 ? 1 : 0;
                        parcel.writeInt(n);
                        try {
                            parcel.writeString(string2);
                            if (parcelFileDescriptor != null) {
                                parcel.writeInt(1);
                                parcelFileDescriptor.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (remoteCallback != null) {
                                parcel.writeInt(1);
                                remoteCallback.writeToParcel(parcel, 0);
                                break block10;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dumpHeap(bl, bl2, bl3, string2, parcelFileDescriptor, remoteCallback);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var4_8;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void dumpMemInfo(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, String[] arrstring) throws RemoteException {
                Parcel parcel;
                void var1_5;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (memoryInfo != null) {
                        parcel.writeInt(1);
                        memoryInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl4 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = n;
                    if (bl5) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    try {
                        parcel.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        if (!this.mRemote.transact(31, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dumpMemInfo(parcelFileDescriptor, memoryInfo, bl, bl2, bl3, bl4, bl5, arrstring);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_5;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void dumpMemInfoProto(ParcelFileDescriptor parcelFileDescriptor, Debug.MemoryInfo memoryInfo, boolean bl, boolean bl2, boolean bl3, boolean bl4, String[] arrstring) throws RemoteException {
                Parcel parcel;
                void var1_5;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (memoryInfo != null) {
                        parcel.writeInt(1);
                        memoryInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl2 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = bl3 ? 1 : 0;
                    parcel.writeInt(n2);
                    n2 = n;
                    if (bl4) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    try {
                        parcel.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        if (!this.mRemote.transact(32, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dumpMemInfoProto(parcelFileDescriptor, memoryInfo, bl, bl2, bl3, bl4, arrstring);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public void dumpProvider(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(34, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpProvider(parcelFileDescriptor, iBinder, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void dumpService(ParcelFileDescriptor parcelFileDescriptor, IBinder iBinder, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpService(parcelFileDescriptor, iBinder, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void handleTrustStorageUpdate() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(47, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleTrustStorageUpdate();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCleartextNetwork(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(43, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCleartextNetwork(arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void performDirectAction(IBinder iBinder, String string2, Bundle bundle, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (remoteCallback2 != null) {
                        parcel.writeInt(1);
                        remoteCallback2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(53, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performDirectAction(iBinder, string2, bundle, remoteCallback, remoteCallback2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void processInBackground() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().processInBackground();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void profilerControl(boolean bl, ProfilerInfo profilerInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (profilerInfo != null) {
                        parcel.writeInt(1);
                        profilerInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().profilerControl(bl, profilerInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void requestAssistContextExtras(IBinder iBinder, IBinder iBinder2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(37, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestAssistContextExtras(iBinder, iBinder2, n, n2, n3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestDirectActions(IBinder iBinder, IVoiceInteractor iVoiceInteractor, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iVoiceInteractor != null ? iVoiceInteractor.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (remoteCallback2 != null) {
                        parcel.writeInt(1);
                        remoteCallback2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(52, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().requestDirectActions(iBinder, iVoiceInteractor, remoteCallback, remoteCallback2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void runIsolatedEntryPoint(String string2, String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().runIsolatedEntryPoint(string2, arrstring);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleApplicationInfoChanged(ApplicationInfo applicationInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (applicationInfo != null) {
                        parcel.writeInt(1);
                        applicationInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(49, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleApplicationInfoChanged(applicationInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void scheduleBindService(IBinder iBinder, Intent intent, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    int n2 = 0;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().scheduleBindService(iBinder, intent, bl, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleCrash(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleCrash(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleCreateBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (applicationInfo != null) {
                        parcel.writeInt(1);
                        applicationInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (compatibilityInfo != null) {
                        parcel.writeInt(1);
                        compatibilityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleCreateBackupAgent(applicationInfo, compatibilityInfo, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleCreateService(IBinder iBinder, ServiceInfo serviceInfo, CompatibilityInfo compatibilityInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (serviceInfo != null) {
                        parcel.writeInt(1);
                        serviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (compatibilityInfo != null) {
                        parcel.writeInt(1);
                        compatibilityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleCreateService(iBinder, serviceInfo, compatibilityInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleDestroyBackupAgent(ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (applicationInfo != null) {
                        parcel.writeInt(1);
                        applicationInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (compatibilityInfo != null) {
                        parcel.writeInt(1);
                        compatibilityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleDestroyBackupAgent(applicationInfo, compatibilityInfo, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleEnterAnimationComplete(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(42, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleEnterAnimationComplete(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleExit() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleExit();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleInstallProvider(ProviderInfo providerInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (providerInfo != null) {
                        parcel.writeInt(1);
                        providerInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(40, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleInstallProvider(providerInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void scheduleLocalVoiceInteractionStarted(IBinder iBinder, IVoiceInteractor iVoiceInteractor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iVoiceInteractor != null ? iVoiceInteractor.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (this.mRemote.transact(46, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().scheduleLocalVoiceInteractionStarted(iBinder, iVoiceInteractor);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleLowMemory() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleLowMemory();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleOnNewActivityOptions(IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleOnNewActivityOptions(iBinder, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void scheduleReceiver(Intent intent, ActivityInfo activityInfo, CompatibilityInfo compatibilityInfo, int n, String string2, Bundle bundle, boolean bl, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n4 = 0;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (activityInfo != null) {
                        parcel.writeInt(1);
                        activityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (compatibilityInfo != null) {
                        parcel.writeInt(1);
                        compatibilityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n4 = 1;
                    }
                    parcel.writeInt(n4);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().scheduleReceiver(intent, activityInfo, compatibilityInfo, n, string2, bundle, bl, n2, n3);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void scheduleRegisteredReceiver(IIntentReceiver iIntentReceiver, Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2, int n3) throws RemoteException {
                Parcel parcel;
                void var1_5;
                block12 : {
                    int n4;
                    block11 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        IBinder iBinder = iIntentReceiver != null ? iIntentReceiver.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                        n4 = 0;
                        if (intent != null) {
                            parcel.writeInt(1);
                            intent.writeToParcel(parcel, 0);
                            break block11;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeString(string2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        int n5 = bl ? 1 : 0;
                        parcel.writeInt(n5);
                        n5 = n4;
                        if (bl2) {
                            n5 = 1;
                        }
                        parcel.writeInt(n5);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().scheduleRegisteredReceiver(iIntentReceiver, intent, n, string2, bundle, bl, bl2, n2, n3);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public void scheduleServiceArgs(IBinder iBinder, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleServiceArgs(iBinder, parceledListSlice);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleSleeping(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleSleeping(iBinder, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleStopService(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleStopService(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleSuicide() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleSuicide();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleTransaction(ClientTransaction clientTransaction) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (clientTransaction != null) {
                        parcel.writeInt(1);
                        clientTransaction.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(51, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleTransaction(clientTransaction);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleTranslucentConversionComplete(IBinder iBinder, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleTranslucentConversionComplete(iBinder, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleTrimMemory(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleTrimMemory(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void scheduleUnbindService(IBinder iBinder, Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scheduleUnbindService(iBinder, intent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setCoreSettings(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCoreSettings(bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setNetworkBlockSeq(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(50, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkBlockSeq(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setProcessState(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProcessState(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setSchedulingGroup(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSchedulingGroup(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void startBinderTracking() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startBinderTracking();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopBinderTrackingAndDump(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(45, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopBinderTrackingAndDump(parcelFileDescriptor);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unstableProviderDied(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(36, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unstableProviderDied(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateHttpProxy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateHttpProxy();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updatePackageCompatibilityInfo(String string2, CompatibilityInfo compatibilityInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (compatibilityInfo != null) {
                        parcel.writeInt(1);
                        compatibilityInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePackageCompatibilityInfo(string2, compatibilityInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateTimePrefs(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateTimePrefs(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateTimeZone() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateTimeZone();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

