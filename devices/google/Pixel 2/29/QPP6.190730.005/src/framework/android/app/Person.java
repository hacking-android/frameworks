/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class Person
implements Parcelable {
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>(){

        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        public Person[] newArray(int n) {
            return new Person[n];
        }
    };
    private Icon mIcon;
    private boolean mIsBot;
    private boolean mIsImportant;
    private String mKey;
    private CharSequence mName;
    private String mUri;

    private Person(Builder builder) {
        this.mName = builder.mName;
        this.mIcon = builder.mIcon;
        this.mUri = builder.mUri;
        this.mKey = builder.mKey;
        this.mIsBot = builder.mIsBot;
        this.mIsImportant = builder.mIsImportant;
    }

    private Person(Parcel parcel) {
        this.mName = parcel.readCharSequence();
        if (parcel.readInt() != 0) {
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        }
        this.mUri = parcel.readString();
        this.mKey = parcel.readString();
        this.mIsImportant = parcel.readBoolean();
        this.mIsBot = parcel.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Person;
        boolean bl2 = false;
        if (bl) {
            Icon icon;
            Icon icon2;
            object = (Person)object;
            if (Objects.equals(this.mName, ((Person)object).mName) && ((icon = this.mIcon) == null ? ((Person)object).mIcon == null : (icon2 = ((Person)object).mIcon) != null && icon.sameAs(icon2)) && Objects.equals(this.mUri, ((Person)object).mUri) && Objects.equals(this.mKey, ((Person)object).mKey) && this.mIsBot == ((Person)object).mIsBot && this.mIsImportant == ((Person)object).mIsImportant) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public String getKey() {
        return this.mKey;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getUri() {
        return this.mUri;
    }

    public int hashCode() {
        return Objects.hash(this.mName, this.mIcon, this.mUri, this.mKey, this.mIsBot, this.mIsImportant);
    }

    public boolean isBot() {
        return this.mIsBot;
    }

    public boolean isImportant() {
        return this.mIsImportant;
    }

    public String resolveToLegacyUri() {
        CharSequence charSequence = this.mUri;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.mName != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("name:");
            ((StringBuilder)charSequence).append((Object)this.mName);
            return ((StringBuilder)charSequence).toString();
        }
        return "";
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mName);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mUri);
        parcel.writeString(this.mKey);
        parcel.writeBoolean(this.mIsImportant);
        parcel.writeBoolean(this.mIsBot);
    }

    public static class Builder {
        private Icon mIcon;
        private boolean mIsBot;
        private boolean mIsImportant;
        private String mKey;
        private CharSequence mName;
        private String mUri;

        public Builder() {
        }

        private Builder(Person person) {
            this.mName = person.mName;
            this.mIcon = person.mIcon;
            this.mUri = person.mUri;
            this.mKey = person.mKey;
            this.mIsBot = person.mIsBot;
            this.mIsImportant = person.mIsImportant;
        }

        public Person build() {
            return new Person(this);
        }

        public Builder setBot(boolean bl) {
            this.mIsBot = bl;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setImportant(boolean bl) {
            this.mIsImportant = bl;
            return this;
        }

        public Builder setKey(String string2) {
            this.mKey = string2;
            return this;
        }

        public Builder setName(CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        public Builder setUri(String string2) {
            this.mUri = string2;
            return this;
        }
    }

}

