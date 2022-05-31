import React, { useEffect, useState, useContext } from 'react';
import NavBar from '../../Components/Partials/Navbar'
import ListUser from '../../Components/Users/ListUser';
import {ToastContainer} from 'react-toastify';
import { useNavigate } from "react-router-dom";
import axios from '../../axios.js';
import { UserContext } from '../../Providers/UserProvider';

const ListUsers = () => {

    const user = useContext(UserContext);
    const navigate = useNavigate();
    const [users, setUsers] = useState([]);

    useEffect(() => {
        async function validateUser(user){
            if(user !== null){
                if (user.role === "admin") {
                    const getUsers = async() =>{
                        await axios.get(`/users`, {
                            headers: {
                                Accept: "application/json ,text/plain, */*"
                            }
                        })
                        .then(async res => {
                            console.log(res.data);
                            setUsers(res.data);
                        })
                    }
                    getUsers();
                }
                else{
                    navigate("/documents");
                }
            }
        }
        console.log("User", user);
        validateUser(user);
          
    }, [user, navigate]);
    
    return(
        <>
            {user === null ? null: user.role === "admin" ? 
                <div>
                    <NavBar />
                    <div>
                        <ToastContainer />
                        <ListUser users={users}/>
                    </div>
                </div>: 
                null
            }
        </>
    )
}

export default ListUsers;