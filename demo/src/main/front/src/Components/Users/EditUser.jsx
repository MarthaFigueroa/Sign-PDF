import React, { useState, useEffect, useContext } from 'react';
import EditUserForm from './EditUserForm';
import { firestore } from '../../Config/config';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { UserContext } from "../../Providers/UserProvider";
import axios from '../../axios.js';

const Link = () => {

    const { id } = useParams();
    const [user, setUser] = useState([]);
    const currUser = useContext(UserContext);


    const navigate = useNavigate();

    const goTo = (route) =>{
        navigate(route);
    }

    const editUser = async(userObject)=>{
        await axios.post(`/editUser/${id}`, userObject, {
            headers: {
                Accept: "application/json ,text/plain, */*"
            }
          })
          .then(async res => {
          })
    }

    const message = (msg, type) =>{
        if(currUser.role === "admin" && type === "success"){
            goTo('/users');
        }else if(currUser.role === "user" && type === "success"){
            goTo('/profile');
        }
        toast(msg, {
            type: type,
            autoClose: 2000
        });
    }
    
    useEffect(() => {
        const getUser = async() =>{
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