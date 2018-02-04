package com.igabeto.colortap;

import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TwoPlayers extends Fragment {

    TextView mainColor;
    TextView color0;
    TextView color1;
    TextView color2;
    TextView color3;
    TextView color4;
    TextView color5;
    TextView color6;
    TextView color7;
    TextView color8;
    TextView color9;
    TextView color10;
    TextView color11;
    TextView color12;
    TextView color13;
    TextView color14;
    TextView color15;
    TextView time1;
    TextView time2;
    TextView points1;
    TextView points2;
    TextView led1;
    TextView led2;

    int record1;
    int record2;
    int counter;
    int mode;
    CountDownTimer timer;
    Random rnd = new Random();

    TypedArray myColors;
    Animation anim;

    SoundPool sounds;
    int soundGoodPoint;
    int soundBadPoint;
    int soundReady;
    int soundSet;
    int soundGo;
    int soundDraw;
    int soundTimeOver;

    public TwoPlayers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two_players, container, false);

        this.record1 = 0;
        this.record2 = 0;
        this.counter = 3;
        mode = getArguments().getInt("someInt");

        this.sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        this.soundGoodPoint = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.good_point, 1);
        this.soundBadPoint = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.bad_point, 1);
        this.soundReady = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.ready, 1);
        this.soundSet = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.set, 1);
        this.soundGo = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.go, 1);
        this.soundTimeOver = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.time_over, 1);
        this.soundDraw = sounds.load(getActivity().getApplication().getApplicationContext(), R.raw.its_a_tie, 1);

        timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                led1.setText(String.valueOf(counter));
                led2.setText(String.valueOf(counter));
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
                led1.setText("");
                led2.setText("");
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
        color8 = (TextView) view.findViewById(R.id.color8);
        color9 = (TextView) view.findViewById(R.id.color9);
        color10 = (TextView) view.findViewById(R.id.color10);
        color11 = (TextView) view.findViewById(R.id.color11);
        color12 = (TextView) view.findViewById(R.id.color12);
        color13 = (TextView) view.findViewById(R.id.color13);
        color14 = (TextView) view.findViewById(R.id.color14);
        color15 = (TextView) view.findViewById(R.id.color15);

        time1 = (TextView) view.findViewById(R.id.txtTime1);
        time2 = (TextView) view.findViewById(R.id.txtTime2);
        points1 = (TextView) view.findViewById(R.id.txtPoints1);
        points2 = (TextView) view.findViewById(R.id.txtPoints2);
        led1 = (TextView) view.findViewById(R.id.led1);
        led2 = (TextView) view.findViewById(R.id.led2);

        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        mainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points1.setText(String.valueOf(record1));
                points2.setText(String.valueOf(record2));
                time1.setText("30");
                time2.setText("30");
                mainColor.setClickable(false);
                timer.start();
            }
        });
        color0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color0.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color1.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color2.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color3.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color4.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color5.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color6.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color7.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(1);
                }
                else{
                    bad(1);
                }
            }
        });
        color8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color8.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color9.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color10.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color11.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color12.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color13.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color14.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
                }
            }
        });
        color15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(color15.getBackground().getConstantState() == mainColor.getBackground().getConstantState()){
                    good(2);
                }
                else{
                    bad(2);
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
        Collections.shuffle(index);
        color8.setBackgroundResource(myColors.getResourceId(index.get(0), -1));
        color9.setBackgroundResource(myColors.getResourceId(index.get(1), -1));
        color10.setBackgroundResource(myColors.getResourceId(index.get(2), -1));
        color11.setBackgroundResource(myColors.getResourceId(index.get(3), -1));
        color12.setBackgroundResource(myColors.getResourceId(index.get(4), -1));
        color13.setBackgroundResource(myColors.getResourceId(index.get(5), -1));
        color14.setBackgroundResource(myColors.getResourceId(index.get(6), -1));
        color15.setBackgroundResource(myColors.getResourceId(index.get(7), -1));
    }
    public void good(int id){
        sounds.play(soundGoodPoint, 1, 1, 1, 0, 1);
        if(id == 1){
            record1++;
            points1.setText(String.valueOf(record1));
            led1.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
        else{
            record2++;
            points2.setText(String.valueOf(record2));
            led2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }
        int rndInt = rnd.nextInt(myColors.length());
        int drawableID = myColors.getResourceId(rndInt, -1);
        mainColor.setBackgroundResource(drawableID);
        if(mode == 2){
            shuffleColors();
        }
    }
    public void bad(int id){
        sounds.play(soundBadPoint, 1, 1, 1, 0, 1);
        if(id == 1){
            if(record1 > 0){
                record1--;
                points1.setText(String.valueOf(record1));
            }
            led1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
        else{
            if(record2 > 0){
                record2--;
                points2.setText(String.valueOf(record2));
            }
            led2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
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
        color8.setClickable(true);
        color9.setClickable(true);
        color10.setClickable(true);
        color11.setClickable(true);
        color12.setClickable(true);
        color13.setClickable(true);
        color14.setClickable(true);
        color15.setClickable(true);

        timer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time1.setText(String.valueOf(millisUntilFinished / 1000));
                time2.setText(String.valueOf(millisUntilFinished / 1000));
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
        color8.setClickable(false);
        color9.setClickable(false);
        color10.setClickable(false);
        color11.setClickable(false);
        color12.setClickable(false);
        color13.setClickable(false);
        color14.setClickable(false);
        color15.setClickable(false);

        sounds.play(soundTimeOver, 1, 1, 1, 0, 1);

        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(record1 > record2){
                    led1.setText(getResources().getText(R.string.winner));
                    led2.setText(getResources().getText(R.string.loser));
                    led1.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    led2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    led1.startAnimation(anim);
                    led2.startAnimation(anim);
                }
                else if(record2 > record1){
                    led1.setText(getResources().getText(R.string.loser));
                    led2.setText(getResources().getText(R.string.winner));
                    led1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    led2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    led1.startAnimation(anim);
                    led2.startAnimation(anim);
                }
                else{
                    led1.setText(getResources().getText(R.string.draw));
                    led2.setText(getResources().getText(R.string.draw));
                    led1.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    led2.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    led1.startAnimation(anim);
                    led2.startAnimation(anim);
                    if(millisUntilFinished / 1000 < 2){
                        sounds.play(soundDraw, 1, 1, 1, 0, 1);
                    }
                }
            }

            @Override
            public void onFinish() {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        };
        timer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}