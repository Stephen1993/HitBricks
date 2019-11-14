import Ball from './ball';
import Brick from './brick';
import Prop from './prop';
import Screen from './screen';

let instance = null;
let ctx = null;
let screenWidth = window.innerWidth; // 屏幕宽度
let screenHeight = window.innerHeight; // 屏幕高度
let bitMap = null; //主界面图片
let lifeNum = 2; //剩余生命数
let score = 0; //得分
let gameState = 1; //游戏状态
let isBrickEmpty = false; //标记本关砖块是否已空

//球
let ball = null; //主小球
let ball2 = null; //副小球(吃了某种道具后使用)
let ballList = []; //小球列表
let ballBitMap = null;
let ballBitMap2 = null;

//挡板
let board; //小球挡板
let boardDefault = null; //默认挡板
let boardShort = null; //短挡板
let boardLong1 = null; //稍长挡板
let boardLong2 = null; //更长挡板
let boardBitMap1 = null;
let boardBitMap2 = null;
let boardBitMap3 = null;
let boardBitMap4 = null;

//挡板的起始位置
let boardLeft = 0;
let boardTop = 0;
let boardRight = 0;
let boardBottom = 0;

//挡板需要到达的目的点
let boardGoalX = 0;

//挡板分成Ball.offsetLen段,每段的长度,作用是小球与挡板接触的位置不同则弹回的方向也不同
let segLen = 0;

//关卡
let screen = null; //关卡
let brickList = null; //每个关卡的砖块布局

//四周墙壁的坐标
let wallLeft = 0;
let wallTop = 0;
let wallRight = 0;
let wallBottom = 0;

//道具
let prop4Left = 0; //道具墙左壁
let prop4Right = 0; //道具墙右壁
let propList = []; //道具
let bulletList = []; //子弹
let propTime = new Array(20); //每个道具效果剩余的时间

export default class GameView {
	/**
	 * 
	 * @author chenyang
	 * 游戏主界面类
	 */
  static loadSuc = 16;

  constructor(ctx_) {
    if (instance) {
      instance.resetGame();
      return instance;
    }
    instance = this;
    ctx = ctx_;
    this.initEvent();
    this.loadImg();
    // this.initData(); // 由于加载图片慢，无法获取图片数据，所以不能直接初始化数据
  }

  static loadSucCal() {
    GameView.loadSuc--;
  }

  touchFun(e) { // 触摸回调函数
    e.preventDefault()
    // console.log('touchstart');
    let event = e.touches[0];
    if (gameState == 1 && 
    event.clientX >= screenWidth/2 - 30 && 
    event.clientX <= screenWidth/2 + 30 &&
    event.clientY >= boardTop-120 &&
    event.clientY <= boardTop-60) {
      gameState = 2;
      return;
    }
    if (gameState == 3 &&
      event.clientX >= screenWidth / 2 - 30 &&
      event.clientX <= screenWidth / 2 + 30 &&
      event.clientY >= boardTop - 120 &&
      event.clientY <= boardTop - 60) {
      this.resetGame();
      return;
    }
    if (gameState == 2) {
      if (event.clientY < boardTop) { // 被挡板粘住的小球可以弹出
        for (let b of ballList) {
          if (!b.getIsMove()) {
            b.setTop(b.getTop() - 10);
            b.setY(b.getY() - 10);
            b.setMove(true);
            break;
          }
        }
      }
      if (event.clientY >= boardTop) {
        boardGoalX = event.clientX;
      }
    }
  }

  initEvent() { // 初始化监听事件
    canvas.addEventListener('touchstart', this.touchFun.bind(this));
    canvas.addEventListener('touchmove', this.touchFun.bind(this));
    // canvas.addEventListener('touchend', this.touchFun.bind(this));
  }

