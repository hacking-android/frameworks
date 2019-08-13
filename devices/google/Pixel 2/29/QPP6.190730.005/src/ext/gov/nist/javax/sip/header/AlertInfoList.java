/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class AlertInfoList
extends SIPHeaderList<AlertInfo> {
    private static final long serialVersionUID = 1L;

    public AlertInfoList() {
        super(AlertInfo.class, "Alert-Info");
    }

    @Override
    public Object clone() {
        AlertInfoList alertInfoList = new AlertInfoList();
        alertInfoList.clonehlist(this.hlist);
        return alertInfoList;
    }
}

