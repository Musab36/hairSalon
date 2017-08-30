import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();
  
  // Client stantiating test
  @Test
  public void monster_instantiatesCorrectly_true() {
    Client testClient = new Client("Ashley", 1);
    assertEquals(true, testClient instanceof Client);
  }

  // Client's name test
  @Test
  public void Client_instantiatesWithName_String() {
    Client testClient = new Client("Ashley", 1);
    assertEquals("Ashley", testClient.getName());
  }

  // Assigning each client with stylist id test
  @Test
  public void Client_instantiatesWithStylistId_int() {
    Client testClient = new Client("Ashley", 1);
    assertEquals(1, testClient.getStylistId());
  }

  // Overriding equals
  @Test
  public void equals_returnsTrueIfNameAndStylistIdAreSame_true() {
    Client testClient = new Client("Ashley", 1);
    Client anotherClient = new Client("Ashley", 1);
    assertTrue(testClient.equals(anotherClient));
  }

  // Saving clients to database test
  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Client testClient = new Client("Ashley", 1);
    testClient.save();
    assertTrue(Client.all().get(0).equals(testClient));
  }

  // Assigning ids to clients test
  @Test
  public void save_assignsIdToClient() {
    Client testClient = new Client("Ashley", 1);
    testClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(savedClient.getId(), testClient.getId());
  }

  // Returning all database entries test
  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("Ashley", 1);
    firstClient.save();
    Client secondClient = new Client("Ariana", 1);
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

  // Finding clients based on thier ids
  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client firstClient = new Client("Ashley", 1);
    firstClient.save();
    Client secondClient = new Client("Ariana", 3);
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  // One to many relationship test
  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    testStylist.save();
    Client testClient = new Client("Ariana", testStylist.getId());
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(savedClient.getStylistId(), testStylist.getId());
  }

  @Test
  public void update_updatesClientName_true() {
   Client myClient = new Client("Zulfa", 1);
   myClient.save();
   myClient.update("Rowda");
   assertEquals("Rowda", Client.find(myClient.getId()).getName());
  }
 
  @Test
  public void delete_deletesClients_true() {
   Client myClient = new Client("Zulfa", 1);
   myClient.save();
   int myClientId = myClient.getId();
   myClient.delete();
   assertEquals(null, Client.find(myClientId));
  }

}