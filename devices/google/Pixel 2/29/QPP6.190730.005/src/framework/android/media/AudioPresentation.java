/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.ULocale
 */
package android.media;

import android.icu.util.ULocale;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class AudioPresentation {
    public static final int MASTERED_FOR_3D = 3;
    public static final int MASTERED_FOR_HEADPHONE = 4;
    public static final int MASTERED_FOR_STEREO = 1;
    public static final int MASTERED_FOR_SURROUND = 2;
    public static final int MASTERING_NOT_INDICATED = 0;
    private static final int UNKNOWN_ID = -1;
    private final boolean mAudioDescriptionAvailable;
    private final boolean mDialogueEnhancementAvailable;
    private final Map<ULocale, CharSequence> mLabels;
    private final ULocale mLanguage;
    private final int mMasteringIndication;
    private final int mPresentationId;
    private final int mProgramId;
    private final boolean mSpokenSubtitlesAvailable;

    private AudioPresentation(int n, int n2, ULocale uLocale, int n3, boolean bl, boolean bl2, boolean bl3, Map<ULocale, CharSequence> map) {
        this.mPresentationId = n;
        this.mProgramId = n2;
        this.mLanguage = uLocale;
        this.mMasteringIndication = n3;
        this.mAudioDescriptionAvailable = bl;
        this.mSpokenSubtitlesAvailable = bl2;
        this.mDialogueEnhancementAvailable = bl3;
        this.mLabels = new HashMap<ULocale, CharSequence>(map);
    }

    private Map<ULocale, CharSequence> getULabels() {
        return this.mLabels;
    }

    private ULocale getULocale() {
        return this.mLanguage;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof AudioPresentation)) {
            return false;
        }
        if (this.mPresentationId != ((AudioPresentation)(object = (AudioPresentation)object)).getPresentationId() || this.mProgramId != ((AudioPresentation)object).getProgramId() || !this.mLanguage.equals((Object)AudioPresentation.super.getULocale()) || this.mMasteringIndication != ((AudioPresentation)object).getMasteringIndication() || this.mAudioDescriptionAvailable != ((AudioPresentation)object).hasAudioDescription() || this.mSpokenSubtitlesAvailable != ((AudioPresentation)object).hasSpokenSubtitles() || this.mDialogueEnhancementAvailable != ((AudioPresentation)object).hasDialogueEnhancement() || !this.mLabels.equals(AudioPresentation.super.getULabels())) {
            bl = false;
        }
        return bl;
    }

    public Map<Locale, String> getLabels() {
        HashMap<Locale, String> hashMap = new HashMap<Locale, String>(this.mLabels.size());
        for (Map.Entry<ULocale, CharSequence> entry : this.mLabels.entrySet()) {
            hashMap.put(entry.getKey().toLocale(), entry.getValue().toString());
        }
        return hashMap;
    }

    public Locale getLocale() {
        return this.mLanguage.toLocale();
    }

    public int getMasteringIndication() {
        return this.mMasteringIndication;
    }

    public int getPresentationId() {
        return this.mPresentationId;
    }

    public int getProgramId() {
        return this.mProgramId;
    }

    public boolean hasAudioDescription() {
        return this.mAudioDescriptionAvailable;
    }

    public boolean hasDialogueEnhancement() {
        return this.mDialogueEnhancementAvailable;
    }

    public boolean hasSpokenSubtitles() {
        return this.mSpokenSubtitlesAvailable;
    }

    public int hashCode() {
        return Objects.hash(this.mPresentationId, this.mProgramId, this.mLanguage.hashCode(), this.mMasteringIndication, this.mAudioDescriptionAvailable, this.mSpokenSubtitlesAvailable, this.mDialogueEnhancementAvailable, this.mLabels.hashCode());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.getClass().getSimpleName());
        stringBuilder2.append(" ");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("{ presentation id=");
        stringBuilder2.append(this.mPresentationId);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", program id=");
        stringBuilder2.append(this.mProgramId);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", language=");
        stringBuilder2.append((Object)this.mLanguage);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", labels=");
        stringBuilder2.append(this.mLabels);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", mastering indication=");
        stringBuilder2.append(this.mMasteringIndication);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", audio description=");
        stringBuilder2.append(this.mAudioDescriptionAvailable);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", spoken subtitles=");
        stringBuilder2.append(this.mSpokenSubtitlesAvailable);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(", dialogue enhancement=");
        stringBuilder2.append(this.mDialogueEnhancementAvailable);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    public static final class Builder {
        private boolean mAudioDescriptionAvailable = false;
        private boolean mDialogueEnhancementAvailable = false;
        private Map<ULocale, CharSequence> mLabels = new HashMap<ULocale, CharSequence>();
        private ULocale mLanguage = new ULocale("");
        private int mMasteringIndication = 0;
        private final int mPresentationId;
        private int mProgramId = -1;
        private boolean mSpokenSubtitlesAvailable = false;

        public Builder(int n) {
            this.mPresentationId = n;
        }

        public AudioPresentation build() {
            return new AudioPresentation(this.mPresentationId, this.mProgramId, this.mLanguage, this.mMasteringIndication, this.mAudioDescriptionAvailable, this.mSpokenSubtitlesAvailable, this.mDialogueEnhancementAvailable, this.mLabels);
        }

        public Builder setHasAudioDescription(boolean bl) {
            this.mAudioDescriptionAvailable = bl;
            return this;
        }

        public Builder setHasDialogueEnhancement(boolean bl) {
            this.mDialogueEnhancementAvailable = bl;
            return this;
        }

        public Builder setHasSpokenSubtitles(boolean bl) {
            this.mSpokenSubtitlesAvailable = bl;
            return this;
        }

        public Builder setLabels(Map<ULocale, CharSequence> map) {
            this.mLabels = new HashMap<ULocale, CharSequence>(map);
            return this;
        }

        public Builder setLocale(ULocale uLocale) {
            this.mLanguage = uLocale;
            return this;
        }

        public Builder setMasteringIndication(int n) {
            if (n != 0 && n != 1 && n != 2 && n != 3 && n != 4) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown mastering indication: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mMasteringIndication = n;
            return this;
        }

        public Builder setProgramId(int n) {
            this.mProgramId = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MasteringIndicationType {
    }

}

