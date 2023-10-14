# Powerpuff-NewJeans API Documentation
The Powerpuff-NewJeans API provides information about the various songs by the
girl group NewJeans that the Powerpuff Girls like.

## Get a list of all NewJeans songs in this service
**Request Format:** /songs

**Request Type:** GET

**Returned Data Format**: Plain Text

**Description:** Returns a list of all the NewJeans songs that are included in this API.

**Example Request:** /songs

**Example Response:**
```
Attention
Ditto
New Jeans
Hype Boy
...
```

**Error Handling:**
- N/A

## Get information about the NewJeans songs that a Powerpuff girl likes.
**Request Format:** /songs/:powerpuff

**Request Type:** GET

**Returned Data Format**: JSON

**Description:** *Given a valid Powerpuff Girl name, it returns a JSON of the NewJeans songs
that the given girl likes, including the name of the song and a few line of lyrics.
The only valid Powerpuff Girl names are "bubbles", "blossom", and "buttercup" with
no capitalization or spaces.

**Example Request:** /songs/bubbles

**Example Response:**
*Fill in example response in the {}*

```json
[
  {
    "name": "Ditto",
    "powerpuff": "bubbles",
    "lyrics": "Stay in the middle \n Like you a little"
  },
  {
    "name": "Super Shy",
    "powerpuff": "bubbles",
    "lyrics": "I'm super shy, super shy \n But wait a minute while I make you mine, make you mine"
  }
]
```

**Error Handling:**
- A possible 400 (invalid request) error:
  - If passed in an invalid Powerpuff girl name, returns an error with the message:
  `Sorry! There are no powerpuff girl named {powerpuff}`

