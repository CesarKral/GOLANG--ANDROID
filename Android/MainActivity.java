package com.example.cesar.javac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button sendStr;
    Button getStr;
    Button sendJson;
    Button getJson;
    Button sendJsonArray;
    Button getJsonArray;
    Button nodemcu;
    TextView strToShow;
    RequestQueue queue;
    Socket socketa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendStr = (Button)findViewById(R.id.btnSendString);
        getStr = (Button)findViewById(R.id.btnGetString);
        sendJson = (Button)findViewById(R.id.btnSendJson);
        getJson = (Button)findViewById(R.id.btnGetJson);
        sendJsonArray = (Button)findViewById(R.id.btnSendJsonArray);
        getJsonArray = (Button)findViewById(R.id.btnGetJsonArray);
        nodemcu = (Button)findViewById(R.id.btnNodemcu);
        strToShow = (TextView)findViewById(R.id.stringToShow);
        queue = Volley.newRequestQueue(this);
        //GET STRING
        getStr.setOnClickListener(v -> {
            String url = "http://213.82.212.147:90";
            Runnable taskA = () -> {
                StringRequest sr = new StringRequest(Request.Method.GET, url, response -> strToShow.setText(response), error -> strToShow.setText("It didn't work!"));
                queue.add(sr);
            };
            new Thread(taskA).start();
        });
        //SEND STRING
        sendStr.setOnClickListener(v -> {
            String url = "http://213.82.212.147:90/getstring";
            Runnable taskB = () -> {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    //If the respond success
                }, error -> {
                    //IF the respond fails
                }){
                    @Override
                    public byte[] getBody() {
                        String xx = "Android Studio";
                        return xx.getBytes();
                    }
                };
                queue.add(stringRequest);
            };
            new Thread(taskB).start();

        });
        //GET JSON
        getJson.setOnClickListener(v -> {
            String url = "http://213.82.212.147:90/sendjson";
            Runnable taskC = () -> {
                JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
                    //
                    try {
                        String Name = response.getString("Name");
                        String Car = response.getString("Car");
                        String Country = response.getString("Country");
                        strToShow.setText(Name + " " + Car + " " + Country);
                    }catch (JSONException e){

                    }
                }, error -> {
                    //
                });
                queue.add(jor);
            };
            new Thread(taskC).start();
        });
        //SEND JSON
        sendJson.setOnClickListener(v -> {
            String url = "http://213.82.212.147:90/getjson";

            final Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("Name", "Nuria");
            jsonParams.put("Car", "Ferrari");
            jsonParams.put("Country", "Spain");

            Runnable taskD = () -> {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                    //If the respond success
                }, error -> {
                    //IF the respond fails
                }){
                    @Override
                    public byte[] getBody() {
                        return new JSONObject(jsonParams).toString().getBytes();
                    }
                };
                queue.add(stringRequest);
            };
            new Thread(taskD).start();

        });
        //GET JSON ARRAY
        getJsonArray.setOnClickListener(v -> {
            String url = "http://213.82.212.147:90/sendjsonarray";
            Runnable taskE = () -> {
                JsonArrayRequest jor = new JsonArrayRequest(Request.Method.POST, url, null, response -> {
                    //
                    try {
                        strToShow.setText("");
                        JSONArray jsons = response;
                        for(int i = 0; i < jsons.length(); i++){
                            JSONObject girl = jsons.getJSONObject(i);
                            String Name = girl.getString("Name");
                            String Car = girl.getString("Car");
                            String Country = girl.getString("Country");
                            strToShow.append(Name + " " + Car + " " + Country + "\n");
                        }
                    }catch (JSONException e){

                    }
                }, error -> {
                    //
                });
                queue.add(jor);
            };
            new Thread(taskE).start();
        });
        //SEND JSON ARRAY
        sendJsonArray.setOnClickListener(v -> {
            String url = "http://213.82.212.147:90/getjsonarray";

            List<Map<String, String>> jsonArray = new ArrayList<>();

            Map<String ,String> mapOne = new HashMap<>();
            mapOne.put("Name", "Laura");
            mapOne.put("Car", "Porsche");
            mapOne.put("Country", "Spain");
            jsonArray.add(mapOne);

            Map<String ,String> mapTwo = new HashMap<>();
            mapTwo.put("Name", "Natalia");
            mapTwo.put("Car", "Audi");
            mapTwo.put("Country", "Spain");
            jsonArray.add(mapTwo);


            Runnable taskF = () -> {
                StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, url, response -> {
                    //If the respond success
                }, error -> {
                    //IF the respond fails
                }){
                    @Override
                    public byte[] getBody() {
                        return new JSONArray(jsonArray).toString().getBytes();
                    }
                };
                queue.add(jsonArrayRequest);
            };
            new Thread(taskF).start();

        });
        //SENT STR TO NODEMCU
        nodemcu.setOnClickListener(v -> {
            Runnable TaskG = () -> {
                String xa = "11";
                try {
                    socketa = new Socket("192.168.1.40", 80);
                    OutputStream os = socketa.getOutputStream();
                    os.write(xa.getBytes("UTF-8"));
                    os.close();
                    socketa.close();
                }catch (IOException e){e.printStackTrace();}
            };
            new Thread(TaskG).start();
        });
    }
}
