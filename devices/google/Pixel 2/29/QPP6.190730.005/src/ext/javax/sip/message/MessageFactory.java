/*
 * Decompiled with CFR 0.145.
 */
package javax.sip.message;

import java.text.ParseException;
import java.util.List;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ServerHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.UserAgentHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public interface MessageFactory {
    public Request createRequest(String var1) throws ParseException;

    public Request createRequest(URI var1, String var2, CallIdHeader var3, CSeqHeader var4, FromHeader var5, ToHeader var6, List var7, MaxForwardsHeader var8) throws ParseException;

    public Request createRequest(URI var1, String var2, CallIdHeader var3, CSeqHeader var4, FromHeader var5, ToHeader var6, List var7, MaxForwardsHeader var8, ContentTypeHeader var9, Object var10) throws ParseException;

    public Request createRequest(URI var1, String var2, CallIdHeader var3, CSeqHeader var4, FromHeader var5, ToHeader var6, List var7, MaxForwardsHeader var8, ContentTypeHeader var9, byte[] var10) throws ParseException;

    public Response createResponse(int var1, CallIdHeader var2, CSeqHeader var3, FromHeader var4, ToHeader var5, List var6, MaxForwardsHeader var7) throws ParseException;

    public Response createResponse(int var1, CallIdHeader var2, CSeqHeader var3, FromHeader var4, ToHeader var5, List var6, MaxForwardsHeader var7, ContentTypeHeader var8, Object var9) throws ParseException;

    public Response createResponse(int var1, CallIdHeader var2, CSeqHeader var3, FromHeader var4, ToHeader var5, List var6, MaxForwardsHeader var7, ContentTypeHeader var8, byte[] var9) throws ParseException;

    public Response createResponse(int var1, Request var2) throws ParseException;

    public Response createResponse(int var1, Request var2, ContentTypeHeader var3, Object var4) throws ParseException;

    public Response createResponse(int var1, Request var2, ContentTypeHeader var3, byte[] var4) throws ParseException;

    public Response createResponse(String var1) throws ParseException;

    public void setDefaultContentEncodingCharset(String var1) throws NullPointerException, IllegalArgumentException;

    public void setDefaultServerHeader(ServerHeader var1);

    public void setDefaultUserAgentHeader(UserAgentHeader var1);
}

