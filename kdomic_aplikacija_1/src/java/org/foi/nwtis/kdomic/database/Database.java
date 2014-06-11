package org.foi.nwtis.kdomic.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.kdomic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.kdomic.data.Location;
import org.foi.nwtis.kdomic.data.Logs;
import org.foi.nwtis.kdomic.data.Users;
import org.foi.nwtis.kdomic.data.WeatherData;
import org.foi.nwtis.kdomic.data.WeatherDataSmall;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;

/**
 * Klasa za interakciju s bazom
 *
 * @author Krunoslav
 */
public class Database {

    public static String insert(String query) {
        String insertedId = "";
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database() + "?useUnicode=true&characterEncoding=utf-8", bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            int affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = String.valueOf(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
        }
        return insertedId;
    }

    public static ArrayList<Location> getAllAddress() {
        ArrayList<Location> list = new ArrayList<>();
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;

            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tbladdress");

            while (resultSet.next()) {
                list.add(new Location(resultSet.getString("latitude"), resultSet.getString("longitude"), resultSet.getString("address"), resultSet.getString("idAddress")));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static Location getAddressById(String id) {
        Location l = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tbladdress WHERE idAddress=" + id);
            if (resultSet.next()) {
                l = new Location(resultSet.getString("latitude"), resultSet.getString("longitude"), resultSet.getString("address"), resultSet.getString("idAddress"));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public static Location getAddressByName(String adresa) {
        Location l = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database() + "?useUnicode=true&characterEncoding=utf-8", bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tbladdress WHERE address='" + adresa + "'");
            if (resultSet.next()) {
                l = new Location(resultSet.getString("latitude"), resultSet.getString("longitude"), resultSet.getString("address"), resultSet.getString("idAddress"));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public static String getAdresaId(String adresa) {
        String id = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;

            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tbladdress WHERE address=\'" + adresa + "\'");
            if (resultSet.next()) {
                id = resultSet.getString("idAddress");
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static WeatherData getMeteo(String idAdrese) {
        WeatherData data = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;

            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM tblmeteodata WHERE adresa=" + idAdrese + " order by vrijemePreuzimanja desc";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                data = new WeatherData(resultSet);
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static ArrayList<WeatherData> getMeteoAll(String idAdrese) {
        ArrayList<WeatherData> list = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM tblmeteodata WHERE adresa=" + idAdrese + " order by vrijemePreuzimanja desc";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new WeatherData(resultSet));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static ArrayList<WeatherData> getMeteoAll(String idAdrese, Integer limitNum) {
        ArrayList<WeatherData> list = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM tblmeteodata WHERE adresa=" + idAdrese + " order by vrijemePreuzimanja desc LIMIT " + limitNum.toString();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new WeatherData(resultSet));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static ArrayList<WeatherData> getMeteoAll(String idAdrese, String startDate, String endDate) {
        ArrayList<WeatherData> list = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM tblmeteodata WHERE adresa=" + idAdrese + " AND (vrijemePreuzimanja BETWEEN '" + startDate + "' AND '" + endDate + "') ORDER BY vrijemePreuzimanja DESC";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new WeatherData(resultSet));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static ArrayList<WeatherDataSmall> getRecentGeoMeteo(String startDate, String endDate) {
        String sql = "";
        sql += "SELECT * FROM tbladdress AS a";
        sql += " JOIN tblmeteodata AS m ON m.adresa=a.idAddress";
        sql += " WHERE (vrijemePreuzimanja BETWEEN '" + startDate + "' AND '" + endDate + "')";
        sql += " AND NOT EXISTS(SELECT * FROM tblmeteodata AS m2 WHERE (vrijemePreuzimanja BETWEEN '" + startDate + "' AND '" + endDate + "') AND m2.adresa=a.idAddress AND m2.vrijemePreuzimanja>m.vrijemePreuzimanja)";
        sql += " ORDER BY a.idAddress ASC ";
        ArrayList<WeatherDataSmall> list = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new WeatherDataSmall(resultSet));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static String makeMeteoSqlWIthFilter(String page, String maxPerPage, Boolean dateCheck, String dateStart, String dateEnd, Boolean addressCheck, String addressId) {
        String sql = "SELECT * FROM tblmeteodata";
        if (addressCheck) {
            sql += " WHERE adresa=" + addressId;
        }
        if (dateCheck) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStart = sdf.format(new SimpleDateFormat("dd.MM.yyyy hh.mm.ss").parse(dateStart));
                dateEnd = sdf.format(new SimpleDateFormat("dd.MM.yyyy hh.mm.ss").parse(dateEnd));
            } catch (ParseException ex) {
                maxPerPage = "svi";
            }
            if (addressCheck) {
                sql += " AND (vrijemePreuzimanja BETWEEN '" + dateStart + "' AND '" + dateEnd + "')";
            } else {
                sql += " WHERE (vrijemePreuzimanja BETWEEN '" + dateStart + "' AND '" + dateEnd + "')";
            }
        }
        sql += " ORDER BY vrijemePreuzimanja DESC";
        if (!maxPerPage.equals("svi")) {
            Integer rowPerPage = Integer.parseInt(maxPerPage);
            Integer offset = (Integer.parseInt(page) - 1) * rowPerPage;
            sql += " LIMIT " + offset + "," + rowPerPage;
        }
        return sql;
    }

    public static ArrayList<WeatherData> getMeteoAllByFilter(String page, String maxPerPage, Boolean dateCheck, String dateStart, String dateEnd, Boolean addressCheck, String addressId) {
        String sql = Database.makeMeteoSqlWIthFilter(page, maxPerPage, dateCheck, dateStart, dateEnd, addressCheck, addressId);
        System.out.println(sql);
        ArrayList<WeatherData> list = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new WeatherData(resultSet));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static Integer countAllMeteoByFilter(String page, String maxPerPage, Boolean dateCheck, String dateStart, String dateEnd, Boolean addressCheck, String addressId) {
        String sql = Database.makeMeteoSqlWIthFilter(page, "svi", dateCheck, dateStart, dateEnd, addressCheck, addressId);
        sql = sql.replace("*", "count(*) AS num");
        System.out.println(sql);
        Integer num = 0;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                num = Integer.parseInt(resultSet.getString("num"));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

    public static String authenticateUser(String username, String password) {
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM tblPearsons WHERE username='" + username + "' AND password='" + password + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String insertAddress(String address, String latitude, String longitude, String user) {
        if (Database.getAddressByName(address) == null) {
            return insert("INSERT INTO tbladdress(address, latitude, longitude, user) VALUES ('" + address + "','" + latitude + "','" + longitude + "','" + user + "')");
        }
        return null;
    }

    public static String insertAddress(String address, String latitude, String longitude) {
        if (Database.getAddressByName(address) == null) {
            return insert("INSERT INTO tbladdress(address, latitude, longitude) VALUES ('" + address + "','" + latitude + "','" + longitude + "')");
        }
        return null;
    }

    public static String insertUser(String username, String password) {
        return insert("INSERT INTO tblpearsons(username, password) VALUES ('" + username + "','" + password + "')");
    }

    public static List<Location> getAddressListWithMostData(Integer limitNum) {
        List<Location> l = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT m.adresa, count(*) FROM tblmeteodata AS m GROUP BY m.adresa ORDER BY 2 LIMIT " + limitNum.toString();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (l == null) {
                    l = new ArrayList();
                }
                l.add(Database.getAddressById(resultSet.getString("adresa")));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public static String insertLog(String user, String command, String duration) {
        return insert("INSERT INTO tbllogs(user, action, duration, datetime) VALUES ('" + user + "','" + command + "','" + duration + "',NOW())");
    }

    public static String insertLogFilter(String user, String command, String duration) {
        return insert("INSERT INTO tbllogs(user, action, duration, datetime) VALUES ( (SELECT id FROM tblpearsons WHERE username='" + user + "') ,'" + command + "','" + duration + "',NOW())");
    }

    public static List<Logs> getAllLogs() {
        List<Logs> l = null;

        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * from tbllogs ";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (l == null) {
                    l = new ArrayList();
                }
                l.add(new Logs(resultSet.getString("id"), resultSet.getString("user"), resultSet.getString("action"), resultSet.getString("duration"), resultSet.getString("datetime")));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public static List<Users> getUsers() {
        List<Users> users = null;

        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            String sql = "SELECT * from tblpearsons";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (users == null) {
                    users = new ArrayList();
                }
                users.add(new Users(resultSet.getString("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("role")));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public static String makeLogSqlWIthFilter(String page, String maxPerPage, Boolean dateCheck, String dateStart, String dateEnd, Boolean userCheck, String userId) {
        String sql = "SELECT * FROM tbllogs";
        if (userCheck) {
            sql += " WHERE user=" + userId;
        }
        if (dateCheck) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStart = sdf.format(new SimpleDateFormat("dd.MM.yyyy hh.mm.ss").parse(dateStart));
                dateEnd = sdf.format(new SimpleDateFormat("dd.MM.yyyy hh.mm.ss").parse(dateEnd));
            } catch (ParseException ex) {
                maxPerPage = "svi";
            }
            if (userCheck) {
                sql += " AND (datetime BETWEEN '" + dateStart + "' AND '" + dateEnd + "')";
            } else {
                sql += " WHERE (datetime BETWEEN '" + dateStart + "' AND '" + dateEnd + "')";
            }
        }
        sql += " ORDER BY datetime DESC";
        if (!maxPerPage.equals("svi")) {
            Integer rowPerPage = Integer.parseInt(maxPerPage);
            Integer offset = (Integer.parseInt(page) - 1) * rowPerPage;
            sql += " LIMIT " + offset + "," + rowPerPage;
        }
        return sql;
    }

    public static List<Logs> getAllLogsByFilter(String page, String maxPerPage, Boolean dateCheck, String dateStart, String dateEnd, Boolean userCheck, String userId) {
        String sql = makeLogSqlWIthFilter(page, maxPerPage, dateCheck, dateStart, dateEnd, userCheck, userId);
        List<Logs> l = null;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (l == null) {
                    l = new ArrayList();
                }
                l.add(new Logs(resultSet.getString("id"), resultSet.getString("user"), resultSet.getString("action"), resultSet.getString("duration"), resultSet.getString("datetime")));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return l;
    }

    public static Integer countAllLogsByFilter(String page, String maxPerPage, Boolean dateCheck, String dateStart, String dateEnd, Boolean userCheck, String userId) {
        String sql = Database.makeLogSqlWIthFilter(page, "svi", dateCheck, dateStart, dateEnd, userCheck, userId);
        sql = sql.replace("*", "count(*) AS num");
        System.out.println(sql);
        Integer num = 0;
        try {
            BP_Konfiguracija bp = (BP_Konfiguracija) ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                num = Integer.parseInt(resultSet.getString("num"));
            }
            connect.close();
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return num;
    }

}
