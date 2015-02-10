package com.willowtreeapps.animatedsvgtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;

import oak.svg.AnimatedSvgView;


public class WillowTreeLogoActivity extends Activity {

    private Handler mHandler = new Handler();

    private Button mReset;
    private AnimatedSvgView mAnimatedSvgView;
    private ImageView mSubtitleView;
    private float mInitialLogoOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wt_logo);

        mInitialLogoOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 49,
                getResources().getDisplayMetrics());

        mReset = (Button) findViewById(R.id.reset);
        mAnimatedSvgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        mSubtitleView = (ImageView) findViewById(R.id.wt_logo_bottom);
        mSubtitleView.setVisibility(View.INVISIBLE);
        mAnimatedSvgView.setTranslationY(mInitialLogoOffset);

        mAnimatedSvgView.setGlyphStrings(WillowTreeLogoPaths.WT_LOGO_GLYPHS);

        // ARGB values for each glyph
        mAnimatedSvgView.setFillPaints(
                new int[]{255, 255},
                new int[]{51, 117},
                new int[]{138, 203},
                new int[]{151, 196});

        int traceColor = Color.argb(255, 0, 0, 0);
        int[] traceColors = new int[2]; // 4 glyphs
        int residueColor = Color.argb(50, 0, 0, 0);
        int[] residueColors = new int[2]; // 4 glyphs

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
                mAnimatedSvgView.setTranslationY(mInitialLogoOffset);
                mSubtitleView.setVisibility(View.INVISIBLE);

                animateLogo();
            }
        });


        mAnimatedSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if (state == AnimatedSvgView.STATE_FILL_STARTED) {
                    mSubtitleView.setAlpha(0.0f);

                    mSubtitleView.setVisibility(View.VISIBLE);

                    AnimatorSet set = new AnimatorSet();
                    Interpolator interpolator = new DecelerateInterpolator();
                    ObjectAnimator a1 = ObjectAnimator.ofFloat(mAnimatedSvgView, "translationY", 0);
                    ObjectAnimator a2 = ObjectAnimator.ofFloat(mSubtitleView, "alpha", 1);
                    a1.setInterpolator(interpolator);
                    set.setDuration(1000).playTogether(a1, a2);
                    set.start();
                }
            }
        });
    }

    private void animateLogo() {
        mAnimatedSvgView.start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateLogo();
            }
        }, 1000);
    }
}
