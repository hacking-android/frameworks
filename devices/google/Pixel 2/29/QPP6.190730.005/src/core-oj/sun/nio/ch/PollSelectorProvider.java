/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.PollSelectorImpl;
import sun.nio.ch.SelectorProviderImpl;

public class PollSelectorProvider
extends SelectorProviderImpl {
    @Override
    public Channel inheritedChannel() throws IOException {
        return null;
    }

    @Override
    public AbstractSelector openSelector() throws IOException {
        return new PollSelectorImpl(this);
    }
}

