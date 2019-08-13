/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.Entity;
import java.util.Iterator;

public interface EntityIterator
extends Iterator<Entity> {
    public void close();

    public void reset();
}

