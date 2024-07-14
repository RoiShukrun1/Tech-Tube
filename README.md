# TechTube
Welcome to our project!

This repository contains the Tech-Tube project, a web and Android application that replicates the core functionalities of YouTube.
This project is part of the Advanced Systems Programming course and is proudly developed by TechTitans, our in-house company.

## Project Overview:
Tech-Tube is a comprehensive video-sharing platform inspired by YouTube. It allows users to upload, view, and interact with videos. The project includes both a web application and an Android application, ensuring a seamless experience across devices.

## Features
- User Authentication (Sign Up, Login, Logout)
- Video Uploading and Streaming
- Video Management (Edit, Delete)
- Video Searching and Filtering
- User Profiles
- Commenting and Liking Videos
- Dark Mode Support

## New features
User actions are now processed on a server that stores and retrieves data from a dedicated MongoDB server. This enables:
- Storing new user, video, and comment records.
- Recording user actions such as liking, unliking, and subscribing.
- Supporting data modifications: video titles, descriptions, comment content, and more.
- In the side bar menu, users can navigate to their channel page and delete their account.
- In the main page user can view the 10 most view videos and 10 random videos.
- New web pages added: publisher channel page and searched videos page.

# Getting Started

## Instructions to run (Server):
Please note that the server is crucial for the client-side functionality. Running the client without the server may lead to unexpected errors.

1. Navigate to the following directory: Server.
2. Ensure all dependencies mentioned in the package.json are installed.
3. enter: 'npm start'
Please keep the command window open to ensure the server remains operational. Note that the server is running on port 80.

## Instructions to run (Web):

1. Navigate to the following directory: 'asp-tech-titans'
2. Insure you install npm and run the build
3. Navigate to the following directory: 'server'
4. enter: 'npm start'
5. Open browser and enter 'localhost' in the URL bar

## Instruction to run (AndroidApp)

1. Navigate to the following directory: asp-tech-titans-app
2. Add android emulator from version 9.0 and above
3. Add a configuration that runs the deafault activity and pair it to the emulator
4. Run the App using the play button or by pressing shift+F10

## Be aware

All of the mendatory features are working in both of the platforms, Including extra features along. Some of the extra features aren't fully inplemented, please ignore those.

## Basic instractions for the app and web
- Registration: To create an account, navigate to the login screen and click on the "Create Account" link at the bottom of the page.
- Uploading video: To upload a video, click on the add icon, notice: you have to enter all the required fields to upload.
- Deleting a Video: To delete a video, click on the bin icon below the video on the homepage (available only for the publisher of the video).
- Editing Content: To edit the title, description, or a comment, click on the pencil icon next to those fields on the watch page (available for logged-in users only).
- Deleting a Comment: To delete a comment, click on the X icon next to the comment you want to delete (available only for the publisher of the comment).
- Edit the video description (Web): Notice the pencil icon near the video title. Click on it to open the option to edit the video description as well. Press the 'more...' option, and an input window with the current description will be shown.
  
  ![image](https://github.com/AvielSegev/Tech-Titans/assets/127956356/34446a8d-4312-4daf-a82b-0fadacb9b6e5)
- In web The above icons located to the right of the search tab have the following functions, from left to right:
  Toggle dark mode, Upload New Video, Login.
  Additionally, clicking on the publisher's image on both the main page and the video watch page will navigate you to the relevent publisher page.
- In the App: The login button is located in the bottom right corner,the dark mode button is in the upper left corner, the add video button is in the middle of the bottom section.
  
## Working process

- First, we analyzed the assignment requirements and divided the work among team members, documenting everything in Jira.
- We held weekly meetings on Sundays and occasional daily stand-ups.
- Additionally, we added our personal touch to the project, such as designing the logo and naming the app.
- Whenever we encountered bugs or difficult features, we collaborated to find solutions together.
- Throughout the process, we learned a lot about both platforms and strengthened our coding skills.
- We are very pleased with the final result and hope you enjoy it as much as we do.

## Acknowledgements
We would like to thank our course instructors and fellow students for their support and feedback throughout this project.

Happy coding!

The Tech-Titans Team
