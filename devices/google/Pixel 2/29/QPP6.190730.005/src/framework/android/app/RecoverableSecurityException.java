/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.app._$$Lambda$RecoverableSecurityException$LocalDialog$r8YNkpjWIZllJsQ_8eA0q51FU5Q;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class RecoverableSecurityException
extends SecurityException
implements Parcelable {
    public static final Parcelable.Creator<RecoverableSecurityException> CREATOR = new Parcelable.Creator<RecoverableSecurityException>(){

        @Override
        public RecoverableSecurityException createFromParcel(Parcel parcel) {
            return new RecoverableSecurityException(parcel);
        }

        public RecoverableSecurityException[] newArray(int n) {
            return new RecoverableSecurityException[n];
        }
    };
    private static final String TAG = "RecoverableSecurityException";
    private final RemoteAction mUserAction;
    private final CharSequence mUserMessage;

    public RecoverableSecurityException(Parcel parcel) {
        this(new SecurityException(parcel.readString()), parcel.readCharSequence(), RemoteAction.CREATOR.createFromParcel(parcel));
    }

    public RecoverableSecurityException(Throwable throwable, CharSequence charSequence, RemoteAction remoteAction) {
        super(throwable.getMessage());
        this.mUserMessage = Objects.requireNonNull(charSequence);
        this.mUserAction = Objects.requireNonNull(remoteAction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public RemoteAction getUserAction() {
        return this.mUserAction;
    }

    public CharSequence getUserMessage() {
        return this.mUserMessage;
    }

    public void showAsDialog(Activity object) {
        LocalDialog localDialog = new LocalDialog();
        Object object2 = new Bundle();
        ((Bundle)object2).putParcelable(TAG, this);
        localDialog.setArguments((Bundle)object2);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("RecoverableSecurityException_");
        ((StringBuilder)object2).append(this.mUserAction.getActionIntent().getCreatorUid());
        object2 = ((StringBuilder)object2).toString();
        Object object3 = ((Activity)object).getFragmentManager();
        object = ((FragmentManager)object3).beginTransaction();
        object3 = ((FragmentManager)object3).findFragmentByTag((String)object2);
        if (object3 != null) {
            ((FragmentTransaction)object).remove((Fragment)object3);
        }
        ((FragmentTransaction)object).add(localDialog, (String)object2);
        ((FragmentTransaction)object).commitAllowingStateLoss();
    }

    public void showAsNotification(Context object, String string2) {
        NotificationManager notificationManager = ((Context)object).getSystemService(NotificationManager.class);
        object = new Notification.Builder((Context)object, string2).setSmallIcon(17302775).setContentTitle(this.mUserAction.getTitle()).setContentText(this.mUserMessage).setContentIntent(this.mUserAction.getActionIntent()).setCategory("err");
        notificationManager.notify(TAG, this.mUserAction.getActionIntent().getCreatorUid(), ((Notification.Builder)object).build());
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.getMessage());
        parcel.writeCharSequence(this.mUserMessage);
        this.mUserAction.writeToParcel(parcel, n);
    }

    public static class LocalDialog
    extends DialogFragment {
        static /* synthetic */ void lambda$onCreateDialog$0(RecoverableSecurityException recoverableSecurityException, DialogInterface dialogInterface, int n) {
            try {
                recoverableSecurityException.mUserAction.getActionIntent().send();
            }
            catch (PendingIntent.CanceledException canceledException) {
                // empty catch block
            }
        }

        @Override
        public Dialog onCreateDialog(Bundle parcelable) {
            parcelable = (RecoverableSecurityException)this.getArguments().getParcelable(RecoverableSecurityException.TAG);
            return new AlertDialog.Builder(this.getActivity()).setMessage(((RecoverableSecurityException)parcelable).mUserMessage).setPositiveButton(((RecoverableSecurityException)parcelable).mUserAction.getTitle(), (DialogInterface.OnClickListener)new _$$Lambda$RecoverableSecurityException$LocalDialog$r8YNkpjWIZllJsQ_8eA0q51FU5Q((RecoverableSecurityException)parcelable)).setNegativeButton(17039360, null).create();
        }
    }

}

