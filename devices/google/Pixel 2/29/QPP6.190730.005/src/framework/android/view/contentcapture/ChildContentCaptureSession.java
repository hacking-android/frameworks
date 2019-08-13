/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.view.autofill.AutofillId;
import android.view.contentcapture.ContentCaptureContext;
import android.view.contentcapture.ContentCaptureSession;
import android.view.contentcapture.MainContentCaptureSession;
import android.view.contentcapture.ViewNode;

final class ChildContentCaptureSession
extends ContentCaptureSession {
    private final ContentCaptureSession mParent;

    protected ChildContentCaptureSession(ContentCaptureSession contentCaptureSession, ContentCaptureContext contentCaptureContext) {
        super(contentCaptureContext);
        this.mParent = contentCaptureSession;
    }

    @Override
    void flush(int n) {
        this.mParent.flush(n);
    }

    @Override
    MainContentCaptureSession getMainCaptureSession() {
        ContentCaptureSession contentCaptureSession = this.mParent;
        if (contentCaptureSession instanceof MainContentCaptureSession) {
            return (MainContentCaptureSession)contentCaptureSession;
        }
        return contentCaptureSession.getMainCaptureSession();
    }

    @Override
    void internalNotifyViewAppeared(ViewNode.ViewStructureImpl viewStructureImpl) {
        this.getMainCaptureSession().notifyViewAppeared(this.mId, viewStructureImpl);
    }

    @Override
    void internalNotifyViewDisappeared(AutofillId autofillId) {
        this.getMainCaptureSession().notifyViewDisappeared(this.mId, autofillId);
    }

    @Override
    void internalNotifyViewTextChanged(AutofillId autofillId, CharSequence charSequence) {
        this.getMainCaptureSession().notifyViewTextChanged(this.mId, autofillId, charSequence);
    }

    @Override
    public void internalNotifyViewTreeEvent(boolean bl) {
        this.getMainCaptureSession().notifyViewTreeEvent(this.mId, bl);
    }

    @Override
    boolean isContentCaptureEnabled() {
        return this.getMainCaptureSession().isContentCaptureEnabled();
    }

    @Override
    ContentCaptureSession newChild(ContentCaptureContext contentCaptureContext) {
        ChildContentCaptureSession childContentCaptureSession = new ChildContentCaptureSession(this, contentCaptureContext);
        this.getMainCaptureSession().notifyChildSessionStarted(this.mId, childContentCaptureSession.mId, contentCaptureContext);
        return childContentCaptureSession;
    }

    @Override
    void onDestroy() {
        this.getMainCaptureSession().notifyChildSessionFinished(this.mParent.mId, this.mId);
    }

    @Override
    public void updateContentCaptureContext(ContentCaptureContext contentCaptureContext) {
        this.getMainCaptureSession().notifyContextUpdated(this.mId, contentCaptureContext);
    }
}

