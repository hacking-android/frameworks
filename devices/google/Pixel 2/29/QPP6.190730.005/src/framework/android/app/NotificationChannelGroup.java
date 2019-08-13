/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlSerializer
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.NotificationChannel;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

public final class NotificationChannelGroup
implements Parcelable {
    private static final String ATT_BLOCKED = "blocked";
    private static final String ATT_DESC = "desc";
    private static final String ATT_ID = "id";
    private static final String ATT_NAME = "name";
    private static final String ATT_USER_LOCKED = "locked";
    public static final Parcelable.Creator<NotificationChannelGroup> CREATOR = new Parcelable.Creator<NotificationChannelGroup>(){

        @Override
        public NotificationChannelGroup createFromParcel(Parcel parcel) {
            return new NotificationChannelGroup(parcel);
        }

        public NotificationChannelGroup[] newArray(int n) {
            return new NotificationChannelGroup[n];
        }
    };
    private static final int MAX_TEXT_LENGTH = 1000;
    private static final String TAG_GROUP = "channelGroup";
    public static final int USER_LOCKED_BLOCKED_STATE = 1;
    private boolean mBlocked;
    private List<NotificationChannel> mChannels = new ArrayList<NotificationChannel>();
    private String mDescription;
    @UnsupportedAppUsage
    private final String mId;
    private CharSequence mName;
    private int mUserLockedFields;

    protected NotificationChannelGroup(Parcel parcel) {
        this.mId = parcel.readByte() != 0 ? parcel.readString() : null;
        this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = parcel.readByte() != 0 ? parcel.readString() : null;
        parcel.readParcelableList(this.mChannels, NotificationChannel.class.getClassLoader());
        this.mBlocked = parcel.readBoolean();
        this.mUserLockedFields = parcel.readInt();
    }

    public NotificationChannelGroup(String string2, CharSequence charSequence) {
        this.mId = this.getTrimmedString(string2);
        string2 = charSequence != null ? this.getTrimmedString(charSequence.toString()) : null;
        this.mName = string2;
    }

    private String getTrimmedString(String string2) {
        if (string2 != null && string2.length() > 1000) {
            return string2.substring(0, 1000);
        }
        return string2;
    }

    private static boolean safeBool(XmlPullParser object, String string2, boolean bl) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return bl;
        }
        return Boolean.parseBoolean((String)object);
    }

    public void addChannel(NotificationChannel notificationChannel) {
        this.mChannels.add(notificationChannel);
    }

    public NotificationChannelGroup clone() {
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(this.getId(), this.getName());
        notificationChannelGroup.setDescription(this.getDescription());
        notificationChannelGroup.setBlocked(this.isBlocked());
        notificationChannelGroup.setChannels(this.getChannels());
        notificationChannelGroup.lockFields(this.mUserLockedFields);
        return notificationChannelGroup;
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (NotificationChannelGroup)object;
            if (!(this.isBlocked() == ((NotificationChannelGroup)object).isBlocked() && this.mUserLockedFields == ((NotificationChannelGroup)object).mUserLockedFields && Objects.equals(this.getId(), ((NotificationChannelGroup)object).getId()) && Objects.equals(this.getName(), ((NotificationChannelGroup)object).getName()) && Objects.equals(this.getDescription(), ((NotificationChannelGroup)object).getDescription()) && Objects.equals(this.getChannels(), ((NotificationChannelGroup)object).getChannels()))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public List<NotificationChannel> getChannels() {
        return this.mChannels;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getDescription(), this.isBlocked(), this.getChannels(), this.mUserLockedFields);
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    public void lockFields(int n) {
        this.mUserLockedFields |= n;
    }

    public void populateFromXml(XmlPullParser xmlPullParser) {
        this.setDescription(xmlPullParser.getAttributeValue(null, ATT_DESC));
        this.setBlocked(NotificationChannelGroup.safeBool(xmlPullParser, ATT_BLOCKED, false));
    }

    public void setBlocked(boolean bl) {
        this.mBlocked = bl;
    }

    public void setChannels(List<NotificationChannel> list) {
        this.mChannels = list;
    }

    public void setDescription(String string2) {
        this.mDescription = this.getTrimmedString(string2);
    }

    @SystemApi
    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ATT_ID, (Object)this.getId());
        jSONObject.put(ATT_NAME, (Object)this.getName());
        jSONObject.put(ATT_DESC, (Object)this.getDescription());
        jSONObject.put(ATT_BLOCKED, this.isBlocked());
        jSONObject.put(ATT_USER_LOCKED, this.mUserLockedFields);
        return jSONObject;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NotificationChannelGroup{mId='");
        stringBuilder.append(this.mId);
        stringBuilder.append('\'');
        stringBuilder.append(", mName=");
        stringBuilder.append((Object)this.mName);
        stringBuilder.append(", mDescription=");
        String string2 = !TextUtils.isEmpty(this.mDescription) ? "hasDescription " : "";
        stringBuilder.append(string2);
        stringBuilder.append(", mBlocked=");
        stringBuilder.append(this.mBlocked);
        stringBuilder.append(", mChannels=");
        stringBuilder.append(this.mChannels);
        stringBuilder.append(", mUserLockedFields=");
        stringBuilder.append(this.mUserLockedFields);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void unlockFields(int n) {
        this.mUserLockedFields &= n;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mId != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte)0);
        }
        TextUtils.writeToParcel(this.mName, parcel, n);
        if (this.mDescription != null) {
            parcel.writeByte((byte)1);
            parcel.writeString(this.mDescription);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeParcelableList(this.mChannels, n);
        parcel.writeBoolean(this.mBlocked);
        parcel.writeInt(this.mUserLockedFields);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName.toString());
        protoOutputStream.write(1138166333443L, this.mDescription);
        protoOutputStream.write(1133871366148L, this.mBlocked);
        Iterator<NotificationChannel> iterator = this.mChannels.iterator();
        while (iterator.hasNext()) {
            iterator.next().writeToProto(protoOutputStream, 2246267895813L);
        }
        protoOutputStream.end(l);
    }

    public void writeXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, TAG_GROUP);
        xmlSerializer.attribute(null, ATT_ID, this.getId());
        if (this.getName() != null) {
            xmlSerializer.attribute(null, ATT_NAME, this.getName().toString());
        }
        if (this.getDescription() != null) {
            xmlSerializer.attribute(null, ATT_DESC, this.getDescription().toString());
        }
        xmlSerializer.attribute(null, ATT_BLOCKED, Boolean.toString(this.isBlocked()));
        xmlSerializer.attribute(null, ATT_USER_LOCKED, Integer.toString(this.mUserLockedFields));
        xmlSerializer.endTag(null, TAG_GROUP);
    }

}

