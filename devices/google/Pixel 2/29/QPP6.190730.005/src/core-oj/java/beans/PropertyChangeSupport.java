/*
 * Decompiled with CFR 0.145.
 */
package java.beans;

import java.beans.ChangeListenerMap;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PropertyChangeSupport
implements Serializable {
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("children", Hashtable.class), new ObjectStreamField("source", Object.class), new ObjectStreamField("propertyChangeSupportSerializedDataVersion", Integer.TYPE)};
    static final long serialVersionUID = 6401253773779951803L;
    private PropertyChangeListenerMap map = new PropertyChangeListenerMap();
    private Object source;

    public PropertyChangeSupport(Object object) {
        if (object != null) {
            this.source = object;
            return;
        }
        throw new NullPointerException();
    }

    private static void fire(PropertyChangeListener[] arrpropertyChangeListener, PropertyChangeEvent propertyChangeEvent) {
        if (arrpropertyChangeListener != null) {
            int n = arrpropertyChangeListener.length;
            for (int i = 0; i < n; ++i) {
                arrpropertyChangeListener[i].propertyChange(propertyChangeEvent);
            }
        }
    }

    private void readObject(ObjectInputStream object) throws ClassNotFoundException, IOException {
        Object object2;
        this.map = new PropertyChangeListenerMap();
        ObjectInputStream.GetField object22 = ((ObjectInputStream)((Object)object)).readFields();
        Hashtable hashtable = (Hashtable)object22.get("children", null);
        this.source = object22.get("source", null);
        object22.get("propertyChangeSupportSerializedDataVersion", 2);
        while ((object2 = ((ObjectInputStream)((Object)object)).readObject()) != null) {
            this.map.add(null, (PropertyChangeListener)object2);
        }
        if (hashtable != null) {
            for (Map.Entry entry : hashtable.entrySet()) {
                for (PropertyChangeListener propertyChangeListener : ((PropertyChangeSupport)entry.getValue()).getPropertyChangeListeners()) {
                    this.map.add((String)entry.getKey(), propertyChangeListener);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Hashtable<String, PropertyChangeSupport> hashtable;
        Object object2 = null;
        PropertyChangeListener[] arrpropertyChangeListener = null;
        PropertyChangeListenerMap propertyChangeListenerMap = this.map;
        synchronized (propertyChangeListenerMap) {
            for (Map.Entry<String, L[]> entry : this.map.getEntries()) {
                String string = entry.getKey();
                if (string == null) {
                    arrpropertyChangeListener = (PropertyChangeListener[])entry.getValue();
                    continue;
                }
                hashtable = object2;
                if (object2 == null) {
                    hashtable = new Hashtable<String, PropertyChangeSupport>();
                }
                object2 = new PropertyChangeSupport(this.source);
                ((PropertyChangeSupport)object2).map.set(null, (EventListener[])((PropertyChangeListener[])entry.getValue()));
                hashtable.put(string, (PropertyChangeSupport)object2);
                object2 = hashtable;
            }
        }
        hashtable = objectOutputStream.putFields();
        ((ObjectOutputStream.PutField)((Object)hashtable)).put("children", object2);
        ((ObjectOutputStream.PutField)((Object)hashtable)).put("source", this.source);
        ((ObjectOutputStream.PutField)((Object)hashtable)).put("propertyChangeSupportSerializedDataVersion", 2);
        objectOutputStream.writeFields();
        if (arrpropertyChangeListener != null) {
            for (Object object2 : arrpropertyChangeListener) {
                if (!(object2 instanceof Serializable)) continue;
                objectOutputStream.writeObject(object2);
            }
        }
        objectOutputStream.writeObject(null);
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        if (propertyChangeListener == null) {
            return;
        }
        if (propertyChangeListener instanceof PropertyChangeListenerProxy) {
            propertyChangeListener = (PropertyChangeListenerProxy)propertyChangeListener;
            this.addPropertyChangeListener(((PropertyChangeListenerProxy)propertyChangeListener).getPropertyName(), (PropertyChangeListener)((EventListenerProxy)((Object)propertyChangeListener)).getListener());
        } else {
            this.map.add(null, propertyChangeListener);
        }
    }

    public void addPropertyChangeListener(String string, PropertyChangeListener propertyChangeListener) {
        if (propertyChangeListener != null && string != null) {
            if ((propertyChangeListener = this.map.extract(propertyChangeListener)) != null) {
                this.map.add(string, propertyChangeListener);
            }
            return;
        }
    }

    public void fireIndexedPropertyChange(String string, int n, int n2, int n3) {
        if (n2 != n3) {
            this.fireIndexedPropertyChange(string, n, (Object)n2, (Object)n3);
        }
    }

    public void fireIndexedPropertyChange(String string, int n, Object object, Object object2) {
        if (object == null || object2 == null || !object.equals(object2)) {
            this.firePropertyChange(new IndexedPropertyChangeEvent(this.source, string, object, object2, n));
        }
    }

    public void fireIndexedPropertyChange(String string, int n, boolean bl, boolean bl2) {
        if (bl != bl2) {
            this.fireIndexedPropertyChange(string, n, (Object)bl, (Object)bl2);
        }
    }

    public void firePropertyChange(PropertyChangeEvent propertyChangeEvent) {
        PropertyChangeListener[] arrpropertyChangeListener = propertyChangeEvent.getOldValue();
        PropertyChangeListener[] arrpropertyChangeListener2 = propertyChangeEvent.getNewValue();
        if (arrpropertyChangeListener == null || arrpropertyChangeListener2 == null || !arrpropertyChangeListener.equals(arrpropertyChangeListener2)) {
            String string = propertyChangeEvent.getPropertyName();
            arrpropertyChangeListener2 = this.map;
            arrpropertyChangeListener = null;
            arrpropertyChangeListener2 = (PropertyChangeListener[])arrpropertyChangeListener2.get(null);
            if (string != null) {
                arrpropertyChangeListener = (PropertyChangeListener[])this.map.get(string);
            }
            PropertyChangeSupport.fire(arrpropertyChangeListener2, propertyChangeEvent);
            PropertyChangeSupport.fire(arrpropertyChangeListener, propertyChangeEvent);
        }
    }

    public void firePropertyChange(String string, int n, int n2) {
        if (n != n2) {
            this.firePropertyChange(string, (Object)n, (Object)n2);
        }
    }

    public void firePropertyChange(String string, Object object, Object object2) {
        if (object == null || object2 == null || !object.equals(object2)) {
            this.firePropertyChange(new PropertyChangeEvent(this.source, string, object, object2));
        }
    }

    public void firePropertyChange(String string, boolean bl, boolean bl2) {
        if (bl != bl2) {
            this.firePropertyChange(string, (Object)bl, (Object)bl2);
        }
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return (PropertyChangeListener[])this.map.getListeners();
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String string) {
        return (PropertyChangeListener[])this.map.getListeners(string);
    }

    public boolean hasListeners(String string) {
        return this.map.hasListeners(string);
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        if (propertyChangeListener == null) {
            return;
        }
        if (propertyChangeListener instanceof PropertyChangeListenerProxy) {
            propertyChangeListener = (PropertyChangeListenerProxy)propertyChangeListener;
            this.removePropertyChangeListener(((PropertyChangeListenerProxy)propertyChangeListener).getPropertyName(), (PropertyChangeListener)((EventListenerProxy)((Object)propertyChangeListener)).getListener());
        } else {
            this.map.remove(null, propertyChangeListener);
        }
    }

    public void removePropertyChangeListener(String string, PropertyChangeListener propertyChangeListener) {
        if (propertyChangeListener != null && string != null) {
            if ((propertyChangeListener = this.map.extract(propertyChangeListener)) != null) {
                this.map.remove(string, propertyChangeListener);
            }
            return;
        }
    }

    private static final class PropertyChangeListenerMap
    extends ChangeListenerMap<PropertyChangeListener> {
        private static final PropertyChangeListener[] EMPTY = new PropertyChangeListener[0];

        private PropertyChangeListenerMap() {
        }

        @Override
        public final PropertyChangeListener extract(PropertyChangeListener propertyChangeListener) {
            while (propertyChangeListener instanceof PropertyChangeListenerProxy) {
                propertyChangeListener = (PropertyChangeListener)((PropertyChangeListenerProxy)propertyChangeListener).getListener();
            }
            return propertyChangeListener;
        }

        protected PropertyChangeListener[] newArray(int n) {
            PropertyChangeListener[] arrpropertyChangeListener = n > 0 ? new PropertyChangeListener[n] : EMPTY;
            return arrpropertyChangeListener;
        }

        @Override
        protected PropertyChangeListener newProxy(String string, PropertyChangeListener propertyChangeListener) {
            return new PropertyChangeListenerProxy(string, propertyChangeListener);
        }
    }

}

