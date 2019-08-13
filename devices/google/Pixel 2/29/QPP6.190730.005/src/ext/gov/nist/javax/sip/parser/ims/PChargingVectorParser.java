/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PChargingVector;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PChargingVectorParser
extends ParametersParser
implements TokenTypes {
    protected PChargingVectorParser(Lexer lexer) {
        super(lexer);
    }

    public PChargingVectorParser(String string) {
        super(string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public SIPHeader parse() throws ParseException {
        if (PChargingVectorParser.debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2125);
            pChargingVector = new PChargingVector();
            while (this.lexer.lookAhead(0) != '\n') {
                this.parseParameter(pChargingVector);
                this.lexer.SPorHT();
                c = this.lexer.lookAhead(0);
                if (c == '\n' || c == '\u0000') ** break;
                this.lexer.match(59);
                this.lexer.SPorHT();
            }
            {
                catch (ParseException parseException) {
                    throw parseException;
                }
            }
            super.parse(pChargingVector);
            object = pChargingVector.getParameter("icid-value");
            if (object != null) {
                if (PChargingVectorParser.debug == false) return pChargingVector;
                this.dbg_leave("parse");
                return pChargingVector;
            }
            object = new ParseException("Missing a required Parameter : icid-value", 0);
            throw object;
        }
        catch (Throwable throwable) {
            if (PChargingVectorParser.debug == false) throw throwable;
            this.dbg_leave("parse");
            throw throwable;
        }
    }

    protected void parseParameter(PChargingVector pChargingVector) throws ParseException {
        if (debug) {
            this.dbg_enter("parseParameter");
        }
        try {
            pChargingVector.setParameter(this.nameValue('='));
            return;
        }
        finally {
            if (debug) {
                this.dbg_leave("parseParameter");
            }
        }
    }
}

