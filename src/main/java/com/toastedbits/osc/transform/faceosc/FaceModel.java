package com.toastedbits.osc.transform.faceosc;

import java.util.List;

/*
FaceOSC

Pose
 * center position: /pose/position
 * scale: /pose/scale
 * orientation (which direction you're facing): /pose/orientation

Gestures
 * mouth width: /gesture/mouth/width
 * mouth height: /gesture/mouth/height
 * left eyebrow height: /gesture/eyebrow/left
 * right eyebrow height: /gesture/eyebrow/right
 * left eye openness: /gesture/eye/left
 * right eye openness: /gesture/eye/right
 * jaw openness: /gesture/jaw
 * nostril flate: /gesture/nostrils

Raw
 * raw points (66 xy-pairs): /raw
 */
public class FaceModel {
    private float centerPositionX, centerPositionY;
    private float scale;
    private float orientationX, orientationY, orientationZ;

    private float mouthWidth;
    private float mouthHeight;
    private float leftEyebrowHeight;
    private float rightEyebrowHeight;
    private float leftEyeOpenness;
    private float rightEyeOpenness;
    private float jawOpenness;
    private float nostirlFlate;

    private List<Float> raw;

    public float getCenterPositionX() {
        return centerPositionX;
    }

    public void setCenterPositionX(float centerPositionX) {
        this.centerPositionX = centerPositionX;
    }

    public float getCenterPositionY() {
        return centerPositionY;
    }

    public void setCenterPositionY(float centerPositionY) {
        this.centerPositionY = centerPositionY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getOrientationX() {
        return orientationX;
    }

    public void setOrientationX(float orientationX) {
        this.orientationX = orientationX;
    }

    public float getOrientationY() {
        return orientationY;
    }

    public void setOrientationY(float orientationY) {
        this.orientationY = orientationY;
    }

    public float getOrientationZ() {
        return orientationZ;
    }

    public void setOrientationZ(float orientationZ) {
        this.orientationZ = orientationZ;
    }

    public float getMouthWidth() {
        return mouthWidth;
    }

    public void setMouthWidth(float mouthWidth) {
        this.mouthWidth = mouthWidth;
    }

    public float getMouthHeight() {
        return mouthHeight;
    }

    public void setMouthHeight(float mouthHeight) {
        this.mouthHeight = mouthHeight;
    }

    public float getLeftEyebrowHeight() {
        return leftEyebrowHeight;
    }

    public void setLeftEyebrowHeight(float leftEyebrowHeight) {
        this.leftEyebrowHeight = leftEyebrowHeight;
    }

    public float getRightEyebrowHeight() {
        return rightEyebrowHeight;
    }

    public void setRightEyebrowHeight(float rightEyebrowHeight) {
        this.rightEyebrowHeight = rightEyebrowHeight;
    }

    public float getLeftEyeOpenness() {
        return leftEyeOpenness;
    }

    public void setLeftEyeOpenness(float leftEyeOpenness) {
        this.leftEyeOpenness = leftEyeOpenness;
    }

    public float getRightEyeOpenness() {
        return rightEyeOpenness;
    }

    public void setRightEyeOpenness(float rightEyeOpenness) {
        this.rightEyeOpenness = rightEyeOpenness;
    }

    public float getJawOpenness() {
        return jawOpenness;
    }

    public void setJawOpenness(float jawOpenness) {
        this.jawOpenness = jawOpenness;
    }

    public float getNostirlFlate() {
        return nostirlFlate;
    }

    public void setNostirlFlate(float nostirlFlate) {
        this.nostirlFlate = nostirlFlate;
    }

    public List<Float> getRaw() {
        return raw;
    }

    public void setRaw(List<Float> raw) {
        this.raw = raw;
    }
}
