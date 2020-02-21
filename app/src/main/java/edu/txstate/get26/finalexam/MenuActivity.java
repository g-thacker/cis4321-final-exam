package edu.txstate.get26.finalexam;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class MenuActivity extends ListActivity {

    List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu);
        getEvents();
    }

    void getEvents() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));

        RestApiClient.get(MenuActivity.this, "events.json", headers.toArray(new Header[headers.size()]), null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                eventList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        eventList.add(new Event(response.getJSONObject(i)));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                setListAdapter(new ArrayAdapter<>(MenuActivity.this, R.layout.activity_menu, R.id.txtEventListing, eventList));
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                eventList = new ArrayList<>();
                Iterator<String> keys = response.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        eventList.add(new Event(response.getJSONObject(key)));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                setListAdapter(new ArrayAdapter<>(MenuActivity.this, R.layout.activity_menu, R.id.txtEventListing, eventList));
            }
        });
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Event selectedEvent = eventList.get(position);
        int thisId = selectedEvent.getId();
        String thisName = selectedEvent.getName();
        int thisCapacity = selectedEvent.getCapacity();
        int thisNumberAttending = selectedEvent.getNumberAttending();
        String thisRoom = selectedEvent.getRoom();

        String url = "events/" + position + "/NumOfAttendees.json";
        StringEntity entity = null;
        if (thisNumberAttending < thisCapacity) {
            try {
                int updatedAttending = thisNumberAttending + 1;
                entity = new StringEntity("" + updatedAttending);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
            RestApiClient.put(MenuActivity.this, url, entity, "application/text", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(MenuActivity.this, "Error: " + responseString, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Toast.makeText(MenuActivity.this, "Welcome to the event!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(MenuActivity.this, "No seats are available!", Toast.LENGTH_LONG).show();
        }
    }
}
