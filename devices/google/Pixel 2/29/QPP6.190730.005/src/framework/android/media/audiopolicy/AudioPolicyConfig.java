/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class AudioPolicyConfig
implements Parcelable {
    public static final Parcelable.Creator<AudioPolicyConfig> CREATOR = new Parcelable.Creator<AudioPolicyConfig>(){

        @Override
        public AudioPolicyConfig createFromParcel(Parcel parcel) {
            return new AudioPolicyConfig(parcel);
        }

        public AudioPolicyConfig[] newArray(int n) {
            return new AudioPolicyConfig[n];
        }
    };
    private static final String TAG = "AudioPolicyConfig";
    protected int mDuckingPolicy = 0;
    private int mMixCounter = 0;
    protected final ArrayList<AudioMix> mMixes;
    private String mRegistrationId = null;

    protected AudioPolicyConfig(AudioPolicyConfig audioPolicyConfig) {
        this.mMixes = audioPolicyConfig.mMixes;
    }

    private AudioPolicyConfig(Parcel parcel) {
        this.mMixes = new ArrayList();
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            AudioMix.Builder builder = new AudioMix.Builder();
            builder.setRouteFlags(parcel.readInt());
            builder.setCallbackFlags(parcel.readInt());
            builder.setDevice(parcel.readInt(), parcel.readString());
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            builder.setFormat(new AudioFormat.Builder().setSampleRate(n2).setChannelMask(n4).setEncoding(n3).build());
            AudioMixingRule.Builder builder2 = new AudioMixingRule.Builder();
            builder2.allowPrivilegedPlaybackCapture(parcel.readBoolean());
            n4 = parcel.readInt();
            for (n3 = 0; n3 < n4; ++n3) {
                builder2.addRuleFromParcel(parcel);
            }
            builder.setMixingRule(builder2.build());
            this.mMixes.add(builder.build());
        }
    }

    AudioPolicyConfig(ArrayList<AudioMix> arrayList) {
        this.mMixes = arrayList;
    }

    private static String mixTypeId(int n) {
        if (n == 0) {
            return "p";
        }
        if (n == 1) {
            return "r";
        }
        return "i";
    }

    private void setMixRegistration(AudioMix audioMix) {
        if (!this.mRegistrationId.isEmpty()) {
            if ((audioMix.getRouteFlags() & 2) == 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mRegistrationId);
                stringBuilder.append("mix");
                stringBuilder.append(AudioPolicyConfig.mixTypeId(audioMix.getMixType()));
                stringBuilder.append(":");
                stringBuilder.append(this.mMixCounter);
                audioMix.setRegistration(stringBuilder.toString());
            } else if ((audioMix.getRouteFlags() & 1) == 1) {
                audioMix.setRegistration(audioMix.mDeviceAddress);
            }
        } else {
            audioMix.setRegistration("");
        }
        ++this.mMixCounter;
    }

    @GuardedBy(value={"mMixes"})
    protected void add(ArrayList<AudioMix> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            AudioMix audioMix = (AudioMix)object.next();
            this.setMixRegistration(audioMix);
            this.mMixes.add(audioMix);
        }
    }

    public void addMix(AudioMix audioMix) throws IllegalArgumentException {
        if (audioMix != null) {
            this.mMixes.add(audioMix);
            return;
        }
        throw new IllegalArgumentException("Illegal null AudioMix argument");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<AudioMix> getMixes() {
        return this.mMixes;
    }

    protected String getRegistration() {
        return this.mRegistrationId;
    }

    public int hashCode() {
        return Objects.hash(this.mMixes);
    }

    @GuardedBy(value={"mMixes"})
    protected void remove(ArrayList<AudioMix> object) {
        Iterator<AudioMix> iterator = ((ArrayList)object).iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            this.mMixes.remove(object);
        }
    }

    protected void setRegistration(String object) {
        CharSequence charSequence = this.mRegistrationId;
        boolean bl = false;
        boolean bl2 = charSequence == null || ((String)charSequence).isEmpty();
        if (object == null || ((String)object).isEmpty()) {
            bl = true;
        }
        if (!(bl2 || bl || this.mRegistrationId.equals(object))) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid registration transition from ");
            ((StringBuilder)charSequence).append(this.mRegistrationId);
            ((StringBuilder)charSequence).append(" to ");
            ((StringBuilder)charSequence).append((String)object);
            Log.e(TAG, ((StringBuilder)charSequence).toString());
            return;
        }
        if (object == null) {
            object = "";
        }
        this.mRegistrationId = object;
        object = this.mMixes.iterator();
        while (object.hasNext()) {
            this.setMixRegistration((AudioMix)object.next());
        }
    }

    public String toLogFriendlyString() {
        String string2 = new String("android.media.audiopolicy.AudioPolicyConfig:\n");
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(this.mMixes.size());
        ((StringBuilder)charSequence).append(" AudioMix: ");
        ((StringBuilder)charSequence).append(this.mRegistrationId);
        ((StringBuilder)charSequence).append("\n");
        charSequence = ((StringBuilder)charSequence).toString();
        for (AudioMix audioMix : this.mMixes) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)charSequence);
            ((StringBuilder)object2).append("* route flags=0x");
            ((StringBuilder)object2).append(Integer.toHexString(audioMix.getRouteFlags()));
            ((StringBuilder)object2).append("\n");
            charSequence = ((StringBuilder)object2).toString();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)charSequence);
            ((StringBuilder)object2).append("  rate=");
            ((StringBuilder)object2).append(audioMix.getFormat().getSampleRate());
            ((StringBuilder)object2).append("Hz\n");
            object2 = ((StringBuilder)object2).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object2);
            ((StringBuilder)charSequence).append("  encoding=");
            ((StringBuilder)charSequence).append(audioMix.getFormat().getEncoding());
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)charSequence);
            ((StringBuilder)object2).append("  channels=0x");
            charSequence = ((StringBuilder)object2).toString();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)charSequence);
            ((StringBuilder)object2).append(Integer.toHexString(audioMix.getFormat().getChannelMask()).toUpperCase());
            ((StringBuilder)object2).append("\n");
            charSequence = ((StringBuilder)object2).toString();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)charSequence);
            ((StringBuilder)object2).append("  ignore playback capture opt out=");
            ((StringBuilder)object2).append(audioMix.getRule().allowPrivilegedPlaybackCapture());
            ((StringBuilder)object2).append("\n");
            charSequence = ((StringBuilder)object2).toString();
            for (Object object2 : audioMix.getRule().getCriteria()) {
                CharSequence charSequence2;
                int n = ((AudioMixingRule.AudioMixMatchCriterion)object2).mRule;
                if (n != 1) {
                    if (n != 2) {
                        if (n != 4) {
                            if (n != 32772) {
                                switch (n) {
                                    default: {
                                        object2 = new StringBuilder();
                                        ((StringBuilder)object2).append((String)charSequence);
                                        ((StringBuilder)object2).append("invalid rule!");
                                        charSequence = ((StringBuilder)object2).toString();
                                        break;
                                    }
                                    case 32770: {
                                        charSequence2 = new StringBuilder();
                                        ((StringBuilder)charSequence2).append((String)charSequence);
                                        ((StringBuilder)charSequence2).append("  exclude capture preset ");
                                        charSequence2 = ((StringBuilder)charSequence2).toString();
                                        charSequence = new StringBuilder();
                                        ((StringBuilder)charSequence).append((String)charSequence2);
                                        ((StringBuilder)charSequence).append(((AudioMixingRule.AudioMixMatchCriterion)object2).mAttr.getCapturePreset());
                                        charSequence = ((StringBuilder)charSequence).toString();
                                        break;
                                    }
                                    case 32769: {
                                        charSequence2 = new StringBuilder();
                                        ((StringBuilder)charSequence2).append((String)charSequence);
                                        ((StringBuilder)charSequence2).append("  exclude usage ");
                                        charSequence = ((StringBuilder)charSequence2).toString();
                                        charSequence2 = new StringBuilder();
                                        ((StringBuilder)charSequence2).append((String)charSequence);
                                        ((StringBuilder)charSequence2).append(((AudioMixingRule.AudioMixMatchCriterion)object2).mAttr.usageToString());
                                        charSequence = ((StringBuilder)charSequence2).toString();
                                        break;
                                    }
                                }
                            } else {
                                charSequence2 = new StringBuilder();
                                ((StringBuilder)charSequence2).append((String)charSequence);
                                ((StringBuilder)charSequence2).append("  exclude UID ");
                                charSequence = ((StringBuilder)charSequence2).toString();
                                charSequence2 = new StringBuilder();
                                ((StringBuilder)charSequence2).append((String)charSequence);
                                ((StringBuilder)charSequence2).append(((AudioMixingRule.AudioMixMatchCriterion)object2).mIntProp);
                                charSequence = ((StringBuilder)charSequence2).toString();
                            }
                        } else {
                            charSequence2 = new StringBuilder();
                            ((StringBuilder)charSequence2).append((String)charSequence);
                            ((StringBuilder)charSequence2).append("  match UID ");
                            charSequence = ((StringBuilder)charSequence2).toString();
                            charSequence2 = new StringBuilder();
                            ((StringBuilder)charSequence2).append((String)charSequence);
                            ((StringBuilder)charSequence2).append(((AudioMixingRule.AudioMixMatchCriterion)object2).mIntProp);
                            charSequence = ((StringBuilder)charSequence2).toString();
                        }
                    } else {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        ((StringBuilder)charSequence2).append("  match capture preset ");
                        charSequence = ((StringBuilder)charSequence2).toString();
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        ((StringBuilder)charSequence2).append(((AudioMixingRule.AudioMixMatchCriterion)object2).mAttr.getCapturePreset());
                        charSequence = ((StringBuilder)charSequence2).toString();
                    }
                } else {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append("  match usage ");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(((AudioMixingRule.AudioMixMatchCriterion)object2).mAttr.usageToString());
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append("\n");
                charSequence = ((StringBuilder)object2).toString();
            }
        }
        return charSequence;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mMixes.size());
        for (AudioMix audioMix : this.mMixes) {
            parcel.writeInt(audioMix.getRouteFlags());
            parcel.writeInt(audioMix.mCallbackFlags);
            parcel.writeInt(audioMix.mDeviceSystemType);
            parcel.writeString(audioMix.mDeviceAddress);
            parcel.writeInt(audioMix.getFormat().getSampleRate());
            parcel.writeInt(audioMix.getFormat().getEncoding());
            parcel.writeInt(audioMix.getFormat().getChannelMask());
            parcel.writeBoolean(audioMix.getRule().allowPrivilegedPlaybackCapture());
            ArrayList<AudioMixingRule.AudioMixMatchCriterion> arrayList = audioMix.getRule().getCriteria();
            parcel.writeInt(arrayList.size());
            Iterator<AudioMixingRule.AudioMixMatchCriterion> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                iterator.next().writeToParcel(parcel);
            }
        }
    }

}

