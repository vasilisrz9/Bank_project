package project;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {

    private int fcl;

    public void MenuView(int i, String username, BankAccount bacc, FileMake fm) {
        Scanner in = new Scanner(System.in);
        boolean exit = false;
        String menuChoice;
        if (i == 1) {
            bacc.setUsername(username);
            bacc.set_Filename();//Admin menu
            fm.setFilename();
            try {
                fm.openFile();
            } catch (IOException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Menu " + username);
            System.out.println("1. View Cooperative internal bank account");
            System.out.println("2. View members bank accounts");
            System.out.println("3. Deposit to member bank account");
            System.out.println("4. Withdraw from member bank account");
            System.out.println("5. Send today's transactions");
            System.out.println("6. Exit the application");
            do {
                System.out.println("Make your choice:");
                menuChoice = in.next();
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Menu " + username);
                System.out.println("1. View Cooperative internal bank account");
                System.out.println("2. View members bank accounts");
                System.out.println("3. Deposit to member bank account");
                System.out.println("4. Withdraw from member bank account");
                System.out.println("5. Send today's transactions");
                System.out.println("6. Exit the application");
                switch (menuChoice) {

                    case "1":
                        bacc.myAccountView();
                        break;
                    case "2":
                        bacc.mbankAccounts();
                        break;
                    case "3":
                        if (fcl == 1) {
                            try {
                                fm.openFile();
                                fcl = 0;
                            } catch (IOException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        bacc.setFlag(1);
                        bacc.depositA_Users();
                        break;
                    case "4":
                        if (fcl == 1) {
                            try {
                                fm.openFile();
                                fcl = 0;
                            } catch (IOException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        bacc.setFlag(2);
                        bacc.withdrawA_Users();
                        break;
                    case "5":
                        try {
                            fm.closeFile();
                            fcl = 1;
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "6":
                        exit = true;
                        try {
                            fm.closeFile();
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

            } while (!exit);
        } else if (i == 2) {
            //User menu
            bacc.setUsername(username);
            bacc.set_Filename();
            fm.setFilename();
            try {
                fm.openFile();
            } catch (IOException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Menu " + username);
            System.out.println("1. View your bank account");
            System.out.println("2. Deposit to Cooperative internal bank account");
            System.out.println("3. Deposit to another member bank account");
            System.out.println("4. Send today's transactions");
            System.out.println("5. Exit the application");

            do {
                System.out.println("Make your choice:");
                menuChoice = in.next();
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Menu " + username);
                System.out.println("1. View your bank account");
                System.out.println("2. Deposit to Cooperative internal bank account");
                System.out.println("3. Deposit to another member bank account");
                System.out.println("4. Send today's transactions");
                System.out.println("5. Exit the application");

                switch (menuChoice) {
                    case "1":
                        bacc.myAccountView();
                        break;
                    case "2":
                        if (fcl == 1) {
                            try {
                                fm.openFile();
                                fcl = 0;
                            } catch (IOException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        bacc.setFlag(1);
                        bacc.setDeposit_to("admin");
                        bacc.depositSimple();
                        break;
                    case "3":

                        if (fcl == 1) {
                            try {
                                fm.openFile();
                                fcl = 0;
                            } catch (IOException ex) {
                                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        bacc.setFlag(1);
                        bacc.setDeposit_to(bacc.showUsers());
                        bacc.depositSimple();
                        break;
                    case "4":
                        try {
                            fm.closeFile();
                            fcl = 1;
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "5":

                        try {
                            fm.closeFile();
                        } catch (IOException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            } while (!exit);
        }
    }

}
