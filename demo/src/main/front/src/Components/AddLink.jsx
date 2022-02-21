import React from 'react';
import AddLinkForm from './AddLinkForm';
import { db } from '../firebaseConfig';
import { toast } from 'react-toastify';

const Links = () => {

    const addOrEditLink = async (linkObject) =>{
        console.log(linkObject);
        await db.collection('links').doc().set(linkObject);
        toast('New Link Added', {
            type: 'success',
            autoClose: 2000
        });
    }

    return(
        <div>
            <AddLinkForm addOrEditLink={addOrEditLink}/>
        </div>
    )
}

export default Links;