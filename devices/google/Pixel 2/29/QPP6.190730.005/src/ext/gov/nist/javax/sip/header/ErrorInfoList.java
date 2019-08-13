/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.ErrorInfo;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class ErrorInfoList
extends SIPHeaderList<ErrorInfo> {
    private static final long serialVersionUID = 1L;

    public ErrorInfoList() {
        super(ErrorInfo.class, "Error-Info");
    }

    @Override
    public Object clone() {
        ErrorInfoList errorInfoList = new ErrorInfoList();
        errorInfoList.clonehlist(this.hlist);
        return errorInfoList;
    }
}

