export default class Brick {
	/**
	 * 
	 * 脳漏驴茅脌脿
	 * @author chenyang
	 * @param color -脳漏驴茅脩脮脡芦
	 * @param isExist -脳漏驴茅脢脟路帽麓忙脭脷
	 * 
	 */
  static bitMapDisp = new Array(10); //脳漏驴茅脧没脢搂脨搂鹿没

  left = 0;
  top = 0;
  right = 0;
  bottom = 0;
  color = 0; //脳漏驴茅脩脮脡芦
  score = 5; //脳漏驴茅碌脛路脰脢媒
  isExist = true; //卤锚脢露脳漏驴茅脢脟路帽麓忙脭脷
  isDisap = true; //卤锚录脟脳漏驴茅脢脟路帽驴脡脧没脢搂
  bitMap = [];
  defaultBrick = null;
  bitMapIndex = 0; //卤铆脢戮脧脭脢戮脛脛脮脜bitMap
  mapOffset = 0; //脳漏驴茅脧没脢搂脥录脝卢脧脿露脭脳漏驴茅脥录脝卢脨猫脪陋脪脝露炉碌脛脦禄脰脙

  constructor() {
    if (arguments.length == 5) {
      this.constructor2(arguments[0], arguments[1], arguments[2], 
      arguments[3], arguments[4]);
    } else if (arguments.length == 3) {
      this.constructor3(arguments[0], arguments[1], arguments[2]);
    }
  }

  constructor2(left, top, right, bottom, color) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.color = color;
  }

  constructor3(bitMap, left, top) {
    this.left = left;
    this.top = top;
    this.bitMap = this.defaultBrick = bitMap;
  }

  static loadBrickDisp() {
    Brick.bitMapDisp[0] = new Image();
    Brick.bitMapDisp[0].src = "images/hitbricks/eff1.png";

    Brick.bitMapDisp[1] = new Image();
    Brick.bitMapDisp[1].src = "images/hitbricks/eff2.png";

    Brick.bitMapDisp[2] = new Image();
    Brick.bitMapDisp[2].src = "images/hitbricks/eff3.png";

    Brick.bitMapDisp[3] = new Image();
    Brick.bitMapDisp[3].src = "images/hitbricks/eff4.png";

    Brick.bitMapDisp[4] = new Image();
    Brick.bitMapDisp[4].src = "images/hitbricks/eff5.png";

    Brick.bitMapDisp[5] = new Image();
    Brick.bitMapDisp[5].src = "images/hitbricks/eff6.png";

    Brick.bitMapDisp[6] = new Image();
    Brick.bitMapDisp[6].src = "images/hitbricks/eff7.png";

    Brick.bitMapDisp[7] = new Image();
    Brick.bitMapDisp[7].src = "images/hitbricks/eff8.png";
  }

  drawBrickBitMap() {
    if (arguments.length == 1) {
      this.drawBrickBitMap2(arguments[0]);
    }else if (arguments.length == 2) {
      this.drawBrickBitMap3(arguments[0], arguments[1]);
    }
  }

	/**
	 * 禄忙脰脝脳漏驴茅(脥录脝卢)
	 * @param canvas
	 * @param paint
	 */
  drawBrickBitMap2(ctx) {
    if (!this.bitMap) return; //路脌脰鹿bitMapIndex脪禄脰卤脳脭录脫脧脗脠楼
    this.mapOffset = 0;
    if (!this.isExist) { //脳漏驴茅脧没脢搂脭貌脮鹿脢戮脧没脢搂脨搂鹿没
      this.mapOffset = 50;
      this.bitMap = Brick.bitMapDisp[this.bitMapIndex++];
    }
    if (!this.bitMap) return; //脳漏驴茅录掳脝盲脧没脢搂脨搂鹿没脪脩脥锚鲁脡
    ctx.drawImage(
      this.bitMap, 
      this.left - this.mapOffset, 
      this.top - this.mapOffset); //禄忙脰脝卤鲁戮掳脥录
  }

	/**
	 * 禄忙脰脝脳漏驴茅(脥录脝卢)
	 * @param bitmap
	 * @param canvas
	 * @param paint
	 */
  drawBrickBitMap3(bitmap, ctx) {
    if (!this.isExist) { this.drawBrickBitMap2(ctx); return; }
    ctx.drawImage(bitmap, this.left, this.top); //禄忙脰脝卤鲁戮掳脥录
  }

	/**
	 * 脡猫脰脙脳漏驴茅脦禄脰脙
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
  setPos(left, top, right, bottom) {
    this.setLeft(left);
    this.setTop(top);
    this.setRight(right);
    this.setBottom(bottom);
  }

	/**
	 * 脰脴脰脙脳漏驴茅禄霉卤戮脢么脨脭
	 */
  resetBrick() {
    this.isExist = true;
    this.bitMap = this.defaultBrick;
    this.bitMapIndex = 0;
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

  getRight() {
    return this.right;
  }

  setRight(right) {
    this.right = right;
  }

  getBottom() {
    return this.bottom;
  }

  setBottom(bottom) {
    this.bottom = bottom;
  }

  getColor() {
    return this.color;
  }

  setColor(color) {
    this.color = color;
  }

  getIsExist() {
    return this.isExist;
  }

  setExist(isExist) {
    this.isExist = isExist;
  }

  getBitMapIndex() {
    return this.bitMapIndex;
  }

  setBitMapIndex(bitMapIndex) {
    this.bitMapIndex = bitMapIndex;
  }

  getBitMap() {
    return this.bitMap;
  }

  setBitMap(bitMap) {
    this.bitMap = bitMap;
  }

  getScore() {
    return this.score;
  }

  setScore(score) {
    this.score = score;
  }

  getIsDisap() {
    return this.isDisap;
  }

  setDisap(isDisap) {
    this.isDisap = isDisap;
  }
}
