import firebase from 'firebase/compat/app';
import 'firebase/compat/firestore';
import "firebase/compat/storage";
import axios from '../axios.js';
import { toast } from 'react-toastify';
import {
  GoogleAuthProvider,
  getAuth,
  signInWithPopup,
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  sendPasswordResetEmail,
  signOut
} from "firebase/auth";

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
  const app = firebase.initializeApp(firebaseConfig);
  let auth = getAuth(app);

const googleProvider = new GoogleAuthProvider();
googleProvider.setCustomParameters({
  prompt: 'select_account'
});

const signInWithGoogle = async () => {
  try {
    await signInWithPopup(auth, googleProvider)
    .then(credential =>  {
      const user = credential.user;
      let role = user;
      firestore.collection("users").where("uid", "==", user.uid).get()
      .then(async (querySnapshot) => {
        const matchedDocs = querySnapshot.size;
        if (matchedDocs === 0) {
          const data = {
            uid: user.uid,
            name: user.displayName,
            role: role,
            photoURL: user.photoURL,
            Created_at: parseInt(user.metadata.createdAt),
            authProvider: "google",
            disable: false,
            email: user.email,
          }

          await axios.post(`/register`, data, {
            headers: {
              Accept: "application/json ,text/plain, */*"
            }
          })
          .then(async res => {
            window.location.href = "/profile";
          })
        }
      })
    })
    .catch(error => {
      if(error.message === 'Firebase: Error (auth/user-disabled).'){
        messageAlert("Este usuario seleccionado se encuentra deshabilitado.", "error");
      }
    });
  } catch (err) {
    console.error(err);
    alert(err.message);
  }
};

const messageAlert = async (msg, type) =>{
  toast(msg, {
    type: type,
    autoClose: 2000
  });
}

const logInWithEmailAndPassword = async (email, password) => {
  try {
    await signInWithEmailAndPassword(auth, email, password);
  } catch (err) {
    console.error(err);
    toast(err.message, {
      type: 'error',
      autoClose: 2000
    });
  }
};

const registerWithEmailAndPassword = async (name, email, password, role) => {
  try {
    const res = await createUserWithEmailAndPassword(auth, email, password);
    const user = res.user;
    const data = {
      uid: user.uid,
      name,
      authProvider: "local",
      email,
      Created_at: user.metadata.createdAt,
      photoURL: user.photoURL,
      role
    }
    await firestore.collection("users").add(data);
  } catch (err) {
    console.error(err);
    alert(err.message);
  }
};

const sendPasswordReset = async (email) => {
  try {
    await sendPasswordResetEmail(auth, email);
    alert("Password reset link sent!");
  } catch (err) {
    console.error(err);
    alert(err.message);
  }
};

const logout = () => {
  const auth = getAuth();
  signOut(auth);
  localStorage.clear();
  window.location.href = "/";
};

const storage = firebase.storage();
const firestore = firebase.firestore();
export {
  storage, 
  firestore, 
  auth,
  app,
  signInWithGoogle,
  logInWithEmailAndPassword,
  registerWithEmailAndPassword,
  sendPasswordReset,
  logout
};
