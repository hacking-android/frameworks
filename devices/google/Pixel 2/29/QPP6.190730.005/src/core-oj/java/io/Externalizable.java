/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public interface Externalizable
extends Serializable {
    public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException;

    public void writeExternal(ObjectOutput var1) throws IOException;
}

