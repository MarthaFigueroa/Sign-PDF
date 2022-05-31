import React, { useState, useEffect } from 'react';
import EditUserForm from './EditUserForm';
import { firestore } from '../../Config/config';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import axios from '../../axios.js';

const Link = () => {

    const { id } = useParams();
    const [user, setUser] = useState([]);

    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    const editUser = async(userObject)=>{
        console.log(userObject);
        await axios.post(`/editUser/${id}`, userObject, {
            headers: {
                // 'Content-Type': 'multipart/form-data',
                Accept: "application/json ,text/plain, */*"
            }
          })
          .then(async res => {
            console.log(res.data);
          })
        // await firestore.collection('users').doc(id).update(userObject);
    }

    const message = async (msg, type) =>{
        type === "success" ? goTo('/users') : console.log("Alert");
        // goTo('/documents');
        console.log(msg);
        await toast(msg, {
            type: type,
            autoClose: 2000
        });
    }
    
    useEffect(() => {
        const getUser = async() =>{
            console.log(id);
            await firestore.collection('users').doc(id).get().then(snapshot => setUser(snapshot.data()))
        }
        getUser();
    }, [id]);

    return(
        <>
            <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Editar Usuario: {user.name}</div>
            <div className="flex w-full">
                {/* <div className='w-full'> */}
                <EditUserForm EditUser={user} id={id} message={message} editUser={editUser}/>
                {/* </div> */}
            </div>
        </>
    )
}

export default Link;