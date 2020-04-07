package com.ceva.cevatrack.domain;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ceva.framework.domain.AbstractEntity;

/**
 * The persistent class for the smartnet client database table.
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "technet_client")
public class TechNetClient extends AbstractEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String prefixes;
    private String image;
    @Column(name = "aaf_instance")
    private String aafInstance;

    public TechNetClient() {
    }

    /**
     * @param id
     *            is the id
     * @param name
     *            is the client name
     * @param prefixes
     *            is the list of microlise consignment prefixes delimited by bar
     * @param image
     *            is the png name
     * @param aafInstance
     *            is the aaf secured instance
     */
    public TechNetClient(Long id, String name, String prefixes, String image, String aafInstance) {
        this.name = name;
        this.prefixes = prefixes;
        this.image = image;
        this.aafInstance = aafInstance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(String prefixes) {
        this.prefixes = prefixes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAafInstance() {
        return aafInstance;
    }

    public void setAafInstance(String aafInstance) {
        this.aafInstance = aafInstance;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
