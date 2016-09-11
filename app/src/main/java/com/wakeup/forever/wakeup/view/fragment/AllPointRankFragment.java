package com.wakeup.forever.wakeup.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.bijection.BeamFragment;
import com.jude.beam.bijection.RequiresPresenter;
import com.wakeup.forever.wakeup.R;
import com.wakeup.forever.wakeup.model.bean.UserPoint;
import com.wakeup.forever.wakeup.presenter.adapter.PointRankAdapter;
import com.wakeup.forever.wakeup.presenter.fragmentPresenter.AllPointRankFragmentPresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresPresenter(AllPointRankFragmentPresenter.class)
public class AllPointRankFragment extends BeamFragment<AllPointRankFragmentPresenter> {


    @Bind(R.id.rv_allRank)
    RecyclerView rvAllRank;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srlRefresh;

    private PointRankAdapter pointRankAdapter;
    private ArrayList<UserPoint> userPointList;
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem;
    private int viewCount;

    public AllPointRankFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_point_rank, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
    }

    private void initData() {
        getPresenter().refreshData();
        rvAllRank.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && (lastVisibleItem+1 == userPointList.size())) {
                    getPresenter().loadMore(userPointList.size());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                viewCount=lastVisibleItem-linearLayoutManager.findFirstVisibleItemPosition();
            }
        });

        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().refreshData();
            }
        });
    }

    private void initView() {
        userPointList=new ArrayList<UserPoint>();
        pointRankAdapter=new PointRankAdapter(userPointList,getContext());
        linearLayoutManager=new LinearLayoutManager(getContext());
        rvAllRank.setLayoutManager(linearLayoutManager);
        rvAllRank.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(getContext())
                .drawable(R.drawable.shape_common_divider)
                .size(10)
                .build()
        );
        rvAllRank.setAdapter(pointRankAdapter);
    }

    public void onRefreshData(ArrayList<UserPoint> userPoints){
        userPointList.clear();
        userPointList.addAll(userPoints);
        pointRankAdapter.notifyDataSetChanged();
    }

    public void onLoadMore(ArrayList<UserPoint> userPoints){
        userPointList.addAll(userPoints);
        pointRankAdapter.notifyDataSetChanged();
    }

    public void stopRefresh(){
        srlRefresh.setRefreshing(false);
    }
}
