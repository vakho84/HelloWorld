# Hello World
The Hello World app - free app for introducing popular technologies in Android and Android baisics.
## Application Structure:
App with side menu and 5 fragments.
### 1) Home Fragment
A start fragment ....................
### 2) Weather Fragment
A fragment with possibility to get actual weather forecast and to update data using pull to refresh.</br>
Contains data:</br>
City, Region, Country, Wind, Temperature, Weather icon.</br>
Used API from  https://www.weatherapi.com</br>
Used Libs RetroFit, Glide.
### 3) Flags Fragment
A fragment with list of countries with it's flags</br>
Countries: Austria, Poland, Italy, Colombia, Madagascar, Thailand, Denmark, Switzerland.</br>
Indicates data loading process (flags downloading).</br>
Used Libs Glide.
### 4) Sandbox Fragment
A fragment for experiments and test tasks.</br>
Often new layout and new features.
### 5) About Fragment
A fragment with detailed information:</br>
Title, Logo, Contact info (phone number, link for web site, email), Version. </br>
Also in this fragment you can change language (English or Russian) and theme (dark, light or system default).
## Technologies being used:
Databases: SQLite;
Languages: Kotlin, XML;
Libraries: Kotlin Coroutines, DataBinding, AndroidX, Navigation, JUnit.
## External libraries: 
ROOM, Retrofit, Lottie, Glide, SplashScreen, SwipeRefreshLayout.
