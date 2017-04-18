package com.amulyakhare.td.sample.sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.TypedValue;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

/**
 * @author amulya
 * @datetime 17 Oct 2014, 4:02 PM
 */
public class DrawableProvider {

	public static final int SAMPLE_MULTIPLE_LETTERS = 7;
	public static final int SAMPLE_FONT = 8;

	private final ColorGenerator mGenerator;
	private final Context mContext;

	public DrawableProvider(Context context) {
		mGenerator = ColorGenerator.DEFAULT;
		mContext = context;
	}

	public TextDrawable getRect(String text) {
		return TextDrawable.builder()
				.buildRect(text, mGenerator.getColor(text));
	}

	public TextDrawable getRound(String text) {
		return TextDrawable.builder().buildRound(text,
				mGenerator.getColor(text));
	}

	public TextDrawable getRoundRect(String text) {
		return TextDrawable.builder().buildRoundRect(text,
				mGenerator.getColor(text), toPx(10));
	}

	public TextDrawable getRectWithBorder(String text) {
		return TextDrawable.builder().beginConfig().withBorder(toPx(2))
				.endConfig().buildRect(text, mGenerator.getColor(text));
	}

	public TextDrawable getRoundWithBorder(String text) {
		return TextDrawable.builder().beginConfig().withBorder(toPx(2))
				.endConfig().buildRound(text, mGenerator.getColor(text));
	}

	public TextDrawable getRoundRectWithBorder(String text) {
		return TextDrawable.builder().beginConfig().withBorder(toPx(2))
				.endConfig()
				.buildRoundRect(text, mGenerator.getColor(text), toPx(10));
	}

	public TextDrawable getRectWithMultiLetter(String text) {
		// String text = "AK";
		return TextDrawable
				.builder()
				.beginConfig()
				.fontSize(toPx(18))
				.toUpperCase()
				.endConfig()
				.buildRoundRect(cutString(text), Color.parseColor("#ff0000"),
						20);
	}

	public TextDrawable getRoundWithCustomFont(String text) {
		// String text = "Bold";
		return TextDrawable.builder().beginConfig().useFont(Typeface.DEFAULT)
				.fontSize(toPx(18)).textColor(0xffffffff).bold().endConfig()
				.buildRound(cutString(text), Color.GRAY /* toPx(5) */);
	}

	public Drawable getRectWithCustomSize() {
		String leftText = "I";
		String rightText = "J";

		TextDrawable.IBuilder builder = TextDrawable.builder().beginConfig()
				.width(toPx(29)).withBorder(toPx(2)).endConfig().rect();

		TextDrawable left = builder.build(leftText,
				mGenerator.getColor(leftText));

		TextDrawable right = builder.build(rightText,
				mGenerator.getColor(rightText));

		Drawable[] layerList = { new InsetDrawable(left, 0, 0, toPx(31), 0),
				new InsetDrawable(right, toPx(31), 0, 0, 0) };
		return new LayerDrawable(layerList);
	}

	public Drawable getRectWithAnimation() {
		TextDrawable.IBuilder builder = TextDrawable.builder().rect();

		AnimationDrawable animationDrawable = new AnimationDrawable();
		for (int i = 10; i > 0; i--) {
			TextDrawable frame = builder.build(String.valueOf(i),
					mGenerator.getRandomColor());
			animationDrawable.addFrame(frame, 1200);
		}
		animationDrawable.setOneShot(false);
		animationDrawable.start();

		return animationDrawable;
	}

	public int toPx(int dp) {
		Resources resources = mContext.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				resources.getDisplayMetrics());
	}

	public String cutString(String str) {
		if (TextUtils.isEmpty(str)) {
			return " ";
		}
		String reg = "[a-zA-Z]*";
		String name = "";
		if (str.matches(reg)) {//
			name = str.substring(0, 2).toUpperCase();
		} else {//
			if (str.length() > 2) {
				name = str.substring(str.length() - 2, str.length());
			} else {
				name = str;
			}
		}
		return name;
	}
}
