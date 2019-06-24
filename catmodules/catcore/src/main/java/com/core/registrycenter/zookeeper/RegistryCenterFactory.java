package com.core.registrycenter.zookeeper;

import com.core.generator.IncrementIdGenerator;
import com.core.generator.RegisterDto;
import com.core.properties.CatCloudProperties;
import com.core.properties.domain.ZookeeperProperties;
import com.core.registrycenter.CoordinatorRegistryCenter;
import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.core.properties.domain.AliyunProperties;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryCenterFactory {
	
	public static void main(String[] args) {
		    CatCloudProperties cloudProperties = null;
		    String applicationName = null;
		    String hostAddress;
			try {
				hostAddress = InetAddress.getLocalHost().getHostAddress();
				RegistryCenterFactory.startup(cloudProperties, hostAddress, applicationName);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		    
	}
    private static final ConcurrentHashMap<HashCode, CoordinatorRegistryCenter> REG_CENTER_REGISTRY = new ConcurrentHashMap<>();

    /**
     * 创建注册中心.
     *
     * @param zookeeperProperties the zookeeper properties
     *
     * @return 注册中心对象 coordinator registry center
     */
    public static CoordinatorRegistryCenter createCoordinatorRegistryCenter(ZookeeperProperties zookeeperProperties) {
        Hasher hasher = Hashing.md5().newHasher().putString(zookeeperProperties.getZkAddressList(), Charsets.UTF_8);
        HashCode hashCode = hasher.hash();
        CoordinatorRegistryCenter result = REG_CENTER_REGISTRY.get(hashCode);
        if (null != result) {
            return result;
        }
        result = new ZookeeperRegistryCenter(zookeeperProperties);
        result.init();
        REG_CENTER_REGISTRY.put(hashCode, result);
        return result;
    }

    /**
     * Startup.
     *
     * @param cloudProperties the paascloud properties
     * @param host                the host
     * @param app                 the app
     */
    public static void startup(CatCloudProperties cloudProperties, String host, String app) {
        CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(cloudProperties.getZookeeperProperties());
        RegisterDto dto = new RegisterDto(app, host, coordinatorRegistryCenter);
        Long serviceId = new IncrementIdGenerator(dto).nextId();
        IncrementIdGenerator.setServiceId(serviceId);
        registerMq(cloudProperties, host, app);
    }

    private static void registerMq(CatCloudProperties CloudProperties, String host, String app) {
        CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(CloudProperties.getZookeeperProperties());
        AliyunProperties.RocketMqProperties rocketMq = CloudProperties.getAliyun().getRocketMq();
        String consumerGroup = rocketMq.isReliableMessageConsumer() ? rocketMq.getConsumerGroup() : null;
        String namesrvAddr = rocketMq.getNamesrvAddr();
        String producerGroup = rocketMq.isReliableMessageProducer() ? rocketMq.getProducerGroup() : null;
        coordinatorRegistryCenter.registerMq(app, host, producerGroup, consumerGroup, namesrvAddr);
    }
}
