/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.AnimationHandler;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.ActivityThread;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class AnimatorSet
extends Animator
implements AnimationHandler.AnimationFrameCallback {
    private static final String TAG = "AnimatorSet";
    private boolean mChildrenInitialized;
    private ValueAnimator mDelayAnim;
    private boolean mDependencyDirty;
    private AnimatorListenerAdapter mDummyListener;
    private long mDuration;
    private final boolean mEndCanBeCalled;
    private ArrayList<AnimationEvent> mEvents = new ArrayList();
    private long mFirstFrame;
    private TimeInterpolator mInterpolator;
    private int mLastEventId;
    private long mLastFrameTime;
    private ArrayMap<Animator, Node> mNodeMap = new ArrayMap();
    private ArrayList<Node> mNodes = new ArrayList();
    private long mPauseTime;
    private ArrayList<Node> mPlayingSet = new ArrayList();
    private boolean mReversing;
    private Node mRootNode;
    private SeekState mSeekState;
    private boolean mSelfPulse;
    private final boolean mShouldIgnoreEndWithoutStart;
    private final boolean mShouldResetValuesAtStart;
    private long mStartDelay;
    private boolean mStarted;
    private long mTotalDuration;

    public AnimatorSet() {
        boolean bl;
        boolean bl2 = false;
        this.mDependencyDirty = false;
        this.mStarted = false;
        this.mStartDelay = 0L;
        this.mDelayAnim = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(0L);
        this.mRootNode = new Node(this.mDelayAnim);
        this.mDuration = -1L;
        this.mInterpolator = null;
        this.mTotalDuration = 0L;
        this.mLastFrameTime = -1L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mReversing = false;
        this.mSelfPulse = true;
        this.mSeekState = new SeekState();
        this.mChildrenInitialized = false;
        this.mPauseTime = -1L;
        this.mDummyListener = new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                if (AnimatorSet.this.mNodeMap.get(animator2) != null) {
                    ((Node)AnimatorSet.access$100((AnimatorSet)AnimatorSet.this).get((Object)animator2)).mEnded = true;
                    return;
                }
                throw new AndroidRuntimeException("Error: animation ended is not in the node map");
            }
        };
        this.mNodeMap.put(this.mDelayAnim, this.mRootNode);
        this.mNodes.add(this.mRootNode);
        Application application = ActivityThread.currentApplication();
        if (application != null && application.getApplicationInfo() != null) {
            this.mShouldIgnoreEndWithoutStart = application.getApplicationInfo().targetSdkVersion < 24;
            bl = application.getApplicationInfo().targetSdkVersion < 26;
        } else {
            this.mShouldIgnoreEndWithoutStart = true;
            bl = true;
        }
        boolean bl3 = !bl;
        this.mShouldResetValuesAtStart = bl3;
        bl3 = bl2;
        if (!bl) {
            bl3 = true;
        }
        this.mEndCanBeCalled = bl3;
    }

    private void addAnimationCallback(long l) {
        if (!this.mSelfPulse) {
            return;
        }
        AnimationHandler.getInstance().addAnimationFrameCallback(this, l);
    }

    private void addDummyListener() {
        for (int i = 1; i < this.mNodes.size(); ++i) {
            this.mNodes.get((int)i).mAnimation.addListener(this.mDummyListener);
        }
    }

    private void createDependencyGraph() {
        Cloneable cloneable;
        int n;
        Cloneable cloneable2;
        int n2;
        int n3;
        if (!this.mDependencyDirty) {
            n3 = 0;
            n = 0;
            do {
                n2 = n3;
                if (n >= this.mNodes.size()) break;
                cloneable2 = this.mNodes.get((int)n).mAnimation;
                if (this.mNodes.get((int)n).mTotalDuration != ((Animator)cloneable2).getTotalDuration()) {
                    n2 = 1;
                    break;
                }
                ++n;
            } while (true);
            if (n2 == 0) {
                return;
            }
        }
        this.mDependencyDirty = false;
        n3 = this.mNodes.size();
        for (n = 0; n < n3; ++n) {
            this.mNodes.get((int)n).mParentsAdded = false;
        }
        for (n = 0; n < n3; ++n) {
            cloneable2 = this.mNodes.get(n);
            if (((Node)cloneable2).mParentsAdded) continue;
            ((Node)cloneable2).mParentsAdded = true;
            if (((Node)cloneable2).mSiblings == null) continue;
            this.findSiblings((Node)cloneable2, ((Node)cloneable2).mSiblings);
            ((Node)cloneable2).mSiblings.remove(cloneable2);
            int n4 = ((Node)cloneable2).mSiblings.size();
            for (n2 = 0; n2 < n4; ++n2) {
                ((Node)cloneable2).addParents(cloneable2.mSiblings.get((int)n2).mParents);
            }
            for (n2 = 0; n2 < n4; ++n2) {
                cloneable = ((Node)cloneable2).mSiblings.get(n2);
                ((Node)cloneable).addParents(((Node)cloneable2).mParents);
                ((Node)cloneable).mParentsAdded = true;
            }
        }
        for (n = 0; n < n3; ++n) {
            cloneable2 = this.mNodes.get(n);
            if (cloneable2 == this.mRootNode || ((Node)cloneable2).mParents != null) continue;
            ((Node)cloneable2).addParent(this.mRootNode);
        }
        cloneable = new ArrayList<Node>(this.mNodes.size());
        cloneable2 = this.mRootNode;
        ((Node)cloneable2).mStartTime = 0L;
        ((Node)cloneable2).mEndTime = this.mDelayAnim.getDuration();
        this.updatePlayTime(this.mRootNode, (ArrayList<Node>)cloneable);
        this.sortAnimationEvents();
        cloneable2 = this.mEvents;
        this.mTotalDuration = ((AnimationEvent)((ArrayList)cloneable2).get(((ArrayList)cloneable2).size() - 1)).getTime();
    }

    private void endAnimation() {
        this.mStarted = false;
        this.mLastFrameTime = -1L;
        this.mFirstFrame = -1L;
        this.mLastEventId = -1;
        this.mPaused = false;
        this.mPauseTime = -1L;
        this.mSeekState.reset();
        this.mPlayingSet.clear();
        this.removeAnimationCallback();
        if (this.mListeners != null) {
            ArrayList arrayList = (ArrayList)this.mListeners.clone();
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                ((Animator.AnimatorListener)arrayList.get(i)).onAnimationEnd(this, this.mReversing);
            }
        }
        this.removeDummyListener();
        this.mSelfPulse = true;
        this.mReversing = false;
    }

    private int findLatestEventIdForTime(long l) {
        int n;
        int n2 = this.mEvents.size();
        int n3 = this.mLastEventId;
        if (this.mReversing) {
            long l2 = this.getTotalDuration();
            int n4 = n = this.mLastEventId;
            if (n == -1) {
                n4 = n2;
            }
            this.mLastEventId = n4;
            for (n4 = this.mLastEventId - 1; n4 >= 0; --n4) {
                if (this.mEvents.get(n4).getTime() < l2 - l) continue;
                n3 = n4;
            }
            n = n3;
        } else {
            int n5 = this.mLastEventId + 1;
            do {
                n = n3;
                if (n5 >= n2) break;
                AnimationEvent animationEvent = this.mEvents.get(n5);
                n = n3;
                if (animationEvent.getTime() != -1L) {
                    n = n3;
                    if (animationEvent.getTime() <= l) {
                        n = n5;
                    }
                }
                ++n5;
                n3 = n;
            } while (true);
        }
        return n;
    }

    private void findSiblings(Node node, ArrayList<Node> arrayList) {
        if (!arrayList.contains(node)) {
            arrayList.add(node);
            if (node.mSiblings == null) {
                return;
            }
            for (int i = 0; i < node.mSiblings.size(); ++i) {
                this.findSiblings(node.mSiblings.get(i), arrayList);
            }
        }
    }

    private void forceToEnd() {
        if (this.mEndCanBeCalled) {
            this.end();
            return;
        }
        if (this.mReversing) {
            this.handleAnimationEvents(this.mLastEventId, 0, this.getTotalDuration());
        } else {
            long l;
            long l2 = l = this.getTotalDuration();
            if (l == -1L) {
                l2 = Integer.MAX_VALUE;
            }
            this.handleAnimationEvents(this.mLastEventId, this.mEvents.size() - 1, l2);
        }
        this.mPlayingSet.clear();
        this.endAnimation();
    }

    private Node getNodeForAnimation(Animator animator2) {
        Node node;
        Node node2 = node = this.mNodeMap.get(animator2);
        if (node == null) {
            node2 = new Node(animator2);
            this.mNodeMap.put(animator2, node2);
            this.mNodes.add(node2);
        }
        return node2;
    }

    private long getPlayTimeForNode(long l, Node node) {
        return this.getPlayTimeForNode(l, node, this.mReversing);
    }

    private long getPlayTimeForNode(long l, Node node, boolean bl) {
        if (bl) {
            long l2 = this.getTotalDuration();
            return node.mEndTime - (l2 - l);
        }
        return l - node.mStartTime;
    }

    private void handleAnimationEvents(int n, int n2, long l) {
        if (this.mReversing) {
            if (n == -1) {
                n = this.mEvents.size();
            }
            --n;
            while (n >= n2) {
                AnimationEvent animationEvent = this.mEvents.get(n);
                Node node = animationEvent.mNode;
                if (animationEvent.mEvent == 2) {
                    if (node.mAnimation.isStarted()) {
                        node.mAnimation.cancel();
                    }
                    node.mEnded = false;
                    this.mPlayingSet.add(animationEvent.mNode);
                    node.mAnimation.startWithoutPulsing(true);
                    this.pulseFrame(node, 0L);
                } else if (animationEvent.mEvent == 1 && !node.mEnded) {
                    this.pulseFrame(node, this.getPlayTimeForNode(l, node));
                }
                --n;
            }
        } else {
            ++n;
            while (n <= n2) {
                AnimationEvent animationEvent = this.mEvents.get(n);
                Node node = animationEvent.mNode;
                if (animationEvent.mEvent == 0) {
                    this.mPlayingSet.add(animationEvent.mNode);
                    if (node.mAnimation.isStarted()) {
                        node.mAnimation.cancel();
                    }
                    node.mEnded = false;
                    node.mAnimation.startWithoutPulsing(false);
                    this.pulseFrame(node, 0L);
                } else if (animationEvent.mEvent == 2 && !node.mEnded) {
                    this.pulseFrame(node, this.getPlayTimeForNode(l, node));
                }
                ++n;
            }
        }
    }

    private void initAnimation() {
        if (this.mInterpolator != null) {
            for (int i = 0; i < this.mNodes.size(); ++i) {
                this.mNodes.get((int)i).mAnimation.setInterpolator(this.mInterpolator);
            }
        }
        this.updateAnimatorsDuration();
        this.createDependencyGraph();
    }

    private void initChildren() {
        if (!this.isInitialized()) {
            this.mChildrenInitialized = true;
            this.skipToEndValue(false);
        }
    }

    private static boolean isEmptySet(AnimatorSet animatorSet) {
        if (animatorSet.getStartDelay() > 0L) {
            return false;
        }
        for (int i = 0; i < animatorSet.getChildAnimations().size(); ++i) {
            Animator animator2 = animatorSet.getChildAnimations().get(i);
            if (!(animator2 instanceof AnimatorSet)) {
                return false;
            }
            if (AnimatorSet.isEmptySet((AnimatorSet)animator2)) continue;
            return false;
        }
        return true;
    }

    private void printChildCount() {
        ArrayList<Node> arrayList = new ArrayList<Node>(this.mNodes.size());
        arrayList.add(this.mRootNode);
        Log.d(TAG, "Current tree: ");
        int n = 0;
        while (n < arrayList.size()) {
            int n2 = arrayList.size();
            StringBuilder stringBuilder = new StringBuilder();
            while (n < n2) {
                Node node = (Node)arrayList.get(n);
                int n3 = 0;
                int n4 = 0;
                if (node.mChildNodes != null) {
                    int n5 = 0;
                    do {
                        n3 = n4;
                        if (n5 >= node.mChildNodes.size()) break;
                        Node node2 = node.mChildNodes.get(n5);
                        n3 = n4;
                        if (node2.mLatestParent == node) {
                            n3 = n4 + 1;
                            arrayList.add(node2);
                        }
                        ++n5;
                        n4 = n3;
                    } while (true);
                }
                stringBuilder.append(" ");
                stringBuilder.append(n3);
                ++n;
            }
            Log.d(TAG, stringBuilder.toString());
        }
    }

    private void pulseFrame(Node node, long l) {
        if (!node.mEnded) {
            float f = ValueAnimator.getDurationScale();
            if (f == 0.0f) {
                f = 1.0f;
            }
            node.mEnded = node.mAnimation.pulseAnimationFrame((long)((float)l * f));
        }
    }

    private void removeAnimationCallback() {
        if (!this.mSelfPulse) {
            return;
        }
        AnimationHandler.getInstance().removeCallback(this);
    }

    private void removeDummyListener() {
        for (int i = 1; i < this.mNodes.size(); ++i) {
            this.mNodes.get((int)i).mAnimation.removeListener(this.mDummyListener);
        }
    }

    private void skipToStartValue(boolean bl) {
        this.skipToEndValue(bl ^ true);
    }

    private void sortAnimationEvents() {
        ArrayList<AnimationEvent> arrayList;
        int n;
        this.mEvents.clear();
        for (n = 1; n < this.mNodes.size(); ++n) {
            arrayList = this.mNodes.get(n);
            this.mEvents.add(new AnimationEvent((Node)((Object)arrayList), 0));
            this.mEvents.add(new AnimationEvent((Node)((Object)arrayList), 1));
            this.mEvents.add(new AnimationEvent((Node)((Object)arrayList), 2));
        }
        this.mEvents.sort(new Comparator<AnimationEvent>(){

            @Override
            public int compare(AnimationEvent animationEvent, AnimationEvent animationEvent2) {
                long l;
                long l2 = animationEvent.getTime();
                if (l2 == (l = animationEvent2.getTime())) {
                    if (animationEvent2.mEvent + animationEvent.mEvent == 1) {
                        return animationEvent.mEvent - animationEvent2.mEvent;
                    }
                    return animationEvent2.mEvent - animationEvent.mEvent;
                }
                if (l == -1L) {
                    return -1;
                }
                if (l2 == -1L) {
                    return 1;
                }
                return (int)(l2 - l);
            }
        });
        int n2 = this.mEvents.size();
        n = 0;
        while (n < n2) {
            block16 : {
                block19 : {
                    boolean bl;
                    int n3;
                    block18 : {
                        block17 : {
                            arrayList = this.mEvents.get(n);
                            if (((AnimationEvent)arrayList).mEvent != 2) break block16;
                            if (arrayList.mNode.mStartTime != arrayList.mNode.mEndTime) break block17;
                            bl = true;
                            break block18;
                        }
                        if (arrayList.mNode.mEndTime != arrayList.mNode.mStartTime + arrayList.mNode.mAnimation.getStartDelay()) break block19;
                        bl = false;
                    }
                    int n4 = n2;
                    int n5 = n2;
                    for (n3 = n + 1; n3 < n2 && (n4 >= n2 || n5 >= n2); ++n3) {
                        int n6 = n4;
                        int n7 = n5;
                        if (this.mEvents.get((int)n3).mNode == ((AnimationEvent)arrayList).mNode) {
                            if (this.mEvents.get((int)n3).mEvent == 0) {
                                n6 = n3;
                                n7 = n5;
                            } else {
                                n6 = n4;
                                n7 = n5;
                                if (this.mEvents.get((int)n3).mEvent == 1) {
                                    n7 = n3;
                                    n6 = n4;
                                }
                            }
                        }
                        n4 = n6;
                        n5 = n7;
                    }
                    if (bl && n4 == this.mEvents.size()) {
                        throw new UnsupportedOperationException("Something went wrong, no start isfound after stop for an animation that has the same start and endtime.");
                    }
                    if (n5 != this.mEvents.size()) {
                        n3 = n;
                        if (bl) {
                            arrayList = this.mEvents.remove(n4);
                            this.mEvents.add(n, (AnimationEvent)((Object)arrayList));
                            n3 = n + 1;
                        }
                        arrayList = this.mEvents.remove(n5);
                        this.mEvents.add(n3, (AnimationEvent)((Object)arrayList));
                        n = n3 + 2;
                        continue;
                    }
                    throw new UnsupportedOperationException("Something went wrong, no startdelay end is found after stop for an animation");
                }
                ++n;
                continue;
            }
            ++n;
        }
        if (!this.mEvents.isEmpty() && this.mEvents.get((int)0).mEvent != 0) {
            throw new UnsupportedOperationException("Sorting went bad, the start event should always be at index 0");
        }
        this.mEvents.add(0, new AnimationEvent(this.mRootNode, 0));
        this.mEvents.add(1, new AnimationEvent(this.mRootNode, 1));
        this.mEvents.add(2, new AnimationEvent(this.mRootNode, 2));
        arrayList = this.mEvents;
        if (arrayList.get((int)(arrayList.size() - 1)).mEvent != 0) {
            arrayList = this.mEvents;
            if (arrayList.get((int)(arrayList.size() - 1)).mEvent != 1) {
                return;
            }
        }
        throw new UnsupportedOperationException("Something went wrong, the last event is not an end event");
    }

    private void start(boolean bl, boolean bl2) {
        if (Looper.myLooper() != null) {
            Cloneable cloneable;
            int n;
            this.mStarted = true;
            this.mSelfPulse = bl2;
            this.mPaused = false;
            this.mPauseTime = -1L;
            int n2 = this.mNodes.size();
            for (n = 0; n < n2; ++n) {
                cloneable = this.mNodes.get(n);
                ((Node)cloneable).mEnded = false;
                ((Node)cloneable).mAnimation.setAllowRunningAsynchronously(false);
            }
            this.initAnimation();
            if (bl && !this.canReverse()) {
                throw new UnsupportedOperationException("Cannot reverse infinite AnimatorSet");
            }
            this.mReversing = bl;
            bl2 = AnimatorSet.isEmptySet(this);
            if (!bl2) {
                this.startAnimation();
            }
            if (this.mListeners != null) {
                cloneable = (ArrayList)this.mListeners.clone();
                n2 = ((ArrayList)cloneable).size();
                for (n = 0; n < n2; ++n) {
                    ((Animator.AnimatorListener)((ArrayList)cloneable).get(n)).onAnimationStart(this, bl);
                }
            }
            if (bl2) {
                this.end();
            }
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    private void startAnimation() {
        int n;
        this.addDummyListener();
        this.addAnimationCallback(0L);
        if (this.mSeekState.getPlayTimeNormalized() == 0L && this.mReversing) {
            this.mSeekState.reset();
        }
        if (this.mShouldResetValuesAtStart) {
            if (this.isInitialized()) {
                this.skipToEndValue(this.mReversing ^ true);
            } else if (this.mReversing) {
                this.initChildren();
                this.skipToEndValue(this.mReversing ^ true);
            } else {
                for (n = this.mEvents.size() - 1; n >= 0; --n) {
                    Animator animator2;
                    if (this.mEvents.get((int)n).mEvent != 1 || !(animator2 = this.mEvents.get((int)n).mNode.mAnimation).isInitialized()) continue;
                    animator2.skipToEndValue(true);
                }
            }
        }
        if (this.mReversing || this.mStartDelay == 0L || this.mSeekState.isActive()) {
            long l;
            if (this.mSeekState.isActive()) {
                this.mSeekState.updateSeekDirection(this.mReversing);
                l = this.mSeekState.getPlayTime();
            } else {
                l = 0L;
            }
            int n2 = this.findLatestEventIdForTime(l);
            this.handleAnimationEvents(-1, n2, l);
            for (n = this.mPlayingSet.size() - 1; n >= 0; --n) {
                if (!this.mPlayingSet.get((int)n).mEnded) continue;
                this.mPlayingSet.remove(n);
            }
            this.mLastEventId = n2;
        }
    }

    private void updateAnimatorsDuration() {
        if (this.mDuration >= 0L) {
            int n = this.mNodes.size();
            for (int i = 0; i < n; ++i) {
                this.mNodes.get((int)i).mAnimation.setDuration(this.mDuration);
            }
        }
        this.mDelayAnim.setDuration(this.mStartDelay);
    }

    private void updatePlayTime(Node node, ArrayList<Node> arrayList) {
        if (node.mChildNodes == null) {
            if (node == this.mRootNode) {
                for (int i = 0; i < this.mNodes.size(); ++i) {
                    node = this.mNodes.get(i);
                    if (node == this.mRootNode) continue;
                    node.mStartTime = -1L;
                    node.mEndTime = -1L;
                }
            }
            return;
        }
        arrayList.add(node);
        int n = node.mChildNodes.size();
        for (int i = 0; i < n; ++i) {
            Object object = node.mChildNodes.get(i);
            ((Node)object).mTotalDuration = ((Node)object).mAnimation.getTotalDuration();
            int n2 = arrayList.indexOf(object);
            if (n2 >= 0) {
                while (n2 < arrayList.size()) {
                    arrayList.get((int)n2).mLatestParent = null;
                    arrayList.get((int)n2).mStartTime = -1L;
                    arrayList.get((int)n2).mEndTime = -1L;
                    ++n2;
                }
                ((Node)object).mStartTime = -1L;
                ((Node)object).mEndTime = -1L;
                ((Node)object).mLatestParent = null;
                object = new StringBuilder();
                ((StringBuilder)object).append("Cycle found in AnimatorSet: ");
                ((StringBuilder)object).append(this);
                Log.w(TAG, ((StringBuilder)object).toString());
                continue;
            }
            if (((Node)object).mStartTime != -1L) {
                if (node.mEndTime == -1L) {
                    ((Node)object).mLatestParent = node;
                    ((Node)object).mStartTime = -1L;
                    ((Node)object).mEndTime = -1L;
                } else {
                    if (node.mEndTime >= ((Node)object).mStartTime) {
                        ((Node)object).mLatestParent = node;
                        ((Node)object).mStartTime = node.mEndTime;
                    }
                    long l = ((Node)object).mTotalDuration == -1L ? -1L : ((Node)object).mStartTime + ((Node)object).mTotalDuration;
                    ((Node)object).mEndTime = l;
                }
            }
            this.updatePlayTime((Node)object, arrayList);
        }
        arrayList.remove(node);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    void animateBasedOnPlayTime(long l, long l2, boolean bl) {
        long l3;
        int n;
        Object object;
        if (l < 0L || l2 < 0L) throw new UnsupportedOperationException("Error: Play time should never be negative.");
        if (bl) {
            if (this.getTotalDuration() == -1L) throw new UnsupportedOperationException("Cannot reverse AnimatorSet with infinite duration");
            l3 = this.getTotalDuration() - this.mStartDelay;
            l = l3 - Math.min(l, l3);
            l3 -= l2;
            bl = false;
            l2 = l;
            l = l3;
        } else {
            l3 = l;
            l = l2;
            l2 = l3;
        }
        Object object2 = new ArrayList();
        for (n = 0; n < this.mEvents.size() && ((AnimationEvent)(object = this.mEvents.get(n))).getTime() <= l2 && ((AnimationEvent)object).getTime() != -1L; ++n) {
            if (((AnimationEvent)object).mEvent == 1 && (object.mNode.mEndTime == -1L || object.mNode.mEndTime > l2)) {
                ((ArrayList)object2).add(((AnimationEvent)object).mNode);
            }
            if (((AnimationEvent)object).mEvent != 2) continue;
            object.mNode.mAnimation.skipToEndValue(false);
        }
        for (n = 0; n < ((ArrayList)object2).size(); ++n) {
            object = (Node)((ArrayList)object2).get(n);
            l3 = this.getPlayTimeForNode(l2, (Node)object, bl);
            if (!bl) {
                l3 -= ((Node)object).mAnimation.getStartDelay();
            }
            ((Node)object).mAnimation.animateBasedOnPlayTime(l3, l, bl);
        }
        for (n = 0; n < this.mEvents.size(); ++n) {
            object2 = this.mEvents.get(n);
            if (((AnimationEvent)object2).getTime() <= l2 || ((AnimationEvent)object2).mEvent != 1) continue;
            object2.mNode.mAnimation.skipToEndValue(true);
        }
    }

    @Override
    public boolean canReverse() {
        boolean bl = this.getTotalDuration() != -1L;
        return bl;
    }

    @Override
    public void cancel() {
        if (Looper.myLooper() != null) {
            if (this.isStarted()) {
                int n;
                int n2;
                ArrayList<Node> arrayList;
                if (this.mListeners != null) {
                    arrayList = (ArrayList<Node>)this.mListeners.clone();
                    n = arrayList.size();
                    for (n2 = 0; n2 < n; ++n2) {
                        ((Animator.AnimatorListener)((Object)arrayList.get(n2))).onAnimationCancel(this);
                    }
                }
                arrayList = new ArrayList<Node>(this.mPlayingSet);
                n = arrayList.size();
                for (n2 = 0; n2 < n; ++n2) {
                    arrayList.get((int)n2).mAnimation.cancel();
                }
                this.mPlayingSet.clear();
                this.endAnimation();
            }
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    @Override
    public AnimatorSet clone() {
        Node node;
        Node node2;
        int n;
        final AnimatorSet animatorSet = (AnimatorSet)super.clone();
        int n2 = this.mNodes.size();
        animatorSet.mStarted = false;
        animatorSet.mLastFrameTime = -1L;
        animatorSet.mFirstFrame = -1L;
        animatorSet.mLastEventId = -1;
        animatorSet.mPaused = false;
        animatorSet.mPauseTime = -1L;
        animatorSet.mSeekState = new SeekState();
        animatorSet.mSelfPulse = true;
        animatorSet.mPlayingSet = new ArrayList();
        animatorSet.mNodeMap = new ArrayMap();
        animatorSet.mNodes = new ArrayList(n2);
        animatorSet.mEvents = new ArrayList();
        animatorSet.mDummyListener = new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                if (animatorSet.mNodeMap.get(animator2) != null) {
                    ((Node)AnimatorSet.access$100((AnimatorSet)animatorSet).get((Object)animator2)).mEnded = true;
                    return;
                }
                throw new AndroidRuntimeException("Error: animation ended is not in the node map");
            }
        };
        animatorSet.mReversing = false;
        animatorSet.mDependencyDirty = true;
        HashMap<Node, Node> hashMap = new HashMap<Node, Node>(n2);
        for (n = 0; n < n2; ++n) {
            node = this.mNodes.get(n);
            node2 = node.clone();
            node2.mAnimation.removeListener(this.mDummyListener);
            hashMap.put(node, node2);
            animatorSet.mNodes.add(node2);
            animatorSet.mNodeMap.put(node2.mAnimation, node2);
        }
        animatorSet.mRootNode = (Node)hashMap.get(this.mRootNode);
        animatorSet.mDelayAnim = (ValueAnimator)animatorSet.mRootNode.mAnimation;
        for (n = 0; n < n2; ++n) {
            int n3;
            Node node3 = this.mNodes.get(n);
            node2 = (Node)hashMap.get(node3);
            node = node3.mLatestParent == null ? null : (Node)hashMap.get(node3.mLatestParent);
            node2.mLatestParent = node;
            int n4 = node3.mChildNodes == null ? 0 : node3.mChildNodes.size();
            for (n3 = 0; n3 < n4; ++n3) {
                node2.mChildNodes.set(n3, (Node)hashMap.get(node3.mChildNodes.get(n3)));
            }
            n4 = node3.mSiblings == null ? 0 : node3.mSiblings.size();
            for (n3 = 0; n3 < n4; ++n3) {
                node2.mSiblings.set(n3, (Node)hashMap.get(node3.mSiblings.get(n3)));
            }
            n4 = node3.mParents == null ? 0 : node3.mParents.size();
            for (n3 = 0; n3 < n4; ++n3) {
                node2.mParents.set(n3, (Node)hashMap.get(node3.mParents.get(n3)));
            }
        }
        return animatorSet;
    }

    @Override
    public void commitAnimationFrame(long l) {
    }

    @Override
    public boolean doAnimationFrame(long l) {
        float f = ValueAnimator.getDurationScale();
        if (f == 0.0f) {
            this.forceToEnd();
            return true;
        }
        if (this.mFirstFrame < 0L) {
            this.mFirstFrame = l;
        }
        if (this.mPaused) {
            this.mPauseTime = l;
            this.removeAnimationCallback();
            return false;
        }
        long l2 = this.mPauseTime;
        if (l2 > 0L) {
            this.mFirstFrame += l - l2;
            this.mPauseTime = -1L;
        }
        if (this.mSeekState.isActive()) {
            this.mSeekState.updateSeekDirection(this.mReversing);
            this.mFirstFrame = this.mReversing ? (long)((float)l - (float)this.mSeekState.getPlayTime() * f) : (long)((float)l - (float)(this.mSeekState.getPlayTime() + this.mStartDelay) * f);
            this.mSeekState.reset();
        }
        if (!this.mReversing && (float)l < (float)this.mFirstFrame + (float)this.mStartDelay * f) {
            return false;
        }
        l2 = (long)((float)(l - this.mFirstFrame) / f);
        this.mLastFrameTime = l;
        int n = this.findLatestEventIdForTime(l2);
        this.handleAnimationEvents(this.mLastEventId, n, l2);
        this.mLastEventId = n;
        for (n = 0; n < this.mPlayingSet.size(); ++n) {
            Node node = this.mPlayingSet.get(n);
            if (node.mEnded) continue;
            this.pulseFrame(node, this.getPlayTimeForNode(l2, node));
        }
        for (n = this.mPlayingSet.size() - 1; n >= 0; --n) {
            if (!this.mPlayingSet.get((int)n).mEnded) continue;
            this.mPlayingSet.remove(n);
        }
        int n2 = 0;
        if (this.mReversing) {
            if (this.mPlayingSet.size() == 1 && this.mPlayingSet.get(0) == this.mRootNode) {
                n = 1;
            } else {
                n = n2;
                if (this.mPlayingSet.isEmpty()) {
                    n = n2;
                    if (this.mLastEventId < 3) {
                        n = 1;
                    }
                }
            }
        } else {
            n = this.mPlayingSet.isEmpty() && this.mLastEventId == this.mEvents.size() - 1 ? 1 : 0;
        }
        if (n != 0) {
            this.endAnimation();
            return true;
        }
        return false;
    }

    @Override
    public void end() {
        if (Looper.myLooper() != null) {
            if (this.mShouldIgnoreEndWithoutStart && !this.isStarted()) {
                return;
            }
            if (this.isStarted()) {
                if (this.mReversing) {
                    int n;
                    int n2 = n = this.mLastEventId;
                    if (n == -1) {
                        n2 = this.mEvents.size();
                    }
                    this.mLastEventId = n2;
                    while ((n2 = this.mLastEventId) > 0) {
                        this.mLastEventId = n2 - 1;
                        AnimationEvent animationEvent = this.mEvents.get(this.mLastEventId);
                        Animator animator2 = animationEvent.mNode.mAnimation;
                        if (this.mNodeMap.get((Object)animator2).mEnded) continue;
                        if (animationEvent.mEvent == 2) {
                            animator2.reverse();
                            continue;
                        }
                        if (animationEvent.mEvent != 1 || !animator2.isStarted()) continue;
                        animator2.end();
                    }
                } else {
                    while (this.mLastEventId < this.mEvents.size() - 1) {
                        ++this.mLastEventId;
                        AnimationEvent animationEvent = this.mEvents.get(this.mLastEventId);
                        Animator animator3 = animationEvent.mNode.mAnimation;
                        if (this.mNodeMap.get((Object)animator3).mEnded) continue;
                        if (animationEvent.mEvent == 0) {
                            animator3.start();
                            continue;
                        }
                        if (animationEvent.mEvent != 2 || !animator3.isStarted()) continue;
                        animator3.end();
                    }
                }
                this.mPlayingSet.clear();
            }
            this.endAnimation();
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    @Override
    public int getChangingConfigurations() {
        int n = super.getChangingConfigurations();
        int n2 = this.mNodes.size();
        for (int i = 0; i < n2; ++i) {
            n |= this.mNodes.get((int)i).mAnimation.getChangingConfigurations();
        }
        return n;
    }

    public ArrayList<Animator> getChildAnimations() {
        ArrayList<Animator> arrayList = new ArrayList<Animator>();
        int n = this.mNodes.size();
        for (int i = 0; i < n; ++i) {
            Node node = this.mNodes.get(i);
            if (node == this.mRootNode) continue;
            arrayList.add(node.mAnimation);
        }
        return arrayList;
    }

    public long getCurrentPlayTime() {
        if (this.mSeekState.isActive()) {
            return this.mSeekState.getPlayTime();
        }
        if (this.mLastFrameTime == -1L) {
            return 0L;
        }
        float f = ValueAnimator.getDurationScale();
        if (f == 0.0f) {
            f = 1.0f;
        }
        if (this.mReversing) {
            return (long)((float)(this.mLastFrameTime - this.mFirstFrame) / f);
        }
        return (long)((float)(this.mLastFrameTime - this.mFirstFrame - this.mStartDelay) / f);
    }

    @Override
    public long getDuration() {
        return this.mDuration;
    }

    @Override
    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    @Override
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override
    public long getTotalDuration() {
        this.updateAnimatorsDuration();
        this.createDependencyGraph();
        return this.mTotalDuration;
    }

    @Override
    boolean isInitialized() {
        boolean bl;
        if (this.mChildrenInitialized) {
            return true;
        }
        boolean bl2 = true;
        int n = 0;
        do {
            bl = bl2;
            if (n >= this.mNodes.size()) break;
            if (!this.mNodes.get((int)n).mAnimation.isInitialized()) {
                bl = false;
                break;
            }
            ++n;
        } while (true);
        this.mChildrenInitialized = bl;
        return this.mChildrenInitialized;
    }

    @Override
    public boolean isRunning() {
        if (this.mStartDelay == 0L) {
            return this.mStarted;
        }
        boolean bl = this.mLastFrameTime > 0L;
        return bl;
    }

    @Override
    public boolean isStarted() {
        return this.mStarted;
    }

    @Override
    public void pause() {
        if (Looper.myLooper() != null) {
            boolean bl = this.mPaused;
            super.pause();
            if (!bl && this.mPaused) {
                this.mPauseTime = -1L;
            }
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    public Builder play(Animator animator2) {
        if (animator2 != null) {
            return new Builder(animator2);
        }
        return null;
    }

    public void playSequentially(List<Animator> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                this.play(list.get(0));
            } else {
                for (int i = 0; i < list.size() - 1; ++i) {
                    this.play(list.get(i)).before(list.get(i + 1));
                }
            }
        }
    }

    public void playSequentially(Animator ... arranimator) {
        if (arranimator != null) {
            if (arranimator.length == 1) {
                this.play(arranimator[0]);
            } else {
                for (int i = 0; i < arranimator.length - 1; ++i) {
                    this.play(arranimator[i]).before(arranimator[i + 1]);
                }
            }
        }
    }

    public void playTogether(Collection<Animator> object) {
        if (object != null && object.size() > 0) {
            Animator animator2 = null;
            Iterator<Animator> iterator = object.iterator();
            object = animator2;
            while (iterator.hasNext()) {
                animator2 = iterator.next();
                if (object == null) {
                    object = this.play(animator2);
                    continue;
                }
                ((Builder)object).with(animator2);
            }
        }
    }

    public void playTogether(Animator ... arranimator) {
        if (arranimator != null) {
            Builder builder = this.play(arranimator[0]);
            for (int i = 1; i < arranimator.length; ++i) {
                builder.with(arranimator[i]);
            }
        }
    }

    @Override
    boolean pulseAnimationFrame(long l) {
        return this.doAnimationFrame(l);
    }

    @Override
    public void resume() {
        if (Looper.myLooper() != null) {
            boolean bl = this.mPaused;
            super.resume();
            if (bl && !this.mPaused && this.mPauseTime >= 0L) {
                this.addAnimationCallback(0L);
            }
            return;
        }
        throw new AndroidRuntimeException("Animators may only be run on Looper threads");
    }

    @Override
    public void reverse() {
        this.start(true, true);
    }

    public void setCurrentPlayTime(long l) {
        block5 : {
            block8 : {
                block7 : {
                    block6 : {
                        if (this.mReversing && this.getTotalDuration() == -1L) {
                            throw new UnsupportedOperationException("Error: Cannot seek in reverse in an infinite AnimatorSet");
                        }
                        if (this.getTotalDuration() != -1L && l > this.getTotalDuration() - this.mStartDelay || l < 0L) break block5;
                        this.initAnimation();
                        if (!this.isStarted() || this.isPaused()) break block6;
                        this.mSeekState.setPlayTime(l, this.mReversing);
                        break block7;
                    }
                    if (this.mReversing) break block8;
                    if (!this.mSeekState.isActive()) {
                        this.findLatestEventIdForTime(0L);
                        this.initChildren();
                        this.mSeekState.setPlayTime(0L, this.mReversing);
                    }
                    this.animateBasedOnPlayTime(l, 0L, this.mReversing);
                    this.mSeekState.setPlayTime(l, this.mReversing);
                }
                return;
            }
            throw new UnsupportedOperationException("Error: Something went wrong. mReversing should not be set when AnimatorSet is not started.");
        }
        throw new UnsupportedOperationException("Error: Play time should always be in between0 and duration.");
    }

    @Override
    public AnimatorSet setDuration(long l) {
        if (l >= 0L) {
            this.mDependencyDirty = true;
            this.mDuration = l;
            return this;
        }
        throw new IllegalArgumentException("duration must be a value of zero or greater");
    }

    @Override
    public void setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    @Override
    public void setStartDelay(long l) {
        long l2;
        long l3 = l;
        if (l < 0L) {
            Log.w(TAG, "Start delay should always be non-negative");
            l3 = 0L;
        }
        if ((l2 = l3 - this.mStartDelay) == 0L) {
            return;
        }
        this.mStartDelay = l3;
        if (!this.mDependencyDirty) {
            int n = this.mNodes.size();
            int n2 = 0;
            do {
                l3 = -1L;
                if (n2 >= n) break;
                Node node = this.mNodes.get(n2);
                if (node == this.mRootNode) {
                    node.mEndTime = this.mStartDelay;
                } else {
                    l = node.mStartTime == -1L ? -1L : node.mStartTime + l2;
                    node.mStartTime = l;
                    l = node.mEndTime == -1L ? l3 : node.mEndTime + l2;
                    node.mEndTime = l;
                }
                ++n2;
            } while (true);
            l = this.mTotalDuration;
            if (l != -1L) {
                this.mTotalDuration = l + l2;
            }
        }
    }

    @Override
    public void setTarget(Object object) {
        int n = this.mNodes.size();
        for (int i = 0; i < n; ++i) {
            Animator animator2 = this.mNodes.get((int)i).mAnimation;
            if (animator2 instanceof AnimatorSet) {
                ((AnimatorSet)animator2).setTarget(object);
                continue;
            }
            if (!(animator2 instanceof ObjectAnimator)) continue;
            ((ObjectAnimator)animator2).setTarget(object);
        }
    }

    @Override
    public void setupEndValues() {
        int n = this.mNodes.size();
        for (int i = 0; i < n; ++i) {
            Node node = this.mNodes.get(i);
            if (node == this.mRootNode) continue;
            node.mAnimation.setupEndValues();
        }
    }

    @Override
    public void setupStartValues() {
        int n = this.mNodes.size();
        for (int i = 0; i < n; ++i) {
            Node node = this.mNodes.get(i);
            if (node == this.mRootNode) continue;
            node.mAnimation.setupStartValues();
        }
    }

    public boolean shouldPlayTogether() {
        boolean bl;
        this.updateAnimatorsDuration();
        this.createDependencyGraph();
        ArrayList<Node> arrayList = this.mRootNode.mChildNodes;
        boolean bl2 = bl = true;
        if (arrayList != null) {
            bl2 = this.mRootNode.mChildNodes.size() == this.mNodes.size() - 1 ? bl : false;
        }
        return bl2;
    }

    @Override
    void skipToEndValue(boolean bl) {
        if (this.isInitialized()) {
            this.initAnimation();
            if (bl) {
                for (int i = this.mEvents.size() - 1; i >= 0; --i) {
                    if (this.mEvents.get((int)i).mEvent != 1) continue;
                    this.mEvents.get((int)i).mNode.mAnimation.skipToEndValue(true);
                }
            } else {
                for (int i = 0; i < this.mEvents.size(); ++i) {
                    if (this.mEvents.get((int)i).mEvent != 2) continue;
                    this.mEvents.get((int)i).mNode.mAnimation.skipToEndValue(false);
                }
            }
            return;
        }
        throw new UnsupportedOperationException("Children must be initialized.");
    }

    @Override
    public void start() {
        this.start(false, true);
    }

    @Override
    void startWithoutPulsing(boolean bl) {
        this.start(bl, false);
    }

    public String toString() {
        StringBuilder stringBuilder;
        CharSequence charSequence = new StringBuilder();
        charSequence.append("AnimatorSet@");
        charSequence.append(Integer.toHexString(this.hashCode()));
        charSequence.append("{");
        charSequence = charSequence.toString();
        int n = this.mNodes.size();
        for (int i = 0; i < n; ++i) {
            Node node = this.mNodes.get(i);
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("\n    ");
            stringBuilder.append(node.mAnimation.toString());
            charSequence = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }

    private static class AnimationEvent {
        static final int ANIMATION_DELAY_ENDED = 1;
        static final int ANIMATION_END = 2;
        static final int ANIMATION_START = 0;
        final int mEvent;
        final Node mNode;

        AnimationEvent(Node node, int n) {
            this.mNode = node;
            this.mEvent = n;
        }

        long getTime() {
            int n = this.mEvent;
            if (n == 0) {
                return this.mNode.mStartTime;
            }
            if (n == 1) {
                long l = this.mNode.mStartTime;
                long l2 = -1L;
                if (l != -1L) {
                    l2 = this.mNode.mStartTime;
                    l2 = this.mNode.mAnimation.getStartDelay() + l2;
                }
                return l2;
            }
            return this.mNode.mEndTime;
        }

        public String toString() {
            int n = this.mEvent;
            String string2 = n == 0 ? "start" : (n == 1 ? "delay ended" : "end");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(this.mNode.mAnimation.toString());
            return stringBuilder.toString();
        }
    }

    public class Builder {
        private Node mCurrentNode;

        Builder(Animator animator2) {
            AnimatorSet.this.mDependencyDirty = true;
            this.mCurrentNode = AnimatorSet.this.getNodeForAnimation(animator2);
        }

        public Builder after(long l) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimator.setDuration(l);
            this.after(valueAnimator);
            return this;
        }

        public Builder after(Animator cloneable) {
            cloneable = AnimatorSet.this.getNodeForAnimation(cloneable);
            this.mCurrentNode.addParent((Node)cloneable);
            return this;
        }

        public Builder before(Animator cloneable) {
            cloneable = AnimatorSet.this.getNodeForAnimation(cloneable);
            this.mCurrentNode.addChild((Node)cloneable);
            return this;
        }

        public Builder with(Animator cloneable) {
            cloneable = AnimatorSet.this.getNodeForAnimation(cloneable);
            this.mCurrentNode.addSibling((Node)cloneable);
            return this;
        }
    }

    private static class Node
    implements Cloneable {
        Animator mAnimation;
        ArrayList<Node> mChildNodes = null;
        long mEndTime = 0L;
        boolean mEnded = false;
        Node mLatestParent = null;
        ArrayList<Node> mParents;
        boolean mParentsAdded = false;
        ArrayList<Node> mSiblings;
        long mStartTime = 0L;
        long mTotalDuration = 0L;

        public Node(Animator animator2) {
            this.mAnimation = animator2;
        }

        void addChild(Node node) {
            if (this.mChildNodes == null) {
                this.mChildNodes = new ArrayList();
            }
            if (!this.mChildNodes.contains(node)) {
                this.mChildNodes.add(node);
                node.addParent(this);
            }
        }

        public void addParent(Node node) {
            if (this.mParents == null) {
                this.mParents = new ArrayList();
            }
            if (!this.mParents.contains(node)) {
                this.mParents.add(node);
                node.addChild(this);
            }
        }

        public void addParents(ArrayList<Node> arrayList) {
            if (arrayList == null) {
                return;
            }
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.addParent(arrayList.get(i));
            }
        }

        public void addSibling(Node node) {
            if (this.mSiblings == null) {
                this.mSiblings = new ArrayList();
            }
            if (!this.mSiblings.contains(node)) {
                this.mSiblings.add(node);
                node.addSibling(this);
            }
        }

        public Node clone() {
            try {
                ArrayList<Node> arrayList;
                Node node = (Node)super.clone();
                node.mAnimation = this.mAnimation.clone();
                if (this.mChildNodes != null) {
                    node.mChildNodes = arrayList = new ArrayList<Node>(this.mChildNodes);
                }
                if (this.mSiblings != null) {
                    arrayList = new ArrayList<Node>(this.mSiblings);
                    node.mSiblings = arrayList;
                }
                if (this.mParents != null) {
                    arrayList = new ArrayList<Node>(this.mParents);
                    node.mParents = arrayList;
                }
                node.mEnded = false;
                return node;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new AssertionError();
            }
        }
    }

    private class SeekState {
        private long mPlayTime = -1L;
        private boolean mSeekingInReverse = false;

        private SeekState() {
        }

        long getPlayTime() {
            return this.mPlayTime;
        }

        long getPlayTimeNormalized() {
            if (AnimatorSet.this.mReversing) {
                return AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay - this.mPlayTime;
            }
            return this.mPlayTime;
        }

        boolean isActive() {
            boolean bl = this.mPlayTime != -1L;
            return bl;
        }

        void reset() {
            this.mPlayTime = -1L;
            this.mSeekingInReverse = false;
        }

        void setPlayTime(long l, boolean bl) {
            if (AnimatorSet.this.getTotalDuration() != -1L) {
                this.mPlayTime = Math.min(l, AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay);
            }
            this.mPlayTime = Math.max(0L, this.mPlayTime);
            this.mSeekingInReverse = bl;
        }

        void updateSeekDirection(boolean bl) {
            if (bl && AnimatorSet.this.getTotalDuration() == -1L) {
                throw new UnsupportedOperationException("Error: Cannot reverse infinite animator set");
            }
            if (this.mPlayTime >= 0L && bl != this.mSeekingInReverse) {
                this.mPlayTime = AnimatorSet.this.getTotalDuration() - AnimatorSet.this.mStartDelay - this.mPlayTime;
                this.mSeekingInReverse = bl;
            }
        }
    }

}

