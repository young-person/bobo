package com.cloud.feign.catmain;

import com.bobo.domain.AuthUser;
import com.bobo.domain.ResultMeta;
import com.bobo.feign.FeignService;
import com.mybatis.pojo.China;
import com.mybatis.pojo.SysMenu;
import com.mybatis.pojo.SysUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.bobo.feign.FeignService.Service_catmain;

@FeignClient(Service_catmain)
public interface LoginActionFeign extends FeignService {
    /**
     * 系统token 认证用户
     * @return
     */
    @PostMapping("/system/token/auth")
    ResultMeta queryUserToToken(@RequestBody AuthUser user);
    /**
     * 远程接口调用是否存在用户
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
    public Map<String,String> validate(@RequestBody AuthUser user);
    /**
     * 根据token获取用户
     * @param token
     * @return
     */
    @RequestMapping(value = "/user/token", method = RequestMethod.POST)
    public ResponseEntity<SysUser> loginToken(@RequestHeader("Authorization") String token);

    /**
     * 获取项目 导航栏目录
     * @return
     */
    @GetMapping("/system/menus/{id}")
    public List<SysMenu> queryListMenu(@PathVariable(value = "id") String id);

    /**
     * 获取全国所有的地区信息
     * @return
     */
    @GetMapping("/system/chinas")
    List<China> queryAllChinas();


}
