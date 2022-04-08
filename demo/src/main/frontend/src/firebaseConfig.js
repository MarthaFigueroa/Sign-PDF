// Import the functions you need from the SDKs you need
// import { initializeApp } from "firebase/app";
// import firebase from 'firebase/app';
// import 'firebase/firestore';
import firebase from 'firebase/compat/app';
import 'firebase/compat/firestore';
import "firebase/compat/storage";
// import "firebase/firestore";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration for fb-crud-react
// const firebaseConfig = {
//   apiKey: "AIzaSyBRgPFGnRhzC9m-vi8Id8SOftgoDmQ-Akw",
//   authDomain: "fb-crud-react-75fd2.firebaseapp.com",
//   projectId: "fb-crud-react-75fd2",
//   storageBucket: "fb-crud-react-75fd2.appspot.com",
//   messagingSenderId: "980648023376",
//   appId: "1:980648023376:web:b3ad0e1e06b3b849d28855"
// };

const firebaseConfig = {
  apiKey: "AIzaSyAhmTegVIUN-VZcZL2LjhfHYm_ojpZaRzo",
  authDomain: "validacion-de-documentos.firebaseapp.com",
  projectId: "validacion-de-documentos",
  storageBucket: "validacion-de-documentos.appspot.com",
  messagingSenderId: "749784893713",
  appId: "1:749784893713:web:6dd07d99112e769c8ae4b0",
  measurementId: "G-V7QXD3BDVX"
};


// Initialize Firebase
if (!firebase.apps.length) {
  firebase.initializeApp(firebaseConfig);
} else {
  firebase.app();
}
// const fb = firebase.initializeApp(firebaseConfig);
const storage = firebase.storage();
const firestore = firebase.firestore();
export {storage, firestore};
