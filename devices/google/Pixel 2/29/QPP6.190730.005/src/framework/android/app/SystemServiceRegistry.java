/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.accounts.AccountManager;
import android.accounts.IAccountManager;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.ContextImpl;
import android.app.DownloadManager;
import android.app.IAlarmManager;
import android.app.IWallpaperManager;
import android.app.JobSchedulerImpl;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.StatsManager;
import android.app.StatusBarManager;
import android.app.UiModeManager;
import android.app.UriGrantsManager;
import android.app.VrManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.IDevicePolicyManager;
import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.IContentSuggestionsManager;
import android.app.job.IJobScheduler;
import android.app.job.JobScheduler;
import android.app.prediction.AppPredictionManager;
import android.app.role.RoleControllerManager;
import android.app.role.RoleManager;
import android.app.slice.SliceManager;
import android.app.timedetector.TimeDetector;
import android.app.timezone.RulesManager;
import android.app.trust.TrustManager;
import android.app.usage.IStorageStatsManager;
import android.app.usage.IUsageStatsManager;
import android.app.usage.NetworkStatsManager;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.companion.CompanionDeviceManager;
import android.companion.ICompanionDeviceManager;
import android.content.ContentCaptureOptions;
import android.content.Context;
import android.content.IRestrictionsManager;
import android.content.RestrictionsManager;
import android.content.om.IOverlayManager;
import android.content.om.OverlayManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.CrossProfileApps;
import android.content.pm.ICrossProfileApps;
import android.content.pm.IPackageManager;
import android.content.pm.IShortcutService;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.content.rollback.IRollbackManager;
import android.content.rollback.RollbackManager;
import android.debug.AdbManager;
import android.debug.IAdbManager;
import android.hardware.ConsumerIrManager;
import android.hardware.ISerialManager;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.SerialManager;
import android.hardware.SystemSensorManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.IBiometricService;
import android.hardware.camera2.CameraManager;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManager;
import android.hardware.face.FaceManager;
import android.hardware.face.IFaceService;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.input.InputManager;
import android.hardware.iris.IIrisService;
import android.hardware.iris.IrisManager;
import android.hardware.location.ContextHubManager;
import android.hardware.radio.RadioManager;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbManager;
import android.location.CountryDetector;
import android.location.ICountryDetector;
import android.location.ILocationManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.midi.IMidiManager;
import android.media.midi.MidiManager;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.soundtrigger.SoundTriggerManager;
import android.media.tv.ITvInputManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.ConnectivityThread;
import android.net.EthernetManager;
import android.net.IConnectivityManager;
import android.net.IEthernetManager;
import android.net.IIpSecService;
import android.net.INetworkPolicyManager;
import android.net.ITestNetworkManager;
import android.net.IpSecManager;
import android.net.NetworkPolicyManager;
import android.net.NetworkScoreManager;
import android.net.NetworkWatchlistManager;
import android.net.TestNetworkManager;
import android.net.lowpan.ILowpanManager;
import android.net.lowpan.LowpanManager;
import android.net.nsd.INsdManager;
import android.net.nsd.NsdManager;
import android.net.wifi.IWifiManager;
import android.net.wifi.IWifiScanner;
import android.net.wifi.RttManager;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiScanner;
import android.net.wifi.aware.IWifiAwareManager;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.IWifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.rtt.IWifiRttManager;
import android.net.wifi.rtt.WifiRttManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.BugreportManager;
import android.os.DeviceIdleManager;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.HardwarePropertiesManager;
import android.os.IBatteryPropertiesRegistrar;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.IDumpstate;
import android.os.IHardwarePropertiesManager;
import android.os.IPowerManager;
import android.os.IRecoverySystem;
import android.os.ISystemUpdateManager;
import android.os.IUserManager;
import android.os.IncidentManager;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemUpdateManager;
import android.os.SystemVibrator;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.health.SystemHealthManager;
import android.os.image.DynamicSystemManager;
import android.os.image.IDynamicSystemService;
import android.os.storage.StorageManager;
import android.permission.PermissionControllerManager;
import android.permission.PermissionManager;
import android.print.IPrintManager;
import android.print.PrintManager;
import android.service.oemlock.IOemLockService;
import android.service.oemlock.OemLockManager;
import android.service.persistentdata.IPersistentDataBlockService;
import android.service.persistentdata.PersistentDataBlockManager;
import android.service.vr.IVrManager;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccCardManager;
import android.telephony.euicc.EuiccManager;
import android.telephony.ims.RcsManager;
import android.text.ClipboardManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.autofill.AutofillManager;
import android.view.autofill.IAutoFillManager;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.IContentCaptureManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassificationManager;
import android.view.textservice.TextServicesManager;
import com.android.internal.app.IAppOpsService;
import com.android.internal.app.IBatteryStats;
import com.android.internal.app.ISoundTriggerService;
import com.android.internal.appwidget.IAppWidgetService;
import com.android.internal.net.INetworkWatchlistManager;
import com.android.internal.os.IDropBoxManagerService;
import com.android.internal.policy.PhoneLayoutInflater;
import java.util.Map;

final class SystemServiceRegistry {
    private static final Map<String, ServiceFetcher<?>> SYSTEM_SERVICE_FETCHERS;
    private static final Map<Class<?>, String> SYSTEM_SERVICE_NAMES;
    private static final String TAG = "SystemServiceRegistry";
    private static int sServiceCacheSize;

