package com.mlkt.development.rxsamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST_RX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startCheckEditText();
//        testRxFrom();
//        testRxRange();
//        makeRxAsync();
//        testRxMap();
//        testRxBuffer();
//        testRxTake();
//        testRxSkip();
//        testRxDistinct();
//        testRxFilter();
//        testRxMerge();
//        testRxZip();
//        testRxUntil();
//        testRxAll();
    }

    // Проверка удовлетворению условию всех элементов последовательности
    private void testRxAll(){
        Func1<Integer, Boolean> lessThanTen = i -> i < 10;

        // create observable
        Observable<Boolean> observable = Observable
                .from(new Integer[]{1,2,3,4,5,6,7,8})
                .all(lessThanTen);

        // create observer
        Observer<Boolean> observer = new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Boolean s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Получаем результаты до тех пор, пока результат не будет удовлетворять условию
    private void testRxUntil(){
        Func1<Integer, Boolean> isFive = i -> i == 5;

        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{1,2,3,4,5,6,7,8})
                .takeUntil(isFive);

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Объединяем результаты выполнения запросов
    private void testRxZip(){
        Func2<Integer, String, String> zipIntWithString = (i, s) -> s + ": " + i;

        // create observable
        Observable<String> observable = Observable
                .from(new Integer[]{1,2,3})
                .zipWith(Observable.from(new String[]{"One", "Two", "Three"}), zipIntWithString);

        // create observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Объединяем несколько observable
    private void testRxMerge(){
        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{1,2,3})
                .mergeWith(Observable.from(new Integer[]{6,7,8,9}));

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Исключаем данные с помощью фильтра
    private void testRxFilter(){
        Func1<String, Boolean> filterFiveOnly = s -> s.contains("5");

        // create observable
        Observable<String> observable = Observable
                .from(new String[]{"15", "27", "34", "46", "52", "63"})
                .filter(filterFiveOnly);

        // create observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Убираем дубликаты в последовательности
    private void testRxDistinct(){
        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{5,9,7,5,8,6,7,8,9})
                .distinct();
    }

    // Пропускаем указанное количество первых элементов
    private void testRxSkip(){
        // create observable
        Observable<Integer> observable = Observable
                .from(new Integer[]{5,6,7,8,9})
                .skip(2);
    }

    // Получаем указанное количество первых входящих элементов
    private void testRxTake(){
        // create observable

        Observable<Integer> observable = Observable
                .from(new Integer[]{5,6,7,8,9})
                .take(3)
                .doOnRequest(aLong -> Log.d("doOnRequest","called"))
                .doOnEach(notification -> Log.d("doOnEach","called"))
                .doOnTerminate(() -> Log.d("doOnTerminate","called"))
                .doAfterTerminate(() -> Log.d("doAfterTerminate","called"))
                .doOnSubscribe(() -> Log.d("doOnSubscribe","called"))
                .doOnUnsubscribe(() -> Log.d("doOnUnsubscribe","called"))
                .doOnNext(integer -> Log.d("doOnNext","called"));

        // create observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Делим данные по группам
    private void testRxBuffer(){
        // create observable
        Observable<List<Integer>> observable = Observable
                .from(new Integer[]{1,2,3,4,5,6,7,8})
                .buffer(3);

        // create observer
        Observer<List<Integer>> observer = new Observer<List<Integer>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(List<Integer> s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Преобразование элементов
    private void testRxMap(){
        Func1<String, Integer> stringToInteger = s -> Integer.parseInt(s);

        // create observable
        Observable<Integer> observable = Observable
                .from(new String[]{"1", "2", "3", "4", "5", "6"})
                .map(stringToInteger);


        // create observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };

        // subscribe
        observable.subscribe(observer);
    }

    // Делаем синхронный метод асинхронным
    private int longAction(String text) {
        Log.d(TAG,"longAction");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(text);
    }

    class CallableLongAction implements Callable<Integer> {

        private final String data;

        public CallableLongAction(String data) {
            this.data = data;
        }

        @Override
        public Integer call() throws Exception {
            return longAction(data);
        }
    }

    private void makeRxAsync(){
        Observable.fromCallable(new CallableLongAction("5"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> Log.d(TAG,"onNext " + integer));
    }



    // Наблюдатель для запуска метода через интервал времени
    /**
     * Используемые методы
     * - interval - устанавливается промежуток времени через который следует повторить операцию
     */
    private void testRxInterval(){
        // create observable
        Observable<Long> observable = Observable.interval(2000, TimeUnit.MILLISECONDS);
        // create observer
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }
            @Override
            public void onNext(Long s) {
                Log.d(TAG, "onNext: " + s);
            }
        };
        // subscribe
        observable.subscribe(observer);
    }

    // Наблюдатель для последовательности чисел
    /**
     * Используемые методы
     * - range - передаем стартовое число и длину последовательности
     */
    private void testRxRange(){
        // create observable
        Observable<Integer> observable = Observable.range(10, 4);
        // create observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Integer s) {
                Log.d(TAG, "onNext: " + s);
            }
        };
        // subscribe
        observable.subscribe(observer);
    }

    // Наблюдатель для коллекции или массива
    /**
     * Используемые методы
     * - from - для передачи массива/коллекции объектов
     */
    private void testRxFrom() {
        // create observable
        Observable<String> observable = Observable.from(new String[]{"one", "two", "three"});
        // create observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }
        };
        // subscribe
        observable.subscribe(observer);
    }


    // Наблюдатель для поля ввода текста.
    /**
     * используемые операторы:
     * - debounce - искусственная задержка перед вызовом методов
     * - filter - обработка на исключения входящих параметров
     * - distinctUntilChanged - избегаем дублирования вызовов метода с одинаковыми параметрами
     * - switchMap - при поступлении нового запроса, отписываемся от предыдущего в виду неактульности
     */
    public void startCheckEditText(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            RxSearchObservable.fromView(findViewById(R.id.et))
                    .debounce(1000, TimeUnit.MILLISECONDS)
                    .filter(s -> {
                                Log.d("filter","called");
                                if (s.isEmpty()) {
                                    Log.d("filter","empty");
                                    return false;
                                } else {
                                    Log.d("filter","not empty");
                                    return true;
                                }
                            }
                    )
                    .distinctUntilChanged()
                    .switchMap((Func1<String, Observable<?>>) s -> {
                                Log.d("switchMap","called");
                                return checkQuery(s);
                            }


                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> Log.d("onNext","result: "+o.toString()));
        }
    }

    public static class RxSearchObservable {
        public static Observable<String> fromView(EditText searchView) {
            final PublishSubject<String> subject = PublishSubject.create();
            searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    subject.onNext(s.toString());
                }
            });
            return subject;
        }
    }

    public Observable<?> checkQuery(final String query) {
        return Observable.create(subscriber -> {
            Log.d("checkQuery","called");
            if (query.toUpperCase().contains("ROB")) {
                subscriber.onNext("Correct");
            } else {
                subscriber.onNext("Wrong");
            }
        });
    }

}