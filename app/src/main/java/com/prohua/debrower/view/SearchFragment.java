package com.prohua.debrower.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prohua.debrower.R;
import com.prohua.debrower.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 搜索界面
 * Created by Deep on 2017/9/19 0019.
 */

public class SearchFragment extends SupportFragment {

    @BindView(R.id.search_text)
    TextView tvSearch;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvSearch.getLayoutParams();
        layoutParams.topMargin = (int) (StatusBarUtil.getStatusBarHeight(getContext()) + getResources().getDimension(R.dimen.x50));
        tvSearch.setLayoutParams(layoutParams);

        tvSearch.setFocusable(true);
        tvSearch.setFocusableInTouchMode(true);
        tvSearch.requestFocus();
        //打开软键盘
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}