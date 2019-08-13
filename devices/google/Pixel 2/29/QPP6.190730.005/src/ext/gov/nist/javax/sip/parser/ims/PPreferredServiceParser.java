/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PPreferredService;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class PPreferredServiceParser
extends HeaderParser
implements TokenTypes {
    protected PPreferredServiceParser(Lexer lexer) {
        super(lexer);
    }

    public PPreferredServiceParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PPreferredServiceParser.parse");
        }
        try {
            this.lexer.match(2144);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            PPreferredService pPreferredService = new PPreferredService();
            Object object = this.lexer.getBuffer();
            if (((String)object).contains("urn:urn-7:")) {
                boolean bl = ((String)object).contains("3gpp-service");
                if (bl) {
                    if (!((String)(object = ((String)object).split("3gpp-service.")[1])).trim().equals("")) {
                        pPreferredService.setSubserviceIdentifiers((String)object);
                    } else {
                        try {
                            object = new InvalidArgumentException("URN should atleast have one sub-service");
                            throw object;
                        }
                        catch (InvalidArgumentException invalidArgumentException) {
                            invalidArgumentException.printStackTrace();
                        }
                    }
                } else if (((String)object).contains("3gpp-application")) {
                    if (!((String)(object = ((String)object).split("3gpp-application")[1])).trim().equals("")) {
                        pPreferredService.setApplicationIdentifiers((String)object);
                    } else {
                        try {
                            object = new InvalidArgumentException("URN should atleast have one sub-application");
                            throw object;
                        }
                        catch (InvalidArgumentException invalidArgumentException) {
                            invalidArgumentException.printStackTrace();
                        }
                    }
                } else {
                    try {
                        object = new InvalidArgumentException("URN is not well formed");
                        throw object;
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        invalidArgumentException.printStackTrace();
                    }
                }
            }
            super.parse();
            return pPreferredService;
        }
        finally {
            if (debug) {
                this.dbg_enter("PPreferredServiceParser.parse");
            }
        }
    }
}

