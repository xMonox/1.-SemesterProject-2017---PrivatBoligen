package dataaccess.db.queries;

import dataaccess.db.DBConnection;
import logic.entities.Customer;
import logic.entities.Invoice;
import logic.entities.User;

import java.sql.*;


public class Write {
    private DBConnection dbcon;
    public Write(DBConnection dbcon) {
        this.dbcon = dbcon;
    }


    /*USER RELATED*/
    //Bertram
    public User createUser(User user) {
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        String sql = "INSERT INTO privatboligen.users VALUES (DEFAULT, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return user;
    }
    //Julie, Bertram
    public void updateUserPassword(int userId, String password) {
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        String sql = "UPDATE privatboligen.users SET user_password = ? WHERE user_userid = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    /*INVOICE RELATED*/
    //Bertram
    public Invoice createInvoice(Invoice invoice) {
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        ResultSet rs;
        //transaction
        String sql = "INSERT INTO privatboligen.files VALUES (DEFAULT, ?, ?)";
        try {
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, invoice.getFileName());
            pstmt.setBlob(2, invoice.getInputStream());
            int row = pstmt.executeUpdate();
            if (row > 0) {
                con.commit();
                sql = "INSERT INTO privatboligen.invoices VALUES (?, ?, ?, ?, LAST_INSERT_ID(), ?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, invoice.getInvoiceNumber());
                pstmt.setInt(2, invoice.getCustomerCvr());
                pstmt.setString(3, invoice.getInvoiceDate());
                pstmt.setString(4, invoice.getPaymentDate());
                pstmt.setInt(5, invoice.getUserId());
                row = pstmt.executeUpdate();
                if (row > 0) {
                    con.commit();
                } else {
                    con.rollback();
                }
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return invoice;
    }

    //Svend
    public void deleteInvoiceById (int invoiceNumber) {
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        String sql = "DELETE FROM privatboligen.invoices WHERE invoice_invoicenumber = " + invoiceNumber;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    //Bertram, Svend
    public Invoice updateInvoice(int tempInvoiceNumber, boolean hasInputStream, Invoice invoice){
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;

        String sql;
        if (hasInputStream) {
            sql = "UPDATE invoices inv, files fil SET invoice_invoicenumber = ?, invoice_customercvr = ?, invoice_invoicedate = ?, invoice_paymentdate = ?, file_filename = ?, file_file = ? WHERE invoice_invoicenumber = ? AND invoice_fileid = file_fileid";
        }
        else {
            sql = "UPDATE invoices inv, files fil SET invoice_invoicenumber = ?, invoice_customercvr = ?, invoice_invoicedate = ?, invoice_paymentdate = ?, file_filename = ? WHERE invoice_invoicenumber = ? AND invoice_fileid = file_fileid";
        }
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, invoice.getInvoiceNumber());
            pstmt.setInt(2, invoice.getCustomerCvr());
            pstmt.setString(3, invoice.getInvoiceDate());
            pstmt.setString(4, invoice.getPaymentDate());
            pstmt.setString(5, invoice.getFileName());
            pstmt.setInt(6, tempInvoiceNumber);
            if (hasInputStream) {
                pstmt.setBlob(6, invoice.getInputStream());
                pstmt.setInt(7, tempInvoiceNumber);
            }
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return invoice;
    }


    /*CUSTOMER RELATED*/
    //Julie
    public Customer createCustomer(Customer customer) {
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        String sql = "INSERT INTO privatboligen.customers VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, customer.getCvr());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.getZipcode());
            pstmt.setString(5, customer.getContact());
            pstmt.setInt(6, customer.getPhoneNumber());
            pstmt.setString(7, customer.getEmail());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return customer;
    }

    //Julie, Bertram
    public void deleteCustomerByCvr (int cvr){
        Connection con = dbcon.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "DELETE FROM privatboligen.files\n" +
                "WHERE file_fileid IN(SELECT invoice_fileid FROM privatboligen.invoices WHERE invoice_customercvr = " + cvr + ")";
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.commit();
            sql = "DELETE FROM privatboligen.customers WHERE customer_customercvr = " + cvr;
            stmt = con.createStatement();
            int row = stmt.executeUpdate(sql);
            if (row > 0) {
                con.commit();
            } else {
                con.rollback();
            }

        }catch(SQLException se){
            se.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
        } finally {
            try{
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException se2){
                se2.printStackTrace();
            }
        }
    }

    //Julie, Bertram
    public Customer updateCustomer(int cvr, Customer customer){
        Connection con = dbcon.getConnection();
        PreparedStatement pstmt;
        String sql = "UPDATE privatboligen.customers SET customer_customercvr = ?, customer_customername = ?, customer_address = ?, customer_zipcode = ?, customer_contactperson = ?, customer_telephonenumber = ?, customer_email = ? WHERE customer_customercvr = ?";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, customer.getCvr());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.getZipcode());
            pstmt.setString(5, customer.getContact());
            pstmt.setInt(6, customer.getPhoneNumber());
            pstmt.setString(7, customer.getEmail());
            pstmt.setInt(8, cvr);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return customer;
    }
}