    static {
        SYSTEM_SERVICE_NAMES = new ArrayMap();
        SYSTEM_SERVICE_FETCHERS = new ArrayMap();
        SystemServiceRegistry.registerService("accessibility", AccessibilityManager.class, new CachedServiceFetcher<AccessibilityManager>(){

            @Override
            public AccessibilityManager createService(ContextImpl contextImpl) {
                return AccessibilityManager.getInstance(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("captioning", CaptioningManager.class, new CachedServiceFetcher<CaptioningManager>(){

            @Override
            public CaptioningManager createService(ContextImpl contextImpl) {
                return new CaptioningManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("account", AccountManager.class, new CachedServiceFetcher<AccountManager>(){

            @Override
            public AccountManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AccountManager(contextImpl, IAccountManager.Stub.asInterface(ServiceManager.getServiceOrThrow("account")));
            }
        });
        SystemServiceRegistry.registerService("activity", ActivityManager.class, new CachedServiceFetcher<ActivityManager>(){

            @Override
            public ActivityManager createService(ContextImpl contextImpl) {
                return new ActivityManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("activity_task", ActivityTaskManager.class, new CachedServiceFetcher<ActivityTaskManager>(){

            @Override
            public ActivityTaskManager createService(ContextImpl contextImpl) {
                return new ActivityTaskManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("uri_grants", UriGrantsManager.class, new CachedServiceFetcher<UriGrantsManager>(){

            @Override
            public UriGrantsManager createService(ContextImpl contextImpl) {
                return new UriGrantsManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("alarm", AlarmManager.class, new CachedServiceFetcher<AlarmManager>(){

            @Override
            public AlarmManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AlarmManager(IAlarmManager.Stub.asInterface(ServiceManager.getServiceOrThrow("alarm")), contextImpl);
            }
        });
        SystemServiceRegistry.registerService("audio", AudioManager.class, new CachedServiceFetcher<AudioManager>(){

            @Override
            public AudioManager createService(ContextImpl contextImpl) {
                return new AudioManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("media_router", MediaRouter.class, new CachedServiceFetcher<MediaRouter>(){

            @Override
            public MediaRouter createService(ContextImpl contextImpl) {
                return new MediaRouter(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("bluetooth", BluetoothManager.class, new CachedServiceFetcher<BluetoothManager>(){

            @Override
            public BluetoothManager createService(ContextImpl contextImpl) {
                return new BluetoothManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("hdmi_control", HdmiControlManager.class, new StaticServiceFetcher<HdmiControlManager>(){

            @Override
            public HdmiControlManager createService() throws ServiceManager.ServiceNotFoundException {
                return new HdmiControlManager(IHdmiControlService.Stub.asInterface(ServiceManager.getServiceOrThrow("hdmi_control")));
            }
        });
        SystemServiceRegistry.registerService("textclassification", TextClassificationManager.class, new CachedServiceFetcher<TextClassificationManager>(){

            @Override
            public TextClassificationManager createService(ContextImpl contextImpl) {
                return new TextClassificationManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("clipboard", android.content.ClipboardManager.class, new CachedServiceFetcher<android.content.ClipboardManager>(){

            @Override
            public android.content.ClipboardManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new android.content.ClipboardManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SYSTEM_SERVICE_NAMES.put(ClipboardManager.class, "clipboard");
        SystemServiceRegistry.registerService("connectivity", ConnectivityManager.class, new StaticApplicationContextServiceFetcher<ConnectivityManager>(){

            @Override
            public ConnectivityManager createService(Context context) throws ServiceManager.ServiceNotFoundException {
                return new ConnectivityManager(context, IConnectivityManager.Stub.asInterface(ServiceManager.getServiceOrThrow("connectivity")));
            }
        });
        SystemServiceRegistry.registerService("netd", IBinder.class, new StaticServiceFetcher<IBinder>(){

            @Override
            public IBinder createService() throws ServiceManager.ServiceNotFoundException {
                return ServiceManager.getServiceOrThrow("netd");
            }
        });
        SystemServiceRegistry.registerService("ipsec", IpSecManager.class, new CachedServiceFetcher<IpSecManager>(){

            @Override
            public IpSecManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new IpSecManager(contextImpl, IIpSecService.Stub.asInterface(ServiceManager.getService("ipsec")));
            }
        });
        SystemServiceRegistry.registerService("test_network", TestNetworkManager.class, new StaticApplicationContextServiceFetcher<TestNetworkManager>(){

            @Override
            public TestNetworkManager createService(Context object) throws ServiceManager.ServiceNotFoundException {
                object = ServiceManager.getServiceOrThrow("connectivity");
                object = IConnectivityManager.Stub.asInterface((IBinder)object);
                try {
                    object = object.startOrGetTestNetworkService();
                    return new TestNetworkManager(ITestNetworkManager.Stub.asInterface((IBinder)object));
                }
                catch (RemoteException remoteException) {
                    throw new ServiceManager.ServiceNotFoundException("test_network");
                }
            }
        });
        SystemServiceRegistry.registerService("country_detector", CountryDetector.class, new StaticServiceFetcher<CountryDetector>(){

            @Override
            public CountryDetector createService() throws ServiceManager.ServiceNotFoundException {
                return new CountryDetector(ICountryDetector.Stub.asInterface(ServiceManager.getServiceOrThrow("country_detector")));
            }
        });
        SystemServiceRegistry.registerService("device_policy", DevicePolicyManager.class, new CachedServiceFetcher<DevicePolicyManager>(){

            @Override
            public DevicePolicyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DevicePolicyManager(contextImpl, IDevicePolicyManager.Stub.asInterface(ServiceManager.getServiceOrThrow("device_policy")));
            }
        });
        SystemServiceRegistry.registerService("download", DownloadManager.class, new CachedServiceFetcher<DownloadManager>(){

            @Override
            public DownloadManager createService(ContextImpl contextImpl) {
                return new DownloadManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("batterymanager", BatteryManager.class, new CachedServiceFetcher<BatteryManager>(){

            @Override
            public BatteryManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBatteryStats iBatteryStats = IBatteryStats.Stub.asInterface(ServiceManager.getServiceOrThrow("batterystats"));
                return new BatteryManager(contextImpl, iBatteryStats, IBatteryPropertiesRegistrar.Stub.asInterface(ServiceManager.getServiceOrThrow("batteryproperties")));
            }
        });
        SystemServiceRegistry.registerService("nfc", NfcManager.class, new CachedServiceFetcher<NfcManager>(){

            @Override
            public NfcManager createService(ContextImpl contextImpl) {
                return new NfcManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("dropbox", DropBoxManager.class, new CachedServiceFetcher<DropBoxManager>(){

            @Override
            public DropBoxManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DropBoxManager(contextImpl, IDropBoxManagerService.Stub.asInterface(ServiceManager.getServiceOrThrow("dropbox")));
            }
        });
        SystemServiceRegistry.registerService("input", InputManager.class, new StaticServiceFetcher<InputManager>(){

            @Override
            public InputManager createService() {
                return InputManager.getInstance();
            }
        });
        SystemServiceRegistry.registerService("display", DisplayManager.class, new CachedServiceFetcher<DisplayManager>(){

            @Override
            public DisplayManager createService(ContextImpl contextImpl) {
                return new DisplayManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("color_display", ColorDisplayManager.class, new CachedServiceFetcher<ColorDisplayManager>(){

            @Override
            public ColorDisplayManager createService(ContextImpl contextImpl) {
                return new ColorDisplayManager();
            }
        });
        SystemServiceRegistry.registerService("input_method", InputMethodManager.class, new ServiceFetcher<InputMethodManager>(){

            @Override
            public InputMethodManager getService(ContextImpl contextImpl) {
                return InputMethodManager.forContext(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("textservices", TextServicesManager.class, new CachedServiceFetcher<TextServicesManager>(){

            @Override
            public TextServicesManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return TextServicesManager.createInstance(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("keyguard", KeyguardManager.class, new CachedServiceFetcher<KeyguardManager>(){

            @Override
            public KeyguardManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new KeyguardManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("layout_inflater", LayoutInflater.class, new CachedServiceFetcher<LayoutInflater>(){

            @Override
            public LayoutInflater createService(ContextImpl contextImpl) {
                return new PhoneLayoutInflater(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("location", LocationManager.class, new CachedServiceFetcher<LocationManager>(){

            @Override
            public LocationManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new LocationManager(contextImpl, ILocationManager.Stub.asInterface(ServiceManager.getServiceOrThrow("location")));
            }
        });
        SystemServiceRegistry.registerService("netpolicy", NetworkPolicyManager.class, new CachedServiceFetcher<NetworkPolicyManager>(){

            @Override
            public NetworkPolicyManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkPolicyManager(contextImpl, INetworkPolicyManager.Stub.asInterface(ServiceManager.getServiceOrThrow("netpolicy")));
            }
        });
        SystemServiceRegistry.registerService("notification", NotificationManager.class, new CachedServiceFetcher<NotificationManager>(){

            @Override
            public NotificationManager createService(ContextImpl contextImpl) {
                Context context = contextImpl.getOuterContext();
                return new NotificationManager(new ContextThemeWrapper(context, Resources.selectSystemTheme(0, context.getApplicationInfo().targetSdkVersion, 16973835, 16973935, 16974126, 16974130)), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("servicediscovery", NsdManager.class, new CachedServiceFetcher<NsdManager>(){

            @Override
            public NsdManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                INsdManager iNsdManager = INsdManager.Stub.asInterface(ServiceManager.getServiceOrThrow("servicediscovery"));
                return new NsdManager(contextImpl.getOuterContext(), iNsdManager);
            }
        });
        SystemServiceRegistry.registerService("power", PowerManager.class, new CachedServiceFetcher<PowerManager>(){

            @Override
            public PowerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IPowerManager iPowerManager = IPowerManager.Stub.asInterface(ServiceManager.getServiceOrThrow("power"));
                return new PowerManager(contextImpl.getOuterContext(), iPowerManager, contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("recovery", RecoverySystem.class, new CachedServiceFetcher<RecoverySystem>(){

            @Override
            public RecoverySystem createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RecoverySystem(IRecoverySystem.Stub.asInterface(ServiceManager.getServiceOrThrow("recovery")));
            }
        });
        SystemServiceRegistry.registerService("search", SearchManager.class, new CachedServiceFetcher<SearchManager>(){

            @Override
            public SearchManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SearchManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("sensor", SensorManager.class, new CachedServiceFetcher<SensorManager>(){

            @Override
            public SensorManager createService(ContextImpl contextImpl) {
                return new SystemSensorManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        SystemServiceRegistry.registerService("sensor_privacy", SensorPrivacyManager.class, new CachedServiceFetcher<SensorPrivacyManager>(){

            @Override
            public SensorPrivacyManager createService(ContextImpl contextImpl) {
                return SensorPrivacyManager.getInstance(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("stats", StatsManager.class, new CachedServiceFetcher<StatsManager>(){

            @Override
            public StatsManager createService(ContextImpl contextImpl) {
                return new StatsManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("statusbar", StatusBarManager.class, new CachedServiceFetcher<StatusBarManager>(){

            @Override
            public StatusBarManager createService(ContextImpl contextImpl) {
                return new StatusBarManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("storage", StorageManager.class, new CachedServiceFetcher<StorageManager>(){

            @Override
            public StorageManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new StorageManager(contextImpl, contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        SystemServiceRegistry.registerService("storagestats", StorageStatsManager.class, new CachedServiceFetcher<StorageStatsManager>(){

            @Override
            public StorageStatsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new StorageStatsManager(contextImpl, IStorageStatsManager.Stub.asInterface(ServiceManager.getServiceOrThrow("storagestats")));
            }
        });
        SystemServiceRegistry.registerService("system_update", SystemUpdateManager.class, new CachedServiceFetcher<SystemUpdateManager>(){

            @Override
            public SystemUpdateManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SystemUpdateManager(ISystemUpdateManager.Stub.asInterface(ServiceManager.getServiceOrThrow("system_update")));
            }
        });
        SystemServiceRegistry.registerService("phone", TelephonyManager.class, new CachedServiceFetcher<TelephonyManager>(){

            @Override
            public TelephonyManager createService(ContextImpl contextImpl) {
                return new TelephonyManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("telephony_subscription_service", SubscriptionManager.class, new CachedServiceFetcher<SubscriptionManager>(){

            @Override
            public SubscriptionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SubscriptionManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("ircs", RcsManager.class, new CachedServiceFetcher<RcsManager>(){

            @Override
            public RcsManager createService(ContextImpl contextImpl) {
                return new RcsManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("carrier_config", CarrierConfigManager.class, new CachedServiceFetcher<CarrierConfigManager>(){

            @Override
            public CarrierConfigManager createService(ContextImpl contextImpl) {
                return new CarrierConfigManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("telecom", TelecomManager.class, new CachedServiceFetcher<TelecomManager>(){

            @Override
            public TelecomManager createService(ContextImpl contextImpl) {
                return new TelecomManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("euicc", EuiccManager.class, new CachedServiceFetcher<EuiccManager>(){

            @Override
            public EuiccManager createService(ContextImpl contextImpl) {
                return new EuiccManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("euicc_card", EuiccCardManager.class, new CachedServiceFetcher<EuiccCardManager>(){

            @Override
            public EuiccCardManager createService(ContextImpl contextImpl) {
                return new EuiccCardManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("uimode", UiModeManager.class, new CachedServiceFetcher<UiModeManager>(){

            @Override
            public UiModeManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UiModeManager();
            }
        });
        SystemServiceRegistry.registerService("usb", UsbManager.class, new CachedServiceFetcher<UsbManager>(){

            @Override
            public UsbManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UsbManager(contextImpl, IUsbManager.Stub.asInterface(ServiceManager.getServiceOrThrow("usb")));
            }
        });
        SystemServiceRegistry.registerService("adb", AdbManager.class, new CachedServiceFetcher<AdbManager>(){

            @Override
            public AdbManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AdbManager(contextImpl, IAdbManager.Stub.asInterface(ServiceManager.getServiceOrThrow("adb")));
            }
        });
        SystemServiceRegistry.registerService("serial", SerialManager.class, new CachedServiceFetcher<SerialManager>(){

            @Override
            public SerialManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SerialManager(contextImpl, ISerialManager.Stub.asInterface(ServiceManager.getServiceOrThrow("serial")));
            }
        });
        SystemServiceRegistry.registerService("vibrator", Vibrator.class, new CachedServiceFetcher<Vibrator>(){

            @Override
            public Vibrator createService(ContextImpl contextImpl) {
                return new SystemVibrator(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("wallpaper", WallpaperManager.class, new CachedServiceFetcher<WallpaperManager>(){

            @Override
            public WallpaperManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder iBinder = contextImpl.getApplicationInfo().targetSdkVersion >= 28 ? ServiceManager.getServiceOrThrow("wallpaper") : ServiceManager.getService("wallpaper");
                return new WallpaperManager(IWallpaperManager.Stub.asInterface(iBinder), contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("lowpan", LowpanManager.class, new CachedServiceFetcher<LowpanManager>(){

            @Override
            public LowpanManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                ILowpanManager iLowpanManager = ILowpanManager.Stub.asInterface(ServiceManager.getServiceOrThrow("lowpan"));
                return new LowpanManager(contextImpl.getOuterContext(), iLowpanManager, ConnectivityThread.getInstanceLooper());
            }
        });
        SystemServiceRegistry.registerService("wifi", WifiManager.class, new CachedServiceFetcher<WifiManager>(){

            @Override
            public WifiManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IWifiManager iWifiManager = IWifiManager.Stub.asInterface(ServiceManager.getServiceOrThrow("wifi"));
                return new WifiManager(contextImpl.getOuterContext(), iWifiManager, ConnectivityThread.getInstanceLooper());
            }
        });
        SystemServiceRegistry.registerService("wifip2p", WifiP2pManager.class, new StaticServiceFetcher<WifiP2pManager>(){

            @Override
            public WifiP2pManager createService() throws ServiceManager.ServiceNotFoundException {
                return new WifiP2pManager(IWifiP2pManager.Stub.asInterface(ServiceManager.getServiceOrThrow("wifip2p")));
            }
        });
        SystemServiceRegistry.registerService("wifiaware", WifiAwareManager.class, new CachedServiceFetcher<WifiAwareManager>(){

            @Override
            public WifiAwareManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IWifiAwareManager iWifiAwareManager = IWifiAwareManager.Stub.asInterface(ServiceManager.getServiceOrThrow("wifiaware"));
                if (iWifiAwareManager == null) {
                    return null;
                }
                return new WifiAwareManager(contextImpl.getOuterContext(), iWifiAwareManager);
            }
        });
        SystemServiceRegistry.registerService("wifiscanner", WifiScanner.class, new CachedServiceFetcher<WifiScanner>(){

            @Override
            public WifiScanner createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IWifiScanner iWifiScanner = IWifiScanner.Stub.asInterface(ServiceManager.getServiceOrThrow("wifiscanner"));
                return new WifiScanner(contextImpl.getOuterContext(), iWifiScanner, ConnectivityThread.getInstanceLooper());
            }
        });
        SystemServiceRegistry.registerService("rttmanager", RttManager.class, new CachedServiceFetcher<RttManager>(){

            @Override
            public RttManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IWifiRttManager iWifiRttManager = IWifiRttManager.Stub.asInterface(ServiceManager.getServiceOrThrow("wifirtt"));
                return new RttManager(contextImpl.getOuterContext(), new WifiRttManager(contextImpl.getOuterContext(), iWifiRttManager));
            }
        });
        SystemServiceRegistry.registerService("wifirtt", WifiRttManager.class, new CachedServiceFetcher<WifiRttManager>(){

            @Override
            public WifiRttManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IWifiRttManager iWifiRttManager = IWifiRttManager.Stub.asInterface(ServiceManager.getServiceOrThrow("wifirtt"));
                return new WifiRttManager(contextImpl.getOuterContext(), iWifiRttManager);
            }
        });
        SystemServiceRegistry.registerService("ethernet", EthernetManager.class, new CachedServiceFetcher<EthernetManager>(){

            @Override
            public EthernetManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IEthernetManager iEthernetManager = IEthernetManager.Stub.asInterface(ServiceManager.getServiceOrThrow("ethernet"));
                return new EthernetManager(contextImpl.getOuterContext(), iEthernetManager);
            }
        });
        SystemServiceRegistry.registerService("window", WindowManager.class, new CachedServiceFetcher<WindowManager>(){

            @Override
            public WindowManager createService(ContextImpl contextImpl) {
                return new WindowManagerImpl(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("user", UserManager.class, new CachedServiceFetcher<UserManager>(){

            @Override
            public UserManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new UserManager(contextImpl, IUserManager.Stub.asInterface(ServiceManager.getServiceOrThrow("user")));
            }
        });
        SystemServiceRegistry.registerService("appops", AppOpsManager.class, new CachedServiceFetcher<AppOpsManager>(){

            @Override
            public AppOpsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AppOpsManager(contextImpl, IAppOpsService.Stub.asInterface(ServiceManager.getServiceOrThrow("appops")));
            }
        });
        SystemServiceRegistry.registerService("camera", CameraManager.class, new CachedServiceFetcher<CameraManager>(){

            @Override
            public CameraManager createService(ContextImpl contextImpl) {
                return new CameraManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("launcherapps", LauncherApps.class, new CachedServiceFetcher<LauncherApps>(){

            @Override
            public LauncherApps createService(ContextImpl contextImpl) {
                return new LauncherApps(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("restrictions", RestrictionsManager.class, new CachedServiceFetcher<RestrictionsManager>(){

            @Override
            public RestrictionsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RestrictionsManager(contextImpl, IRestrictionsManager.Stub.asInterface(ServiceManager.getServiceOrThrow("restrictions")));
            }
        });
        SystemServiceRegistry.registerService("print", PrintManager.class, new CachedServiceFetcher<PrintManager>(){

            @Override
            public PrintManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IPrintManager iPrintManager = null;
                if (contextImpl.getPackageManager().hasSystemFeature("android.software.print")) {
                    iPrintManager = IPrintManager.Stub.asInterface(ServiceManager.getServiceOrThrow("print"));
                }
                int n = contextImpl.getUserId();
                int n2 = UserHandle.getAppId(contextImpl.getApplicationInfo().uid);
                return new PrintManager(contextImpl.getOuterContext(), iPrintManager, n, n2);
            }
        });
        SystemServiceRegistry.registerService("companiondevice", CompanionDeviceManager.class, new CachedServiceFetcher<CompanionDeviceManager>(){

            @Override
            public CompanionDeviceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                ICompanionDeviceManager iCompanionDeviceManager = null;
                if (contextImpl.getPackageManager().hasSystemFeature("android.software.companion_device_setup")) {
                    iCompanionDeviceManager = ICompanionDeviceManager.Stub.asInterface(ServiceManager.getServiceOrThrow("companiondevice"));
                }
                return new CompanionDeviceManager(iCompanionDeviceManager, contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("consumer_ir", ConsumerIrManager.class, new CachedServiceFetcher<ConsumerIrManager>(){

            @Override
            public ConsumerIrManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ConsumerIrManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("media_session", MediaSessionManager.class, new CachedServiceFetcher<MediaSessionManager>(){

            @Override
            public MediaSessionManager createService(ContextImpl contextImpl) {
                return new MediaSessionManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("trust", TrustManager.class, new StaticServiceFetcher<TrustManager>(){

            @Override
            public TrustManager createService() throws ServiceManager.ServiceNotFoundException {
                return new TrustManager(ServiceManager.getServiceOrThrow("trust"));
            }
        });
        SystemServiceRegistry.registerService("fingerprint", FingerprintManager.class, new CachedServiceFetcher<FingerprintManager>(){

            @Override
            public FingerprintManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                Object object = contextImpl.getApplicationInfo().targetSdkVersion >= 26 ? ServiceManager.getServiceOrThrow("fingerprint") : ServiceManager.getService("fingerprint");
                object = IFingerprintService.Stub.asInterface((IBinder)object);
                return new FingerprintManager(contextImpl.getOuterContext(), (IFingerprintService)object);
            }
        });
        SystemServiceRegistry.registerService("face", FaceManager.class, new CachedServiceFetcher<FaceManager>(){

            @Override
            public FaceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                Object object = contextImpl.getApplicationInfo().targetSdkVersion >= 26 ? ServiceManager.getServiceOrThrow("face") : ServiceManager.getService("face");
                object = IFaceService.Stub.asInterface((IBinder)object);
                return new FaceManager(contextImpl.getOuterContext(), (IFaceService)object);
            }
        });
        SystemServiceRegistry.registerService("iris", IrisManager.class, new CachedServiceFetcher<IrisManager>(){

            @Override
            public IrisManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IIrisService iIrisService = IIrisService.Stub.asInterface(ServiceManager.getServiceOrThrow("iris"));
                return new IrisManager(contextImpl.getOuterContext(), iIrisService);
            }
        });
        SystemServiceRegistry.registerService("biometric", BiometricManager.class, new CachedServiceFetcher<BiometricManager>(){

            @Override
            public BiometricManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                if (BiometricManager.hasBiometrics(contextImpl)) {
                    Object object = ServiceManager.getServiceOrThrow("biometric");
                    object = IBiometricService.Stub.asInterface((IBinder)object);
                    return new BiometricManager(contextImpl.getOuterContext(), (IBiometricService)object);
                }
                return new BiometricManager(contextImpl.getOuterContext(), null);
            }
        });
        SystemServiceRegistry.registerService("tv_input", TvInputManager.class, new CachedServiceFetcher<TvInputManager>(){

            @Override
            public TvInputManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TvInputManager(ITvInputManager.Stub.asInterface(ServiceManager.getServiceOrThrow("tv_input")), contextImpl.getUserId());
            }
        });
        SystemServiceRegistry.registerService("network_score", NetworkScoreManager.class, new CachedServiceFetcher<NetworkScoreManager>(){

            @Override
            public NetworkScoreManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkScoreManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("usagestats", UsageStatsManager.class, new CachedServiceFetcher<UsageStatsManager>(){

            @Override
            public UsageStatsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IUsageStatsManager iUsageStatsManager = IUsageStatsManager.Stub.asInterface(ServiceManager.getServiceOrThrow("usagestats"));
                return new UsageStatsManager(contextImpl.getOuterContext(), iUsageStatsManager);
            }
        });
        SystemServiceRegistry.registerService("netstats", NetworkStatsManager.class, new CachedServiceFetcher<NetworkStatsManager>(){

            @Override
            public NetworkStatsManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkStatsManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("jobscheduler", JobScheduler.class, new StaticServiceFetcher<JobScheduler>(){

            @Override
            public JobScheduler createService() throws ServiceManager.ServiceNotFoundException {
                return new JobSchedulerImpl(IJobScheduler.Stub.asInterface(ServiceManager.getServiceOrThrow("jobscheduler")));
            }
        });
        SystemServiceRegistry.registerService("persistent_data_block", PersistentDataBlockManager.class, new StaticServiceFetcher<PersistentDataBlockManager>(){

            @Override
            public PersistentDataBlockManager createService() throws ServiceManager.ServiceNotFoundException {
                Object object = ServiceManager.getServiceOrThrow("persistent_data_block");
                if ((object = IPersistentDataBlockService.Stub.asInterface((IBinder)object)) != null) {
                    return new PersistentDataBlockManager((IPersistentDataBlockService)object);
                }
                return null;
            }
        });
        SystemServiceRegistry.registerService("oem_lock", OemLockManager.class, new StaticServiceFetcher<OemLockManager>(){

            @Override
            public OemLockManager createService() throws ServiceManager.ServiceNotFoundException {
                IOemLockService iOemLockService = IOemLockService.Stub.asInterface(ServiceManager.getServiceOrThrow("oem_lock"));
                if (iOemLockService != null) {
                    return new OemLockManager(iOemLockService);
                }
                return null;
            }
        });
        SystemServiceRegistry.registerService("media_projection", MediaProjectionManager.class, new CachedServiceFetcher<MediaProjectionManager>(){

            @Override
            public MediaProjectionManager createService(ContextImpl contextImpl) {
                return new MediaProjectionManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("appwidget", AppWidgetManager.class, new CachedServiceFetcher<AppWidgetManager>(){

            @Override
            public AppWidgetManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AppWidgetManager(contextImpl, IAppWidgetService.Stub.asInterface(ServiceManager.getServiceOrThrow("appwidget")));
            }
        });
        SystemServiceRegistry.registerService("midi", MidiManager.class, new CachedServiceFetcher<MidiManager>(){

            @Override
            public MidiManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new MidiManager(IMidiManager.Stub.asInterface(ServiceManager.getServiceOrThrow("midi")));
            }
        });
        SystemServiceRegistry.registerService("broadcastradio", RadioManager.class, new CachedServiceFetcher<RadioManager>(){

            @Override
            public RadioManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RadioManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("hardware_properties", HardwarePropertiesManager.class, new CachedServiceFetcher<HardwarePropertiesManager>(){

            @Override
            public HardwarePropertiesManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder iBinder = ServiceManager.getServiceOrThrow("hardware_properties");
                return new HardwarePropertiesManager(contextImpl, IHardwarePropertiesManager.Stub.asInterface(iBinder));
            }
        });
        SystemServiceRegistry.registerService("soundtrigger", SoundTriggerManager.class, new CachedServiceFetcher<SoundTriggerManager>(){

            @Override
            public SoundTriggerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SoundTriggerManager(contextImpl, ISoundTriggerService.Stub.asInterface(ServiceManager.getServiceOrThrow("soundtrigger")));
            }
        });
        SystemServiceRegistry.registerService("shortcut", ShortcutManager.class, new CachedServiceFetcher<ShortcutManager>(){

            @Override
            public ShortcutManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ShortcutManager(contextImpl, IShortcutService.Stub.asInterface(ServiceManager.getServiceOrThrow("shortcut")));
            }
        });
        SystemServiceRegistry.registerService("overlay", OverlayManager.class, new CachedServiceFetcher<OverlayManager>(){

            @Override
            public OverlayManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new OverlayManager(contextImpl, IOverlayManager.Stub.asInterface(ServiceManager.getServiceOrThrow("overlay")));
            }
        });
        SystemServiceRegistry.registerService("network_watchlist", NetworkWatchlistManager.class, new CachedServiceFetcher<NetworkWatchlistManager>(){

            @Override
            public NetworkWatchlistManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new NetworkWatchlistManager(contextImpl, INetworkWatchlistManager.Stub.asInterface(ServiceManager.getServiceOrThrow("network_watchlist")));
            }
        });
        SystemServiceRegistry.registerService("systemhealth", SystemHealthManager.class, new CachedServiceFetcher<SystemHealthManager>(){

            @Override
            public SystemHealthManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SystemHealthManager(IBatteryStats.Stub.asInterface(ServiceManager.getServiceOrThrow("batterystats")));
            }
        });
        SystemServiceRegistry.registerService("contexthub", ContextHubManager.class, new CachedServiceFetcher<ContextHubManager>(){

            @Override
            public ContextHubManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new ContextHubManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler().getLooper());
            }
        });
        SystemServiceRegistry.registerService("incident", IncidentManager.class, new CachedServiceFetcher<IncidentManager>(){

            @Override
            public IncidentManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new IncidentManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("bugreport", BugreportManager.class, new CachedServiceFetcher<BugreportManager>(){

            @Override
            public BugreportManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder iBinder = ServiceManager.getServiceOrThrow("bugreport");
                return new BugreportManager(contextImpl.getOuterContext(), IDumpstate.Stub.asInterface(iBinder));
            }
        });
        SystemServiceRegistry.registerService("autofill", AutofillManager.class, new CachedServiceFetcher<AutofillManager>(){

            @Override
            public AutofillManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IAutoFillManager iAutoFillManager = IAutoFillManager.Stub.asInterface(ServiceManager.getService("autofill"));
                return new AutofillManager(contextImpl.getOuterContext(), iAutoFillManager);
            }
        });
        SystemServiceRegistry.registerService("content_capture", ContentCaptureManager.class, new CachedServiceFetcher<ContentCaptureManager>(){

            @Override
            public ContentCaptureManager createService(ContextImpl context) throws ServiceManager.ServiceNotFoundException {
                IContentCaptureManager iContentCaptureManager;
                ContentCaptureOptions contentCaptureOptions = (context = ((ContextImpl)context).getOuterContext()).getContentCaptureOptions();
                if (contentCaptureOptions != null && (contentCaptureOptions.lite || contentCaptureOptions.isWhitelisted(context)) && (iContentCaptureManager = IContentCaptureManager.Stub.asInterface(ServiceManager.getService("content_capture"))) != null) {
                    return new ContentCaptureManager(context, iContentCaptureManager, contentCaptureOptions);
                }
                return null;
            }
        });
        SystemServiceRegistry.registerService("app_prediction", AppPredictionManager.class, new CachedServiceFetcher<AppPredictionManager>(){

            @Override
            public AppPredictionManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new AppPredictionManager(contextImpl);
            }
        });
        SystemServiceRegistry.registerService("content_suggestions", ContentSuggestionsManager.class, new CachedServiceFetcher<ContentSuggestionsManager>(){

            @Override
            public ContentSuggestionsManager createService(ContextImpl contextImpl) {
                Object object = ServiceManager.getService("content_suggestions");
                object = IContentSuggestionsManager.Stub.asInterface((IBinder)object);
                return new ContentSuggestionsManager(contextImpl.getUserId(), (IContentSuggestionsManager)object);
            }
        });
        SystemServiceRegistry.registerService("vrmanager", VrManager.class, new CachedServiceFetcher<VrManager>(){

            @Override
            public VrManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new VrManager(IVrManager.Stub.asInterface(ServiceManager.getServiceOrThrow("vrmanager")));
            }
        });
        SystemServiceRegistry.registerService("timezone", RulesManager.class, new CachedServiceFetcher<RulesManager>(){

            @Override
            public RulesManager createService(ContextImpl contextImpl) {
                return new RulesManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("crossprofileapps", CrossProfileApps.class, new CachedServiceFetcher<CrossProfileApps>(){

            @Override
            public CrossProfileApps createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder iBinder = ServiceManager.getServiceOrThrow("crossprofileapps");
                return new CrossProfileApps(contextImpl.getOuterContext(), ICrossProfileApps.Stub.asInterface(iBinder));
            }
        });
        SystemServiceRegistry.registerService("slice", SliceManager.class, new CachedServiceFetcher<SliceManager>(){

            @Override
            public SliceManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new SliceManager(contextImpl.getOuterContext(), contextImpl.mMainThread.getHandler());
            }
        });
        SystemServiceRegistry.registerService("deviceidle", DeviceIdleManager.class, new CachedServiceFetcher<DeviceIdleManager>(){

            @Override
            public DeviceIdleManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IDeviceIdleController iDeviceIdleController = IDeviceIdleController.Stub.asInterface(ServiceManager.getServiceOrThrow("deviceidle"));
                return new DeviceIdleManager(contextImpl.getOuterContext(), iDeviceIdleController);
            }
        });
        SystemServiceRegistry.registerService("time_detector", TimeDetector.class, new CachedServiceFetcher<TimeDetector>(){

            @Override
            public TimeDetector createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new TimeDetector();
            }
        });
        SystemServiceRegistry.registerService("permission", PermissionManager.class, new CachedServiceFetcher<PermissionManager>(){

            @Override
            public PermissionManager createService(ContextImpl contextImpl) {
                IPackageManager iPackageManager = AppGlobals.getPackageManager();
                return new PermissionManager(contextImpl.getOuterContext(), iPackageManager);
            }
        });
        SystemServiceRegistry.registerService("permission_controller", PermissionControllerManager.class, new CachedServiceFetcher<PermissionControllerManager>(){

            @Override
            public PermissionControllerManager createService(ContextImpl contextImpl) {
                return new PermissionControllerManager(contextImpl.getOuterContext(), contextImpl.getMainThreadHandler());
            }
        });
        SystemServiceRegistry.registerService("role", RoleManager.class, new CachedServiceFetcher<RoleManager>(){

            @Override
            public RoleManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RoleManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("role_controller", RoleControllerManager.class, new CachedServiceFetcher<RoleControllerManager>(){

            @Override
            public RoleControllerManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new RoleControllerManager(contextImpl.getOuterContext());
            }
        });
        SystemServiceRegistry.registerService("rollback", RollbackManager.class, new CachedServiceFetcher<RollbackManager>(){

            @Override
            public RollbackManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                IBinder iBinder = ServiceManager.getServiceOrThrow("rollback");
                return new RollbackManager(contextImpl.getOuterContext(), IRollbackManager.Stub.asInterface(iBinder));
            }
        });
        SystemServiceRegistry.registerService("dynamic_system", DynamicSystemManager.class, new CachedServiceFetcher<DynamicSystemManager>(){

            @Override
            public DynamicSystemManager createService(ContextImpl contextImpl) throws ServiceManager.ServiceNotFoundException {
                return new DynamicSystemManager(IDynamicSystemService.Stub.asInterface(ServiceManager.getServiceOrThrow("dynamic_system")));
            }
        });
    }

    private SystemServiceRegistry() {
    }

    static /* synthetic */ int access$008() {
        int n = sServiceCacheSize;
        sServiceCacheSize = n + 1;
        return n;
    }

    public static Object[] createServiceCache() {
        return new Object[sServiceCacheSize];
    }

    public static Object getSystemService(ContextImpl contextImpl, String object) {
        contextImpl = (object = SYSTEM_SERVICE_FETCHERS.get(object)) != null ? object.getService(contextImpl) : null;
        return contextImpl;
    }

    public static String getSystemServiceName(Class<?> class_) {
        return SYSTEM_SERVICE_NAMES.get(class_);
    }

    public static void onServiceNotFound(ServiceManager.ServiceNotFoundException serviceNotFoundException) {
        if (Process.myUid() < 10000) {
            Log.wtf(TAG, serviceNotFoundException.getMessage(), serviceNotFoundException);
        } else {
            Log.w(TAG, serviceNotFoundException.getMessage());
        }
    }

    private static <T> void registerService(String string2, Class<T> class_, ServiceFetcher<T> serviceFetcher) {
        SYSTEM_SERVICE_NAMES.put(class_, string2);
        SYSTEM_SERVICE_FETCHERS.put(string2, serviceFetcher);
    }

    static abstract class CachedServiceFetcher<T>
    implements ServiceFetcher<T> {
        private final int mCacheIndex = SystemServiceRegistry.access$008();

        CachedServiceFetcher() {
        }

        public abstract T createService(ContextImpl var1) throws ServiceManager.ServiceNotFoundException;

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public final T getService(ContextImpl object) {
            Object[] arrobject = ((ContextImpl)object).mServiceCache;
            int[] arrn = ((ContextImpl)object).mServiceInitializationStateArray;
            do {
                int n;
                block26 : {
                    Throwable throwable22;
                    block25 : {
                        n = 0;
                        // MONITORENTER : arrobject
                        Object object2 = arrobject[this.mCacheIndex];
                        if (object2 != null) {
                            // MONITOREXIT : arrobject
                            return (T)object2;
                        }
                        if (arrn[this.mCacheIndex] == 3) {
                            return (T)object2;
                        }
                        if (arrn[this.mCacheIndex] == 2) {
                            arrn[this.mCacheIndex] = 0;
                        }
                        if (arrn[this.mCacheIndex] == 0) {
                            n = 1;
                            arrn[this.mCacheIndex] = 1;
                        }
                        // MONITOREXIT : arrobject
                        if (n != 0) {
                            object2 = null;
                            object = this.createService((ContextImpl)object);
                            arrobject[this.mCacheIndex] = object;
                            arrn[this.mCacheIndex] = 2;
                            arrobject.notifyAll();
                            // MONITOREXIT : arrobject
                            return (T)object;
                        }
                        // MONITORENTER : arrobject
                        break block26;
                        {
                            catch (Throwable throwable22) {
                                break block25;
                            }
                            catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {}
                            {
                                SystemServiceRegistry.onServiceNotFound(serviceNotFoundException);
                            }
                            // MONITORENTER : arrobject
                            arrobject[this.mCacheIndex] = null;
                            arrn[this.mCacheIndex] = 3;
                            arrobject.notifyAll();
                            // MONITOREXIT : arrobject
                            object = object2;
                        }
                        return (T)object;
                    }
                    // MONITORENTER : arrobject
                    arrobject[this.mCacheIndex] = null;
                    arrn[this.mCacheIndex] = 3;
                    arrobject.notifyAll();
                    // MONITOREXIT : arrobject
                    throw throwable22;
                }
                while ((n = arrn[this.mCacheIndex]) < 2) {
                    try {
                        arrobject.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        Log.w("SystemServiceRegistry", "getService() interrupted");
                        Thread.currentThread().interrupt();
                        // MONITOREXIT : arrobject
                        return null;
                    }
                }
                // MONITOREXIT : arrobject
            } while (true);
        }
    }

    static interface ServiceFetcher<T> {
        public T getService(ContextImpl var1);
    }

    static abstract class StaticApplicationContextServiceFetcher<T>
    implements ServiceFetcher<T> {
        private T mCachedInstance;

        StaticApplicationContextServiceFetcher() {
        }

        public abstract T createService(Context var1) throws ServiceManager.ServiceNotFoundException;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final T getService(ContextImpl context) {
            synchronized (this) {
                if (this.mCachedInstance == null) {
                    Context context2 = context.getApplicationContext();
                    if (context2 != null) {
                        context = context2;
                    }
                    try {
                        this.mCachedInstance = this.createService(context);
                    }
                    catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                        SystemServiceRegistry.onServiceNotFound(serviceNotFoundException);
                    }
                }
                context = this.mCachedInstance;
                return (T)context;
            }
        }
    }

    static abstract class StaticServiceFetcher<T>
    implements ServiceFetcher<T> {
        private T mCachedInstance;

        StaticServiceFetcher() {
        }

        public abstract T createService() throws ServiceManager.ServiceNotFoundException;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public final T getService(ContextImpl contextImpl) {
            synchronized (this) {
                contextImpl = this.mCachedInstance;
                if (contextImpl == null) {
                    try {
                        this.mCachedInstance = this.createService();
                    }
                    catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                        SystemServiceRegistry.onServiceNotFound(serviceNotFoundException);
                    }
                }
                contextImpl = this.mCachedInstance;
                return (T)contextImpl;
            }
        }
    }

}

