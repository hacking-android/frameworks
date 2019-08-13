/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.FieldPort;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterPort;
import android.filterfw.core.FinalPort;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.core.GenerateFinalPort;
import android.filterfw.core.GenerateProgramPort;
import android.filterfw.core.GenerateProgramPorts;
import android.filterfw.core.InputPort;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.core.OutputPort;
import android.filterfw.core.Program;
import android.filterfw.core.ProgramPort;
import android.filterfw.core.ProtocolException;
import android.filterfw.core.SerializedFrame;
import android.filterfw.core.SimpleFrame;
import android.filterfw.core.StreamPort;
import android.filterfw.format.ObjectFormat;
import android.filterfw.io.GraphIOException;
import android.filterfw.io.TextGraphReader;
import android.util.Log;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class Filter {
    static final int STATUS_ERROR = 6;
    static final int STATUS_FINISHED = 5;
    static final int STATUS_PREINIT = 0;
    static final int STATUS_PREPARED = 2;
    static final int STATUS_PROCESSING = 3;
    static final int STATUS_RELEASED = 7;
    static final int STATUS_SLEEPING = 4;
    static final int STATUS_UNPREPARED = 1;
    private static final String TAG = "Filter";
    private long mCurrentTimestamp;
    private HashSet<Frame> mFramesToRelease;
    private HashMap<String, Frame> mFramesToSet;
    private int mInputCount = -1;
    private HashMap<String, InputPort> mInputPorts;
    private boolean mIsOpen = false;
    private boolean mLogVerbose;
    private String mName;
    private int mOutputCount = -1;
    private HashMap<String, OutputPort> mOutputPorts;
    private int mSleepDelay;
    private int mStatus = 0;

    @UnsupportedAppUsage
    public Filter(String string2) {
        this.mName = string2;
        this.mFramesToRelease = new HashSet();
        this.mFramesToSet = new HashMap();
        this.mStatus = 0;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    private final void addAndSetFinalPorts(KeyValueMap serializable) {
        for (Field field : this.getClass().getDeclaredFields()) {
            Object object = field.getAnnotation(GenerateFinalPort.class);
            if (object == null) continue;
            Object object2 = object;
            object = object2.name().isEmpty() ? field.getName() : object2.name();
            this.addFieldPort((String)object, field, object2.hasDefault(), true);
            if (((HashMap)serializable).containsKey(object)) {
                this.setImmediateInputValue((String)object, ((HashMap)serializable).get(object));
                ((HashMap)serializable).remove(object);
                continue;
            }
            if (object2.hasDefault()) continue;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("No value specified for final input port '");
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append("' of filter ");
            ((StringBuilder)serializable).append(this);
            ((StringBuilder)serializable).append("!");
            throw new RuntimeException(((StringBuilder)serializable).toString());
        }
    }

    private final void addAnnotatedPorts() {
        for (Field field : this.getClass().getDeclaredFields()) {
            Object object = field.getAnnotation(GenerateFieldPort.class);
            if (object != null) {
                this.addFieldGenerator((GenerateFieldPort)object, field);
                continue;
            }
            object = field.getAnnotation(GenerateProgramPort.class);
            if (object != null) {
                this.addProgramGenerator((GenerateProgramPort)object, field);
                continue;
            }
            object = field.getAnnotation(GenerateProgramPorts.class);
            if (object == null) continue;
            object = ((GenerateProgramPorts)object).value();
            int n = ((GenerateProgramPort[])object).length;
            for (int i = 0; i < n; ++i) {
                this.addProgramGenerator(object[i], field);
            }
        }
    }

    private final void addFieldGenerator(GenerateFieldPort generateFieldPort, Field field) {
        String string2 = generateFieldPort.name().isEmpty() ? field.getName() : generateFieldPort.name();
        this.addFieldPort(string2, field, generateFieldPort.hasDefault(), false);
    }

    private final void addProgramGenerator(GenerateProgramPort generateProgramPort, Field field) {
        String string2 = generateProgramPort.name();
        String string3 = generateProgramPort.variableName().isEmpty() ? string2 : generateProgramPort.variableName();
        this.addProgramPort(string2, string3, field, generateProgramPort.type(), generateProgramPort.hasDefault());
    }

    private final void closePorts() {
        Iterator<OutputPort> iterator;
        if (this.mLogVerbose) {
            iterator = new StringBuilder();
            ((StringBuilder)((Object)iterator)).append("Closing all ports on ");
            ((StringBuilder)((Object)iterator)).append(this);
            ((StringBuilder)((Object)iterator)).append("!");
            Log.v(TAG, ((StringBuilder)((Object)iterator)).toString());
        }
        iterator = this.mInputPorts.values().iterator();
        while (iterator.hasNext()) {
            ((InputPort)iterator.next()).close();
        }
        iterator = this.mOutputPorts.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().close();
        }
    }

    private final boolean filterMustClose() {
        FilterPort filterPort;
        Object object = this.mInputPorts.values().iterator();
        while (object.hasNext()) {
            filterPort = object.next();
            if (!((InputPort)filterPort).filterMustClose()) continue;
            if (this.mLogVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Filter ");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append(" must close due to port ");
                ((StringBuilder)object).append(filterPort);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return true;
        }
        object = this.mOutputPorts.values().iterator();
        while (object.hasNext()) {
            filterPort = (OutputPort)object.next();
            if (!((OutputPort)filterPort).filterMustClose()) continue;
            if (this.mLogVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Filter ");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append(" must close due to port ");
                ((StringBuilder)object).append(filterPort);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return true;
        }
        return false;
    }

    private final void initFinalPorts(KeyValueMap keyValueMap) {
        this.mInputPorts = new HashMap();
        this.mOutputPorts = new HashMap();
        this.addAndSetFinalPorts(keyValueMap);
    }

    private final void initRemainingPorts(KeyValueMap keyValueMap) {
        this.addAnnotatedPorts();
        this.setupPorts();
        this.setInitialInputValues(keyValueMap);
    }

    private final boolean inputConditionsMet() {
        Object object = this.mInputPorts.values().iterator();
        while (object.hasNext()) {
            FilterPort filterPort = object.next();
            if (filterPort.isReady()) continue;
            if (this.mLogVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Input condition not met: ");
                ((StringBuilder)object).append(filterPort);
                ((StringBuilder)object).append("!");
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return false;
        }
        return true;
    }

    @UnsupportedAppUsage
    public static final boolean isAvailable(String object) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            object = classLoader.loadClass((String)object);
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
        try {
            ((Class)object).asSubclass(Filter.class);
            return true;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    private final boolean outputConditionsMet() {
        Object object = this.mOutputPorts.values().iterator();
        while (object.hasNext()) {
            FilterPort filterPort = object.next();
            if (filterPort.isReady()) continue;
            if (this.mLogVerbose) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Output condition not met: ");
                ((StringBuilder)object).append(filterPort);
                ((StringBuilder)object).append("!");
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return false;
        }
        return true;
    }

    private final void releasePulledFrames(FilterContext filterContext) {
        for (Frame frame : this.mFramesToRelease) {
            filterContext.getFrameManager().releaseFrame(frame);
        }
        this.mFramesToRelease.clear();
    }

    private final void setImmediateInputValue(String object, Object object2) {
        if (this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Setting immediate value ");
            stringBuilder.append(object2);
            stringBuilder.append(" for port ");
            stringBuilder.append((String)object);
            stringBuilder.append("!");
            Log.v(TAG, stringBuilder.toString());
        }
        object = this.getInputPort((String)object);
        ((FilterPort)object).open();
        ((FilterPort)object).setFrame(SimpleFrame.wrapObject(object2, null));
    }

    private final void setInitialInputValues(KeyValueMap object2) {
        for (Map.Entry entry : ((HashMap)object2).entrySet()) {
            this.setInputValue((String)entry.getKey(), entry.getValue());
        }
    }

    private final void transferInputFrames(FilterContext filterContext) {
        Iterator<InputPort> iterator = this.mInputPorts.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().transfer(filterContext);
        }
    }

    private final Frame wrapInputValue(String object, Object object2) {
        boolean bl = true;
        MutableFrameFormat mutableFrameFormat = ObjectFormat.fromObject(object2, 1);
        if (object2 == null) {
            object = (object = this.getInputPort((String)object).getPortFormat()) == null ? null : ((FrameFormat)object).getObjectClass();
            mutableFrameFormat.setObjectClass((Class)object);
        }
        if (object2 instanceof Number || object2 instanceof Boolean || object2 instanceof String || !(object2 instanceof Serializable)) {
            bl = false;
        }
        object = bl ? new SerializedFrame(mutableFrameFormat, null) : new SimpleFrame(mutableFrameFormat, null);
        ((Frame)object).setObjectValue(object2);
        return object;
    }

    protected void addFieldPort(String string2, Field field, boolean bl, boolean bl2) {
        field.setAccessible(true);
        FieldPort fieldPort = bl2 ? new FinalPort(this, string2, field, bl) : new FieldPort(this, string2, field, bl);
        if (this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Filter ");
            stringBuilder.append(this);
            stringBuilder.append(" adding ");
            stringBuilder.append(fieldPort);
            Log.v(TAG, stringBuilder.toString());
        }
        fieldPort.setPortFormat(ObjectFormat.fromClass(field.getType(), 1));
        this.mInputPorts.put(string2, fieldPort);
    }

    protected void addInputPort(String string2) {
        this.addMaskedInputPort(string2, null);
    }

    protected void addMaskedInputPort(String string2, FrameFormat frameFormat) {
        StreamPort streamPort = new StreamPort(this, string2);
        if (this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Filter ");
            stringBuilder.append(this);
            stringBuilder.append(" adding ");
            stringBuilder.append(streamPort);
            Log.v(TAG, stringBuilder.toString());
        }
        this.mInputPorts.put(string2, streamPort);
        streamPort.setPortFormat(frameFormat);
    }

    protected void addOutputBasedOnInput(String string2, String string3) {
        OutputPort outputPort = new OutputPort(this, string2);
        if (this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Filter ");
            stringBuilder.append(this);
            stringBuilder.append(" adding ");
            stringBuilder.append(outputPort);
            Log.v(TAG, stringBuilder.toString());
        }
        outputPort.setBasePort(this.getInputPort(string3));
        this.mOutputPorts.put(string2, outputPort);
    }

    protected void addOutputPort(String string2, FrameFormat frameFormat) {
        OutputPort outputPort = new OutputPort(this, string2);
        if (this.mLogVerbose) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Filter ");
            stringBuilder.append(this);
            stringBuilder.append(" adding ");
            stringBuilder.append(outputPort);
            Log.v(TAG, stringBuilder.toString());
        }
        outputPort.setPortFormat(frameFormat);
        this.mOutputPorts.put(string2, outputPort);
    }

    protected void addProgramPort(String string2, String charSequence, Field object, Class class_, boolean bl) {
        ((AccessibleObject)object).setAccessible(true);
        object = new ProgramPort(this, string2, (String)charSequence, (Field)object, bl);
        if (this.mLogVerbose) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Filter ");
            ((StringBuilder)charSequence).append(this);
            ((StringBuilder)charSequence).append(" adding ");
            ((StringBuilder)charSequence).append(object);
            Log.v(TAG, ((StringBuilder)charSequence).toString());
        }
        ((FilterPort)object).setPortFormat(ObjectFormat.fromClass(class_, 1));
        this.mInputPorts.put(string2, (InputPort)object);
    }

    final boolean canProcess() {
        synchronized (this) {
            block5 : {
                boolean bl;
                block6 : {
                    if (this.mLogVerbose) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Checking if can process: ");
                        stringBuilder.append(this);
                        stringBuilder.append(" (");
                        stringBuilder.append(this.mStatus);
                        stringBuilder.append(").");
                        Log.v(TAG, stringBuilder.toString());
                    }
                    int n = this.mStatus;
                    boolean bl2 = false;
                    if (n > 3) break block5;
                    bl = bl2;
                    if (!this.inputConditionsMet()) break block6;
                    boolean bl3 = this.outputConditionsMet();
                    bl = bl2;
                    if (!bl3) break block6;
                    bl = true;
                }
                return bl;
            }
            return false;
        }
    }

    final void clearInputs() {
        Iterator<InputPort> iterator = this.mInputPorts.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
    }

    final void clearOutputs() {
        Iterator<OutputPort> iterator = this.mOutputPorts.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().clear();
        }
    }

    public void close(FilterContext filterContext) {
    }

    protected void closeOutputPort(String string2) {
        this.getOutputPort(string2).close();
    }

    protected void delayNextProcess(int n) {
        this.mSleepDelay = n;
        this.mStatus = 4;
    }

    public void fieldPortValueUpdated(String string2, FilterContext filterContext) {
    }

    public String getFilterClassName() {
        return this.getClass().getSimpleName();
    }

    public final FrameFormat getInputFormat(String string2) {
        return this.getInputPort(string2).getSourceFormat();
    }

    public final InputPort getInputPort(String string2) {
        HashMap<String, InputPort> hashMap = this.mInputPorts;
        if (hashMap != null) {
            if ((hashMap = hashMap.get(string2)) != null) {
                return hashMap;
            }
            hashMap = new StringBuilder();
            ((StringBuilder)((Object)hashMap)).append("Unknown input port '");
            ((StringBuilder)((Object)hashMap)).append(string2);
            ((StringBuilder)((Object)hashMap)).append("' on filter ");
            ((StringBuilder)((Object)hashMap)).append(this);
            ((StringBuilder)((Object)hashMap)).append("!");
            throw new IllegalArgumentException(((StringBuilder)((Object)hashMap)).toString());
        }
        hashMap = new StringBuilder();
        ((StringBuilder)((Object)hashMap)).append("Attempting to access input port '");
        ((StringBuilder)((Object)hashMap)).append(string2);
        ((StringBuilder)((Object)hashMap)).append("' of ");
        ((StringBuilder)((Object)hashMap)).append(this);
        ((StringBuilder)((Object)hashMap)).append(" before Filter has been initialized!");
        throw new NullPointerException(((StringBuilder)((Object)hashMap)).toString());
    }

    final Collection<InputPort> getInputPorts() {
        return this.mInputPorts.values();
    }

    public final String getName() {
        return this.mName;
    }

    public final int getNumberOfConnectedInputs() {
        int n = 0;
        Iterator<InputPort> iterator = this.mInputPorts.values().iterator();
        while (iterator.hasNext()) {
            int n2 = n;
            if (iterator.next().isConnected()) {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    public final int getNumberOfConnectedOutputs() {
        int n = 0;
        Iterator<OutputPort> iterator = this.mOutputPorts.values().iterator();
        while (iterator.hasNext()) {
            int n2 = n;
            if (iterator.next().isConnected()) {
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    public final int getNumberOfInputs() {
        int n = this.mOutputPorts == null ? 0 : this.mInputPorts.size();
        return n;
    }

    public final int getNumberOfOutputs() {
        int n = this.mInputPorts == null ? 0 : this.mOutputPorts.size();
        return n;
    }

    public FrameFormat getOutputFormat(String string2, FrameFormat frameFormat) {
        return null;
    }

    public final OutputPort getOutputPort(String string2) {
        if (this.mInputPorts != null) {
            Object object = this.mOutputPorts.get(string2);
            if (object != null) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown output port '");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append("' on filter ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append("!");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to access output port '");
        stringBuilder.append(string2);
        stringBuilder.append("' of ");
        stringBuilder.append(this);
        stringBuilder.append(" before Filter has been initialized!");
        throw new NullPointerException(stringBuilder.toString());
    }

    final Collection<OutputPort> getOutputPorts() {
        return this.mOutputPorts.values();
    }

    public final int getSleepDelay() {
        return 250;
    }

    final int getStatus() {
        synchronized (this) {
            int n = this.mStatus;
            return n;
        }
    }

    public final void init() throws ProtocolException {
        this.initWithValueMap(new KeyValueMap());
    }

    protected void initProgramInputs(Program program, FilterContext filterContext) {
        if (program != null) {
            for (InputPort inputPort : this.mInputPorts.values()) {
                if (inputPort.getTarget() != program) continue;
                inputPort.transfer(filterContext);
            }
        }
    }

    public final void initWithAssignmentList(Object ... arrobject) {
        KeyValueMap keyValueMap = new KeyValueMap();
        keyValueMap.setKeyValues(arrobject);
        this.initWithValueMap(keyValueMap);
    }

    public final void initWithAssignmentString(String string2) {
        try {
            TextGraphReader textGraphReader = new TextGraphReader();
            this.initWithValueMap(textGraphReader.readKeyValueAssignments(string2));
            return;
        }
        catch (GraphIOException graphIOException) {
            throw new IllegalArgumentException(graphIOException.getMessage());
        }
    }

    public final void initWithValueMap(KeyValueMap keyValueMap) {
        this.initFinalPorts(keyValueMap);
        this.initRemainingPorts(keyValueMap);
        this.mStatus = 1;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    final void notifyFieldPortValueUpdated(String string2, FilterContext filterContext) {
        int n = this.mStatus;
        if (n == 3 || n == 2) {
            this.fieldPortValueUpdated(string2, filterContext);
        }
    }

    public void open(FilterContext filterContext) {
    }

    final void openOutputs() {
        if (this.mLogVerbose) {
            StringBuilder object = new StringBuilder();
            object.append("Opening all output ports on ");
            object.append(this);
            object.append("!");
            Log.v(TAG, object.toString());
        }
        for (OutputPort outputPort : this.mOutputPorts.values()) {
            if (outputPort.isOpen()) continue;
            outputPort.open();
        }
    }

    protected void parametersUpdated(Set<String> set) {
    }

    final void performClose(FilterContext filterContext) {
        synchronized (this) {
            if (this.mIsOpen) {
                if (this.mLogVerbose) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Closing ");
                    stringBuilder.append(this);
                    Log.v(TAG, stringBuilder.toString());
                }
                this.mIsOpen = false;
                this.mStatus = 2;
                this.close(filterContext);
                this.closePorts();
            }
            return;
        }
    }

    final void performOpen(FilterContext object) {
        synchronized (this) {
            if (!this.mIsOpen) {
                Serializable serializable;
                if (this.mStatus == 1) {
                    if (this.mLogVerbose) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Preparing ");
                        ((StringBuilder)serializable).append(this);
                        Log.v(TAG, ((StringBuilder)serializable).toString());
                    }
                    this.prepare((FilterContext)object);
                    this.mStatus = 2;
                }
                if (this.mStatus == 2) {
                    if (this.mLogVerbose) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Opening ");
                        ((StringBuilder)serializable).append(this);
                        Log.v(TAG, ((StringBuilder)serializable).toString());
                    }
                    this.open((FilterContext)object);
                    this.mStatus = 3;
                }
                if (this.mStatus == 3) {
                    this.mIsOpen = true;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Filter ");
                    ((StringBuilder)object).append(this);
                    ((StringBuilder)object).append(" was brought into invalid state during opening (state: ");
                    ((StringBuilder)object).append(this.mStatus);
                    ((StringBuilder)object).append(")!");
                    serializable = new RuntimeException(((StringBuilder)object).toString());
                    throw serializable;
                }
            }
            return;
        }
    }

    final void performProcess(FilterContext object) {
        synchronized (this) {
            if (this.mStatus != 7) {
                this.transferInputFrames((FilterContext)object);
                if (this.mStatus < 3) {
                    this.performOpen((FilterContext)object);
                }
                if (this.mLogVerbose) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Processing ");
                    stringBuilder.append(this);
                    Log.v(TAG, stringBuilder.toString());
                }
                this.mCurrentTimestamp = -1L;
                this.process((FilterContext)object);
                this.releasePulledFrames((FilterContext)object);
                if (this.filterMustClose()) {
                    this.performClose((FilterContext)object);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Filter ");
            stringBuilder.append(this);
            stringBuilder.append(" is already torn down!");
            object = new RuntimeException(stringBuilder.toString());
            throw object;
        }
    }

    final void performTearDown(FilterContext filterContext) {
        synchronized (this) {
            this.performClose(filterContext);
            if (this.mStatus != 7) {
                this.tearDown(filterContext);
                this.mStatus = 7;
            }
            return;
        }
    }

    protected void prepare(FilterContext filterContext) {
    }

    public abstract void process(FilterContext var1);

    protected final Frame pullInput(String string2) {
        Frame frame = this.getInputPort(string2).pullFrame();
        if (this.mCurrentTimestamp == -1L) {
            this.mCurrentTimestamp = frame.getTimestamp();
            if (this.mLogVerbose) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Default-setting current timestamp from input port ");
                stringBuilder.append(string2);
                stringBuilder.append(" to ");
                stringBuilder.append(this.mCurrentTimestamp);
                Log.v("Filter", stringBuilder.toString());
            }
        }
        this.mFramesToRelease.add(frame);
        return frame;
    }

    final void pushInputFrame(String object, Frame frame) {
        synchronized (this) {
            object = this.getInputPort((String)object);
            if (!((FilterPort)object).isOpen()) {
                ((FilterPort)object).open();
            }
            ((FilterPort)object).pushFrame(frame);
            return;
        }
    }

    final void pushInputValue(String string2, Object object) {
        synchronized (this) {
            this.pushInputFrame(string2, this.wrapInputValue(string2, object));
            return;
        }
    }

    protected final void pushOutput(String string2, Frame frame) {
        if (frame.getTimestamp() == -2L) {
            if (this.mLogVerbose) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Default-setting output Frame timestamp on port ");
                stringBuilder.append(string2);
                stringBuilder.append(" to ");
                stringBuilder.append(this.mCurrentTimestamp);
                Log.v("Filter", stringBuilder.toString());
            }
            frame.setTimestamp(this.mCurrentTimestamp);
        }
        this.getOutputPort(string2).pushFrame(frame);
    }

    public void setInputFrame(String object, Frame frame) {
        if (!((FilterPort)(object = this.getInputPort((String)object))).isOpen()) {
            ((FilterPort)object).open();
        }
        ((FilterPort)object).setFrame(frame);
    }

    @UnsupportedAppUsage
    public final void setInputValue(String string2, Object object) {
        this.setInputFrame(string2, this.wrapInputValue(string2, object));
    }

    protected void setWaitsOnInputPort(String string2, boolean bl) {
        this.getInputPort(string2).setBlocking(bl);
    }

    protected void setWaitsOnOutputPort(String string2, boolean bl) {
        this.getOutputPort(string2).setBlocking(bl);
    }

    public abstract void setupPorts();

    public void tearDown(FilterContext filterContext) {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(this.getName());
        stringBuilder.append("' (");
        stringBuilder.append(this.getFilterClassName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    protected void transferInputPortFrame(String string2, FilterContext filterContext) {
        this.getInputPort(string2).transfer(filterContext);
    }

    final void unsetStatus(int n) {
        synchronized (this) {
            this.mStatus &= n;
            return;
        }
    }
}

