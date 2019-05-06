package com.cloud.feign.catupload;

import com.bobo.feign.FeignService;
import com.cloud.feign.catupload.fit.FallbackFactoryCustomer;
import com.cloud.feign.catupload.fit.FeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

import static com.bobo.feign.FeignService.Service_catupload;

/**
 * fallback，fallbackFactory 针对都是实现断路器的功能
 */
@FeignClient(value = Service_catupload,configuration = FeignConfiguration.class,fallbackFactory= FallbackFactoryCustomer.class)
public interface CustomerLoginFeign extends FeignService {

}
