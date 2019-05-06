package com.app.service.impl;

import com.bobo.domain.AuthUser;
import com.bobo.utils.CTokenUtil;
import com.mybatis.mapper.ChinaMapper;
import com.mybatis.mapper.SysUserMapper;
import com.mybatis.pojo.China;
import com.mybatis.pojo.SysMenu;
import com.mybatis.pojo.SysUser;
import com.mybatis.pojo.SysUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ChinaMapper chinaMapper;

    @Autowired
    private AuthManagerServiceImpl authManagerServiceImpl;
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

    /**
     * 根据父id拿到下面所有子ID
     * @param menus
     * @param id
     * @return
     */
    public List<SysMenu> contSysMenu(List<SysMenu> menus,Integer id){
        List<SysMenu> roots = new ArrayList<SysMenu>();
        for(SysMenu menu : menus){
            if (id.equals(menu.getId())){
                roots.add(menu);
            }
        }
        return null;
    }

    /**
     * 验证用户是否存在
     * @param user
     * @return
     */
    public Map<String, String> validate(AuthUser user) {
        List<SysUser> list = validateAuthUser(user);
        Map<String,String> map = new HashMap<>();
        if (list.size()>0){
            map.put("result","true");
        }else{
            map.put("result","false");
        }
        return map;
    }

    public List<SysUser> validateAuthUser(AuthUser user) {

        SysUserExample example = new SysUserExample();
        example.createCriteria().andPasswordEqualTo(user.getPassword()).andTelEqualTo(user.getUsername());

        SysUserExample.Criteria criteria1 =
                example.createCriteria().andPasswordEqualTo(user.getPassword()).andEmailEqualTo(user.getUsername());

        example.or(criteria1);

        List<SysUser> sysUser = sysUserMapper.selectByExample(example);
        return sysUser;
    }

    public ResponseEntity<SysUser> loginToken(String token) {
        AuthUser user = new AuthUser();
        user = CTokenUtil.generatorToken_T(token,user);

        SysUserExample example = new SysUserExample();
        example.createCriteria().andPasswordEqualTo(user.getPassword()).andEmailEqualTo(user.getUsername());
        List<SysUser> sysUser = sysUserMapper.selectByExample(example);
        if (sysUser.size()>0){
            return new ResponseEntity<SysUser>(sysUser.get(0), HttpStatus.OK);
        }else {
            return new ResponseEntity<SysUser>(new SysUser(), HttpStatus.OK);
        }
    }

    public List<SysMenu> queryListMenu(String id) {
        return authManagerServiceImpl.queryListMenu(id);
    }

}
