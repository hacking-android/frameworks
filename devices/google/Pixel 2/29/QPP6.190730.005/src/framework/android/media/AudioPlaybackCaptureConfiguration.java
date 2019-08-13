/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.-$
 *  android.media.-$$Lambda
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw-cxkUNQt_briVbM
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw
 *  android.media.-$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js
 */
package android.media;

import android.media.-$;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media._$$Lambda$AudioPlaybackCaptureConfiguration$CbJtSEmgD3swIYuOWlTCDMPxK1s;
import android.media._$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc;
import android.media._$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw_cxkUNQt_briVbM;
import android.media._$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw;
import android.media._$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.projection.IMediaProjection;
import android.media.projection.MediaProjection;
import android.os.RemoteException;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class AudioPlaybackCaptureConfiguration {
    private final AudioMixingRule mAudioMixingRule;
    private final MediaProjection mProjection;

    private AudioPlaybackCaptureConfiguration(AudioMixingRule audioMixingRule, MediaProjection mediaProjection) {
        this.mAudioMixingRule = audioMixingRule;
        this.mProjection = mediaProjection;
    }

    private int[] getIntPredicates(int n, ToIntFunction<AudioMixingRule.AudioMixMatchCriterion> toIntFunction) {
        return this.mAudioMixingRule.getCriteria().stream().filter(new _$$Lambda$AudioPlaybackCaptureConfiguration$CbJtSEmgD3swIYuOWlTCDMPxK1s(n)).mapToInt(toIntFunction).toArray();
    }

    static /* synthetic */ int lambda$getExcludeUids$3(AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
        return audioMixMatchCriterion.getIntProp();
    }

    static /* synthetic */ int lambda$getExcludeUsages$2(AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
        return audioMixMatchCriterion.getAudioAttributes().getUsage();
    }

    static /* synthetic */ boolean lambda$getIntPredicates$4(int n, AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
        boolean bl = audioMixMatchCriterion.getRule() == n;
        return bl;
    }

    static /* synthetic */ int lambda$getMatchingUids$1(AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
        return audioMixMatchCriterion.getIntProp();
    }

    static /* synthetic */ int lambda$getMatchingUsages$0(AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
        return audioMixMatchCriterion.getAudioAttributes().getUsage();
    }

    AudioMix createAudioMix(AudioFormat audioFormat) {
        return new AudioMix.Builder(this.mAudioMixingRule).setFormat(audioFormat).setRouteFlags(3).build();
    }

    public int[] getExcludeUids() {
        return this.getIntPredicates(32772, (ToIntFunction<AudioMixingRule.AudioMixMatchCriterion>)_$$Lambda$AudioPlaybackCaptureConfiguration$OOmSH4uNi7bw_cxkUNQt_briVbM.INSTANCE);
    }

    public int[] getExcludeUsages() {
        return this.getIntPredicates(32769, (ToIntFunction<AudioMixingRule.AudioMixMatchCriterion>)_$$Lambda$AudioPlaybackCaptureConfiguration$XNNDt4F3y6GuPLTW14ozD51SjGw.INSTANCE);
    }

    public int[] getMatchingUids() {
        return this.getIntPredicates(4, (ToIntFunction<AudioMixingRule.AudioMixMatchCriterion>)_$$Lambda$AudioPlaybackCaptureConfiguration$lExv8IaPEEDrexk0ZpgJOYug6js.INSTANCE);
    }

    public int[] getMatchingUsages() {
        return this.getIntPredicates(1, (ToIntFunction<AudioMixingRule.AudioMixMatchCriterion>)_$$Lambda$AudioPlaybackCaptureConfiguration$JTl6zvocylA2c1D_zvOeuHFBuXc.INSTANCE);
    }

    public MediaProjection getMediaProjection() {
        return this.mProjection;
    }

    public static final class Builder {
        private static final String ERROR_MESSAGE_MISMATCHED_RULES = "Inclusive and exclusive usage rules cannot be combined";
        private static final String ERROR_MESSAGE_NON_AUDIO_PROJECTION = "MediaProjection can not project audio";
        private static final String ERROR_MESSAGE_START_ACTIVITY_FAILED = "startActivityForResult failed";
        private static final int MATCH_TYPE_EXCLUSIVE = 2;
        private static final int MATCH_TYPE_INCLUSIVE = 1;
        private static final int MATCH_TYPE_UNSPECIFIED = 0;
        private final AudioMixingRule.Builder mAudioMixingRuleBuilder;
        private final MediaProjection mProjection;
        private int mUidMatchType = 0;
        private int mUsageMatchType = 0;

        public Builder(MediaProjection mediaProjection) {
            Preconditions.checkNotNull(mediaProjection);
            try {
                Preconditions.checkArgument(mediaProjection.getProjection().canProjectAudio(), ERROR_MESSAGE_NON_AUDIO_PROJECTION);
                this.mProjection = mediaProjection;
                this.mAudioMixingRuleBuilder = new AudioMixingRule.Builder();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }

        public Builder addMatchingUid(int n) {
            boolean bl = this.mUidMatchType != 2;
            Preconditions.checkState(bl, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.addMixRule(4, n);
            this.mUidMatchType = 1;
            return this;
        }

        public Builder addMatchingUsage(int n) {
            boolean bl = this.mUsageMatchType != 2;
            Preconditions.checkState(bl, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.addRule(new AudioAttributes.Builder().setUsage(n).build(), 1);
            this.mUsageMatchType = 1;
            return this;
        }

        public AudioPlaybackCaptureConfiguration build() {
            return new AudioPlaybackCaptureConfiguration(this.mAudioMixingRuleBuilder.build(), this.mProjection);
        }

        public Builder excludeUid(int n) {
            int n2 = this.mUidMatchType;
            boolean bl = true;
            if (n2 == 1) {
                bl = false;
            }
            Preconditions.checkState(bl, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.excludeMixRule(4, n);
            this.mUidMatchType = 2;
            return this;
        }

        public Builder excludeUsage(int n) {
            boolean bl = this.mUsageMatchType != 1;
            Preconditions.checkState(bl, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.excludeRule(new AudioAttributes.Builder().setUsage(n).build(), 1);
            this.mUsageMatchType = 2;
            return this;
        }
    }

}

