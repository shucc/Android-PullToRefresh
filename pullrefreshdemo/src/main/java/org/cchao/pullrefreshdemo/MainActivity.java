package org.cchao.pullrefreshdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_one).setOnClickListener(view -> NestedScrollViewActivity.launch(this));
        findViewById(R.id.btn_two).setOnClickListener(view -> RecyclerViewActivity.launch(this));
    }
}
