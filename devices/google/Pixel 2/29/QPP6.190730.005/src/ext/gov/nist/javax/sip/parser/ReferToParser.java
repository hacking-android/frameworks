/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReferTo;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.io.PrintStream;
import java.text.ParseException;

public class ReferToParser
extends AddressParametersParser {
    protected ReferToParser(Lexer lexer) {
        super(lexer);
    }

    public ReferToParser(String string) {
        super(string);
    }

    public static void main(String[] arrstring) throws ParseException {
        arrstring = new String[]{"Refer-To: <sip:dave@denver.example.org?Replaces=12345%40192.168.118.3%3Bto-tag%3D12345%3Bfrom-tag%3D5FFE-3994>\n", "Refer-To: <sip:+1-650-555-2222@ss1.wcom.com;user=phone>;tag=5617\n", "Refer-To: T. A. Watson <sip:watson@bell-telephone.com>\n", "Refer-To: LittleGuy <sip:UserB@there.com>\n", "Refer-To: sip:mranga@120.6.55.9\n", "Refer-To: sip:mranga@129.6.55.9 ; tag=696928473514.129.6.55.9\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            ReferTo referTo = (ReferTo)new ReferToParser(arrstring[i]).parse();
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("encoded = ");
            stringBuilder.append(referTo.encode());
            printStream.println(stringBuilder.toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.headerName(2114);
        ReferTo referTo = new ReferTo();
        super.parse(referTo);
        this.lexer.match(10);
        return referTo;
    }
}

