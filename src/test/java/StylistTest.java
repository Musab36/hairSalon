import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistTest {
    // Database rule
    @Rule
    public DatabaseRule database = new DatabaseRule();

 // Instantiation tes
  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    assertEquals(true, testStylist instanceof Stylist);
  }

  // Stylist name test
  @Test
  public void getName_stylistInstantiatesWithName_Henry() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    assertEquals("Suleqa", testStylist.getName());
  }
  
  // Stylist description test
  @Test
  public void getDescription_stylistInstantiatesWithDescription_String() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    assertEquals("Top stylist", testStylist.getDescription());
  }

  // Overriding equals test
  @Test
  public void equals_returnsTrueIfNameAndDescriptionAreSame_true() {
    Stylist firstStylist = new Stylist("Suleqa", "Top stylist");
    Stylist anotherStylist = new Stylist("Suleqa", "Top stylist");
    assertTrue(firstStylist.equals(anotherStylist));
  }

  // Database setup test
  @Test
  public void save_insertsObjectIntoDatabase_Stylist() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    testStylist.save();
    assertTrue(Stylist.all().get(0).equals(testStylist));
  }

  // Returning all database entries test
  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    Stylist firstStylist = new Stylist("Suleqa", "Top stylist");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Yasmin", "Good stylist");
    secondStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(firstStylist));
    assertEquals(true, Stylist.all().get(1).equals(secondStylist));
  }

  // Assigning id to objects test
  @Test
  public void save_assignsIdToObject() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    testStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(testStylist.getId(), savedStylist.getId());
  }

  // Finding stylists based on thier id test
  @Test
  public void find_returnsStylistWithSameId_secondStylist() {
    Stylist firstStylist = new Stylist("Suleqa", "Top stylist");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Yasmin", "Good stylist");
    secondStylist.save();
    assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
  }

  // Returning all client objects belonging to Stylist test
  @Test
  public void getClients_retrievesAllClientsFromDatabase_clientsList() {
    Stylist testStylist = new Stylist("Suleqa", "Top stylist");
    testStylist.save();
    Client firstClient = new Client("Ashley", testStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Ariana", testStylist.getId());
    secondClient.save();
    Client[] clients = new Client[] { firstClient, secondClient };
    assertTrue(testStylist.getClients().containsAll(Arrays.asList(clients)));
  }

}