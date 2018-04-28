package sdp01.sdp.com.sdp01;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sdp01.sdp.com.sdp01.data_source.DataSource;
import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListener;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;
import sdp01.sdp.com.sdp01.dummy.DummyContent;
import sdp01.sdp.com.sdp01.dummy.DummyContent.DummyItem;
import sdp01.sdp.com.sdp01.models.History;
import sdp01.sdp.com.sdp01.sp.MyRequestRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    SwipeRefreshLayout mSwipeRefreshLayout;

    HistoryRecyclerViewAdapter ha;
    public static final List<History> ITEMS = new ArrayList<History>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HistoryFragment newInstance(int columnCount) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);

        if (mListener != null) {
            mListener.onListFragmentInteraction("History", null);
        }
        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            ha = new HistoryRecyclerViewAdapter(ITEMS, mListener);
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(ha);
//        }



        return view;
    }

    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        // Fetching data from server
        getHistory();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        ha = new HistoryRecyclerViewAdapter(ITEMS, mListener);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(ha);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

//        getRequests();
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                getHistory();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String title, History item);
    }


    public void getHistory(){
        Log.e("History", "HI");
        mSwipeRefreshLayout.setRefreshing(true);
        DataSource.getHistory(new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                ITEMS.clear();
                try {
//                    Log.e("History", response.getJSONArray("history").toString());

                    JSONArray histories = response.getJSONArray("history");
                    for (int i = 0; i < histories.length(); i++){
                        JSONObject history_js = histories.getJSONObject(i);

                        JSONObject from_js = history_js.getJSONObject("location").getJSONObject("from");
                        Location from = new Location("from");
                        from.setLatitude(Double.parseDouble(from_js.getString("latitude")));
                        from.setLongitude(Double.parseDouble(from_js.getString("longitude")));

                        JSONObject to_js = history_js.getJSONObject("location").getJSONObject("to");
                        Location to = new Location("to");
                        to.setLatitude(Double.parseDouble(to_js.getString("latitude")));
                        to.setLongitude(Double.parseDouble(to_js.getString("longitude")));

                        ITEMS.add(new History(history_js.getString("history_id"),
                                history_js.getString("customer_id"),
                                history_js.getString("sp_id"),
                                history_js.getString("status"),
                                history_js.getString("rating"),
                                history_js.getString("timestamp"),
                                from,
                                to));
                    }
                    ha.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    History empty = new History("-1", "", "", "No Data", "", "", null, null);
                    ITEMS.add(empty);
                    ha.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onError(ErrorCode anError) {
//                final int errorCode = anError.code;
//                Log.e("History", anError.toString());
                ITEMS.clear();
                History empty = new History("-1", "", "", "No Data", "", "", null, null);
                ITEMS.add(empty);
                ha.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
