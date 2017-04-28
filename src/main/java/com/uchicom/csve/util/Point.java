// (c) 2006 uchicom
package com.uchicom.csve.util;

/**
 * @author uchiyama
 *
 */
public class Point {

	public Point() {

	}
	/**
	 *
	 * @param x
	 */
	public Point(int x) {
		this(x, null, null);
	}
	/**
	 *
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		this(x, y, null);
	}
	/**
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.exist = true;
	}
	/**
	 * @return x
	 */
	public Integer getX() {
		return x;
	}
	/**
	 * @param x セットする x
	 */
	public void setX(Integer x) {
		this.x = x;
	}
	/**
	 * @return y
	 */
	public Integer getY() {
		return y;
	}
	/**
	 * @param y セットする y
	 */
	public void setY(Integer y) {
		this.y = y;
	}
	/**
	 * @return z
	 */
	public Integer getZ() {
		return z;
	}
	/**
	 * @param z セットする z
	 */
	public void setZ(Integer z) {
		this.z = z;
	}
	private Integer x = null;
	private Integer y = null;
	private Integer z = null;
	boolean exist = false;
	/**
	 * @return exist
	 */
	public boolean isExist() {
		return exist;
	}
	/**
	 * @param exist セットする exist
	 */
	public void setExist(boolean exist) {
		this.exist = exist;
	}

}
