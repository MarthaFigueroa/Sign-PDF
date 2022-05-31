import React from 'react';
import NavBar from '../../Components/Partials/Navbar'
import ListDoc from '../../Components/Documents/ListDoc.jsx';
import {ToastContainer} from 'react-toastify';

const ListDocs = () => {
    
    return(
        <div>
            <NavBar />
            {/* <div className="container p-4">
                <div className="row"> */}
                <div>
                    <ToastContainer />
                    <ListDoc />
                </div>
                </div>
        //     </div>
        // </div>
    )
}

export default ListDocs;