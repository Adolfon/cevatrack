package com.ceva.cevatrack.presentation;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.ceva.cjf.base.web.jsf.CJFBaseBean;
import com.ceva.framework.ejb.service.profile.vo.UserProfileVo;
import com.ceva.security.aaf.session.SessionManager;

/**
 * TimeZoneBean. Show Demo page with different time zones.
 * 
 * @author arun.kumar
 *
 */
@ManagedBean
@ViewScoped
public class TimeZoneBean extends CJFBaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date date;
    private String zoneId = "Universal";
    private Map<String, String> timeZones;
    private String userProfileTimeZone;
    private static final String CST_TIMEZONE = "America/Managua";

    /**
     * initilizes timezones.
     */
    @PostConstruct
    public void init() {
        this.timeZones = new LinkedHashMap<>();
        intilizeTimeZones();
        convertCurrentTime();
        UserProfileVo userProfile = (UserProfileVo) SessionManager.getSession().getAttribute("USER_PROFILE");
        if (userProfile != null && userProfile.getTimeZone() != null) {
            setUserProfileTimeZone(TimeZone.getTimeZone(userProfile.getTimeZone()).getDisplayName());
        } else {
            setUserProfileTimeZone(TimeZone.getTimeZone(CST_TIMEZONE).getDisplayName());
        }
    }

    private void convertCurrentTime() {
        date = Date.from(ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault())
                .withZoneSameInstant(TimeZone.getTimeZone("UTC").toZoneId()).toLocalDateTime()
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * read all times from java.time.api.
     */
    private void intilizeTimeZones() {
        List<String> zoneList = new ArrayList<>(ZoneId.getAvailableZoneIds());
        Map<String, String> sortedMap = new LinkedHashMap<>();
        Map<String, String> allZonesWithOffSet = new HashMap<>();
        TimeZone timeZoneId = null;
        ZoneId zone = null;
        ZonedDateTime zdt = null;
        ZoneOffset zos = null;
        String offset = null;
        LocalDateTime dt = LocalDateTime.now();
        for (String zoneId : zoneList) {
            timeZoneId = TimeZone.getTimeZone(zoneId);
            zone = ZoneId.of(zoneId);
            zdt = dt.atZone(zone);
            zos = zdt.getOffset();
            offset = zos.getId().replaceAll("Z", "+00:00");
            allZonesWithOffSet.put(timeZoneId.getDisplayName() + "  " + "(" + timeZoneId.toZoneId().toString() + ")"
                    + "," + timeZoneId.getID(), offset);
        }

        allZonesWithOffSet.entrySet().stream().sorted(Map.Entry.<String, String> comparingByValue().reversed())
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));

        sortedMap.forEach((k, v) -> {
            String[] array = k.split(",");
            String key = "(UTC" + v + (")") + " " + array[0];
            timeZones.put(key, array[1]);
        });
    }

    /**
     * show selected zoneId as per java.time.api.
     */
    public void selectedTimeZone() {
        convertCurrentTime();
        if (zoneId != null && zoneId != null) {
            addInfoMessage(zoneId);
        } else {
            addErrorMessage("TimeZone is not selected.");
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public Map<String, String> getTimeZones() {
        return timeZones;
    }

    public void setTimeZones(Map<String, String> timeZones) {
        this.timeZones = timeZones;
    }

    public String getUserProfileTimeZone() {
        return userProfileTimeZone;
    }

    public void setUserProfileTimeZone(String userProfileTimeZone) {
        this.userProfileTimeZone = userProfileTimeZone;
    }
}
