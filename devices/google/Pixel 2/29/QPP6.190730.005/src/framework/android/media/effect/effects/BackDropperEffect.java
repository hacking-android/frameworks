/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect.effects;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.OneShotScheduler;
import android.filterpacks.videoproc.BackDropperFilter;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectUpdateListener;
import android.media.effect.FilterGraphEffect;

public class BackDropperEffect
extends FilterGraphEffect {
    private static final String mGraphDefinition = "@import android.filterpacks.base;\n@import android.filterpacks.videoproc;\n@import android.filterpacks.videosrc;\n\n@filter GLTextureSource foreground {\n  texId = 0;\n  width = 0;\n  height = 0;\n  repeatFrame = true;\n}\n\n@filter MediaSource background {\n  sourceUrl = \"no_file_specified\";\n  waitForNewFrame = false;\n  sourceIsUrl = true;\n}\n\n@filter BackDropperFilter replacer {\n  autowbToggle = 1;\n}\n\n@filter GLTextureTarget output {\n  texId = 0;\n}\n\n@connect foreground[frame]  => replacer[video];\n@connect background[video]  => replacer[background];\n@connect replacer[video]    => output[frame];\n";
    private EffectUpdateListener mEffectListener = null;
    private BackDropperFilter.LearningDoneListener mLearningListener = new BackDropperFilter.LearningDoneListener(){

        @Override
        public void onLearningDone(BackDropperFilter backDropperFilter) {
            if (BackDropperEffect.this.mEffectListener != null) {
                BackDropperEffect.this.mEffectListener.onEffectUpdated(BackDropperEffect.this, null);
            }
        }
    };

    public BackDropperEffect(EffectContext effectContext, String string2) {
        super(effectContext, string2, mGraphDefinition, "foreground", "output", OneShotScheduler.class);
        this.mGraph.getFilter("replacer").setInputValue("learningDoneListener", this.mLearningListener);
    }

    @Override
    public void setParameter(String string2, Object object) {
        block1 : {
            block0 : {
                if (!string2.equals("source")) break block0;
                this.mGraph.getFilter("background").setInputValue("sourceUrl", object);
                break block1;
            }
            if (!string2.equals("context")) break block1;
            this.mGraph.getFilter("background").setInputValue("context", object);
        }
    }

    @Override
    public void setUpdateListener(EffectUpdateListener effectUpdateListener) {
        this.mEffectListener = effectUpdateListener;
    }

}

