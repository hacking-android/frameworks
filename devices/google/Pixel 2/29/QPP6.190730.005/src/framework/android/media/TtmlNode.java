/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.util.ArrayList;
import java.util.List;

class TtmlNode {
    public final String mAttributes;
    public final List<TtmlNode> mChildren = new ArrayList<TtmlNode>();
    public final long mEndTimeMs;
    public final String mName;
    public final TtmlNode mParent;
    public final long mRunId;
    public final long mStartTimeMs;
    public final String mText;

    public TtmlNode(String string2, String string3, String string4, long l, long l2, TtmlNode ttmlNode, long l3) {
        this.mName = string2;
        this.mAttributes = string3;
        this.mText = string4;
        this.mStartTimeMs = l;
        this.mEndTimeMs = l2;
        this.mParent = ttmlNode;
        this.mRunId = l3;
    }

    public boolean isActive(long l, long l2) {
        boolean bl = this.mEndTimeMs > l && this.mStartTimeMs < l2;
        return bl;
    }
}

