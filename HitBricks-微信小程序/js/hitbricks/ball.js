export default class Ball {
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

  //设置小球的方向数组,小球在挡板的不同位置弹起拥有不同的方向(用x轴, y轴方向增加速度不同导致斜率不同体现)
  static speed = 2.8;
  static x_offset = [- 13 / Ball.speed, -11/Ball.speed, -10/Ball.speed, -9/Ball.speed, -6/Ball.speed, -2/Ball.speed, 2/Ball.speed, 6/Ball.speed, 9/Ball.speed, 10/Ball.speed, 11/Ball.speed, 13/Ball.speed, 14/Ball.speed];
  static y_offset = [- 6 / Ball.speed, -9 / Ball.speed, -10 / Ball.speed, -11 / Ball.speed, -13 / Ball.speed, -14 / Ball.speed, -14 / Ball.speed, -13 / Ball.speed, -11 / Ball.speed, -10 / Ball.speed, -9 / Ball.speed, -6 / Ball.speed, -2 / Ball.speed];
  static offsetLen = Ball.x_offset.length - 1; //数组中最后一个不算,只是为了二分查找没找到的时候采用最后一组速度

  //普通绘制球的信息
  x = 0; //球心位置
  y = 0; //球心位置
  r = 0; //球半径
  xOffset = -10 / Ball.speed; //x轴方向速度
  yOffset = -10 / Ball.speed; //y轴方向速度
  color = 0; //球颜色
  isMove = true; //表示球是否移动

  //绘制图片球的信息
  left = 0; //球外切矩形左上角顶点坐标
  top = 0; //球外切矩形左上角顶点坐标
  bitMap = null; //球的图片
	offsetId = 5;
	
	constructor(){
    if (arguments.length == 4) {
      this.constructor2(arguments[0], arguments[1], arguments[2], arguments[3]);
    } else if (arguments.length == 3) {
      this.constructor3(arguments[0], arguments[1], arguments[2]);
    } else {
      this.xOffset = Ball.x_offset[offsetId];
      this.yOffset = Ball.y_offset[offsetId];
    }
  }
	
	constructor2(x, y, r, color){
    this.x = x;
    this.y = y;
    this.r = r;
    this.color = color;
  }
	
	constructor3(bitMap, left, top){
    this.left = left;
    this.top = top;
    this.bitMap = bitMap;
  }

	/**
	 * 球移动
	 */
	movd(){
    if (!this.isMove) return;
    this.x += this.xOffset;
    this.y += this.yOffset;
    this.left += this.xOffset;
    this.top += this.yOffset;
  }

	/**
	 * 球移动
	 * @param offset
	 */
	move(offset){
    this.x += offset;
    //y += offset;
    this.left += offset;
    //top += offset;
  }

	/**
	 * 球的偏移方向
	 * @param xOffset
	 * @param yOffset
	 */
  hitCorner(xOffset, yOffset){
    this.setxOffset(xOffset);
    this.setyOffset(yOffset);
  }

  /**
   * 绘制小球(图片)
   * @param canvas
   * @param paint
   */
  drawBallBitMap(ctx) { //绘制小球背景图
    ctx.drawImage(
      this.bitMap,
      this.left,
      this.top);
  }

	/**
	 * 对象复制
	 * @return
	 */
	copy(){
    let b = new Ball(this.getBitMap(), this.getLeft(), this.getTop());
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
	setPos(left, top, x, y){ //设置小球位置
    this.setLeft(left);
    this.setTop(top);
    this.setX(x);
    this.setY(y);
  }

	getX() {
    return this.x;
  }

	setX(x) {
    this.x = x;
  }

	getY() {
    return this.y;
  }

	setY(y) {
    this.y = y;
  }

	getR() {
    return this.r;
  }

	setR(r) {
    this.r = r;
  }

	getxOffset() {
    return this.xOffset;
  }

	setxOffset(xOffset) {
    this.xOffset = xOffset;
  }

	getyOffset() {
    return this.yOffset;
  }

	setyOffset(yOffset) {
    this.yOffset = yOffset;
  }

	getColor() {
    return this.color;
  }

	setColor(color) {
    this.color = color;
  }

	getLeft() {
    return this.left;
  }

	setLeft(left) {
    this.left = left;
  }

	getTop() {
    return this.top;
  }

	setTop(top) {
    this.top = top;
  }

	getBitMap() {
    return this.bitMap;
  }

	setBitMap(bitMap) {
    this.bitMap = bitMap;
  }

	getOffsetId() {
    return this.offsetId;
  }

	setOffsetId(offsetId) {
    this.offsetId = offsetId;
    this.hitCorner(Ball.x_offset[offsetId], Ball.y_offset[offsetId]);
  }

	getIsMove() {
    return this.isMove;
  }

	setMove(isMove) {
    this.isMove = isMove;
  }
}
