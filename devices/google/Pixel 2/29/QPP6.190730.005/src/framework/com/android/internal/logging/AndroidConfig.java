/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.logging;

import com.android.internal.logging.AndroidHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidConfig {
    public AndroidConfig() {
        try {
            Logger logger = Logger.getLogger("");
            AndroidHandler androidHandler = new AndroidHandler();
            logger.addHandler(androidHandler);
            logger.setLevel(Level.INFO);
            Logger.getLogger("org.apache").setLevel(Level.WARNING);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

