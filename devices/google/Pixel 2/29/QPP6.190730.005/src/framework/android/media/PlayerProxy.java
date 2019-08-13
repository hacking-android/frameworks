/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.media.AudioPlaybackConfiguration;
import android.media.IPlayer;
import android.media.VolumeShaper;
import android.os.RemoteException;

@SystemApi
public class PlayerProxy {
    private static final boolean DEBUG = false;
    private static final String TAG = "PlayerProxy";
    private final AudioPlaybackConfiguration mConf;

    PlayerProxy(AudioPlaybackConfiguration audioPlaybackConfiguration) {
        if (audioPlaybackConfiguration != null) {
            this.mConf = audioPlaybackConfiguration;
            return;
        }
        throw new IllegalArgumentException("Illegal null AudioPlaybackConfiguration");
    }

    public void applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        try {
            this.mConf.getIPlayer().applyVolumeShaper(configuration, operation);
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for applyVolumeShaper operation, player already released?", exception);
        }
    }

    @SystemApi
    public void pause() {
        try {
            this.mConf.getIPlayer().pause();
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for pause operation, player already released?", exception);
        }
    }

    @SystemApi
    public void setPan(float f) {
        try {
            this.mConf.getIPlayer().setPan(f);
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for setPan operation, player already released?", exception);
        }
    }

    @SystemApi
    public void setStartDelayMs(int n) {
        try {
            this.mConf.getIPlayer().setStartDelayMs(n);
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for setStartDelayMs operation, player already released?", exception);
        }
    }

    @SystemApi
    public void setVolume(float f) {
        try {
            this.mConf.getIPlayer().setVolume(f);
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for setVolume operation, player already released?", exception);
        }
    }

    @SystemApi
    public void start() {
        try {
            this.mConf.getIPlayer().start();
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for start operation, player already released?", exception);
        }
    }

    @SystemApi
    public void stop() {
        try {
            this.mConf.getIPlayer().stop();
            return;
        }
        catch (RemoteException | NullPointerException exception) {
            throw new IllegalStateException("No player to proxy for stop operation, player already released?", exception);
        }
    }
}

