export default class Prop {
	/**
	 * 道具类
	 */

  static propLen = 12; //道具数量
  static bitMap = []; //道具图片
  static yOffset = 3; //道具掉落速度

  prop = []; //当前应显示的道具
  left = 0.0; //道具位置
  top = 0.0; //道具位置

  //道具是球状,所以碰撞检测需要x, y, r
  x = 0.0; 
  y = 0.0;
  r = 0.0;
  isExist = true; //判断道具是否存在
  propId = 0;

  constructor() {
    if (arguments.length == 4) {
      this.constructor2(arguments[0], arguments[1], arguments[2], arguments[3]);
    }
  }

  constructor2(prop, left, top, propId) {
    this.prop = prop;
    this.left = left;
    this.top = top;
    this.propId = propId;
  }

  static loadPropImg() {
    Prop.bitMap[0] = new Image();
    Prop.bitMap[0].src = "images/hitbricks/prop1.png";

    Prop.bitMap[1] = new Image();
    Prop.bitMap[1].src = "images/hitbricks/prop2.png";

    Prop.bitMap[2] = new Image();
    Prop.bitMap[2].src = "images/hitbricks/prop3.png";

    Prop.bitMap[3] = new Image();
    Prop.bitMap[3].src = "images/hitbricks/prop4.png";

    Prop.bitMap[4] = new Image();
    Prop.bitMap[4].src = "images/hitbricks/prop5.png";

    Prop.bitMap[5] = new Image();
    Prop.bitMap[5].src = "images/hitbricks/prop6.png";

    Prop.bitMap[6] = new Image();
    Prop.bitMap[6].src = "images/hitbricks/prop7.png";

    Prop.bitMap[7] = new Image();
    Prop.bitMap[7].src = "images/hitbricks/prop8.png";

    Prop.bitMap[8] = new Image();
    Prop.bitMap[8].src = "images/hitbricks/prop9.png";

    Prop.bitMap[9] = new Image();
    Prop.bitMap[9].src = "images/hitbricks/prop10.png";

    Prop.bitMap[10] = new Image();
    Prop.bitMap[10].src = "images/hitbricks/prop11.png";

    Prop.bitMap[11] = new Image();
    Prop.bitMap[11].src = "images/hitbricks/prop12.png";

    Prop.bitMap[12] = new Image();
    Prop.bitMap[12].src = "images/hitbricks/prop13.png";
  }

  static move() {
    if (arguments.length == 1) {
      Prop.move2(arguments[0]);
    }else if(arguments.length == 2) {
      Prop.move3(arguments[0], arguments[1]);
    }
  }
  static move2(p) {
    p.top += Prop.yOffset;
    p.y += Prop.yOffset;
  }

  static move3(p, offset) {
    p.top += offset;
    p.y += offset;
  }
	
  drawPropBitMap(ctx){ //绘制道具
    ctx.drawImage(this.prop, this.left, this.top);
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

	getIsExist() {
    return this.isExist;
  }

	setExist(isExist) {
    this.isExist = isExist;
  }

	getPropId() {
    return this.propId;
  }

	setPropId(propId) {
    this.propId = propId;
  }
}
