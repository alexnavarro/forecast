# Forecast

This project is a weather Android app for you to keep updated your preferred places. The wheather provider is the [worldweatheronline api][1] what 
means you need create a api key to build you own weather app.

Third Party Libraries and Reasons
---------------------------------

To be compatible with old versions are used as much as possible Views and Classes from support-library, that includes appcompat and design. 

It is used some libraries that are standard in the market, like retrofit and okhttp3 to handle with network. They are very efficient, flexible. 
For example, [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid), are compatible with retrofit.
 
For loading images was used Picasso because it's simple, powerful and optimize development time. 

To simplify the data persistence layer Suggar ORM was picked.

[Butter Knife](https://github.com/JakeWharton/butterknife) help us with thousands of findsbyId. It implements Dependency Injection and is easy and fast to understand and learn.

[Android Priority Job Queue][https://github.com/yigit/android-priority-jobqueue] was used to avoid Async Task's boilerplate and it is easily schedule jobs (tasks) that run in the background, improving UX and application stability.

[Otto][https://github.com/square/otto] that implements the Observable Design Pattern solved problem to communicate with Job Queue.


Improvements
------------

The project was very simple to apply the DAO Pattern, only a few queries are made, but it could be done in case to keep readability. The most important refactoring that could be applied would be [RxJava](https://github.com/ReactiveX/RxJava), although \n
the available time was short.

Another nice improvement could be using support data binding api.

Definitely, tests should be done.

##Build
------------------

Very straightforward, clone the project and open it in Android Studio or run it buy command line.

##Available Elements
###Main Screen
Main screen of the app, the user can delete a city, see the detail screen or include a new city.

<img src="/screenshots/main_screen.png" width="200">

###Forecast Detail
In this screen are present the current weather and next five days forecast.

<img src="/screenshots/forecast_detail.png" width="200">

###Insert City

This one allows add a new city.

<img src="/screenshots/search.png" width="200">

[1]: http://api.worldweatheronline.com