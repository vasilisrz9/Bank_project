package project;

import java.io.Console;
import java.security.MessageDigest;
import java.util.Scanner;

public class Login {

    private String username;
    private String password;
    private Scanner stdin;
    private Console cns;

    public int LoginScreen(Database db) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Give your username");
        this.username = stdin.nextLine();
        System.out.println("Give your password");
        cns = System.console();
        char[] passString = cns.readPassword();

        if (db.checkUser(this.username, sha256(new String(passString))) == 1) {
            return 1;
        } else if (db.checkUser(this.username, sha256(new String(passString))) == 2) {
            return 2;
        } else if ((db.checkUser(this.username, sha256(new String(passString))) == 5)) {
            return 5;
        } else {
            System.out.println("Problem with checkuser");
            return 3;
        }
    }

    private static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getUsername() {
        return username;
    }

}
