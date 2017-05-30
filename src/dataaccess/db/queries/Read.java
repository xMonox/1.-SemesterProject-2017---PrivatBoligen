package dataaccess.db.queries;

import dataaccess.db.DBConnection;
import dataaccess.dto.FileDTO;
import logic.entities.Customer;
import logic.entities.Invoice;
import logic.entities.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Read {
    private DBConnection dbcon;
    public Read(DBConnection dbcon) {
        this.dbcon = dbcon;
    }


    /*USER RELATED*/
    //Bertram
    public User fetchUser(String username, String password) {
        User user = null;
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM privatboligen.users WHERE user_username = ? AND user_password = ?";
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return user;
    }

    //Bertram, Julie
    public User getUserById(int userId) {
        User user = null;
        Connection con = dbcon.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM privatboligen.users WHERE user_userid = " + userId;
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return user;
    }

    //Julie
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        Connection con = dbcon.getConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT * FROM privatboligen.users;";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return users;
    }
    //Bertram, Julie
    public List<User> searchUser(String searchString){
        List<User> userList = new LinkedList<>();
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        ResultSet rs;
        searchString = "%" + searchString + "%";
        String sql = "SELECT * FROM privatboligen.users " +
                "WHERE user_username LIKE ? "+
                "OR user_role LIKE ? ";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,searchString);
            pstmt.setString(2,searchString);
            rs = pstmt.executeQuery();
            while(rs.next()){
                User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return userList;
    }


    /*INVOICE RELATED*/

    //Bertram, Svend
    public List<Invoice> getUserInvoices(int userId) {
        List<Invoice> invoices = new LinkedList<>();
        Connection con = dbcon.getConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT invoice_invoicenumber, customer_customername, invoice_customercvr, invoice_invoicedate, invoice_paymentdate, invoice_userid, invoice_fileid, file_filename\n" +
                "FROM invoices\n" +
                "LEFT JOIN files\n"+
                "on files.file_fileid = invoice_fileid\n" +
                "LEFT JOIN customers\n"+
                "on customers.customer_customercvr = invoices.invoice_customercvr\n" +
                "WHERE invoice_userid = " + userId + " ORDER BY invoice_paymentdate DESC";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Invoice invoice = new Invoice(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8));
                invoices.add(invoice);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return invoices;
    }

    //Bertram
    public List<Invoice> getUserInvoicesBetweenDates(int userId, String dateFrom, String dateTo) {
        List<Invoice> invoices = new LinkedList<>();
        Connection con = dbcon.getConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT invoice_invoicenumber, customer_customername, invoice_customercvr, invoice_invoicedate, invoice_paymentdate, invoice_userid, invoice_fileid, file_filename\n" +
                "FROM invoices\n" +
                "LEFT JOIN files\n"+
                "on files.file_fileid = invoice_fileid\n" +
                "LEFT JOIN customers\n"+
                "on customers.customer_customercvr = invoices.invoice_customercvr\n" +
                "WHERE invoice_userid = " + userId + " AND invoice_paymentdate >= '" + dateFrom +  "' AND invoice_paymentdate <= '" + dateTo + "\'";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Invoice invoice = new Invoice(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8));
                invoices.add(invoice);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return invoices;
    }

    //Svend, Julie, Bertram
    public List<Invoice> searchInvoice(String searchString){
        List<Invoice> invoiceList = new LinkedList<>();
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        ResultSet rs;
        searchString = "%" + searchString + "%";

        String sql = "SELECT * FROM privatboligen.invoices " +
                "LEFT JOIN files " +
                "ON files.file_fileid = invoice_fileid " +
                "LEFT JOIN customers " +
                "ON customers.customer_customercvr = invoices.invoice_customercvr " +
                "WHERE invoice_invoicenumber LIKE ? "+
                "OR invoice_customercvr LIKE ? " +
                "OR invoice_userid LIKE ? " +
                "OR file_filename LIKE ?";

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,searchString);
            pstmt.setString(2,searchString);
            pstmt.setString(3,searchString);
            pstmt.setString(4,searchString);

            rs = pstmt.executeQuery();

            while(rs.next()){
                Invoice invoice = new Invoice(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8));
                invoiceList.add(invoice);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return invoiceList;
    }


    /*FILE STUFF*/
    //Jesper
    public FileDTO getFile(int fileId) {
        Connection conn = dbcon.getConnection();
        String sql = "SELECT * FROM privatboligen.files WHERE file_fileid = " + fileId;

        Blob blob = null;
        FileDTO fileDTO = null;
        try {
            Statement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                fileDTO = new FileDTO(resultSet.getInt(1), resultSet.getString(2), resultSet.getBlob(3));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return fileDTO;
    }


    /*CUSTOMER RELATED*/
    //Julie
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new LinkedList<>();
        Connection con = dbcon.getConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT * FROM privatboligen.customers INNER JOIN zipcodes ON customer_zipcode = zipcode_zip";
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(9), rs.getString(5), rs.getInt(6), rs.getString(7));
                customers.add(customer);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return customers;
    }

    //Jesper
    public  List<Customer> getCustomerAndCvr() {
        Connection con = dbcon.getConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT customer_customercvr, customer_customername FROM privatboligen.customers";
        List<Customer> list = new LinkedList<Customer>();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Customer customer = new Customer(rs.getInt(1), rs.getString(2));
                list.add(customer);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return list;
    }

    //Bertram, Julie
    public List<Customer> searchCustomer(String searchString){
        List<Customer> customerList = new LinkedList<>();
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        ResultSet rs;
        searchString = "%" + searchString + "%";
        String sql = "SELECT * FROM privatboligen.customers " +
                "JOIN zipcodes " +
                "ON customer_zipcode = zipcode_zip " +
                "WHERE customer_customercvr LIKE ? "+
                "OR customer_customername LIKE ? " +
                "OR customer_address LIKE ? " +
                "OR customer_zipcode LIKE ? " +
                "OR zipcode_city LIKE ? " +
                "OR customer_contactperson LIKE ? " +
                "OR customer_telephonenumber LIKE ? " +
                "OR customer_email LIKE ?";

        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,searchString);
            pstmt.setString(2,searchString);
            pstmt.setString(3,searchString);
            pstmt.setString(4,searchString);
            pstmt.setString(5,searchString);
            pstmt.setString(6,searchString);
            pstmt.setString(7,searchString);
            pstmt.setString(8,searchString);

            rs = pstmt.executeQuery();

            while(rs.next()){
                Customer customer = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(9), rs.getString(5), rs.getInt(6), rs.getString(7));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return customerList;
    }


    /*ZIPCODE RELATED*/
    //Julie
    public String getCityFromZipcode(int zipcode) {
        String city = "";
        Connection con = dbcon.getConnection();
        Statement stmt;
        ResultSet rs;
        String sql = "SELECT zipcode_city FROM privatboligen.zipcodes WHERE zipcode_zip = " + zipcode;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                city = rs.getString(1);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return city;
    }
}
