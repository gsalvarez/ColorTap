package com.igabeto.colortap;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OnePlayer extends Fragment {

    //private AdView mAdView;

    DatabaseHelper db1;
    SQLiteDatabase db;

    TextView mainColor;
    TextView color0;
    TextView color1;
    TextView color2;
    TextView color3;
    TextView color4;
    TextView color5;
    TextView color6;
    TextView color7;
    TextView time;
    TextView points;

    int mode;
    String playerName;
    int newRecord;
    int playerNormalRecord;
    int playerAdvancedRecord;
    float playerAverage;
    int counter;
    Random rnd = new Random();
    CountDownTimer timer;

    TypedArray myColors;
    SoundPool sounds;
    int soundGoodPoint;
    int soundBadPoint;
    int soundReady;
    int soundSet;
    int soundGo;
    int soundTimeOver;

    public OnePlayer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_player, container, false);

        /*mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

        db1 = new DatabaseHelper(getActivity().getApplication().getApplicationContext(), DatabaseHelper.DB_NAME, null, DatabaseHelper.db_v);
        db = db1.getWritableDatabase();

        this.mode = getArguments().getInt("mode");
        this.newRecord = 0;
        this.playerName = getArguments().getString("playerName");
        this.playerNormalRecord = getArguments().getInt("playerNormalRecord");
        this.playerAdvancedRecord = getArguments().getInt("playerAdvancedRecord");
        this.playerAverage = getArguments().getFloat("playerAverage");
        this.counter = 3;

        this.sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        this.soundGoodPoint = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.good_point, 1);
        this.soundBadPoint = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.bad_point, 1);
        this.soundReady = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.ready, 1);
        this.soundSet = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.set, 1);
        this.soundGo = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.go, 1);
        this.soundTimeOver = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.time_over, 1);

        timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mainColor.setText(String.valueOf(counter));
                switch(counter){
                    case 2:
                        sounds.play(soundReady, 1, 1, 1, 0, 1);
                        break;
                    case 1:
                        sounds.play(soundSet, 1, 1, 1, 0, 1);
                        break;
                }
                counter--;
            }

            @Override
            public void onFinish() {
                sounds.play(soundGo, 1, 1, 1, 0, 1);
                mainColor.setText("");
                startGame();
            }
        };

        myColors = getResources().obtainTypedArray(R.array.colors);

        mainColor = (TextView) view.findViewById(R.id.mainColor);
        color0 = (TextView) view.findViewById(R.id.color0);
        color1 = (TextView) view.findViewById(R.id.color1);
        color2 = (TextView) view.findViewById(R.id.color2);
        color3 = (TextView) view.findViewById(R.id.color3);
        color4 = (TextView) view.findViewById(R.id.color4);
        color5 = (TextView) view.findViewById(R.id.color5);
        color6 = (TextView) view.findViewById(R.id.color6);
        color7 = (TextView) view.findViewById(R.id.color7);

        time = (TextView) view.findViewById(R.id.txtNumTime);
        points = (TextView) view.findViewById(R.id.txtNumPoints);

        mainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points.setText(String.valueOf(newRecord));
                time.setText("30");
                mainColor.setClickable(false);
                timer.start();
            }
        });

        color0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color0.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color1.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color2.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color3.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color4.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color5.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color6.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });
        color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color7.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good();
                }
                else{
                    bad();
                }
            }
        });

        return view;
    }
    public void shuffleColors(){
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < myColors.length(); i++){
            index.add(i);
        }
        Collections.shuffle(index);
        color0.setBackgroundResource(myColors.getResourceId(index.get(0), -1));
        color1.setBackgroundResource(myColors.getResourceId(index.get(1), -1));
        color2.setBackgroundResource(myColors.getResourceId(index.get(2), -1));
        color3.setBackgroundResource(myColors.getResourceId(index.get(3), -1));
        color4.setBackgroundResource(myColors.getResourceId(index.get(4), -1));
        color5.setBackgroundResource(myColors.getResourceId(index.get(5), -1));
        color6.setBackgroundResource(myColors.getResourceId(index.get(6), -1));
        color7.setBackgroundResource(myColors.getResourceId(index.get(7), -1));
    }
    public void good(){
        newRecord++;
        points.setText(String.valueOf(newRecord));
        int rndInt = rnd.nextInt(myColors.length());
        int drawableID = myColors.getResourceId(rndInt, -1);
        mainColor.setBackgroundResource(drawableID);
        points.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        sounds.play(soundGoodPoint, 1, 1, 1, 0, 1);
        if(mode == 2){
            shuffleColors();
        }
    }
    public void bad(){
        if(newRecord > 0){
            newRecord--;
            points.setText(String.valueOf(newRecord));
        }
        points.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        sounds.play(soundBadPoint, 1, 1, 1, 0, 1);
    }

    public void startGame(){
        int rndInt = rnd.nextInt(myColors.length());
        int drawableID = myColors.getResourceId(rndInt, -1);
        mainColor.setBackgroundResource(drawableID);

        shuffleColors();

        color0.setClickable(true);
        color1.setClickable(true);
        color2.setClickable(true);
        color3.setClickable(true);
        color4.setClickable(true);
        color5.setClickable(true);
        color6.setClickable(true);
        color7.setClickable(true);

        timer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished / 1000));
            }
            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }

    public void endGame(){
        color0.setClickable(false);
        color1.setClickable(false);
        color2.setClickable(false);
        color3.setClickable(false);
        color4.setClickable(false);
        color5.setClickable(false);
        color6.setClickable(false);
        color7.setClickable(false);
        sounds.play(soundTimeOver, 1, 1, 1, 0, 1);
        checkRecords();
        timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                points.setBackgroundColor(getResources().getColor(android.R.color.white));
            }

            @Override
            public void onFinish() {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        };
        timer.start();
    }

    public void checkRecords(){
        ContentValues newRecords = new ContentValues();
        switch (mode){
            case 1:
                if(newRecord > playerNormalRecord){
                    newRecords.put("normalRecord", newRecord);
                    newRecords.put("average", (newRecord+playerAdvancedRecord)/2f);
                    db.update("players", newRecords, "name='"+playerName+"'", null);
                    Toast.makeText(getActivity().getApplication().getApplicationContext(), "new Normal record!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity().getApplication().getApplicationContext(), "Your Normal Record is "+playerNormalRecord, Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(newRecord > playerAdvancedRecord){
                    newRecords.put("advancedRecord", newRecord);
                    newRecords.put("average", (playerNormalRecord+newRecord)/2f);
                    db.update("players", newRecords, "name='"+playerName+"'", null);
                    Toast.makeText(getActivity().getApplication().getApplicationContext(), "new Advanced record!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity().getApplication().getApplicationContext(), "Your Advanced Record is "+playerAdvancedRecord, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}