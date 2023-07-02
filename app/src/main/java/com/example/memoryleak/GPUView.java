package com.example.memoryleak;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GPUView extends Fragment {


    private GLSurfaceView mGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// 实例化自定义的SurfaceView
        mGLSurfaceView = new MyGLSurfaceView(getActivity());

        // 将SurfaceView添加到布局中
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(mGLSurfaceView, layoutParams);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}