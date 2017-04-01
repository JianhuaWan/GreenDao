package com.xiangyue.weight.text;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class DrawableCenter extends TextView {

	public DrawableCenter(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public DrawableCenter(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DrawableCenter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Drawable[] drawables = getCompoundDrawables();
		if (drawables != null) {
			Drawable drawableLeft = drawables[0];
			if (drawableLeft != null) {
				float textWidth = getPaint().measureText(getText().toString());
				int drawablePadding = getCompoundDrawablePadding();
				int drawableWidth = 0;
				drawableWidth = drawableLeft.getIntrinsicWidth();
				float bodyWidth = textWidth + drawableWidth + drawablePadding;
				canvas.translate((getWidth() - bodyWidth) / 2, Gravity.CENTER);
			}
		}
		super.onDraw(canvas);

	}
}
