/*
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

    post("/clients", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        ArrayList<Client> clients = request.session().attribute("clients");
        if (clients == null) {
            clients = new ArrayList<Client>(); 
            request.session().attribute("clientss", clients);
        }

        String name = request.queryParams("name");
        Client newClient = new Client(name, 45);
        request.session().attribute("client", "newClient");
        clients.add(newClient);


        model.put("template", "templates/success.vtl");
        return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // Stylist creation

    get("/stylists/new", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("template", "templates/stylist-form.vtl");
        model.put("template", "templates/stylists.vtl");
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

    // Stylist details page

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
*/

import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
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

    post("/clients", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
          String name = request.queryParams("name");
          Client newClient = new Client(name, stylist.getId());
          newClient.save();
          model.put("stylist", stylist);
          model.put("template", "templates/stylist-client-success.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    get("/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/stylist-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      Stylist newStylist = new Stylist(name, description);
      newStylist.save();
      model.put("template", "templates/stylist-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
      Client client = Client.find(Integer.parseInt(request.params(":id")));
      model.put("stylist", stylist);
      model.put("client", client);
      model.put("template", "templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

 post("/stylists/:stylist_id/clients/:id", (request, response) -> {
  Map<String, Object> model = new HashMap<String, Object>();
  Client client = Client.find(Integer.parseInt(request.params("id")));
  String description = request.queryParams("description");
  Stylist stylist = Stylist.find(client.getStylistId());
  client.update(description);
  String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
  response.redirect(url);
  return new ModelAndView(model, layout);
}, new VelocityTemplateEngine());

 post("/stylists/:stylist_id/clients/:id/delete", (request, response) -> {
  HashMap<String, Object> model = new HashMap<String, Object>();
  Client client = Client.find(Integer.parseInt(request.params("id")));
  Stylist stylist = Stylist.find(client.getStylistId());
  client.delete();
  model.put("stylist", stylist);
  model.put("template", "templates/stylist.vtl");
  return new ModelAndView(model, layout);
 }, new VelocityTemplateEngine());

  }

}