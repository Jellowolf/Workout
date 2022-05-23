# Workout Android App
A barebones android app for logging workouts. I had mostly put this together as a way to familiarize myself with native Android development and get some experience in
with Java and Android's Room ORM. It's nothing too impressive or attractive to look at, but its one of the longest running personal projects I've thrown together.
About the most interesting thing about it is that it's set up to sync data from a companion fitbit app, although the method on that is very proof of concept.

![WorkoutCap](https://user-images.githubusercontent.com/6111060/169909901-32532451-387e-4649-a621-e929d856099c.jpg)

## Features
- The main view is a list with a floating action button for adding new workouts.
- You can add and maintain workout types by navigating to settings, there are currently none by default, so it takes adding at least one to use the app.
- You can also import/export data stored in CSV format.
- There's also a feature for syncing data from the companion fitbit app in my WorkoutFitbit repo. It works, but it's more of a proof of concept at the moment. The main
  view starts a REST server when the app opens, which the fitbit app makes calls to, then a user can press the refresh button to see synced data.
