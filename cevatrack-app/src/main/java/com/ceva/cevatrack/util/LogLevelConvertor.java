package com.ceva.cevatrack.util;

import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * JSF SelectOneMenu converter. It used to convert String to Level {@link Level} object and
 * vice-versa.
 * 
 * @author varsha.chowdary
 *
 */
@FacesConverter(value = "logLevelConverter")
public class LogLevelConvertor implements Converter {

  /**
   * Convert String representation to Level object.
   */
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    if (!StringUtils.isNotEmpty(value)) {
      return null;
    }
    return Level.parse(value);
  }

  /**
   * Convert Level object representation to String.
   */
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value == null) {
      return null;
    }
    return ((Level) value).getName();
  }

}
