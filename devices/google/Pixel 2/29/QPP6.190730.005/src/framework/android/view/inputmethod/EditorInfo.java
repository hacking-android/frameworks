/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Printer;
import java.util.Arrays;

public class EditorInfo
implements InputType,
Parcelable {
    public static final Parcelable.Creator<EditorInfo> CREATOR = new Parcelable.Creator<EditorInfo>(){

        @Override
        public EditorInfo createFromParcel(Parcel parcel) {
            EditorInfo editorInfo = new EditorInfo();
            editorInfo.inputType = parcel.readInt();
            editorInfo.imeOptions = parcel.readInt();
            editorInfo.privateImeOptions = parcel.readString();
            editorInfo.actionLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.actionId = parcel.readInt();
            editorInfo.initialSelStart = parcel.readInt();
            editorInfo.initialSelEnd = parcel.readInt();
            editorInfo.initialCapsMode = parcel.readInt();
            editorInfo.hintText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.label = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.packageName = parcel.readString();
            editorInfo.fieldId = parcel.readInt();
            editorInfo.fieldName = parcel.readString();
            editorInfo.extras = parcel.readBundle();
            LocaleList localeList = LocaleList.CREATOR.createFromParcel(parcel);
            if (localeList.isEmpty()) {
                localeList = null;
            }
            editorInfo.hintLocales = localeList;
            editorInfo.contentMimeTypes = parcel.readStringArray();
            editorInfo.targetInputMethodUser = UserHandle.readFromParcel(parcel);
            return editorInfo;
        }

        public EditorInfo[] newArray(int n) {
            return new EditorInfo[n];
        }
    };
    public static final int IME_ACTION_DONE = 6;
    public static final int IME_ACTION_GO = 2;
    public static final int IME_ACTION_NEXT = 5;
    public static final int IME_ACTION_NONE = 1;
    public static final int IME_ACTION_PREVIOUS = 7;
    public static final int IME_ACTION_SEARCH = 3;
    public static final int IME_ACTION_SEND = 4;
    public static final int IME_ACTION_UNSPECIFIED = 0;
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NAVIGATE_NEXT = 134217728;
    public static final int IME_FLAG_NAVIGATE_PREVIOUS = 67108864;
    public static final int IME_FLAG_NO_ACCESSORY_ACTION = 536870912;
    public static final int IME_FLAG_NO_ENTER_ACTION = 1073741824;
    public static final int IME_FLAG_NO_EXTRACT_UI = 268435456;
    public static final int IME_FLAG_NO_FULLSCREEN = 33554432;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    public static final int IME_MASK_ACTION = 255;
    public static final int IME_NULL = 0;
    public int actionId = 0;
    public CharSequence actionLabel = null;
    public String[] contentMimeTypes = null;
    public Bundle extras;
    public int fieldId;
    public String fieldName;
    public LocaleList hintLocales = null;
    public CharSequence hintText;
    public int imeOptions = 0;
    public int initialCapsMode = 0;
    public int initialSelEnd = -1;
    public int initialSelStart = -1;
    public int inputType = 0;
    public CharSequence label;
    public String packageName;
    public String privateImeOptions = null;
    public UserHandle targetInputMethodUser = null;

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("inputType=0x");
        stringBuilder.append(Integer.toHexString(this.inputType));
        stringBuilder.append(" imeOptions=0x");
        stringBuilder.append(Integer.toHexString(this.imeOptions));
        stringBuilder.append(" privateImeOptions=");
        stringBuilder.append(this.privateImeOptions);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("actionLabel=");
        stringBuilder.append((Object)this.actionLabel);
        stringBuilder.append(" actionId=");
        stringBuilder.append(this.actionId);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("initialSelStart=");
        stringBuilder.append(this.initialSelStart);
        stringBuilder.append(" initialSelEnd=");
        stringBuilder.append(this.initialSelEnd);
        stringBuilder.append(" initialCapsMode=0x");
        stringBuilder.append(Integer.toHexString(this.initialCapsMode));
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("hintText=");
        stringBuilder.append((Object)this.hintText);
        stringBuilder.append(" label=");
        stringBuilder.append((Object)this.label);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("packageName=");
        stringBuilder.append(this.packageName);
        stringBuilder.append(" fieldId=");
        stringBuilder.append(this.fieldId);
        stringBuilder.append(" fieldName=");
        stringBuilder.append(this.fieldName);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("extras=");
        stringBuilder.append(this.extras);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("hintLocales=");
        stringBuilder.append(this.hintLocales);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("contentMimeTypes=");
        stringBuilder.append(Arrays.toString(this.contentMimeTypes));
        printer.println(stringBuilder.toString());
        if (this.targetInputMethodUser != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("targetInputMethodUserId=");
            stringBuilder.append(this.targetInputMethodUser.getIdentifier());
            printer.println(stringBuilder.toString());
        }
    }

    public final void makeCompatible(int n) {
        if (n < 11) {
            n = this.inputType;
            int n2 = n & 4095;
            if (n2 != 2 && n2 != 18) {
                if (n2 != 209) {
                    if (n2 == 225) {
                        this.inputType = n & 16773120 | 129;
                    }
                } else {
                    this.inputType = n & 16773120 | 33;
                }
            } else {
                this.inputType = this.inputType & 16773120 | 2;
            }
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.inputType);
        parcel.writeInt(this.imeOptions);
        parcel.writeString(this.privateImeOptions);
        TextUtils.writeToParcel(this.actionLabel, parcel, n);
        parcel.writeInt(this.actionId);
        parcel.writeInt(this.initialSelStart);
        parcel.writeInt(this.initialSelEnd);
        parcel.writeInt(this.initialCapsMode);
        TextUtils.writeToParcel(this.hintText, parcel, n);
        TextUtils.writeToParcel(this.label, parcel, n);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.fieldId);
        parcel.writeString(this.fieldName);
        parcel.writeBundle(this.extras);
        LocaleList localeList = this.hintLocales;
        if (localeList != null) {
            localeList.writeToParcel(parcel, n);
        } else {
            LocaleList.getEmptyLocaleList().writeToParcel(parcel, n);
        }
        parcel.writeStringArray(this.contentMimeTypes);
        UserHandle.writeToParcel(this.targetInputMethodUser, parcel);
    }

}

