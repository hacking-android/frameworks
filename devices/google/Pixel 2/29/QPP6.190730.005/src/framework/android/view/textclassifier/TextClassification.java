/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.textclassifier.-$
 *  android.view.textclassifier.-$$Lambda
 *  android.view.textclassifier.-$$Lambda$L_UQMPjXwBN0ch4zL2dD82nf9RI
 *  com.google.android.textclassifier.AnnotatorModel
 *  com.google.android.textclassifier.AnnotatorModel$ClassificationResult
 */
package android.view.textclassifier;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannedString;
import android.util.ArrayMap;
import android.view.View;
import android.view.textclassifier.-$;
import android.view.textclassifier.EntityConfidence;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.Log;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier._$$Lambda$L_UQMPjXwBN0ch4zL2dD82nf9RI;
import android.view.textclassifier._$$Lambda$TextClassification$ysasaE5ZkXkkzjVWIJ06GTV92_g;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import com.google.android.textclassifier.AnnotatorModel;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class TextClassification
implements Parcelable {
    public static final Parcelable.Creator<TextClassification> CREATOR;
    public static final TextClassification EMPTY;
    private static final String LOG_TAG = "TextClassification";
    private static final int MAX_LEGACY_ICON_SIZE = 192;
    private final List<RemoteAction> mActions;
    private final EntityConfidence mEntityConfidence;
    private final Bundle mExtras;
    private final String mId;
    private final Drawable mLegacyIcon;
    private final Intent mLegacyIntent;
    private final String mLegacyLabel;
    private final View.OnClickListener mLegacyOnClickListener;
    private final String mText;

    static {
        EMPTY = new Builder().build();
        CREATOR = new Parcelable.Creator<TextClassification>(){

            @Override
            public TextClassification createFromParcel(Parcel parcel) {
                return new TextClassification(parcel);
            }

            public TextClassification[] newArray(int n) {
                return new TextClassification[n];
            }
        };
    }

    private TextClassification(Parcel parcel) {
        this.mText = parcel.readString();
        this.mActions = parcel.createTypedArrayList(RemoteAction.CREATOR);
        if (!this.mActions.isEmpty()) {
            RemoteAction remoteAction = this.mActions.get(0);
            this.mLegacyIcon = TextClassification.maybeLoadDrawable(remoteAction.getIcon());
            this.mLegacyLabel = remoteAction.getTitle().toString();
            this.mLegacyOnClickListener = TextClassification.createIntentOnClickListener(this.mActions.get(0).getActionIntent());
        } else {
            this.mLegacyIcon = null;
            this.mLegacyLabel = null;
            this.mLegacyOnClickListener = null;
        }
        this.mLegacyIntent = null;
        this.mEntityConfidence = EntityConfidence.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    private TextClassification(String string2, Drawable drawable2, String string3, Intent intent, View.OnClickListener onClickListener, List<RemoteAction> list, EntityConfidence entityConfidence, String string4, Bundle bundle) {
        this.mText = string2;
        this.mLegacyIcon = drawable2;
        this.mLegacyLabel = string3;
        this.mLegacyIntent = intent;
        this.mLegacyOnClickListener = onClickListener;
        this.mActions = Collections.unmodifiableList(list);
        this.mEntityConfidence = Preconditions.checkNotNull(entityConfidence);
        this.mId = string4;
        this.mExtras = bundle;
    }

    public static View.OnClickListener createIntentOnClickListener(PendingIntent pendingIntent) {
        Preconditions.checkNotNull(pendingIntent);
        return new _$$Lambda$TextClassification$ysasaE5ZkXkkzjVWIJ06GTV92_g(pendingIntent);
    }

    public static PendingIntent createPendingIntent(Context context, Intent intent, int n) {
        return PendingIntent.getActivity(context, n, intent, 134217728);
    }

    static /* synthetic */ void lambda$createIntentOnClickListener$0(PendingIntent pendingIntent, View view) {
        try {
            pendingIntent.send();
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.e(LOG_TAG, "Error sending PendingIntent", canceledException);
        }
    }

    private static Drawable maybeLoadDrawable(Icon icon) {
        if (icon == null) {
            return null;
        }
        int n = icon.getType();
        if (n != 1) {
            if (n != 3) {
                if (n != 5) {
                    return null;
                }
                return new AdaptiveIconDrawable(null, new BitmapDrawable(Resources.getSystem(), icon.getBitmap()));
            }
            return new BitmapDrawable(Resources.getSystem(), BitmapFactory.decodeByteArray(icon.getDataBytes(), icon.getDataOffset(), icon.getDataLength()));
        }
        return new BitmapDrawable(Resources.getSystem(), icon.getBitmap());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<RemoteAction> getActions() {
        return this.mActions;
    }

    public float getConfidenceScore(String string2) {
        return this.mEntityConfidence.getConfidenceScore(string2);
    }

    public String getEntity(int n) {
        return this.mEntityConfidence.getEntities().get(n);
    }

    public int getEntityCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    @Deprecated
    public Drawable getIcon() {
        return this.mLegacyIcon;
    }

    public String getId() {
        return this.mId;
    }

    @Deprecated
    public Intent getIntent() {
        return this.mLegacyIntent;
    }

    @Deprecated
    public CharSequence getLabel() {
        return this.mLegacyLabel;
    }

    public View.OnClickListener getOnClickListener() {
        return this.mLegacyOnClickListener;
    }

    public String getText() {
        return this.mText;
    }

    public String toString() {
        return String.format(Locale.US, "TextClassification {text=%s, entities=%s, actions=%s, id=%s, extras=%s}", this.mText, this.mEntityConfidence, this.mActions, this.mId, this.mExtras);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mText);
        parcel.writeTypedList(this.mActions);
        this.mEntityConfidence.writeToParcel(parcel, n);
        parcel.writeString(this.mId);
        parcel.writeBundle(this.mExtras);
    }

    public static final class Builder {
        private final ArrayList<Intent> mActionIntents = new ArrayList();
        private List<RemoteAction> mActions = new ArrayList<RemoteAction>();
        private final Map<String, AnnotatorModel.ClassificationResult> mClassificationResults = new ArrayMap<String, AnnotatorModel.ClassificationResult>();
        private Bundle mExtras;
        private Bundle mForeignLanguageExtra;
        private String mId;
        private Drawable mLegacyIcon;
        private Intent mLegacyIntent;
        private String mLegacyLabel;
        private View.OnClickListener mLegacyOnClickListener;
        private String mText;
        private final Map<String, Float> mTypeScoreMap = new ArrayMap<String, Float>();

        private Bundle buildExtras(EntityConfidence object) {
            Object object2 = this.mExtras;
            Bundle bundle = object2;
            if (object2 == null) {
                bundle = new Bundle();
            }
            if (this.mActionIntents.stream().anyMatch(_$$Lambda$L_UQMPjXwBN0ch4zL2dD82nf9RI.INSTANCE)) {
                ExtrasUtils.putActionsIntents(bundle, this.mActionIntents);
            }
            if ((object2 = this.mForeignLanguageExtra) != null) {
                ExtrasUtils.putForeignLanguageExtra(bundle, (Bundle)object2);
            }
            object2 = ((EntityConfidence)object).getEntities();
            object = new ArrayList();
            Iterator iterator = object2.iterator();
            while (iterator.hasNext()) {
                object2 = (String)iterator.next();
                ((ArrayList)object).add(this.mClassificationResults.get(object2));
            }
            ExtrasUtils.putEntities(bundle, ((ArrayList)object).toArray((T[])new AnnotatorModel.ClassificationResult[0]));
            object = bundle.isEmpty() ? Bundle.EMPTY : bundle;
            return object;
        }

        private Builder setEntityType(String string2, float f, AnnotatorModel.ClassificationResult classificationResult) {
            this.mTypeScoreMap.put(string2, Float.valueOf(f));
            this.mClassificationResults.put(string2, classificationResult);
            return this;
        }

        public Builder addAction(RemoteAction remoteAction) {
            return this.addAction(remoteAction, null);
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public Builder addAction(RemoteAction remoteAction, Intent intent) {
            boolean bl = remoteAction != null;
            Preconditions.checkArgument(bl);
            this.mActions.add(remoteAction);
            this.mActionIntents.add(intent);
            return this;
        }

        public TextClassification build() {
            EntityConfidence entityConfidence = new EntityConfidence(this.mTypeScoreMap);
            return new TextClassification(this.mText, this.mLegacyIcon, this.mLegacyLabel, this.mLegacyIntent, this.mLegacyOnClickListener, this.mActions, entityConfidence, this.mId, this.buildExtras(entityConfidence));
        }

        public Builder setEntityType(AnnotatorModel.ClassificationResult classificationResult) {
            this.setEntityType(classificationResult.getCollection(), classificationResult.getScore(), classificationResult);
            return this;
        }

        public Builder setEntityType(String string2, float f) {
            this.setEntityType(string2, f, null);
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public Builder setForeignLanguageExtra(Bundle bundle) {
            this.mForeignLanguageExtra = bundle;
            return this;
        }

        @Deprecated
        public Builder setIcon(Drawable drawable2) {
            this.mLegacyIcon = drawable2;
            return this;
        }

        public Builder setId(String string2) {
            this.mId = string2;
            return this;
        }

        @Deprecated
        public Builder setIntent(Intent intent) {
            this.mLegacyIntent = intent;
            return this;
        }

        @Deprecated
        public Builder setLabel(String string2) {
            this.mLegacyLabel = string2;
            return this;
        }

        @Deprecated
        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            this.mLegacyOnClickListener = onClickListener;
            return this;
        }

        public Builder setText(String string2) {
            this.mText = string2;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    private static @interface IntentType {
        public static final int ACTIVITY = 0;
        public static final int SERVICE = 1;
        public static final int UNSUPPORTED = -1;
    }

    public static final class Request
    implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>(){

            @Override
            public Request createFromParcel(Parcel parcel) {
                return Request.readFromParcel(parcel);
            }

            public Request[] newArray(int n) {
                return new Request[n];
            }
        };
        private String mCallingPackageName;
        private final LocaleList mDefaultLocales;
        private final int mEndIndex;
        private final Bundle mExtras;
        private final ZonedDateTime mReferenceTime;
        private final int mStartIndex;
        private final CharSequence mText;

        private Request(CharSequence charSequence, int n, int n2, LocaleList localeList, ZonedDateTime zonedDateTime, Bundle bundle) {
            this.mText = charSequence;
            this.mStartIndex = n;
            this.mEndIndex = n2;
            this.mDefaultLocales = localeList;
            this.mReferenceTime = zonedDateTime;
            this.mExtras = bundle;
        }

        private static Request readFromParcel(Parcel object) {
            CharSequence charSequence = ((Parcel)object).readCharSequence();
            int n = ((Parcel)object).readInt();
            int n2 = ((Parcel)object).readInt();
            ZonedDateTime zonedDateTime = null;
            LocaleList localeList = (LocaleList)((Parcel)object).readParcelable(null);
            String string2 = ((Parcel)object).readString();
            if (string2 != null) {
                zonedDateTime = ZonedDateTime.parse(string2);
            }
            string2 = ((Parcel)object).readString();
            object = new Request(charSequence, n, n2, localeList, zonedDateTime, ((Parcel)object).readBundle());
            ((Request)object).setCallingPackageName(string2);
            return object;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getCallingPackageName() {
            return this.mCallingPackageName;
        }

        public LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public int getEndIndex() {
            return this.mEndIndex;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
        }

        public int getStartIndex() {
            return this.mStartIndex;
        }

        public CharSequence getText() {
            return this.mText;
        }

        @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
        public void setCallingPackageName(String string2) {
            this.mCallingPackageName = string2;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeCharSequence(this.mText);
            parcel.writeInt(this.mStartIndex);
            parcel.writeInt(this.mEndIndex);
            parcel.writeParcelable(this.mDefaultLocales, n);
            Object object = this.mReferenceTime;
            object = object == null ? null : ((ZonedDateTime)object).toString();
            parcel.writeString((String)object);
            parcel.writeString(this.mCallingPackageName);
            parcel.writeBundle(this.mExtras);
        }

        public static final class Builder {
            private LocaleList mDefaultLocales;
            private final int mEndIndex;
            private Bundle mExtras;
            private ZonedDateTime mReferenceTime;
            private final int mStartIndex;
            private final CharSequence mText;

            public Builder(CharSequence charSequence, int n, int n2) {
                TextClassifier.Utils.checkArgument(charSequence, n, n2);
                this.mText = charSequence;
                this.mStartIndex = n;
                this.mEndIndex = n2;
            }

            public Request build() {
                Bundle bundle;
                SpannedString spannedString = new SpannedString(this.mText);
                int n = this.mStartIndex;
                int n2 = this.mEndIndex;
                LocaleList localeList = this.mDefaultLocales;
                ZonedDateTime zonedDateTime = this.mReferenceTime;
                Bundle bundle2 = bundle = this.mExtras;
                if (bundle == null) {
                    bundle2 = Bundle.EMPTY;
                }
                return new Request(spannedString, n, n2, localeList, zonedDateTime, bundle2);
            }

            public Builder setDefaultLocales(LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public Builder setExtras(Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public Builder setReferenceTime(ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }
        }

    }

}

