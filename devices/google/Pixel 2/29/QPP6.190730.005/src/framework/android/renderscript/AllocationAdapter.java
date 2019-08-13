/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Type;

public class AllocationAdapter
extends Allocation {
    Type mWindow;

    AllocationAdapter(long l, RenderScript renderScript, Allocation allocation, Type type) {
        super(l, renderScript, allocation.mType, allocation.mUsage);
        this.mAdaptedAllocation = allocation;
        this.mWindow = type;
    }

    public static AllocationAdapter create1D(RenderScript renderScript, Allocation allocation) {
        renderScript.validate();
        return AllocationAdapter.createTyped(renderScript, allocation, Type.createX(renderScript, allocation.getElement(), allocation.getType().getX()));
    }

    public static AllocationAdapter create2D(RenderScript renderScript, Allocation allocation) {
        renderScript.validate();
        return AllocationAdapter.createTyped(renderScript, allocation, Type.createXY(renderScript, allocation.getElement(), allocation.getType().getX(), allocation.getType().getY()));
    }

    public static AllocationAdapter createTyped(RenderScript renderScript, Allocation allocation, Type type) {
        renderScript.validate();
        if (allocation.mAdaptedAllocation == null) {
            if (allocation.getType().getElement().equals(type.getElement())) {
                if (!type.hasFaces() && !type.hasMipmaps()) {
                    Type type2 = allocation.getType();
                    if (type.getX() <= type2.getX() && type.getY() <= type2.getY() && type.getZ() <= type2.getZ() && type.getArrayCount() <= type2.getArrayCount()) {
                        long l;
                        if (type.getArrayCount() > 0) {
                            for (int i = 0; i < type.getArray(i); ++i) {
                                if (type.getArray(i) <= type2.getArray(i)) {
                                    continue;
                                }
                                throw new RSInvalidStateException("Type cannot have dimension larger than the source allocation.");
                            }
                        }
                        if ((l = renderScript.nAllocationAdapterCreate(allocation.getID(renderScript), type.getID(renderScript))) != 0L) {
                            return new AllocationAdapter(l, renderScript, allocation, type);
                        }
                        throw new RSRuntimeException("AllocationAdapter creation failed.");
                    }
                    throw new RSInvalidStateException("Type cannot have dimension larger than the source allocation.");
                }
                throw new RSInvalidStateException("Adapters do not support window types with Mipmaps or Faces.");
            }
            throw new RSInvalidStateException("Element must match Allocation type.");
        }
        throw new RSInvalidStateException("Adapters cannot be nested.");
    }

    private void updateOffsets() {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = n = 0;
        if (this.mSelectedArray != null) {
            if (this.mSelectedArray.length > 0) {
                n3 = this.mSelectedArray[0];
            }
            if (this.mSelectedArray.length > 1) {
                n5 = this.mSelectedArray[2];
            }
            if (this.mSelectedArray.length > 2) {
                n7 = this.mSelectedArray[2];
            }
            n2 = n3;
            n4 = n5;
            n6 = n7;
            n8 = n;
            if (this.mSelectedArray.length > 3) {
                n8 = this.mSelectedArray[3];
                n6 = n7;
                n4 = n5;
                n2 = n3;
            }
        }
        this.mRS.nAllocationAdapterOffset(this.getID(this.mRS), this.mSelectedX, this.mSelectedY, this.mSelectedZ, this.mSelectedLOD, this.mSelectedFace.mID, n2, n4, n6, n8);
    }

    void initLOD(int n) {
        if (n >= 0) {
            int n2 = this.mAdaptedAllocation.mType.getX();
            int n3 = this.mAdaptedAllocation.mType.getY();
            int n4 = this.mAdaptedAllocation.mType.getZ();
            for (int i = 0; i < n; ++i) {
                if (n2 == 1 && n3 == 1 && n4 == 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Attempting to set lod (");
                    stringBuilder.append(n);
                    stringBuilder.append(") out of range.");
                    throw new RSIllegalArgumentException(stringBuilder.toString());
                }
                int n5 = n2;
                if (n2 > 1) {
                    n5 = n2 >> 1;
                }
                int n6 = n3;
                if (n3 > 1) {
                    n6 = n3 >> 1;
                }
                int n7 = n4;
                if (n4 > 1) {
                    n7 = n4 >> 1;
                }
                n2 = n5;
                n3 = n6;
                n4 = n7;
            }
            this.mCurrentDimX = n2;
            this.mCurrentDimY = n3;
            this.mCurrentDimZ = n4;
            this.mCurrentCount = this.mCurrentDimX;
            if (this.mCurrentDimY > 1) {
                this.mCurrentCount *= this.mCurrentDimY;
            }
            if (this.mCurrentDimZ > 1) {
                this.mCurrentCount *= this.mCurrentDimZ;
            }
            this.mSelectedY = 0;
            this.mSelectedZ = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to set negative lod (");
        stringBuilder.append(n);
        stringBuilder.append(").");
        throw new RSIllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void resize(int n) {
        synchronized (this) {
            RSInvalidStateException rSInvalidStateException = new RSInvalidStateException("Resize not allowed for Adapters.");
            throw rSInvalidStateException;
        }
    }

    public void setArray(int n, int n2) {
        if (this.mAdaptedAllocation.getType().getArray(n) != 0) {
            if (this.mAdaptedAllocation.getType().getArray(n) > n2) {
                if (this.mWindow.getArray(n) != this.mAdaptedAllocation.getType().getArray(n)) {
                    if (this.mWindow.getArray(n) + n2 < this.mAdaptedAllocation.getType().getArray(n)) {
                        this.mSelectedArray[n] = n2;
                        this.updateOffsets();
                        return;
                    }
                    throw new RSInvalidStateException("Cannot set (arrayNum + window) which would be larger than dimension of allocation.");
                }
                throw new RSInvalidStateException("Cannot set arrayNum when the adapter includes arrayNum.");
            }
            throw new RSInvalidStateException("Cannot set arrayNum greater than dimension of allocation.");
        }
        throw new RSInvalidStateException("Cannot set arrayNum when the allocation type does not include arrayNum dim.");
    }

    public void setFace(Type.CubemapFace cubemapFace) {
        if (this.mAdaptedAllocation.getType().hasFaces()) {
            if (!this.mWindow.hasFaces()) {
                if (cubemapFace != null) {
                    this.mSelectedFace = cubemapFace;
                    this.updateOffsets();
                    return;
                }
                throw new RSIllegalArgumentException("Cannot set null face.");
            }
            throw new RSInvalidStateException("Cannot set face when the adapter includes faces.");
        }
        throw new RSInvalidStateException("Cannot set Face when the allocation type does not include faces.");
    }

    public void setLOD(int n) {
        if (this.mAdaptedAllocation.getType().hasMipmaps()) {
            if (!this.mWindow.hasMipmaps()) {
                this.initLOD(n);
                this.mSelectedLOD = n;
                this.updateOffsets();
                return;
            }
            throw new RSInvalidStateException("Cannot set LOD when the adapter includes mipmaps.");
        }
        throw new RSInvalidStateException("Cannot set LOD when the allocation type does not include mipmaps.");
    }

    public void setX(int n) {
        if (this.mAdaptedAllocation.getType().getX() > n) {
            if (this.mWindow.getX() != this.mAdaptedAllocation.getType().getX()) {
                if (this.mWindow.getX() + n < this.mAdaptedAllocation.getType().getX()) {
                    this.mSelectedX = n;
                    this.updateOffsets();
                    return;
                }
                throw new RSInvalidStateException("Cannot set (X + window) which would be larger than dimension of allocation.");
            }
            throw new RSInvalidStateException("Cannot set X when the adapter includes X.");
        }
        throw new RSInvalidStateException("Cannot set X greater than dimension of allocation.");
    }

    public void setY(int n) {
        if (this.mAdaptedAllocation.getType().getY() != 0) {
            if (this.mAdaptedAllocation.getType().getY() > n) {
                if (this.mWindow.getY() != this.mAdaptedAllocation.getType().getY()) {
                    if (this.mWindow.getY() + n < this.mAdaptedAllocation.getType().getY()) {
                        this.mSelectedY = n;
                        this.updateOffsets();
                        return;
                    }
                    throw new RSInvalidStateException("Cannot set (Y + window) which would be larger than dimension of allocation.");
                }
                throw new RSInvalidStateException("Cannot set Y when the adapter includes Y.");
            }
            throw new RSInvalidStateException("Cannot set Y greater than dimension of allocation.");
        }
        throw new RSInvalidStateException("Cannot set Y when the allocation type does not include Y dim.");
    }

    public void setZ(int n) {
        if (this.mAdaptedAllocation.getType().getZ() != 0) {
            if (this.mAdaptedAllocation.getType().getZ() > n) {
                if (this.mWindow.getZ() != this.mAdaptedAllocation.getType().getZ()) {
                    if (this.mWindow.getZ() + n < this.mAdaptedAllocation.getType().getZ()) {
                        this.mSelectedZ = n;
                        this.updateOffsets();
                        return;
                    }
                    throw new RSInvalidStateException("Cannot set (Z + window) which would be larger than dimension of allocation.");
                }
                throw new RSInvalidStateException("Cannot set Z when the adapter includes Z.");
            }
            throw new RSInvalidStateException("Cannot set Z greater than dimension of allocation.");
        }
        throw new RSInvalidStateException("Cannot set Z when the allocation type does not include Z dim.");
    }
}

