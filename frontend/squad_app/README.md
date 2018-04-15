# I Stalk U

I Stalk U is a personal activity Android application that uses the Google Maps API to record the user's daily activity. I Stalk
U calculates and presents statistics including distance traveled, steps taken, hourly movement, and calories burned. This
information is displayed in the Statistics page.

## Features

### - Tutorial
Opening the app for the first time welcomes the user with a sliding tutorial showcasing each feature.

### - Action Bar
The Action Bar houses:
 - app's icon
 - a toggle for the tracking service
 - a date selector
 - additional options
  - Refresh: refreshes statistics
  - Set GPS Frequency
  - Delete File: delete's the info for the selected day

### - Home
The Home screen shows 3 daily activity metrics:
  - Distance traveled (in km)
  - Steps walked
  - Max speed (in km/hour)

The progress towards each of these three metrics is visualized in a progress bar.

To change a daily goal, the user just needs to click the icon associated with that goal. This brings up a dialog that can be adjusted via sliding or manual input.

### - Statistics
I Stalk U calculates and presents statistics including distance traveled, steps taken, and hourly movement. The bar chart on the Statistics page has individual hours on the x-axis and distance traveled in km on the y-axis.

### - Time Machine

 The app’s Time Machine feature allows the user to see all of the
places they’ve visited throughout their day and when they were there. This is made possible by recording time-location
pairs. Every day’s statistics and time-location values will be stored in an archive. The user can scroll to change the time/location of the map marker, which the mapView automatically centers around. Tapping on the marker opens a pop-up with the time at which that location was recorded. Additionally, the user may choose one of the buttons in the bottom-right to get directions to this location through Google Maps.

## Authors
I Stalk U was made by Sung Jun Park and Stephen K Liao in November 2017.

## Libraries
In addition to the Android and Google Maps libraries, we used MPAndroidChart (by Philipp Jahoda).

## Acknowledgements
Special thanks for Jun Ho Lee '16 for testing our app and collecting data. We thank Sergey, Ruibo, Varun, Kirti, and Zhenli for their teaching and feedback.
