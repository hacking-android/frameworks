/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityClient;
import gov.nist.javax.sip.header.ims.SecurityClientList;
import gov.nist.javax.sip.header.ims.SecurityServer;
import gov.nist.javax.sip.header.ims.SecurityServerList;
import gov.nist.javax.sip.header.ims.SecurityVerify;
import gov.nist.javax.sip.header.ims.SecurityVerifyList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.io.Serializable;
import java.text.ParseException;

public class SecurityAgreeParser
extends HeaderParser {
    protected SecurityAgreeParser(Lexer lexer) {
        super(lexer);
    }

    public SecurityAgreeParser(String string) {
        super(string);
    }

    public SIPHeaderList parse(SecurityAgree serializable) throws ParseException {
        block19 : {
            SIPHeaderList sIPHeaderList;
            block17 : {
                block18 : {
                    block16 : {
                        if (!serializable.getClass().isInstance(new SecurityClient())) break block16;
                        sIPHeaderList = new SecurityClientList();
                        break block17;
                    }
                    if (!serializable.getClass().isInstance(new SecurityServer())) break block18;
                    sIPHeaderList = new SecurityServerList();
                    break block17;
                }
                if (!serializable.getClass().isInstance(new SecurityVerify())) break block19;
                sIPHeaderList = new SecurityVerifyList();
            }
            this.lexer.SPorHT();
            this.lexer.match(4095);
            ((SecurityAgree)serializable).setSecurityMechanism(this.lexer.getNextToken().getTokenValue());
            this.lexer.SPorHT();
            char c = this.lexer.lookAhead(0);
            if (c == '\n') {
                sIPHeaderList.add(serializable);
                return sIPHeaderList;
            }
            if (c == ';') {
                this.lexer.match(59);
            }
            this.lexer.SPorHT();
            do {
                Serializable serializable2;
                block15 : {
                    if (this.lexer.lookAhead(0) == '\n') break;
                    this.parseParameter((SecurityAgree)serializable);
                    this.lexer.SPorHT();
                    c = this.lexer.lookAhead(0);
                    if (c == '\n' || c == '\u0000') break;
                    serializable2 = serializable;
                    if (c != ',') break block15;
                    sIPHeaderList.add(serializable);
                    serializable2 = serializable.getClass();
                    SecurityAgree securityAgree = new SecurityClient();
                    if (((Class)serializable2).isInstance(securityAgree)) {
                        serializable = new SecurityClient();
                    } else {
                        serializable2 = serializable.getClass();
                        if (((Class)serializable2).isInstance(securityAgree = new SecurityServer())) {
                            serializable = new SecurityServer();
                        } else {
                            serializable2 = serializable.getClass();
                            if (((Class)serializable2).isInstance(securityAgree = new SecurityVerify())) {
                                serializable = new SecurityVerify();
                            }
                        }
                    }
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    ((SecurityAgree)serializable).setSecurityMechanism(this.lexer.getNextToken().getTokenValue());
                    serializable2 = serializable;
                }
                this.lexer.SPorHT();
                if (this.lexer.lookAhead(0) == ';') {
                    this.lexer.match(59);
                }
                this.lexer.SPorHT();
                serializable = serializable2;
                continue;
                break;
            } while (true);
            sIPHeaderList.add(serializable);
            return sIPHeaderList;
        }
        return null;
    }

    protected void parseParameter(SecurityAgree securityAgree) throws ParseException {
        if (debug) {
            this.dbg_enter("parseParameter");
        }
        try {
            securityAgree.setParameter(this.nameValue('='));
            return;
        }
        finally {
            if (debug) {
                this.dbg_leave("parseParameter");
            }
        }
    }
}

