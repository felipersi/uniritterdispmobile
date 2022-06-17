package com.example.uniritter_disp_mobile.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.uniritter_disp_mobile.services.GPSService;
import com.example.uniritter_disp_mobile.views.LandActivity;

public class GPSBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "GPSBroadcastReceiver";
    private GPSService service;
    private NotificationChannel canalNotificacao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GPSBroadcastReceiver() {
        canalNotificacao = new NotificationChannel(
                "UniR",
                "Channel UniRitter",
                NotificationManager.IMPORTANCE_LOW
        );
        canalNotificacao.setDescription("Este é o canal de notificação do app modelo de aula");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "onReceive: "+intent.getAction());
        Intent intentPosBoot = new Intent();
        intentPosBoot.setAction("br.edu.uniritter.GPS_START");
        Intent intent2 = new Intent(context, LandActivity.class);
        Toast.makeText(context, "Intent recebida\n"+intent.getAction(), Toast.LENGTH_LONG).show();

        // dispara a notificação para solicitar o início da localização
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            disparaNotificacaoOnBoot(context);

        }
        // dispara o serviço de localização do App
        if (intent.getAction().equals("br.edu.uniritter.GPS_START")) {

            Intent intent1 = new Intent(context,GPSService.class);
            intent1.putExtra("boot", false);
            context.startForegroundService(intent);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void disparaNotificacaoOnBoot(Context context) {
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(canalNotificacao);
        // intenti que será disparada quando a notificação for clicada
        Intent intent = new Intent(context,GPSService.class);

        // intent pendente para disparo de serviço
        PendingIntent pendingIntent =
                PendingIntent.getForegroundService(context, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                new Notification.Builder(context,"UniR")
                        .setContentTitle("Projeto de Mobile")
                        .setContentText("Clique aqui para iniciar a app")
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                        .setAutoCancel(true)
                        .build();
        manager.notify(666, notification);
        Log.d(TAG, "onReceive: notification enviada");
    }
}

