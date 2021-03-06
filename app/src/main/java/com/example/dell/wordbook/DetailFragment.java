package com.example.dell.wordbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dell.wordbook.dummy.DummyContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "word";
    private static final String ARG_PARAM2 = "param2";
    View view;
    TextView textViewWord;
    EditText textViewMeaning;
    EditText textViewSample;
    Button removebutton;
    Button changebutton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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

        String sql="select * from wordstable where word=\""+mParam1+"\"";
        Log.i("888",mParam1);
        Cursor cursor=sqlite.readdb.rawQuery(sql,null);
        if(cursor.moveToNext()) {
            view = inflater.inflate(R.layout.fragment_detail, container, false);
            textViewWord = (TextView) view.findViewById(R.id.word);
            textViewMeaning = (EditText) view.findViewById(R.id.wordmeaning);
            textViewSample = (EditText) view.findViewById(R.id.wordsample);
            removebutton = (Button) view.findViewById(R.id.removebutton);
            changebutton = (Button) view.findViewById(R.id.changebutton);
            textViewWord.setText(cursor.getString(0));
            textViewMeaning.setText(cursor.getString(1));
            textViewSample.setText(cursor.getString(2));

            removebutton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sql = "delete from wordstable where word=\"" + mParam1 + "\"";
                            sqlite.writedb.execSQL(sql);
                            textViewWord.setText("");
                            textViewMeaning.setText("");
                            textViewSample.setText("");
                            removebutton.setVisibility(removebutton.GONE);
                            changebutton.setVisibility(changebutton.GONE);
                            DummyContent.gx();
                            MainActivity.gxfra();
                        }
                    }
            );
            changebutton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sql="update wordstable set meaning=?,sample=? where word=?";
                            sqlite.writedb.execSQL(sql,new String[]{textViewMeaning.getText().toString(),textViewSample.getText().toString(),textViewWord.getText().toString()});

                        }
                    }
            );
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