  loadImg() { // 加载图片
    //加载小球挡板背景图
    boardBitMap1 = new Image();
    boardBitMap1.src = "images/hitbricks/board1.png";
    boardBitMap1.onload = GameView.loadSucCal;

    boardBitMap2 = new Image();
    boardBitMap2.src = "images/hitbricks/board2.png";
    boardBitMap2.onload = GameView.loadSucCal;

    boardBitMap3 = new Image();
    boardBitMap3.src = "images/hitbricks/board3.png";
    boardBitMap3.onload = GameView.loadSucCal;

    boardBitMap4 = new Image();
    boardBitMap4.src = "images/hitbricks/board4.png";
    boardBitMap4.onload = GameView.loadSucCal;

    //加载小球背景图
    ballBitMap = new Image();
    ballBitMap.src = "images/hitbricks/ball.png";
    ballBitMap.onload = GameView.loadSucCal;

    ballBitMap2 = new Image();
    ballBitMap2.src = "images/hitbricks/ball2.png";
    ballBitMap2.onload = GameView.loadSucCal;

    //加载关卡背景图
    Screen.loadBgImg();

    //加载砖块背景图
    Screen.loadBrickImg();

    //加载砖块消失背景图
    Brick.loadBrickDisp();

    //加载道具背景图
    Prop.loadPropImg();
  }

	/**
	 * 初始化数据
	 */
  initData() {
    screen = new Screen();

    //当前关卡为第一关
    brickList = screen.getScreen(Screen.screenId);
    bitMap = Screen.bgMap[Screen.screenId];

    //设置墙壁坐标
    wallLeft = 20;
    wallTop = 55;
    wallRight = screenWidth - wallLeft;
    wallBottom = screenHeight - 20;

    //初始化道具墙
    prop4Left = wallLeft;
    prop4Right = wallRight;

    //设置挡板位置和其他挡板信息
    let per = 5.5;
    boardLeft = (screenWidth - boardBitMap1.width) / 2;
    boardTop = screenHeight - screenHeight / per;
    boardRight = boardLeft + boardBitMap1.width;
    boardBottom = boardTop + boardBitMap1.height;

    boardDefault = new Brick(boardBitMap1, boardLeft, boardTop);
    boardDefault.setRight(boardRight);
    boardDefault.setBottom(boardBottom);

    board = boardDefault;
    boardGoalX = (boardLeft + boardRight) / 2.0;
    segLen = (boardRight - boardLeft) / Ball.offsetLen;

    let board2Left = (screenWidth - boardBitMap2.width) / 2;
    let board2Top = screenHeight - screenHeight / per;
    let board2Right = board2Left + boardBitMap2.width;
    let board2Bottom = board2Top + boardBitMap2.height;

    boardLong2 = new Brick(boardBitMap4, board2Left, boardTop);
    boardLong2.setRight(board2Left + boardBitMap2.width);
    boardLong2.setBottom(board2Bottom);

    let board3Left = (screenWidth - boardBitMap3.width) / 2;
    let board3Top = screenHeight - screenHeight / per;
    let board3Right = board3Left + boardBitMap3.width;
    let board3Bottom = board3Top + boardBitMap3.height;

    boardShort = new Brick(boardBitMap3, board3Left, board3Top);
    boardShort.setRight(board3Left + boardBitMap3.width);
    boardShort.setBottom(board3Bottom);

    let board4Left = (screenWidth - boardBitMap4.width) / 2;
    let board4Top = screenHeight - screenHeight / per;
    let board4Right = board4Left + boardBitMap4.width;
    let board4Bottom = board4Top + boardBitMap4.height;

    boardLong1 = new Brick(boardBitMap4, board4Left, boardTop);
    boardLong1.setRight(board4Left + boardBitMap4.width);
    boardLong1.setBottom(board4Bottom);

    let r = (ballBitMap.height - 7) / 2.0;
    ball = new Ball(ballBitMap, boardGoalX - r, boardTop - 2 * r);
    ball.setR(r);
    ball.setX(boardGoalX);
    ball.setY(boardTop - r);
    ballList.push(ball.copy()); //添加主球

    let r2 = (ballBitMap2.height - 3) / 2.0;
    ball2 = new Ball(ballBitMap2, 0, 0);
    ball2.setR(r2);

    //初始化道具效果剩余时间
    for (let i = 0; i < propTime.length; ++i) propTime[i] = 0;
  }

