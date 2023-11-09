package core;

import java.util.Set;
import persistence.JsonIO;
import types.User;

public class UserAccess {

  /**
   * Instance of JsonIO used to gain access to persistently stored information of
   * a ceratin user.
   */
  private JsonIO jsonIO;

  // /**
  // * Sets new instance of JsonIO.
  // * @param newJsonIO The new JsonIO to use.
  // */
  // protected void setJsonIO(JsonIO newJsonIO) {
  // this.jsonIO = newJsonIO;
  // }

  public static void main(String[] args) {
    UserAccess a = new UserAccess("guest");
    System.out.println(a.getAllCategories());
  }

  /**
   * Provides access to the JsonIO.
   * 
   * @return The instances JsonIO.
   */
  protected JsonIO getJsonIO() {
    return jsonIO;
  }

  /**
   * Instantiates a new instance of UserAccess, enabling access to persistently
   * stored information about a certain user.
   * 
   * @param username The username of the user to access the persistent information
   *                 of.
   */
  public UserAccess(final String username) {
    this.jsonIO = new JsonIO(username);
  }

  /**
   * Delegates the task of finding all categories available to the user.
   * 
   * @return Set<String> containing all categories available to the given user.
   */
  // kan brukes videre av Game for velging av ny kategori underveis i en
  // spill-økt.
  public Set<String> getAllCategories() {
    return jsonIO.getAllCategories();
  }

}
