package com.example.demo.utils;

import com.example.demo.vo.MenuNodeVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuTreeUtils {

    public static Comparator<MenuNodeVO> order(){
        Comparator<MenuNodeVO> comparator = (o1, o2) ->{
            if(!o1.getOrderNum().equals(o2.getOrderNum())){
                return (int)(o1.getOrderNum() - o2.getOrderNum());
            }
            return 0;
        };
        return comparator;
    }

    public static List<MenuNodeVO> build(List<MenuNodeVO> nodes){
        List<MenuNodeVO> rootMenu = new ArrayList<>();
        for(MenuNodeVO nav:nodes){
            if(nav.getParentId() == 0){
                rootMenu.add(nav);
            }
        }
        Collections.sort(rootMenu,order());
        for(MenuNodeVO nav:rootMenu){
            nav.setChildren(getChild(nav.getId(),nodes));
        }
        return rootMenu;
    }

    private static List<MenuNodeVO> getChild(Long id, List<MenuNodeVO> nodes){
        List<MenuNodeVO> childList = new ArrayList<>();
        for(MenuNodeVO nav:nodes){
            if(nav.getParentId().equals(id)){
                childList.add(nav);
            }
        }
        for(MenuNodeVO nav:childList){
            nav.setChildren(getChild(nav.getId(),nodes));
        }
        Collections.sort(childList,order());
        return childList;
    }
}
