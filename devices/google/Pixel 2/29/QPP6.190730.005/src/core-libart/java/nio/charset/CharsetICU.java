/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetDecoderICU;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CharsetEncoderICU;
import libcore.icu.NativeConverter;

final class CharsetICU
extends Charset {
    private final String icuCanonicalName;

    protected CharsetICU(String string, String string2, String[] arrstring) {
        super(string, arrstring);
        this.icuCanonicalName = string2;
    }

    @Override
    public boolean contains(Charset charset) {
        if (charset == null) {
            return false;
        }
        if (this.equals(charset)) {
            return true;
        }
        return NativeConverter.contains(this.name(), charset.name());
    }

    @Override
    public CharsetDecoder newDecoder() {
        return CharsetDecoderICU.newInstance(this, this.icuCanonicalName);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return CharsetEncoderICU.newInstance(this, this.icuCanonicalName);
    }
}

