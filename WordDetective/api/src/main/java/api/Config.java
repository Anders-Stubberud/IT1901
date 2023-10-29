package api;

import org.springframework.context.annotation.Bean;

import persistence.JsonIO;

public class Config {
  
  @Bean 
  public JsonIO jsonIO() {
    return new JsonIO();
  }

}
