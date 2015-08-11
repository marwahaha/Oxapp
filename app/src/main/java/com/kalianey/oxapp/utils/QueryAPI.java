package com.kalianey.oxapp.utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalianey.oxapp.AppController;
import com.kalianey.oxapp.models.ModelConversation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by kalianey on 11/08/2015.
 */
//http://stackoverflow.com/questions/31602042/android-java-how-to-delay-return-in-a-method


public class QueryAPI {

    private String hostname;

    public class ApiResult
    {
        public Boolean success;
        public String message;
        public JSONArray data;
    }

    public interface ApiResponse<T>
    {
        public void onCompletion(T result);
    }


    public void RequestApi( String url, final ApiResponse<ApiResult> completion  )
    {
        hostname = "http://bonnieandclit.com/";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, hostname+url, (JSONObject) null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response)
                {

                    //Log.v("Data: ", response.toString());
                    try {
                        Boolean success = response.getBoolean("success");
                        //Log.v("Success: ", success.toString());
                        JSONArray data = response.getJSONArray("data");
                        ApiResult res = new ApiResult();
                        res.success = success;
                        res.data = data;
                        completion.onCompletion(res);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ApiResult res = new ApiResult();
                    res.success = false;
                    completion.onCompletion(res);
                }
            }
        );

        AppController.getInstance().addToRequestQueue(jsonRequest);
    }



    public void getConversations(final ApiResponse<List<ModelConversation>> completion)
    {
        String url = "owapi/messenger/conversationList";
        final List<ModelConversation> conversations = new ArrayList<ModelConversation>();
        this.RequestApi(url, new ApiResponse<ApiResult>() {
            @Override
            public void onCompletion(ApiResult res) {

                if (res.success) {

                    //Log.v("JSONArray: ", res.data.toString());

                    for (int i = 0; i < res.data.length(); i++) {

                        try {
                            JSONObject jsonObject = res.data.getJSONObject(i);

                            ModelConversation conversation = new ModelConversation();
                            conversation.setAvatarUrl(jsonObject.getString("avatarUrl"));
                            conversation.setId(jsonObject.getString("conversationId"));
                            conversation.setName(jsonObject.getString("displayName"));
                            conversation.setPreviewText("previewText");

                            conversations.add(conversation);

                            //TODO: implements with GSON for automatic object creation
                            //conversation = new Gson().fromJson(jsonObject.toString(), ModelConversation.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
                completion.onCompletion(conversations);
            }
        });

    }



}


