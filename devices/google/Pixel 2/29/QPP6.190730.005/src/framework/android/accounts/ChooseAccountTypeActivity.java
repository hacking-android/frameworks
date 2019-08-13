/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.BaseBundle;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChooseAccountTypeActivity
extends Activity {
    private static final String TAG = "AccountChooser";
    private ArrayList<AuthInfo> mAuthenticatorInfosToDisplay;
    private HashMap<String, AuthInfo> mTypeToAuthenticatorInfo = new HashMap();

    static /* synthetic */ ArrayList access$000(ChooseAccountTypeActivity chooseAccountTypeActivity) {
        return chooseAccountTypeActivity.mAuthenticatorInfosToDisplay;
    }

    private void buildTypeToAuthDescriptionMap() {
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(this).getAuthenticatorTypes()) {
            Object object;
            Object object2;
            block10 : {
                Object object3;
                block11 : {
                    CharSequence charSequence;
                    CharSequence charSequence2;
                    Object object4;
                    Object object5;
                    block9 : {
                        charSequence2 = null;
                        StringBuilder stringBuilder = null;
                        Object var7_7 = null;
                        charSequence = null;
                        object2 = null;
                        object3 = charSequence2;
                        object5 = object2;
                        object4 = stringBuilder;
                        object = charSequence;
                        Context context = this.createPackageContext(authenticatorDescription.packageName, 0);
                        object3 = charSequence2;
                        object5 = object2;
                        object4 = stringBuilder;
                        object = charSequence;
                        object2 = context.getDrawable(authenticatorDescription.iconId);
                        object3 = charSequence2;
                        object5 = object2;
                        object4 = stringBuilder;
                        object = object2;
                        charSequence = context.getResources().getText(authenticatorDescription.labelId);
                        object = var7_7;
                        if (charSequence == null) break block9;
                        object3 = charSequence2;
                        object5 = object2;
                        object4 = stringBuilder;
                        object = object2;
                        charSequence2 = charSequence.toString();
                        object = charSequence2;
                    }
                    object3 = object;
                    object5 = object2;
                    object4 = object;
                    object = object2;
                    try {
                        charSequence2 = charSequence.toString();
                        object3 = charSequence2;
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        object = object3;
                        object2 = object5;
                        if (Log.isLoggable(TAG, 5)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No icon resource for account type ");
                            ((StringBuilder)object).append(authenticatorDescription.type);
                            Log.w(TAG, ((StringBuilder)object).toString());
                            object = object3;
                            object2 = object5;
                        }
                        break block10;
                    }
                    catch (PackageManager.NameNotFoundException nameNotFoundException) {
                        object3 = object4;
                        object2 = object;
                        if (!Log.isLoggable(TAG, 5)) break block11;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("No icon name for account type ");
                        ((StringBuilder)object3).append(authenticatorDescription.type);
                        Log.w(TAG, ((StringBuilder)object3).toString());
                        object2 = object;
                        object3 = object4;
                    }
                }
                object = object3;
            }
            object = new AuthInfo(authenticatorDescription, (String)object, (Drawable)object2);
            this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, (AuthInfo)object);
        }
    }

    private void setResultAndFinish(String string2) {
        Object object = new Bundle();
        ((BaseBundle)object).putString("accountType", string2);
        this.setResult(-1, new Intent().putExtras((Bundle)object));
        if (Log.isLoggable(TAG, 2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ChooseAccountTypeActivity.setResultAndFinish: selected account type ");
            ((StringBuilder)object).append(string2);
            Log.v(TAG, ((StringBuilder)object).toString());
        }
        this.finish();
    }

    @Override
    public void onCreate(Bundle object) {
        Object object2;
        super.onCreate((Bundle)object);
        if (Log.isLoggable(TAG, 2)) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ChooseAccountTypeActivity.onCreate(savedInstanceState=");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(")");
            Log.v(TAG, ((StringBuilder)object2).toString());
        }
        object = null;
        Object object3 = this.getIntent().getStringArrayExtra("allowableAccountTypes");
        if (object3 != null) {
            object2 = new HashSet(((String[])object3).length);
            int n = ((String[])object3).length;
            int n2 = 0;
            do {
                object = object2;
                if (n2 >= n) break;
                object2.add(object3[n2]);
                ++n2;
            } while (true);
        }
        this.buildTypeToAuthDescriptionMap();
        this.mAuthenticatorInfosToDisplay = new ArrayList(this.mTypeToAuthenticatorInfo.size());
        for (Map.Entry entry : this.mTypeToAuthenticatorInfo.entrySet()) {
            object3 = (String)entry.getKey();
            AuthInfo object4 = (AuthInfo)entry.getValue();
            if (object != null && !object.contains(object3)) continue;
            this.mAuthenticatorInfosToDisplay.add(object4);
        }
        if (this.mAuthenticatorInfosToDisplay.isEmpty()) {
            object = new Bundle();
            ((BaseBundle)object).putString("errorMessage", "no allowable account types");
            this.setResult(-1, new Intent().putExtras((Bundle)object));
            this.finish();
            return;
        }
        if (this.mAuthenticatorInfosToDisplay.size() == 1) {
            this.setResultAndFinish(this.mAuthenticatorInfosToDisplay.get((int)0).desc.type);
            return;
        }
        this.setContentView(17367118);
        object = (ListView)this.findViewById(16908298);
        ((ListView)object).setAdapter(new AccountArrayAdapter(this, 17367043, this.mAuthenticatorInfosToDisplay));
        ((AbsListView)object).setChoiceMode(0);
        ((AbsListView)object).setTextFilterEnabled(false);
        ((AdapterView)object).setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> callback, View view, int n, long l) {
                callback = ChooseAccountTypeActivity.this;
                callback.setResultAndFinish(((AuthInfo)ChooseAccountTypeActivity.access$000(callback).get((int)n)).desc.type);
            }
        });
    }

    private static class AccountArrayAdapter
    extends ArrayAdapter<AuthInfo> {
        private ArrayList<AuthInfo> mInfos;
        private LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(Context context, int n, ArrayList<AuthInfo> arrayList) {
            super(context, n, arrayList);
            this.mInfos = arrayList;
            this.mLayoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        @Override
        public View getView(int n, View view, ViewGroup object) {
            if (view == null) {
                view = this.mLayoutInflater.inflate(17367117, null);
                object = new ViewHolder();
                ((ViewHolder)object).text = (TextView)view.findViewById(16908667);
                ((ViewHolder)object).icon = (ImageView)view.findViewById(16908666);
                view.setTag(object);
            } else {
                object = (ViewHolder)view.getTag();
            }
            ((ViewHolder)object).text.setText(this.mInfos.get((int)n).name);
            ((ViewHolder)object).icon.setImageDrawable(this.mInfos.get((int)n).drawable);
            return view;
        }
    }

    private static class AuthInfo {
        final AuthenticatorDescription desc;
        final Drawable drawable;
        final String name;

        AuthInfo(AuthenticatorDescription authenticatorDescription, String string2, Drawable drawable2) {
            this.desc = authenticatorDescription;
            this.name = string2;
            this.drawable = drawable2;
        }
    }

    private static class ViewHolder {
        ImageView icon;
        TextView text;

        private ViewHolder() {
        }
    }

}

