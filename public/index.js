/*
 * Name: Mia Huynh
 * Date: August 8, 2023
 * Section: CSE 154 AC - TA Allison Ho
 *
 * This is the index.js page for my website about Powerpuff Girls.
 * It is used by index.html for all parts.
 * When the user clicks buttons on the page, it randomly shows emojis that
 * each Powerpuff Girl likes, an activity each girl likes, a meaning behind
 * each girl's name, and the girls' favorite NewJeans songs.
 */
"use strict";
(function() {

  const BORED_API_URL = "http://www.boredapi.com/api/activity";
  const DICT_API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

  window.addEventListener("load", init);

  /**
   * Sets up the emoji buttons, activity radio buttons, name meaning buttons,
   * all songs button, and personal picks radio buttons for the Powerpuff Girls page.
   * A user can generate random emojis for each girl, generate an activity each girl likes,
   * view a meaning behind each girl's name, view all NewJeans songs the girls like,
   * and view each girl's favorite NewJeans songs.
   */
  function init() {
    id("bubbles-emoji-btn").addEventListener("click", () => showEmoji("blue", "bubbles"));
    id("blossom-emoji-btn").addEventListener("click", () => showEmoji("pink", "blossom"));
    id("buttercup-emoji-btn").addEventListener("click", () => showEmoji("green", "buttercup"));

    let activityRadios = qsa("input[name='activity']");
    for (let i = 0; i < activityRadios.length; i++) {
      activityRadios[i].addEventListener("change", getActivity);
    }

    id("bubbles-meaning-btn").addEventListener("click", () => getMeaning("bubbles"));
    id("blossom-meaning-btn").addEventListener("click", () => getMeaning("blossom"));
    id("buttercup-meaning-btn").addEventListener("click", () => getMeaning("buttercup"));

    id("songs-btn").addEventListener("click", getSongs);

    let songRadios = qsa("input[name='powerpuff']");
    for (let i = 0; i < songRadios.length; i++) {
      songRadios[i].addEventListener("change", (event) => {
        getPersonalPick(event.currentTarget.value);
      });
    }
  }

  /**
   * Generates a random emoji image in a give  color for a given Powerpuff girl
   * and makes it visible on the page.
   * All emoji images are retrieved from emojipedia.org.
   * @param {string} color - given color.
   * @param {string} powerpuff - given Powerpuff girl.
   */
  function showEmoji(color, powerpuff) {
    let emojiImg = document.createElement("img");
    let num = Math.floor(Math.random() * 10) + 1;
    emojiImg.src = "img/" + color + "-" + num + ".png";
    emojiImg.alt = "a random " + color + " iphone emoji";
    emojiImg.classList.add("emojis-img");
    id(powerpuff + "-emojis").appendChild(emojiImg);
  }

  /**
   * Fetches an activity from Bored API based on what radio the user chooses.
   * The chosen activity can be of type "education", "recreational", "social",
   * "diy", "charity", "cooking", "relaxation", "music", or "busywork".
   */
  function getActivity() {
    let activityType = this.value;
    id("error").classList.add("hidden");
    fetch(BORED_API_URL + "?type=" + activityType)
      .then(statusCheck)
      .then(resp => resp.json())
      .then(showActivity)
      .catch(handleError);
  }

  /**
   * Displays the fetched activity onto the page, including information about
   * the activity type, the number of participants, and the accessibility level.
   * @param {object} resp - the fetched activity.
   */
  function showActivity(resp) {
    clearActivity();
    let activity = gen("p");
    activity.textContent = resp.activity;
    id("generated-activity").appendChild(activity);

    let type = gen("p");
    type.textContent = "type: " + resp.type;
    id("generated-activity").appendChild(type);

    let participants = gen("p");
    participants.textContent = "participants: " + resp.participants;
    id("generated-activity").appendChild(participants);

    let accessibility = gen("p");
    accessibility.textContent = "accessibility: " + resp.accessibility;
    id("generated-activity").appendChild(accessibility);
  }

  /**
   * Clears the existing activity off the page.
   */
  function clearActivity() {
    let paragraphs = qsa("#generated-activity p");
    for (let i = 0; i < paragraphs.length; i++) {
      paragraphs[i].parentNode.removeChild(paragraphs[i]);
    }
  }

  /**
   * Fetches one of the meanings of the given Powerpuff girls's name from the Dictionary API.
   * @param {string} powerpuff - given Powerpuff girl's name.
   */
  function getMeaning(powerpuff) {
    id("error").classList.add("hidden");
    fetch(DICT_API_URL + powerpuff)
      .then(statusCheck)
      .then(resp => resp.json())
      .then((resp) => showMeaning(resp, powerpuff))
      .catch(handleError);
  }

  /**
   * Displays the fetched meaning of the given Powerpuff girl onto the page,
   * including information about the Part of Speech and the Definition.
   * Disables the Powerpuff girl's name meaning button once clicked.
   * @param {object} resp - the fetched meaning.
   * @param {string} powerpuff - the given Powerpuff girl.
   */
  function showMeaning(resp, powerpuff) {
    let meanings = resp["0"]["meanings"];
    let meaning = meanings[0];
    let partOfSpeech = meaning.partOfSpeech;
    let partOfSpeechP = gen("p");
    partOfSpeechP.textContent = "Part of Speech: " + partOfSpeech;
    id(powerpuff + "-meaning-btn").appendChild(partOfSpeechP);
    let definition = meaning.definitions["0"].definition;
    let definitionP = gen("p");
    definitionP.textContent = "Definition: " + definition;
    id(powerpuff + "-meaning-btn").appendChild(definitionP);
    id(powerpuff + "-meaning-btn").disabled = true;
  }

  /**
   * Fetches the list of songs that the Powerpuff Girls like from the Powerpuff-NewJeans API.
   */
  function getSongs() {
    id("error").classList.add("hidden");
    fetch("/songs")
      .then(statusCheck)
      .then(resp => resp.text())
      .then(showSongNames)
      .catch(handleError);
  }

  /**
   * Displays the fetched list of songs onto the page.
   * Disables the show all songs button once clicked.
   * @param {object} resp - the fetched list of songs.
   */
  function showSongNames(resp) {
    let songNamesSplit = resp.split('\n');
    for (let i = 1; i < songNamesSplit.length; i++) {
      let songName = gen("p");
      songName.textContent = '' + i + '. ' + songNamesSplit[i - 1];
      id("all-songs-container").appendChild(songName);
    }
    id('songs-btn').disabled = true;
  }

  /**
   * Fetches information about the songs that a given Powerpuff girl likes
   * from the Powerpuff-NewJeans API.
   * @param {string} powerpuff - the given Powerpuff girl's name.
   */
  function getPersonalPick(powerpuff) {
    id("error").classList.add("hidden");
    fetch("/songs/" + powerpuff)
      .then(statusCheck)
      .then(resp => resp.json())
      .then((resp) => showPersonalPick(resp, powerpuff))
      .catch(handleError);
  }

  /**
   * Displays the fetched information about the songs that a given Powerpuff girl likes
   * onto the page, including the name of the song and several lines of lyrics.
   * @param {object} resp - the fetched information about the songs.
   * @param {string} powerpuff - the given Powerpuff girl's name.
   */
  function showPersonalPick(resp, powerpuff) {
    id("personal-picks-container").innerHTML = "";
    id("personal-picks-container").className = "";
    id("personal-picks-container").classList.add(powerpuff + "-border");
    for (let i = 0; i < resp.length; i++) {
      let songName = gen("p");
      songName.textContent = resp[i].name;
      id("personal-picks-container").appendChild(songName);
      let lyricsSplit = resp[i].lyrics.split("\n");
      for (let j = 0; j < lyricsSplit.length; j++) {
        let line = gen("p");
        line.textContent = "🎵 " + lyricsSplit[j] + " 🎵";
        id("personal-picks-container").appendChild(line);
      }
    }
  }

  /**
   * Displays an image saying there has been an error fetching the response.
   */
  function handleError() {
    id("error").classList.remove("hidden");
  }

  /**
   * Retrieved from CSE 154 Summer 2023 Lecture code
   * Checks whether the fetched response is in the ok range.
   * @param {object} res - the fetched response.
   * @returns {object} the unchanged fetched response if there was no error.
   */
  async function statusCheck(res) {
    if (!res.ok) {
      throw new Error(await res.text());
    }
    return res;
  }

  /**
   * Retrieved from CSE 154 Summer 2023 lecture code.
   * Creates an element of the given HTML tag.
   * @param {string} tagName - the HTML tag.
   * @returns {object} - a DOM element of the given HTML tag.
   */
  function gen(tagName) {
    return document.createElement(tagName);
  }

  /**
   * Retrieved from CSE 154 Summer 2023 lecture code.
   * Returns the element that has the ID attribute with the specified value.
   * @param {string} name - element ID.
   * @returns {object} - DOM object associated with ID.
   */
  function id(name) {
    return document.getElementById(name);
  }

  /**
   * Retrieved from CSE154 Summer 2023 lecture code.
   * Returns the array of elements that have the given CSS selector.
   * @param {string} selector - CSS selector.
   * @returns {object} - array of DOM objects associated with CSS selector.
   */
  function qsa(selector) {
    return document.querySelectorAll(selector);
  }
})();