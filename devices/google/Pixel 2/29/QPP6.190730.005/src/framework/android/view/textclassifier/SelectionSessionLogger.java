/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.content.Context;
import android.metrics.LogMaker;
import android.view.textclassifier.Log;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassificationSessionId;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.Preconditions;
import java.text.BreakIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

public final class SelectionSessionLogger {
    static final String CLASSIFIER_ID = "androidtc";
    private static final int ENTITY_TYPE = 1254;
    private static final int EVENT_END = 1251;
    private static final int EVENT_START = 1250;
    private static final int INDEX = 1120;
    private static final String LOG_TAG = "SelectionSessionLogger";
    private static final int MODEL_NAME = 1256;
    private static final int PREV_EVENT_DELTA = 1118;
    private static final int SESSION_ID = 1119;
    private static final int SMART_END = 1253;
    private static final int SMART_START = 1252;
    private static final int START_EVENT_DELTA = 1117;
    private static final String UNKNOWN = "unknown";
    private static final int WIDGET_TYPE = 1255;
    private static final int WIDGET_VERSION = 1262;
    private static final String ZERO = "0";
    private final MetricsLogger mMetricsLogger;

    public SelectionSessionLogger() {
        this.mMetricsLogger = new MetricsLogger();
    }

    @VisibleForTesting
    public SelectionSessionLogger(MetricsLogger metricsLogger) {
        this.mMetricsLogger = Preconditions.checkNotNull(metricsLogger);
    }

    public static String createId(String string2, int n, int n2, Context context, int n3, List<Locale> object) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(object);
        StringJoiner stringJoiner = new StringJoiner(",");
        object = object.iterator();
        while (object.hasNext()) {
            stringJoiner.add(((Locale)object.next()).toLanguageTag());
        }
        return SignatureParser.createSignature(CLASSIFIER_ID, String.format(Locale.US, "%s_v%d", stringJoiner.toString(), n3), Objects.hash(string2, n, n2, context.getPackageName()));
    }

    private static void debugLog(LogMaker logMaker) {
        CharSequence charSequence;
        if (!Log.ENABLE_FULL_LOGGING) {
            return;
        }
        String string2 = Objects.toString(logMaker.getTaggedData(1255), UNKNOWN);
        String string3 = Objects.toString(logMaker.getTaggedData(1262), "");
        if (!string3.isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append(string3);
            string2 = ((StringBuilder)charSequence).toString();
        }
        int n = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1120), ZERO));
        if (logMaker.getType() == 1101) {
            charSequence = Objects.toString(logMaker.getTaggedData(1119), "");
            Log.d(LOG_TAG, String.format("New selection session: %s (%s)", string2, ((String)charSequence).substring(((String)charSequence).lastIndexOf("-") + 1)));
        }
        String string4 = Objects.toString(logMaker.getTaggedData(1256), UNKNOWN);
        charSequence = Objects.toString(logMaker.getTaggedData(1254), UNKNOWN);
        string3 = SelectionSessionLogger.getLogTypeString(logMaker.getType());
        String string5 = SelectionSessionLogger.getLogSubTypeString(logMaker.getSubtype());
        int n2 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1252), ZERO));
        int n3 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1253), ZERO));
        int n4 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1250), ZERO));
        int n5 = Integer.parseInt(Objects.toString(logMaker.getTaggedData(1251), ZERO));
        Log.v(LOG_TAG, String.format(Locale.US, "%2d: %s/%s/%s, range=%d,%d - smart_range=%d,%d (%s/%s)", n, string3, string5, charSequence, n4, n5, n2, n3, string2, string4));
    }

    private static int getLogSubType(SelectionEvent selectionEvent) {
        int n = selectionEvent.getInvocationMethod();
        if (n != 1) {
            if (n != 2) {
                return 0;
            }
            return 2;
        }
        return 1;
    }

    private static String getLogSubTypeString(int n) {
        if (n != 1) {
            if (n != 2) {
                return UNKNOWN;
            }
            return "LINK";
        }
        return "MANUAL";
    }

    private static int getLogType(SelectionEvent selectionEvent) {
        int n = selectionEvent.getEventType();
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

    public static BreakIterator getTokenIterator(Locale locale) {
        return BreakIterator.getWordInstance(Preconditions.checkNotNull(locale));
    }

    static boolean isPlatformLocalTextClassifierSmartSelection(String string2) {
        return CLASSIFIER_ID.equals(SignatureParser.getClassifierId(string2));
    }

    public void writeEvent(SelectionEvent selectionEvent) {
        Preconditions.checkNotNull(selectionEvent);
        LogMaker logMaker = new LogMaker(1100).setType(SelectionSessionLogger.getLogType(selectionEvent)).setSubtype(SelectionSessionLogger.getLogSubType(selectionEvent)).setPackageName(selectionEvent.getPackageName()).addTaggedData(1117, selectionEvent.getDurationSinceSessionStart()).addTaggedData(1118, selectionEvent.getDurationSincePreviousEvent()).addTaggedData(1120, selectionEvent.getEventIndex()).addTaggedData(1255, selectionEvent.getWidgetType()).addTaggedData(1262, selectionEvent.getWidgetVersion()).addTaggedData(1254, selectionEvent.getEntityType()).addTaggedData(1250, selectionEvent.getStart()).addTaggedData(1251, selectionEvent.getEnd());
        if (SelectionSessionLogger.isPlatformLocalTextClassifierSmartSelection(selectionEvent.getResultId())) {
            logMaker.addTaggedData(1256, SignatureParser.getModelName(selectionEvent.getResultId())).addTaggedData(1252, selectionEvent.getSmartStart()).addTaggedData(1253, selectionEvent.getSmartEnd());
        }
        if (selectionEvent.getSessionId() != null) {
            logMaker.addTaggedData(1119, selectionEvent.getSessionId().flattenToString());
        }
        this.mMetricsLogger.write(logMaker);
        SelectionSessionLogger.debugLog(logMaker);
    }

    @VisibleForTesting
    public static final class SignatureParser {
        static String createSignature(String string2, String string3, int n) {
            return String.format(Locale.US, "%s|%s|%d", string2, string3, n);
        }

        static String getClassifierId(String string2) {
            if (string2 == null) {
                return "";
            }
            int n = string2.indexOf("|");
            if (n >= 0) {
                return string2.substring(0, n);
            }
            return "";
        }

        static int getHash(String string2) {
            if (string2 == null) {
                return 0;
            }
            int n = string2.indexOf("|", string2.indexOf("|"));
            if (n > 0) {
                return Integer.parseInt(string2.substring(n));
            }
            return 0;
        }

        static String getModelName(String string2) {
            if (string2 == null) {
                return "";
            }
            int n = string2.indexOf("|") + 1;
            int n2 = string2.indexOf("|", n);
            if (n >= 1 && n2 >= n) {
                return string2.substring(n, n2);
            }
            return "";
        }
    }

}

