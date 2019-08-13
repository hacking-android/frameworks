/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.KeyEvent;
import com.android.internal.util.Preconditions;

public final class KeyboardShortcutInfo
implements Parcelable {
    public static final Parcelable.Creator<KeyboardShortcutInfo> CREATOR = new Parcelable.Creator<KeyboardShortcutInfo>(){

        @Override
        public KeyboardShortcutInfo createFromParcel(Parcel parcel) {
            return new KeyboardShortcutInfo(parcel);
        }

        public KeyboardShortcutInfo[] newArray(int n) {
            return new KeyboardShortcutInfo[n];
        }
    };
    private final char mBaseCharacter;
    private final Icon mIcon;
    private final int mKeycode;
    private final CharSequence mLabel;
    private final int mModifiers;

    private KeyboardShortcutInfo(Parcel parcel) {
        this.mLabel = parcel.readCharSequence();
        this.mIcon = (Icon)parcel.readParcelable(null);
        this.mBaseCharacter = (char)parcel.readInt();
        this.mKeycode = parcel.readInt();
        this.mModifiers = parcel.readInt();
    }

    public KeyboardShortcutInfo(CharSequence charSequence, char c, int n) {
        this.mLabel = charSequence;
        boolean bl = c != '\u0000';
        Preconditions.checkArgument(bl);
        this.mBaseCharacter = c;
        this.mKeycode = 0;
        this.mModifiers = n;
        this.mIcon = null;
    }

    public KeyboardShortcutInfo(CharSequence charSequence, int n, int n2) {
        this(charSequence, null, n, n2);
    }

    public KeyboardShortcutInfo(CharSequence charSequence, Icon icon, int n, int n2) {
        this.mLabel = charSequence;
        this.mIcon = icon;
        boolean bl = false;
        this.mBaseCharacter = (char)(false ? 1 : 0);
        boolean bl2 = bl;
        if (n >= 0) {
            bl2 = bl;
            if (n <= KeyEvent.getMaxKeyCode()) {
                bl2 = true;
            }
        }
        Preconditions.checkArgument(bl2);
        this.mKeycode = n;
        this.mModifiers = n2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public char getBaseCharacter() {
        return this.mBaseCharacter;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public int getKeycode() {
        return this.mKeycode;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public int getModifiers() {
        return this.mModifiers;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mLabel);
        parcel.writeParcelable(this.mIcon, 0);
        parcel.writeInt(this.mBaseCharacter);
        parcel.writeInt(this.mKeycode);
        parcel.writeInt(this.mModifiers);
    }

}

