/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocaleStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class SuggestedLocaleAdapter
extends BaseAdapter
implements Filterable {
    private static final int MIN_REGIONS_FOR_SUGGESTIONS = 6;
    private static final int TYPE_HEADER_ALL_OTHERS = 1;
    private static final int TYPE_HEADER_SUGGESTED = 0;
    private static final int TYPE_LOCALE = 2;
    private Context mContextOverride = null;
    private final boolean mCountryMode;
    private Locale mDisplayLocale = null;
    private LayoutInflater mInflater;
    private ArrayList<LocaleStore.LocaleInfo> mLocaleOptions;
    private ArrayList<LocaleStore.LocaleInfo> mOriginalLocaleOptions;
    private int mSuggestionCount;

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> object, boolean bl) {
        this.mCountryMode = bl;
        this.mLocaleOptions = new ArrayList(object.size());
        Iterator<LocaleStore.LocaleInfo> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (((LocaleStore.LocaleInfo)object).isSuggested()) {
                ++this.mSuggestionCount;
            }
            this.mLocaleOptions.add((LocaleStore.LocaleInfo)object);
        }
    }

    static /* synthetic */ int access$208(SuggestedLocaleAdapter suggestedLocaleAdapter) {
        int n = suggestedLocaleAdapter.mSuggestionCount;
        suggestedLocaleAdapter.mSuggestionCount = n + 1;
        return n;
    }

    private void setTextTo(TextView textView, int n) {
        Context context = this.mContextOverride;
        if (context == null) {
            textView.setText(n);
        } else {
            textView.setText(context.getText(n));
        }
    }

    private boolean showHeaders() {
        boolean bl = this.mCountryMode;
        boolean bl2 = false;
        if (bl && this.mLocaleOptions.size() < 6) {
            return false;
        }
        int n = this.mSuggestionCount;
        bl = bl2;
        if (n != 0) {
            bl = bl2;
            if (n != this.mLocaleOptions.size()) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        if (this.showHeaders()) {
            return this.mLocaleOptions.size() + 2;
        }
        return this.mLocaleOptions.size();
    }

    @Override
    public Filter getFilter() {
        return new FilterByNativeAndUiNames();
    }

    @Override
    public Object getItem(int n) {
        int n2 = 0;
        if (this.showHeaders()) {
            n2 = n > this.mSuggestionCount ? -2 : -1;
        }
        return this.mLocaleOptions.get(n + n2);
    }

    @Override
    public long getItemId(int n) {
        return n;
    }

    @Override
    public int getItemViewType(int n) {
        if (!this.showHeaders()) {
            return 2;
        }
        if (n == 0) {
            return 0;
        }
        if (n == this.mSuggestionCount + 1) {
            return 1;
        }
        return 2;
    }

    @Override
    public View getView(int n, View object, ViewGroup object2) {
        int n2;
        if (object == null && this.mInflater == null) {
            this.mInflater = LayoutInflater.from(((View)object2).getContext());
        }
        if ((n2 = this.getItemViewType(n)) != 0 && n2 != 1) {
            View view = object;
            if (!(object instanceof ViewGroup)) {
                view = this.mInflater.inflate(17367174, (ViewGroup)object2, false);
            }
            TextView textView = (TextView)view.findViewById(16909076);
            object2 = (LocaleStore.LocaleInfo)this.getItem(n);
            textView.setText(((LocaleStore.LocaleInfo)object2).getLabel(this.mCountryMode));
            textView.setTextLocale(((LocaleStore.LocaleInfo)object2).getLocale());
            textView.setContentDescription(((LocaleStore.LocaleInfo)object2).getContentDescription(this.mCountryMode));
            object = view;
            if (this.mCountryMode) {
                n = TextUtils.getLayoutDirectionFromLocale(((LocaleStore.LocaleInfo)object2).getParent());
                view.setLayoutDirection(n);
                n = n == 1 ? 4 : 3;
                textView.setTextDirection(n);
                object = view;
            }
        } else {
            View view = object;
            if (!(object instanceof TextView)) {
                view = this.mInflater.inflate(17367175, (ViewGroup)object2, false);
            }
            object2 = (TextView)view;
            if (n2 == 0) {
                this.setTextTo((TextView)object2, 17040225);
            } else if (this.mCountryMode) {
                this.setTextTo((TextView)object2, 17040904);
            } else {
                this.setTextTo((TextView)object2, 17040224);
            }
            object = this.mDisplayLocale;
            if (object == null) {
                object = Locale.getDefault();
            }
            ((TextView)object2).setTextLocale((Locale)object);
            object = view;
        }
        return object;
    }

    @Override
    public int getViewTypeCount() {
        if (this.showHeaders()) {
            return 3;
        }
        return 1;
    }

    @Override
    public boolean isEnabled(int n) {
        boolean bl = this.getItemViewType(n) == 2;
        return bl;
    }

    public void setDisplayLocale(Context context, Locale locale) {
        if (locale == null) {
            this.mDisplayLocale = null;
            this.mContextOverride = null;
        } else if (!locale.equals(this.mDisplayLocale)) {
            this.mDisplayLocale = locale;
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            this.mContextOverride = context.createConfigurationContext(configuration);
        }
    }

    public void sort(LocaleHelper.LocaleInfoComparator localeInfoComparator) {
        Collections.sort(this.mLocaleOptions, localeInfoComparator);
    }

    class FilterByNativeAndUiNames
    extends Filter {
        FilterByNativeAndUiNames() {
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence object) {
            Object object2;
            Filter.FilterResults filterResults = new Filter.FilterResults();
            if (SuggestedLocaleAdapter.this.mOriginalLocaleOptions == null) {
                object2 = SuggestedLocaleAdapter.this;
                ((SuggestedLocaleAdapter)object2).mOriginalLocaleOptions = new ArrayList(((SuggestedLocaleAdapter)object2).mLocaleOptions);
            }
            object2 = new ArrayList(SuggestedLocaleAdapter.this.mOriginalLocaleOptions);
            if (object != null && object.length() != 0) {
                Locale locale = Locale.getDefault();
                String string2 = LocaleHelper.normalizeForSearch(object.toString(), locale);
                int n = ((ArrayList)object2).size();
                object = new ArrayList();
                for (int i = 0; i < n; ++i) {
                    LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo)((ArrayList)object2).get(i);
                    String string3 = LocaleHelper.normalizeForSearch(localeInfo.getFullNameInUiLanguage(), locale);
                    if (!this.wordMatches(LocaleHelper.normalizeForSearch(localeInfo.getFullNameNative(), locale), string2) && !this.wordMatches(string3, string2)) continue;
                    ((ArrayList)object).add(localeInfo);
                }
                filterResults.values = object;
                filterResults.count = ((ArrayList)object).size();
            } else {
                filterResults.values = object2;
                filterResults.count = ((ArrayList)object2).size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence object, Filter.FilterResults filterResults) {
            SuggestedLocaleAdapter.this.mLocaleOptions = (ArrayList)filterResults.values;
            SuggestedLocaleAdapter.this.mSuggestionCount = 0;
            object = SuggestedLocaleAdapter.this.mLocaleOptions.iterator();
            while (object.hasNext()) {
                if (!((LocaleStore.LocaleInfo)object.next()).isSuggested()) continue;
                SuggestedLocaleAdapter.access$208(SuggestedLocaleAdapter.this);
            }
            if (filterResults.count > 0) {
                SuggestedLocaleAdapter.this.notifyDataSetChanged();
            } else {
                SuggestedLocaleAdapter.this.notifyDataSetInvalidated();
            }
        }

        boolean wordMatches(String arrstring, String string2) {
            if (arrstring.startsWith(string2)) {
                return true;
            }
            arrstring = arrstring.split(" ");
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!arrstring[i].startsWith(string2)) continue;
                return true;
            }
            return false;
        }
    }

}

