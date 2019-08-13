/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionProvider;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import com.android.internal.R;
import com.android.internal.view.menu.MenuItemImpl;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class MenuInflater {
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    private static final String LOG_TAG = "MenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;

    static {
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class};
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    }

    public MenuInflater(Context context) {
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public MenuInflater(Context context, Object object) {
        this.mContext = context;
        this.mRealOwner = object;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    private Object findRealOwner(Object object) {
        if (object instanceof Activity) {
            return object;
        }
        if (object instanceof ContextWrapper) {
            return this.findRealOwner(((ContextWrapper)object).getBaseContext());
        }
        return object;
    }

    private Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = this.findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private void parseMenu(XmlPullParser object, AttributeSet attributeSet, Menu object2) throws XmlPullParserException, IOException {
        int n;
        Object object3;
        int n2;
        MenuState menuState;
        int n3;
        block27 : {
            menuState = new MenuState((Menu)object2);
            n2 = object.getEventType();
            n3 = 0;
            object3 = null;
            do {
                if (n2 == 2) {
                    object2 = object.getName();
                    if (((String)object2).equals(XML_MENU)) {
                        n2 = object.next();
                        break block27;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Expecting menu, got ");
                    ((StringBuilder)object).append((String)object2);
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                n2 = n = object.next();
            } while (n != 1);
            n2 = n;
        }
        n = 0;
        int n4 = n2;
        while (n == 0) {
            if (n4 != 1) {
                int n5;
                if (n4 != 2) {
                    if (n4 != 3) {
                        n2 = n3;
                        object2 = object3;
                        n5 = n;
                    } else {
                        String string2 = object.getName();
                        if (n3 != 0 && string2.equals(object3)) {
                            n2 = 0;
                            object2 = null;
                            n5 = n;
                        } else if (string2.equals(XML_GROUP)) {
                            menuState.resetGroup();
                            n2 = n3;
                            object2 = object3;
                            n5 = n;
                        } else if (string2.equals(XML_ITEM)) {
                            n2 = n3;
                            object2 = object3;
                            n5 = n;
                            if (!menuState.hasAddedItem()) {
                                if (menuState.itemActionProvider != null && menuState.itemActionProvider.hasSubMenu()) {
                                    this.registerMenu(menuState.addSubMenuItem(), attributeSet);
                                    n2 = n3;
                                    object2 = object3;
                                    n5 = n;
                                } else {
                                    this.registerMenu(menuState.addItem(), attributeSet);
                                    n2 = n3;
                                    object2 = object3;
                                    n5 = n;
                                }
                            }
                        } else {
                            n2 = n3;
                            object2 = object3;
                            n5 = n;
                            if (string2.equals(XML_MENU)) {
                                n5 = 1;
                                n2 = n3;
                                object2 = object3;
                            }
                        }
                    }
                } else if (n3 != 0) {
                    n2 = n3;
                    object2 = object3;
                    n5 = n;
                } else {
                    object2 = object.getName();
                    if (((String)object2).equals(XML_GROUP)) {
                        menuState.readGroup(attributeSet);
                        n2 = n3;
                        object2 = object3;
                        n5 = n;
                    } else if (((String)object2).equals(XML_ITEM)) {
                        menuState.readItem(attributeSet);
                        n2 = n3;
                        object2 = object3;
                        n5 = n;
                    } else if (((String)object2).equals(XML_MENU)) {
                        object2 = menuState.addSubMenuItem();
                        this.registerMenu((SubMenu)object2, attributeSet);
                        this.parseMenu((XmlPullParser)object, attributeSet, (Menu)object2);
                        n2 = n3;
                        object2 = object3;
                        n5 = n;
                    } else {
                        n2 = 1;
                        n5 = n;
                    }
                }
                n4 = object.next();
                n3 = n2;
                object3 = object2;
                n = n5;
                continue;
            }
            throw new RuntimeException("Unexpected end of document");
        }
    }

    private void registerMenu(MenuItem menuItem, AttributeSet attributeSet) {
    }

    private void registerMenu(SubMenu subMenu, AttributeSet attributeSet) {
    }

    Context getContext() {
        return this.mContext;
    }

    /*
     * Exception decompiling
     */
    public void inflate(int var1_1, Menu var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object object, String object2) {
            this.mRealOwner = object;
            Class<?> class_ = object.getClass();
            try {
                this.mMethod = class_.getMethod((String)object2, PARAM_TYPES);
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't resolve menu item onClick handler ");
                stringBuilder.append((String)object2);
                stringBuilder.append(" in class ");
                stringBuilder.append(class_.getName());
                object2 = new InflateException(stringBuilder.toString());
                ((Throwable)object2).initCause(exception);
                throw object2;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return (Boolean)this.mMethod.invoke(this.mRealOwner, menuItem);
                }
                this.mMethod.invoke(this.mRealOwner, menuItem);
                return true;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private ColorStateList itemIconTintList = null;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private BlendMode mItemIconBlendMode = null;
        private Menu menu;

        public MenuState(Menu menu2) {
            this.menu = menu2;
            this.resetGroup();
        }

        private char getShortcut(String string2) {
            if (string2 == null) {
                return '\u0000';
            }
            return string2.charAt(0);
        }

        private <T> T newInstance(String string2, Class<?>[] object, Object[] arrobject) {
            try {
                object = MenuInflater.this.mContext.getClassLoader().loadClass(string2).getConstructor((Class<?>)object);
                ((AccessibleObject)object).setAccessible(true);
                object = ((Constructor)object).newInstance(arrobject);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot instantiate class: ");
                ((StringBuilder)object).append(string2);
                Log.w(MenuInflater.LOG_TAG, ((StringBuilder)object).toString(), exception);
                return null;
            }
            return (T)object;
        }

        private void setItem(MenuItem menuItem) {
            int n;
            Object object = menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
            boolean bl = this.itemCheckable >= 1;
            object.setCheckable(bl).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut, this.itemAlphabeticModifiers).setNumericShortcut(this.itemNumericShortcut, this.itemNumericModifiers);
            int n2 = this.itemShowAsAction;
            if (n2 >= 0) {
                menuItem.setShowAsAction(n2);
            }
            if ((object = this.mItemIconBlendMode) != null) {
                menuItem.setIconTintBlendMode((BlendMode)((Object)object));
            }
            if ((object = this.itemIconTintList) != null) {
                menuItem.setIconTintList((ColorStateList)object);
            }
            if (this.itemListenerMethodName != null) {
                if (!MenuInflater.this.mContext.isRestricted()) {
                    menuItem.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(MenuInflater.this.getRealOwner(), this.itemListenerMethodName));
                } else {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
            }
            if (menuItem instanceof MenuItemImpl) {
                object = (MenuItemImpl)menuItem;
                if (this.itemCheckable >= 2) {
                    ((MenuItemImpl)object).setExclusiveCheckable(true);
                }
            }
            n2 = 0;
            object = this.itemActionViewClassName;
            if (object != null) {
                menuItem.setActionView((View)this.newInstance((String)object, ACTION_VIEW_CONSTRUCTOR_SIGNATURE, MenuInflater.this.mActionViewConstructorArguments));
                n2 = 1;
            }
            if ((n = this.itemActionViewLayout) > 0) {
                if (n2 == 0) {
                    menuItem.setActionView(n);
                } else {
                    Log.w(MenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if ((object = this.itemActionProvider) != null) {
                menuItem.setActionProvider((ActionProvider)object);
            }
            menuItem.setContentDescription(this.itemContentDescription);
            menuItem.setTooltipText(this.itemTooltipText);
        }

        public MenuItem addItem() {
            this.itemAdded = true;
            MenuItem menuItem = this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            this.setItem(menuItem);
            return menuItem;
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            this.setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        public void readGroup(AttributeSet object) {
            object = MenuInflater.this.mContext.obtainStyledAttributes((AttributeSet)object, R.styleable.MenuGroup);
            this.groupId = ((TypedArray)object).getResourceId(1, 0);
            this.groupCategory = ((TypedArray)object).getInt(3, 0);
            this.groupOrder = ((TypedArray)object).getInt(4, 0);
            this.groupCheckable = ((TypedArray)object).getInt(5, 0);
            this.groupVisible = ((TypedArray)object).getBoolean(2, true);
            this.groupEnabled = ((TypedArray)object).getBoolean(0, true);
            ((TypedArray)object).recycle();
        }

        public void readItem(AttributeSet object) {
            object = MenuInflater.this.mContext.obtainStyledAttributes((AttributeSet)object, R.styleable.MenuItem);
            this.itemId = ((TypedArray)object).getResourceId(2, 0);
            this.itemCategoryOrder = -65536 & ((TypedArray)object).getInt(5, this.groupCategory) | 65535 & ((TypedArray)object).getInt(6, this.groupOrder);
            this.itemTitle = ((TypedArray)object).getText(7);
            this.itemTitleCondensed = ((TypedArray)object).getText(8);
            this.itemIconResId = ((TypedArray)object).getResourceId(0, 0);
            this.mItemIconBlendMode = ((TypedArray)object).hasValue(22) ? Drawable.parseBlendMode(((TypedArray)object).getInt(22, -1), this.mItemIconBlendMode) : null;
            this.itemIconTintList = ((TypedArray)object).hasValue(21) ? ((TypedArray)object).getColorStateList(21) : null;
            this.itemAlphabeticShortcut = this.getShortcut(((TypedArray)object).getString(9));
            this.itemAlphabeticModifiers = ((TypedArray)object).getInt(19, 4096);
            this.itemNumericShortcut = this.getShortcut(((TypedArray)object).getString(10));
            this.itemNumericModifiers = ((TypedArray)object).getInt(20, 4096);
            this.itemCheckable = ((TypedArray)object).hasValue(11) ? (int)(((TypedArray)object).getBoolean(11, false) ? 1 : 0) : this.groupCheckable;
            this.itemChecked = ((TypedArray)object).getBoolean(3, false);
            this.itemVisible = ((TypedArray)object).getBoolean(4, this.groupVisible);
            boolean bl = this.groupEnabled;
            boolean bl2 = true;
            this.itemEnabled = ((TypedArray)object).getBoolean(1, bl);
            this.itemShowAsAction = ((TypedArray)object).getInt(14, -1);
            this.itemListenerMethodName = ((TypedArray)object).getString(12);
            this.itemActionViewLayout = ((TypedArray)object).getResourceId(15, 0);
            this.itemActionViewClassName = ((TypedArray)object).getString(16);
            this.itemActionProviderClassName = ((TypedArray)object).getString(17);
            if (this.itemActionProviderClassName == null) {
                bl2 = false;
            }
            if (bl2 && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider)this.newInstance(this.itemActionProviderClassName, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, MenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (bl2) {
                    Log.w(MenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = ((TypedArray)object).getText(13);
            this.itemTooltipText = ((TypedArray)object).getText(18);
            ((TypedArray)object).recycle();
            this.itemAdded = false;
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }
    }

}

