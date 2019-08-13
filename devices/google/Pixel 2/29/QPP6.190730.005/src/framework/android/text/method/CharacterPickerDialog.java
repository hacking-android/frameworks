/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class CharacterPickerDialog
extends Dialog
implements AdapterView.OnItemClickListener,
View.OnClickListener {
    private Button mCancelButton;
    private LayoutInflater mInflater;
    private boolean mInsert;
    private String mOptions;
    private Editable mText;
    private View mView;

    public CharacterPickerDialog(Context context, View view, Editable editable, String string2, boolean bl) {
        super(context, 16973913);
        this.mView = view;
        this.mText = editable;
        this.mOptions = string2;
        this.mInsert = bl;
        this.mInflater = LayoutInflater.from(context);
    }

    private void replaceCharacterAndClose(CharSequence charSequence) {
        int n = Selection.getSelectionEnd(this.mText);
        if (!this.mInsert && n != 0) {
            this.mText.replace(n - 1, n, charSequence);
        } else {
            this.mText.insert(n, charSequence);
        }
        this.dismiss();
    }

    @Override
    public void onClick(View view) {
        if (view == this.mCancelButton) {
            this.dismiss();
        } else if (view instanceof Button) {
            this.replaceCharacterAndClose(((Button)view).getText());
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getWindow().getAttributes();
        ((WindowManager.LayoutParams)object).token = this.mView.getApplicationWindowToken();
        ((WindowManager.LayoutParams)object).type = 1003;
        ((WindowManager.LayoutParams)object).flags |= 1;
        this.setContentView(17367114);
        object = (GridView)this.findViewById(16908801);
        ((GridView)object).setAdapter(new OptionsAdapter(this.getContext()));
        ((AdapterView)object).setOnItemClickListener(this);
        this.mCancelButton = (Button)this.findViewById(16908792);
        this.mCancelButton.setOnClickListener(this);
    }

    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.replaceCharacterAndClose(String.valueOf(this.mOptions.charAt(n)));
    }

    private class OptionsAdapter
    extends BaseAdapter {
        public OptionsAdapter(Context context) {
        }

        @Override
        public final int getCount() {
            return CharacterPickerDialog.this.mOptions.length();
        }

        @Override
        public final Object getItem(int n) {
            return String.valueOf(CharacterPickerDialog.this.mOptions.charAt(n));
        }

        @Override
        public final long getItemId(int n) {
            return n;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            view = (Button)CharacterPickerDialog.this.mInflater.inflate(17367115, null);
            ((TextView)view).setText(String.valueOf(CharacterPickerDialog.this.mOptions.charAt(n)));
            view.setOnClickListener(CharacterPickerDialog.this);
            return view;
        }
    }

}

