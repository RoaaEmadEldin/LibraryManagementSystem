import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        // Add here the MySQL Server's URL that specifies its location, port number and shcema name.
        String url = "";
        // Add here the MySQL Server's Username.
        String username = "";
        // Add here the MySQL Server's Password.
        String password = "";


        // 1st insert statement
        String query1 = """
                INSERT INTO Author (Author_ID, Name)
                VALUES (?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             // PreparedStatement created using SQl query
             PreparedStatement statement = connection.prepareStatement(query1))
        {
            // setting values to the 1st statement
            statement.setInt(1, 14);
            statement.setString(2, "Ahmed Khaled Tawfik");

            // Execute the 1st INSERT statement
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " record(s) inserted into Author.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2nd insert statement
        String query2 = """
                INSERT INTO Book (ISBN, Publisher_Name, Title, Publication_Year, Admin_ID, Author_ID)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             // PreparedStatement created using SQl query
             PreparedStatement statement = connection.prepareStatement(query2))
        {
            // insert ISPN
            statement.setInt(1, 344566);

            // insert Publisher_Name
            statement.setString(2, "AL-Shrouk");

            // insert Title
            statement.setString(3, "The Enchanted Garden");

            // insert Publication_Year
            statement.setInt(4, 1976);

            // insert Admin_ID
            statement.setInt(5, 1);

            // insert Author_ID
            statement.setInt(6, 8);

            // Execute the 2nd INSERT statement
           int rowsAffected = statement.executeUpdate();
           System.out.println(rowsAffected + " record(s) inserted into Author.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // 1st Delete statement
        String query3 = """
                DELETE FROM student
                WHERE Student_ID IN (
                \tSELECT Borrower_ID
                \tFROM loan
                \tWHERE Book_ID = (
                \t\tSELECT ISBN
                \t\tFROM book
                \t\tWHERE Title = 'The Great Gatsby'
                \t)
                );""";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             // PreparedStatement created using SQl query
             PreparedStatement statement = connection.prepareStatement(query3))
        {
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " record(s) deleted successfully.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // 2nd Delete Statement
        System.out.println("\nDelete all loan records where the corresponding books have been published after the year 2000");
        String query4 = """
                DELETE FROM Loan
                WHERE Book_ID IN (
                  SELECT ISBN
                  FROM Book
                  WHERE Publication_Year > 2000
                );""";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query4))
        {
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " record(s) deleted successfully.");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // 1st update statement
        String query5 = """
                UPDATE Borrower
                SET Phone = ?
                WHERE Borrower_ID = ?
                     
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query5))
        {
            // Borrower_ID
            statement.setInt(1, 22344665);
            // Phone
            statement.setInt(2, 4);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " record(s) updated successfully.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // 2nd update statement
        String query6 = """
                UPDATE Publisher
                SET Address = ? ,
                    Phone = ? 
                WHERE Name = ?
                     
                """;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query6))
        {
            // Name
            statement.setString(1, "123 Street 8");
            // Address
            statement.setInt(2, 5554667);
            // phone Penguin Random House
            statement.setString(3, "Penguin Random House");

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " record(s) updated successfully.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Select data from Author table
        System.out.println("This Query is to retrieve data from Author table");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement())
        {
            String query7 = """
                    SELECT Author_ID, Name
                    FROM Author
                    """;

            // Execute
            ResultSet resultSet = statement.executeQuery(query7);

            while (resultSet.next())
            {
                // Access the data from the result set
                int authorId = resultSet.getInt("Author_id");
                String authorName = resultSet.getString("Name");

                // Print the retrieved data
                System.out.println("Author ID: " + authorId);
                System.out.println("Name: " + authorName);
                System.out.println("----------------------");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("\n\n");

        // Select data from Borrower table
        System.out.println("This Query is to retrieve data from Borrower table");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement())
        {
            String query8 = """
                    SELECT Borrower_ID, phone
                    FROM Borrower
                    """;
            ResultSet resultSet = statement.executeQuery(query8);
            while (resultSet.next())
            {
                int borrowerId = resultSet.getInt("Borrower_ID");
                String borrowerPhone = resultSet.getString("Phone");

                System.out.println("Borrower_ID: " + borrowerId);
                System.out.println("Phone : " + borrowerPhone);
                System.out.println("----------------------");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("\n\n");

        // Select data that involves more than one table of the database
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement())
        {
            String query9 = """
                    SELECT Book.Title , Book.Publication_Year , Author.Name, Publisher.Address
                    FROM Book JOIN Author 
                    ON Book.Author_ID = Author.Author_ID
                    JOIN Publisher ON Book.Publisher_Name = Publisher.Name;
                    """;

            // Execute query
            ResultSet resultSet = statement.executeQuery(query9);

            while (resultSet.next())
            {
                String bookTitle = resultSet.getString("Title");
                String bookPublicationYear = resultSet.getString("Publication_Year");
                String authorName = resultSet.getString("Name");
                String publisherAddress = resultSet.getString("Address");

                System.out.println("Book Title: " + bookTitle);
                System.out.println("Author Name: " + authorName);
                System.out.println("Publisher Address: " + publisherAddress);
                System.out.println("Publication Year: " + bookPublicationYear);
                System.out.println("----------------------");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("\n\n");
    }
}
