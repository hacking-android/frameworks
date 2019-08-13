/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

abstract class BaseParceledListSlice<T>
implements Parcelable {
    private static boolean DEBUG = false;
    private static final int MAX_IPC_SIZE = 65536;
    private static String TAG = "ParceledListSlice";
    private int mInlineCountLimit = Integer.MAX_VALUE;
    private final List<T> mList;

    static {
        DEBUG = false;
    }

    BaseParceledListSlice(Parcel object, ClassLoader classLoader) {
        Object object2;
        List<T> list;
        Object object3;
        Object object4;
        int n = ((Parcel)object).readInt();
        this.mList = new ArrayList<T>(n);
        if (DEBUG) {
            object3 = TAG;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Retrieving ");
            ((StringBuilder)object4).append(n);
            ((StringBuilder)object4).append(" items");
            Log.d((String)object3, ((StringBuilder)object4).toString());
        }
        if (n <= 0) {
            return;
        }
        Parcelable.Creator<?> creator = this.readParcelableCreator((Parcel)object, classLoader);
        int n2 = 0;
        object4 = null;
        do {
            object3 = ": ";
            if (n2 >= n || ((Parcel)object).readInt() == 0) break;
            object3 = this.readCreator(creator, (Parcel)object, classLoader);
            if (object4 == null) {
                object4 = object3.getClass();
            } else {
                BaseParceledListSlice.verifySameType(object4, object3.getClass());
            }
            this.mList.add(object3);
            if (DEBUG) {
                object2 = TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Read inline #");
                ((StringBuilder)object3).append(n2);
                ((StringBuilder)object3).append(": ");
                list = this.mList;
                ((StringBuilder)object3).append(list.get(list.size() - 1));
                Log.d((String)object2, ((StringBuilder)object3).toString());
            }
            ++n2;
        } while (true);
        if (n2 >= n) {
            return;
        }
        object2 = ((Parcel)object).readStrongBinder();
        object = object3;
        while (n2 < n) {
            if (DEBUG) {
                object3 = TAG;
                list = new StringBuilder();
                ((StringBuilder)((Object)list)).append("Reading more @");
                ((StringBuilder)((Object)list)).append(n2);
                ((StringBuilder)((Object)list)).append(" of ");
                ((StringBuilder)((Object)list)).append(n);
                ((StringBuilder)((Object)list)).append(": retriever=");
                ((StringBuilder)((Object)list)).append(object2);
                Log.d((String)object3, ((StringBuilder)((Object)list)).toString());
            }
            object3 = Parcel.obtain();
            list = Parcel.obtain();
            ((Parcel)object3).writeInt(n2);
            try {
                object2.transact(1, (Parcel)object3, (Parcel)((Object)list), 0);
            }
            catch (RemoteException remoteException) {
                object4 = TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("Failure retrieving array; only received ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" of ");
                ((StringBuilder)object).append(n);
                Log.w((String)object4, ((StringBuilder)object).toString(), remoteException);
                return;
            }
            while (n2 < n && ((Parcel)((Object)list)).readInt() != 0) {
                Object object5 = this.readCreator(creator, (Parcel)((Object)list), classLoader);
                BaseParceledListSlice.verifySameType(object4, object5.getClass());
                this.mList.add(object5);
                if (DEBUG) {
                    String string2 = TAG;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append("Read extra #");
                    ((StringBuilder)object5).append(n2);
                    ((StringBuilder)object5).append((String)object);
                    List<T> list2 = this.mList;
                    ((StringBuilder)object5).append(list2.get(list2.size() - 1));
                    Log.d(string2, ((StringBuilder)object5).toString());
                }
                ++n2;
            }
            ((Parcel)((Object)list)).recycle();
            ((Parcel)object3).recycle();
        }
    }

    public BaseParceledListSlice(List<T> list) {
        this.mList = list;
    }

    private T readCreator(Parcelable.Creator<?> creator, Parcel parcel, ClassLoader classLoader) {
        if (creator instanceof Parcelable.ClassLoaderCreator) {
            return ((Parcelable.ClassLoaderCreator)creator).createFromParcel(parcel, classLoader);
        }
        return (T)creator.createFromParcel(parcel);
    }

    private static void verifySameType(Class<?> object, Class<?> class_) {
        if (!class_.equals(object)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't unparcel type ");
            stringBuilder.append(class_.getName());
            stringBuilder.append(" in list of type ");
            object = object == null ? null : ((Class)object).getName();
            stringBuilder.append((String)object);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @UnsupportedAppUsage
    public List<T> getList() {
        return this.mList;
    }

    protected abstract Parcelable.Creator<?> readParcelableCreator(Parcel var1, ClassLoader var2);

    public void setInlineCountLimit(int n) {
        this.mInlineCountLimit = n;
    }

    protected abstract void writeElement(T var1, Parcel var2, int var3);

    @UnsupportedAppUsage
    protected abstract void writeParcelableCreator(T var1, Parcel var2);

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        CharSequence charSequence;
        Object object;
        final int n2 = this.mList.size();
        parcel.writeInt(n2);
        if (DEBUG) {
            charSequence = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("Writing ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" items");
            Log.d((String)charSequence, ((StringBuilder)object).toString());
        }
        if (n2 > 0) {
            Object object2;
            int n3;
            object = this.mList.get(0).getClass();
            this.writeParcelableCreator(this.mList.get(0), parcel);
            for (n3 = 0; n3 < n2 && n3 < this.mInlineCountLimit && parcel.dataSize() < 65536; ++n3) {
                parcel.writeInt(1);
                charSequence = this.mList.get(n3);
                BaseParceledListSlice.verifySameType(object, charSequence.getClass());
                this.writeElement(charSequence, parcel, n);
                if (!DEBUG) continue;
                charSequence = TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Wrote inline #");
                ((StringBuilder)object2).append(n3);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(this.mList.get(n3));
                Log.d((String)charSequence, ((StringBuilder)object2).toString());
            }
            if (n3 < n2) {
                parcel.writeInt(0);
                object2 = new Binder((Class)object, n){
                    final /* synthetic */ int val$callFlags;
                    final /* synthetic */ Class val$listElementClass;
                    {
                        this.val$listElementClass = class_;
                        this.val$callFlags = n22;
                    }

                    @Override
                    protected boolean onTransact(int n, Parcel object, Parcel parcel, int n22) throws RemoteException {
                        CharSequence charSequence;
                        if (n != 1) {
                            return super.onTransact(n, (Parcel)object, parcel, n22);
                        }
                        n = n22 = ((Parcel)object).readInt();
                        if (DEBUG) {
                            charSequence = TAG;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Writing more @");
                            ((StringBuilder)object).append(n22);
                            ((StringBuilder)object).append(" of ");
                            ((StringBuilder)object).append(n2);
                            Log.d((String)charSequence, ((StringBuilder)object).toString());
                            n = n22;
                        }
                        while (n < n2 && parcel.dataSize() < 65536) {
                            parcel.writeInt(1);
                            object = BaseParceledListSlice.this.mList.get(n);
                            BaseParceledListSlice.verifySameType(this.val$listElementClass, object.getClass());
                            BaseParceledListSlice.this.writeElement(object, parcel, this.val$callFlags);
                            if (DEBUG) {
                                charSequence = TAG;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Wrote extra #");
                                ((StringBuilder)object).append(n);
                                ((StringBuilder)object).append(": ");
                                ((StringBuilder)object).append(BaseParceledListSlice.this.mList.get(n));
                                Log.d((String)charSequence, ((StringBuilder)object).toString());
                            }
                            ++n;
                        }
                        if (n < n2) {
                            if (DEBUG) {
                                object = TAG;
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append("Breaking @");
                                ((StringBuilder)charSequence).append(n);
                                ((StringBuilder)charSequence).append(" of ");
                                ((StringBuilder)charSequence).append(n2);
                                Log.d((String)object, ((StringBuilder)charSequence).toString());
                            }
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                };
                if (DEBUG) {
                    object = TAG;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Breaking @");
                    ((StringBuilder)charSequence).append(n3);
                    ((StringBuilder)charSequence).append(" of ");
                    ((StringBuilder)charSequence).append(n2);
                    ((StringBuilder)charSequence).append(": retriever=");
                    ((StringBuilder)charSequence).append(object2);
                    Log.d((String)object, ((StringBuilder)charSequence).toString());
                }
                parcel.writeStrongBinder((IBinder)object2);
            }
        }
    }

}

