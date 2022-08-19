package com.example.translationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import androidx.camera.core.*;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.LifecycleOwner;

//import com.google.mlkit.vision.demo.GraphicOverlay;
//import com.google.mlkit.vision.demo.java.VisionProcessorBase;
//import com.google.mlkit.vision.demo.preference.PreferenceUtils;


import android.media.Image;

public class MainActivity extends AppCompatActivity {

    private EditText inputToTranslate;
    private TextView translatedTv;
    private String originalText;
    private String translatedText;


    private static final String key = "AIzaSyClSfDF8l5HGXYaUbz1I9AjtmMBTsZgZDA";
    private static final String postUrl = "https://translation.googleapis.com/language/translate/v2?key=" + key;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    // camera vars
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
    private Image image;
    private int imagerot;

    private ImageCapture imageCapture;

    private final TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);;

    private Context context;

    private boolean home;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        home = true;


        //transcribe = new TextFromImage();

        context = this;

        //recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        imageCapture = new ImageCapture.Builder()
                .build();

        // camera handling from here on out
        previewView = findViewById(R.id.previewView);
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
                System.out.println("camera should be working now");
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
                System.out.println("Theres an issue");
            }
        }, ContextCompat.getMainExecutor(this));



        Button translate = findViewById(R.id.translate);

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executor cameraExecutor = Executors.newSingleThreadExecutor();

                imageCapture.takePicture(ContextCompat.getMainExecutor(context), new ImageCapture.OnImageCapturedCallback() {
                    @SuppressLint("UnsafeOptInUsageError")
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy imageP) {
                        System.out.println("took image");
                        handleImage(imageP);
                        super.onCaptureSuccess(imageP);
                        imageP.close();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        System.out.println(exception);
                        super.onError(exception);
                    }
                });

                //

            }
        });


        // update this to use real layout later
        /*inputToTranslate = findViewById(R.id.inputToTranslate);
        translatedTv = findViewById(R.id.translatedTv);
        Button translateButton = findViewById(R.id.translateButton);



        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String textToTranslate = inputToTranslate.getText().toString();

                    // Serialize request
                    Gson gson = new Gson();
                    String[] text = {textToTranslate};
                    String postBody = gson.toJson(new TranslateRequest(text, "en"));

                    Log.d("MyActivity", postBody);

                    postRequest(postUrl,postBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/


    }

    // new method should switch views, call text from image, and call the translator
    @SuppressLint("UnsafeOptInUsageError")
    void handleImage (ImageProxy imageP) {
        // old method for transcribing image
        //transcribe.analyzeImage(imageP.getImage(), imageP.getImageInfo().getRotationDegrees());

        InputImage image = InputImage.fromMediaImage(imageP.getImage(), imageP.getImageInfo().getRotationDegrees());

        // gets
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // call translate on success
                                translate(visionText.getText());

                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("transcription failed");
                                    }
                                });

        //System.out.println(originalText);
    }

    void translate (String inputText) {
        originalText = inputText;
        setContentView(R.layout.fragment_dashboard);
        home = false;

        TextView untranslated = findViewById(R.id.untranslated);
        translatedTv = findViewById(R.id.translated);

        untranslated.setText(inputText);

        try {
            String textToTranslate = inputText;

            // Serialize request
            Gson gson = new Gson();
            String[] text = {textToTranslate};
            String postBody = gson.toJson(new TranslateRequest(text, "en"));

            Log.d("MyActivity", postBody);

            postRequest(postUrl,postBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
        cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, imageCapture);
    }


    void postRequest(String postUrl,String postBody) throws IOException {
        // Create request and post to Google Cloud Translate API
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(postBody, JSON);
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Convert response body to string
                String responseBody = response.body().string();

                // Deserialize JSON
                Gson gson = new Gson();
                TranslationResponse res = gson.fromJson(responseBody, TranslationResponse.class);
                String[] translations = res.getData().getTranslatedStrings();
                // Updates translated text box from UIThread
                //translatedTv.setText(translations[0]);
                translatedTv.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          translatedTv.setText(translations[0]);
                                      }
                                  });
            }
        });
    }
}