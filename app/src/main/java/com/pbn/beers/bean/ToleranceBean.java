package com.pbn.beers.bean;

/**
 * 容差相關
 */
public class ToleranceBean
{
	private float L;
	private float a;
	private float b;
	private float c;
	private float H;
	private float dEab;
	private float dEch;
	private float dEcmc;
	private float dE94;
	private float de00; //de*00(2000) 業界多數用這個色差標準
	
	/**
	 * @return L值容差
	 */
	public float getL() {
		return L;
	}
	
	/**
	 * @param l L值容差
	 */
	public void setL(float l) {
		L = l;
	}
	
	/**
	 * @return A值容差
	 */
	public float getA() {
		return a;
	}
	
	/**
	 * @param a a值容差
	 */
	public void setA(float a) {
		this.a = a;
	}
	
	/**
	 * @return 返回b值容差
	 */
	public float getB() {
		return b;
	}
	
	/**
	 * @param b b值容差
	 */
	public void setB(float b) {
		this.b = b;
	}
	
	/**
	 * @return 返回c值容差
	 */
	public float getC() {
		return c;
	}
	
	/**
	 * @param c c值容差
	 */
	public void setC(float c) {
		this.c = c;
	}
	
	/**
	 * @return 返回dE00色差公式容差值
	 */
	public float getDe00() {
		return de00;
	}
	
	/**
	 * @param de00 dE00色差公式容差值
	 */
	public void setDe00(float de00) {
		this.de00 = de00;
	}
	
	/**
	 * @return 返回dE94色差公式容差值
	 */
	public float getdE94() {
		return dE94;
	}
	
	/**
	 * @param dE94 dE94色差公式容差值
	 */
	public void setdE94(float dE94) {
		this.dE94 = dE94;
	}
	
	/**
	 * @return 返回dEab色差公式容差值
	 */
	public float getdEab() {
		return dEab;
	}
	
	/**
	 * @param dEab dEab色差公式容差值
	 */
	public void setdEab(float dEab) {
		this.dEab = dEab;
	}
	
	/**
	 * @return 返回dEch色差公式容差值
	 */
	public float getdEch() {
		return dEch;
	}
	
	/**
	 * @param dEch dEch色差公式容差值
	 */
	public void setdEch(float dEch) {
		this.dEch = dEch;
	}
	
	/**
	 * @return 返回dEcmc色差公式容差值
	 */
	public float getdEcmc() {
		return dEcmc;
	}
	
	/**
	 * @param dEcmc dEcmc色差公式容差值
	 */
	public void setdEcmc(float dEcmc) {
		this.dEcmc = dEcmc;
	}
	
	/**
	 * @return 返回h值容差
	 */
	public float getH() {
		return H;
	}
	
	/**
	 * @param h h值容差
	 */
	public void setH(float h) {
		H = h;
	}
	
}
