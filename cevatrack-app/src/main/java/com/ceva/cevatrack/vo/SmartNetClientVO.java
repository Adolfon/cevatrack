package com.ceva.cevatrack.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author brian.goodall
 *
 */
public class SmartNetClientVO implements Serializable {

    public static final String FIND_ALL = "SMARTNETCLIENT.findAll";

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;
    private String prefixes;
    private List<String> pfx;
    private String image;
    private String aafInstance;

    public SmartNetClientVO() {
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
    public SmartNetClientVO(Long id, String name, String prefixes, String image, String aafInstance) {
        this.name = name;
        this.prefixes = prefixes;
        this.image = image;
        this.aafInstance = aafInstance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getPfx() {
        return Arrays.asList(prefixes.split("\\|"));
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

}
