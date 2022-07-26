import React, { useContext, useEffect } from 'react';
import NavBar from '../../Components/Partials/Navbar'
import UploadCert from '../../Components/Certificates/UploadCert.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from "react-router-dom";
import { UserContext } from '../../Providers/UserProvider';

const UploadCerts = () => {

    const user = useContext(UserContext);
    const navigate = useNavigate();

    useEffect(() => {
        function validateUser(user){
            if(user !== null){
                if (user.role !== "admin") {
                    navigate("/documents");
                }
            }
        }
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
                                    <UploadCert />
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