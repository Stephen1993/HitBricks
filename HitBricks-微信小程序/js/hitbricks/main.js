import GameView from './gameview';

let ctx = canvas.getContext('2d');

/**
 * 游戏主函数
 */
export default class Main {
  intNum = 0;
  interId = 0;
  andiId = 0; // 打开的游戏id
  
  constructor() {
    this.gameview = new GameView(ctx);
    this.bindLoop = this.loop.bind(this);
    this.interId = setInterval((function () {
      if (GameView.loadSuc <= 0 || this.intNum >= 20) {
        clearInterval(this.interId);
        this.gameStart();
      }
      this.intNum++;
      // console.log(GameView.loadSuc + ' ' + this.intNum);
    }).bind(this), 100);
  }

  gameStart() { // 重启游戏
    this.gameview.initData();
    // 清除上一局的动画
    window.cancelAnimationFrame(this.aniId);
    this.aniId = window.requestAnimationFrame(
      this.bindLoop,
      canvas
    )
  }

  // 实现游戏帧循环，每一帧重新绘制所有需要的元素
  loop() {
    this.gameview.update();
    this.gameview.render();
    this.aniId = window.requestAnimationFrame(
      this.bindLoop,
      canvas
    );
  }
}
