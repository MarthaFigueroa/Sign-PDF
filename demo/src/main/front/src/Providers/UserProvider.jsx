import React, {useState, useEffect, createContext} from "react";
import { app, firestore } from "../Config/config"
import { getAuth } from "firebase/auth";

export const UserContext = createContext()
const UserProvider = (props) => {
  const [user, setuser] = useState(null);
  useEffect(() => {
    let auth = getAuth(app);
    // const users = [];
    auth.onAuthStateChanged(async (user) => {
    if(user !== null){
      const { displayName, email, photoURL, uuid }  = user;
      firestore.collection('users').where("email", '==', email).get()
      .then(async (querySnapshot) => {
        const matchedDocs = querySnapshot.size
        if (matchedDocs) {
          querySnapshot.forEach((doc) => {
              console.log(doc.data());
              // users.push({...doc.data(), id: doc.id});
              setuser({...doc.data(), id: doc.id});          
          })
          // setuser(users);          
        }else{
          setuser({
            displayName, 
            email, 
            photoURL, 
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
