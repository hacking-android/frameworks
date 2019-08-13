/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.CallAttributes;
import android.telephony.CellInfo;
import android.telephony.DataConnectionRealTimeInfo;
import android.telephony.PhoneCapability;
import android.telephony.PhysicalChannelConfig;
import android.telephony.PreciseCallState;
import android.telephony.PreciseDataConnectionState;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.ims.ImsReasonInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IPhoneStateListener
extends IInterface {
    public void onActiveDataSubIdChanged(int var1) throws RemoteException;

    public void onCallAttributesChanged(CallAttributes var1) throws RemoteException;

    public void onCallDisconnectCauseChanged(int var1, int var2) throws RemoteException;

    public void onCallForwardingIndicatorChanged(boolean var1) throws RemoteException;

    public void onCallStateChanged(int var1, String var2) throws RemoteException;

    public void onCarrierNetworkChange(boolean var1) throws RemoteException;

    public void onCellInfoChanged(List<CellInfo> var1) throws RemoteException;

    public void onCellLocationChanged(Bundle var1) throws RemoteException;

    public void onDataActivationStateChanged(int var1) throws RemoteException;

    public void onDataActivity(int var1) throws RemoteException;

    public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo var1) throws RemoteException;

    public void onDataConnectionStateChanged(int var1, int var2) throws RemoteException;

    public void onEmergencyNumberListChanged(Map var1) throws RemoteException;

    public void onImsCallDisconnectCauseChanged(ImsReasonInfo var1) throws RemoteException;

    public void onMessageWaitingIndicatorChanged(boolean var1) throws RemoteException;

    public void onOemHookRawEvent(byte[] var1) throws RemoteException;

    public void onOtaspChanged(int var1) throws RemoteException;

    public void onPhoneCapabilityChanged(PhoneCapability var1) throws RemoteException;

    public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> var1) throws RemoteException;

    public void onPreciseCallStateChanged(PreciseCallState var1) throws RemoteException;

    public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState var1) throws RemoteException;

    public void onRadioPowerStateChanged(int var1) throws RemoteException;

    public void onServiceStateChanged(ServiceState var1) throws RemoteException;

    public void onSignalStrengthChanged(int var1) throws RemoteException;

    public void onSignalStrengthsChanged(SignalStrength var1) throws RemoteException;

    public void onSrvccStateChanged(int var1) throws RemoteException;

    public void onUserMobileDataStateChanged(boolean var1) throws RemoteException;

    public void onVoiceActivationStateChanged(int var1) throws RemoteException;

    public static class Default
    implements IPhoneStateListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActiveDataSubIdChanged(int n) throws RemoteException {
        }

        @Override
        public void onCallAttributesChanged(CallAttributes callAttributes) throws RemoteException {
        }

        @Override
        public void onCallDisconnectCauseChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onCallForwardingIndicatorChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onCallStateChanged(int n, String string2) throws RemoteException {
        }

        @Override
        public void onCarrierNetworkChange(boolean bl) throws RemoteException {
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> list) throws RemoteException {
        }

        @Override
        public void onCellLocationChanged(Bundle bundle) throws RemoteException {
        }

        @Override
        public void onDataActivationStateChanged(int n) throws RemoteException {
        }

        @Override
        public void onDataActivity(int n) throws RemoteException {
        }

        @Override
        public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws RemoteException {
        }

        @Override
        public void onDataConnectionStateChanged(int n, int n2) throws RemoteException {
        }

        @Override
        public void onEmergencyNumberListChanged(Map map) throws RemoteException {
        }

        @Override
        public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void onMessageWaitingIndicatorChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onOemHookRawEvent(byte[] arrby) throws RemoteException {
        }

        @Override
        public void onOtaspChanged(int n) throws RemoteException {
        }

        @Override
        public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
        }

        @Override
        public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) throws RemoteException {
        }

        @Override
        public void onPreciseCallStateChanged(PreciseCallState preciseCallState) throws RemoteException {
        }

        @Override
        public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
        }

        @Override
        public void onRadioPowerStateChanged(int n) throws RemoteException {
        }

        @Override
        public void onServiceStateChanged(ServiceState serviceState) throws RemoteException {
        }

        @Override
        public void onSignalStrengthChanged(int n) throws RemoteException {
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException {
        }

        @Override
        public void onSrvccStateChanged(int n) throws RemoteException {
        }

        @Override
        public void onUserMobileDataStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onVoiceActivationStateChanged(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPhoneStateListener {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IPhoneStateListener";
        static final int TRANSACTION_onActiveDataSubIdChanged = 23;
        static final int TRANSACTION_onCallAttributesChanged = 25;
        static final int TRANSACTION_onCallDisconnectCauseChanged = 27;
        static final int TRANSACTION_onCallForwardingIndicatorChanged = 4;
        static final int TRANSACTION_onCallStateChanged = 6;
        static final int TRANSACTION_onCarrierNetworkChange = 20;
        static final int TRANSACTION_onCellInfoChanged = 12;
        static final int TRANSACTION_onCellLocationChanged = 5;
        static final int TRANSACTION_onDataActivationStateChanged = 18;
        static final int TRANSACTION_onDataActivity = 8;
        static final int TRANSACTION_onDataConnectionRealTimeInfoChanged = 15;
        static final int TRANSACTION_onDataConnectionStateChanged = 7;
        static final int TRANSACTION_onEmergencyNumberListChanged = 26;
        static final int TRANSACTION_onImsCallDisconnectCauseChanged = 28;
        static final int TRANSACTION_onMessageWaitingIndicatorChanged = 3;
        static final int TRANSACTION_onOemHookRawEvent = 19;
        static final int TRANSACTION_onOtaspChanged = 11;
        static final int TRANSACTION_onPhoneCapabilityChanged = 22;
        static final int TRANSACTION_onPhysicalChannelConfigurationChanged = 10;
        static final int TRANSACTION_onPreciseCallStateChanged = 13;
        static final int TRANSACTION_onPreciseDataConnectionStateChanged = 14;
        static final int TRANSACTION_onRadioPowerStateChanged = 24;
        static final int TRANSACTION_onServiceStateChanged = 1;
        static final int TRANSACTION_onSignalStrengthChanged = 2;
        static final int TRANSACTION_onSignalStrengthsChanged = 9;
        static final int TRANSACTION_onSrvccStateChanged = 16;
        static final int TRANSACTION_onUserMobileDataStateChanged = 21;
        static final int TRANSACTION_onVoiceActivationStateChanged = 17;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPhoneStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPhoneStateListener) {
                return (IPhoneStateListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPhoneStateListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 28: {
                    return "onImsCallDisconnectCauseChanged";
                }
                case 27: {
                    return "onCallDisconnectCauseChanged";
                }
                case 26: {
                    return "onEmergencyNumberListChanged";
                }
                case 25: {
                    return "onCallAttributesChanged";
                }
                case 24: {
                    return "onRadioPowerStateChanged";
                }
                case 23: {
                    return "onActiveDataSubIdChanged";
                }
                case 22: {
                    return "onPhoneCapabilityChanged";
                }
                case 21: {
                    return "onUserMobileDataStateChanged";
                }
                case 20: {
                    return "onCarrierNetworkChange";
                }
                case 19: {
                    return "onOemHookRawEvent";
                }
                case 18: {
                    return "onDataActivationStateChanged";
                }
                case 17: {
                    return "onVoiceActivationStateChanged";
                }
                case 16: {
                    return "onSrvccStateChanged";
                }
                case 15: {
                    return "onDataConnectionRealTimeInfoChanged";
                }
                case 14: {
                    return "onPreciseDataConnectionStateChanged";
                }
                case 13: {
                    return "onPreciseCallStateChanged";
                }
                case 12: {
                    return "onCellInfoChanged";
                }
                case 11: {
                    return "onOtaspChanged";
                }
                case 10: {
                    return "onPhysicalChannelConfigurationChanged";
                }
                case 9: {
                    return "onSignalStrengthsChanged";
                }
                case 8: {
                    return "onDataActivity";
                }
                case 7: {
                    return "onDataConnectionStateChanged";
                }
                case 6: {
                    return "onCallStateChanged";
                }
                case 5: {
                    return "onCellLocationChanged";
                }
                case 4: {
                    return "onCallForwardingIndicatorChanged";
                }
                case 3: {
                    return "onMessageWaitingIndicatorChanged";
                }
                case 2: {
                    return "onSignalStrengthChanged";
                }
                case 1: 
            }
            return "onServiceStateChanged";
        }

        public static boolean setDefaultImpl(IPhoneStateListener iPhoneStateListener) {
            if (Proxy.sDefaultImpl == null && iPhoneStateListener != null) {
                Proxy.sDefaultImpl = iPhoneStateListener;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onImsCallDisconnectCauseChanged((ImsReasonInfo)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onCallDisconnectCauseChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onEmergencyNumberListChanged(((Parcel)object).readHashMap(this.getClass().getClassLoader()));
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CallAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCallAttributesChanged((CallAttributes)object);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRadioPowerStateChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onActiveDataSubIdChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneCapability.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPhoneCapabilityChanged((PhoneCapability)object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onUserMobileDataStateChanged(bl4);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onCarrierNetworkChange(bl4);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onOemHookRawEvent(((Parcel)object).createByteArray());
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDataActivationStateChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onVoiceActivationStateChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSrvccStateChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? DataConnectionRealTimeInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onDataConnectionRealTimeInfoChanged((DataConnectionRealTimeInfo)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PreciseDataConnectionState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPreciseDataConnectionStateChanged((PreciseDataConnectionState)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PreciseCallState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPreciseCallStateChanged((PreciseCallState)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onCellInfoChanged(((Parcel)object).createTypedArrayList(CellInfo.CREATOR));
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onOtaspChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPhysicalChannelConfigurationChanged(((Parcel)object).createTypedArrayList(PhysicalChannelConfig.CREATOR));
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SignalStrength.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSignalStrengthsChanged((SignalStrength)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDataActivity(((Parcel)object).readInt());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDataConnectionStateChanged(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onCallStateChanged(((Parcel)object).readInt(), ((Parcel)object).readString());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCellLocationChanged((Bundle)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onCallForwardingIndicatorChanged(bl4);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.onMessageWaitingIndicatorChanged(bl4);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onSignalStrengthChanged(((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ServiceState.CREATOR.createFromParcel((Parcel)object) : null;
                this.onServiceStateChanged((ServiceState)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPhoneStateListener {
            public static IPhoneStateListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onActiveDataSubIdChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActiveDataSubIdChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCallAttributesChanged(CallAttributes callAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callAttributes != null) {
                        parcel.writeInt(1);
                        callAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallAttributesChanged(callAttributes);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCallDisconnectCauseChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallDisconnectCauseChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCallForwardingIndicatorChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallForwardingIndicatorChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCallStateChanged(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallStateChanged(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCarrierNetworkChange(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCarrierNetworkChange(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCellInfoChanged(List<CellInfo> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCellInfoChanged(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCellLocationChanged(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCellLocationChanged(bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDataActivationStateChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataActivationStateChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDataActivity(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataActivity(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDataConnectionRealTimeInfoChanged(DataConnectionRealTimeInfo dataConnectionRealTimeInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (dataConnectionRealTimeInfo != null) {
                        parcel.writeInt(1);
                        dataConnectionRealTimeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataConnectionRealTimeInfoChanged(dataConnectionRealTimeInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDataConnectionStateChanged(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDataConnectionStateChanged(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEmergencyNumberListChanged(Map map) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeMap(map);
                    if (!this.mRemote.transact(26, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEmergencyNumberListChanged(map);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onImsCallDisconnectCauseChanged(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onImsCallDisconnectCauseChanged(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onMessageWaitingIndicatorChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMessageWaitingIndicatorChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onOemHookRawEvent(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onOemHookRawEvent(arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onOtaspChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onOtaspChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPhoneCapabilityChanged(PhoneCapability phoneCapability) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneCapability != null) {
                        parcel.writeInt(1);
                        phoneCapability.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPhoneCapabilityChanged(phoneCapability);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPhysicalChannelConfigurationChanged(List<PhysicalChannelConfig> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPhysicalChannelConfigurationChanged(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPreciseCallStateChanged(PreciseCallState preciseCallState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (preciseCallState != null) {
                        parcel.writeInt(1);
                        preciseCallState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPreciseCallStateChanged(preciseCallState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPreciseDataConnectionStateChanged(PreciseDataConnectionState preciseDataConnectionState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (preciseDataConnectionState != null) {
                        parcel.writeInt(1);
                        preciseDataConnectionState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRadioPowerStateChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRadioPowerStateChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onServiceStateChanged(ServiceState serviceState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (serviceState != null) {
                        parcel.writeInt(1);
                        serviceState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onServiceStateChanged(serviceState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSignalStrengthChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSignalStrengthChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (signalStrength != null) {
                        parcel.writeInt(1);
                        signalStrength.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSignalStrengthsChanged(signalStrength);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSrvccStateChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSrvccStateChanged(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUserMobileDataStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUserMobileDataStateChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVoiceActivationStateChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceActivationStateChanged(n);
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

