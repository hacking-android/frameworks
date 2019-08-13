/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

class DeleteOnExitHook {
    private static LinkedHashSet<String> files = new LinkedHashSet();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run() {
                DeleteOnExitHook.runHooks();
            }
        });
    }

    private DeleteOnExitHook() {
    }

    static void add(String object) {
        synchronized (DeleteOnExitHook.class) {
            if (files != null) {
                files.add((String)object);
                return;
            }
            object = new IllegalStateException("Shutdown in progress");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void runHooks() {
        Object object;
        synchronized (DeleteOnExitHook.class) {
            object = files;
            files = null;
        }
        object = new ArrayList<String>((Collection<String>)object);
        Collections.reverse(object);
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            new File((String)object.next()).delete();
        }
        return;
    }

}

