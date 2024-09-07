package com.example.studyapp_ryan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleTimerView extends View {
    private Paint circlePaint;
    private Paint textPaint;
    private Paint arcPaint;
    private float progress = 0;
    private String timeText = "00:00";

    public CircleTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(20);
        circlePaint.setAntiAlias(true);

        arcPaint = new Paint();
        arcPaint.setColor(Color.GREEN);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(20);
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the circle
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        float radius = Math.min(centerX, centerY) - 20;
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        // Draw the arc
        float sweepAngle = 360 * progress / 100;
        canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                -90, sweepAngle, false, arcPaint);

        // Draw the text
        canvas.drawText(timeText, centerX, centerY + (textPaint.getTextSize() / 3), textPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
        invalidate();
    }
}
