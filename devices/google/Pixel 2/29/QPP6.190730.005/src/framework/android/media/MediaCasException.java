/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaCasStateException;

public class MediaCasException
extends Exception {
    private MediaCasException(String string2) {
        super(string2);
    }

    static void throwExceptionIfNeeded(int n) throws MediaCasException {
        if (n == 0) {
            return;
        }
        if (n != 7) {
            if (n != 8) {
                if (n != 11) {
                    MediaCasStateException.throwExceptionIfNeeded(n);
                    return;
                }
                throw new DeniedByServerException(null);
            }
            throw new ResourceBusyException(null);
        }
        throw new NotProvisionedException(null);
    }

    public static final class DeniedByServerException
    extends MediaCasException {
        public DeniedByServerException(String string2) {
            super(string2);
        }
    }

    public static final class NotProvisionedException
    extends MediaCasException {
        public NotProvisionedException(String string2) {
            super(string2);
        }
    }

    public static final class ResourceBusyException
    extends MediaCasException {
        public ResourceBusyException(String string2) {
            super(string2);
        }
    }

    public static final class UnsupportedCasException
    extends MediaCasException {
        public UnsupportedCasException(String string2) {
            super(string2);
        }
    }

}

