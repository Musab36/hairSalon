import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Stylist {
    private String name;
    private String description;
    private int id;
    private List<Stylist> mStylist;
      
    // Stylist constructor
      public Stylist(String name, String description) {
          this.name = name;
          this.description = description;
          mStylist = new ArrayList<Stylist>();
      }
 
      public String getName() {
          return name;
      }

      public String getDescription() {
          return description;
      }

      public int getId() {
          return id;
      }
    
      public void addClient(Stylist stylist) {
      mStylist.add(stylist);
    }

      @Override
      public boolean equals(Object otherStylist){
        if (!(otherStylist instanceof Stylist)) {
          return false;
        } else {
          Stylist newStylist = (Stylist) otherStylist;
          return this.getName().equals(newStylist.getName()) &&
                 this.getDescription().equals(newStylist.getDescription());
        }
      }

      // Saving to the database
      public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO stylists (name, description) VALUES (:name, :description)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .addParameter("description", this.description)
            .executeUpdate()
            .getKey();
        }
      }

      public static List<Stylist> all() {
        String sql = "SELECT * FROM Stylists";
        try(Connection con = DB.sql2o.open()) {
         return con.createQuery(sql).executeAndFetch(Stylist.class);
        }
      }

      public static Stylist find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM stylists WHERE id=:id";
          Stylist stylist = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Stylist.class);
          return stylist;
        }
      }

      public List<Client> getClients() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM clients WHERE stylistId=:id";
          return con.createQuery(sql)
            .addParameter("id", this.id)
            .executeAndFetch(Client.class);
        }
      }
    }