/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.io.Serializable;
import javax.sip.Dialog;
import javax.sip.ObjectInUseException;
import javax.sip.SipProvider;
import javax.sip.TransactionState;
import javax.sip.message.Request;

public interface Transaction
extends Serializable {
    public Object getApplicationData();

    public String getBranchId();

    public Dialog getDialog();

    public String getHost();

    public String getPeerAddress();

    public int getPeerPort();

    public int getPort();

    public Request getRequest();

    public int getRetransmitTimer() throws UnsupportedOperationException;

    public SipProvider getSipProvider();

    public TransactionState getState();

    public String getTransport();

    public void setApplicationData(Object var1);

    public void setRetransmitTimer(int var1) throws UnsupportedOperationException;

    public void terminate() throws ObjectInUseException;
}

