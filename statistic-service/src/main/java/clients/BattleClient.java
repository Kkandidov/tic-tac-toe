package clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "battle-client", url = "http://127.0.0.1:9998/api")
public interface BattleClient {
}
