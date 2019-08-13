/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSDriverException;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.SparseArray;
import dalvik.system.CloseGuard;
import java.io.UnsupportedEncodingException;

public class Script
extends BaseObj {
    private final SparseArray<FieldID> mFIDs = new SparseArray();
    private final SparseArray<InvokeID> mIIDs = new SparseArray();
    long[] mInIdsBuffer = new long[1];
    private final SparseArray<KernelID> mKIDs = new SparseArray();

    Script(long l, RenderScript renderScript) {
        super(l, renderScript);
        this.guard.open("destroy");
    }

    public void bindAllocation(Allocation allocation, int n) {
        this.mRS.validate();
        this.mRS.validateObject(allocation);
        if (allocation != null) {
            Type type;
            if (this.mRS.getApplicationContext().getApplicationInfo().targetSdkVersion >= 20 && ((type = allocation.mType).hasMipmaps() || type.hasFaces() || type.getY() != 0 || type.getZ() != 0)) {
                throw new RSIllegalArgumentException("API 20+ only allows simple 1D allocations to be used with bind.");
            }
            this.mRS.nScriptBindAllocation(this.getID(this.mRS), allocation.getID(this.mRS), n);
        } else {
            this.mRS.nScriptBindAllocation(this.getID(this.mRS), 0L, n);
        }
    }

    protected FieldID createFieldID(int n, Element baseObj) {
        baseObj = this.mFIDs.get(n);
        if (baseObj != null) {
            return baseObj;
        }
        long l = this.mRS.nScriptFieldIDCreate(this.getID(this.mRS), n);
        if (l != 0L) {
            baseObj = new FieldID(l, this.mRS, this, n);
            this.mFIDs.put(n, (FieldID)baseObj);
            return baseObj;
        }
        throw new RSDriverException("Failed to create FieldID");
    }

    protected InvokeID createInvokeID(int n) {
        InvokeID invokeID = this.mIIDs.get(n);
        if (invokeID != null) {
            return invokeID;
        }
        long l = this.mRS.nScriptInvokeIDCreate(this.getID(this.mRS), n);
        if (l != 0L) {
            invokeID = new InvokeID(l, this.mRS, this, n);
            this.mIIDs.put(n, invokeID);
            return invokeID;
        }
        throw new RSDriverException("Failed to create KernelID");
    }

    protected KernelID createKernelID(int n, int n2, Element baseObj, Element element) {
        baseObj = this.mKIDs.get(n);
        if (baseObj != null) {
            return baseObj;
        }
        long l = this.mRS.nScriptKernelIDCreate(this.getID(this.mRS), n, n2);
        if (l != 0L) {
            baseObj = new KernelID(l, this.mRS, this, n, n2);
            this.mKIDs.put(n, (KernelID)baseObj);
            return baseObj;
        }
        throw new RSDriverException("Failed to create KernelID");
    }

    protected void forEach(int n, Allocation allocation, Allocation allocation2, FieldPacker fieldPacker) {
        this.forEach(n, allocation, allocation2, fieldPacker, null);
    }

    protected void forEach(int n, Allocation object, Allocation object2, FieldPacker fieldPacker, LaunchOptions launchOptions) {
        this.mRS.validate();
        this.mRS.validateObject((BaseObj)object);
        this.mRS.validateObject((BaseObj)object2);
        if (object == null && object2 == null && launchOptions == null) {
            throw new RSIllegalArgumentException("At least one of input allocation, output allocation, or LaunchOptions is required to be non-null.");
        }
        long[] arrl = null;
        if (object != null) {
            arrl = this.mInIdsBuffer;
            arrl[0] = object.getID(this.mRS);
        }
        long l = 0L;
        if (object2 != null) {
            l = object2.getID(this.mRS);
        }
        object = fieldPacker != null ? fieldPacker.getData() : null;
        object2 = launchOptions != null ? new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend} : null;
        this.mRS.nScriptForEach(this.getID(this.mRS), n, arrl, l, (byte[])object, (int[])object2);
    }

    protected void forEach(int n, Allocation[] arrallocation, Allocation allocation, FieldPacker fieldPacker) {
        this.forEach(n, arrallocation, allocation, fieldPacker, null);
    }

    protected void forEach(int n, Allocation[] arrobject, Allocation object, FieldPacker object2, LaunchOptions launchOptions) {
        long[] arrl;
        int n2;
        this.mRS.validate();
        if (arrobject != null) {
            int n3 = arrobject.length;
            for (n2 = 0; n2 < n3; ++n2) {
                arrl = arrobject[n2];
                this.mRS.validateObject((BaseObj)arrl);
            }
        }
        this.mRS.validateObject((BaseObj)object);
        if (arrobject == null && object == null) {
            throw new RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
        }
        if (arrobject != null) {
            arrl = new long[arrobject.length];
            for (n2 = 0; n2 < arrobject.length; ++n2) {
                arrl[n2] = arrobject[n2].getID(this.mRS);
            }
            arrobject = arrl;
        } else {
            arrobject = null;
        }
        long l = object != null ? object.getID(this.mRS) : 0L;
        object = object2 != null ? object2.getData() : null;
        object2 = launchOptions != null ? new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend} : null;
        this.mRS.nScriptForEach(this.getID(this.mRS), n, (long[])arrobject, l, (byte[])object, (int[])object2);
    }

    public boolean getVarB(int n) {
        boolean bl = this.mRS.nScriptGetVarI(this.getID(this.mRS), n) > 0;
        return bl;
    }

    public double getVarD(int n) {
        return this.mRS.nScriptGetVarD(this.getID(this.mRS), n);
    }

    public float getVarF(int n) {
        return this.mRS.nScriptGetVarF(this.getID(this.mRS), n);
    }

    public int getVarI(int n) {
        return this.mRS.nScriptGetVarI(this.getID(this.mRS), n);
    }

    public long getVarJ(int n) {
        return this.mRS.nScriptGetVarJ(this.getID(this.mRS), n);
    }

    public void getVarV(int n, FieldPacker fieldPacker) {
        this.mRS.nScriptGetVarV(this.getID(this.mRS), n, fieldPacker.getData());
    }

    protected void invoke(int n) {
        this.mRS.nScriptInvoke(this.getID(this.mRS), n);
    }

    protected void invoke(int n, FieldPacker fieldPacker) {
        if (fieldPacker != null) {
            this.mRS.nScriptInvokeV(this.getID(this.mRS), n, fieldPacker.getData());
        } else {
            this.mRS.nScriptInvoke(this.getID(this.mRS), n);
        }
    }

    protected void reduce(int n, Allocation[] object, Allocation allocation, LaunchOptions launchOptions) {
        this.mRS.validate();
        if (object != null && ((Allocation[])object).length >= 1) {
            if (allocation != null) {
                long[] arrl;
                int n2;
                int n3 = ((Allocation[])object).length;
                for (n2 = 0; n2 < n3; ++n2) {
                    arrl = object[n2];
                    this.mRS.validateObject((BaseObj)arrl);
                }
                arrl = new long[((Allocation[])object).length];
                for (n2 = 0; n2 < ((Allocation[])object).length; ++n2) {
                    arrl[n2] = object[n2].getID(this.mRS);
                }
                long l = allocation.getID(this.mRS);
                object = launchOptions != null ? new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend} : null;
                this.mRS.nScriptReduce(this.getID(this.mRS), n, arrl, l, (int[])object);
                return;
            }
            throw new RSIllegalArgumentException("aout is required to be non-null.");
        }
        throw new RSIllegalArgumentException("At least one input is required.");
    }

    public void setTimeZone(String string2) {
        this.mRS.validate();
        try {
            this.mRS.nScriptSetTimeZone(this.getID(this.mRS), string2.getBytes("UTF-8"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public void setVar(int n, double d) {
        this.mRS.nScriptSetVarD(this.getID(this.mRS), n, d);
    }

    public void setVar(int n, float f) {
        this.mRS.nScriptSetVarF(this.getID(this.mRS), n, f);
    }

    public void setVar(int n, int n2) {
        this.mRS.nScriptSetVarI(this.getID(this.mRS), n, n2);
    }

    public void setVar(int n, long l) {
        this.mRS.nScriptSetVarJ(this.getID(this.mRS), n, l);
    }

    public void setVar(int n, BaseObj baseObj) {
        this.mRS.validate();
        this.mRS.validateObject(baseObj);
        RenderScript renderScript = this.mRS;
        long l = this.getID(this.mRS);
        long l2 = baseObj == null ? 0L : baseObj.getID(this.mRS);
        renderScript.nScriptSetVarObj(l, n, l2);
    }

    public void setVar(int n, FieldPacker fieldPacker) {
        this.mRS.nScriptSetVarV(this.getID(this.mRS), n, fieldPacker.getData());
    }

    public void setVar(int n, FieldPacker fieldPacker, Element element, int[] arrn) {
        this.mRS.nScriptSetVarVE(this.getID(this.mRS), n, fieldPacker.getData(), element.getID(this.mRS), arrn);
    }

    public void setVar(int n, boolean bl) {
        this.mRS.nScriptSetVarI(this.getID(this.mRS), n, (int)bl);
    }

    public static class Builder {
        @UnsupportedAppUsage
        RenderScript mRS;

        @UnsupportedAppUsage
        Builder(RenderScript renderScript) {
            this.mRS = renderScript;
        }
    }

    public static class FieldBase {
        protected Allocation mAllocation;
        protected Element mElement;

        protected FieldBase() {
        }

        public Allocation getAllocation() {
            return this.mAllocation;
        }

        public Element getElement() {
            return this.mElement;
        }

        public Type getType() {
            return this.mAllocation.getType();
        }

        protected void init(RenderScript renderScript, int n) {
            this.mAllocation = Allocation.createSized(renderScript, this.mElement, n, 1);
        }

        protected void init(RenderScript renderScript, int n, int n2) {
            this.mAllocation = Allocation.createSized(renderScript, this.mElement, n, n2 | 1);
        }

        public void updateAllocation() {
        }
    }

    public static final class FieldID
    extends BaseObj {
        Script mScript;
        int mSlot;

        FieldID(long l, RenderScript renderScript, Script script, int n) {
            super(l, renderScript);
            this.mScript = script;
            this.mSlot = n;
        }
    }

    public static final class InvokeID
    extends BaseObj {
        Script mScript;
        int mSlot;

        InvokeID(long l, RenderScript renderScript, Script script, int n) {
            super(l, renderScript);
            this.mScript = script;
            this.mSlot = n;
        }
    }

    public static final class KernelID
    extends BaseObj {
        Script mScript;
        int mSig;
        int mSlot;

        KernelID(long l, RenderScript renderScript, Script script, int n, int n2) {
            super(l, renderScript);
            this.mScript = script;
            this.mSlot = n;
            this.mSig = n2;
        }
    }

    public static final class LaunchOptions {
        private int strategy;
        private int xend = 0;
        private int xstart = 0;
        private int yend = 0;
        private int ystart = 0;
        private int zend = 0;
        private int zstart = 0;

        public int getXEnd() {
            return this.xend;
        }

        public int getXStart() {
            return this.xstart;
        }

        public int getYEnd() {
            return this.yend;
        }

        public int getYStart() {
            return this.ystart;
        }

        public int getZEnd() {
            return this.zend;
        }

        public int getZStart() {
            return this.zstart;
        }

        public LaunchOptions setX(int n, int n2) {
            if (n >= 0 && n2 > n) {
                this.xstart = n;
                this.xend = n2;
                return this;
            }
            throw new RSIllegalArgumentException("Invalid dimensions");
        }

        public LaunchOptions setY(int n, int n2) {
            if (n >= 0 && n2 > n) {
                this.ystart = n;
                this.yend = n2;
                return this;
            }
            throw new RSIllegalArgumentException("Invalid dimensions");
        }

        public LaunchOptions setZ(int n, int n2) {
            if (n >= 0 && n2 > n) {
                this.zstart = n;
                this.zend = n2;
                return this;
            }
            throw new RSIllegalArgumentException("Invalid dimensions");
        }
    }

}

