package com.example.mapandroidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.*
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.runtime.image.ImageProvider

/***
 * Демонстрация работы с Яндекс картами
 *
 * Документация : https://yandex.ru/dev/maps/mapkit/doc/android-quickstart/concepts/android/quickstart.html
 *
 */

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: com.yandex.mapkit.mapview.MapView
    private lateinit var button: Button
    private lateinit var placeMark1: PlacemarkMapObject
    private lateinit var placeMark2: PlacemarkMapObject
    private lateinit var placeMark3: PlacemarkMapObject
    private lateinit var bufMark: PlacemarkMapObject

    /*
    Listener-объекты, сообщающие о результате многократных операций или состоянии объекта,
    нужно явно сохранять в памяти
     */
    private var mapObjectTapListener: MapObjectTapListener =
        MapObjectTapListener { mapObject, point ->
            val mark = mapObject as PlacemarkMapObject
            var ppoint: Point = Point(mark.geometry.latitude, mark.geometry.longitude)
            mapView.map.move(
                CameraPosition(ppoint, 16.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 2f), null
            )
            return@MapObjectTapListener true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Задайте API-ключ перед инициализацией MapKitFactory.
         * Рекомендуется устанавливать ключ в методе Application.onCreate,
         * но в данном примере он устанавливается в activity.
         */
        MapKitFactory.setApiKey("ApiKey")

        /**
         * Инициализация библиотеки для загрузки необходимых нативных библиотек.
         * Рекомендуется инициализировать библиотеку MapKit в методе Activity.onCreate
         * Инициализация в методе Application.onCreate может привести к лишним вызовам и увеличенному использованию батареи.
         */
        MapKitFactory.initialize(this)

        // Создание MapView.
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        //53.327300, 50.316413
        // Перемещение камеры в центр Москвы
        mapView = findViewById(R.id.mapView)
        mapView.map.move(
            CameraPosition(Point(53.327300, 50.316413), 16.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f), null
        )


        // пример добавления маркера без картинки
        mapView.map.mapObjects.addPlacemark(Point(53.327300, 50.316413))

        // создание маркера
        placeMark1 = mapView.map.mapObjects.addPlacemark(
            Point(53.327300, 50.316413),
            ImageProvider.fromResource(this, R.drawable.navigation_ic_markerbro)
        )
        placeMark2 = mapView.map.mapObjects.addPlacemark(
            Point(53.327300, 50.416413),
            ImageProvider.fromResource(this, R.drawable.navigation_ic_markerbro)
        )

        // Обработка нажатий на маркеры
        placeMark1.addTapListener(mapObjectTapListener)
        placeMark2.addTapListener(mapObjectTapListener)

        // Обработка нажатия на кнопку
        button.setOnClickListener { test() }


    }

    private fun test() {
        mapView.map.move(
            CameraPosition(Point(5.327300, 5.316413), 16.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 2f), null
        )
    }

    private fun clickMarker(mark: PlacemarkMapObject) {
        bufMark = mark
        bufMark.addTapListener(mapObjectTapListener)
    }


    override fun onStart() {
        super.onStart()
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}




