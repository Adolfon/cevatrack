package com.ceva.cevatrack.vo;

import java.io.Serializable;
import com.ceva.framework.ejb.service.profile.vo.UserProfileVo;

/**
 * AppUserProfileVO.
 * 
 * @author pruthvi.janapareddy
 *
 */
public class AppUserProfileVO extends UserProfileVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userProfileId;
    private boolean enableTodoNotification;

    /**
     * Default Constructor.
     */
    public AppUserProfileVO() {
        /**
         * Zero Parameter Constructor
         */
    }

    /**
     * Constructor.
     * 
     * @param id
     *            Id
     * @param userProfileId
     *            UserProfileId
     * @param enableTodoNotification
     *            EnableTodoNotification
     */
    public AppUserProfileVO(Long id, Long userProfileId, boolean enableTodoNotification) {
        this.id = id;
        this.userProfileId = userProfileId;
        this.enableTodoNotification = enableTodoNotification;
    }

    /**
     * getter for ID.
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for ID.
     * 
     * @param id
     *            ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for userProfileID.
     * 
     * @return the userProfileId
     */
    public Long getUserProfileId() {
        return userProfileId;
    }

    /**
     * setter for userProfileID.
     * 
     * @param userProfileId
     *            the userProfileId to set
     */
    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    /**
     * getter for enableTodoNotification.
     * 
     * @return the enableTodoNotification
     */
    public boolean isEnableTodoNotification() {
        return enableTodoNotification;
    }

    /**
     * setter for enableTodoNotification.
     * 
     * @param enableTodoNotification
     *            the enableTodoNotification to set
     */
    public void setEnableTodoNotification(boolean enableTodoNotification) {
        this.enableTodoNotification = enableTodoNotification;
    }

}
