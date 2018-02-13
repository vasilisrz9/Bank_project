package project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    public void changePassword() {
        PreparedStatement alt1 = null;
        PreparedStatement select2 = null;
        PreparedStatement update3 = null;
        ResultSet sel2 = null;
        String sql1 = "alter table afdemp_java_1.users modify password char(64) not null";
        String sql2 = "select id,password from afdemp_java_1.users";
        String sql3 = "update afdemp_java_1.users set password = sha2(?,256) "
                + " where id = ?";
        try {
            alt1 = Connector.getConnection().prepareStatement(sql1);
            alt1.executeUpdate();
            alt1.close();

            select2 = Connector.getConnection().prepareStatement(sql2);
            sel2 = select2.executeQuery();

            while (sel2.next()) {
                update3 = Connector.getConnection().prepareStatement(sql3);
                update3.setString(1, sel2.getString(2));
                update3.setInt(2, sel2.getInt(1));
                update3.addBatch();
                update3.executeBatch();
            }

            update3.close();
            sel2.close();
            select2.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (alt1 != null) {
                    alt1.close();
                }
                if (select2 != null) {
                    select2.close();
                }
                if (update3 != null) {
                    update3.close();
                }
                if (sel2 != null) {
                    sel2.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int checkUser(String username, String password) {

        PreparedStatement statement1 = null;
        ResultSet set = null;
        String sql1 = "select username, password from afdemp_java_1.users where BINARY username=? and password=?";
        try {
            int counter = 0;
            statement1 = Connector.getConnection().prepareStatement(sql1);
            statement1.setString(1, username);
            statement1.setString(2, password);
            set = statement1.executeQuery();
            while (set.next()) {
                counter++;
            }
            if (counter > 0) {
                if (username.equals("admin")) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                return 5;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement1 != null) {
                    statement1.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                return 3;
            }
        }
        return 3;
    }

    public boolean checkTable() {

        PreparedStatement statement1 = null;
        ResultSet set = null;
        String sql1 = "select data_type from information_schema.columns where table_name='users' and column_name='password'";
        try {

            statement1 = Connector.getConnection().prepareStatement(sql1);
            set = statement1.executeQuery();
            set.first();
            if (set.getString(1).equals("char")) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement1 != null) {
                    statement1.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                return true;
            }
        }
        return true;
    }

    public boolean executeSQL(String sql) { //ektelei ta queries gia th creation ths vashs
        Statement statement = null;
        PreparedStatement statement0 = null;
        ResultSet set = null;
        boolean result = true;
        try {
            statement0 = Connector.getConnection().prepareStatement("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'afdemp_java_1'");
            set = statement0.executeQuery();
            if (!set.first()) {
                statement = Connector.getConnection().createStatement();
                String[] inst = sql.toString().split(";");
                for (int i = 0; i < inst.length; i++) {
                    if (!inst[i].trim().equals("")) {
                        statement.executeUpdate(inst[i]);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error at ExecuteSQL");
            result = false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (statement0 != null) {
                    statement0.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        }
    }

    public ArrayList<String> getUsers(String username) {
        ArrayList<String> usernames = new ArrayList<String>();
        PreparedStatement statement1 = null;
        ResultSet set = null;
        String sql1 = "select username from afdemp_java_1.users";
        try {
            int counter = 0;
            statement1 = Connector.getConnection().prepareStatement(sql1);
            set = statement1.executeQuery();
            while (set.next()) {

                if (!set.getString(1).equals("admin") && !set.getString(1).equals(username)) {
                    usernames.add(set.getString(1));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement1 != null) {
                    statement1.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usernames;
    }

    public ArrayList<String> getAccounts() {
        ArrayList<String> accounts = new ArrayList<String>();
        PreparedStatement statement1 = null;
        ResultSet set = null;
        String sql1 = "select username,truncate(amount,2) from afdemp_java_1.users inner join afdemp_java_1.accounts on users.id=accounts.user_id";
        try {

            statement1 = Connector.getConnection().prepareStatement(sql1);
            set = statement1.executeQuery();
            while (set.next()) {
                if (!set.getString(1).equals("admin")) {
                    accounts.add(set.getString(1));
                    String am = String.valueOf(set.getDouble(2));
                    accounts.add(am);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement1 != null) {
                    statement1.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return accounts;
    }

    public double viewAccount(String username) {
        PreparedStatement statement1 = null;
        ResultSet set = null;
        String sql1 = "select truncate(amount,2) from afdemp_java_1.accounts where user_id = (select id from afdemp_java_1.users where username=?)";
        try {
            statement1 = Connector.getConnection().prepareStatement(sql1);
            statement1.setString(1, username);
            set = statement1.executeQuery();
            if (set.first()) {
                return set.getDouble(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement1 != null) {
                    statement1.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;
    }

    public void update(double money, String username) {

        PreparedStatement statement1 = null;
        ResultSet set = null;
        String sql1 = "update afdemp_java_1.accounts set amount=truncate(?,2),transaction_date=? where user_id=(select id from afdemp_java_1.users where username=?)";
        try {
            statement1 = Connector.getConnection().prepareStatement(sql1);
            statement1.setDouble(1, money);
            statement1.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement1.setString(3, username);
            statement1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (statement1 != null) {
                    statement1.close();
                }
                if (set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
