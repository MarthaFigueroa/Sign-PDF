import React from 'react';
import { ToastContainer } from 'react-toastify';
// import NavBar from '../../Components/NavBar.jsx'
// import 'react-toastify/dist/ReactToastify.css';
import ListCard from '../../Components/Documents/ListCard';
import Navbar from '../../Components/Navbar';

const ListDocs = () => {
    
    return(
        <div>
            <Navbar />
            {/* <div className="container p-4">
                <div className="row"> */}
                <div>
                    <ToastContainer />
                    <ListCard />    
                </div>
                {/* </div>
            </div> */}
        </div>
    )
}

export default ListDocs;