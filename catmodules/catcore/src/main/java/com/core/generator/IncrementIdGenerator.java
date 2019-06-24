package com.core.generator;

import org.apache.curator.retry.RetryNTimes;

import com.core.registrycenter.CoordinatorRegistryCenter;

/**
 * FrameworkID 的保存器.
 *
 */
public class IncrementIdGenerator implements IdGenerator {
    private static Long serviceId = null;
    private final RegisterDto registerDto;

    /**
     * Instantiates a new Increment id generator.
     *
     * @param registerDto the register dto
     */
    public IncrementIdGenerator(RegisterDto registerDto) {
        this.registerDto = registerDto;
    }
    
    String path = "/paascloud/registry/id/%s/%s";
    String seq = "/paascloud/seq";
    @Override
    public Long nextId() {
        String app = this.registerDto.getApp();
        String host = this.registerDto.getHost();
        CoordinatorRegistryCenter regCenter = this.registerDto.getCoordinatorRegistryCenter();
        if (regCenter.isExisted(String.format(path, app,host))) {
            // 如果已经有该节点，表示已经为当前的host上部署的该app分配的编号（应对某个服务重启之后编号不变的问题），直接获取该id，而无需生成
            return Long.valueOf(String.format(path, app,host));
        } else {
            // 节点不存在，那么需要生成id，利用zk节点的版本号每写一次就自增的机制来实现
            regCenter.increment(seq, new RetryNTimes(2000, 3));
            // 生成id
            Integer id = regCenter.getAtomicValue(seq, new RetryNTimes(2000, 3)).postValue();
            // 将数据写入节点
            regCenter.persist(String.format(path, app,host));
            regCenter.persist(String.format(path, app,host), String.valueOf(id));
            return Long.valueOf(id);
        }
    }

    /**
     * Gets service id.
     *
     * @return the service id
     */
    public static Long getServiceId() {
        return serviceId;
    }

    /**
     * Sets service id.
     *
     * @param serviceId the service id
     */
    public static void setServiceId(Long serviceId) {
        IncrementIdGenerator.serviceId = serviceId;
    }
}
