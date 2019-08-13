/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.ims.PUserDatabaseHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PUserDatabase
extends ParametersHeader
implements PUserDatabaseHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    private String databaseName;

    public PUserDatabase() {
        super("P-User-Database");
    }

    public PUserDatabase(String string) {
        super("P-User-Database");
        this.databaseName = string;
    }

    @Override
    public Object clone() {
        return (PUserDatabase)super.clone();
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<");
        if (this.getDatabaseName() != null) {
            stringBuffer.append(this.getDatabaseName());
        }
        if (!this.parameters.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(";");
            stringBuilder.append(this.parameters.encode());
            stringBuffer.append(stringBuilder.toString());
        }
        stringBuffer.append(">");
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof PUserDatabaseHeader && super.equals(object);
        return bl;
    }

    @Override
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public void setDatabaseName(String string) {
        if (string != null && !string.equals(" ")) {
            if (!string.contains("aaa://")) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("aaa://");
                stringBuffer.append(string);
                this.databaseName = stringBuffer.toString();
            } else {
                this.databaseName = string;
            }
            return;
        }
        throw new NullPointerException("Database name is null");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

