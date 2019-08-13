/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.PollSelectorProvider;

public class DefaultSelectorProvider {
    private DefaultSelectorProvider() {
    }

    public static SelectorProvider create() {
        return new PollSelectorProvider();
    }
}

