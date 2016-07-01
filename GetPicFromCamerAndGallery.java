
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
 }