package com.ceva.cevatrack.wsmicrolise;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author brian.goodall
 *
 */
public class Common {
  private static final Logger LOG = LoggerFactory.getLogger(Common.class);
  private static final DateTimeFormatter FORMATTERMS = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
  // private static final DateTimeFormatter FORMATTERDEFAULT = DateTimeFormatter.ofPattern("dd MMM
  // hh:mm a");
  private static final DateTimeFormatter FORMATTERDEFAULT =
      DateTimeFormatter.ofPattern("dd MMM HH:mm");
  // private static final DateTimeFormatter OUTPUTFORMAT = DateTimeFormatter.ofPattern("dd/MM/yy
  // hh:mm");
  private static final DateTimeFormatter OUTPUTFORMAT =
      DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");

  private static String fixMicroSecondsWhitT(String xml) {
    // 2023-04-20T06:11:26.353
    DateTimeFormatter FORMATDATE = FORMATTERDEFAULT;
    if ("2023-04-20T06:11:26.353".length() == xml.length()) {
      LOG.info(" 3 digits");
      FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }
    if ("2023-04-20T06:11:26.35".length() == xml.length()) {
      LOG.info(" 2 digits");
      FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
    }
    if ("2023-04-20T06:11:26.3".length() == xml.length()) {
      LOG.info(" 1 digit");
      FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
    }
    LocalDateTime ldt = LocalDateTime.parse(xml, FORMATDATE);
    String formattedDateTime = ldt.format(OUTPUTFORMAT);
    LOG.info(" --Returning: " + formattedDateTime);
    return formattedDateTime;
  }

  private static String fixMicroSecondsWhitoutT(String xml) {
    // 2023-04-20 06:11:26.353
    DateTimeFormatter FORMATDATE = FORMATTERDEFAULT;
    if ("2023-04-20 06:11:26.353".length() == xml.length()) {
      LOG.info(" 3 digits");
      FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }
    if ("2023-04-20 06:11:26.35".length() == xml.length()) {
      LOG.info(" 2 digits");
      FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
    }
    if ("2023-04-20 06:11:26.3".length() == xml.length()) {
      LOG.info(" 1 digit");
      FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    }
    LocalDateTime ldt = LocalDateTime.parse(xml, FORMATDATE);
    String formattedDateTime = ldt.format(OUTPUTFORMAT);
    LOG.info(" --Returning: " + formattedDateTime);
    return formattedDateTime;
  }

  /**
   * @param fromXml text of date returned
   * @param formatter the format required
   * @param blankString string to replace zero-dates
   * @param invalidString straing to replace invalid dates
   * @return the result Method that converts the incoming String format (there are several ones) to
   *         the output formatter (OUTPUTFORMAT)
   */
  public static String formatDate(String fromXml, DateTimeFormatter formatter, String blankString,
      String invalidString) {
    LOG.info(" Formatting date: " + fromXml + " ,with format: " + formatter);
    if (fromXml == null) {
      LOG.warn(" -- parameter fromXml was null, returning empty String.. --");
      return "";
    }
    LocalDateTime ldt;
    if (fromXml.equals("0001-01-01T00:00:00")) {
      LOG.info("-- Returning blankString: " + blankString);
      return blankString;
    } else {
      if (formatter == null) {
        formatter = FORMATTERDEFAULT;
      }
      try {
        if ((fromXml.contains(".")) && (fromXml.contains("T"))) {
          // 2023-03-30T07:14:25.623' could not be parsed at index 10
          LOG.info("(.)&&(T) ---inside");
          return fixMicroSecondsWhitT(fromXml);
        }
        if (fromXml.contains(".")) {
          LOG.info(" -- A dot was in the date string to format ... --");
          return fixMicroSecondsWhitoutT(fromXml);
        } else {
          if (fromXml.contains("T")) {
            LOG.info("-- String contains a T: " + fromXml);
            ldt = LocalDateTime.parse(fromXml, FORMATTERMS);
            String formattedDateTime = ldt.format(OUTPUTFORMAT);
            LOG.info(" --(T)Returning: " + formattedDateTime);
            return formattedDateTime;
          } else {
            if (fromXml.contains("AM") || fromXml.contains("PM")) {
              LOG.info("(AM||PM) ---inside");
              DateTimeFormatter FORMATDATENOTA = DateTimeFormatter.ofPattern("dd MMM hh:mm a");
              ldt = LocalDateTime.parse(fromXml, FORMATDATENOTA);
              String formattedDateTime = ldt.format(OUTPUTFORMAT);
              LOG.info(" --(AMPM) The returning string date would be: " + formattedDateTime);
              return formattedDateTime;
            }
            if (fromXml.contains("-")) {
              LOG.info("(-) ---inside");
              DateTimeFormatter FORMATDATENOT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
              ldt = LocalDateTime.parse(fromXml, FORMATDATENOT);
              String formattedDateTime = ldt.format(OUTPUTFORMAT);
              LOG.info(" --(-) The returning string date is: " + formattedDateTime);
              return formattedDateTime;
            }
            if (fromXml.contains("/")) {
              LOG.info("(/) ---inside");
              DateTimeFormatter FORMATDATENOT = DateTimeFormatter.ofPattern("dd/MM/uu HH:mm");
              ldt = LocalDateTime.parse(fromXml, FORMATDATENOT);
              String formattedDateTime = ldt.format(OUTPUTFORMAT);
              LOG.info(" -- (/)The returning string date is: " + formattedDateTime);
              return formattedDateTime;
            }
          }
        }
        LOG.warn(" -- none of the options before applied, gonna apply the parameter formatter: "
            + formatter);
        ldt = LocalDateTime.parse(fromXml, formatter);
        String formattedDateTime = ldt.format(OUTPUTFORMAT);
        LOG.info(" -- returning: " + formattedDateTime);
        return formattedDateTime;
      } catch (Exception e) {
        LOG.error(" -- Problem formating date: " + fromXml + " formatters : " + FORMATTERMS + " , "
            + formatter, e);
        return invalidString;
      }
    }
  }

  /**
   * @param status from xml
   * @return description of status per microlise document
   */
  public static String getStatusDescription(short status) {
    switch (status) {
      case 0:
        return "Unknown";
      case 67:
        return "Delivered";
      case 78:
        return "To be delivered";
      case 82:
        return "Refused";
      case 88:
        return "Claused";
      default:
        return String.valueOf(status);
    }
  }

  /**
   * @param status from xml
   * @return description of status per microlise document
   */
  public static String getStatusCssClass(short status) {
    switch (status) {
      case 67:
        return "success";
      case 78:
        return "warning";
      case 82:
        return "danger";
      case 88:
        return "danger";
      default:
        return "default";
    }
  }

  public static String getPrefix(String consignmentRef) {
    if (consignmentRef.contains("/")) {
      return consignmentRef.substring(0, consignmentRef.indexOf("/"));
    }
    // Made an exception for Toners because it comes with a bad format
    if (consignmentRef.contains("Toners")) {
      return "Toners";
    }
    return consignmentRef;
  }
}
