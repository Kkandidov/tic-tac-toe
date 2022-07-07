package org.astashonok.battleservice.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "statistic-client", url = "http://127.0.0.1:9999/api")
public interface StatisticClient {
}
