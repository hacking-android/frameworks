/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.view.textclassifier.Log;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.SelectionSessionLogger;
import android.view.textclassifier.SystemTextClassifier;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.util.Preconditions;

final class TextClassificationSession
implements TextClassifier {
    private static final String LOG_TAG = "TextClassificationSession";
    private final TextClassificationContext mClassificationContext;
    private final TextClassifier mDelegate;
    private boolean mDestroyed;
    private final SelectionEventHelper mEventHelper;
    private final TextClassificationSessionId mSessionId;

    TextClassificationSession(TextClassificationContext textClassificationContext, TextClassifier textClassifier) {
        this.mClassificationContext = Preconditions.checkNotNull(textClassificationContext);
        this.mDelegate = Preconditions.checkNotNull(textClassifier);
        this.mSessionId = new TextClassificationSessionId();
        this.mEventHelper = new SelectionEventHelper(this.mSessionId, this.mClassificationContext);
        this.initializeRemoteSession();
    }

    private void checkDestroyed() {
        if (!this.mDestroyed) {
            return;
        }
        throw new IllegalStateException("This TextClassification session has been destroyed");
    }

    private void initializeRemoteSession() {
        TextClassifier textClassifier = this.mDelegate;
        if (textClassifier instanceof SystemTextClassifier) {
            ((SystemTextClassifier)textClassifier).initializeRemoteSession(this.mClassificationContext, this.mSessionId);
        }
    }

    @Override
    public TextClassification classifyText(TextClassification.Request request) {
        this.checkDestroyed();
        return this.mDelegate.classifyText(request);
    }

    @Override
    public void destroy() {
        this.mEventHelper.endSession();
        this.mDelegate.destroy();
        this.mDestroyed = true;
    }

    @Override
    public TextLinks generateLinks(TextLinks.Request request) {
        this.checkDestroyed();
        return this.mDelegate.generateLinks(request);
    }

    @Override
    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    @Override
    public void onSelectionEvent(SelectionEvent selectionEvent) {
        try {
            if (this.mEventHelper.sanitizeEvent(selectionEvent)) {
                this.mDelegate.onSelectionEvent(selectionEvent);
            }
        }
        catch (Exception exception) {
            Log.e(LOG_TAG, "Error reporting text classifier selection event", exception);
        }
    }

    @Override
    public void onTextClassifierEvent(TextClassifierEvent textClassifierEvent) {
        try {
            textClassifierEvent.mHiddenTempSessionId = this.mSessionId;
            this.mDelegate.onTextClassifierEvent(textClassifierEvent);
        }
        catch (Exception exception) {
            Log.e(LOG_TAG, "Error reporting text classifier event", exception);
        }
    }

    @Override
    public TextSelection suggestSelection(TextSelection.Request request) {
        this.checkDestroyed();
        return this.mDelegate.suggestSelection(request);
    }

    private static final class SelectionEventHelper {
        private final TextClassificationContext mContext;
        private int mInvocationMethod = 0;
        private SelectionEvent mPrevEvent;
        private final TextClassificationSessionId mSessionId;
        private SelectionEvent mSmartEvent;
        private SelectionEvent mStartEvent;

        SelectionEventHelper(TextClassificationSessionId textClassificationSessionId, TextClassificationContext textClassificationContext) {
            this.mSessionId = Preconditions.checkNotNull(textClassificationSessionId);
            this.mContext = Preconditions.checkNotNull(textClassificationContext);
        }

        private void modifyAutoSelectionEventType(SelectionEvent selectionEvent) {
            int n = selectionEvent.getEventType();
            if (n != 3 && n != 4 && n != 5) {
                return;
            }
            if (SelectionSessionLogger.isPlatformLocalTextClassifierSmartSelection(selectionEvent.getResultId())) {
                if (selectionEvent.getAbsoluteEnd() - selectionEvent.getAbsoluteStart() > 1) {
                    selectionEvent.setEventType(4);
                } else {
                    selectionEvent.setEventType(3);
                }
            } else {
                selectionEvent.setEventType(5);
            }
        }

        private void updateInvocationMethod(SelectionEvent selectionEvent) {
            selectionEvent.setTextClassificationSessionContext(this.mContext);
            if (selectionEvent.getInvocationMethod() == 0) {
                selectionEvent.setInvocationMethod(this.mInvocationMethod);
            } else {
                this.mInvocationMethod = selectionEvent.getInvocationMethod();
            }
        }

        void endSession() {
            this.mPrevEvent = null;
            this.mSmartEvent = null;
            this.mStartEvent = null;
        }

        boolean sanitizeEvent(SelectionEvent selectionEvent) {
            SelectionEvent selectionEvent2;
            this.updateInvocationMethod(selectionEvent);
            this.modifyAutoSelectionEventType(selectionEvent);
            int n = selectionEvent.getEventType();
            boolean bl = false;
            if (n != 1 && this.mStartEvent == null) {
                Log.d(TextClassificationSession.LOG_TAG, "Selection session not yet started. Ignoring event");
                return false;
            }
            long l = System.currentTimeMillis();
            n = selectionEvent.getEventType();
            if (n != 1) {
                if (n != 2) {
                    if (n == 3 || n == 4 || n == 5) {
                        this.mSmartEvent = selectionEvent;
                    }
                } else {
                    selectionEvent2 = this.mPrevEvent;
                    if (selectionEvent2 != null && selectionEvent2.getAbsoluteStart() == selectionEvent.getAbsoluteStart() && this.mPrevEvent.getAbsoluteEnd() == selectionEvent.getAbsoluteEnd()) {
                        return false;
                    }
                }
            } else {
                if (selectionEvent.getAbsoluteEnd() == selectionEvent.getAbsoluteStart() + 1) {
                    bl = true;
                }
                Preconditions.checkArgument(bl);
                selectionEvent.setSessionId(this.mSessionId);
                this.mStartEvent = selectionEvent;
            }
            selectionEvent.setEventTime(l);
            selectionEvent2 = this.mStartEvent;
            if (selectionEvent2 != null) {
                selectionEvent.setSessionId(selectionEvent2.getSessionId()).setDurationSinceSessionStart(l - this.mStartEvent.getEventTime()).setStart(selectionEvent.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart()).setEnd(selectionEvent.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            if ((selectionEvent2 = this.mSmartEvent) != null) {
                selectionEvent.setResultId(selectionEvent2.getResultId()).setSmartStart(this.mSmartEvent.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart()).setSmartEnd(this.mSmartEvent.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            if ((selectionEvent2 = this.mPrevEvent) != null) {
                selectionEvent.setDurationSincePreviousEvent(l - selectionEvent2.getEventTime()).setEventIndex(this.mPrevEvent.getEventIndex() + 1);
            }
            this.mPrevEvent = selectionEvent;
            return true;
        }
    }

}

