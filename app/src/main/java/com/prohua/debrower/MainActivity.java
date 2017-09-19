package com.prohua.debrower;

import android.os.Bundle;

import com.prohua.debrower.util.StatusBarUtil;
import com.prohua.debrower.view.LogoFragment;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarUtil.darkMode(this);

        if (findFragment(LogoFragment.class) == null) {
            loadRootFragment(R.id.root_view, LogoFragment.newInstance());  // 加载根Fragment
        }
    }
}
