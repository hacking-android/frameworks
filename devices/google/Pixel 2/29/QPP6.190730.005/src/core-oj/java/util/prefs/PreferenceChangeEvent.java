/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EventObject;
import java.util.prefs.Preferences;

public class PreferenceChangeEvent
extends EventObject {
    private static final long serialVersionUID = 793724513368024975L;
    private String key;
    private String newValue;

    public PreferenceChangeEvent(Preferences preferences, String string, String string2) {
        super(preferences);
        this.key = string;
        this.newValue = string2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }

    public String getKey() {
        return this.key;
    }

    public String getNewValue() {
        return this.newValue;
    }

    public Preferences getNode() {
        return (Preferences)this.getSource();
    }
}

