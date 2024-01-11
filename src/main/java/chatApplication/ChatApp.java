package chatApplication;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChatApp extends Application
{
    GridPane gridPane = new GridPane();
    private Stage primaryStage;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start (Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Chat Application");

        //Initial login screen
        showLoginScreen();
        
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(300);
        
        primaryStage.setResizable(true);
        primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> handleResize());
        primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> handleResize());
        
        primaryStage.show();
    }
    
    private void handleResize() {
        double newWidth = primaryStage.getWidth();
        double newHeight = primaryStage.getHeight();

        // Adjust the size of UI components based on the new window size
        gridPane.setPrefSize(newWidth * 0.8, newHeight * 0.8);
    }

    
    private void showLoginScreen() {
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");

        Text messageText = new Text("Welcome to Chase's Chat Room Application.\nPlease enter your username and password.");
        messageText.setLineSpacing(5);

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        // Create RowConstraints for the first row and set vgrow to Priority.NEVER
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.NEVER);
        gridPane.getRowConstraints().add(rowConstraints);

        gridPane.add(messageText, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                showChatRoomSelectionScreen(true);
            } else {
                showWarningAlert("Incomplete Information", "Please enter both username and password.");
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> exitChatRoomApplication());

        HBox buttonBox = new HBox(10, loginButton, exitButton);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        gridPane.add(buttonBox, 1, 3);
        gridPane.setAlignment(Pos.CENTER);

        Text versionText = new Text("Chat App v0.2");
        versionText.setFill(Color.GRAY);
        versionText.setFont(Font.font("Arial", FontWeight.BOLD, 10));

        VBox vbox = new VBox(gridPane, versionText);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("base");

        Scene loginScene = new Scene(vbox, 400, 300);
        loginScene.getStylesheets().add("/chatApplication/styles.css");
        primaryStage.setScene(loginScene);
    }

    private void exitChatRoomApplication ()
    {
        primaryStage.close();
    }

    private void showChatRoomSelectionScreen(boolean fadeTransition) {
        ListView<String> chatRoomsListView = new ListView<>();
        chatRoomsListView.getStyleClass().add("custom-list-view");
        chatRoomsListView.getItems().addAll("Room 1", "Room 2", "Room 3");
        
        Button joinButton = new Button("Join");
        joinButton.getStyleClass().add("custom-button");
        joinButton.setOnAction(e -> {
            String selectedRoom = chatRoomsListView.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                showChatRoomScreen(selectedRoom);
            } else {
                showWarningAlert("Select a room", "Please select a room before joining.");
            }
        });
        
        Button leaveChatRoomButton = new Button("Back");
        leaveChatRoomButton.setOnAction(e -> showLoginScreen());
        
        HBox buttonBox = new HBox(10, joinButton, leaveChatRoomButton);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(chatRoomsListView, buttonBox); // Use the HBox for buttons
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(5, 5, 5, 5));

        Scene chatRoomSelectionScene = new Scene(vbox, 400, 300);
        chatRoomSelectionScene.getStylesheets().add("/chatApplication/styles.css");
        applyFadeTransition(chatRoomSelectionScene, fadeTransition);
        primaryStage.setScene(chatRoomSelectionScene);
    }
    
    private void showWarningAlert (String headerText, String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showChatRoomScreen(String selectedRoom) {
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.appendText("Welcome to Chat " + selectedRoom + ".\nFeel free to discuss whatever topics you like. Please respect others.\n");
        
        TextField messageField = new TextField();
        
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> sendMessage(messageField.getText()));
        
        Button leaveChatRoomButton = new Button("Back");
        leaveChatRoomButton.setOnAction(e -> showChatRoomSelectionScreen(false));
        
        Label roomLabel = new Label("Chat " + selectedRoom);
        roomLabel.getStyleClass().add("header-text");
        roomLabel.setAlignment(Pos.CENTER);
        
        HBox roomHeader = new HBox(roomLabel);
        roomHeader.setAlignment(Pos.CENTER);
        roomHeader.setPadding(new Insets(10, 0, 10, 0));
        
        HBox buttonBox = new HBox(10, sendButton, leaveChatRoomButton);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        buttonBox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox(roomHeader, chatArea, messageField, buttonBox);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setSpacing(10);
        
        Scene chatRoomScene = new Scene(vbox, 400, 300);
        chatRoomScene.getStylesheets().add("/chatApplication/styles.css");
        primaryStage.setScene(chatRoomScene);
    }

    private void sendMessage (String text)
    {
        // TODO Auto-generated method stub
    }
    
    private void applyFadeTransition(Scene scene, boolean forward) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), scene.getRoot());
        
        if (forward) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);   
        } else {
            //Do nothing
        }
        
        fadeTransition.play();
    }

}
