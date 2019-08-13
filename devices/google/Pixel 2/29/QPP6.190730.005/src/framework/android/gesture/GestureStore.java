/*
 * Decompiled with CFR 0.145.
 */
package android.gesture;

import android.gesture.Gesture;
import android.gesture.GestureUtils;
import android.gesture.Instance;
import android.gesture.InstanceLearner;
import android.gesture.Learner;
import android.gesture.Prediction;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GestureStore {
    private static final short FILE_FORMAT_VERSION = 1;
    public static final int ORIENTATION_INVARIANT = 1;
    public static final int ORIENTATION_SENSITIVE = 2;
    static final int ORIENTATION_SENSITIVE_4 = 4;
    static final int ORIENTATION_SENSITIVE_8 = 8;
    private static final boolean PROFILE_LOADING_SAVING = false;
    public static final int SEQUENCE_INVARIANT = 1;
    public static final int SEQUENCE_SENSITIVE = 2;
    private boolean mChanged = false;
    private Learner mClassifier = new InstanceLearner();
    private final HashMap<String, ArrayList<Gesture>> mNamedGestures = new HashMap();
    private int mOrientationStyle = 2;
    private int mSequenceType = 2;

    private void readFormatV1(DataInputStream dataInputStream) throws IOException {
        Learner learner = this.mClassifier;
        HashMap<String, ArrayList<Gesture>> hashMap = this.mNamedGestures;
        hashMap.clear();
        int n = dataInputStream.readInt();
        for (int i = 0; i < n; ++i) {
            String string2 = dataInputStream.readUTF();
            int n2 = dataInputStream.readInt();
            ArrayList<Gesture> arrayList = new ArrayList<Gesture>(n2);
            for (int j = 0; j < n2; ++j) {
                Gesture gesture = Gesture.deserialize(dataInputStream);
                arrayList.add(gesture);
                learner.addInstance(Instance.createInstance(this.mSequenceType, this.mOrientationStyle, gesture, string2));
            }
            hashMap.put(string2, arrayList);
        }
    }

    public void addGesture(String string2, Gesture gesture) {
        if (string2 != null && string2.length() != 0) {
            ArrayList<Gesture> arrayList;
            ArrayList<Gesture> arrayList2 = arrayList = this.mNamedGestures.get(string2);
            if (arrayList == null) {
                arrayList2 = new ArrayList();
                this.mNamedGestures.put(string2, arrayList2);
            }
            arrayList2.add(gesture);
            this.mClassifier.addInstance(Instance.createInstance(this.mSequenceType, this.mOrientationStyle, gesture, string2));
            this.mChanged = true;
            return;
        }
    }

    public Set<String> getGestureEntries() {
        return this.mNamedGestures.keySet();
    }

    public ArrayList<Gesture> getGestures(String object) {
        if ((object = this.mNamedGestures.get(object)) != null) {
            return new ArrayList<Gesture>((Collection<Gesture>)object);
        }
        return null;
    }

    Learner getLearner() {
        return this.mClassifier;
    }

    public int getOrientationStyle() {
        return this.mOrientationStyle;
    }

    public int getSequenceType() {
        return this.mSequenceType;
    }

    public boolean hasChanged() {
        return this.mChanged;
    }

    public void load(InputStream inputStream) throws IOException {
        this.load(inputStream, false);
    }

    public void load(InputStream inputStream, boolean bl) throws IOException {
        block10 : {
            InputStream inputStream2;
            InputStream inputStream3;
            block9 : {
                inputStream2 = inputStream3 = null;
                inputStream2 = inputStream3;
                try {
                    if (inputStream instanceof BufferedInputStream) break block9;
                    inputStream2 = inputStream3;
                }
                catch (Throwable throwable) {
                    if (bl) {
                        GestureUtils.closeStream(inputStream2);
                    }
                    throw throwable;
                }
                inputStream = new BufferedInputStream(inputStream, 32768);
            }
            inputStream2 = inputStream3;
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            inputStream2 = inputStream = dataInputStream;
            if (((DataInputStream)inputStream).readShort() != 1) break block10;
            inputStream2 = inputStream;
            this.readFormatV1((DataInputStream)inputStream);
        }
        if (bl) {
            GestureUtils.closeStream(inputStream);
        }
    }

    public ArrayList<Prediction> recognize(Gesture object) {
        object = Instance.createInstance(this.mSequenceType, this.mOrientationStyle, (Gesture)object, null);
        return this.mClassifier.classify(this.mSequenceType, this.mOrientationStyle, ((Instance)object).vector);
    }

    public void removeEntry(String string2) {
        this.mNamedGestures.remove(string2);
        this.mClassifier.removeInstances(string2);
        this.mChanged = true;
    }

    public void removeGesture(String string2, Gesture gesture) {
        ArrayList<Gesture> arrayList = this.mNamedGestures.get(string2);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(gesture);
        if (arrayList.isEmpty()) {
            this.mNamedGestures.remove(string2);
        }
        this.mClassifier.removeInstance(gesture.getID());
        this.mChanged = true;
    }

    public void save(OutputStream outputStream) throws IOException {
        this.save(outputStream, false);
    }

    public void save(OutputStream outputStream, boolean bl) throws IOException {
        block23 : {
            Object object;
            OutputStream outputStream2;
            Object object2;
            block22 : {
                object = null;
                outputStream2 = object;
                object2 = this.mNamedGestures;
                outputStream2 = object;
                outputStream2 = object;
                if (outputStream instanceof BufferedOutputStream) break block22;
                outputStream2 = object;
                outputStream = new BufferedOutputStream(outputStream, 32768);
            }
            outputStream2 = object;
            Object object3 = new DataOutputStream(outputStream);
            outputStream2 = outputStream = object3;
            ((DataOutputStream)outputStream).writeShort(1);
            outputStream2 = outputStream;
            ((DataOutputStream)outputStream).writeInt(((HashMap)object2).size());
            outputStream2 = outputStream;
            object = ((HashMap)object2).entrySet().iterator();
            block19 : do {
                outputStream2 = outputStream;
                if (!object.hasNext()) break;
                outputStream2 = outputStream;
                object2 = (Map.Entry)object.next();
                outputStream2 = outputStream;
                object3 = (String)object2.getKey();
                outputStream2 = outputStream;
                object2 = (ArrayList)object2.getValue();
                outputStream2 = outputStream;
                int n = ((ArrayList)object2).size();
                outputStream2 = outputStream;
                ((DataOutputStream)outputStream).writeUTF((String)object3);
                outputStream2 = outputStream;
                ((DataOutputStream)outputStream).writeInt(n);
                int n2 = 0;
                do {
                    if (n2 >= n) continue block19;
                    outputStream2 = outputStream;
                    ((Gesture)((ArrayList)object2).get(n2)).serialize((DataOutputStream)outputStream);
                    ++n2;
                } while (true);
                break;
            } while (true);
            outputStream2 = outputStream;
            try {
                ((DataOutputStream)outputStream).flush();
                outputStream2 = outputStream;
            }
            catch (Throwable throwable) {
                if (bl) {
                    GestureUtils.closeStream(outputStream2);
                }
                throw throwable;
            }
            this.mChanged = false;
            if (!bl) break block23;
            GestureUtils.closeStream(outputStream);
        }
    }

    public void setOrientationStyle(int n) {
        this.mOrientationStyle = n;
    }

    public void setSequenceType(int n) {
        this.mSequenceType = n;
    }
}

