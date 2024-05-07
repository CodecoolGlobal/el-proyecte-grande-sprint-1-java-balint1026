
# PuzzleShowdown


### This project is deployed and can be accessed at http://www.puzzleshowdown.xyz


 This project allows you to play chess puzzles either alone or race against your friends in real-time and climb the leaderboard. It is built using Java Spring Boot for the backend, and Typescript with React for the frontend. The database is hosted on Supabase.

 
![PuzzleShowdown](https://github.com/lmate/el-proyecte-grande-frontend/assets/9825717/244d9c33-850b-4bb2-a702-c56ce1dcae98)


## Features

- Play chess puzzles solo or against friends.
- Real-time gameplay experience.
- Easy-to-use interface built with React.

  <img width="913" alt="ps_readme_2" src="https://github.com/CodecoolGlobal/el-proyecte-grande-sprint-1-java-balint1026/assets/130932233/f62e2083-518d-471b-aede-00fa98318b81">
  <img width="915" alt="ps_readme_1" src="https://github.com/CodecoolGlobal/el-proyecte-grande-sprint-1-java-balint1026/assets/130932233/f6db0d65-931d-47ac-90d2-5f615c195d57">

## Prerequisites

Before running the project, make sure you have the following environment variables set up:

- `DB_URL`: URL for accessing the Supabase hosted SQL database.
- `DB_USERNAME`: Username for accessing the database.
- `DB_PASSWORD`: Password for accessing the database.

  Also make sure you have the following applications installed:
  
- **Java Development Kit (JDK):** Make sure you have JDK installed on your system. You can download it from the [official website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) and follow the installation instructions.

- **Maven:** Maven is used for building and managing the Java project. Ensure you have Maven installed on your system. You can download it from the [official website](https://maven.apache.org/download.cgi) and follow the installation instructions.

- **Node.js and npm:** Node.js is required for running JavaScript applications, and npm is the Node.js package manager. You can download and install Node.js from the [official website](https://nodejs.org/en/download/) or use a package manager for your operating system.



## Setup Instructions

To set up and run the Chess Puzzle Project locally, follow these steps:

1. Clone the repository to your local machine.
   git clone https://github.com/CodeCoolGlobal/el-proyecte-grande-sprint-1-java-balint1026.git
2. Frontend repository:
   git clone https://github.com/lmate/el-proyecte-grande-frontend.git
3. Set up the environment variables in your preferred way. You can either export them directly or use a `.env` file. If you're using IntelliJ IDEA, you can set them up there.
4. Build and run the backend server using Maven.
 mvn spring-boot:run
5. Navigate to the frontend directory and install frontend dependencies with `npm install`.
6. Start the frontend with `npm run dev`.

After this, you can access the project if you navigate to `http://localhost:5173`.

## Contributing

Contributions are welcome! If you would like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them with descriptive messages.
4. Push your changes to your fork.
5. Submit a pull request to the main repository.



