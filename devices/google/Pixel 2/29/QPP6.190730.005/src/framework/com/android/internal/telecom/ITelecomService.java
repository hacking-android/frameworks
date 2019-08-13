/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomAnalytics;
import java.util.ArrayList;
import java.util.List;

public interface ITelecomService
extends IInterface {
    public void acceptHandover(Uri var1, int var2, PhoneAccountHandle var3) throws RemoteException;

    public void acceptRingingCall(String var1) throws RemoteException;

    public void acceptRingingCallWithVideoState(String var1, int var2) throws RemoteException;

    public void addNewIncomingCall(PhoneAccountHandle var1, Bundle var2) throws RemoteException;

    public void addNewUnknownCall(PhoneAccountHandle var1, Bundle var2) throws RemoteException;

    public void addOrRemoveTestCallCompanionApp(String var1, boolean var2) throws RemoteException;

    public void cancelMissedCallsNotification(String var1) throws RemoteException;

    public void clearAccounts(String var1) throws RemoteException;

    public Intent createManageBlockedNumbersIntent() throws RemoteException;

    public TelecomAnalytics dumpCallAnalytics() throws RemoteException;

    public boolean enablePhoneAccount(PhoneAccountHandle var1, boolean var2) throws RemoteException;

    public boolean endCall(String var1) throws RemoteException;

    public Uri getAdnUriForPhoneAccount(PhoneAccountHandle var1, String var2) throws RemoteException;

    public List<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException;

    public List<PhoneAccount> getAllPhoneAccounts() throws RemoteException;

    public int getAllPhoneAccountsCount() throws RemoteException;

    public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getCallState() throws RemoteException;

    public int getCurrentTtyMode(String var1) throws RemoteException;

    public String getDefaultDialerPackage() throws RemoteException;

    public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String var1, String var2) throws RemoteException;

    public ComponentName getDefaultPhoneApp() throws RemoteException;

    public String getLine1Number(PhoneAccountHandle var1, String var2) throws RemoteException;

    public PhoneAccount getPhoneAccount(PhoneAccountHandle var1) throws RemoteException;

    public List<PhoneAccountHandle> getPhoneAccountsForPackage(String var1) throws RemoteException;

    public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String var1, String var2) throws RemoteException;

    public List<PhoneAccountHandle> getSelfManagedPhoneAccounts(String var1) throws RemoteException;

    public PhoneAccountHandle getSimCallManager(int var1) throws RemoteException;

    public PhoneAccountHandle getSimCallManagerForUser(int var1) throws RemoteException;

    public String getSystemDialerPackage() throws RemoteException;

    public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String var1) throws RemoteException;

    public String getVoiceMailNumber(PhoneAccountHandle var1, String var2) throws RemoteException;

    public void handleCallIntent(Intent var1) throws RemoteException;

    public boolean handlePinMmi(String var1, String var2) throws RemoteException;

    public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle var1, String var2, String var3) throws RemoteException;

    public boolean isInCall(String var1) throws RemoteException;

    public boolean isInEmergencyCall() throws RemoteException;

    public boolean isInManagedCall(String var1) throws RemoteException;

    public boolean isIncomingCallPermitted(PhoneAccountHandle var1) throws RemoteException;

    public boolean isOutgoingCallPermitted(PhoneAccountHandle var1) throws RemoteException;

    public boolean isRinging(String var1) throws RemoteException;

    public boolean isTtySupported(String var1) throws RemoteException;

    public boolean isVoiceMailNumber(PhoneAccountHandle var1, String var2, String var3) throws RemoteException;

    public void placeCall(Uri var1, Bundle var2, String var3) throws RemoteException;

    public void registerPhoneAccount(PhoneAccount var1) throws RemoteException;

    public boolean setDefaultDialer(String var1) throws RemoteException;

    public void setTestAutoModeApp(String var1) throws RemoteException;

    public void setTestDefaultCallRedirectionApp(String var1) throws RemoteException;

    public void setTestDefaultCallScreeningApp(String var1) throws RemoteException;

    public void setTestDefaultDialer(String var1) throws RemoteException;

    public void setTestPhoneAcctSuggestionComponent(String var1) throws RemoteException;

    public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle var1) throws RemoteException;

    public void showInCallScreen(boolean var1, String var2) throws RemoteException;

    public void silenceRinger(String var1) throws RemoteException;

    public void unregisterPhoneAccount(PhoneAccountHandle var1) throws RemoteException;

    public void waitOnHandlers() throws RemoteException;

    public static class Default
    implements ITelecomService {
        @Override
        public void acceptHandover(Uri uri, int n, PhoneAccountHandle phoneAccountHandle) throws RemoteException {
        }

        @Override
        public void acceptRingingCall(String string2) throws RemoteException {
        }

        @Override
        public void acceptRingingCallWithVideoState(String string2, int n) throws RemoteException {
        }

        @Override
        public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
        }

        @Override
        public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
        }

        @Override
        public void addOrRemoveTestCallCompanionApp(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelMissedCallsNotification(String string2) throws RemoteException {
        }

        @Override
        public void clearAccounts(String string2) throws RemoteException {
        }

        @Override
        public Intent createManageBlockedNumbersIntent() throws RemoteException {
            return null;
        }

        @Override
        public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
            return null;
        }

        @Override
        public boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean endCall(String string2) throws RemoteException {
            return false;
        }

        @Override
        public Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
            return null;
        }

        @Override
        public List<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
            return null;
        }

        @Override
        public int getAllPhoneAccountsCount() throws RemoteException {
            return 0;
        }

        @Override
        public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean bl, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getCallState() throws RemoteException {
            return 0;
        }

        @Override
        public int getCurrentTtyMode(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String getDefaultDialerPackage() throws RemoteException {
            return null;
        }

        @Override
        public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getDefaultPhoneApp() throws RemoteException {
            return null;
        }

        @Override
        public String getLine1Number(PhoneAccountHandle phoneAccountHandle, String string2) throws RemoteException {
            return null;
        }

        @Override
        public PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return null;
        }

        @Override
        public List<PhoneAccountHandle> getPhoneAccountsForPackage(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public List<PhoneAccountHandle> getSelfManagedPhoneAccounts(String string2) throws RemoteException {
            return null;
        }

        @Override
        public PhoneAccountHandle getSimCallManager(int n) throws RemoteException {
            return null;
        }

        @Override
        public PhoneAccountHandle getSimCallManagerForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getSystemDialerPackage() throws RemoteException {
            return null;
        }

        @Override
        public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void handleCallIntent(Intent intent) throws RemoteException {
        }

        @Override
        public boolean handlePinMmi(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInCall(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInEmergencyCall() throws RemoteException {
            return false;
        }

        @Override
        public boolean isInManagedCall(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return false;
        }

        @Override
        public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRinging(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isTtySupported(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public void placeCall(Uri uri, Bundle bundle, String string2) throws RemoteException {
        }

        @Override
        public void registerPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
        }

        @Override
        public boolean setDefaultDialer(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void setTestAutoModeApp(String string2) throws RemoteException {
        }

        @Override
        public void setTestDefaultCallRedirectionApp(String string2) throws RemoteException {
        }

        @Override
        public void setTestDefaultCallScreeningApp(String string2) throws RemoteException {
        }

        @Override
        public void setTestDefaultDialer(String string2) throws RemoteException {
        }

        @Override
        public void setTestPhoneAcctSuggestionComponent(String string2) throws RemoteException {
        }

        @Override
        public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
        }

        @Override
        public void showInCallScreen(boolean bl, String string2) throws RemoteException {
        }

        @Override
        public void silenceRinger(String string2) throws RemoteException {
        }

        @Override
        public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
        }

        @Override
        public void waitOnHandlers() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITelecomService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ITelecomService";
        static final int TRANSACTION_acceptHandover = 48;
        static final int TRANSACTION_acceptRingingCall = 31;
        static final int TRANSACTION_acceptRingingCallWithVideoState = 32;
        static final int TRANSACTION_addNewIncomingCall = 39;
        static final int TRANSACTION_addNewUnknownCall = 40;
        static final int TRANSACTION_addOrRemoveTestCallCompanionApp = 54;
        static final int TRANSACTION_cancelMissedCallsNotification = 33;
        static final int TRANSACTION_clearAccounts = 17;
        static final int TRANSACTION_createManageBlockedNumbersIntent = 44;
        static final int TRANSACTION_dumpCallAnalytics = 24;
        static final int TRANSACTION_enablePhoneAccount = 42;
        static final int TRANSACTION_endCall = 30;
        static final int TRANSACTION_getAdnUriForPhoneAccount = 36;
        static final int TRANSACTION_getAllPhoneAccountHandles = 12;
        static final int TRANSACTION_getAllPhoneAccounts = 11;
        static final int TRANSACTION_getAllPhoneAccountsCount = 10;
        static final int TRANSACTION_getCallCapablePhoneAccounts = 5;
        static final int TRANSACTION_getCallState = 29;
        static final int TRANSACTION_getCurrentTtyMode = 38;
        static final int TRANSACTION_getDefaultDialerPackage = 22;
        static final int TRANSACTION_getDefaultOutgoingPhoneAccount = 2;
        static final int TRANSACTION_getDefaultPhoneApp = 21;
        static final int TRANSACTION_getLine1Number = 20;
        static final int TRANSACTION_getPhoneAccount = 9;
        static final int TRANSACTION_getPhoneAccountsForPackage = 8;
        static final int TRANSACTION_getPhoneAccountsSupportingScheme = 7;
        static final int TRANSACTION_getSelfManagedPhoneAccounts = 6;
        static final int TRANSACTION_getSimCallManager = 13;
        static final int TRANSACTION_getSimCallManagerForUser = 14;
        static final int TRANSACTION_getSystemDialerPackage = 23;
        static final int TRANSACTION_getUserSelectedOutgoingPhoneAccount = 3;
        static final int TRANSACTION_getVoiceMailNumber = 19;
        static final int TRANSACTION_handleCallIntent = 50;
        static final int TRANSACTION_handlePinMmi = 34;
        static final int TRANSACTION_handlePinMmiForPhoneAccount = 35;
        static final int TRANSACTION_isInCall = 26;
        static final int TRANSACTION_isInEmergencyCall = 49;
        static final int TRANSACTION_isInManagedCall = 27;
        static final int TRANSACTION_isIncomingCallPermitted = 45;
        static final int TRANSACTION_isOutgoingCallPermitted = 46;
        static final int TRANSACTION_isRinging = 28;
        static final int TRANSACTION_isTtySupported = 37;
        static final int TRANSACTION_isVoiceMailNumber = 18;
        static final int TRANSACTION_placeCall = 41;
        static final int TRANSACTION_registerPhoneAccount = 15;
        static final int TRANSACTION_setDefaultDialer = 43;
        static final int TRANSACTION_setTestAutoModeApp = 55;
        static final int TRANSACTION_setTestDefaultCallRedirectionApp = 51;
        static final int TRANSACTION_setTestDefaultCallScreeningApp = 53;
        static final int TRANSACTION_setTestDefaultDialer = 56;
        static final int TRANSACTION_setTestPhoneAcctSuggestionComponent = 52;
        static final int TRANSACTION_setUserSelectedOutgoingPhoneAccount = 4;
        static final int TRANSACTION_showInCallScreen = 1;
        static final int TRANSACTION_silenceRinger = 25;
        static final int TRANSACTION_unregisterPhoneAccount = 16;
        static final int TRANSACTION_waitOnHandlers = 47;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITelecomService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITelecomService) {
                return (ITelecomService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITelecomService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 56: {
                    return "setTestDefaultDialer";
                }
                case 55: {
                    return "setTestAutoModeApp";
                }
                case 54: {
                    return "addOrRemoveTestCallCompanionApp";
                }
                case 53: {
                    return "setTestDefaultCallScreeningApp";
                }
                case 52: {
                    return "setTestPhoneAcctSuggestionComponent";
                }
                case 51: {
                    return "setTestDefaultCallRedirectionApp";
                }
                case 50: {
                    return "handleCallIntent";
                }
                case 49: {
                    return "isInEmergencyCall";
                }
                case 48: {
                    return "acceptHandover";
                }
                case 47: {
                    return "waitOnHandlers";
                }
                case 46: {
                    return "isOutgoingCallPermitted";
                }
                case 45: {
                    return "isIncomingCallPermitted";
                }
                case 44: {
                    return "createManageBlockedNumbersIntent";
                }
                case 43: {
                    return "setDefaultDialer";
                }
                case 42: {
                    return "enablePhoneAccount";
                }
                case 41: {
                    return "placeCall";
                }
                case 40: {
                    return "addNewUnknownCall";
                }
                case 39: {
                    return "addNewIncomingCall";
                }
                case 38: {
                    return "getCurrentTtyMode";
                }
                case 37: {
                    return "isTtySupported";
                }
                case 36: {
                    return "getAdnUriForPhoneAccount";
                }
                case 35: {
                    return "handlePinMmiForPhoneAccount";
                }
                case 34: {
                    return "handlePinMmi";
                }
                case 33: {
                    return "cancelMissedCallsNotification";
                }
                case 32: {
                    return "acceptRingingCallWithVideoState";
                }
                case 31: {
                    return "acceptRingingCall";
                }
                case 30: {
                    return "endCall";
                }
                case 29: {
                    return "getCallState";
                }
                case 28: {
                    return "isRinging";
                }
                case 27: {
                    return "isInManagedCall";
                }
                case 26: {
                    return "isInCall";
                }
                case 25: {
                    return "silenceRinger";
                }
                case 24: {
                    return "dumpCallAnalytics";
                }
                case 23: {
                    return "getSystemDialerPackage";
                }
                case 22: {
                    return "getDefaultDialerPackage";
                }
                case 21: {
                    return "getDefaultPhoneApp";
                }
                case 20: {
                    return "getLine1Number";
                }
                case 19: {
                    return "getVoiceMailNumber";
                }
                case 18: {
                    return "isVoiceMailNumber";
                }
                case 17: {
                    return "clearAccounts";
                }
                case 16: {
                    return "unregisterPhoneAccount";
                }
                case 15: {
                    return "registerPhoneAccount";
                }
                case 14: {
                    return "getSimCallManagerForUser";
                }
                case 13: {
                    return "getSimCallManager";
                }
                case 12: {
                    return "getAllPhoneAccountHandles";
                }
                case 11: {
                    return "getAllPhoneAccounts";
                }
                case 10: {
                    return "getAllPhoneAccountsCount";
                }
                case 9: {
                    return "getPhoneAccount";
                }
                case 8: {
                    return "getPhoneAccountsForPackage";
                }
                case 7: {
                    return "getPhoneAccountsSupportingScheme";
                }
                case 6: {
                    return "getSelfManagedPhoneAccounts";
                }
                case 5: {
                    return "getCallCapablePhoneAccounts";
                }
                case 4: {
                    return "setUserSelectedOutgoingPhoneAccount";
                }
                case 3: {
                    return "getUserSelectedOutgoingPhoneAccount";
                }
                case 2: {
                    return "getDefaultOutgoingPhoneAccount";
                }
                case 1: 
            }
            return "showInCallScreen";
        }

        public static boolean setDefaultImpl(ITelecomService iTelecomService) {
            if (Proxy.sDefaultImpl == null && iTelecomService != null) {
                Proxy.sDefaultImpl = iTelecomService;
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
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTestDefaultDialer(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTestAutoModeApp(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.addOrRemoveTestCallCompanionApp(string2, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTestDefaultCallScreeningApp(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTestPhoneAcctSuggestionComponent(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setTestDefaultCallRedirectionApp(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleCallIntent((Intent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInEmergencyCall() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.acceptHandover(uri, n, (PhoneAccountHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.waitOnHandlers();
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isOutgoingCallPermitted((PhoneAccountHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isIncomingCallPermitted((PhoneAccountHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createManageBlockedNumbersIntent();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setDefaultDialer(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl4 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        n = this.enablePhoneAccount(phoneAccountHandle, bl4) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.placeCall(uri, bundle, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addNewUnknownCall(phoneAccountHandle, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addNewIncomingCall(phoneAccountHandle, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCurrentTtyMode(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTtySupported(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getAdnUriForPhoneAccount(phoneAccountHandle, ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.handlePinMmiForPhoneAccount(phoneAccountHandle, ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.handlePinMmi(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelMissedCallsNotification(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.acceptRingingCallWithVideoState(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.acceptRingingCall(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.endCall(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCallState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRinging(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInManagedCall(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInCall(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.silenceRinger(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.dumpCallAnalytics();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((TelecomAnalytics)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemDialerPackage();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultDialerPackage();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultPhoneApp();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLine1Number(phoneAccountHandle, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getVoiceMailNumber(phoneAccountHandle, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PhoneAccountHandle phoneAccountHandle = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isVoiceMailNumber(phoneAccountHandle, ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearAccounts(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unregisterPhoneAccount((PhoneAccountHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccount.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerPhoneAccount((PhoneAccount)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSimCallManagerForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PhoneAccountHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSimCallManager(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PhoneAccountHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllPhoneAccountHandles();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllPhoneAccounts();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getAllPhoneAccountsCount();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPhoneAccount((PhoneAccountHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PhoneAccount)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPhoneAccountsForPackage(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPhoneAccountsSupportingScheme(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSelfManagedPhoneAccounts(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        object = this.getCallCapablePhoneAccounts(bl4, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUserSelectedOutgoingPhoneAccount((PhoneAccountHandle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserSelectedOutgoingPhoneAccount(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PhoneAccountHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultOutgoingPhoneAccount(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PhoneAccountHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                bl4 = bl3;
                if (((Parcel)object).readInt() != 0) {
                    bl4 = true;
                }
                this.showInCallScreen(bl4, ((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITelecomService {
            public static ITelecomService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void acceptHandover(Uri uri, int n, PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acceptHandover(uri, n, phoneAccountHandle);
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
            public void acceptRingingCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acceptRingingCall(string2);
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
            public void acceptRingingCallWithVideoState(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acceptRingingCallWithVideoState(string2, n);
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
            public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addNewIncomingCall(phoneAccountHandle, bundle);
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
            public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addNewUnknownCall(phoneAccountHandle, bundle);
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
            public void addOrRemoveTestCallCompanionApp(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOrRemoveTestCallCompanionApp(string2, bl);
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

            @Override
            public void cancelMissedCallsNotification(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelMissedCallsNotification(string2);
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
            public void clearAccounts(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearAccounts(string2);
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
            public Intent createManageBlockedNumbersIntent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(44, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Intent intent = Stub.getDefaultImpl().createManageBlockedNumbersIntent();
                        parcel.recycle();
                        parcel2.recycle();
                        return intent;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Intent intent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return intent;
            }

            @Override
            public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(24, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        TelecomAnalytics telecomAnalytics = Stub.getDefaultImpl().dumpCallAnalytics();
                        parcel.recycle();
                        parcel2.recycle();
                        return telecomAnalytics;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                TelecomAnalytics telecomAnalytics = parcel.readInt() != 0 ? TelecomAnalytics.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return telecomAnalytics;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().enablePhoneAccount(phoneAccountHandle, bl);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    bl = n != 0 ? bl2 : false;
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean endCall(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(30, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().endCall(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Uri getAdnUriForPhoneAccount(PhoneAccountHandle parcelable, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PhoneAccountHandle)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Uri uri = Stub.getDefaultImpl().getAdnUriForPhoneAccount((PhoneAccountHandle)parcelable, (String)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return uri;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Uri uri = Uri.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public List<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<PhoneAccountHandle> list = Stub.getDefaultImpl().getAllPhoneAccountHandles();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<PhoneAccountHandle> arrayList = parcel2.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<PhoneAccount> list = Stub.getDefaultImpl().getAllPhoneAccounts();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<PhoneAccount> arrayList = parcel2.createTypedArrayList(PhoneAccount.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getAllPhoneAccountsCount() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getAllPhoneAccountsCount();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean bl, String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getCallCapablePhoneAccounts(bl, (String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCallState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCallState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCurrentTtyMode(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getCurrentTtyMode(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getDefaultDialerPackage() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDefaultDialerPackage();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getDefaultOutgoingPhoneAccount((String)object, string2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public ComponentName getDefaultPhoneApp() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(21, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getDefaultPhoneApp();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getLine1Number(PhoneAccountHandle object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((PhoneAccountHandle)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getLine1Number((PhoneAccountHandle)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PhoneAccount getPhoneAccount(PhoneAccountHandle parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PhoneAccountHandle)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        PhoneAccount phoneAccount = Stub.getDefaultImpl().getPhoneAccount((PhoneAccountHandle)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return phoneAccount;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        PhoneAccount phoneAccount = PhoneAccount.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public List<PhoneAccountHandle> getPhoneAccountsForPackage(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getPhoneAccountsForPackage((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String arrayList, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getPhoneAccountsSupportingScheme((String)((Object)arrayList), string2);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<PhoneAccountHandle> getSelfManagedPhoneAccounts(String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getSelfManagedPhoneAccounts((String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PhoneAccountHandle getSimCallManager(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        PhoneAccountHandle phoneAccountHandle = Stub.getDefaultImpl().getSimCallManager(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return phoneAccountHandle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                PhoneAccountHandle phoneAccountHandle = parcel2.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return phoneAccountHandle;
            }

            @Override
            public PhoneAccountHandle getSimCallManagerForUser(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(14, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        PhoneAccountHandle phoneAccountHandle = Stub.getDefaultImpl().getSimCallManagerForUser(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return phoneAccountHandle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                PhoneAccountHandle phoneAccountHandle = parcel2.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return phoneAccountHandle;
            }

            @Override
            public String getSystemDialerPackage() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSystemDialerPackage();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getUserSelectedOutgoingPhoneAccount((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public String getVoiceMailNumber(PhoneAccountHandle object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((PhoneAccountHandle)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getVoiceMailNumber((PhoneAccountHandle)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void handleCallIntent(Intent intent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleCallIntent(intent);
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
            public boolean handlePinMmi(String string2, String string3) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(34, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().handlePinMmi(string2, string3);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().handlePinMmiForPhoneAccount(phoneAccountHandle, string2, string3);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isInCall(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(26, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInCall(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isInEmergencyCall() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(49, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInEmergencyCall();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isInManagedCall(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(27, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInManagedCall(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isIncomingCallPermitted(phoneAccountHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isOutgoingCallPermitted(phoneAccountHandle);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isRinging(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(28, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRinging(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isTtySupported(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(37, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTtySupported(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isVoiceMailNumber(phoneAccountHandle, string2, string3);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void placeCall(Uri uri, Bundle bundle, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().placeCall(uri, bundle, string2);
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
            public void registerPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccount != null) {
                        parcel.writeInt(1);
                        phoneAccount.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerPhoneAccount(phoneAccount);
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
            public boolean setDefaultDialer(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(43, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setDefaultDialer(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setTestAutoModeApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestAutoModeApp(string2);
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
            public void setTestDefaultCallRedirectionApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestDefaultCallRedirectionApp(string2);
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
            public void setTestDefaultCallScreeningApp(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestDefaultCallScreeningApp(string2);
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
            public void setTestDefaultDialer(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestDefaultDialer(string2);
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
            public void setTestPhoneAcctSuggestionComponent(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTestPhoneAcctSuggestionComponent(string2);
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
            public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
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
            public void showInCallScreen(boolean bl, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().showInCallScreen(bl, string2);
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
            public void silenceRinger(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().silenceRinger(string2);
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
            public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterPhoneAccount(phoneAccountHandle);
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
            public void waitOnHandlers() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().waitOnHandlers();
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

