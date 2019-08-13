/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.R;

public class QuickContactBadge
extends ImageView
implements View.OnClickListener {
    static final int EMAIL_ID_COLUMN_INDEX = 0;
    static final String[] EMAIL_LOOKUP_PROJECTION = new String[]{"contact_id", "lookup"};
    static final int EMAIL_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final String EXTRA_URI_CONTENT = "uri_content";
    static final int PHONE_ID_COLUMN_INDEX = 0;
    static final String[] PHONE_LOOKUP_PROJECTION = new String[]{"_id", "lookup"};
    static final int PHONE_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final int TOKEN_EMAIL_LOOKUP = 0;
    private static final int TOKEN_EMAIL_LOOKUP_AND_TRIGGER = 2;
    private static final int TOKEN_PHONE_LOOKUP = 1;
    private static final int TOKEN_PHONE_LOOKUP_AND_TRIGGER = 3;
    private String mContactEmail;
    private String mContactPhone;
    private Uri mContactUri;
    private Drawable mDefaultAvatar;
    protected String[] mExcludeMimes = null;
    private Bundle mExtras = null;
    @UnsupportedAppUsage
    private Drawable mOverlay;
    private String mPrioritizedMimeType;
    private QueryHandler mQueryHandler;

    public QuickContactBadge(Context context) {
        this(context, null);
    }

    public QuickContactBadge(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QuickContactBadge(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public QuickContactBadge(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        this.mOverlay = ((TypedArray)object).getDrawable(325);
        ((TypedArray)object).recycle();
        this.setOnClickListener(this);
    }

    static /* synthetic */ Uri access$000(QuickContactBadge quickContactBadge) {
        return quickContactBadge.mContactUri;
    }

    static /* synthetic */ Uri access$002(QuickContactBadge quickContactBadge, Uri uri) {
        quickContactBadge.mContactUri = uri;
        return uri;
    }

    static /* synthetic */ void access$100(QuickContactBadge quickContactBadge) {
        quickContactBadge.onContactUriChanged();
    }

    static /* synthetic */ String access$200(QuickContactBadge quickContactBadge) {
        return quickContactBadge.mPrioritizedMimeType;
    }

    private boolean isAssigned() {
        boolean bl = this.mContactUri != null || this.mContactEmail != null || this.mContactPhone != null;
        return bl;
    }

    private void onContactUriChanged() {
        this.setEnabled(this.isAssigned());
    }

    public void assignContactFromEmail(String string2, boolean bl) {
        this.assignContactFromEmail(string2, bl, null);
    }

    public void assignContactFromEmail(String object, boolean bl, Bundle bundle) {
        this.mContactEmail = object;
        this.mExtras = bundle;
        if (!bl && (object = this.mQueryHandler) != null) {
            ((AsyncQueryHandler)object).startQuery(0, null, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, null, null, null);
        } else {
            this.mContactUri = null;
            this.onContactUriChanged();
        }
    }

    public void assignContactFromPhone(String string2, boolean bl) {
        this.assignContactFromPhone(string2, bl, new Bundle());
    }

    public void assignContactFromPhone(String object, boolean bl, Bundle bundle) {
        this.mContactPhone = object;
        this.mExtras = bundle;
        if (!bl && (object = this.mQueryHandler) != null) {
            ((AsyncQueryHandler)object).startQuery(1, null, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, null, null, null);
        } else {
            this.mContactUri = null;
            this.onContactUriChanged();
        }
    }

    public void assignContactUri(Uri uri) {
        this.mContactUri = uri;
        this.mContactEmail = null;
        this.mContactPhone = null;
        this.onContactUriChanged();
    }

    @Override
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable2 = this.mOverlay;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mOverlay;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(this.getDrawableState())) {
            this.invalidateDrawable(drawable2);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return QuickContactBadge.class.getName();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.isInEditMode()) {
            this.mQueryHandler = new QueryHandler(this.mContext.getContentResolver());
        }
    }

    @Override
    public void onClick(View object) {
        block7 : {
            block5 : {
                Object object2;
                block6 : {
                    block4 : {
                        object = object2 = this.mExtras;
                        if (object2 == null) {
                            object = new Bundle();
                        }
                        if (this.mContactUri == null) break block4;
                        ContactsContract.QuickContact.showQuickContact(this.getContext(), (View)this, this.mContactUri, this.mExcludeMimes, this.mPrioritizedMimeType);
                        break block5;
                    }
                    object2 = this.mContactEmail;
                    if (object2 == null || this.mQueryHandler == null) break block6;
                    ((BaseBundle)object).putString(EXTRA_URI_CONTENT, (String)object2);
                    this.mQueryHandler.startQuery(2, object, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, null, null, null);
                    break block5;
                }
                object2 = this.mContactPhone;
                if (object2 == null || this.mQueryHandler == null) break block7;
                ((BaseBundle)object).putString(EXTRA_URI_CONTENT, (String)object2);
                this.mQueryHandler.startQuery(3, object, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, null, null, null);
            }
            return;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.isEnabled()) {
            return;
        }
        Drawable drawable2 = this.mOverlay;
        if (drawable2 != null && drawable2.getIntrinsicWidth() != 0 && this.mOverlay.getIntrinsicHeight() != 0) {
            this.mOverlay.setBounds(0, 0, this.getWidth(), this.getHeight());
            if (this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
                this.mOverlay.draw(canvas);
            } else {
                int n = canvas.getSaveCount();
                canvas.save();
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
                this.mOverlay.draw(canvas);
                canvas.restoreToCount(n);
            }
            return;
        }
    }

    public void setExcludeMimes(String[] arrstring) {
        this.mExcludeMimes = arrstring;
    }

    public void setImageToDefault() {
        if (this.mDefaultAvatar == null) {
            this.mDefaultAvatar = this.mContext.getDrawable(17302352);
        }
        this.setImageDrawable(this.mDefaultAvatar);
    }

    public void setMode(int n) {
    }

    public void setOverlay(Drawable drawable2) {
        this.mOverlay = drawable2;
    }

    public void setPrioritizedMimeType(String string2) {
        this.mPrioritizedMimeType = string2;
    }

    private class QueryHandler
    extends AsyncQueryHandler {
        public QueryHandler(ContentResolver contentResolver) {
            super(contentResolver);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        protected void onQueryComplete(int var1_1, Object var2_2, Cursor var3_4) {
            block13 : {
                block12 : {
                    block11 : {
                        var4_5 = null;
                        var5_6 = null;
                        var6_7 = null;
                        var7_8 = null;
                        var8_9 = 0;
                        var9_10 = 0;
                        var10_11 = 0;
                        var11_12 = var2_2 != null ? (Bundle)var2_2 : new Bundle();
                        var2_2 = var5_6;
                        if (var1_1 == 0) break block11;
                        var2_2 = var7_8;
                        var8_9 = var10_11;
                        if (var1_1 == 1) ** GOTO lbl28
                        if (var1_1 == 2) ** GOTO lbl23
                        if (var1_1 != 3) {
                            var7_8 = var4_5;
                            var1_1 = var9_10;
                        } else {
                            block10 : {
                                var8_9 = 1;
                                try {
                                    var2_2 = Uri.fromParts("tel", var11_12.getString("uri_content"), null);
                                    break block10;
lbl23: // 1 sources:
                                    var8_9 = 1;
                                    var2_2 = Uri.fromParts("mailto", var11_12.getString("uri_content"), null);
                                    break block11;
                                }
                                catch (Throwable var2_3) {
                                    break block12;
                                }
                            }
                            var7_8 = var4_5;
                            var6_7 = var2_2;
                            var1_1 = var8_9;
                            if (var3_4 != null) {
                                var7_8 = var4_5;
                                var6_7 = var2_2;
                                var1_1 = var8_9;
                                if (var3_4.moveToFirst()) {
                                    var7_8 = ContactsContract.Contacts.getLookupUri(var3_4.getLong(0), var3_4.getString(1));
                                    var6_7 = var2_2;
                                    var1_1 = var8_9;
                                }
                            }
                        }
                        break block13;
                    }
                    var7_8 = var4_5;
                    var6_7 = var2_2;
                    var1_1 = var8_9;
                    if (var3_4 != null) {
                        var7_8 = var4_5;
                        var6_7 = var2_2;
                        var1_1 = var8_9;
                        if (var3_4.moveToFirst()) {
                            var7_8 = ContactsContract.Contacts.getLookupUri(var3_4.getLong(0), var3_4.getString(1));
                            var6_7 = var2_2;
                            var1_1 = var8_9;
                        }
                    }
                    break block13;
                }
                if (var3_4 == null) throw var2_3;
                var3_4.close();
                throw var2_3;
            }
            if (var3_4 != null) {
                var3_4.close();
            }
            QuickContactBadge.access$002(QuickContactBadge.this, var7_8);
            QuickContactBadge.access$100(QuickContactBadge.this);
            if (var1_1 != 0 && QuickContactBadge.access$000(QuickContactBadge.this) != null) {
                var3_4 = QuickContactBadge.this.getContext();
                var2_2 = QuickContactBadge.this;
                ContactsContract.QuickContact.showQuickContact((Context)var3_4, (View)var2_2, QuickContactBadge.access$000((QuickContactBadge)var2_2), QuickContactBadge.this.mExcludeMimes, QuickContactBadge.access$200(QuickContactBadge.this));
                return;
            }
            if (var6_7 == null) return;
            var2_2 = new Intent("com.android.contacts.action.SHOW_OR_CREATE_CONTACT", (Uri)var6_7);
            var11_12.remove("uri_content");
            var2_2.putExtras(var11_12);
            QuickContactBadge.this.getContext().startActivity((Intent)var2_2);
        }
    }

}

