import React from 'react';
import NavBar from '../../Components/Partials/Navbar'
import EditUser from '../../Components/Users/EditUser.jsx'
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const EditUsers = () => {
    
    return(
        <div>
            <NavBar />
            <div className="">
                <div className="row">
                    <ToastContainer />
                    <div className="flex w-full justify-center content-center mx-auto mt-5">
                        <div className="">
                            <EditUser />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        )
}

export default EditUsers;