package com.ceva.cevatrack.wsmicrolise;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author brian.goodall
 *
 */
public class Common {
    private static final Logger LOG = LoggerFactory.getLogger(Common.class);
    private static final DateTimeFormatter FORMATTERMS = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter FORMATTERDEFAULT = DateTimeFormatter.ofPattern("dd MMM hh:mm a");

    /**
     * @param fromXml
     *            text of date returned
     * @param formatter
     *            the format required
     * @param blankString
     *            string to replace zero-dates
     * @param invalidString
     *            straing to replace invalid dates
     * @return the result
     */
    public static String formatDate(String fromXml, DateTimeFormatter formatter, String blankString,
            String invalidString) {
        if (fromXml.equals("0001-01-01T00:00:00")) {
            return blankString;
        } else {
            if (formatter == null) {
                formatter = FORMATTERDEFAULT;
            }
            try {
                // LOG.info("-- from xml to parse: " + fromXml + " ,formatter: " + FORMATTERMS); //Too much Log..
                LocalDateTime ldt = LocalDateTime.parse(fromXml, FORMATTERMS);
                return ldt.format(formatter);
            } catch (Exception e) {
                LOG.error(" -- Problem formating date: " + fromXml + " formatters : " + FORMATTERMS + " , " + formatter,
                        e);
                return invalidString;
            }
        }
    }

    /**
     * @param status
     *            from xml
     * @return description of status per microlise document
     */
    public static String getStatusDescription(short status) {
        switch (status) {
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
     * @param status
     *            from xml
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
        return consignmentRef.substring(0, consignmentRef.indexOf("/"));
    }
}
