package cc.aoeiuv020.sample.aidl.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cc.aoeiuv020.sample.aidl.service.IRandomProducerService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private IRandomProducerService service = null;
    private boolean bound = false;
    private TextView text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                if (service != null) {
                    try {
                        text.setText(Integer.toString(service.nextRandom(1, 3)));
                    } catch (RemoteException e) {
                        Log.e(TAG, "onClick: ", e);
                    }
                } else {
                    text.setText("没有绑定Service");
                    bindRandomProducerService();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        bindRandomProducerService();
    }

    private void bindRandomProducerService(){
        Log.d(TAG, "bindRandomProducerService: ");
        bindService(new Intent()
                        .setPackage("cc.aoeiuv020.sample.aidl.service")
                        .setAction("cc.aoeiuv020.sample.aidl.service.RandomProducerService"),
                connection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        unbindService(connection);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            MainActivity.this.service = IRandomProducerService.Stub.asInterface(service);
            MainActivity.this.bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            MainActivity.this.service = null;
            MainActivity.this.bound = false;
        }
    };
}
