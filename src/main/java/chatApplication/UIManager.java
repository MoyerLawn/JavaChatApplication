package chatApplication;

import java.util.Optional;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

public class UIManager
{
    
    public void applyFadeTransition(Scene scene, boolean forward) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), scene.getRoot());
        
        if (forward) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);   
        } else {
            //Do nothing
        }
        
        fadeTransition.play();
    }
    
    public void showWarningAlert (String headerText, String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public boolean showConfirmationAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }
    
    public boolean promptToDeleteRoom(String roomToDelete) {
        return showConfirmationAlert("Delete Room", "You are about to delete " + roomToDelete + "!", "Do you want to delete this room?");
    }
}
