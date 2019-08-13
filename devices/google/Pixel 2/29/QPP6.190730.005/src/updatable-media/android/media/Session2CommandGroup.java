/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.media;

import android.media.Session2Command;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class Session2CommandGroup
implements Parcelable {
    public static final Parcelable.Creator<Session2CommandGroup> CREATOR = new Parcelable.Creator<Session2CommandGroup>(){

        public Session2CommandGroup createFromParcel(Parcel parcel) {
            return new Session2CommandGroup(parcel);
        }

        public Session2CommandGroup[] newArray(int n) {
            return new Session2CommandGroup[n];
        }
    };
    private static final String TAG = "Session2CommandGroup";
    Set<Session2Command> mCommands = new HashSet<Session2Command>();

    Session2CommandGroup(Parcel arrparcelable) {
        arrparcelable = arrparcelable.readParcelableArray(Session2Command.class.getClassLoader());
        if (arrparcelable != null) {
            for (Parcelable parcelable : arrparcelable) {
                this.mCommands.add((Session2Command)parcelable);
            }
        }
    }

    Session2CommandGroup(Collection<Session2Command> collection) {
        if (collection != null) {
            this.mCommands.addAll(collection);
        }
    }

    public int describeContents() {
        return 0;
    }

    public Set<Session2Command> getCommands() {
        return new HashSet<Session2Command>(this.mCommands);
    }

    public boolean hasCommand(int n) {
        if (n != 0) {
            Iterator<Session2Command> iterator = this.mCommands.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getCommandCode() != n) continue;
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Use hasCommand(Command) for custom command");
    }

    public boolean hasCommand(Session2Command session2Command) {
        if (session2Command != null) {
            return this.mCommands.contains(session2Command);
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            parcel.writeParcelableArray((Parcelable[])this.mCommands.toArray(new Session2Command[0]), 0);
            return;
        }
        throw new IllegalArgumentException("parcel shouldn't be null");
    }

    public static final class Builder {
        private Set<Session2Command> mCommands;

        public Builder() {
            this.mCommands = new HashSet<Session2Command>();
        }

        public Builder(Session2CommandGroup session2CommandGroup) {
            if (session2CommandGroup != null) {
                this.mCommands = session2CommandGroup.getCommands();
                return;
            }
            throw new IllegalArgumentException("command group shouldn't be null");
        }

        public Builder addCommand(Session2Command session2Command) {
            if (session2Command != null) {
                this.mCommands.add(session2Command);
                return this;
            }
            throw new IllegalArgumentException("command shouldn't be null");
        }

        public Session2CommandGroup build() {
            return new Session2CommandGroup(this.mCommands);
        }

        public Builder removeCommand(Session2Command session2Command) {
            if (session2Command != null) {
                this.mCommands.remove(session2Command);
                return this;
            }
            throw new IllegalArgumentException("command shouldn't be null");
        }
    }

}

