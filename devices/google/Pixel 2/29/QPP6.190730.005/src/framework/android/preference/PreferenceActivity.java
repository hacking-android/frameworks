/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.preference;

import android.animation.LayoutTransition;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

@Deprecated
public abstract class PreferenceActivity
extends ListActivity
implements PreferenceManager.OnPreferenceTreeClickListener,
PreferenceFragment.OnPreferenceStartFragmentCallback {
    private static final String BACK_STACK_PREFS = ":android:prefs";
    private static final String CUR_HEADER_TAG = ":android:cur_header";
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1L;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final String PREFERENCES_TAG = ":android:preferences";
    private static final String TAG = "PreferenceActivity";
    private CharSequence mActivityTitle;
    private Header mCurHeader;
    private FragmentBreadCrumbs mFragmentBreadCrumbs;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                if (n == 2) {
                    Object object2 = new ArrayList<Header>(PreferenceActivity.this.mHeaders);
                    PreferenceActivity.this.mHeaders.clear();
                    object = PreferenceActivity.this;
                    ((PreferenceActivity)object).onBuildHeaders(((PreferenceActivity)object).mHeaders);
                    if (PreferenceActivity.this.mAdapter instanceof BaseAdapter) {
                        ((BaseAdapter)PreferenceActivity.this.mAdapter).notifyDataSetChanged();
                    }
                    if ((object = PreferenceActivity.this.onGetNewHeader()) != null && ((Header)object).fragment != null) {
                        if ((object2 = PreferenceActivity.this.findBestMatchingHeader((Header)object, (ArrayList<Header>)object2)) == null || PreferenceActivity.this.mCurHeader != object2) {
                            PreferenceActivity.this.switchToHeader((Header)object);
                        }
                    } else if (PreferenceActivity.this.mCurHeader != null) {
                        object = PreferenceActivity.this;
                        if ((object = ((PreferenceActivity)object).findBestMatchingHeader(((PreferenceActivity)object).mCurHeader, PreferenceActivity.this.mHeaders)) != null) {
                            PreferenceActivity.this.setSelectedHeader((Header)object);
                        }
                    }
                }
            } else {
                PreferenceActivity.this.bindPreferences();
            }
        }
    };
    private final ArrayList<Header> mHeaders = new ArrayList();
    private ViewGroup mHeadersContainer;
    private FrameLayout mListFooter;
    private Button mNextButton;
    private int mPreferenceHeaderItemResId = 0;
    private boolean mPreferenceHeaderRemoveEmptyIcon = false;
    @UnsupportedAppUsage
    private PreferenceManager mPreferenceManager;
    @UnsupportedAppUsage
    private ViewGroup mPrefsContainer;
    private Bundle mSavedInstanceState;
    private boolean mSinglePane;

    private void bindPreferences() {
        Object object = this.getPreferenceScreen();
        if (object != null) {
            ((PreferenceScreen)object).bind(this.getListView());
            object = this.mSavedInstanceState;
            if (object != null) {
                super.onRestoreInstanceState((Bundle)object);
                this.mSavedInstanceState = null;
            }
        }
    }

    @UnsupportedAppUsage
    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    @UnsupportedAppUsage
    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            if (this.mAdapter == null) {
                throw new RuntimeException("This should be called after super.onCreate.");
            }
            throw new RuntimeException("Modern two-pane PreferenceActivity requires use of a PreferenceFragment");
        }
    }

    private void switchToHeaderInner(String object, Bundle object2) {
        this.getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        if (this.isValidFragment((String)object)) {
            object2 = Fragment.instantiate(this, (String)object, (Bundle)object2);
            object = this.getFragmentManager().beginTransaction();
            int n = this.mSinglePane ? 0 : 4099;
            ((FragmentTransaction)object).setTransition(n);
            ((FragmentTransaction)object).replace(16909249, (Fragment)object2);
            ((FragmentTransaction)object).commitAllowingStateLoss();
            if (this.mSinglePane && this.mPrefsContainer.getVisibility() == 8) {
                this.mPrefsContainer.setVisibility(0);
                this.mHeadersContainer.setVisibility(8);
            }
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Invalid fragment for this activity: ");
        ((StringBuilder)object2).append((String)object);
        throw new IllegalArgumentException(((StringBuilder)object2).toString());
    }

    @Deprecated
    public void addPreferencesFromIntent(Intent intent) {
        this.requirePreferenceManager();
        this.setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, this.getPreferenceScreen()));
    }

    @Deprecated
    public void addPreferencesFromResource(int n) {
        this.requirePreferenceManager();
        this.setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this, n, this.getPreferenceScreen()));
    }

    Header findBestMatchingHeader(Header header, ArrayList<Header> object) {
        int n;
        int n2;
        ArrayList<Header> arrayList = new ArrayList<Header>();
        for (n2 = 0; n2 < ((ArrayList)object).size(); ++n2) {
            Header header2 = ((ArrayList)object).get(n2);
            if (header != header2 && (header.id == -1L || header.id != header2.id)) {
                if (header.fragment != null) {
                    if (!header.fragment.equals(header2.fragment)) continue;
                    arrayList.add(header2);
                    continue;
                }
                if (header.intent != null) {
                    if (!header.intent.equals(header2.intent)) continue;
                    arrayList.add(header2);
                    continue;
                }
                if (header.title == null || !header.title.equals(header2.title)) continue;
                arrayList.add(header2);
                continue;
            }
            arrayList.clear();
            arrayList.add(header2);
            break;
        }
        if ((n = arrayList.size()) == 1) {
            return (Header)arrayList.get(0);
        }
        if (n > 1) {
            for (n2 = 0; n2 < n; ++n2) {
                object = (Header)arrayList.get(n2);
                if (header.fragmentArguments != null && header.fragmentArguments.equals(((Header)object).fragmentArguments)) {
                    return object;
                }
                if (header.extras != null && header.extras.equals(((Header)object).extras)) {
                    return object;
                }
                if (header.title == null || !header.title.equals(((Header)object).title)) continue;
                return object;
            }
        }
        return null;
    }

    @Deprecated
    public Preference findPreference(CharSequence charSequence) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return preferenceManager.findPreference(charSequence);
    }

    public void finishPreferencePanel(Fragment fragment, int n, Intent intent) {
        this.onBackPressed();
        if (fragment != null && fragment.getTargetFragment() != null) {
            fragment.getTargetFragment().onActivityResult(fragment.getTargetRequestCode(), n, intent);
        }
    }

    @UnsupportedAppUsage
    public List<Header> getHeaders() {
        return this.mHeaders;
    }

    protected Button getNextButton() {
        return this.mNextButton;
    }

    @Deprecated
    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    @Deprecated
    public PreferenceScreen getPreferenceScreen() {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            return preferenceManager.getPreferenceScreen();
        }
        return null;
    }

    public boolean hasHeaders() {
        ViewGroup viewGroup = this.mHeadersContainer;
        boolean bl = viewGroup != null && viewGroup.getVisibility() == 0;
        return bl;
    }

    protected boolean hasNextButton() {
        boolean bl = this.mNextButton != null;
        return bl;
    }

    public void invalidateHeaders() {
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public boolean isMultiPane() {
        return this.mSinglePane ^ true;
    }

    protected boolean isValidFragment(String string2) {
        if (this.getApplicationInfo().targetSdkVersion < 19) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! ");
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append(" has not checked if fragment ");
        stringBuilder.append(string2);
        stringBuilder.append(" is valid.");
        throw new RuntimeException(stringBuilder.toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void loadHeadersFromResource(int var1_1, List<Header> var2_2) {
        block37 : {
            block40 : {
                var3_18 = null;
                var4_19 = null;
                var5_20 = null;
                var6_21 = null;
                var7_22 = null;
                var8_23 /* !! */  = null;
                var9_24 = this.getResources();
                var7_22 = var8_23 /* !! */ ;
                var5_20 = var9_24.getXml(var1_1);
                var7_22 = var5_20;
                var3_18 = var5_20;
                var4_19 = var5_20;
                var8_23 /* !! */  = Xml.asAttributeSet(var5_20);
                do {
                    var7_22 = var5_20;
                    var3_18 = var5_20;
                    var4_19 = var5_20;
                } while ((var1_1 = var5_20.next()) != 1 && var1_1 != 2);
                var7_22 = var5_20;
                var3_18 = var5_20;
                var4_19 = var5_20;
                var6_21 = var5_20.getName();
                var7_22 = var5_20;
                var3_18 = var5_20;
                var4_19 = var5_20;
                if (!"preference-headers".equals(var6_21)) break block40;
                var6_21 = null;
                var7_22 = var5_20;
                var3_18 = var5_20;
                var4_19 = var5_20;
                var1_1 = var5_20.getDepth();
                ** GOTO lbl75
            }
            var7_22 = var5_20;
            try {
                block39 : {
                    block38 : {
                        var7_22 = var5_20;
                        var7_22 = var5_20;
                        var3_18 = new StringBuilder();
                        var7_22 = var5_20;
                        var3_18.append("XML document must start with <preference-headers> tag; found");
                        var7_22 = var5_20;
                        var3_18.append((String)var6_21);
                        var7_22 = var5_20;
                        var3_18.append(" at ");
                        var7_22 = var5_20;
                        var3_18.append(var5_20.getPositionDescription());
                        var7_22 = var5_20;
                        var2_2 = new RuntimeException(var3_18.toString());
                        var7_22 = var5_20;
                        throw var2_2;
                        catch (Throwable var2_8) {
                            var5_20 = var7_22;
                            break block37;
                        }
                        catch (IOException var2_9) {
                            var5_20 = var3_18;
                            break block38;
                        }
                        catch (XmlPullParserException var2_10) {
                            var5_20 = var4_19;
                            break block39;
                        }
                        catch (Throwable var2_11) {
                            break block37;
                        }
                        catch (IOException var2_13) {
                            var5_20 = var6_21;
                            break block38;
                        }
                        catch (XmlPullParserException var2_15) {
                            var5_20 = var7_22;
                            break block39;
                        }
lbl75: // 1 sources:
                        do {
                            block41 : {
                                var7_22 = var5_20;
                                var3_18 = var5_20;
                                var4_19 = var5_20;
                                var10_25 = var5_20.next();
                                if (var10_25 == 1) ** GOTO lbl143
                                if (var10_25 != 3) break block41;
                                var7_22 = var5_20;
                                var3_18 = var5_20;
                                var4_19 = var5_20;
                                if (var5_20.getDepth() <= var1_1) ** GOTO lbl143
                            }
                            if (var10_25 == 3 || var10_25 == 4) continue;
                            var7_22 = var5_20;
                            var3_18 = var5_20;
                            var4_19 = var5_20;
                            if (!"header".equals(var5_20.getName())) ** GOTO lbl140
                            var7_22 = var5_20;
                            var3_18 = var5_20;
                            var4_19 = var5_20;
                            var7_22 = var5_20;
                            var3_18 = var5_20;
                            var4_19 = var5_20;
                            var9_24 = new Header();
                            var7_22 = var5_20;
                            var3_18 = var5_20;
                            var4_19 = var5_20;
                            var11_26 = R.styleable.PreferenceHeader;
                            var7_22 = this.obtainStyledAttributes(var8_23 /* !! */ , var11_26);
                            var9_24.id = var7_22.getResourceId(1, -1);
                            var3_18 = var7_22.peekValue(2);
                            if (var3_18 != null && var3_18.type == 3) {
                                if (var3_18.resourceId != 0) {
                                    var9_24.titleRes = var3_18.resourceId;
                                } else {
                                    var9_24.title = var3_18.string;
                                }
                            }
                            if ((var3_18 = var7_22.peekValue(3)) != null && var3_18.type == 3) {
                                if (var3_18.resourceId != 0) {
                                    var9_24.summaryRes = var3_18.resourceId;
                                } else {
                                    var9_24.summary = var3_18.string;
                                }
                            }
                            if ((var3_18 = var7_22.peekValue(5)) != null && var3_18.type == 3) {
                                if (var3_18.resourceId != 0) {
                                    var9_24.breadCrumbTitleRes = var3_18.resourceId;
                                } else {
                                    var9_24.breadCrumbTitle = var3_18.string;
                                }
                            }
                            if ((var3_18 = var7_22.peekValue(6)) != null && var3_18.type == 3) {
                                if (var3_18.resourceId != 0) {
                                    var9_24.breadCrumbShortTitleRes = var3_18.resourceId;
                                } else {
                                    var9_24.breadCrumbShortTitle = var3_18.string;
                                }
                            }
                            var9_24.iconRes = var7_22.getResourceId(0, 0);
                            var9_24.fragment = var7_22.getString(4);
                            var7_22.recycle();
                            if (var6_21 == null) {
                                var6_21 = new Bundle();
                            }
                            var10_25 = var5_20.getDepth();
                            ** GOTO lbl145
                            {
                                catch (Throwable var2_3) {
                                    break block37;
                                }
                                catch (IOException var2_4) {
                                    break block38;
                                }
                                catch (XmlPullParserException var2_5) {
                                    break block39;
                                }
lbl140: // 1 sources:
                                var7_22 = var5_20;
                                XmlUtils.skipCurrentTag(var5_20);
                                continue;
lbl143: // 2 sources:
                                var5_20.close();
                                return;
lbl145: // 5 sources:
                                while ((var12_27 = var5_20.next()) != 1 && (var12_27 != 3 || var5_20.getDepth() > var10_25)) {
                                    if (var12_27 == 3 || var12_27 == 4) continue;
                                    var7_22 = var5_20.getName();
                                    if (var7_22.equals("extra")) {
                                        this.getResources().parseBundleExtra("extra", var8_23 /* !! */ , (Bundle)var6_21);
                                        XmlUtils.skipCurrentTag(var5_20);
                                        continue;
                                    }
                                    if (var7_22.equals("intent")) {
                                        var9_24.intent = Intent.parseIntent(this.getResources(), var5_20, var8_23 /* !! */ );
                                        continue;
                                    }
                                    XmlUtils.skipCurrentTag(var5_20);
                                }
                                var3_18 = var6_21;
                                if (var6_21.size() > 0) {
                                    var9_24.fragmentArguments = var6_21;
                                    var3_18 = null;
                                }
                                var7_22 = var5_20;
                                ** try [egrp 3[TRYBLOCK] [103, 102, 104 : 718->760)] { 
lbl163: // 1 sources:
                                var2_2.add(var9_24);
                                var6_21 = var3_18;
                                continue;
                            }
                            break;
                        } while (true);
lbl167: // 2 sources:
                        catch (IOException var2_6) {
                            break block38;
                        }
lbl169: // 2 sources:
                        catch (XmlPullParserException var2_7) {
                            break block39;
                        }
                    }
                    var7_22 = var5_20;
                    var7_22 = var5_20;
                    var6_21 = new RuntimeException("Error parsing headers", (Throwable)var2_14);
                    var7_22 = var5_20;
                    throw var6_21;
                }
                var7_22 = var5_20;
                var7_22 = var5_20;
                var6_21 = new RuntimeException("Error parsing headers", (Throwable)var2_16);
                var7_22 = var5_20;
                throw var6_21;
            }
lbl183: // 2 sources:
            catch (Throwable var2_17) {
                var5_20 = var7_22;
            }
        }
        if (var5_20 == null) throw var2_12;
        var5_20.close();
        throw var2_12;
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchActivityResult(n, n2, intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.mCurHeader != null && this.mSinglePane && this.getFragmentManager().getBackStackEntryCount() == 0 && this.getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) == null) {
            this.mCurHeader = null;
            this.mPrefsContainer.setVisibility(8);
            this.mHeadersContainer.setVisibility(0);
            CharSequence charSequence = this.mActivityTitle;
            if (charSequence != null) {
                this.showBreadCrumbs(charSequence, null);
            }
            this.getListView().clearChoices();
        } else {
            super.onBackPressed();
        }
    }

    public void onBuildHeaders(List<Header> list) {
    }

    public Intent onBuildStartFragmentIntent(String string2, Bundle bundle, int n, int n2) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClass(this, this.getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, string2);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, bundle);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, n);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, n2);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (this.mPreferenceManager != null) {
            this.postBindPreferences();
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        Object object2 = this.obtainStyledAttributes(null, R.styleable.PreferenceActivity, 17957038, 0);
        int n = ((TypedArray)object2).getResourceId(0, 17367241);
        this.mPreferenceHeaderItemResId = ((TypedArray)object2).getResourceId(1, 17367235);
        this.mPreferenceHeaderRemoveEmptyIcon = ((TypedArray)object2).getBoolean(2, false);
        ((TypedArray)object2).recycle();
        this.setContentView(n);
        this.mListFooter = (FrameLayout)this.findViewById(16909072);
        this.mPrefsContainer = (ViewGroup)this.findViewById(16909251);
        this.mHeadersContainer = (ViewGroup)this.findViewById(16908981);
        boolean bl = this.onIsHidingHeaders() || !this.onIsMultiPane();
        this.mSinglePane = bl;
        object2 = this.getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        ArrayList arrayList = this.getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        int n2 = this.getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
        int n3 = this.getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
        this.mActivityTitle = this.getTitle();
        if (object != null) {
            arrayList = ((Bundle)object).getParcelableArrayList(HEADERS_TAG);
            if (arrayList != null) {
                this.mHeaders.addAll(arrayList);
                n = ((BaseBundle)object).getInt(CUR_HEADER_TAG, -1);
                if (n >= 0 && n < this.mHeaders.size()) {
                    this.setSelectedHeader(this.mHeaders.get(n));
                } else if (!this.mSinglePane && object2 == null) {
                    this.switchToHeader(this.onGetInitialHeader());
                }
            } else {
                this.showBreadCrumbs(this.getTitle(), null);
            }
        } else {
            if (!this.onIsHidingHeaders()) {
                this.onBuildHeaders(this.mHeaders);
            }
            if (object2 != null) {
                this.switchToHeader((String)object2, (Bundle)((Object)arrayList));
            } else if (!this.mSinglePane && this.mHeaders.size() > 0) {
                this.switchToHeader(this.onGetInitialHeader());
            }
        }
        if (this.mHeaders.size() > 0) {
            this.setListAdapter(new HeaderAdapter(this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon));
            if (!this.mSinglePane) {
                this.getListView().setChoiceMode(1);
            }
        }
        if (this.mSinglePane && object2 != null && n2 != 0) {
            arrayList = this.getText(n2);
            object = n3 != 0 ? this.getText(n3) : null;
            this.showBreadCrumbs((CharSequence)((Object)arrayList), (CharSequence)object);
        }
        if (this.mHeaders.size() == 0 && object2 == null) {
            this.setContentView(17367243);
            this.mListFooter = (FrameLayout)this.findViewById(16909072);
            this.mPrefsContainer = (ViewGroup)this.findViewById(16909249);
            this.mPreferenceManager = new PreferenceManager(this, 100);
            this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
            this.mHeadersContainer = null;
        } else if (this.mSinglePane) {
            if (object2 == null && this.mCurHeader == null) {
                this.mPrefsContainer.setVisibility(8);
            } else {
                this.mHeadersContainer.setVisibility(8);
            }
            ((ViewGroup)this.findViewById(16909250)).setLayoutTransition(new LayoutTransition());
        } else if (this.mHeaders.size() > 0 && (object = this.mCurHeader) != null) {
            this.setSelectedHeader((Header)object);
        }
        arrayList = this.getIntent();
        if (((Intent)((Object)arrayList)).getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            String string2;
            ((View)this.findViewById(16908780)).setVisibility(0);
            object = (Button)this.findViewById(16908754);
            ((View)object).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    PreferenceActivity.this.setResult(0);
                    PreferenceActivity.this.finish();
                }
            });
            object2 = (Button)this.findViewById(16909369);
            ((View)object2).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            this.mNextButton = (Button)this.findViewById(16909145);
            this.mNextButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            if (((Intent)((Object)arrayList)).hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                string2 = ((Intent)((Object)arrayList)).getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (TextUtils.isEmpty(string2)) {
                    this.mNextButton.setVisibility(8);
                } else {
                    this.mNextButton.setText(string2);
                }
            }
            if (((Intent)((Object)arrayList)).hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                string2 = ((Intent)((Object)arrayList)).getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (TextUtils.isEmpty(string2)) {
                    ((View)object).setVisibility(8);
                } else {
                    ((TextView)object).setText(string2);
                }
            }
            if (((Intent)((Object)arrayList)).getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                ((View)object2).setVisibility(0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        super.onDestroy();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchActivityDestroy();
        }
    }

    public Header onGetInitialHeader() {
        for (int i = 0; i < this.mHeaders.size(); ++i) {
            Header header = this.mHeaders.get(i);
            if (header.fragment == null) continue;
            return header;
        }
        throw new IllegalStateException("Must have at least one header with a fragment");
    }

    public Header onGetNewHeader() {
        return null;
    }

    public void onHeaderClick(Header header, int n) {
        if (header.fragment != null) {
            this.switchToHeader(header);
        } else if (header.intent != null) {
            this.startActivity(header.intent);
        }
    }

    public boolean onIsHidingHeaders() {
        return this.getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public boolean onIsMultiPane() {
        return this.getResources().getBoolean(17891612);
    }

    @Override
    protected void onListItemClick(ListView object, View view, int n, long l) {
        if (!this.isResumed()) {
            return;
        }
        super.onListItemClick((ListView)object, view, n, l);
        if (this.mAdapter != null && (object = this.mAdapter.getItem(n)) instanceof Header) {
            this.onHeaderClick((Header)object, n);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchNewIntent(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment, Preference preference) {
        this.startPreferencePanel(preference.getFragment(), preference.getExtras(), preference.getTitleRes(), preference.getTitle(), null, 0);
        return true;
    }

    @Deprecated
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle parcelable) {
        PreferenceScreen preferenceScreen;
        Bundle bundle;
        if (this.mPreferenceManager != null && (bundle = parcelable.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = this.getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(bundle);
            this.mSavedInstanceState = parcelable;
            return;
        }
        super.onRestoreInstanceState((Bundle)parcelable);
        if (!this.mSinglePane && (parcelable = this.mCurHeader) != null) {
            this.setSelectedHeader((Header)parcelable);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        PreferenceScreen preferenceScreen;
        Parcelable parcelable;
        super.onSaveInstanceState(bundle);
        if (this.mHeaders.size() > 0) {
            int n;
            bundle.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            parcelable = this.mCurHeader;
            if (parcelable != null && (n = this.mHeaders.indexOf(parcelable)) >= 0) {
                bundle.putInt(CUR_HEADER_TAG, n);
            }
        }
        if (this.mPreferenceManager != null && (preferenceScreen = this.getPreferenceScreen()) != null) {
            parcelable = new Bundle();
            preferenceScreen.saveHierarchyState((Bundle)parcelable);
            bundle.putBundle(PREFERENCES_TAG, (Bundle)parcelable);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager != null) {
            preferenceManager.dispatchActivityStop();
        }
    }

    public void setListFooter(View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, new FrameLayout.LayoutParams(-1, -2));
    }

    public void setParentTitle(CharSequence charSequence, CharSequence charSequence2, View.OnClickListener onClickListener) {
        FragmentBreadCrumbs fragmentBreadCrumbs = this.mFragmentBreadCrumbs;
        if (fragmentBreadCrumbs != null) {
            fragmentBreadCrumbs.setParentTitle(charSequence, charSequence2, onClickListener);
        }
    }

    @Deprecated
    public void setPreferenceScreen(PreferenceScreen object) {
        this.requirePreferenceManager();
        if (this.mPreferenceManager.setPreferences((PreferenceScreen)object) && object != null) {
            this.postBindPreferences();
            object = this.getPreferenceScreen().getTitle();
            if (object != null) {
                this.setTitle((CharSequence)object);
            }
        }
    }

    void setSelectedHeader(Header header) {
        this.mCurHeader = header;
        int n = this.mHeaders.indexOf(header);
        if (n >= 0) {
            this.getListView().setItemChecked(n, true);
        } else {
            this.getListView().clearChoices();
        }
        this.showBreadCrumbs(header);
    }

    void showBreadCrumbs(Header header) {
        if (header != null) {
            CharSequence charSequence;
            CharSequence charSequence2 = charSequence = header.getBreadCrumbTitle(this.getResources());
            if (charSequence == null) {
                charSequence2 = header.getTitle(this.getResources());
            }
            charSequence = charSequence2;
            if (charSequence2 == null) {
                charSequence = this.getTitle();
            }
            this.showBreadCrumbs(charSequence, header.getBreadCrumbShortTitle(this.getResources()));
        } else {
            this.showBreadCrumbs(this.getTitle(), null);
        }
    }

    public void showBreadCrumbs(CharSequence charSequence, CharSequence charSequence2) {
        if (this.mFragmentBreadCrumbs == null) {
            Object object;
            block7 : {
                block8 : {
                    object = this.findViewById(16908310);
                    try {
                        this.mFragmentBreadCrumbs = (FragmentBreadCrumbs)object;
                        object = this.mFragmentBreadCrumbs;
                        if (object != null) break block7;
                        if (charSequence == null) break block8;
                    }
                    catch (ClassCastException classCastException) {
                        this.setTitle(charSequence);
                        return;
                    }
                    this.setTitle(charSequence);
                }
                return;
            }
            if (this.mSinglePane) {
                ((View)object).setVisibility(8);
                object = this.findViewById(16908769);
                if (object != null) {
                    ((View)object).setVisibility(8);
                }
                this.setTitle(charSequence);
            }
            this.mFragmentBreadCrumbs.setMaxVisible(2);
            this.mFragmentBreadCrumbs.setActivity(this);
        }
        if (this.mFragmentBreadCrumbs.getVisibility() != 0) {
            this.setTitle(charSequence);
        } else {
            this.mFragmentBreadCrumbs.setTitle(charSequence, charSequence2);
            this.mFragmentBreadCrumbs.setParentTitle(null, null, null);
        }
    }

    public void startPreferenceFragment(Fragment fragment, boolean bl) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(16909249, fragment);
        if (bl) {
            fragmentTransaction.setTransition(4097);
            fragmentTransaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            fragmentTransaction.setTransition(4099);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void startPreferencePanel(String object, Bundle object2, int n, CharSequence charSequence, Fragment fragment, int n2) {
        object = Fragment.instantiate(this, (String)object, (Bundle)object2);
        if (fragment != null) {
            ((Fragment)object).setTargetFragment(fragment, n2);
        }
        object2 = this.getFragmentManager().beginTransaction();
        ((FragmentTransaction)object2).replace(16909249, (Fragment)object);
        if (n != 0) {
            ((FragmentTransaction)object2).setBreadCrumbTitle(n);
        } else if (charSequence != null) {
            ((FragmentTransaction)object2).setBreadCrumbTitle(charSequence);
        }
        ((FragmentTransaction)object2).setTransition(4097);
        ((FragmentTransaction)object2).addToBackStack(BACK_STACK_PREFS);
        ((FragmentTransaction)object2).commitAllowingStateLoss();
    }

    public void startWithFragment(String string2, Bundle bundle, Fragment fragment, int n) {
        this.startWithFragment(string2, bundle, fragment, n, 0, 0);
    }

    public void startWithFragment(String object, Bundle bundle, Fragment fragment, int n, int n2, int n3) {
        object = this.onBuildStartFragmentIntent((String)object, bundle, n2, n3);
        if (fragment == null) {
            this.startActivity((Intent)object);
        } else {
            fragment.startActivityForResult((Intent)object, n);
        }
    }

    public void switchToHeader(Header header) {
        block4 : {
            block3 : {
                block2 : {
                    if (this.mCurHeader != header) break block2;
                    this.getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
                    break block3;
                }
                if (header.fragment == null) break block4;
                this.switchToHeaderInner(header.fragment, header.fragmentArguments);
                this.setSelectedHeader(header);
            }
            return;
        }
        throw new IllegalStateException("can't switch to header that has no fragment");
    }

    public void switchToHeader(String string2, Bundle bundle) {
        Header header;
        Header header2 = null;
        int n = 0;
        do {
            header = header2;
            if (n >= this.mHeaders.size()) break;
            if (string2.equals(this.mHeaders.get((int)n).fragment)) {
                header = this.mHeaders.get(n);
                break;
            }
            ++n;
        } while (true);
        this.setSelectedHeader(header);
        this.switchToHeaderInner(string2, bundle);
    }

    @Deprecated
    public static final class Header
    implements Parcelable {
        public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>(){

            @Override
            public Header createFromParcel(Parcel parcel) {
                return new Header(parcel);
            }

            public Header[] newArray(int n) {
                return new Header[n];
            }
        };
        public CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id = -1L;
        public Intent intent;
        public CharSequence summary;
        public int summaryRes;
        public CharSequence title;
        public int titleRes;

        public Header() {
        }

        Header(Parcel parcel) {
            this.readFromParcel(parcel);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public CharSequence getBreadCrumbShortTitle(Resources resources) {
            int n = this.breadCrumbShortTitleRes;
            if (n != 0) {
                return resources.getText(n);
            }
            return this.breadCrumbShortTitle;
        }

        public CharSequence getBreadCrumbTitle(Resources resources) {
            int n = this.breadCrumbTitleRes;
            if (n != 0) {
                return resources.getText(n);
            }
            return this.breadCrumbTitle;
        }

        public CharSequence getSummary(Resources resources) {
            int n = this.summaryRes;
            if (n != 0) {
                return resources.getText(n);
            }
            return this.summary;
        }

        public CharSequence getTitle(Resources resources) {
            int n = this.titleRes;
            if (n != 0) {
                return resources.getText(n);
            }
            return this.title;
        }

        public void readFromParcel(Parcel parcel) {
            this.id = parcel.readLong();
            this.titleRes = parcel.readInt();
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.summaryRes = parcel.readInt();
            this.summary = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbTitleRes = parcel.readInt();
            this.breadCrumbTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.breadCrumbShortTitleRes = parcel.readInt();
            this.breadCrumbShortTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.iconRes = parcel.readInt();
            this.fragment = parcel.readString();
            this.fragmentArguments = parcel.readBundle();
            if (parcel.readInt() != 0) {
                this.intent = Intent.CREATOR.createFromParcel(parcel);
            }
            this.extras = parcel.readBundle();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.id);
            parcel.writeInt(this.titleRes);
            TextUtils.writeToParcel(this.title, parcel, n);
            parcel.writeInt(this.summaryRes);
            TextUtils.writeToParcel(this.summary, parcel, n);
            parcel.writeInt(this.breadCrumbTitleRes);
            TextUtils.writeToParcel(this.breadCrumbTitle, parcel, n);
            parcel.writeInt(this.breadCrumbShortTitleRes);
            TextUtils.writeToParcel(this.breadCrumbShortTitle, parcel, n);
            parcel.writeInt(this.iconRes);
            parcel.writeString(this.fragment);
            parcel.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                parcel.writeInt(1);
                this.intent.writeToParcel(parcel, n);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.extras);
        }

    }

    private static class HeaderAdapter
    extends ArrayAdapter<Header> {
        private LayoutInflater mInflater;
        private int mLayoutResId;
        private boolean mRemoveIconIfEmpty;

        public HeaderAdapter(Context context, List<Header> list, int n, boolean bl) {
            super(context, 0, list);
            this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
            this.mLayoutResId = n;
            this.mRemoveIconIfEmpty = bl;
        }

        @Override
        public View getView(int n, View object, ViewGroup view) {
            if (object == null) {
                view = this.mInflater.inflate(this.mLayoutResId, (ViewGroup)view, false);
                object = new HeaderViewHolder();
                ((HeaderViewHolder)object).icon = (ImageView)view.findViewById(16908294);
                ((HeaderViewHolder)object).title = (TextView)view.findViewById(16908310);
                ((HeaderViewHolder)object).summary = (TextView)view.findViewById(16908304);
                view.setTag(object);
            } else {
                view = object;
                object = (HeaderViewHolder)view.getTag();
            }
            Object object2 = (Header)this.getItem(n);
            if (this.mRemoveIconIfEmpty) {
                if (((Header)object2).iconRes == 0) {
                    ((HeaderViewHolder)object).icon.setVisibility(8);
                } else {
                    ((HeaderViewHolder)object).icon.setVisibility(0);
                    ((HeaderViewHolder)object).icon.setImageResource(((Header)object2).iconRes);
                }
            } else {
                ((HeaderViewHolder)object).icon.setImageResource(((Header)object2).iconRes);
            }
            ((HeaderViewHolder)object).title.setText(((Header)object2).getTitle(this.getContext().getResources()));
            object2 = ((Header)object2).getSummary(this.getContext().getResources());
            if (!TextUtils.isEmpty((CharSequence)object2)) {
                ((HeaderViewHolder)object).summary.setVisibility(0);
                ((HeaderViewHolder)object).summary.setText((CharSequence)object2);
            } else {
                ((HeaderViewHolder)object).summary.setVisibility(8);
            }
            return view;
        }

        private static class HeaderViewHolder {
            ImageView icon;
            TextView summary;
            TextView title;

            private HeaderViewHolder() {
            }
        }

    }

}

