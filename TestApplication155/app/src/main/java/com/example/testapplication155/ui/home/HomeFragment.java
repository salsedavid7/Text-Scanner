package com.example.testapplication155.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.testapplication155.MainActivity;
import com.example.testapplication155.R;
import com.example.testapplication155.databinding.FragmentHomeBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public ProcessCameraProvider cameraProvider;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private Camera camera;
    private ImageCapture imageCapture;
    private ImageProxy imageProxy;
    public PreviewView previewView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // this begins the process of initializing the previewview
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity());

        // camera based on code from https://developer.android.com/training/camerax/
        // creates the camera provider, used for the preview and images
        previewView = binding.previewView;
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
                System.out.println("theres an error anyway");
            }
        }, ContextCompat.getMainExecutor(requireActivity()));

        /*
        */


        //final TextView textView = binding.textHome; // seems to be no text for this
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    // binds the previewview so that it shows what the camera sees
    public void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        //System.out.println(previewView.getSurfaceProvider().isValid());

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        camera = cameraProvider.bindToLifecycle((LifecycleOwner)requireActivity(), cameraSelector, preview);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cameraProvider.unbindAll(); // allows the camera to be seen if the user returns to this screen
        cameraProvider = null;
        cameraProviderFuture = null;
        previewView = null;
        binding = null;
    }
}