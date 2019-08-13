/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.Util;

class UnixException
extends Exception {
    static final long serialVersionUID = 7227016794320723218L;
    private int errno;
    private String msg;

    UnixException(int n) {
        this.errno = n;
        this.msg = null;
    }

    UnixException(String string) {
        this.errno = 0;
        this.msg = string;
    }

    private IOException translateToIOException(String string, String string2) {
        String string3 = this.msg;
        if (string3 != null) {
            return new IOException(string3);
        }
        if (this.errno() == UnixConstants.EACCES) {
            return new AccessDeniedException(string, string2, null);
        }
        if (this.errno() == UnixConstants.ENOENT) {
            return new NoSuchFileException(string, string2, null);
        }
        if (this.errno() == UnixConstants.EEXIST) {
            return new FileAlreadyExistsException(string, string2, null);
        }
        return new FileSystemException(string, string2, this.errorString());
    }

    IOException asIOException(UnixPath unixPath) {
        return this.translateToIOException(unixPath.getPathForExceptionMessage(), null);
    }

    int errno() {
        return this.errno;
    }

    String errorString() {
        String string = this.msg;
        if (string != null) {
            return string;
        }
        return Util.toString(UnixNativeDispatcher.strerror(this.errno()));
    }

    @Override
    public String getMessage() {
        return this.errorString();
    }

    void rethrowAsIOException(String string) throws IOException {
        throw this.translateToIOException(string, null);
    }

    void rethrowAsIOException(UnixPath unixPath) throws IOException {
        this.rethrowAsIOException(unixPath, null);
    }

    void rethrowAsIOException(UnixPath object, UnixPath object2) throws IOException {
        Object var3_3 = null;
        object = object == null ? null : ((UnixPath)object).getPathForExceptionMessage();
        object2 = object2 == null ? var3_3 : ((UnixPath)object2).getPathForExceptionMessage();
        throw this.translateToIOException((String)object, (String)object2);
    }

    void setError(int n) {
        this.errno = n;
        this.msg = null;
    }
}

