```
mvn clean install
```

```
java -jar target/adventure-book-backend-0.0.1-SNAPSHOT.jar
```

http://localhost:8080/swagger-ui/index.html


```
Create New Book Payload

{
  "title": "Test New Book",
  "author": "Leonardo Troeira",
  "difficulty": "MEDIUM",
  "categories": [
    "Adventure",
    "Mystery"
  ],
  "sections": [
    {
      "id": 1,
      "text": "You wake up in a dark prison cell.",
      "type": "BEGIN",
      "options": [
        {
          "description": "Try to open the door",
          "gotoId": 500
        },
        {
          "description": "Look under the bed",
          "gotoId": 20
        }
      ]
    }
  ]
}

```