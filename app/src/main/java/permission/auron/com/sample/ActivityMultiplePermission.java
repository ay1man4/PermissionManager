package permission.auron.com.sample;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import permission.auron.com.library.ActivityManagePermission;
import permission.auron.com.library.PermissionResult;
import permission.auron.com.library.PermissionUtils;
import permission.auron.com.sample.checkPermission.ActivityCheckPermission;

public class ActivityMultiplePermission extends ActivityManagePermission {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static String TAG = ActivityMultiplePermission.class.getSimpleName();
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle(R.string.askMultiPermission);
        floatingActionButton = ((FloatingActionButton) findViewById(R.id.fab));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sampleAskMultiplePermission();
            }
        });


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void sampleAskMultiplePermission() {
        String permissionAsk[] = {PermissionUtils.Manifest_CAMERA, PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE};

        askCompactPermissions(permissionAsk, new PermissionResult() {
            @Override
            public void permissionGranted() {
                //permission granted
                //replace with your action
                dispatchTakePictureIntent();
            }

            @Override
            public void permissionDenied() {
                //permission denied
                //replace with your action
            }

            @Override
            public void permissionForeverDenied() {

                showDialog();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ask_single_activity:
                this.finish();
                return true;
            case R.id.ask_multi_activity:
                this.finish();
                return true;
            case R.id.ask_single_fragment :
                startActivity(new Intent(ActivityMultiplePermission.this, ActivityContainer.class));
                return true;
//            case R.id.ask_annotation :
//                startActivity(new Intent(ActivityMultiplePermission.this, ActivityAnnotation.class)); work in progress ;-)
 //               return true;
            case R.id.check_permission:
                startActivity(new Intent(ActivityMultiplePermission.this, ActivityCheckPermission.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(ActivityMultiplePermission.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.attention);
        builder.setMessage(R.string.messageperm);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openSettingsApp(ActivityMultiplePermission.this);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}