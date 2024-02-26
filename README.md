# Hello World
The Hello World app - free app for introducing popular technologies in Android and Android baisics.
## Application Structure:
App with side menu and 5 fragments.
### 1) Home Fragment
A start fragment contains cards of images loaded from web.</br>
Card contains 
- image author
- image
- favorite status indicator

Used Libs RetroFit, Glide, Room.

### 2) Favorites Fragment
A Favorites fragment contains only  cards of images with favorite status checked
Card contains
- image author
- image
- favorite status indicator

Used Libs RetroFit, Glide, Room.

### 3) Gallery Fragment
A fragment with possibility to load image from web</br>
to ImageView by using URL</br>
Indicates data loading process (flags downloading).

Used Libs RetroFit, Glide.

### 4) Weather Fragment
A fragment with possibility to get actual weather forecast and to update data using pull to refresh.</br>
Contains data:
- City
- Region
- Country
- Wind
- Temperature
- Weather icon.

Used API from  https://www.weatherapi.com</br>
Used Libs RetroFit, Glide.

### 5) About Fragment
A fragment with detailed information:</br>
- Title
- Logo
- Contact info (phone number, link for web site, email)
- Version

  
Also in this fragment you can change language (English or Russian) and theme (dark, light or system default).
## Technologies being used:
Databases: SQLite;
Languages: Kotlin, XML;
Libraries: Kotlin Coroutines, DataBinding, AndroidX, Navigation, JUnit.
## External libraries: 
ROOM, Retrofit, Lottie, Glide, SplashScreen, SwipeRefreshLayout.
