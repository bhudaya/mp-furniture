package centmp.depotmebel.adminpartner.bean;

import java.util.List;

public class OrderListBean {
	
	private List<OrderBean> allList;
	private List<OrderBean> unalloactedList;
	
	private Integer unalloactedNum;
	private Integer readyQcNum;
	
	public List<OrderBean> getAllList() {
		return allList;
	}
	public void setAllList(List<OrderBean> allList) {
		this.allList = allList;
	}
	public List<OrderBean> getUnalloactedList() {
		return unalloactedList;
	}
	public void setUnalloactedList(List<OrderBean> unalloactedList) {
		this.unalloactedList = unalloactedList;
	}
	public Integer getUnalloactedNum() {
		return unalloactedNum;
	}
	public void setUnalloactedNum(Integer unalloactedNum) {
		this.unalloactedNum = unalloactedNum;
	}
	public Integer getReadyQcNum() {
		return readyQcNum;
	}
	public void setReadyQcNum(Integer readyQcNum) {
		this.readyQcNum = readyQcNum;
	}

}
