/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.ManagerFactoryParameters;

public class KeyStoreBuilderParameters
implements ManagerFactoryParameters {
    private final List<KeyStore.Builder> parameters;

    public KeyStoreBuilderParameters(KeyStore.Builder builder) {
        this.parameters = Collections.singletonList(Objects.requireNonNull(builder));
    }

    public KeyStoreBuilderParameters(List<KeyStore.Builder> list) {
        if (!list.isEmpty()) {
            this.parameters = Collections.unmodifiableList(new ArrayList<KeyStore.Builder>(list));
            return;
        }
        throw new IllegalArgumentException();
    }

    public List<KeyStore.Builder> getParameters() {
        return this.parameters;
    }
}

