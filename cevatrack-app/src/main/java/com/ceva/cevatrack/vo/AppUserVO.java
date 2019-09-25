package com.ceva.cevatrack.vo;

import java.io.Serializable;
import com.ceva.security.aaf.vo.AAFUserVO;

/**
 * AppUserVO.
 * 
 * @author mohammad.rasheed
 *
 */
public class AppUserVO implements Serializable {

    private AAFUserVO maafUser;

    public AAFUserVO getAAFUser() {
        return maafUser;
    }

    public void setAAFUser(AAFUserVO maafUser) {
        this.maafUser = maafUser;
    }

}
