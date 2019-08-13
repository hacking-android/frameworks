/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.CharacterDataImpl;
import org.apache.harmony.xml.dom.DocumentImpl;
import org.w3c.dom.Comment;

public final class CommentImpl
extends CharacterDataImpl
implements Comment {
    CommentImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl, string);
    }

    public boolean containsDashDash() {
        boolean bl = this.buffer.indexOf("--") != -1;
        return bl;
    }

    @Override
    public String getNodeName() {
        return "#comment";
    }

    @Override
    public short getNodeType() {
        return 8;
    }
}

