# Chat Application

The Chat Application is a simple JavaFX-based messaging application that allows users to log in, select chat rooms, and communicate with other users in real-time.

## Features

- **Login Screen:** Users can log in with a username and password to access the chat application.

- **Chat Room Selection:** After logging in, users can choose from different chat rooms to join.

- **Real-time Messaging:** Users can send and receive messages within the selected chat room.

## Tools Used

- **Java:** The application is built using Java programming language.

- **JavaFX:** The graphical user interface is developed using JavaFX, a Java library for building rich desktop applications.

## How to Run the Application

1. **Clone the Repository:**
git clone https://github.com/MoyerLawn/JavaChatApplication


2. **Navigate to the Project Directory:**
cd ChatApplication


3. **Compile and Run the Application:**
javac -cp "path/to/javafx-sdk/lib/*" chatApplication/ChatApp.java
java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml chatApplication.ChatApp

## Additional Notes

- The application uses CSS for styling. The styles are defined in the `styles.css` file.

- The code is organized into separate classes for better maintainability. You can find the main application logic in the `ChatApp.java` file, and each screen has its own class (e.g., `LoginScreen.java`, `ChatRoomSelectionScreen.java`, `ChatRoomScreen.java`).

- Feel free to explore and customize the application based on your requirements.

## Contributors

- Chase Moyer

If you encounter any issues or have suggestions for improvements, please feel free to [create an issue](https://github.com/MoyerLawn/JavaChatApplication/issues) or submit a pull request.

Happy chatting!
