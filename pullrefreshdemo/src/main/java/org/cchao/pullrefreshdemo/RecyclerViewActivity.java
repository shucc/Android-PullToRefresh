package org.cchao.pullrefreshdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/12/11.
 * cc@cchao.org
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private PullToRefreshRecyclerView prRecycler;

    private List<String> data;

    private DataAdapter adapter;

    private int page = 1;

    public static void launch(Context context) {
        Intent starter = new Intent(context, RecyclerViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_refresh);
        prRecycler = findViewById(R.id.pr_recycler);

        data = new ArrayList<>();

        prRecycler.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                page++;
                getData();
            }
        });
        prRecycler.postDelayed(() -> prRecycler.setRefreshing(), 500);
    }

    private void getData() {
        prRecycler.postDelayed(() -> {
            if (page == 1) {
                data.clear();
            }
            int start = (page - 1) * 10;
            for (int i = start; i < start + 10; i++) {
                data.add("我是内容" + i);
            }
            prRecycler.onRefreshComplete();
            showData();
        }, 1000);
    }

    private void showData() {
        if (null == adapter) {
            RecyclerView recyclerView = prRecycler.getRefreshableView();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new DataAdapter(data);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> Toast.makeText(RecyclerViewActivity.this, data.get(position), Toast.LENGTH_SHORT).show());
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
