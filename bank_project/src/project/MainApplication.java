package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {

    private static final String DBUSER = "root";
    private static final String DBPASS = "user";
    private static final String DBURL = "jdbc:mysql://localhost:3306/project";

    public static void main(String[] args) throws SQLException {
        Menu menu = new Menu();
        Database db = new Database();
        FileMake file = new FileMake();
        /*Dhmiourgia twn class Variables*/
        Login login = new Login();
        BankAccount bacc = new BankAccount();
        File newfile;
        //PreparedStatement statement = null;
        Connector keep = Connector.getInstance(DBUSER, DBPASS, DBURL);	//ekkinhsh tou connector
        if (Connector.getConnection() == null) {
            System.err.println("Can't connect to database");
        }
        newfile = new File("C:\\Users\\RAZER9\\Documents\\NetBeansProjects\\Project\\afdemp_java_1.sql");
        String sql = null;
        try {
            sql = new Scanner(newfile).useDelimiter("\\Z").next();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (db.executeSQL(sql)) {//dhmiourgia ths bashs sumfona me to script file dhladh to sql file
            System.out.println("Database ready");
        } else {
            System.out.println("Database can't created");
            return;
        }
        if (db.checkTable() == false) {
            db.changePassword();
        }

        bacc.setDb(db);
        bacc.setFm(file);
        file.setBc(bacc);

        int ct = 0;
        do {
            int n = login.LoginScreen(db);
            if (n == 1) {

                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                menu.MenuView(1, login.getUsername(), bacc, file);
                break;
            } else if (n == 2) {

                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                menu.MenuView(2, login.getUsername(), bacc, file);
                break;
            } else if (n == 5) {
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                ct++;
                if (ct == 2) {
                    System.out.println("You have one more chance");
                }
                if (ct == 3) {
                    System.out.println("Locked out!!!");
                }

            }
        } while (ct < 3);

        Connector.getInstance().closeConnection();
        /*Kleisimo ths vashs*/

    }
}
