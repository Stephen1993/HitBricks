package com.example.gameView;

import com.example.Interface.BallInterface;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Prop implements BallInterface{
	/**
	 * 道具类
	 */
	
	public static int propLen = 12; //道具数量
	public static Bitmap[] bitMap = new Bitmap[15]; //道具图片
	private static float yOffset = 5; //道具掉落速度
	private Bitmap prop = null; //当前应显示的道具
	private float left = 0, top = 0; //道具位置
	//道具是球状,所以碰撞检测需要x, y, r
	private float x = 0, y = 0, r = 0;
	private boolean isExist = true; //判断道具是否存在
	private int propId = 0;
	
	public Prop(){
		super();
	}
	
	public Prop(Bitmap prop, float left, float top, int propId){
		this.prop = prop;
		this.left = left;
		this.top = top;
		this.propId = propId;
	}
	
	public static void move(Prop p){
		p.top += yOffset;
		p.y += yOffset;
	}
	
	public static void move(Prop p, float offset){
		p.top += offset;
		p.y += offset;
	}
	
	void drawPropBitMap(Canvas canvas, Paint paint){ //绘制道具
		canvas.drawBitmap(prop, left, top, paint);
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

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}
	
}
