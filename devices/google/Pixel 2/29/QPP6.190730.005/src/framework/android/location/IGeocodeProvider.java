/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.annotation.UnsupportedAppUsage;
import android.location.Address;
import android.location.GeocoderParams;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IGeocodeProvider
extends IInterface {
    @UnsupportedAppUsage
    public String getFromLocation(double var1, double var3, int var5, GeocoderParams var6, List<Address> var7) throws RemoteException;

    @UnsupportedAppUsage
    public String getFromLocationName(String var1, double var2, double var4, double var6, double var8, int var10, GeocoderParams var11, List<Address> var12) throws RemoteException;

    public static class Default
    implements IGeocodeProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getFromLocation(double d, double d2, int n, GeocoderParams geocoderParams, List<Address> list) throws RemoteException {
            return null;
        }

        @Override
        public String getFromLocationName(String string2, double d, double d2, double d3, double d4, int n, GeocoderParams geocoderParams, List<Address> list) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGeocodeProvider {
        private static final String DESCRIPTOR = "android.location.IGeocodeProvider";
        static final int TRANSACTION_getFromLocation = 1;
        static final int TRANSACTION_getFromLocationName = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGeocodeProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGeocodeProvider) {
                return (IGeocodeProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGeocodeProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getFromLocationName";
            }
            return "getFromLocation";
        }

        public static boolean setDefaultImpl(IGeocodeProvider iGeocodeProvider) {
            if (Proxy.sDefaultImpl == null && iGeocodeProvider != null) {
                Proxy.sDefaultImpl = iGeocodeProvider;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string2 = ((Parcel)object).readString();
                double d = ((Parcel)object).readDouble();
                double d2 = ((Parcel)object).readDouble();
                double d3 = ((Parcel)object).readDouble();
                double d4 = ((Parcel)object).readDouble();
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? GeocoderParams.CREATOR.createFromParcel((Parcel)object) : null;
                ArrayList<Address> arrayList = new ArrayList<Address>();
                object = this.getFromLocationName(string2, d, d2, d3, d4, n, (GeocoderParams)object, arrayList);
                parcel.writeNoException();
                parcel.writeString((String)object);
                parcel.writeTypedList(arrayList);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            double d = ((Parcel)object).readDouble();
            double d5 = ((Parcel)object).readDouble();
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? GeocoderParams.CREATOR.createFromParcel((Parcel)object) : null;
            ArrayList<Address> arrayList = new ArrayList<Address>();
            object = this.getFromLocation(d, d5, n, (GeocoderParams)object, arrayList);
            parcel.writeNoException();
            parcel.writeString((String)object);
            parcel.writeTypedList(arrayList);
            return true;
        }

        private static class Proxy
        implements IGeocodeProvider {
            public static IGeocodeProvider sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
            public String getFromLocation(double d, double d2, int n, GeocoderParams object, List<Address> list) throws RemoteException {
                void var6_9;
                Parcel parcel2;
                Parcel parcel;
                block12 : {
                    Parcelable.Creator<Address> creator;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeDouble(d);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeDouble(d2);
                        parcel2.writeInt(n);
                        if (object != null) {
                            parcel2.writeInt(1);
                            ((GeocoderParams)object).writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(1, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().getFromLocation(d, d2, n, (GeocoderParams)object, list);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readString();
                        creator = Address.CREATOR;
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.readTypedList(list, creator);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var6_9;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String getFromLocationName(String var1_1, double var2_9, double var4_10, double var6_11, double var8_12, int var10_13, GeocoderParams var11_14, List<Address> var12_15) throws RemoteException {
                block14 : {
                    block15 : {
                        block13 : {
                            var13_16 = Parcel.obtain();
                            var14_17 = Parcel.obtain();
                            var13_16.writeInterfaceToken("android.location.IGeocodeProvider");
                            var13_16.writeString((String)var1_1);
                            var13_16.writeDouble(var2_9);
                            var13_16.writeDouble(var4_10);
                            var13_16.writeDouble(var6_11);
                            var13_16.writeDouble(var8_12);
                            var13_16.writeInt(var10_13);
                            if (var11_14 == null) break block13;
                            try {
                                var13_16.writeInt(1);
                                var11_14.writeToParcel(var13_16, 0);
                                ** GOTO lbl21
                            }
                            catch (Throwable var1_2) {
                                break block14;
                            }
                        }
                        var13_16.writeInt(0);
lbl21: // 2 sources:
                        var15_18 = this.mRemote.transact(2, var13_16, var14_17, 0);
                        if (var15_18) break block15;
                        try {
                            if (Stub.getDefaultImpl() == null) break block15;
                            var16_19 = Stub.getDefaultImpl();
                        }
                        catch (Throwable var1_4) {
                            break block14;
                        }
                        try {
                            var1_1 = var16_19.getFromLocationName((String)var1_1, var2_9, var4_10, var6_11, var8_12, var10_13, (GeocoderParams)var11_14, var12_15);
                            var14_17.recycle();
                            var13_16.recycle();
                            return var1_1;
                        }
                        catch (Throwable var1_3) {}
                        break block14;
                    }
                    try {
                        var14_17.readException();
                        var16_20 = var14_17.readString();
                        var11_14 = Address.CREATOR;
                        var1_1 = var14_17;
                    }
                    catch (Throwable var1_6) {}
                    try {
                        var1_1.readTypedList(var12_15, var11_14);
                        var1_1.recycle();
                        var13_16.recycle();
                        return var16_20;
                    }
                    catch (Throwable var1_5) {}
                    break block14;
                    catch (Throwable var1_7) {
                        // empty catch block
                    }
                }
                var14_17.recycle();
                var13_16.recycle();
                throw var1_8;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

