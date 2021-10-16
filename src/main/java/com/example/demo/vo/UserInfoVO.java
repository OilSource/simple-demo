package com.example.demo.vo;

import com.example.demo.common.dto.resp.ISecurityUser;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.usthe.sureness.subject.PrincipalMap;
import com.usthe.sureness.subject.SubjectSum;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;
@Data
public class UserInfoVO implements SubjectSum, ISecurityUser {

    private Long id;

    private String username;

    private User user;

    private Set<String> urls;

    private Set<String> perms;

    private List<Role> roles;

    private List<String> roleNames;

    private List<MenuNodeVO> menus;

    private String token;

    private String targetResource;

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public PrincipalMap getPrincipalMap() {
        return null;
    }

    @Override
    public boolean hasRole(String s) {
        return roleNames.contains(s);
    }

    @Override
    public boolean hasAllRoles(Collection<String> collection) {
        for(String role:collection){
            if(!roleNames.contains(role)){
                return false;
            }
        }
        return true;
    }

}
