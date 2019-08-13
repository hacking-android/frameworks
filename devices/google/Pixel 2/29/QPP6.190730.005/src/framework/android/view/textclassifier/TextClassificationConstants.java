/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.view.textclassifier.ConfigParser;
import com.android.internal.util.IndentingPrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class TextClassificationConstants {
    private static final String CLASSIFY_TEXT_MAX_RANGE_LENGTH = "classify_text_max_range_length";
    private static final int CLASSIFY_TEXT_MAX_RANGE_LENGTH_DEFAULT = 10000;
    private static final List<String> CONVERSATION_ACTIONS_TYPES_DEFAULT_VALUES;
    private static final String DETECT_LANGUAGES_FROM_TEXT_ENABLED = "detect_languages_from_text_enabled";
    private static final boolean DETECT_LANGUAGES_FROM_TEXT_ENABLED_DEFAULT = true;
    private static final String ENTITY_LIST_DEFAULT = "entity_list_default";
    private static final List<String> ENTITY_LIST_DEFAULT_VALUE;
    private static final String ENTITY_LIST_EDITABLE = "entity_list_editable";
    private static final String ENTITY_LIST_NOT_EDITABLE = "entity_list_not_editable";
    private static final String GENERATE_LINKS_LOG_SAMPLE_RATE = "generate_links_log_sample_rate";
    private static final int GENERATE_LINKS_LOG_SAMPLE_RATE_DEFAULT = 100;
    private static final String GENERATE_LINKS_MAX_TEXT_LENGTH = "generate_links_max_text_length";
    private static final int GENERATE_LINKS_MAX_TEXT_LENGTH_DEFAULT = 100000;
    private static final String IN_APP_CONVERSATION_ACTION_TYPES_DEFAULT = "in_app_conversation_action_types_default";
    private static final String LANG_ID_CONTEXT_SETTINGS = "lang_id_context_settings";
    private static final float[] LANG_ID_CONTEXT_SETTINGS_DEFAULT;
    private static final String LANG_ID_THRESHOLD_OVERRIDE = "lang_id_threshold_override";
    private static final float LANG_ID_THRESHOLD_OVERRIDE_DEFAULT = -1.0f;
    private static final String LOCAL_TEXT_CLASSIFIER_ENABLED = "local_textclassifier_enabled";
    private static final boolean LOCAL_TEXT_CLASSIFIER_ENABLED_DEFAULT = true;
    private static final String MODEL_DARK_LAUNCH_ENABLED = "model_dark_launch_enabled";
    private static final boolean MODEL_DARK_LAUNCH_ENABLED_DEFAULT = false;
    private static final String NOTIFICATION_CONVERSATION_ACTION_TYPES_DEFAULT = "notification_conversation_action_types_default";
    private static final String SMART_LINKIFY_ENABLED = "smart_linkify_enabled";
    private static final boolean SMART_LINKIFY_ENABLED_DEFAULT = true;
    private static final String SMART_SELECTION_ENABLED = "smart_selection_enabled";
    private static final boolean SMART_SELECTION_ENABLED_DEFAULT = true;
    private static final String SMART_SELECT_ANIMATION_ENABLED = "smart_select_animation_enabled";
    private static final boolean SMART_SELECT_ANIMATION_ENABLED_DEFAULT = true;
    private static final String SMART_TEXT_SHARE_ENABLED = "smart_text_share_enabled";
    private static final boolean SMART_TEXT_SHARE_ENABLED_DEFAULT = true;
    private static final String SUGGEST_SELECTION_MAX_RANGE_LENGTH = "suggest_selection_max_range_length";
    private static final int SUGGEST_SELECTION_MAX_RANGE_LENGTH_DEFAULT = 10000;
    private static final String SYSTEM_TEXT_CLASSIFIER_ENABLED = "system_textclassifier_enabled";
    private static final boolean SYSTEM_TEXT_CLASSIFIER_ENABLED_DEFAULT = true;
    private static final String TEMPLATE_INTENT_FACTORY_ENABLED = "template_intent_factory_enabled";
    private static final boolean TEMPLATE_INTENT_FACTORY_ENABLED_DEFAULT = true;
    private static final String TRANSLATE_IN_CLASSIFICATION_ENABLED = "translate_in_classification_enabled";
    private static final boolean TRANSLATE_IN_CLASSIFICATION_ENABLED_DEFAULT = true;
    private final ConfigParser mConfigParser;

    static {
        ENTITY_LIST_DEFAULT_VALUE = Arrays.asList("address", "email", "phone", "url", "date", "datetime", "flight");
        CONVERSATION_ACTIONS_TYPES_DEFAULT_VALUES = Arrays.asList("text_reply", "create_reminder", "call_phone", "open_url", "send_email", "send_sms", "track_flight", "view_calendar", "view_map", "add_contact", "copy");
        LANG_ID_CONTEXT_SETTINGS_DEFAULT = new float[]{20.0f, 1.0f, 0.4f};
    }

    public TextClassificationConstants(Supplier<String> supplier) {
        this.mConfigParser = new ConfigParser(supplier);
    }

    void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("TextClassificationConstants:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair(CLASSIFY_TEXT_MAX_RANGE_LENGTH, this.getClassifyTextMaxRangeLength()).println();
        indentingPrintWriter.printPair("detect_language_from_text_enabled", this.isDetectLanguagesFromTextEnabled()).println();
        indentingPrintWriter.printPair(ENTITY_LIST_DEFAULT, this.getEntityListDefault()).println();
        indentingPrintWriter.printPair(ENTITY_LIST_EDITABLE, this.getEntityListEditable()).println();
        indentingPrintWriter.printPair(ENTITY_LIST_NOT_EDITABLE, this.getEntityListNotEditable()).println();
        indentingPrintWriter.printPair(GENERATE_LINKS_LOG_SAMPLE_RATE, this.getGenerateLinksLogSampleRate()).println();
        indentingPrintWriter.printPair(GENERATE_LINKS_MAX_TEXT_LENGTH, this.getGenerateLinksMaxTextLength()).println();
        indentingPrintWriter.printPair(IN_APP_CONVERSATION_ACTION_TYPES_DEFAULT, this.getInAppConversationActionTypes()).println();
        indentingPrintWriter.printPair(LANG_ID_CONTEXT_SETTINGS, Arrays.toString(this.getLangIdContextSettings())).println();
        indentingPrintWriter.printPair(LANG_ID_THRESHOLD_OVERRIDE, Float.valueOf(this.getLangIdThresholdOverride())).println();
        indentingPrintWriter.printPair(LOCAL_TEXT_CLASSIFIER_ENABLED, this.isLocalTextClassifierEnabled()).println();
        indentingPrintWriter.printPair(MODEL_DARK_LAUNCH_ENABLED, this.isModelDarkLaunchEnabled()).println();
        indentingPrintWriter.printPair(NOTIFICATION_CONVERSATION_ACTION_TYPES_DEFAULT, this.getNotificationConversationActionTypes()).println();
        indentingPrintWriter.printPair(SMART_LINKIFY_ENABLED, this.isSmartLinkifyEnabled()).println();
        indentingPrintWriter.printPair(SMART_SELECT_ANIMATION_ENABLED, this.isSmartSelectionAnimationEnabled()).println();
        indentingPrintWriter.printPair(SMART_SELECTION_ENABLED, this.isSmartSelectionEnabled()).println();
        indentingPrintWriter.printPair(SMART_TEXT_SHARE_ENABLED, this.isSmartTextShareEnabled()).println();
        indentingPrintWriter.printPair(SUGGEST_SELECTION_MAX_RANGE_LENGTH, this.getSuggestSelectionMaxRangeLength()).println();
        indentingPrintWriter.printPair(SYSTEM_TEXT_CLASSIFIER_ENABLED, this.isSystemTextClassifierEnabled()).println();
        indentingPrintWriter.printPair(TEMPLATE_INTENT_FACTORY_ENABLED, this.isTemplateIntentFactoryEnabled()).println();
        indentingPrintWriter.printPair(TRANSLATE_IN_CLASSIFICATION_ENABLED, this.isTranslateInClassificationEnabled()).println();
        indentingPrintWriter.decreaseIndent();
    }

    public int getClassifyTextMaxRangeLength() {
        return this.mConfigParser.getInt(CLASSIFY_TEXT_MAX_RANGE_LENGTH, 10000);
    }

    public List<String> getEntityListDefault() {
        return this.mConfigParser.getStringList(ENTITY_LIST_DEFAULT, ENTITY_LIST_DEFAULT_VALUE);
    }

    public List<String> getEntityListEditable() {
        return this.mConfigParser.getStringList(ENTITY_LIST_EDITABLE, ENTITY_LIST_DEFAULT_VALUE);
    }

    public List<String> getEntityListNotEditable() {
        return this.mConfigParser.getStringList(ENTITY_LIST_NOT_EDITABLE, ENTITY_LIST_DEFAULT_VALUE);
    }

    public int getGenerateLinksLogSampleRate() {
        return this.mConfigParser.getInt(GENERATE_LINKS_LOG_SAMPLE_RATE, 100);
    }

    public int getGenerateLinksMaxTextLength() {
        return this.mConfigParser.getInt(GENERATE_LINKS_MAX_TEXT_LENGTH, 100000);
    }

    public List<String> getInAppConversationActionTypes() {
        return this.mConfigParser.getStringList(IN_APP_CONVERSATION_ACTION_TYPES_DEFAULT, CONVERSATION_ACTIONS_TYPES_DEFAULT_VALUES);
    }

    public float[] getLangIdContextSettings() {
        return this.mConfigParser.getFloatArray(LANG_ID_CONTEXT_SETTINGS, LANG_ID_CONTEXT_SETTINGS_DEFAULT);
    }

    public float getLangIdThresholdOverride() {
        return this.mConfigParser.getFloat(LANG_ID_THRESHOLD_OVERRIDE, -1.0f);
    }

    public List<String> getNotificationConversationActionTypes() {
        return this.mConfigParser.getStringList(NOTIFICATION_CONVERSATION_ACTION_TYPES_DEFAULT, CONVERSATION_ACTIONS_TYPES_DEFAULT_VALUES);
    }

    public int getSuggestSelectionMaxRangeLength() {
        return this.mConfigParser.getInt(SUGGEST_SELECTION_MAX_RANGE_LENGTH, 10000);
    }

    public boolean isDetectLanguagesFromTextEnabled() {
        return this.mConfigParser.getBoolean(DETECT_LANGUAGES_FROM_TEXT_ENABLED, true);
    }

    public boolean isLocalTextClassifierEnabled() {
        return this.mConfigParser.getBoolean(LOCAL_TEXT_CLASSIFIER_ENABLED, true);
    }

    public boolean isModelDarkLaunchEnabled() {
        return this.mConfigParser.getBoolean(MODEL_DARK_LAUNCH_ENABLED, false);
    }

    public boolean isSmartLinkifyEnabled() {
        return this.mConfigParser.getBoolean(SMART_LINKIFY_ENABLED, true);
    }

    public boolean isSmartSelectionAnimationEnabled() {
        return this.mConfigParser.getBoolean(SMART_SELECT_ANIMATION_ENABLED, true);
    }

    public boolean isSmartSelectionEnabled() {
        return this.mConfigParser.getBoolean(SMART_SELECTION_ENABLED, true);
    }

    public boolean isSmartTextShareEnabled() {
        return this.mConfigParser.getBoolean(SMART_TEXT_SHARE_ENABLED, true);
    }

    public boolean isSystemTextClassifierEnabled() {
        return this.mConfigParser.getBoolean(SYSTEM_TEXT_CLASSIFIER_ENABLED, true);
    }

    public boolean isTemplateIntentFactoryEnabled() {
        return this.mConfigParser.getBoolean(TEMPLATE_INTENT_FACTORY_ENABLED, true);
    }

    public boolean isTranslateInClassificationEnabled() {
        return this.mConfigParser.getBoolean(TRANSLATE_IN_CLASSIFICATION_ENABLED, true);
    }
}

