package com.example.gameView;

import android.graphics.Bitmap;
import android.view.WindowManager;

public class Screen {
	/**
	 * 
	 * 关卡类
	 * 
	 */
	
	private static int maxTopBrick = 200;
	private static int brickWith = 10; //砖块宽度
	private static int brickHeight = 5; //砖块高度	
	
	public static int screenWidth = 0; //屏幕宽度
	public static int screenHeight = 0; //屏幕高度
	public static int screenId = 1; //当前关卡id
	public static int screenNum = 6; //关卡总数
	
	//每个砖块的坐标
	private static int left = 0;
	private static int top = 0;
	private static int right = 0;
	private static int bottom = 0;
	private static int padding = 5; //每个砖块之间的距离
	
	//砖块Bitmap
	public static Bitmap[] bitMap = new Bitmap[12];
	
	//关卡以及关卡背景
	public static Bitmap[] bgMap = new Bitmap[12]; //关卡背景
	private Brick[][] screen1 = null; //关卡1
	private Brick[][] screen2 = null; //关卡2
	private Brick[][] screen3 = null;
	private Brick[][] screen4 = null;
	private Brick[][] screen5 = null;
	private Brick[][] screen6 = null;
	private Brick[][] screen7 = null;
	private Brick[][] screen8 = null;
	private Brick[][] screen9 = null;
	
	public Screen(){
		super();
		brickWith = bitMap[0].getWidth();
		brickHeight = bitMap[0].getHeight();
		creatScreen1();
		creatScreen2();
		creatScreen3();
		creatScreen4();
		creatScreen5();
		creatScreen6();
		creatScreen7();
		creatScreen8();
		creatScreen9();
	}
	
	/**
	 * 获取关卡
	 * @param id
	 * @return
	 */
	public Brick[][] getScreen(int id){
		if(id == 1)return screen1;
		else if(id == 2)return screen2;
		else if(id == 3)return screen3;
		else if(id == 4)return screen4;
		else if(id == 5)return screen5;
		else if(id == 6)return screen6;
		else if(id == 7)return screen7;
		else if(id == 8)return screen8;
		else if(id == 9)return screen9;
		return null;
	}
	
	public void creatScreen1(){ //生成第一个关卡砖块布局
		screen1 = new Brick[6][6];
		top = maxTopBrick - padding - brickHeight;
		for(int i = 0; i < 6; ++i){
			left = (screenWidth -6 * brickWith) / 3 - padding - brickWith;
			top += padding + brickHeight;
			if(i == 3)top += 100;
			bottom = top + brickHeight;
			for(int j = 0; j < 6; ++j){
				left += padding + brickWith;
				if(j == 3)left += (screenWidth -6 * brickWith) / 3;
				right = left + brickWith;
				screen1[i][j] = new Brick(bitMap[j], left, top);
				screen1[i][j].setRight(right);
				screen1[i][j].setBottom(bottom);
			}
		}
	}
	
	public void creatScreen2(){ //生成第二个关卡砖块布局
		screen2 = new Brick[10][6];
		top = maxTopBrick - padding - brickHeight;
		for(int i = 0; i < 10; ++i){
			left = (screenWidth -6 * brickWith) / 3 - padding - brickWith;
			top += padding + brickHeight;
			bottom = top + brickHeight;
			for(int j = 0; j < 6; ++j){
				left += padding + brickWith;
				if(j == 3)left += (screenWidth -6 * brickWith) / 3;
				right = left + brickWith;
				screen2[i][j] = new Brick(bitMap[i % 8], left, top);
				screen2[i][j].setRight(right);
				screen2[i][j].setBottom(bottom);
			}
		}
	}
	
