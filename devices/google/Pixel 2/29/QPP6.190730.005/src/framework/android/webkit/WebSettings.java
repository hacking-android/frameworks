/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.webkit.WebViewFactory;
import android.webkit.WebViewFactoryProvider;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class WebSettings {
    public static final int FORCE_DARK_AUTO = 1;
    public static final int FORCE_DARK_OFF = 0;
    public static final int FORCE_DARK_ON = 2;
    public static final int LOAD_CACHE_ELSE_NETWORK = 1;
    public static final int LOAD_CACHE_ONLY = 3;
    public static final int LOAD_DEFAULT = -1;
    @Deprecated
    public static final int LOAD_NORMAL = 0;
    public static final int LOAD_NO_CACHE = 2;
    public static final int MENU_ITEM_NONE = 0;
    public static final int MENU_ITEM_PROCESS_TEXT = 4;
    public static final int MENU_ITEM_SHARE = 1;
    public static final int MENU_ITEM_WEB_SEARCH = 2;
    public static final int MIXED_CONTENT_ALWAYS_ALLOW = 0;
    public static final int MIXED_CONTENT_COMPATIBILITY_MODE = 2;
    public static final int MIXED_CONTENT_NEVER_ALLOW = 1;

    public static String getDefaultUserAgent(Context context) {
        return WebViewFactory.getProvider().getStatics().getDefaultUserAgent(context);
    }

    @Deprecated
    public abstract boolean enableSmoothTransition();

    @SystemApi
    public abstract boolean getAcceptThirdPartyCookies();

    public abstract boolean getAllowContentAccess();

    public abstract boolean getAllowFileAccess();

    public abstract boolean getAllowFileAccessFromFileURLs();

    public abstract boolean getAllowUniversalAccessFromFileURLs();

    public abstract boolean getBlockNetworkImage();

    public abstract boolean getBlockNetworkLoads();

    public abstract boolean getBuiltInZoomControls();

    public abstract int getCacheMode();

    public abstract String getCursiveFontFamily();

    public abstract boolean getDatabaseEnabled();

    @Deprecated
    public abstract String getDatabasePath();

    public abstract int getDefaultFixedFontSize();

    public abstract int getDefaultFontSize();

    public abstract String getDefaultTextEncodingName();

    @Deprecated
    public abstract ZoomDensity getDefaultZoom();

    public abstract int getDisabledActionModeMenuItems();

    public abstract boolean getDisplayZoomControls();

    public abstract boolean getDomStorageEnabled();

    public abstract String getFantasyFontFamily();

    public abstract String getFixedFontFamily();

    public int getForceDark() {
        return 1;
    }

    public abstract boolean getJavaScriptCanOpenWindowsAutomatically();

    public abstract boolean getJavaScriptEnabled();

    public abstract LayoutAlgorithm getLayoutAlgorithm();

    @Deprecated
    public abstract boolean getLightTouchEnabled();

    public abstract boolean getLoadWithOverviewMode();

    public abstract boolean getLoadsImagesAutomatically();

    public abstract boolean getMediaPlaybackRequiresUserGesture();

    public abstract int getMinimumFontSize();

    public abstract int getMinimumLogicalFontSize();

    public abstract int getMixedContentMode();

    @SystemApi
    @Deprecated
    public abstract boolean getNavDump();

    public abstract boolean getOffscreenPreRaster();

    @Deprecated
    public abstract PluginState getPluginState();

    @SystemApi
    @Deprecated
    public abstract boolean getPluginsEnabled();

    @Deprecated
    @UnsupportedAppUsage
    public String getPluginsPath() {
        return "";
    }

    public abstract boolean getSafeBrowsingEnabled();

    public abstract String getSansSerifFontFamily();

    @Deprecated
    public abstract boolean getSaveFormData();

    @Deprecated
    public abstract boolean getSavePassword();

    public abstract String getSerifFontFamily();

    public abstract String getStandardFontFamily();

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public TextSize getTextSize() {
        synchronized (this) {
            TextSize textSize = null;
            int n = Integer.MAX_VALUE;
            int n2 = this.getTextZoom();
            for (TextSize textSize2 : TextSize.values()) {
                int n3 = Math.abs(n2 - textSize2.value);
                if (n3 == 0) {
                    return textSize2;
                }
                int n4 = n;
                if (n3 < n) {
                    n4 = n3;
                    textSize = textSize2;
                }
                n = n4;
            }
            if (textSize == null) return TextSize.NORMAL;
            return textSize;
        }
    }

    public abstract int getTextZoom();

    @Deprecated
    @UnsupportedAppUsage
    public boolean getUseDoubleTree() {
        return false;
    }

    @SystemApi
    @Deprecated
    public abstract boolean getUseWebViewBackgroundForOverscrollBackground();

    public abstract boolean getUseWideViewPort();

    @SystemApi
    @Deprecated
    public abstract int getUserAgent();

    public abstract String getUserAgentString();

    @SystemApi
    public abstract boolean getVideoOverlayForEmbeddedEncryptedVideoEnabled();

    @SystemApi
    public abstract void setAcceptThirdPartyCookies(boolean var1);

    public abstract void setAllowContentAccess(boolean var1);

    public abstract void setAllowFileAccess(boolean var1);

    public abstract void setAllowFileAccessFromFileURLs(boolean var1);

    public abstract void setAllowUniversalAccessFromFileURLs(boolean var1);

    public abstract void setAppCacheEnabled(boolean var1);

    @Deprecated
    public abstract void setAppCacheMaxSize(long var1);

    public abstract void setAppCachePath(String var1);

    public abstract void setBlockNetworkImage(boolean var1);

    public abstract void setBlockNetworkLoads(boolean var1);

    public abstract void setBuiltInZoomControls(boolean var1);

    public abstract void setCacheMode(int var1);

    public abstract void setCursiveFontFamily(String var1);

    public abstract void setDatabaseEnabled(boolean var1);

    @Deprecated
    public abstract void setDatabasePath(String var1);

    public abstract void setDefaultFixedFontSize(int var1);

    public abstract void setDefaultFontSize(int var1);

    public abstract void setDefaultTextEncodingName(String var1);

    @Deprecated
    public abstract void setDefaultZoom(ZoomDensity var1);

    public abstract void setDisabledActionModeMenuItems(int var1);

    public abstract void setDisplayZoomControls(boolean var1);

    public abstract void setDomStorageEnabled(boolean var1);

    @Deprecated
    public abstract void setEnableSmoothTransition(boolean var1);

    public abstract void setFantasyFontFamily(String var1);

    public abstract void setFixedFontFamily(String var1);

    public void setForceDark(int n) {
    }

    @Deprecated
    public abstract void setGeolocationDatabasePath(String var1);

    public abstract void setGeolocationEnabled(boolean var1);

    public abstract void setJavaScriptCanOpenWindowsAutomatically(boolean var1);

    public abstract void setJavaScriptEnabled(boolean var1);

    public abstract void setLayoutAlgorithm(LayoutAlgorithm var1);

    @Deprecated
    public abstract void setLightTouchEnabled(boolean var1);

    public abstract void setLoadWithOverviewMode(boolean var1);

    public abstract void setLoadsImagesAutomatically(boolean var1);

    public abstract void setMediaPlaybackRequiresUserGesture(boolean var1);

    public abstract void setMinimumFontSize(int var1);

    public abstract void setMinimumLogicalFontSize(int var1);

    public abstract void setMixedContentMode(int var1);

    @SystemApi
    @Deprecated
    public abstract void setNavDump(boolean var1);

    public abstract void setNeedInitialFocus(boolean var1);

    public abstract void setOffscreenPreRaster(boolean var1);

    @Deprecated
    public abstract void setPluginState(PluginState var1);

    @SystemApi
    @Deprecated
    public abstract void setPluginsEnabled(boolean var1);

    @Deprecated
    @UnsupportedAppUsage
    public void setPluginsPath(String string2) {
    }

    @Deprecated
    public abstract void setRenderPriority(RenderPriority var1);

    public abstract void setSafeBrowsingEnabled(boolean var1);

    public abstract void setSansSerifFontFamily(String var1);

    @Deprecated
    public abstract void setSaveFormData(boolean var1);

    @Deprecated
    public abstract void setSavePassword(boolean var1);

    public abstract void setSerifFontFamily(String var1);

    public abstract void setStandardFontFamily(String var1);

    public abstract void setSupportMultipleWindows(boolean var1);

    public abstract void setSupportZoom(boolean var1);

    @Deprecated
    public void setTextSize(TextSize textSize) {
        synchronized (this) {
            this.setTextZoom(textSize.value);
            return;
        }
    }

    public abstract void setTextZoom(int var1);

    @Deprecated
    @UnsupportedAppUsage
    public void setUseDoubleTree(boolean bl) {
    }

    @SystemApi
    @Deprecated
    public abstract void setUseWebViewBackgroundForOverscrollBackground(boolean var1);

    public abstract void setUseWideViewPort(boolean var1);

    @SystemApi
    @Deprecated
    public abstract void setUserAgent(int var1);

    public abstract void setUserAgentString(String var1);

    @SystemApi
    public abstract void setVideoOverlayForEmbeddedEncryptedVideoEnabled(boolean var1);

    public abstract boolean supportMultipleWindows();

    public abstract boolean supportZoom();

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CacheMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ForceDark {
    }

    public static enum LayoutAlgorithm {
        NORMAL,
        SINGLE_COLUMN,
        NARROW_COLUMNS,
        TEXT_AUTOSIZING;
        
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @Target(value={ElementType.PARAMETER, ElementType.METHOD})
    private static @interface MenuItemFlags {
    }

    public static enum PluginState {
        ON,
        ON_DEMAND,
        OFF;
        
    }

    public static enum RenderPriority {
        NORMAL,
        HIGH,
        LOW;
        
    }

    @Deprecated
    public static enum TextSize {
        SMALLEST(50),
        SMALLER(75),
        NORMAL(100),
        LARGER(150),
        LARGEST(200);
        
        @UnsupportedAppUsage
        int value;

        private TextSize(int n2) {
            this.value = n2;
        }
    }

    public static enum ZoomDensity {
        FAR(150),
        MEDIUM(100),
        CLOSE(75);
        
        int value;

        private ZoomDensity(int n2) {
            this.value = n2;
        }

        public int getValue() {
            return this.value;
        }
    }

}

