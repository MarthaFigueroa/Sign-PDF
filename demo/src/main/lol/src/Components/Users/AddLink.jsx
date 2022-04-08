import React from 'react';
import AddLinkForm from './AddLinkForm';
import { useNavigate } from 'react-router-dom'
import { firestore } from '../../firebaseConfig';
import { toast } from 'react-toastify';

const Links = () => {

    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    // const sleep = (milliseconds) => {
    //     return new Promise(resolve => setTimeout(resolve, milliseconds))
    // }

    const addOrEditLink = async (linkObject) =>{
        console.log(linkObject);
        await firestore.collection('links').doc().set(linkObject);
        // window.location.href = "/";
        await goTo('/');
        await toast('New User Created', {
            type: 'success',
            autoClose: 2000
        });
    }

    const add = async() =>{
        toast('New User Created', {
            type: 'success',
            autoClose: 2000,
            // icon: ({theme, type}) =>  <img src="../logo.svg" alt=""/>
        });
        // await goTo('/');
    }

    return(
        <div>
            <AddLinkForm addOrEditLink={addOrEditLink} add={add}/>
        </div>
    )
}

export default Links;