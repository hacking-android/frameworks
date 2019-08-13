/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.MediaRange;
import gov.nist.javax.sip.header.ParametersHeader;
import java.text.ParseException;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.Parameters;

public class ContentType
extends ParametersHeader
implements ContentTypeHeader {
    private static final long serialVersionUID = 8475682204373446610L;
    protected MediaRange mediaRange;

    public ContentType() {
        super("Content-Type");
    }

    public ContentType(String string, String string2) {
        this();
        this.setContentType(string, string2);
    }

    @Override
    public Object clone() {
        ContentType contentType = (ContentType)super.clone();
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange != null) {
            contentType.mediaRange = (MediaRange)mediaRange.clone();
        }
        return contentType;
    }

    public int compareMediaRange(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mediaRange.type);
        stringBuilder.append("/");
        stringBuilder.append(this.mediaRange.subtype);
        return stringBuilder.toString().compareToIgnoreCase(string);
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        this.mediaRange.encode(stringBuffer);
        if (this.hasParameters()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof ContentTypeHeader;
        boolean bl2 = false;
        if (bl) {
            object = (ContentTypeHeader)object;
            if (this.getContentType().equalsIgnoreCase(object.getContentType()) && this.getContentSubType().equalsIgnoreCase(object.getContentSubType()) && this.equalParameters((Parameters)object)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    @Override
    public String getCharset() {
        return this.getParameter("charset");
    }

    @Override
    public String getContentSubType() {
        Object object = this.mediaRange;
        object = object == null ? null : ((MediaRange)object).getSubtype();
        return object;
    }

    @Override
    public String getContentType() {
        Object object = this.mediaRange;
        object = object == null ? null : ((MediaRange)object).getType();
        return object;
    }

    public MediaRange getMediaRange() {
        return this.mediaRange;
    }

    public String getMediaSubType() {
        return this.mediaRange.subtype;
    }

    public String getMediaType() {
        return this.mediaRange.type;
    }

    @Override
    public void setContentSubType(String string) throws ParseException {
        if (string != null) {
            if (this.mediaRange == null) {
                this.mediaRange = new MediaRange();
            }
            this.mediaRange.setSubtype(string);
            return;
        }
        throw new NullPointerException("null arg");
    }

    @Override
    public void setContentType(String string) throws ParseException {
        if (string != null) {
            if (this.mediaRange == null) {
                this.mediaRange = new MediaRange();
            }
            this.mediaRange.setType(string);
            return;
        }
        throw new NullPointerException("null arg");
    }

    @Override
    public void setContentType(String string, String string2) {
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.setType(string);
        this.mediaRange.setSubtype(string2);
    }

    public void setMediaRange(MediaRange mediaRange) {
        this.mediaRange = mediaRange;
    }
}

