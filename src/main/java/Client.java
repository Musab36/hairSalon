import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private int stylistId;
    private int id;

      public Client(String name, int stylistId) {
          this.name = name;
          this.stylistId = stylistId;
      }
      
      public String getName() {
          return name;
      }

      public int getStylistId() {
          return stylistId;
      }

      public int getId() {
          return id;
      }

      @Override
      public boolean equals(Object otherClient){
        if (!(otherClient instanceof Client)) {
          return false;
        } else {
          Client newClient = (Client) otherClient;
          return this.getName().equals(newClient.getName()) &&
                 this.getStylistId() == newClient.getStylistId();
        }
      }

      public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO clients (name, stylistid) VALUES (:name, :stylistId)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .addParameter("stylistId", this.stylistId)
            .executeUpdate()
            .getKey();
        }
      }

      public static List<Client> all() {
        String sql = "SELECT * FROM clients";
        try(Connection con = DB.sql2o.open()) {
          return con.createQuery(sql).executeAndFetch(Client.class);
        }
      }

      public static Client find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM clients WHERE id=:id";
          Client client = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Client.class);
          return client;
        }
      }

      public void update(String name) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "UPDATE clients SET name = :name WHERE id = :id";
          con.createQuery(sql)
            .addParameter("name", name)
            .addParameter("id", id)
            .executeUpdate();
        }
      }
      
        public void delete() {
          try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM clients WHERE id = :id;";
            con.createQuery(sql)
            .addParameter("id", id)
            .executeUpdate();
          }
        }

    
    }