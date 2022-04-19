import React from 'react';
import NavBar from '../../Components/Navbar'
import ListSignedDoc from '../../Components/Documents/ListSignedDoc';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ListSignedDocs = () => {
    
    return(
        <div>
            <NavBar />
            <div className="container p-4">
                <div className="row">
                    <ToastContainer />
                    <ListSignedDoc />
                </div>
            </div>
        </div>
    )
}

export default ListSignedDocs;