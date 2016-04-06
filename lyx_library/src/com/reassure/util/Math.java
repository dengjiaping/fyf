package com.reassure.util;

public class Math {
	
	private Math(){
		throw new RuntimeException("工具类禁止被实例化")  ;
	}
	/**
	 * 判断是否为奇数?<br>
	 * <br>
	 * 由于恒等式:(a/b)*b + a%b = a ;<br>
	 * 得到当a与a%b具有相同的正负号<br>
	 */
	public static boolean isOdd(int i){
		return (i & 1) != 0 ;
	}
	
	/**
	 * 数组arr是成对出现的数组，只有一个数落单，此方法可查出该数<br>
	 * 根据异或运算的两个特性<br>
	 * <br>
	 * 1.交换性<br>
	 * 2.自己与自己的异或运算为0<br>
	 * 3.任何值与0的异或运算还是它自己<br>
	 * @param arr 
	 * @return
	 */
	public static int findLost(int[] arr){
		int result = 0 ;
		for (int i = 0; i < arr.length; i++) {
			result ^= arr[i] ;
		}
		return result ;
	}
}
