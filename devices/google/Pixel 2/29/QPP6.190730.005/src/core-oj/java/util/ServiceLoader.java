/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.Set;

public final class ServiceLoader<S>
implements Iterable<S> {
    private static final String PREFIX = "META-INF/services/";
    private final ClassLoader loader;
    private ServiceLoader<S> lookupIterator;
    private LinkedHashMap<String, S> providers = new LinkedHashMap();
    private final Class<S> service;

    private ServiceLoader(Class<S> class_, ClassLoader classLoader) {
        this.service = Objects.requireNonNull(class_, "Service interface cannot be null");
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        this.loader = classLoader;
        this.reload();
    }

    private static void fail(Class<?> class_, String string) throws ServiceConfigurationError {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.getName());
        stringBuilder.append(": ");
        stringBuilder.append(string);
        throw new ServiceConfigurationError(stringBuilder.toString());
    }

    private static void fail(Class<?> class_, String string, Throwable throwable) throws ServiceConfigurationError {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(class_.getName());
        stringBuilder.append(": ");
        stringBuilder.append(string);
        throw new ServiceConfigurationError(stringBuilder.toString(), throwable);
    }

    private static void fail(Class<?> class_, URL uRL, int n, String string) throws ServiceConfigurationError {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uRL);
        stringBuilder.append(":");
        stringBuilder.append(n);
        stringBuilder.append(": ");
        stringBuilder.append(string);
        ServiceLoader.fail(class_, stringBuilder.toString());
    }

    public static <S> ServiceLoader<S> load(Class<S> class_) {
        return ServiceLoader.load(class_, Thread.currentThread().getContextClassLoader());
    }

    public static <S> ServiceLoader<S> load(Class<S> class_, ClassLoader classLoader) {
        return new ServiceLoader<S>(class_, classLoader);
    }

    public static <S> S loadFromSystemProperty(Class<S> object) {
        block3 : {
            try {
                object = System.getProperty(((Class)object).getName());
                if (object == null) break block3;
            }
            catch (Exception exception) {
                throw new Error(exception);
            }
            object = ClassLoader.getSystemClassLoader().loadClass((String)object).newInstance();
            return (S)object;
        }
        return null;
    }

    public static <S> ServiceLoader<S> loadInstalled(Class<S> class_) {
        ClassLoader classLoader = null;
        for (ClassLoader classLoader2 = ClassLoader.getSystemClassLoader(); classLoader2 != null; classLoader2 = classLoader2.getParent()) {
            classLoader = classLoader2;
        }
        return ServiceLoader.load(class_, classLoader);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Iterator<String> parse(Class<?> class_, URL object) throws ServiceConfigurationError {
        void var2_7;
        IOException iOException622;
        block19 : {
            InputStream inputStream;
            Object object2;
            block15 : {
                ArrayList<String> arrayList;
                IOException iOException2222;
                block18 : {
                    block17 : {
                        InputStream inputStream2;
                        Object object3;
                        block16 : {
                            int n;
                            InputStream inputStream3 = null;
                            inputStream2 = null;
                            Object var5_11 = null;
                            BufferedReader bufferedReader = null;
                            arrayList = new ArrayList<String>();
                            object2 = bufferedReader;
                            inputStream2 = inputStream = ((URL)object).openStream();
                            object2 = bufferedReader;
                            inputStream3 = inputStream;
                            inputStream2 = inputStream;
                            object2 = bufferedReader;
                            inputStream3 = inputStream;
                            inputStream2 = inputStream;
                            object2 = bufferedReader;
                            inputStream3 = inputStream;
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                            inputStream2 = inputStream;
                            object2 = bufferedReader;
                            inputStream3 = inputStream;
                            object3 = new BufferedReader(inputStreamReader);
                            int n2 = 1;
                            do {
                                n2 = n = this.parseLine(class_, (URL)object, (BufferedReader)object3, n2, arrayList);
                            } while (n >= 0);
                            try {
                                ((BufferedReader)object3).close();
                                if (inputStream == null) return arrayList.iterator();
                                inputStream.close();
                                return arrayList.iterator();
                            }
                            catch (IOException iOException3) {
                                ServiceLoader.fail(class_, "Error closing configuration file", iOException3);
                                return arrayList.iterator();
                            }
                            catch (Throwable throwable) {
                                object2 = object3;
                                break block15;
                            }
                            catch (IOException iOException4) {
                                object = object3;
                                object3 = iOException4;
                                break block16;
                            }
                            catch (Throwable throwable) {
                                inputStream = inputStream2;
                                break block15;
                            }
                            catch (IOException iOException5) {
                                object = var5_11;
                                inputStream = inputStream3;
                            }
                        }
                        inputStream2 = inputStream;
                        object2 = object;
                        {
                            ServiceLoader.fail(class_, "Error reading configuration file", (Throwable)object3);
                            if (object == null) break block17;
                        }
                        try {
                            ((BufferedReader)object).close();
                        }
                        catch (IOException iOException2222) {
                            break block18;
                        }
                    }
                    if (inputStream == null) return arrayList.iterator();
                    inputStream.close();
                    return arrayList.iterator();
                }
                ServiceLoader.fail(class_, "Error closing configuration file", iOException2222);
                return arrayList.iterator();
            }
            if (object2 != null) {
                try {
                    ((BufferedReader)object2).close();
                }
                catch (IOException iOException622) {
                    break block19;
                }
            }
            if (inputStream == null) throw var2_7;
            inputStream.close();
            throw var2_7;
        }
        ServiceLoader.fail(class_, "Error closing configuration file", iOException622);
        throw var2_7;
    }

    private int parseLine(Class<?> class_, URL uRL, BufferedReader object, int n, List<String> list) throws IOException, ServiceConfigurationError {
        int n2;
        CharSequence charSequence = ((BufferedReader)object).readLine();
        if (charSequence == null) {
            return -1;
        }
        int n3 = ((String)charSequence).indexOf(35);
        object = charSequence;
        if (n3 >= 0) {
            object = ((String)charSequence).substring(0, n3);
        }
        if ((n2 = ((String)(object = ((String)object).trim())).length()) != 0) {
            int n4;
            if (((String)object).indexOf(32) >= 0 || ((String)object).indexOf(9) >= 0) {
                ServiceLoader.fail(class_, uRL, n, "Illegal configuration-file syntax");
            }
            if (!Character.isJavaIdentifierStart(n3 = ((String)object).codePointAt(0))) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Illegal provider-class name: ");
                ((StringBuilder)charSequence).append((String)object);
                ServiceLoader.fail(class_, uRL, n, ((StringBuilder)charSequence).toString());
            }
            for (n3 = Character.charCount((int)n3); n3 < n2; n3 += Character.charCount((int)n4)) {
                n4 = ((String)object).codePointAt(n3);
                if (Character.isJavaIdentifierPart(n4) || n4 == 46) continue;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Illegal provider-class name: ");
                ((StringBuilder)charSequence).append((String)object);
                ServiceLoader.fail(class_, uRL, n, ((StringBuilder)charSequence).toString());
            }
            if (!this.providers.containsKey(object) && !list.contains(object)) {
                list.add((String)object);
            }
        }
        return n + 1;
    }

    @Override
    public Iterator<S> iterator() {
        return new Iterator<S>(){
            Iterator<Map.Entry<String, S>> knownProviders;
            {
                this.knownProviders = ServiceLoader.this.providers.entrySet().iterator();
            }

            @Override
            public boolean hasNext() {
                if (this.knownProviders.hasNext()) {
                    return true;
                }
                return ServiceLoader.this.lookupIterator.hasNext();
            }

            @Override
            public S next() {
                if (this.knownProviders.hasNext()) {
                    return this.knownProviders.next().getValue();
                }
                return ServiceLoader.this.lookupIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void reload() {
        this.providers.clear();
        this.lookupIterator = new LazyIterator(this.service, this.loader);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("java.util.ServiceLoader[");
        stringBuilder.append(this.service.getName());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private class LazyIterator
    implements Iterator<S> {
        Enumeration<URL> configs = null;
        ClassLoader loader;
        String nextName = null;
        Iterator<String> pending = null;
        Class<S> service;

        private LazyIterator(Class<S> class_, ClassLoader classLoader) {
            this.service = class_;
            this.loader = classLoader;
        }

        private boolean hasNextService() {
            Object object;
            if (this.nextName != null) {
                return true;
            }
            if (this.configs == null) {
                try {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(ServiceLoader.PREFIX);
                    ((StringBuilder)object).append(this.service.getName());
                    object = ((StringBuilder)object).toString();
                    this.configs = this.loader == null ? ClassLoader.getSystemResources((String)object) : this.loader.getResources((String)object);
                }
                catch (IOException iOException) {
                    ServiceLoader.fail(this.service, "Error locating configuration files", iOException);
                }
            }
            do {
                if ((object = this.pending) != null && object.hasNext()) {
                    this.nextName = this.pending.next();
                    return true;
                }
                if (!this.configs.hasMoreElements()) {
                    return false;
                }
                this.pending = ServiceLoader.this.parse(this.service, this.configs.nextElement());
            } while (true);
        }

        private S nextService() {
            if (this.hasNextService()) {
                Serializable serializable;
                Serializable serializable2;
                String string = this.nextName;
                this.nextName = null;
                Class<Object> class_ = null;
                try {
                    serializable2 = Class.forName(string, false, this.loader);
                    class_ = serializable2;
                }
                catch (ClassNotFoundException classNotFoundException) {
                    serializable2 = this.service;
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Provider ");
                    ((StringBuilder)serializable).append(string);
                    ((StringBuilder)serializable).append(" not found");
                    ServiceLoader.fail((Class)serializable2, ((StringBuilder)serializable).toString(), classNotFoundException);
                }
                if (!this.service.isAssignableFrom(class_)) {
                    serializable2 = new StringBuilder();
                    ((StringBuilder)serializable2).append(this.service.getCanonicalName());
                    ((StringBuilder)serializable2).append(" is not assignable from ");
                    ((StringBuilder)serializable2).append(class_.getCanonicalName());
                    serializable2 = new ClassCastException(((StringBuilder)serializable2).toString());
                    serializable = this.service;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Provider ");
                    stringBuilder.append(string);
                    stringBuilder.append(" not a subtype");
                    ServiceLoader.fail((Class)serializable, stringBuilder.toString(), (Throwable)serializable2);
                }
                try {
                    class_ = this.service.cast(class_.newInstance());
                    ServiceLoader.this.providers.put(string, class_);
                }
                catch (Throwable throwable) {
                    serializable = this.service;
                    serializable2 = new StringBuilder();
                    ((StringBuilder)serializable2).append("Provider ");
                    ((StringBuilder)serializable2).append(string);
                    ((StringBuilder)serializable2).append(" could not be instantiated");
                    ServiceLoader.fail((Class)serializable, ((StringBuilder)serializable2).toString(), throwable);
                    throw new Error();
                }
                return (S)class_;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return this.hasNextService();
        }

        @Override
        public S next() {
            return this.nextService();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

