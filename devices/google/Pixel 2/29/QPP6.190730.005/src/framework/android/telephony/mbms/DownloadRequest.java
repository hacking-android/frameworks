/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.annotation.SystemApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.mbms.FileServiceInfo;
import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public final class DownloadRequest
implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new Parcelable.Creator<DownloadRequest>(){

        @Override
        public DownloadRequest createFromParcel(Parcel parcel) {
            return new DownloadRequest(parcel);
        }

        public DownloadRequest[] newArray(int n) {
            return new DownloadRequest[n];
        }
    };
    private static final int CURRENT_VERSION = 1;
    private static final String LOG_TAG = "MbmsDownloadRequest";
    public static final int MAX_APP_INTENT_SIZE = 50000;
    public static final int MAX_DESTINATION_URI_SIZE = 50000;
    private final Uri destinationUri;
    private final String fileServiceId;
    private final String serializedResultIntentForApp;
    private final Uri sourceUri;
    private final int subscriptionId;
    private final int version;

    private DownloadRequest(Parcel parcel) {
        this.fileServiceId = parcel.readString();
        this.sourceUri = (Uri)parcel.readParcelable(this.getClass().getClassLoader());
        this.destinationUri = (Uri)parcel.readParcelable(this.getClass().getClassLoader());
        this.subscriptionId = parcel.readInt();
        this.serializedResultIntentForApp = parcel.readString();
        this.version = parcel.readInt();
    }

    private DownloadRequest(String string2, Uri uri, Uri uri2, int n, String string3, int n2) {
        this.fileServiceId = string2;
        this.sourceUri = uri;
        this.subscriptionId = n;
        this.destinationUri = uri2;
        this.serializedResultIntentForApp = string3;
        this.version = n2;
    }

    public static int getMaxAppIntentSize() {
        return 50000;
    }

    public static int getMaxDestinationUriSize() {
        return 50000;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof DownloadRequest)) {
            return false;
        }
        object = (DownloadRequest)object;
        if (!(this.subscriptionId == ((DownloadRequest)object).subscriptionId && this.version == ((DownloadRequest)object).version && Objects.equals(this.fileServiceId, ((DownloadRequest)object).fileServiceId) && Objects.equals(this.sourceUri, ((DownloadRequest)object).sourceUri) && Objects.equals(this.destinationUri, ((DownloadRequest)object).destinationUri) && Objects.equals(this.serializedResultIntentForApp, ((DownloadRequest)object).serializedResultIntentForApp))) {
            bl = false;
        }
        return bl;
    }

    public Uri getDestinationUri() {
        return this.destinationUri;
    }

    public String getFileServiceId() {
        return this.fileServiceId;
    }

    public String getHash() {
        MessageDigest messageDigest;
        block2 : {
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
                if (this.version < 1) break block2;
                messageDigest.update(this.sourceUri.toString().getBytes(StandardCharsets.UTF_8));
                messageDigest.update(this.destinationUri.toString().getBytes(StandardCharsets.UTF_8));
                String string2 = this.serializedResultIntentForApp;
                if (string2 == null) break block2;
                messageDigest.update(string2.getBytes(StandardCharsets.UTF_8));
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new RuntimeException("Could not get sha256 hash object");
            }
        }
        return Base64.encodeToString(messageDigest.digest(), 10);
    }

    public Intent getIntentForApp() {
        try {
            Intent intent = Intent.parseUri(this.serializedResultIntentForApp, 0);
            return intent;
        }
        catch (URISyntaxException uRISyntaxException) {
            return null;
        }
    }

    public Uri getSourceUri() {
        return this.sourceUri;
    }

    public int getSubscriptionId() {
        return this.subscriptionId;
    }

    public int getVersion() {
        return this.version;
    }

    public int hashCode() {
        return Objects.hash(this.fileServiceId, this.sourceUri, this.destinationUri, this.subscriptionId, this.serializedResultIntentForApp, this.version);
    }

    public byte[] toByteArray() {
        try {
            byte[] arrby = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream((OutputStream)arrby);
            SerializationDataContainer serializationDataContainer = new SerializationDataContainer(this);
            objectOutputStream.writeObject(serializationDataContainer);
            objectOutputStream.flush();
            arrby = arrby.toByteArray();
            return arrby;
        }
        catch (IOException iOException) {
            Log.e(LOG_TAG, "Got IOException trying to serialize opaque data");
            return null;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.fileServiceId);
        parcel.writeParcelable(this.sourceUri, n);
        parcel.writeParcelable(this.destinationUri, n);
        parcel.writeInt(this.subscriptionId);
        parcel.writeString(this.serializedResultIntentForApp);
        parcel.writeInt(this.version);
    }

    public static class Builder {
        private String appIntent;
        private Uri destination;
        private String fileServiceId;
        private Uri source;
        private int subscriptionId;
        private int version = 1;

        public Builder(Uri uri, Uri uri2) {
            if (uri != null && uri2 != null) {
                this.source = uri;
                this.destination = uri2;
                return;
            }
            throw new IllegalArgumentException("Source and destination URIs must be non-null.");
        }

        public static Builder fromDownloadRequest(DownloadRequest downloadRequest) {
            Builder builder = new Builder(downloadRequest.sourceUri, downloadRequest.destinationUri).setServiceId(downloadRequest.fileServiceId).setSubscriptionId(downloadRequest.subscriptionId);
            builder.appIntent = downloadRequest.serializedResultIntentForApp;
            return builder;
        }

        public static Builder fromSerializedRequest(byte[] object) {
            try {
                Object object2 = new ByteArrayInputStream((byte[])object);
                ObjectInputStream objectInputStream = new ObjectInputStream((InputStream)object2);
                object = (SerializationDataContainer)objectInputStream.readObject();
                object2 = new Builder(((SerializationDataContainer)object).source, ((SerializationDataContainer)object).destination);
                ((Builder)object2).version = ((SerializationDataContainer)object).version;
                ((Builder)object2).appIntent = ((SerializationDataContainer)object).appIntent;
                ((Builder)object2).fileServiceId = ((SerializationDataContainer)object).fileServiceId;
                ((Builder)object2).subscriptionId = ((SerializationDataContainer)object).subscriptionId;
                return object2;
            }
            catch (ClassNotFoundException classNotFoundException) {
                Log.e(DownloadRequest.LOG_TAG, "Got ClassNotFoundException trying to parse opaque data");
                throw new IllegalArgumentException(classNotFoundException);
            }
            catch (IOException iOException) {
                Log.e(DownloadRequest.LOG_TAG, "Got IOException trying to parse opaque data");
                throw new IllegalArgumentException(iOException);
            }
        }

        public DownloadRequest build() {
            return new DownloadRequest(this.fileServiceId, this.source, this.destination, this.subscriptionId, this.appIntent, this.version);
        }

        public Builder setAppIntent(Intent intent) {
            this.appIntent = intent.toUri(0);
            if (this.appIntent.length() <= 50000) {
                return this;
            }
            throw new IllegalArgumentException("App intent must not exceed length 50000");
        }

        @SystemApi
        public Builder setServiceId(String string2) {
            this.fileServiceId = string2;
            return this;
        }

        public Builder setServiceInfo(FileServiceInfo fileServiceInfo) {
            this.fileServiceId = fileServiceInfo.getServiceId();
            return this;
        }

        public Builder setSubscriptionId(int n) {
            this.subscriptionId = n;
            return this;
        }
    }

    private static class SerializationDataContainer
    implements Externalizable {
        private String appIntent;
        private Uri destination;
        private String fileServiceId;
        private Uri source;
        private int subscriptionId;
        private int version;

        public SerializationDataContainer() {
        }

        SerializationDataContainer(DownloadRequest downloadRequest) {
            this.fileServiceId = downloadRequest.fileServiceId;
            this.source = downloadRequest.sourceUri;
            this.destination = downloadRequest.destinationUri;
            this.subscriptionId = downloadRequest.subscriptionId;
            this.appIntent = downloadRequest.serializedResultIntentForApp;
            this.version = downloadRequest.version;
        }

        @Override
        public void readExternal(ObjectInput objectInput) throws IOException {
            this.version = objectInput.read();
            this.fileServiceId = objectInput.readUTF();
            this.source = Uri.parse(objectInput.readUTF());
            this.destination = Uri.parse(objectInput.readUTF());
            this.subscriptionId = objectInput.read();
            this.appIntent = objectInput.readUTF();
        }

        @Override
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.write(this.version);
            objectOutput.writeUTF(this.fileServiceId);
            objectOutput.writeUTF(this.source.toString());
            objectOutput.writeUTF(this.destination.toString());
            objectOutput.write(this.subscriptionId);
            objectOutput.writeUTF(this.appIntent);
        }
    }

}

