import React, { useContext, useEffect, useState } from 'react';
import NavBar from '../../Components/Partials/Navbar'
import UploadCert from '../../Components/Certificates/UploadCert.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from '../../axios.js';
import { useNavigate } from "react-router-dom";
import { UserContext } from '../../Providers/UserProvider';

const UploadCerts = () => {

    const user = useContext(UserContext);
    const navigate = useNavigate();
    // const [users, setUsers] = useState([]);

    useEffect(() => {
        function validateUser(user){
            if(user !== null){
                if (user.role === "admin") {
                    // const getUsers = async() =>{
                    //     await axios.get(`/users`, {
                    //         headers: {
                    //             Accept: "application/json ,text/plain, */*"
                    //         }
                    //     })
                    //     .then(async res => {
                    //         console.log(res.data);
                    //         const usersArr = res.data;
                    //         setUsers(usersArr);
                    //     })
                    // }
                    // getUsers();
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
                    <div className="w-full p-4">
                        <div className="max-w-100">
                            <ToastContainer />
                            <div className="flex w-full justify-center">
                                <div className="relative flex flex-col bg-gray-100 rounded-xl shadow-lg border  xl:w-1/4 md:w-1/2">
                                    {/* <div className="p-3 bg-icon-group text-white flex justify-center rounded-t-xl font-bold text-xl border-b-2">Upload New Cert</div>
                                    <div className="flex p-2"> */}
                                        <UploadCert />
                                    {/* </div> */}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>: 
                null
            }
        </>
        )
}

export default UploadCerts;