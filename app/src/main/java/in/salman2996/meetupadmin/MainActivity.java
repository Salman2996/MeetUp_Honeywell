package in.salman2996.meetupadmin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String ID_KEY = "admin_ID";
    private String EMAIL;

    Button createButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref1 = getSharedPreferences("AdminPREF", Context.MODE_PRIVATE);
        if(!pref1.getBoolean("login_activity_executed", false)){
            Log.v("login","not executed");
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
        EMAIL = pref1.getString(ID_KEY, "");
        //Toast.makeText(MainActivity.this, EMAIL, Toast.LENGTH_SHORT).show();
        //TODO : push email to DB alongwith the event details

        setContentView(R.layout.activity_main);

        createButton = (Button)findViewById(R.id.create_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }
}
