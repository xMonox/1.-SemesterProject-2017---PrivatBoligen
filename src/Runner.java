import logic.Controller;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by lupus on 5/6/17.
 */
public class Runner {
    //Alle har lavet Unit test her.
    public static void main(String[] args)throws Exception {
        Controller controller = login();
        //Controller controller1 = new Controller();
        //User user = new User(1, "bib", "bob", 0);
       // controller.createObservableInvoice(888828888, 123123, "2017-01-01", "2017-01-01", controller.getActiveUser().getId(), "/home/lupus/distcc_notes.txt");
        //createUser(controller1);
        //createInvoice(controller);
        //getAllInvoices(controller);
        //createCustomer(controller);
        /*System.out.println("Username: " + controller.getActiveUser().getUsername());
        System.out.println(controller.getActiveUser().getPassword());*/
        //testFileDownload(controller);

    }
    public static Controller login() {
        Controller controller = new Controller();
        Scanner input = new Scanner(System.in);
        String username;
        String password;
        System.out.println("Velkommen");
        System.out.print("Brugernavn: ");
        username = input.next();
        System.out.print("Kode: ");
        password = input.next();
        controller.loginUser(username, password);
        return controller;
    }
    public static void createInvoice(Controller controller) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Indtast faktura nummer");
        int invoiceNumber = input.nextInt();
        System.out.println("Opret faktura valgt.");
        System.out.print("Modtagelsesdato: ");
        String invoiceDate = input.nextLine();
        System.out.print("Forfaldsdato: ");
        String paymentdate = input.nextLine();
        System.out.print("Fil: ");
        String filepath = input.nextLine();

        System.out.println(invoiceNumber + "\n" + invoiceDate + "\n" + paymentdate + "\n" + filepath);
       // controller.createInvoice(invoiceNumber, 2, invoiceDate, paymentdate, controller.getActiveUser().getId(), filepath);


    }

  /*  public static void getAllInvoices(Controller controller){
        List<Invoice> invoices = controller.getUserObservableInvoices(controller.getActiveUser().getId());
        for(Invoice i : invoices){
            System.out.println(i.getCustomerId());
        }

    }*/
    /*
    public static void createCustomer(Controller controller) {
       // Customer customer = controller.createCustomer(12345678, "Johns ShawarmaHytte", "Nedergade 666", 2000, "John Halal", 88888888, "johnersej@gmail.com");
    }
*/


    public static void createUser(Controller controller){
        Scanner input = new Scanner(System.in);
        System.out.println("Ny bruger");
        System.out.print("Brugernavn: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.println("Administrator rettigheder? j/n");
        String role = "Bruger";
        if(input.nextLine().equalsIgnoreCase("j")){
            role = "Administrator";
        }
        controller.createUser(username, password, role);

    }

    /*public static void testFileDownload(Controller controller) {

        FileService fileService = new FileService();
        DBConnection dbConnection = new DBConnection("jdbc:mysql://91.121.90.114/privatboligen", "lupus", "Nbwza6Ei2fqOgQjU8chw");
        Queries query = new Queries(dbConnection);
        FileDTO fileDTO = query.getFile(10);
        fileService.assembleFile(fileDTO);
    }*/
}
