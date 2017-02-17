package com.example.junweiliu.filterdemo.pop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junweiliu.filterdemo.R;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/2/16.
 */

public abstract class DialogGetHeadPicture extends Dialog implements View.OnClickListener,
        ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener {

    private Activity activity;
    private Button btn_cancel;
    private ExpandableListView expandableListView;
    private MyexpandableListAdapter adapter;
    private ArrayList<String> groupList;
    private ArrayList<List<String>> childList;
    private String mChildText = null;
    private int mGroupPion = 0;


    public DialogGetHeadPicture(Activity activity) {
        super(activity, R.style.GetHeadPictureTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_setting_get_head_picture);

        expandableListView = (ExpandableListView) findViewById(R.id.expandablelist);
        InitData();
        adapter = new MyexpandableListAdapter(activity, groupList, childList);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /***
     * InitData
     */
    void InitData() {
        groupList = new ArrayList<String>();
        groupList.add("Ios");
        groupList.add("Android");
        groupList.add("Window");
        childList = new ArrayList<List<String>>();
        for (int i = 0; i < groupList.size(); i++) {
            ArrayList<String> childTemp;
            if (i == 0) {
                childTemp = new ArrayList<String>();
                childTemp.add("iphone 4");
                childTemp.add("iphone 5");
            } else if (i == 1) {
                childTemp = new ArrayList<String>();
                childTemp.add("Anycall");
                childTemp.add("HTC");
                childTemp.add("Motorola");
            } else {
                childTemp = new ArrayList<String>();
                childTemp.add("Lumia 800C ");
            }
            childList.add(childTemp);
        }

    }


    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int) (dm.heightPixels * 0.7);
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                this.cancel();
                break;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//        Toast.makeText(activity.getApplicationContext(),
//                childList.get(groupPosition).get(childPosition), Toast.LENGTH_LONG).show();
        Toast.makeText(activity.getApplicationContext(),
                groupPosition + "", Toast.LENGTH_SHORT).show();
        mChildText = childList.get(groupPosition).get(childPosition);

        expandableListView.collapseGroup(childPosition);
        expandableListView.expandGroup(childPosition);
        mGroupPion =groupPosition;
        Toast.makeText(activity.getApplicationContext(),
                groupPosition + "", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }

    public class MyexpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private LayoutInflater inflater;
        private ArrayList<String> groupList;
        private ArrayList<List<String>> childList;


        public MyexpandableListAdapter(Context context, ArrayList<String> groupList, ArrayList<List<String>> childList) {
            this.context = context;
            this.groupList = groupList;
            this.childList = childList;
            inflater = LayoutInflater.from(context);
        }

        // ���ظ��б����
        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        // �������б����
        @Override
        public int getChildrenCount(int groupPosition) {
            return childList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {

            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {

            return true;
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            MyexpandableListAdapter.GroupHolder groupHolder = null;
            if (convertView == null) {
                groupHolder = new MyexpandableListAdapter.GroupHolder();
                convertView = inflater.inflate(R.layout.group, null);
                groupHolder.textView = (TextView) convertView
                        .findViewById(R.id.group);
                groupHolder.textView1 = (TextView) convertView
                        .findViewById(R.id.group_text);
                groupHolder.textView2 = (TextView) convertView
                        .findViewById(R.id.group_text1);
                groupHolder.textView3 = (TextView) convertView
                        .findViewById(R.id.group_text2);
                groupHolder.imageView = (ImageView) convertView
                        .findViewById(R.id.image);
                groupHolder.textView.setTextSize(15);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (MyexpandableListAdapter.GroupHolder) convertView.getTag();
            }

            groupHolder.textView.setText(getGroup(groupPosition).toString());
            if (mGroupPion == groupPosition) {

                groupHolder.textView1.setText(mChildText);
                groupHolder.textView2.setVisibility(View.VISIBLE);
                groupHolder.textView3.setVisibility(View.VISIBLE);
            } else if (mGroupPion == groupPosition) {
                groupHolder.textView1.setVisibility(View.GONE);
                groupHolder.textView2.setText(mChildText);
                groupHolder.textView3.setVisibility(View.GONE);
            } else if (mGroupPion == groupPosition) {
                groupHolder.textView1.setVisibility(View.GONE);
                groupHolder.textView2.setVisibility(View.GONE);
                groupHolder.textView3.setText(mChildText);
            }
//            for (int i = groupPosition; i <groupList.size() ; i++) {
//                groupHolder.textView1.setText(mChildText);
//            }


            if (isExpanded) {
                groupHolder.imageView.setImageResource(R.drawable.expanded);
                // ture is Expanded or false is not isExpanded
            } else {
                groupHolder.imageView.setImageResource(R.drawable.collapse);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.item);
            textView.setTextSize(13);
            textView.setText(getChild(groupPosition, childPosition).toString());
            return convertView;
        }

        class GroupHolder {
            TextView textView;
            TextView textView1;
            TextView textView2;
            TextView textView3;
            ImageView imageView;

        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}
