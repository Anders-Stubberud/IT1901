package core;

import persistence.JsonIO;

public abstract class AbstractPersistenceAccess {

  protected JsonIO jsonIO;

  protected AbstractPersistenceAccess(JsonIO jsonIO) {
    this.jsonIO = jsonIO;
  }

}
