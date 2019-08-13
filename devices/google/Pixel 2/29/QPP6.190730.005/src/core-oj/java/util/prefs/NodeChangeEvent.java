/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EventObject;
import java.util.prefs.Preferences;

public class NodeChangeEvent
extends EventObject {
    private static final long serialVersionUID = 8068949086596572957L;
    private Preferences child;

    public NodeChangeEvent(Preferences preferences, Preferences preferences2) {
        super(preferences);
        this.child = preferences2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }

    public Preferences getChild() {
        return this.child;
    }

    public Preferences getParent() {
        return (Preferences)this.getSource();
    }
}

