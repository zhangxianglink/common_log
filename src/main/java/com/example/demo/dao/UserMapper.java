package com.example.demo.dao;

import com.example.demo.model.PermissionEntity;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<User> selectAllUser();
    
    /**
     * 查询用户的权限根据用户查询权限
     *
     * @param userName
     * @return
     */
    @Select(" select permission.* from sys_user user" + " inner join sys_user_role user_role"
            + " on user.id = user_role.user_id inner join "
            + "sys_role_permission role_permission on user_role.role_id = role_permission.role_id "
            + " inner join sys_permission permission on role_permission.perm_id = permission.id where user.username = #{userName};")
    List<PermissionEntity> findPermissionByUsername(@Param("userName") String userName);
}
