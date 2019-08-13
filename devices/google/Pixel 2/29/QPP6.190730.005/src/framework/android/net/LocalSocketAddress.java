/*
 * Decompiled with CFR 0.145.
 */
package android.net;

public class LocalSocketAddress {
    private final String name;
    private final Namespace namespace;

    public LocalSocketAddress(String string2) {
        this(string2, Namespace.ABSTRACT);
    }

    public LocalSocketAddress(String string2, Namespace namespace) {
        this.name = string2;
        this.namespace = namespace;
    }

    public String getName() {
        return this.name;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public static enum Namespace {
        ABSTRACT(0),
        RESERVED(1),
        FILESYSTEM(2);
        
        private int id;

        private Namespace(int n2) {
            this.id = n2;
        }

        int getId() {
            return this.id;
        }
    }

}

