/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.textclassifier;

import com.google.android.textclassifier.AnnotatorModel;
import com.google.android.textclassifier.NamedVariant;
import com.google.android.textclassifier.RemoteActionTemplate;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ActionsSuggestionsModel
implements AutoCloseable {
    private long actionsModelPtr;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    static {
        System.loadLibrary("textclassifier");
    }

    public ActionsSuggestionsModel(int n) {
        this(n, null);
    }

    public ActionsSuggestionsModel(int n, byte[] arrby) {
        this.actionsModelPtr = ActionsSuggestionsModel.nativeNewActionsModel(n, arrby);
        if (this.actionsModelPtr != 0L) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize actions model from file descriptor.");
    }

    public ActionsSuggestionsModel(String string) {
        this(string, null);
    }

    public ActionsSuggestionsModel(String string, byte[] arrby) {
        this.actionsModelPtr = ActionsSuggestionsModel.nativeNewActionsModelFromPath(string, arrby);
        if (this.actionsModelPtr != 0L) {
            return;
        }
        throw new IllegalArgumentException("Couldn't initialize actions model from given file.");
    }

    public static String getLocales(int n) {
        return ActionsSuggestionsModel.nativeGetLocales(n);
    }

    public static String getName(int n) {
        return ActionsSuggestionsModel.nativeGetName(n);
    }

    public static int getVersion(int n) {
        return ActionsSuggestionsModel.nativeGetVersion(n);
    }

    private native void nativeCloseActionsModel(long var1);

    private static native String nativeGetLocales(int var0);

    private static native String nativeGetName(int var0);

    private static native int nativeGetVersion(int var0);

    private static native long nativeNewActionsModel(int var0, byte[] var1);

    private static native long nativeNewActionsModelFromPath(String var0, byte[] var1);

    private native ActionSuggestion[] nativeSuggestActions(long var1, Conversation var3, ActionSuggestionOptions var4, long var5, Object var7, String var8, boolean var9);

    @Override
    public void close() {
        if (this.isClosed.compareAndSet(false, true)) {
            this.nativeCloseActionsModel(this.actionsModelPtr);
            this.actionsModelPtr = 0L;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public ActionSuggestion[] suggestActions(Conversation conversation, ActionSuggestionOptions actionSuggestionOptions, AnnotatorModel annotatorModel) {
        long l = this.actionsModelPtr;
        long l2 = annotatorModel != null ? annotatorModel.getNativeAnnotator() : 0L;
        return this.nativeSuggestActions(l, conversation, actionSuggestionOptions, l2, null, null, false);
    }

    public ActionSuggestion[] suggestActionsWithIntents(Conversation conversation, ActionSuggestionOptions actionSuggestionOptions, Object object, String string, AnnotatorModel annotatorModel) {
        long l = this.actionsModelPtr;
        long l2 = annotatorModel != null ? annotatorModel.getNativeAnnotator() : 0L;
        return this.nativeSuggestActions(l, conversation, actionSuggestionOptions, l2, object, string, true);
    }

    public static final class ActionSuggestion {
        private final String actionType;
        private final NamedVariant[] entityData;
        private final RemoteActionTemplate[] remoteActionTemplates;
        private final String responseText;
        private final float score;
        private final byte[] serializedEntityData;

        public ActionSuggestion(String string, String string2, float f, NamedVariant[] arrnamedVariant, byte[] arrby, RemoteActionTemplate[] arrremoteActionTemplate) {
            this.responseText = string;
            this.actionType = string2;
            this.score = f;
            this.entityData = arrnamedVariant;
            this.serializedEntityData = arrby;
            this.remoteActionTemplates = arrremoteActionTemplate;
        }

        public String getActionType() {
            return this.actionType;
        }

        public NamedVariant[] getEntityData() {
            return this.entityData;
        }

        public RemoteActionTemplate[] getRemoteActionTemplates() {
            return this.remoteActionTemplates;
        }

        public String getResponseText() {
            return this.responseText;
        }

        public float getScore() {
            return this.score;
        }

        public byte[] getSerializedEntityData() {
            return this.serializedEntityData;
        }
    }

    public static final class ActionSuggestionOptions {
    }

    public static final class Conversation {
        public final ConversationMessage[] conversationMessages;

        public Conversation(ConversationMessage[] arrconversationMessage) {
            this.conversationMessages = arrconversationMessage;
        }

        public ConversationMessage[] getConversationMessages() {
            return this.conversationMessages;
        }
    }

    public static final class ConversationMessage {
        private final String detectedTextLanguageTags;
        private final long referenceTimeMsUtc;
        private final String referenceTimezone;
        private final String text;
        private final int userId;

        public ConversationMessage(int n, String string, long l, String string2, String string3) {
            this.userId = n;
            this.text = string;
            this.referenceTimeMsUtc = l;
            this.referenceTimezone = string2;
            this.detectedTextLanguageTags = string3;
        }

        public String getDetectedTextLanguageTags() {
            return this.detectedTextLanguageTags;
        }

        public long getReferenceTimeMsUtc() {
            return this.referenceTimeMsUtc;
        }

        public String getReferenceTimezone() {
            return this.referenceTimezone;
        }

        public String getText() {
            return this.text;
        }

        public int getUserId() {
            return this.userId;
        }
    }

}

