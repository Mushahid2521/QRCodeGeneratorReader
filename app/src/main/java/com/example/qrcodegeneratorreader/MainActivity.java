package com.example.qrcodegeneratorreader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    EditText qrtext;
    Button generate_btn, scan_btn;
    ImageView qr_image_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrtext = findViewById(R.id.qrtext);
        generate_btn = findViewById(R.id.generate);
        qr_image_view = findViewById(R.id.qrimage);
        scan_btn = findViewById(R.id.scan);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showQR();
            }
        });
    }

    private void scanQR() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan a barcode");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) { ;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    private void showQR() {

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            // Set the value here
            String value = qrtext.getText().toString();

            Bitmap bitmap = barcodeEncoder.encodeBitmap(value, BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = (ImageView) findViewById(R.id.qrimage);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch(Exception e) {

        }

    }
//
//    private Bitmap TextToImageEncode(String Value) throws WriterException {
//        BitMatrix bitMatrix;
//        //Find screen size and set the resolution/size
//        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        Display display = manager.getDefaultDisplay();
//        Point point = new Point();
//        display.getSize(point);
//        int width = point.x;
//        int height = point.y;
//        int smallerDimension = width < height ? width : height;
//        smallerDimension = smallerDimension * 3/4;
//
//
//        try {
//            bitMatrix = new MultiFormatWriter().encode(
//                    Value,
//                    BarcodeFormat.DATA_MATRIX.QR_CODE,
//                    smallerDimension, smallerDimension, null
//            );
//
//        } catch (IllegalArgumentException Illegalargumentexception) {
//
//            return null;
//        }
//
//
//        int bitMatrixWidth = bitMatrix.getWidth();
//
//        int bitMatrixHeight = bitMatrix.getHeight();
//
//        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
//
//        for (int y = 0; y < bitMatrixHeight; y++) {
//            int offset = y * bitMatrixWidth;
//
//            for (int x = 0; x < bitMatrixWidth; x++) {
//
//                pixels[offset + x] = bitMatrix.get(x, y) ?
//                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
//
//        bitmap.setPixels(pixels, 0, smallerDimension, 0, 0, bitMatrixWidth, bitMatrixHeight);
//        return bitmap;
//    }

}