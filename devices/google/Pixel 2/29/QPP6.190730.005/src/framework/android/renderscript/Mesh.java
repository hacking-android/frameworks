/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import dalvik.system.CloseGuard;
import java.util.Vector;

public class Mesh
extends BaseObj {
    Allocation[] mIndexBuffers;
    Primitive[] mPrimitives;
    Allocation[] mVertexBuffers;

    Mesh(long l, RenderScript renderScript) {
        super(l, renderScript);
        this.guard.open("destroy");
    }

    public Allocation getIndexSetAllocation(int n) {
        return this.mIndexBuffers[n];
    }

    public Primitive getPrimitive(int n) {
        return this.mPrimitives[n];
    }

    public int getPrimitiveCount() {
        Allocation[] arrallocation = this.mIndexBuffers;
        if (arrallocation == null) {
            return 0;
        }
        return arrallocation.length;
    }

    @UnsupportedAppUsage
    public Allocation getVertexAllocation(int n) {
        return this.mVertexBuffers[n];
    }

    public int getVertexAllocationCount() {
        Allocation[] arrallocation = this.mVertexBuffers;
        if (arrallocation == null) {
            return 0;
        }
        return arrallocation.length;
    }

    @Override
    void updateFromNative() {
        int n;
        super.updateFromNative();
        int n2 = this.mRS.nMeshGetVertexBufferCount(this.getID(this.mRS));
        int n3 = this.mRS.nMeshGetIndexCount(this.getID(this.mRS));
        long[] arrl = new long[n2];
        long[] arrl2 = new long[n3];
        int[] arrn = new int[n3];
        this.mRS.nMeshGetVertices(this.getID(this.mRS), arrl, n2);
        this.mRS.nMeshGetIndices(this.getID(this.mRS), arrl2, arrn, n3);
        this.mVertexBuffers = new Allocation[n2];
        this.mIndexBuffers = new Allocation[n3];
        this.mPrimitives = new Primitive[n3];
        for (n = 0; n < n2; ++n) {
            if (arrl[n] == 0L) continue;
            this.mVertexBuffers[n] = new Allocation(arrl[n], this.mRS, null, 1);
            this.mVertexBuffers[n].updateFromNative();
        }
        for (n = 0; n < n3; ++n) {
            if (arrl2[n] != 0L) {
                this.mIndexBuffers[n] = new Allocation(arrl2[n], this.mRS, null, 1);
                this.mIndexBuffers[n].updateFromNative();
            }
            this.mPrimitives[n] = Primitive.values()[arrn[n]];
        }
    }

    public static class AllocationBuilder {
        Vector mIndexTypes;
        RenderScript mRS;
        int mVertexTypeCount;
        Entry[] mVertexTypes;

        @UnsupportedAppUsage
        public AllocationBuilder(RenderScript renderScript) {
            this.mRS = renderScript;
            this.mVertexTypeCount = 0;
            this.mVertexTypes = new Entry[16];
            this.mIndexTypes = new Vector();
        }

        @UnsupportedAppUsage
        public AllocationBuilder addIndexSetAllocation(Allocation allocation, Primitive primitive) {
            Entry entry = new Entry();
            entry.a = allocation;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        @UnsupportedAppUsage
        public AllocationBuilder addIndexSetType(Primitive primitive) {
            Entry entry = new Entry();
            entry.a = null;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        @UnsupportedAppUsage
        public AllocationBuilder addVertexAllocation(Allocation allocation) throws IllegalStateException {
            int n = this.mVertexTypeCount;
            Entry[] arrentry = this.mVertexTypes;
            if (n < arrentry.length) {
                arrentry[n] = new Entry();
                arrentry = this.mVertexTypes;
                n = this.mVertexTypeCount;
                arrentry[n].a = allocation;
                this.mVertexTypeCount = n + 1;
                return this;
            }
            throw new IllegalStateException("Max vertex types exceeded.");
        }

        @UnsupportedAppUsage
        public Mesh create() {
            Entry entry;
            int n;
            this.mRS.validate();
            Object object = new long[this.mVertexTypeCount];
            long[] arrl = new long[this.mIndexTypes.size()];
            int[] arrn = new int[this.mIndexTypes.size()];
            Allocation[] arrallocation = new Allocation[this.mIndexTypes.size()];
            Primitive[] arrprimitive = new Primitive[this.mIndexTypes.size()];
            Allocation[] arrallocation2 = new Allocation[this.mVertexTypeCount];
            for (n = 0; n < this.mVertexTypeCount; ++n) {
                entry = this.mVertexTypes[n];
                arrallocation2[n] = entry.a;
                object[n] = entry.a.getID(this.mRS);
            }
            for (n = 0; n < this.mIndexTypes.size(); ++n) {
                entry = (Entry)this.mIndexTypes.elementAt(n);
                long l = entry.a == null ? 0L : entry.a.getID(this.mRS);
                arrallocation[n] = entry.a;
                arrprimitive[n] = entry.prim;
                arrl[n] = l;
                arrn[n] = entry.prim.mID;
            }
            object = new Mesh(this.mRS.nMeshCreate((long[])object, arrl, arrn), this.mRS);
            object.mVertexBuffers = arrallocation2;
            object.mIndexBuffers = arrallocation;
            object.mPrimitives = arrprimitive;
            return object;
        }

        public int getCurrentIndexSetIndex() {
            return this.mIndexTypes.size() - 1;
        }

        public int getCurrentVertexTypeIndex() {
            return this.mVertexTypeCount - 1;
        }

        class Entry {
            Allocation a;
            Primitive prim;

            Entry() {
            }
        }

    }

    public static class Builder {
        Vector mIndexTypes;
        RenderScript mRS;
        int mUsage;
        int mVertexTypeCount;
        Entry[] mVertexTypes;

        public Builder(RenderScript renderScript, int n) {
            this.mRS = renderScript;
            this.mUsage = n;
            this.mVertexTypeCount = 0;
            this.mVertexTypes = new Entry[16];
            this.mIndexTypes = new Vector();
        }

        public Builder addIndexSetType(Element element, int n, Primitive primitive) {
            Entry entry = new Entry();
            entry.t = null;
            entry.e = element;
            entry.size = n;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public Builder addIndexSetType(Primitive primitive) {
            Entry entry = new Entry();
            entry.t = null;
            entry.e = null;
            entry.size = 0;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public Builder addIndexSetType(Type type, Primitive primitive) {
            Entry entry = new Entry();
            entry.t = type;
            entry.e = null;
            entry.size = 0;
            entry.prim = primitive;
            this.mIndexTypes.addElement(entry);
            return this;
        }

        public Builder addVertexType(Element element, int n) throws IllegalStateException {
            int n2 = this.mVertexTypeCount;
            Entry[] arrentry = this.mVertexTypes;
            if (n2 < arrentry.length) {
                arrentry[n2] = new Entry();
                arrentry = this.mVertexTypes;
                n2 = this.mVertexTypeCount;
                arrentry[n2].t = null;
                arrentry[n2].e = element;
                arrentry[n2].size = n;
                this.mVertexTypeCount = n2 + 1;
                return this;
            }
            throw new IllegalStateException("Max vertex types exceeded.");
        }

        public Builder addVertexType(Type type) throws IllegalStateException {
            int n = this.mVertexTypeCount;
            Entry[] arrentry = this.mVertexTypes;
            if (n < arrentry.length) {
                arrentry[n] = new Entry();
                arrentry = this.mVertexTypes;
                n = this.mVertexTypeCount;
                arrentry[n].t = type;
                arrentry[n].e = null;
                this.mVertexTypeCount = n + 1;
                return this;
            }
            throw new IllegalStateException("Max vertex types exceeded.");
        }

        public Mesh create() {
            int n;
            Object object;
            this.mRS.validate();
            long[] arrl = new long[this.mVertexTypeCount];
            long[] arrl2 = new long[this.mIndexTypes.size()];
            int[] arrn = new int[this.mIndexTypes.size()];
            Allocation[] arrallocation = new Allocation[this.mVertexTypeCount];
            Allocation[] arrallocation2 = new Allocation[this.mIndexTypes.size()];
            Primitive[] arrprimitive = new Primitive[this.mIndexTypes.size()];
            for (n = 0; n < this.mVertexTypeCount; ++n) {
                block8 : {
                    block7 : {
                        block6 : {
                            object = this.mVertexTypes[n];
                            if (((Entry)object).t == null) break block6;
                            object = Allocation.createTyped(this.mRS, ((Entry)object).t, this.mUsage);
                            break block7;
                        }
                        if (((Entry)object).e == null) break block8;
                        object = Allocation.createSized(this.mRS, ((Entry)object).e, ((Entry)object).size, this.mUsage);
                    }
                    arrallocation[n] = object;
                    arrl[n] = ((BaseObj)object).getID(this.mRS);
                    continue;
                }
                throw new IllegalStateException("Builder corrupt, no valid element in entry.");
            }
            for (n = 0; n < this.mIndexTypes.size(); ++n) {
                block11 : {
                    Entry entry;
                    block10 : {
                        block9 : {
                            entry = (Entry)this.mIndexTypes.elementAt(n);
                            if (entry.t == null) break block9;
                            object = Allocation.createTyped(this.mRS, entry.t, this.mUsage);
                            break block10;
                        }
                        if (entry.e == null) break block11;
                        object = Allocation.createSized(this.mRS, entry.e, entry.size, this.mUsage);
                    }
                    long l = object == null ? 0L : ((BaseObj)object).getID(this.mRS);
                    arrallocation2[n] = object;
                    arrprimitive[n] = entry.prim;
                    arrl2[n] = l;
                    arrn[n] = entry.prim.mID;
                    continue;
                }
                throw new IllegalStateException("Builder corrupt, no valid element in entry.");
            }
            object = new Mesh(this.mRS.nMeshCreate(arrl, arrl2, arrn), this.mRS);
            ((Mesh)object).mVertexBuffers = arrallocation;
            ((Mesh)object).mIndexBuffers = arrallocation2;
            ((Mesh)object).mPrimitives = arrprimitive;
            return object;
        }

        public int getCurrentIndexSetIndex() {
            return this.mIndexTypes.size() - 1;
        }

        public int getCurrentVertexTypeIndex() {
            return this.mVertexTypeCount - 1;
        }

        Type newType(Element object, int n) {
            object = new Type.Builder(this.mRS, (Element)object);
            ((Type.Builder)object).setX(n);
            return ((Type.Builder)object).create();
        }

        class Entry {
            Element e;
            Primitive prim;
            int size;
            Type t;
            int usage;

            Entry() {
            }
        }

    }

    public static enum Primitive {
        POINT(0),
        LINE(1),
        LINE_STRIP(2),
        TRIANGLE(3),
        TRIANGLE_STRIP(4),
        TRIANGLE_FAN(5);
        
        int mID;

        private Primitive(int n2) {
            this.mID = n2;
        }
    }

    public static class TriangleMeshBuilder {
        public static final int COLOR = 1;
        public static final int NORMAL = 2;
        public static final int TEXTURE_0 = 256;
        float mA = 1.0f;
        float mB = 1.0f;
        Element mElement;
        int mFlags;
        float mG = 1.0f;
        int mIndexCount;
        short[] mIndexData;
        int mMaxIndex;
        float mNX = 0.0f;
        float mNY = 0.0f;
        float mNZ = -1.0f;
        float mR = 1.0f;
        RenderScript mRS;
        float mS0 = 0.0f;
        float mT0 = 0.0f;
        int mVtxCount;
        float[] mVtxData;
        int mVtxSize;

        @UnsupportedAppUsage
        public TriangleMeshBuilder(RenderScript renderScript, int n, int n2) {
            this.mRS = renderScript;
            this.mVtxCount = 0;
            this.mMaxIndex = 0;
            this.mIndexCount = 0;
            this.mVtxData = new float[128];
            this.mIndexData = new short[128];
            this.mVtxSize = n;
            this.mFlags = n2;
            if (n >= 2 && n <= 3) {
                return;
            }
            throw new IllegalArgumentException("Vertex size out of range.");
        }

        private void latch() {
            int n;
            float[] arrf;
            if ((this.mFlags & 1) != 0) {
                this.makeSpace(4);
                arrf = this.mVtxData;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mR;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mG;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mB;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mA;
            }
            if ((this.mFlags & 256) != 0) {
                this.makeSpace(2);
                arrf = this.mVtxData;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mS0;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mT0;
            }
            if ((this.mFlags & 2) != 0) {
                this.makeSpace(4);
                arrf = this.mVtxData;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mNX;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mNY;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = this.mNZ;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = 0.0f;
            }
            ++this.mMaxIndex;
        }

        private void makeSpace(int n) {
            int n2 = this.mVtxCount;
            float[] arrf = this.mVtxData;
            if (n2 + n >= arrf.length) {
                float[] arrf2 = new float[arrf.length * 2];
                System.arraycopy(arrf, 0, arrf2, 0, arrf.length);
                this.mVtxData = arrf2;
            }
        }

        @UnsupportedAppUsage
        public TriangleMeshBuilder addTriangle(int n, int n2, int n3) {
            int n4 = this.mMaxIndex;
            if (n < n4 && n >= 0 && n2 < n4 && n2 >= 0 && n3 < n4 && n3 >= 0) {
                short[] arrs;
                n4 = this.mIndexCount;
                short[] arrs2 = this.mIndexData;
                if (n4 + 3 >= arrs2.length) {
                    arrs = new short[arrs2.length * 2];
                    System.arraycopy(arrs2, 0, arrs, 0, arrs2.length);
                    this.mIndexData = arrs;
                }
                arrs = this.mIndexData;
                n4 = this.mIndexCount;
                this.mIndexCount = n4 + 1;
                arrs[n4] = (short)n;
                n = this.mIndexCount;
                this.mIndexCount = n + 1;
                arrs[n] = (short)n2;
                n = this.mIndexCount;
                this.mIndexCount = n + 1;
                arrs[n] = (short)n3;
                return this;
            }
            throw new IllegalStateException("Index provided greater than vertex count.");
        }

        @UnsupportedAppUsage
        public TriangleMeshBuilder addVertex(float f, float f2) {
            if (this.mVtxSize == 2) {
                this.makeSpace(2);
                float[] arrf = this.mVtxData;
                int n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = f;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = f2;
                this.latch();
                return this;
            }
            throw new IllegalStateException("add mistmatch with declared components.");
        }

        public TriangleMeshBuilder addVertex(float f, float f2, float f3) {
            if (this.mVtxSize == 3) {
                this.makeSpace(4);
                float[] arrf = this.mVtxData;
                int n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = f;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = f2;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = f3;
                n = this.mVtxCount;
                this.mVtxCount = n + 1;
                arrf[n] = 1.0f;
                this.latch();
                return this;
            }
            throw new IllegalStateException("add mistmatch with declared components.");
        }

        @UnsupportedAppUsage
        public Mesh create(boolean bl) {
            Object object = new Element.Builder(this.mRS);
            ((Element.Builder)object).add(Element.createVector(this.mRS, Element.DataType.FLOAT_32, this.mVtxSize), "position");
            if ((this.mFlags & 1) != 0) {
                ((Element.Builder)object).add(Element.F32_4(this.mRS), "color");
            }
            if ((this.mFlags & 256) != 0) {
                ((Element.Builder)object).add(Element.F32_2(this.mRS), "texture0");
            }
            if ((this.mFlags & 2) != 0) {
                ((Element.Builder)object).add(Element.F32_3(this.mRS), "normal");
            }
            this.mElement = ((Element.Builder)object).create();
            int n = 1;
            if (bl) {
                n = 1 | 4;
            }
            object = new Builder(this.mRS, n);
            ((Builder)object).addVertexType(this.mElement, this.mMaxIndex);
            ((Builder)object).addIndexSetType(Element.U16(this.mRS), this.mIndexCount, Primitive.TRIANGLE);
            object = ((Builder)object).create();
            ((Mesh)object).getVertexAllocation(0).copy1DRangeFromUnchecked(0, this.mMaxIndex, this.mVtxData);
            if (bl) {
                ((Mesh)object).getVertexAllocation(0).syncAll(1);
            }
            ((Mesh)object).getIndexSetAllocation(0).copy1DRangeFromUnchecked(0, this.mIndexCount, this.mIndexData);
            if (bl) {
                ((Mesh)object).getIndexSetAllocation(0).syncAll(1);
            }
            return object;
        }

        public TriangleMeshBuilder setColor(float f, float f2, float f3, float f4) {
            if ((this.mFlags & 1) != 0) {
                this.mR = f;
                this.mG = f2;
                this.mB = f3;
                this.mA = f4;
                return this;
            }
            throw new IllegalStateException("add mistmatch with declared components.");
        }

        public TriangleMeshBuilder setNormal(float f, float f2, float f3) {
            if ((this.mFlags & 2) != 0) {
                this.mNX = f;
                this.mNY = f2;
                this.mNZ = f3;
                return this;
            }
            throw new IllegalStateException("add mistmatch with declared components.");
        }

        public TriangleMeshBuilder setTexture(float f, float f2) {
            if ((this.mFlags & 256) != 0) {
                this.mS0 = f;
                this.mT0 = f2;
                return this;
            }
            throw new IllegalStateException("add mistmatch with declared components.");
        }
    }

}

