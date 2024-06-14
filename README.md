# GWeather App

### Initial Setup

- Add Firebase to your Android app:
    - Navigate to the Firebase console: Firebase Console.
    - Create a new Firebase project or select an existing one.
    - Add an Android app to your Firebase project:
    - Package name: com.exam.gweather
    - App nickname (optional): Choose a nickname for your app.
    - Debug signing certificate SHA-1 (optional): Required for debug builds.
    - Download the google-services.json file and place it in the app/ directory of your Android project.

- Steps to Configure OpenWeatherMap
  1. Sign up for OpenWeatherMap:
  2. Navigate to the API Keys section to generate a new API key.
  3. Copy your API key. You'll need this key to authenticate your API requests.
  4. Add API key to your project:
     a. Add your OpenWeatherMap API key to .env
  