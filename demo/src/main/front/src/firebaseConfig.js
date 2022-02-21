// Import the functions you need from the SDKs you need
// import { initializeApp } from "firebase/app";
// import firebase from 'firebase/app';
// import 'firebase/firestore';
import firebase from 'firebase/compat/app';
import 'firebase/compat/firestore';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyBRgPFGnRhzC9m-vi8Id8SOftgoDmQ-Akw",
  authDomain: "fb-crud-react-75fd2.firebaseapp.com",
  projectId: "fb-crud-react-75fd2",
  storageBucket: "fb-crud-react-75fd2.appspot.com",
  messagingSenderId: "980648023376",
  appId: "1:980648023376:web:b3ad0e1e06b3b849d28855"
};

// Initialize Firebase
const fb = firebase.initializeApp(firebaseConfig);

export const db = fb.firestore();
