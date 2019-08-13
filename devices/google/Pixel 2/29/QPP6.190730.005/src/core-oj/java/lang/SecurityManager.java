/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

public class SecurityManager {
    @Deprecated
    protected boolean inCheck;

    public void checkAccept(String string, int n) {
    }

    public void checkAccess(Thread thread) {
    }

    public void checkAccess(ThreadGroup threadGroup) {
    }

    public void checkAwtEventQueueAccess() {
    }

    public void checkConnect(String string, int n) {
    }

    public void checkConnect(String string, int n, Object object) {
    }

    public void checkCreateClassLoader() {
    }

    public void checkDelete(String string) {
    }

    public void checkExec(String string) {
    }

    public void checkExit(int n) {
    }

    public void checkLink(String string) {
    }

    public void checkListen(int n) {
    }

    public void checkMemberAccess(Class<?> class_, int n) {
    }

    public void checkMulticast(InetAddress inetAddress) {
    }

    @Deprecated
    public void checkMulticast(InetAddress inetAddress, byte by) {
    }

    public void checkPackageAccess(String string) {
    }

    public void checkPackageDefinition(String string) {
    }

    public void checkPermission(Permission permission) {
    }

    public void checkPermission(Permission permission, Object object) {
    }

    public void checkPrintJobAccess() {
    }

    public void checkPropertiesAccess() {
    }

    public void checkPropertyAccess(String string) {
    }

    public void checkRead(FileDescriptor fileDescriptor) {
    }

    public void checkRead(String string) {
    }

    public void checkRead(String string, Object object) {
    }

    public void checkSecurityAccess(String string) {
    }

    public void checkSetFactory() {
    }

    public void checkSystemClipboardAccess() {
    }

    public boolean checkTopLevelWindow(Object object) {
        return true;
    }

    public void checkWrite(FileDescriptor fileDescriptor) {
    }

    public void checkWrite(String string) {
    }

    @Deprecated
    protected int classDepth(String string) {
        return -1;
    }

    @Deprecated
    protected int classLoaderDepth() {
        return -1;
    }

    @Deprecated
    protected ClassLoader currentClassLoader() {
        return null;
    }

    @Deprecated
    protected Class<?> currentLoadedClass() {
        return null;
    }

    protected Class[] getClassContext() {
        return null;
    }

    @Deprecated
    public boolean getInCheck() {
        return this.inCheck;
    }

    public Object getSecurityContext() {
        return null;
    }

    public ThreadGroup getThreadGroup() {
        return Thread.currentThread().getThreadGroup();
    }

    @Deprecated
    protected boolean inClass(String string) {
        return false;
    }

    @Deprecated
    protected boolean inClassLoader() {
        return false;
    }
}

