import './index.css';
import React from 'react';
import { createRoot, Root } from 'react-dom/client';
import { nextFib } from './fib';

const main: HTMLElement | null = document.getElementById('main');
if (main === null) {
  console.log('Uh oh! no "main" element!');
} else {
  const root: Root = createRoot(main);
  const params: URLSearchParams = new URLSearchParams(window.location.search);
  const firstName: string | null = params.get("firstName");
  const ageParam: string | null = params.get("age");

  if (firstName === null && ageParam === null) {
    root.render(
      <form action="/">
        <p>Hi there! Please enter the following information:</p>
        <p>Your first name: <input type="text" name="firstName"></input></p>
        <p>Your age: <input type="number" name="age" min="0"></input></p>
        <input type="submit" value="Submit"></input>
      </form>
    );
  } else if (firstName === null) {
    root.render(<p>The parameter "firstName" is missing</p>);
  } else if (firstName.length === 0 || firstName.trim().length === 0) {
    root.render(<p>The parameter "firstName" was not a non-empty string</p>);
  } else if (ageParam === null) {
    root.render(<p>The parameter "age" is missing</p>);
  } else {
    const age: number | undefined = parseFloat(ageParam);
    if (isNaN(age) || age < 0 || Math.floor(age) !== age) {
      root.render(<p>The parameter "age" was not a non-negative integer</p>);
    } else {
      const fibNum: number = nextFib(age);
      if (fibNum === age) {
        root.render(
          <div>
            <p>Hi {firstName}! Your age ({age}) is a Fibonacci number!</p>
            <p className="restart"><a href="/">Start Over</a></p>
          </div>
        );
      } else {
        const year: number = fibNum - age;
        root.render(
          <div>
            <p>Hi, {firstName}! Your age ({age}) will be a Fibonacci number in {year} years.</p>
            <p className="restart"><a href="/">Start Over</a></p>
          </div>
        );
      }
    }
  }
}
