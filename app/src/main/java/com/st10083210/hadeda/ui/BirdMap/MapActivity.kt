package com.st10083210.hadeda.ui.BirdMap

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.st10083210.hadeda.MainActivity
import com.st10083210.hadeda.R
import com.st10083210.hadeda.databinding.ActivityMapBinding
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.PuckBearingSource
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.OnPointAnnotationClickListener
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.locationcomponent.location2
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.data.OverviewViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.state.OverviewViewportState
import com.mapbox.maps.plugin.viewport.viewport
import com.st10083210.hadeda.HotspotApi.HotspotInterface
import com.st10083210.hadeda.HotspotApi.HotspotModel
import com.st10083210.hadeda.MyApplication
import com.st10083210.hadeda.Routing.RouteInterface
import com.st10083210.hadeda.Routing.RouteResponseModel
import com.st10083210.hadeda.Routing.RoutesModel
import com.st10083210.hadeda.UserPreferencesRepository
import com.st10083210.hadeda.ui.Settings.SettingsActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// For LocationManager { class MapActivity : AppCompatActivity(), LocationListener }
class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    // Views
    private lateinit var backBtn: ImageButton
    private lateinit var settingsBtn: ImageButton
    private lateinit var mapView: MapView
    private lateinit var recenterFab: FloatingActionButton
    // private lateinit var huh: FloatingActionButton

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backBtn = binding.bckBtn
        settingsBtn = binding.settingsBtn
        mapView = binding.mapView
        recenterFab = binding.recenterFab

        /*
        huh.setOnClickListener {
            focusOn(-29.43, 31.25)
        }*/

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        settingsBtn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        recenterFab.setOnClickListener {
            recenterUser()
        }
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        val locationComponentPlugin2 = mapView.location2

        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.pulsingEnabled = true
            this.pulsingColor = R.color.argentinian_blue
            this.pulsingMaxRadius = 20f
            this.locationPuck = LocationPuck2D(
                topImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_icon
                ),
                bearingImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_bearing_icon
                ),
                shadowImage = AppCompatResources.getDrawable(
                    this@MapActivity,
                    com.mapbox.maps.plugin.locationcomponent.R.drawable.mapbox_user_stroke_icon
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }

        locationComponentPlugin2.updateSettings2 {
            this.puckBearingEnabled = true
            this.showAccuracyRing = true
            this.puckBearingSource = PuckBearingSource.HEADING
        }

        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        locationComponentPlugin2.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin2.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        // Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun recenterUser() {
        val viewportPlugin = mapView.viewport
        val followPuckViewportState: FollowPuckViewportState = viewportPlugin.makeFollowPuckViewportState(
            FollowPuckViewportStateOptions.Builder()
                .bearing(FollowPuckViewportStateBearing.Constant(0.0))
                .padding(EdgeInsets(200.0 * resources.displayMetrics.density, 0.0, 0.0, 0.0))
                .pitch(0.0)
                .build()
        )

        viewportPlugin.transitionTo(followPuckViewportState) { success ->
        }
    }

    private fun focusOn(lat: Double, lng: Double) {
        val viewportPlugin = mapView.viewport

        var minLatitude = lat - 0.005
        var maxLatitude = lat + 0.005
        var minLongitude = lng - 0.005
        var maxLongitude = lng + 0.005

        val routePoints = LineString.fromLngLats(
            listOf(
                Point.fromLngLat(minLongitude, minLatitude),
                Point.fromLngLat(maxLongitude, maxLatitude)
            )
        )

        val overviewViewportState: OverviewViewportState = viewportPlugin.makeOverviewViewportState(
            OverviewViewportStateOptions.Builder()
                .geometry(routePoints)
                .animationDurationMs(500)
                .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
                .build()
        )

        // val immediateTransition = viewportPlugin.makeImmediateViewportTransition()
        // viewportPlugin.transitionTo(overviewViewportState, immediateTransition)
        viewportPlugin.transitionTo(overviewViewportState) { success ->
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    /*
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            128 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return
                } else {
                    // TODO: Permission denied error handling
                }
            }
        }
    }

    // API GET nearby hotspots
    private fun fetchHotspotsInRegion() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.ebird.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val birdService = retrofit.create(HotspotInterface::class.java)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 128)
            return
        }

        // TODO: Check most accurate provider
        try {
            userLatitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.latitude!!
            userLongitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.longitude!!
        } catch (e: Exception) {
            Toast.makeText(this, "Could get your location", Toast.LENGTH_SHORT).show()
            Log.e("locationManager", e.toString())
        }

        // GET /v2/ref/hotspot/geo QUERY {dist}
        val apiKey = "EBIRD_API_KEY"
        val lat = userLatitude
        val lng = userLongitude
        val dist: Int = distRadius
        val fmt = "json"

        Log.e("Latitude", lat.toString())
        Log.e("Longitude", lng.toString())

        val call = birdService.getNearbyHotspots(apiKey, lat, lng, dist, fmt)
        call.enqueue(object: Callback<List<HotspotModel>> {
            override fun onResponse(
                call: Call<List<HotspotModel>>,
                response: Response<List<HotspotModel>>
            ) {
                if (response.isSuccessful) {
                    Log.e("Request Success", "Status code: ${response.code()}")

                    val hotspots = response.body()

                    if (hotspots != null) {
                        Log.e("Request Success", "-->$hotspots")
                        for (point in hotspots) {
                            addAnnotationToMap(point.longitude, point.latitude)
                        }
                    }
                    else {
                        Log.e("Request Error", "Response body is null")
                    }
                }
                else {
                    Log.e("Request Error", "API request failed with status code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<HotspotModel>>, t: Throwable) {
                Log.e("Request Error", "API request failed with exception: $t")

                // TODO: Display error message to user
            }
        })
    }

    // This makes the BirdHotspots from ebird clickable and draws the route
    private fun addAnnotationToMap(lon : Double?, lat : Double?) {
        bitmapFromDrawableRes(
            this@MapActivity,
            R.drawable.ic_map_point
        )?.let {
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            pointAnnotationManager.addClickListener(object : OnPointAnnotationClickListener {
                override fun onAnnotationClick(annotation: PointAnnotation): Boolean {
                    Log.i("clicked", "$lon,$lat")

                    val origin = Point.fromLngLat(userLongitude, userLatitude)
                    val destination = Point.fromLngLat(lon!!,lat!!)
                    val coordinates = "${origin.longitude()},${origin.latitude()}%3B${destination.longitude()},${destination.latitude()}"

                    val mapBoxBaseURL = "https://api.mapbox.com/"
                    val retrofit = Retrofit.Builder()
                        .baseUrl(mapBoxBaseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val mapbBoxService = retrofit.create(RouteInterface::class.java)

                    val url = "https://api.mapbox.com/directions/v5/mapbox/walking/$coordinates?alternatives=true&geometries=geojson&language=en&overview=full&steps=true&access_token=${getString(
                        R.string.YOUR_MAPBOX_ACCESS_TOKEN)}"

                    Log.d("myTag", "URL: $url")

                    val call = mapbBoxService.getRoute(
                        coordinates = coordinates,
                        alternatives = true,
                        geometries = "geojson",
                        language = "en",
                        overview = "full",
                        steps = true,
                        access_token =  getString(R.string.YOUR_MAPBOX_DOWNLOAD_TOKEN)
                    )

                    call.enqueue(object : Callback<RouteResponseModel> {
                        override fun onResponse (
                            call: Call<RouteResponseModel>,
                            response: Response<RouteResponseModel>
                        ) {
                            if (response.isSuccessful) {
                                val route: RoutesModel? = response.body()?.routes?.firstOrNull()
                                if (route != null) {

                                    val coordinates = route.geometry?.coordinates?.map {
                                        Point.fromLngLat(
                                            it[0],
                                            it[1]
                                        )
                                    }
                                    val geometry = coordinates?.let { it1 ->
                                        LineString.fromLngLats(
                                            it1
                                        )
                                    }
                                    val routeFeature = Feature.fromGeometry(geometry)
                                    val featureCollection =
                                        FeatureCollection.fromFeatures(listOf(routeFeature))

                                    val source = mapStyle.getSourceAs<GeoJsonSource>("route-source")
                                    if (source != null) {
                                        source.featureCollection(featureCollection)
                                    } else {
                                        val sourceBuilder = GeoJsonSource.Builder("route-source")
                                            .featureCollection(featureCollection)
                                        mapStyle.addSource(sourceBuilder.build())

                                        val lineLayer = lineLayer(
                                            "route-layer", "route-source"
                                        ) {
                                            lineCap(LineCap.ROUND)
                                            lineJoin(LineJoin.ROUND)
                                            lineColor(Color.parseColor("#ff0077"))
                                            lineWidth(5.0)
                                        }
                                        mapStyle.addLayer(lineLayer)
                                    }

                                } else {
                                    Log.e("route data", "No route available")
                                }
                            } else {

                            }
                        }
                        override fun onFailure(call: Call<RouteResponseModel>, t: Throwable) {
                            Log.e("API error", "Network error: ${t.message}", t)
                        }
                    })

                    return true
                }
            })

            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(lon!!,lat!!))
                .withIconImage(it)

            pointAnnotationManager.create(pointAnnotationOptions)

        }
    }

    //https://docs.mapbox.com/android/maps/guides/annotations/view-annotations/
    //converting xml to bitmaps
    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
    */
}