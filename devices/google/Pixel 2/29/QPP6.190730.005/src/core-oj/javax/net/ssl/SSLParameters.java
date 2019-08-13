/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;

public class SSLParameters {
    private AlgorithmConstraints algorithmConstraints;
    private String[] applicationProtocols = new String[0];
    private String[] cipherSuites;
    private String identificationAlgorithm;
    private boolean needClientAuth;
    private boolean preferLocalCipherSuites;
    private String[] protocols;
    private Map<Integer, SNIMatcher> sniMatchers = null;
    private Map<Integer, SNIServerName> sniNames = null;
    private boolean wantClientAuth;

    public SSLParameters() {
    }

    public SSLParameters(String[] arrstring) {
        this.setCipherSuites(arrstring);
    }

    public SSLParameters(String[] arrstring, String[] arrstring2) {
        this.setCipherSuites(arrstring);
        this.setProtocols(arrstring2);
    }

    private static String[] clone(String[] object) {
        object = object == null ? null : (String[])object.clone();
        return object;
    }

    public AlgorithmConstraints getAlgorithmConstraints() {
        return this.algorithmConstraints;
    }

    public String[] getApplicationProtocols() {
        return (String[])this.applicationProtocols.clone();
    }

    public String[] getCipherSuites() {
        return SSLParameters.clone(this.cipherSuites);
    }

    public String getEndpointIdentificationAlgorithm() {
        return this.identificationAlgorithm;
    }

    public boolean getNeedClientAuth() {
        return this.needClientAuth;
    }

    public String[] getProtocols() {
        return SSLParameters.clone(this.protocols);
    }

    public final Collection<SNIMatcher> getSNIMatchers() {
        Map<Integer, SNIMatcher> map = this.sniMatchers;
        if (map != null) {
            if (!map.isEmpty()) {
                return Collections.unmodifiableList(new ArrayList<SNIMatcher>(this.sniMatchers.values()));
            }
            return Collections.emptyList();
        }
        return null;
    }

    public final List<SNIServerName> getServerNames() {
        Map<Integer, SNIServerName> map = this.sniNames;
        if (map != null) {
            if (!map.isEmpty()) {
                return Collections.unmodifiableList(new ArrayList<SNIServerName>(this.sniNames.values()));
            }
            return Collections.emptyList();
        }
        return null;
    }

    public final boolean getUseCipherSuitesOrder() {
        return this.preferLocalCipherSuites;
    }

    public boolean getWantClientAuth() {
        return this.wantClientAuth;
    }

    public void setAlgorithmConstraints(AlgorithmConstraints algorithmConstraints) {
        this.algorithmConstraints = algorithmConstraints;
    }

    public void setApplicationProtocols(String[] arrstring) {
        if (arrstring != null) {
            arrstring = (String[])arrstring.clone();
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                String string = arrstring[i];
                if (string != null && !string.equals("")) {
                    continue;
                }
                throw new IllegalArgumentException("An element of protocols was null/empty");
            }
            this.applicationProtocols = arrstring;
            return;
        }
        throw new IllegalArgumentException("protocols was null");
    }

    public void setCipherSuites(String[] arrstring) {
        this.cipherSuites = SSLParameters.clone(arrstring);
    }

    public void setEndpointIdentificationAlgorithm(String string) {
        this.identificationAlgorithm = string;
    }

    public void setNeedClientAuth(boolean bl) {
        this.wantClientAuth = false;
        this.needClientAuth = bl;
    }

    public void setProtocols(String[] arrstring) {
        this.protocols = SSLParameters.clone(arrstring);
    }

    public final void setSNIMatchers(Collection<SNIMatcher> object) {
        if (object != null) {
            if (!object.isEmpty()) {
                this.sniMatchers = new HashMap<Integer, SNIMatcher>(object.size());
                Object object2 = object.iterator();
                while (object2.hasNext()) {
                    object = object2.next();
                    if (this.sniMatchers.put(((SNIMatcher)object).getType(), (SNIMatcher)object) == null) continue;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Duplicated server name of type ");
                    ((StringBuilder)object2).append(((SNIMatcher)object).getType());
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
            } else {
                this.sniMatchers = Collections.emptyMap();
            }
        } else {
            this.sniMatchers = null;
        }
    }

    public final void setServerNames(List<SNIServerName> object) {
        if (object != null) {
            if (!object.isEmpty()) {
                this.sniNames = new LinkedHashMap<Integer, SNIServerName>(object.size());
                Object object2 = object.iterator();
                while (object2.hasNext()) {
                    object = object2.next();
                    if (this.sniNames.put(((SNIServerName)object).getType(), (SNIServerName)object) == null) continue;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Duplicated server name of type ");
                    ((StringBuilder)object2).append(((SNIServerName)object).getType());
                    throw new IllegalArgumentException(((StringBuilder)object2).toString());
                }
            } else {
                this.sniNames = Collections.emptyMap();
            }
        } else {
            this.sniNames = null;
        }
    }

    public final void setUseCipherSuitesOrder(boolean bl) {
        this.preferLocalCipherSuites = bl;
    }

    public void setWantClientAuth(boolean bl) {
        this.wantClientAuth = bl;
        this.needClientAuth = false;
    }
}

