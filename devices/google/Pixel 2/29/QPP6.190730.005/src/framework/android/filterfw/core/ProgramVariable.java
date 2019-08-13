/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Program;

public class ProgramVariable {
    private Program mProgram;
    private String mVarName;

    public ProgramVariable(Program program, String string2) {
        this.mProgram = program;
        this.mVarName = string2;
    }

    public Program getProgram() {
        return this.mProgram;
    }

    public Object getValue() {
        Object object = this.mProgram;
        if (object != null) {
            return ((Program)object).getHostValue(this.mVarName);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attempting to get program variable '");
        ((StringBuilder)object).append(this.mVarName);
        ((StringBuilder)object).append("' but the program is null!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public String getVariableName() {
        return this.mVarName;
    }

    public void setValue(Object object) {
        Program program = this.mProgram;
        if (program != null) {
            program.setHostValue(this.mVarName, object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attempting to set program variable '");
        ((StringBuilder)object).append(this.mVarName);
        ((StringBuilder)object).append("' but the program is null!");
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

