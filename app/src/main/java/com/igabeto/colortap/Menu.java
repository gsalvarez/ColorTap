package com.igabeto.colortap;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class Menu extends Fragment {

    //private AdView mAdView;

    DatabaseHelper db1;
    SQLiteDatabase db;
    RadioGroup rg;

    int mode;
    String playerName;
    int playerNormalRecord;
    int playerAdvancedRecord;
    float playerAverage;
    Cursor cursor;

    public Menu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        /*mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        db1 = new DatabaseHelper(getActivity().getApplication().getApplicationContext(), DatabaseHelper.DB_NAME, null, DatabaseHelper.db_v);
        db = db1.getWritableDatabase();

        this.mode=0;
        this.playerName = "";
        this.playerNormalRecord = 0;
        this.playerAdvancedRecord = 0;
        this.playerAverage = (this.playerNormalRecord + this.playerAdvancedRecord) / 2;

        TextView onePlayer = (TextView) view.findViewById(R.id.btnOnePlayer);
        TextView onePlayerAdv = (TextView) view.findViewById(R.id.btnOnePlayerAd);
        TextView twoPlayers = (TextView) view.findViewById(R.id.btnTwoPlayers);
        TextView twoPlayersAdv = (TextView) view.findViewById(R.id.btnTwoPlayersAd);
        TextView records = (TextView) view.findViewById(R.id.btnRecords);

        onePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;
                showPlayersDialog();
            }
        });
        onePlayerAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;
                showPlayersDialog();
            }
        });
        twoPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                TwoPlayers fragment = new TwoPlayers();
                Bundle args = new Bundle();
                args.putInt("someInt", 1);
                fragment.setArguments(args);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
        twoPlayersAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                TwoPlayers fragment = new TwoPlayers();
                Bundle args = new Bundle();
                args.putInt("someInt", 2);
                fragment.setArguments(args);
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Records fragment = new Records();
                transaction.addToBackStack(null);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        return view;
    }

    public void showPlayersDialog(){
        List<String> playersList = new ArrayList<>();
        cursor = db.rawQuery("SELECT name FROM players ORDER BY name", null);
        while(cursor.moveToNext()){
            playersList.add(cursor.getString(0));
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Choose a player");
        final View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.users_dialog, (ViewGroup) getView(), false);
        rg = (RadioGroup) viewInflated.findViewById(R.id.radioGroup);
        for (int i = 0; i < playersList.size(); i++){
            RadioButton rb = new RadioButton(getActivity());
            rb.setText(playersList.get(i));
            rb.setTag(playersList.get(i));
            rg.addView(rb);
        }
        alertDialog.setView(viewInflated);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                View checked = viewInflated.findViewById(rg.getCheckedRadioButtonId());
                int index = rg.indexOfChild(checked);
                if(index == -1){
                    Toast.makeText(getActivity().getApplication().getApplicationContext(), "You have to choose an option", Toast.LENGTH_SHORT).show();
                }
                else if(rg.indexOfChild(viewInflated.findViewById(rg.getCheckedRadioButtonId())) == 0){
                    showNewUserDialog();
                }
                else{
                    String tag = checked.getTag().toString();
                    cursor = db.rawQuery("SELECT name, normalRecord, advancedRecord, average FROM players WHERE name = '"+tag+"'", null);
                    cursor.moveToFirst();
                    playerName = cursor.getString(0);
                    playerNormalRecord = cursor.getInt(1);
                    playerAdvancedRecord = cursor.getInt(2);
                    playerAverage = cursor.getFloat(3);
                    dialog.dismiss();

                    fragmentTransaction();
                }
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void showNewUserDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Enter your name");
        View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog, (ViewGroup) getView(), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        alertDialog.setView(viewInflated);
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerName = input.getText().toString();
                if(playerName.equals("")||playerName.contains(".")||playerName.contains("#")||playerName.contains("$")||playerName.contains("[")||playerName.contains("]")||playerName.contains("  ")){
                    Toast.makeText(getActivity().getApplication().getApplicationContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                }
                else{
                    cursor = db.rawQuery("SELECT name FROM players WHERE name = '"+playerName+"'", null);
                    if(cursor.getCount() > 0){
                        Toast.makeText(getActivity().getApplication().getApplicationContext(), "Player name already exists", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.insert("players", null, newUserValues(playerName));
                        dialog.dismiss();
                        fragmentTransaction();
                    }
                }
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public ContentValues newUserValues(String playerName){
        ContentValues values = new ContentValues();
        values.put("name", playerName);
        values.put("normalRecord", 0);
        values.put("advancedRecord", 0);
        values.put("average", 0);
        return values;
    }

    public void fragmentTransaction(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        OnePlayer fragment = new OnePlayer();
        Bundle args = new Bundle();
        args.putInt("mode", mode);
        args.putString("playerName", playerName);
        args.putInt("playerNormalRecord", playerNormalRecord);
        args.putInt("playerAdvancedRecord", playerAdvancedRecord);
        args.putFloat("playerAverage", playerAverage);
        fragment.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}