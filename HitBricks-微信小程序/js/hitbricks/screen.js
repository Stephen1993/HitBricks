import Brick from './brick';
import GameView from './gameview';

let brickWith = 10; //砖块宽度
let brickHeight = 5; //砖块高度	
let screenWidth = window.innerWidth; // 屏幕宽度
let screenHeight = window.innerHeight; // 屏幕高度
let maxTopBrick = screenHeight / 6;

// 每个砖块的坐标
let left = 0;
let top = 0;
let right = 0;
let bottom = 0;
let padding = 3; //每个砖块之间的距离

// 关卡数组
let screen1 = [];
let screen2 = [];
let screen3 = [];
let screen4 = [];
let screen5 = [];
let screen6 = [];

export default class Screen {
	/**
	 * 
	 * 关卡类
	 * 
	 */
  static screenId = 1; //当前关卡id
  static screenNum = 6; //关卡总数

  //砖块背景
  static bitMap = [];

  //关卡背景
  static bgMap = []; //关卡背景

  constructor() {
    brickWith = Screen.bitMap[0].width;
    brickHeight = Screen.bitMap[0].height;
    this.creatScreen1();
    this.creatScreen2();
    this.creatScreen3();
    this.creatScreen4();
    this.creatScreen5();
    this.creatScreen6();
  }

  static loadBgImg() { // 加载关卡背景图
    Screen.bgMap[1] = new Image();
    Screen.bgMap[1].src = "images/hitbricks/bg1.png";
    Screen.bgMap[1].onload = GameView.loadSucCal;

    Screen.bgMap[2] = new Image();
    Screen.bgMap[2].src = "images/hitbricks/bg2.png";

    Screen.bgMap[3] = new Image();
    Screen.bgMap[3].src = "images/hitbricks/bg3.png";

    Screen.bgMap[4] = new Image();
    Screen.bgMap[4].src = "images/hitbricks/bg4.png";

    Screen.bgMap[5] = new Image();
    Screen.bgMap[5].src = "images/hitbricks/bg5.png";

    Screen.bgMap[6] = new Image();
    Screen.bgMap[6].src = "images/hitbricks/bg6.png";
  }

  static loadBrickImg() { // 加载砖块背景图
    Screen.bitMap[0] = new Image();
    Screen.bitMap[0].src = "images/hitbricks/brick1.png";
    Screen.bitMap[0].onload = GameView.loadSucCal;

    Screen.bitMap[1] = new Image();
    Screen.bitMap[1].src = "images/hitbricks/brick2.png";
    Screen.bitMap[1].onload = GameView.loadSucCal;

    Screen.bitMap[2] = new Image();
    Screen.bitMap[2].src = "images/hitbricks/brick3.png";
    Screen.bitMap[2].onload = GameView.loadSucCal;

    Screen.bitMap[3] = new Image();
    Screen.bitMap[3].src = "images/hitbricks/brick4.png";
    Screen.bitMap[3].onload = GameView.loadSucCal;

    Screen.bitMap[4] = new Image();
    Screen.bitMap[4].src = "images/hitbricks/brick5.png";
    Screen.bitMap[4].onload = GameView.loadSucCal;

    Screen.bitMap[5] = new Image();
    Screen.bitMap[5].src = "images/hitbricks/brick6.png";
    Screen.bitMap[5].onload = GameView.loadSucCal;

    Screen.bitMap[6] = new Image();
    Screen.bitMap[6].src = "images/hitbricks/brick7.png";
    Screen.bitMap[6].onload = GameView.loadSucCal;

    Screen.bitMap[7] = new Image();
    Screen.bitMap[7].src = "images/hitbricks/brick8.png";
    Screen.bitMap[7].onload = GameView.loadSucCal;

    Screen.bitMap[8] = new Image();
    Screen.bitMap[8].src = "images/hitbricks/brick9.png";
    Screen.bitMap[8].onload = GameView.loadSucCal;
  }

	/**
	 * 获取关卡
	 * @param id
	 * @return
	 */
  getScreen(id) {
    if (id == 1) return screen1;
    else if (id == 2) return screen2;
    else if (id == 3) return screen3;
    else if (id == 4) return screen4;
    else if (id == 5) return screen5;
    else return screen6;
  }

  creatScreen1() { //生成第一个关卡砖块布局
    screen1 = new Array(6);
    for (let i = 0; i < screen1.length; ++i) screen1[i] = new Array(6);
    top = maxTopBrick - padding - brickHeight;

    for (let i = 0; i < 6; ++i) {
      left = (screenWidth - 6 * brickWith) / 3 - padding - brickWith;
      top += padding + brickHeight;
      if (i == 3) top += 100;
      bottom = top + brickHeight;
      for (let j = 0; j < 6; ++j) {
        left += padding + brickWith;
        if (j == 3) left += (screenWidth - 6 * brickWith) / 3;
        right = left + brickWith;
        screen1[i][j] = new Brick(Screen.bitMap[j], left, top);
        screen1[i][j].setRight(right);
        screen1[i][j].setBottom(bottom);
      }
    }
  }

