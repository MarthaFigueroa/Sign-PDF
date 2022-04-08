import React, { useState, useEffect } from 'react';
import EditLinkForm from './EditLinkForm';
import { firestore } from '../../firebaseConfig'
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom'
import { useParams } from 'react-router-dom'

const Link = () => {

    const { id } = useParams();
    const [object, setObject] = useState([]);

    const navigate = useNavigate();

    const goTo = (route) =>{
        console.log("kkk");
        navigate(route);
    }

    const editLink = async(linkObject)=>{
        console.log(linkObject);
        await firestore.collection('links').doc(id).update(linkObject);
    }

    const edit = async() =>{
        console.log("GGGGG");
        await goTo('/');
        await toast('User Updated', {
            type: 'info',
            autoClose: 2000,
        });
    }
    
    useEffect(() => {
        const getObject = async() =>{
            console.log(id);
            await firestore.collection('links').doc(id).get().then(snapshot => setObject(snapshot.data()))
        }
        getObject();
    }, [id]);

    return(
        <div className="card">
            <div className="card-header">Edit Contact: {object.firstname} {object.lastname}</div>
            <div className="card-body">
                <EditLinkForm EditObject={object} id={id} edit={edit} editLink={editLink}/>
            </div>
        </div>
    )
}

export default Link;