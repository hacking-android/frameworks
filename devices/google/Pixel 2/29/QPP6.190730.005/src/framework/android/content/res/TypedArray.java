/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.ComplexColor;
import android.content.res.Resources;
import android.content.res.XmlBlock;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Pools;
import android.util.TypedValue;
import com.android.internal.util.XmlUtils;
import dalvik.system.VMRuntime;
import java.util.Arrays;

public class TypedArray {
    static final int STYLE_ASSET_COOKIE = 2;
    static final int STYLE_CHANGING_CONFIGURATIONS = 4;
    static final int STYLE_DATA = 1;
    static final int STYLE_DENSITY = 5;
    static final int STYLE_NUM_ENTRIES = 7;
    static final int STYLE_RESOURCE_ID = 3;
    static final int STYLE_SOURCE_RESOURCE_ID = 6;
    static final int STYLE_TYPE = 0;
    @UnsupportedAppUsage
    private AssetManager mAssets;
    @UnsupportedAppUsage
    int[] mData;
    long mDataAddress;
    @UnsupportedAppUsage
    int[] mIndices;
    long mIndicesAddress;
    @UnsupportedAppUsage
    int mLength;
    @UnsupportedAppUsage
    private DisplayMetrics mMetrics;
    @UnsupportedAppUsage
    private boolean mRecycled;
    @UnsupportedAppUsage
    private final Resources mResources;
    @UnsupportedAppUsage
    Resources.Theme mTheme;
    @UnsupportedAppUsage
    TypedValue mValue = new TypedValue();
    @UnsupportedAppUsage
    XmlBlock.Parser mXml;

    protected TypedArray(Resources resources) {
        this.mResources = resources;
        this.mMetrics = this.mResources.getDisplayMetrics();
        this.mAssets = this.mResources.getAssets();
    }

    @UnsupportedAppUsage
    private boolean getValueAt(int n, TypedValue typedValue) {
        int[] arrn = this.mData;
        int n2 = arrn[n + 0];
        if (n2 == 0) {
            return false;
        }
        typedValue.type = n2;
        typedValue.data = arrn[n + 1];
        typedValue.assetCookie = arrn[n + 2];
        typedValue.resourceId = arrn[n + 3];
        typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(arrn[n + 4]);
        typedValue.density = arrn[n + 5];
        CharSequence charSequence = n2 == 3 ? this.loadStringValueAt(n) : null;
        typedValue.string = charSequence;
        typedValue.sourceResourceId = arrn[n + 6];
        return true;
    }

    private CharSequence loadStringValueAt(int n) {
        int[] arrn = this.mData;
        int n2 = arrn[n + 2];
        if (n2 < 0) {
            XmlBlock.Parser parser = this.mXml;
            if (parser != null) {
                return parser.getPooledString(arrn[n + 1]);
            }
            return null;
        }
        return this.mAssets.getPooledStringForCookie(n2, arrn[n + 1]);
    }

    static TypedArray obtain(Resources resources, int n) {
        TypedArray typedArray;
        TypedArray typedArray2 = typedArray = resources.mTypedArrayPool.acquire();
        if (typedArray == null) {
            typedArray2 = new TypedArray(resources);
        }
        typedArray2.mRecycled = false;
        typedArray2.mAssets = resources.getAssets();
        typedArray2.mMetrics = resources.getDisplayMetrics();
        typedArray2.resize(n);
        return typedArray2;
    }

    private void resize(int n) {
        this.mLength = n;
        int n2 = n * 7;
        VMRuntime vMRuntime = VMRuntime.getRuntime();
        if (this.mDataAddress == 0L || this.mData.length < n2) {
            this.mData = (int[])vMRuntime.newNonMovableArray(Integer.TYPE, n2);
            this.mDataAddress = vMRuntime.addressOf((Object)this.mData);
            this.mIndices = (int[])vMRuntime.newNonMovableArray(Integer.TYPE, n + 1);
            this.mIndicesAddress = vMRuntime.addressOf((Object)this.mIndices);
        }
    }

    @UnsupportedAppUsage
    public int[] extractThemeAttrs() {
        return this.extractThemeAttrs(null);
    }

