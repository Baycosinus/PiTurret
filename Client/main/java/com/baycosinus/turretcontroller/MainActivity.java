package com.baycosinus.turretcontroller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    LinearLayout layout_container;
    RelativeLayout layout_joystick;
    JoyStick js;
    TextView textX,textY;
    Switch autoSwitch;
    public static boolean autoMod = false;
    public static String HOST = "";
    public static int PORT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        textX = findViewById(R.id.textX);
        textY = findViewById(R.id.textY);
        autoSwitch = findViewById(R.id.autoswitch);

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                autoMod = !autoMod;
                new Command().execute("automod: " + String.valueOf(autoMod));
            }
        });
        InitializeJoystick();

        Log.e("Host", HOST);
        Log.e("Port", String.valueOf(PORT));
        if(HOST.equals("") || PORT == 0)
        {
            Toast.makeText(getApplicationContext(),"Bağlantı ayarları yapılandırılmadı.",Toast.LENGTH_SHORT).show();
        }
        else
        {
          Connect();
        }
    }
    public void Connect()
    {
       new Command().execute("handshake");
       if (Command.response.equals("1"))
       {
           Log.e("Response",Command.response);
           Toast.makeText(this.getApplicationContext(),"Bağlantı kuruldu.", Toast.LENGTH_LONG).show();
       }
    }
    public void InitializeJoystick()
    {
        layout_joystick = findViewById(R.id.layout_joystick);
        layout_container = findViewById(R.id.containerLayout);
        js = new JoyStick(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(100, 100);
        js.setLayoutSize(300, 300);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(0);
        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(final View arg0, final MotionEvent arg1) {
                js.drawStick(arg1);

                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    new Command().execute(String.valueOf(js.getX()) + "," + String.valueOf(js.getY()));
                    Log.e("tag",String.valueOf(js.getX()) + "," + String.valueOf(js.getY()));
                    textX.setText("X: " + String.valueOf(js.getX()));
                    textY.setText("Y: " + String.valueOf(js.getY()));
                }
                else if(arg1.getAction() == MotionEvent.ACTION_UP) {}

                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
                return true;
            case R.id.refresh:
                Toast.makeText(getApplicationContext(),"Bağlantı yenileniyor...", Toast.LENGTH_SHORT).show();
                Connect();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
