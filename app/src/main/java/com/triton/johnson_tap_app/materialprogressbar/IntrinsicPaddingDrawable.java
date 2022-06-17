package com.triton.johnson_tap_app.materialprogressbar;

/**
 * Created by Iddinesh.
 */
public interface IntrinsicPaddingDrawable {

    /**
     * Get whether this drawable is using an intrinsic padding. The default is {@code true}.
     *
     * @return Whether this drawable is using an intrinsic padding.
     */
    boolean getUseIntrinsicPadding();

    /**
     * Set whether this drawable should use an intrinsic padding. The default is {@code true}.
     *
     * @param useIntrinsicPadding Whether this drawable should use its intrinsic padding.
     */
    void setUseIntrinsicPadding(boolean useIntrinsicPadding);
}