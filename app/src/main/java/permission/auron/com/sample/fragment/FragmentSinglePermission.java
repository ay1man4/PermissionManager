package permission.auron.com.sample.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import permission.auron.com.library.FragmentManagePermission;
import permission.auron.com.library.PermissionResult;
import permission.auron.com.library.PermissionUtils;
import permission.auron.com.sample.R;

import static android.app.Activity.RESULT_OK;

public class FragmentSinglePermission extends FragmentManagePermission {
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    public static String TAG = FragmentSinglePermission.class.getSimpleName();
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_permission, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        floatingActionButton = ((FloatingActionButton) view.findViewById(R.id.fab));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //single permission
                askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
                    @Override
                    public void permissionGranted() {
                        //permission granted
                        //replace with your action

                        Log.d(TAG, "permissionGranted");
                        dispatchTakePictureIntent();

                    }

                    @Override
                    public void permissionDenied() {
                        //permission denied
                        //replace with your action
                        Log.d(TAG, "permissionDenied");

                    }

                    @Override
                    public void permissionForeverDenied() {
                        //permission denied
                        //replace with your action
                        Log.d(TAG, "permissionForeverDenied");
                        showDialog();
                    }
                });
            }
        });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }


    private void sampleAskMultiplePermission() {


        askCompactPermissions(new String[]{PermissionUtils.Manifest_READ_CONTACTS, PermissionUtils.Manifest_WRITE_CONTACTS}, new PermissionResult() {
            @Override
            public void permissionGranted() {
                //permission granted
                //replace with your action

            }

            @Override
            public void permissionDenied() {
                Log.d(FragmentSinglePermission.class.getSimpleName(), "denied");
                //permission denied
                //replace with your action
            }

            @Override
            public void permissionForeverDenied() {

            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.messageperm);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSettingsApp(getActivity());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
