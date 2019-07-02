package com.gaurav.multiplelistviewapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gaurav.multiplelistviewapp.BuildConfig;
import com.gaurav.multiplelistviewapp.R;
import com.gaurav.multiplelistviewapp.adapter.UserAdapter;
import com.gaurav.multiplelistviewapp.model.UserDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView usersRecyclerView;
    private ProgressBar progressBar;
    private int offset=0;
    private ArrayList<UserDTO> usersList;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        usersRecyclerView = (RecyclerView)findViewById(R.id.usersRecyclerView);
        usersRecyclerView.setHasFixedSize(true);
        usersRecyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        usersRecyclerView.setLayoutManager(layoutManager);
        usersList = new ArrayList<>();
        userAdapter = new UserAdapter(MainActivity.this, usersList);
        usersRecyclerView.setAdapter(userAdapter);

        getUsersListAPI(10);

        usersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLastItemDisplaying(recyclerView)) {
                    getUsersListAPI(10);
                }
            }
        });

    }

    private void getUsersListAPI(int limit){
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest allUsersRequest = new JsonObjectRequest(Request.Method.GET, BuildConfig.BASE_URL + Constants.OFFSET + "=" +
                offset + "&" + Constants.LIMIT + "=" + limit,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray usersArray = response.getJSONObject("data").getJSONArray("users");
                            if(usersArray.length()>0) {
                                for (int i = 0; i < usersArray.length(); i++) {
                                    ArrayList<String> userItemsList = new ArrayList<>();
                                    UserDTO userDTO = new UserDTO();
                                    JSONObject json;
                                    json = usersArray.getJSONObject(i);
                                    userDTO.setName(json.getString(Constants.NAME));
                                    userDTO.setImage(json.getString(Constants.IMAGE));
                                    JSONArray userItemsArray = json.getJSONArray(Constants.ITEMS);
                                    for (int j = 0; j < userItemsArray.length(); j++) {
                                        userItemsList.add(userItemsArray.get(j).toString());
                                    }
                                    userDTO.setItems(userItemsList);
                                    usersList.add(userDTO);
                                }
                                userAdapter.notifyDataSetChanged();
                                offset = offset + 10;
                            } else {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            usersRecyclerView.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        allUsersRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(allUsersRequest);
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == usersRecyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }
}