	/**
	 * 绘制游戏元素
	 */
  render() {
    //绘制背景图
    ctx.drawImage(
      bitMap,
      0,
      0,
      screenWidth,
      screenHeight);

    if (propTime[8] > 0) {
      ctx.fillStyle = "black";
      ctx.fillRect(wallLeft, wallTop, wallRight, wallBottom + 15);
    }

    //画砖块
    for (let bl of brickList) {
      for (let b of bl) {
        if (propTime[8] > 0 && b.getIsExist()) continue; //夜间模式则只需绘画爆炸效果
        if (propTime[9] > 0) b.drawBrickBitMap(Screen.bitMap[8], ctx);
        else if (propTime[10] > 0) b.drawBrickBitMap(Screen.bitMap[2], ctx);
        else b.drawBrickBitMap(ctx);
      }
    }

    //画小球和小球挡板
    for (let b of ballList) b.drawBallBitMap(ctx);
    board.drawBrickBitMap(ctx);

    //绘制挡板两端发出的光束墙
    if (propTime[4] > 0) {
      ctx.beginPath();
      ctx.strokeStyle = 'rgb(0, 205, 205)'; //设置字体透明度和颜色
      ctx.lineWidth = 5; //设置画笔的粗细
      ctx.moveTo(board.getLeft(), boardTop);
      ctx.lineTo(board.getLeft(), wallTop);
      ctx.stroke();

      ctx.beginPath();
      ctx.moveTo(board.getRight(), boardTop);
      ctx.lineTo(board.getRight(), wallTop);
      ctx.stroke();

      //ctx.drawLine(board.getLeft(), boardTop, board.getLeft(), wallTop);
      //ctx.drawLine(board.getRight(), boardTop, board.getRight(), wallTop);
    }

    //绘制挡板上的颜色,表示小球可粘贴在挡板上
    if (propTime[5] > 0) {
      ctx.beginPath();
      ctx.strokeStyle = 'rgb(0, 255, 255)'; //设置字体透明度和颜色
      ctx.lineWidth = 5; //设置画笔的粗细
      ctx.moveTo(board.getLeft() + 10, boardTop + 5);
      ctx.lineTo(board.getRight() - 10, boardTop + 5);
      ctx.stroke();
    }

    //画分数, 关卡和剩余机会次数
    if (propTime[3] > 0) this.drawText(("0000" + score).slice(-4) + "(双倍)", screenWidth / 2, 55, 'yellow', 18);
    else this.drawText(("0000" + score).slice(-4), screenWidth / 2, 55, 'red', 18);
    this.drawText("关卡:" + Screen.screenId, screenWidth/2 - 105, 55, 'rgb(56, 206, 210', 18);
    this.drawText("生命:" + lifeNum, screenWidth/2 + 105, 55, 'rgb(56, 206, 210', 18);

    //绘制暂停/结束等信息
    if (gameState == 1) {
      ctx.beginPath();
      ctx.arc(screenWidth / 2.0, boardTop - 90, 30, 0, 2 * Math.PI);
      ctx.fillStyle = 'rgb(56, 206, 210)';
      ctx.shadowOffsetY = 2;
      ctx.shadowColor = 'rgba(0,0,0,0.54)';
      ctx.fill();
      ctx.fillStyle = 'white';
      ctx.textAlign = 'center';
      ctx.font = '20px sans-serif';
      ctx.shadowOffsetY = 1;
      ctx.fillText("开始", screenWidth / 2.0, boardTop - 82);
    }
    if (gameState == 3) {
      this.drawText("GAME OVER", screenWidth / 2.0, boardTop - 30, 'red', 30);
      ctx.beginPath();
      ctx.arc(screenWidth / 2.0, boardTop - 90, 30, 0, 2 * Math.PI);
      ctx.fillStyle = 'rgb(56, 206, 210)';
      ctx.shadowOffsetY = 2;
      ctx.shadowColor = 'rgba(0,0,0,0.54)';
      ctx.fill();
      ctx.fillStyle = 'white';
      ctx.textAlign = 'center';
      ctx.font = '20px sans-serif';
      ctx.shadowOffsetY = 1;
      ctx.fillText("重玩", screenWidth / 2.0, boardTop - 82);
    }

    //绘制道具
    for (let p of propList) p.drawPropBitMap(ctx);

    //绘制子弹
    for (let p of bulletList) p.drawPropBitMap(ctx);
  }

