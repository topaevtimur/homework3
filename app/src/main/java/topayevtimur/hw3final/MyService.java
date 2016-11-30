package topayevtimur.hw3final;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by AdminPC on 29.11.2016.
 */

public class MyService extends Service implements Runnable {

    private final String URI = "http://pix.co.ua/full_img/new_year/new_year_2016.jpg";
    private File file;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        file = new File(getFilesDir(), MainActivity.IM_NAME);
        new Thread(this).start();
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {

        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = new BufferedInputStream((new URL(URI)).openStream());
            out = new FileOutputStream(file);
            int i;
            byte[] buffer = new byte[1024];
            while ((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
            sendBroadcast(new Intent(MainActivity.BROADCAST_ACTION));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
