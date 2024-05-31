# M7019E News Application

This News Application enables users to explore global and local news through the News API.
Users can create an account, log in, and access news specific to their selected country.

## Installation

1. Clone the repository:
   ```bash
   git clone git@github.com:Leohemmingsson/mobile_app.git

3. Obtain an API key:
   
  Visit https://newsapi.org to get your API key.

5. Create a SECRETS.kt file:
   
  In your project directory, create a Kotlin file named SECRETS.kt in utils and add your API key as follows:
  ```bash
  object SECRETS {
      const val API_KEY = "[API_key]"
  } 

6. Run the app:
Launch the app in an Android emulator.
