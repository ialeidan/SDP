package sdp01.sdp.com.sdp01.sp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import sdp01.sdp.com.sdp01.MyApplication;
import sdp01.sdp.com.sdp01.R;
import sdp01.sdp.com.sdp01.customer.BidFragment;
import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListener;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;
import sdp01.sdp.com.sdp01.models.Request;
import sdp01.sdp.com.sdp01.sp.RequestFragment.OnListFragmentInteractionListener;
import sdp01.sdp.com.sdp01.sp.dummy.DummyContent.DummyItem;
import sdp01.sdp.com.sdp01.util.AuthInfo;
import sdp01.sdp.com.sdp01.util.Networking;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRequestRecyclerViewAdapter extends RecyclerView.Adapter<MyRequestRecyclerViewAdapter.ViewHolder> {

    private final List<Request> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final RequestFragment requestFragment;


    private ViewGroup v = null;
    private Context context = null;

    public MyRequestRecyclerViewAdapter(List<Request> items, OnListFragmentInteractionListener listener, RequestFragment requestFragment) {
        mValues = items;
        mListener = listener;
        this.requestFragment = requestFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_request, parent, false);
        this.v = parent;
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getRequest_id());
        holder.mContentView.setText(mValues.get(position).getService());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction("Requests", holder.mItem);
                    Log.e("SENDBID_RESPONSE", requestFragment.mLastKnownLocation.toString());
                    makeBid(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Request mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    // Alert with TextInput to make a bid:
    public void makeBid(final Request request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter service price");
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.alert_textinput, (ViewGroup) v, false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                m_Text = input.getText().toString();
                Log.e("nnnnnnn", input.getText().toString());
                sendPrice(request.getRequest_id(), input.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void sendPrice(final String request_id, String price){

        Log.e("LOCATION_BID", requestFragment.mLastKnownLocation.toString());
        Networking.sendBid(request_id, price, requestFragment.mLastKnownLocation, new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("request");
                    if (status.equals("error")){
                        //TODO
                        return;
                    }

                    AuthInfo.setRequestId(request_id);

                    Log.e("SENDBID_RESPONSE", response.toString());

                    Fragment fragment = new BidStatusFragment();
                    FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainFrame, fragment);
                    ft.commit();

                } catch (JSONException e) {
                    // TODO
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ErrorCode anError) {
                Log.e("BID_ERROR", anError.info);
                // TODO
            }
        });


    }

}
