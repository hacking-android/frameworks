/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.net.NetPermission;
import java.net.SocketPermission;
import java.security.AllPermission;
import java.security.SecurityPermission;

public final class SecurityConstants {
    public static final AllPermission ALL_PERMISSION = new AllPermission();
    public static final RuntimePermission CHECK_MEMBER_ACCESS_PERMISSION;
    public static final SecurityPermission CREATE_ACC_PERMISSION;
    public static final RuntimePermission CREATE_CLASSLOADER_PERMISSION;
    public static final String FILE_DELETE_ACTION = "delete";
    public static final String FILE_EXECUTE_ACTION = "execute";
    public static final String FILE_READLINK_ACTION = "readlink";
    public static final String FILE_READ_ACTION = "read";
    public static final String FILE_WRITE_ACTION = "write";
    public static final RuntimePermission GET_CLASSLOADER_PERMISSION;
    public static final SecurityPermission GET_COMBINER_PERMISSION;
    public static final NetPermission GET_COOKIEHANDLER_PERMISSION;
    public static final RuntimePermission GET_PD_PERMISSION;
    public static final SecurityPermission GET_POLICY_PERMISSION;
    public static final NetPermission GET_PROXYSELECTOR_PERMISSION;
    public static final NetPermission GET_RESPONSECACHE_PERMISSION;
    public static final RuntimePermission GET_STACK_TRACE_PERMISSION;
    public static final SocketPermission LOCAL_LISTEN_PERMISSION;
    public static final RuntimePermission MODIFY_THREADGROUP_PERMISSION;
    public static final RuntimePermission MODIFY_THREAD_PERMISSION;
    public static final String PROPERTY_READ_ACTION = "read";
    public static final String PROPERTY_RW_ACTION = "read,write";
    public static final String PROPERTY_WRITE_ACTION = "write";
    public static final NetPermission SET_COOKIEHANDLER_PERMISSION;
    public static final NetPermission SET_PROXYSELECTOR_PERMISSION;
    public static final NetPermission SET_RESPONSECACHE_PERMISSION;
    public static final String SOCKET_ACCEPT_ACTION = "accept";
    public static final String SOCKET_CONNECT_ACCEPT_ACTION = "connect,accept";
    public static final String SOCKET_CONNECT_ACTION = "connect";
    public static final String SOCKET_LISTEN_ACTION = "listen";
    public static final String SOCKET_RESOLVE_ACTION = "resolve";
    public static final NetPermission SPECIFY_HANDLER_PERMISSION;
    public static final RuntimePermission STOP_THREAD_PERMISSION;

    static {
        SPECIFY_HANDLER_PERMISSION = new NetPermission("specifyStreamHandler");
        SET_PROXYSELECTOR_PERMISSION = new NetPermission("setProxySelector");
        GET_PROXYSELECTOR_PERMISSION = new NetPermission("getProxySelector");
        SET_COOKIEHANDLER_PERMISSION = new NetPermission("setCookieHandler");
        GET_COOKIEHANDLER_PERMISSION = new NetPermission("getCookieHandler");
        SET_RESPONSECACHE_PERMISSION = new NetPermission("setResponseCache");
        GET_RESPONSECACHE_PERMISSION = new NetPermission("getResponseCache");
        CREATE_CLASSLOADER_PERMISSION = new RuntimePermission("createClassLoader");
        CHECK_MEMBER_ACCESS_PERMISSION = new RuntimePermission("accessDeclaredMembers");
        MODIFY_THREAD_PERMISSION = new RuntimePermission("modifyThread");
        MODIFY_THREADGROUP_PERMISSION = new RuntimePermission("modifyThreadGroup");
        GET_PD_PERMISSION = new RuntimePermission("getProtectionDomain");
        GET_CLASSLOADER_PERMISSION = new RuntimePermission("getClassLoader");
        STOP_THREAD_PERMISSION = new RuntimePermission("stopThread");
        GET_STACK_TRACE_PERMISSION = new RuntimePermission("getStackTrace");
        CREATE_ACC_PERMISSION = new SecurityPermission("createAccessControlContext");
        GET_COMBINER_PERMISSION = new SecurityPermission("getDomainCombiner");
        GET_POLICY_PERMISSION = new SecurityPermission("getPolicy");
        LOCAL_LISTEN_PERMISSION = new SocketPermission("localhost:0", SOCKET_LISTEN_ACTION);
    }

    private SecurityConstants() {
    }
}

