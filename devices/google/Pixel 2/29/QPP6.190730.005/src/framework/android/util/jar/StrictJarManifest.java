/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Streams
 */
package android.util.jar;

import android.util.jar.StrictJarManifestReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import libcore.io.Streams;

public class StrictJarManifest
implements Cloneable {
    static final Attributes.Name ATTRIBUTE_NAME_NAME;
    static final int LINE_LENGTH_LIMIT = 72;
    private static final byte[] LINE_SEPARATOR;
    private static final byte[] VALUE_SEPARATOR;
    private HashMap<String, Chunk> chunks;
    private final HashMap<String, Attributes> entries;
    private final Attributes mainAttributes;
    private int mainEnd;

    static {
        LINE_SEPARATOR = new byte[]{13, 10};
        VALUE_SEPARATOR = new byte[]{58, 32};
        ATTRIBUTE_NAME_NAME = new Attributes.Name("Name");
    }

    public StrictJarManifest() {
        this.entries = new HashMap();
        this.mainAttributes = new Attributes();
    }

    public StrictJarManifest(StrictJarManifest strictJarManifest) {
        this.mainAttributes = (Attributes)strictJarManifest.mainAttributes.clone();
        this.entries = (HashMap)((HashMap)strictJarManifest.getEntries()).clone();
    }

    public StrictJarManifest(InputStream inputStream) throws IOException {
        this();
        this.read(Streams.readFully((InputStream)inputStream));
    }

    StrictJarManifest(byte[] arrby, boolean bl) throws IOException {
        this();
        if (bl) {
            this.chunks = new HashMap();
        }
        this.read(arrby);
    }

    private void read(byte[] object) throws IOException {
        if (((byte[])object).length == 0) {
            return;
        }
        object = new StrictJarManifestReader((byte[])object, this.mainAttributes);
        this.mainEnd = ((StrictJarManifestReader)object).getEndOfMainSection();
        ((StrictJarManifestReader)object).readEntries(this.entries, this.chunks);
    }

    /*
     * WARNING - void declaration
     */
    static void write(StrictJarManifest strictJarManifest, OutputStream outputStream) throws IOException {
        void var6_8;
        CharsetEncoder charsetEncoder = StandardCharsets.UTF_8.newEncoder();
        ByteBuffer byteBuffer = ByteBuffer.allocate(72);
        Object object = Attributes.Name.MANIFEST_VERSION;
        Object object222 = strictJarManifest.mainAttributes.getValue((Attributes.Name)object);
        String object32 = object222;
        if (object222 == null) {
            object = Attributes.Name.SIGNATURE_VERSION;
            String string2 = strictJarManifest.mainAttributes.getValue((Attributes.Name)object);
        }
        if (var6_8 != null) {
            StrictJarManifest.writeEntry(outputStream, (Attributes.Name)object, (String)var6_8, charsetEncoder, byteBuffer);
            for (Object object222 : strictJarManifest.mainAttributes.keySet()) {
                if (((Attributes.Name)object222).equals(object)) continue;
                StrictJarManifest.writeEntry(outputStream, (Attributes.Name)object222, strictJarManifest.mainAttributes.getValue((Attributes.Name)object222), charsetEncoder, byteBuffer);
            }
        }
        outputStream.write(LINE_SEPARATOR);
        for (String string3 : strictJarManifest.getEntries().keySet()) {
            StrictJarManifest.writeEntry(outputStream, ATTRIBUTE_NAME_NAME, string3, charsetEncoder, byteBuffer);
            Attributes attributes = strictJarManifest.entries.get(string3);
            for (Attributes.Name name : attributes.keySet()) {
                StrictJarManifest.writeEntry(outputStream, name, attributes.getValue(name), charsetEncoder, byteBuffer);
            }
            outputStream.write(LINE_SEPARATOR);
        }
    }

    private static void writeEntry(OutputStream outputStream, Attributes.Name object, String object2, CharsetEncoder charsetEncoder, ByteBuffer byteBuffer) throws IOException {
        object = ((Attributes.Name)object).toString();
        outputStream.write(((String)object).getBytes(StandardCharsets.US_ASCII));
        outputStream.write(VALUE_SEPARATOR);
        charsetEncoder.reset();
        byteBuffer.clear().limit(72 - ((String)object).length() - 2);
        CharBuffer charBuffer = CharBuffer.wrap((CharSequence)object2);
        do {
            object = object2 = charsetEncoder.encode(charBuffer, byteBuffer, true);
            if (CoderResult.UNDERFLOW == object2) {
                object = charsetEncoder.flush(byteBuffer);
            }
            outputStream.write(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position());
            outputStream.write(LINE_SEPARATOR);
            if (CoderResult.UNDERFLOW == object) {
                return;
            }
            outputStream.write(32);
            byteBuffer.clear().limit(71);
        } while (true);
    }

    public void clear() {
        this.entries.clear();
        this.mainAttributes.clear();
    }

    public Object clone() {
        return new StrictJarManifest(this);
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        if (!this.mainAttributes.equals(((StrictJarManifest)object).mainAttributes)) {
            return false;
        }
        return this.getEntries().equals(((StrictJarManifest)object).getEntries());
    }

    public Attributes getAttributes(String string2) {
        return this.getEntries().get(string2);
    }

    Chunk getChunk(String string2) {
        return this.chunks.get(string2);
    }

    public Map<String, Attributes> getEntries() {
        return this.entries;
    }

    public Attributes getMainAttributes() {
        return this.mainAttributes;
    }

    int getMainAttributesEnd() {
        return this.mainEnd;
    }

    public int hashCode() {
        return this.mainAttributes.hashCode() ^ this.getEntries().hashCode();
    }

    public void read(InputStream inputStream) throws IOException {
        this.read(Streams.readFullyNoClose((InputStream)inputStream));
    }

    void removeChunks() {
        this.chunks = null;
    }

    public void write(OutputStream outputStream) throws IOException {
        StrictJarManifest.write(this, outputStream);
    }

    static final class Chunk {
        final int end;
        final int start;

        Chunk(int n, int n2) {
            this.start = n;
            this.end = n2;
        }
    }

}

