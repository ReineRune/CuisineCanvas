************* Auth Controller *************

1. http://localhost:9393/api/signup   - POST

    {
    "name" : "user4",
    "email" : "user4.gmail.com",
    "password" : "password"
    }

2. http://localhost:9393/api/login  - POST

    {
    "email" : "user1.gmail.com",
    "password" : "password"
    }


************** Recipe Controller **************

1. http://localhost:9393/recipe/add  - POST

    {
    "title": "Chocolate Cake",
    "description": "Delicious chocolate cake recipe",
    "ingredients": {
    "Flour": 2.5,
    "Sugar": 1.5,
    "Cocoa Powder": 0.5
    }
    }

2. http://localhost:9393/recipe/all  - GET

3. http://localhost:9393/recipe/search-recipe/{title}  - GET

4. http://localhost:9393/recipe/{recipeId}/update   - PUT
 
    {
    "description": "Dark chocolate cake recipe",
    "ingredients": {
    "Flour": 2.5,
    "Sugar": 1.5,
    "Cocoa Powder": 2.5
    }
    }
5. http://localhost:9393/recipe/{recipeId}/delete   - DELETE

6. http://localhost:9393/recipe/{recipeId}/reaction   - POST

   Params -    reactionType : LIKE / DISLIKE
7. http://localhost:9393/recipe/{recipeId}/rating   - POST

   Params-     starRating : ONE_STAR / TWO_STAR
8. http://localhost:9393/recipe/{recipeId}/get-avg-rating   - GET



******************** Comment Controller *****************


1. http://localhost:9393/{recipeId}/comment/add   - POST

   Very nice recipe.
2. http://localhost:9393/{recipeId}/{commentId}/update  - PUT

   A very nice recipe.
3. http://localhost:9393/{recipeId}/{commentId}/delete  - DELETE

4. http://localhost:9393/{commentId}/reaction   - POST

    Params-  reactionType   :  DISLIKE/LIKE