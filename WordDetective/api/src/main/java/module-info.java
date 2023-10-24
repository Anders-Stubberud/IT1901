module project.api {

  exports api.controllers;
  
  requires project.persistence;
  requires project.types;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.webmvc;
  requires transitive project.core;

  opens api;
}
