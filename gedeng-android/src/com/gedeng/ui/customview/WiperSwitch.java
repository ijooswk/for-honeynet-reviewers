/**
 * @modifier: Bella
 * @date:2014/7/16
 * 
 * 一个滑动按钮，用来代替安卓系统原生的switch
 * 网络代码，修正绘图和大小控制Bug
 */
package com.gedeng.ui.customview;

import com.gedeng.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

/**
 * 
 * @author xiaanming
 *
 */
public class WiperSwitch extends View implements OnTouchListener, OnClickListener{
	private Bitmap bg_on, bg_off, slipper_btn;	//开状态背景，关状态背景，滑动按钮图
	private float nowX;	//滑动钮现在所处的位置
	private boolean onSlip = false;		//是否正在滑动
	private boolean nowStatus = false;		//当前开关状态
	private int width = 60, height = 30;	//默认按钮大小
	
	/**
	 * 监听接口
	 */
	private OnChangedListener listener;
	
	
	public WiperSwitch(Context context) {
		super(context);
		init();
	}

	public WiperSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public void init(){
		//载入图片资源
		bg_on = BitmapFactory.decodeResource(getResources(), R.drawable.btn_on);
		bg_off = BitmapFactory.decodeResource(getResources(), R.drawable.btn_off);
		slipper_btn = BitmapFactory.decodeResource(getResources(), R.drawable.btn_trig);
		
		setOnTouchListener(this);
	}
	
	@SuppressLint("DrawAllocation") 
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//得到xml中设置的大小参数
		LayoutParams layoutParams = getLayoutParams();
		if(layoutParams != null){
			width = layoutParams.width;
			height = layoutParams.height;
		}
		
		//目标开关需要绘制的位置，确定大小
		RectF rectF = new RectF();
		rectF.set(0, 0, width, height);
		Paint paint = new Paint();
		float x = 0;
		
		//根据nowX设置背景，开或者关状态
		if (nowX < (width/2)){
			canvas.drawBitmap(bg_off, null, rectF, paint);//画出关闭时的背景
		}else{
			canvas.drawBitmap(bg_on, null, rectF, paint);//画出打开时的背景 
		}
		
		if (onSlip) {//是否是在滑动状态,  
			if(nowX >= width)//是否划出指定范围,不能让滑块跑到外头,必须做这个判断
				x = width - height/2;//减去滑块1/2的长度
			else
				x = nowX - height/2;
		}else {
			if(nowStatus){//根据当前的状态设置滑块的x值
				x = width - height;
			}else{
				x = 0;
			}
		}
		
		//对滑块滑动进行异常处理，不能让滑块出界
		if (x < 0 ){
			x = 0;
		}
		else if(x > width - height){
			x = width - height;
		}
		
		//画出滑块
		rectF.set(x, 0, x + height, height);
		canvas.drawBitmap(slipper_btn, null , rectF, paint); 
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:{
			if (event.getX() > width || event.getY() > height){
				return false;
			}else{
				onSlip = true;
				nowX = event.getX();
			}
			break;
		}
		case MotionEvent.ACTION_MOVE:{
			nowX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP:{
			onSlip = false;
			if(event.getX() >= (width/2)){
				nowStatus = true;
				nowX = width- height;
			}else{
				nowStatus = false;
				nowX = 0;
			}
			
			if(listener != null){
				listener.OnChanged(WiperSwitch.this, nowStatus);
			}
			break;
		}
		}
		//刷新界面
		invalidate();
		return true;
	}
	
	
	
	/**
	 * 为WiperSwitch设置一个监听，供外部调用的方法
	 * @param listener
	 */
	public void setOnChangedListener(OnChangedListener listener){
		this.listener = listener;
	}
	
	
	/**
	 * 设置滑动开关的初始状态，供外部调用
	 * @param checked
	 */
	public void setChecked(boolean checked){
		if(checked){
			nowX = width;
		}else{
			nowX = 0;
		}
		nowStatus = checked;
	}

	
    /**
     * 回调接口
     * @author len
     *
     */
	public interface OnChangedListener {
		public void OnChanged(WiperSwitch wiperSwitch, boolean checkState);
	}


	@Override
	public void onClick(View v) {
		if(nowStatus){
			nowStatus = false;
			nowX = 0;
		}else{
			nowStatus = true;
			nowX = width;
		}
		invalidate();
	}
}