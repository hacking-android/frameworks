/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import java.util.Hashtable;

public class Lexer
extends LexerCore {
    public Lexer(String string, String string2) {
        super(string, string2);
        this.selectLexer(string);
    }

    public static String getHeaderName(String string) {
        String string2;
        block4 : {
            int n;
            if (string == null) {
                return null;
            }
            try {
                n = string.indexOf(":");
                string2 = null;
                if (n < 1) break block4;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                return null;
            }
            string2 = string.substring(0, n).trim();
        }
        return string2;
    }

    public static String getHeaderValue(String string) {
        if (string == null) {
            return null;
        }
        try {
            string = string.substring(string.indexOf(":") + 1);
            return string;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void selectLexer(String string) {
        Hashtable hashtable = lexerTables;
        synchronized (hashtable) {
            this.currentLexer = (Hashtable)lexerTables.get(string);
            this.currentLexerName = string;
            if (this.currentLexer == null) {
                this.addLexer(string);
                if (string.equals("method_keywordLexer")) {
                    this.addKeyword("REGISTER", 2052);
                    this.addKeyword("ACK", 2054);
                    this.addKeyword("OPTIONS", 2056);
                    this.addKeyword("BYE", 2055);
                    this.addKeyword("INVITE", 2053);
                    this.addKeyword("sip".toUpperCase(), 2051);
                    this.addKeyword("sips".toUpperCase(), 2136);
                    this.addKeyword("SUBSCRIBE", 2101);
                    this.addKeyword("NOTIFY", 2102);
                    this.addKeyword("MESSAGE", 2118);
                    this.addKeyword("PUBLISH", 2115);
                } else if (string.equals("command_keywordLexer")) {
                    this.addKeyword("Error-Info".toUpperCase(), 2058);
                    this.addKeyword("Allow-Events".toUpperCase(), 2113);
                    this.addKeyword("Authentication-Info".toUpperCase(), 2112);
                    this.addKeyword("Event".toUpperCase(), 2111);
                    this.addKeyword("Min-Expires".toUpperCase(), 2110);
                    this.addKeyword("RSeq".toUpperCase(), 2108);
                    this.addKeyword("RAck".toUpperCase(), 2109);
                    this.addKeyword("Reason".toUpperCase(), 2107);
                    this.addKeyword("Reply-To".toUpperCase(), 2106);
                    this.addKeyword("Subscription-State".toUpperCase(), 2104);
                    this.addKeyword("Timestamp".toUpperCase(), 2103);
                    this.addKeyword("In-Reply-To".toUpperCase(), 2059);
                    this.addKeyword("MIME-Version".toUpperCase(), 2060);
                    this.addKeyword("Alert-Info".toUpperCase(), 2061);
                    this.addKeyword("From".toUpperCase(), 2062);
                    this.addKeyword("To".toUpperCase(), 2063);
                    this.addKeyword("Refer-To".toUpperCase(), 2114);
                    this.addKeyword("Via".toUpperCase(), 2064);
                    this.addKeyword("User-Agent".toUpperCase(), 2065);
                    this.addKeyword("Server".toUpperCase(), 2066);
                    this.addKeyword("Accept-Encoding".toUpperCase(), 2067);
                    this.addKeyword("Accept".toUpperCase(), 2068);
                    this.addKeyword("Allow".toUpperCase(), 2069);
                    this.addKeyword("Route".toUpperCase(), 2070);
                    this.addKeyword("Authorization".toUpperCase(), 2071);
                    this.addKeyword("Proxy-Authorization".toUpperCase(), 2072);
                    this.addKeyword("Retry-After".toUpperCase(), 2073);
                    this.addKeyword("Proxy-Require".toUpperCase(), 2074);
                    this.addKeyword("Content-Language".toUpperCase(), 2075);
                    this.addKeyword("Unsupported".toUpperCase(), 2076);
                    this.addKeyword("Supported".toUpperCase(), 2068);
                    this.addKeyword("Warning".toUpperCase(), 2078);
                    this.addKeyword("Max-Forwards".toUpperCase(), 2079);
                    this.addKeyword("Date".toUpperCase(), 2080);
                    this.addKeyword("Priority".toUpperCase(), 2081);
                    this.addKeyword("Proxy-Authenticate".toUpperCase(), 2082);
                    this.addKeyword("Content-Encoding".toUpperCase(), 2083);
                    this.addKeyword("Content-Length".toUpperCase(), 2084);
                    this.addKeyword("Subject".toUpperCase(), 2085);
                    this.addKeyword("Content-Type".toUpperCase(), 2086);
                    this.addKeyword("Contact".toUpperCase(), 2087);
                    this.addKeyword("Call-ID".toUpperCase(), 2088);
                    this.addKeyword("Require".toUpperCase(), 2089);
                    this.addKeyword("Expires".toUpperCase(), 2090);
                    this.addKeyword("Record-Route".toUpperCase(), 2092);
                    this.addKeyword("Organization".toUpperCase(), 2093);
                    this.addKeyword("CSeq".toUpperCase(), 2094);
                    this.addKeyword("Accept-Language".toUpperCase(), 2095);
                    this.addKeyword("WWW-Authenticate".toUpperCase(), 2096);
                    this.addKeyword("Call-Info".toUpperCase(), 2099);
                    this.addKeyword("Content-Disposition".toUpperCase(), 2100);
                    this.addKeyword("K".toUpperCase(), 2068);
                    this.addKeyword("C".toUpperCase(), 2086);
                    this.addKeyword("E".toUpperCase(), 2083);
                    this.addKeyword("F".toUpperCase(), 2062);
                    this.addKeyword("I".toUpperCase(), 2088);
                    this.addKeyword("M".toUpperCase(), 2087);
                    this.addKeyword("L".toUpperCase(), 2084);
                    this.addKeyword("S".toUpperCase(), 2085);
                    this.addKeyword("T".toUpperCase(), 2063);
                    this.addKeyword("U".toUpperCase(), 2113);
                    this.addKeyword("V".toUpperCase(), 2064);
                    this.addKeyword("R".toUpperCase(), 2114);
                    this.addKeyword("O".toUpperCase(), 2111);
                    this.addKeyword("X".toUpperCase(), 2133);
                    this.addKeyword("SIP-ETag".toUpperCase(), 2116);
                    this.addKeyword("SIP-If-Match".toUpperCase(), 2117);
                    this.addKeyword("Session-Expires".toUpperCase(), 2133);
                    this.addKeyword("Min-SE".toUpperCase(), 2134);
                    this.addKeyword("Referred-By".toUpperCase(), 2132);
                    this.addKeyword("Replaces".toUpperCase(), 2135);
                    this.addKeyword("Join".toUpperCase(), 2140);
                    this.addKeyword("Path".toUpperCase(), 2119);
                    this.addKeyword("Service-Route".toUpperCase(), 2120);
                    this.addKeyword("P-Asserted-Identity".toUpperCase(), 2121);
                    this.addKeyword("P-Preferred-Identity".toUpperCase(), 2122);
                    this.addKeyword("Privacy".toUpperCase(), 2126);
                    this.addKeyword("P-Called-Party-ID".toUpperCase(), 2128);
                    this.addKeyword("P-Associated-URI".toUpperCase(), 2129);
                    this.addKeyword("P-Visited-Network-ID".toUpperCase(), 2123);
                    this.addKeyword("P-Charging-Function-Addresses".toUpperCase(), 2124);
                    this.addKeyword("P-Charging-Vector".toUpperCase(), 2125);
                    this.addKeyword("P-Access-Network-Info".toUpperCase(), 2127);
                    this.addKeyword("P-Media-Authorization".toUpperCase(), 2130);
                    this.addKeyword("Security-Server".toUpperCase(), 2137);
                    this.addKeyword("Security-Verify".toUpperCase(), 2139);
                    this.addKeyword("Security-Client".toUpperCase(), 2138);
                    this.addKeyword("P-User-Database".toUpperCase(), 2141);
                    this.addKeyword("P-Profile-Key".toUpperCase(), 2142);
                    this.addKeyword("P-Served-User".toUpperCase(), 2143);
                    this.addKeyword("P-Preferred-Service".toUpperCase(), 2144);
                    this.addKeyword("P-Asserted-Service".toUpperCase(), 2145);
                    this.addKeyword("References".toUpperCase(), 2146);
                } else if (string.equals("status_lineLexer")) {
                    this.addKeyword("sip".toUpperCase(), 2051);
                } else if (string.equals("request_lineLexer")) {
                    this.addKeyword("sip".toUpperCase(), 2051);
                } else if (string.equals("sip_urlLexer")) {
                    this.addKeyword("tel".toUpperCase(), 2105);
                    this.addKeyword("sip".toUpperCase(), 2051);
                    this.addKeyword("sips".toUpperCase(), 2136);
                }
            }
            return;
        }
    }
}

