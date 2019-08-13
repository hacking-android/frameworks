/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.security.auth.x500.X500Principal;

class KeyManagerImpl
extends X509ExtendedKeyManager {
    private final HashMap<String, KeyStore.PrivateKeyEntry> hash = new HashMap();

    KeyManagerImpl(KeyStore keyStore, char[] arrc) {
        Enumeration<String> enumeration;
        try {
            enumeration = keyStore.aliases();
        }
        catch (KeyStoreException keyStoreException) {
            return;
        }
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            try {
                if (!keyStore.entryInstanceOf(string, KeyStore.PrivateKeyEntry.class)) continue;
                Object object = new KeyStore.PasswordProtection(arrc);
                object = (KeyStore.PrivateKeyEntry)keyStore.getEntry(string, (KeyStore.ProtectionParameter)object);
                this.hash.put(string, (KeyStore.PrivateKeyEntry)object);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            }
            catch (UnrecoverableEntryException unrecoverableEntryException) {
            }
            catch (KeyStoreException keyStoreException) {}
        }
        return;
    }

    /*
     * WARNING - void declaration
     */
    private String[] chooseAlias(String[] arrstring, Principal[] arrprincipal) {
        String[] object2 = arrstring;
        if (object2 != null && object2.length != 0) {
            List<Principal> list = arrprincipal == null ? null : Arrays.asList(arrprincipal);
            ArrayList<String> arrayList = new ArrayList<String>();
            block0 : for (Map.Entry<String, KeyStore.PrivateKeyEntry> entry : this.hash.entrySet()) {
                String string = entry.getKey();
                Certificate[] arrcertificate = entry.getValue().getCertificateChain();
                Certificate certificate = arrcertificate[0];
                String string2 = certificate.getPublicKey().getAlgorithm();
                String string3 = certificate instanceof X509Certificate ? ((X509Certificate)certificate).getSigAlgName().toUpperCase(Locale.US) : null;
                int n = arrstring.length;
                int n2 = 0;
                do {
                    Principal[] arrprincipal2 = arrprincipal;
                    if (n2 >= n) continue block0;
                    String string4 = arrstring[n2];
                    if (string4 != null) {
                        void var15_25;
                        void var3_10;
                        int n3 = string4.indexOf(95);
                        if (n3 == -1) {
                            Object var15_23 = null;
                        } else {
                            String string5 = string4.substring(n3 + 1);
                            String string6 = string4.substring(0, n3);
                        }
                        if (string2.equals(var3_10) && (var15_25 == null || string3 == null || string3.contains((CharSequence)var15_25))) {
                            if (arrprincipal2 != null && arrprincipal2.length != 0) {
                                for (Certificate certificate2 : arrcertificate) {
                                    if (!(certificate2 instanceof X509Certificate) || !list.contains(((X509Certificate)certificate2).getIssuerX500Principal())) continue;
                                    arrayList.add(string);
                                }
                            } else {
                                arrayList.add(string);
                            }
                        }
                    }
                    ++n2;
                } while (true);
            }
            if (!arrayList.isEmpty()) {
                return arrayList.toArray(new String[arrayList.size()]);
            }
            return null;
        }
        return null;
    }

    @Override
    public String chooseClientAlias(String[] object, Principal[] arrprincipal, Socket socket) {
        object = (object = this.chooseAlias((String[])object, arrprincipal)) == null ? null : object[0];
        return object;
    }

    @Override
    public String chooseEngineClientAlias(String[] object, Principal[] arrprincipal, SSLEngine sSLEngine) {
        object = (object = this.chooseAlias((String[])object, arrprincipal)) == null ? null : object[0];
        return object;
    }

    @Override
    public String chooseEngineServerAlias(String object, Principal[] arrprincipal, SSLEngine sSLEngine) {
        object = (object = this.chooseAlias(new String[]{object}, arrprincipal)) == null ? null : object[0];
        return object;
    }

    @Override
    public String chooseServerAlias(String object, Principal[] arrprincipal, Socket socket) {
        object = (object = this.chooseAlias(new String[]{object}, arrprincipal)) == null ? null : object[0];
        return object;
    }

    @Override
    public X509Certificate[] getCertificateChain(String arrx509Certificate) {
        Certificate[] arrcertificate;
        if (arrx509Certificate == null) {
            return null;
        }
        if (this.hash.containsKey(arrx509Certificate) && (arrcertificate = this.hash.get(arrx509Certificate).getCertificateChain())[0] instanceof X509Certificate) {
            arrx509Certificate = new X509Certificate[arrcertificate.length];
            for (int i = 0; i < arrcertificate.length; ++i) {
                arrx509Certificate[i] = (X509Certificate)arrcertificate[i];
            }
            return arrx509Certificate;
        }
        return null;
    }

    @Override
    public String[] getClientAliases(String string, Principal[] arrprincipal) {
        return this.chooseAlias(new String[]{string}, arrprincipal);
    }

    @Override
    public PrivateKey getPrivateKey(String string) {
        if (string == null) {
            return null;
        }
        if (this.hash.containsKey(string)) {
            return this.hash.get(string).getPrivateKey();
        }
        return null;
    }

    @Override
    public String[] getServerAliases(String string, Principal[] arrprincipal) {
        return this.chooseAlias(new String[]{string}, arrprincipal);
    }
}

