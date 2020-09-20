package edu.eci.arep.sparkD2;

import com.google.gson.Gson;
import edu.eci.arep.sparkD2.data.DBConnection;
import edu.eci.arep.sparkD2.httpserver.HttpServer;

import java.io.IOException;
import java.util.Arrays;

import static spark.Spark.*;

/**
 * Main Application
 */
public class App
{
    /**
     * The entry point of application also it defines the Endpoints .
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main( String[] args ) throws IOException {
        DBConnection db = new DBConnection();
        staticFileLocation("/public");
        port(getPort());
        get("/", (req, res) -> {

            res.redirect("/index.html");
            return "";
        });
        get("/testDB",(req,res)->{
            try{
            StringBuilder d = new StringBuilder();
            for (String[] s: db.getNames()){
                String[] lol = s[0].split("-");
                System.out.println(Arrays.toString(lol));
                d.append("  <tr>\n" + "    <td>").append(lol[0]).append("</td>\n").append("<td> || ").append(s[1]).append("</td>").append("<td> ||" + lol[1] ).append( "</td> </tr>");
            }
            String o ="<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<title>Title of the document</title>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<h1>Personas que han saludado al servidor: </h1>\n"
                    + "<table>\n" +
                    "  <tr>\n" +
                    "    <th>Nombre</th>\n" +
                    "<th>Fecha</th>\n"
                    +"<th>Processed by server No </th>\n"+
                    "  </tr>\n"
                    + d.toString()
                    + "</body>\n"
                    + "</html>\n";
            return o;}
            catch (Exception e){
                e.printStackTrace();
                return "";
            }

        });

        post("/testPost",(req,res) -> {
            try {
                db.insertData(req.body());
                return "Hello! " + req.body() + " Your POST request was succesfull and your name was added into the database! I'm gonna give you this random number :D " + Math.floor(Math.random() * Math.floor(10));
            }
            catch(Exception e){
                e.printStackTrace();
                return "";
            }
            });

        /*
        HttpServer serv = new HttpServer();
        serv.start();
        DBConnection db = new DBConnection();
        System.out.println("Iniciando get Request");|



        sparkD.get("/testGet",((request, response) -> "If you are seeing this, The test endpoint worked succesfully! :D YAY"));

        sparkD.get("/testDB", (request, response) ->  {
            StringBuilder d = new StringBuilder();
            for (String[] s: db.getNames()){
                d.append("  <tr>\n" + "    <td>").append(s[0]).append("</td>\n").append("<td> || ").append(s[1]).append("</td>  </tr>");
            }
            String o ="<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Personas que han saludado al servidor: </h1>\n"
                + "<table>\n" +
                "  <tr>\n" +
                "    <th>Nombre</th>\n" +
                    "<th>Fecha</th>\n" +
                "  </tr>\n"
                    + d.toString()
                + "</body>\n"
                + "</html>\n";
            return o;

        }
        );
        sparkD.post("/testPost",((request, response) -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            db.insertData(request.getBody());
            response.setMimeType("text/html");
            return "Hello! " + request.getBody() +" Your POST request was succesfull and your name was added into the database! I'm gonna give you this random number :D " +  Math.floor(Math.random() * Math.floor(10));

        }));

         */
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set(i.e on localhost)
    }

}
