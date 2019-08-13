/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.Organization;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class OrganizationParser
extends HeaderParser {
    protected OrganizationParser(Lexer lexer) {
        super(lexer);
    }

    public OrganizationParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("OrganizationParser.parse");
        }
        Organization organization = new Organization();
        try {
            this.headerName(2093);
            organization.setHeaderName("Organization");
            this.lexer.SPorHT();
            organization.setOrganization(this.lexer.getRest().trim());
            return organization;
        }
        finally {
            if (debug) {
                this.dbg_leave("OrganizationParser.parse");
            }
        }
    }
}

