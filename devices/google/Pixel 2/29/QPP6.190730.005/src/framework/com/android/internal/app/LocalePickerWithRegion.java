/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocalePicker;
import com.android.internal.app.LocaleStore;
import com.android.internal.app.SuggestedLocaleAdapter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class LocalePickerWithRegion
extends ListFragment
implements SearchView.OnQueryTextListener {
    private static final String PARENT_FRAGMENT_NAME = "localeListEditor";
    private SuggestedLocaleAdapter mAdapter;
    private int mFirstVisiblePosition = 0;
    private LocaleSelectedListener mListener;
    private Set<LocaleStore.LocaleInfo> mLocaleList;
    private LocaleStore.LocaleInfo mParentLocale;
    private CharSequence mPreviousSearch = null;
    private boolean mPreviousSearchHadFocus = false;
    private SearchView mSearchView = null;
    private int mTopDistance = 0;
    private boolean mTranslatedOnly = false;

    private static LocalePickerWithRegion createCountryPicker(Context object, LocaleSelectedListener localeSelectedListener, LocaleStore.LocaleInfo localeInfo, boolean bl) {
        LocalePickerWithRegion localePickerWithRegion = new LocalePickerWithRegion();
        object = localePickerWithRegion.setListener((Context)object, localeSelectedListener, localeInfo, bl) ? localePickerWithRegion : null;
        return object;
    }

    public static LocalePickerWithRegion createLanguagePicker(Context context, LocaleSelectedListener localeSelectedListener, boolean bl) {
        LocalePickerWithRegion localePickerWithRegion = new LocalePickerWithRegion();
        localePickerWithRegion.setListener(context, localeSelectedListener, null, bl);
        return localePickerWithRegion;
    }

    private void returnToParentFrame() {
        this.getFragmentManager().popBackStack(PARENT_FRAGMENT_NAME, 1);
    }

    private boolean setListener(Context context, LocaleSelectedListener localeSelectedListener, LocaleStore.LocaleInfo localeInfo, boolean bl) {
        this.mParentLocale = localeInfo;
        this.mListener = localeSelectedListener;
        this.mTranslatedOnly = bl;
        this.setRetainInstance(true);
        HashSet<String> hashSet = new HashSet<String>();
        if (!bl) {
            Collections.addAll(hashSet, LocalePicker.getLocales().toLanguageTags().split(","));
        }
        if (localeInfo != null) {
            this.mLocaleList = LocaleStore.getLevelLocales(context, hashSet, localeInfo, bl);
            if (this.mLocaleList.size() <= 1) {
                if (localeSelectedListener != null && this.mLocaleList.size() == 1) {
                    localeSelectedListener.onLocaleSelected(this.mLocaleList.iterator().next());
                }
                return false;
            }
        } else {
            this.mLocaleList = LocaleStore.getLevelLocales(context, hashSet, null, bl);
        }
        return true;
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        boolean bl = true;
        this.setHasOptionsMenu(true);
        if (this.mLocaleList == null) {
            this.returnToParentFrame();
            return;
        }
        if (this.mParentLocale == null) {
            bl = false;
        }
        object = bl ? this.mParentLocale.getLocale() : Locale.getDefault();
        this.mAdapter = new SuggestedLocaleAdapter(this.mLocaleList, bl);
        object = new LocaleHelper.LocaleInfoComparator((Locale)object, bl);
        this.mAdapter.sort((LocaleHelper.LocaleInfoComparator)object);
        this.setListAdapter(this.mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu object, MenuInflater menuInflater) {
        if (this.mParentLocale == null) {
            menuInflater.inflate(18087936, (Menu)object);
            object = object.findItem(16909077);
            this.mSearchView = (SearchView)object.getActionView();
            this.mSearchView.setQueryHint(this.getText(17040970));
            this.mSearchView.setOnQueryTextListener(this);
            if (!TextUtils.isEmpty(this.mPreviousSearch)) {
                object.expandActionView();
                this.mSearchView.setIconified(false);
                this.mSearchView.setActivated(true);
                if (this.mPreviousSearchHadFocus) {
                    this.mSearchView.requestFocus();
                }
                this.mSearchView.setQuery(this.mPreviousSearch, true);
            } else {
                this.mSearchView.setQuery(null, false);
            }
            this.getListView().setSelectionFromTop(this.mFirstVisiblePosition, this.mTopDistance);
        }
    }

    @Override
    public void onListItemClick(ListView object, View object2, int n, long l) {
        object2 = (LocaleStore.LocaleInfo)this.getListAdapter().getItem(n);
        if (((LocaleStore.LocaleInfo)object2).getParent() != null) {
            object = this.mListener;
            if (object != null) {
                object.onLocaleSelected((LocaleStore.LocaleInfo)object2);
            }
            this.returnToParentFrame();
        } else {
            object = LocalePickerWithRegion.createCountryPicker(this.getContext(), this.mListener, (LocaleStore.LocaleInfo)object2, this.mTranslatedOnly);
            if (object != null) {
                this.getFragmentManager().beginTransaction().setTransition(4097).replace(this.getId(), (Fragment)object).addToBackStack(null).commit();
            } else {
                this.returnToParentFrame();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.getFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        View view = this.mSearchView;
        int n = 0;
        if (view != null) {
            this.mPreviousSearchHadFocus = ((ViewGroup)view).hasFocus();
            this.mPreviousSearch = this.mSearchView.getQuery();
        } else {
            this.mPreviousSearchHadFocus = false;
            this.mPreviousSearch = null;
        }
        ListView listView = this.getListView();
        view = listView.getChildAt(0);
        this.mFirstVisiblePosition = listView.getFirstVisiblePosition();
        if (view != null) {
            n = view.getTop() - listView.getPaddingTop();
        }
        this.mTopDistance = n;
    }

    @Override
    public boolean onQueryTextChange(String string2) {
        SuggestedLocaleAdapter suggestedLocaleAdapter = this.mAdapter;
        if (suggestedLocaleAdapter != null) {
            suggestedLocaleAdapter.getFilter().filter(string2);
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String string2) {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mParentLocale != null) {
            this.getActivity().setTitle(this.mParentLocale.getFullNameNative());
        } else {
            this.getActivity().setTitle(17040226);
        }
        this.getListView().requestFocus();
    }

    public static interface LocaleSelectedListener {
        public void onLocaleSelected(LocaleStore.LocaleInfo var1);
    }

}

