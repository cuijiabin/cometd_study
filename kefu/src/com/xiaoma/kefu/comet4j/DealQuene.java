package com.xiaoma.kefu.comet4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xiaoma.kefu.model.Customer;
import com.xiaoma.kefu.util.StudyMapUtil;

/**
 * 客服的对话列表
 * 
 * @author cuijiabin
 *
 */
public class DealQuene {

	private String ccnId;
	
	private Long customerId;
	
	private String customerName;
	
	private String ip;
	
	private String ipInfo;
	
	private String enterTime;

	public String getCcnId() {
		return ccnId;
	}

	public void setCcnId(String ccnId) {
		this.ccnId = ccnId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}

	public String getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(String enterTime) {
		this.enterTime = enterTime;
	}
	
	public static List<DealQuene> buildDealQuenes(Map<String, Long> ccnIdCustomerMap, List<Customer> customers){
		
		List<DealQuene> list = new ArrayList<DealQuene>();
		try {
			Map<Long, Customer> customerMap = StudyMapUtil.convertList2Map(customers, Customer.class.getDeclaredField("id"));
			
			for(String ccnId: ccnIdCustomerMap.keySet()){
				DealQuene dealQuene = new DealQuene();
				dealQuene.setCcnId(ccnId);
				Long customerId = ccnIdCustomerMap.get(ccnId);
				dealQuene.setCustomerId(customerId);
				
				Customer customer = customerMap.get(customerId);
				if(customer != null){
					dealQuene.setIp(customer.getIp());
				}
				dealQuene.setEnterTime("16:43");
				
				list.add(dealQuene);
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return list;
		
	}
	
}
