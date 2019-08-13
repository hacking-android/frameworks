/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.util.prefs.FileSystemPreferences;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

class FileSystemPreferencesFactory
implements PreferencesFactory {
    FileSystemPreferencesFactory() {
    }

    @Override
    public Preferences systemRoot() {
        return FileSystemPreferences.getSystemRoot();
    }

    @Override
    public Preferences userRoot() {
        return FileSystemPreferences.getUserRoot();
    }
}

