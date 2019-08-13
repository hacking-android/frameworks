/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf.nano.android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.framework.protobuf.nano.InvalidProtocolBufferNanoException;
import com.android.framework.protobuf.nano.MessageNano;
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

    @Override
    public T createFromParcel(Parcel object) {
        String string2 = ((Parcel)object).readString();
        byte[] arrby = ((Parcel)object).createByteArray();
        Object object2 = null;
        Object object3 = null;
        Object object4 = null;
        Object object5 = null;
        Object object6 = null;
        Object object7 = null;
        object7 = object = Class.forName(string2, false, this.getClass().getClassLoader()).asSubclass(MessageNano.class).getConstructor(new Class[0]).newInstance(new Object[0]);
        object2 = object;
        object3 = object;
        object4 = object;
        object5 = object;
        object6 = object;
        try {
            MessageNano.mergeFrom(object, arrby);
        }
        catch (InvalidProtocolBufferNanoException invalidProtocolBufferNanoException) {
            Log.e(TAG, "Exception trying to create proto from parcel", invalidProtocolBufferNanoException);
            object = object7;
        }
        catch (InstantiationException instantiationException) {
            Log.e(TAG, "Exception trying to create proto from parcel", instantiationException);
            object = object2;
        }
        catch (IllegalAccessException illegalAccessException) {
            Log.e(TAG, "Exception trying to create proto from parcel", illegalAccessException);
            object = object3;
        }
        catch (InvocationTargetException invocationTargetException) {
            Log.e(TAG, "Exception trying to create proto from parcel", invocationTargetException);
            object = object4;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.e(TAG, "Exception trying to create proto from parcel", noSuchMethodException);
            object = object5;
        }
        catch (ClassNotFoundException classNotFoundException) {
            Log.e(TAG, "Exception trying to create proto from parcel", classNotFoundException);
            object = object6;
        }
        return (T)object;
    }

    @Override
    public T[] newArray(int n) {
        return (MessageNano[])Array.newInstance(this.mClazz, n);
    }
}