  creatScreen2() { //生成第二个关卡砖块布局
    screen2 = new Array(10);
    for (let i = 0; i < screen2.length; ++i) screen2[i] = new Array(6);
    top = maxTopBrick - padding - brickHeight;

    for (let i = 0; i < 10; ++i) {
      left = (screenWidth - 6 * brickWith) / 3 - padding - brickWith;
      top += padding + brickHeight;
      bottom = top + brickHeight;
      for (let j = 0; j < 6; ++j) {
        left += padding + brickWith;
        if (j == 3) left += (screenWidth - 6 * brickWith) / 3;
        right = left + brickWith;
        screen2[i][j] = new Brick(Screen.bitMap[i % 8], left, top);
        screen2[i][j].setRight(right);
        screen2[i][j].setBottom(bottom);
      }
    }
  }

  creatScreen3() { //生成第三个关卡砖块布局
    screen3 = new Array(11);
    for (let i = 0; i < screen3.length; ++i) screen3[i] = [];

    screen3[0] = new Array(3);
    for (let i = 1; i < 3; ++i) screen3[i] = new Array(5);
    screen3[3] = new Array(7);
    for (let i = 4; i < 10; ++i) screen3[i] = new Array(6);
    screen3[10] = new Array(7);
    top = maxTopBrick - padding - brickHeight;

    for (let i = 0; i < 11; ++i) {
      let len = screen3[i].length;
      left = (screenWidth - 9 * (brickWith + padding)) / 2 - 2;
      if (i < 1) left += 3 * (brickWith + padding) - brickWith;
      else if (i < 3) left += 2 * (brickWith + padding) - brickWith;
      else if (i < 6) left += brickWith + padding - brickWith;
      else left += padding - padding - brickWith;
      top += padding + brickHeight;
      bottom = top + brickHeight;
      for (let j = 0; j < len; ++j) {
        left += padding + brickWith;
        if (i > 3 && i < 6 && j == 3) left += padding + brickWith;
        else if (i > 5 && j == 3) left += 3 * (padding + brickWith);
        if (i == 10 && j == 6) continue;
        right = left + brickWith;
        screen3[i][j] = new Brick(Screen.bitMap[j % 8], left, top);
        screen3[i][j].setRight(right);
        screen3[i][j].setBottom(bottom);
      }
    }
    left = (screenWidth - 9 * (brickWith + padding)) / 2;
    left += 4 * (brickWith + padding) + padding;
    right = left + brickWith;
    screen3[10][6] = new Brick(Screen.bitMap[8], left, top);
    screen3[10][6].setRight(right);
    screen3[10][6].setBottom(bottom);
    screen3[10][6].setDisap(false);
  }

  creatScreen4() { //生成第四个关卡砖块布局
    screen4 = new Array(9);
    for (let i = 0; i < 9; ++i) screen4[i] = new Array(i+1);
    top = maxTopBrick - padding - brickHeight;

    for (let i = 0; i < 9; ++i) {
      left = (screenWidth - 9 * (brickWith + padding)) / 2 - 2;
      left += padding - padding - brickWith;
      top += padding + brickHeight;
      bottom = top + brickHeight;
      for (let j = 0; j <= i; ++j) {
        left += padding + brickWith;
        right = left + brickWith;
        if (i == 8 && j < 7) { 
          screen4[i][j] = new Brick(Screen.bitMap[8], left, top); 
          screen4[i][j].setDisap(false); 
        }
        else if (i == 8 && j == i) {
          screen4[i][j] = new Brick(Screen.bitMap[0], left, top);
        }
        else screen4[i][j] = new Brick(Screen.bitMap[j], left, top);
        screen4[i][j].setRight(right);
        screen4[i][j].setBottom(bottom);
      }
    }
  }

