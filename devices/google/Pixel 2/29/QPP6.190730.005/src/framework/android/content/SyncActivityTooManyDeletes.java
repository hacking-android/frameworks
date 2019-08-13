/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SyncActivityTooManyDeletes
extends Activity
implements AdapterView.OnItemClickListener {
    private Account mAccount;
    private String mAuthority;
    private long mNumDeletes;
    private String mProvider;

    private void startSyncReallyDelete() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("deletions_override", true);
        bundle.putBoolean("force", true);
        bundle.putBoolean("expedited", true);
        bundle.putBoolean("upload", true);
        ContentResolver.requestSync(this.mAccount, this.mAuthority, bundle);
    }

    private void startSyncUndoDeletes() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("discard_deletions", true);
        bundle.putBoolean("force", true);
        bundle.putBoolean("expedited", true);
        bundle.putBoolean("upload", true);
        ContentResolver.requestSync(this.mAccount, this.mAuthority, bundle);
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getIntent().getExtras();
        if (object == null) {
            this.finish();
            return;
        }
        this.mNumDeletes = ((BaseBundle)object).getLong("numDeletes");
        this.mAccount = (Account)((Bundle)object).getParcelable("account");
        this.mAuthority = ((BaseBundle)object).getString("authority");
        this.mProvider = ((BaseBundle)object).getString("provider");
        Object object2 = new ArrayAdapter<CharSequence>((Context)this, 17367043, 16908308, new CharSequence[]{this.getResources().getText(17041113), this.getResources().getText(17041116), this.getResources().getText(17041112)});
        object = new ListView(this);
        ((ListView)object).setAdapter((ListAdapter)object2);
        ((ListView)object).setItemsCanFocus(true);
        ((AdapterView)object).setOnItemClickListener(this);
        TextView textView = new TextView(this);
        textView.setText(String.format(this.getResources().getText(17041115).toString(), this.mNumDeletes, this.mProvider, this.mAccount.name));
        object2 = new LinearLayout(this);
        ((LinearLayout)object2).setOrientation(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 0.0f);
        ((ViewGroup)object2).addView((View)textView, layoutParams);
        ((ViewGroup)object2).addView((View)object, layoutParams);
        this.setContentView((View)object2);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
        if (n == 0) {
            this.startSyncReallyDelete();
        } else if (n == 1) {
            this.startSyncUndoDeletes();
        }
        this.finish();
    }
}

