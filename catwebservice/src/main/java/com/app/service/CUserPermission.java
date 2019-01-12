package com.app.service;

import com.app.pojo.SysMenu;
import com.app.service.impl.CUserPermissionImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "auth-user",fallback = CUserPermissionImpl.class)
public interface CUserPermission {
    @GetMapping("permission/getRolePermission/{roleId}")
    List<SysMenu> getRolePermission(@PathVariable("roleId") Integer roleId);
}
