/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.slice.Slice;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public final class SliceItem
implements Parcelable {
    public static final Parcelable.Creator<SliceItem> CREATOR = new Parcelable.Creator<SliceItem>(){

        @Override
        public SliceItem createFromParcel(Parcel parcel) {
            return new SliceItem(parcel);
        }

        public SliceItem[] newArray(int n) {
            return new SliceItem[n];
        }
    };
    public static final String FORMAT_ACTION = "action";
    public static final String FORMAT_BUNDLE = "bundle";
    public static final String FORMAT_IMAGE = "image";
    public static final String FORMAT_INT = "int";
    public static final String FORMAT_LONG = "long";
    public static final String FORMAT_REMOTE_INPUT = "input";
    public static final String FORMAT_SLICE = "slice";
    public static final String FORMAT_TEXT = "text";
    @Deprecated
    public static final String FORMAT_TIMESTAMP = "long";
    private static final String TAG = "SliceItem";
    private final String mFormat;
    protected String[] mHints;
    private final Object mObj;
    private final String mSubType;

    public SliceItem(PendingIntent pendingIntent, Slice slice, String string2, String string3, String[] arrstring) {
        this(new Pair<PendingIntent, Slice>(pendingIntent, slice), string2, string3, arrstring);
    }

    public SliceItem(Parcel parcel) {
        this.mHints = parcel.readStringArray();
        this.mFormat = parcel.readString();
        this.mSubType = parcel.readString();
        this.mObj = SliceItem.readObj(this.mFormat, parcel);
    }

    public SliceItem(Object object, String string2, String string3, List<String> list) {
        this(object, string2, string3, list.toArray(new String[list.size()]));
    }

    public SliceItem(Object object, String string2, String string3, String[] arrstring) {
        this.mHints = arrstring;
        this.mFormat = string2;
        this.mSubType = string3;
        this.mObj = object;
    }

    private static String getBaseType(String string2) {
        int n = string2.indexOf(47);
        if (n >= 0) {
            return string2.substring(0, n);
        }
        return string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static Object readObj(String var0, Parcel var1_1) {
        block20 : {
            var2_2 = SliceItem.getBaseType(var0);
            switch (var2_2.hashCode()) {
                case 109526418: {
                    if (!var2_2.equals("slice")) break;
                    var3_3 = 0;
                    break block20;
                }
                case 100358090: {
                    if (!var2_2.equals("input")) break;
                    var3_3 = 6;
                    break block20;
                }
                case 100313435: {
                    if (!var2_2.equals("image")) break;
                    var3_3 = 2;
                    break block20;
                }
                case 3556653: {
                    if (!var2_2.equals("text")) break;
                    var3_3 = 1;
                    break block20;
                }
                case 3327612: {
                    if (!var2_2.equals("long")) break;
                    var3_3 = 5;
                    break block20;
                }
                case 104431: {
                    if (!var2_2.equals("int")) break;
                    var3_3 = 4;
                    break block20;
                }
                case -1377881982: {
                    if (!var2_2.equals("bundle")) break;
                    var3_3 = 7;
                    break block20;
                }
                case -1422950858: {
                    if (!var2_2.equals("action")) break;
                    var3_3 = 3;
                    break block20;
                }
            }
            ** break;
lbl36: // 1 sources:
            var3_3 = -1;
        }
        switch (var3_3) {
            default: {
                var1_1 = new StringBuilder();
                var1_1.append("Unsupported type ");
                var1_1.append(var0);
                throw new RuntimeException(var1_1.toString());
            }
            case 7: {
                return Bundle.CREATOR.createFromParcel((Parcel)var1_1);
            }
            case 6: {
                return RemoteInput.CREATOR.createFromParcel((Parcel)var1_1);
            }
            case 5: {
                return var1_1.readLong();
            }
            case 4: {
                return var1_1.readInt();
            }
            case 3: {
                return new Pair<PendingIntent, Slice>(PendingIntent.CREATOR.createFromParcel((Parcel)var1_1), Slice.CREATOR.createFromParcel((Parcel)var1_1));
            }
            case 2: {
                return Icon.CREATOR.createFromParcel((Parcel)var1_1);
            }
            case 1: {
                return TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)var1_1);
            }
            case 0: 
        }
        return Slice.CREATOR.createFromParcel((Parcel)var1_1);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void writeObj(Parcel var0, int var1_1, Object var2_2, String var3_3) {
        block17 : {
            var3_3 = SliceItem.getBaseType(var3_3);
            switch (var3_3.hashCode()) {
                case 109526418: {
                    if (!var3_3.equals("slice")) break;
                    var4_4 = 0;
                    break block17;
                }
                case 100358090: {
                    if (!var3_3.equals("input")) break;
                    var4_4 = 2;
                    break block17;
                }
                case 100313435: {
                    if (!var3_3.equals("image")) break;
                    var4_4 = 1;
                    break block17;
                }
                case 3556653: {
                    if (!var3_3.equals("text")) break;
                    var4_4 = 5;
                    break block17;
                }
                case 3327612: {
                    if (!var3_3.equals("long")) break;
                    var4_4 = 7;
                    break block17;
                }
                case 104431: {
                    if (!var3_3.equals("int")) break;
                    var4_4 = 6;
                    break block17;
                }
                case -1377881982: {
                    if (!var3_3.equals("bundle")) break;
                    var4_4 = 3;
                    break block17;
                }
                case -1422950858: {
                    if (!var3_3.equals("action")) break;
                    var4_4 = 4;
                    break block17;
                }
            }
            ** break;
lbl36: // 1 sources:
            var4_4 = -1;
        }
        switch (var4_4) {
            default: {
                return;
            }
            case 7: {
                var0.writeLong((Long)var2_2);
                return;
            }
            case 6: {
                var0.writeInt((Integer)var2_2);
                return;
            }
            case 5: {
                TextUtils.writeToParcel((CharSequence)var2_2, var0, var1_1);
                return;
            }
            case 4: {
                ((PendingIntent)((Pair)var2_2).first).writeToParcel(var0, var1_1);
                ((Slice)((Pair)var2_2).second).writeToParcel(var0, var1_1);
                return;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
        }
        ((Parcelable)var2_2).writeToParcel(var0, var1_1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PendingIntent getAction() {
        return (PendingIntent)((Pair)this.mObj).first;
    }

    public Bundle getBundle() {
        return (Bundle)this.mObj;
    }

    public String getFormat() {
        return this.mFormat;
    }

    public List<String> getHints() {
        return Arrays.asList(this.mHints);
    }

    public Icon getIcon() {
        return (Icon)this.mObj;
    }

    public int getInt() {
        return (Integer)this.mObj;
    }

    public long getLong() {
        return (Long)this.mObj;
    }

    public RemoteInput getRemoteInput() {
        return (RemoteInput)this.mObj;
    }

    public RemoteViews getRemoteView() {
        return (RemoteViews)this.mObj;
    }

    public Slice getSlice() {
        if (FORMAT_ACTION.equals(this.getFormat())) {
            return (Slice)((Pair)this.mObj).second;
        }
        return (Slice)this.mObj;
    }

    public String getSubType() {
        return this.mSubType;
    }

    public CharSequence getText() {
        return (CharSequence)this.mObj;
    }

    @Deprecated
    public long getTimestamp() {
        return (Long)this.mObj;
    }

    public boolean hasAnyHints(String[] arrstring) {
        if (arrstring == null) {
            return false;
        }
        for (String string2 : arrstring) {
            if (!ArrayUtils.contains(this.mHints, string2)) continue;
            return true;
        }
        return false;
    }

    public boolean hasHint(String string2) {
        return ArrayUtils.contains(this.mHints, string2);
    }

    public boolean hasHints(String[] arrstring) {
        if (arrstring == null) {
            return true;
        }
        for (String string2 : arrstring) {
            if (TextUtils.isEmpty(string2) || ArrayUtils.contains(this.mHints, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStringArray(this.mHints);
        parcel.writeString(this.mFormat);
        parcel.writeString(this.mSubType);
        SliceItem.writeObj(parcel, n, this.mObj, this.mFormat);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SliceType {
    }

}

