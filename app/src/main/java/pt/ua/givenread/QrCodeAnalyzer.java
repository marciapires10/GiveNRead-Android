package pt.ua.givenread;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public final class QrCodeAnalyzer implements ImageAnalysis.Analyzer {


    public QrCodeAnalyzer(Context context) {
        this.context = context;
    }

    Context context;
    private Paint rectPaint;
    private static final int boxColor = Color.BLUE;
    private static final float strokeWidth = 4.0f;

    @Override
    public void analyze(ImageProxy image) {

        @SuppressLint("UnsafeOptInUsageError")
        Image img = image.getImage();

        if (img != null) {
            InputImage inputImage = InputImage.fromMediaImage(img, image.getImageInfo().getRotationDegrees());

            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build();

            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            scanner.process(inputImage)
                    .addOnSuccessListener(
                            barcodes -> {
                                // Task completed successfully
                                for (Barcode barcode: barcodes){
                                    Rect bounds = barcode.getBoundingBox();
                                    Point[] corners = barcode.getCornerPoints();

                                    String rawValue = barcode.getRawValue();

                                    Log.d("RAWVALUE", rawValue);

                                    if (barcodes.size() > 0 && rawValue != null){
                                        //Toast.makeText(context, rawValue, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, BookSearchActivity.class);
                                        context.startActivity(intent);
                                        //HomepageFragment homepageFragment = new HomepageFragment();
                                        //getSupportFragmentManager().beginTransaction().replace(R.id.container, homepageFragment).commit();


                                        Log.d("GO TO", "Main Activity");
                                    }

                                    /**rectPaint = new Paint();
                                    rectPaint.setColor(boxColor);
                                    rectPaint.setStyle(Paint.Style.STROKE);
                                    rectPaint.setStrokeWidth(strokeWidth);

                                    Canvas canvas = new Canvas();
                                    RectF rect = new RectF(bounds);
                                    canvas.drawRect(rect, rectPaint);**/


                                    /**int valueType = barcode.getValueType();
                                    // See API reference for complete list of supported types
                                    switch (valueType) {
                                        case Barcode.TYPE_WIFI:
                                            String ssid = barcode.getWifi().getSsid();
                                            String password = barcode.getWifi().getPassword();
                                            int type = barcode.getWifi().getEncryptionType();
                                            break;
                                        case Barcode.TYPE_URL:
                                            String title = barcode.getUrl().getTitle();
                                            String url = barcode.getUrl().getUrl();
                                            break;
                                    }**/
                                }
                            }
                    ).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    image.close();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
        }
        //image.close();
    }
}