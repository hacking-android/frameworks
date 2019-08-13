/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;

@SystemApi
public class JsDialogHelper {
    public static final int ALERT = 1;
    public static final int CONFIRM = 2;
    public static final int PROMPT = 3;
    private static final String TAG = "JsDialogHelper";
    public static final int UNLOAD = 4;
    private final String mDefaultValue;
    private final String mMessage;
    private final JsPromptResult mResult;
    private final int mType;
    private final String mUrl;

    public JsDialogHelper(JsPromptResult jsPromptResult, int n, String string2, String string3, String string4) {
        this.mResult = jsPromptResult;
        this.mDefaultValue = string2;
        this.mMessage = string3;
        this.mType = n;
        this.mUrl = string4;
    }

    public JsDialogHelper(JsPromptResult jsPromptResult, Message message) {
        this.mResult = jsPromptResult;
        this.mDefaultValue = message.getData().getString("default");
        this.mMessage = message.getData().getString("message");
        this.mType = message.getData().getInt("type");
        this.mUrl = message.getData().getString("url");
    }

    private static boolean canShowAlertDialog(Context context) {
        return context instanceof Activity;
    }

    private String getJsDialogTitle(Context object) {
        String string2 = this.mUrl;
        if (URLUtil.isDataUrl(this.mUrl)) {
            object = ((Context)object).getString(17040150);
        } else {
            try {
                URL uRL = new URL(this.mUrl);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(uRL.getProtocol());
                stringBuilder.append("://");
                stringBuilder.append(uRL.getHost());
                object = ((Context)object).getString(17040149, stringBuilder.toString());
            }
            catch (MalformedURLException malformedURLException) {
                object = string2;
            }
        }
        return object;
    }

    public boolean invokeCallback(WebChromeClient object, WebView webView) {
        int n = this.mType;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        return ((WebChromeClient)object).onJsBeforeUnload(webView, this.mUrl, this.mMessage, this.mResult);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected type: ");
                    ((StringBuilder)object).append(this.mType);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                return ((WebChromeClient)object).onJsPrompt(webView, this.mUrl, this.mMessage, this.mDefaultValue, this.mResult);
            }
            return ((WebChromeClient)object).onJsConfirm(webView, this.mUrl, this.mMessage, this.mResult);
        }
        return ((WebChromeClient)object).onJsAlert(webView, this.mUrl, this.mMessage, this.mResult);
    }

    public void showDialog(Context object) {
        String string2;
        int n;
        Object object2;
        int n2;
        if (!JsDialogHelper.canShowAlertDialog((Context)object)) {
            Log.w(TAG, "Cannot create a dialog, the WebView context is not an Activity");
            this.mResult.cancel();
            return;
        }
        if (this.mType == 4) {
            object2 = ((Context)object).getString(17040148);
            string2 = ((Context)object).getString(17040145, this.mMessage);
            n2 = 17040147;
            n = 17040146;
        } else {
            object2 = this.getJsDialogTitle((Context)object);
            string2 = this.mMessage;
            n2 = 17039370;
            n = 17039360;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)object);
        builder.setTitle((CharSequence)object2);
        builder.setOnCancelListener(new CancelListener());
        if (this.mType != 3) {
            builder.setMessage(string2);
            builder.setPositiveButton(n2, (DialogInterface.OnClickListener)new PositiveListener(null));
        } else {
            object = LayoutInflater.from((Context)object).inflate(17367170, null);
            object2 = (EditText)((View)object).findViewById(16909539);
            ((TextView)object2).setText(this.mDefaultValue);
            builder.setPositiveButton(n2, (DialogInterface.OnClickListener)new PositiveListener((EditText)object2));
            ((TextView)((View)object).findViewById(16908299)).setText(this.mMessage);
            builder.setView((View)object);
        }
        if (this.mType != 1) {
            builder.setNegativeButton(n, (DialogInterface.OnClickListener)new CancelListener());
        }
        builder.show();
    }

    private class CancelListener
    implements DialogInterface.OnCancelListener,
    DialogInterface.OnClickListener {
        private CancelListener() {
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            JsDialogHelper.this.mResult.cancel();
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int n) {
            JsDialogHelper.this.mResult.cancel();
        }
    }

    private class PositiveListener
    implements DialogInterface.OnClickListener {
        private final EditText mEdit;

        public PositiveListener(EditText editText) {
            this.mEdit = editText;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int n) {
            if (this.mEdit == null) {
                JsDialogHelper.this.mResult.confirm();
            } else {
                JsDialogHelper.this.mResult.confirm(this.mEdit.getText().toString());
            }
        }
    }

}

