package project;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

public class BankAccount {

    private Database db;
    private String username;
    private String filename;
    private String transaction_date;
    private double money_amount;
    private double money2_amount;
    private String withdraw_from;
    private String withdraw_to;
    private String deposit_from;
    private String deposit_to;
    private Locale alocale = new Locale("el", "GR");
    private NumberFormat nf2 = NumberFormat.getCurrencyInstance(alocale);

    public void setDeposit_to(String deposit_to) {
        this.deposit_to = deposit_to;
    }
    private double trans_money;
    private FileMake fm;
    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFm(FileMake fm) {
        this.fm = fm;
    }

    public String get_Filename() {
        return filename;
    }

    public void set_Filename() {
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        today = today.replaceAll("/", "_");

        if (this.username.equals("admin")) {
            this.filename = "statement_" + this.username + "_" + today + ".txt";
        } else {

            String us = this.username.substring(4);
            this.filename = "statement_user_" + us + "_" + today + ".txt";
        }
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public void myAccountView() { //view my account
        double amount = db.viewAccount(this.username);
        System.out.println(this.username + " have " + nf2.format(amount) + "");
    }

    public void depositSimple() { // Deposit simple member
        Scanner scan = new Scanner(System.in);
        System.out.println("How much money would you like to deposit");
        this.deposit_from = this.username;
        do {
            try {
                String k = scan.next();
                double an = Double.parseDouble(k);
                an = Math.round(an * 1e2) / 1e2;
                this.trans_money = an;
                break;
            } catch (Exception e) {
                System.out.println("Dwse poso");
            }
        } while (true);
        if (this.trans_money != 0) {
            this.money_amount = db.viewAccount(this.deposit_from);
            double amount2 = db.viewAccount(this.deposit_to);
            if (this.money_amount == 0) {
                System.out.println("You have zero amount!");
            }
            if (this.money_amount >= this.trans_money) {
                this.money_amount = this.money_amount - this.trans_money;
                this.money2_amount = amount2 + this.trans_money;
                db.update(this.money_amount, this.deposit_from);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String strDate = df.format(cal.getTime());
                this.transaction_date = strDate;
                db.update(this.money2_amount, this.deposit_to);
                fm.print();
            } else {
                System.out.println("The money you have is insufficient");
            }
        }
    }

    public void withdrawSimple() {
        Scanner scan = new Scanner(System.in);
        System.out.println("How much money would you like to withdraw");

        do {
            try {
                String k = scan.next();
                double an = Double.parseDouble(k);
                an = Math.round(an * 1e2) / 1e2;
                this.trans_money = an;
                break;
            } catch (Exception e) {
                System.out.println("Dwse poso");
            }
        } while (true);
        if (this.trans_money != 0) {
            this.money_amount = db.viewAccount(this.withdraw_from);
            double amount2 = db.viewAccount(this.withdraw_to);
            if (this.money_amount == 0) {
                System.out.println("You have zero amount!");
            }
            if (this.money_amount >= this.trans_money) {
                this.money_amount = this.money_amount - this.trans_money;
                this.money2_amount = amount2 + this.trans_money;
                db.update(this.money_amount, this.withdraw_from);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String strDate = df.format(cal.getTime());
                this.transaction_date = strDate;
                db.update(this.money2_amount, this.withdraw_to);
                fm.print();
            } else {
                System.out.println("The money he have is insufficient");
            }
        }
    }

    public void mbankAccounts() { //view members bank account
        ArrayList<String> accounts = db.getAccounts();
        Iterator itr = accounts.iterator();
        while (itr.hasNext()) {
            Object element1 = itr.next();
            Object element2 = itr.next();
            System.out.println(element1 + " have " + nf2.format(Double.parseDouble(element2.toString())) + "");
        }
    }

    public void depositA_Users() {
        ArrayList<String> users = db.getUsers(this.username);
        this.deposit_from = this.username;
        Iterator itr = users.iterator();
        while (itr.hasNext()) {
            Object element = itr.next();
            this.deposit_to = (String) element;
            System.out.println(this.deposit_to);
            depositSimple();
        }
    }

    public void withdrawA_Users() {
        ArrayList<String> users = db.getUsers(this.username);
        this.withdraw_to = this.username;
        Iterator itr = users.iterator();
        while (itr.hasNext()) {
            Object element = itr.next();
            this.withdraw_from = (String) element;
            System.out.println(this.withdraw_from);
            withdrawSimple();
        }
    }

    public String showUsers() {

        ArrayList<String> users = db.getUsers(this.username);
        Iterator itr = users.iterator();
        System.out.println("Choose user");
        while (itr.hasNext()) {
            Object element = itr.next();
            System.out.print(element + "\n");
        }
        System.out.println("Give user username");
        Scanner scan = new Scanner(System.in);
        while (true) {
            String user = scan.next();
            boolean retval = users.contains(user);
            if (retval == true) {
                return user;
            } else {
                System.out.println("This user not exists. Give username");
            }
        }
    }

    @Override
    public String toString() {

        if (this.flag == 1) {
            return "Username: " + this.username + " deposit to " + this.deposit_to + " " + nf2.format(Math.round(this.trans_money * 1e2) / 1e2)
                    + " Transaction date: " + this.transaction_date + " First's amount: " + nf2.format(Math.round(this.money_amount * 1e2) / 1e2) + " Second's amount: " + nf2.format(Math.round(this.money2_amount * 1e2) / 1e2) + "";
        } else {
            return "Username: " + this.username + " withdraw from " + this.withdraw_from + " " + nf2.format(Math.round(this.trans_money * 1e2) / 1e2)
                    + " Transaction date: " + this.transaction_date + " First's amount: " + nf2.format(Math.round(this.money2_amount * 1e2) / 1e2) + " Second's amount: " + nf2.format(Math.round(this.money_amount * 1e2) / 1e2) + "";
        }

    }
}
