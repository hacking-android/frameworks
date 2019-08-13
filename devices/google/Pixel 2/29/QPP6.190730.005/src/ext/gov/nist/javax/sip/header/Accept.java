/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.header.MediaRange;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.InvalidArgumentException;
import javax.sip.header.AcceptHeader;

public final class Accept
extends ParametersHeader
implements AcceptHeader {
    private static final long serialVersionUID = -7866187924308658151L;
    protected MediaRange mediaRange;

    public Accept() {
        super("Accept");
    }

    @Override
    public boolean allowsAllContentSubTypes() {
        MediaRange mediaRange = this.mediaRange;
        boolean bl = false;
        if (mediaRange == null) {
            return false;
        }
        if (mediaRange.getSubtype().compareTo("*") == 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean allowsAllContentTypes() {
        MediaRange mediaRange = this.mediaRange;
        boolean bl = false;
        if (mediaRange == null) {
            return false;
        }
        if (mediaRange.type.compareTo("*") == 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public Object clone() {
        Accept accept = (Accept)super.clone();
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange != null) {
            accept.mediaRange = (MediaRange)mediaRange.clone();
        }
        return accept;
    }

    @Override
    protected String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange != null) {
            mediaRange.encode(stringBuffer);
        }
        if (this.parameters != null && !this.parameters.isEmpty()) {
            stringBuffer.append(';');
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public String getContentSubType() {
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange == null) {
            return null;
        }
        return mediaRange.getSubtype();
    }

    @Override
    public String getContentType() {
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange == null) {
            return null;
        }
        return mediaRange.getType();
    }

    public MediaRange getMediaRange() {
        return this.mediaRange;
    }

    @Override
    public float getQValue() {
        return this.getParameterAsFloat("q");
    }

    @Override
    public boolean hasQValue() {
        return super.hasParameter("q");
    }

    @Override
    public void removeQValue() {
        super.removeParameter("q");
    }

    @Override
    public void setContentSubType(String string) {
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.setSubtype(string);
    }

    @Override
    public void setContentType(String string) {
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.setType(string);
    }

    public void setMediaRange(MediaRange mediaRange) {
        this.mediaRange = mediaRange;
    }

    @Override
    public void setQValue(float f) throws InvalidArgumentException {
        if (f == -1.0f) {
            super.removeParameter("q");
        }
        super.setParameter("q", f);
    }
}

