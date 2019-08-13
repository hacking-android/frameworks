/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.InputPort;
import android.filterfw.core.KeyValueMap;
import android.filterfw.core.OutputPort;
import android.filterpacks.base.FrameBranch;
import android.filterpacks.base.NullFilter;
import android.util.Log;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class FilterGraph {
    public static final int AUTOBRANCH_OFF = 0;
    public static final int AUTOBRANCH_SYNCED = 1;
    public static final int AUTOBRANCH_UNSYNCED = 2;
    public static final int TYPECHECK_DYNAMIC = 1;
    public static final int TYPECHECK_OFF = 0;
    public static final int TYPECHECK_STRICT = 2;
    private String TAG = "FilterGraph";
    private int mAutoBranchMode = 0;
    private boolean mDiscardUnconnectedOutputs = false;
    private HashSet<Filter> mFilters = new HashSet();
    private boolean mIsReady = false;
    private boolean mLogVerbose = Log.isLoggable(this.TAG, 2);
    private HashMap<String, Filter> mNameMap = new HashMap();
    private HashMap<OutputPort, LinkedList<InputPort>> mPreconnections = new HashMap();
    private int mTypeCheckMode = 2;

    private void checkConnections() {
    }

    private void connectPorts() {
        int n = 1;
        Object object = this.mPreconnections.entrySet().iterator();
        while (object.hasNext()) {
            Iterator<OutputPort> iterator = object.next();
            Object object2 = iterator.getKey();
            if (((LinkedList)((Object)(iterator = iterator.getValue()))).size() == 1) {
                ((OutputPort)object2).connectTo((InputPort)((LinkedList)((Object)iterator)).get(0));
                continue;
            }
            if (this.mAutoBranchMode != 0) {
                Object object3;
                if (this.mLogVerbose) {
                    String string2 = this.TAG;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Creating branch for ");
                    ((StringBuilder)object3).append(object2);
                    ((StringBuilder)object3).append("!");
                    Log.v(string2, ((StringBuilder)object3).toString());
                }
                if (this.mAutoBranchMode == 1) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("branch");
                    ((StringBuilder)object3).append(n);
                    object3 = new FrameBranch(((StringBuilder)object3).toString());
                    new KeyValueMap();
                    ((Filter)object3).initWithAssignmentList("outputs", ((LinkedList)((Object)iterator)).size());
                    this.addFilter((Filter)object3);
                    ((OutputPort)object2).connectTo(((Filter)object3).getInputPort("in"));
                    object2 = ((AbstractSequentialList)((Object)iterator)).iterator();
                    iterator = ((Filter)object3).getOutputPorts().iterator();
                    while (iterator.hasNext()) {
                        iterator.next().connectTo((InputPort)object2.next());
                    }
                    ++n;
                    continue;
                }
                throw new RuntimeException("TODO: Unsynced branches not implemented yet!");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Attempting to connect ");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(" to multiple filter ports! Enable auto-branching to allow this.");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        this.mPreconnections.clear();
    }

    private void discardUnconnectedOutputs() {
        Object object = new LinkedList();
        for (Filter filter : this.mFilters) {
            int n = 0;
            for (OutputPort outputPort : filter.getOutputPorts()) {
                int n2 = n;
                if (!outputPort.isConnected()) {
                    Object object2;
                    if (this.mLogVerbose) {
                        object2 = this.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Autoconnecting unconnected ");
                        stringBuilder.append(outputPort);
                        stringBuilder.append(" to Null filter.");
                        Log.v((String)object2, stringBuilder.toString());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(filter.getName());
                    ((StringBuilder)object2).append("ToNull");
                    ((StringBuilder)object2).append(n);
                    object2 = new NullFilter(((StringBuilder)object2).toString());
                    ((Filter)object2).init();
                    ((LinkedList)object).add(object2);
                    outputPort.connectTo(((Filter)object2).getInputPort("frame"));
                    n2 = n + 1;
                }
                n = n2;
            }
        }
        object = ((AbstractSequentialList)object).iterator();
        while (object.hasNext()) {
            this.addFilter((Filter)object.next());
        }
    }

    private HashSet<Filter> getSourceFilters() {
        HashSet<Filter> hashSet = new HashSet<Filter>();
        for (Filter filter : this.getFilters()) {
            if (filter.getNumberOfConnectedInputs() != 0) continue;
            if (this.mLogVerbose) {
                String string2 = this.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Found source filter: ");
                stringBuilder.append(filter);
                Log.v(string2, stringBuilder.toString());
            }
            hashSet.add(filter);
        }
        return hashSet;
    }

    private void preconnect(OutputPort outputPort, InputPort inputPort) {
        LinkedList<InputPort> linkedList;
        LinkedList<InputPort> linkedList2 = linkedList = this.mPreconnections.get(outputPort);
        if (linkedList == null) {
            linkedList2 = new LinkedList();
            this.mPreconnections.put(outputPort, linkedList2);
        }
        linkedList2.add(inputPort);
    }

    private boolean readyForProcessing(Filter filter, Set<Filter> set) {
        if (set.contains(filter)) {
            return false;
        }
        Iterator<InputPort> iterator = filter.getInputPorts().iterator();
        while (iterator.hasNext()) {
            filter = iterator.next().getSourceFilter();
            if (filter == null || set.contains(filter)) continue;
            return false;
        }
        return true;
    }

    private void removeFilter(Filter filter) {
        this.mFilters.remove(filter);
        this.mNameMap.remove(filter.getName());
    }

    private void runTypeCheck() {
        Stack<Filter> stack = new Stack<Filter>();
        HashSet<Filter> hashSet = new HashSet<Filter>();
        stack.addAll(this.getSourceFilters());
        while (!stack.empty()) {
            Object object;
            Filter filter = (Filter)stack.pop();
            hashSet.add(filter);
            this.updateOutputs(filter);
            if (this.mLogVerbose) {
                String string2 = this.TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("Running type check on ");
                ((StringBuilder)object).append(filter);
                ((StringBuilder)object).append("...");
                Log.v(string2, ((StringBuilder)object).toString());
            }
            this.runTypeCheckOn(filter);
            object = filter.getOutputPorts().iterator();
            while (object.hasNext()) {
                filter = ((OutputPort)object.next()).getTargetFilter();
                if (filter == null || !this.readyForProcessing(filter, hashSet)) continue;
                stack.push(filter);
            }
        }
        if (hashSet.size() == this.getFilters().size()) {
            return;
        }
        throw new RuntimeException("Could not schedule all filters! Is your graph malformed?");
    }

    private void runTypeCheckOn(Filter filter) {
        for (InputPort object : filter.getInputPorts()) {
            Object object2;
            Object object3;
            if (this.mLogVerbose) {
                object2 = this.TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Type checking port ");
                ((StringBuilder)object3).append(object);
                Log.v((String)object2, ((StringBuilder)object3).toString());
            }
            object2 = object.getSourceFormat();
            object3 = object.getPortFormat();
            if (object2 == null || object3 == null) continue;
            if (this.mLogVerbose) {
                String string2 = this.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Checking ");
                stringBuilder.append(object2);
                stringBuilder.append(" against ");
                stringBuilder.append(object3);
                stringBuilder.append(".");
                Log.v(string2, stringBuilder.toString());
            }
            boolean bl = true;
            int n = this.mTypeCheckMode;
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        bl = ((FrameFormat)object2).isCompatibleWith((FrameFormat)object3);
                        object.setChecksType(false);
                    }
                } else {
                    bl = ((FrameFormat)object2).mayBeCompatibleWith((FrameFormat)object3);
                    object.setChecksType(true);
                }
            } else {
                object.setChecksType(false);
            }
            if (bl) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Type mismatch: Filter ");
            stringBuilder.append(filter);
            stringBuilder.append(" expects a format of type ");
            stringBuilder.append(object3);
            stringBuilder.append(" but got a format of type ");
            stringBuilder.append(object2);
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    private void updateOutputs(Filter object) {
        for (OutputPort outputPort : ((Filter)object).getOutputPorts()) {
            Object object2 = outputPort.getBasePort();
            if (object2 == null) continue;
            object2 = ((InputPort)object2).getSourceFormat();
            object2 = ((Filter)object).getOutputFormat(outputPort.getName(), (FrameFormat)object2);
            if (object2 != null) {
                outputPort.setPortFormat((FrameFormat)object2);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Filter did not return an output format for ");
            ((StringBuilder)object).append(outputPort);
            ((StringBuilder)object).append("!");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
    }

    public boolean addFilter(Filter filter) {
        if (!this.containsFilter(filter)) {
            this.mFilters.add(filter);
            this.mNameMap.put(filter.getName(), filter);
            return true;
        }
        return false;
    }

    public void beginProcessing() {
        if (this.mLogVerbose) {
            Log.v(this.TAG, "Opening all filter connections...");
        }
        Iterator<Filter> iterator = this.mFilters.iterator();
        while (iterator.hasNext()) {
            iterator.next().openOutputs();
        }
        this.mIsReady = true;
    }

    public void closeFilters(FilterContext filterContext) {
        if (this.mLogVerbose) {
            Log.v(this.TAG, "Closing all filters...");
        }
        Iterator<Filter> iterator = this.mFilters.iterator();
        while (iterator.hasNext()) {
            iterator.next().performClose(filterContext);
        }
        this.mIsReady = false;
    }

    public void connect(Filter object, String string2, Filter object2, String string3) {
        if (object != null && object2 != null) {
            if (this.containsFilter((Filter)object) && this.containsFilter((Filter)object2)) {
                OutputPort outputPort = ((Filter)object).getOutputPort(string2);
                InputPort inputPort = ((Filter)object2).getInputPort(string3);
                if (outputPort != null) {
                    if (inputPort != null) {
                        this.preconnect(outputPort, inputPort);
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown input port '");
                    ((StringBuilder)object).append(string3);
                    ((StringBuilder)object).append("' on Filter ");
                    ((StringBuilder)object).append(object2);
                    ((StringBuilder)object).append("!");
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown output port '");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append("' on Filter ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append("!");
                throw new RuntimeException(((StringBuilder)object2).toString());
            }
            throw new RuntimeException("Attempting to connect filter not in graph!");
        }
        throw new IllegalArgumentException("Passing null Filter in connect()!");
    }

    public void connect(String charSequence, String charSequence2, String string2, String string3) {
        Filter filter = this.getFilter((String)charSequence);
        Filter filter2 = this.getFilter(string2);
        if (filter != null) {
            if (filter2 != null) {
                this.connect(filter, (String)charSequence2, filter2, string3);
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Attempting to connect unknown target filter '");
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append("'!");
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("Attempting to connect unknown source filter '");
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("'!");
        throw new RuntimeException(((StringBuilder)charSequence2).toString());
    }

    public boolean containsFilter(Filter filter) {
        return this.mFilters.contains(filter);
    }

    public void flushFrames() {
        Iterator<Filter> iterator = this.mFilters.iterator();
        while (iterator.hasNext()) {
            iterator.next().clearOutputs();
        }
    }

    @UnsupportedAppUsage
    public Filter getFilter(String string2) {
        return this.mNameMap.get(string2);
    }

    public Set<Filter> getFilters() {
        return this.mFilters;
    }

    public boolean isReady() {
        return this.mIsReady;
    }

    public void setAutoBranchMode(int n) {
        this.mAutoBranchMode = n;
    }

    public void setDiscardUnconnectedOutputs(boolean bl) {
        this.mDiscardUnconnectedOutputs = bl;
    }

    public void setTypeCheckMode(int n) {
        this.mTypeCheckMode = n;
    }

    void setupFilters() {
        if (this.mDiscardUnconnectedOutputs) {
            this.discardUnconnectedOutputs();
        }
        this.connectPorts();
        this.checkConnections();
        this.runTypeCheck();
    }

    @UnsupportedAppUsage
    public void tearDown(FilterContext filterContext) {
        if (!this.mFilters.isEmpty()) {
            this.flushFrames();
            Iterator<Filter> iterator = this.mFilters.iterator();
            while (iterator.hasNext()) {
                iterator.next().performTearDown(filterContext);
            }
            this.mFilters.clear();
            this.mNameMap.clear();
            this.mIsReady = false;
        }
    }
}

