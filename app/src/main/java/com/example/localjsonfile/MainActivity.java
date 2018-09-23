package com.example.localjsonfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> contactlist;
    Button btnjsonfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactlist = new ArrayList<>();
        btnjsonfile = (Button) findViewById(R.id.btnjson);
        btnjsonfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginjsonparsing();
            }
        });
    }

    private void beginjsonparsing() {
        try {
            JSONObject reader = new JSONObject(loadjsonfromassest());
            JSONArray jarray = reader.getJSONArray("rootnode");
            for (int i = 0; i < jarray.length(); i++) {
                try {
                    JSONObject obj = jarray.getJSONObject(i);
                    int id = obj.getInt("id");
                    String name = obj.getString("name");
                    String surname = obj.getString("surname");
                    int age = obj.getInt("age");
                    addingvaluedtohash(id, name, surname, age);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            ListView lv = (ListView) findViewById(R.id.listdata);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactlist, R.layout.list_item, new String[]{"id", "name",
                    "surname", "age"}, new int[]{R.id.idjson, R.id.namejson, R.id.surnamejson, R.id.agejson});
            lv.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addingvaluedtohash(int id, String name, String surname, int age) {
        HashMap<String, String> contact = new HashMap<>();

        contact.put("id", Integer.toString(id));

        contact.put("name", name);
        contact.put("surname", surname);
        contact.put("age", Integer.toString(age));
        contactlist.add(contact);


    }

    public String loadjsonfromassest() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
