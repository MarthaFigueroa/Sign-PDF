import React, { useState, useEffect } from 'react';
import EditLinkForm from './EditLinkForm';
import { db } from '../firebaseConfig'
import { useParams } from 'react-router-dom'

const Link = () => {

    const { id } = useParams();
    const [object, setObject] = useState("");

    const getObject = async() =>{
        // await db.collection('links').doc(id).get()
        console.log(id);
        await db.collection('links').onSnapshot(snapshot => {
            let docs = {};
            snapshot.forEach((doc) => {
                if(doc.id === id){
                    docs = {...doc.data(), id: doc.id};
                    setObject(docs);
                }
            });
        });
    }

    useEffect(() => {
        getObject();
    });

    return(
        <div>
            <EditLinkForm object={object}/>
        </div>
    )
}

export default Link;