/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.speech.tts.AbstractEventLogger;
import android.speech.tts.EventLogTags;
import android.speech.tts.SynthesisRequest;
import android.text.TextUtils;

class EventLogger
extends AbstractEventLogger {
    private final SynthesisRequest mRequest;

    EventLogger(SynthesisRequest synthesisRequest, int n, int n2, String string2) {
        super(n, n2, string2);
        this.mRequest = synthesisRequest;
    }

    private String getLocaleString() {
        StringBuilder stringBuilder = new StringBuilder(this.mRequest.getLanguage());
        if (!TextUtils.isEmpty(this.mRequest.getCountry())) {
            stringBuilder.append('-');
            stringBuilder.append(this.mRequest.getCountry());
            if (!TextUtils.isEmpty(this.mRequest.getVariant())) {
                stringBuilder.append('-');
                stringBuilder.append(this.mRequest.getVariant());
            }
        }
        return stringBuilder.toString();
    }

    private int getUtteranceLength() {
        String string2 = this.mRequest.getText();
        int n = string2 == null ? 0 : string2.length();
        return n;
    }

    @Override
    protected void logFailure(int n) {
        if (n != -2) {
            EventLogTags.writeTtsSpeakFailure(this.mServiceApp, this.mCallerUid, this.mCallerPid, this.getUtteranceLength(), this.getLocaleString(), this.mRequest.getSpeechRate(), this.mRequest.getPitch());
        }
    }

    @Override
    protected void logSuccess(long l, long l2, long l3) {
        EventLogTags.writeTtsSpeakSuccess(this.mServiceApp, this.mCallerUid, this.mCallerPid, this.getUtteranceLength(), this.getLocaleString(), this.mRequest.getSpeechRate(), this.mRequest.getPitch(), l2, l3, l);
    }
}

