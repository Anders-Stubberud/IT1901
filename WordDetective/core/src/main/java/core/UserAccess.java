package core;

import java.util.Set;
import persistence.JsonIO;

public class UserAccess {
  
  /**
   * Instance of JsonIO used to gain access to persistently stored information of a ceratin user.
   */
  protected JsonIO jsonIO;

  /**
   * Instantiates a new instance of UserAccess, enabling access to persistently stored information about a certain user.
   * @param username The username of the user to access the persistent information of.
   */
  public UserAccess(final String username) {
    this.jsonIO = new JsonIO(username);
  }

  /**
   * Delegates the task of finding all categories available to the user.
   * @return Set<String> containing all categories available to the given user.
   */
  //kan brukes videre av Game for velging av ny kategori underveis i en spill-økt.
  public Set<String> getAllCategories() {
    return jsonIO.getAllCategories();
  }

}
