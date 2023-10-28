package core;

import java.util.Set;

import persistence.JsonIO;

public class CategoryAccess extends AbstractPersistenceAccess {

  public CategoryAccess(JsonIO jsonIO) {
    super(jsonIO);
  }

  public Set<String> getAllCategories(String username) {
    return jsonIO.getAllCategories();
  }

}
