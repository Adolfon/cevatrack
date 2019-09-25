package com.ceva.cevatrack.transformer;

import java.util.List;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformer;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * UserTransformer.
 * 
 * @author amit.saha.
 *
 */
public interface UserTransformer extends AbstractEntityTransformer<User> {
    public AppUserVO transformToVO(User todo);

    public List<AppUserVO> transformToVO(List<User> users);

    public User transformToUser(AppUserVO todo);

    public void convertToUser(AppUserVO userVo, User user);

}
