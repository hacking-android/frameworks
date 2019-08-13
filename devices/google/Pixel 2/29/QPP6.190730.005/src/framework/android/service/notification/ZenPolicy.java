/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ZenPolicy
implements Parcelable {
    public static final Parcelable.Creator<ZenPolicy> CREATOR = new Parcelable.Creator<ZenPolicy>(){

        @Override
        public ZenPolicy createFromParcel(Parcel parcel) {
            ZenPolicy zenPolicy = new ZenPolicy();
            zenPolicy.mPriorityCategories = parcel.readArrayList(Integer.class.getClassLoader());
            zenPolicy.mVisualEffects = parcel.readArrayList(Integer.class.getClassLoader());
            zenPolicy.mPriorityCalls = parcel.readInt();
            zenPolicy.mPriorityMessages = parcel.readInt();
            return zenPolicy;
        }

        public ZenPolicy[] newArray(int n) {
            return new ZenPolicy[n];
        }
    };
    public static final int PEOPLE_TYPE_ANYONE = 1;
    public static final int PEOPLE_TYPE_CONTACTS = 2;
    public static final int PEOPLE_TYPE_NONE = 4;
    public static final int PEOPLE_TYPE_STARRED = 3;
    public static final int PEOPLE_TYPE_UNSET = 0;
    public static final int PRIORITY_CATEGORY_ALARMS = 5;
    public static final int PRIORITY_CATEGORY_CALLS = 3;
    public static final int PRIORITY_CATEGORY_EVENTS = 1;
    public static final int PRIORITY_CATEGORY_MEDIA = 6;
    public static final int PRIORITY_CATEGORY_MESSAGES = 2;
    public static final int PRIORITY_CATEGORY_REMINDERS = 0;
    public static final int PRIORITY_CATEGORY_REPEAT_CALLERS = 4;
    public static final int PRIORITY_CATEGORY_SYSTEM = 7;
    public static final int STATE_ALLOW = 1;
    public static final int STATE_DISALLOW = 2;
    public static final int STATE_UNSET = 0;
    public static final int VISUAL_EFFECT_AMBIENT = 5;
    public static final int VISUAL_EFFECT_BADGE = 4;
    public static final int VISUAL_EFFECT_FULL_SCREEN_INTENT = 0;
    public static final int VISUAL_EFFECT_LIGHTS = 1;
    public static final int VISUAL_EFFECT_NOTIFICATION_LIST = 6;
    public static final int VISUAL_EFFECT_PEEK = 2;
    public static final int VISUAL_EFFECT_STATUS_BAR = 3;
    private int mPriorityCalls;
    private ArrayList<Integer> mPriorityCategories;
    private int mPriorityMessages;
    private ArrayList<Integer> mVisualEffects;

    public ZenPolicy() {
        Integer n = 0;
        this.mPriorityMessages = 0;
        this.mPriorityCalls = 0;
        this.mPriorityCategories = new ArrayList<Integer>(Collections.nCopies(8, n));
        this.mVisualEffects = new ArrayList<Integer>(Collections.nCopies(7, n));
    }

    private int getZenPolicyPriorityCategoryState(int n) {
        switch (n) {
            default: {
                return -1;
            }
            case 7: {
                return this.getPriorityCategorySystem();
            }
            case 6: {
                return this.getPriorityCategoryMedia();
            }
            case 5: {
                return this.getPriorityCategoryAlarms();
            }
            case 4: {
                return this.getPriorityCategoryRepeatCallers();
            }
            case 3: {
                return this.getPriorityCategoryCalls();
            }
            case 2: {
                return this.getPriorityCategoryMessages();
            }
            case 1: {
                return this.getPriorityCategoryEvents();
            }
            case 0: 
        }
        return this.getPriorityCategoryReminders();
    }

    private int getZenPolicyVisualEffectState(int n) {
        switch (n) {
            default: {
                return -1;
            }
            case 6: {
                return this.getVisualEffectNotificationList();
            }
            case 5: {
                return this.getVisualEffectAmbient();
            }
            case 4: {
                return this.getVisualEffectBadge();
            }
            case 3: {
                return this.getVisualEffectStatusBar();
            }
            case 2: {
                return this.getVisualEffectPeek();
            }
            case 1: {
                return this.getVisualEffectLights();
            }
            case 0: 
        }
        return this.getVisualEffectFullScreenIntent();
    }

    private String indexToCategory(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 7: {
                return "system";
            }
            case 6: {
                return "media";
            }
            case 5: {
                return "alarms";
            }
            case 4: {
                return "repeatCallers";
            }
            case 3: {
                return "calls";
            }
            case 2: {
                return "messages";
            }
            case 1: {
                return "events";
            }
            case 0: 
        }
        return "reminders";
    }

    private String indexToVisualEffect(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 6: {
                return "notificationList";
            }
            case 5: {
                return "ambient";
            }
            case 4: {
                return "badge";
            }
            case 3: {
                return "statusBar";
            }
            case 2: {
                return "peek";
            }
            case 1: {
                return "lights";
            }
            case 0: 
        }
        return "fullScreenIntent";
    }

    private String peopleTypeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("invalidPeopleType{");
                            stringBuilder.append(n);
                            stringBuilder.append("}");
                            return stringBuilder.toString();
                        }
                        return "none";
                    }
                    return "starred_contacts";
                }
                return "contacts";
            }
            return "anyone";
        }
        return "unset";
    }

    private String priorityCategoriesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.mPriorityCategories.size(); ++i) {
            if (this.mPriorityCategories.get(i) == 0) continue;
            stringBuilder.append(this.indexToCategory(i));
            stringBuilder.append("=");
            stringBuilder.append(this.stateToString(this.mPriorityCategories.get(i)));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    private String stateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalidState{");
                    stringBuilder.append(n);
                    stringBuilder.append("}");
                    return stringBuilder.toString();
                }
                return "disallow";
            }
            return "allow";
        }
        return "unset";
    }

    private String visualEffectsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.mVisualEffects.size(); ++i) {
            if (this.mVisualEffects.get(i) == 0) continue;
            stringBuilder.append(this.indexToVisualEffect(i));
            stringBuilder.append("=");
            stringBuilder.append(this.stateToString(this.mVisualEffects.get(i)));
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public void apply(ZenPolicy zenPolicy) {
        int n;
        if (zenPolicy == null) {
            return;
        }
        for (n = 0; n < this.mPriorityCategories.size(); ++n) {
            int n2;
            int n3;
            if (this.mPriorityCategories.get(n) == 2 || (n2 = zenPolicy.mPriorityCategories.get(n).intValue()) == 0) continue;
            this.mPriorityCategories.set(n, n2);
            if (n == 2 && (n3 = this.mPriorityMessages) < (n2 = zenPolicy.mPriorityMessages)) {
                this.mPriorityMessages = n2;
                continue;
            }
            if (n != 3 || (n3 = this.mPriorityCalls) >= (n2 = zenPolicy.mPriorityCalls)) continue;
            this.mPriorityCalls = n2;
        }
        for (n = 0; n < this.mVisualEffects.size(); ++n) {
            if (this.mVisualEffects.get(n) == 2 || zenPolicy.mVisualEffects.get(n) == 0) continue;
            this.mVisualEffects.set(n, zenPolicy.mVisualEffects.get(n));
        }
    }

    public ZenPolicy copy() {
        Parcel parcel = Parcel.obtain();
        try {
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            ZenPolicy zenPolicy = CREATOR.createFromParcel(parcel);
            return zenPolicy;
        }
        finally {
            parcel.recycle();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof ZenPolicy)) {
            return false;
        }
        boolean bl = true;
        if (object == this) {
            return true;
        }
        object = (ZenPolicy)object;
        if (!Objects.equals(((ZenPolicy)object).mPriorityCategories, this.mPriorityCategories) || !Objects.equals(((ZenPolicy)object).mVisualEffects, this.mVisualEffects) || ((ZenPolicy)object).mPriorityCalls != this.mPriorityCalls || ((ZenPolicy)object).mPriorityMessages != this.mPriorityMessages) {
            bl = false;
        }
        return bl;
    }

    public int getPriorityCallSenders() {
        return this.mPriorityCalls;
    }

    public int getPriorityCategoryAlarms() {
        return this.mPriorityCategories.get(5);
    }

    public int getPriorityCategoryCalls() {
        return this.mPriorityCategories.get(3);
    }

    public int getPriorityCategoryEvents() {
        return this.mPriorityCategories.get(1);
    }

    public int getPriorityCategoryMedia() {
        return this.mPriorityCategories.get(6);
    }

    public int getPriorityCategoryMessages() {
        return this.mPriorityCategories.get(2);
    }

    public int getPriorityCategoryReminders() {
        return this.mPriorityCategories.get(0);
    }

    public int getPriorityCategoryRepeatCallers() {
        return this.mPriorityCategories.get(4);
    }

    public int getPriorityCategorySystem() {
        return this.mPriorityCategories.get(7);
    }

    public int getPriorityMessageSenders() {
        return this.mPriorityMessages;
    }

    public int getVisualEffectAmbient() {
        return this.mVisualEffects.get(5);
    }

    public int getVisualEffectBadge() {
        return this.mVisualEffects.get(4);
    }

    public int getVisualEffectFullScreenIntent() {
        return this.mVisualEffects.get(0);
    }

    public int getVisualEffectLights() {
        return this.mVisualEffects.get(1);
    }

    public int getVisualEffectNotificationList() {
        return this.mVisualEffects.get(6);
    }

    public int getVisualEffectPeek() {
        return this.mVisualEffects.get(2);
    }

    public int getVisualEffectStatusBar() {
        return this.mVisualEffects.get(3);
    }

    public int hashCode() {
        return Objects.hash(this.mPriorityCategories, this.mVisualEffects, this.mPriorityCalls, this.mPriorityMessages);
    }

    public boolean isCategoryAllowed(int n, boolean bl) {
        if ((n = this.getZenPolicyPriorityCategoryState(n)) != 1) {
            if (n != 2) {
                return bl;
            }
            return false;
        }
        return true;
    }

    public boolean isVisualEffectAllowed(int n, boolean bl) {
        if ((n = this.getZenPolicyVisualEffectState(n)) != 1) {
            if (n != 2) {
                return bl;
            }
            return false;
        }
        return true;
    }

    public boolean shouldHideAllVisualEffects() {
        for (int i = 0; i < this.mVisualEffects.size(); ++i) {
            if (this.mVisualEffects.get(i) == 2) continue;
            return false;
        }
        return true;
    }

    public boolean shouldShowAllVisualEffects() {
        for (int i = 0; i < this.mVisualEffects.size(); ++i) {
            if (this.mVisualEffects.get(i) == 1) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(ZenPolicy.class.getSimpleName());
        stringBuilder.append('{');
        stringBuilder.append("priorityCategories=[");
        stringBuilder.append(this.priorityCategoriesToString());
        stringBuilder.append("], visualEffects=[");
        stringBuilder.append(this.visualEffectsToString());
        stringBuilder.append("], priorityCalls=");
        stringBuilder.append(this.peopleTypeToString(this.mPriorityCalls));
        stringBuilder.append(", priorityMessages=");
        stringBuilder.append(this.peopleTypeToString(this.mPriorityMessages));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeList(this.mPriorityCategories);
        parcel.writeList(this.mVisualEffects);
        parcel.writeInt(this.mPriorityCalls);
        parcel.writeInt(this.mPriorityMessages);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1159641169921L, this.getPriorityCategoryReminders());
        protoOutputStream.write(1159641169922L, this.getPriorityCategoryEvents());
        protoOutputStream.write(1159641169923L, this.getPriorityCategoryMessages());
        protoOutputStream.write(1159641169924L, this.getPriorityCategoryCalls());
        protoOutputStream.write(1159641169925L, this.getPriorityCategoryRepeatCallers());
        protoOutputStream.write(1159641169926L, this.getPriorityCategoryAlarms());
        protoOutputStream.write(1159641169927L, this.getPriorityCategoryMedia());
        protoOutputStream.write(1159641169928L, this.getPriorityCategorySystem());
        protoOutputStream.write(1159641169929L, this.getVisualEffectFullScreenIntent());
        protoOutputStream.write(1159641169930L, this.getVisualEffectLights());
        protoOutputStream.write(1159641169931L, this.getVisualEffectPeek());
        protoOutputStream.write(1159641169932L, this.getVisualEffectStatusBar());
        protoOutputStream.write(1159641169933L, this.getVisualEffectBadge());
        protoOutputStream.write(1159641169934L, this.getVisualEffectAmbient());
        protoOutputStream.write(1159641169935L, this.getVisualEffectNotificationList());
        protoOutputStream.write(1159641169937L, this.getPriorityMessageSenders());
        protoOutputStream.write(1159641169936L, this.getPriorityCallSenders());
        protoOutputStream.end(l);
    }

    public static final class Builder {
        private ZenPolicy mZenPolicy;

        public Builder() {
            this.mZenPolicy = new ZenPolicy();
        }

        public Builder(ZenPolicy zenPolicy) {
            this.mZenPolicy = zenPolicy != null ? zenPolicy.copy() : new ZenPolicy();
        }

        public Builder allowAlarms(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mPriorityCategories;
            int n = bl ? 1 : 2;
            arrayList.set(5, n);
            return this;
        }

        public Builder allowAllSounds() {
            for (int i = 0; i < this.mZenPolicy.mPriorityCategories.size(); ++i) {
                this.mZenPolicy.mPriorityCategories.set(i, 1);
            }
            this.mZenPolicy.mPriorityMessages = 1;
            this.mZenPolicy.mPriorityCalls = 1;
            return this;
        }

        public Builder allowCalls(int n) {
            if (n == 0) {
                return this.unsetPriorityCategory(3);
            }
            if (n == 4) {
                this.mZenPolicy.mPriorityCategories.set(3, 2);
            } else {
                if (n != 1 && n != 2 && n != 3) {
                    return this;
                }
                this.mZenPolicy.mPriorityCategories.set(3, 1);
            }
            this.mZenPolicy.mPriorityCalls = n;
            return this;
        }

        public Builder allowCategory(int n, boolean bl) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 4) {
                        if (n != 5) {
                            if (n != 6) {
                                if (n == 7) {
                                    this.allowSystem(bl);
                                }
                            } else {
                                this.allowMedia(bl);
                            }
                        } else {
                            this.allowAlarms(bl);
                        }
                    } else {
                        this.allowRepeatCallers(bl);
                    }
                } else {
                    this.allowEvents(bl);
                }
            } else {
                this.allowReminders(bl);
            }
            return this;
        }

        public Builder allowEvents(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mPriorityCategories;
            int n = bl ? 1 : 2;
            arrayList.set(1, n);
            return this;
        }

        public Builder allowMedia(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mPriorityCategories;
            int n = bl ? 1 : 2;
            arrayList.set(6, n);
            return this;
        }

        public Builder allowMessages(int n) {
            if (n == 0) {
                return this.unsetPriorityCategory(2);
            }
            if (n == 4) {
                this.mZenPolicy.mPriorityCategories.set(2, 2);
            } else {
                if (n != 1 && n != 2 && n != 3) {
                    return this;
                }
                this.mZenPolicy.mPriorityCategories.set(2, 1);
            }
            this.mZenPolicy.mPriorityMessages = n;
            return this;
        }

        public Builder allowReminders(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mPriorityCategories;
            int n = bl ? 1 : 2;
            arrayList.set(0, n);
            return this;
        }

        public Builder allowRepeatCallers(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mPriorityCategories;
            int n = bl ? 1 : 2;
            arrayList.set(4, n);
            return this;
        }

        public Builder allowSystem(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mPriorityCategories;
            int n = bl ? 1 : 2;
            arrayList.set(7, n);
            return this;
        }

        public ZenPolicy build() {
            return this.mZenPolicy.copy();
        }

        public Builder disallowAllSounds() {
            for (int i = 0; i < this.mZenPolicy.mPriorityCategories.size(); ++i) {
                this.mZenPolicy.mPriorityCategories.set(i, 2);
            }
            this.mZenPolicy.mPriorityMessages = 4;
            this.mZenPolicy.mPriorityCalls = 4;
            return this;
        }

        public Builder hideAllVisualEffects() {
            for (int i = 0; i < this.mZenPolicy.mVisualEffects.size(); ++i) {
                this.mZenPolicy.mVisualEffects.set(i, 2);
            }
            return this;
        }

        public Builder showAllVisualEffects() {
            for (int i = 0; i < this.mZenPolicy.mVisualEffects.size(); ++i) {
                this.mZenPolicy.mVisualEffects.set(i, 1);
            }
            return this;
        }

        public Builder showBadges(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(4, n);
            return this;
        }

        public Builder showFullScreenIntent(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(0, n);
            return this;
        }

        public Builder showInAmbientDisplay(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(5, n);
            return this;
        }

        public Builder showInNotificationList(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(6, n);
            return this;
        }

        public Builder showLights(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(1, n);
            return this;
        }

        public Builder showPeeking(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(2, n);
            return this;
        }

        public Builder showStatusBarIcons(boolean bl) {
            ArrayList arrayList = this.mZenPolicy.mVisualEffects;
            int n = bl ? 1 : 2;
            arrayList.set(3, n);
            return this;
        }

        public Builder showVisualEffect(int n, boolean bl) {
            switch (n) {
                default: {
                    break;
                }
                case 6: {
                    this.showInNotificationList(bl);
                    break;
                }
                case 5: {
                    this.showInAmbientDisplay(bl);
                    break;
                }
                case 4: {
                    this.showBadges(bl);
                    break;
                }
                case 3: {
                    this.showStatusBarIcons(bl);
                    break;
                }
                case 2: {
                    this.showPeeking(bl);
                    break;
                }
                case 1: {
                    this.showLights(bl);
                    break;
                }
                case 0: {
                    this.showFullScreenIntent(bl);
                }
            }
            return this;
        }

        public Builder unsetPriorityCategory(int n) {
            this.mZenPolicy.mPriorityCategories.set(n, 0);
            if (n == 2) {
                this.mZenPolicy.mPriorityMessages = 0;
            } else if (n == 3) {
                this.mZenPolicy.mPriorityCalls = 0;
            }
            return this;
        }

        public Builder unsetVisualEffect(int n) {
            this.mZenPolicy.mVisualEffects.set(n, 0);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PeopleType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PriorityCategory {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VisualEffect {
    }

}

