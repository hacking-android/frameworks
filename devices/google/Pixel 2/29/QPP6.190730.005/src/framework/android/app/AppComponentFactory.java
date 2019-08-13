/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

public class AppComponentFactory {
    public static final AppComponentFactory DEFAULT = new AppComponentFactory();

    public Activity instantiateActivity(ClassLoader classLoader, String string2, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Activity)classLoader.loadClass(string2).newInstance();
    }

    public Application instantiateApplication(ClassLoader classLoader, String string2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Application)classLoader.loadClass(string2).newInstance();
    }

    public ClassLoader instantiateClassLoader(ClassLoader classLoader, ApplicationInfo applicationInfo) {
        return classLoader;
    }

    public ContentProvider instantiateProvider(ClassLoader classLoader, String string2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (ContentProvider)classLoader.loadClass(string2).newInstance();
    }

    public BroadcastReceiver instantiateReceiver(ClassLoader classLoader, String string2, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (BroadcastReceiver)classLoader.loadClass(string2).newInstance();
    }

    public Service instantiateService(ClassLoader classLoader, String string2, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (Service)classLoader.loadClass(string2).newInstance();
    }
}

