package com.odero.bigtwo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.odero.bigtwo.Constants;
import com.odero.bigtwo.R;
import com.odero.bigtwo.models.Result;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.odero.bigtwo.adapter.FirebaseResultsViewHolder.decodeFromFirebaseBase64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ResultDetailFragment extends Fragment {
    private ArrayList<Result> mResults;
    private Result mResult;
    private int mPosition;
    private String mSource;
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 11;
    private String currentPhotoPath;

    @BindView(R.id.frtrackImageView) ImageView mImageLabel;
    @BindView(R.id.frtrackNameTextView) TextView mNameLabel;
    @BindView(R.id.frCollectionName) TextView mCollectionName;
    @BindView(R.id.frCountry) TextView mCountry;
    @BindView(R.id.frGenre) TextView mGenre;
//    @BindView(R.id.goToWeb) Button goToWeb;
    @BindView(R.id.saveToFireBase) Button saveToFireBase;
    @BindView(R.id.frReleaseDate) TextView mReleaseDate;
    @BindView(R.id.frTrackDescriptionTextView) TextView martistName;




    public ResultDetailFragment() {
        // Required empty public constructor
    }


    public static ResultDetailFragment newInstance(ArrayList<Result> restaurants, Integer position, String source) {
        ResultDetailFragment restaurantDetailFragment = new ResultDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(Constants.EXTRA_KEY_RESULT, Parcels.wrap(restaurants));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        args.putString(Constants.KEY_SOURCE, source);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResults = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_RESULT));
        mPosition = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        mResult = mResults.get(mPosition);
        mSource = getArguments().getString(Constants.KEY_SOURCE);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_detail, container, false);
        ButterKnife.bind(this, view);

        if (!mResult.getArtworkUrl100().contains("http")) {
            try {
                Bitmap image = decodeFromFirebaseBase64(mResult.getArtworkUrl100());
                mImageLabel.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // This block of code should already exist, we're just moving it to the 'else' statement:
            Picasso.get()
                    .load(mResult.getArtworkUrl100())
                    .into(mImageLabel);
        }

        //Picasso.get().load(mResult.getArtworkUrl100()).into(mImageLabel);
        mNameLabel.setText(mResult.getTrackName());
        mCollectionName.setText("Collection name :" + mResult.getCollectionName());
        mCountry.setText("Country :" + mResult.getCountry());
        mGenre.setText("Genre :" + mResult.getPrimaryGenreName());
//        mPreview.setText("url :" + mResult.getPreviewUrl());
        mReleaseDate.setText("Release Date :" + mResult.getReleaseDate());
        martistName.setText("Artist Name " + mResult.getArtistName());

        if (mSource.equals(Constants.SOURCE_SAVED)) {
            saveToFireBase.setVisibility(View.GONE);
        } else {
            saveToFireBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    final DatabaseReference resultRef = FirebaseDatabase
                            .getInstance()
                            .getReference(Constants.FIREBASE_CHILD_RESULTS)
                            .child(uid);
                    String name = mResult.getCollectionName();
                    resultRef.orderByChild("name").equalTo(name).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getContext(), "Currently Selected Restaurant already exists", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                DatabaseReference pushRef = resultRef.push();
                                String pushId = pushRef.getKey();
                                mResult.setPushId(pushId);
                                pushRef.setValue(mResult);
                                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@androidx.annotation.NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            });


        }

        return view;
    }


    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mSource.equals(Constants.SOURCE_SAVED)) {
            inflater.inflate(R.menu.menu_photo, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                onCameraIconClicked();
            default:
                break;
        }
        return false;
    }

    public void onCameraIconClicked() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            onLaunchCamera();
        } else {
            // let's request permission.getContext(),getContext(),
            String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // we have heard back from our request for camera and write external storage.
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                onLaunchCamera();
            } else {
                Toast.makeText(getContext(), "Can't open the camera without permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Album_JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        // Log.i(TAG, currentPhotoPath);
        return image;

    }

    public void onLaunchCamera() {

        Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider",
                createImageFile());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        // tell the camera to request write permissions
        takePictureIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Toast.makeText(getContext(), "Image saved!", Toast.LENGTH_LONG).show();
            // For those saving their files in directories private to their apps
            // addrestaurantPicsToGallery();
            // Get the dimensions of the View
            int targetW = mImageLabel.getWidth();
            int targetH = mImageLabel.getHeight();
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Alternative way of determining how much to scale down the image
            //  int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

            // Decode the image file into a Bitmap sized to fill the View

          bmOptions.inSampleSize = calculateInSampleSize(bmOptions, targetW, targetH);
            bmOptions.inPurgeable = true;
            bmOptions.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

            mImageLabel.setImageBitmap(bitmap);
            encodeBitmapAndSaveToFirebase(bitmap);
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;


        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private void addrestaurantPicsToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File restaurantFile = new File(currentPhotoPath);
        Uri restaurantPhotoUri = Uri.fromFile(restaurantFile);
        mediaScanIntent.setData(restaurantPhotoUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RESULTS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mResult.getPushId())
                .child("imageUrl");
        ref.setValue(imageEncoded);
    }




}