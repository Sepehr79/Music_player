package com.mediaplayer.main.controller;

/**
 * Sample Skeleton for 'player.fxml' Controller Class
 */

import com.mediaplayer.main.logic.TimeConvertor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Controller {

    @FXML // fx:id="slider"
    private Slider slider; // Value injected by FXMLLoader

    @FXML // fx:id="playButton"
    private Button playButton; // Value injected by FXMLLoader

    @FXML // fx:id="timeLabel"
    private Text timeLabel; // Value injected by FXMLLoader

    private MediaPlayer mediaPlayer;

    private File file;

    private boolean isPlaying = false;

    @FXML
    void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your music");

        file = fileChooser.showOpenDialog(new Stage());

        if (file != null){
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnPlaying(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.setCycleCount(Integer.MAX_VALUE);

                    new Thread(()->{
                        while (isPlaying){
                            slider.setValue(mediaPlayer.getCurrentTime().toSeconds() / mediaPlayer.getStopTime().toSeconds() * 100);
                            timeLabel.setText(TimeConvertor.secondsToMinutesAndSeconds(mediaPlayer.getCurrentTime().toSeconds()));
                            try {
                                Thread.currentThread().sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }

    }

    @FXML
    void playClicked(ActionEvent event) {
        if (mediaPlayer != null){
            if(isPlaying){
                mediaPlayer.pause();
                playButton.setText("⏵");
            }
            else{
                mediaPlayer.play();
                playButton.setText("||");
            }

            isPlaying = !isPlaying;
        }
    }

    public void sliderDragged(MouseEvent mouseEvent) {
        mediaPlayer.seek(new Duration(slider.getValue() * mediaPlayer.getStopTime().toSeconds() * 10));
    }

}

