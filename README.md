# Crypto App

## Overview

This project is a Java-based application that provides cryptographic functionalities and interacts with a PostgreSQL database. The project uses Swing for the GUI and Ant for building the project.

<img src="/overview/intro.png" style="width:235px;height:310px"/>
<img src="/overview/login.png" style="width:235px;height:310px"/>
<img src="/overview/sign-up.png" style="width:475px;height:310px"/>
<img src="/overview/encrypt-decrypt.png" style="width:300px;height:270px"/>

## Features

- User authentication and management
- Language selection (English and Romanian)
- Database connection and management
- Cryptographic operations

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Ant
- PostgreSQL

## Setup

1. **Clone the repository:**

    ```sh
    git clone https://github.com/glcata/crypto_app.git
    cd crypto-app
    ```

2. **Configure the database:**

    - Ensure PostgreSQL is installed and running.
    - Create a database for the project.
    - Update the database connection details in `src/crypto/sql/DBConnect.java`.

3. **Build the project:**

    ```sh
    ant build
    ```

4. **Run the project:**

    ```sh
    ant run
    ```

## Usage

- Launch the application and use the GUI to interact with the cryptographic functionalities.
- Select the language (English or Romanian) from the intro screen.
- Use the login screen to authenticate and access the main features.

## Project Structure

- `src/crypto/simple_forms/intro.java`: Contains the GUI code for the intro screen.
- `src/crypto/sql/DBConnect.java`: Manages the database connection and operations.
- `build.xml`: Ant build script.

## Dependencies

- PostgreSQL JDBC Driver
- Synthetica Look and Feel

## Authors

- Catalin Glavan

## License

This project is licensed under the MIT License.