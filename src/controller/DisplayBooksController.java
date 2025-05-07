package controller;

import java.sql.Connection;

import application.Main;
import data.BookDAO;
import data.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;

public class DisplayBooksController {

    // Columnas de la tabla para mostrar los datos de los libros
    @FXML
    private TableColumn<Book, String> columnAuthor;

    @FXML
    private TableColumn<Book, Long> columnISBN;

    @FXML
    private TableColumn<Book, String> columnTitle;

    // Tabla donde se mostrarán los libros disponibles
    @FXML
    private TableView<Book> tableBook;

    // Conexión a la base de datos y creación del DAO
    private Connection connection = DatabaseConnection.getInstance().getConnection();
    private BookDAO bookDAO = new BookDAO(connection);

    // Método que se ejecuta automáticamente al cargar el controlador
    @FXML
    public void initialize() {

        // Lista observable para mostrar en la tabla
        ObservableList<Book> availableBooks = FXCollections.observableArrayList();

        // Se cargan todos los libros desde la base de datos
        for (Book book : bookDAO.fetchAll()) {
            availableBooks.add(book); // Se agregan a la lista visible en la tabla
        }

        // Se enlazan las columnas con los atributos de la clase Book
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        // Se cargan los datos a la tabla
        tableBook.setItems(availableBooks);
    }

    // Acción al hacer clic en el botón para eliminar (undo)
    @FXML
    void handleUndo(ActionEvent event) {
        if (!tableBook.getSelectionModel().isEmpty()) {
            // Se obtiene el libro seleccionado
            Book book = tableBook.getSelectionModel().getSelectedItem();

            // Se elimina de la base de datos usando el ISBN
            bookDAO.delete(book.getISBN());

            // Se recarga la tabla
            initialize();
        } else {
            // Si no hay libro seleccionado, se muestra una alerta
            Main.mostrarAlerta("Error", "Not Selected Book", "You must select a book to be removed.");
        }
        initialize(); // (Extra) se vuelve a inicializar por si acaso
    }

    // Acción para regresar a la escena principal
    @FXML
    void goBack(ActionEvent event) {
        Main.loadScene("/view/Main.fxml");
    }
}
