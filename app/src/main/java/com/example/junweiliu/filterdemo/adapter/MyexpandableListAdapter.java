package com.example.junweiliu.filterdemo.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.junweiliu.filterdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/17.
 */

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
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = inflater.inflate(R.layout.group, null);
            groupHolder.textView = (TextView) convertView
                    .findViewById(R.id.group);
            groupHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image);
            groupHolder.textView.setTextSize(15);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView.setText(getGroup(groupPosition).toString());
        if (isExpanded)// ture is Expanded or false is not isExpanded
            groupHolder.imageView.setImageResource(R.drawable.expanded);
        else
            groupHolder.imageView.setImageResource(R.drawable.collapse);
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
        ImageView imageView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
