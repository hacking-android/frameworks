/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.Path;
import java.util.List;

public class PathList
extends SIPHeaderList<Path> {
    public PathList() {
        super(Path.class, "Path");
    }

    @Override
    public Object clone() {
        return new PathList().clonehlist(this.hlist);
    }
}

