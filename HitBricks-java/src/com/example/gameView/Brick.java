package com.example.gameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick {
	/**
	 * 
	 * 砖块类
	 * @author chenyang
	 * @param color -砖块颜色
	 * @param isExist -砖块是否存在
	 * 
	 */

	private float left = 0;
	private float top = 0;
	private float right = 0;
	private float bottom = 0;
	private int color = 0; //砖块颜色
	private int score = 5; //砖块的分数
	private boolean isExist = true; //标识砖块是否存在
	private boolean isDisap = true; //标记砖块是否可消失
	private Bitmap bitMap = null;
	private Bitmap defaultBrick = null;
	public static Bitmap[] bitMapDisp = new Bitmap[10]; //砖块消失效果
	private int bitMapIndex = 0; //表示显示哪张bitMap
	private float mapOffset = 0; //砖块消失图片相对砖块图片需要移动的位置
	
	public Brick(){
		super();
	}
	
	public Brick(float left, float top, float right, float bottom, int color){
		this();
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.color = color;
	}
	
	public Brick(Bitmap bitMap, float left, float top){
		this();
		this.left = left;
		this.top = top;
		this.bitMap = this.defaultBrick = bitMap;
	}
	
	/**
	 * 绘制砖块
	 * @param canvas
	 * @param paint
	 */
	public void drawBrick(Canvas canvas, Paint paint){
		if(!isExist)return;
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL); //设置实心
		paint.setAntiAlias(true); //抗锯齿
		paint.setShadowLayer(0, 0, 0, 0); //设置阴影层
		canvas.drawRect(left, top, right, bottom, paint);;
	}
	
	/**
	 * 绘制砖块(图片)
	 * @param canvas
	 * @param paint
	 */
	public void drawBrickBitMap(Canvas canvas, Paint paint){
		if(bitMap == null)return; //防止bitMapIndex一直自加下去
		mapOffset = 0;
		if(!isExist){mapOffset = 50; bitMap = bitMapDisp[bitMapIndex++];} //砖块消失则展示消失效果
		if(bitMap == null)return; //砖块及其消失效果已完成
		canvas.drawBitmap(bitMap, left - mapOffset, top - mapOffset, paint); //绘制背景图
	}
	
	/**
	 * 绘制砖块(图片)
	 * @param bitmap
	 * @param canvas
	 * @param paint
	 */
	public void drawBrickBitMap(Bitmap bitmap, Canvas canvas, Paint paint){
		if(!isExist){drawBrickBitMap(canvas, paint);return;}
		canvas.drawBitmap(bitmap, left, top, paint); //绘制背景图
	}
	
	/**
	 * 设置砖块位置
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setPos(float left, float top, float right, float bottom){
		setLeft(left);
		setTop(top);
		setRight(right);
		setBottom(bottom);
	}
	
	/**
	 * 重置砖块基本属性
	 */
	public void resetBrick(){
		isExist = true;
		bitMap = defaultBrick;
		bitMapIndex = 0;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public int getBitMapIndex() {
		return bitMapIndex;
	}

	public void setBitMapIndex(int bitMapIndex) {
		this.bitMapIndex = bitMapIndex;
	}

	public Bitmap getBitMap() {
		return bitMap;
	}

	public void setBitMap(Bitmap bitMap) {
		this.bitMap = bitMap;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isDisap() {
		return isDisap;
	}

	public void setDisap(boolean isDisap) {
		this.isDisap = isDisap;
	}
	
}
