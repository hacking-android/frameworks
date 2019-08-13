/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.telephony.uicc.AdnRecord;
import java.util.ArrayList;
import java.util.List;

public interface IIccPhoneBook
extends IInterface {
    public List<AdnRecord> getAdnRecordsInEf(int var1) throws RemoteException;

    public List<AdnRecord> getAdnRecordsInEfForSubscriber(int var1, int var2) throws RemoteException;

    public int[] getAdnRecordsSize(int var1) throws RemoteException;

    public int[] getAdnRecordsSizeForSubscriber(int var1, int var2) throws RemoteException;

    public boolean updateAdnRecordsInEfByIndex(int var1, String var2, String var3, int var4, String var5) throws RemoteException;

    public boolean updateAdnRecordsInEfByIndexForSubscriber(int var1, int var2, String var3, String var4, int var5, String var6) throws RemoteException;

    public boolean updateAdnRecordsInEfBySearch(int var1, String var2, String var3, String var4, String var5, String var6) throws RemoteException;

    public boolean updateAdnRecordsInEfBySearchForSubscriber(int var1, int var2, String var3, String var4, String var5, String var6, String var7) throws RemoteException;

    public static class Default
    implements IIccPhoneBook {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public List<AdnRecord> getAdnRecordsInEf(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<AdnRecord> getAdnRecordsInEfForSubscriber(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public int[] getAdnRecordsSize(int n) throws RemoteException {
            return null;
        }

        @Override
        public int[] getAdnRecordsSizeForSubscriber(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public boolean updateAdnRecordsInEfByIndex(int n, String string, String string2, int n2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean updateAdnRecordsInEfByIndexForSubscriber(int n, int n2, String string, String string2, int n3, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean updateAdnRecordsInEfBySearch(int n, String string, String string2, String string3, String string4, String string5) throws RemoteException {
            return false;
        }

        @Override
        public boolean updateAdnRecordsInEfBySearchForSubscriber(int n, int n2, String string, String string2, String string3, String string4, String string5) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIccPhoneBook {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IIccPhoneBook";
        static final int TRANSACTION_getAdnRecordsInEf = 1;
        static final int TRANSACTION_getAdnRecordsInEfForSubscriber = 2;
        static final int TRANSACTION_getAdnRecordsSize = 7;
        static final int TRANSACTION_getAdnRecordsSizeForSubscriber = 8;
        static final int TRANSACTION_updateAdnRecordsInEfByIndex = 5;
        static final int TRANSACTION_updateAdnRecordsInEfByIndexForSubscriber = 6;
        static final int TRANSACTION_updateAdnRecordsInEfBySearch = 3;
        static final int TRANSACTION_updateAdnRecordsInEfBySearchForSubscriber = 4;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static IIccPhoneBook asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIccPhoneBook) {
                return (IIccPhoneBook)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIccPhoneBook getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IIccPhoneBook iIccPhoneBook) {
            if (Proxy.sDefaultImpl == null && iIccPhoneBook != null) {
                Proxy.sDefaultImpl = iIccPhoneBook;
                return true;
            }
            return false;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 8: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getAdnRecordsSizeForSubscriber(object.readInt(), object.readInt());
                        parcel.writeNoException();
                        parcel.writeIntArray(object);
                        return true;
                    }
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getAdnRecordsSize(object.readInt());
                        parcel.writeNoException();
                        parcel.writeIntArray(object);
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.updateAdnRecordsInEfByIndexForSubscriber(object.readInt(), object.readInt(), object.readString(), object.readString(), object.readInt(), object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.updateAdnRecordsInEfByIndex(object.readInt(), object.readString(), object.readString(), object.readInt(), object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.updateAdnRecordsInEfBySearchForSubscriber(object.readInt(), object.readInt(), object.readString(), object.readString(), object.readString(), object.readString(), object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.updateAdnRecordsInEfBySearch(object.readInt(), object.readString(), object.readString(), object.readString(), object.readString(), object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getAdnRecordsInEfForSubscriber(object.readInt(), object.readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList((List)object);
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object = this.getAdnRecordsInEf(object.readInt());
                parcel.writeNoException();
                parcel.writeTypedList((List)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IIccPhoneBook {
            public static IIccPhoneBook sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public List<AdnRecord> getAdnRecordsInEf(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AdnRecord> list = Stub.getDefaultImpl().getAdnRecordsInEf(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createTypedArrayList(AdnRecord.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<AdnRecord> getAdnRecordsInEfForSubscriber(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<AdnRecord> list = Stub.getDefaultImpl().getAdnRecordsInEfForSubscriber(n, n2);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createTypedArrayList(AdnRecord.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getAdnRecordsSize(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getAdnRecordsSize(n);
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
            public int[] getAdnRecordsSizeForSubscriber(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getAdnRecordsSizeForSubscriber(n, n2);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
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
            public boolean updateAdnRecordsInEfByIndex(int n, String string, String string2, int n2, String string3) throws RemoteException {
                Parcel parcel;
                void var2_10;
                Parcel parcel2;
                block17 : {
                    boolean bl;
                    block16 : {
                        block15 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            parcel2.writeInt(n);
                            parcel2.writeString(string);
                            parcel2.writeString(string2);
                            parcel2.writeInt(n2);
                            parcel2.writeString(string3);
                            try {
                                IBinder iBinder = this.mRemote;
                                bl = false;
                                if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block15;
                                bl = Stub.getDefaultImpl().updateAdnRecordsInEfByIndex(n, string, string2, n2, string3);
                            }
                            catch (Throwable throwable) {}
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n == 0) break block16;
                        bl = true;
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                    break block17;
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_10;
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
            public boolean updateAdnRecordsInEfByIndexForSubscriber(int n, int n2, String string, String string2, int n3, String string3) throws RemoteException {
                void var3_11;
                Parcel parcel;
                Parcel parcel2;
                block17 : {
                    boolean bl;
                    block16 : {
                        block15 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            parcel2.writeInt(n);
                            parcel2.writeInt(n2);
                            parcel2.writeString(string);
                            parcel2.writeString(string2);
                            parcel2.writeInt(n3);
                            try {
                                parcel2.writeString(string3);
                                IBinder iBinder = this.mRemote;
                                bl = false;
                                if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block15;
                                bl = Stub.getDefaultImpl().updateAdnRecordsInEfByIndexForSubscriber(n, n2, string, string2, n3, string3);
                            }
                            catch (Throwable throwable) {}
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n == 0) break block16;
                        bl = true;
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                    break block17;
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var3_11;
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
            public boolean updateAdnRecordsInEfBySearch(int n, String string, String string2, String string3, String string4, String string5) throws RemoteException {
                Parcel parcel;
                void var2_10;
                Parcel parcel2;
                block17 : {
                    boolean bl;
                    block16 : {
                        block15 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            parcel2.writeInt(n);
                            parcel2.writeString(string);
                            parcel2.writeString(string2);
                            parcel2.writeString(string3);
                            parcel2.writeString(string4);
                            try {
                                parcel2.writeString(string5);
                                IBinder iBinder = this.mRemote;
                                bl = false;
                                if (iBinder.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block15;
                                bl = Stub.getDefaultImpl().updateAdnRecordsInEfBySearch(n, string, string2, string3, string4, string5);
                            }
                            catch (Throwable throwable) {}
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        if (n == 0) break block16;
                        bl = true;
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                    break block17;
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_10;
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
            public boolean updateAdnRecordsInEfBySearchForSubscriber(int n, int n2, String string, String string2, String string3, String string4, String string5) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var3_10;
                block15 : {
                    boolean bl;
                    block14 : {
                        block13 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            parcel.writeInt(n);
                            parcel.writeInt(n2);
                            parcel.writeString(string);
                            parcel.writeString(string2);
                            try {
                                parcel.writeString(string3);
                                parcel.writeString(string4);
                                parcel.writeString(string5);
                                IBinder iBinder = this.mRemote;
                                bl = false;
                                if (iBinder.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block13;
                                bl = Stub.getDefaultImpl().updateAdnRecordsInEfBySearchForSubscriber(n, n2, string, string2, string3, string4, string5);
                            }
                            catch (Throwable throwable) {}
                            parcel2.recycle();
                            parcel.recycle();
                            return bl;
                        }
                        parcel2.readException();
                        n = parcel2.readInt();
                        if (n == 0) break block14;
                        bl = true;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                    break block15;
                    catch (Throwable throwable) {
                        break block15;
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var3_10;
            }
        }

    }

}

