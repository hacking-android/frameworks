/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.ICUBinary;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationDataReader;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.CollationTailoring;
import android.icu.impl.coll.SharedObject;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class CollationRoot {
    private static final RuntimeException exception;
    private static final CollationTailoring rootSingleton;

    static {
        CollationTailoring collationTailoring = null;
        CollationTailoring collationTailoring2 = null;
        MissingResourceException missingResourceException = null;
        try {
            ByteBuffer byteBuffer = ICUBinary.getRequiredData("coll/ucadata.icu");
            CollationTailoring collationTailoring3 = new CollationTailoring(null);
            CollationDataReader.read(null, byteBuffer, collationTailoring3);
            collationTailoring2 = collationTailoring3;
        }
        catch (RuntimeException runtimeException) {
            collationTailoring2 = collationTailoring;
        }
        catch (IOException iOException) {
            missingResourceException = new MissingResourceException("IOException while reading CLDR root data", "CollationRoot", "data/icudt63b/coll/ucadata.icu");
        }
        rootSingleton = collationTailoring2;
        exception = missingResourceException;
    }

    public static final CollationData getData() {
        return CollationRoot.getRoot().data;
    }

    public static final CollationTailoring getRoot() {
        RuntimeException runtimeException = exception;
        if (runtimeException == null) {
            return rootSingleton;
        }
        throw runtimeException;
    }

    static final CollationSettings getSettings() {
        return CollationRoot.getRoot().settings.readOnly();
    }
}

