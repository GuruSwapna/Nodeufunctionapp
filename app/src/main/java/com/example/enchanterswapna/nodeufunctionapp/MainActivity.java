package com.example.enchanterswapna.nodeufunctionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edname;
    TextView tcount,tcal;
    CalendarView calend;
    String curDate,curyear,curmonth;
    Button binsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        binsert=(Button)findViewById(R.id.btnin);
        edname = (EditText) findViewById(R.id.editname);
        tcount = (TextView) findViewById(R.id.textcount);
        tcal = (TextView) findViewById(R.id.textdate);
        calend = (CalendarView) findViewById(R.id.cald);
        calend.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                int d = dayOfMonth;
                int y = year;
                int m = month+1;
                curDate = String.valueOf(d);
                curyear = String.valueOf(y);
                curmonth = String.valueOf(m);
                tcal.setText(curDate + "/" + curmonth + "/" + curyear);
            }
        });

        edname.addTextChangedListener(mTextEditorWatcher);
        binsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sname=tcount.getText().toString();
                String sdate=tcal.getText().toString();
                String sdname=edname.getText().toString();
                insert_serv(sname,sdate,sdname);
            }
        });

    }



    private final TextWatcher mTextEditorWatcher=new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            tcount.setText(String.valueOf(s.length()));
        }
    };



//        TextWatcher mTextEditorWatcher = null;
//        edname.addTextChangedListener(mTextEditorWatcher);
//
//        mTextEditorWatcher = new TextWatcher() {
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //This sets a textview to the current length
//                tcount.setText(String.valueOf(s.length()));
//
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//        };




    private void insert_serv(final String ssname,final String ssdate,final String ssdname) {

        StringRequest stringreqs = new StringRequest(Request.Method.POST, Global_Url.SIGN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("exits");
                    if (abc)
                    {
                        JSONObject users = jObj.getJSONObject("user_det");
                        String ncount = users.getString("ncount");
                        String ndate = users.getString("ndate");
                        String nname = users.getString("nname");

                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
//                        Intent intent1=new Intent(Signup.this,Login.class);
//                        intent1.putExtra("stname", named);
//                        startActivity(intent1);
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(), "not valid", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> uandme = new HashMap<String, String>();
                uandme.put("ncount", ssname);
                uandme.put("ndate", ssdate);
                uandme.put("nname", ssdname);

                //uandme.put("password2", password1);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }

}
