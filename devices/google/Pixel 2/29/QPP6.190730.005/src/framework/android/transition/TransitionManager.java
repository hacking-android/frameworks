/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.annotation.UnsupportedAppUsage;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TransitionManager {
    private static final String[] EMPTY_STRINGS;
    private static String LOG_TAG;
    private static Transition sDefaultTransition;
    @UnsupportedAppUsage
    private static ArrayList<ViewGroup> sPendingTransitions;
    @UnsupportedAppUsage
    private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> sRunningTransitions;
    ArrayMap<Scene, ArrayMap<Scene, Transition>> mScenePairTransitions = new ArrayMap();
    ArrayMap<Scene, Transition> mSceneTransitions = new ArrayMap();

    static {
        LOG_TAG = "TransitionManager";
        sDefaultTransition = new AutoTransition();
        EMPTY_STRINGS = new String[0];
        sRunningTransitions = new ThreadLocal();
        sPendingTransitions = new ArrayList();
    }

    public static void beginDelayedTransition(ViewGroup viewGroup) {
        TransitionManager.beginDelayedTransition(viewGroup, null);
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, Transition transition2) {
        if (!sPendingTransitions.contains(viewGroup) && viewGroup.isLaidOut()) {
            sPendingTransitions.add(viewGroup);
            Transition transition3 = transition2;
            if (transition2 == null) {
                transition3 = sDefaultTransition;
            }
            transition2 = transition3.clone();
            TransitionManager.sceneChangeSetup(viewGroup, transition2);
            Scene.setCurrentScene(viewGroup, null);
            TransitionManager.sceneChangeRunTransition(viewGroup, transition2);
        }
    }

    private static void changeScene(Scene scene, Transition transition2) {
        ViewGroup viewGroup = scene.getSceneRoot();
        if (!sPendingTransitions.contains(viewGroup)) {
            Scene scene2 = Scene.getCurrentScene(viewGroup);
            if (transition2 == null) {
                if (scene2 != null) {
                    scene2.exit();
                }
                scene.enter();
            } else {
                sPendingTransitions.add(viewGroup);
                transition2 = transition2.clone();
                transition2.setSceneRoot(viewGroup);
                if (scene2 != null && scene2.isCreatedFromLayoutResource()) {
                    transition2.setCanRemoveViews(true);
                }
                TransitionManager.sceneChangeSetup(viewGroup, transition2);
                scene.enter();
                TransitionManager.sceneChangeRunTransition(viewGroup, transition2);
            }
        }
    }

    public static void endTransitions(ViewGroup viewGroup) {
        sPendingTransitions.remove(viewGroup);
        ArrayList<Transition> arrayList = TransitionManager.getRunningTransitions().get(viewGroup);
        if (arrayList != null && !arrayList.isEmpty()) {
            arrayList = new ArrayList<Transition>(arrayList);
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                arrayList.get(i).forceToEnd(viewGroup);
            }
        }
    }

    public static Transition getDefaultTransition() {
        return sDefaultTransition;
    }

    @UnsupportedAppUsage
    private static ArrayMap<ViewGroup, ArrayList<Transition>> getRunningTransitions() {
        Object object = sRunningTransitions.get();
        if (object != null && (object = (ArrayMap)((Reference)object).get()) != null) {
            return object;
        }
        object = new ArrayMap<ViewGroup, ArrayList<Transition>>();
        WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>> weakReference = new WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>((ArrayMap<ViewGroup, ArrayList<Transition>>)object);
        sRunningTransitions.set(weakReference);
        return object;
    }

    public static void go(Scene scene) {
        TransitionManager.changeScene(scene, sDefaultTransition);
    }

    public static void go(Scene scene, Transition transition2) {
        TransitionManager.changeScene(scene, transition2);
    }

    private static void sceneChangeRunTransition(ViewGroup viewGroup, Transition object) {
        if (object != null && viewGroup != null) {
            object = new MultiListener((Transition)object, viewGroup);
            viewGroup.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
            viewGroup.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
        }
    }

    private static void sceneChangeSetup(ViewGroup object, Transition transition2) {
        ArrayList<Transition> arrayList = TransitionManager.getRunningTransitions().get(object);
        if (arrayList != null && arrayList.size() > 0) {
            arrayList = arrayList.iterator();
            while (arrayList.hasNext()) {
                ((Transition)arrayList.next()).pause((View)object);
            }
        }
        if (transition2 != null) {
            transition2.captureValues((ViewGroup)object, true);
        }
        if ((object = Scene.getCurrentScene((ViewGroup)object)) != null) {
            ((Scene)object).exit();
        }
    }

    public Transition getTransition(Scene object) {
        ArrayMap<Scene, Transition> arrayMap;
        Object object2 = ((Scene)object).getSceneRoot();
        if (object2 != null && (object2 = Scene.getCurrentScene((ViewGroup)object2)) != null && (arrayMap = this.mScenePairTransitions.get(object)) != null && (object2 = arrayMap.get(object2)) != null) {
            return object2;
        }
        if ((object = this.mSceneTransitions.get(object)) == null) {
            object = sDefaultTransition;
        }
        return object;
    }

    public void setDefaultTransition(Transition transition2) {
        sDefaultTransition = transition2;
    }

    public void setTransition(Scene scene, Scene scene2, Transition transition2) {
        ArrayMap<Scene, Transition> arrayMap;
        ArrayMap<Scene, Transition> arrayMap2 = arrayMap = this.mScenePairTransitions.get(scene2);
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            this.mScenePairTransitions.put(scene2, arrayMap2);
        }
        arrayMap2.put(scene, transition2);
    }

    public void setTransition(Scene scene, Transition transition2) {
        this.mSceneTransitions.put(scene, transition2);
    }

    public void transitionTo(Scene scene) {
        TransitionManager.changeScene(scene, this.getTransition(scene));
    }

    private static class MultiListener
    implements ViewTreeObserver.OnPreDrawListener,
    View.OnAttachStateChangeListener {
        ViewGroup mSceneRoot;
        Transition mTransition;
        final ViewTreeObserver mViewTreeObserver;

        MultiListener(Transition transition2, ViewGroup viewGroup) {
            this.mTransition = transition2;
            this.mSceneRoot = viewGroup;
            this.mViewTreeObserver = this.mSceneRoot.getViewTreeObserver();
        }

        private void removeListeners() {
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeOnPreDrawListener(this);
            } else {
                this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            this.mSceneRoot.removeOnAttachStateChangeListener(this);
        }

        @Override
        public boolean onPreDraw() {
            Object object;
            this.removeListeners();
            if (!sPendingTransitions.remove(this.mSceneRoot)) {
                return true;
            }
            final ArrayMap arrayMap = TransitionManager.getRunningTransitions();
            ArrayList<Transition> arrayList = (ArrayList<Transition>)arrayMap.get(this.mSceneRoot);
            ArrayList arrayList2 = null;
            if (arrayList == null) {
                object = new ArrayList<Transition>();
                arrayMap.put(this.mSceneRoot, object);
            } else {
                object = arrayList;
                if (arrayList.size() > 0) {
                    arrayList2 = new ArrayList(arrayList);
                    object = arrayList;
                }
            }
            ((ArrayList)object).add(this.mTransition);
            this.mTransition.addListener(new TransitionListenerAdapter(){

                @Override
                public void onTransitionEnd(Transition transition2) {
                    ((ArrayList)arrayMap.get(MultiListener.this.mSceneRoot)).remove(transition2);
                    transition2.removeListener(this);
                }
            });
            this.mTransition.captureValues(this.mSceneRoot, false);
            if (arrayList2 != null) {
                object = arrayList2.iterator();
                while (object.hasNext()) {
                    ((Transition)object.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.playTransition(this.mSceneRoot);
            return true;
        }

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View iterator) {
            this.removeListeners();
            sPendingTransitions.remove(this.mSceneRoot);
            iterator = (ArrayList)TransitionManager.getRunningTransitions().get(this.mSceneRoot);
            if (iterator != null && ((ArrayList)((Object)iterator)).size() > 0) {
                iterator = ((ArrayList)((Object)iterator)).iterator();
                while (iterator.hasNext()) {
                    ((Transition)iterator.next()).resume(this.mSceneRoot);
                }
            }
            this.mTransition.clearValues(true);
        }

    }

}

