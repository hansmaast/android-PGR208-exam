# android-exam

# Exam report
Working with this exam has been a good challenge, and i have learned a lot along the way. I will summarize the techniques i have used briefly, to keep the report short, as you described.   

The app consists of three activities. The first one is the SplashActivity, which starts the MainActivity when the app is configured. The MainActivity consists of two Fragments, one where you can search and choose a destination, and one where you see the details of the selected destination. The last Activity is the MapsActivity that displays the selected destination with a point on GoogleMaps, along with the destination name. For passing data between the fragments i have used SafeArgs, while passing data to the MapActivity is done by intent.putExtra() and intent.getStringExtra().

   
I have used Retrofit for fetching data from the Api, and Moshi for parsing the JSON data into kotlin data classes. For storing the data on the device I have used SQLite along with the Room persistence library. The response of the Api gets stored in the Room Database, and the ViewModel gets its data from the DB.
The Api calls and database actions are running on a different Thread than the UI-Thread, this is to avoid blocking the UI. For achieving this i have used Kotlin Coroutines. For displaying the Image in the details view, i have used Glide, along with a BindingAdapter attached to the ImageView.

   
I Have used DataBinding to bind the data of the ViewModel to the UI. This provides a intuitive approach to passing data into the UI-components, and prevents the activities for doing all the UI framework calls.

   
The first time you open the app, it will load the data directly from the Api, since it is no data available on the device. The app will update the destinations (on a separate thread) in the db after you close the app an open it.

   
The search function is a wildcard query on the database, so if you write “Oslo” in the search field, it will return all destination names that has the letters “Oslo” in it.

    
For the UI-components I have used the standard components found in Android Studio. These components includes ConstraintLayout, RecyclerView, AppbarLayout, ImageView, TextView, ProgressBar, CoordinatorLayout, NestedScrollView, FloatingActionButton, CardView and SearchTextInput.

   
I have been using libraries of Android Architecture Components, that is a part of Android Jetpack, as i found it being a lot of well documented features compared to older libraries.

     
The resources I have used for this project is mainly the codelabs found in this link: https://codelabs.developers.google.com/android-kotlin-fundamentals/​, and the documentation found at ​https://developer.android.com/docs​.
