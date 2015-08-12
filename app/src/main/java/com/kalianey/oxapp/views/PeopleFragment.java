package com.kalianey.oxapp.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.kalianey.oxapp.R;
import com.kalianey.oxapp.SessionManager;
import com.kalianey.oxapp.models.ModelUser;
import com.kalianey.oxapp.utils.QueryAPI;
import com.kalianey.oxapp.views.adapters.PeopleGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PeopleFragment extends Fragment {

    QueryAPI query = new QueryAPI();
    private GridView gridView;
    private SessionManager session;
    private PeopleGridViewAdapter adapter;
    private List<ModelUser> users = new ArrayList<>();

    public PeopleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);

        gridView = (GridView) view.findViewById(R.id.people_gridView);


        query.login("kalianey", "Fxvcoar123@Sal", new QueryAPI.ApiResponse<QueryAPI.ApiResult>() {
                    @Override
                    public void onCompletion(QueryAPI.ApiResult res) {

                        if (res.success) {
                            Log.v("Login Successful", res.success.toString());

                        }


                        query.allUsers(new QueryAPI.ApiResponse<List<ModelUser>>() {
                            @Override
                            public void onCompletion(List<ModelUser> result) {
                                Log.v("UserListCompletion", result.toString());
                                if (!result.isEmpty() && result != null) {
                                    users = result;
                                    adapter = new PeopleGridViewAdapter(getActivity(), R.layout.people_gridview_item, users);
                                    gridView.setAdapter(adapter);
                                    //adapter.notifyDataSetChanged();
                                    Log.v("Data Set Changed: ", users.toString());
                                }
                            }

                        });
                    }
                }
        );



                    return view;
                }
            }