    @UnsupportedAppUsage
    public int[] extractThemeAttrs(int[] arrn) {
        if (!this.mRecycled) {
            int[] arrn2 = null;
            int[] arrn3 = this.mData;
            int n = this.length();
            for (int i = 0; i < n; ++i) {
                int[] arrn4;
                int n2 = i * 7;
                if (arrn3[n2 + 0] != 2) {
                    arrn4 = arrn2;
                } else {
                    arrn3[n2 + 0] = 0;
                    if ((n2 = arrn3[n2 + 1]) == 0) {
                        arrn4 = arrn2;
                    } else {
                        arrn4 = arrn2;
                        if (arrn2 == null) {
                            if (arrn != null && arrn.length == n) {
                                arrn4 = arrn;
                                Arrays.fill(arrn4, 0);
                            } else {
                                arrn4 = new int[n];
                            }
                        }
                        arrn4[i] = n2;
                    }
                }
                arrn2 = arrn4;
            }
            return arrn2;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public boolean getBoolean(int n, boolean bl) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n2 = object[(n *= 7) + 0];
            if (n2 == 0) {
                return bl;
            }
            if (n2 >= 16 && n2 <= 31) {
                bl = object[n + 1] != 0;
                return bl;
            }
            object = this.mValue;
            if (this.getValueAt(n, (TypedValue)object)) {
                StrictMode.noteResourceMismatch(object);
                return XmlUtils.convertValueToBoolean(((TypedValue)object).coerceToString(), bl);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getBoolean of bad type: 0x");
            ((StringBuilder)object).append(Integer.toHexString(n2));
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getChangingConfigurations() {
        if (!this.mRecycled) {
            int n = 0;
            int[] arrn = this.mData;
            int n2 = this.length();
            for (int i = 0; i < n2; ++i) {
                int n3 = i * 7;
                if (arrn[n3 + 0] == 0) continue;
                n |= ActivityInfo.activityInfoConfigNativeToJava(arrn[n3 + 4]);
            }
            return n;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getColor(int n, int n2) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n3 = n * 7;
            int n4 = object[n3 + 0];
            if (n4 == 0) {
                return n2;
            }
            if (n4 >= 16 && n4 <= 31) {
                return object[n3 + 1];
            }
            if (n4 == 3) {
                object = this.mValue;
                if (this.getValueAt(n3, (TypedValue)object)) {
                    return this.mResources.loadColorStateList((TypedValue)object, ((TypedValue)object).resourceId, this.mTheme).getDefaultColor();
                }
                return n2;
            }
            if (n4 == 2) {
                object = this.mValue;
                this.getValueAt(n3, (TypedValue)object);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(object);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't convert value at index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to color: type=0x");
            ((StringBuilder)object).append(Integer.toHexString(n4));
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public ColorStateList getColorStateList(int n) {
        if (!this.mRecycled) {
            TypedValue typedValue = this.mValue;
            if (this.getValueAt(n * 7, typedValue)) {
                if (typedValue.type != 2) {
                    return this.mResources.loadColorStateList(typedValue, typedValue.resourceId, this.mTheme);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(typedValue);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public ComplexColor getComplexColor(int n) {
        if (!this.mRecycled) {
            TypedValue typedValue = this.mValue;
            if (this.getValueAt(n * 7, typedValue)) {
                if (typedValue.type != 2) {
                    return this.mResources.loadComplexColor(typedValue, typedValue.resourceId, this.mTheme);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(typedValue);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public float getDimension(int n, float f) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n2 = n * 7;
            int n3 = object[n2 + 0];
            if (n3 == 0) {
                return f;
            }
            if (n3 == 5) {
                return TypedValue.complexToDimension(object[n2 + 1], this.mMetrics);
            }
            if (n3 == 2) {
                object = this.mValue;
                this.getValueAt(n2, (TypedValue)object);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(object);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't convert value at index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to dimension: type=0x");
            ((StringBuilder)object).append(Integer.toHexString(n3));
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getDimensionPixelOffset(int n, int n2) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n3 = n * 7;
            int n4 = object[n3 + 0];
            if (n4 == 0) {
                return n2;
            }
            if (n4 == 5) {
                return TypedValue.complexToDimensionPixelOffset(object[n3 + 1], this.mMetrics);
            }
            if (n4 == 2) {
                TypedValue typedValue = this.mValue;
                this.getValueAt(n3, typedValue);
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to resolve attribute at index ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(typedValue);
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't convert value at index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to dimension: type=0x");
            ((StringBuilder)object).append(Integer.toHexString(n4));
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getDimensionPixelSize(int n, int n2) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n3 = n * 7;
            int n4 = object[n3 + 0];
            if (n4 == 0) {
                return n2;
            }
            if (n4 == 5) {
                return TypedValue.complexToDimensionPixelSize(object[n3 + 1], this.mMetrics);
            }
            if (n4 == 2) {
                TypedValue typedValue = this.mValue;
                this.getValueAt(n3, typedValue);
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to resolve attribute at index ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(typedValue);
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't convert value at index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to dimension: type=0x");
            ((StringBuilder)object).append(Integer.toHexString(n4));
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public Drawable getDrawable(int n) {
        return this.getDrawableForDensity(n, 0);
    }

    public Drawable getDrawableForDensity(int n, int n2) {
        if (!this.mRecycled) {
            TypedValue typedValue = this.mValue;
            if (this.getValueAt(n * 7, typedValue)) {
                if (typedValue.type != 2) {
                    if (n2 > 0) {
                        this.mResources.getValueForDensity(typedValue.resourceId, n2, typedValue, true);
                    }
                    return this.mResources.loadDrawable(typedValue, typedValue.resourceId, n2, this.mTheme);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(typedValue);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public float getFloat(int n, float f) {
        if (!this.mRecycled) {
            CharSequence charSequence;
            int n2 = n * 7;
            Object object = this.mData;
            n = object[n2 + 0];
            if (n == 0) {
                return f;
            }
            if (n == 4) {
                return Float.intBitsToFloat(object[n2 + 1]);
            }
            if (n >= 16 && n <= 31) {
                return object[n2 + 1];
            }
            object = this.mValue;
            if (this.getValueAt(n2, (TypedValue)object) && (charSequence = ((TypedValue)object).coerceToString()) != null) {
                StrictMode.noteResourceMismatch(object);
                return Float.parseFloat(charSequence.toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getFloat of bad type: 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public Typeface getFont(int n) {
        if (!this.mRecycled) {
            TypedValue typedValue = this.mValue;
            if (this.getValueAt(n * 7, typedValue)) {
                if (typedValue.type != 2) {
                    return this.mResources.getFont(typedValue, typedValue.resourceId);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(typedValue);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public float getFraction(int n, int n2, int n3, float f) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n4 = n * 7;
            int n5 = object[n4 + 0];
            if (n5 == 0) {
                return f;
            }
            if (n5 == 6) {
                return TypedValue.complexToFraction(object[n4 + 1], n2, n3);
            }
            if (n5 == 2) {
                object = this.mValue;
                this.getValueAt(n4, (TypedValue)object);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to resolve attribute at index ");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(object);
                throw new UnsupportedOperationException(stringBuilder.toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't convert value at index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to fraction: type=0x");
            ((StringBuilder)object).append(Integer.toHexString(n5));
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getIndex(int n) {
        if (!this.mRecycled) {
            return this.mIndices[n + 1];
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getIndexCount() {
        if (!this.mRecycled) {
            return this.mIndices[0];
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getInt(int n, int n2) {
        if (!this.mRecycled) {
            int n3 = n * 7;
            Object object = this.mData;
            n = object[n3 + 0];
            if (n == 0) {
                return n2;
            }
            if (n >= 16 && n <= 31) {
                return object[n3 + 1];
            }
            object = this.mValue;
            if (this.getValueAt(n3, (TypedValue)object)) {
                StrictMode.noteResourceMismatch(object);
                return XmlUtils.convertValueToInt(((TypedValue)object).coerceToString(), n2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getInt of bad type: 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getInteger(int n, int n2) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n3 = n * 7;
            int n4 = object[n3 + 0];
            if (n4 == 0) {
                return n2;
            }
            if (n4 >= 16 && n4 <= 31) {
                return object[n3 + 1];
            }
            if (n4 == 2) {
                TypedValue typedValue = this.mValue;
                this.getValueAt(n3, typedValue);
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to resolve attribute at index ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(typedValue);
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't convert value at index ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" to integer: type=0x");
            ((StringBuilder)object).append(Integer.toHexString(n4));
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getLayoutDimension(int n, int n2) {
        if (!this.mRecycled) {
            int[] arrn = this.mData;
            int n3 = arrn[(n *= 7) + 0];
            if (n3 >= 16 && n3 <= 31) {
                return arrn[n + 1];
            }
            if (n3 == 5) {
                return TypedValue.complexToDimensionPixelSize(arrn[n + 1], this.mMetrics);
            }
            return n2;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getLayoutDimension(int n, String charSequence) {
        if (!this.mRecycled) {
            Object object = this.mData;
            int n2 = n * 7;
            int n3 = object[n2 + 0];
            if (n3 >= 16 && n3 <= 31) {
                return object[n2 + 1];
            }
            if (n3 == 5) {
                return TypedValue.complexToDimensionPixelSize(object[n2 + 1], this.mMetrics);
            }
            if (n3 == 2) {
                object = this.mValue;
                this.getValueAt(n2, (TypedValue)object);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Failed to resolve attribute at index ");
                ((StringBuilder)charSequence).append(n);
                ((StringBuilder)charSequence).append(": ");
                ((StringBuilder)charSequence).append(object);
                throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(this.getPositionDescription());
            ((StringBuilder)object).append(": You must supply a ");
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(" attribute.");
            throw new UnsupportedOperationException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    @UnsupportedAppUsage
    public String getNonConfigurationString(int n, int n2) {
        if (!this.mRecycled) {
            int n3 = n * 7;
            Object object = this.mData;
            n = object[n3 + 0];
            int n4 = ActivityInfo.activityInfoConfigNativeToJava(object[n3 + 4]);
            object = null;
            if ((n2 & n4) != 0) {
                return null;
            }
            if (n == 0) {
                return null;
            }
            if (n == 3) {
                return this.loadStringValueAt(n3).toString();
            }
            Object object2 = this.mValue;
            if (this.getValueAt(n3, (TypedValue)object2)) {
                if ((object2 = ((TypedValue)object2).coerceToString()) != null) {
                    object = object2.toString();
                }
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getNonConfigurationString of bad type: 0x");
            ((StringBuilder)object).append(Integer.toHexString(n));
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public String getNonResourceString(int n) {
        if (!this.mRecycled) {
            int[] arrn = this.mData;
            if (arrn[(n *= 7) + 0] == 3 && arrn[n + 2] < 0) {
                return this.mXml.getPooledString(arrn[n + 1]).toString();
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public String getPositionDescription() {
        if (!this.mRecycled) {
            Object object = this.mXml;
            object = object != null ? ((XmlBlock.Parser)object).getPositionDescription() : "<internal>";
            return object;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getResourceId(int n, int n2) {
        if (!this.mRecycled) {
            int[] arrn = this.mData;
            if (arrn[(n *= 7) + 0] != 0 && (n = arrn[n + 3]) != 0) {
                return n;
            }
            return n2;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public Resources getResources() {
        if (!this.mRecycled) {
            return this.mResources;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getSourceResourceId(int n, int n2) {
        if (!this.mRecycled) {
            if ((n = this.mData[n * 7 + 6]) != 0) {
                return n;
            }
            return n2;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public String getString(int n) {
        if (!this.mRecycled) {
            int n2 = n * 7;
            n = this.mData[n2 + 0];
            CharSequence charSequence = null;
            if (n == 0) {
                return null;
            }
            if (n == 3) {
                return this.loadStringValueAt(n2).toString();
            }
            Object object = this.mValue;
            if (this.getValueAt(n2, (TypedValue)object)) {
                if ((object = ((TypedValue)object).coerceToString()) != null) {
                    charSequence = object.toString();
                }
                return charSequence;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("getString of bad type: 0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(n));
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public CharSequence getText(int n) {
        if (!this.mRecycled) {
            int n2 = this.mData[(n *= 7) + 0];
            if (n2 == 0) {
                return null;
            }
            if (n2 == 3) {
                return this.loadStringValueAt(n);
            }
            Object object = this.mValue;
            if (this.getValueAt(n, (TypedValue)object)) {
                return ((TypedValue)object).coerceToString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("getText of bad type: 0x");
            ((StringBuilder)object).append(Integer.toHexString(n2));
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public CharSequence[] getTextArray(int n) {
        if (!this.mRecycled) {
            TypedValue typedValue = this.mValue;
            if (this.getValueAt(n * 7, typedValue)) {
                return this.mResources.getTextArray(typedValue.resourceId);
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getThemeAttributeId(int n, int n2) {
        if (!this.mRecycled) {
            int[] arrn = this.mData;
            if (arrn[(n *= 7) + 0] == 2) {
                return arrn[n + 1];
            }
            return n2;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int getType(int n) {
        if (!this.mRecycled) {
            return this.mData[n * 7 + 0];
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public boolean getValue(int n, TypedValue typedValue) {
        if (!this.mRecycled) {
            return this.getValueAt(n * 7, typedValue);
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public boolean hasValue(int n) {
        if (!this.mRecycled) {
            boolean bl = this.mData[n * 7 + 0] != 0;
            return bl;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public boolean hasValueOrEmpty(int n) {
        if (!this.mRecycled) {
            boolean bl;
            int[] arrn = this.mData;
            int n2 = arrn[(n *= 7) + 0];
            boolean bl2 = bl = true;
            if (n2 == 0) {
                bl2 = arrn[n + 1] == 1 ? bl : false;
            }
            return bl2;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public int length() {
        if (!this.mRecycled) {
            return this.mLength;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public TypedValue peekValue(int n) {
        if (!this.mRecycled) {
            TypedValue typedValue = this.mValue;
            if (this.getValueAt(n * 7, typedValue)) {
                return typedValue;
            }
            return null;
        }
        throw new RuntimeException("Cannot make calls to a recycled instance!");
    }

    public void recycle() {
        if (!this.mRecycled) {
            this.mRecycled = true;
            this.mXml = null;
            this.mTheme = null;
            this.mAssets = null;
            this.mResources.mTypedArrayPool.release(this);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toString());
        stringBuilder.append(" recycled twice!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public String toString() {
        return Arrays.toString(this.mData);
    }
}

