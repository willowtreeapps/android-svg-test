package com.willowtreeapps.animatedsvgtest;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import oak.svg.AnimatedSvgView;


public class MainActivity extends Activity {

    private Handler mHandler = new Handler();

    private Button mReset;
    private AnimatedSvgView mAnimatedSvgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReset = (Button) findViewById(R.id.reset);
        mAnimatedSvgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);

        mAnimatedSvgView.setGlyphStrings(AndroidLogoPaths.ANDROID_GLYPHS);

        // ARGB values for each glyph
        mAnimatedSvgView.setFillPaints(
                new int[]{255, 255, 255, 255},
                new int[]{153, 153, 153, 153},
                new int[]{204, 204, 204, 204},
                new int[]{0, 0, 0, 0});

        int traceColor = Color.argb(255, 0, 0, 0);
        int[] traceColors = new int[4]; // 4 glyphs
        int residueColor = Color.argb(50, 0, 0, 0);
        int[] residueColors = new int[4]; // 4 glyphs

        // Every glyph will have the same trace/residue
        for (int i = 0; i < traceColors.length; i++) {
            traceColors[i] = traceColor;
            residueColors[i] = residueColor;
        }
        mAnimatedSvgView.setTraceColors(traceColors);
        mAnimatedSvgView.setTraceResidueColors(residueColors);

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimatedSvgView.reset();
                mAnimatedSvgView.start();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimatedSvgView.start();
            }
        }, 1000);
    }
}
