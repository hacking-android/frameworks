/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.MicroAlertController;
import java.lang.ref.WeakReference;

public class AlertController {
    public static final int MICRO = 1;
    private ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener(){

        @Override
        public void onClick(View object) {
            if ((object = object == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null ? Message.obtain(AlertController.this.mButtonPositiveMessage) : (object == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null ? Message.obtain(AlertController.this.mButtonNegativeMessage) : (object == AlertController.this.mButtonNeutral && AlertController.this.mButtonNeutralMessage != null ? Message.obtain(AlertController.this.mButtonNeutralMessage) : null))) != null) {
                ((Message)object).sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, AlertController.this.mDialogInterface).sendToTarget();
        }
    };
    private Button mButtonNegative;
    private Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    private Button mButtonNeutral;
    private Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    private Button mButtonPositive;
    private Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    private int mCheckedItem = -1;
    private final Context mContext;
    @UnsupportedAppUsage
    private View mCustomTitleView;
    private final DialogInterface mDialogInterface;
    @UnsupportedAppUsage
    private boolean mForceInverseBackground;
    private Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    private int mListItemLayout;
    private int mListLayout;
    protected ListView mListView;
    protected CharSequence mMessage;
    private Integer mMessageHyphenationFrequency;
    private MovementMethod mMessageMovementMethod;
    protected TextView mMessageView;
    private int mMultiChoiceItemLayout;
    protected ScrollView mScrollView;
    private boolean mShowTitle;
    private int mSingleChoiceItemLayout;
    @UnsupportedAppUsage
    private CharSequence mTitle;
    private TextView mTitleView;
    @UnsupportedAppUsage
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    protected final Window mWindow;

    @UnsupportedAppUsage
    protected AlertController(Context object, DialogInterface dialogInterface, Window window) {
        this.mContext = object;
        this.mDialogInterface = dialogInterface;
        this.mWindow = window;
        this.mHandler = new ButtonHandler(dialogInterface);
        object = ((Context)object).obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        this.mAlertDialogLayout = ((TypedArray)object).getResourceId(10, 17367080);
        this.mButtonPanelSideLayout = ((TypedArray)object).getResourceId(11, 0);
        this.mListLayout = ((TypedArray)object).getResourceId(15, 17367279);
        this.mMultiChoiceItemLayout = ((TypedArray)object).getResourceId(16, 17367059);
        this.mSingleChoiceItemLayout = ((TypedArray)object).getResourceId(21, 17367058);
        this.mListItemLayout = ((TypedArray)object).getResourceId(14, 17367057);
        this.mShowTitle = ((TypedArray)object).getBoolean(20, true);
        ((TypedArray)object).recycle();
        window.requestFeature(1);
    }

    static boolean canTextInput(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        view = (ViewGroup)view;
        int n = ((ViewGroup)view).getChildCount();
        while (n > 0) {
            int n2;
            n = n2 = n - 1;
            if (!AlertController.canTextInput(((ViewGroup)view).getChildAt(n2))) continue;
            return true;
        }
        return false;
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
        button = this.mWindow.findViewById(16909064);
        if (button != null) {
            button.setVisibility(0);
        }
        if ((button = this.mWindow.findViewById(16909292)) != null) {
            button.setVisibility(0);
        }
    }

