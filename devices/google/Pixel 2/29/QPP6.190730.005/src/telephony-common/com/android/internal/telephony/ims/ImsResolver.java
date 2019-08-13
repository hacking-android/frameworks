/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.RemoteException
 *  android.os.UserHandle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.SubscriptionManager
 *  android.telephony.ims.aidl.IImsConfig
 *  android.telephony.ims.aidl.IImsMmTelFeature
 *  android.telephony.ims.aidl.IImsRcsFeature
 *  android.telephony.ims.aidl.IImsRegistration
 *  android.telephony.ims.stub.ImsFeatureConfiguration
 *  android.telephony.ims.stub.ImsFeatureConfiguration$FeatureSlotPair
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.SparseArray
 *  com.android.ims.internal.IImsServiceFeatureCallback
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.os.SomeArgs
 *  com.android.internal.telephony.ims.-$
 *  com.android.internal.telephony.ims.-$$Lambda
 *  com.android.internal.telephony.ims.-$$Lambda$ImsResolver
 *  com.android.internal.telephony.ims.-$$Lambda$ImsResolver$SIkPixr-qGLIK-usUJIKu6S5BBs
 *  com.android.internal.telephony.ims.-$$Lambda$WVd6ghNMbVDukmkxia3ZwNeZzEY
 *  com.android.internal.telephony.ims.-$$Lambda$WamP7BPq0j01TgYE3GvUqU3b-rs
 */
