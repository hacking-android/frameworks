/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TracingConfig {
    public static final int CATEGORIES_ALL = 1;
    public static final int CATEGORIES_ANDROID_WEBVIEW = 2;
    public static final int CATEGORIES_FRAME_VIEWER = 64;
    public static final int CATEGORIES_INPUT_LATENCY = 8;
    public static final int CATEGORIES_JAVASCRIPT_AND_RENDERING = 32;
    public static final int CATEGORIES_NONE = 0;
    public static final int CATEGORIES_RENDERING = 16;
    public static final int CATEGORIES_WEB_DEVELOPER = 4;
    public static final int RECORD_CONTINUOUSLY = 1;
    public static final int RECORD_UNTIL_FULL = 0;
    private final List<String> mCustomIncludedCategories = new ArrayList<String>();
    private int mPredefinedCategories;
    private int mTracingMode;

    public TracingConfig(int n, List<String> list, int n2) {
        this.mPredefinedCategories = n;
        this.mCustomIncludedCategories.addAll(list);
        this.mTracingMode = n2;
    }

    public List<String> getCustomIncludedCategories() {
        return this.mCustomIncludedCategories;
    }

    public int getPredefinedCategories() {
        return this.mPredefinedCategories;
    }

    public int getTracingMode() {
        return this.mTracingMode;
    }

    public static class Builder {
        private final List<String> mCustomIncludedCategories = new ArrayList<String>();
        private int mPredefinedCategories = 0;
        private int mTracingMode = 1;

        public Builder addCategories(Collection<String> collection) {
            this.mCustomIncludedCategories.addAll(collection);
            return this;
        }

        public Builder addCategories(int ... arrn) {
            for (int n : arrn) {
                this.mPredefinedCategories |= n;
            }
            return this;
        }

        public Builder addCategories(String ... arrstring) {
            for (String string2 : arrstring) {
                this.mCustomIncludedCategories.add(string2);
            }
            return this;
        }

        public TracingConfig build() {
            return new TracingConfig(this.mPredefinedCategories, this.mCustomIncludedCategories, this.mTracingMode);
        }

        public Builder setTracingMode(int n) {
            this.mTracingMode = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PredefinedCategories {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TracingMode {
    }

}

