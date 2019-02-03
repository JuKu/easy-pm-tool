package com.jukusoft.pm.tool.app.config;

import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.jukusoft.pm.tool.def.config.Config;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class HazelcastConfig {

    protected static final String CONF_HZ_SECTION = "Hazelcast";

    @Bean
    @Profile("default")
    HazelcastInstance hazelcastInstance() {
        //read configuration
        String groupName = Config.get(CONF_HZ_SECTION, "hz_group_name");
        String groupPassword = Config.get(CONF_HZ_SECTION, "hz_group_password");
        String instanceName = Config.get(CONF_HZ_SECTION, "hz_instance_name");
        String memberStr = Config.get(CONF_HZ_SECTION, "hz_members");

        String[] members = memberStr.split(",");

        com.hazelcast.config.Config config = new com.hazelcast.config.Config();
        config.getGroupConfig().setName(groupName).setPassword(groupPassword);

        if (!Config.getBool(CONF_HZ_SECTION, "standalone")) {
            TcpIpConfig ipc = config.getNetworkConfig().getJoin().getTcpIpConfig();
            ipc.setEnabled(true);

            for (String member : members) {
                ipc.addMember(member);
            }

            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        }

        config.setInstanceName(instanceName);

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    @Profile("default")
    CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }

}
