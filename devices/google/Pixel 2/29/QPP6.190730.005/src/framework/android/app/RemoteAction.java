/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.io.PrintWriter;

public final class RemoteAction
implements Parcelable {
    public static final Parcelable.Creator<RemoteAction> CREATOR = new Parcelable.Creator<RemoteAction>(){

        @Override
        public RemoteAction createFromParcel(Parcel parcel) {
            return new RemoteAction(parcel);
        }

        public RemoteAction[] newArray(int n) {
            return new RemoteAction[n];
        }
    };
    private static final String TAG = "RemoteAction";
    private final PendingIntent mActionIntent;
    private final CharSequence mContentDescription;
    private boolean mEnabled;
    private final Icon mIcon;
    private boolean mShouldShowIcon;
    private final CharSequence mTitle;

    public RemoteAction(Icon icon, CharSequence charSequence, CharSequence charSequence2, PendingIntent pendingIntent) {
        if (icon != null && charSequence != null && charSequence2 != null && pendingIntent != null) {
            this.mIcon = icon;
            this.mTitle = charSequence;
            this.mContentDescription = charSequence2;
            this.mActionIntent = pendingIntent;
            this.mEnabled = true;
            this.mShouldShowIcon = true;
            return;
        }
        throw new IllegalArgumentException("Expected icon, title, content description and action callback");
    }

    RemoteAction(Parcel parcel) {
        this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mActionIntent = PendingIntent.CREATOR.createFromParcel(parcel);
        this.mEnabled = parcel.readBoolean();
        this.mShouldShowIcon = parcel.readBoolean();
    }

    public RemoteAction clone() {
        RemoteAction remoteAction = new RemoteAction(this.mIcon, this.mTitle, this.mContentDescription, this.mActionIntent);
        remoteAction.setEnabled(this.mEnabled);
        remoteAction.setShouldShowIcon(this.mShouldShowIcon);
        return remoteAction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(String charSequence, PrintWriter printWriter) {
        printWriter.print((String)charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("title=");
        ((StringBuilder)charSequence).append((Object)this.mTitle);
        printWriter.print(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" enabled=");
        ((StringBuilder)charSequence).append(this.mEnabled);
        printWriter.print(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" contentDescription=");
        ((StringBuilder)charSequence).append((Object)this.mContentDescription);
        printWriter.print(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" icon=");
        ((StringBuilder)charSequence).append(this.mIcon);
        printWriter.print(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" action=");
        ((StringBuilder)charSequence).append(this.mActionIntent.getIntent());
        printWriter.print(((StringBuilder)charSequence).toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(" shouldShowIcon=");
        ((StringBuilder)charSequence).append(this.mShouldShowIcon);
        printWriter.print(((StringBuilder)charSequence).toString());
        printWriter.println();
    }

    public PendingIntent getActionIntent() {
        return this.mActionIntent;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean bl) {
        this.mEnabled = bl;
    }

    public void setShouldShowIcon(boolean bl) {
        this.mShouldShowIcon = bl;
    }

    public boolean shouldShowIcon() {
        return this.mShouldShowIcon;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mIcon.writeToParcel(parcel, 0);
        TextUtils.writeToParcel(this.mTitle, parcel, n);
        TextUtils.writeToParcel(this.mContentDescription, parcel, n);
        this.mActionIntent.writeToParcel(parcel, n);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeBoolean(this.mShouldShowIcon);
    }

}

