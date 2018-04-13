package sdp01.sdp.com.sdp01.sp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sdp01.sdp.com.sdp01.R;
import sdp01.sdp.com.sdp01.customer.BidFragment;
import sdp01.sdp.com.sdp01.customer.MyBidRecyclerViewAdapter;
import sdp01.sdp.com.sdp01.customer.PaymentFragment;
import sdp01.sdp.com.sdp01.customer.ServiceFragment;
import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListener;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;
import sdp01.sdp.com.sdp01.models.Bid;
import sdp01.sdp.com.sdp01.models.Request;
import sdp01.sdp.com.sdp01.sp.dummy.DummyContent;
import sdp01.sdp.com.sdp01.sp.dummy.DummyContent.DummyItem;
import sdp01.sdp.com.sdp01.util.Networking;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class RequestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    MyRequestRecyclerViewAdapter ha;
    public static final List<Request> ITEMS = new ArrayList<Request>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RequestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RequestFragment newInstance(int columnCount) {
        RequestFragment fragment = new RequestFragment();
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

        status();
    }

    public void status() {
        Networking.getSPStatus(new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");

                    Fragment fragment = null;
                    if (status.equals("not service")){
                        // Stay in this screen
                        getRequests();
                    }else if (status.equals("requesting")){
                        // Go to BidStatusFragment to wait for bid status.
                        fragment = new BidStatusFragment();
                    }else if (status.equals("in service")){
                        // Go to SPServiceFragment.
                        fragment = new SPServiceFragment();
                    }else if (status.equals("payment")){
                        // Go to PaymentFragment.
                        fragment = new PaymentFragment();
                    }

                    if (fragment != null) {
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.mainFrame, fragment);
                        ft.commit();
                    }


                } catch (JSONException e) {
                    // Error handling.
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ErrorCode anError) {
                // Error handling.
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);

        if (mListener != null) {
            mListener.onListFragmentInteraction("Requests", null);
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            ha = new MyRequestRecyclerViewAdapter(ITEMS, mListener);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(ha);
        }
        return view;
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
        void onListFragmentInteraction(String title, Request item);
    }

    public void getRequests() {
        Networking.getRequests(new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray requests = response.getJSONArray("requests");
                    for (int i = 0; i < requests.length(); i++){
                        JSONObject request_js = requests.getJSONObject(i);

                        String request_id = request_js.getString("request_id");
                        String customer_id = request_js.getString("customer_id");
                        String service = request_js.getString("service");

                        JSONObject from_js = request_js.getJSONObject("location");
                        Location from = new Location("from");
                        from.setLatitude(Double.parseDouble(from_js.getString("latitude")));
                        from.setLongitude(Double.parseDouble(from_js.getString("longitude")));

                        ITEMS.add(new Request(request_id, customer_id, service, from));
                    }
                    ha.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Request empty = new Request(".", "", "No Requests", null);
                    ITEMS.add(empty);
                    ha.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(ErrorCode anError) {
                Request empty = new Request(".", "", "No Requests", null);
                ITEMS.add(empty);
                ha.notifyDataSetChanged();
            }
        });
    }

}
