package com.ceva.cevatrack.service;

import java.util.List;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * UserService.
 * 
 * @author mohammad.rasheed
 *
 */
public interface UserService {

  public AppUserVO createUser(AppUserVO todo);

  public AppUserVO updateUser(AppUserVO todo);

  public List<AppUserVO> searchUser();

  public boolean deleteUser(AppUserVO todo);
}
