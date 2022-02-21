import React from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import NavBar from '../Components/NavBar.jsx'
import ListLink from '../Components/ListLink.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ListLinks = () => {
    
    return(
        <div>
            <NavBar />
            <div className="container p-4">
                <div className="row">
                    <ToastContainer />
                    <ListLink />
                </div>
            </div>
        </div>
        )
}

export default ListLinks;