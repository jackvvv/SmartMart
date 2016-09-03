/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sinia.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.sinia.zxing.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

import sinia.com.smartmart.R;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192,
			128, 64 };
	private static final long ANIMATION_DELAY = 80L;
	// private static final int CURRENT_POINT_OPACITY =
	// 0xA0;//将扫描获取的图片回显在框内时的透明度
	private static final int CURRENT_POINT_OPACITY = 0xFF;
	private static final int MAX_RESULT_POINTS = 20;
	private static final int POINT_SIZE = 6;

	private static final int TEXT_SIZE = 16;
	private static final int TEXT_PADDING_TOP = 30;

	private CameraManager cameraManager;
	private final Paint paint;
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int frameColor;
	private final int resultColor;
	private final int laserColor;
	private final int resultPointColor;
	private int scannerAlpha;
	private List<ResultPoint> possibleResultPoints;
	private List<ResultPoint> lastPossibleResultPoints;

	// scan line
	private boolean isFirst;

	private int i = 0;// 添加的
	private Rect mRect;// 扫描线填充边界
	private Drawable lineDrawable;// 采用图片作为扫描线
	private Drawable boxDrawable;// 采用图片作为扫描框
	/**
	 * 手机的屏幕密度
	 */
	private static float density;
	/**
	 * 四个绿色边角对应的长度
	 */
	private int ScreenRate;

	/**
	 * 四个绿色边角对应的宽度
	 */
	private static final int CORNER_WIDTH = 10;

	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);

		Resources resources = getResources();

		density = context.getResources().getDisplayMetrics().density;

		ScreenRate = (int) (20 * density);

		maskColor = resources.getColor(R.color.viewfinder_mask);
		frameColor = resources.getColor(R.color.viewfinder_frame);
		resultColor = resources.getColor(R.color.result_view);
		laserColor = resources.getColor(R.color.viewfinder_laser);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		scannerAlpha = 0;
		possibleResultPoints = new ArrayList<ResultPoint>(5);
		lastPossibleResultPoints = null;
		mRect = new Rect();
		// lineDrawable = getResources().getDrawable(R.drawable.scan_line);
		// boxDrawable = getResources().getDrawable(R.drawable.scan_box);
	}

	public void setCameraManager(CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (cameraManager == null) {
			return; // not ready yet, early draw before done configuring
		}
		Rect frame = cameraManager.getFramingRect();
		Rect previewFrame = cameraManager.getFramingRectInPreview();
		if (frame == null || previewFrame == null) {
			return;
		}
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		// Draw the exterior (i.e. outside the framing rect) darkened
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
				paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);
		// 扫描框自定义==========================
		mRect.set(frame.left, frame.top, frame.right, frame.bottom);
		// boxDrawable.setBounds(mRect);
		// boxDrawable.draw(canvas);
		// 画出四个角
		paint.setColor(getResources().getColor(R.color.themeColor));
		// // 左上角
		canvas.drawRect(frame.left, frame.top, frame.left + 15, frame.top + 5,
				paint);
		canvas.drawRect(frame.left, frame.top, frame.left + 5, frame.top + 15,
				paint);
		// // 右上角
		canvas.drawRect(frame.right - 15, frame.top, frame.right,
				frame.top + 5, paint);
		canvas.drawRect(frame.right - 5, frame.top, frame.right,
				frame.top + 15, paint);
		// // 左下角
		canvas.drawRect(frame.left, frame.bottom - 5, frame.left + 15,
				frame.bottom, paint);
		canvas.drawRect(frame.left, frame.bottom - 15, frame.left + 5,
				frame.bottom, paint);
		// // 右下角
		canvas.drawRect(frame.right - 15, frame.bottom - 5, frame.right,
				frame.bottom, paint);
		canvas.drawRect(frame.right - 5, frame.bottom - 15, frame.right,
				frame.bottom, paint);

		if (resultBitmap != null) {
			canvas.drawBitmap(resultBitmap, null, frame, paint);
			// 扫描线自定义
			paint.setAlpha(CURRENT_POINT_OPACITY);
			canvas.drawBitmap(resultBitmap, null, frame, paint);
		} else {
			if ((i += 5) < frame.bottom - frame.top) {
				// mRect.set(frame.left + 18, frame.top + i - 10,
				// frame.right - 18, frame.top + 10 + i);
				// lineDrawable.setBounds(mRect);
				// lineDrawable.draw(canvas);
				canvas.drawRect(frame.left + 30, frame.top + i - 1,
						frame.right - 30, frame.top + 1 + i, paint);
				// 刷新
				invalidate();
			} else {
				i = 0;
			}
		}

		float scaleX = frame.width() / (float) previewFrame.width();
		float scaleY = frame.height() / (float) previewFrame.height();

		List<ResultPoint> currentPossible = possibleResultPoints;
		List<ResultPoint> currentLast = lastPossibleResultPoints;
		int frameLeft = frame.left;
		int frameTop = frame.top;
		if (currentPossible.isEmpty()) {
			lastPossibleResultPoints = null;
		} else {
			possibleResultPoints = new ArrayList<ResultPoint>(5);
			lastPossibleResultPoints = currentPossible;
			paint.setAlpha(CURRENT_POINT_OPACITY);
			paint.setColor(resultPointColor);
			synchronized (currentPossible) {
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(
							frameLeft + (int) (point.getX() * scaleX), frameTop
									+ (int) (point.getY() * scaleY),
							POINT_SIZE, paint);
				}
			}
		}
		if (currentLast != null) {
			paint.setAlpha(CURRENT_POINT_OPACITY / 2);
			paint.setColor(resultPointColor);
			synchronized (currentLast) {
				float radius = POINT_SIZE / 2.0f;
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(
							frameLeft + (int) (point.getX() * scaleX), frameTop
									+ (int) (point.getY() * scaleY), radius,
							paint);
				}
			}
		}

		// Request another update at the animation interval, but only
		// repaint the laser line,
		// not the entire viewfinder mask.
		postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
				frame.right, frame.bottom);// Jacky
											// 2014-01-07
		// postInvalidateDelayed(ANIMATION_DELAY, frame.left - POINT_SIZE,
		// frame.top - POINT_SIZE, frame.right + POINT_SIZE, frame.bottom +
		// POINT_SIZE);
	}

	public void drawViewfinder() {
		Bitmap resultBitmap = this.resultBitmap;
		this.resultBitmap = null;
		if (resultBitmap != null) {
			resultBitmap.recycle();
		}
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		List<ResultPoint> points = possibleResultPoints;
		synchronized (points) {
			points.add(point);
			int size = points.size();
			if (size > MAX_RESULT_POINTS) {
				// trim it
				points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
			}
		}
	}

}