	public void creatScreen3(){ //生成第三个关卡砖块布局
		screen3 = new Brick[11][];
		screen3[0] = new Brick[3];
		for(int i = 1; i < 3; ++i)screen3[i] = new Brick[5];
		screen3[3] = new Brick[7];
		for(int i = 4; i < 10; ++i)screen3[i] = new Brick[6];
		screen3[10] = new Brick[7];
		top = maxTopBrick - padding - brickHeight;
		for(int i = 0; i < 11; ++i){
			int len = screen3[i].length;
			left = (screenWidth - 9 * (brickWith + padding)) / 2 - 2;
			if(i < 1)left += 3 * (brickWith + padding) - brickWith;
			else if(i < 3)left += 2 * (brickWith + padding) - brickWith;
			else if(i < 6)left += brickWith + padding - brickWith;
			else left += padding - padding - brickWith;
			top += padding + brickHeight;
			bottom = top + brickHeight;
			for(int j = 0; j < len; ++j){
				left += padding + brickWith;
				if(i > 3 && i < 6 && j == 3)left += padding + brickWith;
				else if(i > 5 && j == 3)left += 3 * (padding + brickWith);
				if(i == 10 && j == 6)continue;
				right = left + brickWith;
				screen3[i][j] = new Brick(bitMap[j % 8], left, top);
				screen3[i][j].setRight(right);
				screen3[i][j].setBottom(bottom);
			}
		}
		left = (screenWidth - 9 * (brickWith + padding)) / 2;
		left += 4 * (brickWith + padding) + padding;
		right = left + brickWith;
		screen3[10][6] = new Brick(bitMap[8], left, top);
		screen3[10][6].setRight(right);
		screen3[10][6].setBottom(bottom);
		screen3[10][6].setDisap(false);
	}
	
	public void creatScreen4(){ //生成第四个关卡砖块布局
		screen4 = new Brick[9][];
		for(int i = 0; i < 9; ++i)screen4[i] = new Brick[i+1];
		top = maxTopBrick - padding - brickHeight;
		for(int i = 0; i < 9; ++i){
			left = (screenWidth - 9 * (brickWith + padding)) / 2 - 2;
			left += padding - padding - brickWith;
			top += padding + brickHeight;
			bottom = top + brickHeight;
			for(int j = 0; j <= i; ++j){
				left += padding + brickWith;
				right = left + brickWith;
				if(i == 8 && j != i){screen4[i][j] = new Brick(bitMap[8], left, top);screen4[i][j].setDisap(false);}
				else if(i == 8 && j == i)screen4[i][j] = new Brick(bitMap[0], left, top);
				else screen4[i][j] = new Brick(bitMap[j], left, top);
				screen4[i][j].setRight(right);
				screen4[i][j].setBottom(bottom);
			}
		}
	}
	
	public void creatScreen5(){ //生成第五个关卡砖块布局
		screen5 = new Brick[11][];
		for(int i = 0; i < 3; ++i)screen5[i] = new Brick[4 + i * 2];
		for(int i = 3; i < 6; ++i)screen5[i] = new Brick[9];
		for(int i = 6; i < 9; ++i)screen5[i] = new Brick[7 - 2 * (i - 6)];
		screen5[9] = new Brick[2];
		screen5[10] = new Brick[1];
		top = maxTopBrick - padding - brickHeight;
		for(int i = 0; i < 11; ++i){
			if(i == 0)left = (screenWidth - 5 * (brickWith + padding)) / 2 - 2;
			else left = (screenWidth - screen5[i].length * (brickWith + padding)) / 2 - 2;
			left += padding - padding - brickWith;
			top += padding + brickHeight;
			bottom = top + brickHeight;
			for(int j = 0; j < screen5[i].length; ++j){
				left += padding + brickWith;
				if(i == 0 && j == 2)left += padding + brickWith;
				right = left + brickWith;
				int k = 0;
				if((j == 0 || j == screen5[i].length - 1) && i > 5)k = 3;
				if(i == 3 && (j == 2 || j == 4 || j == 5 || j == 7))k = 4;
				else if(i == 4 && (j == 1 || j == 3 || j == 6 || j == 8))k = 4;
				else if(i == 5 && (j == 2 || j == 3 || j == 6 || j == 7))k = 4;
				else if(i == 6 && (j == 2 || j == 3 || j == 4 || j == 5))k = 4;
				else if(i == 7 && j == 4)k = 4;
				screen5[i][j] = new Brick(bitMap[k], left, top);
				screen5[i][j].setRight(right);
				screen5[i][j].setBottom(bottom);
			}
		}
	}
	
