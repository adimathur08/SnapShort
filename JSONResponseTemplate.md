### RESPONSE TEMPLATE USED

```JSON
{
  "status": "XXX", // Specific HTTP status code (e.g., "200" for success, error code otherwise)
  "data": [ // Array of objects containing data (used for successful responses)
    {
      "key1": "value1",
      "key2": "value2",
      // ... other data properties
      "nestedArray": [ // Optional nested array of data
        "data1",
        "data2",
        // ... nested array elements
      ]
    },
    // ... other objects in the array (if applicable)
  ],
  "errors": [ // Array of error details (used for error responses)
    {
      "message": "Error message." // Clear and informative message
    }
  ]
}

```


### TEMPLATE COMBINED EXAMPLE:

```JSON
{
  "status": "XXX",
  "data": {
    "id": 1,
    "title": "Introduction to REST APIs",
    "content": "This blog post provides a basic introduction to REST APIs...",
    "author": {
      "id": 2,
      "name": "Bard"
    },
    "comments": [
      {
        "id": 3,
        "content": "This is a helpful explanation!",
        "author": {
          "id": 1,
          "username": "user123"
        }
      }
    ]
  },
  "errors": [
    {
      "message": "Blog post with ID 1 not found."
    }
  ]
}
```