/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsEventDescriptor;
import android.telephony.ims.RcsEventQueryResult;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims._$$Lambda$RcsEventQueryResultDescriptor$0eoTyoA0JNoBx53J3zXvi1fQcnA;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RcsEventQueryResultDescriptor
implements Parcelable {
    public static final Parcelable.Creator<RcsEventQueryResultDescriptor> CREATOR = new Parcelable.Creator<RcsEventQueryResultDescriptor>(){

        @Override
        public RcsEventQueryResultDescriptor createFromParcel(Parcel parcel) {
            return new RcsEventQueryResultDescriptor(parcel);
        }

        public RcsEventQueryResultDescriptor[] newArray(int n) {
            return new RcsEventQueryResultDescriptor[n];
        }
    };
    private final RcsQueryContinuationToken mContinuationToken;
    private final List<RcsEventDescriptor> mEvents;

    protected RcsEventQueryResultDescriptor(Parcel parcel) {
        this.mContinuationToken = (RcsQueryContinuationToken)parcel.readParcelable(RcsQueryContinuationToken.class.getClassLoader());
        this.mEvents = new LinkedList<RcsEventDescriptor>();
        parcel.readList(this.mEvents, null);
    }

    public RcsEventQueryResultDescriptor(RcsQueryContinuationToken rcsQueryContinuationToken, List<RcsEventDescriptor> list) {
        this.mContinuationToken = rcsQueryContinuationToken;
        this.mEvents = list;
    }

    static /* synthetic */ RcsEvent lambda$getRcsEventQueryResult$0(RcsControllerCall rcsControllerCall, RcsEventDescriptor rcsEventDescriptor) {
        return rcsEventDescriptor.createRcsEvent(rcsControllerCall);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected RcsEventQueryResult getRcsEventQueryResult(RcsControllerCall object) {
        object = this.mEvents.stream().map(new _$$Lambda$RcsEventQueryResultDescriptor$0eoTyoA0JNoBx53J3zXvi1fQcnA((RcsControllerCall)object)).collect(Collectors.toList());
        return new RcsEventQueryResult(this.mContinuationToken, (List<RcsEvent>)object);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mContinuationToken, n);
        parcel.writeList(this.mEvents);
    }

}

