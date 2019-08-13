/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.InetAddress;
import java.net.NetPermission;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.Permission;

public abstract class Authenticator {
    private static Authenticator theAuthenticator;
    private RequestorType requestingAuthType;
    private String requestingHost;
    private int requestingPort;
    private String requestingPrompt;
    private String requestingProtocol;
    private String requestingScheme;
    private InetAddress requestingSite;
    private URL requestingURL;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PasswordAuthentication requestPasswordAuthentication(String object, InetAddress inetAddress, int n, String string, String string2, String string3) {
        Object object2 = System.getSecurityManager();
        if (object2 != null) {
            ((SecurityManager)object2).checkPermission(new NetPermission("requestPasswordAuthentication"));
        }
        if ((object2 = theAuthenticator) == null) {
            return null;
        }
        synchronized (object2) {
            Authenticator.super.reset();
            ((Authenticator)object2).requestingHost = object;
            ((Authenticator)object2).requestingSite = inetAddress;
            ((Authenticator)object2).requestingPort = n;
            ((Authenticator)object2).requestingProtocol = string;
            ((Authenticator)object2).requestingPrompt = string2;
            ((Authenticator)object2).requestingScheme = string3;
            return ((Authenticator)object2).getPasswordAuthentication();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PasswordAuthentication requestPasswordAuthentication(String object, InetAddress inetAddress, int n, String string, String string2, String string3, URL uRL, RequestorType requestorType) {
        Object object2 = System.getSecurityManager();
        if (object2 != null) {
            ((SecurityManager)object2).checkPermission(new NetPermission("requestPasswordAuthentication"));
        }
        if ((object2 = theAuthenticator) == null) {
            return null;
        }
        synchronized (object2) {
            Authenticator.super.reset();
            ((Authenticator)object2).requestingHost = object;
            ((Authenticator)object2).requestingSite = inetAddress;
            ((Authenticator)object2).requestingPort = n;
            ((Authenticator)object2).requestingProtocol = string;
            ((Authenticator)object2).requestingPrompt = string2;
            ((Authenticator)object2).requestingScheme = string3;
            ((Authenticator)object2).requestingURL = uRL;
            ((Authenticator)object2).requestingAuthType = requestorType;
            return ((Authenticator)object2).getPasswordAuthentication();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PasswordAuthentication requestPasswordAuthentication(InetAddress object, int n, String string, String string2, String string3) {
        Object object2 = System.getSecurityManager();
        if (object2 != null) {
            ((SecurityManager)object2).checkPermission(new NetPermission("requestPasswordAuthentication"));
        }
        if ((object2 = theAuthenticator) == null) {
            return null;
        }
        synchronized (object2) {
            Authenticator.super.reset();
            ((Authenticator)object2).requestingSite = object;
            ((Authenticator)object2).requestingPort = n;
            ((Authenticator)object2).requestingProtocol = string;
            ((Authenticator)object2).requestingPrompt = string2;
            ((Authenticator)object2).requestingScheme = string3;
            return ((Authenticator)object2).getPasswordAuthentication();
        }
    }

    private void reset() {
        this.requestingHost = null;
        this.requestingSite = null;
        this.requestingPort = -1;
        this.requestingProtocol = null;
        this.requestingPrompt = null;
        this.requestingScheme = null;
        this.requestingURL = null;
        this.requestingAuthType = RequestorType.SERVER;
    }

    public static void setDefault(Authenticator authenticator) {
        synchronized (Authenticator.class) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                NetPermission netPermission = new NetPermission("setDefaultAuthenticator");
                securityManager.checkPermission(netPermission);
            }
            theAuthenticator = authenticator;
            return;
        }
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return null;
    }

    protected final String getRequestingHost() {
        return this.requestingHost;
    }

    protected final int getRequestingPort() {
        return this.requestingPort;
    }

    protected final String getRequestingPrompt() {
        return this.requestingPrompt;
    }

    protected final String getRequestingProtocol() {
        return this.requestingProtocol;
    }

    protected final String getRequestingScheme() {
        return this.requestingScheme;
    }

    protected final InetAddress getRequestingSite() {
        return this.requestingSite;
    }

    protected URL getRequestingURL() {
        return this.requestingURL;
    }

    protected RequestorType getRequestorType() {
        return this.requestingAuthType;
    }

    public static enum RequestorType {
        PROXY,
        SERVER;
        
    }

}

