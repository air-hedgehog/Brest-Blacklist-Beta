package com.pain.fleetin.brestblacklist.VKUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pain.fleetin.brestblacklist.MainActivity;
import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.Utils;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;

public class VKUpload {

    private Context context;
    private MainActivity activity;


    public VKUpload(Context context, MainActivity activity) {
        this.context = context;
        this.activity = activity;
        activity.uploadProgressNotification();

    }

    public class UploadOnePhoto extends AsyncTask<Void, Void, VKBatchRequest> {


        String picturePath;
        String title;

        public UploadOnePhoto(String picturePath, String title) {
            this.picturePath = picturePath;
            this.title = title;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            activity.nm.cancel(Utils.UPLOAD_NOTIFICATION_ID);
        }

        @Override
        protected VKBatchRequest doInBackground(Void... params) {

            Bitmap photo = rotatePicture(picturePath);
            VKRequest request = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo, VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            return new VKBatchRequest(request);

        }

        @Override
        protected void onPostExecute(VKBatchRequest batch) {
            super.onPostExecute(batch);
            final VKAttachments attachments = new VKAttachments();

            batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {

                @Override
                public void onComplete(VKResponse[] responses) {
                    String newTitle = title;
                    super.onComplete(responses);

                    for (VKResponse response : responses) {
                        VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                        attachments.add(photoModel);
                    }
                    makePost(attachments, newTitle);
                }

                @Override
                public void onError(VKError error) {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.toast_uploading_error),
                            Toast.LENGTH_SHORT).show();
                    activity.nm.cancel(Utils.UPLOAD_NOTIFICATION_ID);
                }
            });
        }
    }

    public Bitmap rotatePicture(String path) {
        int orientation;

        try {

            Bitmap bitmap = BitmapFactory.decodeFile(path);

            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Matrix matrix = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                matrix.postRotate(180);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }

    }

    /*public class UploadTwoPhotos extends AsyncTask<Void, Void, VKBatchRequest> {

        ArrayList<String> picturePaths;
        String title;

        public UploadTwoPhotos(ArrayList<String> picturePaths, String title) {
            this.picturePaths = picturePaths;
            this.title = title;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            nm.cancel(UPLOAD_NOTIFICATION_ID);
        }

        @Override
        protected VKBatchRequest doInBackground(Void... params) {
            ArrayList<String> photos = picturePaths;

            Bitmap photo1 = BitmapFactory.decodeFile(photos.get(0));
            Bitmap photo2 = BitmapFactory.decodeFile(photos.get(1));

            VKRequest request1 = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo1, VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request2 = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo2, VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            return new VKBatchRequest(request1, request2);
        }

        @Override
        protected void onPostExecute(VKBatchRequest batch) {
            super.onPostExecute(batch);
            final VKAttachments attachments = new VKAttachments();

            batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {

                @Override
                public void onComplete(VKResponse[] responses) {
                    String newTitle = title;
                    super.onComplete(responses);

                    for (VKResponse response : responses) {
                        VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                        attachments.add(photoModel);
                    }
                    makePost(attachments, newTitle);
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }

                @Override
                public void onError(VKError error) {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.toast_uploading_error),
                            Toast.LENGTH_SHORT).show();
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }
            });
        }
    }

    public class UploadThreePhotos extends AsyncTask<Void, Void, VKBatchRequest> {

        ArrayList<String> picturePaths;
        String title;

        public UploadThreePhotos(ArrayList<String> picturePaths, String title) {
            this.picturePaths = picturePaths;
            this.title = title;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            nm.cancel(UPLOAD_NOTIFICATION_ID);
        }

        @Override
        protected VKBatchRequest doInBackground(Void... params) {
            ArrayList<String> photos = picturePaths;

            Bitmap photo1 = BitmapFactory.decodeFile(photos.get(0));
            Bitmap photo2 = BitmapFactory.decodeFile(photos.get(1));
            Bitmap photo3 = BitmapFactory.decodeFile(photos.get(2));

            VKRequest request1 = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo1, VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request2 = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo2, VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request3 = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo3, VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);

            return new VKBatchRequest(request1, request2, request3);
        }

        @Override
        protected void onPostExecute(VKBatchRequest batch) {
            super.onPostExecute(batch);
            final VKAttachments attachments = new VKAttachments();

            batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {

                @Override
                public void onComplete(VKResponse[] responses) {
                    String newTitle = title;
                    super.onComplete(responses);

                    for (VKResponse response : responses) {
                        VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                        attachments.add(photoModel);
                    }
                    makePost(attachments, newTitle);
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }

                @Override
                public void onError(VKError error) {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.toast_uploading_error),
                            Toast.LENGTH_SHORT).show();
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }
            });
        }
    }

    public class UploadFourPhotos extends AsyncTask<Void, Void, VKBatchRequest> {

        ArrayList<Uri> picturePaths;
        String title;

        public UploadFourPhotos(ArrayList<Uri> picturePaths, String title) {
            this.picturePaths = picturePaths;
            this.title = title;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            nm.cancel(UPLOAD_NOTIFICATION_ID);
        }

        @Override
        protected VKBatchRequest doInBackground(Void... params) {
            ArrayList<Uri> photos = picturePaths;

            Bitmap photo1 = null;
            Bitmap photo2 = null;
            Bitmap photo3 = null;
            Bitmap photo4 = null;
            try {
                photo1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(0));
                photo2 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(1));
                photo3 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(2));
                photo4 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(3));

            } catch (IOException e) {
                e.printStackTrace();
            }

            VKRequest request1 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo1), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request2 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo2), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request3 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo3), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request4 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo4), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            return new VKBatchRequest(request1, request2, request3, request4);
        }

        @Override
        protected void onPostExecute(VKBatchRequest batch) {
            super.onPostExecute(batch);
            final VKAttachments attachments = new VKAttachments();

            batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {

                @Override
                public void onComplete(VKResponse[] responses) {
                    String newTitle = title;
                    super.onComplete(responses);

                    for (VKResponse response : responses) {
                        VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                        attachments.add(photoModel);
                    }
                    makePost(attachments, newTitle);
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }

                @Override
                public void onError(VKError error) {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.toast_uploading_error),
                            Toast.LENGTH_SHORT).show();
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }
            });
        }
    }

    public class UploadFivePhotos extends AsyncTask<Void, Void, VKBatchRequest> {

        ArrayList<Uri> picturePaths;
        String title;

        public UploadFivePhotos(ArrayList<Uri> picturePaths, String title) {
            this.picturePaths = picturePaths;
            this.title = title;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            nm.cancel(UPLOAD_NOTIFICATION_ID);
        }

        @Override
        protected VKBatchRequest doInBackground(Void... params) {
            ArrayList<Uri> photos = picturePaths;

            Bitmap photo1 = null;
            Bitmap photo2 = null;
            Bitmap photo3 = null;
            Bitmap photo4 = null;
            Bitmap photo5 = null;
            try {
                photo1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(0));
                photo2 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(1));
                photo3 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(2));
                photo4 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(3));
                photo5 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(4));

            } catch (IOException e) {
                e.printStackTrace();
            }

            VKRequest request1 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo1), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request2 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo2), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request3 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo3), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request4 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo4), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            VKRequest request5 = VKApi.uploadWallPhotoRequest(new VKUploadImage(onPortraitOrientation(photo5), VKImageParameters.jpgImage(0.6f)), 0, Utils.FLEETIN_PAIN);
            return new VKBatchRequest(request1, request2, request3, request4, request5);
        }

        @Override
        protected void onPostExecute(VKBatchRequest batch) {
            super.onPostExecute(batch);
            final VKAttachments attachments = new VKAttachments();

            batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {

                @Override
                public void onComplete(VKResponse[] responses) {
                    String newTitle = title;
                    super.onComplete(responses);

                    for (VKResponse response : responses) {
                        VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                        attachments.add(photoModel);
                    }
                    makePost(attachments, newTitle);
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }

                @Override
                public void onError(VKError error) {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.toast_uploading_error),
                            Toast.LENGTH_SHORT).show();
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }
            });
        }
    }*/


    public void makePost(VKAttachments attachments, String message) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, "-" + 71924797,
                VKApiConst.ATTACHMENTS, attachments, VKApiConst.MESSAGE, message));
        post.setModelClass(VKWallPostResult.class);

        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

                Toast.makeText(context.getApplicationContext(),
                        context.getResources().getString(R.string.toast_uploading_done), Toast.LENGTH_LONG).show();
                activity.nm.cancel(Utils.UPLOAD_NOTIFICATION_ID);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(context.getApplicationContext(),
                        context.getResources().getString(R.string.toast_uploading_photo_error), Toast.LENGTH_SHORT).show();
                activity.nm.cancel(Utils.UPLOAD_NOTIFICATION_ID);
            }
        });
    }

    /*public Bitmap onPortraitOrientation (Bitmap bm){
        if(bm.getWidth() < bm.getHeight()) {
            Bitmap bMapRotate;
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bMapRotate = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            bm.recycle();
            bm = null;
            return bMapRotate;
        } else {
            return bm;
        }

    }*/


}
