package com.hcdarren.view_day27;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hcdarren.view_day27.parallax.animation.ParallaxFragment;
import com.hcdarren.view_day27.parallax.animation.ParallaxViewPager;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // 2.2.1 先把布局和 Fragment 创建好
    private ParallaxViewPager mParallaxViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // findViewById 给ViewPager设置Adapter，意味着代码全部写到activity来了，封装（自定义View）
        mParallaxViewPager = (ParallaxViewPager) findViewById(R.id.parallax_vp);
        // 给一个方法 得一个布局数组 // 用最简便的方式让别人使用
        mParallaxViewPager.setLayout(getSupportFragmentManager(),
                new int[]{R.layout.fragment_page_first, R.layout.fragment_page_second,
                        R.layout.fragment_page_third, R.layout.fragment_page_first});
        mParallaxViewPager.setOnFragmentClickListener(new ParallaxViewPager.OnFragmentClickListener() {
            @Override
            public void getVieMap(Map<Integer, View> view) {
                Log.e("=======mid", "" + R.layout.fragment_page_first);
                view.get(R.layout.fragment_page_first).findViewById(R.id.ivThirdImage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("=======", "parallaxFragment点击");
                    }
                });

            }
        });
//        ParallaxFragment parallaxFragment=mParallaxViewPager.getFragments().get(0);
//
//        if (parallaxFragment!=null){
//            parallaxFragment.getView().findViewById(R.id.ivThirdImage).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("=======","parallaxFragment点击");
//                }
//            });
//            Log.e("=======","parallaxFragment不为空");
//        }else {
//            Log.e("=======","parallaxFragment为空");
//        }
//        mParallaxViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                int index = mParallaxViewPager.getCurrentItem();
//                ParallaxFragment parallaxFragment = (ParallaxFragment) mParallaxViewPager.getAdapter().instantiateItem(mParallaxViewPager, index);
//                if (parallaxFragment != null) {
//                    parallaxFragment.getView().findViewById(R.id.ivThirdImage).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.e("=======", "parallaxFragment点击");
//                        }
//                    });
//                    Log.e("=======", "parallaxFragment不为空");
//                } else {
//                    Log.e("=======", "parallaxFragment为空");
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }
}
