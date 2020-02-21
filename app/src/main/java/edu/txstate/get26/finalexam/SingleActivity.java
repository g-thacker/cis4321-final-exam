package edu.txstate.get26.finalexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class SingleActivity extends AppCompatActivity {

    ArrayList<Event> myEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        // I know this isn't a great way to get a single event, but I wasn't sure how else to do it
        // and didn't want to waste time on the final figuring out

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));
        RestApiClient.get(SingleActivity.this, "events/0.json", headers.toArray(new Header[headers.size()]), null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                myEvent = new ArrayList<>();
                try {
                    myEvent.add(new Event(response.getJSONObject(0)));
                } catch (JSONException ex) {
                   ex.printStackTrace();
                }
            }
//            public void onSuccess(int statusCode, Header[] headers, JSONObject respnse) {
//                myEvent = new ArrayList<>();
//                Iterator<String> keys = respnse.keys();
//
//            }
        });

        TextView txtId = findViewById(R.id.txtId);
        TextView txtName = findViewById(R.id.txtName);
        TextView txtCapacity = findViewById(R.id.txtCapacity);
        TextView txtNumberAttending = findViewById(R.id.txtAttending);
        TextView txtRoom = findViewById(R.id.txtRoom);

        if (myEvent != null) {

            Event thisEvent = myEvent.get(0);
            txtId.setText(thisEvent.getId());
            txtName.setText(thisEvent.getName());
            txtCapacity.setText(thisEvent.getCapacity());
            txtNumberAttending.setText(thisEvent.getNumberAttending());
            txtRoom.setText(thisEvent.getRoom());
        }


    }
}
