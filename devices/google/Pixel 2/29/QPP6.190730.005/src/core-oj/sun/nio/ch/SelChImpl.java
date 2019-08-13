/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.Channel;
import sun.nio.ch.SelectionKeyImpl;

public interface SelChImpl
extends Channel {
    public FileDescriptor getFD();

    public int getFDVal();

    public void kill() throws IOException;

    public void translateAndSetInterestOps(int var1, SelectionKeyImpl var2);

    public boolean translateAndSetReadyOps(int var1, SelectionKeyImpl var2);

    public boolean translateAndUpdateReadyOps(int var1, SelectionKeyImpl var2);

    public int validOps();
}

