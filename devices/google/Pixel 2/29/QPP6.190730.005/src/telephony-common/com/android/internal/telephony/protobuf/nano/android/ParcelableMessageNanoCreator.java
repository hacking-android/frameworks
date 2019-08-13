/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.android.internal.telephony.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.telephony.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.internal.telephony.protobuf.nano.MessageNano;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class ParcelableMessageNanoCreator<T extends MessageNano>
implements Parcelable.Creator<T> {
    private static final String TAG = "PMNCreator";
    private final Class<T> mClazz;

    public ParcelableMessageNanoCreator(Class<T> class_) {
        this.mClazz = class_;
    }

    static <T extends MessageNano> void writeToParcel(Class<T> class_, MessageNano messageNano, Parcel parcel) {
        parcel.writeString(class_.getName());
        parcel.writeByteArray(MessageNano.toByteArray(messageNano));
    }

    public T createFromParcel(Parcel object) {
        String string = object.readString();
        byte[] arrby = object.createByteArray();
        Object object2 = null;
        Object object3 = null;
        Object object4 = null;
        Object object5 = null;
        Object object6 = null;
        Object object7 = null;
        object7 = object = Class.forName(string, false, this.getClass().getClassLoader()).asSubclass(MessageNano.class).getConstructor(new Class[0]).newInstance(new Object[0]);
        object2 = object;
        object3 = object;
        object4 = object;
        object5 = object;
        object6 = object;
        try {
            MessageNano.mergeFrom(object, arrby);
        }
        catch (InvalidProtocolBufferNanoException invalidProtocolBufferNanoException) {
            Log.e((String)TAG, (String)"Exception trying to create proto from parcel", (Throwable)invalidProtocolBufferNanoException);
            object = object7;
        }
        catch (InstantiationException instantiationException) {
            Log.e((String)TAG, (String)"Exception trying to create proto from parcel", (Throwable)instantiationException);
            object = object2;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e((String)TAG, (String)"Exception trying to create proto from parcel", (Throwable)illegalAccessException);
            object = object3;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e((String)TAG, (String)"Exception trying to create proto from parcel", (Throwable)invocationTargetException);
            object = object4;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e((String)TAG, (String)"Exception trying to create proto from parcel", (Throwable)noSuchMethodException);
            object = object5;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e((String)TAG, (String)"Exception trying to create proto from parcel", (Throwable)classNotFoundException);
            object = object6;
        }
        return (T)object;
    }

    public T[] newArray(int n) {
        return (MessageNano[])Array.newInstance(this.mClazz, n);
    }
}

