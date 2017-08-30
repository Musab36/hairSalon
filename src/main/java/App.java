import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {

    ProcessBuilder process = new ProcessBuilder();
   Integer port;
   if (process.environment().get("PORT") != null) {
       port = Integer.parseInt(process.environment().get("PORT"));
   } else {
       port = 4567;
   }

 setPort(port);

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("clients", request.session().attribute("clients"));
    	model.put("template", "templates/index.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("stylists/:id/clients/new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
       Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
       model.put("stylist", stylist);
       model.put("template", "templates/stylist-clients-form.vtl");
       return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("clients", Client.all());
    	model.put("template", "templates/clients.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/clients/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Client client = Client.find(Integer.parseInt(request.params(":id")));
        model.put("client", client);
        model.put("template", "templates/client.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Stylist creation

    get("/stylists/new", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/stylist-form.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    // Posting stylists into the app

    post("/stylists", (request, response) -> {
         Map<String, Object> model = new HashMap<String, Object>();
         String name = request.queryParams("name");
          String description = request.queryParams("description");
         Stylist newStylist = new Stylist(name, description);
         model.put("template", "templates/stylist-success.vtl");
         return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    
    // Listing all stylists

    get("/stylists", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("stylists", Stylist.all());
        model.put("template", "templates/stylists.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Category details page

    get("/stylists/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
        model.put("stylist", stylist);
        model.put("template", "templates/stylist.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/clients", (request, response) -> {
       Map<String, Object> model = new HashMap<String, Object>();

       Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));

       String name = request.queryParams("name");
        String description = request.queryParams("description");
        Stylist newClient = new Stylist(name, description);
        request.session().attribute("client", "newClient"); 

       stylist.addClient(newClient);

       model.put("stylist", stylist);
       model.put("template", "templates/stylist-clients-success.vtl");
       return new ModelAndView(model, layout);
}, new VelocityTemplateEngine());
    
  }
}
