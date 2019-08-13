/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.renderscript.RenderScriptGL;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RSSurfaceView
extends SurfaceView
implements SurfaceHolder.Callback {
    private RenderScriptGL mRS;
    private SurfaceHolder mSurfaceHolder;

    @UnsupportedAppUsage
    public RSSurfaceView(Context context) {
        super(context);
        this.init();
    }

    @UnsupportedAppUsage
    public RSSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.getHolder().addCallback(this);
    }

    public RenderScriptGL createRenderScriptGL(RenderScriptGL.SurfaceConfig object) {
        object = new RenderScriptGL(this.getContext(), (RenderScriptGL.SurfaceConfig)object);
        this.setRenderScriptGL((RenderScriptGL)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void destroyRenderScriptGL() {
        synchronized (this) {
            this.mRS.destroy();
            this.mRS = null;
            return;
        }
    }

    public RenderScriptGL getRenderScriptGL() {
        return this.mRS;
    }

    public void pause() {
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL != null) {
            renderScriptGL.pause();
        }
    }

    public void resume() {
        RenderScriptGL renderScriptGL = this.mRS;
        if (renderScriptGL != null) {
            renderScriptGL.resume();
        }
    }

    public void setRenderScriptGL(RenderScriptGL renderScriptGL) {
        this.mRS = renderScriptGL;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        synchronized (this) {
            if (this.mRS != null) {
                this.mRS.setSurface(surfaceHolder, n2, n3);
            }
            return;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (this) {
            if (this.mRS != null) {
                this.mRS.setSurface(null, 0, 0);
            }
            return;
        }
    }
}

