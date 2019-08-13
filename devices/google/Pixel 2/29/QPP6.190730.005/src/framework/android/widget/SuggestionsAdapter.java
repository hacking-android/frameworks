/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.WeakHashMap;

class SuggestionsAdapter
extends ResourceCursorAdapter
implements View.OnClickListener {
    private static final boolean DBG = false;
    private static final long DELETE_KEY_POST_DELAY = 500L;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed = false;
    private final int mCommitIconResId;
    private int mFlagsCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement = 1;
    private final SearchManager mSearchManager = (SearchManager)this.mContext.getSystemService("search");
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private ColorStateList mUrlColor;

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId();
        context = this.mSearchable.getActivityContext(this.mContext);
        this.mProviderContext = this.mSearchable.getProviderContext(this.mContext, context);
        this.mOutsideDrawablesCache = weakHashMap;
        this.getFilter().setDelayer(new Filter.Delayer(){
            private int mPreviousLength = 0;

            @Override
            public long getPostingDelay(CharSequence charSequence) {
                long l = 0L;
                if (charSequence == null) {
                    return 0L;
                }
                if (charSequence.length() < this.mPreviousLength) {
                    l = 500L;
                }
                this.mPreviousLength = charSequence.length();
                return l;
            }
        });
    }

    private Drawable checkIconCache(String object) {
        if ((object = this.mOutsideDrawablesCache.get(object)) == null) {
            return null;
        }
        return ((Drawable.ConstantState)object).newDrawable();
    }

    private CharSequence formatUrl(Context object, CharSequence charSequence) {
        if (this.mUrlColor == null) {
            TypedValue typedValue = new TypedValue();
            ((Context)object).getTheme().resolveAttribute(17957087, typedValue, true);
            this.mUrlColor = ((Context)object).getColorStateList(typedValue.resourceId);
        }
        object = new SpannableString(charSequence);
        ((SpannableString)object).setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33);
        return object;
    }

    private Drawable getActivityIcon(ComponentName componentName) {
        Object object;
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            object = packageManager.getActivityInfo(componentName, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w(LOG_TAG, nameNotFoundException.toString());
            return null;
        }
        int n = ((ComponentInfo)object).getIconResource();
        if (n == 0) {
            return null;
        }
        object = packageManager.getDrawable(componentName.getPackageName(), n, ((ActivityInfo)object).applicationInfo);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid icon resource ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" for ");
            ((StringBuilder)object).append(componentName.flattenToShortString());
            Log.w(LOG_TAG, ((StringBuilder)object).toString());
            return null;
        }
        return object;
    }

    private Drawable getActivityIconWithCache(ComponentName object) {
        String string2 = ((ComponentName)object).flattenToShortString();
        boolean bl = this.mOutsideDrawablesCache.containsKey(string2);
        Object var4_4 = null;
        Drawable drawable2 = null;
        if (bl) {
            object = this.mOutsideDrawablesCache.get(string2);
            object = object == null ? drawable2 : ((Drawable.ConstantState)object).newDrawable(this.mProviderContext.getResources());
            return object;
        }
        drawable2 = this.getActivityIcon((ComponentName)object);
        object = drawable2 == null ? var4_4 : drawable2.getConstantState();
        this.mOutsideDrawablesCache.put(string2, (Drawable.ConstantState)object);
        return drawable2;
    }

    public static String getColumnString(Cursor cursor, String string2) {
        return SuggestionsAdapter.getStringOrNull(cursor, cursor.getColumnIndex(string2));
    }

    private Drawable getDefaultIcon1(Cursor object) {
        object = this.getActivityIconWithCache(this.mSearchable.getSearchActivity());
        if (object != null) {
            return object;
        }
        return this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Drawable getDrawable(Uri var1_1) {
        try {
            if ("android.resource".equals(var1_1.getScheme())) {
                var2_2 = this.mProviderContext.getContentResolver().getResourceId(var1_1);
                try {
                    return var2_2.r.getDrawable(var2_2.id, this.mProviderContext.getTheme());
                }
                catch (Resources.NotFoundException var2_3) {
                    var3_8 = new StringBuilder();
                    var3_8.append("Resource does not exist: ");
                    var3_8.append(var1_1);
                    var2_4 = new FileNotFoundException(var3_8.toString());
                    throw var2_4;
                }
            }
            var3_9 = this.mProviderContext.getContentResolver().openInputStream(var1_1);
            if (var3_9 == null) ** GOTO lbl57
        }
        catch (FileNotFoundException var3_11) {
            var2_5 = new StringBuilder();
            var2_5.append("Icon not found: ");
            var2_5.append(var1_1);
            var2_5.append(", ");
            var2_5.append(var3_11.getMessage());
            Log.w("SuggestionsAdapter", var2_5.toString());
            return null;
        }
        var2_5 = Drawable.createFromStream((InputStream)var3_9, null);
        {
            catch (Throwable var2_6) {
                try {
                    var3_9.close();
                    throw var2_6;
                }
                catch (IOException var3_10) {
                    var4_13 = new StringBuilder();
                    var4_13.append("Error closing icon stream for ");
                    var4_13.append(var1_1);
                    Log.e("SuggestionsAdapter", var4_13.toString(), var3_10);
                }
                throw var2_6;
            }
        }
        try {
            var3_9.close();
            return var2_5;
        }
        catch (IOException var4_12) {
            var3_9 = new StringBuilder();
            var3_9.append("Error closing icon stream for ");
            var3_9.append(var1_1);
            Log.e("SuggestionsAdapter", var3_9.toString(), var4_12);
        }
        return var2_5;
lbl57: // 1 sources:
        var2_7 = new StringBuilder();
        var2_7.append("Failed to open ");
        var2_7.append(var1_1);
        var3_9 = new FileNotFoundException(var2_7.toString());
        throw var3_9;
    }

    private Drawable getDrawableFromResourceValue(String string2) {
        if (string2 != null && string2.length() != 0 && !"0".equals(string2)) {
            int n;
            Drawable drawable2;
            CharSequence charSequence;
            block6 : {
                n = Integer.parseInt(string2);
                charSequence = new StringBuilder();
                charSequence.append("android.resource://");
                charSequence.append(this.mProviderContext.getPackageName());
                charSequence.append("/");
                charSequence.append(n);
                charSequence = charSequence.toString();
                drawable2 = this.checkIconCache((String)charSequence);
                if (drawable2 == null) break block6;
                return drawable2;
            }
            try {
                drawable2 = this.mProviderContext.getDrawable(n);
                this.storeInIconCache((String)charSequence, drawable2);
                return drawable2;
            }
            catch (Resources.NotFoundException notFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Icon resource not found: ");
                stringBuilder.append(string2);
                Log.w(LOG_TAG, stringBuilder.toString());
                return null;
            }
            catch (NumberFormatException numberFormatException) {
                Drawable drawable3 = this.checkIconCache(string2);
                if (drawable3 != null) {
                    return drawable3;
                }
                drawable3 = this.getDrawable(Uri.parse(string2));
                this.storeInIconCache(string2, drawable3);
                return drawable3;
            }
        }
        return null;
    }

    private Drawable getIcon1(Cursor cursor) {
        int n = this.mIconName1Col;
        if (n == -1) {
            return null;
        }
        Drawable drawable2 = this.getDrawableFromResourceValue(cursor.getString(n));
        if (drawable2 != null) {
            return drawable2;
        }
        return this.getDefaultIcon1(cursor);
    }

    private Drawable getIcon2(Cursor cursor) {
        int n = this.mIconName2Col;
        if (n == -1) {
            return null;
        }
        return this.getDrawableFromResourceValue(cursor.getString(n));
    }

    private static String getStringOrNull(Cursor object, int n) {
        if (n == -1) {
            return null;
        }
        try {
            object = object.getString(n);
            return object;
        }
        catch (Exception exception) {
            Log.e(LOG_TAG, "unexpected error retrieving valid column from cursor, did the remote process die?", exception);
            return null;
        }
    }

    private void setViewDrawable(ImageView imageView, Drawable drawable2, int n) {
        imageView.setImageDrawable(drawable2);
        if (drawable2 == null) {
            imageView.setVisibility(n);
        } else {
            imageView.setVisibility(0);
            drawable2.setVisible(false, false);
            drawable2.setVisible(true, false);
        }
    }

    private void setViewText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    private void storeInIconCache(String string2, Drawable drawable2) {
        if (drawable2 != null) {
            this.mOutsideDrawablesCache.put(string2, drawable2.getConstantState());
        }
    }

    private void updateSpinnerState(Cursor object) {
        if ((object = object != null ? object.getExtras() : null) != null && ((BaseBundle)object).getBoolean("in_progress")) {
            return;
        }
    }

    @Override
    public void bindView(View object, Context context, Cursor cursor) {
        ChildViewCache childViewCache = (ChildViewCache)((View)object).getTag();
        int n = 0;
        int n2 = this.mFlagsCol;
        if (n2 != -1) {
            n = cursor.getInt(n2);
        }
        if (childViewCache.mText1 != null) {
            object = SuggestionsAdapter.getStringOrNull(cursor, this.mText1Col);
            this.setViewText(childViewCache.mText1, (CharSequence)object);
        }
        if (childViewCache.mText2 != null) {
            object = SuggestionsAdapter.getStringOrNull(cursor, this.mText2UrlCol);
            object = object != null ? this.formatUrl(context, (CharSequence)object) : SuggestionsAdapter.getStringOrNull(cursor, this.mText2Col);
            if (TextUtils.isEmpty((CharSequence)object)) {
                if (childViewCache.mText1 != null) {
                    childViewCache.mText1.setSingleLine(false);
                    childViewCache.mText1.setMaxLines(2);
                }
            } else if (childViewCache.mText1 != null) {
                childViewCache.mText1.setSingleLine(true);
                childViewCache.mText1.setMaxLines(1);
            }
            this.setViewText(childViewCache.mText2, (CharSequence)object);
        }
        if (childViewCache.mIcon1 != null) {
            this.setViewDrawable(childViewCache.mIcon1, this.getIcon1(cursor), 4);
        }
        if (childViewCache.mIcon2 != null) {
            this.setViewDrawable(childViewCache.mIcon2, this.getIcon2(cursor), 8);
        }
        if ((n2 = this.mQueryRefinement) != 2 && (n2 != 1 || (n & 1) == 0)) {
            childViewCache.mIconRefine.setVisibility(8);
        } else {
            childViewCache.mIconRefine.setVisibility(0);
            childViewCache.mIconRefine.setTag(childViewCache.mText1.getText());
            childViewCache.mIconRefine.setOnClickListener(this);
        }
    }

    @Override
    public void changeCursor(Cursor cursor) {
        block5 : {
            if (this.mClosed) {
                Log.w(LOG_TAG, "Tried to change cursor after adapter was closed.");
                if (cursor != null) {
                    cursor.close();
                }
                return;
            }
            super.changeCursor(cursor);
            if (cursor == null) break block5;
            try {
                this.mText1Col = cursor.getColumnIndex("suggest_text_1");
                this.mText2Col = cursor.getColumnIndex("suggest_text_2");
                this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
                this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
                this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
                this.mFlagsCol = cursor.getColumnIndex("suggest_flags");
            }
            catch (Exception exception) {
                Log.e(LOG_TAG, "error changing cursor and caching columns", exception);
            }
        }
    }

    public void close() {
        this.changeCursor(null);
        this.mClosed = true;
    }

    @Override
    public CharSequence convertToString(Cursor object) {
        if (object == null) {
            return null;
        }
        String string2 = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_query");
        if (string2 != null) {
            return string2;
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (string2 = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_data")) != null) {
            return string2;
        }
        if (this.mSearchable.shouldRewriteQueryFromText() && (object = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_text_1")) != null) {
            return object;
        }
        return null;
    }

    @Override
    public View getDropDownView(int n, View object, ViewGroup viewGroup) {
        try {
            object = super.getDropDownView(n, (View)object, viewGroup);
            return object;
        }
        catch (RuntimeException runtimeException) {
            Log.w(LOG_TAG, "Search suggestions cursor threw exception.", runtimeException);
            object = this.mDropDownContext == null ? this.mContext : this.mDropDownContext;
            object = this.newDropDownView((Context)object, this.mCursor, viewGroup);
            if (object != null) {
                ((ChildViewCache)object.getTag()).mText1.setText(runtimeException.toString());
            }
            return object;
        }
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    @Override
    public View getView(int n, View view, ViewGroup view2) {
        try {
            view = super.getView(n, view, (ViewGroup)view2);
            return view;
        }
        catch (RuntimeException runtimeException) {
            Log.w(LOG_TAG, "Search suggestions cursor threw exception.", runtimeException);
            view2 = this.newView(this.mContext, this.mCursor, (ViewGroup)view2);
            if (view2 != null) {
                ((ChildViewCache)view2.getTag()).mText1.setText(runtimeException.toString());
            }
            return view2;
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View newView(Context object, Cursor cursor, ViewGroup viewGroup) {
        object = super.newView((Context)object, cursor, viewGroup);
        ((View)object).setTag(new ChildViewCache((View)object));
        ((ImageView)((View)object).findViewById(16908888)).setImageResource(this.mCommitIconResId);
        return object;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.updateSpinnerState(this.getCursor());
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        this.updateSpinnerState(this.getCursor());
    }

    @Override
    public void onClick(View object) {
        if ((object = ((View)object).getTag()) instanceof CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence)object);
        }
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence object) {
        object = object == null ? "" : object.toString();
        if (this.mSearchView.getVisibility() == 0 && this.mSearchView.getWindowVisibility() == 0) {
            block4 : {
                object = this.mSearchManager.getSuggestions(this.mSearchable, (String)object, 50);
                if (object == null) break block4;
                try {
                    object.getCount();
                    return object;
                }
                catch (RuntimeException runtimeException) {
                    Log.w(LOG_TAG, "Search suggestions query threw an exception.", runtimeException);
                }
            }
            return null;
        }
        return null;
    }

    public void setQueryRefinement(int n) {
        this.mQueryRefinement = n;
    }

    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view) {
            this.mText1 = (TextView)view.findViewById(16908308);
            this.mText2 = (TextView)view.findViewById(16908309);
            this.mIcon1 = (ImageView)view.findViewById(16908295);
            this.mIcon2 = (ImageView)view.findViewById(16908296);
            this.mIconRefine = (ImageView)view.findViewById(16908888);
        }
    }

}

