package sdp01.sdp.com.sdp01;

import android.location.Address;
import android.location.Geocoder;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import sdp01.sdp.com.sdp01.HistoryFragment.OnListFragmentInteractionListener;
import sdp01.sdp.com.sdp01.dummy.DummyContent.DummyItem;
import sdp01.sdp.com.sdp01.models.History;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<History> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HistoryRecyclerViewAdapter(List<History> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText("History " + (position + 1));
        holder.mContentView.setText(mValues.get(position).getStatus());
//        holder.mLocationView1.setText("jlshgjlhg");
//        holder.mLocationView2.setText("jlshgjlhg");
        Log.e("HHH", mValues.get(position).getRating());
        if (!mValues.get(position).getRating().isEmpty()) {
            holder.mRatingBar.setRating(Float.parseFloat(mValues.get(position).getRating()));


            try {

                Geocoder geo = new Geocoder(MyApplication.getAppContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(mValues.get(position).getFrom().getLatitude(), mValues.get(position).getFrom().getLongitude(), 1);
                if (addresses.isEmpty()) {
                    holder.mLocationView1.setText("Waiting for Location");
                } else {
                    if (addresses.size() > 0) {
                        holder.mLocationView1.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                Geocoder geo = new Geocoder(MyApplication.getAppContext(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(mValues.get(position).getTo().getLatitude(), mValues.get(position).getTo().getLongitude(), 1);
                if (addresses.isEmpty()) {
                    holder.mLocationView2.setText("Waiting for Location");
                } else {
                    if (addresses.size() > 0) {
                        holder.mLocationView2.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction("History", holder.mItem);
                    Log.e("History_ITEM", holder.mItem.getStatus());
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
        public final TextView mLocationView1;
        public final TextView mLocationView2;
        public final RatingBar mRatingBar;
        public History mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mLocationView1 = (TextView) view.findViewById(R.id.location);
            mLocationView2 = (TextView) view.findViewById(R.id.location2);
            mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
