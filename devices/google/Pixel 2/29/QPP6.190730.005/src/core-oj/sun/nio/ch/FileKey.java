/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

public class FileKey {
    private long st_dev;
    private long st_ino;

    private FileKey() {
    }

    public static FileKey create(FileDescriptor fileDescriptor) {
        FileKey fileKey = new FileKey();
        try {
            fileKey.init(fileDescriptor);
            return fileKey;
        }
        catch (IOException iOException) {
            throw new Error(iOException);
        }
    }

    private native void init(FileDescriptor var1) throws IOException;

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FileKey)) {
            return false;
        }
        object = (FileKey)object;
        return this.st_dev == ((FileKey)object).st_dev && this.st_ino == ((FileKey)object).st_ino;
        {
        }
    }

    public int hashCode() {
        long l = this.st_dev;
        int n = (int)(l ^ l >>> 32);
        l = this.st_ino;
        return n + (int)(l >>> 32 ^ l);
    }
}

