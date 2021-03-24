package com.mediaplayer.main.logic;

public class TimeConvertor {

    private TimeConvertor(){

    }

    public static String secondsToMinutesAndSeconds(double input){
        int seconds = (int) input % 60;
        int minutes = (int) input / 60;

        String strSeconds = (seconds >= 10)? String.valueOf(seconds):"0"+seconds;
        String strMinutes = (minutes >= 10)? String.valueOf(minutes):"0"+minutes;

        return strMinutes + ":" + strSeconds;
    }

}
