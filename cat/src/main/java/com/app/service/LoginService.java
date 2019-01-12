package com.app.service;

import com.bobo.utils.CTokenUtil;
import com.mybatis.mapper.ChinaMapper;
import com.mybatis.mapper.SysMenuMapper;
import com.mybatis.mapper.SysUserMapper;
import com.mybatis.pojo.China;
import com.mybatis.pojo.SysMenu;
import com.mybatis.pojo.SysUser;
import com.bobo.domain.AuthUser;
import com.security.fegin.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ChinaMapper chinaMapper;

    private CustomerLoginService customerLoginService;

    public List<SysMenu> queryListMenu(){
        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        return list;
    }

    public SysUser querySelectOneUser(){
        SysUser sysUser =sysUserMapper.selectByPrimaryKey(1);
        return sysUser;
    }
    public List<China> queryAllChinas(){
        return chinaMapper.selectByExample(null);
    }

    public String queryUserToToken(AuthUser user) {
        Map<String,String> result = customerLoginService.validate(user.getUserName(),user.getPassword());
        String token = "";
        if (!StringUtils.isEmpty(result.get("id"))) {
            //根据数据 生成token 将 唯一id写入到redis 存储里面
        }else{
            //使用本地生成token
            token = CTokenUtil.generatorT_Token(user);
        }
        return token;
    }


}
