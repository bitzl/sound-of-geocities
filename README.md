# Sound of Geocities
How did Geocities sound? A statistical analysis of the Geocities MIDI Archive.



## Processing chains

The analysis relies on processing chains. Some parts are realized via Java Streams.

Some chains might be:

 - Sequence --> Tracks --> Events --> Histogram
 - Sequence --> Tracks per Sequence --> Ordered Events per Sequence --> Chords --> Histogram
 - Sequence --> Tracks --> Events --> Pitches --> Histogram
 - Sequence --> Tracks --> Events --> Pitches --> Histograms --> Clustering (?)


