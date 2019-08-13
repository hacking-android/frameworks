/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Base64;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import java.util.prefs.XmlSupport;

public abstract class AbstractPreferences
extends Preferences {
    private static final AbstractPreferences[] EMPTY_ABSTRACT_PREFS_ARRAY;
    private static final String[] EMPTY_STRING_ARRAY;
    private static Thread eventDispatchThread;
    private static final List<EventObject> eventQueue;
    private final String absolutePath;
    private Map<String, AbstractPreferences> kidCache;
    protected final Object lock;
    private final String name;
    protected boolean newNode;
    private final ArrayList<NodeChangeListener> nodeListeners;
    final AbstractPreferences parent;
    private final ArrayList<PreferenceChangeListener> prefListeners;
    private boolean removed;
    private final AbstractPreferences root;

    static {
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_ABSTRACT_PREFS_ARRAY = new AbstractPreferences[0];
        eventQueue = new LinkedList<EventObject>();
        eventDispatchThread = null;
    }

    protected AbstractPreferences(AbstractPreferences object, String string) {
        block8 : {
            block9 : {
                block7 : {
                    CharSequence charSequence;
                    block5 : {
                        block6 : {
                            this.newNode = false;
                            this.kidCache = new HashMap<String, AbstractPreferences>();
                            this.removed = false;
                            this.prefListeners = new ArrayList();
                            this.nodeListeners = new ArrayList();
                            this.lock = new Object();
                            if (object != null) break block5;
                            if (!string.equals("")) break block6;
                            this.absolutePath = "/";
                            this.root = this;
                            break block7;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Root name '");
                        ((StringBuilder)object).append(string);
                        ((StringBuilder)object).append("' must be \"\"");
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    if (string.indexOf(47) != -1) break block8;
                    if (string.equals("")) break block9;
                    this.root = ((AbstractPreferences)object).root;
                    if (object == this.root) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("/");
                        ((StringBuilder)charSequence).append(string);
                        charSequence = ((StringBuilder)charSequence).toString();
                    } else {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(((AbstractPreferences)object).absolutePath());
                        ((StringBuilder)charSequence).append("/");
                        ((StringBuilder)charSequence).append(string);
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    this.absolutePath = charSequence;
                }
                this.name = string;
                this.parent = object;
                return;
            }
            throw new IllegalArgumentException("Illegal name: empty string");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Name '");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("' contains '/'");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    static /* synthetic */ List access$100() {
        return eventQueue;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void enqueueNodeAddedEvent(Preferences preferences) {
        if (this.nodeListeners.isEmpty()) return;
        List<EventObject> list = eventQueue;
        synchronized (list) {
            List<EventObject> list2 = eventQueue;
            NodeAddedEvent nodeAddedEvent = new NodeAddedEvent(this, preferences);
            list2.add(nodeAddedEvent);
            eventQueue.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void enqueueNodeRemovedEvent(Preferences preferences) {
        if (this.nodeListeners.isEmpty()) return;
        List<EventObject> list = eventQueue;
        synchronized (list) {
            List<EventObject> list2 = eventQueue;
            NodeRemovedEvent nodeRemovedEvent = new NodeRemovedEvent(this, preferences);
            list2.add(nodeRemovedEvent);
            eventQueue.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void enqueuePreferenceChangeEvent(String string, String string2) {
        if (this.prefListeners.isEmpty()) return;
        List<EventObject> list = eventQueue;
        synchronized (list) {
            List<EventObject> list2 = eventQueue;
            PreferenceChangeEvent preferenceChangeEvent = new PreferenceChangeEvent(this, string, string2);
            list2.add(preferenceChangeEvent);
            eventQueue.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void flush2() throws BackingStoreException {
        AbstractPreferences[] arrabstractPreferences;
        Object object = this.lock;
        synchronized (object) {
            this.flushSpi();
            if (this.removed) {
                return;
            }
            arrabstractPreferences = this.cachedChildren();
        }
        int n = 0;
        while (n < arrabstractPreferences.length) {
            arrabstractPreferences[n].flush2();
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Preferences node(StringTokenizer object) {
        String string = ((StringTokenizer)object).nextToken();
        if (string.equals("/")) {
            throw new IllegalArgumentException("Consecutive slashes in path");
        }
        Object object2 = this.lock;
        synchronized (object2) {
            AbstractPreferences abstractPreferences = this.kidCache.get(string);
            Object object3 = abstractPreferences;
            if (abstractPreferences == null) {
                if (string.length() > 80) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Node name ");
                    ((StringBuilder)object3).append(string);
                    ((StringBuilder)object3).append(" too long");
                    object = new IllegalArgumentException(((StringBuilder)object3).toString());
                    throw object;
                }
                object3 = this.childSpi(string);
                if (((AbstractPreferences)object3).newNode) {
                    this.enqueueNodeAddedEvent((Preferences)object3);
                }
                this.kidCache.put(string, (AbstractPreferences)object3);
            }
            if (!((StringTokenizer)object).hasMoreTokens()) {
                return object3;
            }
            ((StringTokenizer)object).nextToken();
            if (((StringTokenizer)object).hasMoreTokens()) {
                return AbstractPreferences.super.node((StringTokenizer)object);
            }
            object = new IllegalArgumentException("Path ends with slash");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean nodeExists(StringTokenizer object) throws BackingStoreException {
        String string = ((StringTokenizer)object).nextToken();
        if (string.equals("/")) {
            throw new IllegalArgumentException("Consecutive slashes in path");
        }
        Object object2 = this.lock;
        synchronized (object2) {
            AbstractPreferences abstractPreferences;
            AbstractPreferences abstractPreferences2 = abstractPreferences = this.kidCache.get(string);
            if (abstractPreferences == null) {
                abstractPreferences2 = this.getChild(string);
            }
            if (abstractPreferences2 == null) {
                return false;
            }
            if (!((StringTokenizer)object).hasMoreTokens()) {
                return true;
            }
            ((StringTokenizer)object).nextToken();
            if (((StringTokenizer)object).hasMoreTokens()) {
                return abstractPreferences2.nodeExists((StringTokenizer)object);
            }
            object = new IllegalArgumentException("Path ends with slash");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeNode2() throws BackingStoreException {
        Object object = this.lock;
        synchronized (object) {
            if (this.removed) {
                IllegalStateException illegalStateException = new IllegalStateException("Node already removed.");
                throw illegalStateException;
            }
            Object object2 = this.childrenNamesSpi();
            for (int i = 0; i < ((String[])object2).length; ++i) {
                if (this.kidCache.containsKey(object2[i])) continue;
                this.kidCache.put((String)object2[i], this.childSpi((String)object2[i]));
            }
            object2 = this.kidCache.values().iterator();
            do {
                boolean bl;
                if (!(bl = object2.hasNext())) {
                    this.removeNodeSpi();
                    this.removed = true;
                    this.parent.enqueueNodeRemovedEvent(this);
                    return;
                }
                try {
                    ((AbstractPreferences)object2.next()).removeNode2();
                    object2.remove();
                }
                catch (BackingStoreException backingStoreException) {
                }
            } while (true);
        }
    }

    private static void startEventDispatchThreadIfNecessary() {
        synchronized (AbstractPreferences.class) {
            if (eventDispatchThread == null) {
                EventDispatchThread eventDispatchThread = new EventDispatchThread();
                AbstractPreferences.eventDispatchThread = eventDispatchThread;
                AbstractPreferences.eventDispatchThread.setDaemon(true);
                AbstractPreferences.eventDispatchThread.start();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sync2() throws BackingStoreException {
        AbstractPreferences[] arrabstractPreferences;
        Object object = this.lock;
        synchronized (object) {
            if (this.removed) {
                IllegalStateException illegalStateException = new IllegalStateException("Node has been removed");
                throw illegalStateException;
            }
            this.syncSpi();
            arrabstractPreferences = this.cachedChildren();
        }
        int n = 0;
        while (n < arrabstractPreferences.length) {
            arrabstractPreferences[n].sync2();
            ++n;
        }
        return;
    }

    @Override
    public String absolutePath() {
        return this.absolutePath;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addNodeChangeListener(NodeChangeListener object) {
        if (object == null) {
            throw new NullPointerException("Change listener is null.");
        }
        Object object2 = this.lock;
        synchronized (object2) {
            if (!this.removed) {
                this.nodeListeners.add((NodeChangeListener)object);
                // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : var2_2
                AbstractPreferences.startEventDispatchThreadIfNecessary();
                return;
            }
            object = new IllegalStateException("Node has been removed.");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addPreferenceChangeListener(PreferenceChangeListener object) {
        if (object == null) {
            throw new NullPointerException("Change listener is null.");
        }
        Object object2 = this.lock;
        synchronized (object2) {
            if (!this.removed) {
                this.prefListeners.add((PreferenceChangeListener)object);
                // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : var2_2
                AbstractPreferences.startEventDispatchThreadIfNecessary();
                return;
            }
            object = new IllegalStateException("Node has been removed.");
            throw object;
        }
    }

    protected final AbstractPreferences[] cachedChildren() {
        return this.kidCache.values().toArray(EMPTY_ABSTRACT_PREFS_ARRAY);
    }

    protected abstract AbstractPreferences childSpi(String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String[] childrenNames() throws BackingStoreException {
        Object object = this.lock;
        synchronized (object) {
            if (this.removed) {
                IllegalStateException illegalStateException = new IllegalStateException("Node has been removed.");
                throw illegalStateException;
            }
            String[] arrstring = new TreeSet(this.kidCache.keySet());
            String[] arrstring2 = this.childrenNamesSpi();
            int n = arrstring2.length;
            int n2 = 0;
            while (n2 < n) {
                arrstring.add((String)arrstring2[n2]);
                ++n2;
            }
            return arrstring.toArray(EMPTY_STRING_ARRAY);
        }
    }

    protected abstract String[] childrenNamesSpi() throws BackingStoreException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void clear() throws BackingStoreException {
        Object object = this.lock;
        synchronized (object) {
            String[] arrstring = this.keys();
            int n = 0;
            while (n < arrstring.length) {
                this.remove(arrstring[n]);
                ++n;
            }
            return;
        }
    }

    @Override
    public void exportNode(OutputStream outputStream) throws IOException, BackingStoreException {
        XmlSupport.export(outputStream, this, false);
    }

    @Override
    public void exportSubtree(OutputStream outputStream) throws IOException, BackingStoreException {
        XmlSupport.export(outputStream, this, true);
    }

    @Override
    public void flush() throws BackingStoreException {
        this.flush2();
    }

    protected abstract void flushSpi() throws BackingStoreException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String get(String object, String string) {
        if (object == null) {
            throw new NullPointerException("Null key");
        }
        Object object2 = this.lock;
        synchronized (object2) {
            boolean bl = this.removed;
            if (bl) {
                object = new IllegalStateException("Node has been removed.");
                throw object;
            }
            Object var5_6 = null;
            try {
                object = this.getSpi((String)object);
            }
            catch (Exception exception) {
                object = var5_6;
            }
            if (object != null) return object;
            return string;
        }
    }

    @Override
    public boolean getBoolean(String string, boolean bl) {
        boolean bl2 = bl;
        string = this.get(string, null);
        bl = bl2;
        if (string != null) {
            if (string.equalsIgnoreCase("true")) {
                bl = true;
            } else {
                bl = bl2;
                if (string.equalsIgnoreCase("false")) {
                    bl = false;
                }
            }
        }
        return bl;
    }

    @Override
    public byte[] getByteArray(String arrby, byte[] arrby2) {
        block2 : {
            String string = this.get((String)arrby, null);
            arrby = arrby2;
            if (string == null) break block2;
            try {
                arrby = Base64.base64ToByteArray(string);
            }
            catch (RuntimeException runtimeException) {
                arrby = arrby2;
            }
        }
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected AbstractPreferences getChild(String object) throws BackingStoreException {
        Object object2 = this.lock;
        synchronized (object2) {
            String[] arrstring = this.childrenNames();
            int n = 0;
            while (n < arrstring.length) {
                if (arrstring[n].equals(object)) {
                    return this.childSpi(arrstring[n]);
                }
                ++n;
            }
            return null;
        }
    }

    @Override
    public double getDouble(String string, double d) {
        double d2;
        block3 : {
            string = this.get(string, null);
            d2 = d;
            if (string == null) break block3;
            try {
                d2 = Double.parseDouble(string);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        d = d2;
        return d;
    }

    @Override
    public float getFloat(String string, float f) {
        float f2;
        block3 : {
            string = this.get(string, null);
            f2 = f;
            if (string == null) break block3;
            try {
                f2 = Float.parseFloat(string);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        f = f2;
        return f;
    }

    @Override
    public int getInt(String string, int n) {
        int n2;
        block3 : {
            string = this.get(string, null);
            n2 = n;
            if (string == null) break block3;
            try {
                n2 = Integer.parseInt(string);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        n = n2;
        return n;
    }

    @Override
    public long getLong(String string, long l) {
        long l2;
        block3 : {
            string = this.get(string, null);
            l2 = l;
            if (string == null) break block3;
            try {
                l2 = Long.parseLong(string);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        l = l2;
        return l;
    }

    protected abstract String getSpi(String var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean isRemoved() {
        Object object = this.lock;
        synchronized (object) {
            return this.removed;
        }
    }

    @Override
    public boolean isUserNode() {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                boolean bl = AbstractPreferences.this.root == Preferences.userRoot();
                return bl;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String[] keys() throws BackingStoreException {
        Object object = this.lock;
        synchronized (object) {
            if (!this.removed) {
                return this.keysSpi();
            }
            IllegalStateException illegalStateException = new IllegalStateException("Node has been removed.");
            throw illegalStateException;
        }
    }

    protected abstract String[] keysSpi() throws BackingStoreException;

    @Override
    public String name() {
        return this.name;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Preferences node(String object) {
        Object object2 = this.lock;
        synchronized (object2) {
            if (this.removed) {
                object = new IllegalStateException("Node has been removed.");
                throw object;
            }
            if (((String)object).equals("")) {
                return this;
            }
            if (((String)object).equals("/")) {
                return this.root;
            }
            if (((String)object).charAt(0) == '/') return this.root.node(new StringTokenizer(((String)object).substring(1), "/", true));
            StringTokenizer stringTokenizer = new StringTokenizer((String)object, "/", true);
            return this.node(stringTokenizer);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean nodeExists(String object) throws BackingStoreException {
        Object object2 = this.lock;
        synchronized (object2) {
            boolean bl = ((String)object).equals("");
            boolean bl2 = false;
            if (bl) {
                if (this.removed) return bl2;
                return true;
            }
            if (this.removed) {
                object = new IllegalStateException("Node has been removed.");
                throw object;
            }
            if (((String)object).equals("/")) {
                return true;
            }
            if (((String)object).charAt(0) == '/') return this.root.nodeExists(new StringTokenizer(((String)object).substring(1), "/", true));
            StringTokenizer stringTokenizer = new StringTokenizer((String)object, "/", true);
            return this.nodeExists(stringTokenizer);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    NodeChangeListener[] nodeListeners() {
        Object object = this.lock;
        synchronized (object) {
            return this.nodeListeners.toArray(new NodeChangeListener[this.nodeListeners.size()]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Preferences parent() {
        Object object = this.lock;
        synchronized (object) {
            if (!this.removed) {
                return this.parent;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Node has been removed.");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    PreferenceChangeListener[] prefListeners() {
        Object object = this.lock;
        synchronized (object) {
            return this.prefListeners.toArray(new PreferenceChangeListener[this.prefListeners.size()]);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void put(String object, String charSequence) {
        if (object != null && charSequence != null) {
            if (((String)object).length() > 80) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Key too long: ");
                ((StringBuilder)charSequence).append((String)object);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            if (((String)charSequence).length() > 8192) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Value too long: ");
                ((StringBuilder)object).append((String)charSequence);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            Object object2 = this.lock;
            synchronized (object2) {
                if (!this.removed) {
                    this.putSpi((String)object, (String)charSequence);
                    this.enqueuePreferenceChangeEvent((String)object, (String)charSequence);
                    return;
                }
                object = new IllegalStateException("Node has been removed.");
                throw object;
            }
        }
        throw new NullPointerException();
    }

    @Override
    public void putBoolean(String string, boolean bl) {
        this.put(string, String.valueOf(bl));
    }

    @Override
    public void putByteArray(String string, byte[] arrby) {
        this.put(string, Base64.byteArrayToBase64(arrby));
    }

    @Override
    public void putDouble(String string, double d) {
        this.put(string, Double.toString(d));
    }

    @Override
    public void putFloat(String string, float f) {
        this.put(string, Float.toString(f));
    }

    @Override
    public void putInt(String string, int n) {
        this.put(string, Integer.toString(n));
    }

    @Override
    public void putLong(String string, long l) {
        this.put(string, Long.toString(l));
    }

    protected abstract void putSpi(String var1, String var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void remove(String object) {
        Objects.requireNonNull(object, "Specified key cannot be null");
        Object object2 = this.lock;
        synchronized (object2) {
            if (!this.removed) {
                this.removeSpi((String)object);
                this.enqueuePreferenceChangeEvent((String)object, null);
                return;
            }
            object = new IllegalStateException("Node has been removed.");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeNode() throws BackingStoreException {
        if (this != this.root) {
            Object object = this.parent.lock;
            synchronized (object) {
                this.removeNode2();
                this.parent.kidCache.remove(this.name);
                return;
            }
        }
        throw new UnsupportedOperationException("Can't remove the root!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removeNodeChangeListener(NodeChangeListener object) {
        Object object2 = this.lock;
        synchronized (object2) {
            if (this.removed) {
                object = new IllegalStateException("Node has been removed.");
                throw object;
            }
            if (this.nodeListeners.contains(object)) {
                this.nodeListeners.remove(object);
                return;
            }
            object = new IllegalArgumentException("Listener not registered.");
            throw object;
        }
    }

    protected abstract void removeNodeSpi() throws BackingStoreException;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void removePreferenceChangeListener(PreferenceChangeListener object) {
        Object object2 = this.lock;
        synchronized (object2) {
            if (this.removed) {
                object = new IllegalStateException("Node has been removed.");
                throw object;
            }
            if (this.prefListeners.contains(object)) {
                this.prefListeners.remove(object);
                return;
            }
            object = new IllegalArgumentException("Listener not registered.");
            throw object;
        }
    }

    protected abstract void removeSpi(String var1);

    @Override
    public void sync() throws BackingStoreException {
        this.sync2();
    }

    protected abstract void syncSpi() throws BackingStoreException;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.isUserNode() ? "User" : "System";
        stringBuilder.append(string);
        stringBuilder.append(" Preference Node: ");
        stringBuilder.append(this.absolutePath());
        return stringBuilder.toString();
    }

    private static class EventDispatchThread
    extends Thread {
        private EventDispatchThread() {
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            block5 : do lbl-1000: // 4 sources:
            {
                block11 : {
                    block10 : {
                        var1_1 = AbstractPreferences.access$100();
                        // MONITORENTER : var1_1
                        while (AbstractPreferences.access$100().isEmpty()) {
                            AbstractPreferences.access$100().wait();
                        }
                        var2_2 = (EventObject)AbstractPreferences.access$100().remove(0);
                        // MONITOREXIT : var1_1
                        var1_1 = (AbstractPreferences)var2_2.getSource();
                        if (!(var2_2 instanceof PreferenceChangeEvent)) break block10;
                        var2_2 = (PreferenceChangeEvent)var2_2;
                        var1_1 = var1_1.prefListeners();
                        var3_5 = 0;
                        do {
                            if (var3_5 >= var1_1.length) ** GOTO lbl-1000
                            var1_1[var3_5].preferenceChange((PreferenceChangeEvent)var2_2);
                            ++var3_5;
                        } while (true);
                    }
                    var2_2 = (NodeChangeEvent)var2_2;
                    var1_1 = var1_1.nodeListeners();
                    if (!(var2_2 instanceof NodeAddedEvent)) break block11;
                    var3_5 = 0;
                    do {
                        if (var3_5 >= var1_1.length) ** GOTO lbl-1000
                        var1_1[var3_5].childAdded((NodeChangeEvent)var2_2);
                        ++var3_5;
                    } while (true);
                }
                var3_5 = 0;
                do {
                    if (var3_5 >= var1_1.length) continue block5;
                    var1_1[var3_5].childRemoved((NodeChangeEvent)var2_2);
                    ++var3_5;
                } while (true);
                break;
            } while (true);
            {
                catch (Throwable var2_3) {
                    throw var2_3;
                }
                catch (InterruptedException var2_4) {}
                {
                    // MONITOREXIT : var1_1
                    return;
                }
            }
        }
    }

    private class NodeAddedEvent
    extends NodeChangeEvent {
        private static final long serialVersionUID = -6743557530157328528L;

        NodeAddedEvent(Preferences preferences, Preferences preferences2) {
            super(preferences, preferences2);
        }
    }

    private class NodeRemovedEvent
    extends NodeChangeEvent {
        private static final long serialVersionUID = 8735497392918824837L;

        NodeRemovedEvent(Preferences preferences, Preferences preferences2) {
            super(preferences, preferences2);
        }
    }

}

