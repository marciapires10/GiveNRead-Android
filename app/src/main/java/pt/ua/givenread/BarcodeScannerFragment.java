package pt.ua.givenread;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BarcodeScannerFragment extends Fragment {

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private String bookstop;
    private String check_type;

    public BarcodeScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("BookStop") && getArguments().containsKey("CheckType")) {
            bookstop = getArguments().getString("BookStop");
            check_type = getArguments().getString("CheckType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_barcode_scanner, container, false);

        previewView = view.findViewById(R.id.preview_view);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindImageAnalysis(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(getContext()));

        return view;
    }

    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder().setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(getContext()),
                imageProxy -> {
                    @SuppressLint("UnsafeOptInUsageError") Image img = imageProxy.getImage();

                    if (img != null) {
                        InputImage inputImage = InputImage.fromMediaImage(img, imageProxy.getImageInfo().getRotationDegrees());

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

                                                 int valueType = barcode.getValueType();

                                                 if (barcodes.size() > 0 && rawValue != null){
                                                     switch (valueType) {
                                                         case Barcode.TYPE_TEXT:
                                                             BookStopCheckFragment bookStopCheckFragment = new BookStopCheckFragment();
                                                             Bundle qrcode_args = new Bundle();
                                                             qrcode_args.putString("ScanResult", rawValue);
                                                             bookStopCheckFragment.setArguments(qrcode_args);
                                                             getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, bookStopCheckFragment).commit();
                                                             break;
                                                         case Barcode.TYPE_ISBN:
                                                             ISBNResultFragment fragment = new ISBNResultFragment();
                                                             Bundle args = new Bundle();
                                                             args.putString("ISBNResult", rawValue);
                                                             args.putString("BookStop", bookstop);
                                                             args.putString("CheckType", check_type);
                                                             fragment.setArguments(args);
                                                             getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                                                             break;
                                                     }
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
                                ).addOnCompleteListener(task -> imageProxy.close())
                                .addOnFailureListener(e -> e.printStackTrace());
                    }
                });

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(this, cameraSelector,
                imageAnalysis, preview);
    }
}