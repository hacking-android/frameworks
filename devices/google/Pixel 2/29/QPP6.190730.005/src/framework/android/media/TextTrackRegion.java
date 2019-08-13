/*
 * Decompiled with CFR 0.145.
 */
package android.media;

class TextTrackRegion {
    static final int SCROLL_VALUE_NONE = 300;
    static final int SCROLL_VALUE_SCROLL_UP = 301;
    float mAnchorPointX = 0.0f;
    float mAnchorPointY = 100.0f;
    String mId = "";
    int mLines = 3;
    int mScrollValue = 300;
    float mViewportAnchorPointX = 0.0f;
    float mViewportAnchorPointY = 100.0f;
    float mWidth = 100.0f;

    TextTrackRegion() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(" {id:\"");
        stringBuilder.append(this.mId);
        stringBuilder.append("\", width:");
        stringBuilder.append(this.mWidth);
        stringBuilder.append(", lines:");
        stringBuilder.append(this.mLines);
        stringBuilder.append(", anchorPoint:(");
        stringBuilder.append(this.mAnchorPointX);
        stringBuilder.append(", ");
        stringBuilder.append(this.mAnchorPointY);
        stringBuilder.append("), viewportAnchorPoints:");
        stringBuilder.append(this.mViewportAnchorPointX);
        stringBuilder.append(", ");
        stringBuilder.append(this.mViewportAnchorPointY);
        stringBuilder.append("), scrollValue:");
        int n = this.mScrollValue;
        String string2 = n == 300 ? "none" : (n == 301 ? "scroll_up" : "INVALID");
        stringBuilder.append(string2);
        return stringBuilder.append("}").toString();
    }
}

