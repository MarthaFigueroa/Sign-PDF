import React from 'react';
import NavBar from '../../Components/ExtraComponents/Navbar'
import UploadFile from '../../Components/Documents/UploadFile.jsx';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const UploadFiles = ({type}) => {
    
    return(
        <div>
            <NavBar />
            <div className="w-full p-4">
                <div className="max-w-100">
                    <ToastContainer />
                    <div className="flex w-full justify-center">
                        <div className="relative flex flex-col bg-gray-100 rounded-xl shadow-lg border  xl:w-1/4 md:w-1/2">
                            <UploadFile type={type}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        )
}

export default UploadFiles;