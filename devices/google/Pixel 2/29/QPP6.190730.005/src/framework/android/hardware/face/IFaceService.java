/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.face;

import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricServiceReceiverInternal;
import android.hardware.face.Face;
import android.hardware.face.IFaceServiceReceiver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IFaceService
extends IInterface {
    public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback var1) throws RemoteException;

    public void authenticate(IBinder var1, long var2, int var4, IFaceServiceReceiver var5, int var6, String var7) throws RemoteException;

    public void cancelAuthentication(IBinder var1, String var2) throws RemoteException;

    public void cancelAuthenticationFromService(IBinder var1, String var2, int var3, int var4, int var5, boolean var6) throws RemoteException;

    public void cancelEnrollment(IBinder var1) throws RemoteException;

    public void enroll(IBinder var1, byte[] var2, IFaceServiceReceiver var3, String var4, int[] var5) throws RemoteException;

    public void enumerate(IBinder var1, int var2, IFaceServiceReceiver var3) throws RemoteException;

    public long generateChallenge(IBinder var1) throws RemoteException;

    public long getAuthenticatorId(String var1) throws RemoteException;

    public List<Face> getEnrolledFaces(int var1, String var2) throws RemoteException;

    public void getFeature(int var1, IFaceServiceReceiver var2) throws RemoteException;

    public boolean hasEnrolledFaces(int var1, String var2) throws RemoteException;

    public boolean isHardwareDetected(long var1, String var3) throws RemoteException;

    public void prepareForAuthentication(boolean var1, IBinder var2, long var3, int var5, IBiometricServiceReceiverInternal var6, String var7, int var8, int var9, int var10, int var11) throws RemoteException;

    public void remove(IBinder var1, int var2, int var3, IFaceServiceReceiver var4) throws RemoteException;

    public void rename(int var1, String var2) throws RemoteException;

    public void resetLockout(byte[] var1) throws RemoteException;

    public int revokeChallenge(IBinder var1) throws RemoteException;

    public void setActiveUser(int var1) throws RemoteException;

    public void setFeature(int var1, boolean var2, byte[] var3, IFaceServiceReceiver var4) throws RemoteException;

    public void startPreparedClient(int var1) throws RemoteException;

    public void userActivity() throws RemoteException;

    public static class Default
    implements IFaceService {
        @Override
        public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void authenticate(IBinder iBinder, long l, int n, IFaceServiceReceiver iFaceServiceReceiver, int n2, String string2) throws RemoteException {
        }

        @Override
        public void cancelAuthentication(IBinder iBinder, String string2) throws RemoteException {
        }

        @Override
        public void cancelAuthenticationFromService(IBinder iBinder, String string2, int n, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void cancelEnrollment(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void enroll(IBinder iBinder, byte[] arrby, IFaceServiceReceiver iFaceServiceReceiver, String string2, int[] arrn) throws RemoteException {
        }

        @Override
        public void enumerate(IBinder iBinder, int n, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
        }

        @Override
        public long generateChallenge(IBinder iBinder) throws RemoteException {
            return 0L;
        }

        @Override
        public long getAuthenticatorId(String string2) throws RemoteException {
            return 0L;
        }

        @Override
        public List<Face> getEnrolledFaces(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public void getFeature(int n, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
        }

        @Override
        public boolean hasEnrolledFaces(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isHardwareDetected(long l, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void prepareForAuthentication(boolean bl, IBinder iBinder, long l, int n, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, String string2, int n2, int n3, int n4, int n5) throws RemoteException {
        }

        @Override
        public void remove(IBinder iBinder, int n, int n2, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
        }

        @Override
        public void rename(int n, String string2) throws RemoteException {
        }

        @Override
        public void resetLockout(byte[] arrby) throws RemoteException {
        }

        @Override
        public int revokeChallenge(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override
        public void setActiveUser(int n) throws RemoteException {
        }

        @Override
        public void setFeature(int n, boolean bl, byte[] arrby, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
        }

        @Override
        public void startPreparedClient(int n) throws RemoteException {
        }

        @Override
        public void userActivity() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IFaceService {
        private static final String DESCRIPTOR = "android.hardware.face.IFaceService";
        static final int TRANSACTION_addLockoutResetCallback = 17;
        static final int TRANSACTION_authenticate = 1;
        static final int TRANSACTION_cancelAuthentication = 4;
        static final int TRANSACTION_cancelAuthenticationFromService = 5;
        static final int TRANSACTION_cancelEnrollment = 7;
        static final int TRANSACTION_enroll = 6;
        static final int TRANSACTION_enumerate = 19;
        static final int TRANSACTION_generateChallenge = 12;
        static final int TRANSACTION_getAuthenticatorId = 15;
        static final int TRANSACTION_getEnrolledFaces = 10;
        static final int TRANSACTION_getFeature = 21;
        static final int TRANSACTION_hasEnrolledFaces = 14;
        static final int TRANSACTION_isHardwareDetected = 11;
        static final int TRANSACTION_prepareForAuthentication = 2;
        static final int TRANSACTION_remove = 8;
        static final int TRANSACTION_rename = 9;
        static final int TRANSACTION_resetLockout = 16;
        static final int TRANSACTION_revokeChallenge = 13;
        static final int TRANSACTION_setActiveUser = 18;
        static final int TRANSACTION_setFeature = 20;
        static final int TRANSACTION_startPreparedClient = 3;
        static final int TRANSACTION_userActivity = 22;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IFaceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IFaceService) {
                return (IFaceService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IFaceService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 22: {
                    return "userActivity";
                }
                case 21: {
                    return "getFeature";
                }
                case 20: {
                    return "setFeature";
                }
                case 19: {
                    return "enumerate";
                }
                case 18: {
                    return "setActiveUser";
                }
                case 17: {
                    return "addLockoutResetCallback";
                }
                case 16: {
                    return "resetLockout";
                }
                case 15: {
                    return "getAuthenticatorId";
                }
                case 14: {
                    return "hasEnrolledFaces";
                }
                case 13: {
                    return "revokeChallenge";
                }
                case 12: {
                    return "generateChallenge";
                }
                case 11: {
                    return "isHardwareDetected";
                }
                case 10: {
                    return "getEnrolledFaces";
                }
                case 9: {
                    return "rename";
                }
                case 8: {
                    return "remove";
                }
                case 7: {
                    return "cancelEnrollment";
                }
                case 6: {
                    return "enroll";
                }
                case 5: {
                    return "cancelAuthenticationFromService";
                }
                case 4: {
                    return "cancelAuthentication";
                }
                case 3: {
                    return "startPreparedClient";
                }
                case 2: {
                    return "prepareForAuthentication";
                }
                case 1: 
            }
            return "authenticate";
        }

        public static boolean setDefaultImpl(IFaceService iFaceService) {
            if (Proxy.sDefaultImpl == null && iFaceService != null) {
                Proxy.sDefaultImpl = iFaceService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.userActivity();
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getFeature(((Parcel)object).readInt(), IFaceServiceReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setFeature(n, bl, ((Parcel)object).createByteArray(), IFaceServiceReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enumerate(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), IFaceServiceReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setActiveUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addLockoutResetCallback(IBiometricServiceLockoutResetCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetLockout(((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getAuthenticatorId(((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasEnrolledFaces(((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.revokeChallenge(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.generateChallenge(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isHardwareDetected(((Parcel)object).readLong(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEnrolledFaces(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.rename(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.remove(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), IFaceServiceReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelEnrollment(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enroll(((Parcel)object).readStrongBinder(), ((Parcel)object).createByteArray(), IFaceServiceReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        int n3 = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        this.cancelAuthenticationFromService(iBinder, string2, n, n2, n3, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelAuthentication(((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startPreparedClient(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl = ((Parcel)object).readInt() != 0;
                        this.prepareForAuthentication(bl, ((Parcel)object).readStrongBinder(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), IBiometricServiceReceiverInternal.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.authenticate(((Parcel)object).readStrongBinder(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), IFaceServiceReceiver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IFaceService {
            public static IFaceService sDefaultImpl;
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
            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iBiometricServiceLockoutResetCallback != null ? iBiometricServiceLockoutResetCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addLockoutResetCallback(iBiometricServiceLockoutResetCallback);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void authenticate(IBinder iBinder, long l, int n, IFaceServiceReceiver iFaceServiceReceiver, int n2, String string2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block12 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel.writeInt(n);
                        IBinder iBinder2 = iFaceServiceReceiver != null ? iFaceServiceReceiver.asBinder() : null;
                        parcel.writeStrongBinder(iBinder2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n2);
                        parcel.writeString(string2);
                        if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().authenticate(iBinder, l, n, iFaceServiceReceiver, n2, string2);
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
                throw var1_7;
            }

            @Override
            public void cancelAuthentication(IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAuthentication(iBinder, string2);
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
            public void cancelAuthenticationFromService(IBinder iBinder, String string2, int n, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                        int n4 = bl ? 1 : 0;
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(5, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().cancelAuthenticationFromService(iBinder, string2, n, n2, n3, bl);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            @Override
            public void cancelEnrollment(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelEnrollment(iBinder);
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
            public void enroll(IBinder iBinder, byte[] arrby, IFaceServiceReceiver iFaceServiceReceiver, String string2, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeByteArray(arrby);
                    IBinder iBinder2 = iFaceServiceReceiver != null ? iFaceServiceReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeString(string2);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enroll(iBinder, arrby, iFaceServiceReceiver, string2, arrn);
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
            public void enumerate(IBinder iBinder, int n, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    IBinder iBinder2 = iFaceServiceReceiver != null ? iFaceServiceReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enumerate(iBinder, n, iFaceServiceReceiver);
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
            public long generateChallenge(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().generateChallenge(iBinder);
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
            public long getAuthenticatorId(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getAuthenticatorId(string2);
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
            public List<Face> getEnrolledFaces(int n, String arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString((String)((Object)arrayList));
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getEnrolledFaces(n, (String)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(Face.CREATOR);
                    return arrayList;
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
            public void getFeature(int n, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iFaceServiceReceiver != null ? iFaceServiceReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getFeature(n, iFaceServiceReceiver);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean hasEnrolledFaces(int n, String string2) throws RemoteException {
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
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(14, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasEnrolledFaces(n, string2);
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
            public boolean isHardwareDetected(long l, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isHardwareDetected(l, string2);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void prepareForAuthentication(boolean bl, IBinder iBinder, long l, int n, IBiometricServiceReceiverInternal iBiometricServiceReceiverInternal, String string2, int n2, int n3, int n4, int n5) throws RemoteException {
                void var2_5;
                Parcel parcel2;
                Parcel parcel;
                block6 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n6 = bl ? 1 : 0;
                    parcel.writeInt(n6);
                    try {
                        parcel.writeStrongBinder(iBinder);
                        parcel.writeLong(l);
                        parcel.writeInt(n);
                        IBinder iBinder2 = iBiometricServiceReceiverInternal != null ? iBiometricServiceReceiverInternal.asBinder() : null;
                        parcel.writeStrongBinder(iBinder2);
                        parcel.writeString(string2);
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        parcel.writeInt(n4);
                        parcel.writeInt(n5);
                        if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().prepareForAuthentication(bl, iBinder, l, n, iBiometricServiceReceiverInternal, string2, n2, n3, n4, n5);
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
                    break block6;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var2_5;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void remove(IBinder iBinder, int n, int n2, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder2 = iFaceServiceReceiver != null ? iFaceServiceReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().remove(iBinder, n, n2, iFaceServiceReceiver);
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
            public void rename(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rename(n, string2);
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
            public void resetLockout(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetLockout(arrby);
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
            public int revokeChallenge(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().revokeChallenge(iBinder);
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
            public void setActiveUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActiveUser(n);
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
            public void setFeature(int n, boolean bl, byte[] arrby, IFaceServiceReceiver iFaceServiceReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    IBinder iBinder = iFaceServiceReceiver != null ? iFaceServiceReceiver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFeature(n, bl, arrby, iFaceServiceReceiver);
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
            public void startPreparedClient(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPreparedClient(n);
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
            public void userActivity() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().userActivity();
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

