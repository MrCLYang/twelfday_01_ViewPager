package com.example.yangchenglei.twelfday_01;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
* 广告轮播图案例
* 1.图片的滑动
* 2.滑动的时候圆形切换
* 3.文本在滑动的时候也切换
* 4.可以无限滑动
* */
public class MainActivity extends Activity {


    private ViewPager mPager;
    private List<ImageView> mListDatas;
    private LinearLayout mPointContainer;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPointContainer = (LinearLayout) findViewById(R.id.point_container);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        int imgs[] = {R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5};
        final String titles[] = {"为梦想坚持", "我相信我是黑马", "黑马公开课", "google IO", "轻松高工资"};
        //viewpager和listview用法一样
        //初始化数据

        mListDatas = new ArrayList<ImageView>();
        for (int i = 0; i < imgs.length; i++) {
            //添加imageview
            ImageView iv = new ImageView(this);
            iv.setImageResource(imgs[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mListDatas.add(iv);
            //动态的添加圆点
            View ponit = new View(this);
            ponit.setBackgroundResource(R.drawable.point_pormal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i != 0) {
                params.leftMargin = 10;
            } else {
                ponit.setBackgroundResource(R.drawable.point_selected);
                mTvTitle.setText(titles[i]);
            }
            mPointContainer.addView(ponit, params);


        }
        //设置数据关系
        mPager.setAdapter(new MyAdapter());
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int count;
            private View view;

            //当滚动pager时候调用的方法
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //position:当前选中的位置，positionOffset：滑动的百分比，positionOffsetPixels：偏移的距离

            }

            //当viewpager的某个页面选中时候回调
            @Override
            public void onPageSelected(int position) {
                position = position % mListDatas.size();
                //设置选中点的样式
                count = mPointContainer.getChildCount();
                for (int i = 0; i < count; i++) {
                    view = mPointContainer.getChildAt(i);
                    if (position == i) {
                        view.setBackgroundResource(R.drawable.point_selected);
                        mTvTitle.setText(titles[position]);
                    } else {
                        view.setBackgroundResource(R.drawable.point_pormal);
                    }
                }


            }

            //滑动状态改变的时候调用
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置默认选中中间的item

        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % mListDatas.size();
        int item = middle - extra;
        mPager.setCurrentItem(item);
    }

    class MyAdapter extends PagerAdapter {
        //返回的是页面的数量
        @Override
        public int getCount() {
            if (mListDatas != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        //用来判断缓存的标记,view 为显示的view object 为标记
        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        //初始化item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加要显示的view
            //取模来实现无限滑动
            position = position % mListDatas.size();
            ImageView iv = mListDatas.get(position);
            mPager.addView(iv);
            return iv;
        }

        //移除item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            position = position % mListDatas.size();
            ImageView iv = mListDatas.get(position);
            mPager.removeView(iv);
        }
    }
}
