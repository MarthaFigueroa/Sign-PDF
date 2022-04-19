import React from 'react';
import NavBar from '../../Components/Navbar'
import ListDoc from '../../Components/Documents/ListDoc.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ListDocs = () => {
    
    return(
        <div>
            <NavBar />
            <div className="container p-4">
                <div className="row">
                    <ToastContainer />
                    <ListDoc />
                </div>
            </div>
        </div>
    )
}

export default ListDocs;