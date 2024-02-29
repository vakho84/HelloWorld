# Hello World
The Hello World app - free app for introducing popular technologies in Android and Android baisics.
## Application Structure:
App with side menu and 5 fragments.
### 1) Home Fragment
A start fragment contains cards of images loaded from web.</br>
Used RecyclerView to load image cards on screen.</br>
Card contains:
- image author
- image
- favorite status indicator

Used Libs RetroFit, Glide, Room.

### 2) Favorites Fragment
A Favorites fragment contains only  cards of images with favorite status checked.</br>
Used RecyclerView to load image cards on screen.</br>
Card contains:
- image author
- image
- favorite status indicator

Used Libs RetroFit, Glide, Room.

### 3) Gallery Fragment
A fragment with possibility to load image from web</br>
to ImageView by using URL</br>
Indicates data loading process (image downloading).

Used Libs RetroFit, Glide.

### 4) About Fragment
A fragment with detailed information:</br>
- Title
- Logo
- Contact info (phone number, link for web site, email)
- Version

  
Also in this fragment you can change language (English or Russian) and theme (dark, light or system default).

### 5) Details Fragment
A fragment with detailed image information.</br>
Fragment provides next information:
- image id
- image author
- image width
- image height
- url
- download url

## Technologies being used:
Databases: SQLite;
Languages: Kotlin, XML;
Libraries: Kotlin Coroutines, DataBinding, AndroidX, Navigation, JUnit.
## External libraries: 
ROOM, Retrofit, Lottie, Glide, SplashScreen, SwipeRefreshLayout.
