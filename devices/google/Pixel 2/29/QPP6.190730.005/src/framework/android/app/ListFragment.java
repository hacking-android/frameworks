/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@Deprecated
public class ListFragment
extends Fragment {
    ListAdapter mAdapter;
    CharSequence mEmptyText;
    View mEmptyView;
    private final Handler mHandler = new Handler();
    ListView mList;
    View mListContainer;
    boolean mListShown;
    private final AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
            ListFragment.this.onListItemClick((ListView)adapterView, view, n, l);
        }
    };
    View mProgressContainer;
    private final Runnable mRequestFocus = new Runnable(){

        @Override
        public void run() {
            ListFragment.this.mList.focusableViewAvailable(ListFragment.this.mList);
        }
    };
    TextView mStandardEmptyView;

    private void ensureList() {
        block12 : {
            block15 : {
                block16 : {
                    Object object;
                    block14 : {
                        block13 : {
                            if (this.mList != null) {
                                return;
                            }
                            object = this.getView();
                            if (object == null) break block12;
                            if (!(object instanceof ListView)) break block13;
                            this.mList = (ListView)object;
                            break block14;
                        }
                        this.mStandardEmptyView = (TextView)((View)object).findViewById(16909037);
                        View view = this.mStandardEmptyView;
                        if (view == null) {
                            this.mEmptyView = ((View)object).findViewById(16908292);
                        } else {
                            view.setVisibility(8);
                        }
                        this.mProgressContainer = ((View)object).findViewById(16909259);
                        this.mListContainer = ((View)object).findViewById(16909070);
                        object = ((View)object).findViewById(16908298);
                        if (!(object instanceof ListView)) break block15;
                        this.mList = (ListView)object;
                        view = this.mList;
                        if (view == null) break block16;
                        object = this.mEmptyView;
                        if (object != null) {
                            ((AdapterView)view).setEmptyView((View)object);
                        } else {
                            object = this.mEmptyText;
                            if (object != null) {
                                this.mStandardEmptyView.setText((CharSequence)object);
                                this.mList.setEmptyView(this.mStandardEmptyView);
                            }
                        }
                    }
                    this.mListShown = true;
                    this.mList.setOnItemClickListener(this.mOnClickListener);
                    if (this.mAdapter != null) {
                        object = this.mAdapter;
                        this.mAdapter = null;
                        this.setListAdapter((ListAdapter)object);
                    } else if (this.mProgressContainer != null) {
                        this.setListShown(false, false);
                    }
                    this.mHandler.post(this.mRequestFocus);
                    return;
                }
                throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
            }
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
        }
        throw new IllegalStateException("Content view not yet created");
    }

    private void setListShown(boolean bl, boolean bl2) {
        this.ensureList();
        View view = this.mProgressContainer;
        if (view != null) {
            if (this.mListShown == bl) {
                return;
            }
            this.mListShown = bl;
            if (bl) {
                if (bl2) {
                    view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), 17432577));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(this.getContext(), 17432576));
                } else {
                    view.clearAnimation();
                    this.mListContainer.clearAnimation();
                }
                this.mProgressContainer.setVisibility(8);
                this.mListContainer.setVisibility(0);
            } else {
                if (bl2) {
                    view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), 17432576));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(this.getContext(), 17432577));
                } else {
                    view.clearAnimation();
                    this.mListContainer.clearAnimation();
                }
                this.mProgressContainer.setVisibility(0);
                this.mListContainer.setVisibility(8);
            }
            return;
        }
        throw new IllegalStateException("Can't be used with a custom content view");
    }

    public ListAdapter getListAdapter() {
        return this.mAdapter;
    }

    public ListView getListView() {
        this.ensureList();
        return this.mList;
    }

    public long getSelectedItemId() {
        this.ensureList();
        return this.mList.getSelectedItemId();
    }

    public int getSelectedItemPosition() {
        this.ensureList();
        return this.mList.getSelectedItemPosition();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(17367060, viewGroup, false);
    }

    @Override
    public void onDestroyView() {
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mList = null;
        this.mListShown = false;
        this.mListContainer = null;
        this.mProgressContainer = null;
        this.mEmptyView = null;
        this.mStandardEmptyView = null;
        super.onDestroyView();
    }

    public void onListItemClick(ListView listView, View view, int n, long l) {
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.ensureList();
    }

    public void setEmptyText(CharSequence charSequence) {
        this.ensureList();
        TextView textView = this.mStandardEmptyView;
        if (textView != null) {
            textView.setText(charSequence);
            if (this.mEmptyText == null) {
                this.mList.setEmptyView(this.mStandardEmptyView);
            }
            this.mEmptyText = charSequence;
            return;
        }
        throw new IllegalStateException("Can't be used with a custom content view");
    }

    public void setListAdapter(ListAdapter listAdapter) {
        Object object = this.mAdapter;
        boolean bl = false;
        boolean bl2 = object != null;
        this.mAdapter = listAdapter;
        object = this.mList;
        if (object != null) {
            ((ListView)object).setAdapter(listAdapter);
            if (!this.mListShown && !bl2) {
                if (this.getView().getWindowToken() != null) {
                    bl = true;
                }
                this.setListShown(true, bl);
            }
        }
    }

    public void setListShown(boolean bl) {
        this.setListShown(bl, true);
    }

    public void setListShownNoAnimation(boolean bl) {
        this.setListShown(bl, false);
    }

    public void setSelection(int n) {
        this.ensureList();
        this.mList.setSelection(n);
    }

}

