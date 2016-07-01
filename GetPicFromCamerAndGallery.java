
//�ṩ�������������ѡ��ͼƬ����ȡ��ʾ�ķ���������

public class MainActivity extends Activity {
 
     private static final int PHOTO_REQUEST_CAREMA = 1;// ����
     private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
     private static final int PHOTO_REQUEST_CUT = 3;// ���
 
     private ImageView iv_image;
 
     /* ͷ������ */
     private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
     private File tempFile;
 
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         this.iv_image = (ImageView) this.findViewById(R.id.iv_image);
     }
 
     /*
      * ������ȡ
      */
     public void gallery(View view) {
         // ����ϵͳͼ�⣬ѡ��һ��ͼƬ
         Intent intent = new Intent(Intent.ACTION_PICK);
         intent.setType("image/*");
         // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_GALLERY
         startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
     }
 
     /*
      * �������ȡ
      */
     public void camera(View view) {
         // �������
         Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
         // �жϴ洢���Ƿ�����ã����ý��д洢
         if (hasSdcard()) {
             tempFile = new File(Environment.getExternalStorageDirectory(),
                     PHOTO_FILE_NAME);
             // ���ļ��д���uri
             Uri uri = Uri.fromFile(tempFile);
             intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
         }
         // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CAREMA
         startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
     }
 
     /*
      * ����ͼƬ
      */
     private void crop(Uri uri) {
         // �ü�ͼƬ��ͼ
         Intent intent = new Intent("com.android.camera.action.CROP");
         intent.setDataAndType(uri, "image/*");
         intent.putExtra("crop", "true");
         // �ü���ı�����1��1
         intent.putExtra("aspectX", 1);
         intent.putExtra("aspectY", 1);
         // �ü������ͼƬ�ĳߴ��С
         intent.putExtra("outputX", 250);
         intent.putExtra("outputY", 250);
 
         intent.putExtra("outputFormat", "JPEG");// ͼƬ��ʽ
         intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
         intent.putExtra("return-data", true);
         // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
         startActivityForResult(intent, PHOTO_REQUEST_CUT);
     }
 
     /*
      * �ж�sdcard�Ƿ񱻹���
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
             // ����᷵�ص�����
             if (data != null) {
                 // �õ�ͼƬ��ȫ·��
                 Uri uri = data.getData();
                 crop(uri);
             }
 
         } else if (requestCode == PHOTO_REQUEST_CAREMA) {
             // ��������ص�����
             if (hasSdcard()) {
                 crop(Uri.fromFile(tempFile));
             } else {
                 Toast.makeText(MainActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��", 0).show();
             }
 
         } else if (requestCode == PHOTO_REQUEST_CUT) {
             // �Ӽ���ͼƬ���ص�����
             if (data != null) {
                 Bitmap bitmap = data.getParcelableExtra("data");
                 this.iv_image.setImageBitmap(bitmap);
             }
             try {
                 // ����ʱ�ļ�ɾ��
                 tempFile.delete();
             } catch (Exception e) {
                 e.printStackTrace();
             }
 
         }
 
         super.onActivityResult(requestCode, resultCode, data);
     }
	 
	 
	 /**
     * ��Bitmapת�����ַ���������SharedPreferences
     * <p/>
     * ע��:
     * ��ѹ��ͼƬ�������ʱ,��Ҫѡ��CompressFormat.JPEG������PNG,��������ͼƬ�к�ɫ����.
     * ����ο����϶�
     */
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //��һ��:��Bitmapѹ�����ֽ����������ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //�ڶ���:����Base64���ֽ�����������е�����ת�����ַ���String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //������:��String������SharedPreferences
        LogUtil.w("imageString:"+imageString.length());
        SharedPreferencesUtil.putString(mContext, CommonConstants.USER_PHOTO, imageString);
//        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
//        Editor editor = sharedPreferences.edit();
//        editor.putString("image", imageString);
//        editor.commit();
    }


    /**
     * ��SharedPreferences��ȡ��Bitmap,����ʾ��imageview��
     */
    private void getBitmapFromSharedPreferences(ImageView mImageView) {
//        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //��һ��:ȡ���ַ�����ʽ��Bitmap
//        String imageString = sharedPreferences.getString("image", "");
        String imageString = SharedPreferencesUtil.getString(mContext, CommonConstants.USER_PHOTO,"");
        if (imageString.equals("")) {
            return;
        }

        //�ڶ���:����Base64���ַ���ת��ΪByteArrayInputStream
        byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        //������:����ByteArrayInputStream����Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        mImageView.setImageBitmap(bitmap);
    }
	
	
	/**
     * �ӱ��ض�ȡͼƬ
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapFromLocal(String url) {

        try {
            String fileName = url;
            File file = new File(LOCAL_PATH, fileName);

            if (file.exists()) {
                // ͼƬѹ��
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;// ��ʾѹ��������2��ʾ��߶�ѹ��Ϊԭ���Ķ���֮һ�� ���Ϊ�ķ�֮һ
                options.inPreferredConfig = Bitmap.Config.RGB_565;// ����bitmap�ĸ�ʽ��565���Խ����ڴ�ռ��

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
     * �򱾵ش�ͼƬ
     *
     * @param url
     * @param bitmap
     */
    public void putBitmapToLocal(String url, Bitmap bitmap) {
        try {
            String fileName = url;
            File file = new File(LOCAL_PATH, fileName);
            File parent = file.getParentFile();

            // �������ļ���
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