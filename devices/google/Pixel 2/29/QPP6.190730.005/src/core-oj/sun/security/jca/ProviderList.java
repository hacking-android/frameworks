/*
 * Decompiled with CFR 0.145.
 */
package sun.security.jca;

import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import sun.security.jca.ProviderConfig;
import sun.security.jca.ServiceId;
import sun.security.util.Debug;

public final class ProviderList {
    static final ProviderList EMPTY;
    private static final Provider EMPTY_PROVIDER;
    private static final Provider[] P0;
    private static final ProviderConfig[] PC0;
    static final Debug debug;
    private volatile boolean allLoaded;
    private final ProviderConfig[] configs;
    private final List<Provider> userList = new AbstractList<Provider>(){

        @Override
        public Provider get(int n) {
            return ProviderList.this.getProvider(n);
        }

        @Override
        public int size() {
            return ProviderList.this.configs.length;
        }
    };

    static {
        debug = Debug.getInstance("jca", "ProviderList");
        PC0 = new ProviderConfig[0];
        P0 = new Provider[0];
        EMPTY = new ProviderList(PC0, true);
        EMPTY_PROVIDER = new Provider("##Empty##", 1.0, "initialization in progress"){
            private static final long serialVersionUID = 1151354171352296389L;

            @Override
            public Provider.Service getService(String string, String string2) {
                return null;
            }
        };
    }

    private ProviderList() {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int n = 1;
        do {
            Object object;
            block7 : {
                Object object2;
                block6 : {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("security.provider.");
                    ((StringBuilder)object).append(n);
                    object = Security.getProperty(((StringBuilder)object).toString());
                    if (object == null) break block6;
                    if (((String)(object = ((String)object).trim())).length() != 0) break block7;
                    object2 = System.err;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("invalid entry for security.provider.");
                    ((StringBuilder)object).append(n);
                    ((PrintStream)object2).println(((StringBuilder)object).toString());
                }
                this.configs = arrayList.toArray(PC0);
                object2 = debug;
                if (object2 != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("provider configuration: ");
                    ((StringBuilder)object).append(arrayList);
                    ((Debug)object2).println(((StringBuilder)object).toString());
                }
                return;
            }
            int n2 = ((String)object).indexOf(32);
            if (!arrayList.contains(object = n2 == -1 ? new ProviderConfig((String)object) : new ProviderConfig(((String)object).substring(0, n2), ((String)object).substring(n2 + 1).trim()))) {
                arrayList.add(object);
            }
            ++n;
        } while (true);
    }

    private ProviderList(ProviderConfig[] arrproviderConfig, boolean bl) {
        this.configs = arrproviderConfig;
        this.allLoaded = bl;
    }

    public static ProviderList add(ProviderList providerList, Provider provider) {
        return ProviderList.insertAt(providerList, provider, -1);
    }

    static ProviderList fromSecurityProperties() {
        return AccessController.doPrivileged(new PrivilegedAction<ProviderList>(){

            @Override
            public ProviderList run() {
                return new ProviderList();
            }
        });
    }

    private ProviderConfig getProviderConfig(String object) {
        int n = this.getIndex((String)object);
        object = n != -1 ? this.configs[n] : null;
        return object;
    }

    public static ProviderList insertAt(ProviderList object, Provider provider, int n) {
        int n2;
        block5 : {
            int n3;
            block4 : {
                if (((ProviderList)object).getProvider(provider.getName()) != null) {
                    return object;
                }
                object = new ArrayList<ProviderConfig>(Arrays.asList(((ProviderList)object).configs));
                n3 = object.size();
                if (n < 0) break block4;
                n2 = n;
                if (n <= n3) break block5;
            }
            n2 = n3;
        }
        object.add(n2, new ProviderConfig(provider));
        return new ProviderList(object.toArray(PC0), true);
    }

    private int loadAll() {
        if (this.allLoaded) {
            return this.configs.length;
        }
        ProviderConfig[] arrproviderConfig = debug;
        if (arrproviderConfig != null) {
            arrproviderConfig.println("Loading all providers");
            new Exception("Call trace").printStackTrace();
        }
        int n = 0;
        for (int i = 0; i < (arrproviderConfig = this.configs).length; ++i) {
            int n2 = n;
            if (arrproviderConfig[i].getProvider() != null) {
                n2 = n + 1;
            }
            n = n2;
        }
        if (n == arrproviderConfig.length) {
            this.allLoaded = true;
        }
        return n;
    }

    public static ProviderList newList(Provider ... arrprovider) {
        ProviderConfig[] arrproviderConfig = new ProviderConfig[arrprovider.length];
        for (int i = 0; i < arrprovider.length; ++i) {
            arrproviderConfig[i] = new ProviderConfig(arrprovider[i]);
        }
        return new ProviderList(arrproviderConfig, true);
    }

    public static ProviderList remove(ProviderList arrproviderConfig, String string) {
        if (arrproviderConfig.getProvider(string) == null) {
            return arrproviderConfig;
        }
        ProviderConfig[] arrproviderConfig2 = new ProviderConfig[arrproviderConfig.size() - 1];
        int n = 0;
        for (ProviderConfig providerConfig : arrproviderConfig.configs) {
            int n2 = n;
            if (!providerConfig.getProvider().getName().equals(string)) {
                arrproviderConfig2[n] = providerConfig;
                n2 = n + 1;
            }
            n = n2;
        }
        return new ProviderList(arrproviderConfig2, true);
    }

    public int getIndex(String string) {
        for (int i = 0; i < this.configs.length; ++i) {
            if (!this.getProvider(i).getName().equals(string)) continue;
            return i;
        }
        return -1;
    }

