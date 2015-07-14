package com.bitzl.soundofgeocities;

import com.bitzl.soundofgeocities.source.Song;
import com.bitzl.soundofgeocities.source.SongIterator;
import rx.Observable;
import rx.Subscriber;

import java.io.File;
import java.util.Iterator;

public class Application {

    class Songs implements Observable.OnSubscribe<Song> {
        private final Iterator<Song> songIterator;
        public Songs(File file) {
            songIterator = new SongIterator(new File(""));
        }
        public void call(Subscriber<? super Song> subscriber) {
            if (songIterator.hasNext()) {
                subscriber.onNext(songIterator.next());
            }
            else {
                subscriber.onCompleted();
            }
        }
    }


    public static void main(String[] args) {
        Application application = new Application();
        application.main();
    }

    public void main() {
        Observable
                .create(new Songs(new File("midis.zip")))
                .count()
                .finallyDo(System.out::println);
    }

}
