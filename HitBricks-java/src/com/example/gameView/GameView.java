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
	 * ��Ϸ��������
	 */
	
	private SurfaceHolder holder = null;//����surface���Ҳ���surface������ʾ��surfaceView�Ķ���
	private Bitmap bitMap = null; //������ͼƬ
	private Matrix matrix = null; //������ͼƬ��Ҫ��matrix
	private static int screenWidth = 0; //��Ļ���
	private static int screenHeight = 0; //��Ļ�߶�
	private int lifeNum = 2; //ʣ��������
	private int score = 0; //�÷�
	private boolean isLoops = true; //���Ƴ����Ƿ�ˢ��canvas
	public int gameState = 1; //��Ϸ״̬
	private boolean isBrickEmpty = false; //��Ǳ���ש���Ƿ��ѿ�
	
	//��
	private Paint paint = null; //����
	private Ball ball = null; //��С��
	private Ball ball2 = null; //��С��(����ĳ�ֵ��ߺ�ʹ��)
	private List<Ball> ballList = new ArrayList<Ball>(); //С���б�
	
	//����
	private Brick board; //С�򵲰�
	private Brick boardDefault = null; //Ĭ�ϵ���
	private Brick boardShort = null; //�̵���
	private Brick boardLong1 = null; //�Գ�����
	private Brick boardLong2 = null; //��������
	
	//�������ʼλ��
	private static int boardLeft = 0;
	private static int boardTop = 0;
	private static int boardRight = 0;
	private static int boardBottom = 0;
	
	//������Ҫ�����Ŀ�ĵ�
	private float boardGoalX = 0;
	
	//����ֳ�Ball.offsetLen��,ÿ�εĳ���,������С���뵲��Ӵ���λ�ò�ͬ�򵯻صķ���Ҳ��ͬ
	private float segLen = 0;
	
	//�ؿ�
	private static Screen screen = null; //�ؿ�
	private Brick[][] brickList = null; //ÿ���ؿ���ש�鲼��
	
	//����ǽ�ڵ�����
	private static int wallLeft = 0;
	private static int wallTop = 0;
	private static int wallRight = 0;
	private static int wallBottom = 0;
	
	//����
	private float prop4Left = 0; //����ǽ���
	private float prop4Right = 0; //����ǽ�ұ�
	private List<Prop> propList = new ArrayList<Prop>(); //����
	private List<Prop> bulletList = new ArrayList<Prop>(); //�ӵ�
	private int[] propTime = new int[20]; //ÿ������Ч��ʣ���ʱ��
	
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
	 * ��ʼ��
	 * @param context
	 */
	private void init(Context context){
		paint = new Paint(); //��ȡ����
		holder = getHolder(); //��ȡholder
		holder.addCallback(this); //���õ�ǰ����Ϊ������(surfaceHolder.callBack�ӿ�ʵ����)
		
		//��ȡ�ؿ�����ͼ�����ñ���ͼΪ��Ӧ��ǰ��Ļ��С
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg1)).getBitmap();
		matrix = new Matrix();
		WindowManager wm = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE));
		screenWidth = wm.getDefaultDisplay().getWidth(); //��ȡ��Ļ���
		screenHeight =wm.getDefaultDisplay().getHeight(); //��ȡ��Ļ�߶�
		int bgWidth = bitMap.getWidth(); //��ȡͼƬ�Ŀ��
		int bgHeight = bitMap.getHeight(); //��ȡͼƬ�ĸ߶�
		
		//�������ű���
		float scaleWidth = ((float)screenWidth) / bgWidth;
		float scaleHeight = ((float)screenHeight) / bgHeight;
		matrix.postScale(scaleWidth, scaleHeight);
		
		//�õ����ź��ͼƬ
		Screen.bgMap[0] = Screen.bgMap[1] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg2)).getBitmap();
		Screen.bgMap[2] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg3)).getBitmap();
		Screen.bgMap[3] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg4)).getBitmap();
		Screen.bgMap[4] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg5)).getBitmap();
		Screen.bgMap[5] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		//bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg6)).getBitmap(); //�˴�bug,Ϊʲô����bg6
		Screen.bgMap[6] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg7)).getBitmap();
		Screen.bgMap[7] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg8)).getBitmap();
		Screen.bgMap[8] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.bg9)).getBitmap();
		Screen.bgMap[9] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		//bitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.over)).getBitmap(); //�˴�bug,Ϊʲô����over
		Screen.bgMap[10] = Bitmap.createBitmap(bitMap, 0, 0, bgWidth, bgHeight, matrix, true);
		
		//���ùؿ��Ĳ���ש�鲢��ȡ�ؿ�����
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
		
		//��ǰ�ؿ�Ϊ��һ��
		brickList = screen.getScreen(Screen.screenId);
		bitMap = Screen.bgMap[Screen.screenId];
		
		//����ǽ������
		wallLeft = 33;
		wallTop =  73;
		wallRight = screenWidth - wallLeft;
		wallBottom = screenHeight - 20;
		
		//��ʼ������ǽ
		prop4Left = wallLeft;
		prop4Right = wallRight;
		
		//����ש����ʧЧ��
		Brick.bitMapDisp[0] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff1)).getBitmap();
		Brick.bitMapDisp[1] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff2)).getBitmap();
		Brick.bitMapDisp[2] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff3)).getBitmap();
		Brick.bitMapDisp[3] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff4)).getBitmap();
		Brick.bitMapDisp[4] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff5)).getBitmap();
		Brick.bitMapDisp[5] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff6)).getBitmap();
		Brick.bitMapDisp[6] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff7)).getBitmap();
		Brick.bitMapDisp[7] = ((BitmapDrawable)getResources().getDrawable(R.drawable.eff8)).getBitmap();
		
		//��ʼ��С�򵲰����Ϣ
		Bitmap boardBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.board1)).getBitmap();
		//���õ���λ��
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
		segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //���õ���ֶκ�ÿ�εĳ���
		
		//��ʼ��С�����Ϣ
		Bitmap ballBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ball)).getBitmap();
		float r = (ballBitMap.getWidth() - 14) / 2f; //r��ʾС��İ뾶
		ball = new Ball(ballBitMap, boardGoalX - r, boardTop - r - r);
		ball.setR(r);
		ball.setX(boardGoalX);
		ball.setY(boardTop - r);
		
		ballBitMap = ((BitmapDrawable)getResources().getDrawable(R.drawable.ball2)).getBitmap();
		r = (ballBitMap.getWidth() - 5) / 2f; //r��ʾС��İ뾶
		ball2 = new Ball(ballBitMap, 0, 0);
		ball2.setR(r);
		
		ballList.add(ball.copy()); //�������
		
		//��ʼ������
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
		
		//��ʼ������Ч��ʣ��ʱ��
		for(int i = 0; i < propTime.length; ++i)propTime[i] = 0;
	}
	
	/**
	 * ������ϷԪ��
	 */
	private void draw(){
		if(holder == null)return;
		Canvas canvas = holder.lockCanvas(); //����canvas������(ͬ��������,��ֹsurface���ݱ��޸�)
		if (canvas == null) return;
		
		//���Ʊ���ͼ
		canvas.drawBitmap(bitMap, 0, 0, paint);
		if(propTime[8] > 0){
			paint.setColor(Color.BLACK);
			canvas.drawRect(wallLeft, wallTop, wallRight, wallBottom + 15, paint);
		}
		
		//��ש��
		for (Brick[] bL : brickList) {
			for(Brick b : bL){
				if(propTime[8] > 0 && b.isExist())continue; //ҹ��ģʽ��ֻ��滭��ըЧ��
				if(propTime[9] > 0)b.drawBrickBitMap(Screen.bitMap[8], canvas, paint);
				else if(propTime[10] > 0)b.drawBrickBitMap(Screen.bitMap[2], canvas, paint);
				else b.drawBrickBitMap(canvas, paint);
			}
		}
		
		//��С���С�򵲰�
		for(Ball b: ballList)b.drawBallBitMap(canvas, paint);
		board.drawBrickBitMap(canvas, paint);
		
		//���Ƶ������˷����Ĺ���ǽ
		if(propTime[4] > 0){
			paint.setStyle(Paint.Style.FILL); //����ʵ��
			paint.setARGB(100, 0, 205, 205); //��������͸���Ⱥ���ɫ
			paint.setStrokeWidth(10); //���û��ʵĴ�ϸ
			canvas.drawLine(board.getLeft(), boardTop, board.getLeft(), wallTop, paint);
			canvas.drawLine(board.getRight(), boardTop, board.getRight(), wallTop, paint);
		}
		
		//���Ƶ����ϵ���ɫ,��ʾС���ճ���ڵ�����
		if(propTime[5] > 0){
			paint.setStrokeWidth(10); //���û��ʵĴ�ϸ
			paint.setARGB(100, 0, 255, 0); //��������͸���Ⱥ���ɫ
			canvas.drawLine(board.getLeft() + 20, boardTop + 10, board.getRight() - 20, boardTop + 10, paint);
		}
		
		//������, �ؿ���ʣ��������
		if(propTime[3] > 0)drawText(score + "", screenWidth / 2, 45, canvas, paint, Color.YELLOW, 40);
		else drawText(score + "", screenWidth / 2, 45, canvas, paint, Color.BLACK, 40);
		drawText("S" + "    " + Screen.screenId, screenWidth - 160, 45, canvas, paint, Color.BLACK, 40);
		drawText("L" + "    " + lifeNum, 160, 45, canvas, paint, Color.BLACK, 40);
		
		//������ͣ/��������Ϣ
		if(gameState == 1){
			drawText("���˵��� ��ʼ/��ͣ ��Ϸ", screenWidth / 2f, boardTop - 200, canvas, paint, Color.WHITE, 20);
		}
		if(gameState == 3){
			drawText("GAME OVER", screenWidth / 2f, boardTop - 200, canvas, paint, Color.RED, 50);
		}
		
		//���Ƶ���
		for(Prop p : propList)p.drawPropBitMap(canvas, paint);
		
		//�����ӵ�
		for(Prop p: bulletList)p.drawPropBitMap(canvas, paint);
		
		// �������ύ����
		if(canvas == null)return;
		holder.unlockCanvasAndPost(canvas);
	}
	
	/**
	 * ������Ϸ����Ҫ��ʾ���ı�
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
		paint.setFakeBoldText(true); //���ô���
		paint.setTextSize(fontSize); //���������С
		paint.setTextAlign(Align.CENTER);
		paint.setShadowLayer(0, 0, 0, 0);
		canvas.drawText(text, x, y, paint);
	}
	
	/**
	 * ��ײ���
	 */
	public void hitCheck(){
		//����С�����ײ���
		for(Iterator<Ball> it = ballList.iterator(); it.hasNext(); ){
			Ball b = it.next();
			if(ballHitCheck(b)){ //�����±�, ���С����ʧ������Ϸ����ʧһ����
				if(ballList.size() == 1)lifeDie();
				else it.remove();
			}else if(isBrickEmpty)return; //���if�жϼ�����Ҫ,���û����ж����ڵ���nextScreen()�����������쳣����,ԭ����ballList�����
		}
		
		//�ж��ӵ���ש�����ײ
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
							//����ڸ�λ�ò�������
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
		
		//�жϵ����뵲����ײ
		int flag = 0;
		for(Iterator<Prop> it = propList.iterator(); it.hasNext(); ){
			Prop p = it.next();
			flag = hitBrick(p, board);
			if(flag != 0){
				int propId = p.getPropId();
				if(propId == 0){ //�̰�䳤
					if(propTime[0] > 0)board = boardLong2; //������α䳤
					else board = boardLong1; //�����һ�α䳤
					segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //���õ���ֶκ�ÿ�εĳ���
					propTime[1] = 0;
				}
				else if(propId == 1){ //������
					board = boardShort;
					segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //���õ���ֶκ�ÿ�εĳ���
					propTime[0] = 0;
				}
				else if(propId == 2){ //����2������,������и���������ո���
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
				else if(propId == 11){ //����10������,����������������������
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
				}else if(propId == 7){ //�������е���Ч��
					for(int i = 0; i < propTime.length; ++i)if(propTime[i] > 0)propTime[i] = 20; //����ֱ�Ӹ�ֵΪ0,��Щ����Ч�������Ǽ�ȥ20֮�����жϵ�
				}
				if(propId != 7)
					propTime[propId] = 10000; //�趨����Ч��ʱ��Ϊ10s
				it.remove();
			}else if(p.getY() + p.getR() >= wallBottom){ //�����±�
				it.remove();
			}
		}
	}
	
	/**
	 * С�����ײ���
	 * @param ball
	 * @return
	 */
	private boolean ballHitCheck(Ball ball){ //����ֵֻ��ʾ�Ƿ������±�
		//��ȡС����Ϣ
		float ballX = ball.getX();
		float ballY = ball.getY();
		float ballR = ball.getR();
		float xOffset = ball.getxOffset();
		float yOffset = ball.getyOffset();
		int flag = 0; //�����ײ״̬
		
		//�ж�С����ש����ײ
		isBrickEmpty = true; //���ש���Ƿ�ȫ������
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
						//����ڸ�λ�ò�������
						isExistProp(b.getLeft(), b.getTop());
						return false;
					}
				}
			}
		}
		if(isBrickEmpty){nextScreen();return false;} //ש�鱻ȫ��������������һ��
		
		//�ж�������ǽ�ڵ���ײ
		if(ballX - ballR <= wallLeft){ //�������ײ(������ײ����ڻ��ߴ���ڳ�����ײ,����xOffset *= -1��Ҫ�����ж�)
			if(xOffset < 0)xOffset *= -1; //����Ӹ��ж�����ΪballX - ballR����һ���� == wallLeft,����һ������֮����һ�ο�����Ȼ�����������,���Ƿ���ȴ���ܸı�
			ball.hitCorner(xOffset, yOffset);
			return false;
		}
		else if(ballY - ballR <= wallTop){ //���ϱ���ײ
			if(yOffset < 0)yOffset *= -1;
			ball.hitCorner(xOffset, yOffset);
			return false;
		}
		else if(ballX + ballR >= wallRight){ //���ұ���ײ
			if(xOffset > 0)xOffset *= -1;
			ball.hitCorner(xOffset, yOffset);
			return false;
		}
		else if(ballY + ballR >= wallBottom){ //���±���ײ
			return true;
		}
		
		//�ж������ǽ����ײ
		if(propTime[4] > 0){
			if(ballX - ballR <= prop4Left){ //�������ײ
				if(xOffset < 0)xOffset *= -1;
				ball.hitCorner(xOffset, yOffset);
				return false;
			}
			else if(ballX + ballR >= prop4Right){ //���ұ���ײ
				if(xOffset > 0)xOffset *= -1;
				ball.hitCorner(xOffset, yOffset);
				return false;
			}
		}
		
		//�ж�С���뵲����ײ
		flag = hitBrick(ball, board);
		if(flag == 1){ //�뵲��������ײ
			float tempLeft = board.getLeft();
			int L = 1, R = Ball.offsetLen, k = 0;
			while(L <= R){ //���ֲ��ҵ�һ��>=value��λ��
				k = (L + R) >> 1;
				if(k * segLen + tempLeft < ballX)L = k + 1;
				else R = k - 1;
			}
			ball.setOffsetId(L - 1); //�±��0��ʼ
			if(propTime[5] > 0){ //С��ճ���ڵ�����
				ball.setTop(boardTop - ball.getR() - ball.getR());
				ball.setY(boardTop - ball.getR());
				ball.setMove(false);
			}
			return false;
		}else if(flag == 2){ //�뵲�������ײ
			if(ballX <= board.getLeft())ball.hitCorner(-14, -2);
			else ball.hitCorner(14, -2);
			if(propTime[5] > 0)ball.setMove(false); //С��ճ���ڵ�����
			return false;
		}
		return false;
	}
	
	/**
	 * �ж�С���ש���Ƿ���ײ
	 * @param ball
	 * @param brick
	 * @return
	 */
	private int hitBrick(BallInterface ball, Brick brick){
		//��ȡС����Ϣ
		float ballX = ball.getX();
		float ballY = ball.getY();
		float ballR = ball.getR();
		
		//��ȡש����Ϣ
		float boardLeft = brick.getLeft();
		float boardTop = brick.getTop();
		float boardRight = brick.getRight();
		float boardBottom = brick.getBottom();
		
		//�ж���ש��
		if(ballY < boardTop){ //С����ש����϶˻��϶����ඥ����ײ
			float LDist = distance(ballX, ballY, boardLeft, boardTop);
			float TDist = boardTop - ballY;
			float RDist = distance(ballX, ballY, boardRight, boardTop);
			if(LDist > TDist && (boardLeft <= ballX && ballX <= boardRight))LDist = TDist;
			if(LDist > RDist)LDist = RDist;
			if(LDist <= ballR)return 1; //��ʾ���϶���ײ
		}else if(ballY > boardBottom){ //С����ש����¶˻��¶����ඥ����ײ
			float LDist = distance(ballX, ballY, boardLeft, boardBottom);
			float TDist = ballY - boardBottom;
			float RDist = distance(ballX, ballY, boardRight, boardBottom);
			if(LDist > TDist && (boardLeft <= ballX && ballX <= boardRight))LDist = TDist;
			if(LDist > RDist)LDist = RDist;
			if(LDist <= ballR)return -1; //��ʾ���¶���ײ
		}else{ //С����ש��������ײ
			//����ֵΪ2��ʾ������������ײ
			if(Math.abs(ballX - boardLeft) <= ballR || Math.abs(ballX - boardRight) <= ballR)return 2;
		}
		return 0; //��ʾ����ײ
	}
	
	/**
	 * ����������
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
	 * �ƶ�����
	 */
	private void moveBoard(){
		//��ȡ����λ��
		float left = board.getLeft();
		float right = board.getRight();
		float center = (left + right) / 2;
		
		//��ȡ������Ҫ�����λ��
		float x = boardGoalX;
		
		float offset = 30;
		if(center > x){ //��������
			if(center - x < offset)offset = center - x;
			if(left - wallLeft < offset)offset = left - wallLeft;
			offset *= -1;
		}else if(center < x){ //��������
			if(x - center < offset)offset = x - center;
			if(wallRight - right < offset)offset = wallRight - right;
		}else return;
		
		if(propTime[5] > 0){ //С��ճ���ڵ�����,���ŵ����ƶ�
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
	 * �����������
	 * @param left
	 * @param top
	 */
	private void isExistProp(float left, float top){
		int isExist = (int)(Math.random() * 5); // 1/5�ĸ��ʲ�������
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
	 * ��Ϸʧ��,������1
	 */
	private void lifeDie(){
		if(lifeNum == 0)gameState = 3;
		else{
			--lifeNum;
			resetLayout(false);
		}
	}
	
	/**
	 * ������һ��
	 */
	private void nextScreen(){
		++Screen.screenId;
		if(Screen.screenId > Screen.screenNum)isLoops = false; //��Ϸͨ��	
		resetLayout(true);
	}
	
	/**
	 * ��������
	 */
	private void resetLayout(boolean isReSet){
		//���õ���		
		propList.clear(); //������԰�ԭ���ĵ���Ԫ��ȫ������Ϊ��,���Կ�����clear
		bulletList.clear();
		for(int i = 0; i < propTime.length; ++i)if(propTime[i] > 0)propTime[i] = 0;
				
		//���õ���
		board = boardDefault;
		board.setPos(boardLeft, boardTop, boardRight, boardBottom); //����ص���ʼλ��
		boardGoalX = (boardLeft + boardRight) / 2.0f;
		
		//����С��
		ballList.clear(); //ע��clear�ǰ�����Ԫ������ΪnullȻ������size=0,���������Ԫ��
		//ballList = new ArrayList<Ball>(); //�����½�һ���൱�����,��clear�Ļ����ballҲ����Ϊ��
		float r = ball.getR();
		ball.setPos(boardGoalX - r, boardTop - 2 * r, boardGoalX, boardTop - r);
		ball.setOffsetId(5);;
		ballList.add(ball.copy()); //ע�ⲻ��ֱ�����ball,����clear��ʱ����ball����Ϊnull
		
		//���ùؿ�����ͼ��ש��
		if(isReSet){
			bitMap = Screen.bgMap[Screen.screenId]; //���ñ���ͼ
			brickList = screen.getScreen(Screen.screenId); //���ùؿ�
			for (Brick[] bL : brickList) { //ש������ΪĬ��ֵ(��Ҫ����Ϸover��ӵ�һ�����¿�ʼ����Ҫ��ԭbrickListΪĬ��ֵ)
				for(Brick b : bL){
					b.resetBrick();
				}
			}
		}
		
		pauseGame();
	}
	
	/**
	 * ������Ϸ
	 */
	public void resetGame(){
		score = 0; //������0
		lifeNum = 2; //ʣ������
		Screen.screenId = 1;
		resetLayout(true);
	}
	
	/**
	 * ��ʼ��Ϸ
	 */
	public void startGame(){
		gameState = 2;
	}
	
	/**
	 * ��ͣ��Ϸ
	 */
	public void pauseGame(){
		gameState = 1;
		synchronized (holder) {
			draw();
		}
	}
	
	/**
	 * ��Ӧ��Ļ�Ĵ���
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
			if(gameState == 2){ //��Ϸ��������״̬����Ϸ����Ż�ı�,���򲻸ı�(��ͣ״̬)
				//�Ե��߲�����С���ƶ�
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
				for(Ball b: ballList)b.movd(); //С���ƶ�
				moveBoard(); //�����ƶ�
				for(Prop p: propList)Prop.move(p); //�����ƶ�
				for(Prop bullet: bulletList)Prop.move(bullet, -15); //�ӵ��ƶ�
				hitCheck(); //��ײ���
				
				if(propTime[0] > 0 || propTime[1] > 0){
					propTime[0] -= 20;
					propTime[1] -= 20;
					if(propTime[0] <= 0 && propTime[1] <= 0){
						board = boardDefault;
						segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen; //���õ���ֶκ�ÿ�εĳ���
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
