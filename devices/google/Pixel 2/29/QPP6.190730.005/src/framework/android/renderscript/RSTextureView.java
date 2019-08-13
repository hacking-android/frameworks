/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.renderscript.RenderScriptGL;
import android.util.AttributeSet;
import android.view.TextureView;

public class RSTextureView
extends TextureView
implements TextureView.SurfaceTextureListener {
    private RenderScriptGL mRS;
    private SurfaceTexture mSurfaceTexture;

    public RSTextureView(Context context) {
        super(context);
        this.init();
    }

    public RSTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    private void init() {
        this.setSurfaceTextureListener(this);
    }

    public RenderScriptGL createRenderScriptGL(RenderScriptGL.SurfaceConfig object) {
        RenderScriptGL renderScriptGL = new RenderScriptGL(this.getContext(), (RenderScriptGL.SurfaceConfig)object);
        this.setRenderScriptGL(renderScriptGL);
        object = this.mSurfaceTexture;
        if (object != null) {
            this.mRS.setSurfaceTexture((SurfaceTexture)object, this.getWidth(), this.getHeight());
        }
        return renderScriptGL;
    }

    public void destroyRenderScriptGL() {
        this.mRS.destroy();
        this.mRS = null;
    }

    public RenderScriptGL getRenderScriptGL() {
        return this.mRS;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture object, int n, int n2) {
        this.mSurfaceTexture = object;
        object = this.mRS;
        if (object != null) {
            ((RenderScriptGL)object).setSurfaceTexture(this.mSurfaceTexture, n, n2);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture object) {
        this.mSurfaceTexture = object;
        object = this.mRS;
        if (object != null) {
            ((RenderScriptGL)object).setSurfaceTexture(null, 0, 0);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture object, int n, int n2) {
        this.mSurfaceTexture = object;
        object = this.mRS;
        if (object != null) {
            ((RenderScriptGL)object).setSurfaceTexture(this.mSurfaceTexture, n, n2);
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
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

    public void setRenderScriptGL(RenderScriptGL object) {
        this.mRS = object;
        object = this.mSurfaceTexture;
        if (object != null) {
            this.mRS.setSurfaceTexture((SurfaceTexture)object, this.getWidth(), this.getHeight());
        }
    }
}

