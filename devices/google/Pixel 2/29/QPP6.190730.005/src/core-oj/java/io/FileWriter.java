/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FileWriter
extends OutputStreamWriter {
    public FileWriter(File file) throws IOException {
        super(new FileOutputStream(file));
    }

    public FileWriter(File file, boolean bl) throws IOException {
        super(new FileOutputStream(file, bl));
    }

    public FileWriter(FileDescriptor fileDescriptor) {
        super(new FileOutputStream(fileDescriptor));
    }

    public FileWriter(String string) throws IOException {
        super(new FileOutputStream(string));
    }

    public FileWriter(String string, boolean bl) throws IOException {
        super(new FileOutputStream(string, bl));
    }
}

