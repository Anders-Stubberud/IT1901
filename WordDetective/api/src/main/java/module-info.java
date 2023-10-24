module project.api {

  // requires project.core;
  requires project.persistence;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.webmvc;
  requires transitive project.core;

  exports api.controllers;

  opens api;
}
