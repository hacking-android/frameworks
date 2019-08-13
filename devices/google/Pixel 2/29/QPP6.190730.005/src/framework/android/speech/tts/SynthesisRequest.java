/*
 * Decompiled with CFR 0.145.
 */
package android.speech.tts;

import android.os.Bundle;

public final class SynthesisRequest {
    private int mCallerUid;
    private String mCountry;
    private String mLanguage;
    private final Bundle mParams;
    private int mPitch;
    private int mSpeechRate;
    private final CharSequence mText;
    private String mVariant;
    private String mVoiceName;

    public SynthesisRequest(CharSequence charSequence, Bundle bundle) {
        this.mText = charSequence;
        this.mParams = new Bundle(bundle);
    }

    public SynthesisRequest(String string2, Bundle bundle) {
        this.mText = string2;
        this.mParams = new Bundle(bundle);
    }

    public int getCallerUid() {
        return this.mCallerUid;
    }

    public CharSequence getCharSequenceText() {
        return this.mText;
    }

    public String getCountry() {
        return this.mCountry;
    }

    public String getLanguage() {
        return this.mLanguage;
    }

    public Bundle getParams() {
        return this.mParams;
    }

    public int getPitch() {
        return this.mPitch;
    }

    public int getSpeechRate() {
        return this.mSpeechRate;
    }

    @Deprecated
    public String getText() {
        return this.mText.toString();
    }

    public String getVariant() {
        return this.mVariant;
    }

    public String getVoiceName() {
        return this.mVoiceName;
    }

    void setCallerUid(int n) {
        this.mCallerUid = n;
    }

    void setLanguage(String string2, String string3, String string4) {
        this.mLanguage = string2;
        this.mCountry = string3;
        this.mVariant = string4;
    }

    void setPitch(int n) {
        this.mPitch = n;
    }

    void setSpeechRate(int n) {
        this.mSpeechRate = n;
    }

    void setVoiceName(String string2) {
        this.mVoiceName = string2;
    }
}

