import React from 'react';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ListLink from './../../Components/Users/ListLink'
import Navbar from '../../Components/ExtraComponents/Navbar';

const ListLinks = () => {
    
    return(
        <div>
            <Navbar />
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