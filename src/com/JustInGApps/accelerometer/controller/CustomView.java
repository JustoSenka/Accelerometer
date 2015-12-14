package com.JustInGApps.accelerometer.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.JustInGApps.accelerometer.R;

public class CustomView extends View {

	private boolean ini = true;
	private float scaled;
	private int Width, Height;
	
	private Paint paint = new Paint();
	private Arrow[] arrows = new Arrow[8];
	
	private boolean[] dataOut = new boolean[4];

	public CustomView(Context context) { this(context, null); }
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public String getButtonsData(){
		if (dataOut == null || dataOut.length < 4){
			return " 0 0 0 0";
		} else {
			String str = "";
			for (int i = 0; i < dataOut.length; i++){
				if (dataOut[i]) str += " 1";
				else str += " 0";
			}
			return str;
		}
	}

	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		if (ini) init(c);
		
		for (int i = 0; i < 8; i++){
			if (arrows[i].shouldDraw) arrows[i].draw(c);
		}
	}
	
	@SuppressLint("ClickableViewAccessibility") 
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		
		float mx = e.getX(), my = e.getY();
		
		// Checks if released finger
		if (e.getAction() == MotionEvent.ACTION_UP) {
			for (int i = 0; i < 4; i++){
				arrows[i].shouldDraw = true;
				arrows[i + 4].shouldDraw = false;
			}
		// Checks what pressed and what to show
		} else {
			for (int i = 0; i < 4; i++){
				if (arrows[i].isClicked(mx, my)){
					arrows[i].shouldDraw = false;
					arrows[i + 4].shouldDraw = true;
				} else {
					arrows[i].shouldDraw = true;
					arrows[i + 4].shouldDraw = false;
				}
			}
		}
		
		// Writes dataOut for controller to know what buttons are pressed
		for (int i = 0; i < 4; i++){
			dataOut[i] = !arrows[i].shouldDraw;
		}
		
		invalidate();
		
		return true;
	}

	private void init(Canvas c) {
		Width = c.getWidth();
		Height = c.getHeight();
		
		Bitmap[] t = new Bitmap[8];
		for (int i = 0; i < 8; i++){
			Bitmap temp;
			temp = BitmapFactory.decodeResource(getResources(), R.drawable.arrow0 + i);
			t[i] = ScaleBitmapW(temp, 300);
		}
		
		Rect[] rect = new Rect[4];
		rect[0] = new Rect(0, getY(380), getX(500), getY(620));
		rect[1] = new Rect(0, 0, Width, getY(380));
		rect[2] = new Rect(getX(500), getY(380), Width, getY(620));
		rect[3] = new Rect(0, getY(620), Width, Height);
		
		arrows[0] = new Arrow(t[0], getX(100), (Height - t[0].getHeight()) / 2, true, rect[0]);
		arrows[1] = new Arrow(t[1], getX(350), getX(100), true, rect[1]);
		arrows[2] = new Arrow(t[2], getX(600), (Height - t[2].getHeight()) / 2, true, rect[2]);
		arrows[3] = new Arrow(t[3], getX(350), Height - getX(100) - t[3].getHeight(), true, rect[3]);
		
		arrows[4] = new Arrow(t[4], getX(100), (Height - t[4].getHeight()) / 2, false, rect[0]);
		arrows[5] = new Arrow(t[5], getX(350), getX(100), false, rect[1]);
		arrows[6] = new Arrow(t[6], getX(600), (Height - t[6].getHeight()) / 2, false, rect[2]);
		arrows[7] = new Arrow(t[7], getX(350), Height - getX(100) - t[3].getHeight(), false, rect[3]);
		
		ini = false;
	}

	private class Arrow {
		
		protected boolean shouldDraw = false;
		
		private Bitmap bmp;
		private float x, y;
		private Rect rect;
		
		protected Arrow(Bitmap bmp, float x, float y, boolean draw, Rect rect){
			this.bmp = bmp;
			this.x = x;
			this.y = y;
			this.shouldDraw = draw;
			this.rect = rect;
		}

		protected void draw(Canvas c) {
			c.drawBitmap(bmp, x, y, paint);
		}
		
		protected boolean isClicked(float mx, float my) {
			return rect.contains((int) mx, (int) my);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	// ###################################################################################################################

	
	
	private Bitmap ScaleBitmap(Bitmap bmp, int w, int h) {
		// TODO Auto-generated method stub

		Bitmap rbmp;
		rbmp = Bitmap.createScaledBitmap(bmp, (int) (Width * 1.00f * w / 1000),
				(int) (Height * 1.00f * h / 1000), false);

		return rbmp;
	}

	private Bitmap ScaleBitmapW(Bitmap bmp, int w) {
		// TODO Auto-generated method stub

		Bitmap rbmp;

		scaled = (Width * 1.00f * w / 1000) / bmp.getWidth();

		rbmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaled),
				(int) (bmp.getHeight() * scaled), false);

		return rbmp;
	}

	private Bitmap ScaleBitmapH(Bitmap bmp, int h) {
		// TODO Auto-generated method stub

		Bitmap rbmp;

		scaled = (Height * 1.00f * h / 1000) / bmp.getHeight();

		rbmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * scaled),
				(int) (bmp.getHeight() * scaled), false);

		return rbmp;
	}

	// Converts from 1000 system to pixels

	private int getX(int arg) {

		arg = (arg * Width) / 1000;

		return arg;
	}

	private int getY(int arg) {

		arg = (arg * Height) / 1000;

		return arg;
	}
}
