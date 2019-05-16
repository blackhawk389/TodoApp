package com.app.boxee.shopper.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.app.boxee.shopper.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CameraUtils {

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    public static int Gallery_PERMISSION = 22;

    public static boolean hasPermission(Fragment fragment) {
        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Gallery_PERMISSION);
            return false;
        }
        return true;

    }
//
//    public static ArrayList<String> getGalleryImage(Fragment fragment) {
//        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
//                PackageManager.PERMISSION_GRANTED) {
//            fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    Gallery_PERMISSION);
//            return null;
//        }
//        Uri uri;
//        Cursor cursor;
//        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
//        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
//        cursor = fragment.getActivity().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
//        ArrayList<String> al_path = new ArrayList<>();
//        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        String croppedImage = fragment.getString(R.string.croppedImageExt);
//        File[] array = fragment.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                String fileName = file.getName();
//                boolean status = (!fileName.contains(fragment.getString(R.string.croppedImage)))
//                        && (!fileName.contains(croppedImage)) &&
//                        fileName.endsWith(".jpg") && file.length() > 5000;
//                return status;
//            }
//        });
//
//        for (File file : array) {
//            if (!file.getName().contains(croppedImage))
//                al_path.add(file.getAbsolutePath());
//        }
//        while (cursor.moveToNext()) {
//            File file = new File(cursor.getString(column_index_data));
//            if (file.exists() && !file.getName().contains(croppedImage))
//                al_path.add(cursor.getString(column_index_data));
//        }
//        return al_path;
//    }


    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


//    public static void openCameraSession(CameraDialog fragment) {
//        CameraManager manager = (CameraManager) fragment.getActivity().getSystemService(Context.CAMERA_SERVICE);
//        try {
//            String camerId = getCameraId(fragment.getActivity());
//            fragment.characteristics = manager.getCameraCharacteristics(camerId);
//            StreamConfigurationMap map = fragment.characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//            Size largest = Collections.max(
//                    Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
//                    new Camera2Dialog.CompareSizesByArea());
//            fragment.mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
//                    ImageFormat.JPEG, /*maxImages*/2);
//            fragment.mImageReader.setOnImageAvailableListener(
//                    fragment.mOnImageAvailableListener, fragment.mBackgroundHandler);
//            fragment.previewsize = map.getOutputSizes(SurfaceTexture.class)[0];
//            if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                fragment.requestPermissions(new String[]{Manifest.permission.CAMERA},
//                        CAMERA_PERMISSION);
//                return;
//            }
//            manager.openCamera(camerId, fragment.stateCallback, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                switch (type) {
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

//    public static String getCameraId(Activity activity) {
//        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
//        try {
//            for (String cameraId : manager.getCameraIdList()) {
//                CameraCharacteristics characteristics
//                        = manager.getCameraCharacteristics(cameraId);
//                // We don't use a front facing camera in this sample.
//                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
//                if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
//                    continue;
//                }
//                StreamConfigurationMap map = characteristics.get(
//                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//                if (map == null) {
//                    continue;
//                }
//                return cameraId;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static String getDataColumn(Context context, Uri uri,
                                        String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Intent photoIntent(Context context, File photoFile) {
        List<Intent> cameraIntents = new ArrayList<>();
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoFile != null)
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            String packageName = res.activityInfo.packageName;
            Intent intent = new Intent(captureIntent);
            intent.setComponent(
                    new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            cameraIntents.add(intent);
        }

        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        return chooserIntent;
    }

    public static File createImageFile() throws IOException {
        String dir = Environment.getExternalStorageDirectory() + File.separator
                + "Pictures" + File.separator + "SomeFolder" + File.separator;
        File directory = new File(dir);
        if (directory.mkdir()) Log.d("dir created", dir);
        return new File(dir, JPEG_FILE_PREFIX
                + Calendar.getInstance().getTimeInMillis() + JPEG_FILE_SUFFIX);
    }


    private static Bitmap getResizedBitmap(Bitmap source, int size) {

        if (source.getWidth() > size) {
            double ar = (double) source.getHeight() / (double) source.getWidth();

            int y = (int) (ar * size);
            Bitmap result = Bitmap.createScaledBitmap(source, size, y, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        return source;
    }

    public static Bitmap decodeResource(Context context, String fileName) {
        Resources res = context.getResources();

        // First decode with inJustDecodeBounds=true to check1 dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        int size = 500;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, size, size);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fileName, options);
    }


    public static String formatPickImage(Activity activity, String image) {
        int orient;
        String lastImage = image;
        Bitmap bitmap = decodeResource(activity, image);
        Bitmap rotatedBitmap;
        if ((orient = getImageOrientation(image)) != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orient);
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        } else {
            rotatedBitmap = bitmap;
        }
        FileOutputStream out = null;
        if (image != null) {
            try {
                out = new FileOutputStream(image =
                        (getCroppedFile(activity, image)).getAbsolutePath());
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                bitmap.recycle();
                rotatedBitmap.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (Throwable ignore) {
                }
            }

        }
        return image;
    }


    static File getCroppedFile(Context activity, String fileName) {
        try {
            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File croppedImage = new File(storageDir, activity.getString(R.string.croppedImage));
            if (!croppedImage.exists()) {
                croppedImage.mkdir();
            }
            int lastIndexOfDot = fileName.lastIndexOf('.');
            if (lastIndexOfDot > 0) {
                fileName = fileName.substring(lastIndexOfDot + 1);
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File file = new File(croppedImage, timeStamp + "." + fileName);
            if (file.exists())
                file.delete();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}