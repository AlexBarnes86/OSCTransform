## Describe your project in a few sentences (Minimum 30 words)
This project reads facial detection and feature data from FaceOSC(https://github.com/kylemcdonald/ofxFaceTracker/releases), a basic OSCTransform java project (written by me: https://github.com/ToastedBits/OSCTransform) merges the inputs into a single OSC address for use within Wekinator. Wekinator then sends its output through OSCTransform to be split into 8 "channels" that can be utilized within a virtual modular synthesizer named VCVRack(https://library.vcvrack.com/trowaSoft/cvOSCcv)

My trained wekinator model, code, and VCV rack project are all available in this archive.

## Describe the input(s) you used.

FaceOSC is capable of emitting the following pieces of tracking data:

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

My example project made use of facial position (x, y), mouth height, and eye openess (left and right)

## Describe the output(s) you used.

Outputs in VCV rack followed the following mappings
Facial Position X -> Pitch, quantized to C Maj (so it sounds pleasant)
Facial Position Y -> Lowpass Filter Cutoff
Mouth Height (openness) -> Mix Dry Signal
Left Eye Openness -> Mix Reverb Signal
Right Eye Openness -> Mix Delay Signal

For additional intrigue a clock source rotates between multiple complex oscillators for more dynamic timbres in the resulting audio.

## Explain how you used machine learning in your project. Be sure to specify what type(s) of algorithms you used, and why. (This should be at least 70 words, but might be longer) 

I didn't have too much difficulty in training the algorithm to map my position and posture data as my concept mostly followed easy to understand linear ranges of motion. Though it was linear I relied on the default Neural Network training just to be different from some of the past projects I had done. It worked well.

If I were to take this project further, I would use DTW for gesture recognition to trigger audio envelopes and percussive sounds. Ideally we would be able to expand beyond FaceOSC's capabilites to include hand tracking. Gestures of striking a bell in the air would be pretty neat to have trigger percussive modules in VCVRack.

## Describe the most significant challenges you encountered in the project and how you overcame them (or attempted to). (This should be at least 50 words)

These were all computationally expensive processes to be running simulatenously. Recording with OBS Studio frequently led to dropped audio packets resulting in some unpleasant pops. Stopping recording freed up sufficient resources to have a mostly smooth experience in VCVRack, but still not ideal. Utilizing a dedicated machines for respective responsibilities: one dedicated to audio production with VCVRack only listening for OSC messages, one for processing FaceOSC and Wekinator, and one for video/audio capture would result in the smoohest experience.

## (Optional) How could the Wekinator software be improved to help support you in creating this type of project?

Allow for easier input/output renaming and configuration. A single spreadsheet view with the ability to dynamically add/delete inputs and outputs, and change names, ranges, and hard/soft cutoff configuration would have saved significant time from having to re-create the project. Eventually I found I could edit the settings.xml directly to rename and drop/add inputs, however this still required closing and re-openning the project.

## Provide instructions for compiling any associated inputs and/or outputs, if necessary (including information about any external libraries that are required, or requirements on the operating system or hardware needed to run your project).

OSCTransform
```
$ ./gradlew build run
```

Or simply open in IntelliJ and run the desired main methods

## Provide instructions for how to run and use your project. (This should be at least 20 words, but might be substantially longer, depending on the complexity of your project.)

1. Plugin a webcam
2. Launch OSCTransform
3. Launch FaceOSC
4. Open VCVRack (either provided demo project or start from scratch with trowaSoft's oscCVosc plugin)
5. Select your desired audio output device in the Audio module of VCVRack
6. Open provided Wekinator project
7. Run
8. Move your face around, open/close your eyes, open/close your mouth and see how VCVRack responds.