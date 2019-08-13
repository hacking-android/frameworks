/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class SearchableInfo
implements Parcelable {
    public static final Parcelable.Creator<SearchableInfo> CREATOR = new Parcelable.Creator<SearchableInfo>(){

        @Override
        public SearchableInfo createFromParcel(Parcel parcel) {
            return new SearchableInfo(parcel);
        }

        public SearchableInfo[] newArray(int n) {
            return new SearchableInfo[n];
        }
    };
    private static final boolean DBG = false;
    private static final String LOG_TAG = "SearchableInfo";
    private static final String MD_LABEL_SEARCHABLE = "android.app.searchable";
    private static final String MD_XML_ELEMENT_SEARCHABLE = "searchable";
    private static final String MD_XML_ELEMENT_SEARCHABLE_ACTION_KEY = "actionkey";
    private static final int SEARCH_MODE_BADGE_ICON = 8;
    private static final int SEARCH_MODE_BADGE_LABEL = 4;
    private static final int SEARCH_MODE_QUERY_REWRITE_FROM_DATA = 16;
    private static final int SEARCH_MODE_QUERY_REWRITE_FROM_TEXT = 32;
    private static final int VOICE_SEARCH_LAUNCH_RECOGNIZER = 4;
    private static final int VOICE_SEARCH_LAUNCH_WEB_SEARCH = 2;
    private static final int VOICE_SEARCH_SHOW_BUTTON = 1;
    private HashMap<Integer, ActionKeyInfo> mActionKeys = null;
    private final boolean mAutoUrlDetect;
    private final int mHintId;
    private final int mIconId;
    private final boolean mIncludeInGlobalSearch;
    private final int mLabelId;
    private final boolean mQueryAfterZeroResults;
    private final ComponentName mSearchActivity;
    private final int mSearchButtonText;
    private final int mSearchImeOptions;
    private final int mSearchInputType;
    private final int mSearchMode;
    private final int mSettingsDescriptionId;
    private final String mSuggestAuthority;
    private final String mSuggestIntentAction;
    private final String mSuggestIntentData;
    private final String mSuggestPath;
    private final String mSuggestProviderPackage;
    private final String mSuggestSelection;
    private final int mSuggestThreshold;
    private final int mVoiceLanguageId;
    private final int mVoiceLanguageModeId;
    private final int mVoiceMaxResults;
    private final int mVoicePromptTextId;
    private final int mVoiceSearchMode;

    @UnsupportedAppUsage
    private SearchableInfo(Context object, AttributeSet object2, ComponentName componentName) {
        this.mSearchActivity = componentName;
        object2 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.Searchable);
        this.mSearchMode = ((TypedArray)object2).getInt(3, 0);
        this.mLabelId = ((TypedArray)object2).getResourceId(0, 0);
        this.mHintId = ((TypedArray)object2).getResourceId(2, 0);
        this.mIconId = ((TypedArray)object2).getResourceId(1, 0);
        this.mSearchButtonText = ((TypedArray)object2).getResourceId(9, 0);
        this.mSearchInputType = ((TypedArray)object2).getInt(10, 1);
        this.mSearchImeOptions = ((TypedArray)object2).getInt(16, 2);
        this.mIncludeInGlobalSearch = ((TypedArray)object2).getBoolean(18, false);
        this.mQueryAfterZeroResults = ((TypedArray)object2).getBoolean(19, false);
        this.mAutoUrlDetect = ((TypedArray)object2).getBoolean(21, false);
        this.mSettingsDescriptionId = ((TypedArray)object2).getResourceId(20, 0);
        this.mSuggestAuthority = ((TypedArray)object2).getString(4);
        this.mSuggestPath = ((TypedArray)object2).getString(5);
        this.mSuggestSelection = ((TypedArray)object2).getString(6);
        this.mSuggestIntentAction = ((TypedArray)object2).getString(7);
        this.mSuggestIntentData = ((TypedArray)object2).getString(8);
        this.mSuggestThreshold = ((TypedArray)object2).getInt(17, 0);
        this.mVoiceSearchMode = ((TypedArray)object2).getInt(11, 0);
        this.mVoiceLanguageModeId = ((TypedArray)object2).getResourceId(12, 0);
        this.mVoicePromptTextId = ((TypedArray)object2).getResourceId(13, 0);
        this.mVoiceLanguageId = ((TypedArray)object2).getResourceId(14, 0);
        this.mVoiceMaxResults = ((TypedArray)object2).getInt(15, 0);
        ((TypedArray)object2).recycle();
        componentName = null;
        object2 = componentName;
        if (this.mSuggestAuthority != null) {
            object = ((Context)object).getPackageManager().resolveContentProvider(this.mSuggestAuthority, 268435456);
            object2 = componentName;
            if (object != null) {
                object2 = ((ProviderInfo)object).packageName;
            }
        }
        this.mSuggestProviderPackage = object2;
        if (this.mLabelId != 0) {
            return;
        }
        throw new IllegalArgumentException("Search label must be a resource reference.");
    }

    SearchableInfo(Parcel parcel) {
        this.mLabelId = parcel.readInt();
        this.mSearchActivity = ComponentName.readFromParcel(parcel);
        this.mHintId = parcel.readInt();
        this.mSearchMode = parcel.readInt();
        this.mIconId = parcel.readInt();
        this.mSearchButtonText = parcel.readInt();
        this.mSearchInputType = parcel.readInt();
        this.mSearchImeOptions = parcel.readInt();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mIncludeInGlobalSearch = bl2;
        bl2 = parcel.readInt() != 0;
        this.mQueryAfterZeroResults = bl2;
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.mAutoUrlDetect = bl2;
        this.mSettingsDescriptionId = parcel.readInt();
        this.mSuggestAuthority = parcel.readString();
        this.mSuggestPath = parcel.readString();
        this.mSuggestSelection = parcel.readString();
        this.mSuggestIntentAction = parcel.readString();
        this.mSuggestIntentData = parcel.readString();
        this.mSuggestThreshold = parcel.readInt();
        for (n = parcel.readInt(); n > 0; --n) {
            this.addActionKey(new ActionKeyInfo(parcel));
        }
        this.mSuggestProviderPackage = parcel.readString();
        this.mVoiceSearchMode = parcel.readInt();
        this.mVoiceLanguageModeId = parcel.readInt();
        this.mVoicePromptTextId = parcel.readInt();
        this.mVoiceLanguageId = parcel.readInt();
        this.mVoiceMaxResults = parcel.readInt();
    }

    private void addActionKey(ActionKeyInfo actionKeyInfo) {
        if (this.mActionKeys == null) {
            this.mActionKeys = new HashMap();
        }
        this.mActionKeys.put(actionKeyInfo.getKeyCode(), actionKeyInfo);
    }

    private static Context createActivityContext(Context object, ComponentName componentName) {
        Object var2_4 = null;
        StringBuilder stringBuilder = null;
        try {
            object = ((Context)object).createPackageContext(componentName.getPackageName(), 0);
        }
        catch (SecurityException securityException) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Can't make context for ");
            stringBuilder.append(componentName.getPackageName());
            Log.e(LOG_TAG, stringBuilder.toString(), securityException);
            object = var2_4;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Package not found ");
            ((StringBuilder)object).append(componentName.getPackageName());
            Log.e(LOG_TAG, ((StringBuilder)object).toString());
            object = stringBuilder;
        }
        return object;
    }

    public static SearchableInfo getActivityMetaData(Context object, ActivityInfo parcelable, int n) {
        Object object2;
        try {
            object2 = new UserHandle(n);
            object2 = ((Context)object).createPackageContextAsUser("system", 0, (UserHandle)object2);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't create package context for user ");
            stringBuilder.append(n);
            Log.e(LOG_TAG, stringBuilder.toString());
            return null;
        }
        object = parcelable.loadXmlMetaData(((Context)object2).getPackageManager(), MD_LABEL_SEARCHABLE);
        if (object == null) {
            return null;
        }
        parcelable = SearchableInfo.getActivityMetaData((Context)object2, (XmlPullParser)object, new ComponentName(parcelable.packageName, parcelable.name));
        object.close();
        return parcelable;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static SearchableInfo getActivityMetaData(Context object, XmlPullParser object2, ComponentName componentName) {
        Object object3 = null;
        Context context = SearchableInfo.createActivityContext((Context)object, componentName);
        if (context == null) {
            return null;
        }
        try {
            int n = object2.next();
            object = object3;
            do {
                if (n == 1) {
                    return object;
                }
                object3 = object;
                if (n == 2) {
                    AttributeSet attributeSet;
                    boolean bl = object2.getName().equals(MD_XML_ELEMENT_SEARCHABLE);
                    if (bl) {
                        attributeSet = Xml.asAttributeSet((XmlPullParser)object2);
                        object3 = object;
                        if (attributeSet != null) {
                            try {
                                object3 = new SearchableInfo(context, attributeSet, componentName);
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Invalid searchable metadata for ");
                                ((StringBuilder)object2).append(componentName.flattenToShortString());
                                ((StringBuilder)object2).append(": ");
                                ((StringBuilder)object2).append(illegalArgumentException.getMessage());
                                Log.w(LOG_TAG, ((StringBuilder)object2).toString());
                                return null;
                            }
                        }
                    } else {
                        object3 = object;
                        if (object2.getName().equals(MD_XML_ELEMENT_SEARCHABLE_ACTION_KEY)) {
                            if (object == null) {
                                return null;
                            }
                            attributeSet = Xml.asAttributeSet((XmlPullParser)object2);
                            object3 = object;
                            if (attributeSet != null) {
                                try {
                                    object3 = new ActionKeyInfo(context, attributeSet);
                                    SearchableInfo.super.addActionKey((ActionKeyInfo)object3);
                                    object3 = object;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Invalid action key for ");
                                    ((StringBuilder)object).append(componentName.flattenToShortString());
                                    ((StringBuilder)object).append(": ");
                                    ((StringBuilder)object).append(illegalArgumentException.getMessage());
                                    Log.w(LOG_TAG, ((StringBuilder)object).toString());
                                    return null;
                                }
                            }
                        }
                    }
                }
                n = object2.next();
                object = object3;
            } while (true);
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Reading searchable metadata for ");
            ((StringBuilder)object2).append(componentName.flattenToShortString());
            Log.w(LOG_TAG, ((StringBuilder)object2).toString(), iOException);
            return null;
        }
        catch (XmlPullParserException xmlPullParserException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Reading searchable metadata for ");
            ((StringBuilder)object2).append(componentName.flattenToShortString());
            Log.w(LOG_TAG, ((StringBuilder)object2).toString(), xmlPullParserException);
            return null;
        }
    }

    public boolean autoUrlDetect() {
        return this.mAutoUrlDetect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public ActionKeyInfo findActionKey(int n) {
        HashMap<Integer, ActionKeyInfo> hashMap = this.mActionKeys;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(n);
    }

    @UnsupportedAppUsage
    public Context getActivityContext(Context context) {
        return SearchableInfo.createActivityContext(context, this.mSearchActivity);
    }

    public int getHintId() {
        return this.mHintId;
    }

    @UnsupportedAppUsage
    public int getIconId() {
        return this.mIconId;
    }

    public int getImeOptions() {
        return this.mSearchImeOptions;
    }

    public int getInputType() {
        return this.mSearchInputType;
    }

    @UnsupportedAppUsage
    public int getLabelId() {
        return this.mLabelId;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public Context getProviderContext(Context context, Context context2) {
        Object var3_5 = null;
        Object var4_6 = null;
        if (this.mSearchActivity.getPackageName().equals(this.mSuggestProviderPackage)) {
            return context2;
        }
        String string2 = this.mSuggestProviderPackage;
        context2 = var3_5;
        if (string2 == null) return context2;
        try {
            context = context.createPackageContext(string2, 0);
            return context;
        }
        catch (SecurityException securityException) {
            return var3_5;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return var4_6;
        }
    }

    public ComponentName getSearchActivity() {
        return this.mSearchActivity;
    }

    public int getSearchButtonText() {
        return this.mSearchButtonText;
    }

    public int getSettingsDescriptionId() {
        return this.mSettingsDescriptionId;
    }

    public String getSuggestAuthority() {
        return this.mSuggestAuthority;
    }

    public String getSuggestIntentAction() {
        return this.mSuggestIntentAction;
    }

    public String getSuggestIntentData() {
        return this.mSuggestIntentData;
    }

    public String getSuggestPackage() {
        return this.mSuggestProviderPackage;
    }

    public String getSuggestPath() {
        return this.mSuggestPath;
    }

    public String getSuggestSelection() {
        return this.mSuggestSelection;
    }

    public int getSuggestThreshold() {
        return this.mSuggestThreshold;
    }

    public int getVoiceLanguageId() {
        return this.mVoiceLanguageId;
    }

    public int getVoiceLanguageModeId() {
        return this.mVoiceLanguageModeId;
    }

    public int getVoiceMaxResults() {
        return this.mVoiceMaxResults;
    }

    public int getVoicePromptTextId() {
        return this.mVoicePromptTextId;
    }

    public boolean getVoiceSearchEnabled() {
        int n = this.mVoiceSearchMode;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean getVoiceSearchLaunchRecognizer() {
        boolean bl = (this.mVoiceSearchMode & 4) != 0;
        return bl;
    }

    public boolean getVoiceSearchLaunchWebSearch() {
        boolean bl = (this.mVoiceSearchMode & 2) != 0;
        return bl;
    }

    public boolean queryAfterZeroResults() {
        return this.mQueryAfterZeroResults;
    }

    public boolean shouldIncludeInGlobalSearch() {
        return this.mIncludeInGlobalSearch;
    }

    public boolean shouldRewriteQueryFromData() {
        boolean bl = (this.mSearchMode & 16) != 0;
        return bl;
    }

    public boolean shouldRewriteQueryFromText() {
        boolean bl = (this.mSearchMode & 32) != 0;
        return bl;
    }

    public boolean useBadgeIcon() {
        boolean bl = (this.mSearchMode & 8) != 0 && this.mIconId != 0;
        return bl;
    }

    public boolean useBadgeLabel() {
        boolean bl = (this.mSearchMode & 4) != 0;
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mLabelId);
        this.mSearchActivity.writeToParcel(parcel, n);
        parcel.writeInt(this.mHintId);
        parcel.writeInt(this.mSearchMode);
        parcel.writeInt(this.mIconId);
        parcel.writeInt(this.mSearchButtonText);
        parcel.writeInt(this.mSearchInputType);
        parcel.writeInt(this.mSearchImeOptions);
        parcel.writeInt((int)this.mIncludeInGlobalSearch);
        parcel.writeInt((int)this.mQueryAfterZeroResults);
        parcel.writeInt((int)this.mAutoUrlDetect);
        parcel.writeInt(this.mSettingsDescriptionId);
        parcel.writeString(this.mSuggestAuthority);
        parcel.writeString(this.mSuggestPath);
        parcel.writeString(this.mSuggestSelection);
        parcel.writeString(this.mSuggestIntentAction);
        parcel.writeString(this.mSuggestIntentData);
        parcel.writeInt(this.mSuggestThreshold);
        Object object = this.mActionKeys;
        if (object == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(((HashMap)object).size());
            object = this.mActionKeys.values().iterator();
            while (object.hasNext()) {
                ((ActionKeyInfo)object.next()).writeToParcel(parcel, n);
            }
        }
        parcel.writeString(this.mSuggestProviderPackage);
        parcel.writeInt(this.mVoiceSearchMode);
        parcel.writeInt(this.mVoiceLanguageModeId);
        parcel.writeInt(this.mVoicePromptTextId);
        parcel.writeInt(this.mVoiceLanguageId);
        parcel.writeInt(this.mVoiceMaxResults);
    }

    public static class ActionKeyInfo
    implements Parcelable {
        private final int mKeyCode;
        private final String mQueryActionMsg;
        private final String mSuggestActionMsg;
        private final String mSuggestActionMsgColumn;

        ActionKeyInfo(Context object, AttributeSet attributeSet) {
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.SearchableActionKey);
            this.mKeyCode = ((TypedArray)object).getInt(0, 0);
            this.mQueryActionMsg = ((TypedArray)object).getString(1);
            this.mSuggestActionMsg = ((TypedArray)object).getString(2);
            this.mSuggestActionMsgColumn = ((TypedArray)object).getString(3);
            ((TypedArray)object).recycle();
            if (this.mKeyCode != 0) {
                if (this.mQueryActionMsg == null && this.mSuggestActionMsg == null && this.mSuggestActionMsgColumn == null) {
                    throw new IllegalArgumentException("No message information.");
                }
                return;
            }
            throw new IllegalArgumentException("No keycode.");
        }

        private ActionKeyInfo(Parcel parcel) {
            this.mKeyCode = parcel.readInt();
            this.mQueryActionMsg = parcel.readString();
            this.mSuggestActionMsg = parcel.readString();
            this.mSuggestActionMsgColumn = parcel.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getKeyCode() {
            return this.mKeyCode;
        }

        @UnsupportedAppUsage
        public String getQueryActionMsg() {
            return this.mQueryActionMsg;
        }

        @UnsupportedAppUsage
        public String getSuggestActionMsg() {
            return this.mSuggestActionMsg;
        }

        @UnsupportedAppUsage
        public String getSuggestActionMsgColumn() {
            return this.mSuggestActionMsgColumn;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mKeyCode);
            parcel.writeString(this.mQueryActionMsg);
            parcel.writeString(this.mSuggestActionMsg);
            parcel.writeString(this.mSuggestActionMsgColumn);
        }
    }

}

