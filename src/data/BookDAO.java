package data;

import java.sql.*;
import java.util.ArrayList;
import model.Book;

public class BookDAO implements CRUD_Operation<Book, Long> {
    private Connection connection;

    // Constructor que recibe la conexión a la base de datos
    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    // Método para guardar un libro en la base de datos
    @Override
    public void save(Book book) {
        // Consulta SQL para insertar un nuevo libro en la tabla Book2
        String sql = "INSERT INTO Book2 (title, author, isbn, year, available) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Establece los valores de los parámetros en la consulta SQL
            stmt.setString(1, book.getTitulo());  // Título del libro
            stmt.setString(2, book.getAutor());   // Autor del libro
            stmt.setLong(3, book.getISBN());      // ISBN del libro
            stmt.setInt(4, book.getYear());       // Año de publicación del libro
            stmt.setBoolean(5, book.isDisponible()); // Disponibilidad del libro (si está disponible o no)
            stmt.executeUpdate(); // Ejecuta la consulta para guardar el libro en la base de datos
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones si ocurre un error
        }
    }

    // Método para actualizar la información de un libro en la base de datos
    @Override
    public void update(Book article) {
        // Consulta SQL para actualizar un libro existente en la tabla Book2
        String sql = "UPDATE Book2 SET title=?, author=?, year=?, available=? WHERE isbn=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Establece los valores de los parámetros en la consulta SQL
            stmt.setString(1, article.getTitulo());
            stmt.setString(2, article.getAutor());
            stmt.setInt(3, article.getYear());
            stmt.setBoolean(4, article.isDisponible());
            stmt.setLong(5, article.getISBN()); // Se actualiza el libro usando el ISBN
            stmt.executeUpdate(); // Ejecuta la consulta de actualización
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones si ocurre un error
        }
    }

    // Método para eliminar un libro de la base de datos por su ISBN
    @Override
    public void delete(Long isbn) {
        // Consulta SQL para eliminar un libro de la tabla Book2 por su ISBN
        String sql = "DELETE FROM Book2 WHERE isbn=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Establece el valor del parámetro en la consulta SQL
            stmt.setLong(1, isbn); // Se pasa el ISBN del libro a eliminar
            stmt.executeUpdate(); // Ejecuta la consulta de eliminación
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones si ocurre un error
        }
    }

    // Método para obtener un libro por su ISBN
    @Override
    public Book fetchById(Long isbn) {
        // Consulta SQL para obtener un libro específico por su ISBN
        String sql = "SELECT * FROM Book2 WHERE isbn=? AND is_deleted=0";  // Considerando el campo is_deleted
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Establece el valor del parámetro en la consulta SQL
            stmt.setLong(1, isbn); // Se pasa el ISBN para obtener el libro
            ResultSet rs = stmt.executeQuery(); // Ejecuta la consulta y obtiene los resultados
            if (rs.next()) { // Si se encuentra el libro
                // Se crea un objeto Book con los resultados obtenidos
                return new Book(
                    rs.getString("title"),  // Título del libro
                    rs.getString("author"), // Autor del libro
                    rs.getLong("ISBN"),     // ISBN del libro
                    rs.getInt("year"),      // Año de publicación del libro
                    rs.getBoolean("available") // Disponibilidad del libro
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones si ocurre un error
        }
        return null; // Retorna null si no se encuentra el libro
    }

    // Método para obtener todos los libros de la base de datos
    @Override
    public ArrayList<Book> fetchAll() {
        ArrayList<Book> books = new ArrayList<>();
        // Consulta SQL para obtener todos los libros que no estén eliminados
        String sql = "SELECT * FROM Book2 WHERE is_deleted=0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Recorre los resultados de la consulta
            while (rs.next()) {
                // Crea un objeto Book con los datos del libro obtenido
                String title = rs.getString("title");
                String author = rs.getString("author");
                long isbn = rs.getLong("ISBN");
                int year = rs.getInt("year");
                boolean available = rs.getBoolean("available");

                Book book = new Book(title, author, isbn, year, available);
                books.add(book); // Añade el libro a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones si ocurre un error
        }
        return books; // Retorna la lista de libros
    }

    // Método para autenticar un libro por su ISBN (por ejemplo, verificar si existe en la base de datos)
    @Override
    public boolean authenticate(Long isbn) {
        // Consulta SQL para verificar si un libro existe con el ISBN proporcionado
        String sql = "SELECT isbn FROM book2 WHERE isbn=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, isbn); // Se pasa el ISBN del libro para verificar si existe
            ResultSet rs = stmt.executeQuery(); // Ejecuta la consulta
            if (rs.next()) {
                // Si se encuentra un libro con el mismo ISBN, devuelve true
                return rs.getLong("isbn") == isbn;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones si ocurre un error
        }
        return false; // Retorna false si no se encuentra el libro con ese ISBN
    }
}
