package com.manger;

import com.config.Order;

public class PackAge {
	private ScheduleItem scheduleitem;
	private Order order;
	
	public PackAge(ScheduleItem scheduleitem, Order order) {
		super();
		this.scheduleitem = scheduleitem;
		this.order = order;
	}

	public ScheduleItem getScheduleitem() {
		return scheduleitem;
	}

	public void setScheduleitem(ScheduleItem scheduleitem) {
		this.scheduleitem = scheduleitem;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
