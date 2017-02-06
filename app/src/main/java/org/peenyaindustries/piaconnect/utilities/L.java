package org.peenyaindustries.piaconnect.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {
    public static void Log(String message){
        Log.d("PIAConnect", message);
    }

    public static void ToastShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
