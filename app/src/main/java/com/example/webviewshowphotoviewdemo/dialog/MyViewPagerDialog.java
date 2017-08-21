package com.example.webviewshowphotoviewdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.webviewshowphotoviewdemo.Config;
import com.example.webviewshowphotoviewdemo.R;
import com.example.webviewshowphotoviewdemo.adapter.MyViewPagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by hasee on 2017/8/21.
 */

public class MyViewPagerDialog extends Dialog {
    View mView;

    List<String> mImgUrls;
    private ViewPager mViewPager;
    private TextView mPagerTextView;
    private List<View> mViewList;

    public MyViewPagerDialog(@NonNull Context context, List<String> imgUrls) {

        //调用父类的构造器,传一个style给dialog
        super(context, R.style.transparentBgDialog);
        initView(context);
        initData(context, imgUrls);
    }

    /**
     * 去for循环添加一个photoView的集合传给viewPagerAdapter
     */
    private void initData(Context context, List<String> imgUrls) {
        mImgUrls = imgUrls;
        mViewList = new ArrayList<>();

        //默认为第一页
        mPagerTextView.setText("1/" + mImgUrls.size());
        for (String url : mImgUrls) {
            final PhotoView photoView = new PhotoView(context);

            //单击事件  当单击photoView的时候让这个dialog消失
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {

                    //让dialog消失
                    dismiss();
                }
            });

            //设置photoView充满父布局
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .build();
            ImageLoader.getInstance().displayImage(url, photoView, options);
            mViewList.add(photoView);
        }
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(mViewList);
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //viewpager切换的时候去改变当前页数
                mPagerTextView.setText(position + 1 + "/" + mImgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.view_pager_dialog_layout, null);
        mViewPager = (ViewPager) mView.findViewById(R.id.my_viewpager);
        mPagerTextView = (TextView) mView.findViewById(R.id.pager_tv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mView);

        //设置dialog 是全屏展示
        //一个dialog一个window,默认dialog的这个window是窗口的形式,不是全屏
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = Config.WINDOW_WIDTH;
        layoutParams.height = Config.WINDOW_HEIGHT;
        window.setAttributes(layoutParams);
    }
}
