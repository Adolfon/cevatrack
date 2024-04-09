
package com.ceva.cevatrack.wsdl.types.getconsignment;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the com.cevalogistics.xsd.microlisesmartnet.tmc.getconsignment package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

  /**
   * Create a new ObjectFactory that can be used to create new instances of schema derived classes
   * for package: com.cevalogistics.xsd.microlisesmartnet.tmc.getconsignment
   * 
   */
  public ObjectFactory() {}

  /**
   * Create an instance of {@link GetConsignment }
   * 
   */
  public GetConsignment createGetConsignment() {
    return new GetConsignment();
  }

  /**
   * Create an instance of {@link GetConsignmentResponse }
   * 
   */
  public GetConsignmentResponse createGetConsignmentResponse() {
    return new GetConsignmentResponse();
  }

  /**
   * Create an instance of {@link Consignment }
   * 
   */
  public Consignment createConsignment() {
    return new Consignment();
  }

  /**
   * Create an instance of {@link ArrayOfClausedConsignmentDetail }
   * 
   */
  public ArrayOfClausedConsignmentDetail createArrayOfClausedConsignmentDetail() {
    return new ArrayOfClausedConsignmentDetail();
  }

  /**
   * Create an instance of {@link Vehicle }
   * 
   */
  public Vehicle createVehicle() {
    return new Vehicle();
  }

  /**
   * Create an instance of {@link ArrayOfDriver }
   * 
   */
  public ArrayOfDriver createArrayOfDriver() {
    return new ArrayOfDriver();
  }

  /**
   * Create an instance of {@link ArrayOfVehicle }
   * 
   */
  public ArrayOfVehicle createArrayOfVehicle() {
    return new ArrayOfVehicle();
  }

  /**
   * Create an instance of {@link Driver }
   * 
   */
  public Driver createDriver() {
    return new Driver();
  }

  /**
   * Create an instance of {@link ClausedConsignmentDetail }
   * 
   */
  public ClausedConsignmentDetail createClausedConsignmentDetail() {
    return new ClausedConsignmentDetail();
  }

  /**
   * Create an instance of {@link ArrayOfConsignmentDetail }
   * 
   */
  public ArrayOfConsignmentDetail createArrayOfConsignmentDetail() {
    return new ArrayOfConsignmentDetail();
  }

  /**
   * Create an instance of {@link ConsignmentDetail }
   * 
   */
  public ConsignmentDetail createConsignmentDetail() {
    return new ConsignmentDetail();
  }

}
