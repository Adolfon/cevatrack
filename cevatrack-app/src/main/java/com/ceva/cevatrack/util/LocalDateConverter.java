package com.ceva.cevatrack.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.johnzon.mapper.Converter;

/**
 * Generic date to string converter. FIXME move to cjf-common
 */
public class LocalDateConverter implements Converter<Date> {
  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

  @Override
  public String toString(final Date date) {
    return date.toString();
  }

  @Override
  public Date fromString(final String text) {
    try {
      return DATE_FORMATTER.parse(text);
    } catch (ParseException e) {
      throw new RuntimeException("Date " + text + " could not be parsed");
    }
  }

}
