package com.example.gameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.Interface.BallInterface;
import com.example.hitbricks.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	/**
	 * 
	 * @author chenyang
	 * @param context
	 * 游戏主界面类
	 */
	
	private SurfaceHolder holder = null;//连接surface并且操纵surface数据显示在surfaceView的对象
	private Bitmap bitMap = null; //主界面图片
	private Matrix matrix = null; //主界面图片需要的matrix
	private static int screenWidth = 0; //屏幕宽度
	private static int screenHeight = 0; //屏幕高度
	private int lifeNum = 2; //剩余生命数
	private int score = 0; //得分
	private boolean isLoops = true; //控制程序是否刷新canvas
	public int gameState = 1; //游戏状态
	private boolean isBrickEmpty = false; //标记本关砖块是否已空
	
	//球
	private Paint paint = null; //画笔
	private Ball ball = null; //主小球
	private Ball ball2 = null; //副小球(吃了某种道具后使用)
	private List<Ball> ballList = new ArrayList<Ball>(); //小球列表
	
	//挡板
	private Brick board; //小球挡板
	private Brick boardDefault = null; //默认挡板
	private Brick boardShort = null; //短挡板
	private Brick boardLong1 = null; //稍长挡板
	private Brick boardLong2 = null; //更长挡板
	
	//挡板的起始位置
	private static int boardLeft = 0;
	private static int boardTop = 0;
	private static int boardRight = 0;
	private static int boardBottom = 0;
	
	//挡板需要到达的目的点
	private float boardGoalX = 0;
	
	//挡板分成Ball.offsetLen段,每段的长度,作用是小球与挡板接触的位置不同则弹回的方向也不同
	private float segLen = 0;
	
	//关卡
	private static Screen screen = null; //关卡
	private Brick[][] brickList = null; //每个关卡的砖块布局
	
	//四周墙壁的坐标
	private static int wallLeft = 0;
	private static int wallTop = 0;
	private static int wallRight = 0;
	private static int wallBottom = 0;
	
	//道具
	private float prop4Left = 0; //道具墙左壁
	private float prop4Right = 0; //道具墙右壁
	private List<Prop> propList = new ArrayList<Prop>(); //道具
	private List<Prop> bulletList = new ArrayList<Prop>(); //子弹
	private int[] propTime = new int[20]; //每个道具效果剩余的时间
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context){
		paint = new Paint(); //获取画笔
		holder = getHolder(); //获取holder
		holder.addCallback(this); //设置当前对象为监听器(surfaceHolder.callBack接口实现类)
		
		//获取关卡背景图并设置背景图为适应当前屏幕大小
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg1)).getBitmap();
		matrix = new Matrix();
		WindowManager wm = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE));
		screenWidth = wm.getDefaultDisplay().getWidth(); //获取屏幕宽度
		screenHeight =wm.getDefaultDisplay().getHeight(); //获取屏幕高度
		int bgWidth = bitMap.getWidth(); //获取图片的宽度
		int bgHeight = bitMap.getHeight(); //获取图片的高度
		
		//计算缩放比例
		float scaleWidth = ((float)screenWidth) / bgWidth;
		float scaleHeight = ((float)screenHeight) / bgHeight;
		matrix.postScale(scaleWidth, scaleHeight);
		
		//得到缩放后的图片
		Screen.bgMap[0] = Screen.bgMap[1] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg2)).getBitmap();
		Screen.bgMap[2] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg3)).getBitmap();
		Screen.bgMap[3] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg4)).getBitmap();
		Screen.bgMap[4] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg5)).getBitmap();
		Screen.bgMap[5] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		//bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg6)).getBitmap(); //此处bug,为什么不能bg6
		Screen.bgMap[6] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg7)).getBitmap();
		Screen.bgMap[7] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg8)).getBitmap();
		Screen.bgMap[8] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg9)).getBitmap();
		Screen.bgMap[9] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		//bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.over)).getBitmap(); //此处bug,为什么不能over
		Screen.bgMap[10] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		//设置关卡的布局砖块并获取关卡对象
		Screen.bitMap[0] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick1)).getBitmap();
		Screen.bitMap[1] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick2)).getBitmap();
		Screen.bitMap[2] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick3)).getBitmap();
		Screen.bitMap[3] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick4)).getBitmap();
		Screen.bitMap[4] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick5)).getBitmap();
		Screen.bitMap[5] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick6)).getBitmap();
		Screen.bitMap[6] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick7)).getBitmap();
		Screen.bitMap[7] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick8)).getBitmap();
		Screen.bitMap[8] = ((BitmapDrawable)getResources().getDrawable(R.drawable.brick9)).getBitmap();
		Screen.screenWidth = screenWidth;
		Screen.screenHeight = screenHeight;
		screen = new Screen();
		
		//当前关卡为第一关
		brickList = screen.getScreen(Screen.screenId);
		bitMap = Screen.bgMap[Screen.screenId];
		
		//设置墙壁坐标
		wallLeft = 33;
		wallTop =  73;
		wallRight = screenWidth - wallLeft;
		wallBottom = screenHeight - 20;
		
		//初始化道具墙
		prop4Left = wallLeft;
		prop4Right = wallRight;
		
		//设置砖块消失效果
		Brick.bitMapDisp[0] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff1)).getBitmap();
		Brick.bitMapDisp[1] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff2)).getBitmap();
		Brick.bitMapDisp[2] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff3)).getBitmap();
		Brick.bitMapDisp[3] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff4)).getBitmap();
		Brick.bitMapDisp[4] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff5)).getBitmap();
		Brick.bitMapDisp[5] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff6)).getBitmap();
		Brick.bitMapDisp[6] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff7)).getBitmap();
		Brick.bitMapDisp[7] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff8)).getBitmap();
		
		//初始化小球挡板的信息
		Bitmap boardBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.board1)).getBitmap();
		//设置挡板位置
		boardLeft = (screenWidth - boardBitMap.getWidth()) / 2;
		boardTop = screenHeight - 180;
		boardRight = boardLeft + boardBitMap.getWidth();
		boardBottom = boardTop + boardBitMap.getHeight();
		
		boardDefault = new Brick(boardBitMap, boardLeft, boardTop);
		boardDefault.setRight(boardRight);
		boardDefault.setBottom(boardBottom);
		
		boardBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.board3)).getBitmap();
		boardShort = new Brick(boardBitMap, boardLeft, boardTop);
		boardShort.setRight(boardLeft + boardBitMap.getWidth());
		boardShort.setBottom(boardBottom);
		
		boardBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.board4)).getBitmap();
		boardLong1 = new Brick(boardBitMap, boardLeft, boardTop);
		boardLong1.setRight(boardLeft + boardBitMap.getWidth());
		boardLong1.setBottom(boardBottom);
		
		boardBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.board2)).getBitmap();
		boardLong2 = new Brick(boardBitMap, boardLeft, boardTop);
		boardLong2.setRight(boardLeft + boardBitMap.getWidth());
		boardLong2.setBottom(boardBottom);
		
		board = boardDefault;
		boardGoalX = (board.getLeft() + board.getRight()) / 2.0f;
		segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //设置挡板分段后每段的长度
		
		//初始化小球的信息
		Bitmap ballBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ball)).getBitmap();
		float r = (ballBitMap.getWidth() - 14) / 2f; //r表示小球的半径
		ball = new Ball(ballBitMap, boardGoalX - r, boardTop - r - r);
		ball.setR(r);
		ball.setX(boardGoalX);
		ball.setY(boardTop - r);
		
		ballBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ball2)).getBitmap();
		r = (ballBitMap.getWidth() - 5) / 2f; //r表示小球的半径
		ball2 = new Ball(ballBitMap, 0, 0);
		ball2.setR(r);
		
		ballList.add(ball.copy()); //添加主球
		
		//初始化道具
		Prop.bitMap[0] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop1)).getBitmap();
		Prop.bitMap[1] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop2)).getBitmap();
		Prop.bitMap[2] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop3)).getBitmap();
		Prop.bitMap[3] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop4)).getBitmap();
		Prop.bitMap[4] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop5)).getBitmap();
		Prop.bitMap[5] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop6)).getBitmap();
		Prop.bitMap[6] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop7)).getBitmap();
		Prop.bitMap[7] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop8)).getBitmap();
		Prop.bitMap[8] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop9)).getBitmap();
		Prop.bitMap[9] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop10)).getBitmap();
		Prop.bitMap[10] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop11)).getBitmap();
		Prop.bitMap[11] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop12)).getBitmap();
		Prop.bitMap[12] = ((BitmapDrawable)getResources().getDrawable(R.drawable.prop13)).getBitmap();
		
		//初始化道具效果剩余时间
		for(int i = 0; i < propTime.length; ++i)propTime[i] = 0;
	}
	
	/**
	 * 绘制游戏元素
	 */
	private void draw(){
		if(holder == null)return;
		Canvas canvas = holder.lockCanvas(); //锁定canvas并返回(同步锁机制,防止surface数据被修改)
		if (canvas == null) return;
		
		//绘制背景图
		canvas.drawBitmap(bitMap, 0, 0, paint);
		if(propTime[8] > 0){
			paint.setColor(Color.BLACK);
			canvas.drawRect(wallLeft, wallTop, wallRight, wallBottom + 15, paint);
		}
		
		//画砖块
		for (Brick[] bL : brickList) {
			for(Brick b : bL){
				if(propTime[8] > 0 && b.isExist())continue; //夜间模式则只需绘画爆炸效果
				if(propTime[9] > 0)b.drawBrickBitMap(Screen.bitMap[8], canvas, paint);
				else if(propTime[10] > 0)b.drawBrickBitMap(Screen.bitMap[2], canvas, paint);
				else b.drawBrickBitMap(canvas, paint);
			}
		}
		
		//画小球和小球挡板
		for(Ball b: ballList)b.drawBallBitMap(canvas, paint);
		board.drawBrickBitMap(canvas, paint);
		
		//绘制挡板两端发出的光束墙
		if(propTime[4] > 0){
			paint.setStyle(Paint.Style.FILL); //设置实心
			paint.setARGB(100, 0, 205, 205); //设置字体透明度和颜色
			paint.setStrokeWidth(10); //设置画笔的粗细
			canvas.drawLine(board.getLeft(), boardTop, board.getLeft(), wallTop, paint);
			canvas.drawLine(board.getRight(), boardTop, board.getRight(), wallTop, paint);
		}
		
		//绘制挡板上的颜色,表示小球可粘贴在挡板上
		if(propTime[5] > 0){
			paint.setStrokeWidth(10); //设置画笔的粗细
			paint.setARGB(100, 0, 255, 0); //设置字体透明度和颜色
			canvas.drawLine(board.getLeft() + 20, boardTop + 10, board.getRight() - 20, boardTop + 10, paint);
		}
		
		//画分数, 关卡和剩余机会次数
		if(propTime[3] > 0)drawText(score + "", screenWidth / 2, 45, canvas, paint, Color.YELLOW, 40);
		else drawText(score + "", screenWidth / 2, 45, canvas, paint, Color.BLACK, 40);
		drawText("S" + "    " + Screen.screenId, screenWidth - 160, 45, canvas, paint, Color.BLACK, 40);
		drawText("L" + "    " + lifeNum, 160, 45, canvas, paint, Color.BLACK, 40);
		
		//绘制暂停/结束等信息
		if(gameState == 1){
			drawText("按菜单键 开始/暂停 游戏", screenWidth / 2f, boardTop - 200, canvas, paint, Color.WHITE, 20);
		}
		if(gameState == 3){
			drawText("GAME OVER", screenWidth / 2f, boardTop - 200, canvas, paint, Color.RED, 50);
		}
		
		//绘制道具
		for(Prop p : propList)p.drawPropBitMap(canvas, paint);
		
		//绘制子弹
		for(Prop p: bulletList)p.drawPropBitMap(canvas, paint);
		
		// 解锁并提交画布
		if(canvas == null)return;
		holder.unlockCanvasAndPost(canvas);
	}
	
	/**
	 * 绘制游戏中需要显示的文本
	 * @param text
	 * @param x
	 * @param y
	 * @param canvas
	 * @param paint
	 * @param color
	 * @param fontSize
	 */
	private void drawText(String text, float x, float y, Canvas canvas, Paint paint, int color, int fontSize){
		int len = text.length();
		if(len == 4)text = "0" + text;
		else if(len == 3)text = "00" + text;
		else if(len == 2)text = "000" + text;
		else if(len == 1)text = "0000" + text;
		paint.setColor(color);
		paint.setFakeBoldText(true); //设置粗体
		paint.setTextSize(fontSize); //设置字体大小
		paint.setTextAlign(Align.CENTER);
		paint.setShadowLayer(0, 0, 0, 0);
		canvas.drawText(text, x, y, paint);
	}
	
	/**
	 * 碰撞检测
	 */
	public void hitCheck(){
		//所有小球的碰撞检测
		for(Iterator<Ball> it = ballList.iterator(); it.hasNext(); ){
			Ball b = it.next();
			if(ballHitCheck(b)){ //碰到下壁, 这颗小球消失或者游戏者损失一条命
				if(ballList.size() == 1)lifeDie();
				else it.remove();
			}else if(isBrickEmpty)return; //这个if判断极其重要,如果没这个判断则在调用nextScreen()函数后会产生异常错误,原因是ballList清空了
		}
		
		//判断子弹与砖块的碰撞
		for(Iterator<Prop> it = bulletList.iterator(); it.hasNext(); ){
			boolean flag = false;
			Prop p = it.next();
			for (Brick[] bL : brickList) {
				for(Brick b : bL) {
					if(!b.isExist())continue;
					if(p.getX() + p.getR() >= b.getLeft() && p.getX() - p.getR() <= b.getRight()){
						if(p.getY() - p.getR() <= b.getBottom()){
							flag = true;
							if((!b.isDisap() && propTime[10] <= 0) || propTime[9] > 0)continue;
							b.setExist(false);
							score += b.getScore();
							if(propTime[3] > 0)score += b.getScore();
							//随机在该位置产生道具
							isExistProp(b.getLeft(), b.getTop());
						}
					}
					if(flag)break;
				}
				if(flag)break;
			}
			if(flag)it.remove();
			else if(p.getY() - p.getR() <= wallTop)it.remove();
		}
		
		//判断道具与挡板碰撞
		int flag = 0;
		for(Iterator<Prop> it = propList.iterator(); it.hasNext(); ){
			Prop p = it.next();
			flag = hitBrick(p, board);
			if(flag != 0){
				int propId = p.getPropId();
				if(propId == 0){ //短板变长
					if(propTime[0] > 0)board = boardLong2; //挡板二次变长
					else board = boardLong1; //挡板第一次变长
					segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //设置挡板分段后每段的长度
					propTime[1] = 0;
				}
				else if(propId == 1){ //挡板变短
					board = boardShort;
					segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //设置挡板分段后每段的长度
					propTime[0] = 0;
				}
				else if(propId == 2){ //叠加2个主球,如果已有副球则先清空副球
					Ball b = null, newBall = null;
					if(propTime[11] > 0){
						b = ballList.get(0);
						newBall = ball.copy();
						newBall.setPos(b.getLeft(), b.getTop(), b.getX(), b.getY());
						newBall.hitCorner(b.getxOffset(), b.getyOffset());
						ballList.clear();
						ballList.add(newBall);
					}
					b = ballList.get(0);
					for(int i = 0; i < 2; ++i){
						b = b.copy();
						ballList.add(b);
					}
				}
				else if(propId == 11){ //叠加10个副球,如果已有主球则先清空主球
					Ball b = null, newBall = null;
					if(propTime[11] <= 0){
						b = ballList.get(0);
						newBall = ball2.copy();
						newBall.setPos(b.getLeft(), b.getTop(), b.getX(), b.getY());
						newBall.hitCorner(b.getxOffset(), b.getyOffset());
						ballList.clear();
						ballList.add(newBall);
					}
					b = ballList.get(0);
					for(int i = 0; i < 9; ++i){
						b = b.copy();
						ballList.add(b);
					}
				}else if(propId == 7){ //消除所有道具效果
					for(int i = 0; i < propTime.length; ++i)if(propTime[i] > 0)propTime[i] = 20; //不能直接赋值为0,有些道具效果消除是减去20之后在判断的
				}
				if(propId != 7)
					propTime[propId] = 10000; //设定道具效果时间为10s
				it.remove();
			}else if(p.getY() + p.getR() >= wallBottom){ //碰到下壁
				it.remove();
			}
		}
	}
	
	/**
	 * 小球的碰撞检测
	 * @param ball
	 * @return
	 */
	private boolean ballHitCheck(Ball ball){ //返回值只表示是否碰到下壁
		//获取小球信息
		float ballX = ball.getX();
		float ballY = ball.getY();
		float ballR = ball.getR();
		float xOffset = ball.getxOffset();
		float yOffset = ball.getyOffset();
		int flag = 0; //标记碰撞状态
		
		//判断小球与砖块碰撞
		isBrickEmpty = true; //标记砖块是否被全部打完
		for (Brick[] bL : brickList) {
			for(Brick b : bL){
				if(b.isExist()){
					if(b.isDisap())isBrickEmpty = false;
					flag = hitBrick(ball, b);
					if((flag == 1 && yOffset > 0) || (flag == -1 && yOffset < 0))yOffset *= -1;
					else if(flag == 2)xOffset *= -1;
					if(flag != 0){ //flag == 1 || flag == 2
						ball.hitCorner(xOffset, yOffset);
						if((!b.isDisap() && propTime[10] <= 0) || propTime[9] > 0)return false;
						b.setExist(false);						
						score += b.getScore();
						if(propTime[3] > 0)score += b.getScore();
						//随机在该位置产生道具
						isExistProp(b.getLeft(), b.getTop());
						return false;
					}
				}
			}
		}
		if(isBrickEmpty){nextScreen();return false;} //砖块被全部消除，进入下一关
		
		//判断与四周墙壁的碰撞
		if(ballX - ballR <= wallLeft){ //与左壁碰撞(可能是撞上左壁或者从左壁出来碰撞,所以xOffset *= -1需要进行判断)
			if(xOffset < 0)xOffset *= -1; //这里加个判断是因为ballX - ballR并不一定是 == wallLeft,所以一次行走之后下一次可能仍然满足这个条件,但是方向却不能改变
			ball.hitCorner(xOffset, yOffset);
			return false;
		}
		else if(ballY - ballR <= wallTop){ //与上壁碰撞
			if(yOffset < 0)yOffset *= -1;
			ball.hitCorner(xOffset, yOffset);
			return false;
		}
		else if(ballX + ballR >= wallRight){ //与右壁碰撞
			if(xOffset > 0)xOffset *= -1;
			ball.hitCorner(xOffset, yOffset);
			return false;
		}
		else if(ballY + ballR >= wallBottom){ //与下壁碰撞
			return true;
		}
		
		//判断与道具墙的碰撞
		if(propTime[4] > 0){
			if(ballX - ballR <= prop4Left){ //与左壁碰撞
				if(xOffset < 0)xOffset *= -1;
				ball.hitCorner(xOffset, yOffset);
				return false;
			}
			else if(ballX + ballR >= prop4Right){ //与右壁碰撞
				if(xOffset > 0)xOffset *= -1;
				ball.hitCorner(xOffset, yOffset);
				return false;
			}
		}
		
		//判断小球与挡板碰撞
		flag = hitBrick(ball, board);
		if(flag == 1){ //与挡板上面碰撞
			float tempLeft = board.getLeft();
			int L = 1, R = Ball.offsetLen, k = 0;
			while(L <= R){ //二分查找第一个>=value的位置
				k = (L + R) >> 1;
				if(k * segLen + tempLeft < ballX)L = k + 1;
				else R = k - 1;
			}
			ball.setOffsetId(L - 1); //下标从0开始
			if(propTime[5] > 0){ //小球粘贴在挡板上
				ball.setTop(boardTop - ball.getR() - ball.getR());
				ball.setY(boardTop - ball.getR());
				ball.setMove(false);
			}
			return false;
		}else if(flag == 2){ //与挡板侧面碰撞
			if(ballX <= board.getLeft())ball.hitCorner(-14, -2);
			else ball.hitCorner(14, -2);
			if(propTime[5] > 0)ball.setMove(false); //小球粘贴在挡板上
			return false;
		}
		return false;
	}
	
	/**
	 * 判断小球和砖块是否碰撞
	 * @param ball
	 * @param brick
	 * @return
	 */
	private int hitBrick(BallInterface ball, Brick brick){
		//获取小球信息
		float ballX = ball.getX();
		float ballY = ball.getY();
		float ballR = ball.getR();
		
		//获取砖块信息
		float boardLeft = brick.getLeft();
		float boardTop = brick.getTop();
		float boardRight = brick.getRight();
		float boardBottom = brick.getBottom();
		
		//判断与砖块
		if(ballY < boardTop){ //小球与砖块的上端或上端两侧顶点碰撞
			float LDist = distance(ballX, ballY, boardLeft, boardTop);
			float TDist = boardTop - ballY;
			float RDist = distance(ballX, ballY, boardRight, boardTop);
			if(LDist > TDist && (boardLeft <= ballX && ballX <= boardRight))LDist = TDist;
			if(LDist > RDist)LDist = RDist;
			if(LDist <= ballR)return 1; //表示与上端碰撞
		}else if(ballY > boardBottom){ //小球与砖块的下端或下端两侧顶点碰撞
			float LDist = distance(ballX, ballY, boardLeft, boardBottom);
			float TDist = ballY - boardBottom;
			float RDist = distance(ballX, ballY, boardRight, boardBottom);
			if(LDist > TDist && (boardLeft <= ballX && ballX <= boardRight))LDist = TDist;
			if(LDist > RDist)LDist = RDist;
			if(LDist <= ballR)return -1; //表示与下端碰撞
		}else{ //小球与砖块两侧碰撞
			//返回值为2表示与左右两侧碰撞
			if(Math.abs(ballX - boardLeft) <= ballR || Math.abs(ballX - boardRight) <= ballR)return 2;
		}
		return 0; //表示无碰撞
	}
	
	/**
	 * 求两点间距离
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private float distance(float x1, float y1, float x2, float y2){
		return (float)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	/**
	 * 移动挡板
	 */
	private void moveBoard(){
		//获取挡板位置
		float left = board.getLeft();
		float right = board.getRight();
		float center = (left + right) / 2;
		
		//获取挡板需要到达的位置
		float x = boardGoalX;
		
		float offset = 30;
		if(center > x){ //挡板左移
			if(center - x < offset)offset = center - x;
			if(left - wallLeft < offset)offset = left - wallLeft;
			offset *= -1;
		}else if(center < x){ //挡板右移
			if(x - center < offset)offset = x - center;
			if(wallRight - right < offset)offset = wallRight - right;
		}else return;
		
		if(propTime[5] > 0){ //小球粘贴在挡板上,跟着挡板移动
			for(Ball b: ballList){
				if(!b.isMove()){
					b.move(offset);;
				}
			}
		}
		
		left += offset;
		boardDefault.setLeft(left);
		boardDefault.setRight(left + boardDefault.getBitMap().getWidth());
		boardShort.setLeft(left);
		boardShort.setRight(left + boardShort.getBitMap().getWidth());
		boardLong1.setLeft(left);
		boardLong1.setRight(left + boardLong1.getBitMap().getWidth());
		boardLong2.setLeft(left);
		boardLong2.setRight(left + boardLong2.getBitMap().getWidth());
	}
	
	/**
	 * 随机产生道具
	 * @param left
	 * @param top
	 */
	private void isExistProp(float left, float top){
		int isExist = (int)(Math.random() * 5); // 1/5的概率产生道具
		if(isExist == 0){
			int propId = (int)(Math.random() * Prop.propLen);
			Prop p = new Prop(Prop.bitMap[propId], left, top, propId);
			float r= Prop.bitMap[propId].getWidth() / 2f;
			p.setR(r);
			p.setX(left + r);
			p.setY(top + r);
			propList.add(p);
		}
	}
	
	/**
	 * 游戏失败,生命减1
	 */
	private void lifeDie(){
		if(lifeNum == 0)gameState = 3;
		else{
			--lifeNum;
			resetLayout(false);
		}
	}
	
	/**
	 * 进入下一关
	 */
	private void nextScreen(){
		++Screen.screenId;
		if(Screen.screenId > Screen.screenNum)isLoops = false; //游戏通关	
		resetLayout(true);
	}
	
	/**
	 * 重置设置
	 */
	private void resetLayout(boolean isReSet){
		//重置道具		
		propList.clear(); //这里可以把原来的道具元素全部设置为空,所以可以用clear
		bulletList.clear();
		for(int i = 0; i < propTime.length; ++i)if(propTime[i] > 0)propTime[i] = 0;
				
		//重置挡板
		board = boardDefault;
		board.setPos(boardLeft, boardTop, boardRight, boardBottom); //挡板回到初始位置
		boardGoalX = (boardLeft + boardRight) / 2.0f;
		
		//重置小球
		ballList.clear(); //注意clear是把所有元素设置为null然后设置size=0,并不是清空元素
		//ballList = new ArrayList<Ball>(); //重新新建一个相当于清空,用clear的话会把ball也设置为空
		float r = ball.getR();
		ball.setPos(boardGoalX - r, boardTop - 2 * r, boardGoalX, boardTop - r);
		ball.setOffsetId(5);;
		ballList.add(ball.copy()); //注意不能直接添加ball,否则clear的时候会把ball设置为null
		
		//重置关卡背景图和砖块
		if(isReSet){
			bitMap = Screen.bgMap[Screen.screenId]; //重置背景图
			brickList = screen.getScreen(Screen.screenId); //重置关卡
			for (Brick[] bL : brickList) { //砖块设置为默认值(主要是游戏over后从第一关重新开始则需要还原brickList为默认值)
				for(Brick b : bL){
					b.resetBrick();
				}
			}
		}
		
		pauseGame();
	}
	
	/**
	 * 重置游戏
	 */
	public void resetGame(){
		score = 0; //分数清0
		lifeNum = 2; //剩余生命
		Screen.screenId = 1;
		resetLayout(true);
	}
	
	/**
	 * 开始游戏
	 */
	public void startGame(){
		gameState = 2;
	}
	
	/**
	 * 暂停游戏
	 */
	public void pauseGame(){
		gameState = 1;
		synchronized (holder) {
			draw();
		}
	}
	
	/**
	 * 响应屏幕的触摸
	 */
	@Override
	public boolean onTouchEvent(android.view.MotionEvent event) {
		if(event.getY() < boardTop || gameState == 1){
			if(gameState == 2)
				for(Ball b: ballList){
					if(!b.isMove()){
						b.setTop(b.getTop() - 10);
						b.setY(b.getY() - 10);
						b.setMove(true);
						break;
					}
				}
			return super.onTouchEvent(event);
		}
		int action = event.getAction();
		switch(action){
			case MotionEvent.ACTION_DOWN:
				boardGoalX = event.getX();
				return true;
			case MotionEvent.ACTION_MOVE:
				boardGoalX = event.getX();
				return true;
			case MotionEvent.ACTION_UP:
				boardGoalX = event.getX();
				return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		synchronized (holder) {
			draw();
		}
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isLoops = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isLoops){
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(gameState == 2){ //游戏处于运行状态则游戏画面才会改变,否则不改变(暂停状态)
				//吃道具产生的小球移动
				if(propTime[2] > 0 || propTime[11] > 0){
					propTime[2] -= 20;
					propTime[11] -= 20;
					if(propTime[2] <= 0 && propTime[11] <= 0){					
						Ball b = ballList.get(0);
						ball.setPos(b.getLeft(), b.getTop(), b.getX(), b.getY());
						ball.hitCorner(b.getxOffset(), b.getyOffset());
						ballList.clear();
						ballList.add(ball.copy());
					}
				}
				for(Ball b: ballList)b.movd(); //小球移动
				moveBoard(); //挡板移动
				for(Prop p: propList)Prop.move(p); //道具移动
				for(Prop bullet: bulletList)Prop.move(bullet, -15); //子弹移动
				hitCheck(); //碰撞检测
				
				if(propTime[0] > 0 || propTime[1] > 0){
					propTime[0] -= 20;
					propTime[1] -= 20;
					if(propTime[0] <= 0 && propTime[1] <= 0){
						board = boardDefault;
						segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //设置挡板分段后每段的长度
					}
				}
				
				if(propTime[3] > 0)propTime[3] -= 20;
				if(propTime[4] > 0){
					propTime[4] -= 20;
					if(propTime[4] <= 0){prop4Left = wallLeft; prop4Right = wallRight;}
					else {prop4Left = board.getLeft(); prop4Right = board.getRight();}
				}
				if(propTime[5] > 0){
					propTime[5] -= 20;
					if(propTime[5] <= 0){
						for(Ball b: ballList)b.setMove(true);
					}
				}
				if(propTime[6] > 0){
					propTime[6] -= 20;
					if(propTime[6] % 200 == 0){
						float r = Prop.bitMap[12].getHeight() / 2f;
						Prop bullet = new Prop(Prop.bitMap[12], board.getLeft(), boardTop - r - r, 12);
						bullet.setR(r);
						bullet.setX(board.getLeft() + r);
						bullet.setY(boardTop - r);
						bulletList.add(bullet);
						
						bullet = new Prop(Prop.bitMap[12], board.getRight(), boardTop - r - r, 12);
						bullet.setR(r);
						bullet.setX(board.getRight() - r);
						bullet.setY(boardTop - r);
						bulletList.add(bullet);
					}
				}
				if(propTime[8] > 0)propTime[8] -= 20;
				if(propTime[9] > 0)propTime[9] -= 20;
				if(propTime[10] > 0)propTime[10] -= 20;
				synchronized (holder) {
					draw();
				}
			}
		}
	}
}