	/**
	 * 绘制游戏中需要显示的文本
	 * @param text
	 * @param x
	 * @param y
	 * @param color
	 * @param fontSize
	 */
  drawText(text, x, y, color, fontSize) {
    ctx.fillStyle = color;
    ctx.font = fontSize + 'px sans-serif';
    ctx.textAlign = 'center';
    ctx.fillText(text, x, y);
  }

	/**
	 * 碰撞检测
	 */
  hitCheck() {
    //所有小球的碰撞检测
    for (let i = ballList.length - 1; i >= 0; --i) {
      let b = ballList[i];
      if (this.ballHitCheck(b)) { //碰到下壁, 这颗小球消失或者游戏者损失一条命
        if (ballList.length == 1) this.lifeDie();
        else ballList.splice(i, 1);
      } else if (isBrickEmpty) return; //这个if判断极其重要,如果没这个判断则在调用nextScreen()函数后会产生异常错误,原因是ballList清空了
    }

    //判断子弹与砖块的碰撞
    for (let i = bulletList.length - 1; i >= 0; --i) {
      let p = bulletList[i];
      let flag = false;
      for (let bl of brickList) {
        for (let b of bl) {
          if (!b.getIsExist()) continue;
          if (p.getX() + p.getR() >= b.getLeft() && p.getX() - p.getR() <= b.getRight()) {
            if (p.getY() - p.getR() <= b.getBottom()) {
              flag = true;
              if ((!b.getIsDisap() && propTime[10] <= 0) || propTime[9] > 0) continue;
              b.setExist(false);
              score += b.getScore();
              if (propTime[3] > 0) score += b.getScore();
              //随机在该位置产生道具
              this.isExistProp(b.getLeft(), b.getTop());
            }
          }
          if (flag) break;
        }
        if (flag) break;
      }
      if (flag) bulletList.splice(i, 1);
      else if (p.getY() - p.getR() <= wallTop) bulletList.splice(i, 1);
    }

    //判断道具与挡板碰撞
    let flag = 0;
    for (let i = propList.length - 1; i >= 0; --i) {
      let p = propList[i];
      flag = this.hitBrick(p, board);
      if (flag != 0) {
        let propId = p.getPropId();
        if (propId == 0) { //短板变长
          if (propTime[0] > 0) board = boardLong2; //挡板二次变长
          else board = boardLong1; //挡板第一次变长
          segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen;
          propTime[1] = 0;
        }
        else if (propId == 1) { //挡板变短
          board = boardShort;
          segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen;
          propTime[0] = 0;
        }
        else if (propId == 2) { //叠加2个主球,如果已有副球则先清空副球
          let b = null, newBall = null;
          if (propTime[11] > 0) {
            b = ballList[0];
            newBall = ball.copy();
            newBall.setPos(b.getLeft(), b.getTop(), b.getX(), b.getY());
            newBall.hitCorner(b.getxOffset(), b.getyOffset());
            ballList = [newBall];
            propTime[11] = 0;
          }
          b = ballList[0];
          for (let i = 0; i < 2; ++i) {
            b = b.copy();
            ballList.push(b);
          }
        }
        else if (propId == 11) { //叠加10个副球,如果已有主球则先清空主球
          let b = null, newBall = null;
          if (propTime[11] <= 0) {
            b = ballList[0];
            newBall = ball2.copy();
            newBall.setPos(b.getLeft(), b.getTop(), b.getX(), b.getY());
            newBall.hitCorner(b.getxOffset(), b.getyOffset());
            ballList = [newBall];
            propTime[2] = 0;
          }
          b = ballList[0];
          for (let i = 0; i < 9; ++i) {
            b = b.copy();
            ballList.push(b);
          }
        } else if (propId == 7) { //消除所有道具效果
          for (let i = 0; i < propTime.length; ++i) if (propTime[i] > 0) propTime[i] = 20; //不能直接赋值为0,有些道具效果消除是减去20之后在判断的
        }
        if (propId != 7)
          propTime[propId] = 10000; //设定道具效果时间为10s
        propList.splice(i, 1); // 删除该元素
      } else if (p.getY() + p.getR() >= wallBottom) { //碰到下壁
        propList.splice(i, 1); // 删除该元素
      }
    }
  }

