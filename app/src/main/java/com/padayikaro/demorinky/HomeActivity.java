package com.padayikaro.demorinky;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public RecyclerView mRecyclerView;
    private HomeActivityRecyclerViewAdapter mHomeActivityRecyclerViewAdapter;
    private List<ResultModel> list;
    public EditText mSearch;
    ArrayList<String> AllaryList;
    ResultModel resultModel;
    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, String> map1 = new HashMap<String, String>();
    HashMap<String, String> map2 = new HashMap<String, String>();
    HashMap<String, String> map3 = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mSearch = (EditText) findViewById(R.id.search_et);
        Log.d("HA", "hit api 1");
        list = new ArrayList<>();
        AllaryList = new ArrayList<>();

        hitAPI("https://api.themoviedb.org/4/list/1?api_key=31a0f012b46442ffae28c330375c3b53");
        Log.d("HA", "hit api");

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!list.isEmpty()) {
                    list.clear();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();
                if (!list.isEmpty()) {
                    list.clear();
                }
                for (int i3 = 0; i3 < AllaryList.size(); i3++) {
                    resultModel = new ResultModel();
                    final String text = AllaryList.get(i3).toLowerCase();
                    if (text.contains(charSequence)) {

                        resultModel.setTitle(AllaryList.get(i3));
                        resultModel.setReleaseDate(map.get(AllaryList.get(i3)));
                        resultModel.setOverview(map1.get(AllaryList.get(i3)));
                        resultModel.setPopularity(map2.get(AllaryList.get(i3)));
                        resultModel.setBackdropPath(map3.get(AllaryList.get(i3)));
                        list.add(resultModel);
                    }
                    mHomeActivityRecyclerViewAdapter = new HomeActivityRecyclerViewAdapter(HomeActivity.this, list);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    mRecyclerView.setAdapter(mHomeActivityRecyclerViewAdapter);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    mHomeActivityRecyclerViewAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {

                }
            }
        });

    }
    private void hitAPI(final String url) {

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("url", url);
                Log.e("urlresresponse", response);
                try {
                    //parse data and put all to list
                    JSONObject main_obj = new JSONObject(response);
                    JSONArray output_array = main_obj.getJSONArray("results");

                    for (int i = 0; i < output_array.length(); i++) {
                        JSONObject obj = output_array.getJSONObject(i);
                        resultModel = new ResultModel();

                        resultModel.setTitle(obj.getString("original_title"));
                        resultModel.setReleaseDate(obj.getString("release_date"));
                        resultModel.setOverview(obj.getString("overview"));
                        resultModel.setPopularity(obj.getString("popularity"));
                        resultModel.setBackdropPath(obj.getString("backdrop_path"));
                        AllaryList.add(obj.getString("original_title"));
                        list.add(resultModel);
                        map.put(obj.getString("original_title"), obj.getString("release_date"));
                        map1.put(obj.getString("original_title"), obj.getString("overview"));
                        map2.put(obj.getString("original_title"), obj.getString("popularity"));
                        map3.put(obj.getString("original_title"), obj.getString("backdrop_path"));


                    }
                    Log.d("HA", "" + list.size());
                    mRecyclerView.setHasFixedSize(true);
                    mHomeActivityRecyclerViewAdapter = new HomeActivityRecyclerViewAdapter(HomeActivity.this, list);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                    mRecyclerView.setAdapter(mHomeActivityRecyclerViewAdapter);


                    Log.d("MAF", "" + list.size());

                } catch (JSONException e) {
                    Log.e("error", e.getMessage());

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                //  Utils.stopProgress(getActivity());
            }
        });

        Volley.newRequestQueue(this).add(strReq);


    }

   /* private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<ResultModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ResultModel s : list) {
            //if the existing elements contains the search input
            if (s.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mHomeActivityRecyclerViewAdapter.filterList(filterdNames);
    }
*/
}
