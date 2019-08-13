/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.Float4;
import android.renderscript.Matrix3f;
import android.renderscript.Matrix4f;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsic;

public final class ScriptIntrinsicColorMatrix
extends ScriptIntrinsic {
    private final Float4 mAdd = new Float4();
    private final Matrix4f mMatrix = new Matrix4f();

    private ScriptIntrinsicColorMatrix(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ScriptIntrinsicColorMatrix create(RenderScript renderScript) {
        return new ScriptIntrinsicColorMatrix(renderScript.nScriptIntrinsicCreate(2, 0L), renderScript);
    }

    @Deprecated
    public static ScriptIntrinsicColorMatrix create(RenderScript renderScript, Element element) {
        return ScriptIntrinsicColorMatrix.create(renderScript);
    }

    private void setMatrix() {
        FieldPacker fieldPacker = new FieldPacker(64);
        fieldPacker.addMatrix(this.mMatrix);
        this.setVar(0, fieldPacker);
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        this.forEach(allocation, allocation2, null);
    }

    public void forEach(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        if (!(allocation.getElement().isCompatible(Element.U8(this.mRS)) || allocation.getElement().isCompatible(Element.U8_2(this.mRS)) || allocation.getElement().isCompatible(Element.U8_3(this.mRS)) || allocation.getElement().isCompatible(Element.U8_4(this.mRS)) || allocation.getElement().isCompatible(Element.F32(this.mRS)) || allocation.getElement().isCompatible(Element.F32_2(this.mRS)) || allocation.getElement().isCompatible(Element.F32_3(this.mRS)) || allocation.getElement().isCompatible(Element.F32_4(this.mRS)))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        if (!(allocation2.getElement().isCompatible(Element.U8(this.mRS)) || allocation2.getElement().isCompatible(Element.U8_2(this.mRS)) || allocation2.getElement().isCompatible(Element.U8_3(this.mRS)) || allocation2.getElement().isCompatible(Element.U8_4(this.mRS)) || allocation2.getElement().isCompatible(Element.F32(this.mRS)) || allocation2.getElement().isCompatible(Element.F32_2(this.mRS)) || allocation2.getElement().isCompatible(Element.F32_3(this.mRS)) || allocation2.getElement().isCompatible(Element.F32_4(this.mRS)))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        this.forEach(0, allocation, allocation2, null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return this.createKernelID(0, 3, null, null);
    }

    public void setAdd(float f, float f2, float f3, float f4) {
        Object object = this.mAdd;
        ((Float4)object).x = f;
        ((Float4)object).y = f2;
        ((Float4)object).z = f3;
        ((Float4)object).w = f4;
        object = new FieldPacker(16);
        ((FieldPacker)object).addF32(this.mAdd.x);
        ((FieldPacker)object).addF32(this.mAdd.y);
        ((FieldPacker)object).addF32(this.mAdd.z);
        ((FieldPacker)object).addF32(this.mAdd.w);
        this.setVar(1, (FieldPacker)object);
    }

    public void setAdd(Float4 float4) {
        this.mAdd.x = float4.x;
        this.mAdd.y = float4.y;
        this.mAdd.z = float4.z;
        this.mAdd.w = float4.w;
        FieldPacker fieldPacker = new FieldPacker(16);
        fieldPacker.addF32(float4.x);
        fieldPacker.addF32(float4.y);
        fieldPacker.addF32(float4.z);
        fieldPacker.addF32(float4.w);
        this.setVar(1, fieldPacker);
    }

    public void setColorMatrix(Matrix3f matrix3f) {
        this.mMatrix.load(matrix3f);
        this.setMatrix();
    }

    public void setColorMatrix(Matrix4f matrix4f) {
        this.mMatrix.load(matrix4f);
        this.setMatrix();
    }

    public void setGreyscale() {
        this.mMatrix.loadIdentity();
        this.mMatrix.set(0, 0, 0.299f);
        this.mMatrix.set(1, 0, 0.587f);
        this.mMatrix.set(2, 0, 0.114f);
        this.mMatrix.set(0, 1, 0.299f);
        this.mMatrix.set(1, 1, 0.587f);
        this.mMatrix.set(2, 1, 0.114f);
        this.mMatrix.set(0, 2, 0.299f);
        this.mMatrix.set(1, 2, 0.587f);
        this.mMatrix.set(2, 2, 0.114f);
        this.setMatrix();
    }

    public void setRGBtoYUV() {
        this.mMatrix.loadIdentity();
        this.mMatrix.set(0, 0, 0.299f);
        this.mMatrix.set(1, 0, 0.587f);
        this.mMatrix.set(2, 0, 0.114f);
        this.mMatrix.set(0, 1, -0.14713f);
        this.mMatrix.set(1, 1, -0.28886f);
        this.mMatrix.set(2, 1, 0.436f);
        this.mMatrix.set(0, 2, 0.615f);
        this.mMatrix.set(1, 2, -0.51499f);
        this.mMatrix.set(2, 2, -0.10001f);
        this.setMatrix();
    }

    public void setYUVtoRGB() {
        this.mMatrix.loadIdentity();
        this.mMatrix.set(0, 0, 1.0f);
        this.mMatrix.set(1, 0, 0.0f);
        this.mMatrix.set(2, 0, 1.13983f);
        this.mMatrix.set(0, 1, 1.0f);
        this.mMatrix.set(1, 1, -0.39465f);
        this.mMatrix.set(2, 1, -0.5806f);
        this.mMatrix.set(0, 2, 1.0f);
        this.mMatrix.set(1, 2, 2.03211f);
        this.mMatrix.set(2, 2, 0.0f);
        this.setMatrix();
    }
}