package com.android.internal.telephony.ims;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.android.ims.internal.IImsServiceFeatureCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import com.android.internal.telephony.ims.-$;
import com.android.internal.telephony.ims.ImsServiceController;
import com.android.internal.telephony.ims.ImsServiceControllerCompat;
import com.android.internal.telephony.ims.ImsServiceControllerStaticCompat;
import com.android.internal.telephony.ims.ImsServiceFeatureQueryManager;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$SIkPixr_qGLIK_usUJIKu6S5BBs;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$VfY5To_kbbTJevLzywTg__S1JhA;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$_jFhgP_NotuFSwzjQBXWuvls4x4;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$aWLlEvfonhYSfDR8cVsM6A5pmqI;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$kF808g2NWzNL8H1SwzDc1FxiQdQ;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$pNx4XUM9FmR6cV_MCAGiEt8F4pg;
import com.android.internal.telephony.ims._$$Lambda$ImsResolver$rPjfocpARQ2sab24iic4o3kTTgw;
import com.android.internal.telephony.ims._$$Lambda$WVd6ghNMbVDukmkxia3ZwNeZzEY;
import com.android.internal.telephony.ims._$$Lambda$WamP7BPq0j01TgYE3GvUqU3b_rs;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImsResolver
implements ImsServiceController.ImsServiceControllerCallbacks {
    private static final int DELAY_DYNAMIC_QUERY_MS = 5000;
    private static final int HANDLER_ADD_PACKAGE = 0;
    private static final int HANDLER_CONFIG_CHANGED = 2;
    private static final int HANDLER_DYNAMIC_FEATURE_CHANGE = 4;
    private static final int HANDLER_OVERRIDE_IMS_SERVICE_CONFIG = 5;
    private static final int HANDLER_REMOVE_PACKAGE = 1;
    private static final int HANDLER_START_DYNAMIC_FEATURE_QUERY = 3;
    public static final String METADATA_EMERGENCY_MMTEL_FEATURE = "android.telephony.ims.EMERGENCY_MMTEL_FEATURE";
    public static final String METADATA_MMTEL_FEATURE = "android.telephony.ims.MMTEL_FEATURE";
    private static final String METADATA_OVERRIDE_PERM_CHECK = "override_bind_check";
    public static final String METADATA_RCS_FEATURE = "android.telephony.ims.RCS_FEATURE";
    private static final String TAG = "ImsResolver";
    private Map<ComponentName, ImsServiceController> mActiveControllers = new HashMap<ComponentName, ImsServiceController>();
    private BroadcastReceiver mAppChangedReceiver = new BroadcastReceiver(){

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void onReceive(Context object, Intent object2) {
            int n;
            object = object2.getAction();
            String string = object2.getData().getSchemeSpecificPart();
            switch (((String)object).hashCode()) {
                default: {
                    return;
                }
                case 1544582882: {
                    if (!((String)object).equals("android.intent.action.PACKAGE_ADDED")) return;
                    n = 0;
                    break;
                }
                case 525384130: {
                    if (!((String)object).equals("android.intent.action.PACKAGE_REMOVED")) return;
                    n = 3;
                    break;
                }
                case 172491798: {
                    if (!((String)object).equals("android.intent.action.PACKAGE_CHANGED")) return;
                    n = 2;
                    break;
                }
                case -810471698: {
                    if (!((String)object).equals("android.intent.action.PACKAGE_REPLACED")) return;
                    n = 1;
                }
            }
            if (n != 0 && n != 1 && n != 2) {
                if (n != 3) {
                    return;
                }
                ImsResolver.this.mHandler.obtainMessage(1, (Object)string).sendToTarget();
                return;
            } else {
                ImsResolver.this.mHandler.obtainMessage(0, (Object)string).sendToTarget();
            }
        }
    };
    private List<SparseArray<ImsServiceController>> mBoundImsServicesByFeature;
    private final Object mBoundServicesLock = new Object();
    private final CarrierConfigManager mCarrierConfigManager;
    private String[] mCarrierServices;
    private BroadcastReceiver mConfigChangedReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            int n = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
            if (n == -1) {
                Log.i((String)ImsResolver.TAG, (String)"Received SIM change for invalid slot id.");
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Received Carrier Config Changed for SlotId: ");
            ((StringBuilder)object).append(n);
            Log.i((String)ImsResolver.TAG, (String)((StringBuilder)object).toString());
            ImsResolver.this.mHandler.obtainMessage(2, (Object)n).sendToTarget();
        }
    };
    private final Context mContext;
    private String mDeviceService;
    private ImsServiceFeatureQueryManager.Listener mDynamicQueryListener = new ImsServiceFeatureQueryManager.Listener(){

        @Override
        public void onComplete(ComponentName componentName, Set<ImsFeatureConfiguration.FeatureSlotPair> set) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onComplete called for name: ");
            stringBuilder.append((Object)componentName);
            stringBuilder.append("features:");
            stringBuilder.append(ImsResolver.this.printFeatures(set));
            Log.d((String)ImsResolver.TAG, (String)stringBuilder.toString());
            ImsResolver.this.handleFeaturesChanged(componentName, set);
        }

        @Override
        public void onError(ComponentName componentName) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onError: ");
            stringBuilder.append((Object)componentName);
            stringBuilder.append("returned with an error result");
            Log.w((String)ImsResolver.TAG, (String)stringBuilder.toString());
            ImsResolver.this.scheduleQueryForFeatures(componentName, 5000);
        }
    };
    private ImsDynamicQueryManagerFactory mDynamicQueryManagerFactory = _$$Lambda$WamP7BPq0j01TgYE3GvUqU3b_rs.INSTANCE;
    private ImsServiceFeatureQueryManager mFeatureQueryManager;
    private Handler mHandler = new Handler(Looper.getMainLooper(), (Handler.Callback)new _$$Lambda$ImsResolver$pNx4XUM9FmR6cV_MCAGiEt8F4pg(this));
    private ImsServiceControllerFactory mImsServiceControllerFactory = new ImsServiceControllerFactory(){

        @Override
        public ImsServiceController create(Context context, ComponentName componentName, ImsServiceController.ImsServiceControllerCallbacks imsServiceControllerCallbacks) {
            return new ImsServiceController(context, componentName, imsServiceControllerCallbacks);
        }

        @Override
        public String getServiceInterface() {
            return "android.telephony.ims.ImsService";
        }
    };
    private ImsServiceControllerFactory mImsServiceControllerFactoryCompat = new ImsServiceControllerFactory(){

        @Override
        public ImsServiceController create(Context context, ComponentName componentName, ImsServiceController.ImsServiceControllerCallbacks imsServiceControllerCallbacks) {
            return new ImsServiceControllerCompat(context, componentName, imsServiceControllerCallbacks);
        }

        @Override
        public String getServiceInterface() {
            return "android.telephony.ims.compat.ImsService";
        }
    };
    private ImsServiceControllerFactory mImsServiceControllerFactoryStaticBindingCompat = new ImsServiceControllerFactory(){

        @Override
        public ImsServiceController create(Context context, ComponentName componentName, ImsServiceController.ImsServiceControllerCallbacks imsServiceControllerCallbacks) {
            return new ImsServiceControllerStaticCompat(context, componentName, imsServiceControllerCallbacks);
        }

        @Override
        public String getServiceInterface() {
            return null;
        }
    };
    private Map<ComponentName, ImsServiceInfo> mInstalledServicesCache = new HashMap<ComponentName, ImsServiceInfo>();
    private final boolean mIsDynamicBinding;
    private final int mNumSlots;
    private final ComponentName mStaticComponent;
    private SubscriptionManagerProxy mSubscriptionManagerProxy = new SubscriptionManagerProxy(){

        @Override
        public int getSlotIndex(int n) {
            return SubscriptionManager.getSlotIndex((int)n);
        }

        @Override
        public int getSubId(int n) {
            int[] arrn = SubscriptionManager.getSubId((int)n);
            if (arrn != null) {
                return arrn[0];
            }
            return -1;
        }
    };

    public ImsResolver(Context context, String string, int n, boolean bl) {
        this.mContext = context;
        this.mDeviceService = string;
        this.mNumSlots = n;
        this.mIsDynamicBinding = bl;
        this.mStaticComponent = new ComponentName(this.mContext, ImsResolver.class);
        if (!this.mIsDynamicBinding) {
            Log.i((String)TAG, (String)"ImsResolver initialized with static binding.");
            this.mDeviceService = this.mStaticComponent.getPackageName();
        }
        this.mCarrierConfigManager = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
        this.mCarrierServices = new String[n];
        this.mBoundImsServicesByFeature = Stream.generate(_$$Lambda$WVd6ghNMbVDukmkxia3ZwNeZzEY.INSTANCE).limit(this.mNumSlots).collect(Collectors.toList());
        if (this.mIsDynamicBinding) {
            string = new IntentFilter();
            string.addAction("android.intent.action.PACKAGE_CHANGED");
            string.addAction("android.intent.action.PACKAGE_REMOVED");
            string.addAction("android.intent.action.PACKAGE_ADDED");
            string.addDataScheme("package");
            context.registerReceiverAsUser(this.mAppChangedReceiver, UserHandle.ALL, (IntentFilter)string, null, null);
            context.registerReceiver(this.mConfigChangedReceiver, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"));
        }
    }

    private void bindImsService(ImsServiceInfo imsServiceInfo) {
        if (imsServiceInfo == null) {
            return;
        }
        this.bindImsServiceWithFeatures(imsServiceInfo, this.calculateFeaturesToCreate(imsServiceInfo));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void bindImsServiceWithFeatures(ImsServiceInfo imsServiceInfo, HashSet<ImsFeatureConfiguration.FeatureSlotPair> object) {
        if (this.shouldFeaturesCauseBind((HashSet<ImsFeatureConfiguration.FeatureSlotPair>)object)) {
            ImsServiceController imsServiceController = this.getControllerByServiceInfo(this.mActiveControllers, imsServiceInfo);
            if (imsServiceController != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ImsService connection exists, updating features ");
                stringBuilder.append(object);
                Log.i((String)TAG, (String)stringBuilder.toString());
                try {
                    imsServiceController.changeImsServiceFeatures((HashSet<ImsFeatureConfiguration.FeatureSlotPair>)object);
                }
                catch (RemoteException remoteException) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("bindImsService: error=");
                    stringBuilder.append(remoteException.getMessage());
                    Log.w((String)TAG, (String)stringBuilder.toString());
                }
                object = imsServiceController;
            } else {
                imsServiceController = imsServiceInfo.controllerFactory.create(this.mContext, imsServiceInfo.name, this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Binding ImsService: ");
                stringBuilder.append((Object)imsServiceController.getComponentName());
                stringBuilder.append(" with features: ");
                stringBuilder.append(object);
                Log.i((String)TAG, (String)stringBuilder.toString());
                imsServiceController.bind((HashSet<ImsFeatureConfiguration.FeatureSlotPair>)object);
                object = imsServiceController;
            }
            this.mActiveControllers.put(imsServiceInfo.name, (ImsServiceController)object);
        }
    }

    private HashSet<ImsFeatureConfiguration.FeatureSlotPair> calculateFeaturesToCreate(ImsServiceInfo imsServiceInfo) {
        HashSet<ImsFeatureConfiguration.FeatureSlotPair> hashSet = new HashSet<ImsFeatureConfiguration.FeatureSlotPair>();
        int n = this.getSlotForActiveCarrierService(imsServiceInfo);
        if (n != -1) {
            hashSet.addAll(imsServiceInfo.getSupportedFeatures().stream().filter(new _$$Lambda$ImsResolver$_jFhgP_NotuFSwzjQBXWuvls4x4(n)).collect(Collectors.toList()));
        } else if (this.isDeviceService(imsServiceInfo)) {
            for (n = 0; n < this.mNumSlots; ++n) {
                ImsServiceInfo imsServiceInfo2 = this.getImsServiceInfoFromCache(this.mCarrierServices[n]);
                if (imsServiceInfo2 == null) {
                    hashSet.addAll(imsServiceInfo.getSupportedFeatures().stream().filter(new _$$Lambda$ImsResolver$VfY5To_kbbTJevLzywTg__S1JhA(n)).collect(Collectors.toList()));
                    continue;
                }
                HashSet<ImsFeatureConfiguration.FeatureSlotPair> hashSet2 = new HashSet<ImsFeatureConfiguration.FeatureSlotPair>(imsServiceInfo.getSupportedFeatures());
                hashSet2.removeAll(imsServiceInfo2.getSupportedFeatures());
                hashSet.addAll(hashSet2.stream().filter(new _$$Lambda$ImsResolver$kF808g2NWzNL8H1SwzDc1FxiQdQ(n)).collect(Collectors.toList()));
            }
        }
        return hashSet;
    }

    private void carrierConfigChanged(int n) {
        int n2 = this.mSubscriptionManagerProxy.getSubId(n);
        PersistableBundle persistableBundle = this.mCarrierConfigManager.getConfigForSubId(n2);
        if (persistableBundle != null) {
            this.maybeRebindService(n, persistableBundle.getString("config_ims_package_override_string", null));
        } else {
            Log.w((String)TAG, (String)"carrierConfigChanged: CarrierConfig is null!");
        }
    }

    private void dynamicQueryComplete(ComponentName componentName, Set<ImsFeatureConfiguration.FeatureSlotPair> object) {
        ImsServiceInfo imsServiceInfo = this.getImsServiceInfoFromCache(componentName.getPackageName());
        if (imsServiceInfo == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("handleFeaturesChanged: Couldn't find cached info for name: ");
            ((StringBuilder)object).append((Object)componentName);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        imsServiceInfo.replaceFeatures((Set<ImsFeatureConfiguration.FeatureSlotPair>)object);
        if (this.isActiveCarrierService(imsServiceInfo)) {
            this.bindImsService(imsServiceInfo);
            this.updateImsServiceFeatures(this.getImsServiceInfoFromCache(this.mDeviceService));
        } else if (this.isDeviceService(imsServiceInfo)) {
            this.bindImsService(imsServiceInfo);
        }
    }

    private ImsServiceController getControllerByServiceInfo(Map<ComponentName, ImsServiceController> map, ImsServiceInfo imsServiceInfo) {
        return map.values().stream().filter(new _$$Lambda$ImsResolver$aWLlEvfonhYSfDR8cVsM6A5pmqI(imsServiceInfo)).findFirst().orElse(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private SparseArray<ImsServiceController> getImsServiceControllers(int n) {
        if (n < 0) return null;
        if (n >= this.mNumSlots) {
            return null;
        }
        Object object = this.mBoundServicesLock;
        synchronized (object) {
            SparseArray<ImsServiceController> sparseArray = this.mBoundImsServicesByFeature.get(n);
            if (sparseArray != null) return sparseArray;
            return null;
        }
    }

    private List<ImsServiceInfo> getImsServiceInfo(String string) {
        ArrayList<ImsServiceInfo> arrayList = new ArrayList<ImsServiceInfo>();
        if (!this.mIsDynamicBinding) {
            arrayList.addAll(this.getStaticImsService());
        } else {
            arrayList.addAll(this.searchForImsServices(string, this.mImsServiceControllerFactory));
            arrayList.addAll(this.searchForImsServices(string, this.mImsServiceControllerFactoryCompat));
        }
        return arrayList;
    }

    private ImsServiceInfo getInfoByComponentName(Map<ComponentName, ImsServiceInfo> map, ComponentName componentName) {
        return map.get((Object)componentName);
    }

    private ImsServiceInfo getInfoByPackageName(Map<ComponentName, ImsServiceInfo> map, String string) {
        return map.values().stream().filter(new _$$Lambda$ImsResolver$rPjfocpARQ2sab24iic4o3kTTgw(string)).findFirst().orElse(null);
    }

    private int getSlotForActiveCarrierService(ImsServiceInfo imsServiceInfo) {
        for (int i = 0; i < this.mNumSlots; ++i) {
            if (!TextUtils.equals((CharSequence)this.mCarrierServices[i], (CharSequence)imsServiceInfo.name.getPackageName())) continue;
            return i;
        }
        return -1;
    }

    private List<ImsServiceInfo> getStaticImsService() {
        ArrayList<ImsServiceInfo> arrayList = new ArrayList<ImsServiceInfo>();
        ImsServiceInfo imsServiceInfo = new ImsServiceInfo(this.mNumSlots);
        imsServiceInfo.name = this.mStaticComponent;
        imsServiceInfo.controllerFactory = this.mImsServiceControllerFactoryStaticBindingCompat;
        imsServiceInfo.addFeatureForAllSlots(0);
        imsServiceInfo.addFeatureForAllSlots(1);
        arrayList.add(imsServiceInfo);
        return arrayList;
    }

    private void handleFeaturesChanged(ComponentName componentName, Set<ImsFeatureConfiguration.FeatureSlotPair> set) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = componentName;
        someArgs.arg2 = set;
        this.mHandler.obtainMessage(4, (Object)someArgs).sendToTarget();
    }

    private boolean isActiveCarrierService(ImsServiceInfo imsServiceInfo) {
        for (int i = 0; i < this.mNumSlots; ++i) {
            if (!TextUtils.equals((CharSequence)this.mCarrierServices[i], (CharSequence)imsServiceInfo.name.getPackageName())) continue;
            return true;
        }
        return false;
    }

    private boolean isDeviceService(ImsServiceInfo imsServiceInfo) {
        return TextUtils.equals((CharSequence)this.mDeviceService, (CharSequence)imsServiceInfo.name.getPackageName());
    }

    static /* synthetic */ boolean lambda$calculateFeaturesToCreate$3(int n, ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) {
        boolean bl = n == featureSlotPair.slotId;
        return bl;
    }

    static /* synthetic */ boolean lambda$calculateFeaturesToCreate$4(int n, ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) {
        boolean bl = n == featureSlotPair.slotId;
        return bl;
    }

    static /* synthetic */ boolean lambda$calculateFeaturesToCreate$5(int n, ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) {
        boolean bl = n == featureSlotPair.slotId;
        return bl;
    }

    static /* synthetic */ boolean lambda$getControllerByServiceInfo$1(ImsServiceInfo imsServiceInfo, ImsServiceController imsServiceController) {
        return Objects.equals((Object)imsServiceController.getComponentName(), (Object)imsServiceInfo.name);
    }

    static /* synthetic */ boolean lambda$getInfoByPackageName$2(String string, ImsServiceInfo imsServiceInfo) {
        return Objects.equals(imsServiceInfo.name.getPackageName(), string);
    }

    static /* synthetic */ boolean lambda$shouldFeaturesCauseBind$6(ImsFeatureConfiguration.FeatureSlotPair featureSlotPair) {
        boolean bl = featureSlotPair.featureType != 0;
        return bl;
    }

    private void maybeAddedImsService(String iterator) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("maybeAddedImsService, packageName: ");
        ((StringBuilder)object).append((String)((Object)iterator));
        Log.d((String)TAG, (String)((StringBuilder)object).toString());
        object = this.getImsServiceInfo((String)((Object)iterator));
        iterator = new ArrayList();
        Iterator iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            object = (ImsServiceInfo)iterator2.next();
            Object object2 = this.getInfoByComponentName(this.mInstalledServicesCache, ((ImsServiceInfo)object).name);
            if (object2 != null) {
                if (((ImsServiceInfo)object).featureFromMetadata) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Updating features in cached ImsService: ");
                    stringBuilder.append((Object)((ImsServiceInfo)object).name);
                    Log.i((String)TAG, (String)stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Updating features - Old features: ");
                    stringBuilder.append(object2);
                    stringBuilder.append(" new features: ");
                    stringBuilder.append(object);
                    Log.d((String)TAG, (String)stringBuilder.toString());
                    ((ImsServiceInfo)object2).replaceFeatures(((ImsServiceInfo)object).getSupportedFeatures());
                    this.updateImsServiceFeatures((ImsServiceInfo)object);
                    continue;
                }
                this.scheduleQueryForFeatures((ImsServiceInfo)object);
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Adding newly added ImsService to cache: ");
            ((StringBuilder)object2).append((Object)((ImsServiceInfo)object).name);
            Log.i((String)TAG, (String)((StringBuilder)object2).toString());
            this.mInstalledServicesCache.put(((ImsServiceInfo)object).name, (ImsServiceInfo)object);
            if (((ImsServiceInfo)object).featureFromMetadata) {
                iterator.add(object);
                continue;
            }
            this.scheduleQueryForFeatures((ImsServiceInfo)object);
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object = (ImsServiceInfo)iterator.next();
            if (this.isActiveCarrierService((ImsServiceInfo)object)) {
                this.bindImsService((ImsServiceInfo)object);
                this.updateImsServiceFeatures(this.getImsServiceInfoFromCache(this.mDeviceService));
                continue;
            }
            if (!this.isDeviceService((ImsServiceInfo)object)) continue;
            this.bindImsService((ImsServiceInfo)object);
        }
    }

    private void maybeRebindService(int n, String string) {
        if (n <= -1) {
            for (n = 0; n < this.mNumSlots; ++n) {
                this.updateBoundCarrierServices(n, string);
            }
        } else {
            this.updateBoundCarrierServices(n, string);
        }
    }

    private boolean maybeRemovedImsService(String object) {
        if ((object = this.getInfoByPackageName(this.mInstalledServicesCache, (String)object)) != null) {
            this.mInstalledServicesCache.remove((Object)((ImsServiceInfo)object).name);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Removing ImsService: ");
            stringBuilder.append((Object)((ImsServiceInfo)object).name);
            Log.i((String)TAG, (String)stringBuilder.toString());
            this.unbindImsService((ImsServiceInfo)object);
            this.updateImsServiceFeatures(this.getImsServiceInfoFromCache(this.mDeviceService));
            return true;
        }
        return false;
    }

    private String printFeatures(Set<ImsFeatureConfiguration.FeatureSlotPair> object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("features: [");
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                ImsFeatureConfiguration.FeatureSlotPair featureSlotPair = (ImsFeatureConfiguration.FeatureSlotPair)object.next();
                stringBuilder.append("{");
                stringBuilder.append(featureSlotPair.slotId);
                stringBuilder.append(",");
                stringBuilder.append(featureSlotPair.featureType);
                stringBuilder.append("} ");
            }
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void putImsController(int n, int n2, ImsServiceController object) {
        if (n >= 0 && n < this.mNumSlots && n2 > -1 && n2 < 3) {
            Object object2 = this.mBoundServicesLock;
            synchronized (object2) {
                Object object3 = this.mBoundImsServicesByFeature.get(n);
                SparseArray sparseArray = object3;
                if (object3 == null) {
                    sparseArray = new SparseArray();
                    this.mBoundImsServicesByFeature.add(n, (SparseArray<ImsServiceController>)sparseArray);
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("ImsServiceController added on slot: ");
                ((StringBuilder)object3).append(n);
                ((StringBuilder)object3).append(" with feature: ");
                ((StringBuilder)object3).append(n2);
                ((StringBuilder)object3).append(" using package: ");
                ((StringBuilder)object3).append((Object)((ImsServiceController)object).getComponentName());
                Log.i((String)TAG, (String)((StringBuilder)object3).toString());
                sparseArray.put(n2, object);
                return;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("putImsController received invalid parameters - slot: ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(", feature: ");
        ((StringBuilder)object).append(n2);
        Log.w((String)TAG, (String)((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ImsServiceController removeImsController(int n, int n2) {
        if (n >= 0 && n < this.mNumSlots && n2 > -1 && n2 < 3) {
            Object object = this.mBoundServicesLock;
            synchronized (object) {
                SparseArray<ImsServiceController> sparseArray = this.mBoundImsServicesByFeature.get(n);
                if (sparseArray == null) {
                    return null;
                }
                ImsServiceController imsServiceController = (ImsServiceController)sparseArray.get(n2, null);
                if (imsServiceController != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ImsServiceController removed on slot: ");
                    stringBuilder.append(n);
                    stringBuilder.append(" with feature: ");
                    stringBuilder.append(n2);
                    stringBuilder.append(" using package: ");
                    stringBuilder.append((Object)imsServiceController.getComponentName());
                    Log.i((String)TAG, (String)stringBuilder.toString());
                    sparseArray.remove(n2);
                }
                return imsServiceController;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("removeImsController received invalid parameters - slot: ");
        stringBuilder.append(n);
        stringBuilder.append(", feature: ");
        stringBuilder.append(n2);
        Log.w((String)TAG, (String)stringBuilder.toString());
        return null;
    }

    private void scheduleQueryForFeatures(ComponentName componentName, int n) {
        Object object = this.getImsServiceInfoFromCache(componentName.getPackageName());
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("scheduleQueryForFeatures: Couldn't find cached info for name: ");
            ((StringBuilder)object).append((Object)componentName);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        this.scheduleQueryForFeatures((ImsServiceInfo)object, n);
    }

    private void scheduleQueryForFeatures(ImsServiceInfo imsServiceInfo) {
        this.scheduleQueryForFeatures(imsServiceInfo, 0);
    }

    private void scheduleQueryForFeatures(ImsServiceInfo imsServiceInfo, int n) {
        if (!this.isDeviceService(imsServiceInfo) && this.getSlotForActiveCarrierService(imsServiceInfo) == -1) {
            Log.i((String)TAG, (String)"scheduleQueryForFeatures: skipping query for ImsService that is not set as carrier/device ImsService.");
            return;
        }
        Message message = Message.obtain((Handler)this.mHandler, (int)3, (Object)imsServiceInfo);
        if (this.mHandler.hasMessages(3, (Object)imsServiceInfo)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("scheduleQueryForFeatures: dynamic query for ");
            stringBuilder.append((Object)imsServiceInfo.name);
            stringBuilder.append(" already scheduled");
            Log.d((String)TAG, (String)stringBuilder.toString());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("scheduleQueryForFeatures: starting dynamic query for ");
        stringBuilder.append((Object)imsServiceInfo.name);
        stringBuilder.append(" in ");
        stringBuilder.append(n);
        stringBuilder.append("ms.");
        Log.d((String)TAG, (String)stringBuilder.toString());
        this.mHandler.sendMessageDelayed(message, (long)n);
    }

    private List<ImsServiceInfo> searchForImsServices(String object, ImsServiceControllerFactory imsServiceControllerFactory) {
        ArrayList<ImsServiceInfo> arrayList = new ArrayList<ImsServiceInfo>();
        Object object2 = new Intent(imsServiceControllerFactory.getServiceInterface());
        object2.setPackage((String)object);
        object2 = this.mContext.getPackageManager().queryIntentServicesAsUser((Intent)object2, 128, this.mContext.getUserId()).iterator();
        while (object2.hasNext()) {
            Object object3 = ((ResolveInfo)object2.next()).serviceInfo;
            if (object3 == null) continue;
            object = new ImsServiceInfo(this.mNumSlots);
            ((ImsServiceInfo)object).name = new ComponentName(((ServiceInfo)object3).packageName, ((ServiceInfo)object3).name);
            ((ImsServiceInfo)object).controllerFactory = imsServiceControllerFactory;
            if (!this.isDeviceService((ImsServiceInfo)object) && this.mImsServiceControllerFactoryCompat != imsServiceControllerFactory) {
                ((ImsServiceInfo)object).featureFromMetadata = false;
            } else {
                if (((ServiceInfo)object3).metaData != null) {
                    if (((ServiceInfo)object3).metaData.getBoolean(METADATA_EMERGENCY_MMTEL_FEATURE, false)) {
                        ((ImsServiceInfo)object).addFeatureForAllSlots(0);
                    }
                    if (((ServiceInfo)object3).metaData.getBoolean(METADATA_MMTEL_FEATURE, false)) {
                        ((ImsServiceInfo)object).addFeatureForAllSlots(1);
                    }
                    if (((ServiceInfo)object3).metaData.getBoolean(METADATA_RCS_FEATURE, false)) {
                        ((ImsServiceInfo)object).addFeatureForAllSlots(2);
                    }
                }
                if (this.mImsServiceControllerFactoryCompat != imsServiceControllerFactory && ((ImsServiceInfo)object).getSupportedFeatures().isEmpty()) {
                    ((ImsServiceInfo)object).featureFromMetadata = false;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service name: ");
            stringBuilder.append((Object)((ImsServiceInfo)object).name);
            stringBuilder.append(", manifest query: ");
            stringBuilder.append(((ImsServiceInfo)object).featureFromMetadata);
            Log.i((String)TAG, (String)stringBuilder.toString());
            if (!TextUtils.equals((CharSequence)((ServiceInfo)object3).permission, (CharSequence)"android.permission.BIND_IMS_SERVICE") && !((ServiceInfo)object3).metaData.getBoolean(METADATA_OVERRIDE_PERM_CHECK, false)) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("ImsService is not protected with BIND_IMS_SERVICE permission: ");
                ((StringBuilder)object3).append((Object)((ImsServiceInfo)object).name);
                Log.w((String)TAG, (String)((StringBuilder)object3).toString());
                continue;
            }
            arrayList.add((ImsServiceInfo)object);
        }
        return arrayList;
    }

    private boolean shouldFeaturesCauseBind(HashSet<ImsFeatureConfiguration.FeatureSlotPair> hashSet) {
        boolean bl = hashSet.stream().filter(_$$Lambda$ImsResolver$SIkPixr_qGLIK_usUJIKu6S5BBs.INSTANCE).count() > 0L;
        return bl;
    }

    private void startDynamicQuery(ImsServiceInfo imsServiceInfo) {
        if (!this.mFeatureQueryManager.startQuery(imsServiceInfo.name, imsServiceInfo.controllerFactory.getServiceInterface())) {
            Log.w((String)TAG, (String)"startDynamicQuery: service could not connect. Retrying after delay.");
            this.scheduleQueryForFeatures(imsServiceInfo, 5000);
        } else {
            Log.d((String)TAG, (String)"startDynamicQuery: Service queried, waiting for response.");
        }
    }

    private void unbindImsService(ImsServiceInfo imsServiceInfo) {
        if (imsServiceInfo == null) {
            return;
        }
        ImsServiceController imsServiceController = this.getControllerByServiceInfo(this.mActiveControllers, imsServiceInfo);
        if (imsServiceController != null) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unbinding ImsService: ");
                stringBuilder.append((Object)imsServiceController.getComponentName());
                Log.i((String)TAG, (String)stringBuilder.toString());
                imsServiceController.unbind();
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unbindImsService: Remote Exception: ");
                stringBuilder.append(remoteException.getMessage());
                Log.e((String)TAG, (String)stringBuilder.toString());
            }
            this.mActiveControllers.remove((Object)imsServiceInfo.name);
        }
    }

    private void updateBoundCarrierServices(int n, String object) {
        if (n > -1 && n < this.mNumSlots) {
            String[] arrstring = this.mCarrierServices;
            String string = arrstring[n];
            arrstring[n] = object;
            if (!TextUtils.equals((CharSequence)object, (CharSequence)string)) {
                Log.i((String)TAG, (String)"Carrier Config updated, binding new ImsService");
                this.unbindImsService(this.getImsServiceInfoFromCache(string));
                object = this.getImsServiceInfoFromCache((String)object);
                if (object != null && !((ImsServiceInfo)object).featureFromMetadata) {
                    this.scheduleQueryForFeatures((ImsServiceInfo)object);
                } else {
                    this.bindImsService((ImsServiceInfo)object);
                    this.updateImsServiceFeatures(this.getImsServiceInfoFromCache(this.mDeviceService));
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void updateImsServiceFeatures(ImsServiceInfo var1_1) {
        if (var1_1 == null) {
            return;
        }
        var2_3 = this.getControllerByServiceInfo(this.mActiveControllers, var1_1);
        var3_4 = this.calculateFeaturesToCreate(var1_1);
        if (!this.shouldFeaturesCauseBind((HashSet<ImsFeatureConfiguration.FeatureSlotPair>)var3_4)) {
            if (var2_3 == null) return;
            var3_4 = new StringBuilder();
            var3_4.append("Unbinding: features = 0 for ImsService: ");
            var3_4.append((Object)var2_3.getComponentName());
            Log.i((String)"ImsResolver", (String)var3_4.toString());
            this.unbindImsService(var1_1);
            return;
        }
        if (var2_3 == null) ** GOTO lbl34
        try {
            block4 : {
                var4_5 = new StringBuilder();
                var4_5.append("Updating features for ImsService: ");
                var4_5.append((Object)var2_3.getComponentName());
                Log.i((String)"ImsResolver", (String)var4_5.toString());
                var4_5 = new StringBuilder();
                var4_5.append("Updating Features - New Features: ");
                var4_5.append(var3_4);
                Log.d((String)"ImsResolver", (String)var4_5.toString());
                var2_3.changeImsServiceFeatures((HashSet<ImsFeatureConfiguration.FeatureSlotPair>)var3_4);
                break block4;
lbl34: // 1 sources:
                Log.i((String)"ImsResolver", (String)"updateImsServiceFeatures: unbound with active features, rebinding");
                this.bindImsServiceWithFeatures(var1_1, (HashSet<ImsFeatureConfiguration.FeatureSlotPair>)var3_4);
            }
            if (this.isActiveCarrierService(var1_1) == false) return;
            if (TextUtils.equals((CharSequence)var1_1.name.getPackageName(), (CharSequence)this.mDeviceService) != false) return;
            Log.i((String)"ImsResolver", (String)"Updating device default");
            this.updateImsServiceFeatures(this.getImsServiceInfoFromCache(this.mDeviceService));
            return;
        }
        catch (RemoteException var1_2) {
            var2_3 = new StringBuilder();
            var2_3.append("updateImsServiceFeatures: Remote Exception: ");
            var2_3.append(var1_2.getMessage());
            Log.e((String)"ImsResolver", (String)var2_3.toString());
            return;
        }
    }

    public void disableIms(int n) {
        SparseArray<ImsServiceController> sparseArray = this.getImsServiceControllers(n);
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); ++i) {
                ((ImsServiceController)sparseArray.get(sparseArray.keyAt(i))).disableIms(n);
            }
        }
    }

    public void enableIms(int n) {
        SparseArray<ImsServiceController> sparseArray = this.getImsServiceControllers(n);
        if (sparseArray != null) {
            for (int i = 0; i < sparseArray.size(); ++i) {
                ((ImsServiceController)sparseArray.get(sparseArray.keyAt(i))).enableIms(n);
            }
        }
    }

    @VisibleForTesting
    public Handler getHandler() {
        return this.mHandler;
    }

    public IImsConfig getImsConfig(int n, int n2) throws RemoteException {
        ImsServiceController imsServiceController = this.getImsServiceController(n, n2);
        if (imsServiceController != null) {
            return imsServiceController.getConfig(n);
        }
        return null;
    }

    public IImsRegistration getImsRegistration(int n, int n2) throws RemoteException {
        ImsServiceController imsServiceController = this.getImsServiceController(n, n2);
        if (imsServiceController != null) {
            return imsServiceController.getRegistration(n);
        }
        return null;
    }

    public String getImsServiceConfiguration(int n, boolean bl) {
        if (n >= 0 && n < this.mNumSlots) {
            String string = bl ? this.mCarrierServices[n] : this.mDeviceService;
            return string;
        }
        Log.w((String)TAG, (String)"getImsServiceConfiguration: invalid slotId!");
        return "";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public ImsServiceController getImsServiceController(int n, int n2) {
        if (n < 0) return null;
        if (n >= this.mNumSlots) {
            return null;
        }
        Object object = this.mBoundServicesLock;
        synchronized (object) {
            Object object2 = this.mBoundImsServicesByFeature.get(n);
            if (object2 != null) return (ImsServiceController)object2.get(n2);
            return null;
        }
    }

    @VisibleForTesting
    public ImsServiceController getImsServiceControllerAndListen(int n, int n2, IImsServiceFeatureCallback iImsServiceFeatureCallback) {
        ImsServiceController imsServiceController = this.getImsServiceController(n, n2);
        if (imsServiceController != null) {
            imsServiceController.addImsServiceFeatureCallback(iImsServiceFeatureCallback);
            return imsServiceController;
        }
        return null;
    }

    @VisibleForTesting
    public ImsServiceInfo getImsServiceInfoFromCache(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if ((object = this.getInfoByPackageName(this.mInstalledServicesCache, (String)object)) != null) {
            return object;
        }
        return null;
    }

    public IImsMmTelFeature getMmTelFeatureAndListen(int n, IImsServiceFeatureCallback object) {
        object = (object = this.getImsServiceControllerAndListen(n, 1, (IImsServiceFeatureCallback)object)) != null ? ((ImsServiceController)object).getMmTelFeature(n) : null;
        return object;
    }

    public IImsRcsFeature getRcsFeatureAndListen(int n, IImsServiceFeatureCallback object) {
        object = (object = this.getImsServiceControllerAndListen(n, 2, (IImsServiceFeatureCallback)object)) != null ? ((ImsServiceController)object).getRcsFeature(n) : null;
        return object;
    }

    @Override
    public void imsServiceFeatureCreated(int n, int n2, ImsServiceController imsServiceController) {
        this.putImsController(n, n2, imsServiceController);
    }

    @Override
    public void imsServiceFeatureRemoved(int n, int n2, ImsServiceController imsServiceController) {
        this.removeImsController(n, n2);
    }

    @Override
    public void imsServiceFeaturesChanged(ImsFeatureConfiguration imsFeatureConfiguration, ImsServiceController imsServiceController) {
        if (imsServiceController != null && imsFeatureConfiguration != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("imsServiceFeaturesChanged: config=");
            stringBuilder.append(imsFeatureConfiguration.getServiceFeatures());
            stringBuilder.append(", ComponentName=");
            stringBuilder.append((Object)imsServiceController.getComponentName());
            Log.i((String)TAG, (String)stringBuilder.toString());
            this.handleFeaturesChanged(imsServiceController.getComponentName(), imsFeatureConfiguration.getServiceFeatures());
            return;
        }
    }

    public void initPopulateCacheAndStartBind() {
        Log.i((String)TAG, (String)"Initializing cache and binding.");
        this.mFeatureQueryManager = this.mDynamicQueryManagerFactory.create(this.mContext, this.mDynamicQueryListener);
        this.mHandler.obtainMessage(2, (Object)-1).sendToTarget();
        this.mHandler.obtainMessage(0, null).sendToTarget();
    }

    public /* synthetic */ boolean lambda$new$0$ImsResolver(Message object) {
        int n = object.what;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            boolean bl = false;
                            if (n != 5) {
                                return false;
                            }
                            n = object.arg1;
                            if (object.arg2 == 1) {
                                bl = true;
                            }
                            object = (String)object.obj;
                            if (bl) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("overriding carrier ImsService - slot=");
                                stringBuilder.append(n);
                                stringBuilder.append(" packageName=");
                                stringBuilder.append((String)object);
                                Log.i((String)TAG, (String)stringBuilder.toString());
                                this.maybeRebindService(n, (String)object);
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("overriding device ImsService -  packageName=");
                                stringBuilder.append((String)object);
                                Log.i((String)TAG, (String)stringBuilder.toString());
                                if (!TextUtils.equals((CharSequence)this.mDeviceService, (CharSequence)object)) {
                                    this.unbindImsService(this.getImsServiceInfoFromCache(this.mDeviceService));
                                    this.mDeviceService = object;
                                    object = this.getImsServiceInfoFromCache(this.mDeviceService);
                                    if (object != null) {
                                        if (object.featureFromMetadata) {
                                            this.bindImsService((ImsServiceInfo)object);
                                        } else {
                                            this.scheduleQueryForFeatures((ImsServiceInfo)object);
                                        }
                                    }
                                }
                            }
                        } else {
                            object = (SomeArgs)object.obj;
                            ComponentName componentName = (ComponentName)object.arg1;
                            Set set = (Set)object.arg2;
                            object.recycle();
                            this.dynamicQueryComplete(componentName, set);
                        }
                    } else {
                        this.startDynamicQuery((ImsServiceInfo)object.obj);
                    }
                } else {
                    this.carrierConfigChanged((Integer)object.obj);
                }
            } else {
                this.maybeRemovedImsService((String)object.obj);
            }
        } else {
            this.maybeAddedImsService((String)object.obj);
        }
        return true;
    }

    public boolean overrideImsServiceConfiguration(int n, boolean bl, String string) {
        if (n >= 0 && n < this.mNumSlots) {
            if (string == null) {
                Log.w((String)TAG, (String)"overrideImsServiceConfiguration: null packageName!");
                return false;
            }
            Message.obtain((Handler)this.mHandler, (int)5, (int)n, (int)bl, (Object)string).sendToTarget();
            return true;
        }
        Log.w((String)TAG, (String)"overrideImsServiceConfiguration: invalid slotId!");
        return false;
    }

    @VisibleForTesting
    public void setImsDynamicQueryManagerFactory(ImsDynamicQueryManagerFactory imsDynamicQueryManagerFactory) {
        this.mDynamicQueryManagerFactory = imsDynamicQueryManagerFactory;
    }

    @VisibleForTesting
    public void setImsServiceControllerFactory(ImsServiceControllerFactory imsServiceControllerFactory) {
        this.mImsServiceControllerFactory = imsServiceControllerFactory;
    }

    @VisibleForTesting
    public void setSubscriptionManagerProxy(SubscriptionManagerProxy subscriptionManagerProxy) {
        this.mSubscriptionManagerProxy = subscriptionManagerProxy;
    }

    @VisibleForTesting
    public static interface ImsDynamicQueryManagerFactory {
        public ImsServiceFeatureQueryManager create(Context var1, ImsServiceFeatureQueryManager.Listener var2);
    }

    @VisibleForTesting
    public static interface ImsServiceControllerFactory {
        public ImsServiceController create(Context var1, ComponentName var2, ImsServiceController.ImsServiceControllerCallbacks var3);

        public String getServiceInterface();
    }

    @VisibleForTesting
    public static class ImsServiceInfo {
        public ImsServiceControllerFactory controllerFactory;
        public boolean featureFromMetadata = true;
        private final int mNumSlots;
        private final HashSet<ImsFeatureConfiguration.FeatureSlotPair> mSupportedFeatures;
        public ComponentName name;

        public ImsServiceInfo(int n) {
            this.mNumSlots = n;
            this.mSupportedFeatures = new HashSet();
        }

        void addFeatureForAllSlots(int n) {
            for (int i = 0; i < this.mNumSlots; ++i) {
                this.mSupportedFeatures.add(new ImsFeatureConfiguration.FeatureSlotPair(i, n));
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (ImsServiceInfo)object;
                Object object2 = this.name;
                if (object2 != null ? !object2.equals((Object)((ImsServiceInfo)object).name) : ((ImsServiceInfo)object).name != null) {
                    return false;
                }
                if (!this.mSupportedFeatures.equals(((ImsServiceInfo)object).mSupportedFeatures)) {
                    return false;
                }
                object2 = this.controllerFactory;
                if (object2 != null) {
                    bl = object2.equals(((ImsServiceInfo)object).controllerFactory);
                } else if (((ImsServiceInfo)object).controllerFactory != null) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        @VisibleForTesting
        public HashSet<ImsFeatureConfiguration.FeatureSlotPair> getSupportedFeatures() {
            return this.mSupportedFeatures;
        }

        public int hashCode() {
            Object object = this.name;
            int n = 0;
            int n2 = object != null ? object.hashCode() : 0;
            object = this.controllerFactory;
            if (object != null) {
                n = object.hashCode();
            }
            return n2 * 31 + n;
        }

        void replaceFeatures(Set<ImsFeatureConfiguration.FeatureSlotPair> set) {
            this.mSupportedFeatures.clear();
            this.mSupportedFeatures.addAll(set);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[ImsServiceInfo] name=");
            stringBuilder.append((Object)this.name);
            stringBuilder.append(", supportedFeatures=[ ");
            for (ImsFeatureConfiguration.FeatureSlotPair featureSlotPair : this.mSupportedFeatures) {
                stringBuilder.append("(");
                stringBuilder.append(featureSlotPair.slotId);
                stringBuilder.append(",");
                stringBuilder.append(featureSlotPair.featureType);
                stringBuilder.append(") ");
            }
            return stringBuilder.toString();
        }
    }

    @VisibleForTesting
    public static interface SubscriptionManagerProxy {
        public int getSlotIndex(int var1);

        public int getSubId(int var1);
    }

}

