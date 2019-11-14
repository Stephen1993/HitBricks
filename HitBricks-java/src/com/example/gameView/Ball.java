package com.example.gameView;

import com.example.Interface.BallInterface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Ball implements BallInterface{
	/**
	 * 
	 * ��������
	 * @author chenyang
	 * @param x, y -����λ��(������ԭ��Ϊ���Ͻ�)
	 * @param r -��뾶
	 * @param xOffset, yOffset -����x,y�᷽���ƶ����ٶ�
	 * @param color -����ɫ
	 * ���ϲ���Ĭ��ֵȫΪ0
	 * 
	 */
	
	//��ͨ���������Ϣ
	private float x = 0, y = 0; //����λ��
	private float r = 0; //��뾶
	private float xOffset = -10; //x�᷽���ٶ�
	private float yOffset = -10; //y�᷽���ٶ�
	private int color = 0; //����ɫ
	private boolean isMove = true; //��ʾ���Ƿ��ƶ�
	
	//����ͼƬ�����Ϣ
	private float left = 0, top = 0; //�����о������ϽǶ�������
	private Bitmap bitMap = null; //���ͼƬ
	
	//����С��ķ�������,С���ڵ���Ĳ�ͬλ�õ���ӵ�в�ͬ�ķ���(��x��, y�᷽�������ٶȲ�ͬ����б�ʲ�ͬ����)
	public static float[] x_offset = {-13, -11, -10, -9, -6, -2, 2, 6, 9, 10, 11, 13, 14};
	public static float[] y_offset = {-6, -9, -10, -11, -13, -14, -14, -13, -11, -10, -9, -6, -2};
	public static int offsetLen = x_offset.length - 1; //���������һ������,ֻ��Ϊ�˶��ֲ���û�ҵ���ʱ��������һ���ٶ�
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
	 * ���ƶ�
	 */
	public void movd(){
		if(!isMove)return;
		x += xOffset;
		y += yOffset;
		left += xOffset;
		top += yOffset;
	}
	
	/**
	 * ���ƶ�
	 * @param offset
	 */
	public void move(float offset){
		x += offset;
		//y += offset;
		left += offset;
		//top += offset;
	}
	
	/**
	 * ���ƫ�Ʒ���
	 * @param xOffset
	 * @param yOffset
	 */
	public void hitCorner(float xOffset, float yOffset){
		setxOffset(xOffset);
		setyOffset(yOffset);
	}
	
	/**
	 * ����С��
	 * @param canvas
	 * @param paint
	 */
	public void drawBall(Canvas canvas, Paint paint){
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL); //����ʵ��
		paint.setAntiAlias(true); //���ÿ����
		paint.setShadowLayer(0, 0, 0, 0); //������Ӱ��
		canvas.drawCircle(x, y, r, paint);
	}
	
	/**
	 * ����С��(ͼƬ)
	 * @param canvas
	 * @param paint
	 */
	void drawBallBitMap(Canvas canvas, Paint paint){
		canvas.drawBitmap(bitMap, left, top, paint); //����С�򱳾�ͼ
	}
	
	/**
	 * ������
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
	 * ����λ��
	 * @param left
	 * @param top
	 * @param x
	 * @param y
	 */
	public void setPos(float left, float top, float x, float y){ //����С��λ��
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