  creatScreen5() { //生成第五个关卡砖块布局
    screen5 = new Array(11);
    for (let i = 0; i < screen5.length; ++i) screen5[i] = [];
    for (let i = 0; i < 3; ++i) screen5[i] = new Array(4 + i * 2);
    for (let i = 3; i < 6; ++i) screen5[i] = new Array(9);
    for (let i = 6; i < 9; ++i) screen5[i] = new Array(7 - 2 * (i - 6));
    screen5[9] = new Array(2);
    screen5[10] = new Array(1);
    top = maxTopBrick - padding - brickHeight;

    for (let i = 0; i < 11; ++i) {
      if (i == 0) left = (screenWidth - 5 * (brickWith + padding)) / 2 - 2;
      else left = (screenWidth - screen5[i].length * (brickWith + padding)) / 2 - 2;
      left += padding - padding - brickWith;
      top += padding + brickHeight;
      bottom = top + brickHeight;
      for (let j = 0; j < screen5[i].length; ++j) {
        left += padding + brickWith;
        if (i == 0 && j == 2) left += padding + brickWith;
        right = left + brickWith;
        let k = 0;
        if ((j == 0 || j == screen5[i].length - 1) && i > 5) k = 3;
        if (i == 3 && (j == 2 || j == 4 || j == 5 || j == 7)) k = 4;
        else if (i == 4 && (j == 1 || j == 3 || j == 6 || j == 8)) k = 4;
        else if (i == 5 && (j == 2 || j == 3 || j == 6 || j == 7)) k = 4;
        else if (i == 6 && (j == 2 || j == 3 || j == 4 || j == 5)) k = 4;
        else if (i == 7 && j == 4) k = 4;
        screen5[i][j] = new Brick(Screen.bitMap[k], left, top);
        screen5[i][j].setRight(right);
        screen5[i][j].setBottom(bottom);
      }
    }
  }

  creatScreen6() { //生成第六个关卡砖块布局
    screen6 = new Array(18);
    for (let i = 0; i < screen6.length; ++i) screen6[i] = [];
    screen6[0] = new Array(2);
    screen6[1] = new Array(3);
    screen6[2] = new Array(5);
    for (let i = 3; i < 7; ++i) screen6[i] = new Array(7);
    for (let i = 7; i < 9; ++i) screen6[i] = new Array(5);
    screen6[9] = new Array(3);
    screen6[10] = new Array(5);
    for (let i = 11; i < 14; ++i) screen6[i] = new Array(9);
    screen6[14] = new Array(7);
    for (let i = 15; i < 17; ++i) screen6[i] = new Array(5);
    screen6[17] = new Array(7);
    top = maxTopBrick - 50 - padding - brickHeight;

    for (let i = 0; i < 18; ++i) {
      if (i == 0) left = (screenWidth - 3 * (brickWith + padding)) / 2 - 2;
      else if (i == 1) left = (screenWidth - 5 * (brickWith + padding)) / 2 - 2;
      else if (i == 10 || i == 15 || i == 16) left = (screenWidth - 7 * (brickWith + padding)) / 2 - 2;
      else if (i == 17) left = (screenWidth - 9 * (brickWith + padding)) / 2 - 2;
      else left = (screenWidth - screen6[i].length * (brickWith + padding)) / 2 - 2;
      left += padding - padding - brickWith;
      top += padding + brickHeight;
      bottom = top + brickHeight;
      for (let j = 0; j < screen6[i].length; ++j) {
        left += padding + brickWith;
        if (i == 0 && j == 1) left += padding + brickWith;
        else if (i == 1 && j > 0) left += padding + brickWith;
        else if ((i == 10 || i == 15 || i == 16) && (j == 1 || j == 4)) left += padding + brickWith;
        else if (i == 17 && (j == 3 || j == 4)) left += padding + brickWith;
        right = left + brickWith;
        let k = 2;
        if (i < 2 || (i == 2 && (j == 0 || j == 2 || j == 4))) k = 3;
        else if (i == 9 && j == 1) k = 3;
        else if ((i == 10 || i == 16) && j == 2) k = 3;
        else if (i > 10 && i < 14 && j > 2 && j < 6) k = 3;
        else if (i == 14 && j > 1 && j < 5) k = 3;
        else if (i == 15 && j > 0 && j < 4) k = 3;
        else if (i == 2 && (j == 1 || j == 3)) k = 5;
        else if (i == 4 && (j == 1 || j == 5)) k = 0;
        else if (i == 5 && (j == 1 || j == 2 || j == 4 || j == 5)) k = 0;
        else if (i == 6 && (j == 2 || j == 3 || j == 4)) k = 0;
        else if (i == 7 && (j == 1 || j == 3)) k = 0;
        else if (i == 8 && j == 2) k = 0;
        else if (i == 7 && j == 2) k = 4;
        if (i == 17) k = 8;
        if (i == 17 && j == 3) k =3;
        screen6[i][j] = new Brick(Screen.bitMap[k], left, top);
        screen6[i][j].setRight(right);
        screen6[i][j].setBottom(bottom);
        if (i == 17 && j != 3) screen6[i][j].setDisap(false);
      }
    }
  }
}
