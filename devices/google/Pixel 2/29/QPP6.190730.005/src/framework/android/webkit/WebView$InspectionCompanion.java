/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.webkit.WebView;
import android.webkit._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class WebView$InspectionCompanion
implements InspectionCompanion<WebView> {
    private int mContentHeightId;
    private int mFaviconId;
    private int mOriginalUrlId;
    private int mProgressId;
    private boolean mPropertiesMapped = false;
    private int mRendererPriorityWaivedWhenNotVisibleId;
    private int mRendererRequestedPriorityId;
    private int mTitleId;
    private int mUrlId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mContentHeightId = propertyMapper.mapInt("contentHeight", 0);
        this.mFaviconId = propertyMapper.mapObject("favicon", 0);
        this.mOriginalUrlId = propertyMapper.mapObject("originalUrl", 0);
        this.mProgressId = propertyMapper.mapInt("progress", 0);
        this.mRendererPriorityWaivedWhenNotVisibleId = propertyMapper.mapBoolean("rendererPriorityWaivedWhenNotVisible", 0);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(0, "waived");
        sparseArray.put(1, "bound");
        sparseArray.put(2, "important");
        Objects.requireNonNull(sparseArray);
        this.mRendererRequestedPriorityId = propertyMapper.mapIntEnum("rendererRequestedPriority", 0, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mTitleId = propertyMapper.mapObject("title", 0);
        this.mUrlId = propertyMapper.mapObject("url", 0);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(WebView webView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readInt(this.mContentHeightId, webView.getContentHeight());
            propertyReader.readObject(this.mFaviconId, webView.getFavicon());
            propertyReader.readObject(this.mOriginalUrlId, webView.getOriginalUrl());
            propertyReader.readInt(this.mProgressId, webView.getProgress());
            propertyReader.readBoolean(this.mRendererPriorityWaivedWhenNotVisibleId, webView.getRendererPriorityWaivedWhenNotVisible());
            propertyReader.readIntEnum(this.mRendererRequestedPriorityId, webView.getRendererRequestedPriority());
            propertyReader.readObject(this.mTitleId, webView.getTitle());
            propertyReader.readObject(this.mUrlId, webView.getUrl());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

