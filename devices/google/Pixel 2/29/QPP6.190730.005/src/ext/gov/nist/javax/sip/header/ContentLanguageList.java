/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.ContentLanguage;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public final class ContentLanguageList
extends SIPHeaderList<ContentLanguage> {
    private static final long serialVersionUID = -5302265987802886465L;

    public ContentLanguageList() {
        super(ContentLanguage.class, "Content-Language");
    }

    @Override
    public Object clone() {
        ContentLanguageList contentLanguageList = new ContentLanguageList();
        contentLanguageList.clonehlist(this.hlist);
        return contentLanguageList;
    }
}

