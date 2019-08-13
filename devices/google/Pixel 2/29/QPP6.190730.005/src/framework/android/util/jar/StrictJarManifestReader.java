/*
 * Decompiled with CFR 0.145.
 */
package android.util.jar;

import android.util.jar.StrictJarManifest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

class StrictJarManifestReader {
    private final HashMap<String, Attributes.Name> attributeNameCache = new HashMap();
    private final byte[] buf;
    private int consecutiveLineBreaks = 0;
    private final int endOfMainSection;
    private Attributes.Name name;
    private int pos;
    private String value;
    private final ByteArrayOutputStream valueBuffer = new ByteArrayOutputStream(80);

    public StrictJarManifestReader(byte[] arrby, Attributes attributes) throws IOException {
        this.buf = arrby;
        while (this.readHeader()) {
            attributes.put(this.name, this.value);
        }
        this.endOfMainSection = this.pos;
    }

    private boolean readHeader() throws IOException {
        int n = this.consecutiveLineBreaks;
        boolean bl = true;
        if (n > 1) {
            this.consecutiveLineBreaks = 0;
            return false;
        }
        this.readName();
        this.consecutiveLineBreaks = 0;
        this.readValue();
        if (this.consecutiveLineBreaks <= 0) {
            bl = false;
        }
        return bl;
    }

    private void readName() throws IOException {
        int n;
        Object object;
        int n2 = this.pos;
        while ((n = this.pos) < ((byte[])(object = this.buf)).length) {
            this.pos = n + 1;
            if (object[n] != 58) continue;
            object = new String((byte[])object, n2, this.pos - n2 - 1, StandardCharsets.US_ASCII);
            Object object2 = this.buf;
            n2 = this.pos;
            this.pos = n2 + 1;
            if (object2[n2] == 32) {
                try {
                    this.name = this.attributeNameCache.get(object);
                    if (this.name == null) {
                        this.name = object2 = new Attributes.Name((String)object);
                        this.attributeNameCache.put((String)object, this.name);
                    }
                    return;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new IOException(illegalArgumentException.getMessage());
                }
            }
            throw new IOException(String.format("Invalid value for attribute '%s'", object));
        }
    }

    private void readValue() throws IOException {
        byte[] arrby;
        int n;
        boolean bl = false;
        int n2 = this.pos;
        int n3 = this.pos;
        this.valueBuffer.reset();
        while ((n = this.pos) < (arrby = this.buf).length) {
            this.pos = n + 1;
            if ((n = arrby[n]) != 0) {
                if (n != 10) {
                    if (n != 13) {
                        if (n == 32 && this.consecutiveLineBreaks == 1) {
                            this.valueBuffer.write(arrby, n2, n3 - n2);
                            n2 = this.pos;
                            this.consecutiveLineBreaks = 0;
                            continue;
                        }
                        if (this.consecutiveLineBreaks >= 1) {
                            --this.pos;
                            break;
                        }
                        n3 = this.pos;
                        continue;
                    }
                    bl = true;
                    ++this.consecutiveLineBreaks;
                    continue;
                }
                if (bl) {
                    bl = false;
                    continue;
                }
                ++this.consecutiveLineBreaks;
                continue;
            }
            throw new IOException("NUL character in a manifest");
        }
        this.valueBuffer.write(this.buf, n2, n3 - n2);
        this.value = this.valueBuffer.toString(StandardCharsets.UTF_8.name());
    }

    public int getEndOfMainSection() {
        return this.endOfMainSection;
    }

    public void readEntries(Map<String, Attributes> map, Map<String, StrictJarManifest.Chunk> map2) throws IOException {
        int n = this.pos;
        while (this.readHeader()) {
            if (StrictJarManifest.ATTRIBUTE_NAME_NAME.equals(this.name)) {
                Attributes attributes;
                String string2 = this.value;
                Attributes attributes2 = attributes = map.get(string2);
                if (attributes == null) {
                    attributes2 = new Attributes(12);
                }
                while (this.readHeader()) {
                    attributes2.put(this.name, this.value);
                }
                int n2 = n;
                if (map2 != null) {
                    if (map2.get(string2) == null) {
                        map2.put(string2, new StrictJarManifest.Chunk(n, this.pos));
                        n2 = this.pos;
                    } else {
                        throw new IOException("A jar verifier does not support more than one entry with the same name");
                    }
                }
                map.put(string2, attributes2);
                n = n2;
                continue;
            }
            throw new IOException("Entry is not named");
        }
    }
}

