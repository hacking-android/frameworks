/*
 * Decompiled with CFR 0.145.
 */
package android.app.servertransaction;

import android.app.ClientTransactionHandler;
import android.app.servertransaction.ClientTransactionItem;
import android.app.servertransaction.ObjectPool;
import android.app.servertransaction.PendingTransactionActions;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class ConfigurationChangeItem
extends ClientTransactionItem {
    public static final Parcelable.Creator<ConfigurationChangeItem> CREATOR = new Parcelable.Creator<ConfigurationChangeItem>(){

        @Override
        public ConfigurationChangeItem createFromParcel(Parcel parcel) {
            return new ConfigurationChangeItem(parcel);
        }

        public ConfigurationChangeItem[] newArray(int n) {
            return new ConfigurationChangeItem[n];
        }
    };
    private Configuration mConfiguration;

    private ConfigurationChangeItem() {
    }

    private ConfigurationChangeItem(Parcel parcel) {
        this.mConfiguration = parcel.readTypedObject(Configuration.CREATOR);
    }

    public static ConfigurationChangeItem obtain(Configuration configuration) {
        ConfigurationChangeItem configurationChangeItem;
        ConfigurationChangeItem configurationChangeItem2 = configurationChangeItem = ObjectPool.obtain(ConfigurationChangeItem.class);
        if (configurationChangeItem == null) {
            configurationChangeItem2 = new ConfigurationChangeItem();
        }
        configurationChangeItem2.mConfiguration = configuration;
        return configurationChangeItem2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ConfigurationChangeItem)object;
            return Objects.equals(this.mConfiguration, ((ConfigurationChangeItem)object).mConfiguration);
        }
        return false;
    }

    @Override
    public void execute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleConfigurationChanged(this.mConfiguration);
    }

    public int hashCode() {
        return this.mConfiguration.hashCode();
    }

    @Override
    public void preExecute(ClientTransactionHandler clientTransactionHandler, IBinder iBinder) {
        clientTransactionHandler.updatePendingConfiguration(this.mConfiguration);
    }

    @Override
    public void recycle() {
        this.mConfiguration = null;
        ObjectPool.recycle(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ConfigurationChangeItem{config=");
        stringBuilder.append(this.mConfiguration);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.mConfiguration, n);
    }

}

