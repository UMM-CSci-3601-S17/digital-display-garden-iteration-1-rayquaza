package umm3601;

import umm3601.user.UserController;
import umm3601.plant.PlantController;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Arrays;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import static spark.Spark.*;


public class Server {
    public static void main(String[] args) throws IOException {

        UserController userController = new UserController();
        PlantController plantController = new PlantController("test");

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
 
            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Simple example route
        get("/hello", (req, res) -> "Hello World");

        // Redirects for the "home" page
        redirect.get("", "/");
        redirect.get("/", "http://localhost:9000");

        // List users
        get("api/users", (req, res) -> {
            res.type("application/json");
            return userController.listUsers(req.queryMap().toMap());
        });

        // See specific user
        get("api/users/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params("id");
            return userController.getUser(id);
        });

        // Get average ages by company
        get("api/avgUserAgeByCompany", (req, res) -> {
            res.type("application/json");
            return userController.getAverageAgeByCompany();
        });

        // Get specific plant
        get("api/plant/:id", (req, res) -> {
            res.type("application/json");
            String id = req.params("id");
            return plantController.getPlant(id);
        });

        // Like a specific plant
        // todo Should this be POST or PUT or something?
        get("api/plant/:id/like", (req, res) -> {
            res.type("application/json");
            String id = req.params("id");
            return plantController.incrementMetadata(id, "likes");
        });

        // Dislike a specific plant
        // todo Should this be POST or PUT or something?
        get("api/plant/:id/dislike", (req, res) -> {
            res.type("application/json");
            String id = req.params("id");
            return plantController.incrementMetadata(id, "dislikes");
        });

        // Posting a comment
        post("api/plant/leaveComment", (req, res) -> {
            res.type("application/json");
            return plantController.storePlantComment(req.body());
        });

        // Accept an xls file
        // post cl
        post("api/forTestingOnly", (req, res) -> {

                try {
                    res.type("appliation/json");

                    MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
                    req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
                    // Object[] things  = req.raw().getParts().toArray();
                    // System.out.println(req.getAttributes());

                    // InputStream fromClient = req.raw().getInputStream();
                    // OutputStream toDisk = new FileOutputStream("/tmp/lcs-dont-use-my-name");
                    // int buf;
                    // while ((buf = fromClient.read()) != -1) {
                    //     toDisk.write(buf);
                    //     System.out.println("reading!!!");
                    // }
                    // toDisk.close();
                    // fromClient.close();

                    // System.err.println(Arrays.toString(things));
                    Part part = req.raw().getPart("file[]"); // this turns out to be null ??
                    // System.out.println(part);
                    part.write("lcs-dont-use-my-name");
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }

                return true;
            });


        // Handle "404" file not found requests:
        notFound((req, res) -> {
            res.type("text");
            res.status(404);
            return "Sorry, we couldn't find that!";
        });

    }

}
