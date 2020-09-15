package com.project.daylight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

/**
 * Created by Chau Thai on 4/12/16.
 * Updated by inhwan Jeong on 4/12/19.
 */
public class ListAdapter extends ArrayAdapter<String> {
    private final LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper;

    public ListAdapter(Context context, List<String> objects) {
        super(context, R.layout.row_list, objects);
        mInflater = LayoutInflater.from(context);
        binderHelper = new ViewBinderHelper();

        // uncomment if you want to open only one row at a time
        // binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row_list, parent, false);

            holder = new ViewHolder();
            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.frontView = convertView.findViewById(R.id.front_layout);
            holder.deleteView = convertView.findViewById(R.id.delete_layout);
            holder.shareView = convertView.findViewById(R.id.share_layout);
            holder.updateView = convertView.findViewById(R.id.update_layout);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            holder.swipeTextView = (TextView) convertView.findViewById(R.id.swipe_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String item = getItem(position);
        if (item != null) {
            binderHelper.bind(holder.swipeLayout, item);

            // 실제 커스텀 리스트뷰에 들어갈 문자열 세팅 및 클릭 리스너 이벤트 정의
            holder.textView.setText(item);
            holder.swipeTextView.setText("이난이 돌잔치" + position);
            holder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove(item);
                }
            });
            holder.updateView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO make update callback
                }
            });
            holder.shareView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),MainActivity.class); // 최상단 액티비티의 컨텐트를 가져옴
                    Intent msg = new Intent(Intent.ACTION_SEND);

                    msg.addCategory(Intent.CATEGORY_DEFAULT);
                    msg.putExtra(Intent.EXTRA_SUBJECT, "주제");
                    msg.putExtra(Intent.EXTRA_TEXT, "종강");
                    msg.putExtra(Intent.EXTRA_TITLE, "안드로이드 빨리 제발");
                    msg.setType("text/plain");
                    v.getContext().startActivity(Intent.createChooser(msg, "공유"));
                }
            });
            holder.frontView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String displayText = "" + item + " clicked";
                    Toast.makeText(getContext(), displayText, Toast.LENGTH_SHORT).show();
                    Log.d("ListAdapter", displayText);
                }
            });
        }

        return convertView;
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder {
        SwipeRevealLayout swipeLayout;
        View frontView;
        View deleteView;
        View shareView;
        View updateView;
        TextView textView;
        TextView swipeTextView;
    }
}
