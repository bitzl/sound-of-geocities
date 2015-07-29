# Sound of Geocities

[![Build Status](https://travis-ci.org/bitzl/sound-of-geocities.svg)](https://travis-ci.org/bitzl/sound-of-geocities)
[![codecov.io](http://codecov.io/github/bitzl/sound-of-geocities/coverage.svg?branch=master)](http://codecov.io/github/bitzl/sound-of-geocities?branch=master)


How did Geocities sound? A statistical analysis of the Geocities MIDI Archive.



## Processing chains

The analysis relies on processing chains. Some parts are realized via Java Streams.

Some chains might be:

 - Sequence --> Tracks --> Events --> Histogram
 - Sequence --> Tracks per Sequence --> Ordered Events per Sequence --> Chords --> Histogram
 - Sequence --> Tracks --> Events --> Pitches --> Histogram
 - Sequence --> Tracks --> Events --> Pitches --> Histograms --> Clustering (?)


