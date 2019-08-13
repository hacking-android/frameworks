/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.slice.SliceItem;
import android.app.slice.SliceSpec;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class Slice
implements Parcelable {
    public static final Parcelable.Creator<Slice> CREATOR = new Parcelable.Creator<Slice>(){

        @Override
        public Slice createFromParcel(Parcel parcel) {
            return new Slice(parcel);
        }

        public Slice[] newArray(int n) {
            return new Slice[n];
        }
    };
    public static final String EXTRA_RANGE_VALUE = "android.app.slice.extra.RANGE_VALUE";
    @Deprecated
    public static final String EXTRA_SLIDER_VALUE = "android.app.slice.extra.SLIDER_VALUE";
    public static final String EXTRA_TOGGLE_STATE = "android.app.slice.extra.TOGGLE_STATE";
    public static final String HINT_ACTIONS = "actions";
    public static final String HINT_CALLER_NEEDED = "caller_needed";
    public static final String HINT_ERROR = "error";
    public static final String HINT_HORIZONTAL = "horizontal";
    public static final String HINT_KEYWORDS = "keywords";
    public static final String HINT_LARGE = "large";
    public static final String HINT_LAST_UPDATED = "last_updated";
    public static final String HINT_LIST = "list";
    public static final String HINT_LIST_ITEM = "list_item";
    public static final String HINT_NO_TINT = "no_tint";
    public static final String HINT_PARTIAL = "partial";
    public static final String HINT_PERMISSION_REQUEST = "permission_request";
    public static final String HINT_SEE_MORE = "see_more";
    public static final String HINT_SELECTED = "selected";
    public static final String HINT_SHORTCUT = "shortcut";
    public static final String HINT_SUMMARY = "summary";
    public static final String HINT_TITLE = "title";
    public static final String HINT_TOGGLE = "toggle";
    public static final String HINT_TTL = "ttl";
    public static final String SUBTYPE_COLOR = "color";
    public static final String SUBTYPE_CONTENT_DESCRIPTION = "content_description";
    public static final String SUBTYPE_LAYOUT_DIRECTION = "layout_direction";
    public static final String SUBTYPE_MAX = "max";
    public static final String SUBTYPE_MESSAGE = "message";
    public static final String SUBTYPE_MILLIS = "millis";
    public static final String SUBTYPE_PRIORITY = "priority";
    public static final String SUBTYPE_RANGE = "range";
    @Deprecated
    public static final String SUBTYPE_SLIDER = "slider";
    public static final String SUBTYPE_SOURCE = "source";
    public static final String SUBTYPE_TOGGLE = "toggle";
    public static final String SUBTYPE_VALUE = "value";
    private final String[] mHints;
    private final SliceItem[] mItems;
    private SliceSpec mSpec;
    private Uri mUri;

    protected Slice(Parcel parcel) {
        this.mHints = parcel.readStringArray();
        int n = parcel.readInt();
        this.mItems = new SliceItem[n];
        for (int i = 0; i < n; ++i) {
            this.mItems[i] = SliceItem.CREATOR.createFromParcel(parcel);
        }
        this.mUri = Uri.CREATOR.createFromParcel(parcel);
        this.mSpec = parcel.readTypedObject(SliceSpec.CREATOR);
    }

    Slice(ArrayList<SliceItem> arrayList, String[] arrstring, Uri uri, SliceSpec sliceSpec) {
        this.mHints = arrstring;
        this.mItems = arrayList.toArray(new SliceItem[arrayList.size()]);
        this.mUri = uri;
        this.mSpec = sliceSpec;
    }

    private String toString(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.mItems.length; ++i) {
            stringBuilder.append(string2);
            if (Objects.equals(this.mItems[i].getFormat(), "slice")) {
                stringBuilder.append("slice:\n");
                Slice slice = this.mItems[i].getSlice();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(string2);
                stringBuilder2.append("   ");
                stringBuilder.append(slice.toString(stringBuilder2.toString()));
                continue;
            }
            if (Objects.equals(this.mItems[i].getFormat(), "text")) {
                stringBuilder.append("text: ");
                stringBuilder.append(this.mItems[i].getText());
                stringBuilder.append("\n");
                continue;
            }
            stringBuilder.append(this.mItems[i].getFormat());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getHints() {
        return Arrays.asList(this.mHints);
    }

    public List<SliceItem> getItems() {
        return Arrays.asList(this.mItems);
    }

    public SliceSpec getSpec() {
        return this.mSpec;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean hasHint(String string2) {
        return ArrayUtils.contains(this.mHints, string2);
    }

    public boolean isCallerNeeded() {
        return this.hasHint(HINT_CALLER_NEEDED);
    }

    public String toString() {
        return this.toString("");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        SliceItem[] arrsliceItem;
        parcel.writeStringArray(this.mHints);
        parcel.writeInt(this.mItems.length);
        for (int i = 0; i < (arrsliceItem = this.mItems).length; ++i) {
            arrsliceItem[i].writeToParcel(parcel, n);
        }
        this.mUri.writeToParcel(parcel, 0);
        parcel.writeTypedObject(this.mSpec, n);
    }

    public static class Builder {
        private ArrayList<String> mHints = new ArrayList();
        private ArrayList<SliceItem> mItems = new ArrayList();
        private SliceSpec mSpec;
        private final Uri mUri;

        public Builder(Builder builder) {
            this.mUri = builder.mUri.buildUpon().appendPath("_gen").appendPath(String.valueOf(this.mItems.size())).build();
        }

        @Deprecated
        public Builder(Uri uri) {
            this.mUri = uri;
        }

        public Builder(Uri uri, SliceSpec sliceSpec) {
            this.mUri = uri;
            this.mSpec = sliceSpec;
        }

        public Builder addAction(PendingIntent pendingIntent, Slice slice, String string2) {
            Preconditions.checkNotNull(pendingIntent);
            Preconditions.checkNotNull(slice);
            List<String> list = slice.getHints();
            slice.mSpec = null;
            this.mItems.add(new SliceItem(pendingIntent, slice, "action", string2, list.toArray(new String[list.size()])));
            return this;
        }

        public Builder addBundle(Bundle bundle, String string2, List<String> list) {
            Preconditions.checkNotNull(bundle);
            this.mItems.add(new SliceItem((Object)bundle, "bundle", string2, list));
            return this;
        }

        public Builder addHints(List<String> list) {
            this.mHints.addAll(list);
            return this;
        }

        public Builder addIcon(Icon icon, String string2, List<String> list) {
            Preconditions.checkNotNull(icon);
            this.mItems.add(new SliceItem((Object)icon, "image", string2, list));
            return this;
        }

        public Builder addInt(int n, String string2, List<String> list) {
            this.mItems.add(new SliceItem((Object)n, "int", string2, list));
            return this;
        }

        public Builder addLong(long l, String string2, List<String> list) {
            this.mItems.add(new SliceItem((Object)l, "long", string2, list.toArray(new String[list.size()])));
            return this;
        }

        public Builder addRemoteInput(RemoteInput remoteInput, String string2, List<String> list) {
            Preconditions.checkNotNull(remoteInput);
            this.mItems.add(new SliceItem((Object)remoteInput, "input", string2, list));
            return this;
        }

        public Builder addSubSlice(Slice slice, String string2) {
            Preconditions.checkNotNull(slice);
            this.mItems.add(new SliceItem((Object)slice, "slice", string2, slice.getHints().toArray(new String[slice.getHints().size()])));
            return this;
        }

        public Builder addText(CharSequence charSequence, String string2, List<String> list) {
            this.mItems.add(new SliceItem((Object)charSequence, "text", string2, list));
            return this;
        }

        @Deprecated
        public Builder addTimestamp(long l, String string2, List<String> list) {
            return this.addLong(l, string2, list);
        }

        public Slice build() {
            ArrayList<SliceItem> arrayList = this.mItems;
            ArrayList<String> arrayList2 = this.mHints;
            return new Slice(arrayList, arrayList2.toArray(new String[arrayList2.size()]), this.mUri, this.mSpec);
        }

        public Builder setCallerNeeded(boolean bl) {
            if (bl) {
                this.mHints.add(Slice.HINT_CALLER_NEEDED);
            } else {
                this.mHints.remove(Slice.HINT_CALLER_NEEDED);
            }
            return this;
        }

        public Builder setSpec(SliceSpec sliceSpec) {
            this.mSpec = sliceSpec;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SliceHint {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SliceSubtype {
    }

}

