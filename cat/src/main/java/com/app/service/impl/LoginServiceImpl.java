package com.app.service.impl;

import com.bobo.utils.CTokenUtil;
import com.mybatis.mapper.ChinaMapper;
import com.mybatis.mapper.SysMenuMapper;
import com.mybatis.mapper.SysUserMapper;
import com.mybatis.pojo.China;
import com.mybatis.pojo.SysMenu;
import com.bobo.domain.AuthUser;
import com.mybatis.pojo.SysMenuExample;
import com.security.fegin.CustomerLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ChinaMapper chinaMapper;

    private CustomerLoginService customerLoginService;

    /**
     * 获取程序目录
     * @param id
     * @return
     */
    public List<SysMenu> queryListMenu(String id){
        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        if (StringUtils.isNotBlank(id)){

        }
        return list;
    }

    /**
     * 获取中国区域信息
     * @return
     */
    public List<China> queryAllChinas(){
        return chinaMapper.selectByExample(null);
    }

    /**
     * 用户获取token
     * @param user
     * @return
     */
    public String queryUserToToken(AuthUser user) {
        //使用本地生成token
        return CTokenUtil.generatorT_Token(user);
    }

    public List<SysMenu> contSysMenu(List<SysMenu> menus,Integer id){
        List<SysMenu> roots = new ArrayList<SysMenu>();
        for(SysMenu menu : menus){
            if (id.equals(menu.getId())){
                roots.add(menu);
            }
        }
        return null;
    }
}
