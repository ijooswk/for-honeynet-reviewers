/**
 * @author:Bella
 * @date:2014/7/16
 * An ImageView Class for crop
 * 自定义组件，用于进行圆形的图像裁剪
 */

package com.gedeng.ui.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CropCircularImageView extends ImageView {

	private final static int START_COLOR = Color.WHITE; // 最开始的选择框的颜色
	private final static int RECT_COLOR = Color.WHITE; // 四个小框的颜色
	private final static int MOVE_COLOR = Color.RED; // 移动的时候的框的颜色
	public static final int LEFT_AREA_ALPHA = 160;
	public static final int PAINT_STROKE_WIDTH = 6;

	public static final int MIN_RADIUS = 80;

	private final static int PRESS_T = 0;// 表示上方矩形框
	private final static int PRESS_L = 1;// 表示左边矩形框
	private final static int PRESS_B = 2;// 表示下方矩形框
	private final static int PRESS_R = 3;// 表示右边矩形框

	private Bitmap bitMap = null; // 原始图片
	private Bitmap overBitmap = null;
	private RectF src = null; // 经过比例转换后的裁剪区域
	private RectF dst = null; // 图片显示区域（可被裁剪的区域大小）
	private RectF originDst = null;
	private RectF ChooseArea = null; // 选择需要裁剪的区域
	private Paint mPaint = null; // 画笔
	private Matrix matrix = null; // 矩阵

	private int mx = 0; // 存储触笔移动时，之前所在的触笔的x坐标
	private int my = 0; // 存储触笔移动时，之前所在的触笔的y坐标
	private boolean touchFlag = false; // 触笔是否在屏幕之上
	private boolean cutFlag = false; // 是否点击了menu上的裁剪按钮
	private int recFlag = -1; // 用来存储触笔点击了哪个小矩形框（改变选择区域大小的小矩形框）
	private boolean firstFlag = false;

	private RectF recT = null; // 上方的小矩形框
	private RectF recR = null; // 右边的小矩形框
	private RectF recL = null; // 左边的小矩形框
	private RectF recB = null; // 下方的小矩形框

	private RectF leftRectL = null;
	private RectF leftRectR = null;
	private RectF leftRectT = null;
	private RectF leftRectB = null;
	private Paint leftAreaPaint = null;

	public CropCircularImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}

	public CropCircularImageView(Context context) {
		super(context);
		this.init();
	}

	public void init() {
		cutFlag = true;
		recT = new RectF();
		recL = new RectF();
		recR = new RectF();
		recB = new RectF();
		dst = new RectF();
		originDst = new RectF();
		mPaint = new Paint();
		mPaint.setColor(START_COLOR);
		mPaint.setStyle(Paint.Style.STROKE); // 将画笔的风格改为空心
		mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
		ChooseArea = new RectF();
		this.setPressRecLoc();
		src = null;
		firstFlag = true;

		// 选择框之外的灰色区域，分成四个矩形框

		leftAreaPaint = new Paint();
		leftAreaPaint.setStyle(Paint.Style.FILL);
		leftAreaPaint.setAlpha(CropCircularImageView.LEFT_AREA_ALPHA);
	}

	public void setBitmap(Bitmap bitmap) {
		src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		this.bitMap = bitmap.copy(Config.ARGB_8888, true);
		overBitmap = toCover(bitmap.getWidth(), bitmap.getHeight());

		this.setImageBitmap(bitMap);
		leftRectB = new RectF();
		leftRectL = new RectF();
		leftRectR = new RectF();
		leftRectT = new RectF();
	}
	
	private Bitmap toCover(int width, int height) {
		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, width / 2, height / 2, paint);

		paint.setAlpha(CropCircularImageView.LEFT_AREA_ALPHA);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
		canvas.drawRect(0, 0, width, height, paint);
		return output;
	}

	public void imageScale() {
		matrix = this.getImageMatrix();
		matrix.mapRect(dst, src);
		int padding = this.getPaddingBottom();
		originDst.set(dst.left + padding, dst.top + padding, dst.right
				+ padding, dst.bottom + padding);
		dst.set((int) (dst.left + padding), (int) (dst.top + padding),
				(int) (dst.right + padding), (int) (dst.bottom + padding));
		int maxLength = Math.min((int) (dst.right - dst.left),
				(int) (dst.bottom - dst.top));
		ChooseArea = new RectF(dst.left, dst.top, dst.left + maxLength, dst.top
				+ maxLength);
		this.setPressRecLoc();
		this.invalidate();
	}

	// 裁剪出选择区域里的图片
	// 之前要做比例转换，因为，你选择的区域是针对比例转换后的图片
	// 所以要使用ChooseArea的数值做比例转换，然后，使用这些数值重新设置rec的大小
	public Bitmap getSubsetBitmap() {
		float ratioWidth = bitMap.getWidth() / (float) (dst.right - dst.left);
		float ratioHeight = bitMap.getHeight() / (float) (dst.bottom - dst.top);
		int left = (int) ((ChooseArea.left - dst.left) * ratioWidth);
		int right = (int) (left + (ChooseArea.right - ChooseArea.left)
				* ratioWidth);
		int top = (int) ((ChooseArea.top - dst.top) * ratioHeight);
		int bottom = (int) (top + (ChooseArea.bottom - ChooseArea.top)
				* ratioHeight);
		return Bitmap.createBitmap(bitMap, left, top, right - left, bottom
				- top);
	}

	// 获得ChooseArea对象
	public RectF getChooseArea() {
		return ChooseArea;
	}

	// 移动选择区域，选择区域是不能从图片区域里移出去的
	public void moveChooseArea(int f, int g) {
		if (ChooseArea.left + f >= dst.left
				&& ChooseArea.right + f <= dst.right
				&& ChooseArea.top + g >= dst.top
				&& ChooseArea.bottom + g <= dst.bottom) {
			ChooseArea.set(ChooseArea.left + f, ChooseArea.top + g,
					ChooseArea.right + f, ChooseArea.bottom + g);
		} else if (f < 10 && f > -10) {
			f = 0;
			if (ChooseArea.top + g >= dst.top
					&& ChooseArea.bottom + g <= dst.bottom) {
				ChooseArea.set(ChooseArea.left + f, ChooseArea.top + g,
						ChooseArea.right + f, ChooseArea.bottom + g);
			}
		} else if (g < 10 && g > -10) {
			g = 0;
			if (ChooseArea.left + f >= dst.left
					&& ChooseArea.right + f <= dst.right) {
				ChooseArea.set(ChooseArea.left + f, ChooseArea.top + g,
						ChooseArea.right + f, ChooseArea.bottom + g);
			}
		}
		this.setPressRecLoc();
		mPaint.setColor(MOVE_COLOR);
		this.invalidate();
	}

	public boolean onTouchEvent(MotionEvent event) {
		mPaint.setColor(START_COLOR);

		// 点击了裁剪按钮之后才会执行以下事件
		if (event.getAction() == MotionEvent.ACTION_DOWN && cutFlag) {
			// System.out.println(event.getX() + "," + event.getY());
			// 判断触笔是否在裁剪区域内，也就是ChooseArea内
			// 如果是，整个区域随着鼠标移动而移动
			mx = (int) event.getX();
			my = (int) event.getY();
			if (this.judgeLocation(mx, my)) {
				touchFlag = true;
				mPaint.setColor(MOVE_COLOR);
				this.invalidate();
				return true;
			} else {
				// 不在裁剪区域内，就判断触笔是否在改变区域大小的小矩形框之内
				if (this.findPresseddst((int) event.getX(), (int) event.getY())) {
					touchFlag = true;
					mPaint.setColor(START_COLOR);
					return true;
				}
			}
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE && touchFlag) {
			// 判断是否点击了哪个个小矩形框
			if (this.isOutOfArea((int) event.getX(), (int) event.getY())) {
				return true;
			}

			// 如果选择区域大小跟图像大小一样时，就不能移动
			if (ChooseArea.left == dst.left && ChooseArea.top == dst.top
					&& ChooseArea.right == dst.right
					&& ChooseArea.bottom == dst.bottom) {
			} else {
				this.moveChooseArea((int) (event.getX()) - mx,
						(int) (event.getY() - my));
				mx = (int) event.getX();
				my = (int) event.getY();
			}
		}

		// 触笔移出屏幕时，将一些变量设回初值
		if (event.getAction() == MotionEvent.ACTION_UP) {
			recFlag = -1;
			this.invalidate();
			touchFlag = false;
		}

		return super.onTouchEvent(event);
	}

	// 判断是否要超出图片区域，这个函数会调用下面四个press打头的函数
	// 这个函数也会重绘整个画布，也就是选择区域会随着鼠标的移动改变
	private boolean isOutOfArea(int x, int y) {
		switch (recFlag) {
		case CropCircularImageView.PRESS_T:
			this.pressT(x - mx, y - my);
			break;
		case CropCircularImageView.PRESS_L:
			this.pressL(x - mx, y - my);
			break;
		case CropCircularImageView.PRESS_B:
			this.pressB(x - mx, y - my);
			break;
		case CropCircularImageView.PRESS_R:
			this.pressR(x - mx, y - my);
			break;
		default:
			return false;
		}
		mx = x;
		my = y;
		this.invalidate();
		return true;
	}

	// 找到点击了哪个矩形框（改变选择区域大小的小矩形框）
	// 这个是在MotionEvent.ACTION_DOWN这个动作时执行的
	// 是为了在MotionEvent.ACTION_MOVE的时候，知道应该移动哪个小矩形框
	public boolean findPresseddst(int x, int y) {
		boolean returnFlag = false;
		if (this.isInRect(x, y, recT)) {
			recFlag = CropCircularImageView.PRESS_T;
			returnFlag = true;
		} else if (this.isInRect(x, y, recL)) {
			recFlag = CropCircularImageView.PRESS_L;
			returnFlag = true;
		} else if (this.isInRect(x, y, recB)) {
			recFlag = CropCircularImageView.PRESS_B;
			returnFlag = true;
		} else if (this.isInRect(x, y, recR)) {
			recFlag = CropCircularImageView.PRESS_R;
			returnFlag = true;
		}

		return returnFlag;
	}

	public boolean isInRect(int x, int y, RectF rect) {
		if (x >= rect.left - 30 && x <= rect.right + 30 && y > rect.top - 30
				&& y < rect.bottom + 30) {
			return true;
		}
		return false;
	}

	// 点击角落矩形框改变选择区域大小时，不能超过图片所在的区域
	// 下面以press打头的四个函数就是判断是否超出图片区域
	// 如果超出了，就移动不了
	// 不超出按照触笔移动的距离来移动小矩形框

	// pressL是当点击左边小矩形框改变大小时是否超出图片区域
	// 如果超出就将left与bottom的值设为图片区域的left和bottom
	private void pressL(int x, int y) {
		float left = ChooseArea.left + x;
		float right = ChooseArea.right;
		float top = ChooseArea.top + x / 2;
		float bottom = ChooseArea.bottom - (x - (x / 2));
		if (left <= right - MIN_RADIUS && left >= dst.left
				&& right <= dst.right && top <= bottom - MIN_RADIUS
				&& top >= dst.top && bottom <= dst.bottom) {
			ChooseArea.set(left, top, right, bottom);
		}
		this.setPressRecLoc();
	}

	// pressT是当点击上面小矩形框改变大小时是否超出图片区域
	// 如果超出就将left与top的值设为图片区域的left和top
	private void pressT(int x, int y) {
		float left = ChooseArea.left + y / 2;
		float right = ChooseArea.right - (y - (y / 2));
		float top = ChooseArea.top + y;
		float bottom = ChooseArea.bottom;
		if (left <= right - MIN_RADIUS && left >= dst.left
				&& right <= dst.right && top <= bottom - MIN_RADIUS
				&& top >= dst.top && bottom <= dst.bottom) {
			ChooseArea.set(left, top, right, bottom);
		}
		this.setPressRecLoc();
	}

	// pressR是当点击右边小矩形框改变大小时是否超出图片区域
	// 如果超出就将right与top的值设为图片区域的right和top
	private void pressR(int x, int y) {
		float left = ChooseArea.left;
		float right = ChooseArea.right + x;
		float top = ChooseArea.top - (x - (x / 2));
		float bottom = ChooseArea.bottom + x / 2;

		if (left <= right - MIN_RADIUS && left >= dst.left
				&& right <= dst.right && top <= bottom - MIN_RADIUS
				&& top >= dst.top && bottom <= dst.bottom) {
			ChooseArea.set(left, top, right, bottom);
		}
		this.setPressRecLoc();
	}

	// pressB是当点击下面小矩形框改变大小时是否超出图片区域
	// 如果超出就将right与bottom的值设为图片区域的right和bottom
	private void pressB(int x, int y) {
		float left = ChooseArea.left - y / 2;
		float right = ChooseArea.right + (y - (y / 2));
		float top = ChooseArea.top;
		float bottom = ChooseArea.bottom + y;

		if (left <= right - MIN_RADIUS && left >= dst.left
				&& right <= dst.right && top <= bottom - MIN_RADIUS
				&& top >= dst.top && bottom <= dst.bottom) {
			ChooseArea.set(left, top, right, bottom);
		}
		this.setPressRecLoc();
	}

	// 每次改变选择区域矩形的大小或者移动，各角落上的小矩形也要改变它的Location
	private void setPressRecLoc() {
		float middleInWidth = (ChooseArea.left + ChooseArea.right) / 2;
		float middleInHeight = (ChooseArea.top + ChooseArea.bottom) / 2;

		recT.set(middleInWidth - 8, ChooseArea.top - 8, middleInWidth + 8,
				ChooseArea.top + 8);
		recL.set(ChooseArea.left - 8, middleInHeight - 8, ChooseArea.left + 8,
				middleInHeight + 8);
		recR.set(ChooseArea.right - 8, middleInHeight - 8,
				ChooseArea.right + 8, middleInHeight + 8);
		recB.set(middleInWidth - 8, ChooseArea.bottom - 8, middleInWidth + 8,
				ChooseArea.bottom + 8);
	}

	// 判断触笔是否在选择区域内
	public boolean judgeLocation(float x, float y) {
		float start_x = this.getChooseArea().left;
		float start_y = this.getChooseArea().top;
		float last_x = this.getChooseArea().right;
		float last_y = this.getChooseArea().bottom;
		if (x > start_x + 10 && x < last_x - 10 && y > start_y + 10
				&& y < last_y - 10) {
			return true;
		}
		return false;
	}

	@SuppressLint("DrawAllocation")
	/*
	 * 
	 * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
	 * 显示圆形裁剪器效果的核心代码
	 */
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (firstFlag) {
			this.imageScale();
			firstFlag = false;
			mPaint.setColor(START_COLOR);
		} else {
			set_LeftArea_Alpha();
		}

		Paint paint = new Paint();
		Rect r = new Rect((int) ChooseArea.left, (int) ChooseArea.top,
				(int) (ChooseArea.right), (int) (ChooseArea.bottom));
		
		/*
		 * 绘制四周的灰色区域
		 */
		canvas.drawRect(leftRectL, leftAreaPaint);
		canvas.drawRect(leftRectR, leftAreaPaint);
		canvas.drawRect(leftRectT, leftAreaPaint);
		canvas.drawRect(leftRectB, leftAreaPaint);
		
		canvas.drawBitmap(overBitmap, null, r, paint);
		canvas.drawRoundRect(ChooseArea, ChooseArea.width() / 2,
				ChooseArea.height() / 2, mPaint);

		mPaint.setColor(RECT_COLOR);
		canvas.drawRect(recT, mPaint);
		canvas.drawRect(recL, mPaint);
		canvas.drawRect(recR, mPaint);
		canvas.drawRect(recB, mPaint);
	}

	public void set_LeftArea_Alpha() {
		leftRectL.set(originDst.left, originDst.top, ChooseArea.left, originDst.bottom);
		leftRectR.set(ChooseArea.right, originDst.top, originDst.right, originDst.bottom);
		leftRectT.set(ChooseArea.left, originDst.top, ChooseArea.right,
				ChooseArea.top);
		leftRectB.set(ChooseArea.left, ChooseArea.bottom, ChooseArea.right,
				originDst.bottom);
	}
}