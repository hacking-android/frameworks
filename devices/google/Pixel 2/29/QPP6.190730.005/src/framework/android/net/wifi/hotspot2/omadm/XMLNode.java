/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.omadm;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XMLNode {
    private final List<XMLNode> mChildren;
    private final XMLNode mParent;
    private final String mTag;
    private String mText;
    private StringBuilder mTextBuilder;

    public XMLNode(XMLNode xMLNode, String string2) {
        this.mTag = string2;
        this.mParent = xMLNode;
        this.mChildren = new ArrayList<XMLNode>();
        this.mTextBuilder = new StringBuilder();
        this.mText = null;
    }

    public void addChild(XMLNode xMLNode) {
        this.mChildren.add(xMLNode);
    }

    public void addText(String string2) {
        this.mTextBuilder.append(string2);
    }

    public void close() {
        this.mText = this.mTextBuilder.toString().trim();
        this.mTextBuilder = null;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof XMLNode)) {
            return false;
        }
        object = (XMLNode)object;
        if (!(TextUtils.equals(this.mTag, ((XMLNode)object).mTag) && TextUtils.equals(this.mText, ((XMLNode)object).mText) && this.mChildren.equals(((XMLNode)object).mChildren))) {
            bl = false;
        }
        return bl;
    }

    public List<XMLNode> getChildren() {
        return this.mChildren;
    }

    public XMLNode getParent() {
        return this.mParent;
    }

    public String getTag() {
        return this.mTag;
    }

    public String getText() {
        return this.mText;
    }

    public int hashCode() {
        return Objects.hash(this.mTag, this.mText, this.mChildren);
    }
}

