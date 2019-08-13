/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.android.internal.widget.RecyclerView;
import com.android.internal.widget.SimpleItemAnimator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator
extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private static TimeInterpolator sDefaultInterpolator;
    ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList();
    ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList();
    ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList();
    ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList();
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList();
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList();
    ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList();

    private void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mRemoveAnimations.add(viewHolder);
        viewPropertyAnimator.setDuration(this.getRemoveDuration()).alpha(0.0f).setListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                view.setAlpha(1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished(viewHolder);
                DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }
        }).start();
    }

    private void endChangeAnimation(List<ChangeInfo> list, RecyclerView.ViewHolder viewHolder) {
        for (int i = list.size() - 1; i >= 0; --i) {
            ChangeInfo changeInfo = list.get(i);
            if (!this.endChangeAnimationIfNecessary(changeInfo, viewHolder) || changeInfo.oldHolder != null || changeInfo.newHolder != null) continue;
            list.remove(changeInfo);
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        block4 : {
            boolean bl;
            block3 : {
                block2 : {
                    bl = false;
                    if (changeInfo.newHolder != viewHolder) break block2;
                    changeInfo.newHolder = null;
                    break block3;
                }
                if (changeInfo.oldHolder != viewHolder) break block4;
                changeInfo.oldHolder = null;
                bl = true;
            }
            viewHolder.itemView.setAlpha(1.0f);
            viewHolder.itemView.setTranslationX(0.0f);
            viewHolder.itemView.setTranslationY(0.0f);
            this.dispatchChangeFinished(viewHolder, bl);
            return true;
        }
        return false;
    }

    private void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        viewHolder.itemView.animate().setInterpolator(sDefaultInterpolator);
        this.endAnimation(viewHolder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    void animateAddImpl(final RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mAddAnimations.add(viewHolder);
        viewPropertyAnimator.alpha(1.0f).setDuration(this.getAddDuration()).setListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationCancel(Animator animator2) {
                view.setAlpha(1.0f);
            }

            @Override
            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                DefaultItemAnimator.this.dispatchAddFinished(viewHolder);
                DefaultItemAnimator.this.mAddAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchAddStarting(viewHolder);
            }
        }).start();
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
        if (viewHolder == viewHolder2) {
            return this.animateMove(viewHolder, n, n2, n3, n4);
        }
        float f = viewHolder.itemView.getTranslationX();
        float f2 = viewHolder.itemView.getTranslationY();
        float f3 = viewHolder.itemView.getAlpha();
        this.resetAnimation(viewHolder);
        int n5 = (int)((float)(n3 - n) - f);
        int n6 = (int)((float)(n4 - n2) - f2);
        viewHolder.itemView.setTranslationX(f);
        viewHolder.itemView.setTranslationY(f2);
        viewHolder.itemView.setAlpha(f3);
        if (viewHolder2 != null) {
            this.resetAnimation(viewHolder2);
            viewHolder2.itemView.setTranslationX(-n5);
            viewHolder2.itemView.setTranslationY(-n6);
            viewHolder2.itemView.setAlpha(0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, n, n2, n3, n4));
        return true;
    }

    void animateChangeImpl(final ChangeInfo changeInfo) {
        Object object = changeInfo.oldHolder;
        View view = null;
        object = object == null ? null : ((RecyclerView.ViewHolder)object).itemView;
        Object object2 = changeInfo.newHolder;
        if (object2 != null) {
            view = ((RecyclerView.ViewHolder)object2).itemView;
        }
        if (object != null) {
            object2 = ((View)object).animate().setDuration(this.getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            ((ViewPropertyAnimator)object2).translationX(changeInfo.toX - changeInfo.fromX);
            ((ViewPropertyAnimator)object2).translationY(changeInfo.toY - changeInfo.fromY);
            ((ViewPropertyAnimator)object2).alpha(0.0f).setListener(new AnimatorListenerAdapter((ViewPropertyAnimator)object2, (View)object){
                final /* synthetic */ ViewPropertyAnimator val$oldViewAnim;
                final /* synthetic */ View val$view;
                {
                    this.val$oldViewAnim = viewPropertyAnimator;
                    this.val$view = view;
                }

                @Override
                public void onAnimationEnd(Animator animator2) {
                    this.val$oldViewAnim.setListener(null);
                    this.val$view.setAlpha(1.0f);
                    this.val$view.setTranslationX(0.0f);
                    this.val$view.setTranslationY(0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                @Override
                public void onAnimationStart(Animator animator2) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }
            }).start();
        }
        if (view != null) {
            object = view.animate();
            this.mChangeAnimations.add(changeInfo.newHolder);
            ((ViewPropertyAnimator)object).translationX(0.0f).translationY(0.0f).setDuration(this.getChangeDuration()).alpha(1.0f).setListener(new AnimatorListenerAdapter((ViewPropertyAnimator)object, view){
                final /* synthetic */ View val$newView;
                final /* synthetic */ ViewPropertyAnimator val$newViewAnimation;
                {
                    this.val$newViewAnimation = viewPropertyAnimator;
                    this.val$newView = view;
                }

                @Override
                public void onAnimationEnd(Animator animator2) {
                    this.val$newViewAnimation.setListener(null);
                    this.val$newView.setAlpha(1.0f);
                    this.val$newView.setTranslationX(0.0f);
                    this.val$newView.setTranslationY(0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                @Override
                public void onAnimationStart(Animator animator2) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }
            }).start();
        }
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
        View view = viewHolder.itemView;
        n = (int)((float)n + viewHolder.itemView.getTranslationX());
        int n5 = (int)((float)n2 + viewHolder.itemView.getTranslationY());
        this.resetAnimation(viewHolder);
        n2 = n3 - n;
        int n6 = n4 - n5;
        if (n2 == 0 && n6 == 0) {
            this.dispatchMoveFinished(viewHolder);
            return false;
        }
        if (n2 != 0) {
            view.setTranslationX(-n2);
        }
        if (n6 != 0) {
            view.setTranslationY(-n6);
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, n, n5, n3, n4));
        return true;
    }

    void animateMoveImpl(final RecyclerView.ViewHolder viewHolder, final int n, final int n2, int n3, int n4) {
        final View view = viewHolder.itemView;
        n = n3 - n;
        n2 = n4 - n2;
        if (n != 0) {
            view.animate().translationX(0.0f);
        }
        if (n2 != 0) {
            view.animate().translationY(0.0f);
        }
        final ViewPropertyAnimator viewPropertyAnimator = view.animate();
        this.mMoveAnimations.add(viewHolder);
        viewPropertyAnimator.setDuration(this.getMoveDuration()).setListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationCancel(Animator animator2) {
                if (n != 0) {
                    view.setTranslationX(0.0f);
                }
                if (n2 != 0) {
                    view.setTranslationY(0.0f);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator2) {
                viewPropertyAnimator.setListener(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder);
                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationStart(Animator animator2) {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder);
            }
        }).start();
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        return true;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder, List<Object> list) {
        boolean bl = !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
        return bl;
    }

    void cancelAll(List<RecyclerView.ViewHolder> list) {
        for (int i = list.size() - 1; i >= 0; --i) {
            list.get((int)i).itemView.animate().cancel();
        }
    }

    void dispatchFinishedWhenDone() {
        if (!this.isRunning()) {
            this.dispatchAnimationsFinished();
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
        ArrayList<Object> arrayList;
        int n;
        View view = viewHolder.itemView;
        view.animate().cancel();
        for (n = this.mPendingMoves.size() - 1; n >= 0; --n) {
            if (this.mPendingMoves.get((int)n).holder != viewHolder) continue;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            this.dispatchMoveFinished(viewHolder);
            this.mPendingMoves.remove(n);
        }
        this.endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            view.setAlpha(1.0f);
            this.dispatchRemoveFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            view.setAlpha(1.0f);
            this.dispatchAddFinished(viewHolder);
        }
        for (n = this.mChangesList.size() - 1; n >= 0; --n) {
            arrayList = this.mChangesList.get(n);
            this.endChangeAnimation(arrayList, viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mChangesList.remove(n);
        }
        block2 : for (n = this.mMovesList.size() - 1; n >= 0; --n) {
            arrayList = this.mMovesList.get(n);
            for (int i = arrayList.size() - 1; i >= 0; --i) {
                if (((MoveInfo)arrayList.get((int)i)).holder != viewHolder) continue;
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                this.dispatchMoveFinished(viewHolder);
                arrayList.remove(i);
                if (!arrayList.isEmpty()) continue block2;
                this.mMovesList.remove(n);
                continue block2;
            }
        }
        for (n = this.mAdditionsList.size() - 1; n >= 0; --n) {
            arrayList = this.mAdditionsList.get(n);
            if (!arrayList.remove(viewHolder)) continue;
            view.setAlpha(1.0f);
            this.dispatchAddFinished(viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mAdditionsList.remove(n);
        }
        this.mRemoveAnimations.remove(viewHolder);
        this.mAddAnimations.remove(viewHolder);
        this.mChangeAnimations.remove(viewHolder);
        this.mMoveAnimations.remove(viewHolder);
        this.dispatchFinishedWhenDone();
    }

    @Override
    public void endAnimations() {
        int n;
        Object object;
        Object object2;
        int n2;
        for (n2 = this.mPendingMoves.size() - 1; n2 >= 0; --n2) {
            object = this.mPendingMoves.get(n2);
            object2 = object.holder.itemView;
            ((View)object2).setTranslationY(0.0f);
            ((View)object2).setTranslationX(0.0f);
            this.dispatchMoveFinished(((MoveInfo)object).holder);
            this.mPendingMoves.remove(n2);
        }
        for (n2 = this.mPendingRemovals.size() - 1; n2 >= 0; --n2) {
            this.dispatchRemoveFinished(this.mPendingRemovals.get(n2));
            this.mPendingRemovals.remove(n2);
        }
        for (n2 = this.mPendingAdditions.size() - 1; n2 >= 0; --n2) {
            object2 = this.mPendingAdditions.get(n2);
            ((RecyclerView.ViewHolder)object2).itemView.setAlpha(1.0f);
            this.dispatchAddFinished((RecyclerView.ViewHolder)object2);
            this.mPendingAdditions.remove(n2);
        }
        for (n2 = this.mPendingChanges.size() - 1; n2 >= 0; --n2) {
            this.endChangeAnimationIfNecessary(this.mPendingChanges.get(n2));
        }
        this.mPendingChanges.clear();
        if (!this.isRunning()) {
            return;
        }
        for (n2 = this.mMovesList.size() - 1; n2 >= 0; --n2) {
            ArrayList<MoveInfo> arrayList = this.mMovesList.get(n2);
            for (n = arrayList.size() - 1; n >= 0; --n) {
                object2 = arrayList.get(n);
                object = object2.holder.itemView;
                ((View)object).setTranslationY(0.0f);
                ((View)object).setTranslationX(0.0f);
                this.dispatchMoveFinished(((MoveInfo)object2).holder);
                arrayList.remove(n);
                if (!arrayList.isEmpty()) continue;
                this.mMovesList.remove(arrayList);
            }
        }
        for (n2 = this.mAdditionsList.size() - 1; n2 >= 0; --n2) {
            object = this.mAdditionsList.get(n2);
            for (n = object.size() - 1; n >= 0; --n) {
                object2 = (RecyclerView.ViewHolder)((ArrayList)object).get(n);
                ((RecyclerView.ViewHolder)object2).itemView.setAlpha(1.0f);
                this.dispatchAddFinished((RecyclerView.ViewHolder)object2);
                ((ArrayList)object).remove(n);
                if (!((ArrayList)object).isEmpty()) continue;
                this.mAdditionsList.remove(object);
            }
        }
        for (n2 = this.mChangesList.size() - 1; n2 >= 0; --n2) {
            object2 = this.mChangesList.get(n2);
            for (n = object2.size() - 1; n >= 0; --n) {
                this.endChangeAnimationIfNecessary((ChangeInfo)((ArrayList)object2).get(n));
                if (!((ArrayList)object2).isEmpty()) continue;
                this.mChangesList.remove(object2);
            }
        }
        this.cancelAll(this.mRemoveAnimations);
        this.cancelAll(this.mMoveAnimations);
        this.cancelAll(this.mAddAnimations);
        this.cancelAll(this.mChangeAnimations);
        this.dispatchAnimationsFinished();
    }

    @Override
    public boolean isRunning() {
        boolean bl = !(this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty());
        return bl;
    }

    @Override
    public void runPendingAnimations() {
        Object object;
        boolean bl = this.mPendingRemovals.isEmpty() ^ true;
        boolean bl2 = this.mPendingMoves.isEmpty() ^ true;
        boolean bl3 = this.mPendingChanges.isEmpty() ^ true;
        boolean bl4 = this.mPendingAdditions.isEmpty() ^ true;
        if (!(bl || bl2 || bl4 || bl3)) {
            return;
        }
        Object object2 = this.mPendingRemovals.iterator();
        while (object2.hasNext()) {
            this.animateRemoveImpl(object2.next());
        }
        this.mPendingRemovals.clear();
        if (bl2) {
            object2 = new ArrayList();
            ((ArrayList)object2).addAll(this.mPendingMoves);
            this.mMovesList.add((ArrayList<MoveInfo>)object2);
            this.mPendingMoves.clear();
            object = new Runnable((ArrayList)object2){
                final /* synthetic */ ArrayList val$moves;
                {
                    this.val$moves = arrayList;
                }

                @Override
                public void run() {
                    for (MoveInfo moveInfo : this.val$moves) {
                        DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                    }
                    this.val$moves.clear();
                    DefaultItemAnimator.this.mMovesList.remove(this.val$moves);
                }
            };
            if (bl) {
                ((MoveInfo)object2.get((int)0)).holder.itemView.postOnAnimationDelayed((Runnable)object, this.getRemoveDuration());
            } else {
                object.run();
            }
        }
        if (bl3) {
            object = new ArrayList<Object>();
            ((ArrayList)object).addAll(this.mPendingChanges);
            this.mChangesList.add((ArrayList<ChangeInfo>)object);
            this.mPendingChanges.clear();
            object2 = new Runnable((ArrayList)object){
                final /* synthetic */ ArrayList val$changes;
                {
                    this.val$changes = arrayList;
                }

                @Override
                public void run() {
                    for (ChangeInfo changeInfo : this.val$changes) {
                        DefaultItemAnimator.this.animateChangeImpl(changeInfo);
                    }
                    this.val$changes.clear();
                    DefaultItemAnimator.this.mChangesList.remove(this.val$changes);
                }
            };
            if (bl) {
                ((ChangeInfo)object.get((int)0)).oldHolder.itemView.postOnAnimationDelayed((Runnable)object2, this.getRemoveDuration());
            } else {
                object2.run();
            }
        }
        if (bl4) {
            object = new ArrayList();
            ((ArrayList)object).addAll(this.mPendingAdditions);
            this.mAdditionsList.add((ArrayList<RecyclerView.ViewHolder>)object);
            this.mPendingAdditions.clear();
            object2 = new Runnable((ArrayList)object){
                final /* synthetic */ ArrayList val$additions;
                {
                    this.val$additions = arrayList;
                }

                @Override
                public void run() {
                    for (RecyclerView.ViewHolder viewHolder : this.val$additions) {
                        DefaultItemAnimator.this.animateAddImpl(viewHolder);
                    }
                    this.val$additions.clear();
                    DefaultItemAnimator.this.mAdditionsList.remove(this.val$additions);
                }
            };
            if (!(bl || bl2 || bl3)) {
                object2.run();
            } else {
                long l = 0L;
                long l2 = bl ? this.getRemoveDuration() : 0L;
                long l3 = bl2 ? this.getMoveDuration() : 0L;
                if (bl3) {
                    l = this.getChangeDuration();
                }
                l3 = Math.max(l3, l);
                ((RecyclerView.ViewHolder)object.get((int)0)).itemView.postOnAnimationDelayed((Runnable)object2, l3 + l2);
            }
        }
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
            this(viewHolder, viewHolder2);
            this.fromX = n;
            this.fromY = n2;
            this.toX = n3;
            this.toY = n4;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ChangeInfo{oldHolder=");
            stringBuilder.append(this.oldHolder);
            stringBuilder.append(", newHolder=");
            stringBuilder.append(this.newHolder);
            stringBuilder.append(", fromX=");
            stringBuilder.append(this.fromX);
            stringBuilder.append(", fromY=");
            stringBuilder.append(this.fromY);
            stringBuilder.append(", toX=");
            stringBuilder.append(this.toX);
            stringBuilder.append(", toY=");
            stringBuilder.append(this.toY);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        MoveInfo(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
            this.holder = viewHolder;
            this.fromX = n;
            this.fromY = n2;
            this.toX = n3;
            this.toY = n4;
        }
    }

}

