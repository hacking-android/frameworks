/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.format.ImageFormat;
import android.graphics.Bitmap;
import java.io.OutputStream;

public class ImageEncoder
extends Filter {
    @GenerateFieldPort(name="stream")
    private OutputStream mOutputStream;
    @GenerateFieldPort(hasDefault=true, name="quality")
    private int mQuality = 80;

    public ImageEncoder(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext filterContext) {
        this.pullInput("image").getBitmap().compress(Bitmap.CompressFormat.JPEG, this.mQuality, this.mOutputStream);
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("image", ImageFormat.create(3, 0));
    }
}

