/*
 * Decompiled with CFR 0.145.
 */
package sun.security.jca;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.ProviderException;
import sun.security.util.Debug;
import sun.security.util.PropertyExpander;

final class ProviderConfig {
    private static final Class[] CL_STRING;
    private static final int MAX_LOAD_TRIES = 30;
    private static final String P11_SOL_ARG = "${java.home}/lib/security/sunpkcs11-solaris.cfg";
    private static final String P11_SOL_NAME = "sun.security.pkcs11.SunPKCS11";
    private static final Debug debug;
    private final String argument;
    private final String className;
    private boolean isLoading;
    private volatile Provider provider;
    private int tries;

    static {
        debug = Debug.getInstance("jca", "ProviderConfig");
        CL_STRING = new Class[]{String.class};
    }

    ProviderConfig(String string) {
        this(string, "");
    }

    ProviderConfig(String string, String string2) {
        if (string.equals(P11_SOL_NAME) && string2.equals(P11_SOL_ARG)) {
            this.checkSunPKCS11Solaris();
        }
        this.className = string;
        this.argument = ProviderConfig.expand(string2);
    }

    ProviderConfig(Provider provider) {
        this.className = provider.getClass().getName();
        this.argument = "";
        this.provider = provider;
    }

    private void checkSunPKCS11Solaris() {
        if (AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                if (!new File("/usr/lib/libpkcs11.so").exists()) {
                    return Boolean.FALSE;
                }
                if ("false".equalsIgnoreCase(System.getProperty("sun.security.pkcs11.enable-solaris"))) {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
        }) == Boolean.FALSE) {
            this.tries = 30;
        }
    }

    private void disableLoad() {
        this.tries = 30;
    }

    private Provider doLoadProvider() {
        return AccessController.doPrivileged(new PrivilegedAction<Provider>(){

            @Override
            public Provider run() {
                Debug debug;
                Serializable serializable;
                if (debug != null) {
                    debug = debug;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Loading provider: ");
                    ((StringBuilder)serializable).append(ProviderConfig.this);
                    debug.println(((StringBuilder)serializable).toString());
                }
                try {
                    serializable = ProviderConfig.this.initProvider(ProviderConfig.this.className, Object.class.getClassLoader());
                    return serializable;
                }
                catch (Exception exception) {
                    try {
                        Provider provider = ProviderConfig.this.initProvider(ProviderConfig.this.className, ClassLoader.getSystemClassLoader());
                        return provider;
                    }
                    catch (Exception exception2) {
                        Throwable throwable;
                        if (exception2 instanceof InvocationTargetException) {
                            throwable = ((InvocationTargetException)exception2).getCause();
                        }
                        if (debug != null) {
                            debug = debug;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Error loading provider ");
                            stringBuilder.append(ProviderConfig.this);
                            debug.println(stringBuilder.toString());
                            throwable.printStackTrace();
                        }
                        if (!(throwable instanceof ProviderException)) {
                            if (throwable instanceof UnsupportedOperationException) {
                                ProviderConfig.this.disableLoad();
                            }
                            return null;
                        }
                        throw (ProviderException)throwable;
                    }
                }
            }
        });
    }

    private static String expand(final String string) {
        if (!string.contains("${")) {
            return string;
        }
        return AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                try {
                    String string2 = PropertyExpander.expand(string);
                    return string2;
                }
                catch (GeneralSecurityException generalSecurityException) {
                    throw new ProviderException(generalSecurityException);
                }
            }
        });
    }

    private boolean hasArgument() {
        boolean bl = this.argument.length() != 0;
        return bl;
    }

    private Provider initProvider(String charSequence, ClassLoader serializable) throws Exception {
        serializable = serializable != null ? ((ClassLoader)((Object)serializable)).loadClass((String)charSequence) : Class.forName((String)charSequence);
        serializable = !this.hasArgument() ? serializable.newInstance() : serializable.getConstructor(CL_STRING).newInstance(this.argument);
        if (serializable instanceof Provider) {
            Debug debug = ProviderConfig.debug;
            if (debug != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Loaded provider ");
                ((StringBuilder)charSequence).append(serializable);
                debug.println(((StringBuilder)charSequence).toString());
            }
            return (Provider)serializable;
        }
        Debug debug = ProviderConfig.debug;
        if (debug != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append((String)charSequence);
            ((StringBuilder)serializable).append(" is not a provider");
            debug.println(((StringBuilder)serializable).toString());
        }
        this.disableLoad();
        return null;
    }

    private boolean shouldLoad() {
        boolean bl = this.tries < 30;
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProviderConfig)) {
            return false;
        }
        object = (ProviderConfig)object;
        if (!this.className.equals(((ProviderConfig)object).className) || !this.argument.equals(((ProviderConfig)object).argument)) {
            bl = false;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Provider getProvider() {
        synchronized (this) {
            Object object = this.provider;
            if (object != null) {
                return object;
            }
            boolean bl = this.shouldLoad();
            if (!bl) {
                return null;
            }
            if (this.isLoading) {
                if (debug != null) {
                    object = debug;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Recursion loading provider: ");
                    stringBuilder.append(this);
                    ((Debug)object).println(stringBuilder.toString());
                    object = new Exception("Call trace");
                    ((Throwable)object).printStackTrace();
                }
                return null;
            }
            this.isLoading = true;
            ++this.tries;
            object = this.doLoadProvider();
            this.provider = object;
            return object;
            finally {
                this.isLoading = false;
            }
        }
    }

    public int hashCode() {
        return this.className.hashCode() + this.argument.hashCode();
    }

    boolean isLoaded() {
        boolean bl = this.provider != null;
        return bl;
    }

    public String toString() {
        if (this.hasArgument()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.className);
            stringBuilder.append("('");
            stringBuilder.append(this.argument);
            stringBuilder.append("')");
            return stringBuilder.toString();
        }
        return this.className;
    }

}

