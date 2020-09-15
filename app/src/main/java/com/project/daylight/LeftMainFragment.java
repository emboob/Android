package com.project.daylight;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * created by inhwan Jeong on 4/12/19.
 */

public class LeftMainFragment extends Fragment {
    TextView version_tv;
    TextView notification_tv;
    TextView donation_tv;
    TextView theme_tv;
    TextView bug_tv;
    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.left_main, container,false);
        //activity_main.xml2이 인플레이트 되고 자바 소스와 연결됨

        version_tv = rootView.findViewById(R.id.version_tv);
        notification_tv = rootView.findViewById(R.id.notification_tv);
        donation_tv = rootView.findViewById(R.id.donation_tv);
        theme_tv = rootView.findViewById(R.id.theme_tv);
        bug_tv = rootView.findViewById(R.id.bug_tv);

        version_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("Version", "Now version: -1.0.0");
            }
        });
        notification_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(rootView.getContext(), "설정해야함", Toast.LENGTH_SHORT).show();
            }
        });
        donation_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("Thank you for a cup of coffee.", "우리은행 1002-352-639226");
            }
        });
        theme_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme();
                //Toast.makeText(rootView.getContext(), "설정해야함", Toast.LENGTH_SHORT).show();
            }
        });
        bug_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bugReport();
            }
        });

        return rootView;
    }

    protected void showPopup(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(title).setMessage(msg);

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    protected void setTheme(){
        View dialogView = getLayoutInflater().inflate(R.layout.theme_setting, null);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.r_group);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        builder.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int select = 0;
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radio_emerald:
                        select = 0;
                        break;
                    case R.id.radio_green:
                        select = 1;
                        break;
                    case R.id.radio_yellow:
                        select = 2;
                        break;
                    case R.id.radio_pink:
                        select = 3;
                        break;
                    case R.id.radio_pupple:
                        select = 4;
                        break;
                }
                UserInfo.getUser().theme = select;
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void bugReport(){
        String msg = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Bug report");
        final EditText et = new EditText(getContext());
        et.setPadding(10, 15, 15, 10);
        et.setHint(" Write Bug message here!");
        builder.setView(et);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String msg = et.getText().toString();
                Toast.makeText(getContext(), "[Error]We don't have this kind of bug.\nmsg: " + msg, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}
