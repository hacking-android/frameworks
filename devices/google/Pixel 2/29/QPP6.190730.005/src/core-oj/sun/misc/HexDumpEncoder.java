/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import sun.misc.CharacterEncoder;

public class HexDumpEncoder
extends CharacterEncoder {
    private int currentByte;
    private int offset;
    private byte[] thisLine = new byte[16];
    private int thisLineLength;

    static void hexDigit(PrintStream printStream, byte by) {
        char c = (char)(by >> 4 & 15);
        c = c > '\t' ? (char)(c - 10 + 65) : (char)(c + 48);
        printStream.write(c);
        by = (byte)(by & 15);
        by = by > 9 ? (byte)(by - 10 + 65) : (byte)(by + 48);
        printStream.write(by);
    }

    @Override
    protected int bytesPerAtom() {
        return 1;
    }

    @Override
    protected int bytesPerLine() {
        return 16;
    }

    @Override
    protected void encodeAtom(OutputStream outputStream, byte[] arrby, int n, int n2) throws IOException {
        this.thisLine[this.currentByte] = arrby[n];
        HexDumpEncoder.hexDigit(this.pStream, arrby[n]);
        this.pStream.print(" ");
        ++this.currentByte;
        if (this.currentByte == 8) {
            this.pStream.print("  ");
        }
    }

    @Override
    protected void encodeBufferPrefix(OutputStream outputStream) throws IOException {
        this.offset = 0;
        super.encodeBufferPrefix(outputStream);
    }

    @Override
    protected void encodeLinePrefix(OutputStream outputStream, int n) throws IOException {
        HexDumpEncoder.hexDigit(this.pStream, (byte)(this.offset >>> 8 & 255));
        HexDumpEncoder.hexDigit(this.pStream, (byte)(this.offset & 255));
        this.pStream.print(": ");
        this.currentByte = 0;
        this.thisLineLength = n;
    }

    @Override
    protected void encodeLineSuffix(OutputStream arrby) throws IOException {
        int n;
        if (this.thisLineLength < 16) {
            for (n = this.thisLineLength; n < 16; ++n) {
                this.pStream.print("   ");
                if (n != 7) continue;
                this.pStream.print("  ");
            }
        }
        this.pStream.print(" ");
        for (n = 0; n < this.thisLineLength; ++n) {
            arrby = this.thisLine;
            if (arrby[n] >= 32 && arrby[n] <= 122) {
                this.pStream.write(this.thisLine[n]);
                continue;
            }
            this.pStream.print(".");
        }
        this.pStream.println();
        this.offset += this.thisLineLength;
    }
}

