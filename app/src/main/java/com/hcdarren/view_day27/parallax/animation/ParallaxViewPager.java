package com.hcdarren.view_day27.parallax.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hcdarren.view_day27.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hcDarren on 2017/8/12.
 * 视差动画的ViewPager
 */

public class ParallaxViewPager extends ViewPager {
    private List<ParallaxFragment> mFragments;

    private List<Integer>mId;

    private OnFragmentClickListener onFragmentClickListener;

    private Map<Integer, View> mViews;


    public ParallaxViewPager(Context context) {
        this(context, null);
    }

    public ParallaxViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFragments = new ArrayList<>();
        mViews = new HashMap<>();
        mId=new ArrayList<>();

    }

    public void setOnFragmentClickListener(OnFragmentClickListener onFragmentClickListener) {
        this.onFragmentClickListener = onFragmentClickListener;

    }

    /**
     * 设置布局数组
     *
     * @param layoutIds
     */
    public void setLayout(FragmentManager fm, int[] layoutIds) {
        mFragments.clear();
        mId.clear();
        // 实例化Fragment
        for (int layoutId : layoutIds) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layoutId);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            mId.add(layoutId);
        }
        // 设置我们的 ViewPager 的Adapter
        setAdapter(new ParallaxPagerAdapter(fm));

        // 2.2.3 监听滑动滚动改变位移
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滚动  position 当前位置    positionOffset 0-1     positionOffsetPixels 0-屏幕的宽度px
                Log.e("TAG", "position->" + position + " positionOffset->" + positionOffset + " positionOffsetPixels->" + positionOffsetPixels);

                // 获取左out 右 in
                ParallaxFragment outFragment = mFragments.get(position);

                mViews.put(mId.get(position), outFragment.getView());
                Log.e("=======id", "" + outFragment.getId());
                onFragmentClickListener.getVieMap(mViews);

                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                    // 为什么这样写 ？
                    parallaxView.setTranslationX((-positionOffsetPixels) * tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels) * tag.translationYOut);
                }

                try {
                    ParallaxFragment inFragment = mFragments.get(position + 1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                        parallaxView.setTranslationX((getMeasuredWidth() - positionOffsetPixels) * tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth() - positionOffsetPixels) * tag.translationYIn);
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onPageSelected(int position) {
                // 选择切换完毕
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public List<ParallaxFragment> getFragments() {

        return mFragments;
    }

    private class ParallaxPagerAdapter extends FragmentPagerAdapter {

        public ParallaxPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }


    public interface OnFragmentClickListener {
        void getVieMap(Map<Integer, View> view);
    }
}
