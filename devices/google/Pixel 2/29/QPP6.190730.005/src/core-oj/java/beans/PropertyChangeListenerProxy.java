/*
 * Decompiled with CFR 0.145.
 */
package java.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListenerProxy;

public class PropertyChangeListenerProxy
extends EventListenerProxy<PropertyChangeListener>
implements PropertyChangeListener {
    private final String propertyName;

    public PropertyChangeListenerProxy(String string, PropertyChangeListener propertyChangeListener) {
        super(propertyChangeListener);
        this.propertyName = string;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        ((PropertyChangeListener)this.getListener()).propertyChange(propertyChangeEvent);
    }
}

