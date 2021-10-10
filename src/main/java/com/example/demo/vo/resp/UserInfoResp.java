package com.example.demo.vo.resp;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.vo.MenuNodeVO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserInfoResp {

    private Long id;

    private String username;

    private User user;

    private Set<String> urls;

    private Set<String> perms;

    private List<Role> roles;

    private List<String> roleNames;

    private List<MenuNodeVO> menus;

    private String token;

}
