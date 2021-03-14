package com.example.demo.dao;

import java.security.Permission;
import java.util.List;

import com.example.demo.model.PermissionEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionMapper {

    @Select(" select * from sys_permission ")
    List<PermissionEntity> findAllPermission();

}
