![enter image description here](https://i.imgur.com/7y28abd.png)
Hadeda is a cutting-edge mobile application designed to enhance the bird-watching experience for enthusiasts. Hadeda focuses on providing users with a feature-rich platform for viewing nearby bird observations, tracking hotspots, and curating their bird sightings. The app, built for the Android platform, is crafted in Kotlin with a modern design that seamlessly transitions between day and night modes.
## App Features

 - Account creation
 - Notable nearby bird sightings
 - Birding hotspots map
 - GPS routing
 - Personal bird collection
 - Post bird observations
 - Light and dark mode
 - Distance preferences (hotspot map)
## Overview
Hadeda serves as a bird-watching companion, empowering users to explore bird observations, discover hotspots, and document their own bird sightings. The app offers a user-friendly interface supported by Firebase for seamless authentication, allowing users to create accounts, log in, and log out with ease.
- **Language:** Kotlin
- **Platform:** Android (Mobile)
- **SDK Version (min):** 31

## Setup

#### Prerequisites

1.  **Android Studio:** Ensure that you have Android Studio installed on your development machine.
2.  **Git:** Have Git installed to clone the project repository.
    

#### Clone the Repository

    git clone https://github.com/deans-bradley/Hadeda-Android.git

#### Open Project in Android Studio

1.  Launch Android Studio.
2.  Click on "Open an Existing Project."
3.  Navigate to the cloned Hadeda project directory and select it.

#### Configure Dependencies

1.  **Firebase:**
    -   Setup Firebase for the project by following this [guide](https://firebase.google.com/docs/android/setup).
    -   Replace the placeholder in `app\google-services.json` with your actual Firebase configuration.
2.  **eBird API Key:**
    -   Acquire an API key from the [eBird API](https://documenter.getpostman.com/view/664302/S1ENwy59#intro).
    -   Replace the placeholder `EBIRD_API_KEY = "API_KEY"` in `Hededa\apikey.properties` with your eBird API key.
3.  **MapBox API Key:**
    -   Acquire the public (access token) and secret (downloads token) token for the [Mapbox API](https://www.mapbox.com/).
    -   Replace the placeholder `MAPBOX_DOWNLOADS_TOKEN="PLACE_MAPBOX_DOWNLOAD_TOKEN_HERE"`   in `Hadeda\gradle.properties` with your Mapbox downloads token.
    - Replace the placeholder `<string name="mapbox_access_token">MAPBOX_ACCESS_TOKEN_HERE</string>` with Mapbox access token in `Hadeda\app\src\main\res\values\strings.xml`

#### Build and Run

1.  **Sync Gradle:**
    
    -   Click on "Sync Project with Gradle Files" in Android Studio to ensure all dependencies are resolved.
2.  **Run the App:**
    
    -   Click on the green play button or use the shortcut `Shift + F10` to build and run the Hadeda app on your emulator or connected device.

#### Explore the Code

-   Delve into the `app/src/main` directory to explore the Kotlin source code, organized into packages such as `ui`, `model`, `layout`, and `res`.

#### Contributing Guidelines

-   Currently, Hadeda is not accepting any contributions. However, you are welcome to discuss this with me at developer@deansbrad.com if you wish to contribute to the project.

#### Issues and Bug Reports

-   Report any issues or bugs using the GitHub Issue Tracker.

#### Happy Coding!

Congratulations! You've successfully set up the Hadeda project on your development environment. Explore the code and enjoy the experience of enhancing this bird-watching app. Happy coding!
