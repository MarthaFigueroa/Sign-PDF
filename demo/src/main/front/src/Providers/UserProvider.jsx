import React, {useState, useEffect, createContext} from "react";
import { app, firestore } from "../Config/config"
import { getAuth } from "firebase/auth";

export const UserContext = createContext()
const UserProvider = (props) => {
  
  const [user, setuser] = useState(null);
  
  useEffect(() => {
    let auth = getAuth(app);
    auth.onAuthStateChanged(async (user) => {
    if(user !== null){
      const { displayName, email, photoURL, uuid }  = user;
      let photoUrl2 = photoURL+auth.getCurrentAccessToken;
      firestore.collection('users').where("email", '==', email).get()
      .then(async (querySnapshot) => {
        const matchedDocs = querySnapshot.size
        if (matchedDocs) {
          querySnapshot.forEach((doc) => {
              setuser({...doc.data(), id: doc.id});          
          })       
        }else{
          setuser({
            displayName, 
            email, 
            photoUrl2, 
            uuid
          });
        }
      })
  }
})
  },[])
  return (
    <UserContext.Provider value={user}>{props.children}</UserContext.Provider>
  )
}

export default UserProvider;
