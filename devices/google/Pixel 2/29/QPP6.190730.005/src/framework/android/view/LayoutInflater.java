/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.PathClassLoader
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import com.android.internal.R;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public abstract class LayoutInflater {
    @UnsupportedAppUsage
    private static final int[] ATTRS_THEME;
    private static final String ATTR_LAYOUT = "layout";
    private static final ClassLoader BOOT_CLASS_LOADER;
    private static final String COMPILED_VIEW_DEX_FILE_NAME = "/compiled_view.dex";
    private static final boolean DEBUG = false;
    private static final StackTraceElement[] EMPTY_STACK_TRACE;
    private static final String TAG;
    private static final String TAG_1995 = "blink";
    private static final String TAG_INCLUDE = "include";
    private static final String TAG_MERGE = "merge";
    private static final String TAG_REQUEST_FOCUS = "requestFocus";
    private static final String TAG_TAG = "tag";
    private static final String USE_PRECOMPILED_LAYOUT = "view.precompiled_layout_enabled";
    @UnsupportedAppUsage
    static final Class<?>[] mConstructorSignature;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769490L)
    private static final HashMap<String, Constructor<? extends View>> sConstructorMap;
    @UnsupportedAppUsage(maxTargetSdk=28)
    final Object[] mConstructorArgs = new Object[2];
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected final Context mContext;
    @UnsupportedAppUsage
    private Factory mFactory;
    @UnsupportedAppUsage
    private Factory2 mFactory2;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private boolean mFactorySet;
    private Filter mFilter;
    private HashMap<String, Boolean> mFilterMap;
    private ClassLoader mPrecompiledClassLoader;
    @UnsupportedAppUsage
    private Factory2 mPrivateFactory;
    private TypedValue mTempValue;
    private boolean mUseCompiledView;

    static {
        TAG = LayoutInflater.class.getSimpleName();
        EMPTY_STACK_TRACE = new StackTraceElement[0];
        mConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sConstructorMap = new HashMap();
        ATTRS_THEME = new int[]{16842752};
        BOOT_CLASS_LOADER = LayoutInflater.class.getClassLoader();
    }

    protected LayoutInflater(Context context) {
        this.mContext = context;
        this.initPrecompiledViews();
    }

    protected LayoutInflater(LayoutInflater layoutInflater, Context context) {
        this.mContext = context;
        this.mFactory = layoutInflater.mFactory;
        this.mFactory2 = layoutInflater.mFactory2;
        this.mPrivateFactory = layoutInflater.mPrivateFactory;
        this.setFilter(layoutInflater.mFilter);
        this.initPrecompiledViews();
    }

    private void advanceToRootNode(XmlPullParser xmlPullParser) throws InflateException, IOException, XmlPullParserException {
        int n;
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n == 2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(xmlPullParser.getPositionDescription());
        stringBuilder.append(": No start tag found!");
        throw new InflateException(stringBuilder.toString());
    }

    static final void consumeChildElements(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
        }
    }

    @UnsupportedAppUsage
    private View createViewFromTag(View view, String string2, Context context, AttributeSet attributeSet) {
        return this.createViewFromTag(view, string2, context, attributeSet, false);
    }

    private void failNotAllowed(String string2, String string3, Context object, AttributeSet attributeSet) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LayoutInflater.getParserStateDescription((Context)object, attributeSet));
        stringBuilder.append(": Class not allowed to be inflated ");
        if (string3 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string3);
            ((StringBuilder)object).append(string2);
            string2 = ((StringBuilder)object).toString();
        }
        stringBuilder.append(string2);
        throw new InflateException(stringBuilder.toString());
    }

    public static LayoutInflater from(Context object) {
        if ((object = (LayoutInflater)((Context)object).getSystemService("layout_inflater")) != null) {
            return object;
        }
        throw new AssertionError((Object)"LayoutInflater not found.");
    }

    private static String getParserStateDescription(Context context, AttributeSet attributeSet) {
        int n = Resources.getAttributeSetSourceResId(attributeSet);
        if (n == 0) {
            return attributeSet.getPositionDescription();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(attributeSet.getPositionDescription());
        stringBuilder.append(" in ");
        stringBuilder.append(context.getResources().getResourceName(n));
        return stringBuilder.toString();
    }

    private void initPrecompiledViews() {
        this.initPrecompiledViews(false);
    }

    private void initPrecompiledViews(boolean bl) {
        this.mUseCompiledView = bl;
        if (!this.mUseCompiledView) {
            this.mPrecompiledClassLoader = null;
            return;
        }
        Object object = this.mContext.getApplicationInfo();
        if (!((ApplicationInfo)object).isEmbeddedDexUsed() && !((ApplicationInfo)object).isPrivilegedApp()) {
            try {
                this.mPrecompiledClassLoader = this.mContext.getClassLoader();
                object = new StringBuilder();
                ((StringBuilder)object).append(this.mContext.getCodeCacheDir());
                ((StringBuilder)object).append(COMPILED_VIEW_DEX_FILE_NAME);
                object = ((StringBuilder)object).toString();
                File file = new File((String)object);
                if (file.exists()) {
                    file = new PathClassLoader((String)object, this.mPrecompiledClassLoader);
                    this.mPrecompiledClassLoader = file;
                } else {
                    this.mUseCompiledView = false;
                }
            }
            catch (Throwable throwable) {
                this.mUseCompiledView = false;
            }
            if (!this.mUseCompiledView) {
                this.mPrecompiledClassLoader = null;
            }
            return;
        }
        this.mUseCompiledView = false;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private void parseInclude(XmlPullParser object, Context object2, View object3, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        block32 : {
            void var1_7;
            Object object4;
            block30 : {
                AttributeSet attributeSet2;
                block27 : {
                    block29 : {
                        int n;
                        Object object5;
                        ViewGroup viewGroup;
                        int n2;
                        block31 : {
                            boolean bl;
                            block28 : {
                                if (!(object3 instanceof ViewGroup)) throw new InflateException("<include /> can only be used inside of a ViewGroup");
                                object4 = ((Context)object2).obtainStyledAttributes(attributeSet, ATTRS_THEME);
                                n2 = ((TypedArray)object4).getResourceId(0, 0);
                                bl = n2 != 0;
                                if (bl) {
                                    object2 = new ContextThemeWrapper((Context)object2, n2);
                                }
                                ((TypedArray)object4).recycle();
                                n2 = n = attributeSet.getAttributeResourceValue(null, ATTR_LAYOUT, 0);
                                if (n == 0) {
                                    object4 = attributeSet.getAttributeValue(null, ATTR_LAYOUT);
                                    if (object4 == null) throw new InflateException("You must specify a layout in the include tag: <include layout=\"@layout/layoutID\" />");
                                    if (((String)object4).length() <= 0) throw new InflateException("You must specify a layout in the include tag: <include layout=\"@layout/layoutID\" />");
                                    n2 = ((Context)object2).getResources().getIdentifier(((String)object4).substring(1), "attr", ((Context)object2).getPackageName());
                                }
                                if (this.mTempValue == null) {
                                    this.mTempValue = new TypedValue();
                                }
                                if (n2 != 0 && ((Context)object2).getTheme().resolveAttribute(n2, this.mTempValue, true)) {
                                    n2 = this.mTempValue.resourceId;
                                }
                                if (n2 == 0) {
                                    object = attributeSet.getAttributeValue(null, ATTR_LAYOUT);
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("You must specify a valid layout reference. The layout ID ");
                                    ((StringBuilder)object2).append((String)object);
                                    ((StringBuilder)object2).append(" is not valid.");
                                    throw new InflateException(((StringBuilder)object2).toString());
                                }
                                if (this.tryInflatePrecompiled(n2, ((Context)object2).getResources(), (ViewGroup)object3, true) != null) break block32;
                                object4 = ((Context)object2).getResources().getLayout(n2);
                                attributeSet2 = Xml.asAttributeSet((XmlPullParser)object4);
                                while ((n2 = object4.next()) != 2 && n2 != 1) {
                                }
                                if (n2 != 2) break block27;
                                object5 = object4.getName();
                                boolean bl2 = TAG_MERGE.equals(object5);
                                if (!bl2) break block28;
                                try {
                                    this.rInflate((XmlPullParser)object4, (View)object3, (Context)object2, attributeSet2, false);
                                    break block29;
                                }
                                catch (Throwable throwable) {
                                    break block30;
                                }
                            }
                            object5 = this.createViewFromTag((View)object3, (String)object5, (Context)object2, attributeSet2, bl);
                            viewGroup = (ViewGroup)object3;
                            object2 = ((Context)object2).obtainStyledAttributes(attributeSet, R.styleable.Include);
                            n2 = ((TypedArray)object2).getResourceId(0, -1);
                            n = ((TypedArray)object2).getInt(1, -1);
                            ((TypedArray)object2).recycle();
                            object2 = null;
                            try {
                                object2 = object3 = viewGroup.generateLayoutParams(attributeSet);
                                break block31;
                            }
                            catch (Throwable throwable) {
                                break block30;
                            }
                            catch (RuntimeException runtimeException) {
                                // empty catch block
                            }
                        }
                        if (object2 == null) {
                            object2 = viewGroup.generateLayoutParams(attributeSet2);
                        }
                        ((View)object5).setLayoutParams((ViewGroup.LayoutParams)object2);
                        try {
                            this.rInflateChildren((XmlPullParser)object4, (View)object5, attributeSet2, true);
                            if (n2 != -1) {
                                ((View)object5).setId(n2);
                            }
                            if (n != 0) {
                                if (n != 1) {
                                    if (n == 2) {
                                        ((View)object5).setVisibility(8);
                                    }
                                } else {
                                    ((View)object5).setVisibility(4);
                                }
                            } else {
                                ((View)object5).setVisibility(0);
                            }
                            viewGroup.addView((View)object5);
                        }
                        catch (Throwable throwable) {}
                    }
                    object4.close();
                    break block32;
                    catch (Throwable throwable) {}
                    break block30;
                }
                object3 = new StringBuilder();
                ((StringBuilder)object3).append(LayoutInflater.getParserStateDescription((Context)object2, attributeSet2));
                ((StringBuilder)object3).append(": No start tag found!");
                object = new InflateException(((StringBuilder)object3).toString());
                throw object;
                break block30;
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            object4.close();
            throw var1_7;
        }
        LayoutInflater.consumeChildElements((XmlPullParser)object);
    }

    private void parseViewTag(XmlPullParser xmlPullParser, View view, AttributeSet object) throws XmlPullParserException, IOException {
        object = view.getContext().obtainStyledAttributes((AttributeSet)object, R.styleable.ViewTag);
        view.setTag(((TypedArray)object).getResourceId(1, 0), ((TypedArray)object).getText(0));
        ((TypedArray)object).recycle();
        LayoutInflater.consumeChildElements(xmlPullParser);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private View tryInflatePrecompiled(int n, Resources object, ViewGroup viewGroup, boolean bl) {
        Object object2;
        block7 : {
            block8 : {
                if (!this.mUseCompiledView) {
                    return null;
                }
                Trace.traceBegin(8L, "inflate (precompiled)");
                Object object3 = ((Resources)object).getResourcePackageName(n);
                object2 = ((Resources)object).getResourceEntryName(n);
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append((String)object3);
                    stringBuilder.append(".CompiledView");
                    object2 = (View)Class.forName(stringBuilder.toString(), false, this.mPrecompiledClassLoader).getMethod((String)object2, Context.class, Integer.TYPE).invoke(null, this.mContext, n);
                    if (object2 == null || viewGroup == null) break block7;
                    object = ((Resources)object).getLayout(n);
                }
                catch (Throwable throwable) {
                    Trace.traceEnd(8L);
                    return null;
                }
                object3 = Xml.asAttributeSet((XmlPullParser)object);
                this.advanceToRootNode((XmlPullParser)object);
                object3 = viewGroup.generateLayoutParams((AttributeSet)object3);
                if (bl) {
                    viewGroup.addView((View)object2, (ViewGroup.LayoutParams)object3);
                    break block8;
                }
                ((View)object2).setLayoutParams((ViewGroup.LayoutParams)object3);
                {
                    catch (Throwable throwable) {
                        object.close();
                        throw throwable;
                    }
                }
            }
            object.close();
        }
        Trace.traceEnd(8L);
        return object2;
    }

    private final boolean verifyClassLoader(Constructor<? extends View> object) {
        ClassLoader classLoader = ((Constructor)object).getDeclaringClass().getClassLoader();
        if (classLoader == BOOT_CLASS_LOADER) {
            return true;
        }
        object = this.mContext.getClassLoader();
        do {
            if (classLoader != object) continue;
            return true;
        } while ((object = ((ClassLoader)object).getParent()) != null);
        return false;
    }

    public abstract LayoutInflater cloneInContext(Context var1);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final View createView(Context var1_1, String var2_4, String var3_5, AttributeSet var4_7) throws ClassNotFoundException, InflateException {
        block27 : {
            block29 : {
                block30 : {
                    block28 : {
                        Objects.requireNonNull(var1_1);
                        Objects.requireNonNull(var2_4);
                        var5_8 /* !! */  = LayoutInflater.sConstructorMap.get(var2_4);
                        var6_9 = var5_8 /* !! */ ;
                        if (var5_8 /* !! */  != null) {
                            var6_9 = var5_8 /* !! */ ;
                            if (!this.verifyClassLoader((Constructor<? extends View>)var5_8 /* !! */ )) {
                                var6_9 = null;
                                LayoutInflater.sConstructorMap.remove(var2_4);
                            }
                        }
                        var7_10 = null;
                        var8_11 = null;
                        var9_12 = null;
                        var5_8 /* !! */  = var8_11;
                        Trace.traceBegin(8L, (String)var2_4);
                        if (var6_9 != null) break block28;
                        if (var3_5 != null) {
                            var5_8 /* !! */  = var8_11;
                            var5_8 /* !! */  = var8_11;
                            var10_13 = new Constructor<View>();
                            var5_8 /* !! */  = var8_11;
                            var10_13.append(var3_5);
                            var5_8 /* !! */  = var8_11;
                            var10_13.append((String)var2_4);
                            var5_8 /* !! */  = var8_11;
                            var10_13 = var10_13.toString();
                        } else {
                            var10_13 = var2_4;
                        }
                        var5_8 /* !! */  = var8_11;
                        var10_13 = Class.forName((String)var10_13, false, this.mContext.getClassLoader()).asSubclass(View.class);
                        var5_8 /* !! */  = var10_13;
                        if (this.mFilter != null && var10_13 != null) {
                            var5_8 /* !! */  = var10_13;
                            if (!this.mFilter.onLoadClass((Class)var10_13)) {
                                var5_8 /* !! */  = var10_13;
                                this.failNotAllowed((String)var2_4, var3_5, (Context)var1_1, var4_7);
                            }
                        }
                        var5_8 /* !! */  = var10_13;
                        var11_16 = var10_13.getConstructor(LayoutInflater.mConstructorSignature);
                        var5_8 /* !! */  = var10_13;
                        var11_16.setAccessible(true);
                        var5_8 /* !! */  = var10_13;
                        LayoutInflater.sConstructorMap.put((String)var2_4, (Constructor<? extends View>)var11_16);
                        break block29;
                    }
                    var11_16 = var6_9;
                    var10_13 = var7_10;
                    var5_8 /* !! */  = var8_11;
                    if (this.mFilter == null) break block29;
                    var5_8 /* !! */  = var8_11;
                    var11_16 = this.mFilterMap.get(var2_4);
                    if (var11_16 != null) break block30;
                    if (var3_5 != null) {
                        var5_8 /* !! */  = var8_11;
                        var5_8 /* !! */  = var8_11;
                        var10_13 = new Constructor<View>();
                        var5_8 /* !! */  = var8_11;
                        var10_13.append(var3_5);
                        var5_8 /* !! */  = var8_11;
                        var10_13.append((String)var2_4);
                        var5_8 /* !! */  = var8_11;
                        var10_13 = var10_13.toString();
                    } else {
                        var10_13 = var2_4;
                    }
                    var5_8 /* !! */  = var8_11;
                    var11_16 = Class.forName((String)var10_13, false, this.mContext.getClassLoader()).asSubclass(View.class);
                    if (var11_16 == null) ** GOTO lbl-1000
                    var5_8 /* !! */  = var11_16;
                    if (this.mFilter.onLoadClass((Class)var11_16)) {
                        var12_18 = true;
                    } else lbl-1000: // 2 sources:
                    {
                        var12_18 = false;
                    }
                    var5_8 /* !! */  = var11_16;
                    this.mFilterMap.put((String)var2_4, var12_18);
                    var10_13 = var11_16;
                    if (var12_18) ** GOTO lbl-1000
                    var5_8 /* !! */  = var11_16;
                    this.failNotAllowed((String)var2_4, var3_5, (Context)var1_1, var4_7);
                    var10_13 = var11_16;
                    ** GOTO lbl-1000
                }
                var10_13 = var9_12;
                var5_8 /* !! */  = var8_11;
                if (var11_16.equals(Boolean.FALSE)) {
                    var5_8 /* !! */  = var8_11;
                    this.failNotAllowed((String)var2_4, var3_5, (Context)var1_1, var4_7);
                    var10_13 = var7_10;
                    var11_16 = var6_9;
                } else lbl-1000: // 3 sources:
                {
                    var11_16 = var6_9;
                }
            }
            var5_8 /* !! */  = var10_13;
            var6_9 = this.mConstructorArgs[0];
            var5_8 /* !! */  = var10_13;
            this.mConstructorArgs[0] = var1_1;
            var5_8 /* !! */  = var10_13;
            var8_11 = this.mConstructorArgs;
            var8_11[1] = var4_7;
            var11_16 = (View)var11_16.newInstance(var8_11);
            if (var11_16 instanceof ViewStub) {
                ((ViewStub)var11_16).setLayoutInflater(this.cloneInContext((Context)var8_11[0]));
            }
            var5_8 /* !! */  = var10_13;
            this.mConstructorArgs[0] = var6_9;
            Trace.traceEnd(8L);
            return var11_16;
            catch (Throwable var11_17) {
                var5_8 /* !! */  = var10_13;
                try {
                    this.mConstructorArgs[0] = var6_9;
                    var5_8 /* !! */  = var10_13;
                    throw var11_17;
                }
                catch (Throwable var1_2) {
                    break block27;
                }
                catch (Exception var3_6) {
                    var2_4 = new StringBuilder();
                    var2_4.append(LayoutInflater.getParserStateDescription((Context)var1_1, var4_7));
                    var2_4.append(": Error inflating class ");
                    var1_1 = var5_8 /* !! */  == null ? "<unknown>" : var5_8 /* !! */ .getName();
                    var2_4.append((String)var1_1);
                    var10_13 = new Constructor<View>(var2_4.toString(), (Throwable)var3_6);
                    var10_13.setStackTrace(LayoutInflater.EMPTY_STACK_TRACE);
                    throw var10_13;
                }
                catch (ClassNotFoundException var1_3) {
                    throw var1_3;
                }
                catch (ClassCastException var10_14) {
                    var6_9 = new Constructor<View>();
                    var6_9.append(LayoutInflater.getParserStateDescription((Context)var1_1, var4_7));
                    var6_9.append(": Class is not a View ");
                    if (var3_5 != null) {
                        var1_1 = new StringBuilder();
                        var1_1.append(var3_5);
                        var1_1.append((String)var2_4);
                        var2_4 = var1_1.toString();
                    }
                    var6_9.append((String)var2_4);
                    var5_8 /* !! */  = new InflateException(var6_9.toString(), var10_14);
                    var5_8 /* !! */ .setStackTrace(LayoutInflater.EMPTY_STACK_TRACE);
                    throw var5_8 /* !! */ ;
                }
                catch (NoSuchMethodException var10_15) {
                    var5_8 /* !! */  = new StringBuilder();
                    var5_8 /* !! */ .append(LayoutInflater.getParserStateDescription((Context)var1_1, var4_7));
                    var5_8 /* !! */ .append(": Error inflating class ");
                    if (var3_5 != null) {
                        var1_1 = new StringBuilder();
                        var1_1.append(var3_5);
                        var1_1.append((String)var2_4);
                        var1_1 = var1_1.toString();
                    } else {
                        var1_1 = var2_4;
                    }
                    var5_8 /* !! */ .append((String)var1_1);
                    var6_9 = new Constructor<View>(var5_8 /* !! */ .toString(), (Throwable)var10_15);
                    var6_9.setStackTrace(LayoutInflater.EMPTY_STACK_TRACE);
                    throw var6_9;
                }
            }
        }
        Trace.traceEnd(8L);
        throw var1_2;
    }

    public final View createView(String string2, String string3, AttributeSet attributeSet) throws ClassNotFoundException, InflateException {
        Context context;
        Context context2 = context = (Context)this.mConstructorArgs[0];
        if (context == null) {
            context2 = this.mContext;
        }
        return this.createView(context2, string2, string3, attributeSet);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    View createViewFromTag(View view, String object, Context object2, AttributeSet attributeSet, boolean bl) {
        Object object3;
        Object string2 = object;
        if (((String)object).equals("view")) {
            string2 = attributeSet.getAttributeValue(null, "class");
        }
        object = object2;
        if (!bl) {
            object3 = ((Context)object2).obtainStyledAttributes(attributeSet, ATTRS_THEME);
            int n = ((TypedArray)object3).getResourceId(0, 0);
            object = object2;
            if (n != 0) {
                object = new ContextThemeWrapper((Context)object2, n);
            }
            ((TypedArray)object3).recycle();
        }
        object2 = object3 = this.tryCreateView(view, (String)string2, (Context)object, attributeSet);
        if (object3 != null) return object2;
        object2 = this.mConstructorArgs[0];
        this.mConstructorArgs[0] = object;
        view = -1 == ((String)string2).indexOf(46) ? this.onCreateView((Context)object, view, (String)string2, attributeSet) : this.createView((Context)object, (String)string2, null, attributeSet);
        {
            catch (Throwable throwable) {
                this.mConstructorArgs[0] = object2;
                throw throwable;
            }
        }
        try {
            this.mConstructorArgs[0] = object2;
            return view;
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(LayoutInflater.getParserStateDescription((Context)object, attributeSet));
            ((StringBuilder)object2).append(": Error inflating class ");
            ((StringBuilder)object2).append((String)string2);
            InflateException inflateException = new InflateException(((StringBuilder)object2).toString(), exception);
            inflateException.setStackTrace(EMPTY_STACK_TRACE);
            throw inflateException;
        }
        catch (ClassNotFoundException classNotFoundException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(LayoutInflater.getParserStateDescription((Context)object, attributeSet));
            ((StringBuilder)object2).append(": Error inflating class ");
            ((StringBuilder)object2).append((String)string2);
            InflateException inflateException = new InflateException(((StringBuilder)object2).toString(), classNotFoundException);
            inflateException.setStackTrace(EMPTY_STACK_TRACE);
            throw inflateException;
        }
        catch (InflateException inflateException) {
            throw inflateException;
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public final Factory getFactory() {
        return this.mFactory;
    }

    public final Factory2 getFactory2() {
        return this.mFactory2;
    }

    public Filter getFilter() {
        return this.mFilter;
    }

    public View inflate(int n, ViewGroup viewGroup) {
        boolean bl = viewGroup != null;
        return this.inflate(n, viewGroup, bl);
    }

    public View inflate(int n, ViewGroup view, boolean bl) {
        Object object = this.getContext().getResources();
        View view2 = this.tryInflatePrecompiled(n, (Resources)object, (ViewGroup)view, bl);
        if (view2 != null) {
            return view2;
        }
        object = ((Resources)object).getLayout(n);
        try {
            view = this.inflate((XmlPullParser)object, (ViewGroup)view, bl);
            return view;
        }
        finally {
            object.close();
        }
    }

    public View inflate(XmlPullParser xmlPullParser, ViewGroup viewGroup) {
        boolean bl = viewGroup != null;
        return this.inflate(xmlPullParser, viewGroup, bl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public View inflate(XmlPullParser var1_1, ViewGroup var2_13, boolean var3_14) {
        block22 : {
            block21 : {
                block19 : {
                    block20 : {
                        var4_15 = this.mConstructorArgs;
                        // MONITORENTER : var4_15
                        Trace.traceBegin(8L, "inflate");
                        var5_16 = this.mContext;
                        var6_17 = Xml.asAttributeSet((XmlPullParser)var1_1);
                        var7_18 = (Context)this.mConstructorArgs[0];
                        this.mConstructorArgs[0] = var5_16;
                        var8_19 = var2_13;
                        this.advanceToRootNode((XmlPullParser)var1_1);
                        var9_20 = var1_1.getName();
                        var10_21 = "merge".equals(var9_20);
                        if (!var10_21) ** GOTO lbl20
                        if (var2_13 == null || !var3_14) ** GOTO lbl18
                        this.rInflate((XmlPullParser)var1_1, (View)var2_13, var5_16, var6_17, false);
                        break block19;
lbl18: // 1 sources:
                        var1_1 = new InflateException("<merge /> can be used only with a valid ViewGroup root and attachToRoot=true");
                        throw var1_1;
lbl20: // 1 sources:
                        var11_22 = this.createViewFromTag((View)var2_13, (String)var9_20, var5_16, var6_17);
                        var9_20 = null;
                        if (var2_13 == null) break block20;
                        var12_23 = var2_13.generateLayoutParams(var6_17);
                        var9_20 = var12_23;
                        if (var3_14) break block20;
                        var11_22.setLayoutParams(var12_23);
                        var9_20 = var12_23;
                    }
                    this.rInflateChildren((XmlPullParser)var1_1, var11_22, var6_17, true);
                    if (var2_13 != null && var3_14) {
                        var2_13.addView(var11_22, (ViewGroup.LayoutParams)var9_20);
                    }
                    if (var2_13 != null && var3_14) break block19;
                    var8_19 = var11_22;
                }
                this.mConstructorArgs[0] = var7_18;
                this.mConstructorArgs[1] = null;
                Trace.traceEnd(8L);
                // MONITOREXIT : var4_15
                return var8_19;
                catch (Exception var1_2) {
                    break block21;
                }
                catch (XmlPullParserException var1_3) {
                    ** GOTO lbl72
                }
                catch (Throwable var1_4) {
                    break block22;
                }
                catch (Exception var1_5) {
                    break block21;
                }
                catch (XmlPullParserException var1_6) {
                    ** GOTO lbl72
                }
                catch (Throwable var1_7) {
                    break block22;
                }
                catch (Exception var1_8) {
                    // empty catch block
                }
            }
            try {
                var8_19 = new StringBuilder();
                var8_19.append(LayoutInflater.getParserStateDescription(var5_16, var6_17));
                var8_19.append(": ");
                var8_19.append(var1_1.getMessage());
                var2_13 = new InflateException(var8_19.toString(), (Throwable)var1_1);
                var2_13.setStackTrace(LayoutInflater.EMPTY_STACK_TRACE);
                throw var2_13;
                catch (XmlPullParserException var1_9) {
                    // empty catch block
                }
lbl72: // 3 sources:
                var2_13 = new InflateException(var1_10.getMessage(), (Throwable)var1_10);
                var2_13.setStackTrace(LayoutInflater.EMPTY_STACK_TRACE);
                throw var2_13;
            }
            catch (Throwable var1_11) {
                // empty catch block
            }
        }
        this.mConstructorArgs[0] = var7_18;
        this.mConstructorArgs[1] = null;
        Trace.traceEnd(8L);
        throw var1_1;
    }

    public View onCreateView(Context context, View view, String string2, AttributeSet attributeSet) throws ClassNotFoundException {
        return this.onCreateView(view, string2, attributeSet);
    }

    protected View onCreateView(View view, String string2, AttributeSet attributeSet) throws ClassNotFoundException {
        return this.onCreateView(string2, attributeSet);
    }

    protected View onCreateView(String string2, AttributeSet attributeSet) throws ClassNotFoundException {
        return this.createView(string2, "android.view.", attributeSet);
    }

    void rInflate(XmlPullParser xmlPullParser, View view, Context context, AttributeSet attributeSet, boolean bl) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        boolean bl2 = false;
        while (((n = xmlPullParser.next()) != 3 || xmlPullParser.getDepth() > n2) && n != 1) {
            if (n != 2) continue;
            Object object = xmlPullParser.getName();
            if ("requestFocus".equals(object)) {
                bl2 = true;
                LayoutInflater.consumeChildElements(xmlPullParser);
                continue;
            }
            if ("tag".equals(object)) {
                this.parseViewTag(xmlPullParser, view, attributeSet);
                continue;
            }
            if ("include".equals(object)) {
                if (xmlPullParser.getDepth() != 0) {
                    this.parseInclude(xmlPullParser, context, view, attributeSet);
                    continue;
                }
                throw new InflateException("<include /> cannot be the root element");
            }
            if (!"merge".equals(object)) {
                View view2 = this.createViewFromTag(view, (String)object, context, attributeSet);
                object = (ViewGroup)view;
                ViewGroup.LayoutParams layoutParams = ((ViewGroup)object).generateLayoutParams(attributeSet);
                this.rInflateChildren(xmlPullParser, view2, attributeSet, true);
                ((ViewGroup)object).addView(view2, layoutParams);
                continue;
            }
            throw new InflateException("<merge /> must be the root element");
        }
        if (bl2) {
            view.restoreDefaultFocus();
        }
        if (bl) {
            view.onFinishInflate();
        }
    }

    final void rInflateChildren(XmlPullParser xmlPullParser, View view, AttributeSet attributeSet, boolean bl) throws XmlPullParserException, IOException {
        this.rInflate(xmlPullParser, view, view.getContext(), attributeSet, bl);
    }

    public void setFactory(Factory factory) {
        if (!this.mFactorySet) {
            if (factory != null) {
                this.mFactorySet = true;
                Factory factory2 = this.mFactory;
                this.mFactory = factory2 == null ? factory : new FactoryMerger(factory, null, factory2, this.mFactory2);
                return;
            }
            throw new NullPointerException("Given factory can not be null");
        }
        throw new IllegalStateException("A factory has already been set on this LayoutInflater");
    }

    public void setFactory2(Factory2 factory2) {
        if (!this.mFactorySet) {
            if (factory2 != null) {
                this.mFactorySet = true;
                Factory factory = this.mFactory;
                if (factory == null) {
                    this.mFactory2 = factory2;
                    this.mFactory = factory2;
                } else {
                    this.mFactory2 = factory2 = new FactoryMerger(factory2, factory2, factory, this.mFactory2);
                    this.mFactory = factory2;
                }
                return;
            }
            throw new NullPointerException("Given factory can not be null");
        }
        throw new IllegalStateException("A factory has already been set on this LayoutInflater");
    }

    public void setFilter(Filter filter) {
        this.mFilter = filter;
        if (filter != null) {
            this.mFilterMap = new HashMap<K, V>();
        }
    }

    public void setPrecompiledLayoutsEnabledForTesting(boolean bl) {
        this.initPrecompiledViews(bl);
    }

    @UnsupportedAppUsage
    public void setPrivateFactory(Factory2 factory2) {
        Factory2 factory22 = this.mPrivateFactory;
        this.mPrivateFactory = factory22 == null ? factory2 : new FactoryMerger(factory2, factory2, factory22, factory22);
    }

    @UnsupportedAppUsage(trackingBug=122360734L)
    public final View tryCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        if (string2.equals("blink")) {
            return new BlinkLayout(context, attributeSet);
        }
        Object object = this.mFactory2;
        object = object != null ? object.onCreateView(view, string2, context, attributeSet) : ((object = this.mFactory) != null ? object.onCreateView(string2, context, attributeSet) : null);
        Object object2 = object;
        if (object == null) {
            Factory2 factory2 = this.mPrivateFactory;
            object2 = object;
            if (factory2 != null) {
                object2 = factory2.onCreateView(view, string2, context, attributeSet);
            }
        }
        return object2;
    }

    private static class BlinkLayout
    extends FrameLayout {
        private static final int BLINK_DELAY = 500;
        private static final int MESSAGE_BLINK = 66;
        private boolean mBlink;
        private boolean mBlinkState;
        private final Handler mHandler = new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message object) {
                if (((Message)object).what == 66) {
                    if (BlinkLayout.this.mBlink) {
                        object = BlinkLayout.this;
                        ((BlinkLayout)object).mBlinkState = ((BlinkLayout)object).mBlinkState ^ true;
                        BlinkLayout.this.makeBlink();
                    }
                    BlinkLayout.this.invalidate();
                    return true;
                }
                return false;
            }
        });

        public BlinkLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        private void makeBlink() {
            Message message = this.mHandler.obtainMessage(66);
            this.mHandler.sendMessageDelayed(message, 500L);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            if (this.mBlinkState) {
                super.dispatchDraw(canvas);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.mBlink = true;
            this.mBlinkState = true;
            this.makeBlink();
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.mBlink = false;
            this.mBlinkState = true;
            this.mHandler.removeMessages(66);
        }

    }

    public static interface Factory {
        public View onCreateView(String var1, Context var2, AttributeSet var3);
    }

    public static interface Factory2
    extends Factory {
        public View onCreateView(View var1, String var2, Context var3, AttributeSet var4);
    }

    private static class FactoryMerger
    implements Factory2 {
        private final Factory mF1;
        private final Factory2 mF12;
        private final Factory mF2;
        private final Factory2 mF22;

        FactoryMerger(Factory factory, Factory2 factory2, Factory factory3, Factory2 factory22) {
            this.mF1 = factory;
            this.mF2 = factory3;
            this.mF12 = factory2;
            this.mF22 = factory22;
        }

        @Override
        public View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
            Object object = this.mF12;
            object = object != null ? object.onCreateView(view, string2, context, attributeSet) : this.mF1.onCreateView(string2, context, attributeSet);
            if (object != null) {
                return object;
            }
            object = this.mF22;
            view = object != null ? object.onCreateView(view, string2, context, attributeSet) : this.mF2.onCreateView(string2, context, attributeSet);
            return view;
        }

        @Override
        public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
            View view = this.mF1.onCreateView(string2, context, attributeSet);
            if (view != null) {
                return view;
            }
            return this.mF2.onCreateView(string2, context, attributeSet);
        }
    }

    public static interface Filter {
        public boolean onLoadClass(Class var1);
    }

}

