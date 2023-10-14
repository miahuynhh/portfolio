/**
 * Name: Mia Huynh
 * Date: August 8, 2023
 * Section: CSE 154 AC - TA Allison Ho
 *
 * This is the app.js page for my website about Powerpuff Girls.
 * It sends back responses of either a list of songs that all Powerpuff Girls like
 * or some information about songs that each Powerpuff girl likes, including
 * song's name and several lines of lyrics.
 */
'use strict';

const express = require('express');
const app = express();

const SONGSDATA = [
  {
    'name': 'Attention',
    'powerpuff': 'buttercup',
    'lyrics': 'A-T-T-E-N-T-I-ON \n Attention is what I want'
  },
  {
    'name': 'Ditto',
    'powerpuff': 'bubbles',
    'lyrics': 'Stay in the middle \n Like you a little'
  },
  {
    'name': 'New Jeans',
    'powerpuff': 'blossom',
    'lyrics': 'New hair, new tee \n NewJeans, do you see?'
  },
  {
    'name': 'Hype Boy',
    'powerpuff': 'blossom',
    'lyrics': 'I just want you call my phone right now \n I just wanna hear you\'re mine'
  },
  {
    'name': 'Super Shy',
    'powerpuff': 'bubbles',
    'lyrics': 'I\'m super shy, super shy \n But wait a minute while I make you mine, make you mine'
  },
  {
    'name': 'Zero',
    'powerpuff': 'buttercup',
    'lyrics': '\'Cause you know you\'re sparkling like a shooting star \n I can see us going far'
  }
];

/**
 * Returns the list of all NewJeans songs that the Powerpuff Girl likes.
 */
app.get('/songs', function(req, res) {
  let songNames = '';
  for (let i = 0; i < SONGSDATA.length; i++) {
    songNames = songNames + SONGSDATA[i].name + '\n';
  }
  res.type('text').send(songNames);
});

/**
 * Returns information about the songs that a given Powerpuff Girl likes,
 * including the songs' names and several lines of lyrics.
 */
app.get('/songs/:powerpuff', function(req, res) {
  let powerpuffName = req.params['powerpuff'];
  if (powerpuffName === 'bubbles' || powerpuffName === 'blossom' || powerpuffName === 'buttercup') {
    res.json(getSongs(powerpuffName));
  } else {
    res.status(400).json({'error': 'Sorry! There are no powerpuff girl named ' + powerpuffName});
  }
});

/**
 * Retrieves information about the songs that a given Powerpuff Girl likes.
 * @param {string} powerpuffName - the given Powerpuff girl's name.
 * @returns {object} the array containing information about the songs.
 */
function getSongs(powerpuffName) {
  let chosenSongs = [];
  for (let i = 0; i < SONGSDATA.length; i++) {
    if (SONGSDATA[i].powerpuff === powerpuffName) {
      chosenSongs.push(SONGSDATA[i]);
    }
  }
  return chosenSongs;
}

app.use(express.static('public'));
const PORT = process.env.PORT || 8000;
app.listen(PORT);