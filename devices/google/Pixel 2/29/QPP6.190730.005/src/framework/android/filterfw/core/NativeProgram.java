/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.NativeFrame;
import android.filterfw.core.Program;

public class NativeProgram
extends Program {
    private boolean mHasGetValueFunction = false;
    private boolean mHasInitFunction = false;
    private boolean mHasResetFunction = false;
    private boolean mHasSetValueFunction = false;
    private boolean mHasTeardownFunction = false;
    private boolean mTornDown = false;
    private int nativeProgramId;

    static {
        System.loadLibrary("filterfw");
    }

    public NativeProgram(String charSequence, String charSequence2) {
        this.allocate();
        CharSequence charSequence3 = new StringBuilder();
        charSequence3.append("lib");
        charSequence3.append((String)charSequence);
        charSequence3.append(".so");
        charSequence = charSequence3.toString();
        if (this.openNativeLibrary((String)charSequence)) {
            charSequence3 = new StringBuilder();
            charSequence3.append((String)charSequence2);
            charSequence3.append("_process");
            charSequence3 = charSequence3.toString();
            if (this.bindProcessFunction((String)charSequence3)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("_init");
                this.mHasInitFunction = this.bindInitFunction(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("_teardown");
                this.mHasTeardownFunction = this.bindTeardownFunction(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("_setvalue");
                this.mHasSetValueFunction = this.bindSetValueFunction(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("_getvalue");
                this.mHasGetValueFunction = this.bindGetValueFunction(((StringBuilder)charSequence).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("_reset");
                this.mHasResetFunction = this.bindResetFunction(((StringBuilder)charSequence).toString());
                if (this.mHasInitFunction && !this.callNativeInit()) {
                    throw new RuntimeException("Could not initialize NativeProgram!");
                }
                return;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Could not find native program function name ");
            ((StringBuilder)charSequence2).append((String)charSequence3);
            ((StringBuilder)charSequence2).append(" in library ");
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("! This function is required!");
            throw new RuntimeException(((StringBuilder)charSequence2).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Could not find native library named '");
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("' required for native program!");
        throw new RuntimeException(((StringBuilder)charSequence2).toString());
    }

    private native boolean allocate();

    private native boolean bindGetValueFunction(String var1);

    private native boolean bindInitFunction(String var1);

    private native boolean bindProcessFunction(String var1);

    private native boolean bindResetFunction(String var1);

    private native boolean bindSetValueFunction(String var1);

    private native boolean bindTeardownFunction(String var1);

    private native String callNativeGetValue(String var1);

    private native boolean callNativeInit();

    private native boolean callNativeProcess(NativeFrame[] var1, NativeFrame var2);

    private native boolean callNativeReset();

    private native boolean callNativeSetValue(String var1, String var2);

    private native boolean callNativeTeardown();

    private native boolean deallocate();

    private native boolean nativeInit();

    private native boolean openNativeLibrary(String var1);

    protected void finalize() throws Throwable {
        this.tearDown();
    }

    @Override
    public Object getHostValue(String string2) {
        if (!this.mTornDown) {
            if (this.mHasGetValueFunction) {
                return this.callNativeGetValue(string2);
            }
            throw new RuntimeException("Attempting to get native variable, but native code does not define native getvalue function!");
        }
        throw new RuntimeException("NativeProgram already torn down!");
    }

    @Override
    public void process(Frame[] object, Frame frame) {
        if (!this.mTornDown) {
            NativeFrame[] arrnativeFrame = new NativeFrame[((Frame[])object).length];
            for (int i = 0; i < ((Frame[])object).length; ++i) {
                if (object[i] != null && !(object[i] instanceof NativeFrame)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("NativeProgram got non-native frame as input ");
                    ((StringBuilder)object).append(i);
                    ((StringBuilder)object).append("!");
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                arrnativeFrame[i] = (NativeFrame)object[i];
            }
            if (frame != null && !(frame instanceof NativeFrame)) {
                throw new RuntimeException("NativeProgram got non-native output frame!");
            }
            if (this.callNativeProcess(arrnativeFrame, (NativeFrame)frame)) {
                return;
            }
            throw new RuntimeException("Calling native process() caused error!");
        }
        throw new RuntimeException("NativeProgram already torn down!");
    }

    @Override
    public void reset() {
        if (this.mHasResetFunction && !this.callNativeReset()) {
            throw new RuntimeException("Could not reset NativeProgram!");
        }
    }

    @Override
    public void setHostValue(String string2, Object object) {
        if (!this.mTornDown) {
            if (this.mHasSetValueFunction) {
                if (this.callNativeSetValue(string2, object.toString())) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Error setting native value for variable '");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("'!");
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            throw new RuntimeException("Attempting to set native variable, but native code does not define native setvalue function!");
        }
        throw new RuntimeException("NativeProgram already torn down!");
    }

    public void tearDown() {
        if (this.mTornDown) {
            return;
        }
        if (this.mHasTeardownFunction && !this.callNativeTeardown()) {
            throw new RuntimeException("Could not tear down NativeProgram!");
        }
        this.deallocate();
        this.mTornDown = true;
    }
}

