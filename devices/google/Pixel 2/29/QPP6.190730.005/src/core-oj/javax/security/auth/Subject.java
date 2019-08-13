/*
 * Decompiled with CFR 0.145.
 */
package javax.security.auth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.DomainCombiner;
import java.security.Permission;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import javax.security.auth.AuthPermission;
import javax.security.auth.PrivateCredentialPermission;
import javax.security.auth.SubjectDomainCombiner;
import sun.security.util.ResourcesMgr;

public final class Subject
implements Serializable {
    private static final ProtectionDomain[] NULL_PD_ARRAY = new ProtectionDomain[0];
    private static final int PRINCIPAL_SET = 1;
    private static final int PRIV_CREDENTIAL_SET = 3;
    private static final int PUB_CREDENTIAL_SET = 2;
    private static final long serialVersionUID = -8308522755600156056L;
    Set<Principal> principals;
    transient Set<Object> privCredentials;
    transient Set<Object> pubCredentials;
    private volatile boolean readOnly = false;

    public Subject() {
        this.principals = Collections.synchronizedSet(new SecureSet(this, 1));
        this.pubCredentials = Collections.synchronizedSet(new SecureSet(this, 2));
        this.privCredentials = Collections.synchronizedSet(new SecureSet(this, 3));
    }

    public Subject(boolean bl, Set<? extends Principal> set, Set<?> set2, Set<?> set3) {
        if (set != null && set2 != null && set3 != null) {
            this.principals = Collections.synchronizedSet(new SecureSet<Principal>(this, 1, set));
            this.pubCredentials = Collections.synchronizedSet(new SecureSet(this, 2, set2));
            this.privCredentials = Collections.synchronizedSet(new SecureSet(this, 3, set3));
            this.readOnly = bl;
            return;
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.input.s."));
    }

    private static AccessControlContext createContext(final Subject subject, final AccessControlContext accessControlContext) {
        return AccessController.doPrivileged(new PrivilegedAction<AccessControlContext>(){

            @Override
            public AccessControlContext run() {
                Subject subject2 = subject;
                if (subject2 == null) {
                    return new AccessControlContext(accessControlContext, null);
                }
                return new AccessControlContext(accessControlContext, new SubjectDomainCombiner(subject2));
            }
        });
    }

    public static <T> T doAs(Subject subject, PrivilegedAction<T> privilegedAction) {
        Object object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
        }
        if (privilegedAction != null) {
            object = AccessController.getContext();
            return AccessController.doPrivileged(privilegedAction, Subject.createContext(subject, (AccessControlContext)object));
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
    }

    public static <T> T doAs(Subject subject, PrivilegedExceptionAction<T> privilegedExceptionAction) throws PrivilegedActionException {
        Object object = System.getSecurityManager();
        if (object != null) {
            ((SecurityManager)object).checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
        }
        if (privilegedExceptionAction != null) {
            object = AccessController.getContext();
            return AccessController.doPrivileged(privilegedExceptionAction, Subject.createContext(subject, (AccessControlContext)object));
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
    }

    public static <T> T doAsPrivileged(Subject subject, PrivilegedAction<T> privilegedAction, AccessControlContext accessControlContext) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
        }
        if (privilegedAction != null) {
            if (accessControlContext == null) {
                accessControlContext = new AccessControlContext(NULL_PD_ARRAY);
            }
            return AccessController.doPrivileged(privilegedAction, Subject.createContext(subject, accessControlContext));
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
    }

    public static <T> T doAsPrivileged(Subject subject, PrivilegedExceptionAction<T> privilegedExceptionAction, AccessControlContext accessControlContext) throws PrivilegedActionException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
        }
        if (privilegedExceptionAction != null) {
            if (accessControlContext == null) {
                accessControlContext = new AccessControlContext(NULL_PD_ARRAY);
            }
            return AccessController.doPrivileged(privilegedExceptionAction, Subject.createContext(subject, accessControlContext));
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
    }

    private int getCredHashCode(Object object) {
        try {
            int n = object.hashCode();
            return n;
        }
        catch (IllegalStateException illegalStateException) {
            return object.getClass().toString().hashCode();
        }
    }

    public static Subject getSubject(final AccessControlContext accessControlContext) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.GET_SUBJECT_PERMISSION);
        }
        if (accessControlContext != null) {
            return AccessController.doPrivileged(new PrivilegedAction<Subject>(){

                @Override
                public Subject run() {
                    DomainCombiner domainCombiner = accessControlContext.getDomainCombiner();
                    if (!(domainCombiner instanceof SubjectDomainCombiner)) {
                        return null;
                    }
                    return ((SubjectDomainCombiner)domainCombiner).getSubject();
                }
            });
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.AccessControlContext.provided"));
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object = ((ObjectInputStream)object).readFields();
        this.readOnly = ((ObjectInputStream.GetField)object).get("readOnly", false);
        if ((object = (Set)((ObjectInputStream.GetField)object).get("principals", null)) != null) {
            try {
                SecureSet secureSet = new SecureSet(this, 1, object);
                this.principals = Collections.synchronizedSet(secureSet);
            }
            catch (NullPointerException nullPointerException) {
                this.principals = Collections.synchronizedSet(new SecureSet(this, 1));
            }
            this.pubCredentials = Collections.synchronizedSet(new SecureSet(this, 2));
            this.privCredentials = Collections.synchronizedSet(new SecureSet(this, 3));
            return;
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.input.s."));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Set<Principal> set = this.principals;
        synchronized (set) {
            objectOutputStream.defaultWriteObject();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof Subject)) return false;
        object = (Subject)object;
        Set<Object> set = ((Subject)object).principals;
        // MONITORENTER : set
        HashSet<Object> hashSet = new HashSet<Object>(((Subject)object).principals);
        // MONITOREXIT : set
        if (!this.principals.equals(hashSet)) {
            return false;
        }
        set = ((Subject)object).pubCredentials;
        // MONITORENTER : set
        hashSet = new HashSet<Object>(((Subject)object).pubCredentials);
        // MONITOREXIT : set
        if (!this.pubCredentials.equals(hashSet)) {
            return false;
        }
        set = ((Subject)object).privCredentials;
        // MONITORENTER : set
        hashSet = new HashSet<Object>(((Subject)object).privCredentials);
        // MONITOREXIT : set
        if (this.privCredentials.equals(hashSet)) return true;
        return false;
    }

    public Set<Principal> getPrincipals() {
        return this.principals;
    }

    public <T extends Principal> Set<T> getPrincipals(Class<T> class_) {
        if (class_ != null) {
            return new ClassSet<T>(1, class_);
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.Class.provided"));
    }

    public Set<Object> getPrivateCredentials() {
        return this.privCredentials;
    }

    public <T> Set<T> getPrivateCredentials(Class<T> class_) {
        if (class_ != null) {
            return new ClassSet<T>(3, class_);
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.Class.provided"));
    }

    public Set<Object> getPublicCredentials() {
        return this.pubCredentials;
    }

    public <T> Set<T> getPublicCredentials(Class<T> class_) {
        if (class_ != null) {
            return new ClassSet<T>(2, class_);
        }
        throw new NullPointerException(ResourcesMgr.getString("invalid.null.Class.provided"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int hashCode() {
        Iterator<Object> iterator;
        int n = 0;
        Set<Object> set = this.principals;
        synchronized (set) {
            iterator = this.principals.iterator();
            while (iterator.hasNext()) {
                n ^= iterator.next().hashCode();
            }
        }
        set = this.pubCredentials;
        synchronized (set) {
            iterator = this.pubCredentials.iterator();
            while (iterator.hasNext()) {
                n ^= this.getCredHashCode(iterator.next());
            }
            return n;
        }
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.SET_READ_ONLY_PERMISSION);
        }
        this.readOnly = true;
    }

    public String toString() {
        return this.toString(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String toString(boolean bl) {
        Object object;
        String string = ResourcesMgr.getString("Subject.");
        Object object2 = "";
        Object object3 = this.principals;
        synchronized (object3) {
            for (Iterator<Object> iterator : this.principals) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(ResourcesMgr.getString(".Principal."));
                ((StringBuilder)object).append(iterator.toString());
                ((StringBuilder)object).append(ResourcesMgr.getString("NEWLINE"));
                object2 = ((StringBuilder)object).toString();
            }
        }
        object3 = this.pubCredentials;
        synchronized (object3) {
            for (Iterator<Object> iterator : this.pubCredentials) {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object2);
                ((StringBuilder)object).append(ResourcesMgr.getString(".Public.Credential."));
                ((StringBuilder)object).append(iterator.toString());
                ((StringBuilder)object).append(ResourcesMgr.getString("NEWLINE"));
                object2 = ((StringBuilder)object).toString();
            }
        }
        object3 = object2;
        if (bl) {
            Set<Object> set = this.privCredentials;
            synchronized (set) {
                Iterator<Object> iterator;
                iterator = this.privCredentials.iterator();
                do {
                    bl = iterator.hasNext();
                    object3 = object2;
                    if (!bl) break;
                    try {
                        object = iterator.next();
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append((String)object2);
                        ((StringBuilder)object3).append(ResourcesMgr.getString(".Private.Credential."));
                        ((StringBuilder)object3).append(object.toString());
                        ((StringBuilder)object3).append(ResourcesMgr.getString("NEWLINE"));
                        object2 = object3 = ((StringBuilder)object3).toString();
                    }
                    catch (SecurityException securityException) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append((String)object2);
                        ((StringBuilder)object3).append(ResourcesMgr.getString(".Private.Credential.inaccessible."));
                        object3 = ((StringBuilder)object3).toString();
                        break;
                    }
                } while (true);
            }
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append((String)object3);
        return ((StringBuilder)object2).toString();
    }

    static class AuthPermissionHolder {
        static final AuthPermission DO_AS_PERMISSION = new AuthPermission("doAs");
        static final AuthPermission DO_AS_PRIVILEGED_PERMISSION = new AuthPermission("doAsPrivileged");
        static final AuthPermission GET_SUBJECT_PERMISSION;
        static final AuthPermission MODIFY_PRINCIPALS_PERMISSION;
        static final AuthPermission MODIFY_PRIVATE_CREDENTIALS_PERMISSION;
        static final AuthPermission MODIFY_PUBLIC_CREDENTIALS_PERMISSION;
        static final AuthPermission SET_READ_ONLY_PERMISSION;

        static {
            SET_READ_ONLY_PERMISSION = new AuthPermission("setReadOnly");
            GET_SUBJECT_PERMISSION = new AuthPermission("getSubject");
            MODIFY_PRINCIPALS_PERMISSION = new AuthPermission("modifyPrincipals");
            MODIFY_PUBLIC_CREDENTIALS_PERMISSION = new AuthPermission("modifyPublicCredentials");
            MODIFY_PRIVATE_CREDENTIALS_PERMISSION = new AuthPermission("modifyPrivateCredentials");
        }

        AuthPermissionHolder() {
        }
    }

    private class ClassSet<T>
    extends AbstractSet<T> {
        private Class<T> c;
        private Set<T> set;
        private int which;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        ClassSet(int n, Class<T> class_) {
            this.which = n;
            this.c = class_;
            this.set = new HashSet<T>();
            if (n == 1) {
                Subject.this = ((Subject)Subject.this).principals;
                synchronized (Subject.this) {
                    this.populateSet();
                    return;
                }
            }
            if (n != 2) {
                Subject.this = ((Subject)Subject.this).privCredentials;
                synchronized (Subject.this) {
                    this.populateSet();
                    return;
                }
            }
            Subject.this = ((Subject)Subject.this).pubCredentials;
            synchronized (Subject.this) {
                this.populateSet();
                return;
            }
        }

        private void populateSet() {
            int n = this.which;
            final Iterator<Object> iterator = n != 1 ? (n != 2 ? Subject.this.privCredentials.iterator() : Subject.this.pubCredentials.iterator()) : Subject.this.principals.iterator();
            while (iterator.hasNext()) {
                Object object = this.which == 3 ? AccessController.doPrivileged(new PrivilegedAction<Object>(){

                    @Override
                    public Object run() {
                        return iterator.next();
                    }
                }) : iterator.next();
                if (!this.c.isAssignableFrom(object.getClass())) continue;
                if (this.which != 3) {
                    this.set.add(object);
                    continue;
                }
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    securityManager.checkPermission(new PrivateCredentialPermission(object.getClass().getName(), Subject.this.getPrincipals()));
                }
                this.set.add(object);
            }
        }

        @Override
        public boolean add(T t) {
            if (t.getClass().isAssignableFrom(this.c)) {
                return this.set.add(t);
            }
            throw new SecurityException(new MessageFormat(ResourcesMgr.getString("attempting.to.add.an.object.which.is.not.an.instance.of.class")).format(new Object[]{this.c.toString()}));
        }

        @Override
        public Iterator<T> iterator() {
            return this.set.iterator();
        }

        @Override
        public int size() {
            return this.set.size();
        }

    }

    private static class SecureSet<E>
    extends AbstractSet<E>
    implements Serializable {
        private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("this$0", Subject.class), new ObjectStreamField("elements", LinkedList.class), new ObjectStreamField("which", Integer.TYPE)};
        private static final long serialVersionUID = 7911754171111800359L;
        LinkedList<E> elements;
        Subject subject;
        private int which;

        SecureSet(Subject subject, int n) {
            this.subject = subject;
            this.which = n;
            this.elements = new LinkedList();
        }

        SecureSet(Subject subject, int n, Set<? extends E> set) {
            this.subject = subject;
            this.which = n;
            this.elements = new LinkedList<E>(set);
        }

        private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
            object = ((ObjectInputStream)object).readFields();
            this.subject = (Subject)((ObjectInputStream.GetField)object).get("this$0", null);
            this.which = ((ObjectInputStream.GetField)object).get("which", 0);
            this.elements = (object = (LinkedList)((ObjectInputStream.GetField)object).get("elements", null)).getClass() != LinkedList.class ? new LinkedList(object) : object;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object;
            if (this.which == 3) {
                object = this.iterator();
                while (object.hasNext()) {
                    object.next();
                }
            }
            object = objectOutputStream.putFields();
            ((ObjectOutputStream.PutField)object).put("this$0", this.subject);
            ((ObjectOutputStream.PutField)object).put("elements", this.elements);
            ((ObjectOutputStream.PutField)object).put("which", this.which);
            objectOutputStream.writeFields();
        }

        @Override
        public boolean add(E e) {
            if (!this.subject.isReadOnly()) {
                SecurityManager securityManager = System.getSecurityManager();
                if (securityManager != null) {
                    int n = this.which;
                    if (n != 1) {
                        if (n != 2) {
                            securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
                        } else {
                            securityManager.checkPermission(AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
                        }
                    } else {
                        securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
                    }
                }
                if (this.which != 1 || e instanceof Principal) {
                    if (!this.elements.contains(e)) {
                        return this.elements.add(e);
                    }
                    return false;
                }
                throw new SecurityException(ResourcesMgr.getString("attempting.to.add.an.object.which.is.not.an.instance.of.java.security.Principal.to.a.Subject.s.Principal.Set"));
            }
            throw new IllegalStateException(ResourcesMgr.getString("Subject.is.read.only"));
        }

        @Override
        public void clear() {
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (this.which != 3) {
                    iterator.next();
                } else {
                    AccessController.doPrivileged(new PrivilegedAction<E>(){

                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                iterator.remove();
            }
        }

        @Override
        public boolean contains(Object object) {
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                SecurityManager securityManager;
                if (this.which != 3) {
                    securityManager = iterator.next();
                } else {
                    securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkPermission(new PrivateCredentialPermission(object.getClass().getName(), this.subject.getPrincipals()));
                    }
                    securityManager = AccessController.doPrivileged(new PrivilegedAction<E>(){

                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                if (!(securityManager == null ? object == null : securityManager.equals(object))) continue;
                return true;
            }
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>(){
                ListIterator<E> i;
                {
                    this.i = elements.listIterator(0);
                }

                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }

                @Override
                public E next() {
                    if (which != 3) {
                        return this.i.next();
                    }
                    SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            PrivateCredentialPermission privateCredentialPermission = new PrivateCredentialPermission(elements.get(this.i.nextIndex()).getClass().getName(), subject.getPrincipals());
                            securityManager.checkPermission(privateCredentialPermission);
                        }
                        catch (SecurityException securityException) {
                            this.i.next();
                            throw securityException;
                        }
                    }
                    return this.i.next();
                }

                @Override
                public void remove() {
                    if (!subject.isReadOnly()) {
                        SecurityManager securityManager = System.getSecurityManager();
                        if (securityManager != null) {
                            int n = which;
                            if (n != 1) {
                                if (n != 2) {
                                    securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
                                } else {
                                    securityManager.checkPermission(AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
                                }
                            } else {
                                securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
                            }
                        }
                        this.i.remove();
                        return;
                    }
                    throw new IllegalStateException(ResourcesMgr.getString("Subject.is.read.only"));
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                Object object2 = this.which != 3 ? iterator.next() : AccessController.doPrivileged(new PrivilegedAction<E>(){

                    @Override
                    public E run() {
                        return iterator.next();
                    }
                });
                if (object2 == null) {
                    if (object != null) continue;
                    iterator.remove();
                    return true;
                }
                if (!object2.equals(object)) continue;
                iterator.remove();
                return true;
            }
            return false;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean removeAll(Collection<?> var1_1) {
            Objects.requireNonNull(var1_1);
            var2_2 = false;
            var3_3 = this.iterator();
            while (var3_3.hasNext() != false) {
                block2 : {
                    var4_4 = this.which != 3 ? var3_3.next() : AccessController.doPrivileged(new PrivilegedAction<E>(){

                        @Override
                        public E run() {
                            return var3_3.next();
                        }
                    });
                    var5_5 = var1_1.iterator();
                    do lbl-1000: // 3 sources:
                    {
                        var6_6 = var2_2;
                        if (!var5_5.hasNext()) break block2;
                        var7_7 = var5_5.next();
                        if (var4_4 != null) continue;
                        if (var7_7 != null) ** GOTO lbl-1000
                        var3_3.remove();
                        var6_6 = true;
                        break block2;
                    } while (!var4_4.equals(var7_7));
                    var3_3.remove();
                    var6_6 = true;
                }
                var2_2 = var6_6;
            }
            return var2_2;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public boolean retainAll(Collection<?> var1_1) {
            Objects.requireNonNull(var1_1);
            var2_2 = false;
            var3_3 = this.iterator();
            while (var3_3.hasNext() != false) {
                block2 : {
                    var4_4 = false;
                    var5_5 = this.which != 3 ? var3_3.next() : AccessController.doPrivileged(new PrivilegedAction<E>(){

                        @Override
                        public E run() {
                            return var3_3.next();
                        }
                    });
                    var6_6 = var1_1.iterator();
                    do lbl-1000: // 3 sources:
                    {
                        var7_7 = var4_4;
                        if (!var6_6.hasNext()) break block2;
                        var8_8 = var6_6.next();
                        if (var5_5 != null) continue;
                        if (var8_8 != null) ** GOTO lbl-1000
                        var7_7 = true;
                        break block2;
                    } while (!var5_5.equals(var8_8));
                    var7_7 = true;
                }
                if (var7_7) continue;
                var3_3.remove();
                var2_2 = true;
            }
            return var2_2;
        }

        @Override
        public int size() {
            return this.elements.size();
        }

    }

}

