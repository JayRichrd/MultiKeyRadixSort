package com.jy;

/**
 * 数据包装类 实现了Comparable接口 并重写compareTo方法
 * 
 * @author jiangyu
 *
 */
public class DataWrap implements Comparable<DataWrap> {
	public int data;
	public String flag;

	public DataWrap(int data, String flag) {
		super();
		this.data = data;
		this.flag = flag;
	}

	@Override
	public int compareTo(DataWrap dataWrap) {
		// 根据data的大小来判定DataWrap变量的大小
		return this.data > dataWrap.data ? 1 : (this.data == dataWrap.data ? 0 : -1);
	}

	@Override
	public String toString() {
		return data + flag;
	}

}
