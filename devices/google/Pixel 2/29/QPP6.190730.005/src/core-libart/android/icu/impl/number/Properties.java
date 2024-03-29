/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.impl.number.DecimalFormatProperties;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Properties
implements Serializable {
    private static final long serialVersionUID = 4095518955889349243L;
    private transient DecimalFormatProperties instance;

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        if (this.instance == null) {
            this.instance = new DecimalFormatProperties();
        }
        this.instance.readObjectImpl(objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.instance == null) {
            this.instance = new DecimalFormatProperties();
        }
        this.instance.writeObjectImpl(objectOutputStream);
    }

    public DecimalFormatProperties getInstance() {
        return this.instance;
    }
}

