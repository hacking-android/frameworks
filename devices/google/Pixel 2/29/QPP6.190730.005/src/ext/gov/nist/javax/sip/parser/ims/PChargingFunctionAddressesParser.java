/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PChargingFunctionAddresses;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.TokenTypes;
import java.io.PrintStream;
import java.text.ParseException;

public class PChargingFunctionAddressesParser
extends ParametersParser
implements TokenTypes {
    protected PChargingFunctionAddressesParser(Lexer lexer) {
        super(lexer);
    }

    public PChargingFunctionAddressesParser(String string) {
        super(string);
    }

    public static void main(String[] arrstring) throws ParseException {
        arrstring = new String[]{"P-Charging-Function-Addresses: ccf=\"test str\"; ecf=token\n", "P-Charging-Function-Addresses: ccf=192.1.1.1; ccf=192.1.1.2; ecf=192.1.1.3; ecf=192.1.1.4\n", "P-Charging-Function-Addresses: ccf=[5555::b99:c88:d77:e66]; ccf=[5555::a55:b44:c33:d22]; ecf=[5555::1ff:2ee:3dd:4cc]; ecf=[5555::6aa:7bb:8cc:9dd]\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            Object object = new PChargingFunctionAddressesParser(arrstring[i]);
            Object object2 = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("original = ");
            stringBuilder.append(arrstring[i]);
            ((PrintStream)object2).println(stringBuilder.toString());
            object2 = (PChargingFunctionAddresses)((PChargingFunctionAddressesParser)object).parse();
            object = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append("encoded = ");
            stringBuilder.append(((SIPHeader)object2).encode());
            ((PrintStream)object).println(stringBuilder.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        if (PChargingFunctionAddressesParser.debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2124);
            var1_1 = new PChargingFunctionAddresses();
            while (this.lexer.lookAhead(0) != '\n') {
                this.parseParameter(var1_1);
                this.lexer.SPorHT();
                var2_4 = this.lexer.lookAhead(0);
                if (var2_4 == '\n' || var2_4 == '\u0000') ** break;
                this.lexer.match(59);
                this.lexer.SPorHT();
            }
            super.parse(var1_1);
            return var1_1;
        }
        finally {
            if (PChargingFunctionAddressesParser.debug) {
                this.dbg_leave("parse");
            }
        }
    }

    protected void parseParameter(PChargingFunctionAddresses pChargingFunctionAddresses) throws ParseException {
        if (debug) {
            this.dbg_enter("parseParameter");
        }
        try {
            pChargingFunctionAddresses.setMultiParameter(this.nameValue('='));
            return;
        }
        finally {
            if (debug) {
                this.dbg_leave("parseParameter");
            }
        }
    }
}

