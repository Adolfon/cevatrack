package com.ceva.cevatrack.service;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.repository.UserRepository;
import com.ceva.cevatrack.transformer.UserTransformer;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * UserServiceImpl.
 * 
 * @author mohammad.rasheed
 *
 */
@Stateless(name = "com.ceva.cevatrack.service.UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserTransformer userTransformer;

    @Override
    public AppUserVO createUser(AppUserVO userVo) {
        User user = userTransformer.transformToUser(userVo);
        userTransformer.prepareForCreate(user);
        User savedUser = userRepository.save(user);

        return userTransformer.transformToVO(savedUser);
    }

    @Override
    public AppUserVO updateUser(AppUserVO userVo) {
        Optional<User> userOpt = userRepository.findByLogin(userVo.getAAFUser().getEmail());
        AppUserVO appUserVO = userVo;

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            userTransformer.convertToUser(userVo, user);
            userTransformer.prepareForUpdate(user);
            User savedUser = userRepository.save(user);
            appUserVO = userTransformer.transformToVO(savedUser);
        }

        return appUserVO;
    }

    @Override
    public List<AppUserVO> searchUser() {
        // Introduce Search Criteria with pagination and sorting
        List<User> matchingUsers = userRepository.findAll();
        return userTransformer.transformToVO(matchingUsers);
    }

    @Override
    public boolean deleteUser(AppUserVO user) {
        return false;
    }

}
