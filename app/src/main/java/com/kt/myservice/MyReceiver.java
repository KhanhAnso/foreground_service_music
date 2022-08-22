package com.kt.myservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//Tạo một BroadCast Receiver
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //action nhận được khi thực hiện click pause, resume, clear
        int actionMusic = intent.getIntExtra("action_music",0);
        //truyền action ngược lại service
        Intent intentService = new Intent(context, MyService.class);
        intentService.putExtra("action_music_service",actionMusic);
        //start Service         -Nó chỉ được tạo 1 lần, nó sẽ vào onStartCommand()
        context.startService(intentService);
    }
}