    public static final AlertController create(Context context, DialogInterface dialogInterface, Window window) {
        TypedArray typedArray = context.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 16974371);
        int n = typedArray.getInt(12, 0);
        typedArray.recycle();
        if (n != 1) {
            return new AlertController(context, dialogInterface, window);
        }
        return new MicroAlertController(context, dialogInterface, window);
    }

    private static void manageScrollIndicators(View view, View view2, View view3) {
        int n;
        int n2 = 0;
        if (view2 != null) {
            n = view.canScrollVertically(-1) ? 0 : 4;
            view2.setVisibility(n);
        }
        if (view3 != null) {
            n = view.canScrollVertically(1) ? n2 : 4;
            view3.setVisibility(n);
        }
    }

    private ViewGroup resolvePanel(View view, View view2) {
        ViewParent viewParent;
        if (view == null) {
            view = view2;
            if (view2 instanceof ViewStub) {
                view = ((ViewStub)view2).inflate();
            }
            return (ViewGroup)view;
        }
        if (view2 != null && (viewParent = view2.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(view2);
        }
        view2 = view;
        if (view instanceof ViewStub) {
            view2 = ((ViewStub)view).inflate();
        }
        return (ViewGroup)view2;
    }

    private int selectContentView() {
        int n = this.mButtonPanelSideLayout;
        if (n == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint == 1) {
            return n;
        }
        return this.mAlertDialogLayout;
    }

    private void setBackground(TypedArray typedArray, View object, View view, View view2, View view3, boolean bl, boolean bl2, boolean bl3) {
        block16 : {
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            if (typedArray.getBoolean(17, true)) {
                n = 17303256;
                n2 = 17303270;
                n3 = 17303253;
                n4 = 17303250;
                n5 = 17303255;
                n6 = 17303269;
                n7 = 17303252;
                n8 = 17303249;
                n9 = 17303251;
            }
            int n10 = typedArray.getResourceId(5, n6);
            int n11 = typedArray.getResourceId(1, n2);
            int n12 = typedArray.getResourceId(6, n7);
            n7 = typedArray.getResourceId(2, n3);
            View[] arrview = new View[4];
            boolean[] arrbl = new boolean[4];
            n3 = 0;
            if (bl) {
                arrview[0] = object;
                arrbl[0] = false;
                n3 = 0 + 1;
            }
            if (view.getVisibility() == 8) {
                view = null;
            }
            arrview[n3] = view;
            bl = this.mListView != null;
            arrbl[n3] = bl;
            n3 = n2 = n3 + 1;
            if (bl2) {
                arrview[n2] = view2;
                arrbl[n2] = this.mForceInverseBackground;
                n3 = n2 + 1;
            }
            if (bl3) {
                arrview[n3] = view3;
                arrbl[n3] = true;
            }
            object = null;
            n2 = 0;
            bl = false;
            n3 = n7;
            n7 = n11;
            for (n6 = 0; n6 < arrview.length; ++n6) {
                view = arrview[n6];
                if (view == null) continue;
                if (object != null) {
                    if (n2 == 0) {
                        n2 = bl ? n10 : n7;
                        ((View)object).setBackgroundResource(n2);
                    } else {
                        n2 = bl ? n12 : n3;
                        ((View)object).setBackgroundResource(n2);
                    }
                    n2 = 1;
                }
                bl = arrbl[n6];
                object = view;
            }
            n3 = n;
            if (object != null) {
                if (n2 != 0) {
                    n3 = typedArray.getResourceId(7, n8);
                    n7 = typedArray.getResourceId(8, n9);
                    n2 = typedArray.getResourceId(3, n4);
                    if (bl) {
                        if (bl3) {
                            n3 = n7;
                        }
                    } else {
                        n3 = n2;
                    }
                    ((View)object).setBackgroundResource(n3);
                    n3 = n;
                } else {
                    n7 = typedArray.getResourceId(4, n5);
                    n3 = typedArray.getResourceId(0, n);
                    n = bl ? n7 : n3;
                    ((View)object).setBackgroundResource(n);
                }
            }
            if ((view = this.mListView) == null || (object = this.mAdapter) == null) break block16;
            ((ListView)view).setAdapter((ListAdapter)object);
            n = this.mCheckedItem;
            if (n > -1) {
                ((AbsListView)view).setItemChecked(n, true);
                ((AbsListView)view).setSelectionFromTop(n, typedArray.getDimensionPixelSize(19, 0));
            }
        }
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        View view = this.mView;
        boolean bl = false;
        if ((view = view != null ? this.mView : (this.mViewLayoutResId != 0 ? LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null)) != null) {
            bl = true;
        }
        if (!bl || !AlertController.canTextInput(view)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (bl) {
            FrameLayout frameLayout = (FrameLayout)this.mWindow.findViewById(16908331);
            frameLayout.addView(view, new ViewGroup.LayoutParams(-1, -1));
            if (this.mViewSpacingSpecified) {
                frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            }
            if (this.mListView != null) {
                ((LinearLayout.LayoutParams)viewGroup.getLayoutParams()).weight = 0.0f;
            }
        } else {
            viewGroup.setVisibility(8);
        }
    }

    private void setupView() {
        Object object = this.mWindow.findViewById(16909216);
        Object object2 = ((View)object).findViewById(16909482);
        Object t = ((View)object).findViewById(16908829);
        Object object3 = ((View)object).findViewById(16908778);
        object = (ViewGroup)((View)object).findViewById(16908853);
        this.setupCustomContent((ViewGroup)object);
        Object t2 = ((View)object).findViewById(16909482);
        Object object4 = ((View)object).findViewById(16908829);
        Object object5 = ((View)object).findViewById(16908778);
        object2 = this.resolvePanel((View)t2, (View)object2);
        object4 = this.resolvePanel((View)object4, (View)t);
        object5 = this.resolvePanel((View)object5, (View)object3);
        this.setupContent((ViewGroup)object4);
        this.setupButtons((ViewGroup)object5);
        this.setupTitle((ViewGroup)object2);
        boolean bl = ((View)object).getVisibility() != 8;
        boolean bl2 = object2 != null && ((View)object2).getVisibility() != 8;
        boolean bl3 = object5 != null && ((View)object5).getVisibility() != 8;
        if (!bl3) {
            if (object4 != null && (object3 = ((View)object4).findViewById(16909449)) != null) {
                ((View)object3).setVisibility(0);
            }
            this.mWindow.setCloseOnTouchOutsideIfNotSet(true);
        }
        if (bl2) {
            object3 = this.mScrollView;
            if (object3 != null) {
                ((ViewGroup)object3).setClipToPadding(true);
            }
            t = null;
            if (this.mMessage == null && this.mListView == null && !bl) {
                object3 = ((View)object2).findViewById(16909469);
            } else {
                if (!bl) {
                    t = ((View)object2).findViewById(16909468);
                }
                object3 = t;
                if (t == null) {
                    object3 = ((View)object2).findViewById(16909467);
                }
            }
            if (object3 != null) {
                ((View)object3).setVisibility(0);
            }
        } else if (object4 != null && (object3 = ((View)object4).findViewById(16909450)) != null) {
            ((View)object3).setVisibility(0);
        }
        object3 = this.mListView;
        if (object3 instanceof RecycleListView) {
            ((RecycleListView)object3).setHasDecor(bl2, bl3);
        }
        if (!bl) {
            object3 = this.mListView;
            if (object3 == null) {
                object3 = this.mScrollView;
            }
            if (object3 != null) {
                int n = bl2 ? 1 : 0;
                int n2 = bl3 ? 2 : 0;
                ((View)object3).setScrollIndicators(n | n2, 3);
            }
        }
        object3 = this.mContext.obtainStyledAttributes(null, R.styleable.AlertDialog, 16842845, 0);
        this.setBackground((TypedArray)object3, (View)object2, (View)object4, (View)object, (View)object5, bl2, bl, bl3);
        ((TypedArray)object3).recycle();
    }

    private static boolean shouldCenterSingleButton(Context object) {
        TypedValue typedValue = new TypedValue();
        object = ((Context)object).getTheme();
        boolean bl = true;
        ((Resources.Theme)object).resolveAttribute(17956877, typedValue, true);
        if (typedValue.data == 0) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public Button getButton(int n) {
        if (n != -3) {
            if (n != -2) {
                if (n != -1) {
                    return null;
                }
                return this.mButtonPositive;
            }
            return this.mButtonNegative;
        }
        return this.mButtonNeutral;
    }

    public int getIconAttributeResId(int n) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(n, typedValue, true);
        return typedValue.resourceId;
    }

    @UnsupportedAppUsage
    public ListView getListView() {
        return this.mListView;
    }

    @UnsupportedAppUsage
    public void installContent() {
        int n = this.selectContentView();
        this.mWindow.setContentView(n);
        this.setupView();
    }

    public void installContent(AlertParams alertParams) {
        alertParams.apply(this);
        this.installContent();
    }

    @UnsupportedAppUsage
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        ScrollView scrollView = this.mScrollView;
        boolean bl = scrollView != null && scrollView.executeKeyEvent(keyEvent);
        return bl;
    }

    @UnsupportedAppUsage
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        ScrollView scrollView = this.mScrollView;
        boolean bl = scrollView != null && scrollView.executeKeyEvent(keyEvent);
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void setButton(int n, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message) {
        Message message2 = message;
        if (message == null) {
            message2 = message;
            if (onClickListener != null) {
                message2 = this.mHandler.obtainMessage(n, onClickListener);
            }
        }
        if (n != -3) {
            if (n != -2) {
                if (n != -1) throw new IllegalArgumentException("Button does not exist");
                this.mButtonPositiveText = charSequence;
                this.mButtonPositiveMessage = message2;
                return;
            } else {
                this.mButtonNegativeText = charSequence;
                this.mButtonNegativeMessage = message2;
            }
            return;
        } else {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = message2;
        }
    }

    public void setButtonPanelLayoutHint(int n) {
        this.mButtonPanelLayoutHint = n;
    }

    @UnsupportedAppUsage
    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    @UnsupportedAppUsage
    public void setIcon(int n) {
        this.mIcon = null;
        this.mIconId = n;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (n != 0) {
                imageView.setVisibility(0);
                this.mIconView.setImageResource(this.mIconId);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    @UnsupportedAppUsage
    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        this.mIconId = 0;
        ImageView imageView = this.mIconView;
        if (imageView != null) {
            if (drawable2 != null) {
                imageView.setVisibility(0);
                this.mIconView.setImageDrawable(drawable2);
            } else {
                imageView.setVisibility(8);
            }
        }
    }

    public void setInverseBackgroundForced(boolean bl) {
        this.mForceInverseBackground = bl;
    }

    @UnsupportedAppUsage
    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setMessageHyphenationFrequency(int n) {
        this.mMessageHyphenationFrequency = n;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setHyphenationFrequency(n);
        }
    }

    public void setMessageMovementMethod(MovementMethod movementMethod) {
        this.mMessageMovementMethod = movementMethod;
        TextView textView = this.mMessageView;
        if (textView != null) {
            textView.setMovementMethod(movementMethod);
        }
    }

    @UnsupportedAppUsage
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void setView(int n) {
        this.mView = null;
        this.mViewLayoutResId = n;
        this.mViewSpacingSpecified = false;
    }

    @UnsupportedAppUsage
    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int n, int n2, int n3, int n4) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = n;
        this.mViewSpacingTop = n2;
        this.mViewSpacingRight = n3;
        this.mViewSpacingBottom = n4;
    }

    protected void setupButtons(ViewGroup viewGroup) {
        int n = 0;
        this.mButtonPositive = (Button)viewGroup.findViewById(16908313);
        this.mButtonPositive.setOnClickListener(this.mButtonHandler);
        boolean bl = TextUtils.isEmpty(this.mButtonPositiveText);
        boolean bl2 = false;
        if (bl) {
            this.mButtonPositive.setVisibility(8);
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            this.mButtonPositive.setVisibility(0);
            n = false | true;
        }
        this.mButtonNegative = (Button)viewGroup.findViewById(16908314);
        this.mButtonNegative.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNegativeText)) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            this.mButtonNegative.setVisibility(0);
            n |= 2;
        }
        this.mButtonNeutral = (Button)viewGroup.findViewById(16908315);
        this.mButtonNeutral.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty(this.mButtonNeutralText)) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            this.mButtonNeutral.setVisibility(0);
            n |= 4;
        }
        if (AlertController.shouldCenterSingleButton(this.mContext)) {
            if (n == 1) {
                this.centerButton(this.mButtonPositive);
            } else if (n == 2) {
                this.centerButton(this.mButtonNegative);
            } else if (n == 4) {
                this.centerButton(this.mButtonNeutral);
            }
        }
        if (n != 0) {
            bl2 = true;
        }
        if (!bl2) {
            viewGroup.setVisibility(8);
        }
    }

    protected void setupContent(ViewGroup object) {
        this.mScrollView = (ScrollView)((View)object).findViewById(16909311);
        this.mScrollView.setFocusable(false);
        TextView textView = this.mMessageView = (TextView)((View)object).findViewById(16908299);
        if (textView == null) {
            return;
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            textView.setText(charSequence);
            object = this.mMessageMovementMethod;
            if (object != null) {
                this.mMessageView.setMovementMethod((MovementMethod)object);
            }
            if ((object = this.mMessageHyphenationFrequency) != null) {
                this.mMessageView.setHyphenationFrequency((Integer)object);
            }
        } else {
            textView.setVisibility(8);
            this.mScrollView.removeView(this.mMessageView);
            if (this.mListView != null) {
                object = (ViewGroup)this.mScrollView.getParent();
                int n = ((ViewGroup)object).indexOfChild(this.mScrollView);
                ((ViewGroup)object).removeViewAt(n);
                ((ViewGroup)object).addView((View)this.mListView, n, new ViewGroup.LayoutParams(-1, -1));
            } else {
                ((View)object).setVisibility(8);
            }
        }
    }

    protected void setupTitle(ViewGroup object) {
        if (this.mCustomTitleView != null && this.mShowTitle) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
            ((ViewGroup)object).addView(this.mCustomTitleView, 0, layoutParams);
            ((View)this.mWindow.findViewById(16909472)).setVisibility(8);
        } else {
            this.mIconView = (ImageView)this.mWindow.findViewById(16908294);
            if (TextUtils.isEmpty(this.mTitle) ^ true && this.mShowTitle) {
                this.mTitleView = (TextView)this.mWindow.findViewById(16908711);
                this.mTitleView.setText(this.mTitle);
                int n = this.mIconId;
                if (n != 0) {
                    this.mIconView.setImageResource(n);
                } else {
                    object = this.mIcon;
                    if (object != null) {
                        this.mIconView.setImageDrawable((Drawable)object);
                    } else {
                        this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
                        this.mIconView.setVisibility(8);
                    }
                }
            } else {
                ((View)this.mWindow.findViewById(16909472)).setVisibility(8);
                this.mIconView.setVisibility(8);
                ((View)object).setVisibility(8);
            }
        }
    }

    public static class AlertParams {
        @UnsupportedAppUsage
        public ListAdapter mAdapter;
        @UnsupportedAppUsage
        public boolean mCancelable;
        @UnsupportedAppUsage
        public int mCheckedItem = -1;
        @UnsupportedAppUsage
        public boolean[] mCheckedItems;
        @UnsupportedAppUsage
        public final Context mContext;
        @UnsupportedAppUsage
        public Cursor mCursor;
        @UnsupportedAppUsage
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        @UnsupportedAppUsage
        public Drawable mIcon;
        public int mIconAttrId = 0;
        @UnsupportedAppUsage
        public int mIconId = 0;
        @UnsupportedAppUsage
        public final LayoutInflater mInflater;
        @UnsupportedAppUsage
        public String mIsCheckedColumn;
        @UnsupportedAppUsage
        public boolean mIsMultiChoice;
        @UnsupportedAppUsage
        public boolean mIsSingleChoice;
        @UnsupportedAppUsage
        public CharSequence[] mItems;
        @UnsupportedAppUsage
        public String mLabelColumn;
        @UnsupportedAppUsage
        public CharSequence mMessage;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mNegativeButtonListener;
        @UnsupportedAppUsage
        public CharSequence mNegativeButtonText;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mNeutralButtonListener;
        @UnsupportedAppUsage
        public CharSequence mNeutralButtonText;
        @UnsupportedAppUsage
        public DialogInterface.OnCancelListener mOnCancelListener;
        @UnsupportedAppUsage
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mOnClickListener;
        @UnsupportedAppUsage
        public DialogInterface.OnDismissListener mOnDismissListener;
        @UnsupportedAppUsage
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        @UnsupportedAppUsage
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        @UnsupportedAppUsage
        public DialogInterface.OnClickListener mPositiveButtonListener;
        @UnsupportedAppUsage
        public CharSequence mPositiveButtonText;
        public boolean mRecycleOnMeasure = true;
        @UnsupportedAppUsage
        public CharSequence mTitle;
        @UnsupportedAppUsage
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        @UnsupportedAppUsage
        public AlertParams(Context context) {
            this.mContext = context;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        private void createListView(final AlertController alertController) {
            Object object;
            final RecycleListView recycleListView = (RecycleListView)this.mInflater.inflate(alertController.mListLayout, null);
            if (this.mIsMultiChoice) {
                object = this.mCursor;
                object = object == null ? new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, 16908308, this.mItems){

                    @Override
                    public View getView(int n, View view, ViewGroup viewGroup) {
                        view = super.getView(n, view, viewGroup);
                        if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[n]) {
                            recycleListView.setItemChecked(n, true);
                        }
                        return view;
                    }
                } : new CursorAdapter(this.mContext, (Cursor)object, false){
                    private final int mIsCheckedIndex;
                    private final int mLabelIndex;
                    {
                        super(context, cursor, bl);
                        AlertParams.this = this.getCursor();
                        this.mLabelIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                        this.mIsCheckedIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        ((CheckedTextView)view.findViewById(16908308)).setText(cursor.getString(this.mLabelIndex));
                        view = recycleListView;
                        int n = cursor.getPosition();
                        int n2 = cursor.getInt(this.mIsCheckedIndex);
                        boolean bl = true;
                        if (n2 != 1) {
                            bl = false;
                        }
                        ((AbsListView)view).setItemChecked(n, bl);
                    }

                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                    }
                };
            } else {
                int n = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
                object = this.mCursor;
                object = object != null ? new SimpleCursorAdapter(this.mContext, n, (Cursor)object, new String[]{this.mLabelColumn}, new int[]{16908308}) : (this.mAdapter != null ? this.mAdapter : new CheckedItemAdapter(this.mContext, n, 16908308, this.mItems));
            }
            OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
            if (onPrepareListViewListener != null) {
                onPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.mAdapter = (ListAdapter)object;
            alertController.mCheckedItem = this.mCheckedItem;
            if (this.mOnClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                        AlertParams.this.mOnClickListener.onClick(alertController.mDialogInterface, n);
                        if (!AlertParams.this.mIsSingleChoice) {
                            alertController.mDialogInterface.dismiss();
                        }
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[n] = recycleListView.isItemChecked(n);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(alertController.mDialogInterface, n, recycleListView.isItemChecked(n));
                    }
                });
            }
            object = this.mOnItemSelectedListener;
            if (object != null) {
                recycleListView.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)object);
            }
            if (this.mIsSingleChoice) {
                recycleListView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                recycleListView.setChoiceMode(2);
            }
            recycleListView.mRecycleOnMeasure = this.mRecycleOnMeasure;
            alertController.mListView = recycleListView;
        }

        @UnsupportedAppUsage
        public void apply(AlertController alertController) {
            int n;
            Object object = this.mCustomTitleView;
            if (object != null) {
                alertController.setCustomTitle((View)object);
            } else {
                object = this.mTitle;
                if (object != null) {
                    alertController.setTitle((CharSequence)object);
                }
                if ((object = this.mIcon) != null) {
                    alertController.setIcon((Drawable)object);
                }
                if ((n = this.mIconId) != 0) {
                    alertController.setIcon(n);
                }
                if ((n = this.mIconAttrId) != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(n));
                }
            }
            object = this.mMessage;
            if (object != null) {
                alertController.setMessage((CharSequence)object);
            }
            if ((object = this.mPositiveButtonText) != null) {
                alertController.setButton(-1, (CharSequence)object, this.mPositiveButtonListener, null);
            }
            if ((object = this.mNegativeButtonText) != null) {
                alertController.setButton(-2, (CharSequence)object, this.mNegativeButtonListener, null);
            }
            if ((object = this.mNeutralButtonText) != null) {
                alertController.setButton(-3, (CharSequence)object, this.mNeutralButtonListener, null);
            }
            if (this.mForceInverseBackground) {
                alertController.setInverseBackgroundForced(true);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                this.createListView(alertController);
            }
            if ((object = this.mView) != null) {
                if (this.mViewSpacingSpecified) {
                    alertController.setView((View)object, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                } else {
                    alertController.setView((View)object);
                }
            } else {
                n = this.mViewLayoutResId;
                if (n != 0) {
                    alertController.setView(n);
                }
            }
        }

        public static interface OnPrepareListViewListener {
            public void onPrepareListView(ListView var1);
        }

    }

    private static final class ButtonHandler
    extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<DialogInterface>(dialogInterface);
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != -3 && n != -2 && n != -1) {
                if (n == 1) {
                    ((DialogInterface)message.obj).dismiss();
                }
            } else {
                ((DialogInterface.OnClickListener)message.obj).onClick((DialogInterface)this.mDialog.get(), message.what);
            }
        }
    }

    private static class CheckedItemAdapter
    extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int n, int n2, CharSequence[] arrcharSequence) {
            super(context, n, n2, arrcharSequence);
        }

        @Override
        public long getItemId(int n) {
            return n;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    public static class RecycleListView
    extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;
        boolean mRecycleOnMeasure = true;

        @UnsupportedAppUsage
        public RecycleListView(Context context) {
            this(context, null);
        }

        @UnsupportedAppUsage
        public RecycleListView(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = ((TypedArray)object).getDimensionPixelOffset(0, -1);
            this.mPaddingTopNoTitle = ((TypedArray)object).getDimensionPixelOffset(1, -1);
        }

        @Override
        protected boolean recycleOnMeasure() {
            return this.mRecycleOnMeasure;
        }

        public void setHasDecor(boolean bl, boolean bl2) {
            if (!bl2 || !bl) {
                int n = this.getPaddingLeft();
                int n2 = bl ? this.getPaddingTop() : this.mPaddingTopNoTitle;
                int n3 = this.getPaddingRight();
                int n4 = bl2 ? this.getPaddingBottom() : this.mPaddingBottomNoButtons;
                this.setPadding(n, n2, n3, n4);
            }
        }
    }

}

