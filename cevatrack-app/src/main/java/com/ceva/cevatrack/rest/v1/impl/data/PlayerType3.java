package com.ceva.cevatrack.rest.v1.impl.data;

/*
 * 
 * Type as per example:
 * https://www.benchresources.net/apache-cxf-jax-rs-restful-web-service-using-both-json-xml-example/
 * As per
 * https://www.nabisoft.com/tutorials/java-ee/producing-and-consuming-json-or-xml-in-java-rest-
 * services-with-jersey-and-jackson
 */
public class PlayerType3 {

  public PlayerType3() {
    super();
  }

  public PlayerType3(int playerId, String name, int age, int matches) {
    super();
    this.playerId = playerId;
    this.name = name;
    this.age = age;
    this.matches = matches;
  }

  protected int playerId;
  protected String name;
  protected int age;
  protected int matches;

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int value) {
    this.playerId = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int value) {
    this.age = value;
  }

  public int getMatches() {
    return matches;
  }

  public void setMatches(int value) {
    this.matches = value;
  }

  @Override
  public String toString() {
    return "{playerId=" + playerId + ", name=" + name + ", age=" + age + ", matches=" + matches
        + "}";
  }

}
