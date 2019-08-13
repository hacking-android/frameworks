/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier.logging;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.metrics.LogMaker;
import android.util.Log;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextSelection;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.UUID;

public final class SmartSelectionEventTracker {
    private static final String CUSTOM_EDITTEXT = "customedit";
    private static final String CUSTOM_TEXTVIEW = "customview";
    private static final String CUSTOM_UNSELECTABLE_TEXTVIEW = "nosel-customview";
    private static final boolean DEBUG_LOG_ENABLED = true;
    private static final String EDITTEXT = "edittext";
    private static final String EDIT_WEBVIEW = "edit-webview";
    private static final int ENTITY_TYPE = 1254;
    private static final int EVENT_END = 1251;
    private static final int EVENT_START = 1250;
    private static final int INDEX = 1120;
    private static final String LOG_TAG = "SmartSelectEventTracker";
    private static final int MODEL_NAME = 1256;
    private static final int PREV_EVENT_DELTA = 1118;
    private static final int SESSION_ID = 1119;
    private static final int SMART_END = 1253;
    private static final int SMART_START = 1252;
    private static final int START_EVENT_DELTA = 1117;
    private static final String TEXTVIEW = "textview";
    private static final String UNKNOWN = "unknown";
    private static final String UNSELECTABLE_TEXTVIEW = "nosel-textview";
    private static final String WEBVIEW = "webview";
    private static final int WIDGET_TYPE = 1255;
    private static final int WIDGET_VERSION = 1262;
    private static final String ZERO = "0";
    private final Context mContext;
    private int mIndex;
    private long mLastEventTime;
    private final MetricsLogger mMetricsLogger = new MetricsLogger();
    private String mModelName;
    private int mOrigStart;
    private final int[] mPrevIndices = new int[2];
    private String mSessionId;
    private long mSessionStartTime;
    private final int[] mSmartIndices = new int[2];
    private boolean mSmartSelectionTriggered;
    private final int mWidgetType;
    private final String mWidgetVersion;

    @UnsupportedAppUsage
    public SmartSelectionEventTracker(Context context, int n) {
        this.mWidgetType = n;
        this.mWidgetVersion = null;
        this.mContext = Preconditions.checkNotNull(context);
    }

