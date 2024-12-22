package hu.petrik.stopwatch;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private Text timerDisplay;

    @FXML
    private ListView<String> lapList;

    @FXML
    private Button lapResetButton;

    private LocalDateTime startTime;
    private Duration elapsedTime = Duration.ZERO;
    private Timer timer;
    private boolean running = false;

    @FXML
    private void startTimer() {
        if (!running) {
            running = true;
            startTime = LocalDateTime.now();
            lapResetButton.setText("Részidő");
            timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    updateDisplay();
                }
            }, 0, 10);
        }
    }

    @FXML
    private void stopTimer() {
        if (running) {
            running = false;
            if (timer != null) {
                timer.cancel();
            }
            elapsedTime = elapsedTime.plus(Duration.between(startTime, LocalDateTime.now()));
            lapResetButton.setText("Reset");
        }
    }

    @FXML
    private void handleLapOrReset() {
        if (running) {
            recordLap();
        } else {
            resetTimer();
        }
    }

    private void resetTimer() {
        lapList.getItems().clear();
        timerDisplay.setText("00:00:00.000");
        startTime = null;
        elapsedTime = Duration.ZERO;
    }

    private void recordLap() {
        if (running && startTime != null) {
            lapList.getItems().add(durationToString(elapsedTime.plus(Duration.between(startTime, LocalDateTime.now()))));
        }
    }

    private void updateDisplay() {
        if (running && startTime != null) {
            Platform.runLater(() -> timerDisplay.setText(durationToString(elapsedTime.plus(Duration.between(startTime, LocalDateTime.now())))));
        }
    }

    private String durationToString(Duration duration)
    {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        long millis = duration.toMillis() % 1000;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis);
    }
}