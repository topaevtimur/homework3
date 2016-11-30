package topayevtimur.hw3final;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by AdminPC on 29.11.2016.
 */

public class MainActivity extends AppCompatActivity {

    TextView errorText;
    ImageView image;
    Button delete;

    BroadcastReceiver imReceiver;
    BroadcastReceiver brReceiver;

    public static final String BROADCAST_ACTION = "ACTION";
    public static final String IM_NAME = "pic.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorText = (TextView) findViewById(R.id.text_error);
        image = (ImageView) findViewById(R.id.image);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(onButton);

        display();

        brReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                display();
            }
        };
        imReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                context.startService(new Intent(context, MyService.class));

            }
        };
        registerReceiver(imReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        registerReceiver(brReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    public void display() {
        File file = new File(getFilesDir(), IM_NAME);

        if (file.exists()) {
            errorText.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        } else{
            errorText.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
        }
    }
    View.OnClickListener onButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            File file = new File(getFilesDir(), IM_NAME);

            if(file.exists()) {
                file.delete();
                display();
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imReceiver);
        unregisterReceiver(brReceiver);
    }
}
