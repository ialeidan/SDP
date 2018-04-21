package sdp01.sdp.com.sdp01.customer;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.braintreepayments.cardform.view.CardForm;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sdp01.sdp.com.sdp01.R;
import sdp01.sdp.com.sdp01.UserMainFragment;
import sdp01.sdp.com.sdp01.data_source.DataSourceRequestListener;
import sdp01.sdp.com.sdp01.data_source.ErrorCode;
import sdp01.sdp.com.sdp01.models.Request;
import sdp01.sdp.com.sdp01.sp.SPServiceFragment;
import sdp01.sdp.com.sdp01.util.AuthInfo;
import sdp01.sdp.com.sdp01.util.Networking;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private Button button;
    private TextView textView;
    private RatingBar ratingBar;
    private CardForm cardForm;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mListener != null) {
            mListener.onFragmentInteraction("Payment");
        }

        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(v);
            }
        });

        cardForm = (CardForm) view.findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
//                .mobileNumberRequired(true)
//                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(getActivity());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }

    public void send(View view) {

        String rating = String.valueOf(ratingBar.getRating());

        if (rating.isEmpty()) {
            // Error form. TODO
            Log.e("ERROR", "Rating is required.");
            return;
        }

        if (!cardForm.isValid()) {
            Log.e("ERROR", "Card is invalid.");
            cardForm.validate();
            return;
        }

        String visa = cardForm.getCardNumber();
        String cvv = cardForm.getCvv();
        String expir = cardForm.getExpirationMonth() + "/" + cardForm.getExpirationYear();
        String postal = cardForm.getPostalCode();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("visa", visa);
            jsonObject.put("cvv", cvv);
            jsonObject.put("expir", expir);
            jsonObject.put("postal", postal);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", "Card is invalid.");
            return;
        }

        Networking.sendPayment(jsonObject.toString(), rating, new DataSourceRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.e("RESPONSE", response.toString());
                    String requests = response.getString("request");

                    if (requests.equals("error")) {
                        Log.e("ERROR", "Couldn't send payment.");
                        return;
                    }


                    AlertDialog.Builder creditDialog = new AlertDialog.Builder(getContext());
                    creditDialog.setTitle("Payment Sent")
                            .setMessage("Payment sent successfully.")
                            .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AuthInfo.setRequestId(null);
                                    Fragment fragment = new UserMainFragment();
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.replace(R.id.mainFrame, fragment);
                                    ft.commit();
                                }
                            }).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ERROR", "Couldn't send payment.");
                }
            }

            @Override
            public void onError(ErrorCode anError) {
                Log.e("ERROR", "Couldn't send payment.");
            }
        });


    }
}
