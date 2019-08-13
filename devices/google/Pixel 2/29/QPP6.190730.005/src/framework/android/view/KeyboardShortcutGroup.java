/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.KeyboardShortcutInfo;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class KeyboardShortcutGroup
implements Parcelable {
    public static final Parcelable.Creator<KeyboardShortcutGroup> CREATOR = new Parcelable.Creator<KeyboardShortcutGroup>(){

        @Override
        public KeyboardShortcutGroup createFromParcel(Parcel parcel) {
            return new KeyboardShortcutGroup(parcel);
        }

        public KeyboardShortcutGroup[] newArray(int n) {
            return new KeyboardShortcutGroup[n];
        }
    };
    private final List<KeyboardShortcutInfo> mItems;
    private final CharSequence mLabel;
    private boolean mSystemGroup;

    private KeyboardShortcutGroup(Parcel parcel) {
        this.mItems = new ArrayList<KeyboardShortcutInfo>();
        this.mLabel = parcel.readCharSequence();
        parcel.readTypedList(this.mItems, KeyboardShortcutInfo.CREATOR);
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mSystemGroup = bl;
    }

    public KeyboardShortcutGroup(CharSequence charSequence) {
        this(charSequence, Collections.emptyList());
    }

    public KeyboardShortcutGroup(CharSequence charSequence, List<KeyboardShortcutInfo> list) {
        this.mLabel = charSequence;
        this.mItems = new ArrayList<KeyboardShortcutInfo>((Collection)Preconditions.checkNotNull(list));
    }

    public KeyboardShortcutGroup(CharSequence charSequence, List<KeyboardShortcutInfo> list, boolean bl) {
        this.mLabel = charSequence;
        this.mItems = new ArrayList<KeyboardShortcutInfo>((Collection)Preconditions.checkNotNull(list));
        this.mSystemGroup = bl;
    }

    public KeyboardShortcutGroup(CharSequence charSequence, boolean bl) {
        this(charSequence, Collections.emptyList(), bl);
    }

    public void addItem(KeyboardShortcutInfo keyboardShortcutInfo) {
        this.mItems.add(keyboardShortcutInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<KeyboardShortcutInfo> getItems() {
        return this.mItems;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public boolean isSystemGroup() {
        return this.mSystemGroup;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeTypedList(this.mItems);
        parcel.writeInt((int)this.mSystemGroup);
    }

}

