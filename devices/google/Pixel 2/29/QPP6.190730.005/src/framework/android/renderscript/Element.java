/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.BaseObj;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;

public class Element
extends BaseObj {
    int[] mArraySizes;
    String[] mElementNames;
    Element[] mElements;
    DataKind mKind;
    boolean mNormalized;
    int[] mOffsetInBytes;
    int mSize;
    DataType mType;
    int mVectorSize;
    int[] mVisibleElementMap;

    Element(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    Element(long l, RenderScript renderScript, DataType dataType, DataKind dataKind, boolean bl, int n) {
        super(l, renderScript);
        this.mSize = dataType != DataType.UNSIGNED_5_6_5 && dataType != DataType.UNSIGNED_4_4_4_4 && dataType != DataType.UNSIGNED_5_5_5_1 ? (n == 3 ? dataType.mSize * 4 : dataType.mSize * n) : dataType.mSize;
        this.mType = dataType;
        this.mKind = dataKind;
        this.mNormalized = bl;
        this.mVectorSize = n;
    }

    Element(long l, RenderScript arrelement, Element[] arrobject, String[] arrstring, int[] arrn) {
        super(l, (RenderScript)arrelement);
        this.mSize = 0;
        this.mVectorSize = 1;
        this.mElements = arrobject;
        this.mElementNames = arrstring;
        this.mArraySizes = arrn;
        this.mType = DataType.NONE;
        this.mKind = DataKind.USER;
        this.mOffsetInBytes = new int[this.mElements.length];
        for (int i = 0; i < (arrelement = this.mElements).length; ++i) {
            arrobject = this.mOffsetInBytes;
            int n = this.mSize;
            arrobject[i] = (Element)n;
            this.mSize = n + arrelement[i].mSize * this.mArraySizes[i];
        }
        this.updateVisibleSubElements();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element ALLOCATION(RenderScript renderScript) {
        if (renderScript.mElement_ALLOCATION != null) return renderScript.mElement_ALLOCATION;
        synchronized (renderScript) {
            if (renderScript.mElement_ALLOCATION != null) return renderScript.mElement_ALLOCATION;
            renderScript.mElement_ALLOCATION = Element.createUser(renderScript, DataType.RS_ALLOCATION);
            return renderScript.mElement_ALLOCATION;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element A_8(RenderScript renderScript) {
        if (renderScript.mElement_A_8 != null) return renderScript.mElement_A_8;
        synchronized (renderScript) {
            if (renderScript.mElement_A_8 != null) return renderScript.mElement_A_8;
            renderScript.mElement_A_8 = Element.createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_A);
            return renderScript.mElement_A_8;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element BOOLEAN(RenderScript renderScript) {
        if (renderScript.mElement_BOOLEAN != null) return renderScript.mElement_BOOLEAN;
        synchronized (renderScript) {
            if (renderScript.mElement_BOOLEAN != null) return renderScript.mElement_BOOLEAN;
            renderScript.mElement_BOOLEAN = Element.createUser(renderScript, DataType.BOOLEAN);
            return renderScript.mElement_BOOLEAN;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element ELEMENT(RenderScript renderScript) {
        if (renderScript.mElement_ELEMENT != null) return renderScript.mElement_ELEMENT;
        synchronized (renderScript) {
            if (renderScript.mElement_ELEMENT != null) return renderScript.mElement_ELEMENT;
            renderScript.mElement_ELEMENT = Element.createUser(renderScript, DataType.RS_ELEMENT);
            return renderScript.mElement_ELEMENT;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F16(RenderScript renderScript) {
        if (renderScript.mElement_F16 != null) return renderScript.mElement_F16;
        synchronized (renderScript) {
            if (renderScript.mElement_F16 != null) return renderScript.mElement_F16;
            renderScript.mElement_F16 = Element.createUser(renderScript, DataType.FLOAT_16);
            return renderScript.mElement_F16;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F16_2(RenderScript renderScript) {
        if (renderScript.mElement_HALF_2 != null) return renderScript.mElement_HALF_2;
        synchronized (renderScript) {
            if (renderScript.mElement_HALF_2 != null) return renderScript.mElement_HALF_2;
            renderScript.mElement_HALF_2 = Element.createVector(renderScript, DataType.FLOAT_16, 2);
            return renderScript.mElement_HALF_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F16_3(RenderScript renderScript) {
        if (renderScript.mElement_HALF_3 != null) return renderScript.mElement_HALF_3;
        synchronized (renderScript) {
            if (renderScript.mElement_HALF_3 != null) return renderScript.mElement_HALF_3;
            renderScript.mElement_HALF_3 = Element.createVector(renderScript, DataType.FLOAT_16, 3);
            return renderScript.mElement_HALF_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F16_4(RenderScript renderScript) {
        if (renderScript.mElement_HALF_4 != null) return renderScript.mElement_HALF_4;
        synchronized (renderScript) {
            if (renderScript.mElement_HALF_4 != null) return renderScript.mElement_HALF_4;
            renderScript.mElement_HALF_4 = Element.createVector(renderScript, DataType.FLOAT_16, 4);
            return renderScript.mElement_HALF_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F32(RenderScript renderScript) {
        if (renderScript.mElement_F32 != null) return renderScript.mElement_F32;
        synchronized (renderScript) {
            if (renderScript.mElement_F32 != null) return renderScript.mElement_F32;
            renderScript.mElement_F32 = Element.createUser(renderScript, DataType.FLOAT_32);
            return renderScript.mElement_F32;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F32_2(RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_2 != null) return renderScript.mElement_FLOAT_2;
        synchronized (renderScript) {
            if (renderScript.mElement_FLOAT_2 != null) return renderScript.mElement_FLOAT_2;
            renderScript.mElement_FLOAT_2 = Element.createVector(renderScript, DataType.FLOAT_32, 2);
            return renderScript.mElement_FLOAT_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F32_3(RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_3 != null) return renderScript.mElement_FLOAT_3;
        synchronized (renderScript) {
            if (renderScript.mElement_FLOAT_3 != null) return renderScript.mElement_FLOAT_3;
            renderScript.mElement_FLOAT_3 = Element.createVector(renderScript, DataType.FLOAT_32, 3);
            return renderScript.mElement_FLOAT_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F32_4(RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_4 != null) return renderScript.mElement_FLOAT_4;
        synchronized (renderScript) {
            if (renderScript.mElement_FLOAT_4 != null) return renderScript.mElement_FLOAT_4;
            renderScript.mElement_FLOAT_4 = Element.createVector(renderScript, DataType.FLOAT_32, 4);
            return renderScript.mElement_FLOAT_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F64(RenderScript renderScript) {
        if (renderScript.mElement_F64 != null) return renderScript.mElement_F64;
        synchronized (renderScript) {
            if (renderScript.mElement_F64 != null) return renderScript.mElement_F64;
            renderScript.mElement_F64 = Element.createUser(renderScript, DataType.FLOAT_64);
            return renderScript.mElement_F64;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F64_2(RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_2 != null) return renderScript.mElement_DOUBLE_2;
        synchronized (renderScript) {
            if (renderScript.mElement_DOUBLE_2 != null) return renderScript.mElement_DOUBLE_2;
            renderScript.mElement_DOUBLE_2 = Element.createVector(renderScript, DataType.FLOAT_64, 2);
            return renderScript.mElement_DOUBLE_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F64_3(RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_3 != null) return renderScript.mElement_DOUBLE_3;
        synchronized (renderScript) {
            if (renderScript.mElement_DOUBLE_3 != null) return renderScript.mElement_DOUBLE_3;
            renderScript.mElement_DOUBLE_3 = Element.createVector(renderScript, DataType.FLOAT_64, 3);
            return renderScript.mElement_DOUBLE_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element F64_4(RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_4 != null) return renderScript.mElement_DOUBLE_4;
        synchronized (renderScript) {
            if (renderScript.mElement_DOUBLE_4 != null) return renderScript.mElement_DOUBLE_4;
            renderScript.mElement_DOUBLE_4 = Element.createVector(renderScript, DataType.FLOAT_64, 4);
            return renderScript.mElement_DOUBLE_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element FONT(RenderScript renderScript) {
        if (renderScript.mElement_FONT != null) return renderScript.mElement_FONT;
        synchronized (renderScript) {
            if (renderScript.mElement_FONT != null) return renderScript.mElement_FONT;
            renderScript.mElement_FONT = Element.createUser(renderScript, DataType.RS_FONT);
            return renderScript.mElement_FONT;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I16(RenderScript renderScript) {
        if (renderScript.mElement_I16 != null) return renderScript.mElement_I16;
        synchronized (renderScript) {
            if (renderScript.mElement_I16 != null) return renderScript.mElement_I16;
            renderScript.mElement_I16 = Element.createUser(renderScript, DataType.SIGNED_16);
            return renderScript.mElement_I16;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I16_2(RenderScript renderScript) {
        if (renderScript.mElement_SHORT_2 != null) return renderScript.mElement_SHORT_2;
        synchronized (renderScript) {
            if (renderScript.mElement_SHORT_2 != null) return renderScript.mElement_SHORT_2;
            renderScript.mElement_SHORT_2 = Element.createVector(renderScript, DataType.SIGNED_16, 2);
            return renderScript.mElement_SHORT_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I16_3(RenderScript renderScript) {
        if (renderScript.mElement_SHORT_3 != null) return renderScript.mElement_SHORT_3;
        synchronized (renderScript) {
            if (renderScript.mElement_SHORT_3 != null) return renderScript.mElement_SHORT_3;
            renderScript.mElement_SHORT_3 = Element.createVector(renderScript, DataType.SIGNED_16, 3);
            return renderScript.mElement_SHORT_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I16_4(RenderScript renderScript) {
        if (renderScript.mElement_SHORT_4 != null) return renderScript.mElement_SHORT_4;
        synchronized (renderScript) {
            if (renderScript.mElement_SHORT_4 != null) return renderScript.mElement_SHORT_4;
            renderScript.mElement_SHORT_4 = Element.createVector(renderScript, DataType.SIGNED_16, 4);
            return renderScript.mElement_SHORT_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I32(RenderScript renderScript) {
        if (renderScript.mElement_I32 != null) return renderScript.mElement_I32;
        synchronized (renderScript) {
            if (renderScript.mElement_I32 != null) return renderScript.mElement_I32;
            renderScript.mElement_I32 = Element.createUser(renderScript, DataType.SIGNED_32);
            return renderScript.mElement_I32;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I32_2(RenderScript renderScript) {
        if (renderScript.mElement_INT_2 != null) return renderScript.mElement_INT_2;
        synchronized (renderScript) {
            if (renderScript.mElement_INT_2 != null) return renderScript.mElement_INT_2;
            renderScript.mElement_INT_2 = Element.createVector(renderScript, DataType.SIGNED_32, 2);
            return renderScript.mElement_INT_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I32_3(RenderScript renderScript) {
        if (renderScript.mElement_INT_3 != null) return renderScript.mElement_INT_3;
        synchronized (renderScript) {
            if (renderScript.mElement_INT_3 != null) return renderScript.mElement_INT_3;
            renderScript.mElement_INT_3 = Element.createVector(renderScript, DataType.SIGNED_32, 3);
            return renderScript.mElement_INT_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I32_4(RenderScript renderScript) {
        if (renderScript.mElement_INT_4 != null) return renderScript.mElement_INT_4;
        synchronized (renderScript) {
            if (renderScript.mElement_INT_4 != null) return renderScript.mElement_INT_4;
            renderScript.mElement_INT_4 = Element.createVector(renderScript, DataType.SIGNED_32, 4);
            return renderScript.mElement_INT_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I64(RenderScript renderScript) {
        if (renderScript.mElement_I64 != null) return renderScript.mElement_I64;
        synchronized (renderScript) {
            if (renderScript.mElement_I64 != null) return renderScript.mElement_I64;
            renderScript.mElement_I64 = Element.createUser(renderScript, DataType.SIGNED_64);
            return renderScript.mElement_I64;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I64_2(RenderScript renderScript) {
        if (renderScript.mElement_LONG_2 != null) return renderScript.mElement_LONG_2;
        synchronized (renderScript) {
            if (renderScript.mElement_LONG_2 != null) return renderScript.mElement_LONG_2;
            renderScript.mElement_LONG_2 = Element.createVector(renderScript, DataType.SIGNED_64, 2);
            return renderScript.mElement_LONG_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I64_3(RenderScript renderScript) {
        if (renderScript.mElement_LONG_3 != null) return renderScript.mElement_LONG_3;
        synchronized (renderScript) {
            if (renderScript.mElement_LONG_3 != null) return renderScript.mElement_LONG_3;
            renderScript.mElement_LONG_3 = Element.createVector(renderScript, DataType.SIGNED_64, 3);
            return renderScript.mElement_LONG_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I64_4(RenderScript renderScript) {
        if (renderScript.mElement_LONG_4 != null) return renderScript.mElement_LONG_4;
        synchronized (renderScript) {
            if (renderScript.mElement_LONG_4 != null) return renderScript.mElement_LONG_4;
            renderScript.mElement_LONG_4 = Element.createVector(renderScript, DataType.SIGNED_64, 4);
            return renderScript.mElement_LONG_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I8(RenderScript renderScript) {
        if (renderScript.mElement_I8 != null) return renderScript.mElement_I8;
        synchronized (renderScript) {
            if (renderScript.mElement_I8 != null) return renderScript.mElement_I8;
            renderScript.mElement_I8 = Element.createUser(renderScript, DataType.SIGNED_8);
            return renderScript.mElement_I8;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I8_2(RenderScript renderScript) {
        if (renderScript.mElement_CHAR_2 != null) return renderScript.mElement_CHAR_2;
        synchronized (renderScript) {
            if (renderScript.mElement_CHAR_2 != null) return renderScript.mElement_CHAR_2;
            renderScript.mElement_CHAR_2 = Element.createVector(renderScript, DataType.SIGNED_8, 2);
            return renderScript.mElement_CHAR_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I8_3(RenderScript renderScript) {
        if (renderScript.mElement_CHAR_3 != null) return renderScript.mElement_CHAR_3;
        synchronized (renderScript) {
            if (renderScript.mElement_CHAR_3 != null) return renderScript.mElement_CHAR_3;
            renderScript.mElement_CHAR_3 = Element.createVector(renderScript, DataType.SIGNED_8, 3);
            return renderScript.mElement_CHAR_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element I8_4(RenderScript renderScript) {
        if (renderScript.mElement_CHAR_4 != null) return renderScript.mElement_CHAR_4;
        synchronized (renderScript) {
            if (renderScript.mElement_CHAR_4 != null) return renderScript.mElement_CHAR_4;
            renderScript.mElement_CHAR_4 = Element.createVector(renderScript, DataType.SIGNED_8, 4);
            return renderScript.mElement_CHAR_4;
        }
    }

    public static Element MATRIX4X4(RenderScript renderScript) {
        return Element.MATRIX_4X4(renderScript);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element MATRIX_2X2(RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_2X2 != null) return renderScript.mElement_MATRIX_2X2;
        synchronized (renderScript) {
            if (renderScript.mElement_MATRIX_2X2 != null) return renderScript.mElement_MATRIX_2X2;
            renderScript.mElement_MATRIX_2X2 = Element.createUser(renderScript, DataType.MATRIX_2X2);
            return renderScript.mElement_MATRIX_2X2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element MATRIX_3X3(RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_3X3 != null) return renderScript.mElement_MATRIX_3X3;
        synchronized (renderScript) {
            if (renderScript.mElement_MATRIX_3X3 != null) return renderScript.mElement_MATRIX_3X3;
            renderScript.mElement_MATRIX_3X3 = Element.createUser(renderScript, DataType.MATRIX_3X3);
            return renderScript.mElement_MATRIX_3X3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element MATRIX_4X4(RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_4X4 != null) return renderScript.mElement_MATRIX_4X4;
        synchronized (renderScript) {
            if (renderScript.mElement_MATRIX_4X4 != null) return renderScript.mElement_MATRIX_4X4;
            renderScript.mElement_MATRIX_4X4 = Element.createUser(renderScript, DataType.MATRIX_4X4);
            return renderScript.mElement_MATRIX_4X4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element MESH(RenderScript renderScript) {
        if (renderScript.mElement_MESH != null) return renderScript.mElement_MESH;
        synchronized (renderScript) {
            if (renderScript.mElement_MESH != null) return renderScript.mElement_MESH;
            renderScript.mElement_MESH = Element.createUser(renderScript, DataType.RS_MESH);
            return renderScript.mElement_MESH;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element PROGRAM_FRAGMENT(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_FRAGMENT != null) return renderScript.mElement_PROGRAM_FRAGMENT;
        synchronized (renderScript) {
            if (renderScript.mElement_PROGRAM_FRAGMENT != null) return renderScript.mElement_PROGRAM_FRAGMENT;
            renderScript.mElement_PROGRAM_FRAGMENT = Element.createUser(renderScript, DataType.RS_PROGRAM_FRAGMENT);
            return renderScript.mElement_PROGRAM_FRAGMENT;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element PROGRAM_RASTER(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_RASTER != null) return renderScript.mElement_PROGRAM_RASTER;
        synchronized (renderScript) {
            if (renderScript.mElement_PROGRAM_RASTER != null) return renderScript.mElement_PROGRAM_RASTER;
            renderScript.mElement_PROGRAM_RASTER = Element.createUser(renderScript, DataType.RS_PROGRAM_RASTER);
            return renderScript.mElement_PROGRAM_RASTER;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element PROGRAM_STORE(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_STORE != null) return renderScript.mElement_PROGRAM_STORE;
        synchronized (renderScript) {
            if (renderScript.mElement_PROGRAM_STORE != null) return renderScript.mElement_PROGRAM_STORE;
            renderScript.mElement_PROGRAM_STORE = Element.createUser(renderScript, DataType.RS_PROGRAM_STORE);
            return renderScript.mElement_PROGRAM_STORE;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element PROGRAM_VERTEX(RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_VERTEX != null) return renderScript.mElement_PROGRAM_VERTEX;
        synchronized (renderScript) {
            if (renderScript.mElement_PROGRAM_VERTEX != null) return renderScript.mElement_PROGRAM_VERTEX;
            renderScript.mElement_PROGRAM_VERTEX = Element.createUser(renderScript, DataType.RS_PROGRAM_VERTEX);
            return renderScript.mElement_PROGRAM_VERTEX;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element RGBA_4444(RenderScript renderScript) {
        if (renderScript.mElement_RGBA_4444 != null) return renderScript.mElement_RGBA_4444;
        synchronized (renderScript) {
            if (renderScript.mElement_RGBA_4444 != null) return renderScript.mElement_RGBA_4444;
            renderScript.mElement_RGBA_4444 = Element.createPixel(renderScript, DataType.UNSIGNED_4_4_4_4, DataKind.PIXEL_RGBA);
            return renderScript.mElement_RGBA_4444;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element RGBA_5551(RenderScript renderScript) {
        if (renderScript.mElement_RGBA_5551 != null) return renderScript.mElement_RGBA_5551;
        synchronized (renderScript) {
            if (renderScript.mElement_RGBA_5551 != null) return renderScript.mElement_RGBA_5551;
            renderScript.mElement_RGBA_5551 = Element.createPixel(renderScript, DataType.UNSIGNED_5_5_5_1, DataKind.PIXEL_RGBA);
            return renderScript.mElement_RGBA_5551;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element RGBA_8888(RenderScript renderScript) {
        if (renderScript.mElement_RGBA_8888 != null) return renderScript.mElement_RGBA_8888;
        synchronized (renderScript) {
            if (renderScript.mElement_RGBA_8888 != null) return renderScript.mElement_RGBA_8888;
            renderScript.mElement_RGBA_8888 = Element.createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_RGBA);
            return renderScript.mElement_RGBA_8888;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element RGB_565(RenderScript renderScript) {
        if (renderScript.mElement_RGB_565 != null) return renderScript.mElement_RGB_565;
        synchronized (renderScript) {
            if (renderScript.mElement_RGB_565 != null) return renderScript.mElement_RGB_565;
            renderScript.mElement_RGB_565 = Element.createPixel(renderScript, DataType.UNSIGNED_5_6_5, DataKind.PIXEL_RGB);
            return renderScript.mElement_RGB_565;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element RGB_888(RenderScript renderScript) {
        if (renderScript.mElement_RGB_888 != null) return renderScript.mElement_RGB_888;
        synchronized (renderScript) {
            if (renderScript.mElement_RGB_888 != null) return renderScript.mElement_RGB_888;
            renderScript.mElement_RGB_888 = Element.createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_RGB);
            return renderScript.mElement_RGB_888;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element SAMPLER(RenderScript renderScript) {
        if (renderScript.mElement_SAMPLER != null) return renderScript.mElement_SAMPLER;
        synchronized (renderScript) {
            if (renderScript.mElement_SAMPLER != null) return renderScript.mElement_SAMPLER;
            renderScript.mElement_SAMPLER = Element.createUser(renderScript, DataType.RS_SAMPLER);
            return renderScript.mElement_SAMPLER;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element SCRIPT(RenderScript renderScript) {
        if (renderScript.mElement_SCRIPT != null) return renderScript.mElement_SCRIPT;
        synchronized (renderScript) {
            if (renderScript.mElement_SCRIPT != null) return renderScript.mElement_SCRIPT;
            renderScript.mElement_SCRIPT = Element.createUser(renderScript, DataType.RS_SCRIPT);
            return renderScript.mElement_SCRIPT;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element TYPE(RenderScript renderScript) {
        if (renderScript.mElement_TYPE != null) return renderScript.mElement_TYPE;
        synchronized (renderScript) {
            if (renderScript.mElement_TYPE != null) return renderScript.mElement_TYPE;
            renderScript.mElement_TYPE = Element.createUser(renderScript, DataType.RS_TYPE);
            return renderScript.mElement_TYPE;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U16(RenderScript renderScript) {
        if (renderScript.mElement_U16 != null) return renderScript.mElement_U16;
        synchronized (renderScript) {
            if (renderScript.mElement_U16 != null) return renderScript.mElement_U16;
            renderScript.mElement_U16 = Element.createUser(renderScript, DataType.UNSIGNED_16);
            return renderScript.mElement_U16;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U16_2(RenderScript renderScript) {
        if (renderScript.mElement_USHORT_2 != null) return renderScript.mElement_USHORT_2;
        synchronized (renderScript) {
            if (renderScript.mElement_USHORT_2 != null) return renderScript.mElement_USHORT_2;
            renderScript.mElement_USHORT_2 = Element.createVector(renderScript, DataType.UNSIGNED_16, 2);
            return renderScript.mElement_USHORT_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U16_3(RenderScript renderScript) {
        if (renderScript.mElement_USHORT_3 != null) return renderScript.mElement_USHORT_3;
        synchronized (renderScript) {
            if (renderScript.mElement_USHORT_3 != null) return renderScript.mElement_USHORT_3;
            renderScript.mElement_USHORT_3 = Element.createVector(renderScript, DataType.UNSIGNED_16, 3);
            return renderScript.mElement_USHORT_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U16_4(RenderScript renderScript) {
        if (renderScript.mElement_USHORT_4 != null) return renderScript.mElement_USHORT_4;
        synchronized (renderScript) {
            if (renderScript.mElement_USHORT_4 != null) return renderScript.mElement_USHORT_4;
            renderScript.mElement_USHORT_4 = Element.createVector(renderScript, DataType.UNSIGNED_16, 4);
            return renderScript.mElement_USHORT_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U32(RenderScript renderScript) {
        if (renderScript.mElement_U32 != null) return renderScript.mElement_U32;
        synchronized (renderScript) {
            if (renderScript.mElement_U32 != null) return renderScript.mElement_U32;
            renderScript.mElement_U32 = Element.createUser(renderScript, DataType.UNSIGNED_32);
            return renderScript.mElement_U32;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U32_2(RenderScript renderScript) {
        if (renderScript.mElement_UINT_2 != null) return renderScript.mElement_UINT_2;
        synchronized (renderScript) {
            if (renderScript.mElement_UINT_2 != null) return renderScript.mElement_UINT_2;
            renderScript.mElement_UINT_2 = Element.createVector(renderScript, DataType.UNSIGNED_32, 2);
            return renderScript.mElement_UINT_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U32_3(RenderScript renderScript) {
        if (renderScript.mElement_UINT_3 != null) return renderScript.mElement_UINT_3;
        synchronized (renderScript) {
            if (renderScript.mElement_UINT_3 != null) return renderScript.mElement_UINT_3;
            renderScript.mElement_UINT_3 = Element.createVector(renderScript, DataType.UNSIGNED_32, 3);
            return renderScript.mElement_UINT_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U32_4(RenderScript renderScript) {
        if (renderScript.mElement_UINT_4 != null) return renderScript.mElement_UINT_4;
        synchronized (renderScript) {
            if (renderScript.mElement_UINT_4 != null) return renderScript.mElement_UINT_4;
            renderScript.mElement_UINT_4 = Element.createVector(renderScript, DataType.UNSIGNED_32, 4);
            return renderScript.mElement_UINT_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U64(RenderScript renderScript) {
        if (renderScript.mElement_U64 != null) return renderScript.mElement_U64;
        synchronized (renderScript) {
            if (renderScript.mElement_U64 != null) return renderScript.mElement_U64;
            renderScript.mElement_U64 = Element.createUser(renderScript, DataType.UNSIGNED_64);
            return renderScript.mElement_U64;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U64_2(RenderScript renderScript) {
        if (renderScript.mElement_ULONG_2 != null) return renderScript.mElement_ULONG_2;
        synchronized (renderScript) {
            if (renderScript.mElement_ULONG_2 != null) return renderScript.mElement_ULONG_2;
            renderScript.mElement_ULONG_2 = Element.createVector(renderScript, DataType.UNSIGNED_64, 2);
            return renderScript.mElement_ULONG_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U64_3(RenderScript renderScript) {
        if (renderScript.mElement_ULONG_3 != null) return renderScript.mElement_ULONG_3;
        synchronized (renderScript) {
            if (renderScript.mElement_ULONG_3 != null) return renderScript.mElement_ULONG_3;
            renderScript.mElement_ULONG_3 = Element.createVector(renderScript, DataType.UNSIGNED_64, 3);
            return renderScript.mElement_ULONG_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U64_4(RenderScript renderScript) {
        if (renderScript.mElement_ULONG_4 != null) return renderScript.mElement_ULONG_4;
        synchronized (renderScript) {
            if (renderScript.mElement_ULONG_4 != null) return renderScript.mElement_ULONG_4;
            renderScript.mElement_ULONG_4 = Element.createVector(renderScript, DataType.UNSIGNED_64, 4);
            return renderScript.mElement_ULONG_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U8(RenderScript renderScript) {
        if (renderScript.mElement_U8 != null) return renderScript.mElement_U8;
        synchronized (renderScript) {
            if (renderScript.mElement_U8 != null) return renderScript.mElement_U8;
            renderScript.mElement_U8 = Element.createUser(renderScript, DataType.UNSIGNED_8);
            return renderScript.mElement_U8;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U8_2(RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_2 != null) return renderScript.mElement_UCHAR_2;
        synchronized (renderScript) {
            if (renderScript.mElement_UCHAR_2 != null) return renderScript.mElement_UCHAR_2;
            renderScript.mElement_UCHAR_2 = Element.createVector(renderScript, DataType.UNSIGNED_8, 2);
            return renderScript.mElement_UCHAR_2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U8_3(RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_3 != null) return renderScript.mElement_UCHAR_3;
        synchronized (renderScript) {
            if (renderScript.mElement_UCHAR_3 != null) return renderScript.mElement_UCHAR_3;
            renderScript.mElement_UCHAR_3 = Element.createVector(renderScript, DataType.UNSIGNED_8, 3);
            return renderScript.mElement_UCHAR_3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element U8_4(RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_4 != null) return renderScript.mElement_UCHAR_4;
        synchronized (renderScript) {
            if (renderScript.mElement_UCHAR_4 != null) return renderScript.mElement_UCHAR_4;
            renderScript.mElement_UCHAR_4 = Element.createVector(renderScript, DataType.UNSIGNED_8, 4);
            return renderScript.mElement_UCHAR_4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Element YUV(RenderScript renderScript) {
        if (renderScript.mElement_YUV != null) return renderScript.mElement_YUV;
        synchronized (renderScript) {
            if (renderScript.mElement_YUV != null) return renderScript.mElement_YUV;
            renderScript.mElement_YUV = Element.createPixel(renderScript, DataType.UNSIGNED_8, DataKind.PIXEL_YUV);
            return renderScript.mElement_YUV;
        }
    }

    public static Element createPixel(RenderScript renderScript, DataType dataType, DataKind dataKind) {
        if (dataKind != DataKind.PIXEL_L && dataKind != DataKind.PIXEL_A && dataKind != DataKind.PIXEL_LA && dataKind != DataKind.PIXEL_RGB && dataKind != DataKind.PIXEL_RGBA && dataKind != DataKind.PIXEL_DEPTH && dataKind != DataKind.PIXEL_YUV) {
            throw new RSIllegalArgumentException("Unsupported DataKind");
        }
        if (dataType != DataType.UNSIGNED_8 && dataType != DataType.UNSIGNED_16 && dataType != DataType.UNSIGNED_5_6_5 && dataType != DataType.UNSIGNED_4_4_4_4 && dataType != DataType.UNSIGNED_5_5_5_1) {
            throw new RSIllegalArgumentException("Unsupported DataType");
        }
        if (dataType == DataType.UNSIGNED_5_6_5 && dataKind != DataKind.PIXEL_RGB) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == DataType.UNSIGNED_5_5_5_1 && dataKind != DataKind.PIXEL_RGBA) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == DataType.UNSIGNED_4_4_4_4 && dataKind != DataKind.PIXEL_RGBA) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == DataType.UNSIGNED_16 && dataKind != DataKind.PIXEL_DEPTH) {
            throw new RSIllegalArgumentException("Bad kind and type combo");
        }
        int n = 1;
        int n2 = 1.$SwitchMap$android$renderscript$Element$DataKind[dataKind.ordinal()];
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 == 4) {
                        n = 2;
                    }
                } else {
                    n = 4;
                }
            } else {
                n = 3;
            }
        } else {
            n = 2;
        }
        return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, true, n), renderScript, dataType, dataKind, true, n);
    }

    @UnsupportedAppUsage
    static Element createUser(RenderScript renderScript, DataType dataType) {
        DataKind dataKind = DataKind.USER;
        return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, false, 1), renderScript, dataType, dataKind, false, 1);
    }

    public static Element createVector(RenderScript renderScript, DataType dataType, int n) {
        if (n >= 2 && n <= 4) {
            switch (dataType) {
                default: {
                    throw new RSIllegalArgumentException("Cannot create vector of non-primitive type.");
                }
                case FLOAT_16: 
                case FLOAT_32: 
                case FLOAT_64: 
                case SIGNED_8: 
                case SIGNED_16: 
                case SIGNED_32: 
                case SIGNED_64: 
                case UNSIGNED_8: 
                case UNSIGNED_16: 
                case UNSIGNED_32: 
                case UNSIGNED_64: 
                case BOOLEAN: 
            }
            DataKind dataKind = DataKind.USER;
            return new Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, false, n), renderScript, dataType, dataKind, false, n);
        }
        throw new RSIllegalArgumentException("Vector size out of range 2-4.");
    }

    private void updateVisibleSubElements() {
        int n;
        int n2;
        if (this.mElements == null) {
            return;
        }
        int n3 = 0;
        int n4 = this.mElementNames.length;
        for (n = 0; n < n4; ++n) {
            n2 = n3;
            if (this.mElementNames[n].charAt(0) != '#') {
                n2 = n3 + 1;
            }
            n3 = n2;
        }
        this.mVisibleElementMap = new int[n3];
        n3 = 0;
        for (n2 = 0; n2 < n4; ++n2) {
            n = n3;
            if (this.mElementNames[n2].charAt(0) != '#') {
                this.mVisibleElementMap[n3] = n2;
                n = n3 + 1;
            }
            n3 = n;
        }
    }

    public int getBytesSize() {
        return this.mSize;
    }

    public DataKind getDataKind() {
        return this.mKind;
    }

    public DataType getDataType() {
        return this.mType;
    }

    public Element getSubElement(int n) {
        int[] arrn = this.mVisibleElementMap;
        if (arrn != null) {
            if (n >= 0 && n < arrn.length) {
                return this.mElements[arrn[n]];
            }
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        throw new RSIllegalArgumentException("Element contains no sub-elements");
    }

    public int getSubElementArraySize(int n) {
        int[] arrn = this.mVisibleElementMap;
        if (arrn != null) {
            if (n >= 0 && n < arrn.length) {
                return this.mArraySizes[arrn[n]];
            }
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        throw new RSIllegalArgumentException("Element contains no sub-elements");
    }

    public int getSubElementCount() {
        int[] arrn = this.mVisibleElementMap;
        if (arrn == null) {
            return 0;
        }
        return arrn.length;
    }

    public String getSubElementName(int n) {
        int[] arrn = this.mVisibleElementMap;
        if (arrn != null) {
            if (n >= 0 && n < arrn.length) {
                return this.mElementNames[arrn[n]];
            }
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        throw new RSIllegalArgumentException("Element contains no sub-elements");
    }

    public int getSubElementOffsetBytes(int n) {
        int[] arrn = this.mVisibleElementMap;
        if (arrn != null) {
            if (n >= 0 && n < arrn.length) {
                return this.mOffsetInBytes[arrn[n]];
            }
            throw new RSIllegalArgumentException("Illegal sub-element index");
        }
        throw new RSIllegalArgumentException("Element contains no sub-elements");
    }

    public int getVectorSize() {
        return this.mVectorSize;
    }

    public boolean isCompatible(Element element) {
        boolean bl = this.equals(element);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (this.mSize != element.mSize || this.mType == DataType.NONE || this.mType != element.mType || this.mVectorSize != element.mVectorSize) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean isComplex() {
        Element[] arrelement;
        if (this.mElements == null) {
            return false;
        }
        for (int i = 0; i < (arrelement = this.mElements).length; ++i) {
            if (arrelement[i].mElements == null) continue;
            return true;
        }
        return false;
    }

    @Override
    void updateFromNative() {
        super.updateFromNative();
        int[] arrn = new int[5];
        this.mRS.nElementGetNativeData(this.getID(this.mRS), arrn);
        int n = arrn[2];
        int n2 = 0;
        boolean bl = n == 1;
        this.mNormalized = bl;
        this.mVectorSize = arrn[3];
        this.mSize = 0;
        for (DataType arrl2 : DataType.values()) {
            if (arrl2.mID != arrn[0]) continue;
            this.mType = arrl2;
            this.mSize = this.mType.mSize * this.mVectorSize;
        }
        Enum[] arrenum = DataKind.values();
        int n3 = arrenum.length;
        for (n = n2; n < n3; ++n) {
            Enum enum_ = arrenum[n];
            if (((DataKind)enum_).mID != arrn[1]) continue;
            this.mKind = enum_;
        }
        n2 = arrn[4];
        if (n2 > 0) {
            this.mElements = new Element[n2];
            this.mElementNames = new String[n2];
            this.mArraySizes = new int[n2];
            this.mOffsetInBytes = new int[n2];
            long[] arrl = new long[n2];
            this.mRS.nElementGetSubElements(this.getID(this.mRS), arrl, this.mElementNames, this.mArraySizes);
            for (n = 0; n < n2; ++n) {
                this.mElements[n] = new Element(arrl[n], this.mRS);
                this.mElements[n].updateFromNative();
                arrn = this.mOffsetInBytes;
                arrn[n] = n3 = this.mSize;
                this.mSize = n3 + this.mElements[n].mSize * this.mArraySizes[n];
            }
        }
        this.updateVisibleSubElements();
    }

    public static class Builder {
        int[] mArraySizes;
        int mCount;
        String[] mElementNames;
        Element[] mElements;
        RenderScript mRS;
        int mSkipPadding;

        public Builder(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mCount = 0;
            this.mElements = new Element[8];
            this.mElementNames = new String[8];
            this.mArraySizes = new int[8];
        }

        public Builder add(Element element, String string2) {
            return this.add(element, string2, 1);
        }

        public Builder add(Element element, String string2, int n) {
            if (n >= 1) {
                Object[] arrobject;
                if (this.mSkipPadding != 0 && string2.startsWith("#padding_")) {
                    this.mSkipPadding = 0;
                    return this;
                }
                this.mSkipPadding = element.mVectorSize == 3 ? 1 : 0;
                int n2 = this.mCount;
                Element[] arrelement = this.mElements;
                if (n2 == arrelement.length) {
                    Element[] arrelement2 = new Element[n2 + 8];
                    arrobject = new String[n2 + 8];
                    int[] arrn = new int[n2 + 8];
                    System.arraycopy(arrelement, 0, arrelement2, 0, n2);
                    System.arraycopy(this.mElementNames, 0, arrobject, 0, this.mCount);
                    System.arraycopy(this.mArraySizes, 0, arrn, 0, this.mCount);
                    this.mElements = arrelement2;
                    this.mElementNames = arrobject;
                    this.mArraySizes = arrn;
                }
                arrobject = this.mElements;
                n2 = this.mCount;
                arrobject[n2] = element;
                this.mElementNames[n2] = string2;
                this.mArraySizes[n2] = n;
                this.mCount = n2 + 1;
                return this;
            }
            throw new RSIllegalArgumentException("Array size cannot be less than 1.");
        }

        public Element create() {
            this.mRS.validate();
            int n = this.mCount;
            Element[] arrelement = new Element[n];
            String[] arrstring = new String[n];
            int[] arrn = new int[n];
            System.arraycopy(this.mElements, 0, arrelement, 0, n);
            System.arraycopy(this.mElementNames, 0, arrstring, 0, this.mCount);
            System.arraycopy(this.mArraySizes, 0, arrn, 0, this.mCount);
            long[] arrl = new long[arrelement.length];
            for (n = 0; n < arrelement.length; ++n) {
                arrl[n] = arrelement[n].getID(this.mRS);
            }
            return new Element(this.mRS.nElementCreate2(arrl, arrstring, arrn), this.mRS, arrelement, arrstring, arrn);
        }
    }

    public static enum DataKind {
        USER(0),
        PIXEL_L(7),
        PIXEL_A(8),
        PIXEL_LA(9),
        PIXEL_RGB(10),
        PIXEL_RGBA(11),
        PIXEL_DEPTH(12),
        PIXEL_YUV(13);
        
        int mID;

        private DataKind(int n2) {
            this.mID = n2;
        }
    }

    public static enum DataType {
        NONE(0, 0),
        FLOAT_16(1, 2),
        FLOAT_32(2, 4),
        FLOAT_64(3, 8),
        SIGNED_8(4, 1),
        SIGNED_16(5, 2),
        SIGNED_32(6, 4),
        SIGNED_64(7, 8),
        UNSIGNED_8(8, 1),
        UNSIGNED_16(9, 2),
        UNSIGNED_32(10, 4),
        UNSIGNED_64(11, 8),
        BOOLEAN(12, 1),
        UNSIGNED_5_6_5(13, 2),
        UNSIGNED_5_5_5_1(14, 2),
        UNSIGNED_4_4_4_4(15, 2),
        MATRIX_4X4(16, 64),
        MATRIX_3X3(17, 36),
        MATRIX_2X2(18, 16),
        RS_ELEMENT(1000),
        RS_TYPE(1001),
        RS_ALLOCATION(1002),
        RS_SAMPLER(1003),
        RS_SCRIPT(1004),
        RS_MESH(1005),
        RS_PROGRAM_FRAGMENT(1006),
        RS_PROGRAM_VERTEX(1007),
        RS_PROGRAM_RASTER(1008),
        RS_PROGRAM_STORE(1009),
        RS_FONT(1010);
        
        int mID;
        int mSize;

        private DataType(int n2) {
            this.mID = n2;
            this.mSize = 4;
            if (RenderScript.sPointerSize == 8) {
                this.mSize = 32;
            }
        }

        private DataType(int n2, int n3) {
            this.mID = n2;
            this.mSize = n3;
        }
    }

}

