/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;
import javax.sip.header.Parameters;

public interface SecurityAgreeHeader
extends Parameters,
Header {
    public String getAlgorithm();

    public String getEncryptionAlgorithm();

    public String getMode();

    public int getPortClient();

    public int getPortServer();

    public float getPreference();

    public String getProtocol();

    public int getSPIClient();

    public int getSPIServer();

    public String getSecurityMechanism();

    public void setAlgorithm(String var1) throws ParseException;

    public void setEncryptionAlgorithm(String var1) throws ParseException;

    public void setMode(String var1) throws ParseException;

    public void setPortClient(int var1) throws InvalidArgumentException;

    public void setPortServer(int var1) throws InvalidArgumentException;

    public void setPreference(float var1) throws InvalidArgumentException;

    public void setProtocol(String var1) throws ParseException;

    public void setSPIClient(int var1) throws InvalidArgumentException;

    public void setSPIServer(int var1) throws InvalidArgumentException;

    public void setSecurityMechanism(String var1) throws ParseException;
}

