/**
 * TuSdkDemo
 * EditMultipleComponentSimple.java
 *
 * @author Clear
 * @Date 2015-4-21 下午1:38:04
 * @Copyright (c) 2015 tusdk.com. All rights reserved.
 */
package com.xiangyue.tusdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xiangyue.act.R;
import com.xiangyue.base.BaseApplication;
import com.xiangyue.provider.BusProvider;
import com.xiangyue.provider.PhotoHeadRefreshEvent;
import com.xiangyue.provider.PhotoListRefreshEvent;
import com.xiangyue.provider.PhotoRefreshEvent;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuEditMultipleComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent.TuSdkComponentDelegate;
import org.lasque.tusdk.modules.components.TuSdkHelperComponent;

/**
 * 多功能图片编辑组件范例
 *
 * @author Clear
 */
public class EditMultipleComponentSimple extends SimpleBase {
    private Context context;

    /**
     * 多功能图片编辑组件范例
     */
    public EditMultipleComponentSimple() {
        super(2, R.string.simple_EditMultipleComponent);
    }

    /**
     * 显示范例
     */
    @Override
    public void showSimple(Activity activity) {
        if (activity == null)
            return;
        context = activity;
        BusProvider.getInstance().register(context);
        // see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/base/TuSdkHelperComponent.html
        this.componentHelper = new TuSdkHelperComponent(activity);

        TuSdkGeeV1.albumCommponent(activity, new TuSdkComponentDelegate() {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment) {
                openEditMultiple(result, error, lastFragment);
            }
        }).showComponent();
    }

    /**
     * 开启多功能图片编辑
     */
    private void openEditMultiple(TuSdkResult result, Error error, TuFragment lastFragment) {
        if (result == null || error != null)
            return;

        // 组件委托
        TuSdkComponentDelegate delegate = new TuSdkComponentDelegate() {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment) {
                TLog.d("onEditMultipleComponentReaded: %s | %s", result, error);

                if (BaseApplication.getInstance().getPhotoorheadIcon().equals("1")) {
                    BusProvider.getInstance().post(new PhotoRefreshEvent(result.imageSqlInfo.path));
                } else if (BaseApplication.getInstance().getPhotoorheadIcon().equals("2")) {
                    BusProvider.getInstance().post(new PhotoListRefreshEvent(result.imageSqlInfo.path));
                } else if (BaseApplication.getInstance().getPhotoorheadIcon().equals("3")) {
                    BusProvider.getInstance().post(new PhotoHeadRefreshEvent(result.imageSqlInfo.path));
                } else if (BaseApplication.getInstance().getPhotoorheadIcon().equals("4")) {

                    Intent intent = new Intent();
                    intent.setAction("bgcircle");
                    intent.putExtra("path", result.imageSqlInfo.path);
                    context.sendBroadcast(intent);
                } else if (BaseApplication.getInstance().getPhotoorheadIcon().equals("5")) {

                    Intent intent = new Intent();
                    intent.setAction("bgme");
                    intent.putExtra("path", result.imageSqlInfo.path);
                    context.sendBroadcast(intent);
                }
            }
        };

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditMultipleComponent.html
        TuEditMultipleComponent component = null;

        if (lastFragment == null) {
            component = TuSdkGeeV1.editMultipleCommponent(this.componentHelper.activity(), delegate);
        } else {
            component = TuSdkGeeV1.editMultipleCommponent(lastFragment, delegate);
        }
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuEditMultipleComponentOption.html
        // component.componentOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditMultipleOption.html
        // component.componentOption().editMultipleOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditCuterOption.html
        // component.componentOption().editCuterOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditFilterOption.html
        // component.componentOption().editFilterOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditSkinOption.html
        // component.componentOption().editSkinOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/sticker/TuEditStickerOption.html
        // component.componentOption().editStickerOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditAdjustOption.html
        // component.componentOption().editAdjustOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditSharpnessOption.html
        // component.componentOption().editSharpnessOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditApertureOption.html
        // component.componentOption().editApertureOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditVignetteOption.html
        // component.componentOption().editVignetteOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/smudge/TuEditSmudgeOption.html
        // component.componentOption().editSmudgeOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/filter/TuEditWipeAndFilterOption.html
        // component.componentOption().editWipeAndFilterOption()

        // 设置图片
        component.setImage(result.image)
                // 设置系统照片
                .setImageSqlInfo(result.imageSqlInfo)
                        // 设置临时文件
                .setTempFilePath(result.imageFile)
                        // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                        // 开启组件
                .showComponent();

    }
}