# Documentation

[![](https://i.imgur.com/yi8w1s8.png)](https://i.imgur.com/yi8w1s8.png)

## The problem
The problem is to recreate the application as shown in the mockups provided below. 
The application should fetch the podcast data from the provided API and display it in a list. 
When a list item is tapped, the application should navigate to a new screen and display the podcast's details. 
The user should be able to favourite the podcast by tapping a button.

#### PodcastListViewModel
The `PodcastListViewModel` class is responsible for fetching the podcast data from the API.
It uses the `PodcastService` class to fetch the data and stores the result in the `podcasts` property.
Coroutines are used to make the network request in a background IO thread.

#### Screen 1
The `PodcastListScreen.kt` file defines a composable function that displays a list of podcasts. 
This screen would fetch podcast data from an API and display it in a list format. 
When a podcast item is tapped, it navigates to the `PodcastDetailsScreen` to show the details of the selected podcast.

#### Screen 2
The `PodcastDetails.kt` file defines a composable function `PodcastDetailsScreen` that displays the details of a podcast. It includes:

- A back button to navigate back.
- The podcast title and publisher.
- An image of the podcast.
- A button to mark the podcast as a favorite.
- The podcast description.

The `stripHtml` function is used to remove HTML tags from the podcast description.

