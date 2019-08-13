/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.CellInfo;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.ims.ImsReasonInfo;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.IPhoneStateListener;
import java.util.ArrayList;
import java.util.List;

public interface ITelephonyRegistry
extends IInterface {
    public void addOnOpportunisticSubscriptionsChangedListener(String var1, IOnSubscriptionsChangedListener var2) throws RemoteException;

    public void addOnSubscriptionsChangedListener(String var1, IOnSubscriptionsChangedListener var2) throws RemoteException;

    @UnsupportedAppUsage
    public void listen(String var1, IPhoneStateListener var2, int var3, boolean var4) throws RemoteException;

    public void listenForSubscriber(int var1, String var2, IPhoneStateListener var3, int var4, boolean var5) throws RemoteException;

    public void notifyActiveDataSubIdChanged(int var1) throws RemoteException;

    public void notifyCallForwardingChanged(boolean var1) throws RemoteException;

    public void notifyCallForwardingChangedForSubscriber(int var1, boolean var2) throws RemoteException;

    public void notifyCallQualityChanged(CallQuality var1, int var2, int var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void notifyCallState(int var1, String var2) throws RemoteException;

    public void notifyCallStateForPhoneId(int var1, int var2, int var3, String var4) throws RemoteException;

    public void notifyCarrierNetworkChange(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void notifyCellInfo(List<CellInfo> var1) throws RemoteException;

    public void notifyCellInfoForSubscriber(int var1, List<CellInfo> var2) throws RemoteException;

    public void notifyCellLocation(Bundle var1) throws RemoteException;

    public void notifyCellLocationForSubscriber(int var1, Bundle var2) throws RemoteException;

    public void notifyDataActivity(int var1) throws RemoteException;

    public void notifyDataActivityForSubscriber(int var1, int var2) throws RemoteException;

    public void notifyDataConnection(int var1, boolean var2, String var3, String var4, LinkProperties var5, NetworkCapabilities var6, int var7, boolean var8) throws RemoteException;

    @UnsupportedAppUsage
    public void notifyDataConnectionFailed(String var1) throws RemoteException;

    public void notifyDataConnectionFailedForSubscriber(int var1, int var2, String var3) throws RemoteException;

    public void notifyDataConnectionForSubscriber(int var1, int var2, int var3, boolean var4, String var5, String var6, LinkProperties var7, NetworkCapabilities var8, int var9, boolean var10) throws RemoteException;

    public void notifyDisconnectCause(int var1, int var2, int var3, int var4) throws RemoteException;

    public void notifyEmergencyNumberList(int var1, int var2) throws RemoteException;

    public void notifyImsDisconnectCause(int var1, ImsReasonInfo var2) throws RemoteException;

    public void notifyMessageWaitingChangedForPhoneId(int var1, int var2, boolean var3) throws RemoteException;

    public void notifyOemHookRawEventForSubscriber(int var1, int var2, byte[] var3) throws RemoteException;

    public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException;

    public void notifyOtaspChanged(int var1, int var2) throws RemoteException;

    public void notifyPhoneCapabilityChanged(PhoneCapability var1) throws RemoteException;

    public void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> var1) throws RemoteException;

    public void notifyPhysicalChannelConfigurationForSubscriber(int var1, List<PhysicalChannelConfig> var2) throws RemoteException;

    public void notifyPreciseCallState(int var1, int var2, int var3, int var4, int var5) throws RemoteException;

    public void notifyPreciseDataConnectionFailed(int var1, int var2, String var3, String var4, int var5) throws RemoteException;

    public void notifyRadioPowerStateChanged(int var1, int var2, int var3) throws RemoteException;

    public void notifyServiceStateForPhoneId(int var1, int var2, ServiceState var3) throws RemoteException;

    public void notifySignalStrengthForPhoneId(int var1, int var2, SignalStrength var3) throws RemoteException;

    public void notifySimActivationStateChangedForPhoneId(int var1, int var2, int var3, int var4) throws RemoteException;

    public void notifySrvccStateChanged(int var1, int var2) throws RemoteException;

    public void notifySubscriptionInfoChanged() throws RemoteException;

    public void notifyUserMobileDataStateChangedForPhoneId(int var1, int var2, boolean var3) throws RemoteException;

    public void removeOnSubscriptionsChangedListener(String var1, IOnSubscriptionsChangedListener var2) throws RemoteException;

    public static class Default
    implements ITelephonyRegistry {
        @Override
        public void addOnOpportunisticSubscriptionsChangedListener(String string2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
        }

        @Override
        public void addOnSubscriptionsChangedListener(String string2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void listen(String string2, IPhoneStateListener iPhoneStateListener, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void listenForSubscriber(int n, String string2, IPhoneStateListener iPhoneStateListener, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void notifyActiveDataSubIdChanged(int n) throws RemoteException {
        }

        @Override
        public void notifyCallForwardingChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void notifyCallForwardingChangedForSubscriber(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void notifyCallQualityChanged(CallQuality callQuality, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void notifyCallState(int n, String string2) throws RemoteException {
        }

        @Override
        public void notifyCallStateForPhoneId(int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public void notifyCarrierNetworkChange(boolean bl) throws RemoteException {
        }

        @Override
        public void notifyCellInfo(List<CellInfo> list) throws RemoteException {
        }

        @Override
        public void notifyCellInfoForSubscriber(int n, List<CellInfo> list) throws RemoteException {
        }

        @Override
        public void notifyCellLocation(Bundle bundle) throws RemoteException {
        }

        @Override
        public void notifyCellLocationForSubscriber(int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void notifyDataActivity(int n) throws RemoteException {
        }

        @Override
        public void notifyDataActivityForSubscriber(int n, int n2) throws RemoteException {
        }

        @Override
        public void notifyDataConnection(int n, boolean bl, String string2, String string3, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n2, boolean bl2) throws RemoteException {
        }

        @Override
        public void notifyDataConnectionFailed(String string2) throws RemoteException {
        }

        @Override
        public void notifyDataConnectionFailedForSubscriber(int n, int n2, String string2) throws RemoteException {
        }

        @Override
        public void notifyDataConnectionForSubscriber(int n, int n2, int n3, boolean bl, String string2, String string3, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n4, boolean bl2) throws RemoteException {
        }

        @Override
        public void notifyDisconnectCause(int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void notifyEmergencyNumberList(int n, int n2) throws RemoteException {
        }

        @Override
        public void notifyImsDisconnectCause(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void notifyMessageWaitingChangedForPhoneId(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void notifyOemHookRawEventForSubscriber(int n, int n2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
        }

        @Override
        public void notifyOtaspChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
        }

        @Override
        public void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> list) throws RemoteException {
        }

        @Override
        public void notifyPhysicalChannelConfigurationForSubscriber(int n, List<PhysicalChannelConfig> list) throws RemoteException {
        }

        @Override
        public void notifyPreciseCallState(int n, int n2, int n3, int n4, int n5) throws RemoteException {
        }

        @Override
        public void notifyPreciseDataConnectionFailed(int n, int n2, String string2, String string3, int n3) throws RemoteException {
        }

        @Override
        public void notifyRadioPowerStateChanged(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void notifyServiceStateForPhoneId(int n, int n2, ServiceState serviceState) throws RemoteException {
        }

        @Override
        public void notifySignalStrengthForPhoneId(int n, int n2, SignalStrength signalStrength) throws RemoteException {
        }

        @Override
        public void notifySimActivationStateChangedForPhoneId(int n, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void notifySrvccStateChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void notifySubscriptionInfoChanged() throws RemoteException {
        }

        @Override
        public void notifyUserMobileDataStateChangedForPhoneId(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void removeOnSubscriptionsChangedListener(String string2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITelephonyRegistry {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ITelephonyRegistry";
        static final int TRANSACTION_addOnOpportunisticSubscriptionsChangedListener = 2;
        static final int TRANSACTION_addOnSubscriptionsChangedListener = 1;
        static final int TRANSACTION_listen = 4;
        static final int TRANSACTION_listenForSubscriber = 5;
        static final int TRANSACTION_notifyActiveDataSubIdChanged = 37;
        static final int TRANSACTION_notifyCallForwardingChanged = 11;
        static final int TRANSACTION_notifyCallForwardingChangedForSubscriber = 12;
        static final int TRANSACTION_notifyCallQualityChanged = 40;
        static final int TRANSACTION_notifyCallState = 6;
        static final int TRANSACTION_notifyCallStateForPhoneId = 7;
        static final int TRANSACTION_notifyCarrierNetworkChange = 34;
        static final int TRANSACTION_notifyCellInfo = 22;
        static final int TRANSACTION_notifyCellInfoForSubscriber = 28;
        static final int TRANSACTION_notifyCellLocation = 19;
        static final int TRANSACTION_notifyCellLocationForSubscriber = 20;
        static final int TRANSACTION_notifyDataActivity = 13;
        static final int TRANSACTION_notifyDataActivityForSubscriber = 14;
        static final int TRANSACTION_notifyDataConnection = 15;
        static final int TRANSACTION_notifyDataConnectionFailed = 17;
        static final int TRANSACTION_notifyDataConnectionFailedForSubscriber = 18;
        static final int TRANSACTION_notifyDataConnectionForSubscriber = 16;
        static final int TRANSACTION_notifyDisconnectCause = 26;
        static final int TRANSACTION_notifyEmergencyNumberList = 39;
        static final int TRANSACTION_notifyImsDisconnectCause = 41;
        static final int TRANSACTION_notifyMessageWaitingChangedForPhoneId = 10;
        static final int TRANSACTION_notifyOemHookRawEventForSubscriber = 31;
        static final int TRANSACTION_notifyOpportunisticSubscriptionInfoChanged = 33;
        static final int TRANSACTION_notifyOtaspChanged = 21;
        static final int TRANSACTION_notifyPhoneCapabilityChanged = 36;
        static final int TRANSACTION_notifyPhysicalChannelConfiguration = 23;
        static final int TRANSACTION_notifyPhysicalChannelConfigurationForSubscriber = 24;
        static final int TRANSACTION_notifyPreciseCallState = 25;
        static final int TRANSACTION_notifyPreciseDataConnectionFailed = 27;
        static final int TRANSACTION_notifyRadioPowerStateChanged = 38;
        static final int TRANSACTION_notifyServiceStateForPhoneId = 8;
        static final int TRANSACTION_notifySignalStrengthForPhoneId = 9;
        static final int TRANSACTION_notifySimActivationStateChangedForPhoneId = 30;
        static final int TRANSACTION_notifySrvccStateChanged = 29;
        static final int TRANSACTION_notifySubscriptionInfoChanged = 32;
        static final int TRANSACTION_notifyUserMobileDataStateChangedForPhoneId = 35;
        static final int TRANSACTION_removeOnSubscriptionsChangedListener = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITelephonyRegistry asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITelephonyRegistry) {
                return (ITelephonyRegistry)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITelephonyRegistry getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 41: {
                    return "notifyImsDisconnectCause";
                }
                case 40: {
                    return "notifyCallQualityChanged";
                }
                case 39: {
                    return "notifyEmergencyNumberList";
                }
                case 38: {
                    return "notifyRadioPowerStateChanged";
                }
                case 37: {
                    return "notifyActiveDataSubIdChanged";
                }
                case 36: {
                    return "notifyPhoneCapabilityChanged";
                }
                case 35: {
                    return "notifyUserMobileDataStateChangedForPhoneId";
                }
                case 34: {
                    return "notifyCarrierNetworkChange";
                }
                case 33: {
                    return "notifyOpportunisticSubscriptionInfoChanged";
                }
                case 32: {
                    return "notifySubscriptionInfoChanged";
                }
                case 31: {
                    return "notifyOemHookRawEventForSubscriber";
                }
                case 30: {
                    return "notifySimActivationStateChangedForPhoneId";
                }
                case 29: {
                    return "notifySrvccStateChanged";
                }
                case 28: {
                    return "notifyCellInfoForSubscriber";
                }
                case 27: {
                    return "notifyPreciseDataConnectionFailed";
                }
                case 26: {
                    return "notifyDisconnectCause";
                }
                case 25: {
                    return "notifyPreciseCallState";
                }
                case 24: {
                    return "notifyPhysicalChannelConfigurationForSubscriber";
                }
                case 23: {
                    return "notifyPhysicalChannelConfiguration";
                }
                case 22: {
                    return "notifyCellInfo";
                }
                case 21: {
                    return "notifyOtaspChanged";
                }
                case 20: {
                    return "notifyCellLocationForSubscriber";
                }
                case 19: {
                    return "notifyCellLocation";
                }
                case 18: {
                    return "notifyDataConnectionFailedForSubscriber";
                }
                case 17: {
                    return "notifyDataConnectionFailed";
                }
                case 16: {
                    return "notifyDataConnectionForSubscriber";
                }
                case 15: {
                    return "notifyDataConnection";
                }
                case 14: {
                    return "notifyDataActivityForSubscriber";
                }
                case 13: {
                    return "notifyDataActivity";
                }
                case 12: {
                    return "notifyCallForwardingChangedForSubscriber";
                }
                case 11: {
                    return "notifyCallForwardingChanged";
                }
                case 10: {
                    return "notifyMessageWaitingChangedForPhoneId";
                }
                case 9: {
                    return "notifySignalStrengthForPhoneId";
                }
                case 8: {
                    return "notifyServiceStateForPhoneId";
                }
                case 7: {
                    return "notifyCallStateForPhoneId";
                }
                case 6: {
                    return "notifyCallState";
                }
                case 5: {
                    return "listenForSubscriber";
                }
                case 4: {
                    return "listen";
                }
                case 3: {
                    return "removeOnSubscriptionsChangedListener";
                }
                case 2: {
                    return "addOnOpportunisticSubscriptionsChangedListener";
                }
                case 1: 
            }
            return "addOnSubscriptionsChangedListener";
        }

        public static boolean setDefaultImpl(ITelephonyRegistry iTelephonyRegistry) {
            if (Proxy.sDefaultImpl == null && iTelephonyRegistry != null) {
                Proxy.sDefaultImpl = iTelephonyRegistry;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyImsDisconnectCause(n, (ImsReasonInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        CallQuality callQuality = ((Parcel)object).readInt() != 0 ? CallQuality.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyCallQualityChanged(callQuality, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyEmergencyNumberList(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyRadioPowerStateChanged(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyActiveDataSubIdChanged(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneCapability.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyPhoneCapabilityChanged((PhoneCapability)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.notifyUserMobileDataStateChangedForPhoneId(n, n2, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.notifyCarrierNetworkChange(bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyOpportunisticSubscriptionInfoChanged();
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifySubscriptionInfoChanged();
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyOemHookRawEventForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifySimActivationStateChangedForPhoneId(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifySrvccStateChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyCellInfoForSubscriber(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(CellInfo.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPreciseDataConnectionFailed(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDisconnectCause(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPreciseCallState(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPhysicalChannelConfigurationForSubscriber(((Parcel)object).readInt(), ((Parcel)object).createTypedArrayList(PhysicalChannelConfig.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPhysicalChannelConfiguration(((Parcel)object).createTypedArrayList(PhysicalChannelConfig.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyCellInfo(((Parcel)object).createTypedArrayList(CellInfo.CREATOR));
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyOtaspChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyCellLocationForSubscriber(n, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyCellLocation((Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDataConnectionFailedForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDataConnectionFailed(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        int n3 = ((Parcel)object).readInt();
                        int n4 = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        bl6 = ((Parcel)object).readInt() != 0;
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        LinkProperties linkProperties = ((Parcel)object).readInt() != 0 ? LinkProperties.CREATOR.createFromParcel((Parcel)object) : null;
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        this.notifyDataConnectionForSubscriber(n3, n4, n2, bl6, string2, string3, linkProperties, networkCapabilities, n, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = ((Parcel)object).readInt() != 0;
                        String string4 = ((Parcel)object).readString();
                        String string5 = ((Parcel)object).readString();
                        LinkProperties linkProperties = ((Parcel)object).readInt() != 0 ? LinkProperties.CREATOR.createFromParcel((Parcel)object) : null;
                        NetworkCapabilities networkCapabilities = ((Parcel)object).readInt() != 0 ? NetworkCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        n2 = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        this.notifyDataConnection(n, bl6, string4, string5, linkProperties, networkCapabilities, n2, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDataActivityForSubscriber(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDataActivity(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.notifyCallForwardingChangedForSubscriber(n, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.notifyCallForwardingChanged(bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        bl6 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.notifyMessageWaitingChangedForPhoneId(n2, n, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? SignalStrength.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifySignalStrengthForPhoneId(n2, n, (SignalStrength)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ServiceState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyServiceStateForPhoneId(n2, n, (ServiceState)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyCallStateForPhoneId(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyCallState(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string6 = ((Parcel)object).readString();
                        IPhoneStateListener iPhoneStateListener = IPhoneStateListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n2 = ((Parcel)object).readInt();
                        bl6 = ((Parcel)object).readInt() != 0;
                        this.listenForSubscriber(n, string6, iPhoneStateListener, n2, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        IPhoneStateListener iPhoneStateListener = IPhoneStateListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        bl6 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.listen(string7, iPhoneStateListener, n, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeOnSubscriptionsChangedListener(((Parcel)object).readString(), IOnSubscriptionsChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addOnOpportunisticSubscriptionsChangedListener(((Parcel)object).readString(), IOnSubscriptionsChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.addOnSubscriptionsChangedListener(((Parcel)object).readString(), IOnSubscriptionsChangedListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITelephonyRegistry {
            public static ITelephonyRegistry sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addOnOpportunisticSubscriptionsChangedListener(String string2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iOnSubscriptionsChangedListener != null ? iOnSubscriptionsChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnOpportunisticSubscriptionsChangedListener(string2, iOnSubscriptionsChangedListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addOnSubscriptionsChangedListener(String string2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iOnSubscriptionsChangedListener != null ? iOnSubscriptionsChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnSubscriptionsChangedListener(string2, iOnSubscriptionsChangedListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void listen(String string2, IPhoneStateListener iPhoneStateListener, int n, boolean bl) throws RemoteException {
                IBinder iBinder;
                Parcel parcel;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel2.writeString(string2);
                    if (iPhoneStateListener == null) break block8;
                    iBinder = iPhoneStateListener.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                parcel2.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().listen(string2, iPhoneStateListener, n, bl);
                        return;
                    }
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void listenForSubscriber(int n, String string2, IPhoneStateListener iPhoneStateListener, int n2, boolean bl) throws RemoteException {
                IBinder iBinder;
                Parcel parcel;
                Parcel parcel2;
                block8 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel2.writeInt(n);
                    parcel2.writeString(string2);
                    if (iPhoneStateListener == null) break block8;
                    iBinder = iPhoneStateListener.asBinder();
                }
                iBinder = null;
                parcel2.writeStrongBinder(iBinder);
                parcel2.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel2.writeInt(n3);
                    if (!this.mRemote.transact(5, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().listenForSubscriber(n, string2, iPhoneStateListener, n2, bl);
                        return;
                    }
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void notifyActiveDataSubIdChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyActiveDataSubIdChanged(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCallForwardingChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCallForwardingChanged(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCallForwardingChangedForSubscriber(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCallForwardingChangedForSubscriber(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCallQualityChanged(CallQuality callQuality, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callQuality != null) {
                        parcel.writeInt(1);
                        callQuality.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCallQualityChanged(callQuality, n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCallState(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCallState(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCallStateForPhoneId(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCallStateForPhoneId(n, n2, n3, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCarrierNetworkChange(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCarrierNetworkChange(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCellInfo(List<CellInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCellInfo(list);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCellInfoForSubscriber(int n, List<CellInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCellInfoForSubscriber(n, list);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCellLocation(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCellLocation(bundle);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyCellLocationForSubscriber(int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyCellLocationForSubscriber(n, bundle);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyDataActivity(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDataActivity(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyDataActivityForSubscriber(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDataActivityForSubscriber(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
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
            public void notifyDataConnection(int n, boolean bl, String string2, String string3, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n2, boolean bl2) throws RemoteException {
                Parcel parcel;
                void var3_7;
                Parcel parcel2;
                block12 : {
                    int n3;
                    int n4;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                        n4 = 1;
                        n3 = bl ? 1 : 0;
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        if (linkProperties != null) {
                            parcel.writeInt(1);
                            linkProperties.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        if (networkCapabilities != null) {
                            parcel.writeInt(1);
                            networkCapabilities.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n2);
                        n3 = bl2 ? n4 : 0;
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().notifyDataConnection(n, bl, string2, string3, linkProperties, networkCapabilities, n2, bl2);
                            parcel2.recycle();
                            parcel.recycle();
                            return;
                        }
                        parcel2.readException();
                        parcel2.recycle();
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var3_7;
            }

            @Override
            public void notifyDataConnectionFailed(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDataConnectionFailed(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyDataConnectionFailedForSubscriber(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDataConnectionFailedForSubscriber(n, n2, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void notifyDataConnectionForSubscriber(int n, int n2, int n3, boolean bl, String string2, String string3, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n4, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    int n5 = 1;
                    int n6 = bl ? 1 : 0;
                    parcel.writeInt(n6);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (linkProperties != null) {
                        parcel.writeInt(1);
                        linkProperties.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (networkCapabilities != null) {
                        parcel.writeInt(1);
                        networkCapabilities.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n4);
                    n6 = bl2 ? n5 : 0;
                    parcel.writeInt(n6);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDataConnectionForSubscriber(n, n2, n3, bl, string2, string3, linkProperties, networkCapabilities, n4, bl2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyDisconnectCause(int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDisconnectCause(n, n2, n3, n4);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyEmergencyNumberList(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyEmergencyNumberList(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyImsDisconnectCause(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyImsDisconnectCause(n, imsReasonInfo);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyMessageWaitingChangedForPhoneId(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyMessageWaitingChangedForPhoneId(n, n2, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyOemHookRawEventForSubscriber(int n, int n2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyOemHookRawEventForSubscriber(n, n2, arrby);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyOpportunisticSubscriptionInfoChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyOpportunisticSubscriptionInfoChanged();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyOtaspChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyOtaspChanged(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneCapability != null) {
                        parcel.writeInt(1);
                        phoneCapability.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPhoneCapabilityChanged(phoneCapability);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPhysicalChannelConfiguration(List<PhysicalChannelConfig> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPhysicalChannelConfiguration(list);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPhysicalChannelConfigurationForSubscriber(int n, List<PhysicalChannelConfig> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPhysicalChannelConfigurationForSubscriber(n, list);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPreciseCallState(int n, int n2, int n3, int n4, int n5) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    parcel.writeInt(n5);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPreciseCallState(n, n2, n3, n4, n5);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPreciseDataConnectionFailed(int n, int n2, String string2, String string3, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPreciseDataConnectionFailed(n, n2, string2, string3, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyRadioPowerStateChanged(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyRadioPowerStateChanged(n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyServiceStateForPhoneId(int n, int n2, ServiceState serviceState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (serviceState != null) {
                        parcel.writeInt(1);
                        serviceState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyServiceStateForPhoneId(n, n2, serviceState);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifySignalStrengthForPhoneId(int n, int n2, SignalStrength signalStrength) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (signalStrength != null) {
                        parcel.writeInt(1);
                        signalStrength.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySignalStrengthForPhoneId(n, n2, signalStrength);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifySimActivationStateChangedForPhoneId(int n, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySimActivationStateChangedForPhoneId(n, n2, n3, n4);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifySrvccStateChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySrvccStateChanged(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifySubscriptionInfoChanged() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySubscriptionInfoChanged();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyUserMobileDataStateChangedForPhoneId(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyUserMobileDataStateChangedForPhoneId(n, n2, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeOnSubscriptionsChangedListener(String string2, IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iOnSubscriptionsChangedListener != null ? iOnSubscriptionsChangedListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnSubscriptionsChangedListener(string2, iOnSubscriptionsChangedListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

