/*
 * Decompiled with CFR 0.145.
 */
package android.net;

public final class TcpRepairWindow {
    public final int maxWindow;
    public final int rcvWnd;
    public final int rcvWndScale;
    public final int rcvWup;
    public final int sndWl1;
    public final int sndWnd;

    public TcpRepairWindow(int n, int n2, int n3, int n4, int n5, int n6) {
        this.sndWl1 = n;
        this.sndWnd = n2;
        this.maxWindow = n3;
        this.rcvWnd = n4;
        this.rcvWup = n5;
        this.rcvWndScale = n6;
    }
}

