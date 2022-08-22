package com.kt.myservice;

import static com.kt.myservice.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

//foreground service
public class MyService extends Service {

    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLEAR = 3;

    //chạy mp3 thông qua MediaPlayer
    private MediaPlayer mediaPlayer;
    //
    private boolean isPlaying;  //mặc định false
    //
    private Song mSong;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MyService.class.getName(),"onCreate MyService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            Song song = (Song) bundle.get("object_song");
            if (song != null) {
                //gán mSong xử lý update view;
                mSong = song;
                //
                startMusic(song);
                //gửi dữ liệu service lên notification
                sendNotification(song);
            }
        }

        //nhận lại action từ Broadcast
        int actionMusic = intent.getIntExtra("action_music_service",0);
        //thực hiện xử lý music theo action mà ta có
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }
    //start một link mp3 chạy nhạc
    private void startMusic(Song song) {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResource());
        }
        mediaPlayer.start();
        //nó đang chạy nhạc
        isPlaying = true;
    }
    //xử lý hoạt động của music
    private void handleActionMusic(int action){
        switch (action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                resumeMusic();
                break;
            case ACTION_CLEAR:
                stopSelf(); //stop luôn service
                break;
        }
    }
    private void pauseMusic(){
        if (mediaPlayer != null && isPlaying){
            mediaPlayer.pause();
            isPlaying = false;
            sendNotification(mSong);
        }
    }
    //tiếp tục phát nhạc
    private void resumeMusic(){
        if (mediaPlayer != null && !isPlaying){
            mediaPlayer.start();
            isPlaying = true;
            sendNotification(mSong);
        }
    }

    //gửi dữ liệu lên notification
    private void sendNotification(Song song) {
        //click vào notification
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //custom view notification
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), song.getImage());
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);
        remoteViews.setTextViewText(R.id.tv_single_song,song.getTitle());
        remoteViews.setTextViewText(R.id.tv_single_song,song.getSingle());
        remoteViews.setImageViewBitmap(R.id.img_song,bitmap);
        remoteViews.setImageViewResource(R.id.img_play_or_pause,R.drawable.ic_baseline_pause_24);

        //Nếu nó đang chạy
        if (isPlaying){
            //bắt sự kiện với remoteViews
            remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause,getPendingIntent(this, ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.img_play_or_pause,R.drawable.ic_baseline_pause_24);
        }
        //Nếu nó đang dừng ta muốn chạy
        else {
            remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause,getPendingIntent(this, ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.img_play_or_pause,R.drawable.ic_baseline_play_circle_outline_24);
        }
        remoteViews.setOnClickPendingIntent(R.id.img_clear,getPendingIntent(this,ACTION_CLEAR));

        //đưa dữ liệu lên notification
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_access_time_24)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)     //tắt tiếng cho notification (dưới android 8.0)
                .build();
        //đưa nó ra foreground service
        startForeground(1,notification);
    }

    //pending intent với từng action
    private PendingIntent getPendingIntent(Context context, int action){
        //sử dụng broadcast Receiver
        Intent intent = new Intent(this,MyReceiver.class);
        intent.putExtra("action_music",action);
        //truyền dữ liệu qua broadcast receiver
        //Nên ta sẽ xử lý trong broadcast receiver
        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(MyService.class.getName(),"onDestroy MyService");
        //tắt nhạc
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
