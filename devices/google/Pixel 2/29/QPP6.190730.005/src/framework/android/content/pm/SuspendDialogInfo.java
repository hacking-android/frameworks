/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.content.res.ResourceId;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

@SystemApi
public final class SuspendDialogInfo
implements Parcelable {
    public static final Parcelable.Creator<SuspendDialogInfo> CREATOR;
    private static final String TAG;
    private static final String XML_ATTR_BUTTON_TEXT_RES_ID = "buttonTextResId";
    private static final String XML_ATTR_DIALOG_MESSAGE = "dialogMessage";
    private static final String XML_ATTR_DIALOG_MESSAGE_RES_ID = "dialogMessageResId";
    private static final String XML_ATTR_ICON_RES_ID = "iconResId";
    private static final String XML_ATTR_TITLE_RES_ID = "titleResId";
    private final String mDialogMessage;
    private final int mDialogMessageResId;
    private final int mIconResId;
    private final int mNeutralButtonTextResId;
    private final int mTitleResId;

    static {
        TAG = SuspendDialogInfo.class.getSimpleName();
        CREATOR = new Parcelable.Creator<SuspendDialogInfo>(){

            @Override
            public SuspendDialogInfo createFromParcel(Parcel parcel) {
                return new SuspendDialogInfo(parcel);
            }

            public SuspendDialogInfo[] newArray(int n) {
                return new SuspendDialogInfo[n];
            }
        };
    }

    SuspendDialogInfo(Builder builder) {
        this.mIconResId = builder.mIconResId;
        this.mTitleResId = builder.mTitleResId;
        this.mDialogMessageResId = builder.mDialogMessageResId;
        String string2 = this.mDialogMessageResId == 0 ? builder.mDialogMessage : null;
        this.mDialogMessage = string2;
        this.mNeutralButtonTextResId = builder.mNeutralButtonTextResId;
    }

    private SuspendDialogInfo(Parcel parcel) {
        this.mIconResId = parcel.readInt();
        this.mTitleResId = parcel.readInt();
        this.mDialogMessageResId = parcel.readInt();
        this.mDialogMessage = parcel.readString();
        this.mNeutralButtonTextResId = parcel.readInt();
    }

    public static SuspendDialogInfo restoreFromXml(XmlPullParser object) {
        Builder builder;
        block11 : {
            int n;
            int n2;
            int n3;
            block10 : {
                builder = new Builder();
                int n4 = XmlUtils.readIntAttribute(object, XML_ATTR_ICON_RES_ID, 0);
                n3 = XmlUtils.readIntAttribute(object, XML_ATTR_TITLE_RES_ID, 0);
                n2 = XmlUtils.readIntAttribute(object, XML_ATTR_BUTTON_TEXT_RES_ID, 0);
                n = XmlUtils.readIntAttribute(object, XML_ATTR_DIALOG_MESSAGE_RES_ID, 0);
                object = XmlUtils.readStringAttribute(object, XML_ATTR_DIALOG_MESSAGE);
                if (n4 == 0) break block10;
                builder.setIcon(n4);
            }
            if (n3 != 0) {
                builder.setTitle(n3);
            }
            if (n2 != 0) {
                builder.setNeutralButtonText(n2);
            }
            if (n != 0) {
                builder.setMessage(n);
            }
            if (object == null) break block11;
            try {
                builder.setMessage((String)object);
            }
            catch (Exception exception) {
                Slog.e(TAG, "Exception while parsing from xml. Some fields may default", exception);
            }
        }
        return builder.build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof SuspendDialogInfo)) {
            return false;
        }
        object = (SuspendDialogInfo)object;
        if (this.mIconResId != ((SuspendDialogInfo)object).mIconResId || this.mTitleResId != ((SuspendDialogInfo)object).mTitleResId || this.mDialogMessageResId != ((SuspendDialogInfo)object).mDialogMessageResId || this.mNeutralButtonTextResId != ((SuspendDialogInfo)object).mNeutralButtonTextResId || !Objects.equals(this.mDialogMessage, ((SuspendDialogInfo)object).mDialogMessage)) {
            bl = false;
        }
        return bl;
    }

    public String getDialogMessage() {
        return this.mDialogMessage;
    }

    public int getDialogMessageResId() {
        return this.mDialogMessageResId;
    }

    public int getIconResId() {
        return this.mIconResId;
    }

    public int getNeutralButtonTextResId() {
        return this.mNeutralButtonTextResId;
    }

    public int getTitleResId() {
        return this.mTitleResId;
    }

    public int hashCode() {
        return (((this.mIconResId * 31 + this.mTitleResId) * 31 + this.mNeutralButtonTextResId) * 31 + this.mDialogMessageResId) * 31 + Objects.hashCode(this.mDialogMessage);
    }

    public void saveToXml(XmlSerializer xmlSerializer) throws IOException {
        int n = this.mIconResId;
        if (n != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_ICON_RES_ID, n);
        }
        if ((n = this.mTitleResId) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_TITLE_RES_ID, n);
        }
        if ((n = this.mDialogMessageResId) != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_DIALOG_MESSAGE_RES_ID, n);
        } else {
            XmlUtils.writeStringAttribute(xmlSerializer, XML_ATTR_DIALOG_MESSAGE, this.mDialogMessage);
        }
        n = this.mNeutralButtonTextResId;
        if (n != 0) {
            XmlUtils.writeIntAttribute(xmlSerializer, XML_ATTR_BUTTON_TEXT_RES_ID, n);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("SuspendDialogInfo: {");
        if (this.mIconResId != 0) {
            stringBuilder.append("mIconId = 0x");
            stringBuilder.append(Integer.toHexString(this.mIconResId));
            stringBuilder.append(" ");
        }
        if (this.mTitleResId != 0) {
            stringBuilder.append("mTitleResId = 0x");
            stringBuilder.append(Integer.toHexString(this.mTitleResId));
            stringBuilder.append(" ");
        }
        if (this.mNeutralButtonTextResId != 0) {
            stringBuilder.append("mNeutralButtonTextResId = 0x");
            stringBuilder.append(Integer.toHexString(this.mNeutralButtonTextResId));
            stringBuilder.append(" ");
        }
        if (this.mDialogMessageResId != 0) {
            stringBuilder.append("mDialogMessageResId = 0x");
            stringBuilder.append(Integer.toHexString(this.mDialogMessageResId));
            stringBuilder.append(" ");
        } else if (this.mDialogMessage != null) {
            stringBuilder.append("mDialogMessage = \"");
            stringBuilder.append(this.mDialogMessage);
            stringBuilder.append("\" ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mIconResId);
        parcel.writeInt(this.mTitleResId);
        parcel.writeInt(this.mDialogMessageResId);
        parcel.writeString(this.mDialogMessage);
        parcel.writeInt(this.mNeutralButtonTextResId);
    }

    public static final class Builder {
        private String mDialogMessage;
        private int mDialogMessageResId = 0;
        private int mIconResId = 0;
        private int mNeutralButtonTextResId = 0;
        private int mTitleResId = 0;

        public SuspendDialogInfo build() {
            return new SuspendDialogInfo(this);
        }

        public Builder setIcon(int n) {
            Preconditions.checkArgument(ResourceId.isValid(n), "Invalid resource id provided");
            this.mIconResId = n;
            return this;
        }

        public Builder setMessage(int n) {
            Preconditions.checkArgument(ResourceId.isValid(n), "Invalid resource id provided");
            this.mDialogMessageResId = n;
            return this;
        }

        public Builder setMessage(String string2) {
            Preconditions.checkStringNotEmpty(string2, "Message cannot be null or empty");
            this.mDialogMessage = string2;
            return this;
        }

        public Builder setNeutralButtonText(int n) {
            Preconditions.checkArgument(ResourceId.isValid(n), "Invalid resource id provided");
            this.mNeutralButtonTextResId = n;
            return this;
        }

        public Builder setTitle(int n) {
            Preconditions.checkArgument(ResourceId.isValid(n), "Invalid resource id provided");
            this.mTitleResId = n;
            return this;
        }
    }

}

