import React from 'react';
import { ToastContainer } from 'react-toastify';
// import NavBar from '../../Components/NavBar.jsx'
// import 'react-toastify/dist/ReactToastify.css';
import ListCert from '../../Components/Certificates/ListCert';
import Navbar from '../../Components/ExtraComponents/Navbar';

const ListDocs = () => {
    
    return(
        <div>
            <Navbar />
            {/* <div className="container p-4">
                <div className="row"> */}
                <div>
                    <ToastContainer />
                    <ListCert />    
                </div>
                </div>
        //     </div>
        // </div>
    )
}

export default ListDocs;