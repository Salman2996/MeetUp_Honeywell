package in.salman2996.meetupadmin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateEventActivity extends AppCompatActivity {

    final public static String BASE_URL = "http://18.221.8.243/meetup/enter_data.php?";

    EditText eventName, eventVenue, eventDate, eventDetails;
    Button button;

    OkHttpClient client;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventName = (EditText)findViewById(R.id.event_name);
        eventVenue = (EditText)findViewById(R.id.event_venue);
        eventDate = (EditText)findViewById(R.id.event_date);
        eventDetails = (EditText)findViewById(R.id.event_details);
        button = (Button)findViewById(R.id.button);

        client = new OkHttpClient();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eventName.getText().toString();
                String date = eventDate.getText().toString();
                String venue = eventVenue.getText().toString();
                String details = eventDetails.getText().toString();

                if(!isNetworkAvailable()){
                    toast("No internet connection!");
                    return;
                }

                request = new Request.Builder().url(BASE_URL + "name=" + name + "&date=" + date
                        + "&venue=" + venue + "&details=" + details).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        toast("Error occured! Couldn't create event!");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.i("Upload response", responseBody);

                        if(responseBody.equalsIgnoreCase("OK!")){
                            toast("Uploaded successfully");
                        } else {
                            toast("Error uploading");
                        }
                    }
                });
            }
        });
    }

    public void toast(final String message){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
