package com.example.hitbricks;

import java.util.Timer;
import java.util.TimerTask;

import com.example.gameView.GameView;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	private GameView gameView = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        //隐去电池等图标和一切修饰部分（状态栏部分）
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 隐去标题栏（程序的名字）
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(gameView);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
    	switch(keyCode){
    		case KeyEvent.KEYCODE_MENU: //按下菜单键执行相应的功能
    			if(gameView.gameState == 1){ //1表示游戏处于等待开始状态(暂停状态或者刚进入游戏等待开始状态)
    				//开始游戏
    				gameView.startGame();
    			}else if(gameView.gameState == 2){ //2表示游戏处于正在进行状态
    				//暂停游戏
    				gameView.pauseGame();
    			}else{ //游戏处于结束状态
    				//游戏重置
    				gameView.resetGame();
    			}
    			break;
    			
    		case KeyEvent.KEYCODE_BACK:
    			exitByDoubleClick(); //连续按两次退出游戏
    			break;
    			
    		default:
    			break;
    	}
    	return false;
    }
    
    /**
     * 连续按两下退出
     */
    private static boolean isExit = false;
    private void exitByDoubleClick(){
    	Timer tExit = null;
    	if(!isExit){
    		isExit = true;
    		Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
    		tExit = new Timer();
    		tExit.schedule(new TimerTask(){
    			@Override
    			public void run(){
    				isExit = false;
    			}
    		}, 2000);
    	}else{
    		finish();
    		System.exit(0);
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
