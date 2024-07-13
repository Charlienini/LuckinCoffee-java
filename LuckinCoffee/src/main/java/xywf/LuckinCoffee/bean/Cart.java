package xywf.LuckinCoffee.bean;
import java.util.ArrayList;

import xywf.LuckinCoffee.bean.Coffee;

public class Cart {
	private Coffee sp_coffee;
	private int sp_cup;
	
	private int sp_id;
	private String sp_cfname;
	private String sp_size;
	private String sp_temp;
	private String sp_sweet;
	private float sp_price;
	
	public Cart() {
		
	}
	public Cart(int sp_id,String sp_cfname,String sp_size, String sp_temp, String sp_sweet,float sp_price , int sp_cup) {
		this.sp_id = sp_id;
		this.sp_cfname = sp_cfname;
		this.sp_size = sp_size;
		this.sp_temp = sp_temp;
		this.sp_sweet = sp_sweet;
		this.sp_price = sp_price;
		this.sp_cup = sp_cup;
		
		
	}
	
	
	public int getSp_id() {
		return sp_id;
	}
	public void setSp_id(int sp_id) {
		this.sp_id = sp_id;
	}
	public String getSp_cfname() {
		return sp_cfname;
	}
	public void setSp_cfname(String sp_cfname) {
		this.sp_cfname = sp_cfname;
	}
	public String getSp_size() {
		return sp_size;
	}
	public void setSp_size(String sp_size) {
		this.sp_size = sp_size;
	}
	public String getSp_temp() {
		return sp_temp;
	}
	public void setSp_temp(String sp_temp) {
		this.sp_temp = sp_temp;
	}
	public String getSp_sweet() {
		return sp_sweet;
	}
	public void setSp_sweet(String sp_sweet) {
		this.sp_sweet = sp_sweet;
	}
	public float getSp_price() {
		return sp_price;
	}
	public void setSp_price(float sp_price) {
		this.sp_price = sp_price;
	}
	public int getSp_cup() {
		return sp_cup;
	}
	public void setSp_cup(int sp_cup) {
		this.sp_cup = sp_cup;
	}
	
	
	public Coffee getSp_coffee() {
		return sp_coffee;
	}
	public void setSp_coffee(Coffee sp_coffee) {
		this.sp_coffee = sp_coffee;
	}
	
	
}