    ProviderList getJarList(String[] arrstring) {
        ArrayList<ProviderConfig> arrayList = new ArrayList<ProviderConfig>();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            ProviderConfig providerConfig;
            ProviderConfig providerConfig2 = new ProviderConfig(arrstring[i]);
            ProviderConfig[] arrproviderConfig = this.configs;
            int n2 = arrproviderConfig.length;
            int n3 = 0;
            do {
                providerConfig = providerConfig2;
                if (n3 >= n2 || (providerConfig = arrproviderConfig[n3]).equals(providerConfig2)) break;
                ++n3;
            } while (true);
            arrayList.add(providerConfig);
        }
        return new ProviderList(arrayList.toArray(PC0), false);
    }

    Provider getProvider(int n) {
        Provider provider = this.configs[n].getProvider();
        if (provider == null) {
            provider = EMPTY_PROVIDER;
        }
        return provider;
    }

    public Provider getProvider(String object) {
        object = (object = this.getProviderConfig((String)object)) == null ? null : ((ProviderConfig)object).getProvider();
        return object;
    }

    public Provider.Service getService(String string, String string2) {
        for (int i = 0; i < this.configs.length; ++i) {
            Provider.Service service = this.getProvider(i).getService(string, string2);
            if (service == null) continue;
            return service;
        }
        return null;
    }

    public List<Provider.Service> getServices(String string, String string2) {
        return new ServiceList(string, string2);
    }

    @Deprecated
    public List<Provider.Service> getServices(String string, List<String> object) {
        ArrayList<ServiceId> arrayList = new ArrayList<ServiceId>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new ServiceId(string, (String)object.next()));
        }
        return this.getServices(arrayList);
    }

    public List<Provider.Service> getServices(List<ServiceId> list) {
        return new ServiceList(list);
    }

    public List<Provider> providers() {
        return this.userList;
    }

    ProviderList removeInvalid() {
        Object object;
        int n = this.loadAll();
        if (n == this.configs.length) {
            return this;
        }
        ProviderConfig[] arrproviderConfig = new ProviderConfig[n];
        n = 0;
        for (int i = 0; i < ((ProviderConfig[])(object = this.configs)).length; ++i) {
            object = object[i];
            int n2 = n;
            if (((ProviderConfig)object).isLoaded()) {
                arrproviderConfig[n] = object;
                n2 = n + 1;
            }
            n = n2;
        }
        return new ProviderList(arrproviderConfig, true);
    }

    public int size() {
        return this.configs.length;
    }

    public Provider[] toArray() {
        return this.providers().toArray(P0);
    }

    public String toString() {
        return Arrays.asList(this.configs).toString();
    }

    private final class ServiceList
    extends AbstractList<Provider.Service> {
        private final String algorithm;
        private Provider.Service firstService;
        private final List<ServiceId> ids;
        private int providerIndex;
        private List<Provider.Service> services;
        private final String type;

        ServiceList(String string, String string2) {
            this.type = string;
            this.algorithm = string2;
            this.ids = null;
        }

        ServiceList(List<ServiceId> list) {
            this.type = null;
            this.algorithm = null;
            this.ids = list;
        }

        private void addService(Provider.Service service) {
            if (this.firstService == null) {
                this.firstService = service;
            } else {
                if (this.services == null) {
                    this.services = new ArrayList<Provider.Service>(4);
                    this.services.add(this.firstService);
                }
                this.services.add(service);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        private Provider.Service tryGet(int var1_1) {
            block0 : do {
                if (var1_1 == 0 && (var2_2 = this.firstService) != null) {
                    return var2_2;
                }
                var2_2 = this.services;
                if (var2_2 != null && var2_2.size() > var1_1) {
                    return this.services.get(var1_1);
                }
                if (this.providerIndex >= ProviderList.access$100(ProviderList.this).length) {
                    return null;
                }
                var2_2 = ProviderList.this;
                var3_3 = this.providerIndex;
                this.providerIndex = var3_3 + 1;
                var2_2 = var2_2.getProvider(var3_3);
                var4_4 = this.type;
                if (var4_4 != null) {
                    if ((var2_2 = var2_2.getService((String)var4_4, this.algorithm)) == null) continue;
                    this.addService((Provider.Service)var2_2);
                    continue;
                }
                var4_4 = this.ids.iterator();
                do {
                    if (var4_4.hasNext()) ** break;
                    continue block0;
                    var5_5 = (ServiceId)var4_4.next();
                    var5_5 = var2_2.getService(var5_5.type, var5_5.algorithm);
                    if (var5_5 == null) continue;
                    this.addService((Provider.Service)var5_5);
                } while (true);
                break;
            } while (true);
        }

        @Override
        public Provider.Service get(int n) {
            Provider.Service service = this.tryGet(n);
            if (service != null) {
                return service;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean isEmpty() {
            boolean bl = false;
            if (this.tryGet(0) == null) {
                bl = true;
            }
            return bl;
        }

        @Override
        public Iterator<Provider.Service> iterator() {
            return new Iterator<Provider.Service>(){
                int index;

                @Override
                public boolean hasNext() {
                    boolean bl = ServiceList.this.tryGet(this.index) != null;
                    return bl;
                }

                @Override
                public Provider.Service next() {
                    Provider.Service service = ServiceList.this.tryGet(this.index);
                    if (service != null) {
                        ++this.index;
                        return service;
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        @Override
        public int size() {
            List<Provider.Service> list = this.services;
            int n = list != null ? list.size() : (this.firstService != null ? 1 : 0);
            while (this.tryGet(n) != null) {
                ++n;
            }
            return n;
        }

    }

}

