/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DebugUtils;
import android.util.Slog;

public class DumpHeapActivity
extends Activity {
    public static final String ACTION_DELETE_DUMPHEAP = "com.android.server.am.DELETE_DUMPHEAP";
    public static final String EXTRA_DELAY_DELETE = "delay_delete";
    public static final Uri JAVA_URI = Uri.parse("content://com.android.server.heapdump/java");
    public static final String KEY_DIRECT_LAUNCH = "direct_launch";
    public static final String KEY_IS_SYSTEM_PROCESS = "is_system_process";
    public static final String KEY_IS_USER_INITIATED = "is_user_initiated";
    public static final String KEY_PROCESS = "process";
    public static final String KEY_SIZE = "size";
    AlertDialog mDialog;
    boolean mHandled = false;
    String mProcess;
    long mSize;

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.mProcess = this.getIntent().getStringExtra(KEY_PROCESS);
        this.mSize = this.getIntent().getLongExtra(KEY_SIZE, 0L);
        boolean bl = this.getIntent().getBooleanExtra(KEY_IS_USER_INITIATED, false);
        boolean bl2 = this.getIntent().getBooleanExtra(KEY_IS_SYSTEM_PROCESS, false);
        object = this.getIntent().getStringExtra(KEY_DIRECT_LAUNCH);
        if (object != null) {
            Intent intent = new Intent("android.app.action.REPORT_HEAP_LIMIT");
            intent.setPackage((String)object);
            Object object2 = ClipData.newUri(this.getContentResolver(), "Heap Dump", JAVA_URI);
            intent.setClipData((ClipData)object2);
            intent.addFlags(1);
            intent.setType(((ClipData)object2).getDescription().getMimeType(0));
            intent.putExtra("android.intent.extra.STREAM", JAVA_URI);
            try {
                this.startActivity(intent);
                this.scheduleDelete();
                this.mHandled = true;
                this.finish();
                return;
            }
            catch (ActivityNotFoundException activityNotFoundException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unable to direct launch to ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(": ");
                ((StringBuilder)object2).append(activityNotFoundException.getMessage());
                Slog.i("DumpHeapActivity", ((StringBuilder)object2).toString());
            }
        }
        int n = bl ? 17039878 : (bl2 ? 17039879 : 17039880);
        object = new AlertDialog.Builder(this, 16974394);
        ((AlertDialog.Builder)object).setTitle(17039881);
        ((AlertDialog.Builder)object).setMessage(this.getString(n, this.mProcess, DebugUtils.sizeValueToString(this.mSize, null)));
        ((AlertDialog.Builder)object).setNegativeButton(17039360, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface object, int n) {
                object = DumpHeapActivity.this;
                ((DumpHeapActivity)object).mHandled = true;
                ((ContextWrapper)object).sendBroadcast(new Intent(DumpHeapActivity.ACTION_DELETE_DUMPHEAP));
                DumpHeapActivity.this.finish();
            }
        });
        ((AlertDialog.Builder)object).setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface object, int n) {
                object = DumpHeapActivity.this;
                ((DumpHeapActivity)object).mHandled = true;
                ((DumpHeapActivity)object).scheduleDelete();
                object = new Intent("android.intent.action.SEND");
                Object object2 = ClipData.newUri(DumpHeapActivity.this.getContentResolver(), "Heap Dump", JAVA_URI);
                ((Intent)object).setClipData((ClipData)object2);
                ((Intent)object).addFlags(1);
                ((Intent)object).setType(((ClipData)object2).getDescription().getMimeType(0));
                ((Intent)object).putExtra("android.intent.extra.STREAM", JAVA_URI);
                object2 = DumpHeapActivity.this;
                ((Activity)object2).startActivity(Intent.createChooser((Intent)object, ((Context)object2).getText(17039881)));
                DumpHeapActivity.this.finish();
            }
        });
        this.mDialog = ((AlertDialog.Builder)object).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!this.isChangingConfigurations() && !this.mHandled) {
            this.sendBroadcast(new Intent(ACTION_DELETE_DUMPHEAP));
        }
    }

    void scheduleDelete() {
        Intent intent = new Intent(ACTION_DELETE_DUMPHEAP);
        intent.putExtra(EXTRA_DELAY_DELETE, true);
        this.sendBroadcast(intent);
    }

}

