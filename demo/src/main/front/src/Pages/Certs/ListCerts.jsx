import React from 'react';
import { ToastContainer } from 'react-toastify';
import ListCert from '../../Components/Certificates/ListCert';
import Navbar from '../../Components/Partials/Navbar';

const ListCerts = () => {
    
    return(
        <div>
            <Navbar />
            <div>
                <ToastContainer />
                <ListCert />    
            </div>
        </div>

    )
}

export default ListCerts;