package com.example.gameView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick {
	/**
	 * 
	 * ש����
	 * @author chenyang
	 * @param color -ש����ɫ
	 * @param isExist -ש���Ƿ����
	 * 
	 */

	private float left = 0;
	private float top = 0;
	private float right = 0;
	private float bottom = 0;
	private int color = 0; //ש����ɫ
	private int score = 5; //ש��ķ���
	private boolean isExist = true; //��ʶש���Ƿ����
	private boolean isDisap = true; //���ש���Ƿ����ʧ
	private Bitmap bitMap = null;
	private Bitmap defaultBrick = null;
	public static Bitmap[] bitMapDisp = new Bitmap[10]; //ש����ʧЧ��
	private int bitMapIndex = 0; //��ʾ��ʾ����bitMap
	private float mapOffset = 0; //ש����ʧͼƬ���ש��ͼƬ��Ҫ�ƶ���λ��
	
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
	 * ����ש��
	 * @param canvas
	 * @param paint
	 */
	public void drawBrick(Canvas canvas, Paint paint){
		if(!isExist)return;
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL); //����ʵ��
		paint.setAntiAlias(true); //�����
		paint.setShadowLayer(0, 0, 0, 0); //������Ӱ��
		canvas.drawRect(left, top, right, bottom, paint);;
	}
	
	/**
	 * ����ש��(ͼƬ)
	 * @param canvas
	 * @param paint
	 */
	public void drawBrickBitMap(Canvas canvas, Paint paint){
		if(bitMap == null)return; //��ֹbitMapIndexһֱ�Լ���ȥ
		mapOffset = 0;
		if(!isExist){mapOffset = 50; bitMap = bitMapDisp[bitMapIndex++];} //ש����ʧ��չʾ��ʧЧ��
		if(bitMap == null)return; //ש�鼰����ʧЧ�������
		canvas.drawBitmap(bitMap, left - mapOffset, top - mapOffset, paint); //���Ʊ���ͼ
	}
	
	/**
	 * ����ש��(ͼƬ)
	 * @param bitmap
	 * @param canvas
	 * @param paint
	 */
	public void drawBrickBitMap(Bitmap bitmap, Canvas canvas, Paint paint){
		if(!isExist){drawBrickBitMap(canvas, paint);return;}
		canvas.drawBitmap(bitmap, left, top, paint); //���Ʊ���ͼ
	}
	
	/**
	 * ����ש��λ��
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
	 * ����ש���������
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
