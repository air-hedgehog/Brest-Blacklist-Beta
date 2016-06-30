package com.pain.fleetin.brestblacklist.new_crime_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pain.fleetin.brestblacklist.MainActivity;
import com.pain.fleetin.brestblacklist.R;
import com.pain.fleetin.brestblacklist.Utils;
import com.pain.fleetin.brestblacklist.VKUtils.TitleAndImages;
import com.pain.fleetin.brestblacklist.model.ModelCard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddingCrimeDialogFragment extends DialogFragment {

    private AddingCrimeListener addingCrimeListener;

    public ModelCard modelCard = new ModelCard();
    public ImageButton photoIcon;
    public ImageButton locationIcon;

    public MainActivity activity;
    private static final int CAMERA_REQUEST = 10;

    private EditText etTitle;
    private EditText etDate;
    private EditText etTime;


    private TitleAndImages titleAndImages = new TitleAndImages();

    private boolean pictureSaved = false;

    private String pictureName;
    private Uri pictureUri;

    public interface AddingCrimeListener {
        void onCrimeAdded(ModelCard newCrime);

        void onCrimeAddingCanceled();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addingCrimeListener = (AddingCrimeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddingCrimeListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                if (titleAndImages.picturesUri().size() <= 5) {
                    titleAndImages.picturesUri().add(pictureUri);
                    photoIcon.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    pictureSaved = true;
                    Toast.makeText(getActivity(), R.string.dialog_toast_image_added, Toast.LENGTH_SHORT).show();
                }


            }

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreateDialog(savedInstanceState);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.new_crime_dialog_label);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_crime, null);

        final TextInputLayout tilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogCrimeTitle);
        etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogCrimeDate);
        etDate = tilDate.getEditText();

        TextInputLayout tilTime = (TextInputLayout) container.findViewById(R.id.tilDialogCrimeTime);
        etTime = tilTime.getEditText();

        final TextView spinnerHint = (TextView) container.findViewById(R.id.hashtag_spinner_hint);
        final FrameLayout spinnerFrame = (FrameLayout) container.findViewById(R.id.spinner_frame);

        final Button takePictureButton = (Button) container.findViewById(R.id.take_picture_button);
        //final Button setLocationButton = (Button) container.findViewById(R.id.set_location_button);
        //final Button removeLocationButton = (Button) container.findViewById(R.id.remove_location_button);
        photoIcon = (ImageButton) container.findViewById(R.id.take_picture_icon);
        locationIcon = (ImageButton) container.findViewById(R.id.set_location_icon);
        final RelativeLayout takePictureFrame = (RelativeLayout) container.findViewById(R.id.take_picture_button_frame);
        final RelativeLayout setLocationFrame = (RelativeLayout) container.findViewById(R.id.set_location_button_frame);

        photoIcon.setBackgroundColor(getResources().getColor(R.color.gray_200));
        locationIcon.setBackgroundColor(getResources().getColor(R.color.gray_200));

        String hashTagBeauty = getResources().getString(R.string.hashtag_beauty);
        String hashTagBuy = getResources().getString(R.string.hashtag_buy);
        String hashTagFun = getResources().getString(R.string.hashtag_fun);
        String hashTagPub = getResources().getString(R.string.hashtag_pub);
        String hashTagTransport = getResources().getString(R.string.hashtag_transport);

        final Spinner spHashtag = (Spinner) container.findViewById(R.id.spDialogCrime);
        List<String> hashtagAssignment = new ArrayList<String>();
        hashtagAssignment.add(hashTagBeauty);
        hashtagAssignment.add(hashTagBuy);
        hashtagAssignment.add(hashTagFun);
        hashtagAssignment.add(hashTagPub);
        hashtagAssignment.add(hashTagTransport);

        ArrayAdapter<String> hashtagAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, hashtagAssignment);

        spHashtag.setAdapter(hashtagAdapter);
        spHashtag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                modelCard.setHashtag((String) adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                modelCard.setHashtag((String) adapterView.getItemAtPosition(1));
            }
        });

        tilTitle.setHint(getResources().getString(R.string.new_title_hint));
        tilDate.setHint(getResources().getString(R.string.new_date_hint));
        tilTime.setHint(getResources().getString(R.string.new_time_hint));

        builder.setView(container);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etDate.length() == 0) {
                    etDate.setText(" ");
                }

                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTime.length() == 0) {
                    etTime.setText(" ");
                }

                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        etTime.setText(Utils.getTime(calendar.getTimeInMillis()));

                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etTime.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        takePictureFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File pictureDirectory = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
                pictureName = getPictureName();
                File imageFile = new File(pictureDirectory, pictureName);
                pictureUri = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File pictureDirectory = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
                pictureName = getPictureName();
                File imageFile = new File(pictureDirectory, pictureName);
                pictureUri = Uri.fromFile(imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);

            }
        });


        builder.setPositiveButton(R.string.dialog_OK_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //activity.pos

                modelCard.setTitle(etTitle.getText().toString());

                if (etDate.length() != 0 || etTime.length() != 0) {
                    modelCard.setDate(calendar.getTimeInMillis());
                } else {
                    modelCard.setDate(Calendar.getInstance().getTimeInMillis());
                }

                if (pictureSaved) {

                    try {
                        Bitmap image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pictureUri);
                        //modelCard.setImageBitmap(image);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "unable to get picture", Toast.LENGTH_LONG).show();
                    }
                }

                addingCrimeListener.onCrimeAdded(modelCard);

                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addingCrimeListener.onCrimeAddingCanceled();
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                if (etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                    spinnerHint.setVisibility(View.INVISIBLE);
                    spHashtag.setEnabled(false);

                    takePictureFrame.setEnabled(false);
                    takePictureButton.setEnabled(false);
                    takePictureButton.setTextColor(getResources().getColor(R.color.gray_200));

                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                            spHashtag.setEnabled(false);
                            takePictureFrame.setEnabled(false);
                            takePictureButton.setEnabled(false);
                            takePictureButton.setTextColor(getResources().getColor(R.color.gray_200));
                            spinnerHint.setVisibility(View.INVISIBLE);
                            spinnerFrame.setBackgroundColor(getResources().getColor(R.color.gray_200));
                            takePictureFrame.setBackgroundColor(getResources().getColor(R.color.gray_200));
                        } else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                            spHashtag.setEnabled(true);
                            takePictureFrame.setEnabled(true);
                            takePictureButton.setEnabled(true);
                            takePictureButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                            spinnerHint.setVisibility(View.VISIBLE);
                            spinnerFrame.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            takePictureFrame.setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            }
        });

        return alertDialog;
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "Brest Blacklist Image " + timestamp + ".jpg";
    }
}
