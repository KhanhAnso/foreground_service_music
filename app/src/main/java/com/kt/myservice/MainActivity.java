package com.kt.myservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStartService, btnStopService;
    private RelativeLayout layoutBottom;
    private ImageView imgSong, imgPlayOrPause, imgClear;
    private TextView txtTitleSong, txtSingleSong;

    private Song mSong;
    private boolean isPlaying;

    //Khai báo đối tượng broadcast receiver
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Nơi đây nhận cái intent dữ liệu từ MyReceiver.java
            Bundle bundle = intent.getExtras();
            if (bundle == null){
                return;
            }
            mSong = (Song) bundle.get("object_song");
            isPlaying = bundle.getBoolean("status_player");
            int actionMusic = bundle.getInt("action_music");
            //xử lý layout của music trên mainActivity
            handleLayoutMusic(actionMusic);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lắng nghe broadcast receiver và intent filter chỉ định bên service
        //Đăng ký broadcast receiver lắng nghe      //registerReceiver thì cũng phải có unRegisterReceiver
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("send_data_to_activity"));

        btnStartService = findViewById(R.id.btn_start_service);
        btnStopService = findViewById(R.id.btn_stop_service);
        layoutBottom = findViewById(R.id.layout_bottom);
        imgSong = findViewById(R.id.img_song);
        imgClear = findViewById(R.id.img_clear);
        imgPlayOrPause = findViewById(R.id.img_play_or_pause);
        txtSingleSong = findViewById(R.id.tv_single_song);
        txtTitleSong = findViewById(R.id.tv_title_song);



        btnStartService.setOnClickListener(view ->{
            clickStartService();
        });
        btnStopService.setOnClickListener(view ->{
            clickStopService();
        });
    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    private void clickStartService() {
        Song song = new Song("Big city boy","khánh",R.drawable.image,R.raw.bigcityboy);

        Intent intent = new Intent(this, MyService.class);
        //gửi đối tượng song cho service
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song",song);
        intent.putExtras(bundle);
        //start service
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //un register Receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    //xử lý layout của music
    private void handleLayoutMusic(int action){
        switch (action){
            case MyService.ACTION_START:
                layoutBottom.setVisibility(View.VISIBLE);
                //hiển thị bài hát lên layout
                showInfoSong();
                //đổi icon pause or play
                setStatusButtonPlayOrPause();
                break;
            case MyService.ACTION_PAUSE:
                setStatusButtonPlayOrPause();
                break;
            case MyService.ACTION_RESUME:
                setStatusButtonPlayOrPause();
                break;
            case MyService.ACTION_CLEAR:
                layoutBottom.setVisibility(View.GONE);
                break;
        }
    }

    //hiển thị thông tin lên layout
    private void showInfoSong(){
        if (mSong == null){
            return;
        }
        imgSong.setImageResource(mSong.getImage());
        txtTitleSong.setText(mSong.getTitle());
        txtSingleSong.setText(mSong.getSingle());

        imgPlayOrPause.setOnClickListener(view -> {
            //gửi action pause đến service
            if (isPlaying){
                sendActionToService(MyService.ACTION_PAUSE);
            }else {
                sendActionToService(MyService.ACTION_RESUME);
            }
        });
        imgClear.setOnClickListener(view ->{
            sendActionToService(MyService.ACTION_CLEAR);
        });

    }

    //set lại nút cho layout
    private void setStatusButtonPlayOrPause(){
        if (isPlaying){
            imgPlayOrPause.setImageResource(R.drawable.ic_baseline_pause_24);
        }else {
            imgPlayOrPause.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);

        }
    }
    //gửi dữ liệu lên service
    private void sendActionToService(int action){
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("action_music_service",action);
        startService(intent);
    }
}