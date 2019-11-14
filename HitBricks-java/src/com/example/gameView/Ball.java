package com.example.gameView;

import com.example.Interface.BallInterface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Ball implements BallInterface{
	/**
	 * 
	 * 定义球类
	 * @author chenyang
	 * @param x, y -球心位置(坐标轴原点为左上角)
	 * @param r -球半径
	 * @param xOffset, yOffset -球向x,y轴方向移动的速度
	 * @param color -球颜色
	 * 以上参数默认值全为0
	 * 
	 */
	
	//普通绘制球的信息
	private float x = 0, y = 0; //球心位置
	private float r = 0; //球半径
	private float xOffset = -10; //x轴方向速度
	private float yOffset = -10; //y轴方向速度
	private int color = 0; //球颜色
	private boolean isMove = true; //表示球是否移动
	
	//绘制图片球的信息
	private float left = 0, top = 0; //球外切矩形左上角顶点坐标
	private Bitmap bitMap = null; //球的图片
	
	//设置小球的方向数组,小球在挡板的不同位置弹起拥有不同的方向(用x轴, y轴方向增加速度不同导致斜率不同体现)
	public static float[] x_offset = {-13, -11, -10, -9, -6, -2, 2, 6, 9, 10, 11, 13, 14};
	public static float[] y_offset = {-6, -9, -10, -11, -13, -14, -14, -13, -11, -10, -9, -6, -2};
	public static int offsetLen = x_offset.length - 1; //数组中最后一个不算,只是为了二分查找没找到的时候采用最后一组速度
	private int offsetId = 5;
	
	public Ball(){
		super();
		xOffset = x_offset[offsetId];
		yOffset = y_offset[offsetId];
	}
	
	public Ball(float x, float y, float r, int color){
		this();
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}
	
	public Ball(Bitmap bitMap, float left, float top){
		this();
		this.left = left;
		this.top = top;
		this.bitMap = bitMap;
	}
	
	/**
	 * 球移动
	 */
	public void movd(){
		if(!isMove)return;
		x += xOffset;
		y += yOffset;
		left += xOffset;
		top += yOffset;
	}
	
	/**
	 * 球移动
	 * @param offset
	 */
	public void move(float offset){
		x += offset;
		//y += offset;
		left += offset;
		//top += offset;
	}
	
	/**
	 * 球的偏移方向
	 * @param xOffset
	 * @param yOffset
	 */
	public void hitCorner(float xOffset, float yOffset){
		setxOffset(xOffset);
		setyOffset(yOffset);
	}
	
	/**
	 * 绘制小球
	 * @param canvas
	 * @param paint
	 */
	public void drawBall(Canvas canvas, Paint paint){
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL); //设置实心
		paint.setAntiAlias(true); //设置抗锯齿
		paint.setShadowLayer(0, 0, 0, 0); //设置阴影层
		canvas.drawCircle(x, y, r, paint);
	}
	
	/**
	 * 绘制小球(图片)
	 * @param canvas
	 * @param paint
	 */
	void drawBallBitMap(Canvas canvas, Paint paint){
		canvas.drawBitmap(bitMap, left, top, paint); //绘制小球背景图
	}
	
	/**
	 * 对象复制
	 * @return
	 */
	public Ball copy(){
		Ball b =new Ball(this.getBitMap(), this.getLeft(), this.getTop());
		b.setR(this.getR());
		b.setX(this.getX());
		b.setY(this.getY());
		b.setOffsetId((this.getOffsetId() + 1) % Ball.offsetLen);
		return b;
	}
	
	/**
	 * 设置位置
	 * @param left
	 * @param top
	 * @param x
	 * @param y
	 */
	public void setPos(float left, float top, float x, float y){ //设置小球位置
		setLeft(left);
		setTop(top);
		setX(x);
		setY(y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
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

	public Bitmap getBitMap() {
		return bitMap;
	}

	public void setBitMap(Bitmap bitMap) {
		this.bitMap = bitMap;
	}

	public int getOffsetId() {
		return offsetId;
	}

	public void setOffsetId(int offsetId) {
		this.offsetId = offsetId;
		hitCorner(Ball.x_offset[offsetId], Ball.y_offset[offsetId]);
	}

	public boolean isMove() {
		return isMove;
	}

	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}
	
}
