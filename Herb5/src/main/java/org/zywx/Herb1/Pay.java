package org.zywx.Herb1;

/**
 * 值类型的持久化类 
 *
 */
public class Pay {

	private int monthPay;
	private int yearPay;
	private int vocationWithPay;
	
	
	
	public Pay() {
		super();
	}
	public Pay(int monthPay, int yearPay, int vocationWithPay) {
		super();
		this.monthPay = monthPay;
		this.yearPay = yearPay;
		this.vocationWithPay = vocationWithPay;
	}
	public int getMonthPay() {
		return monthPay;
	}
	public void setMonthPay(int monthPay) {
		this.monthPay = monthPay;
	}
	public int getYearPay() {
		return yearPay;
	}
	public void setYearPay(int yearPay) {
		this.yearPay = yearPay;
	}
	public int getVocationWithPay() {
		return vocationWithPay;
	}
	public void setVocationWithPay(int vocationWithPay) {
		this.vocationWithPay = vocationWithPay;
	}
	
}
