/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

@Deprecated
public class Plugin {
    private String mDescription;
    private String mFileName;
    private PreferencesClickHandler mHandler;
    private String mName;
    private String mPath;

    @Deprecated
    public Plugin(String string2, String string3, String string4, String string5) {
        this.mName = string2;
        this.mPath = string3;
        this.mFileName = string4;
        this.mDescription = string5;
        this.mHandler = new DefaultClickHandler();
    }

    @Deprecated
    public void dispatchClickEvent(Context context) {
        PreferencesClickHandler preferencesClickHandler = this.mHandler;
        if (preferencesClickHandler != null) {
            preferencesClickHandler.handleClickEvent(context);
        }
    }

    @Deprecated
    public String getDescription() {
        return this.mDescription;
    }

    @Deprecated
    public String getFileName() {
        return this.mFileName;
    }

    @Deprecated
    public String getName() {
        return this.mName;
    }

    @Deprecated
    public String getPath() {
        return this.mPath;
    }

    @Deprecated
    public void setClickHandler(PreferencesClickHandler preferencesClickHandler) {
        this.mHandler = preferencesClickHandler;
    }

    @Deprecated
    public void setDescription(String string2) {
        this.mDescription = string2;
    }

    @Deprecated
    public void setFileName(String string2) {
        this.mFileName = string2;
    }

    @Deprecated
    public void setName(String string2) {
        this.mName = string2;
    }

    @Deprecated
    public void setPath(String string2) {
        this.mPath = string2;
    }

    @Deprecated
    public String toString() {
        return this.mName;
    }

    @Deprecated
    private class DefaultClickHandler
    implements PreferencesClickHandler,
    DialogInterface.OnClickListener {
        private AlertDialog mDialog;

        private DefaultClickHandler() {
        }

        @Deprecated
        @Override
        public void handleClickEvent(Context context) {
            if (this.mDialog == null) {
                this.mDialog = new AlertDialog.Builder(context).setTitle(Plugin.this.mName).setMessage(Plugin.this.mDescription).setPositiveButton(17039370, (DialogInterface.OnClickListener)this).setCancelable(false).show();
            }
        }

        @Deprecated
        @Override
        public void onClick(DialogInterface dialogInterface, int n) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }

    public static interface PreferencesClickHandler {
        public void handleClickEvent(Context var1);
    }

}