	/**
	 * 小球的碰撞检测
	 * @param ball
	 * @return
	 */
  ballHitCheck(ball) { //返回值只表示是否碰到下壁
    //获取小球信息
    let ballX = ball.getX();
    let ballY = ball.getY();
    let ballR = ball.getR();
    let xOffset = ball.getxOffset();
    let yOffset = ball.getyOffset();
    let flag = 0; //标记碰撞状态

    //判断小球与砖块碰撞
    isBrickEmpty = true; //标记砖块是否被全部打完
    for (let bl of brickList) {
      for (let b of bl) {
        if (b.getIsExist()) {
          if (b.getIsDisap()) isBrickEmpty = false;
          flag = this.hitBrick(ball, b);
          if ((flag == 1 && yOffset > 0) || (flag == -1 && yOffset < 0)) yOffset *= -1;
          else if (flag == 2) xOffset *= -1;
          if (flag != 0) { //flag == 1 || flag == 2
            ball.hitCorner(xOffset, yOffset);
            if ((!b.getIsDisap() && propTime[10] <= 0) || propTime[9] > 0) return false;
            b.setExist(false);
            score += b.getScore();
            if (propTime[3] > 0) score += b.getScore();
            //随机在该位置产生道具
            this.isExistProp(b.getLeft(), b.getTop());
            return false;
          }
        }
      }
    }
    if (isBrickEmpty) { this.nextScreen(); return false; } //砖块被全部消除，进入下一关

    //判断与四周墙壁的碰撞
    if (ballX - ballR <= wallLeft) { //与左壁碰撞(可能是撞上左壁或者从左壁出来碰撞,所以xOffset *= -1需要进行判断)
      if (xOffset < 0) xOffset *= -1; //这里加个判断是因为ballX - ballR并不一定是 == wallLeft,所以一次行走之后下一次可能仍然满足这个条件,但是方向却不能改变
      ball.hitCorner(xOffset, yOffset);
      return false;
    }
    else if (ballY - ballR <= wallTop) { //与上壁碰撞
      if (yOffset < 0) yOffset *= -1;
      ball.hitCorner(xOffset, yOffset);
      return false;
    }
    else if (ballX + ballR >= wallRight) { //与右壁碰撞
      if (xOffset > 0) xOffset *= -1;
      ball.hitCorner(xOffset, yOffset);
      return false;
    }
    else if (ballY + ballR >= wallBottom) { //与下壁碰撞
      return true;
    }

    //判断与道具墙的碰撞
    if (propTime[4] > 0) {
      if (ballX - ballR <= prop4Left) { //与左壁碰撞
        if (xOffset < 0) xOffset *= -1;
        ball.hitCorner(xOffset, yOffset);
        return false;
      }
      else if (ballX + ballR >= prop4Right) { //与右壁碰撞
        if (xOffset > 0) xOffset *= -1;
        ball.hitCorner(xOffset, yOffset);
        return false;
      }
    }

    //判断小球与挡板碰撞
    flag = this.hitBrick(ball, board);
    if (flag == 1) { //与挡板上面碰撞
      let tempLeft = board.getLeft();
      let L = 1, R = Ball.offsetLen, k = 0;
      while (L <= R) { //二分查找第一个>=value的位置
        k = (L + R) >> 1;
        if (k * segLen + tempLeft < ballX) L = k + 1;
        else R = k - 1;
      }
      ball.setOffsetId(L - 1); //下标从0开始
      if (propTime[5] > 0) { //小球粘贴在挡板上
        ball.setTop(boardTop - ball.getR() - ball.getR());
        ball.setY(boardTop - ball.getR());
        ball.setMove(false);
      }
      return false;
    } else if (flag == 2) { //与挡板侧面碰撞
      if (ballX <= board.getLeft()) ball.hitCorner(-14, -2);
      else ball.hitCorner(14, -2);
      if (propTime[5] > 0) ball.setMove(false); //小球粘贴在挡板上
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
  hitBrick(ball, brick) {
    //获取小球信息
    let ballX = ball.getX();
    let ballY = ball.getY();
    let ballR = ball.getR();

    //获取砖块信息
    let boardLeft = brick.getLeft();
    let boardTop = brick.getTop();
    let boardRight = brick.getRight();
    let boardBottom = brick.getBottom();

    //判断与砖块
    if (ballY < boardTop) { //小球与砖块的上端或上端两侧顶点碰撞
      let LDist = this.distance(ballX, ballY, boardLeft, boardTop);
      let TDist = boardTop - ballY;
      let RDist = this.distance(ballX, ballY, boardRight, boardTop);
      if (LDist > TDist && (boardLeft <= ballX && ballX <= boardRight)) LDist = TDist;
      if (LDist > RDist) LDist = RDist;
      if (LDist <= ballR) return 1; //表示与上端碰撞
    } else if (ballY > boardBottom) { //小球与砖块的下端或下端两侧顶点碰撞
      let LDist = this.distance(ballX, ballY, boardLeft, boardBottom);
      let TDist = ballY - boardBottom;
      let RDist = this.distance(ballX, ballY, boardRight, boardBottom);
      if (LDist > TDist && (boardLeft <= ballX && ballX <= boardRight)) LDist = TDist;
      if (LDist > RDist) LDist = RDist;
      if (LDist <= ballR) return -1; //表示与下端碰撞
    } else { //小球与砖块两侧碰撞
      //返回值为2表示与左右两侧碰撞
      if (Math.abs(ballX - boardLeft) <= ballR || Math.abs(ballX - boardRight) <= ballR) return 2;
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
  distance(x1, y1, x2, y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

	/**
	 * 移动挡板
	 */
  moveBoard() {
    //获取挡板位置
    let left = board.getLeft();
    let right = board.getRight();
    let center = (left + right) / 2;

    //获取挡板需要到达的位置
    let x = boardGoalX;

    let offset = 30;
    if (center > x) { //挡板左移
      if (center - x < offset) offset = center - x;
      if (left - wallLeft < offset) offset = left - wallLeft;
      offset *= -1;
    } else if (center < x) { //挡板右移
      if (x - center < offset) offset = x - center;
      if (wallRight - right < offset) offset = wallRight - right;
    } else return;

    if (propTime[5] > 0) { //小球粘贴在挡板上,跟着挡板移动
      for (let b of ballList) {
        if (!b.getIsMove()) {
          b.move(offset);;
        }
      }
    }

    left += offset;
    boardDefault.setLeft(left);
    boardDefault.setRight(left + boardDefault.getBitMap().width);
    boardShort.setLeft(left);
    boardShort.setRight(left + boardShort.getBitMap().width);
    boardLong1.setLeft(left);
    boardLong1.setRight(left + boardLong1.getBitMap().width);
    boardLong2.setLeft(left);
    boardLong2.setRight(left + boardLong2.getBitMap().width);
  }

	/**
	 * 随机产生道具
	 * @param left
	 * @param top
	 */
  isExistProp(left, top) {
    let isExist = Math.random() * 5; // 2/5的概率产生道具
    if (isExist <= 2) {
      let propId = Math.floor((Math.random() * Prop.propLen));
      let p = new Prop(Prop.bitMap[propId], left, top, propId);
      let r = Prop.bitMap[propId].width / 2.0;
      p.setR(r);
      p.setX(left + r);
      p.setY(top + r);
      propList.push(p);
    }
  }

	/**
	 * 游戏失败,生命减1
	 */
  lifeDie() {
    if (lifeNum == 0) gameState = 3;
    else {
      --lifeNum;
      this.resetLayout(false);
    }
  }

	/**
	 * 进入下一关
	 */
  nextScreen() {
    Screen.screenId = Screen.screenId % Screen.screenNum + 1;
    this.resetLayout(true);
  }

	/**
	 * 重置设置并暂停游戏
	 */
  resetLayout(isReSet) {
    //重置道具		
    propList = []; //这里可以把原来的道具元素全部设置为空,所以可以用clear
    bulletList = [];
    for (let i = 0; i < propTime.length; ++i) if (propTime[i] > 0) propTime[i] = 0;

    //重置挡板
    board = boardDefault;
    board.setPos(boardLeft, boardTop, boardRight, boardBottom); //挡板回到初始位置
    boardGoalX = (boardLeft + boardRight) / 2.0;

    //重置小球
    ballList = [];
    let r = ball.getR();
    ball.setPos(boardGoalX - r, boardTop - 2 * r, boardGoalX, boardTop - r);
    ball.setOffsetId(5);;
    ballList.push(ball.copy()); //注意不能直接添加ball,否则clear的时候会把ball设置为null

    //重置关卡背景图和砖块
    if (isReSet) {
      bitMap = Screen.bgMap[Screen.screenId]; //重置背景图
      brickList = screen.getScreen(Screen.screenId); //重置关卡
      for (let bl of brickList) { //砖块设置为默认值(主要是游戏over后从第一关重新开始则需要还原brickList为默认值)
        for (let b of bl) {
          b.resetBrick();
        }
      }
    }
    gameState = 1;
  }

	/**
	 * 重置游戏
	 */
  resetGame() {
    score = 0; //分数清0
    lifeNum = 2; //剩余生命
    Screen.screenId = 1;
    this.resetLayout(true);
  }

  update() {
    if (gameState == 2) { //游戏处于运行状态则游戏画面才会改变,否则不改变(暂停状态)
      //吃道具产生的小球移动
      if (propTime[2] > 0 || propTime[11] > 0) {
        propTime[2] -= 20;
        propTime[11] -= 20;
        if (propTime[2] <= 0 && propTime[11] <= 0) {
          let b = ballList[0];
          ball.setPos(b.getLeft(), b.getTop(), b.getX(), b.getY());
          ball.hitCorner(b.getxOffset(), b.getyOffset());
          ballList = [ball.copy()];
        }
      }
      for (let b of ballList) b.movd(); //小球移动
      this.moveBoard(); //挡板移动
      for (let p of propList) Prop.move(p); //道具移动
      for (let bullet of bulletList) Prop.move(bullet, -10); //子弹移动
      this.hitCheck(); //碰撞检测

      if (propTime[0] > 0 || propTime[1] > 0) {
        propTime[0] -= 20;
        propTime[1] -= 20;
        if (propTime[0] <= 0 && propTime[1] <= 0) {
          board = boardDefault;
          segLen = (board.getRight() - board.getLeft()) / Ball.offsetLen;
        }
      }

      if (propTime[3] > 0) propTime[3] -= 20;
      if (propTime[4] > 0) {
        propTime[4] -= 20;
        if (propTime[4] <= 0) { prop4Left = wallLeft; prop4Right = wallRight; }
        else { prop4Left = board.getLeft(); prop4Right = board.getRight(); }
      }
      if (propTime[5] > 0) {
        propTime[5] -= 20;
        if (propTime[5] <= 0) {
          for (let b of ballList) b.setMove(true);
        }
      }
      if (propTime[6] > 0) {
        propTime[6] -= 20;
        if (propTime[6] % 200 == 0) {
          let r = Prop.bitMap[12].height / 2.0;
          let bullet = new Prop(Prop.bitMap[12], board.getLeft(), boardTop - r - r, 12);
          bullet.setR(r);
          bullet.setX(board.getLeft() + 10);
          bullet.setY(boardTop - 10);
          bulletList.push(bullet);

          bullet = new Prop(Prop.bitMap[12], board.getRight(), boardTop - r - r, 12);
          bullet.setR(r);
          bullet.setX(board.getRight() - r);
          bullet.setY(boardTop - r);
          bulletList.push(bullet);
        }
      }
      if (propTime[8] > 0) propTime[8] -= 20;
      if (propTime[9] > 0) propTime[9] -= 20;
      if (propTime[10] > 0) propTime[10] -= 20;
    }
  }
}