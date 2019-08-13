/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import java.util.Objects;

public class DisplayAdjustments {
    public static final DisplayAdjustments DEFAULT_DISPLAY_ADJUSTMENTS = new DisplayAdjustments();
    private volatile CompatibilityInfo mCompatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
    private Configuration mConfiguration;

    @UnsupportedAppUsage
    public DisplayAdjustments() {
    }

    public DisplayAdjustments(Configuration configuration) {
        if (configuration == null) {
            configuration = Configuration.EMPTY;
        }
        this.mConfiguration = new Configuration(configuration);
    }

    public DisplayAdjustments(DisplayAdjustments object) {
        this.setCompatibilityInfo(((DisplayAdjustments)object).mCompatInfo);
        object = ((DisplayAdjustments)object).mConfiguration;
        if (object == null) {
            object = Configuration.EMPTY;
        }
        this.mConfiguration = new Configuration((Configuration)object);
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof DisplayAdjustments;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (DisplayAdjustments)object;
            if (!Objects.equals(((DisplayAdjustments)object).mCompatInfo, this.mCompatInfo) || !Objects.equals(((DisplayAdjustments)object).mConfiguration, this.mConfiguration)) break block1;
            bl = true;
        }
        return bl;
    }

    public CompatibilityInfo getCompatibilityInfo() {
        return this.mCompatInfo;
    }

    @UnsupportedAppUsage
    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int hashCode() {
        return (17 * 31 + Objects.hashCode(this.mCompatInfo)) * 31 + Objects.hashCode(this.mConfiguration);
    }

    @UnsupportedAppUsage
    public void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        if (this != DEFAULT_DISPLAY_ADJUSTMENTS) {
            this.mCompatInfo = compatibilityInfo != null && (compatibilityInfo.isScalingRequired() || !compatibilityInfo.supportsScreen()) ? compatibilityInfo : CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
            return;
        }
        throw new IllegalArgumentException("setCompatbilityInfo: Cannot modify DEFAULT_DISPLAY_ADJUSTMENTS");
    }

    public void setConfiguration(Configuration configuration) {
        if (this != DEFAULT_DISPLAY_ADJUSTMENTS) {
            Configuration configuration2 = this.mConfiguration;
            if (configuration == null) {
                configuration = Configuration.EMPTY;
            }
            configuration2.setTo(configuration);
            return;
        }
        throw new IllegalArgumentException("setConfiguration: Cannot modify DEFAULT_DISPLAY_ADJUSTMENTS");
    }
}

