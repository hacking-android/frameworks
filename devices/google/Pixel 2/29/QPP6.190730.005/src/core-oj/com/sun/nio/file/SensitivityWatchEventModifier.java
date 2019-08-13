/*
 * Decompiled with CFR 0.145.
 */
package com.sun.nio.file;

import java.nio.file.WatchEvent;

public enum SensitivityWatchEventModifier implements WatchEvent.Modifier
{
    HIGH(2),
    MEDIUM(10),
    LOW(30);
    
    private final int sensitivity;

    private SensitivityWatchEventModifier(int n2) {
        this.sensitivity = n2;
    }

    public int sensitivityValueInSeconds() {
        return this.sensitivity;
    }
}

