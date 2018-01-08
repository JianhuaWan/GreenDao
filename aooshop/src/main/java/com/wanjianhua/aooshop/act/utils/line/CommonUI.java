package com.wanjianhua.aooshop.act.utils.line;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;


import com.wanjianhua.aooshop.R;

import java.util.Arrays;
import java.util.List;

public class CommonUI
{
    /**
     * <p>
     * Spinner 设置些样式统一 <br>
     * style="@style/Widget.AppCompat.Spinner.Underlined"
     * </p>
     */
    public static <T> ArrayAdapter<T> commonArrayAdapter(Context context, T[] objects)
    {
        return commonArrayAdapter(context, Arrays.asList(objects));
    }

    /**
     * <p>
     * Spinner 设置些样式统一 <br>
     * style="@style/Widget.AppCompat.Spinner.Underlined"
     * </p>
     */
    public static <T> ArrayAdapter<T> commonArrayAdapter(Context context, List<T> objects)
    {
        ArrayAdapter<T> arrayAdapter = new ArrayAdapter<>(context, R.layout.simple_spinner_item, objects);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        return arrayAdapter;
    }

    public static void AlertDialog(Context context, int message, DialogInterface.OnClickListener okListener)
    {
        AlertDialog(context, 0, message, null, okListener, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
    }

    public static void AlertDialog(Context context, int title, View contentView,
                                   DialogInterface.OnClickListener okListener)
    {
        AlertDialog(context, title, 0, contentView, okListener, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
    }

    public static void AlertDialog(Context context, View contentView)
    {
        AlertDialog(context, 0, 0, contentView, null, null);
    }

    public static void AlertDialog(Context context, int title, int message, DialogInterface.OnClickListener okListener)
    {
        AlertDialog(context, title, message, null, okListener, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
    }

    public static void AlertDialog(Context context, String message, DialogInterface.OnClickListener okListener,
                                   DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog(context, 0, message, null, okListener, cancelListener);
    }

    public static void AlertDialog(Context context, int message, DialogInterface.OnClickListener okListener,
                                   DialogInterface.OnClickListener cancelListener)
    {
        AlertDialog(context, 0, message, null, okListener, cancelListener);
    }

    public static void AlertDialog(Context context, int title, int message, DialogInterface.OnClickListener okListener,
                                   boolean notCancel)
    {
        AlertDialog(context, title, message, null, okListener, null);
    }

    public static void AlertDialog(Context context, String message, DialogInterface.OnClickListener okListener)
    {
        AlertDialog(context, 0, message, null, okListener, null);
    }

    private static void AlertDialog(Context context, int title, int message, View customView,
                                    final DialogInterface.OnClickListener okListener,
                                    DialogInterface.OnClickListener cancelListener)
    {
//        CommonDialog.Show(context, title, message, new CommonDialog.OnClickConfirm()
//        {
//            @Override
//            public void doConfirm()
//            {
//                okListener.onClick();
//            }
//        });
        Builder dialog = new Builder(context);
        if(title != 0)
        {
            dialog.setTitle(title);
        }
        if(message != 0)
        {
            dialog.setMessage(message);
        }
        if(customView != null)
        {
            dialog.setView(customView);
        }
        dialog.setPositiveButton(R.string.dialog_ok, okListener);
        if(null != cancelListener)
        {
            dialog.setNegativeButton(R.string.dialog_cancel, cancelListener);
        }
        dialog.setCancelable(false);
        dialog.create().show();
        dialog = null;
    }

    private static void AlertDialog(Context context, int title, String message, View customView,
                                    DialogInterface.OnClickListener okListener,
                                    DialogInterface.OnClickListener cancelListener)
    {
        Builder dialog = new Builder(context);
        if(title != 0)
        {
            dialog.setTitle(title);
        }
        dialog.setMessage(message);
        if(customView != null)
        {
            dialog.setView(customView);
        }
        dialog.setPositiveButton(R.string.dialog_ok, okListener);
        if(null != cancelListener)
        {
            dialog.setNegativeButton(R.string.dialog_cancel, cancelListener);
        }
        dialog.setCancelable(false);
        dialog.create().show();
        dialog = null;
    }

    public static void setPricePoint(final EditText editText)
    {
        editText.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.toString().contains("."))
                {
                    if(s.length() - 1 - s.toString().indexOf(".") > 4)
                    {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 5);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if(s.toString().trim().substring(0).equals("."))
                {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if(s.toString().startsWith("0") && s.toString().trim().length() > 1)
                {
                    if(!s.toString().substring(1, 2).equals("."))
                    {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }

        });

    }

}
