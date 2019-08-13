/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Matrix;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.InputChannel;
import com.android.internal.view.IInputMethodSession;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class InputBindResult
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<InputBindResult> CREATOR = new Parcelable.Creator<InputBindResult>(){

        @Override
        public InputBindResult createFromParcel(Parcel parcel) {
            return new InputBindResult(parcel);
        }

        public InputBindResult[] newArray(int n) {
            return new InputBindResult[n];
        }
    };
    public static final InputBindResult DISPLAY_ID_MISMATCH;
    public static final InputBindResult IME_NOT_CONNECTED;
    public static final InputBindResult INVALID_CLIENT;
    public static final InputBindResult INVALID_DISPLAY_ID;
    public static final InputBindResult INVALID_PACKAGE_NAME;
    public static final InputBindResult INVALID_USER;
    public static final InputBindResult NOT_IME_TARGET_WINDOW;
    public static final InputBindResult NO_EDITOR;
    public static final InputBindResult NO_IME;
    public static final InputBindResult NULL;
    public static final InputBindResult NULL_EDITOR_INFO;
    public final InputChannel channel;
    public final String id;
    private final float[] mActivityViewToScreenMatrixValues;
    @UnsupportedAppUsage
    public final IInputMethodSession method;
    public final int result;
    public final int sequence;

    static {
        NULL = InputBindResult.error(4);
        NO_IME = InputBindResult.error(5);
        NO_EDITOR = InputBindResult.error(12);
        INVALID_PACKAGE_NAME = InputBindResult.error(6);
        NULL_EDITOR_INFO = InputBindResult.error(10);
        NOT_IME_TARGET_WINDOW = InputBindResult.error(11);
        IME_NOT_CONNECTED = InputBindResult.error(8);
        INVALID_USER = InputBindResult.error(9);
        DISPLAY_ID_MISMATCH = InputBindResult.error(13);
        INVALID_DISPLAY_ID = InputBindResult.error(14);
        INVALID_CLIENT = InputBindResult.error(15);
    }

    public InputBindResult(int n, IInputMethodSession iInputMethodSession, InputChannel inputChannel, String string2, int n2, Matrix matrix) {
        this.result = n;
        this.method = iInputMethodSession;
        this.channel = inputChannel;
        this.id = string2;
        this.sequence = n2;
        if (matrix == null) {
            this.mActivityViewToScreenMatrixValues = null;
        } else {
            this.mActivityViewToScreenMatrixValues = new float[9];
            matrix.getValues(this.mActivityViewToScreenMatrixValues);
        }
    }

    InputBindResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.method = IInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
        this.channel = parcel.readInt() != 0 ? InputChannel.CREATOR.createFromParcel(parcel) : null;
        this.id = parcel.readString();
        this.sequence = parcel.readInt();
        this.mActivityViewToScreenMatrixValues = parcel.createFloatArray();
    }

    private static InputBindResult error(int n) {
        return new InputBindResult(n, null, null, null, -1, null);
    }

    @Override
    public int describeContents() {
        InputChannel inputChannel = this.channel;
        int n = inputChannel != null ? inputChannel.describeContents() : 0;
        return n;
    }

    public Matrix getActivityViewToScreenMatrix() {
        if (this.mActivityViewToScreenMatrixValues == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setValues(this.mActivityViewToScreenMatrixValues);
        return matrix;
    }

    public String getResultString() {
        switch (this.result) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown(");
                stringBuilder.append(this.result);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            case 15: {
                return "ERROR_INVALID_CLIENT";
            }
            case 14: {
                return "ERROR_INVALID_DISPLAY_ID";
            }
            case 13: {
                return "ERROR_DISPLAY_ID_MISMATCH";
            }
            case 12: {
                return "ERROR_NO_EDITOR";
            }
            case 11: {
                return "ERROR_NOT_IME_TARGET_WINDOW";
            }
            case 10: {
                return "ERROR_NULL_EDITOR_INFO";
            }
            case 9: {
                return "ERROR_INVALID_USER";
            }
            case 8: {
                return "ERROR_IME_NOT_CONNECTED";
            }
            case 7: {
                return "ERROR_SYSTEM_NOT_READY";
            }
            case 6: {
                return "ERROR_INVALID_PACKAGE_NAME";
            }
            case 5: {
                return "ERROR_NO_IME";
            }
            case 4: {
                return "ERROR_NULL";
            }
            case 3: {
                return "SUCCESS_REPORT_WINDOW_FOCUS_ONLY";
            }
            case 2: {
                return "SUCCESS_WAITING_IME_BINDING";
            }
            case 1: {
                return "SUCCESS_WAITING_IME_SESSION";
            }
            case 0: 
        }
        return "SUCCESS_WITH_IME_SESSION";
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("InputBindResult{result=");
        stringBuilder.append(this.getResultString());
        stringBuilder.append(" method=");
        stringBuilder.append(this.method);
        stringBuilder.append(" id=");
        stringBuilder.append(this.id);
        stringBuilder.append(" sequence=");
        stringBuilder.append(this.sequence);
        stringBuilder.append(" activityViewToScreenMatrix=");
        stringBuilder.append(this.getActivityViewToScreenMatrix());
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.result);
        parcel.writeStrongInterface(this.method);
        if (this.channel != null) {
            parcel.writeInt(1);
            this.channel.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.id);
        parcel.writeInt(this.sequence);
        parcel.writeFloatArray(this.mActivityViewToScreenMatrixValues);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResultCode {
        public static final int ERROR_DISPLAY_ID_MISMATCH = 13;
        public static final int ERROR_IME_NOT_CONNECTED = 8;
        public static final int ERROR_INVALID_CLIENT = 15;
        public static final int ERROR_INVALID_DISPLAY_ID = 14;
        public static final int ERROR_INVALID_PACKAGE_NAME = 6;
        public static final int ERROR_INVALID_USER = 9;
        public static final int ERROR_NOT_IME_TARGET_WINDOW = 11;
        public static final int ERROR_NO_EDITOR = 12;
        public static final int ERROR_NO_IME = 5;
        public static final int ERROR_NULL = 4;
        public static final int ERROR_NULL_EDITOR_INFO = 10;
        public static final int ERROR_SYSTEM_NOT_READY = 7;
        public static final int SUCCESS_REPORT_WINDOW_FOCUS_ONLY = 3;
        public static final int SUCCESS_WAITING_IME_BINDING = 2;
        public static final int SUCCESS_WAITING_IME_SESSION = 1;
        public static final int SUCCESS_WITH_IME_SESSION = 0;
    }

}

