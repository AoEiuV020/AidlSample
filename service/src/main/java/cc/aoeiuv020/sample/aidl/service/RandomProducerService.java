package cc.aoeiuv020.sample.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RandomProducerService extends Service {
    private static final String TAG = "RandomProducerService";
    IRandomProducerService.Stub mBinder = new IRandomProducerService.Stub() {
        @Override
        public int nextRandom(int min, int max) throws RemoteException {
            Log.d(TAG, "nextRandom: "+min+" ~ "+max);
            return (int) (Math.random() * (max - min + 1)) + 1;
        }
    };

    public RandomProducerService() {
        Log.d(TAG, "RandomProducerService: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + intent.toString());
        return mBinder;
    }
}
