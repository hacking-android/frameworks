/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.Toolbar;

public final class Toolbar$InspectionCompanion
implements InspectionCompanion<Toolbar> {
    private int mCollapseContentDescriptionId;
    private int mCollapseIconId;
    private int mContentInsetEndId;
    private int mContentInsetEndWithActionsId;
    private int mContentInsetLeftId;
    private int mContentInsetRightId;
    private int mContentInsetStartId;
    private int mContentInsetStartWithNavigationId;
    private int mLogoDescriptionId;
    private int mLogoId;
    private int mNavigationContentDescriptionId;
    private int mNavigationIconId;
    private int mPopupThemeId;
    private boolean mPropertiesMapped = false;
    private int mSubtitleId;
    private int mTitleId;
    private int mTitleMarginBottomId;
    private int mTitleMarginEndId;
    private int mTitleMarginStartId;
    private int mTitleMarginTopId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mCollapseContentDescriptionId = propertyMapper.mapObject("collapseContentDescription", 16843984);
        this.mCollapseIconId = propertyMapper.mapObject("collapseIcon", 16844031);
        this.mContentInsetEndId = propertyMapper.mapInt("contentInsetEnd", 16843860);
        this.mContentInsetEndWithActionsId = propertyMapper.mapInt("contentInsetEndWithActions", 16844067);
        this.mContentInsetLeftId = propertyMapper.mapInt("contentInsetLeft", 16843861);
        this.mContentInsetRightId = propertyMapper.mapInt("contentInsetRight", 16843862);
        this.mContentInsetStartId = propertyMapper.mapInt("contentInsetStart", 16843859);
        this.mContentInsetStartWithNavigationId = propertyMapper.mapInt("contentInsetStartWithNavigation", 16844066);
        this.mLogoId = propertyMapper.mapObject("logo", 16843454);
        this.mLogoDescriptionId = propertyMapper.mapObject("logoDescription", 16844009);
        this.mNavigationContentDescriptionId = propertyMapper.mapObject("navigationContentDescription", 16843969);
        this.mNavigationIconId = propertyMapper.mapObject("navigationIcon", 16843968);
        this.mPopupThemeId = propertyMapper.mapInt("popupTheme", 16843945);
        this.mSubtitleId = propertyMapper.mapObject("subtitle", 16843473);
        this.mTitleId = propertyMapper.mapObject("title", 16843233);
        this.mTitleMarginBottomId = propertyMapper.mapInt("titleMarginBottom", 16844028);
        this.mTitleMarginEndId = propertyMapper.mapInt("titleMarginEnd", 16844026);
        this.mTitleMarginStartId = propertyMapper.mapInt("titleMarginStart", 16844025);
        this.mTitleMarginTopId = propertyMapper.mapInt("titleMarginTop", 16844027);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(Toolbar toolbar, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mCollapseContentDescriptionId, toolbar.getCollapseContentDescription());
            propertyReader.readObject(this.mCollapseIconId, toolbar.getCollapseIcon());
            propertyReader.readInt(this.mContentInsetEndId, toolbar.getContentInsetEnd());
            propertyReader.readInt(this.mContentInsetEndWithActionsId, toolbar.getContentInsetEndWithActions());
            propertyReader.readInt(this.mContentInsetLeftId, toolbar.getContentInsetLeft());
            propertyReader.readInt(this.mContentInsetRightId, toolbar.getContentInsetRight());
            propertyReader.readInt(this.mContentInsetStartId, toolbar.getContentInsetStart());
            propertyReader.readInt(this.mContentInsetStartWithNavigationId, toolbar.getContentInsetStartWithNavigation());
            propertyReader.readObject(this.mLogoId, toolbar.getLogo());
            propertyReader.readObject(this.mLogoDescriptionId, toolbar.getLogoDescription());
            propertyReader.readObject(this.mNavigationContentDescriptionId, toolbar.getNavigationContentDescription());
            propertyReader.readObject(this.mNavigationIconId, toolbar.getNavigationIcon());
            propertyReader.readInt(this.mPopupThemeId, toolbar.getPopupTheme());
            propertyReader.readObject(this.mSubtitleId, toolbar.getSubtitle());
            propertyReader.readObject(this.mTitleId, toolbar.getTitle());
            propertyReader.readInt(this.mTitleMarginBottomId, toolbar.getTitleMarginBottom());
            propertyReader.readInt(this.mTitleMarginEndId, toolbar.getTitleMarginEnd());
            propertyReader.readInt(this.mTitleMarginStartId, toolbar.getTitleMarginStart());
            propertyReader.readInt(this.mTitleMarginTopId, toolbar.getTitleMarginTop());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

