/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.metrics.LogMaker;
import android.os.Parcelable;
import android.view.textclassifier.Log;
import android.view.textclassifier.SelectionSessionLogger;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassifierEvent;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.Preconditions;

public final class TextClassifierEventTronLogger {
    private static final String TAG = "TCEventTronLogger";
    private final MetricsLogger mMetricsLogger;

    public TextClassifierEventTronLogger() {
        this(new MetricsLogger());
    }

    @VisibleForTesting
    public TextClassifierEventTronLogger(MetricsLogger metricsLogger) {
        this.mMetricsLogger = Preconditions.checkNotNull(metricsLogger);
    }

    private void debugLog(LogMaker object) {
        if (!Log.ENABLE_FULL_LOGGING) {
            return;
        }
        String string2 = String.valueOf(((LogMaker)object).getTaggedData(1634));
        String string3 = this.toCategoryName(((LogMaker)object).getCategory());
        String string4 = this.toEventName(((LogMaker)object).getSubtype());
        String string5 = String.valueOf(((LogMaker)object).getTaggedData(1639));
        String string6 = String.valueOf(((LogMaker)object).getTaggedData(1640));
        String string7 = String.valueOf(((LogMaker)object).getTaggedData(1256));
        String string8 = String.valueOf(((LogMaker)object).getTaggedData(1635));
        String string9 = String.valueOf(((LogMaker)object).getTaggedData(1636));
        String string10 = String.valueOf(((LogMaker)object).getTaggedData(1637));
        String string11 = String.valueOf(((LogMaker)object).getTaggedData(1638));
        object = new StringBuilder();
        ((StringBuilder)object).append("writeEvent: ");
        ((StringBuilder)object).append("id=");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(", category=");
        ((StringBuilder)object).append(string3);
        ((StringBuilder)object).append(", eventName=");
        ((StringBuilder)object).append(string4);
        ((StringBuilder)object).append(", widgetType=");
        ((StringBuilder)object).append(string5);
        ((StringBuilder)object).append(", widgetVersion=");
        ((StringBuilder)object).append(string6);
        ((StringBuilder)object).append(", model=");
        ((StringBuilder)object).append(string7);
        ((StringBuilder)object).append(", firstEntityType=");
        ((StringBuilder)object).append(string8);
        ((StringBuilder)object).append(", secondEntityType=");
        ((StringBuilder)object).append(string9);
        ((StringBuilder)object).append(", thirdEntityType=");
        ((StringBuilder)object).append(string10);
        ((StringBuilder)object).append(", score=");
        ((StringBuilder)object).append(string11);
        Log.v(TAG, ((StringBuilder)object).toString());
    }

    private static int getCategory(TextClassifierEvent textClassifierEvent) {
        int n = textClassifierEvent.getEventCategory();
        if (n != 3) {
            if (n != 4) {
                return -1;
            }
            return 1614;
        }
        return 1615;
    }

    private static int getLogType(TextClassifierEvent textClassifierEvent) {
        int n = textClassifierEvent.getEventType();
        if (n != 6) {
            if (n != 13) {
                if (n != 19) {
                    if (n != 20) {
                        return 0;
                    }
                    return 1619;
                }
                return 1618;
            }
            return 1113;
        }
        return 1616;
    }

    private static String getModelName(TextClassifierEvent textClassifierEvent) {
        if (textClassifierEvent.getModelName() != null) {
            return textClassifierEvent.getModelName();
        }
        return SelectionSessionLogger.SignatureParser.getModelName(textClassifierEvent.getResultId());
    }

    private String toCategoryName(int n) {
        if (n != 1614) {
            if (n != 1615) {
                return "unknown";
            }
            return "conversation_actions";
        }
        return "language_detection";
    }

    private String toEventName(int n) {
        if (n != 1113) {
            if (n != 1616) {
                if (n != 1618) {
                    if (n != 1619) {
                        return "unknown";
                    }
                    return "actions_generated";
                }
                return "manual_reply";
            }
            return "actions_shown";
        }
        return "smart_share";
    }

    public void writeEvent(TextClassifierEvent parcelable) {
        String[] arrstring;
        Preconditions.checkNotNull(parcelable);
        int n = TextClassifierEventTronLogger.getCategory((TextClassifierEvent)parcelable);
        if (n == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown category: ");
            stringBuilder.append(((TextClassifierEvent)parcelable).getEventCategory());
            Log.w(TAG, stringBuilder.toString());
            return;
        }
        LogMaker logMaker = new LogMaker(n).setSubtype(TextClassifierEventTronLogger.getLogType((TextClassifierEvent)parcelable)).addTaggedData(1634, ((TextClassifierEvent)parcelable).getResultId()).addTaggedData(1256, TextClassifierEventTronLogger.getModelName((TextClassifierEvent)parcelable));
        if (((TextClassifierEvent)parcelable).getScores().length >= 1) {
            logMaker.addTaggedData(1638, Float.valueOf(((TextClassifierEvent)parcelable).getScores()[0]));
        }
        if ((arrstring = ((TextClassifierEvent)parcelable).getEntityTypes()).length >= 1) {
            logMaker.addTaggedData(1635, arrstring[0]);
        }
        if (arrstring.length >= 2) {
            logMaker.addTaggedData(1636, arrstring[1]);
        }
        if (arrstring.length >= 3) {
            logMaker.addTaggedData(1637, arrstring[2]);
        }
        if ((parcelable = ((TextClassifierEvent)parcelable).getEventContext()) != null) {
            logMaker.addTaggedData(1639, ((TextClassificationContext)parcelable).getWidgetType());
            logMaker.addTaggedData(1640, ((TextClassificationContext)parcelable).getWidgetVersion());
            logMaker.setPackageName(((TextClassificationContext)parcelable).getPackageName());
        }
        this.mMetricsLogger.write(logMaker);
        this.debugLog(logMaker);
    }
}

