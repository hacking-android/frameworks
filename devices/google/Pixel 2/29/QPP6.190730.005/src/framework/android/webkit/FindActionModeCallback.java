/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

@SystemApi
public class FindActionModeCallback
implements ActionMode.Callback,
TextWatcher,
View.OnClickListener,
WebView.FindListener {
    private ActionMode mActionMode;
    private int mActiveMatchIndex;
    private View mCustomView;
    private EditText mEditText;
    private Point mGlobalVisibleOffset = new Point();
    private Rect mGlobalVisibleRect = new Rect();
    private InputMethodManager mInput;
    private TextView mMatches;
    private boolean mMatchesFound;
    private int mNumberOfMatches;
    private Resources mResources;
    private WebView mWebView;

    public FindActionModeCallback(Context context) {
        this.mCustomView = LayoutInflater.from(context).inflate(17367336, null);
        this.mEditText = (EditText)this.mCustomView.findViewById(16908291);
        this.mEditText.setCustomSelectionActionModeCallback(new NoAction());
        this.mEditText.setOnClickListener(this);
        this.setText("");
        this.mMatches = (TextView)this.mCustomView.findViewById(16909088);
        this.mInput = context.getSystemService(InputMethodManager.class);
        this.mResources = context.getResources();
    }

    private void findNext(boolean bl) {
        WebView webView = this.mWebView;
        if (webView != null) {
            if (!this.mMatchesFound) {
                this.findAll();
                return;
            }
            if (this.mNumberOfMatches == 0) {
                return;
            }
            webView.findNext(bl);
            this.updateMatchesString();
            return;
        }
        throw new AssertionError((Object)"No WebView for FindActionModeCallback::findNext");
    }

    private void updateMatchesString() {
        int n = this.mNumberOfMatches;
        if (n == 0) {
            this.mMatches.setText(17040472);
        } else {
            this.mMatches.setText(this.mResources.getQuantityString(18153493, n, this.mActiveMatchIndex + 1, this.mNumberOfMatches));
        }
        this.mMatches.setVisibility(0);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

    public void findAll() {
        if (this.mWebView != null) {
            Editable editable = this.mEditText.getText();
            if (editable.length() == 0) {
                this.mWebView.clearMatches();
                this.mMatches.setVisibility(8);
                this.mMatchesFound = false;
                this.mWebView.findAll(null);
            } else {
                this.mMatchesFound = true;
                this.mMatches.setVisibility(4);
                this.mNumberOfMatches = 0;
                this.mWebView.findAllAsync(editable.toString());
            }
            return;
        }
        throw new AssertionError((Object)"No WebView for FindActionModeCallback::findAll");
    }

    public void finish() {
        this.mActionMode.finish();
    }

    public int getActionModeGlobalBottom() {
        View view;
        if (this.mActionMode == null) {
            return 0;
        }
        View view2 = view = (View)((Object)this.mCustomView.getParent());
        if (view == null) {
            view2 = this.mCustomView;
        }
        view2.getGlobalVisibleRect(this.mGlobalVisibleRect, this.mGlobalVisibleOffset);
        return this.mGlobalVisibleRect.bottom;
    }

    @Override
    public boolean onActionItemClicked(ActionMode object, MenuItem menuItem) {
        object = this.mWebView;
        if (object != null) {
            this.mInput.hideSoftInputFromWindow(((View)object).getWindowToken(), 0);
            switch (menuItem.getItemId()) {
                default: {
                    return false;
                }
                case 16908920: {
                    this.findNext(false);
                    break;
                }
                case 16908919: {
                    this.findNext(true);
                }
            }
            return true;
        }
        throw new AssertionError((Object)"No WebView for FindActionModeCallback::onActionItemClicked");
    }

    @Override
    public void onClick(View view) {
        this.findNext(true);
    }

    @Override
    public boolean onCreateActionMode(ActionMode object, Menu menu2) {
        if (!((ActionMode)object).isUiFocusable()) {
            return false;
        }
        ((ActionMode)object).setCustomView(this.mCustomView);
        ((ActionMode)object).getMenuInflater().inflate(18087938, menu2);
        this.mActionMode = object;
        object = this.mEditText.getText();
        Selection.setSelection((Spannable)object, object.length());
        this.mMatches.setVisibility(8);
        this.mMatchesFound = false;
        this.mMatches.setText("0");
        this.mEditText.requestFocus();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.mActionMode = null;
        this.mWebView.notifyFindDialogDismissed();
        this.mWebView.setFindDialogFindListener(null);
        this.mInput.hideSoftInputFromWindow(this.mWebView.getWindowToken(), 0);
    }

    @Override
    public void onFindResultReceived(int n, int n2, boolean bl) {
        if (bl) {
            bl = n2 == 0;
            this.updateMatchCount(n, n2, bl);
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
        return false;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        this.findAll();
    }

    public void setText(String charSequence) {
        this.mEditText.setText(charSequence);
        charSequence = this.mEditText.getText();
        int n = charSequence.length();
        Selection.setSelection((Spannable)charSequence, n, n);
        charSequence.setSpan(this, 0, n, 18);
        this.mMatchesFound = false;
    }

    public void setWebView(WebView webView) {
        if (webView != null) {
            this.mWebView = webView;
            this.mWebView.setFindDialogFindListener(this);
            return;
        }
        throw new AssertionError((Object)"WebView supplied to FindActionModeCallback cannot be null");
    }

    public void showSoftInput() {
        if (this.mEditText.requestFocus()) {
            this.mInput.showSoftInput(this.mEditText, 0);
        }
    }

    public void updateMatchCount(int n, int n2, boolean bl) {
        if (!bl) {
            this.mNumberOfMatches = n2;
            this.mActiveMatchIndex = n;
            this.updateMatchesString();
        } else {
            this.mMatches.setVisibility(8);
            this.mNumberOfMatches = 0;
        }
    }

    public static class NoAction
    implements ActionMode.Callback {
        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            return false;
        }
    }

}

