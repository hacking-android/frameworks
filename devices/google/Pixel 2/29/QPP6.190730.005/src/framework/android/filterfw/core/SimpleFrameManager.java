/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrame;
import android.filterfw.core.NativeFrame;
import android.filterfw.core.SimpleFrame;
import android.filterfw.core.VertexFrame;

public class SimpleFrameManager
extends FrameManager {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Frame createNewFrame(FrameFormat object) {
        int n = ((FrameFormat)object).getTarget();
        if (n == 1) return new SimpleFrame((FrameFormat)object, this);
        if (n == 2) return new NativeFrame((FrameFormat)object, this);
        if (n != 3) {
            if (n == 4) {
                return new VertexFrame((FrameFormat)object, this);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported frame target type: ");
            stringBuilder.append(FrameFormat.targetToString(((FrameFormat)object).getTarget()));
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString());
        }
        object = new GLFrame((FrameFormat)object, this);
        ((GLFrame)object).init(this.getGLEnvironment());
        return object;
    }

    @Override
    public Frame newBoundFrame(FrameFormat object, int n, long l) {
        if (((FrameFormat)object).getTarget() == 3) {
            object = new GLFrame((FrameFormat)object, this, n, l);
            ((GLFrame)object).init(this.getGLEnvironment());
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attached frames are not supported for target type: ");
        stringBuilder.append(FrameFormat.targetToString(((FrameFormat)object).getTarget()));
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public Frame newFrame(FrameFormat frameFormat) {
        return this.createNewFrame(frameFormat);
    }

    @Override
    public Frame releaseFrame(Frame frame) {
        int n = frame.decRefCount();
        if (n == 0 && frame.hasNativeAllocation()) {
            frame.releaseNativeAllocation();
            return null;
        }
        if (n >= 0) {
            return frame;
        }
        throw new RuntimeException("Frame reference count dropped below 0!");
    }

    @Override
    public Frame retainFrame(Frame frame) {
        frame.incRefCount();
        return frame;
    }
}

