/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserManager;
import java.util.ArrayList;
import java.util.List;

public interface IUserManager
extends IInterface {
    public boolean canAddMoreManagedProfiles(int var1, boolean var2) throws RemoteException;

    public boolean canHaveRestrictedProfile(int var1) throws RemoteException;

    public void clearSeedAccountData() throws RemoteException;

    public UserInfo createProfileForUser(String var1, int var2, int var3, String[] var4) throws RemoteException;

    public UserInfo createProfileForUserEvenWhenDisallowed(String var1, int var2, int var3, String[] var4) throws RemoteException;

    public UserInfo createRestrictedProfile(String var1, int var2) throws RemoteException;

    public UserInfo createUser(String var1, int var2) throws RemoteException;

    public void evictCredentialEncryptionKey(int var1) throws RemoteException;

    public Bundle getApplicationRestrictions(String var1) throws RemoteException;

    public Bundle getApplicationRestrictionsForUser(String var1, int var2) throws RemoteException;

    public int getCredentialOwnerProfile(int var1) throws RemoteException;

    public Bundle getDefaultGuestRestrictions() throws RemoteException;

    public int getManagedProfileBadge(int var1) throws RemoteException;

    public UserInfo getPrimaryUser() throws RemoteException;

    public int[] getProfileIds(int var1, boolean var2) throws RemoteException;

    public UserInfo getProfileParent(int var1) throws RemoteException;

    public int getProfileParentId(int var1) throws RemoteException;

    public List<UserInfo> getProfiles(int var1, boolean var2) throws RemoteException;

    public String getSeedAccountName() throws RemoteException;

    public PersistableBundle getSeedAccountOptions() throws RemoteException;

    public String getSeedAccountType() throws RemoteException;

    public String getUserAccount(int var1) throws RemoteException;

    public long getUserCreationTime(int var1) throws RemoteException;

    public int getUserHandle(int var1) throws RemoteException;

    public ParcelFileDescriptor getUserIcon(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public UserInfo getUserInfo(int var1) throws RemoteException;

    public String getUserName() throws RemoteException;

    public int getUserRestrictionSource(String var1, int var2) throws RemoteException;

    public List<UserManager.EnforcingUser> getUserRestrictionSources(String var1, int var2) throws RemoteException;

    public Bundle getUserRestrictions(int var1) throws RemoteException;

    public int getUserSerialNumber(int var1) throws RemoteException;

    public long getUserStartRealtime() throws RemoteException;

    public long getUserUnlockRealtime() throws RemoteException;

    public List<UserInfo> getUsers(boolean var1) throws RemoteException;

    public boolean hasBaseUserRestriction(String var1, int var2) throws RemoteException;

    public boolean hasRestrictedProfiles() throws RemoteException;

    public boolean hasUserRestriction(String var1, int var2) throws RemoteException;

    public boolean hasUserRestrictionOnAnyUser(String var1) throws RemoteException;

    public boolean isDemoUser(int var1) throws RemoteException;

    public boolean isManagedProfile(int var1) throws RemoteException;

    public boolean isQuietModeEnabled(int var1) throws RemoteException;

    public boolean isRestricted() throws RemoteException;

    public boolean isSameProfileGroup(int var1, int var2) throws RemoteException;

    public boolean isUserNameSet(int var1) throws RemoteException;

    public boolean isUserRunning(int var1) throws RemoteException;

    public boolean isUserUnlocked(int var1) throws RemoteException;

    public boolean isUserUnlockingOrUnlocked(int var1) throws RemoteException;

    public boolean markGuestForDeletion(int var1) throws RemoteException;

    public boolean removeUser(int var1) throws RemoteException;

    public boolean removeUserEvenWhenDisallowed(int var1) throws RemoteException;

    public boolean requestQuietModeEnabled(String var1, boolean var2, int var3, IntentSender var4) throws RemoteException;

    public void setApplicationRestrictions(String var1, Bundle var2, int var3) throws RemoteException;

    public void setDefaultGuestRestrictions(Bundle var1) throws RemoteException;

    public void setSeedAccountData(int var1, String var2, String var3, PersistableBundle var4, boolean var5) throws RemoteException;

    public void setUserAccount(int var1, String var2) throws RemoteException;

    public void setUserAdmin(int var1) throws RemoteException;

    public void setUserEnabled(int var1) throws RemoteException;

    public void setUserIcon(int var1, Bitmap var2) throws RemoteException;

    public void setUserName(int var1, String var2) throws RemoteException;

    public void setUserRestriction(String var1, boolean var2, int var3) throws RemoteException;

    public boolean someUserHasSeedAccount(String var1, String var2) throws RemoteException;

    public static class Default
    implements IUserManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean canAddMoreManagedProfiles(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean canHaveRestrictedProfile(int n) throws RemoteException {
            return false;
        }

        @Override
        public void clearSeedAccountData() throws RemoteException {
        }

        @Override
        public UserInfo createProfileForUser(String string2, int n, int n2, String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public UserInfo createProfileForUserEvenWhenDisallowed(String string2, int n, int n2, String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public UserInfo createRestrictedProfile(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public UserInfo createUser(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public void evictCredentialEncryptionKey(int n) throws RemoteException {
        }

        @Override
        public Bundle getApplicationRestrictions(String string2) throws RemoteException {
            return null;
        }

        @Override
        public Bundle getApplicationRestrictionsForUser(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getCredentialOwnerProfile(int n) throws RemoteException {
            return 0;
        }

        @Override
        public Bundle getDefaultGuestRestrictions() throws RemoteException {
            return null;
        }

        @Override
        public int getManagedProfileBadge(int n) throws RemoteException {
            return 0;
        }

        @Override
        public UserInfo getPrimaryUser() throws RemoteException {
            return null;
        }

        @Override
        public int[] getProfileIds(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public UserInfo getProfileParent(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getProfileParentId(int n) throws RemoteException {
            return 0;
        }

        @Override
        public List<UserInfo> getProfiles(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public String getSeedAccountName() throws RemoteException {
            return null;
        }

        @Override
        public PersistableBundle getSeedAccountOptions() throws RemoteException {
            return null;
        }

        @Override
        public String getSeedAccountType() throws RemoteException {
            return null;
        }

        @Override
        public String getUserAccount(int n) throws RemoteException {
            return null;
        }

        @Override
        public long getUserCreationTime(int n) throws RemoteException {
            return 0L;
        }

        @Override
        public int getUserHandle(int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParcelFileDescriptor getUserIcon(int n) throws RemoteException {
            return null;
        }

        @Override
        public UserInfo getUserInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getUserName() throws RemoteException {
            return null;
        }

        @Override
        public int getUserRestrictionSource(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public List<UserManager.EnforcingUser> getUserRestrictionSources(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public Bundle getUserRestrictions(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getUserSerialNumber(int n) throws RemoteException {
            return 0;
        }

        @Override
        public long getUserStartRealtime() throws RemoteException {
            return 0L;
        }

        @Override
        public long getUserUnlockRealtime() throws RemoteException {
            return 0L;
        }

        @Override
        public List<UserInfo> getUsers(boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public boolean hasBaseUserRestriction(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasRestrictedProfiles() throws RemoteException {
            return false;
        }

        @Override
        public boolean hasUserRestriction(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasUserRestrictionOnAnyUser(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isDemoUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isManagedProfile(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isQuietModeEnabled(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRestricted() throws RemoteException {
            return false;
        }

        @Override
        public boolean isSameProfileGroup(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserNameSet(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserRunning(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserUnlocked(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUserUnlockingOrUnlocked(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean markGuestForDeletion(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeUserEvenWhenDisallowed(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean requestQuietModeEnabled(String string2, boolean bl, int n, IntentSender intentSender) throws RemoteException {
            return false;
        }

        @Override
        public void setApplicationRestrictions(String string2, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException {
        }

        @Override
        public void setSeedAccountData(int n, String string2, String string3, PersistableBundle persistableBundle, boolean bl) throws RemoteException {
        }

        @Override
        public void setUserAccount(int n, String string2) throws RemoteException {
        }

        @Override
        public void setUserAdmin(int n) throws RemoteException {
        }

        @Override
        public void setUserEnabled(int n) throws RemoteException {
        }

        @Override
        public void setUserIcon(int n, Bitmap bitmap) throws RemoteException {
        }

        @Override
        public void setUserName(int n, String string2) throws RemoteException {
        }

        @Override
        public void setUserRestriction(String string2, boolean bl, int n) throws RemoteException {
        }

        @Override
        public boolean someUserHasSeedAccount(String string2, String string3) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUserManager {
        private static final String DESCRIPTOR = "android.os.IUserManager";
        static final int TRANSACTION_canAddMoreManagedProfiles = 18;
        static final int TRANSACTION_canHaveRestrictedProfile = 26;
        static final int TRANSACTION_clearSeedAccountData = 47;
        static final int TRANSACTION_createProfileForUser = 4;
        static final int TRANSACTION_createProfileForUserEvenWhenDisallowed = 51;
        static final int TRANSACTION_createRestrictedProfile = 5;
        static final int TRANSACTION_createUser = 3;
        static final int TRANSACTION_evictCredentialEncryptionKey = 8;
        static final int TRANSACTION_getApplicationRestrictions = 37;
        static final int TRANSACTION_getApplicationRestrictionsForUser = 38;
        static final int TRANSACTION_getCredentialOwnerProfile = 1;
        static final int TRANSACTION_getDefaultGuestRestrictions = 40;
        static final int TRANSACTION_getManagedProfileBadge = 53;
        static final int TRANSACTION_getPrimaryUser = 14;
        static final int TRANSACTION_getProfileIds = 17;
        static final int TRANSACTION_getProfileParent = 19;
        static final int TRANSACTION_getProfileParentId = 2;
        static final int TRANSACTION_getProfiles = 16;
        static final int TRANSACTION_getSeedAccountName = 44;
        static final int TRANSACTION_getSeedAccountOptions = 46;
        static final int TRANSACTION_getSeedAccountType = 45;
        static final int TRANSACTION_getUserAccount = 22;
        static final int TRANSACTION_getUserCreationTime = 24;
        static final int TRANSACTION_getUserHandle = 28;
        static final int TRANSACTION_getUserIcon = 13;
        static final int TRANSACTION_getUserInfo = 21;
        static final int TRANSACTION_getUserName = 59;
        static final int TRANSACTION_getUserRestrictionSource = 29;
        static final int TRANSACTION_getUserRestrictionSources = 30;
        static final int TRANSACTION_getUserRestrictions = 31;
        static final int TRANSACTION_getUserSerialNumber = 27;
        static final int TRANSACTION_getUserStartRealtime = 60;
        static final int TRANSACTION_getUserUnlockRealtime = 61;
        static final int TRANSACTION_getUsers = 15;
        static final int TRANSACTION_hasBaseUserRestriction = 32;
        static final int TRANSACTION_hasRestrictedProfiles = 57;
        static final int TRANSACTION_hasUserRestriction = 33;
        static final int TRANSACTION_hasUserRestrictionOnAnyUser = 34;
        static final int TRANSACTION_isDemoUser = 50;
        static final int TRANSACTION_isManagedProfile = 49;
        static final int TRANSACTION_isQuietModeEnabled = 42;
        static final int TRANSACTION_isRestricted = 25;
        static final int TRANSACTION_isSameProfileGroup = 20;
        static final int TRANSACTION_isUserNameSet = 56;
        static final int TRANSACTION_isUserRunning = 55;
        static final int TRANSACTION_isUserUnlocked = 54;
        static final int TRANSACTION_isUserUnlockingOrUnlocked = 52;
        static final int TRANSACTION_markGuestForDeletion = 41;
        static final int TRANSACTION_removeUser = 9;
        static final int TRANSACTION_removeUserEvenWhenDisallowed = 10;
        static final int TRANSACTION_requestQuietModeEnabled = 58;
        static final int TRANSACTION_setApplicationRestrictions = 36;
        static final int TRANSACTION_setDefaultGuestRestrictions = 39;
        static final int TRANSACTION_setSeedAccountData = 43;
        static final int TRANSACTION_setUserAccount = 23;
        static final int TRANSACTION_setUserAdmin = 7;
        static final int TRANSACTION_setUserEnabled = 6;
        static final int TRANSACTION_setUserIcon = 12;
        static final int TRANSACTION_setUserName = 11;
        static final int TRANSACTION_setUserRestriction = 35;
        static final int TRANSACTION_someUserHasSeedAccount = 48;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUserManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUserManager) {
                return (IUserManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUserManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 61: {
                    return "getUserUnlockRealtime";
                }
                case 60: {
                    return "getUserStartRealtime";
                }
                case 59: {
                    return "getUserName";
                }
                case 58: {
                    return "requestQuietModeEnabled";
                }
                case 57: {
                    return "hasRestrictedProfiles";
                }
                case 56: {
                    return "isUserNameSet";
                }
                case 55: {
                    return "isUserRunning";
                }
                case 54: {
                    return "isUserUnlocked";
                }
                case 53: {
                    return "getManagedProfileBadge";
                }
                case 52: {
                    return "isUserUnlockingOrUnlocked";
                }
                case 51: {
                    return "createProfileForUserEvenWhenDisallowed";
                }
                case 50: {
                    return "isDemoUser";
                }
                case 49: {
                    return "isManagedProfile";
                }
                case 48: {
                    return "someUserHasSeedAccount";
                }
                case 47: {
                    return "clearSeedAccountData";
                }
                case 46: {
                    return "getSeedAccountOptions";
                }
                case 45: {
                    return "getSeedAccountType";
                }
                case 44: {
                    return "getSeedAccountName";
                }
                case 43: {
                    return "setSeedAccountData";
                }
                case 42: {
                    return "isQuietModeEnabled";
                }
                case 41: {
                    return "markGuestForDeletion";
                }
                case 40: {
                    return "getDefaultGuestRestrictions";
                }
                case 39: {
                    return "setDefaultGuestRestrictions";
                }
                case 38: {
                    return "getApplicationRestrictionsForUser";
                }
                case 37: {
                    return "getApplicationRestrictions";
                }
                case 36: {
                    return "setApplicationRestrictions";
                }
                case 35: {
                    return "setUserRestriction";
                }
                case 34: {
                    return "hasUserRestrictionOnAnyUser";
                }
                case 33: {
                    return "hasUserRestriction";
                }
                case 32: {
                    return "hasBaseUserRestriction";
                }
                case 31: {
                    return "getUserRestrictions";
                }
                case 30: {
                    return "getUserRestrictionSources";
                }
                case 29: {
                    return "getUserRestrictionSource";
                }
                case 28: {
                    return "getUserHandle";
                }
                case 27: {
                    return "getUserSerialNumber";
                }
                case 26: {
                    return "canHaveRestrictedProfile";
                }
                case 25: {
                    return "isRestricted";
                }
                case 24: {
                    return "getUserCreationTime";
                }
                case 23: {
                    return "setUserAccount";
                }
                case 22: {
                    return "getUserAccount";
                }
                case 21: {
                    return "getUserInfo";
                }
                case 20: {
                    return "isSameProfileGroup";
                }
                case 19: {
                    return "getProfileParent";
                }
                case 18: {
                    return "canAddMoreManagedProfiles";
                }
                case 17: {
                    return "getProfileIds";
                }
                case 16: {
                    return "getProfiles";
                }
                case 15: {
                    return "getUsers";
                }
                case 14: {
                    return "getPrimaryUser";
                }
                case 13: {
                    return "getUserIcon";
                }
                case 12: {
                    return "setUserIcon";
                }
                case 11: {
                    return "setUserName";
                }
                case 10: {
                    return "removeUserEvenWhenDisallowed";
                }
                case 9: {
                    return "removeUser";
                }
                case 8: {
                    return "evictCredentialEncryptionKey";
                }
                case 7: {
                    return "setUserAdmin";
                }
                case 6: {
                    return "setUserEnabled";
                }
                case 5: {
                    return "createRestrictedProfile";
                }
                case 4: {
                    return "createProfileForUser";
                }
                case 3: {
                    return "createUser";
                }
                case 2: {
                    return "getProfileParentId";
                }
                case 1: 
            }
            return "getCredentialOwnerProfile";
        }

        public static boolean setDefaultImpl(IUserManager iUserManager) {
            if (Proxy.sDefaultImpl == null && iUserManager != null) {
                Proxy.sDefaultImpl = iUserManager;
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
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getUserUnlockRealtime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getUserStartRealtime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestQuietModeEnabled(string2, bl6, n, (IntentSender)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasRestrictedProfiles() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserNameSet(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserRunning(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserUnlocked(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getManagedProfileBadge(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUserUnlockingOrUnlocked(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createProfileForUserEvenWhenDisallowed(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDemoUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isManagedProfile(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.someUserHasSeedAccount(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearSeedAccountData();
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSeedAccountOptions();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PersistableBundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSeedAccountType();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSeedAccountName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string3 = ((Parcel)object).readString();
                        String string4 = ((Parcel)object).readString();
                        PersistableBundle persistableBundle = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl6 = ((Parcel)object).readInt() != 0;
                        this.setSeedAccountData(n, string3, string4, persistableBundle, bl6);
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isQuietModeEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.markGuestForDeletion(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultGuestRestrictions();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDefaultGuestRestrictions((Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getApplicationRestrictionsForUser(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getApplicationRestrictions(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setApplicationRestrictions(string5, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        bl6 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.setUserRestriction(string6, bl6, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasUserRestrictionOnAnyUser(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasUserRestriction(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasBaseUserRestriction(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserRestrictions(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserRestrictionSources(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUserRestrictionSource(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUserHandle(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUserSerialNumber(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.canHaveRestrictedProfile(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isRestricted() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getUserCreationTime(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUserAccount(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserAccount(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserInfo(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSameProfileGroup(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProfileParent(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        n = this.canAddMoreManagedProfiles(n, bl6) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        object = this.getProfileIds(n, bl6);
                        parcel.writeNoException();
                        parcel.writeIntArray((int[])object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl6 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        object = this.getProfiles(n, bl6);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        object = this.getUsers(bl6);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPrimaryUser();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUserIcon(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bitmap.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUserIcon(n, (Bitmap)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUserName(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeUserEvenWhenDisallowed(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.evictCredentialEncryptionKey(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUserAdmin(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUserEnabled(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createRestrictedProfile(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createProfileForUser(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createUser(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getProfileParentId(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.getCredentialOwnerProfile(((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUserManager {
            public static IUserManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean canAddMoreManagedProfiles(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(18, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().canAddMoreManagedProfiles(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean canHaveRestrictedProfile(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(26, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canHaveRestrictedProfile(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void clearSeedAccountData() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearSeedAccountData();
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
            public UserInfo createProfileForUser(String object, int n, int n2, String[] arrstring) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        parcel2.writeStringArray(arrstring);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createProfileForUser((String)object, n, n2, arrstring);
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
                object = parcel.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public UserInfo createProfileForUserEvenWhenDisallowed(String object, int n, int n2, String[] arrstring) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        parcel2.writeStringArray(arrstring);
                        if (this.mRemote.transact(51, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createProfileForUserEvenWhenDisallowed((String)object, n, n2, arrstring);
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
                object = parcel.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public UserInfo createRestrictedProfile(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createRestrictedProfile((String)object, n);
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
                object = parcel.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public UserInfo createUser(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createUser((String)object, n);
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
                object = parcel.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public void evictCredentialEncryptionKey(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().evictCredentialEncryptionKey(n);
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
            public Bundle getApplicationRestrictions(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(37, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getApplicationRestrictions((String)object);
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
                object = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public Bundle getApplicationRestrictionsForUser(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(38, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getApplicationRestrictionsForUser((String)object, n);
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
                object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public int getCredentialOwnerProfile(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCredentialOwnerProfile(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Bundle getDefaultGuestRestrictions() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(40, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().getDefaultGuestRestrictions();
                        parcel.recycle();
                        parcel2.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return bundle;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int getManagedProfileBadge(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getManagedProfileBadge(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public UserInfo getPrimaryUser() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(14, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        UserInfo userInfo = Stub.getDefaultImpl().getPrimaryUser();
                        parcel.recycle();
                        parcel2.recycle();
                        return userInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                UserInfo userInfo = parcel.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return userInfo;
            }

            @Override
            public int[] getProfileIds(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getProfileIds(n, bl);
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public UserInfo getProfileParent(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(19, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        UserInfo userInfo = Stub.getDefaultImpl().getProfileParent(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return userInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                UserInfo userInfo = parcel2.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return userInfo;
            }

            @Override
            public int getProfileParentId(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getProfileParentId(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<UserInfo> getProfiles(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<UserInfo> list = Stub.getDefaultImpl().getProfiles(n, bl);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<UserInfo> arrayList = parcel2.createTypedArrayList(UserInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSeedAccountName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSeedAccountName();
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
            public PersistableBundle getSeedAccountOptions() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(46, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        PersistableBundle persistableBundle = Stub.getDefaultImpl().getSeedAccountOptions();
                        parcel.recycle();
                        parcel2.recycle();
                        return persistableBundle;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                PersistableBundle persistableBundle = parcel.readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return persistableBundle;
            }

            @Override
            public String getSeedAccountType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSeedAccountType();
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
            public String getUserAccount(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getUserAccount(n);
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
            public long getUserCreationTime(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getUserCreationTime(n);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getUserHandle(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getUserHandle(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParcelFileDescriptor getUserIcon(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().getUserIcon(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParcelFileDescriptor parcelFileDescriptor = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parcelFileDescriptor;
            }

            @Override
            public UserInfo getUserInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        UserInfo userInfo = Stub.getDefaultImpl().getUserInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return userInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                UserInfo userInfo = parcel2.readInt() != 0 ? UserInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return userInfo;
            }

            @Override
            public String getUserName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getUserName();
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
            public int getUserRestrictionSource(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getUserRestrictionSource(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<UserManager.EnforcingUser> getUserRestrictionSources(String arrayList, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)arrayList));
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getUserRestrictionSources((String)((Object)arrayList), n);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(UserManager.EnforcingUser.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Bundle getUserRestrictions(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(31, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().getUserRestrictions(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Bundle bundle = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return bundle;
            }

            @Override
            public int getUserSerialNumber(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getUserSerialNumber(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getUserStartRealtime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getUserStartRealtime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getUserUnlockRealtime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getUserUnlockRealtime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<UserInfo> getUsers(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<UserInfo> list = Stub.getDefaultImpl().getUsers(bl);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<UserInfo> arrayList = parcel2.createTypedArrayList(UserInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean hasBaseUserRestriction(String string2, int n) throws RemoteException {
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
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(32, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasBaseUserRestriction(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean hasRestrictedProfiles() throws RemoteException {
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
                    if (iBinder.transact(57, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasRestrictedProfiles();
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
            public boolean hasUserRestriction(String string2, int n) throws RemoteException {
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
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(33, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasUserRestriction(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean hasUserRestrictionOnAnyUser(String string2) throws RemoteException {
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
                    if (iBinder.transact(34, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasUserRestrictionOnAnyUser(string2);
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
            public boolean isDemoUser(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(50, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDemoUser(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isManagedProfile(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(49, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isManagedProfile(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isQuietModeEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(42, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isQuietModeEnabled(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isRestricted() throws RemoteException {
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
                    if (iBinder.transact(25, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRestricted();
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
            public boolean isSameProfileGroup(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(20, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSameProfileGroup(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isUserNameSet(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(56, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserNameSet(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isUserRunning(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(55, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserRunning(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isUserUnlocked(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(54, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserUnlocked(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isUserUnlockingOrUnlocked(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(52, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUserUnlockingOrUnlocked(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean markGuestForDeletion(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(41, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().markGuestForDeletion(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean removeUser(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeUser(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean removeUserEvenWhenDisallowed(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(10, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeUserEvenWhenDisallowed(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
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
            public boolean requestQuietModeEnabled(String string2, boolean bl, int n, IntentSender intentSender) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl2 = true;
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (intentSender != null) {
                        parcel.writeInt(1);
                        intentSender.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().requestQuietModeEnabled(string2, bl, n, intentSender);
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
            public void setApplicationRestrictions(String string2, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setApplicationRestrictions(string2, bundle, n);
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
            public void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException {
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
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultGuestRestrictions(bundle);
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
            public void setSeedAccountData(int n, String string2, String string3, PersistableBundle persistableBundle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    int n2 = 1;
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSeedAccountData(n, string2, string3, persistableBundle, bl);
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
            public void setUserAccount(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserAccount(n, string2);
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
            public void setUserAdmin(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserAdmin(n);
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
            public void setUserEnabled(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserEnabled(n);
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
            public void setUserIcon(int n, Bitmap bitmap) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bitmap != null) {
                        parcel.writeInt(1);
                        bitmap.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserIcon(n, bitmap);
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
            public void setUserName(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserName(n, string2);
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
            public void setUserRestriction(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserRestriction(string2, bl, n);
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
            public boolean someUserHasSeedAccount(String string2, String string3) throws RemoteException {
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
                    if (iBinder.transact(48, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().someUserHasSeedAccount(string2, string3);
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
        }

    }

}

