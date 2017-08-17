package com.jy;

import java.util.Arrays;

public class MultiKeyRadixSort {

	public static void main(String[] args) {
		DataWrap[] dataWraps = new DataWrap[] { new DataWrap(1100, ""), new DataWrap(192, ""), new DataWrap(221, ""),
				new DataWrap(12, ""), new DataWrap(13, "") };

		System.out.println("排序前：" + Arrays.toString(dataWraps));
		// 开始时间
		long startTime = System.currentTimeMillis();
		multiKeyRadixSort(dataWraps, 10);
		// 结束时间
		long stopTime = System.currentTimeMillis();
		System.out.println("=====================排序结束=====================");
		System.out.println("排序耗时t：" + (stopTime - startTime) + "ms");
		System.out.println("排序后（从小到大）：" + Arrays.toString(dataWraps));
	}

	/**
	 * 多关键字的基数排序
	 * 
	 * @param dataWraps
	 *            待排序的数组
	 * @param radix
	 *            拆分元素得到关键字的进制。例如，radix=10表示按十进制拆分元素得到关键字
	 */
	public static void multiKeyRadixSort(DataWrap[] dataWraps, int radix) {
		System.out.println("=====================开始排序=====================");
		// 根据序序列元素得到几组关键字
		int d = 0;
		// 遍历序列得到d的值
		for (int i = 0; i < dataWraps.length - 1; i++) {
			int temp = 0;
			// 不断除以radix，得出该数按照radix进制，最多有几位
			for (int data = dataWraps[i].data; data != 0; data /= 10)
				temp++;
			// d始终保存最大的值
			if (temp > d)
				d = temp;
		}
		// 调用重载的函数
		multiKeyRadixSort(dataWraps, radix, d);
	}

	/**
	 * 多关键字的基数排序
	 * 
	 * @param dataWraps
	 *            待排序的数组
	 * @param radix
	 *            拆分元素得到关键字的进制。例如，radix=10表示按十进制拆分元素得到关键字
	 * @param d
	 *            按照radix拆分元素，可以得到几租关键字。例如，radix=10，d=2表示按照十进制拆分元素可以得到个位、十位两组关键字
	 */
	public static void multiKeyRadixSort(DataWrap[] dataWraps, int radix, int d) {
		System.out.println("拆分序列元素的进制radix：" + radix);
		System.out.println("拆分序列元素得到关键字组数d：" + d);
		// 序列的长度
		int arrayLength = dataWraps.length;
		// 定义一个临时数组
		DataWrap[] temp = new DataWrap[arrayLength];
		// 使用桶式排序来作为基数排序的辅助排序
		// 桶式排序必须的buckets数组
		int[] buckets = new int[radix];
		// 依次从低位到高位，根据关键字对序列进行排序
		// rate表示当前排序的位(例如rate=10，表示根据十位上的关键字进行排序)
		for (int i = 0, rate = 1; i < d; i++) {
			// 每次都需要将buckets数组的元素重置为0
			Arrays.fill(buckets, 0);
			// 使用临时数组保存数组
			System.arraycopy(dataWraps, 0, temp, 0, arrayLength);
			// 利用桶式排序对每组关键字排序
			// 根据关键字来填充buckets数组
			for (int j = 0; j < arrayLength; j++) {
				int subKey = (temp[j].data / rate) % radix;
				buckets[subKey]++;
			}
			// 计算落入各个桶中的元素在序列中的位置
			// 根据buckets[j] = buckets[j-1] + buckets[j]计算新数组
			for (int j = 1; j < radix; j++)
				buckets[j] = buckets[j - 1] + buckets[j];
			// 根据从关键字获取的buckets中的信息对序列进行排序
			//必须从temp的最后一个元素往前取出元素来安排位置，因为buckets中的元素是递减的，这样才能保证桶式排序是稳定的
			for (int j = arrayLength - 1; j >= 0; j--) {
				int subKey = (temp[j].data / rate) % radix;
				// 考虑到序列中可能出现同一个数出现几次的情况，因此需要在取出buckets数组中某个位置的值后，需要递减
				dataWraps[--buckets[subKey]] = temp[j];
			}

			System.out.println("对" + rate + "位的关键字排序后：");
			System.out.println(Arrays.toString(dataWraps));
			// 每次rate乘以10递增
			rate *= radix;
		}

	}

}
