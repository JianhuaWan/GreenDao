package com.xiangyue.view;


import android.content.Context;

import com.xiangyue.act.R;

/**
 * 公共对话框生产类
 * 
 * @author pengjianbo
 * 
 */
public class CommonDialogFactory {

	/**
	 * 获取List列表对话框
	 * 
	 * @return
	 */
	// public static CommonDialog getListCommonDialog(Context context) {
	// CommonDialog dialog = new CommonDialog(context, R.style.Dialog,
	// R.layout.common_list_dialog);
	// return dialog;
	// }

	/**
	 * 获取带输入编辑文本框的对话框
	 * 
	 * @param context
	 *            -- 上下文
	 * @param finish
	 *            -- 对话框监听器
	 * @param isShowEditText
	 *            -- 是否显示编辑文本框
	 * @param content
	 *            -- 对话框消息内容
	 * @return
	 */
	// public static CommonEditConfirmDialog getEditDialog(Context context,
	// OnFinishInterface finish,
	// boolean isShowEditText, String content) {
	// CommonEditConfirmDialog dialog = new CommonEditConfirmDialog(context,
	// finish,
	// context.getResources().getString(R.string.prompt),
	// content,
	// context.getResources().getString(R.string.input_text),
	// "", false);
	// return dialog;
	// }


	/**
	 * 获取确认、取消对话框
	 * 
	 * @param context
	 *            -- 上下文
	 * @param onFinishInterface
	 *            -- 确认按钮监听器
	 * @param title
	 *            -- 对话框标题
	 * @param btn_cancel
	 *            -- 取消按钮文字
	 * @param btn_confirm
	 *            -- 确认按钮文字
	 * @param msg
	 *            -- 对话框内容
	 * @return 对话框句柄
	 */
	// public static Dialog getOkCancelDialog(Context context,
	// OnFinishInterface onFinishInterface, String title,
	// String btn_cancel, String btn_confirm, String msg) {
	// Dialog dialog = new CommonEditConfirmDialog(context, onFinishInterface,
	// title, msg, btn_cancel, btn_confirm);
	// return dialog;
	// }

}
