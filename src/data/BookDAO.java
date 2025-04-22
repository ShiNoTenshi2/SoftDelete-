package data;

import java.sql.*;
import java.util.ArrayList;

import model.Book;

public class BookDAO implements CRUD_Operation<Book,Long> {
    private Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO Book2 (title, author, isbn, year, available) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitulo());
            stmt.setString(2, book.getAutor());
            stmt.setLong(3, book.getISBN());
            stmt.setInt(4, book.getYear());
            stmt.setBoolean(5, book.isDisponible());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book article) {
        String sql = "UPDATE Book2 SET title=?, author=?, year=?, available=? WHERE isbn=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, article.getTitulo());
            stmt.setString(2, article.getAutor());
            stmt.setInt(3, article.getYear());
            stmt.setBoolean(4, article.isDisponible());
            stmt.setLong(5, article.getISBN());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long isbn) {
        String sql = "DELETE FROM Book2 WHERE isbn=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, isbn);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book fetchById(Long isbn) {
        String sql = "SELECT * FROM Book2 WHERE issn=? AND is_deleted=0";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getLong("ISBN"),
                    rs.getInt("year"),
                    rs.getBoolean("available")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Book> fetchAll() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book2 WHERE is_deleted=0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	
                String title = rs.getString("title");
                String author = rs.getString("author");
                long isbn = rs.getLong("ISBN");
                int year = rs.getInt("year");
                boolean available = rs.getBoolean("available");
                
                Book book = new Book(title, author, isbn, year, available);
                books.add(book);
             }               
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
    @Override
    public boolean authenticate(Long isbn) {
        String sql = "SELECT isbn FROM book2 WHERE isbn=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("isbn")==isbn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

}


