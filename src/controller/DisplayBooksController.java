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
	 @FXML
	    private TableColumn<Book, String> columnAuthor;

	    @FXML
	    private TableColumn<Book, Long> columnISBN;

	    @FXML
	    private TableColumn<Book, String> columnTitle;

	    @FXML
	    private TableView<Book> tableBook;
	    
	    private Connection connection = DatabaseConnection.getInstance().getConnection();
	    private BookDAO bookDAO = new BookDAO(connection);
	    
	    @FXML
	    public void initialize() {
	    

	    	ObservableList<Book> availableBooks = FXCollections.observableArrayList();
	    	// Filter available books and add them to the availableBooks list
	        for (Book book : bookDAO.fetchAll()) {
	                availableBooks.add(book);	               
	            
	        }
	       
	        // Bind only the columns you want to show
	        columnTitle.setCellValueFactory(new PropertyValueFactory<>("titulo"));
	        columnISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
	        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("autor"));

	        // Set data to TableView
	        tableBook.setItems(availableBooks);
	    }

	    @FXML
	    void handleUndo(ActionEvent event) {	    	
	       	if(!tableBook.getSelectionModel().isEmpty()) {
		    	Book book = tableBook.getSelectionModel().getSelectedItem();		    	
	        	bookDAO.delete(book.getISBN());
	        	initialize();
	        	} else {
	                Main.mostrarAlerta("Error", "Not Selected Book", "You must select a book to be removed.");
	            }
	    	initialize();
	    }
	    
	
	    @FXML
	    void goBack(ActionEvent event) {
	    	
	    	 Main.loadScene("/view/Main.fxml");

	    }



}
