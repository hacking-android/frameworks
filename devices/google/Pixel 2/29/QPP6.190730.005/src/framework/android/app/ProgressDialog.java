/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.SpannableStringInternal;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.internal.R;
import java.text.NumberFormat;

@Deprecated
public class ProgressDialog
extends AlertDialog {
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    private boolean mHasStarted;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private int mMax;
    private CharSequence mMessage;
    @UnsupportedAppUsage
    private TextView mMessageView;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private ProgressBar mProgress;
    private Drawable mProgressDrawable;
    @UnsupportedAppUsage
    private TextView mProgressNumber;
    private String mProgressNumberFormat;
    private TextView mProgressPercent;
    private NumberFormat mProgressPercentFormat;
    private int mProgressStyle = 0;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private Handler mViewUpdateHandler;

    public ProgressDialog(Context context) {
        super(context);
        this.initFormats();
    }

    public ProgressDialog(Context context, int n) {
        super(context, n);
        this.initFormats();
    }

    private void initFormats() {
        this.mProgressNumberFormat = "%1d/%2d";
        this.mProgressPercentFormat = NumberFormat.getPercentInstance();
        this.mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        Handler handler;
        if (this.mProgressStyle == 1 && (handler = this.mViewUpdateHandler) != null && !handler.hasMessages(0)) {
            this.mViewUpdateHandler.sendEmptyMessage(0);
        }
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2) {
        return ProgressDialog.show(context, charSequence, charSequence2, false);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        return ProgressDialog.show(context, charSequence, charSequence2, bl, false, null);
    }

    public static ProgressDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean bl, boolean bl2) {
        return ProgressDialog.show(context, charSequence, charSequence2, bl, bl2, null);
    }

    public static ProgressDialog show(Context object, CharSequence charSequence, CharSequence charSequence2, boolean bl, boolean bl2, DialogInterface.OnCancelListener onCancelListener) {
        object = new ProgressDialog((Context)object);
        ((AlertDialog)object).setTitle(charSequence);
        ((ProgressDialog)object).setMessage(charSequence2);
        ((ProgressDialog)object).setIndeterminate(bl);
        ((Dialog)object).setCancelable(bl2);
        ((Dialog)object).setOnCancelListener(onCancelListener);
        ((Dialog)object).show();
        return object;
    }

    public int getMax() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getMax();
        }
        return this.mMax;
    }

    public int getProgress() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getProgress();
        }
        return this.mProgressVal;
    }

    public int getSecondaryProgress() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.getSecondaryProgress();
        }
        return this.mSecondaryProgressVal;
    }

    public void incrementProgressBy(int n) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementProgressBy(n);
            this.onProgressChanged();
        } else {
            this.mIncrementBy += n;
        }
    }

    public void incrementSecondaryProgressBy(int n) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.incrementSecondaryProgressBy(n);
            this.onProgressChanged();
        } else {
            this.mIncrementSecondaryBy += n;
        }
    }

    public boolean isIndeterminate() {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            return progressBar.isIndeterminate();
        }
        return this.mIndeterminate;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        Object object = LayoutInflater.from(this.mContext);
        Object object2 = this.mContext.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        if (this.mProgressStyle == 1) {
            this.mViewUpdateHandler = new Handler(){

                @Override
                public void handleMessage(Message object) {
                    super.handleMessage((Message)object);
                    int n = ProgressDialog.this.mProgress.getProgress();
                    int n2 = ProgressDialog.this.mProgress.getMax();
                    if (ProgressDialog.this.mProgressNumberFormat != null) {
                        object = ProgressDialog.this.mProgressNumberFormat;
                        ProgressDialog.this.mProgressNumber.setText(String.format((String)object, n, n2));
                    } else {
                        ProgressDialog.this.mProgressNumber.setText("");
                    }
                    if (ProgressDialog.this.mProgressPercentFormat != null) {
                        double d = (double)n / (double)n2;
                        object = new SpannableString(ProgressDialog.this.mProgressPercentFormat.format(d));
                        ((SpannableString)object).setSpan(new StyleSpan(1), 0, ((SpannableStringInternal)object).length(), 33);
                        ProgressDialog.this.mProgressPercent.setText((CharSequence)object);
                    } else {
                        ProgressDialog.this.mProgressPercent.setText("");
                    }
                }
            };
            object = ((LayoutInflater)object).inflate(((TypedArray)object2).getResourceId(13, 17367086), null);
            this.mProgress = (ProgressBar)((View)object).findViewById(16908301);
            this.mProgressNumber = (TextView)((View)object).findViewById(16909262);
            this.mProgressPercent = (TextView)((View)object).findViewById(16909263);
            this.setView((View)object);
        } else {
            object = ((LayoutInflater)object).inflate(((TypedArray)object2).getResourceId(18, 17367252), null);
            this.mProgress = (ProgressBar)((View)object).findViewById(16908301);
            this.mMessageView = (TextView)((View)object).findViewById(16908299);
            this.setView((View)object);
        }
        ((TypedArray)object2).recycle();
        int n = this.mMax;
        if (n > 0) {
            this.setMax(n);
        }
        if ((n = this.mProgressVal) > 0) {
            this.setProgress(n);
        }
        if ((n = this.mSecondaryProgressVal) > 0) {
            this.setSecondaryProgress(n);
        }
        if ((n = this.mIncrementBy) > 0) {
            this.incrementProgressBy(n);
        }
        if ((n = this.mIncrementSecondaryBy) > 0) {
            this.incrementSecondaryProgressBy(n);
        }
        if ((object2 = this.mProgressDrawable) != null) {
            this.setProgressDrawable((Drawable)object2);
        }
        if ((object2 = this.mIndeterminateDrawable) != null) {
            this.setIndeterminateDrawable((Drawable)object2);
        }
        if ((object2 = this.mMessage) != null) {
            this.setMessage((CharSequence)object2);
        }
        this.setIndeterminate(this.mIndeterminate);
        this.onProgressChanged();
        super.onCreate(bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mHasStarted = false;
    }

    public void setIndeterminate(boolean bl) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminate(bl);
        } else {
            this.mIndeterminate = bl;
        }
    }

    public void setIndeterminateDrawable(Drawable drawable2) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(drawable2);
        } else {
            this.mIndeterminateDrawable = drawable2;
        }
    }

    public void setMax(int n) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setMax(n);
            this.onProgressChanged();
        } else {
            this.mMax = n;
        }
    }

    @Override
    public void setMessage(CharSequence charSequence) {
        if (this.mProgress != null) {
            if (this.mProgressStyle == 1) {
                super.setMessage(charSequence);
            } else {
                this.mMessageView.setText(charSequence);
            }
        } else {
            this.mMessage = charSequence;
        }
    }

    public void setProgress(int n) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(n);
            this.onProgressChanged();
        } else {
            this.mProgressVal = n;
        }
    }

    public void setProgressDrawable(Drawable drawable2) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setProgressDrawable(drawable2);
        } else {
            this.mProgressDrawable = drawable2;
        }
    }

    public void setProgressNumberFormat(String string2) {
        this.mProgressNumberFormat = string2;
        this.onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat numberFormat) {
        this.mProgressPercentFormat = numberFormat;
        this.onProgressChanged();
    }

    public void setProgressStyle(int n) {
        this.mProgressStyle = n;
    }

    public void setSecondaryProgress(int n) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setSecondaryProgress(n);
            this.onProgressChanged();
        } else {
            this.mSecondaryProgressVal = n;
        }
    }

}

