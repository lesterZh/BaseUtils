package com.gaia.member.gaiatt.mall.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @Title: CustomCommentStar
 * @Package com.gaia.member.gaiatt.mall
 * @Description: TODO
 * Copyright: Copyright (c) 2016
 * Company: 成都壹柒互动科技有限公司
 * @author Android客户端开发组-zhangHaiTao
 * @date 2016/6/16 15:03
 * @version V1.0
 */

/**
 * @author Android客户端开发组-zhangHaiTao
 * @ClassName: CustomCommentStar
 * Description: 自定义评分进度条
 * @date 2016/6/16 15:03
 */

/*

    <declare-styleable name="CustomCommentStar">
        <attr name="lightStarSrc" format="reference"/>
        <attr name="unLightStarSrc" format="reference"/>
        <attr name="imageWidth" format="dimension"/>
        <attr name="imageHeight" format="dimension"/>
        <attr name="imagePadding" format="dimension"/>
        <attr name="clickable" format="boolean"/>
    </declare-styleable>

<gaia.com.componentlibrary.custom.CustomCommentStar
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:imageWidth="20dp"
            app:imageHeight="20dp"
            app:unLightStarSrc="@drawable/gray_star"
            app:lightStarSrc="@drawable/yellow_star"
            app:imagePadding="2dp"
            android:layout_marginTop="20dp"
            android:layout_below="@+id/textView"
            android:layout_alignStart="@+id/textView" />

 */

public class CustomCommentStar extends LinearLayout{

    private int lightStarId;
    private int unLightStarId;
    private ImageView mImageView;
    private LayoutParams params;
    private int mImageWidth;
    private int mImageHeight;
    private int mImagePadding;
    private boolean mClickable;
    private int mCount=0;


    public CustomCommentStar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, gaia.com.componentlibrary.R.styleable.CustomCommentStar);
        lightStarId=array.getResourceId(gaia.com.componentlibrary.R.styleable.CustomCommentStar_lightStarSrc,0);
        unLightStarId= array.getResourceId(gaia.com.componentlibrary.R.styleable.CustomCommentStar_unLightStarSrc,0);
        mImageHeight= (int) array.getDimension(gaia.com.componentlibrary.R.styleable.CustomCommentStar_imageHeight,30);
        mImageWidth= (int) array.getDimension(gaia.com.componentlibrary.R.styleable.CustomCommentStar_imageWidth,30);
        mImagePadding= (int) array.getDimension(gaia.com.componentlibrary.R.styleable.CustomCommentStar_imagePadding,0);
        mClickable=array.getBoolean(gaia.com.componentlibrary.R.styleable.CustomCommentStar_clickable,true);
        init(context);
    }

    void init(Context context){
        setOrientation(HORIZONTAL);
        for(int i=0;i<5;i++){
            final int position=i;
            mImageView=new ImageView(context);
            params=new LayoutParams(mImageWidth,mImageHeight);
            mImageView.setImageResource(unLightStarId);
            mImageView.setLayoutParams(params);
            mImageView.setPadding(mImagePadding, mImagePadding, mImagePadding, mImagePadding);
            if(mClickable)
            mImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setStarState(position);
                }
            });
            addView(mImageView);
        }
    }

    /**
     * 对外接口
     * @param count 需要点亮的数量
     */
    public void setStarcount(int count){
        setStarState(count-1);
    }

    /**
     * 获得点亮的数量
     * @return
     */
    public int getStarCount(){
        return mCount;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    private void setStarState(int position){

        mCount=position+1;

        for(int i=0;i<getChildCount();i++){
            ImageView imageView= (ImageView) getChildAt(i);
            if(i<=position){
                imageView.setImageResource(lightStarId);
            }else{
                imageView.setImageResource(unLightStarId);
            }

        }
    }
}
