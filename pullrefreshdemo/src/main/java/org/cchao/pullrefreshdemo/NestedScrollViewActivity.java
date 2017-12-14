package org.cchao.pullrefreshdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshNestedScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shucc on 17/12/11.
 * cc@cchao.org
 */
public class NestedScrollViewActivity extends AppCompatActivity {

    private PullToRefreshNestedScrollView prScrollView;
    private RecyclerView rvData;

    private List<String> data;

    private DataAdapter adapter;

    private int page = 1;

    public static void launch(Context context) {
        Intent starter = new Intent(context, NestedScrollViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscrollview_refresh);
        prScrollView = findViewById(R.id.pr_scroll);
        rvData = findViewById(R.id.rv_data);

        data = new ArrayList<>();

        prScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<NestedScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<NestedScrollView> refreshView) {
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<NestedScrollView> refreshView) {
                page++;
                getData();
            }
        });
        prScrollView.postDelayed(() -> prScrollView.setRefreshing(), 500);
    }

    private void getData() {
        prScrollView.postDelayed(() -> {
            if (page == 1) {
                data.clear();
            }
            int start = (page - 1) * 10;
            for (int i = start; i < start + 10; i++) {
                data.add("我是内容" + i);
            }
            prScrollView.onRefreshComplete();
            showData();
        }, 1000);
    }

    private void showData() {
        if (null == adapter) {
            rvData.setLayoutManager(new LinearLayoutManager(this));
            rvData.setNestedScrollingEnabled(false);
            adapter = new DataAdapter(data);
            rvData.setAdapter(adapter);
            adapter.setOnItemClickListener(position -> Toast.makeText(this, data.get(position), Toast.LENGTH_SHORT).show());
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