	public void creatScreen6(){ //生成第六个关卡砖块布局
		screen6 = new Brick[18][];
		screen6[0] = new Brick[2];
		screen6[1] = new Brick[3];
		screen6[2] = new Brick[5];
		for(int i = 3; i < 7; ++i)screen6[i] = new Brick[7];
		for(int i = 7; i < 9; ++i)screen6[i] = new Brick[5];
		screen6[9] = new Brick[3];
		screen6[10] = new Brick[5];
		for(int i = 11; i < 14; ++i)screen6[i] = new Brick[9];
		screen6[14] = new Brick[7];
		for(int i = 15; i < 17; ++i)screen6[i] = new Brick[5];
		screen6[17] = new Brick[7];
		top = maxTopBrick - 50 - padding - brickHeight;
		for(int i = 0; i < 18; ++i){
			if(i == 0)left = (screenWidth - 3 * (brickWith + padding)) / 2 - 2;
			else if(i == 1)left = (screenWidth - 5 * (brickWith + padding)) / 2 - 2;
			else if(i == 10 || i == 15 || i == 16)left = (screenWidth - 7 * (brickWith + padding)) / 2 - 2;
			else if(i == 17)left = (screenWidth - 9 * (brickWith + padding)) / 2 - 2;
			else left = (screenWidth - screen6[i].length * (brickWith + padding)) / 2 - 2;
			left += padding - padding - brickWith;
			top += padding + brickHeight;
			bottom = top + brickHeight;
			for(int j = 0; j < screen6[i].length; ++j){
				left += padding + brickWith;
				if(i == 0 && j == 1)left += padding + brickWith;
				else if(i == 1 && j > 0)left += padding + brickWith;
				else if((i == 10 || i == 15 || i == 16) && (j == 1 || j == 4))left += padding + brickWith;
				else if(i == 17 && (j == 3 || j == 4))left += padding + brickWith;
				right = left + brickWith;
				int k = 2;
				if(i < 2 || (i == 2 && (j == 0 || j == 2 || j == 4)))k = 3;
				else if(i == 9 && j == 1)k = 3;
				else if((i == 10 || i == 16) && j == 2)k = 3;
				else if(i > 10 && i < 14 && j > 2 && j < 6)k = 3;
				else if(i == 14 && j > 1 && j < 5)k = 3;
				else if(i == 15 && j > 0 && j < 4)k = 3;
				else if(i == 2 && (j == 1 || j == 3))k = 5;
				else if(i == 4 && (j == 1 || j == 5))k = 0;
				else if(i == 5 && (j == 1 || j == 2 || j == 4 || j == 5))k = 0;
				else if(i == 6 && (j == 2 || j == 3 || j == 4))k = 0;
				else if(i == 7 && (j == 1 || j == 3))k = 0;
				else if(i == 8 && j == 2)k = 0;
				else if(i == 7 && j == 2)k = 4;
				if(i == 17)k = 8;
				screen6[i][j] = new Brick(bitMap[k], left, top);
				screen6[i][j].setRight(right);
				screen6[i][j].setBottom(bottom);
				if(i == 17)screen6[i][j].setDisap(false);
			}
		}
	}
	
	public void creatScreen7(){ //生成第七个关卡砖块布局
		screen7 = new Brick[0][0];
	}
	
	public void creatScreen8(){ //生成第八个关卡砖块布局
		screen8 = new Brick[0][0];
	}
	
	public void creatScreen9(){ //生成第九个关卡砖块布局
		screen9 = new Brick[0][0];
	}
}
