/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.PrimitiveFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class InputStreamSource
extends Filter {
    @GenerateFieldPort(name="stream")
    private InputStream mInputStream;
    @GenerateFinalPort(hasDefault=true, name="format")
    private MutableFrameFormat mOutputFormat = null;
    @GenerateFinalPort(name="target")
    private String mTarget;

    public InputStreamSource(String string2) {
        super(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void process(FilterContext object) {
        int n = 0;
        try {
            int n2;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Object object2 = new byte[1024];
            while ((n2 = this.mInputStream.read((byte[])object2)) > 0) {
                byteArrayOutputStream.write((byte[])object2, 0, n2);
                n += n2;
            }
            object2 = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
            this.mOutputFormat.setDimensions(n);
            object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
            ((Frame)object).setData((ByteBuffer)object2);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("InputStreamSource: Could not read stream: ");
            stringBuilder.append(iOException.getMessage());
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString());
        }
        this.pushOutput("data", (Frame)object);
        ((Frame)object).release();
        this.closeOutputPort("data");
    }

    @Override
    public void setupPorts() {
        int n = FrameFormat.readTargetString(this.mTarget);
        if (this.mOutputFormat == null) {
            this.mOutputFormat = PrimitiveFormat.createByteFormat(n);
        }
        this.addOutputPort("data", this.mOutputFormat);
    }
}

