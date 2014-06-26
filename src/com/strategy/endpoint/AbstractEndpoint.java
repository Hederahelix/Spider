package com.strategy.endpoint;

import com.config.Order;
import com.config.WebConfig;

public abstract class AbstractEndpoint {
	public abstract String endpoint_strategy(String Contents,Order task,String Url);
}
