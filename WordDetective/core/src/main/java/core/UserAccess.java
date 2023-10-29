package core;

import java.util.Set;

import persistence.JsonIO;

public class UserAccess {
  
  protected JsonIO jsonIO;

  public UserAccess(String username) {
    this.jsonIO = new JsonIO(username);
  }

  //kan brukes videre av Game for velging av ny kategori underveis i en spill-økt.
  public Set<String> getAllCategories() {
    return jsonIO.getAllCategories();
  }

}
