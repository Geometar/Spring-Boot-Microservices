package microservices.book.gateway.gateway.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 3/8/2018.
 */
public class RibbonConfiguration {

    @Bean
    public IPing ribbonPing(final IClientConfig iClientConfig) {
        return new PingUrl(false, "/health" );
    }

    public IRule ribbonRule(final IClientConfig config) {
        return new AvailabilityFilteringRule();
    }
}
