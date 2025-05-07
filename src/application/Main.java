package application;

// Importación de clases necesarias de JavaFX
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    // Almacena la ventana principal para usarla en otros métodos
    private static Stage primaryStage;

    // Método principal que arranca la aplicación JavaFX
    @Override
    public void start(Stage primaryStage) {
        try {
            // Se guarda la referencia del Stage en la variable estática
            Main.primaryStage = primaryStage;

            // Se carga el archivo FXML que define la interfaz inicial
            BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/Main.fxml"));

            // Se crea la escena con tamaño 400x400 y se asigna al stage
            Scene scene = new Scene(root,400,400);
            primaryStage.setScene(scene);
            primaryStage.show(); // Muestra la ventana
        } catch(Exception e) {
            e.printStackTrace(); // Muestra errores si no carga bien
        }
    }

    // Método que lanza la aplicación (punto de entrada)
    public static void main(String[] args) {
        launch(args);
    }

    // Método para cambiar de escena cargando otro archivo FXML
    public static void loadScene(String fxmlFile) {
        try {
            // Se carga el nuevo contenido desde el archivo FXML
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlFile));
            Scene scene = primaryStage.getScene();

            if (scene == null) {
                // Si no existe una escena previa, se crea una nueva
                scene = new Scene(root, 600, 600); 
                primaryStage.setScene(scene);
            } else {
                // Si ya hay una escena, se actualiza el contenido
                scene.setRoot(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar una alerta de error
    public static void mostrarAlerta(String titulo, String cabecera, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Tipo de alerta: ERROR
        alert.setTitle(titulo); // Título de la ventana
        alert.setHeaderText(cabecera); // Cabecera de la alerta
        alert.setContentText(mensaje); // Cuerpo del mensaje
        alert.showAndWait(); // Muestra y espera a que el usuario cierre
    }
}
