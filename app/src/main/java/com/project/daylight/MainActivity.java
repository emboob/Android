package com.project.daylight;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(MainActivity.this, ListDemoActivity.class);
        //startActivity(intent);

//        floatingActionButton = findViewById(R.id.floatingActionButton);


        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetScheduleActivity.class);
                startActivity(intent);
            }
        });
        ViewPager pager = findViewById(R.id.viewpager);
        // 미리 캐싱 - 준비 해놓을 프레그먼트 개수
        pager.setOffscreenPageLimit(3);

        // 프래그먼트 매니저 생성과 동시에 어댑터에 할당 즉, 매니저=어댑터
        PagerAdapter pa = new PagerAdapter(getSupportFragmentManager());
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        CenterMainFragment cmf = new CenterMainFragment();
        RightMainFragment rmf = new RightMainFragment();
        LeftMainFragment lmf = new LeftMainFragment();
        pa.addItem(lmf, "settings");
        pa.addItem(cmf, "logo");
        pa.addItem(rmf, "calender");

        pager.setAdapter(pa);
        pager.setCurrentItem(1);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("");
        tabs.getTabAt(0).setIcon(R.drawable.ic_settings);
        tabs.getTabAt(1).setText("Day-Light");
        tabs.getTabAt(2).setText("");
        tabs.getTabAt(2).setIcon(R.drawable.ic_calender);


        createNotificationChannel();
        setMainNotification();

        //크롤링 테스트
        Async test = new Async();
        test.execute();
    }

    class Async extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String kwd = "선물";
            String age = "20대";
            String sex = "남성";
            StringBuilder pageurl = new StringBuilder("https://search.shopping.naver.com/search/all.nhn?query=");
            pageurl.append(age + "+");
            pageurl.append(sex + "+");
            pageurl.append(kwd);
            pageurl.append("&pagingIndex=1&pagingSize=40&viewType=list&sort=review&frm=NVSHATC&query=");
            pageurl.append(age + "%20");
            pageurl.append(sex + "%20");
            pageurl.append(kwd);
            Document doc = new Document("");
            try {
                doc = Jsoup.connect(pageurl.toString()).get();
                selectItem(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private ArrayList<GiftSet> selectItem(Document doc){
            ArrayList<GiftSet> itemList = new ArrayList<>();
            GiftSet gift;
            Elements e = doc.select("ul.goods_list").select("li._itemSection");
            List<String> title = e.select("div.tit").select("a").eachText();
            List<String> price =  e.select("span.price").select("span.num").eachText();
            List<String> imgUrl = e.select("div.img_area").select("img._productLazyImg").eachAttr("data-original");
            for(int i=0;i<7;i++){
                gift = new GiftSet();
                gift.title = title.get(i);
                gift.price = price.get(i);
                gift.imageUrl = imgUrl.get(i);
                System.out.println(gift.title);
                System.out.println(gift.price);
                System.out.println(gift.imageUrl);
                itemList.add(gift);
            }
            return itemList;
        }
    }

    private void setMainNotification(){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "DayLight")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("기념일")
                .setContentText("D+12345")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_NO_CLEAR;    //알림이 사라지지 않게 하기
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("DayLight", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // 프레그먼트 커스텀 어댑터
    class PagerAdapter extends FragmentStatePagerAdapter{
        //프래그먼트가 들어갈 어레이 리스트
        ArrayList<Fragment> page = new ArrayList<Fragment>();
        ArrayList<String> title = new ArrayList<>();
        public PagerAdapter(FragmentManager fm){
            super(fm);
        }
        public void addItem(Fragment _item, String _title){
            page.add(_item);
            title.add(_title);
        }
        @Override
        public Fragment getItem(int position) {
            return page.get(position);
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position){return title.get(position);}
        @Override
        public int getCount() {
            return page.size();
        }
    }


}
