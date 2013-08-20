monorun-libgdx
==============

A [libgdx](http://libgdx.badlogicgames.com) port of Benjamin Horn's [Monorun!](https://github.com/beije/monorun) javascript game, using the [Artemis entity system framework](http://gamadu.com/artemis/index.html).

Dependencies
------------
* [Java JDK/JRE 1.7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* [libgdx 0.9.8](https://code.google.com/p/libgdx/downloads/list)
* [Artemis 2012-09-20](http://gamadu.com/artemis/download.html)
* [Android SDK/ADT bundle](http://developer.android.com/sdk/index.html)

Download the above libraries and place the relevant files like below to resolve the dependencies:

###monorun-android/libs
* armeabi (libgdx)
* armeabi-v7a (libgdx)
* gdx-backend-android.jar (libgdx)
 
###monorun-core/libs
* gdx.jar (libgdx)
* artemis.jar (artemis)

###monorun-desktop/libs
* gdx-backend-lwjgl-natives.jar (libgdx)
* gdx-backend-lwjgl.jar (libgdx)
* gdx-natives.jar (libgdx)
* gdx-tools.jar (libgdx/extensions/gdx-tools)

Building/running
----------------
1. Open Eclipse and import all projects as "Existing projects into workspace".
2. Run monorun-desktop (or android) as a Java application with entry point Main.java.

**Note:** *The desktop app will pack all the textures needed for the game to run - since Eclipse doesn't sync files automatically the app will crash if the files don't exist prior to launching. If so - manually refresh the projects (F5) and run monorun-desktop (or android) again and it should work.*

Notes
-----
The highscores are submitted and fetched from a remote web API. That means an active internet connection is needed in order to register scores. The checked in URL is pointing to a test server, see the ScoreService class (remove .dev to point to the live API).
