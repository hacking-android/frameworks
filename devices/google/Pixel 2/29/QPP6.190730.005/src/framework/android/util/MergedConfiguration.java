/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;

public class MergedConfiguration
implements Parcelable {
    public static final Parcelable.Creator<MergedConfiguration> CREATOR = new Parcelable.Creator<MergedConfiguration>(){

        @Override
        public MergedConfiguration createFromParcel(Parcel parcel) {
            return new MergedConfiguration(parcel);
        }

        public MergedConfiguration[] newArray(int n) {
            return new MergedConfiguration[n];
        }
    };
    private Configuration mGlobalConfig = new Configuration();
    private Configuration mMergedConfig = new Configuration();
    private Configuration mOverrideConfig = new Configuration();

    public MergedConfiguration() {
    }

    public MergedConfiguration(Configuration configuration) {
        this.setGlobalConfiguration(configuration);
    }

    public MergedConfiguration(Configuration configuration, Configuration configuration2) {
        this.setConfiguration(configuration, configuration2);
    }

    private MergedConfiguration(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public MergedConfiguration(MergedConfiguration mergedConfiguration) {
        this.setConfiguration(mergedConfiguration.getGlobalConfiguration(), mergedConfiguration.getOverrideConfiguration());
    }

    private void updateMergedConfig() {
        this.mMergedConfig.setTo(this.mGlobalConfig);
        this.mMergedConfig.updateFrom(this.mOverrideConfig);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("mGlobalConfig=");
        stringBuilder.append(this.mGlobalConfig);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("mOverrideConfig=");
        stringBuilder.append(this.mOverrideConfig);
        printWriter.println(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        if (!(object instanceof MergedConfiguration)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        return this.mMergedConfig.equals(((MergedConfiguration)object).mMergedConfig);
    }

    public Configuration getGlobalConfiguration() {
        return this.mGlobalConfig;
    }

    public Configuration getMergedConfiguration() {
        return this.mMergedConfig;
    }

    public Configuration getOverrideConfiguration() {
        return this.mOverrideConfig;
    }

    public int hashCode() {
        return this.mMergedConfig.hashCode();
    }

    public void readFromParcel(Parcel parcel) {
        this.mGlobalConfig = (Configuration)parcel.readParcelable(Configuration.class.getClassLoader());
        this.mOverrideConfig = (Configuration)parcel.readParcelable(Configuration.class.getClassLoader());
        this.mMergedConfig = (Configuration)parcel.readParcelable(Configuration.class.getClassLoader());
    }

    public void setConfiguration(Configuration configuration, Configuration configuration2) {
        this.mGlobalConfig.setTo(configuration);
        this.mOverrideConfig.setTo(configuration2);
        this.updateMergedConfig();
    }

    public void setGlobalConfiguration(Configuration configuration) {
        this.mGlobalConfig.setTo(configuration);
        this.updateMergedConfig();
    }

    public void setOverrideConfiguration(Configuration configuration) {
        this.mOverrideConfig.setTo(configuration);
        this.updateMergedConfig();
    }

    public void setTo(MergedConfiguration mergedConfiguration) {
        this.setConfiguration(mergedConfiguration.mGlobalConfig, mergedConfiguration.mOverrideConfig);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mGlobalConfig=");
        stringBuilder.append(this.mGlobalConfig);
        stringBuilder.append(" mOverrideConfig=");
        stringBuilder.append(this.mOverrideConfig);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void unset() {
        this.mGlobalConfig.unset();
        this.mOverrideConfig.unset();
        this.updateMergedConfig();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mGlobalConfig, n);
        parcel.writeParcelable(this.mOverrideConfig, n);
        parcel.writeParcelable(this.mMergedConfig, n);
    }

}

