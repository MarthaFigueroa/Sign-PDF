import React from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import NavBar from '../../Components/NavBar.jsx'
import UploadFile from '../../Components/Documents/UploadFile.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const UploadFiles = () => {
    
    return(
        <div>
            <NavBar />
            <div className="container p-4">
                <div className="row">
                    <ToastContainer />
                    <div className="col-md-6 offset-md-3">
                        <div className="card">
                            <div className="card-header">Upload New File</div>
                            <div className="card-body">
                                <UploadFile />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        )
}

export default UploadFiles;