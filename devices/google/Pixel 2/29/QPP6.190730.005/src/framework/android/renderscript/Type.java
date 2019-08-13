/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RenderScript;

public class Type
extends BaseObj {
    static final int mMaxArrays = 4;
    int[] mArrays;
    boolean mDimFaces;
    boolean mDimMipmaps;
    int mDimX;
    int mDimY;
    int mDimYuv;
    int mDimZ;
    Element mElement;
    int mElementCount;

    Type(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static Type createX(RenderScript object, Element element, int n) {
        if (n >= 1) {
            object = new Type(((RenderScript)object).nTypeCreate(element.getID((RenderScript)object), n, 0, 0, false, false, 0), (RenderScript)object);
            ((Type)object).mElement = element;
            ((Type)object).mDimX = n;
            ((Type)object).calcElementCount();
            return object;
        }
        throw new RSInvalidStateException("Dimension must be >= 1.");
    }

    public static Type createXY(RenderScript object, Element element, int n, int n2) {
        if (n >= 1 && n2 >= 1) {
            object = new Type(((RenderScript)object).nTypeCreate(element.getID((RenderScript)object), n, n2, 0, false, false, 0), (RenderScript)object);
            ((Type)object).mElement = element;
            ((Type)object).mDimX = n;
            ((Type)object).mDimY = n2;
            ((Type)object).calcElementCount();
            return object;
        }
        throw new RSInvalidStateException("Dimension must be >= 1.");
    }

    public static Type createXYZ(RenderScript object, Element element, int n, int n2, int n3) {
        if (n >= 1 && n2 >= 1 && n3 >= 1) {
            object = new Type(((RenderScript)object).nTypeCreate(element.getID((RenderScript)object), n, n2, n3, false, false, 0), (RenderScript)object);
            ((Type)object).mElement = element;
            ((Type)object).mDimX = n;
            ((Type)object).mDimY = n2;
            ((Type)object).mDimZ = n3;
            ((Type)object).calcElementCount();
            return object;
        }
        throw new RSInvalidStateException("Dimension must be >= 1.");
    }

    void calcElementCount() {
        boolean bl = this.hasMipmaps();
        int n = this.getX();
        int n2 = this.getY();
        int n3 = this.getZ();
        int n4 = 1;
        if (this.hasFaces()) {
            n4 = 6;
        }
        int n5 = n;
        if (n == 0) {
            n5 = 1;
        }
        n = n2;
        if (n2 == 0) {
            n = 1;
        }
        n2 = n3;
        if (n3 == 0) {
            n2 = 1;
        }
        n3 = n5 * n * n2 * n4;
        int n6 = n5;
        while (bl && (n6 > 1 || n > 1 || n2 > 1)) {
            n5 = n6;
            if (n6 > 1) {
                n5 = n6 >> 1;
            }
            int n7 = n;
            if (n > 1) {
                n7 = n >> 1;
            }
            int n8 = n2;
            if (n2 > 1) {
                n8 = n2 >> 1;
            }
            n3 += n5 * n7 * n8 * n4;
            n6 = n5;
            n = n7;
            n2 = n8;
        }
        n = n3;
        if (this.mArrays != null) {
            n5 = 0;
            do {
                int[] arrn = this.mArrays;
                n = n3;
                if (n5 >= arrn.length) break;
                n3 *= arrn[n5];
                ++n5;
            } while (true);
        }
        this.mElementCount = n;
    }

    public int getArray(int n) {
        if (n >= 0 && n < 4) {
            int[] arrn = this.mArrays;
            if (arrn != null && n < arrn.length) {
                return arrn[n];
            }
            return 0;
        }
        throw new RSIllegalArgumentException("Array dimension out of range.");
    }

    public int getArrayCount() {
        int[] arrn = this.mArrays;
        if (arrn != null) {
            return arrn.length;
        }
        return 0;
    }

    public int getCount() {
        return this.mElementCount;
    }

    public Element getElement() {
        return this.mElement;
    }

    public int getX() {
        return this.mDimX;
    }

    public int getY() {
        return this.mDimY;
    }

    public int getYuv() {
        return this.mDimYuv;
    }

    public int getZ() {
        return this.mDimZ;
    }

    public boolean hasFaces() {
        return this.mDimFaces;
    }

    public boolean hasMipmaps() {
        return this.mDimMipmaps;
    }

    @Override
    void updateFromNative() {
        long[] arrl = new long[6];
        this.mRS.nTypeGetNativeData(this.getID(this.mRS), arrl);
        boolean bl = false;
        this.mDimX = (int)arrl[0];
        this.mDimY = (int)arrl[1];
        this.mDimZ = (int)arrl[2];
        boolean bl2 = arrl[3] == 1L;
        this.mDimMipmaps = bl2;
        bl2 = bl;
        if (arrl[4] == 1L) {
            bl2 = true;
        }
        this.mDimFaces = bl2;
        long l = arrl[5];
        if (l != 0L) {
            this.mElement = new Element(l, this.mRS);
            this.mElement.updateFromNative();
        }
        this.calcElementCount();
    }

    public static class Builder {
        int[] mArray = new int[4];
        boolean mDimFaces;
        boolean mDimMipmaps;
        int mDimX = 1;
        int mDimY;
        int mDimZ;
        Element mElement;
        RenderScript mRS;
        int mYuv;

        public Builder(RenderScript renderScript, Element element) {
            element.checkValid();
            this.mRS = renderScript;
            this.mElement = element;
        }

        public Type create() {
            Object object;
            if (this.mDimZ > 0) {
                if (this.mDimX >= 1 && this.mDimY >= 1) {
                    if (this.mDimFaces) {
                        throw new RSInvalidStateException("Cube maps not supported with 3D types.");
                    }
                } else {
                    throw new RSInvalidStateException("Both X and Y dimension required when Z is present.");
                }
            }
            if (this.mDimY > 0 && this.mDimX < 1) {
                throw new RSInvalidStateException("X dimension required when Y is present.");
            }
            if (this.mDimFaces && this.mDimY < 1) {
                throw new RSInvalidStateException("Cube maps require 2D Types.");
            }
            if (this.mYuv != 0 && (this.mDimZ != 0 || this.mDimFaces || this.mDimMipmaps)) {
                throw new RSInvalidStateException("YUV only supports basic 2D.");
            }
            int[] arrn = null;
            for (int i = 3; i >= 0; --i) {
                object = arrn;
                if (this.mArray[i] != 0) {
                    object = arrn;
                    if (arrn == null) {
                        object = new int[i];
                    }
                }
                if (this.mArray[i] == 0 && object != null) {
                    throw new RSInvalidStateException("Array dimensions must be contigous from 0.");
                }
                arrn = object;
            }
            object = this.mRS;
            object = new Type(((RenderScript)object).nTypeCreate(this.mElement.getID((RenderScript)object), this.mDimX, this.mDimY, this.mDimZ, this.mDimMipmaps, this.mDimFaces, this.mYuv), this.mRS);
            ((Type)object).mElement = this.mElement;
            ((Type)object).mDimX = this.mDimX;
            ((Type)object).mDimY = this.mDimY;
            ((Type)object).mDimZ = this.mDimZ;
            ((Type)object).mDimMipmaps = this.mDimMipmaps;
            ((Type)object).mDimFaces = this.mDimFaces;
            ((Type)object).mDimYuv = this.mYuv;
            ((Type)object).mArrays = arrn;
            ((Type)object).calcElementCount();
            return object;
        }

        public Builder setArray(int n, int n2) {
            if (n >= 0 && n < 4) {
                this.mArray[n] = n2;
                return this;
            }
            throw new RSIllegalArgumentException("Array dimension out of range.");
        }

        public Builder setFaces(boolean bl) {
            this.mDimFaces = bl;
            return this;
        }

        public Builder setMipmaps(boolean bl) {
            this.mDimMipmaps = bl;
            return this;
        }

        public Builder setX(int n) {
            if (n >= 1) {
                this.mDimX = n;
                return this;
            }
            throw new RSIllegalArgumentException("Values of less than 1 for Dimension X are not valid.");
        }

        public Builder setY(int n) {
            if (n >= 1) {
                this.mDimY = n;
                return this;
            }
            throw new RSIllegalArgumentException("Values of less than 1 for Dimension Y are not valid.");
        }

        public Builder setYuvFormat(int n) {
            if (n != 17 && n != 35 && n != 842094169) {
                throw new RSIllegalArgumentException("Only ImageFormat.NV21, .YV12, and .YUV_420_888 are supported..");
            }
            this.mYuv = n;
            return this;
        }

        public Builder setZ(int n) {
            if (n >= 1) {
                this.mDimZ = n;
                return this;
            }
            throw new RSIllegalArgumentException("Values of less than 1 for Dimension Z are not valid.");
        }
    }

    public static enum CubemapFace {
        POSITIVE_X(0),
        NEGATIVE_X(1),
        POSITIVE_Y(2),
        NEGATIVE_Y(3),
        POSITIVE_Z(4),
        NEGATIVE_Z(5),
        POSITVE_X(0),
        POSITVE_Y(2),
        POSITVE_Z(4);
        
        int mID;

        private CubemapFace(int n2) {
            this.mID = n2;
        }
    }

}

