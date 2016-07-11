package com.gaia.member.gaiatt.utils;/**
 * @Title: DialogUtil
 * @Package com.gaia.member.gaiatt.utils
 * @Description: TODO
 * Copyright: Copyright (c) 2016
 * Company: 成都壹柒互动科技有限公司
 * @author Android客户端组-ZhangHaiTao
 * @date 2016/7/11 10:33
 * @version V1.0
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.gaia.member.gaiatt.makeorder.ui.CustomProgressDialog;

/**
 *
 *@ClassName: DialogUtil
 * Description: 弹窗定义类
 * @author Android客户端组-ZhangHaiTao
 * @date 2016/7/11 10:33
 *
 */
public class DialogUtil {
    private static AlertDialog dialog;

    public interface CallBackListener {
        public void call(String select, int position);
    }

    public static void getSelectItem(Activity mContext, String title, final String[] choices, final CallBackListener callBackListener) {
//        final String[] choice = {"父亲", "母亲", "姑姑"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setSingleChoiceItems(choices, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                tvRelationship.setText(choice[which]);
//                SharedPreferencesUtil.putString(mContext, CommonConstants.GUARDIAN_RELATION, choice[which]);//保存到本地
                callBackListener.call(choices[which], which);
                dialog.dismiss();
            }
        });
        dialog = builder.show();
    }

    public static void showProgressDialog(Activity context, String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        CustomProgressDialog.show(context, message, cancelable, cancelListener);
    }

    public static void dismissPProgressDialog() {
        CustomProgressDialog.dismissDialog();
    }
}
