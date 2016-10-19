package com.theironyard;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Session;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void createTables (Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, email VARCHAR, password VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS galleries (id IDENTITY, gallery VARCHAR, " +
                "artist VARCHAR, genre VARCHAR, time VARCHAR, user_id INT)");
    }

    public static void insertUser(Connection conn, String name, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.execute();
    }

    public static User selectUser(Connection conn, String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
        stmt.setString(1, email);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            int id = results.getInt("id");
            return new User(id, email);
        }
        return null;
    }

    public static ArrayList<User> selectUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("email");
            String password = results.getString("password");
            User user = new User(id,name,password);
            users.add(user);
        }
        return users;
    }

    public static void insertGallery(Connection conn, Gallery gallery, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO galleries VALUES (NULL, ?, ?, ?, ?, ?)");
        stmt.setString(1, gallery.galleryName);
        stmt.setString(2, gallery.artist);
        stmt.setString(3, gallery.genre);
        stmt.setString(4, gallery.time);
        stmt.setInt(5, user.id);
        stmt.execute();

    }

    static ArrayList<Gallery> selectGalleries(Connection conn) throws SQLException {
        ArrayList<Gallery> galleries = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM galleries INNER JOIN users ON galleries.user_id = users.id");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("galleries.id");
            String galleryName = results.getString("galleries.gallery");
            String artist = results.getString("galleries.artist");
            String genre = results.getString("galleries.genre");
            String time = results.getString("galleries.time");
            int userId = results.getInt("users.id");
            Gallery gallery = new Gallery(id,galleryName,artist,genre,time,userId);
            galleries.add(gallery);
        }
        return galleries;
    }

    public static Gallery updateGallery(Connection conn, Gallery gallery) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE galleries SET gallery = ?, artist = ?, genre = ?, time = ?," +
                " user_id = ? WHERE id = ?");
        stmt.setString(1,gallery.galleryName);
        stmt.setString(2,gallery.artist);
        stmt.setString(3, gallery.genre);
        stmt.setString(4,gallery.time);
        stmt.setInt(5,gallery.userId);
        stmt.setInt(6,gallery.id);
        stmt.execute();
        return new Gallery(gallery.id,gallery.galleryName, gallery.artist, gallery.genre, gallery.time, gallery.userId);
    }

    public static void deleteGallery(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM galleries WHERE id = ?");
        stmt.setInt(1,id);
        stmt.execute();
    }

    public static void deleteUser(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
        stmt.setInt(1,id);
        stmt.execute();
    }


    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        createTables(conn);
        Spark.externalStaticFileLocation("public");
        Spark.init();

        Spark.post(
                "/login",
                (request, response) -> {
                    String email = request.queryParams("username");
                    String password = request.queryParams("password");
                    User user = selectUser(conn, email);
                    if (user == null) {
                        insertUser(conn, email, password);
                    }
                    else if (!password.equals(user.password)) {
                        Spark.halt(403);
                        return null;
                    }
                    Session session = request.session();
                    session.attribute("username", email);
                    response.redirect("/");
                    return "LOGIN";
                }
        );

        Spark.get(
                "/user",
                (request, response) -> {
                    Session session = request.session();
                    String email = session.attribute("username");
                    if (email == null) {
                        return "";
                    }
                    User user = selectUser(conn, email);
                    JsonSerializer serializer = new JsonSerializer();
                    return serializer.serialize(user);
                }
        );

        Spark.post(
                "/gallery",
                (request, response) -> {
                    Session session = request.session();
                    String email = session.attribute("username");
                    if (email == null) {
                        return "";
                    }
                    User user = selectUser(conn, email);
                    String body = request.body();
                    JsonParser parser = new JsonParser();
                    Gallery gallery = parser.parse(body, Gallery.class);
                    insertGallery(conn,gallery,user);
                    return "Gallery has been added.";
                }
        );

        Spark.get(
                "/gallery",
                (request, response) -> {
                    Session session = request.session();
                    String email = session.attribute("username");
                    if (email == null) {
                        return "";
                    }
                    ArrayList<Gallery> galleries = selectGalleries(conn);
                    JsonSerializer serializer = new JsonSerializer();
                    GalleryWrapper wrapper = new GalleryWrapper(galleries);
                    return serializer.deep(true).serialize(wrapper);
                }
        );

        Spark.put(
                "/gallery",
                (request, response) -> {
                    Session session = request.session();
                    String email = session.attribute("username");
                    if (email == null) {
                        return "";
                    }
                    User user = selectUser(conn, email);
                    String body = request.body();
                    JsonParser parser = new JsonParser();
                    Gallery gallery = parser.parse(body, Gallery.class);
                    updateGallery(conn, gallery);
                    return "Gallery has been updated.";
                }
        );

        Spark.delete(
                "/gallery/:id",
                (request, response) -> {
                    JsonParser parser = new JsonParser();
                    int id = parser.parse(request.params(":id"));
                    deleteGallery(conn,id);
                    return "Gallery deleted.";
                }
        );

        Spark.delete(
                "/user/:id",
                (request, response) -> {
                    JsonParser parser = new JsonParser();
                    int id = parser.parse(request.params(":id"));
                    deleteUser(conn, id);
                    return "User deleted.";
                }
        );

        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "User has logged out.";
                }
        );

    }
}
