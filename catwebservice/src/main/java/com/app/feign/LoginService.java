package com.app.feign;

import com.app.pojo.China;
import com.app.pojo.SysMenu;
import com.bobo.domain.AuthUser;
import com.bobo.feign.FeignService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.bobo.feign.FeignService.Service_cat;

@FeignClient(Service_cat)
public interface LoginService extends FeignService {
    /**
     * 获取项目 导航栏目录
     * @return
     */
    @PostMapping("/system/menus/{id}")
    public List<SysMenu> queryListMenu(@PathVariable(value = "id") String id);
    /**
     * 获取全国所有的地区信息
     * @return
     */
    @GetMapping("/system/chinas")
    List<China> queryAllChinas();
    /**
     * 系统token 认证用户
     * @return
     */
    @PostMapping("/system/token/auth")
    String queryUserToToken(@RequestBody AuthUser user);

}
