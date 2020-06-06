package com.toastedbits.osc.transform.faceosc;

import java.util.ArrayList;
import java.util.List;

public class OSCFaceConverter {
    private final boolean includePose;
    private final boolean includeGesture;
    private final boolean includeRaw;
    
    private static final int FACE_OSC_RAW_VALUES_SIZE = 66;
    
    private OSCFaceConverter(final boolean includePose, final boolean includeGesture, final boolean includeRaw) {
        this.includePose = includePose;
        this.includeGesture = includeGesture;
        this.includeRaw = includeRaw;
    }

    public List<Object> toOscMessageData(final FaceModel face) {
        List<Object> data = new ArrayList<>();

        if(includePose) {
            data.add(face.getCenterPositionX());
            data.add(face.getCenterPositionY());
        }
        
        if(includeGesture) {
            data.add(face.getScale());
            data.add(face.getOrientationX());
            data.add(face.getOrientationY());
            data.add(face.getOrientationZ());
            data.add(face.getMouthWidth());
            data.add(face.getMouthHeight());
            data.add(face.getLeftEyebrowHeight());
            data.add(face.getRightEyebrowHeight());
            data.add(face.getLeftEyeOpenness());
            data.add(face.getRightEyeOpenness());
            data.add(face.getJawOpenness());
            data.add(face.getNostirlFlate());
        }
        
        if(includeRaw) {
            //Raw data from FaceOSC is always 66 values, but may not be present unless toggled on
            //Other values may not be present as well, but are int/flaots and gracefully default to 0
            if(face.getRaw() == null) {
                for(int i = 0; i < FACE_OSC_RAW_VALUES_SIZE; ++i) {
                    data.add(0);
                }
            }
            else {
                data.addAll(face.getRaw());
            }
        }

        return data;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private boolean includePose;
        private boolean includeGesture;
        private boolean includeRaw;
        
        public Builder withIncludePose(final boolean includePose) {
            this.includePose = includePose;
            return this;
        }

        public Builder withIncludeGesture(final boolean includeGesture) {
            this.includeGesture = includeGesture;
            return this;
        }

        public Builder withIncludeRaw(final boolean includeRaw) {
            this.includeRaw = includeRaw;
            return this;
        }

        public OSCFaceConverter build() {
            return new OSCFaceConverter(includePose, includeGesture, includeRaw);
        }
    }
}
