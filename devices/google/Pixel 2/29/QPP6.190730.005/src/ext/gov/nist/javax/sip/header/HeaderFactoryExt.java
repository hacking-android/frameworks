/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SipRequestLine;
import gov.nist.javax.sip.header.SipStatusLine;
import gov.nist.javax.sip.header.extensions.JoinHeader;
import gov.nist.javax.sip.header.extensions.ReferredByHeader;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
import gov.nist.javax.sip.header.extensions.SessionExpiresHeader;
import gov.nist.javax.sip.header.ims.PAccessNetworkInfoHeader;
import gov.nist.javax.sip.header.ims.PAssertedIdentityHeader;
import gov.nist.javax.sip.header.ims.PAssertedServiceHeader;
import gov.nist.javax.sip.header.ims.PAssociatedURIHeader;
import gov.nist.javax.sip.header.ims.PCalledPartyIDHeader;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddressesHeader;
import gov.nist.javax.sip.header.ims.PChargingVectorHeader;
import gov.nist.javax.sip.header.ims.PMediaAuthorizationHeader;
import gov.nist.javax.sip.header.ims.PPreferredIdentityHeader;
import gov.nist.javax.sip.header.ims.PPreferredServiceHeader;
import gov.nist.javax.sip.header.ims.PProfileKeyHeader;
import gov.nist.javax.sip.header.ims.PServedUserHeader;
import gov.nist.javax.sip.header.ims.PUserDatabaseHeader;
import gov.nist.javax.sip.header.ims.PVisitedNetworkIDHeader;
import gov.nist.javax.sip.header.ims.PathHeader;
import gov.nist.javax.sip.header.ims.PrivacyHeader;
import gov.nist.javax.sip.header.ims.SecurityClientHeader;
import gov.nist.javax.sip.header.ims.SecurityServerHeader;
import gov.nist.javax.sip.header.ims.SecurityVerifyHeader;
import gov.nist.javax.sip.header.ims.ServiceRouteHeader;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.address.Address;
import javax.sip.header.Header;
import javax.sip.header.HeaderFactory;

public interface HeaderFactoryExt
extends HeaderFactory {
    public PChargingVectorHeader createChargingVectorHeader(String var1) throws ParseException;

    @Override
    public Header createHeader(String var1) throws ParseException;

    public JoinHeader createJoinHeader(String var1, String var2, String var3) throws ParseException;

    public PAccessNetworkInfoHeader createPAccessNetworkInfoHeader();

    public PAssertedIdentityHeader createPAssertedIdentityHeader(Address var1) throws NullPointerException, ParseException;

    public PAssertedServiceHeader createPAssertedServiceHeader();

    public PAssociatedURIHeader createPAssociatedURIHeader(Address var1);

    public PCalledPartyIDHeader createPCalledPartyIDHeader(Address var1);

    public PChargingFunctionAddressesHeader createPChargingFunctionAddressesHeader();

    public PMediaAuthorizationHeader createPMediaAuthorizationHeader(String var1) throws InvalidArgumentException, ParseException;

    public PPreferredIdentityHeader createPPreferredIdentityHeader(Address var1);

    public PPreferredServiceHeader createPPreferredServiceHeader();

    public PProfileKeyHeader createPProfileKeyHeader(Address var1);

    public PServedUserHeader createPServedUserHeader(Address var1);

    public PUserDatabaseHeader createPUserDatabaseHeader(String var1);

    public PVisitedNetworkIDHeader createPVisitedNetworkIDHeader();

    public PathHeader createPathHeader(Address var1);

    public PrivacyHeader createPrivacyHeader(String var1);

    public ReferredByHeader createReferredByHeader(Address var1);

    public ReplacesHeader createReplacesHeader(String var1, String var2, String var3) throws ParseException;

    public SipRequestLine createRequestLine(String var1) throws ParseException;

    public SecurityClientHeader createSecurityClientHeader();

    public SecurityServerHeader createSecurityServerHeader();

    public SecurityVerifyHeader createSecurityVerifyHeader();

    public ServiceRouteHeader createServiceRouteHeader(Address var1);

    public SessionExpiresHeader createSessionExpiresHeader(int var1) throws InvalidArgumentException;

    public SipStatusLine createStatusLine(String var1) throws ParseException;
}

