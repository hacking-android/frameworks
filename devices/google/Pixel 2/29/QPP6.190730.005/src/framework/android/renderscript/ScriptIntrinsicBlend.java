/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsic;

public class ScriptIntrinsicBlend
extends ScriptIntrinsic {
    ScriptIntrinsicBlend(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    private void blend(int n, Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        if (allocation.getElement().isCompatible(Element.U8_4(this.mRS))) {
            if (allocation2.getElement().isCompatible(Element.U8_4(this.mRS))) {
                this.forEach(n, allocation, allocation2, null, launchOptions);
                return;
            }
            throw new RSIllegalArgumentException("Output is not of expected format.");
        }
        throw new RSIllegalArgumentException("Input is not of expected format.");
    }

    public static ScriptIntrinsicBlend create(RenderScript renderScript, Element element) {
        return new ScriptIntrinsicBlend(renderScript.nScriptIntrinsicCreate(7, element.getID(renderScript)), renderScript);
    }

    public void forEachAdd(Allocation allocation, Allocation allocation2) {
        this.forEachAdd(allocation, allocation2, null);
    }

    public void forEachAdd(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(34, allocation, allocation2, launchOptions);
    }

    public void forEachClear(Allocation allocation, Allocation allocation2) {
        this.forEachClear(allocation, allocation2, null);
    }

    public void forEachClear(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(0, allocation, allocation2, launchOptions);
    }

    public void forEachDst(Allocation allocation, Allocation allocation2) {
    }

    public void forEachDst(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
    }

    public void forEachDstAtop(Allocation allocation, Allocation allocation2) {
        this.forEachDstAtop(allocation, allocation2, null);
    }

    public void forEachDstAtop(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(10, allocation, allocation2, launchOptions);
    }

    public void forEachDstIn(Allocation allocation, Allocation allocation2) {
        this.forEachDstIn(allocation, allocation2, null);
    }

    public void forEachDstIn(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(6, allocation, allocation2, launchOptions);
    }

    public void forEachDstOut(Allocation allocation, Allocation allocation2) {
        this.forEachDstOut(allocation, allocation2, null);
    }

    public void forEachDstOut(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(8, allocation, allocation2, launchOptions);
    }

    public void forEachDstOver(Allocation allocation, Allocation allocation2) {
        this.forEachDstOver(allocation, allocation2, null);
    }

    public void forEachDstOver(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(4, allocation, allocation2, launchOptions);
    }

    public void forEachMultiply(Allocation allocation, Allocation allocation2) {
        this.forEachMultiply(allocation, allocation2, null);
    }

    public void forEachMultiply(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(14, allocation, allocation2, launchOptions);
    }

    public void forEachSrc(Allocation allocation, Allocation allocation2) {
        this.forEachSrc(allocation, allocation2, null);
    }

    public void forEachSrc(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(1, allocation, allocation2, null);
    }

    public void forEachSrcAtop(Allocation allocation, Allocation allocation2) {
        this.forEachSrcAtop(allocation, allocation2, null);
    }

    public void forEachSrcAtop(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(9, allocation, allocation2, launchOptions);
    }

    public void forEachSrcIn(Allocation allocation, Allocation allocation2) {
        this.forEachSrcIn(allocation, allocation2, null);
    }

    public void forEachSrcIn(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(5, allocation, allocation2, launchOptions);
    }

    public void forEachSrcOut(Allocation allocation, Allocation allocation2) {
        this.forEachSrcOut(allocation, allocation2, null);
    }

    public void forEachSrcOut(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(7, allocation, allocation2, launchOptions);
    }

    public void forEachSrcOver(Allocation allocation, Allocation allocation2) {
        this.forEachSrcOver(allocation, allocation2, null);
    }

    public void forEachSrcOver(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(3, allocation, allocation2, launchOptions);
    }

    public void forEachSubtract(Allocation allocation, Allocation allocation2) {
        this.forEachSubtract(allocation, allocation2, null);
    }

    public void forEachSubtract(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(35, allocation, allocation2, launchOptions);
    }

    public void forEachXor(Allocation allocation, Allocation allocation2) {
        this.forEachXor(allocation, allocation2, null);
    }

    public void forEachXor(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.blend(11, allocation, allocation2, launchOptions);
    }

    public Script.KernelID getKernelIDAdd() {
        return this.createKernelID(34, 3, null, null);
    }

    public Script.KernelID getKernelIDClear() {
        return this.createKernelID(0, 3, null, null);
    }

    public Script.KernelID getKernelIDDst() {
        return this.createKernelID(2, 3, null, null);
    }

    public Script.KernelID getKernelIDDstAtop() {
        return this.createKernelID(10, 3, null, null);
    }

    public Script.KernelID getKernelIDDstIn() {
        return this.createKernelID(6, 3, null, null);
    }

    public Script.KernelID getKernelIDDstOut() {
        return this.createKernelID(8, 3, null, null);
    }

    public Script.KernelID getKernelIDDstOver() {
        return this.createKernelID(4, 3, null, null);
    }

    public Script.KernelID getKernelIDMultiply() {
        return this.createKernelID(14, 3, null, null);
    }

    public Script.KernelID getKernelIDSrc() {
        return this.createKernelID(1, 3, null, null);
    }

    public Script.KernelID getKernelIDSrcAtop() {
        return this.createKernelID(9, 3, null, null);
    }

    public Script.KernelID getKernelIDSrcIn() {
        return this.createKernelID(5, 3, null, null);
    }

    public Script.KernelID getKernelIDSrcOut() {
        return this.createKernelID(7, 3, null, null);
    }

    public Script.KernelID getKernelIDSrcOver() {
        return this.createKernelID(3, 3, null, null);
    }

    public Script.KernelID getKernelIDSubtract() {
        return this.createKernelID(35, 3, null, null);
    }

    public Script.KernelID getKernelIDXor() {
        return this.createKernelID(11, 3, null, null);
    }
}

