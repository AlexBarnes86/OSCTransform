# OSC Transform

This is a quick and dirty project to split and join Open Sound Control (OSC) messages for use to and from [FaceOSC](https://github.com/kylemcdonald/ofxFaceTracker/releases), [Wekinator](http://www.wekinator.org/), and [VCVRack](https://vcvrack.com/)

* Converts multi-address ingress OSC messages as exposed by FaceOSC to a single OSC address for use in Wekinator
* Converts single-address egress OSC messages as exposed by Wekinator to multiple OSC addresses for use in VCVRack

All of the applications in use are Free and Open Source Software (FOSS)

* FaceOSC is a simple to use desktop face tracking application that uses a webcam or videos to track and emit detected face positioning and features via OSC
* Wekinator is a desktop program that sends and recieves OSC messages and acting as trainable machine learning middleware
* VCVRack is a virtual modular synthesizer (Eurorack). It is not normally capable of utilizing the OSC protocol, but a [free plugin](https://library.vcvrack.com/trowaSoft/cvOSCcv) by trowaSoft allows it to send and recieve OSC messages

See: http://opensoundcontrol.org/introduction-osc

## Getting Started

Recommend using a java version manager such as [jabba](https://github.com/shyiko/jabba) to install an appropriate JDK

Developed with [IntelliJ](https://www.jetbrains.com/idea/) against java vm 1.13.0

```bash
$  jabba install openjdk@1.1.3.0
```

This project uses the [Gradle build system](https://gradle.org/gradle) and is self bootstrapping
Once an appropriate jvm is on the path this project can be run with

```bash
$  ./gradlew run
```

This will launch both FaceOSC(8338) -> (6448)Wekinator and Wekinator(12000) -> VCVRack(7001) listeners, default OSC/UDP ports are listed in parenthesis and easily editable in the source code. 

Individual main methods may be run from your IDE are found in:
* FaceOscToWekinator.java 
* WekinatorToVCVRack.java

### Prerequisites

Java 1.8

## Built With

*  [Gradle](https://gradle.org/gradle)
*  [IntelliJ](https://www.jetbrains.com/idea/)
*  [JDK 8](https://openjdk.java.net/)

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Alexander Barnes** - *Initial work* - [ToastedBits](http://toastedbits.com/)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* [FaceOSC](https://github.com/kylemcdonald/ofxFaceTracker/releases)
* [VCVRack](https://vcvrack.com/)
* [Wekinator \| Software for real-time, interactive machine learning](http://www.wekinator.org/) by Rebecca Fiebrink
* [Machine Learning for Musicians and Artists - an Online Machine Art Course at Kadenze](https://www.kadenze.com/courses/machine-learning-for-musicians-and-artists-v)