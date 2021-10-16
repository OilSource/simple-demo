package com.example.demo.common.dto.resp;

import com.example.demo.entity.Role;
import com.example.demo.vo.MenuNodeVO;

import java.util.List;
import java.util.Set;

public interface ISecurityUser {

    Set<String> getUrls();

    void setUrls(Set<String> urls);

    Set<String> getPerms();

    void setPerms(Set<String> perms);

    List<MenuNodeVO> getMenus();

    void setMenus(List<MenuNodeVO> menus);

    List<Role> getRoles();

    void setRoles(List<Role> roles);
}
