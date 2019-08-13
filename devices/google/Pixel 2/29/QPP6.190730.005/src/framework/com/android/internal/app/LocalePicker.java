/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.ListFragment;
import android.app.backup.BackupManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class LocalePicker
extends ListFragment {
    private static final boolean DEBUG = false;
    private static final String TAG = "LocalePicker";
    private static final String[] pseudoLocales = new String[]{"en-XA", "ar-XB"};
    LocaleSelectionListener mListener;

    public static ArrayAdapter<LocaleInfo> constructAdapter(Context context) {
        return LocalePicker.constructAdapter(context, 17367183, 16909076);
    }

    public static ArrayAdapter<LocaleInfo> constructAdapter(Context context, int n, int n2) {
        Object object = context.getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)object, "development_settings_enabled", 0) != 0) {
            bl = true;
        }
        object = LocalePicker.getAllAssetLocales(context, bl);
        return new ArrayAdapter<LocaleInfo>(context, n, n2, (List)object, (LayoutInflater)context.getSystemService("layout_inflater"), n, n2){
            final /* synthetic */ int val$fieldId;
            final /* synthetic */ LayoutInflater val$inflater;
            final /* synthetic */ int val$layoutId;
            {
                this.val$inflater = layoutInflater;
                this.val$layoutId = n3;
                this.val$fieldId = n4;
                super(context, n, n2, list);
            }

            @Override
            public View getView(int n, View view, ViewGroup view2) {
                if (view == null) {
                    view2 = this.val$inflater.inflate(this.val$layoutId, (ViewGroup)view2, false);
                    view = (TextView)view2.findViewById(this.val$fieldId);
                    view2.setTag(view);
                } else {
                    view2 = view;
                    view = (TextView)view2.getTag();
                }
                LocaleInfo localeInfo = (LocaleInfo)this.getItem(n);
                ((TextView)view).setText(localeInfo.toString());
                ((TextView)view).setTextLocale(localeInfo.getLocale());
                return view2;
            }
        };
    }

    public static List<LocaleInfo> getAllAssetLocales(Context arrstring, boolean bl) {
        String[] arrstring2 = arrstring.getResources();
        arrstring = LocalePicker.getSystemAssetLocales();
        Object object = new ArrayList(arrstring.length);
        Collections.addAll(object, arrstring);
        Collections.sort(object);
        arrstring = arrstring2.getStringArray(17236104);
        arrstring2 = arrstring2.getStringArray(17236105);
        ArrayList<LocaleInfo> arrayList = new ArrayList<LocaleInfo>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Locale locale = Locale.forLanguageTag(((String)object.next()).replace('_', '-'));
            if (locale == null || "und".equals(locale.getLanguage()) || locale.getLanguage().isEmpty() || locale.getCountry().isEmpty() || !bl && LocaleList.isPseudoLocale(locale)) continue;
            if (arrayList.isEmpty()) {
                arrayList.add(new LocaleInfo(LocalePicker.toTitleCase(locale.getDisplayLanguage(locale)), locale));
                continue;
            }
            LocaleInfo localeInfo = arrayList.get(arrayList.size() - 1);
            if (localeInfo.locale.getLanguage().equals(locale.getLanguage()) && !localeInfo.locale.getLanguage().equals("zz")) {
                localeInfo.label = LocalePicker.toTitleCase(LocalePicker.getDisplayName(localeInfo.locale, arrstring, arrstring2));
                arrayList.add(new LocaleInfo(LocalePicker.toTitleCase(LocalePicker.getDisplayName(locale, arrstring, arrstring2)), locale));
                continue;
            }
            arrayList.add(new LocaleInfo(LocalePicker.toTitleCase(locale.getDisplayLanguage(locale)), locale));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static String getDisplayName(Locale locale, String[] arrstring, String[] arrstring2) {
        String string2 = locale.toString();
        for (int i = 0; i < arrstring.length; ++i) {
            if (!arrstring[i].equals(string2)) continue;
            return arrstring2[i];
        }
        return locale.getDisplayName(locale);
    }

    @UnsupportedAppUsage
    public static LocaleList getLocales() {
        try {
            LocaleList localeList = ActivityManager.getService().getConfiguration().getLocales();
            return localeList;
        }
        catch (RemoteException remoteException) {
            return LocaleList.getDefault();
        }
    }

    public static String[] getSupportedLocales(Context context) {
        return context.getResources().getStringArray(17236106);
    }

    public static String[] getSystemAssetLocales() {
        return Resources.getSystem().getAssets().getLocales();
    }

    private static String toTitleCase(String string2) {
        if (string2.length() == 0) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Character.toUpperCase(string2.charAt(0)));
        stringBuilder.append(string2.substring(1));
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public static void updateLocale(Locale locale) {
        LocalePicker.updateLocales(new LocaleList(locale));
    }

    @UnsupportedAppUsage
    public static void updateLocales(LocaleList localeList) {
        try {
            IActivityManager iActivityManager = ActivityManager.getService();
            Configuration configuration = iActivityManager.getConfiguration();
            configuration.setLocales(localeList);
            configuration.userSetLocale = true;
            iActivityManager.updatePersistentConfiguration(configuration);
            BackupManager.dataChanged("com.android.providers.settings");
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setListAdapter(LocalePicker.constructAdapter(this.getActivity()));
    }

    @Override
    public void onListItemClick(ListView object, View view, int n, long l) {
        if (this.mListener != null) {
            object = ((LocaleInfo)this.getListAdapter().getItem((int)n)).locale;
            this.mListener.onLocaleSelected((Locale)object);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getListView().requestFocus();
    }

    public void setLocaleSelectionListener(LocaleSelectionListener localeSelectionListener) {
        this.mListener = localeSelectionListener;
    }

    public static class LocaleInfo
    implements Comparable<LocaleInfo> {
        static final Collator sCollator = Collator.getInstance();
        String label;
        final Locale locale;

        public LocaleInfo(String string2, Locale locale) {
            this.label = string2;
            this.locale = locale;
        }

        @Override
        public int compareTo(LocaleInfo localeInfo) {
            return sCollator.compare(this.label, localeInfo.label);
        }

        public String getLabel() {
            return this.label;
        }

        @UnsupportedAppUsage
        public Locale getLocale() {
            return this.locale;
        }

        public String toString() {
            return this.label;
        }
    }

    public static interface LocaleSelectionListener {
        public void onLocaleSelected(Locale var1);
    }

}

