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
        //��ȥ��ص�ͼ���һ�����β��֣�״̬�����֣�
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // ��ȥ����������������֣�
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);        
        setContentView(gameView);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
    	switch(keyCode){
    		case KeyEvent.KEYCODE_MENU: //���²˵���ִ����Ӧ�Ĺ���
    			if(gameView.gameState == 1){ //1��ʾ��Ϸ���ڵȴ���ʼ״̬(��ͣ״̬���߸ս�����Ϸ�ȴ���ʼ״̬)
    				//��ʼ��Ϸ
    				gameView.startGame();
    			}else if(gameView.gameState == 2){ //2��ʾ��Ϸ�������ڽ���״̬
    				//��ͣ��Ϸ
    				gameView.pauseGame();
    			}else{ //��Ϸ���ڽ���״̬
    				//��Ϸ����
    				gameView.resetGame();
    			}
    			break;
    			
    		case KeyEvent.KEYCODE_BACK:
    			exitByDoubleClick(); //�����������˳���Ϸ
    			break;
    			
    		default:
    			break;
    	}
    	return false;
    }
    
    /**
     * �����������˳�
     */
    private static boolean isExit = false;
    private void exitByDoubleClick(){
    	Timer tExit = null;
    	if(!isExit){
    		isExit = true;
    		Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�", Toast.LENGTH_SHORT).show();
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
