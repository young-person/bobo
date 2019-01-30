package com.security.fegin;

import com.bobo.domain.AuthUser;
import com.bobo.feign.FeignService;
import com.mybatis.pojo.SysUser;
import com.security.FallbackFactoryCustomer;
import com.security.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static com.bobo.feign.FeignService.Service_catwebservice;
import java.util.Map;

/**
 * fallback，fallbackFactory 针对都是实现断路器的功能
 */
@FeignClient(value = Service_catwebservice,configuration = FeignConfiguration.class,fallbackFactory= FallbackFactoryCustomer.class)
public interface CustomerLoginService extends FeignService {
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
}
