/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.-$
 *  android.hardware.radio.-$$Lambda
 *  android.hardware.radio.-$$Lambda$ProgramList
 *  android.hardware.radio.-$$Lambda$ProgramList$GfCj9jJ5znxw2TV4c2uykq35dgI
 */
package android.hardware.radio;

import android.annotation.SystemApi;
import android.hardware.radio.-$;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.Utils;
import android.hardware.radio._$$Lambda$1DA3e7WM2G0cVcFyFUhdDG0CYnw;
import android.hardware.radio._$$Lambda$ProgramList$1$DVvry5MfhR6n8H2EZn67rvuhllI;
import android.hardware.radio._$$Lambda$ProgramList$1$a_xWqo5pESOZhcJIWvpiCd2AXmY;
import android.hardware.radio._$$Lambda$ProgramList$F_JpTj3vYguKIUQbnLbTePTuqUE;
import android.hardware.radio._$$Lambda$ProgramList$GfCj9jJ5znxw2TV4c2uykq35dgI;
import android.hardware.radio._$$Lambda$ProgramList$aDYMynqVdAUqeKXIxfNtN1u67zs;
import android.hardware.radio._$$Lambda$ProgramList$eY050tMTgAcGV9hiWR_UDxhkfhw;
import android.hardware.radio._$$Lambda$ProgramList$fDnoTVk5UB7qTfD9S7SYPcadYn0;
import android.hardware.radio._$$Lambda$ProgramList$fHYelmhnUsVTYl6dFj75fMqCjGs;
import android.hardware.radio._$$Lambda$ProgramList$pKu0Zp5jwjix619hfB_Imj8Ke_g;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SystemApi
public final class ProgramList
implements AutoCloseable {
    private boolean mIsClosed = false;
    private boolean mIsComplete = false;
    private final List<ListCallback> mListCallbacks = new ArrayList<ListCallback>();
    private final Object mLock = new Object();
    private OnCloseListener mOnCloseListener;
    private final List<OnCompleteListener> mOnCompleteListeners = new ArrayList<OnCompleteListener>();
    private final Map<ProgramSelector.Identifier, RadioManager.ProgramInfo> mPrograms = new HashMap<ProgramSelector.Identifier, RadioManager.ProgramInfo>();

    ProgramList() {
    }

    static /* synthetic */ void lambda$addOnCompleteListener$0(Executor executor, OnCompleteListener onCompleteListener) {
        Objects.requireNonNull(onCompleteListener);
        executor.execute(new _$$Lambda$1DA3e7WM2G0cVcFyFUhdDG0CYnw(onCompleteListener));
    }

    static /* synthetic */ void lambda$apply$4(OnCompleteListener onCompleteListener) {
        onCompleteListener.onComplete();
    }

    static /* synthetic */ void lambda$putLocked$5(ProgramSelector.Identifier identifier, ListCallback listCallback) {
        listCallback.onItemChanged(identifier);
    }

    static /* synthetic */ void lambda$removeLocked$6(ProgramSelector.Identifier identifier, ListCallback listCallback) {
        listCallback.onItemRemoved(identifier);
    }

    private void putLocked(RadioManager.ProgramInfo parcelable) {
        ProgramSelector.Identifier identifier = parcelable.getSelector().getPrimaryId();
        this.mPrograms.put(Objects.requireNonNull(identifier), (RadioManager.ProgramInfo)parcelable);
        parcelable = parcelable.getSelector().getPrimaryId();
        this.mListCallbacks.forEach(new _$$Lambda$ProgramList$fDnoTVk5UB7qTfD9S7SYPcadYn0((ProgramSelector.Identifier)parcelable));
    }

    private void removeLocked(ProgramSelector.Identifier parcelable) {
        if ((parcelable = this.mPrograms.remove(Objects.requireNonNull(parcelable))) == null) {
            return;
        }
        parcelable = ((RadioManager.ProgramInfo)parcelable).getSelector().getPrimaryId();
        this.mListCallbacks.forEach(new _$$Lambda$ProgramList$fHYelmhnUsVTYl6dFj75fMqCjGs((ProgramSelector.Identifier)parcelable));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnCompleteListener(OnCompleteListener onCompleteListener) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.add(Objects.requireNonNull(onCompleteListener));
            if (this.mIsComplete) {
                onCompleteListener.onComplete();
            }
            return;
        }
    }

    public void addOnCompleteListener(Executor executor, OnCompleteListener onCompleteListener) {
        this.addOnCompleteListener(new _$$Lambda$ProgramList$aDYMynqVdAUqeKXIxfNtN1u67zs(executor, onCompleteListener));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void apply(Chunk chunk) {
        Object object = this.mLock;
        synchronized (object) {
            Object object2;
            Stream stream;
            if (this.mIsClosed) {
                return;
            }
            this.mIsComplete = false;
            if (chunk.isPurge()) {
                stream = new Stream(this.mPrograms.keySet());
                object2 = stream.stream();
                stream = new Stream(this);
                object2.forEach(stream);
            }
            stream = chunk.getRemoved().stream();
            object2 = new _$$Lambda$ProgramList$pKu0Zp5jwjix619hfB_Imj8Ke_g(this);
            stream.forEach(object2);
            stream = chunk.getModified().stream();
            object2 = new _$$Lambda$ProgramList$eY050tMTgAcGV9hiWR_UDxhkfhw(this);
            stream.forEach(object2);
            if (chunk.isComplete()) {
                this.mIsComplete = true;
                this.mOnCompleteListeners.forEach((Consumer<OnCompleteListener>)_$$Lambda$ProgramList$GfCj9jJ5znxw2TV4c2uykq35dgI.INSTANCE);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIsClosed) {
                return;
            }
            this.mIsClosed = true;
            this.mPrograms.clear();
            this.mListCallbacks.clear();
            this.mOnCompleteListeners.clear();
            if (this.mOnCloseListener != null) {
                this.mOnCloseListener.onClose();
                this.mOnCloseListener = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public RadioManager.ProgramInfo get(ProgramSelector.Identifier parcelable) {
        Object object = this.mLock;
        synchronized (object) {
            return this.mPrograms.get(Objects.requireNonNull(parcelable));
        }
    }

    public /* synthetic */ void lambda$apply$1$ProgramList(ProgramSelector.Identifier identifier) {
        this.removeLocked(identifier);
    }

    public /* synthetic */ void lambda$apply$2$ProgramList(ProgramSelector.Identifier identifier) {
        this.removeLocked(identifier);
    }

    public /* synthetic */ void lambda$apply$3$ProgramList(RadioManager.ProgramInfo programInfo) {
        this.putLocked(programInfo);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerListCallback(ListCallback listCallback) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.add(Objects.requireNonNull(listCallback));
            return;
        }
    }

    public void registerListCallback(final Executor executor, final ListCallback listCallback) {
        this.registerListCallback(new ListCallback(){

            static /* synthetic */ void lambda$onItemChanged$0(ListCallback listCallback2, ProgramSelector.Identifier identifier) {
                listCallback2.onItemChanged(identifier);
            }

            static /* synthetic */ void lambda$onItemRemoved$1(ListCallback listCallback2, ProgramSelector.Identifier identifier) {
                listCallback2.onItemRemoved(identifier);
            }

            @Override
            public void onItemChanged(ProgramSelector.Identifier identifier) {
                executor.execute(new _$$Lambda$ProgramList$1$DVvry5MfhR6n8H2EZn67rvuhllI(listCallback, identifier));
            }

            @Override
            public void onItemRemoved(ProgramSelector.Identifier identifier) {
                executor.execute(new _$$Lambda$ProgramList$1$a_xWqo5pESOZhcJIWvpiCd2AXmY(listCallback, identifier));
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeOnCompleteListener(OnCompleteListener onCompleteListener) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIsClosed) {
                return;
            }
            this.mOnCompleteListeners.remove(Objects.requireNonNull(onCompleteListener));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setOnCloseListener(OnCloseListener object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mOnCloseListener == null) {
                this.mOnCloseListener = object;
                return;
            }
            object = new IllegalStateException("Close callback is already set");
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<RadioManager.ProgramInfo> toList() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mPrograms.values().stream().collect(Collectors.toList());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterListCallback(ListCallback listCallback) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIsClosed) {
                return;
            }
            this.mListCallbacks.remove(Objects.requireNonNull(listCallback));
            return;
        }
    }

    public static final class Chunk
    implements Parcelable {
        public static final Parcelable.Creator<Chunk> CREATOR = new Parcelable.Creator<Chunk>(){

            @Override
            public Chunk createFromParcel(Parcel parcel) {
                return new Chunk(parcel);
            }

            public Chunk[] newArray(int n) {
                return new Chunk[n];
            }
        };
        private final boolean mComplete;
        private final Set<RadioManager.ProgramInfo> mModified;
        private final boolean mPurge;
        private final Set<ProgramSelector.Identifier> mRemoved;

        private Chunk(Parcel parcel) {
            byte by = parcel.readByte();
            boolean bl = true;
            boolean bl2 = by != 0;
            this.mPurge = bl2;
            bl2 = parcel.readByte() != 0 ? bl : false;
            this.mComplete = bl2;
            this.mModified = Utils.createSet(parcel, RadioManager.ProgramInfo.CREATOR);
            this.mRemoved = Utils.createSet(parcel, ProgramSelector.Identifier.CREATOR);
        }

        public Chunk(boolean bl, boolean bl2, Set<RadioManager.ProgramInfo> set, Set<ProgramSelector.Identifier> set2) {
            this.mPurge = bl;
            this.mComplete = bl2;
            if (set == null) {
                set = Collections.emptySet();
            }
            this.mModified = set;
            if (set2 == null) {
                set2 = Collections.emptySet();
            }
            this.mRemoved = set2;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Set<RadioManager.ProgramInfo> getModified() {
            return this.mModified;
        }

        public Set<ProgramSelector.Identifier> getRemoved() {
            return this.mRemoved;
        }

        public boolean isComplete() {
            return this.mComplete;
        }

        public boolean isPurge() {
            return this.mPurge;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeByte((byte)(this.mPurge ? 1 : 0));
            parcel.writeByte((byte)(this.mComplete ? 1 : 0));
            Utils.writeSet(parcel, this.mModified);
            Utils.writeSet(parcel, this.mRemoved);
        }

    }

    public static final class Filter
    implements Parcelable {
        public static final Parcelable.Creator<Filter> CREATOR = new Parcelable.Creator<Filter>(){

            @Override
            public Filter createFromParcel(Parcel parcel) {
                return new Filter(parcel);
            }

            public Filter[] newArray(int n) {
                return new Filter[n];
            }
        };
        private final boolean mExcludeModifications;
        private final Set<Integer> mIdentifierTypes;
        private final Set<ProgramSelector.Identifier> mIdentifiers;
        private final boolean mIncludeCategories;
        private final Map<String, String> mVendorFilter;

        public Filter() {
            this.mIdentifierTypes = Collections.emptySet();
            this.mIdentifiers = Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = null;
        }

        private Filter(Parcel parcel) {
            this.mIdentifierTypes = Utils.createIntSet(parcel);
            this.mIdentifiers = Utils.createSet(parcel, ProgramSelector.Identifier.CREATOR);
            byte by = parcel.readByte();
            boolean bl = true;
            boolean bl2 = by != 0;
            this.mIncludeCategories = bl2;
            bl2 = parcel.readByte() != 0 ? bl : false;
            this.mExcludeModifications = bl2;
            this.mVendorFilter = Utils.readStringMap(parcel);
        }

        public Filter(Map<String, String> map) {
            this.mIdentifierTypes = Collections.emptySet();
            this.mIdentifiers = Collections.emptySet();
            this.mIncludeCategories = false;
            this.mExcludeModifications = false;
            this.mVendorFilter = map;
        }

        public Filter(Set<Integer> set, Set<ProgramSelector.Identifier> set2, boolean bl, boolean bl2) {
            this.mIdentifierTypes = Objects.requireNonNull(set);
            this.mIdentifiers = Objects.requireNonNull(set2);
            this.mIncludeCategories = bl;
            this.mExcludeModifications = bl2;
            this.mVendorFilter = null;
        }

        public boolean areCategoriesIncluded() {
            return this.mIncludeCategories;
        }

        public boolean areModificationsExcluded() {
            return this.mExcludeModifications;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Set<Integer> getIdentifierTypes() {
            return this.mIdentifierTypes;
        }

        public Set<ProgramSelector.Identifier> getIdentifiers() {
            return this.mIdentifiers;
        }

        public Map<String, String> getVendorFilter() {
            return this.mVendorFilter;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            Utils.writeIntSet(parcel, this.mIdentifierTypes);
            Utils.writeSet(parcel, this.mIdentifiers);
            parcel.writeByte((byte)(this.mIncludeCategories ? 1 : 0));
            parcel.writeByte((byte)(this.mExcludeModifications ? 1 : 0));
            Utils.writeStringMap(parcel, this.mVendorFilter);
        }

    }

    public static abstract class ListCallback {
        public void onItemChanged(ProgramSelector.Identifier identifier) {
        }

        public void onItemRemoved(ProgramSelector.Identifier identifier) {
        }
    }

    static interface OnCloseListener {
        public void onClose();
    }

    public static interface OnCompleteListener {
        public void onComplete();
    }

}

