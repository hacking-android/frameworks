/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.imageproc;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.ImageFormat;
import android.graphics.Bitmap;

public class BitmapSource
extends Filter {
    @GenerateFieldPort(name="bitmap")
    private Bitmap mBitmap;
    private Frame mImageFrame;
    @GenerateFieldPort(hasDefault=true, name="recycleBitmap")
    private boolean mRecycleBitmap = true;
    @GenerateFieldPort(hasDefault=true, name="repeatFrame")
    boolean mRepeatFrame = false;
    private int mTarget;
    @GenerateFieldPort(name="target")
    String mTargetString;

    public BitmapSource(String string2) {
        super(string2);
    }

    @Override
    public void fieldPortValueUpdated(String object, FilterContext filterContext) {
        if ((((String)object).equals("bitmap") || ((String)object).equals("target")) && (object = this.mImageFrame) != null) {
            ((Frame)object).release();
            this.mImageFrame = null;
        }
    }

    public void loadImage(FilterContext filterContext) {
        this.mTarget = FrameFormat.readTargetString(this.mTargetString);
        MutableFrameFormat mutableFrameFormat = ImageFormat.create(this.mBitmap.getWidth(), this.mBitmap.getHeight(), 3, this.mTarget);
        this.mImageFrame = filterContext.getFrameManager().newFrame(mutableFrameFormat);
        this.mImageFrame.setBitmap(this.mBitmap);
        this.mImageFrame.setTimestamp(-1L);
        if (this.mRecycleBitmap) {
            this.mBitmap.recycle();
        }
        this.mBitmap = null;
    }

    @Override
    public void process(FilterContext filterContext) {
        if (this.mImageFrame == null) {
            this.loadImage(filterContext);
        }
        this.pushOutput("image", this.mImageFrame);
        if (!this.mRepeatFrame) {
            this.closeOutputPort("image");
        }
    }

    @Override
    public void setupPorts() {
        this.addOutputPort("image", ImageFormat.create(3, 0));
    }

    @Override
    public void tearDown(FilterContext object) {
        object = this.mImageFrame;
        if (object != null) {
            ((Frame)object).release();
            this.mImageFrame = null;
        }
    }
}

