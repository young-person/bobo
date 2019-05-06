package com.app.service.impl;

import com.mybatis.mapper.SysMenuMapper;
import com.mybatis.pojo.SysMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthManagerServiceImpl {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 获取程序目录
     * @param id
     * @return
     */
    public List<SysMenu> queryListMenu(String id){
        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        if (StringUtils.isNotBlank(id)){
            List<SysMenu> results = new ArrayList<>();
            return loadMenusById(list,Arrays.asList(Integer.parseInt(id)),results);
        }
        return list;
    }

    private List<SysMenu> loadMenusById(List<SysMenu> sysMenus,List<Integer> ids,List<SysMenu> results){
        int size = sysMenus.size(),count = 0;
        List<Integer> tempIds = new ArrayList<Integer>();
        for(SysMenu menu :sysMenus){
            if (ids.contains(menu.getParentid())){
                results.add(menu);
                tempIds.add(menu.getId());
            }
            count ++;
            if (size == count && ids.size() > 0)
                loadMenusById(sysMenus,tempIds,results);
        }
        return results;
    }


    @Transactional
    public int deleteMenu(Integer id){
       return sysMenuMapper.deleteByPrimaryKey(id);
    }

}
