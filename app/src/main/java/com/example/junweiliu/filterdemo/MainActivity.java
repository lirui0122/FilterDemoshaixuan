package com.example.junweiliu.filterdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.junweiliu.filterdemo.bean.PlaceBean;
import com.example.junweiliu.filterdemo.bean.TimeBean;
import com.example.junweiliu.filterdemo.pop.DialogGetHeadPicture;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends BaseActivity {
    /**
     * 展示城市的数据源
     */
    List<PlaceBean> mPopBeens = new ArrayList<>();
    /**
     * 展示类型的数据
     */
    List<String> mTypes = new ArrayList<>();
    /**
     * 展示时间的数据
     */
    List<TimeBean> mTimes = new ArrayList<>();
    /**
     * 展示的时间str集合
     */
    List<String> mTimeStr = new ArrayList<>();
    /**
     * 筛选地区整体
     */
    LinearLayout mPlaceAll;
    /**
     * 筛选城市cb
     */
    CheckBox mPlaceCb;
    /**
     * 筛选类型整体
     */
    LinearLayout mTypeAll;
    /**
     * 筛选类型整体
     */
    CheckBox mTypeCb;
    /**
     * 筛选时间整体
     */
    LinearLayout mTimeAll;
    /**
     * 筛选时间整体
     */
    CheckBox mTimeCb;
    DialogGetHeadPicture GetHeadPicture = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDate();
        initView();
    }


    /**
     * 初始化数据
     */
    private void initDate() {
        // 初始化城市
        PlaceBean placeBean1 = new PlaceBean("天津");
        PlaceBean placeBean2 = new PlaceBean("北京");
        PlaceBean placeBean3 = new PlaceBean("上海");
        PlaceBean placeBean4 = new PlaceBean("深圳");
        PlaceBean placeBean5 = new PlaceBean("四川");
        PlaceBean placeBean6 = new PlaceBean("杭州");
        PlaceBean placeBean7 = new PlaceBean("苏州");
        mPopBeens.add(placeBean1);
        mPopBeens.add(placeBean2);
        mPopBeens.add(placeBean3);
        mPopBeens.add(placeBean4);
        mPopBeens.add(placeBean5);
        mPopBeens.add(placeBean6);
        mPopBeens.add(placeBean7);
        // 初始化类型
        mTypes.add("美食");
        mTypes.add("电影");
        mTypes.add("化妆品");
        mTypes.add("衣服");
        mTypes.add("玩具");
        mTypes.add("电器");
        mTypes.add("装饰");
        mTypes.add("超市");
        // 初始化时间
        TimeBean timeBean1 = new TimeBean("1天内", "去玩");
        TimeBean timeBean2 = new TimeBean("3天内", "去购物");
        TimeBean timeBean3 = new TimeBean("10天内", "去旅行");
        TimeBean timeBean4 = new TimeBean("30天内", "去赚钱");
        mTimes.add(timeBean1);
        mTimes.add(timeBean2);
        mTimes.add(timeBean3);
        mTimes.add(timeBean4);
        // 获取时间中可用于筛选的数据
        for (TimeBean bean : mTimes) {
            mTimeStr.add(bean.getTimeStr());
        }
    }


    /**
     * 初始化控件
     */
    private void initView() {
        mPlaceAll = (LinearLayout) findViewById(R.id.ll_place_tab);
        mPlaceCb = (CheckBox) findViewById(R.id.cb_place);
        mTypeAll = (LinearLayout) findViewById(R.id.ll_type);
        mTypeCb = (CheckBox) findViewById(R.id.cb_type);
        mTimeAll = (LinearLayout) findViewById(R.id.ll_time);
        mTimeCb = (CheckBox) findViewById(R.id.cb_time);
        // 点击选择城市整体
        mPlaceAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlaceCb.isChecked())
                    mPlaceCb.setChecked(false);
                else
                    mPlaceCb.setChecked(true);
            }
        });
        // 点击选择类型整体
        mTypeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTypeCb.isChecked())
                    mTypeCb.setChecked(false);
                else
                    mTypeCb.setChecked(true);
            }
        });
        // 点击选择时间整体
        mTimeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimeCb.isChecked())
                    mTimeCb.setChecked(false);
                else
                    mTimeCb.setChecked(true);
            }
        });

        // 选择城市cb
        mPlaceCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                filterTabToggleT(isChecked, mPlaceAll, mPopBeens, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        hidePopListView();
                        mPlaceCb.setText(mPopBeens.get(position).getFilterStr());
                    }
                }, mPlaceCb, mTypeCb, mTimeCb);
                if (GetHeadPicture != null) {
                    GetHeadPicture.dismiss();
                }
            }
        });

        // 选择类型cb
        mTypeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                filterTabToggle(isChecked, mTypeAll, mTypes, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        hidePopListView();
                        mTypeCb.setText(mTypes.get(position));
                    }
                }, mTypeCb, mPlaceCb, mTimeCb);
                if (GetHeadPicture != null) {
                    GetHeadPicture.dismiss();
                }

            }
        });
        // 选择时间cb
        mTimeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                filterTabToggle(isChecked, mTimeAll, mTimeStr, new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        hidePopListView();
//                        mTimeCb.setText(mTimeStr.get(position));
//                    }
//                }, mTimeCb, mPlaceCb, mTypeCb);
                showDialog();
            }
        });

    }


    /**
     * 自定义调用音频、视频对话框
     *
     * @param
     */
    private void showDialog() {

        GetHeadPicture = new DialogGetHeadPicture(MainActivity.this){};
        GetHeadPicture.show();
    }
}
