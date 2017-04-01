package com.xiangyue.weight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.xiangyue.act.R;

public class LoginDialog extends Dialog {

	private AnimationDrawable animation;
	private ImageView imageView;

	public LoginDialog(Context context, String msg) {
		super(context, R.style.style_dialog_login);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_login);
		imageView = (ImageView) findViewById(R.id.img_dialog_login);
		animation = (AnimationDrawable) imageView.getBackground();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		animation.start();
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

}
