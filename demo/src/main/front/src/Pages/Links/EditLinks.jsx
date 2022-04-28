import React from 'react';
import NavBar from '../../Components/ExtraComponents/Navbar'
import EditLink from '../../Components/Users/EditLink.jsx'
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const EditLinks = () => {
    
    return(
        <div>
            <NavBar />
            <div className="container p-4">
                <div className="row">
                    <ToastContainer />
                    <div className="col-md-6 offset-md-3">
                        <EditLink />
                    </div>
                </div>
            </div>
        </div>
        )
}

export default EditLinks;