
//提供从相册或者相机中选择图片并截取显示的方法和流程

public class MainActivity extends Activity {
 
     private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
     private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
     private static final int PHOTO_REQUEST_CUT = 3;// 结果
 
     private ImageView iv_image;
 
     /* 头像名称 */
     private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
     private File tempFile;
 
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         this.iv_image = (ImageView) this.findViewById(R.id.iv_image);
     }
 
     /*
      * 从相册获取
      */
     public void gallery(View view) {
         // 激活系统图库，选择一张图片
         Intent intent = new Intent(Intent.ACTION_PICK);
         intent.setType("image/*");
         // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
         startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
     }
 
     /*
      * 从相机获取
      */
     public void camera(View view) {
         // 激活相机
         Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
         // 判断存储卡是否可以用，可用进行存储
         if (hasSdcard()) {
             tempFile = new File(Environment.getExternalStorageDirectory(),
                     PHOTO_FILE_NAME);
             // 从文件中创建uri
             Uri uri = Uri.fromFile(tempFile);
             intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
         }
         // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
         startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
     }
 
     /*
      * 剪切图片
      */
     private void crop(Uri uri) {
         // 裁剪图片意图
         Intent intent = new Intent("com.android.camera.action.CROP");
         intent.setDataAndType(uri, "image/*");
         intent.putExtra("crop", "true");
         // 裁剪框的比例，1：1
         intent.putExtra("aspectX", 1);
         intent.putExtra("aspectY", 1);
         // 裁剪后输出图片的尺寸大小
         intent.putExtra("outputX", 250);
         intent.putExtra("outputY", 250);
 
         intent.putExtra("outputFormat", "JPEG");// 图片格式
         intent.putExtra("noFaceDetection", true);// 取消人脸识别
         intent.putExtra("return-data", true);
         // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
         startActivityForResult(intent, PHOTO_REQUEST_CUT);
     }
 
     /*
      * 判断sdcard是否被挂载
      */
     private boolean hasSdcard() {
         if (Environment.getExternalStorageState().equals(
                 Environment.MEDIA_MOUNTED)) {
             return true;
         } else {
             return false;
         }
     }
 
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == PHOTO_REQUEST_GALLERY) {
             // 从相册返回的数据
             if (data != null) {
                 // 得到图片的全路径
                 Uri uri = data.getData();
                 crop(uri);
             }
 
         } else if (requestCode == PHOTO_REQUEST_CAREMA) {
             // 从相机返回的数据
             if (hasSdcard()) {
                 crop(Uri.fromFile(tempFile));
             } else {
                 Toast.makeText(MainActivity.this, "未找到存储卡，无法存储照片！", 0).show();
             }
 
         } else if (requestCode == PHOTO_REQUEST_CUT) {
             // 从剪切图片返回的数据
             if (data != null) {
                 Bitmap bitmap = data.getParcelableExtra("data");
                 this.iv_image.setImageBitmap(bitmap);
             }
             try {
                 // 将临时文件删除
                 tempFile.delete();
             } catch (Exception e) {
                 e.printStackTrace();
             }
 
         }
 
         super.onActivityResult(requestCode, resultCode, data);
     }
	 
	 
	 /**
     * 将Bitmap转换成字符串保存至SharedPreferences
     * <p/>
     * 注意:
     * 在压缩图片至输出流时,不要选择CompressFormat.JPEG而该是PNG,否则会造成图片有黑色背景.
     * 详见参考资料二
     */
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        LogUtil.w("imageString:"+imageString.length());
        SharedPreferencesUtil.putString(mContext, CommonConstants.USER_PHOTO, imageString);
//        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putString("image", imageString);
//        editor.commit();
    }


    /**
     * 从SharedPreferences中取出Bitmap,并显示在imageview上
     */
    private void getBitmapFromSharedPreferences(ImageView mImageView) {
//        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
//        String imageString = sharedPreferences.getString("image", "");
        String imageString = SharedPreferencesUtil.getString(mContext, CommonConstants.USER_PHOTO,"");
        if (imageString.equals("")) {
            return;
        }

        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        //第三步:利用ByteArrayInputStream生成Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        mImageView.setImageBitmap(bitmap);
    }
	
	
	/**
     * 从本地读取图片
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapFromLocal(String url) {

        try {
            String fileName = url;
            File file = new File(LOCAL_PATH, fileName);

            if (file.exists()) {
                // 图片压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;// 表示压缩比例，2表示宽高都压缩为原来的二分之一， 面积为四分之一
                options.inPreferredConfig = Bitmap.Config.RGB_565;// 设置bitmap的格式，565可以降低内存占用

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file), null, options);
                LogUtil.w("get local iv");
                return bitmap;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向本地存图片
     *
     * @param url
     * @param bitmap
     */
    public void putBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = url;
            File file = new File(LOCAL_PATH, fileName);
            File parent = file.getParentFile();

            // 创建父文件夹
            if (!parent.exists()) {
                parent.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
 }