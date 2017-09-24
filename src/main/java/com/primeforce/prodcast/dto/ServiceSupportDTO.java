package com.primeforce.prodcast.dto;

import java.util.List;

import com.primeforce.prodcast.businessobjects.ServiceTicket;

public class ServiceSupportDTO extends ProdcastDTO {
private List<ServiceTicket> serviceSupport;

public List<ServiceTicket> getServiceSupport() {
	return serviceSupport;
}

public void setServiceSupport(List<ServiceTicket> serviceSupport) {
	this.serviceSupport = serviceSupport;
}
}
