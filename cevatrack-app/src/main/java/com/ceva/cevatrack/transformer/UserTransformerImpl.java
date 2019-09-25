package com.ceva.cevatrack.transformer;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Singleton;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * UserTransformerImpl.
 * 
 * @author mohammad.rasheed
 *
 */
@Singleton(name = "com.ceva.template.transformer.UserTransformerImpl")
public class UserTransformerImpl extends AbstractEntityTransformerImpl<User> implements UserTransformer {

    @Override
    public AppUserVO transformToVO(User user) {
        return new AppUserVO();
    }

    @Override
    public List<AppUserVO> transformToVO(List<User> users) {
        return users.stream().map(u -> transformToVO(u)).collect(Collectors.toList());
    }

    /**
     * Transform. {@link AppUserVO} to {@link User}
     */
    public User transformToUser(AppUserVO vo) {
        User user = new User();
        user.setLogin(vo.getAAFUser().getUserPrincipal());
        return user;
    }

    @Override
    public void convertToUser(AppUserVO userVo, User user) {
        // TODO Auto-generated method stub

    }

}
