/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.XmlResourceParser
 *  android.database.Cursor
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IDeviceIdleController
 *  android.os.IDeviceIdleController$Stub
 *  android.os.Looper
 *  android.os.ServiceManager
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStatVfs
 *  android.telephony.Rlog
 *  android.text.TextUtils
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$TelephonyComponentFactory
 *  com.android.internal.telephony.-$$Lambda$TelephonyComponentFactory$InjectedComponents
 *  com.android.internal.telephony.-$$Lambda$TelephonyComponentFactory$InjectedComponents$09rMKC8001jAR0zFrzzlPx26Xjs
 *  com.android.internal.telephony.-$$Lambda$TelephonyComponentFactory$InjectedComponents$UYUq9z2WZwxqOLXquU0tTNN9wAs
 *  dalvik.system.PathClassLoader
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package com.android.internal.telephony;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.IDeviceIdleController;
import android.os.Looper;
import android.os.ServiceManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStatVfs;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.AppSmsManager;
import com.android.internal.telephony.CarrierActionAgent;
import com.android.internal.telephony.CarrierResolver;
import com.android.internal.telephony.CarrierSignalAgent;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.DeviceStateMonitor;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.IccPhoneBookInterfaceManager;
import com.android.internal.telephony.IccSmsInterfaceManager;
import com.android.internal.telephony.InboundSmsTracker;
import com.android.internal.telephony.LocaleTracker;
import com.android.internal.telephony.NewNitzStateMachine;
import com.android.internal.telephony.NitzStateMachine;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SimActivationTracker;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.SmsUsageMonitor;
import com.android.internal.telephony.WspTypeDecoder;
import com.android.internal.telephony._$$Lambda$TelephonyComponentFactory$InjectedComponents$09rMKC8001jAR0zFrzzlPx26Xjs;
import com.android.internal.telephony._$$Lambda$TelephonyComponentFactory$InjectedComponents$DKjB_mCxFOHomOyKLPFU9_9Dywc;
import com.android.internal.telephony._$$Lambda$TelephonyComponentFactory$InjectedComponents$UYUq9z2WZwxqOLXquU0tTNN9wAs;
import com.android.internal.telephony._$$Lambda$TelephonyComponentFactory$InjectedComponents$eUdIxJOKoyVP5UmFJtWXBUO93Qk;
import com.android.internal.telephony._$$Lambda$TelephonyComponentFactory$InjectedComponents$nLdppNQT1Bv7QyIU3LwAwVD2K60;
import com.android.internal.telephony.cdma.CdmaSubscriptionSourceManager;
import com.android.internal.telephony.cdma.EriManager;
import com.android.internal.telephony.dataconnection.DataEnabledSettings;
import com.android.internal.telephony.dataconnection.DcTracker;
import com.android.internal.telephony.dataconnection.TransportManager;
import com.android.internal.telephony.emergency.EmergencyNumberTracker;
import com.android.internal.telephony.imsphone.ImsExternalCallTracker;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccProfile;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TelephonyComponentFactory {
    private static final String TAG = TelephonyComponentFactory.class.getSimpleName();
    static final boolean USE_NEW_NITZ_STATE_MACHINE = true;
    private static TelephonyComponentFactory sInstance;
    private InjectedComponents mInjectedComponents;

    public static TelephonyComponentFactory getInstance() {
        if (sInstance == null) {
            sInstance = new TelephonyComponentFactory();
        }
        return sInstance;
    }

    public CdmaSubscriptionSourceManager getCdmaSubscriptionSourceManagerInstance(Context context, CommandsInterface commandsInterface, Handler handler, int n, Object object) {
        return CdmaSubscriptionSourceManager.getInstance(context, commandsInterface, handler, n, object);
    }

    public IDeviceIdleController getIDeviceIdleController() {
        return IDeviceIdleController.Stub.asInterface((IBinder)ServiceManager.getService((String)"deviceidle"));
    }

    public TelephonyComponentFactory inject(String string) {
        InjectedComponents injectedComponents = this.mInjectedComponents;
        if (injectedComponents != null && injectedComponents.isComponentInjected(string)) {
            return this.mInjectedComponents.mInjectedInstance;
        }
        return sInstance;
    }

    public void injectTheComponentFactory(XmlResourceParser object) {
        if (this.mInjectedComponents != null) {
            Rlog.d((String)TAG, (String)"Already injected.");
            return;
        }
        if (object != null) {
            this.mInjectedComponents = new InjectedComponents();
            this.mInjectedComponents.parseXml((XmlPullParser)object);
            this.mInjectedComponents.makeInjectedInstance();
            boolean bl = TextUtils.isEmpty((CharSequence)this.mInjectedComponents.getValidatedPaths());
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Total components injected: ");
            int n = bl ^ true ? this.mInjectedComponents.mComponentNames.size() : 0;
            stringBuilder.append(n);
            Rlog.d((String)object, (String)stringBuilder.toString());
        }
    }

    public AppSmsManager makeAppSmsManager(Context context) {
        return new AppSmsManager(context);
    }

    public CarrierActionAgent makeCarrierActionAgent(Phone phone) {
        return new CarrierActionAgent(phone);
    }

    public CarrierResolver makeCarrierResolver(Phone phone) {
        return new CarrierResolver(phone);
    }

    public CarrierSignalAgent makeCarrierSignalAgent(Phone phone) {
        return new CarrierSignalAgent(phone);
    }

    public DataEnabledSettings makeDataEnabledSettings(Phone phone) {
        return new DataEnabledSettings(phone);
    }

    public DcTracker makeDcTracker(Phone phone, int n) {
        return new DcTracker(phone, n);
    }

    public DeviceStateMonitor makeDeviceStateMonitor(Phone phone) {
        return new DeviceStateMonitor(phone);
    }

    public EmergencyNumberTracker makeEmergencyNumberTracker(Phone phone, CommandsInterface commandsInterface) {
        return new EmergencyNumberTracker(phone, commandsInterface);
    }

    public EriManager makeEriManager(Phone phone, int n) {
        return new EriManager(phone, n);
    }

    public GsmCdmaCallTracker makeGsmCdmaCallTracker(GsmCdmaPhone gsmCdmaPhone) {
        return new GsmCdmaCallTracker(gsmCdmaPhone);
    }

    public IccPhoneBookInterfaceManager makeIccPhoneBookInterfaceManager(Phone phone) {
        return new IccPhoneBookInterfaceManager(phone);
    }

    public IccSmsInterfaceManager makeIccSmsInterfaceManager(Phone phone) {
        return new IccSmsInterfaceManager(phone);
    }

    public ImsExternalCallTracker makeImsExternalCallTracker(ImsPhone imsPhone) {
        return new ImsExternalCallTracker(imsPhone);
    }

    public ImsPhoneCallTracker makeImsPhoneCallTracker(ImsPhone imsPhone) {
        return new ImsPhoneCallTracker(imsPhone);
    }

    public InboundSmsTracker makeInboundSmsTracker(Cursor cursor, boolean bl) {
        return new InboundSmsTracker(cursor, bl);
    }

    public InboundSmsTracker makeInboundSmsTracker(byte[] arrby, long l, int n, boolean bl, String string, String string2, int n2, int n3, int n4, boolean bl2, String string3, boolean bl3) {
        return new InboundSmsTracker(arrby, l, n, bl, string, string2, n2, n3, n4, bl2, string3, bl3);
    }

    public InboundSmsTracker makeInboundSmsTracker(byte[] arrby, long l, int n, boolean bl, boolean bl2, String string, String string2, String string3, boolean bl3) {
        return new InboundSmsTracker(arrby, l, n, bl, bl2, string, string2, string3, bl3);
    }

    public LocaleTracker makeLocaleTracker(Phone phone, NitzStateMachine nitzStateMachine, Looper looper) {
        return new LocaleTracker(phone, nitzStateMachine, looper);
    }

    public NitzStateMachine makeNitzStateMachine(GsmCdmaPhone gsmCdmaPhone) {
        return new NewNitzStateMachine(gsmCdmaPhone);
    }

    public ServiceStateTracker makeServiceStateTracker(GsmCdmaPhone gsmCdmaPhone, CommandsInterface commandsInterface) {
        return new ServiceStateTracker(gsmCdmaPhone, commandsInterface);
    }

    public SimActivationTracker makeSimActivationTracker(Phone phone) {
        return new SimActivationTracker(phone);
    }

    public SmsStorageMonitor makeSmsStorageMonitor(Phone phone) {
        return new SmsStorageMonitor(phone);
    }

    public SmsUsageMonitor makeSmsUsageMonitor(Context context) {
        return new SmsUsageMonitor(context);
    }

    public TransportManager makeTransportManager(Phone phone) {
        return new TransportManager(phone);
    }

    public UiccProfile makeUiccProfile(Context context, CommandsInterface commandsInterface, IccCardStatus iccCardStatus, int n, UiccCard uiccCard, Object object) {
        return new UiccProfile(context, commandsInterface, iccCardStatus, n, uiccCard, object);
    }

    public WspTypeDecoder makeWspTypeDecoder(byte[] arrby) {
        return new WspTypeDecoder(arrby);
    }

    private static class InjectedComponents {
        private static final String ATTRIBUTE_JAR = "jar";
        private static final String ATTRIBUTE_PACKAGE = "package";
        private static final String PRODUCT = "/product/";
        private static final String SYSTEM = "/system/";
        private static final String TAG_COMPONENT = "component";
        private static final String TAG_COMPONENTS = "components";
        private static final String TAG_INJECTION = "injection";
        private final Set<String> mComponentNames = new HashSet<String>();
        private TelephonyComponentFactory mInjectedInstance;
        private String mJarPath;
        private String mPackageName;

        private InjectedComponents() {
        }

        private String getValidatedPaths() {
            if (!TextUtils.isEmpty((CharSequence)this.mPackageName) && !TextUtils.isEmpty((CharSequence)this.mJarPath)) {
                return Arrays.stream(this.mJarPath.split(File.pathSeparator)).filter((Predicate<String>)_$$Lambda$TelephonyComponentFactory$InjectedComponents$09rMKC8001jAR0zFrzzlPx26Xjs.INSTANCE).filter((Predicate<String>)_$$Lambda$TelephonyComponentFactory$InjectedComponents$UYUq9z2WZwxqOLXquU0tTNN9wAs.INSTANCE).distinct().collect(Collectors.joining(File.pathSeparator));
            }
            return null;
        }

        private boolean isComponentInjected(String string) {
            if (this.mInjectedInstance == null) {
                return false;
            }
            return this.mComponentNames.contains(string);
        }

        static /* synthetic */ boolean lambda$getValidatedPaths$0(String string) {
            boolean bl = string.startsWith(SYSTEM) || string.startsWith(PRODUCT);
            return bl;
        }

        static /* synthetic */ boolean lambda$getValidatedPaths$1(String string) {
            long l;
            int n;
            boolean bl = false;
            try {
                l = Os.statvfs((String)string).f_flag;
                n = OsConstants.ST_RDONLY;
            }
            catch (ErrnoException errnoException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Injection jar is not protected , path: ");
                stringBuilder.append(string);
                stringBuilder.append(errnoException.getMessage());
                Rlog.w((String)string2, (String)stringBuilder.toString());
                return false;
            }
            if ((l & (long)n) != 0L) {
                bl = true;
            }
            return bl;
        }

        private void makeInjectedInstance() {
            CharSequence charSequence = this.getValidatedPaths();
            CharSequence charSequence2 = TAG;
            CharSequence charSequence3 = new StringBuilder();
            charSequence3.append("validated paths: ");
            charSequence3.append((String)charSequence);
            Rlog.d((String)charSequence2, (String)charSequence3.toString());
            if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                try {
                    charSequence2 = new PathClassLoader((String)charSequence, ClassLoader.getSystemClassLoader());
                    this.mInjectedInstance = (TelephonyComponentFactory)charSequence2.loadClass(this.mPackageName).newInstance();
                }
                catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
                    charSequence3 = TAG;
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("injection failed: ");
                    ((StringBuilder)charSequence2).append(reflectiveOperationException.getMessage());
                    Rlog.e((String)charSequence3, (String)((StringBuilder)charSequence2).toString());
                }
                catch (ClassNotFoundException classNotFoundException) {
                    charSequence3 = TAG;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("failed: ");
                    ((StringBuilder)charSequence).append(classNotFoundException.getMessage());
                    Rlog.e((String)charSequence3, (String)((StringBuilder)charSequence).toString());
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void parseComponent(XmlPullParser xmlPullParser) {
            try {
                int n;
                int n2 = xmlPullParser.getDepth();
                while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
                    if (n != 4) continue;
                    this.mComponentNames.add(xmlPullParser.getText());
                }
                return;
            }
            catch (IOException | XmlPullParserException throwable) {
                Rlog.e((String)TAG, (String)"Failed to parse the component.", (Throwable)throwable);
            }
        }

        private void parseComponents(XmlPullParser xmlPullParser) {
            this.parseXmlByTag(xmlPullParser, true, new _$$Lambda$TelephonyComponentFactory$InjectedComponents$DKjB_mCxFOHomOyKLPFU9_9Dywc(this), TAG_COMPONENT);
        }

        private void parseInjection(XmlPullParser xmlPullParser) {
            this.parseXmlByTag(xmlPullParser, false, new _$$Lambda$TelephonyComponentFactory$InjectedComponents$eUdIxJOKoyVP5UmFJtWXBUO93Qk(this), TAG_COMPONENTS);
        }

        private void parseXml(XmlPullParser xmlPullParser) {
            this.parseXmlByTag(xmlPullParser, false, new _$$Lambda$TelephonyComponentFactory$InjectedComponents$nLdppNQT1Bv7QyIU3LwAwVD2K60(this), TAG_INJECTION);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void parseXmlByTag(XmlPullParser xmlPullParser, boolean bl, Consumer<XmlPullParser> object, String string) {
            try {
                int n;
                int n2 = xmlPullParser.getDepth();
                while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
                    if (n != 2 || !string.equals(xmlPullParser.getName())) continue;
                    object.accept(xmlPullParser);
                    if (bl) continue;
                    return;
                }
                return;
            }
            catch (IOException | XmlPullParserException throwable) {
                object = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to parse or find tag: ");
                stringBuilder.append(string);
                Rlog.e((String)object, (String)stringBuilder.toString(), (Throwable)throwable);
            }
        }

        private void setAttributes(XmlPullParser xmlPullParser) {
            for (int i = 0; i < xmlPullParser.getAttributeCount(); ++i) {
                String string = xmlPullParser.getAttributeName(i);
                String string2 = xmlPullParser.getAttributeValue(i);
                if (ATTRIBUTE_PACKAGE.equals(string)) {
                    this.mPackageName = string2;
                    continue;
                }
                if (!ATTRIBUTE_JAR.equals(string)) continue;
                this.mJarPath = string2;
            }
        }

        public /* synthetic */ void lambda$parseComponents$4$TelephonyComponentFactory$InjectedComponents(XmlPullParser xmlPullParser) {
            this.parseComponent(xmlPullParser);
        }

        public /* synthetic */ void lambda$parseInjection$3$TelephonyComponentFactory$InjectedComponents(XmlPullParser xmlPullParser) {
            this.parseComponents(xmlPullParser);
        }

        public /* synthetic */ void lambda$parseXml$2$TelephonyComponentFactory$InjectedComponents(XmlPullParser xmlPullParser) {
            this.setAttributes(xmlPullParser);
            this.parseInjection(xmlPullParser);
        }
    }

}

