package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity matching the CRM Report Excel columns.
 * Table: crm_status78
 * Compatible with Java 8 + JPA
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "crm_status78")
public class CrmStatus78 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "site_id")
    private String siteId;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "route_code")
    private String routeCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ata")
    private Date ata;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "atd")
    private Date atd;

    @Column(name = "order_reference")
    private String orderReference;

    @Column(name = "order_line_ref")
    private String orderLineRef;

    @Column(name = "planned_qty")
    private Integer plannedQty;

    @Column(name = "actual_qty")
    private Integer actualQty;

    @Column(name = "status")
    private Integer status;

    @Column(name = "scanned")
    private String scanned;

    @Column(name = "clause_reason")
    private String clauseReason;

    @Column(name = "network")
    private String network = "technet";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Date lastUpdated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_ws_call")
    private Date lastWsCall;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    // ----- Getters and Setters -----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public Date getAta() {
        return ata;
    }

    public void setAta(Date ata) {
        this.ata = ata;
    }

    public Date getAtd() {
        return atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public String getOrderReference() {
        return orderReference;
    }

    public void setOrderReference(String orderReference) {
        this.orderReference = orderReference;
    }

    public String getOrderLineRef() {
        return orderLineRef;
    }

    public void setOrderLineRef(String orderLineRef) {
        this.orderLineRef = orderLineRef;
    }

    public Integer getPlannedQty() {
        return plannedQty;
    }

    public void setPlannedQty(Integer plannedQty) {
        this.plannedQty = plannedQty;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getScanned() {
        return scanned;
    }

    public void setScanned(String scanned) {
        this.scanned = scanned;
    }

    public String getClauseReason() {
        return clauseReason;
    }

    public void setClauseReason(String clauseReason) {
        this.clauseReason = clauseReason;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastWsCall() {
        return lastWsCall;
    }

    public void setLastWsCall(Date lastWsCall) {
        this.lastWsCall = lastWsCall;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Human-readable status label for the view.
     * Extend with any other codes your WS returns.
     */
    public String getStatusLabel() {
        if (status == null) {
            return "";
        }
        switch (status.intValue()) {
            case 67:
                return "Delivered";
            case 78:
                return "To be delivered";
            case 82:
                return "Refused";
            case 88:
                return "Partially Delivered";
            default:
                return String.valueOf(status);
        }
    }
}
