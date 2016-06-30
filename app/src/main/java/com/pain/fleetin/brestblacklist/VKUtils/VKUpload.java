package com.pain.fleetin.brestblacklist.VKUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;

public class VKUpload {


    private NotificationManager nm;
    private static final int UPLOAD_NOTIFICATION_ID = 127;
    private Context context;

    public VKUpload(Context context) {
        this.context = context;
        uploadProgressNotification();
    }

    private void uploadProgressNotification(){
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_upload)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setTicker(context.getResources().getString(R.string.notification_ticker))
                .setContentTitle(context.getResources().getString(R.string.notification_title))
                .setContentText(context.getResources().getString(R.string.notification_text))
                .setAutoCancel(true)
                .setProgress(100, 0, true);

        Notification notification = builder.getNotification();
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        nm.notify(UPLOAD_NOTIFICATION_ID, notification);

    }


    public void makePost(VKAttachments attachments, String message) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, "-" + 71924797,
                VKApiConst.ATTACHMENTS, attachments, VKApiConst.MESSAGE, message));
        post.setModelClass(VKWallPostResult.class);

        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {

                Toast.makeText(context.getApplicationContext(),
                        context.getResources().getString(R.string.toast_uploading_done), Toast.LENGTH_LONG).show();
                nm.cancel(UPLOAD_NOTIFICATION_ID);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(context.getApplicationContext(),
                        context.getResources().getString(R.string.toast_uploading_photo_error), Toast.LENGTH_SHORT).show();
                nm.cancel(UPLOAD_NOTIFICATION_ID);
            }
        });
    }

    public class UploadOnePhoto extends AsyncTask<Void, Void, VKBatchRequest> {

        ArrayList<Uri> pictureURIs;
        String title;

        public UploadOnePhoto(ArrayList<Uri> pictureURIs, String title){
            this.pictureURIs = pictureURIs;
            this.title = title;
        }

        @Override
        protected VKBatchRequest doInBackground(Void... params) {
            ArrayList<Uri> photos = pictureURIs;

            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photos.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }

            VKRequest request = VKApi.uploadWallPhotoRequest(new VKUploadImage(photo,
                    VKImageParameters.pngImage()), 0, Utils.FLEETIN_PAIN);
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
                    nm.cancel(UPLOAD_NOTIFICATION_ID);
                }
            });
        }
    }


}
