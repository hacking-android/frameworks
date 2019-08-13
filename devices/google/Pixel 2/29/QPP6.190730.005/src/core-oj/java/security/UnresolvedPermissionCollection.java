/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.UnresolvedPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

final class UnresolvedPermissionCollection
extends PermissionCollection
implements Serializable {
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("permissions", Hashtable.class)};
    private static final long serialVersionUID = -7176153071733132400L;
    private transient Map<String, List<UnresolvedPermission>> perms = new HashMap<String, List<UnresolvedPermission>>(11);

    private void readObject(ObjectInputStream iterator) throws IOException, ClassNotFoundException {
        iterator = ((ObjectInputStream)((Object)iterator)).readFields();
        iterator = (Hashtable)((ObjectInputStream.GetField)((Object)iterator)).get("permissions", null);
        this.perms = new HashMap<String, List<UnresolvedPermission>>(((Hashtable)((Object)iterator)).size() * 2);
        for (Map.Entry entry : ((Hashtable)((Object)iterator)).entrySet()) {
            Vector vector = (Vector)entry.getValue();
            ArrayList arrayList = new ArrayList(vector.size());
            arrayList.addAll(vector);
            this.perms.put((String)entry.getKey(), arrayList);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Hashtable hashtable = new Hashtable(this.perms.size() * 2);
        synchronized (this) {
            Iterator<Map.Entry<String, List<UnresolvedPermission>>> iterator = this.perms.entrySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    // MONITOREXIT [6, 8, 9] lbl6 : MonitorExitStatement: MONITOREXIT : this
                    objectOutputStream.putFields().put("permissions", hashtable);
                    objectOutputStream.writeFields();
                    return;
                }
                Map.Entry<String, List<UnresolvedPermission>> entry = iterator.next();
                List<UnresolvedPermission> list = entry.getValue();
                Vector<UnresolvedPermission> vector = new Vector<UnresolvedPermission>(list.size());
                synchronized (list) {
                    vector.addAll(list);
                }
                hashtable.put(entry.getKey(), vector);
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void add(Permission arrayList) {
        if (!(arrayList instanceof UnresolvedPermission)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid permission: ");
            stringBuilder.append(arrayList);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        UnresolvedPermission unresolvedPermission = (UnresolvedPermission)((Object)arrayList);
        synchronized (this) {
            List<UnresolvedPermission> list = this.perms.get(unresolvedPermission.getName());
            arrayList = list;
            if (list == null) {
                arrayList = new ArrayList<UnresolvedPermission>();
                this.perms.put(unresolvedPermission.getName(), arrayList);
            }
        }
        synchronized (arrayList) {
            arrayList.add(unresolvedPermission);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Enumeration<Permission> elements() {
        ArrayList<UnresolvedPermission> arrayList = new ArrayList<UnresolvedPermission>();
        synchronized (this) {
            Iterator<List<UnresolvedPermission>> iterator = this.perms.values().iterator();
            while (iterator.hasNext()) {
                List<UnresolvedPermission> list = iterator.next();
                synchronized (list) {
                    arrayList.addAll(list);
                }
            }
            return Collections.enumeration(arrayList);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<UnresolvedPermission> getUnresolvedPermissions(Permission object) {
        synchronized (this) {
            return this.perms.get(object.getClass().getName());
        }
    }

    @Override
    public boolean implies(Permission permission) {
        return false;
    }
}

