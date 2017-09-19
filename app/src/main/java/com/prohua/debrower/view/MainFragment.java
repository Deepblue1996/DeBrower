package com.prohua.debrower.view;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.AnimationBuilder;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.prohua.debrower.R;
import com.prohua.debrower.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主页
 * Created by Deep on 2017/9/19 0019.
 */

public class MainFragment extends SupportFragment {

    @BindView(R.id.search_text)
    TextView tvSearch;
    @BindView(R.id.content_lin)
    LinearLayout clContent;
    @BindView(R.id.web_win)
    RelativeLayout webWin;
    @BindView(R.id.web_menu)
    ImageView webMenu;

    // 再点一次退出, 程序间隔时间设置
    private static final long WAIT_TIME = 2000L;
    // 第一次触摸的时间
    private long TOUCH_TIME = 0;

    private boolean openSearch = false;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    private void initView() {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvSearch.getLayoutParams();
        layoutParams.topMargin = (int) (StatusBarUtil.getStatusBarHeight(getContext()) + getResources().getDimension(R.dimen.x50));
        tvSearch.setLayoutParams(layoutParams);

        tvSearch.setOnClickListener(view -> {

            WindowManager wm = getActivity().getWindowManager();
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);

            clContent.measure(0, 0);
            tvSearch.measure(0, 0);

            float width = dm.widthPixels - getResources().getDimension(R.dimen.x100);
            float fWidth = dm.widthPixels - getResources().getDimension(R.dimen.x50);

            ViewAnimator
                    .animate(tvSearch)
                    .translationY(getResources().getDimension(R.dimen.x200), 0)
                    .width(width, fWidth)
                    .duration(200)
                    .onStop(() -> {
                        openSearch = true;
                        extraTransaction()
                                .setCustomAnimations(0, 0)
                                .start(SearchFragment.newInstance());
                    }).start();

            ViewAnimator
                    .animate(webWin)
                    .alpha(1.0f,0.0f)
                    .duration(200).start();

            ViewAnimator
                    .animate(webMenu)
                    .alpha(1.0f,0.0f)
                    .duration(200).start();

        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && openSearch) {
            WindowManager wm = getActivity().getWindowManager();
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);

            clContent.measure(0, 0);
            tvSearch.measure(0, 0);


            float width = dm.widthPixels - getResources().getDimension(R.dimen.x100);
            float fWidth = dm.widthPixels - getResources().getDimension(R.dimen.x50);

            ViewAnimator
                    .animate(tvSearch)
                    .translationY(0, getResources().getDimension(R.dimen.x200))
                    .width(fWidth, width)
                    .duration(200)
                    .onStop(() -> openSearch = false)
                    .start();

            ViewAnimator
                    .animate(webWin)
                    .alpha(0.0f,1.0f)
                    .duration(200).start();

            ViewAnimator
                    .animate(webMenu)
                    .alpha(0.0f,1.0f)
                    .duration(200).start();
        }
    }

    /**
     * 处理回退事件
     *
     * @return true
     */
    @Override
    public boolean onBackPressedSupport() {
        if (_mActivity.getSupportFragmentManager().getBackStackEntryCount() > 2) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                // 杀死线程,完全退出
                //android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                //System.exit(0);
                _mActivity.finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                Toast.makeText(getContext(), "再按一次,确定退出", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }
}

