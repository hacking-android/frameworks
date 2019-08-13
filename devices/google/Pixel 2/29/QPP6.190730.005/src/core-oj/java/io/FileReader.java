/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader
extends InputStreamReader {
    public FileReader(File file) throws FileNotFoundException {
        super(new FileInputStream(file));
    }

    public FileReader(FileDescriptor fileDescriptor) {
        super(new FileInputStream(fileDescriptor));
    }

    public FileReader(String string) throws FileNotFoundException {
        super(new FileInputStream(string));
    }
}

