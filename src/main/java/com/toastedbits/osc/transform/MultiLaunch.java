package com.toastedbits.osc.transform;

import com.toastedbits.osc.transform.faceosc.FaceOscToWekinator;
import com.toastedbits.osc.transform.vcvrack.WekinatorToVCVRack;

public class MultiLaunch {
    public static void main(final String[] args) throws Exception {
        FaceOscToWekinator.main(args);
        WekinatorToVCVRack.main(args);
    }
}
