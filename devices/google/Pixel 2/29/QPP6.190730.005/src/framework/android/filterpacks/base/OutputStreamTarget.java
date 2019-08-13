/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class OutputStreamTarget
extends Filter {
    @GenerateFieldPort(name="stream")
    private OutputStream mOutputStream;

    public OutputStreamTarget(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext object) {
        object = this.pullInput("data");
        object = ((Frame)object).getFormat().getObjectClass() == String.class ? ByteBuffer.wrap(((String)((Frame)object).getObjectValue()).getBytes()) : ((Frame)object).getData();
        try {
            this.mOutputStream.write(((ByteBuffer)object).array(), 0, ((Buffer)object).limit());
            this.mOutputStream.flush();
            return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("OutputStreamTarget: Could not write to stream: ");
            ((StringBuilder)object).append(iOException.getMessage());
            ((StringBuilder)object).append("!");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    @Override
    public void setupPorts() {
        this.addInputPort("data");
    }
}