    public SmartSelectionEventTracker(Context context, int n, String string2) {
        this.mWidgetType = n;
        this.mWidgetVersion = string2;
        this.mContext = Preconditions.checkNotNull(context);
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    private static void debugLog(LogMaker logMaker) {
        CharSequence charSequence;
        String string2 = Objects.toString(logMaker.getTaggedData(1255), UNKNOWN);
        String string3 = Objects.toString(logMaker.getTaggedData(1262), "");
        if (!string3.isEmpty()) {
            charSequence = new StringBuilder();
            charSequence.append(string2);
            charSequence.append("-");
            charSequence.append(string3);
            string2 = charSequence.toString();
        }
        int n = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1120), ZERO));
        if (logMaker.getType() == 1101) {
            string3 = Objects.toString(logMaker.getTaggedData(1119), "");
            Log.d(LOG_TAG, String.format("New selection session: %s (%s)", string2, string3.substring(string3.lastIndexOf("-") + 1)));
        }
        charSequence = Objects.toString(logMaker.getTaggedData(1256), UNKNOWN);
        string3 = Objects.toString(logMaker.getTaggedData(1254), UNKNOWN);
        String string4 = SmartSelectionEventTracker.getLogTypeString(logMaker.getType());
        int n2 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1252), ZERO));
        int n3 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1253), ZERO));
        Log.d(LOG_TAG, String.format("%2d: %s/%s, range=%d,%d - smart_range=%d,%d (%s/%s)", n, string4, string3, Integer.parseInt(Objects.toString(logMaker.getTaggedData(1250), ZERO)), Integer.parseInt(Objects.toString(logMaker.getTaggedData(1251), ZERO)), n2, n3, string2, charSequence));
    }

    private void endSession() {
        this.mOrigStart = 0;
        int[] arrn = this.mSmartIndices;
        arrn[1] = 0;
        arrn[0] = 0;
        arrn = this.mPrevIndices;
        arrn[1] = 0;
        arrn[0] = 0;
        this.mIndex = 0;
        this.mSessionStartTime = 0L;
        this.mLastEventTime = 0L;
        this.mSmartSelectionTriggered = false;
        this.mModelName = this.getModelName(null);
        this.mSessionId = null;
    }

    private static int getLogType(SelectionEvent selectionEvent) {
        int n = selectionEvent.mEventType;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 200) {
                                if (n != 201) {
                                    switch (n) {
                                        default: {
                                            return 0;
                                        }
                                        case 108: {
                                            return 1116;
                                        }
                                        case 107: {
                                            return 1115;
                                        }
                                        case 106: {
                                            return 1114;
                                        }
                                        case 105: {
                                            return 1113;
                                        }
                                        case 104: {
                                            return 1112;
                                        }
                                        case 103: {
                                            return 1111;
                                        }
                                        case 102: {
                                            return 1110;
                                        }
                                        case 101: {
                                            return 1109;
                                        }
                                        case 100: 
                                    }
                                    return 1108;
                                }
                                return 1104;
                            }
                            return 1103;
                        }
                        return 1107;
                    }
                    return 1106;
                }
                return 1105;
            }
            return 1102;
        }
        return 1101;
    }

    private static String getLogTypeString(int n) {
        switch (n) {
            default: {
                return UNKNOWN;
            }
            case 1116: {
                return "OTHER";
            }
            case 1115: {
                return "ABANDON";
            }
            case 1114: {
                return "DRAG";
            }
            case 1113: {
                return "SMART_SHARE";
            }
            case 1112: {
                return "SHARE";
            }
            case 1111: {
                return "CUT";
            }
            case 1110: {
                return "PASTE";
            }
            case 1109: {
                return "COPY";
            }
            case 1108: {
                return "OVERTYPE";
            }
            case 1107: {
                return "AUTO_SELECTION";
            }
            case 1106: {
                return "SMART_SELECTION_MULTI";
            }
            case 1105: {
                return "SMART_SELECTION_SINGLE";
            }
            case 1104: {
                return "RESET";
            }
            case 1103: {
                return "SELECT_ALL";
            }
            case 1102: {
                return "SELECTION_MODIFIED";
            }
            case 1101: 
        }
        return "SELECTION_STARTED";
    }

    private String getModelName(SelectionEvent object) {
        String string2 = "";
        object = object == null ? string2 : Objects.toString(((SelectionEvent)object).mVersionTag, "");
        return object;
    }

    private int getRangeDelta(int n) {
        return n - this.mOrigStart;
    }

    private int getSmartRangeDelta(int n) {
        n = this.mSmartSelectionTriggered ? this.getRangeDelta(n) : 0;
        return n;
    }

    private String getWidgetTypeName() {
        switch (this.mWidgetType) {
            default: {
                return UNKNOWN;
            }
            case 8: {
                return CUSTOM_UNSELECTABLE_TEXTVIEW;
            }
            case 7: {
                return CUSTOM_EDITTEXT;
            }
            case 6: {
                return CUSTOM_TEXTVIEW;
            }
            case 5: {
                return UNSELECTABLE_TEXTVIEW;
            }
            case 4: {
                return EDIT_WEBVIEW;
            }
            case 3: {
                return EDITTEXT;
            }
            case 2: {
                return WEBVIEW;
            }
            case 1: 
        }
        return TEXTVIEW;
    }

    private String startNewSession() {
        this.endSession();
        this.mSessionId = SmartSelectionEventTracker.createSessionId();
        return this.mSessionId;
    }

    private void writeEvent(SelectionEvent selectionEvent, long l) {
        long l2 = this.mLastEventTime;
        long l3 = 0L;
        if (l2 != 0L) {
            l3 = l - l2;
        }
        LogMaker logMaker = new LogMaker(1100).setType(SmartSelectionEventTracker.getLogType(selectionEvent)).setSubtype(1).setPackageName(this.mContext.getPackageName()).addTaggedData(1117, l - this.mSessionStartTime).addTaggedData(1118, l3).addTaggedData(1120, this.mIndex).addTaggedData(1255, this.getWidgetTypeName()).addTaggedData(1262, this.mWidgetVersion).addTaggedData(1256, this.mModelName).addTaggedData(1254, selectionEvent.mEntityType).addTaggedData(1252, this.getSmartRangeDelta(this.mSmartIndices[0])).addTaggedData(1253, this.getSmartRangeDelta(this.mSmartIndices[1])).addTaggedData(1250, this.getRangeDelta(selectionEvent.mStart)).addTaggedData(1251, this.getRangeDelta(selectionEvent.mEnd)).addTaggedData(1119, this.mSessionId);
        this.mMetricsLogger.write(logMaker);
        SmartSelectionEventTracker.debugLog(logMaker);
        this.mLastEventTime = l;
        this.mPrevIndices[0] = selectionEvent.mStart;
        this.mPrevIndices[1] = selectionEvent.mEnd;
        ++this.mIndex;
    }

    @UnsupportedAppUsage
    public void logEvent(SelectionEvent selectionEvent) {
        long l;
        block7 : {
            boolean bl;
            block4 : {
                block5 : {
                    block6 : {
                        Preconditions.checkNotNull(selectionEvent);
                        int n = selectionEvent.mEventType;
                        bl = true;
                        if (n != 1 && this.mSessionId == null) {
                            Log.d(LOG_TAG, "Selection session not yet started. Ignoring event");
                            return;
                        }
                        l = System.currentTimeMillis();
                        n = selectionEvent.mEventType;
                        if (n == 1) break block4;
                        if (n == 2) break block5;
                        if (n == 3 || n == 4) break block6;
                        if (n == 5) break block5;
                        break block7;
                    }
                    this.mSmartSelectionTriggered = true;
                    this.mModelName = this.getModelName(selectionEvent);
                    this.mSmartIndices[0] = selectionEvent.mStart;
                    this.mSmartIndices[1] = selectionEvent.mEnd;
                    break block7;
                }
                if (this.mPrevIndices[0] == selectionEvent.mStart && this.mPrevIndices[1] == selectionEvent.mEnd) {
                    return;
                }
                break block7;
            }
            this.mSessionId = this.startNewSession();
            if (selectionEvent.mEnd != selectionEvent.mStart + 1) {
                bl = false;
            }
            Preconditions.checkArgument(bl);
            this.mOrigStart = selectionEvent.mStart;
            this.mSessionStartTime = l;
        }
        this.writeEvent(selectionEvent, l);
        if (selectionEvent.isTerminal()) {
            this.endSession();
        }
    }

    public static final class SelectionEvent {
        private static final String NO_VERSION_TAG = "";
        public static final int OUT_OF_BOUNDS = Integer.MAX_VALUE;
        public static final int OUT_OF_BOUNDS_NEGATIVE = Integer.MIN_VALUE;
        private final int mEnd;
        private final String mEntityType;
        private int mEventType;
        private final int mStart;
        private final String mVersionTag;

        private SelectionEvent(int n, int n2, int n3, String string2, String string3) {
            boolean bl = n2 >= n;
            Preconditions.checkArgument(bl, "end cannot be less than start");
            this.mStart = n;
            this.mEnd = n2;
            this.mEventType = n3;
            this.mEntityType = Preconditions.checkNotNull(string2);
            this.mVersionTag = Preconditions.checkNotNull(string3);
        }

        private static String getSourceClassifier(String string2) {
            int n = string2.indexOf("|");
            if (n >= 0) {
                return string2.substring(0, n);
            }
            return NO_VERSION_TAG;
        }

        private static String getVersionInfo(String string2) {
            int n = string2.indexOf("|");
            int n2 = string2.indexOf("|", n);
            if (n >= 0 && n2 >= n) {
                return string2.substring(n, n2);
            }
            return NO_VERSION_TAG;
        }

        private boolean isTerminal() {
            switch (this.mEventType) {
                default: {
                    return false;
                }
                case 100: 
                case 101: 
                case 102: 
                case 103: 
                case 104: 
                case 105: 
                case 106: 
                case 107: 
                case 108: 
            }
            return true;
        }

        @UnsupportedAppUsage
        public static SelectionEvent selectionAction(int n, int n2, int n3) {
            return new SelectionEvent(n, n2, n3, NO_VERSION_TAG, NO_VERSION_TAG);
        }

        @UnsupportedAppUsage
        public static SelectionEvent selectionAction(int n, int n2, int n3, TextClassification textClassification) {
            String string2 = textClassification.getEntityCount() > 0 ? textClassification.getEntity(0) : NO_VERSION_TAG;
            return new SelectionEvent(n, n2, n3, string2, SelectionEvent.getVersionInfo(textClassification.getId()));
        }

        @UnsupportedAppUsage
        public static SelectionEvent selectionModified(int n, int n2) {
            return new SelectionEvent(n, n2, 2, NO_VERSION_TAG, NO_VERSION_TAG);
        }

        @UnsupportedAppUsage
        public static SelectionEvent selectionModified(int n, int n2, TextClassification textClassification) {
            String string2 = textClassification.getEntityCount() > 0 ? textClassification.getEntity(0) : NO_VERSION_TAG;
            return new SelectionEvent(n, n2, 2, string2, SelectionEvent.getVersionInfo(textClassification.getId()));
        }

        @UnsupportedAppUsage
        public static SelectionEvent selectionModified(int n, int n2, TextSelection textSelection) {
            int n3 = SelectionEvent.getSourceClassifier(textSelection.getId()).equals("androidtc") ? (n2 - n > 1 ? 4 : 3) : 5;
            String string2 = textSelection.getEntityCount() > 0 ? textSelection.getEntity(0) : NO_VERSION_TAG;
            return new SelectionEvent(n, n2, n3, string2, SelectionEvent.getVersionInfo(textSelection.getId()));
        }

        @UnsupportedAppUsage
        public static SelectionEvent selectionStarted(int n) {
            return new SelectionEvent(n, n + 1, 1, NO_VERSION_TAG, NO_VERSION_TAG);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface ActionType {
            public static final int ABANDON = 107;
            public static final int COPY = 101;
            public static final int CUT = 103;
            public static final int DRAG = 106;
            public static final int OTHER = 108;
            public static final int OVERTYPE = 100;
            public static final int PASTE = 102;
            public static final int RESET = 201;
            public static final int SELECT_ALL = 200;
            public static final int SHARE = 104;
            public static final int SMART_SHARE = 105;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        private static @interface EventType {
            public static final int AUTO_SELECTION = 5;
            public static final int SELECTION_MODIFIED = 2;
            public static final int SELECTION_STARTED = 1;
            public static final int SMART_SELECTION_MULTI = 4;
            public static final int SMART_SELECTION_SINGLE = 3;
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WidgetType {
        public static final int CUSTOM_EDITTEXT = 7;
        public static final int CUSTOM_TEXTVIEW = 6;
        public static final int CUSTOM_UNSELECTABLE_TEXTVIEW = 8;
        public static final int EDITTEXT = 3;
        public static final int EDIT_WEBVIEW = 4;
        public static final int TEXTVIEW = 1;
        public static final int UNSELECTABLE_TEXTVIEW = 5;
        public static final int UNSPECIFIED = 0;
        public static final int WEBVIEW = 2;
    }

}

