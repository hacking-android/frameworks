/*
 * Decompiled with CFR 0.145.
 */
package sun.security.jca;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.util.Iterator;
import java.util.List;
import sun.security.jca.ProviderList;
import sun.security.jca.Providers;
import sun.security.jca.ServiceId;

public class GetInstance {
    private GetInstance() {
    }

    public static void checkSuperClass(Provider.Service service, Class<?> serializable, Class<?> class_) throws NoSuchAlgorithmException {
        if (class_ == null) {
            return;
        }
        if (class_.isAssignableFrom((Class<?>)serializable)) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("class configured for ");
        ((StringBuilder)serializable).append(service.getType());
        ((StringBuilder)serializable).append(": ");
        ((StringBuilder)serializable).append(service.getClassName());
        ((StringBuilder)serializable).append(" not a ");
        ((StringBuilder)serializable).append(service.getType());
        throw new NoSuchAlgorithmException(((StringBuilder)serializable).toString());
    }

    /*
     * WARNING - void declaration
     */
    public static Instance getInstance(String object, Class<?> serializable, String object22) throws NoSuchAlgorithmException {
        ProviderList providerList = Providers.getProviderList();
        Provider.Service service = providerList.getService((String)((Object)object), (String)object22);
        if (service != null) {
            try {
                Instance noSuchAlgorithmException2 = GetInstance.getInstance(service, serializable);
                return noSuchAlgorithmException2;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                void var5_10;
                for (Provider.Service service2 : providerList.getServices((String)((Object)object), (String)object22)) {
                    if (service2 == service) continue;
                    try {
                        Instance instance = GetInstance.getInstance(service2, serializable);
                        return instance;
                    }
                    catch (NoSuchAlgorithmException noSuchAlgorithmException2) {
                    }
                }
                throw var5_10;
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append((String)object22);
        ((StringBuilder)serializable).append(" ");
        ((StringBuilder)serializable).append((String)((Object)object));
        ((StringBuilder)serializable).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)serializable).toString());
    }

    public static Instance getInstance(String string, Class<?> serializable, String string2, Object object) throws NoSuchAlgorithmException {
        Object object2 = GetInstance.getServices(string, string2);
        Object object3 = null;
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object3 = (Provider.Service)object2.next();
            try {
                object3 = GetInstance.getInstance((Provider.Service)object3, serializable, object);
                return object3;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            }
        }
        if (object3 != null) {
            throw object3;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(" ");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)serializable).toString());
    }

    public static Instance getInstance(String string, Class<?> class_, String string2, Object object, String string3) throws NoSuchAlgorithmException, NoSuchProviderException {
        return GetInstance.getInstance(GetInstance.getService(string, string2, string3), class_, object);
    }

    public static Instance getInstance(String string, Class<?> class_, String string2, Object object, Provider provider) throws NoSuchAlgorithmException {
        return GetInstance.getInstance(GetInstance.getService(string, string2, provider), class_, object);
    }

    public static Instance getInstance(String string, Class<?> class_, String string2, String string3) throws NoSuchAlgorithmException, NoSuchProviderException {
        return GetInstance.getInstance(GetInstance.getService(string, string2, string3), class_);
    }

    public static Instance getInstance(String string, Class<?> class_, String string2, Provider provider) throws NoSuchAlgorithmException {
        return GetInstance.getInstance(GetInstance.getService(string, string2, provider), class_);
    }

    public static Instance getInstance(Provider.Service service, Class<?> class_) throws NoSuchAlgorithmException {
        Object object = service.newInstance(null);
        GetInstance.checkSuperClass(service, object.getClass(), class_);
        return new Instance(service.getProvider(), object);
    }

    public static Instance getInstance(Provider.Service service, Class<?> class_, Object object) throws NoSuchAlgorithmException {
        object = service.newInstance(object);
        GetInstance.checkSuperClass(service, object.getClass(), class_);
        return new Instance(service.getProvider(), object);
    }

    public static Provider.Service getService(String string, String string2) throws NoSuchAlgorithmException {
        Object object = Providers.getProviderList().getService(string, string2);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" not available");
        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
    }

    public static Provider.Service getService(String object, String string, String string2) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (string2 != null && string2.length() != 0) {
            Provider provider = Providers.getProviderList().getProvider(string2);
            if (provider != null) {
                if ((object = provider.getService((String)object, string)) != null) {
                    return object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("no such algorithm: ");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" for provider ");
                ((StringBuilder)object).append(string2);
                throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("no such provider: ");
            ((StringBuilder)object).append(string2);
            throw new NoSuchProviderException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static Provider.Service getService(String object, String string, Provider provider) throws NoSuchAlgorithmException {
        if (provider != null) {
            if ((object = provider.getService((String)object, string)) != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("no such algorithm: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" for provider ");
            ((StringBuilder)object).append(provider.getName());
            throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static List<Provider.Service> getServices(String string, String string2) {
        return Providers.getProviderList().getServices(string, string2);
    }

    @Deprecated
    public static List<Provider.Service> getServices(String string, List<String> list) {
        return Providers.getProviderList().getServices(string, list);
    }

    public static List<Provider.Service> getServices(List<ServiceId> list) {
        return Providers.getProviderList().getServices(list);
    }

    public static final class Instance {
        public final Object impl;
        public final Provider provider;

        private Instance(Provider provider, Object object) {
            this.provider = provider;
            this.impl = object;
        }

        public Object[] toArray() {
            return new Object[]{this.impl, this.provider};
        }
    }

}

