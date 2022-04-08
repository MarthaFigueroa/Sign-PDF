import React from 'react';
import { ToastContainer } from 'react-toastify';
// import 'bootstrap/dist/css/bootstrap.css';
// import NavBar from '../../Components/NavBar.jsx'
// import 'react-toastify/dist/ReactToastify.css';
import ListCard from '../../Components/Documents/ListCard.jsx';
import NavBar from '../../Components/NavBar.jsx';

const ListDocs = () => {
    
    return(
        <div>
            <NavBar />
            {/* <div className="container p-4">
                <div className="row"> */}
                <div className="bg-red-500">
                    <ToastContainer />
                    <ListCard />    
                </div>
                {/* </div>
            </div> */}
        </div>
    )
}

export default ListDocs;